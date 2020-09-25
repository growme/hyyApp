package com.ccnet.admin.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.sigar.SigarUtils;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.Resources;

/**
 * 系统监控管理
 * @author jackie
 *
 */
@Controller
@RequestMapping("/backstage/monitor")
public class SystemMonitorController extends AdminBaseController<Object> {
	
	public static String INDEX_URL = "/backstage/monitor/server";
	
	/**
	 * web监控页面
	 */
	@RequestMapping("web")
	public ModelAndView webMonitor(){	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			
			mav.addObject(Const.CT_MENU_NAV,"WEB监控管理");
			mav.addObject("monitorUrl", "druid/index.html");
			mav.setViewName("admin/jsp/monitor/web_monitor");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 服务器监控页面
	 */
	@RequestMapping("server")
	public ModelAndView serverMonitor(){	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			
			mav.addObject(Const.CT_MENU_NAV,"服务器监控");
			mav.addObject("serverInfo", SigarUtils.getServerInfo());
			mav.addObject("monitorUrl", "druid/index.html");
			mav.setViewName("admin/jsp/monitor/server_monitor");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * ajax获取服务器数据
	 * @return
	 */
	@RequestMapping(value="usage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes getServerUseAge(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.MENU,INDEX_URL))){
			Dto paramDto = getParamAsDto();
			try {
				//获取服务器信息
				Map<String, String> monitorMap = SigarUtils.getMonitorInfo();
				ar.setSucceed(monitorMap);
			} catch (Exception e) {
				logger.error(e.toString(),e);
			}
		}
		return ar;
	}

}
