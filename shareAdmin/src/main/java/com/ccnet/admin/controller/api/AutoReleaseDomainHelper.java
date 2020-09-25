package com.ccnet.admin.controller.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.email.MailConfig;
import com.ccnet.core.common.utils.email.MailUtil;
import com.ccnet.core.common.utils.sms.SenderSMSUtil;
import com.ccnet.core.entity.Email;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.service.ContentDomainInfoService;

/**
 * 自动释放备用域名任务
 * @author jackieWang
 *
 */
public class AutoReleaseDomainHelper {
	
	//业务处理
	private static ContentDomainInfoService domainInfoService = (ContentDomainInfoService) SpringWebContextUtil.getApplicationContext().getBean("contentDomainInfoService"); 
		
	/**
     * 释放域名任务
     */
    public static void autoReleaseDomain() {
    	int num = 2;
    	//每次释放备用域名个数
    	String  releaseNum = CPSUtil.getParamValue(Const.CT_AUTO_RELEASE_DOMAIN_NUM);
    	if(CPSUtil.isNotEmpty(releaseNum)){
    		num = Integer.valueOf(releaseNum);
    	}
    	CPSUtil.xprint("自动释放备用域名个数为：" + num);
    	
    	//获取所有正常跳转域名
    	List<SbContentDomain> tzymList = domainInfoService.getValidContentDomainList(ContentDomainType.TZYM.getType());
    	//获取所有正常推广域名
    	List<SbContentDomain> tgymList = domainInfoService.getValidContentDomainList(ContentDomainType.TGYM.getType());
    	
    	//获取当前跳转域名剩余正常域名，当剩余不足3个时开始释放备用跳转域名
    	boolean bool = false;
    	List<Integer> domainIds = null;
    	if(CPSUtil.listNotEmpty(tzymList) && tzymList.size() < 3){
    		CPSUtil.xprint("当前系统跳转域名数不足3个，开始释放备用跳转域名.......");
    		List<SbContentDomain> tzbyList = getRandomDomain(ContentDomainType.TZBY.getType(),num);
    		if(CPSUtil.listNotEmpty(tzbyList) && tzbyList.size() >= num){
    			domainIds = getDomainIds(tzbyList);
    			bool = domainInfoService.updateContentDomainState(DomainStateType.Valid.getState(), domainIds,ContentDomainType.TZYM.getType());
    			if(bool){
    				CPSUtil.xprint("批量释放【"+ num +"】备用跳转域名成功.....");
    			}
    			domainIds = null;
    		}else{
    			//发送短信预警
				sendNoticeSms(ContentDomainType.TZBY,num);
				//域名检测被封发送邮件
				sendNoticeMail(ContentDomainType.TZBY,num);
    		}
    	}
    	
    	//获取当前推广域名剩余正常域名，当剩余不足3个时开始释放备用推广域名
        if(CPSUtil.listNotEmpty(tgymList) && tgymList.size() < 3){
        	CPSUtil.xprint("当前系统推广域名数不足3个，开始释放备用推广域名.......");
        	List<SbContentDomain> tgbyList = getRandomDomain(ContentDomainType.TGBY.getType(),num);
    		if(CPSUtil.listNotEmpty(tgbyList) && tgbyList.size() >= num){
    			domainIds = getDomainIds(tgbyList);
    			bool = domainInfoService.updateContentDomainState(DomainStateType.Valid.getState(), domainIds,ContentDomainType.TGYM.getType());
    			if(bool){
    			  CPSUtil.xprint("批量释放【"+ num +"】备用推广域名成功.....");
    			}
    			
    			domainIds = null;
    		}else{
    			//发送短信预警
				sendNoticeSms(ContentDomainType.TGBY,num);
				//域名检测被封发送邮件
				sendNoticeMail(ContentDomainType.TGBY,num);
    		}
    	}
        
        if(bool){
			//同步内存信息
			InitSystemCache.updateCache(Const.CT_DOMAIN_LIST);
			InitSystemCache.updateCache(Const.CT_ZS_DOMAIN_LIST);
			HessianClientUtil.updateCache(Const.CT_DOMAIN_LIST);
			HessianClientUtil.updateCache(Const.CT_ZS_DOMAIN_LIST);
		}
        
    }

