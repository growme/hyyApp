package com.ccnet.admin.controller.api;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.domain.CheckDomainUtil;
import com.ccnet.core.common.utils.email.MailConfig;
import com.ccnet.core.common.utils.email.MailUtil;
import com.ccnet.core.common.utils.sms.SenderSMSUtil;
import com.ccnet.core.entity.Email;
import com.ccnet.core.entity.SbPromoteLink;
import com.ccnet.core.service.SbPromoteLinkService;
/**
 * 推广地址检测
 * @author jackie wang
 *
 */
public class AutoCheckPromoteLinkHelper {
	
	//业务处理
	private static SbPromoteLinkService promoteLinkService = (SbPromoteLinkService) SpringWebContextUtil.getApplicationContext().getBean("sbPromoteLinkService"); 
	
	/**
     * 检测入口任务
     */
    public static void autoCheckPromoteLink() {
    	//获取域名检测的参数
    	String api_user_id = CPSUtil.getParamValue("DOMIAN_API_USER_ID");
    	String api_user_key = CPSUtil.getParamValue("DOMIAN_API_USER_KEY");
    	String sleep_time = CPSUtil.getParamValue("DOMIAN_API_SLEEP_TIME");//控制频率
    	List<SbPromoteLink> linksList = promoteLinkService.getCheckValidPromoteLinkList();
    	if(CPSUtil.isNotEmpty(api_user_id) && CPSUtil.isNotEmpty(api_user_key)){
    	     if(CPSUtil.listNotEmpty(linksList)){
	    		for (SbPromoteLink link : linksList) {
	    			String linkState = CheckDomainUtil.domainIsEnabledVip(api_user_key, link.getLinkAddr());
	    			CPSUtil.xprint("domain="+link.getLinkAddr()+" state="+linkState.trim());
	    			if(CPSUtil.isNotEmpty(linkState)){
	    				try {
	    					//处理域名状态 只修改已经屏蔽的域名
	    					if(linkState.equals(DomainStateType.InValid.getState()+"")){
		    					link.setUpdateTime(new Date());
		    					link.setState(Integer.valueOf(linkState.trim()));
		    					promoteLinkService.editSbPromoteLinkInfo(link);
		        				CPSUtil.xprint("全局推广地址检测频率间隔："+sleep_time);
		        				
		        				//发送短信预警
		        				sendNoticeSms("推广地址",link.getLinkAddr());
		        				//域名检测被封发送邮件
		        				sendNoticeMail("推广地址",link.getLinkAddr());
		        				
		        				if(CPSUtil.isEmpty(sleep_time)){
		        					Thread.sleep(1000);
		        					CPSUtil.xprint("域名检测频率间隔：1秒");
		        				}else{
		        					Thread.sleep(Long.valueOf(sleep_time));
		        				}
	    					}
						} catch (Exception e) {
							e.printStackTrace();
						}
	    			}
				}
	    		
	    		//更新缓存
	    		InitSystemCache.updateCache(Const.CT_FORWARD_LINK_LIST);
				HessianClientUtil.updateCache(Const.CT_FORWARD_LINK_LIST);
    		}
    	}
	}
    
    /**
     * 域名发送预警邮件
     * @param domain
     */
    private static void sendNoticeMail(String domain_type,String domain_name){
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
				email.setSubject("微赚平台入口预警提醒");
				email.setCreateTime(new Date());
				email.setBody("【预警信息】微赚平台的【"+domain_type+"】地址【"+domain_name+"】已经被腾讯封禁，请立即核实处理！");
				MailConfig mc=new MailConfig(mailSmtp,"465",mailAccount,"微赚平台入口预警提醒",mailAccount,mailPassword);
				boolean res = MailUtil.send(mc, email.getToList(), email.getSubject(), email.getBody());
				if(res){
					CPSUtil.xprint("预警邮件发送成功...");
				}else{
					CPSUtil.xprint("预警邮件发送失败...");
				}
			}
		}
    }
    
    /**
     * 发送预警短信
     * @param domain
     * @return
     */
    public static void sendNoticeSms(String domain_type,String domain_name){
    	String sendSms = CPSUtil.getParamValue(Const.CT_SEND_SMS);
		String smsAccount = CPSUtil.getParamValue(Const.CT_SMS_ACCOUNT);
		String smsPassword = CPSUtil.getParamValue(Const.CT_SMS_PASSOWRD);
		String smsTemplate = CPSUtil.getParamValue(Const.CT_SMS_TEMPLATE);
		String recPhone = CPSUtil.getParamValue(Const.CT_SMS_REC_PHONE);
		if(CPSUtil.isNotEmpty(sendSms) && sendSms.equals(StateType.Valid.getState()+"")){//开启发送短信
			if(StringHelper.checkParameter(smsAccount,smsPassword,smsTemplate,recPhone)){
				
				if(smsTemplate.contains("domain_type")){//域名类型
					smsTemplate = smsTemplate.replaceAll("\\$domain_type", domain_type);
				}
				
				if(smsTemplate.contains("domain_name")){//域名名称
					smsTemplate = smsTemplate.replaceAll("\\$domain_name", domain_name);
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
    

}
