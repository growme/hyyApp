package com.ccnet.admin;

import com.ccnet.core.common.cache.ConfigCache;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.SpringWebContextUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;

public class SpringWebContextUtilAdmin extends SpringWebContextUtil{
	
	@Override
	protected void init() {
		long start = System.currentTimeMillis();
		
		log.debug("********************************************");
		log.debug("........后台管理平台开始启动...........");
		log.debug("********************************************");
		/**
		 * 开始开启注册认证
		 */
		LicenseHelper.initLicense();
		
		//启动加载数据到内存
		if (success) {
			log.debug("开始加载数据库中所有公用数据到缓存中...");
			Dto reDto = InitSystemCache.initCache();
			if(CPSUtil.isNotEmpty(reDto)){
				//设置字典参数
				applicationContext.getServletContext().setAttribute(Const.CT_CODE_LIST, reDto.get(Const.CT_CODE_LIST));
				//设置全局参数
				applicationContext.getServletContext().setAttribute(Const.CT_PARAM_LIST, reDto.get(Const.CT_PARAM_LIST));
				//设置派单域名
				applicationContext.getServletContext().setAttribute(Const.CT_DOMAIN_LIST, reDto.get(Const.CT_DOMAIN_LIST));
			    //广告信息
				applicationContext.getServletContext().setAttribute(Const.CT_ADVERTISE_LIST, reDto.get(Const.CT_ADVERTISE_LIST));
				//栏目信息
				applicationContext.getServletContext().setAttribute(Const.CT_COLUMN_LIST, reDto.get(Const.CT_COLUMN_LIST));
				//文章信息
				applicationContext.getServletContext().setAttribute(Const.CT_CONTENT_INFO_LIST, reDto.get(Const.CT_CONTENT_INFO_LIST));
				//会员信息
				applicationContext.getServletContext().setAttribute(Const.CT_SYSTEM_MEMBER_LIST, reDto.get(Const.CT_SYSTEM_MEMBER_LIST));
				//设置跳转入口
				applicationContext.getServletContext().setAttribute(Const.CT_FORWARD_LINK_LIST, reDto.get(Const.CT_FORWARD_LINK_LIST));
				//设置跳转入口
				applicationContext.getServletContext().setAttribute(Const.CT_PROMOTE_LINK_LIST, reDto.get(Const.CT_PROMOTE_LINK_LIST));
			}
			
			//设置模式
			CPSUtil.xprint("demo_mode="+ConfigCache.getConfig("demo_mode"));
			applicationContext.getServletContext().setAttribute(Const.IS_DEMO_MOD, ConfigCache.getConfig("demo_mode"));
			
			log.debug("加载数据库中所有公用数据到缓存中完毕...");
			long timeSec = (System.currentTimeMillis() - start) / 1000;
			log.debug("************************************************************************");
			if (success) {
				log.debug("后台管理平台启动成功[" + CPSUtil.getCurrentTime() + "]");
				log.debug("启动总耗时: " + timeSec / 60 + "分 " + timeSec % 60 + "秒");
			} else {
				log.debug("后台管理平台启动失败[" + CPSUtil.getCurrentTime() + "]");
				log.debug("启动总耗时: " + timeSec / 60 + "分" + timeSec % 60 + "秒");
			}
			log.debug("************************************************************************");
	   }
	}
}
