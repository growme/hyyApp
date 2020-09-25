package com.ccnet.core.common.cache;

import java.util.ArrayList;
import java.util.List;

import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.core.entity.SbMemberDomain;
import com.ccnet.core.entity.SbPromoteLink;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.service.ContentDomainInfoService;
import com.ccnet.core.service.MemberDomainService;
import com.ccnet.core.service.SbForwardLinkService;
import com.ccnet.core.service.SbPromoteLinkService;
import com.ccnet.core.service.SystemCodeService;
import com.ccnet.core.service.SystemParamService;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.SbAdvertiseInfoService;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbContentInfoService;

/**
 * 缓存处理
 * @author jackie wang
 *
 */
public class InitSystemCache {
	
	private static boolean success = true;
	private static Dto reDto = new BaseDto();
	
	private static MemberInfoService memberInfoService = (MemberInfoService) SpringWebContextUtil.getApplicationContext().getBean("memberInfoService"); 
	private static SystemCodeService codeInfoService = (SystemCodeService) SpringWebContextUtil.getApplicationContext().getBean("systemCodeService");  
	private static SystemParamService paramInfoService = (SystemParamService) SpringWebContextUtil.getApplicationContext().getBean("systemParamService");  
	private static SbColumnInfoService columnInfoService = (SbColumnInfoService) SpringWebContextUtil.getApplicationContext().getBean("sbColumnInfoService");  
	private static SbAdvertiseInfoService advertiseInfoService=(SbAdvertiseInfoService)SpringWebContextUtil.getApplicationContext().getBean("sbAdvertiseInfoService"); 
	private static MemberDomainService memberDomainInfoService = (MemberDomainService) SpringWebContextUtil.getApplicationContext().getBean("memberDomainService"); 
	private static ContentDomainInfoService domainInfoService = (ContentDomainInfoService) SpringWebContextUtil.getApplicationContext().getBean("contentDomainInfoService"); 
	private static SbContentInfoService contentInfoService = (SbContentInfoService) SpringWebContextUtil.getApplicationContext().getBean("sbContentInfoService"); 
	private static SbForwardLinkService forwardLinkService = (SbForwardLinkService) SpringWebContextUtil.getApplicationContext().getBean("sbForwardLinkService"); 
	private static SbPromoteLinkService promoteLinkService = (SbPromoteLinkService) SpringWebContextUtil.getApplicationContext().getBean("sbPromoteLinkService"); 
	
