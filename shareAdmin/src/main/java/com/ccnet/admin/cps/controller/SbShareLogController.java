package com.ccnet.admin.cps.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ShareType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.service.SbShareLogService;

/**
 * 分享日志
 * @author JackieWang
 *
 */
@Controller
@RequestMapping("/backstage/sharelog/")
public class SbShareLogController extends BaseController<SbShareLog> {
	
	//静态地址
	public static String INDEX_URL = "/backstage/sharelog/index";
	
	@Autowired
	private SbShareLogService shareLogService;
	
	
	/**
	 * 用户管理首页
	 */
	@RequestMapping("index")
	public ModelAndView shareIndex() throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){	
			Dto paramDto = getParamAsDto();
			Page<SbShareLog> page = newPage(paramDto);
			SbShareLog shareLog = new SbShareLog();
			page = shareLogService.findSbShareLogByPage(shareLog, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("shareType",paramDto.getAsString("shareType"));
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject("typeList",ShareType.values());
			mav.addObject(Const.CT_MENU_NAV,"分享记录");
			mav.setViewName("admincps/jsp/sharelog/share_log_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 删除日志
	 * @param log_id
	 * @return
	 */
	@RequestMapping(value="trash", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashShareLog(String log_id){
		AjaxRes ar=getAjaxRes();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			try {
				if(shareLogService.trashSbShareLogList(log_id)){
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}else{
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}else{
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}
	
	/**
	 * 清空日志
	 * @param log_id
	 * @return
	 */
	@RequestMapping(value="clear", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes clearSahreLog(){
		AjaxRes ar=getAjaxRes();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			try {
				if(shareLogService.truncateShareLog()){
					ar.setSucceedMsg(Const.CLEAR_SUCCEED);
				}else{
					ar.setFailMsg(Const.CLEAR_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.CLEAR_FAIL);
			}
		}else{
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}
	

}