	/**
	 * 随机获取域名
	 * 
	 * @param type
	 * @param num
	 * @return
	 */
	public static List<SbContentDomain> getRandomDomain(Integer type, int count) {
		
		List<SbContentDomain> domainList = domainInfoService.getValidContentDomainList(type);
		if (domainList.size() < count) {
			return domainList;
		}
		int index = 0;
		Random random = new Random();
		List<Integer> indexList = new ArrayList<Integer>();
		List<SbContentDomain> newList = new ArrayList<SbContentDomain>();
		for (int i = 0; i < count; i++) {
			index = random.nextInt(domainList.size());// 将产生的随机数作为被抽list的索引
			if (!indexList.contains(index)) {
				indexList.add(index);
				newList.add(domainList.get(index));
			} else {
				i--;
			}
		}
		CPSUtil.xprint("newList=="+newList);
		return newList;
	}

    
    
    /**
     * 获取域名ID
     * @param domains
     * @return
     */
    private static List<Integer> getDomainIds(List<SbContentDomain> domains){
    	List<Integer> domainIds = null;
    	if(CPSUtil.listNotEmpty(domains)){
    		domainIds = new ArrayList<Integer>();
    		for (SbContentDomain _domain : domains) {
    			domainIds.add(Integer.valueOf(_domain.getDomainId()));
			}
    	}
    	CPSUtil.xprint("domainIds=="+domainIds);
    	return domainIds;
    }
    
    /**
     * 发送域名不足预警短信
     * @param domainType
     * @param num
     */
    public static void sendNoticeSms(ContentDomainType domainType,int num){
    	String sendSms = CPSUtil.getParamValue(Const.CT_SEND_SMS);
		String smsAccount = CPSUtil.getParamValue(Const.CT_SMS_ACCOUNT);
		String smsPassword = CPSUtil.getParamValue(Const.CT_SMS_PASSOWRD);
		String recPhone = CPSUtil.getParamValue(Const.CT_SMS_REC_PHONE);
		String smsTemplate = CPSUtil.getParamValue(Const.CT_SMS_BYYM_TEMPLATE);
		
		if(CPSUtil.isNotEmpty(sendSms) && sendSms.equals(StateType.Valid.getState()+"")){//开启发送短信
			if(StringHelper.checkParameter(smsAccount,smsPassword,smsTemplate,recPhone)){
				
				if(smsTemplate.contains("domain_type")){//域名类型
					smsTemplate = smsTemplate.replaceAll("\\$domain_type", domainType.getName());
				}
				
				if(smsTemplate.contains("domain_num")){//域名个数
					smsTemplate = smsTemplate.replaceAll("\\$domain_num", String.valueOf(num));
				}
				
				String smString = smsTemplate;
				CPSUtil.xprint("发送提示短信内容为>>>"+smString);
				if(SenderSMSUtil.sendSms(smsAccount,smsPassword,recPhone,smString)){
					CPSUtil.xprint("提示发送成功!");
				}else{
					CPSUtil.xprint("提示短信发送失败!");
				}
				
			}
		}
    }
    
    
    /**
     * 域名不足发送预警邮件
     * @param domainType
     * @param num
     */
    private static void sendNoticeMail(ContentDomainType domainType,int num){
    	//获取发送邮件总开关
    	String sendMail = CPSUtil.getParamValue(Const.CT_SEND_MAIL);
    	String mailSmtp = CPSUtil.getParamValue(Const.CT_MAIL_SMTP);
    	String mailAccount = CPSUtil.getParamValue(Const.CT_MAIL_ACCOUNT);
		String mailPassword = CPSUtil.getParamValue(Const.CT_MAIL_PASSOWRD);
		String receiveAddress = CPSUtil.getParamValue(Const.CT_MAIL_REC_ADDR);
		if(CPSUtil.isNotEmpty(sendMail) && sendMail.equals(StateType.Valid.getState()+"")){//开启发送邮件
			if(StringHelper.checkParameter(mailAccount,mailPassword,receiveAddress)){
				Email email = new Email();
				email.setToList(receiveAddress);
				email.setId(UniqueID.getUniqueID(6, 0));
				email.setSubject("微赚平台备用域名不足预警");
				email.setCreateTime(new Date());
				email.setBody("温馨提示，微赚平台的【"+domainType.getName()+"】已经不足【"+num+"】个，请立即核实处理！");
				MailConfig mc=new MailConfig(mailSmtp,"465",mailAccount,"微赚平台备用域名不足预警",mailAccount,mailPassword);
				boolean res = MailUtil.send(mc, email.getToList(), email.getSubject(), email.getBody());
				if(res){
					CPSUtil.xprint("预警邮件发送成功...");
				}else{
					CPSUtil.xprint("预警邮件发送失败...");
				}
			}
		}
    }

}