	public static Dto initCache() {
		try {
			CPSUtil.xprint("开始加载数据库中所有公用数据到缓存中...");
			//加载系统字典信息
			if(success){
				initCodeCache();
			}
			//处理全局参数
			if(success){
				initParamCache();
			}
			//处理会员信息
			if(success){
				//initMemberInfoCache();
			}
			//处理栏目信息
			if(success){
				initColumnCache();
			}
			//处理文章信息
			if(success){
				//initContentCache();
			}
			//处理派单域名
			if(success){
				initContentDomain();
			}
			//处理专属派单域名
			if(success){
				initZContentDomain();
			}
			//处理跳转入口
			if(success){
				initForwardCache();
			}
			//处理推广地址
			if(success){
				initPromoteCache();
			}
			//处理广告信息
			if(success){
				initAdvertiseCache();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reDto;
	}
	
	
	
	/**
	 * 处理会员信息
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2018-04-02
	 */
	@SuppressWarnings("unchecked")
	private static void initMemberInfoCache() {
		
		Dto memberDto = new BaseDto();
		MemberInfo memebrInfo = new MemberInfo();
		List<MemberInfo> memberList = memberInfoService.findList(memebrInfo);
		if(!CPSUtil.checkListBlank(memberList)){
			
//			List<MemberInfo> tdList = null;//徒弟集合
//			List<MemberInfo> tsList = null;//徒孙集合
//			for (MemberInfo member : memberList) {
//				//处理徒弟
//				tdList = memberInfoService.findRecomMemberInfoByVisitCode(member.getVisitCode());
//				if(CPSUtil.listNotEmpty(tdList)){
//					memberDto.put(member.getMemberId()+"_"+Const.CT_TD_MEMBER_LIST, tdList);
//					CPSUtil.xprint("=======会员【"+member.getLoginAccount()+"】徒弟:["+tdList.size()+"]个=======");
//				}
//				//处理徒孙
//				tsList = memberInfoService.findChildMemberInfoByVisitCode(member.getVisitCode());
//				if(CPSUtil.listNotEmpty(tsList)){
//					memberDto.put(member.getMemberId()+"_"+Const.CT_TS_MEMBER_LIST, tsList);
//					CPSUtil.xprint("=======会员【"+member.getLoginAccount()+"】徒孙:["+tsList.size()+"]个=======");
//				}
//				//保存单个会员信息
//				memberDto.put(member.getVisitCode(), member);
//				memberDto.put(member.getMemberId()+"_mid", member);
//			}
//
//			reDto.put(Const.CT_SYSTEM_MEMBER_LIST,memberDto);
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何会员信息！>>>>>>>>>>>=======");
		}
		
	}
	
	
	/**
	 * 处理文章数据
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2018-04-02
	 */
	@SuppressWarnings("unchecked")
	private static void initContentCache() {
		Dto contentDto = new BaseDto();
		SbContentInfo contentInfo = new SbContentInfo();
//		List<SbContentInfo> contentList = contentInfoService.findSbContentInfoList(contentInfo);
//		if(!CPSUtil.checkListBlank(contentList)){
//			for (SbContentInfo content : contentList) {
//				//CPSUtil.xprint("type=="+content.getContentType());
//				contentDto.put(content.getContentId(), content);
//			}
//			reDto.put(Const.CT_CONTENT_INFO_LIST,contentDto);
//			CPSUtil.xprint("=======共加载【"+contentDto.size()+"】条文章数据！=======");
//		}else{
//			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何文章信息！>>>>>>>>>>>=======");
//		}
	}
	
	
	/**
	 * 处理专属域名
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	@SuppressWarnings("unchecked")
	private static void initZContentDomain() {
		
		Dto domainDto = new BaseDto();
		SbMemberDomain mdomain = new SbMemberDomain();
		List<SbMemberDomain> domainList = memberDomainInfoService.getMemberContentDomainList(mdomain);
		if(!CPSUtil.checkListBlank(domainList)){
			
			List<SbMemberDomain> tgymList = new ArrayList<SbMemberDomain>();//推广域名
			List<SbMemberDomain> tzymList = new ArrayList<SbMemberDomain>();//跳转域名
			List<SbMemberDomain> tgbyList = new ArrayList<SbMemberDomain>();//推广备用
			List<SbMemberDomain> tzbyList = new ArrayList<SbMemberDomain>();//跳转备用
			
			for (SbMemberDomain odomain : domainList) {
				SbContentDomain domain = odomain.getContentDomain();
				if(CPSUtil.isNotEmpty(domain) && domain.getEnabled().equals(DomainStateType.Valid.getState())){
					if(domain.getDomainType().equals(ContentDomainType.TGYM.getType())){
						tgymList.add(odomain);
					}
					if(domain.getDomainType().equals(ContentDomainType.TZYM.getType())){
						tzymList.add(odomain);
					}
					if(domain.getDomainType().equals(ContentDomainType.TGBY.getType())){
						tgbyList.add(odomain);
					}
					if(domain.getDomainType().equals(ContentDomainType.TZBY.getType())){
						tzbyList.add(odomain);
					}
				}
			}

			domainDto.put(Const.CT_ZS_TGYM_DOMAIN_LIST, tgymList);
			CPSUtil.xprint("=======共加载【"+tgymList.size()+"】条专属派单域名！=======");
			domainDto.put(Const.CT_ZS_TZYM_DOMAIN_LIST, tzymList);
			CPSUtil.xprint("=======共加载【"+tzymList.size()+"】条专属跳转域名！=======");
			domainDto.put(Const.CT_ZS_TGBY_DOMAIN_LIST, tgbyList);
			CPSUtil.xprint("=======共加载【"+tgbyList.size()+"】条专属推广备用域名！=======");
			domainDto.put(Const.CT_ZS_TZBY_DOMAIN_LIST, tzbyList);
			CPSUtil.xprint("=======共加载【"+tzbyList.size()+"】条专属跳转备用域名！=======");
			reDto.put(Const.CT_ZS_DOMAIN_LIST,domainDto);
			CPSUtil.xprint("=======共加载【"+domainList.size()+"】专属条派单域名！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有配置任何专属域名！>>>>>>>>>>>=======");
		}
		
	}
	
	
	/**
	 * 加载域名
	 */
	@SuppressWarnings("unchecked")
	private static void initContentDomain() {
		Dto domainDto = new BaseDto();
		List<SbContentDomain> domainList = domainInfoService.getValidContentDomainList();
		if(!CPSUtil.checkListBlank(domainList)){
			
			List<SbContentDomain> tgymList = new ArrayList<SbContentDomain>();//推广域名
			List<SbContentDomain> tzymList = new ArrayList<SbContentDomain>();//跳转域名
			List<SbContentDomain> tgbyList = new ArrayList<SbContentDomain>();//推广备用
			List<SbContentDomain> tzbyList = new ArrayList<SbContentDomain>();//跳转备用
			for (SbContentDomain domain : domainList) {
				//非专属类
				if(StateType.InValid.getState().equals(domain.getPersonal())){
					if(domain.getDomainType().equals(ContentDomainType.TGYM.getType())){
						tgymList.add(domain);
					}
					if(domain.getDomainType().equals(ContentDomainType.TZYM.getType())){
						tzymList.add(domain);
					}
					if(domain.getDomainType().equals(ContentDomainType.TGBY.getType())){
						tgbyList.add(domain);
					}
					if(domain.getDomainType().equals(ContentDomainType.TZBY.getType())){
						tzbyList.add(domain);
					}
				}
			}
			
			domainDto.put(Const.CT_TGYM_DOMAIN_LIST, tgymList);
			CPSUtil.xprint("=======共加载【"+tgymList.size()+"】条派单域名！=======");
			domainDto.put(Const.CT_TZYM_DOMAIN_LIST, tzymList);
			CPSUtil.xprint("=======共加载【"+tzymList.size()+"】条跳转域名！=======");
			domainDto.put(Const.CT_TGBY_DOMAIN_LIST, tgbyList);
			CPSUtil.xprint("=======共加载【"+tgbyList.size()+"】条推广备用域名！=======");
			domainDto.put(Const.CT_TZBY_DOMAIN_LIST, tzbyList);
			CPSUtil.xprint("=======共加载【"+tzbyList.size()+"】条跳转备用域名！=======");
			reDto.put(Const.CT_DOMAIN_LIST,domainDto);
			CPSUtil.xprint("=======共加载【"+domainList.size()+"】公共条派单域名！=======");
			
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有配置任何域名！>>>>>>>>>>>=======");
		}
	}
	
	
	private static void initCodeCache(){
		//获取字典参数名称集合
		Dto codeDto = null;
		List<SystemCode> valueList = null;
		List<SystemCode> codeList = codeInfoService.queryCodeList();
		//加入全局缓存
		List<String> keyList = dealRepeatKey(codeList);
		if(!CPSUtil.checkListBlank(keyList)){
			codeDto = new BaseDto();
			for(String code_key : keyList) {
				valueList = codeInfoService.queryCodeListByKey(code_key);
				if(CPSUtil.isNotEmpty(valueList)){
					codeDto.put(code_key, valueList);
				}
			}
			reDto.put(Const.CT_CODE_LIST,codeDto);
			CPSUtil.xprint("=======共加载【"+codeDto.size()+"】条字典参数！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何字典参数！>>>>>>>>>>>=======");
		}
	}
	
	/**
	 * 处理重复
	 * @param @param clist   
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-5-12
	 */
	private static List<String> dealRepeatKey(List<SystemCode> clist){
		List<String> rlist = null;
		if(CPSUtil.listNotEmpty(clist)){
			rlist = new ArrayList<String>();
			for (SystemCode systemCode : clist) {
				if(!rlist.contains(systemCode.getCodeKey())){
					rlist.add(systemCode.getCodeKey());
				}
			}
		}
		return rlist;
	}
	
	/**
	 * 初始化系统参数
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-10-23
	 */
	private static void initParamCache(){
		Dto paramDto = null;
		List<SystemParams> paramList = paramInfoService.queryParamList();
		if(!CPSUtil.checkListBlank(paramList)){
			paramDto = new BaseDto();
			for (SystemParams sp : paramList) {
				paramDto.put(sp.getParamKey(), sp);
			}
			reDto.put(Const.CT_PARAM_LIST,paramDto);
			CPSUtil.xprint("=======共加载【"+paramDto.size()+"】条全局参数！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何全局参数！>>>>>>>>>>>=======");
		}
	}
	
	
	
	/**
	 * 初始化入口
	 * @throws
	 * @author Jackie Wang
	 * @date 2018-08-29
	 */
	private static void initForwardCache(){
		List<SbForwardLink> linkList = forwardLinkService.getValidForwardLinkList();
		if(!CPSUtil.checkListBlank(linkList)){
			reDto.put(Const.CT_FORWARD_LINK_LIST,linkList);
			CPSUtil.xprint("=======共加载【"+linkList.size()+"】条跳转入口信息！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何跳转入口信息！>>>>>>>>>>>=======");
		}
	}
	
	/**
	 * 初始化推广地址
	 * @throws
	 * @author Jackie Wang
	 * @date 2019-05-26
	 */
	private static void initPromoteCache(){
		List<SbPromoteLink> linkList = promoteLinkService.getValidPromoteLinkList();
		if(!CPSUtil.checkListBlank(linkList)){
			reDto.put(Const.CT_PROMOTE_LINK_LIST,linkList);
			CPSUtil.xprint("=======共加载【"+linkList.size()+"】条推广地址信息！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何推广地址信息！>>>>>>>>>>>=======");
		}
	}
	
	
	
	/**
	 * 初始化栏目
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-10-23
	 */
	private static void initColumnCache(){
		List<SbColumnInfo> columnList = columnInfoService.getColumnInfoList(new SbColumnInfo());
		if(!CPSUtil.checkListBlank(columnList)){
			reDto.put(Const.CT_COLUMN_LIST,columnList);
			CPSUtil.xprint("=======共加载【"+columnList.size()+"】条栏目信息！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何栏目信息！>>>>>>>>>>>=======");
		}
	}
	
	/**
	 * 初始化广告
	 * @throws
	 * @author Su Jie
	 * @date 2017-10-23
	 */
	private static void initAdvertiseCache(){
		List<SbAdvertInfo> advertList = advertiseInfoService.findSbAdvertiseInfos();
		if(!CPSUtil.checkListBlank(advertList)){
			reDto.put(Const.CT_ADVERTISE_LIST,advertList);
			CPSUtil.xprint("=======共加载【"+advertList.size()+"】条广告信息！=======");
		}else{
			CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何广告信息！>>>>>>>>>>>=======");
		}
	}
	
	//更新缓存信息
	public static boolean updateCache(String key) {
		boolean temp = false;
		try {
			
			if((Const.CT_CODE_LIST).equals(key)){
				initCodeCache();
				CPSUtil.setContextAtrribute(Const.CT_CODE_LIST, reDto.get(Const.CT_CODE_LIST));
				CPSUtil.xprint("更新字典参数到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_PARAM_LIST).equals(key)){
				initParamCache();
				CPSUtil.setContextAtrribute(Const.CT_PARAM_LIST, reDto.get(Const.CT_PARAM_LIST));
				String readTime = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_TIME);
				CPSUtil.xprint("更新全局参数到缓存成功..."+readTime);
				temp = true;
			}
			
			if((Const.CT_COLUMN_LIST).equals(key)){
				initColumnCache();
				CPSUtil.setContextAtrribute(Const.CT_COLUMN_LIST, reDto.get(Const.CT_COLUMN_LIST));
				CPSUtil.xprint("更新栏目信息到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_CONTENT_INFO_LIST).equals(key)){
				initContentCache();
				CPSUtil.setContextAtrribute(Const.CT_CONTENT_INFO_LIST, reDto.get(Const.CT_CONTENT_INFO_LIST));
				CPSUtil.xprint("更新系统文章到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_ADVERTISE_LIST).equals(key)){
				initAdvertiseCache();
				CPSUtil.setContextAtrribute(Const.CT_ADVERTISE_LIST, reDto.get(Const.CT_ADVERTISE_LIST));
				CPSUtil.xprint("更新广告信息到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_DOMAIN_LIST).equals(key)){
				initContentDomain();
				CPSUtil.setContextAtrribute(Const.CT_DOMAIN_LIST, reDto.get(Const.CT_DOMAIN_LIST));
				CPSUtil.xprint("更新公共域名到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_ZS_DOMAIN_LIST).equals(key)){
				initZContentDomain();
				CPSUtil.setContextAtrribute(Const.CT_ZS_DOMAIN_LIST, reDto.get(Const.CT_ZS_DOMAIN_LIST));
				CPSUtil.xprint("更新专属域名到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_FORWARD_LINK_LIST).equals(key)){
				initForwardCache();
				CPSUtil.setContextAtrribute(Const.CT_FORWARD_LINK_LIST, reDto.get(Const.CT_FORWARD_LINK_LIST));
				CPSUtil.xprint("更新跳转入口到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_PROMOTE_LINK_LIST).equals(key)){
				initPromoteCache();
				CPSUtil.setContextAtrribute(Const.CT_PROMOTE_LINK_LIST, reDto.get(Const.CT_PROMOTE_LINK_LIST));
				CPSUtil.xprint("更新推广地址到缓存成功...");
				temp = true;
			}
			
			if((Const.CT_SYSTEM_MEMBER_LIST).equals(key)){
				initMemberInfoCache();
				CPSUtil.setContextAtrribute(Const.CT_SYSTEM_MEMBER_LIST, reDto.get(Const.CT_SYSTEM_MEMBER_LIST));
				CPSUtil.xprint("更新系统会员到缓存成功...");
				temp = true;
			}
			
		} catch (Exception e) {
			temp = false;
			e.printStackTrace();
			CPSUtil.xprint("更新缓存失败...");
		}
		return temp;
	}

}
