package com.ccnet.admin.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.MemberLoginLog;
import com.ccnet.cps.service.MemberLoginLogService;

/**
 * 登录日志
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/memberlog/")
public class MemberLoginLogController extends AdminBaseController<MemberLoginLog> {
	
	@Autowired
	private MemberLoginLogService memberLoginLogService;
	
	/**
	 * 日志首页
	 */
	@RequestMapping("index")
	public ModelAndView logIndex() throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){
			Dto paramDto = getParamAsDto();
			Page<MemberLoginLog> page = newPage(paramDto);
			page = memberLoginLogService.findMemberLoginLogByPage(new MemberLoginLog(), page,paramDto);
			
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV,"会员登录日志");
			mav.setViewName("admin/jsp/log/memberlog_list");
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
	public AjaxRes trashLog(String log_id){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC))){
			try {
				if(memberLoginLogService.trashMemberLoginLogList(log_id)){
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				}else{
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 清空日志日志
	 * @return
	 */
	@RequestMapping(value="clear", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes clearLog(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC))){
			try {
				if(memberLoginLogService.truncateMemberLoginLog()){
					ar.setSucceedMsg(Const.CLEAR_SUCCEED);
				}else{
					ar.setFailMsg(Const.CLEAR_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.CLEAR_FAIL);
			}
		}
		return ar;
	}

}
