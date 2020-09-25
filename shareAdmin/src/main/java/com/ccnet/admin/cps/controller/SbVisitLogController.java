package com.ccnet.admin.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbVisitLog;
import com.ccnet.cps.service.SbVisitLogService;

/**
 * 文章日志
 * 
 * @author 1234
 * 
 */
@Controller
@RequestMapping("/backstage/svisit/")
public class SbVisitLogController extends AdminBaseController<SbVisitLog> {
	// 静态地址
	public static String INDEX_URL = "/backstage/visit/index";
	public static String GO_ADD_URL = "/backstage/visit/goadd";
	public static String GO_EDIT_URL = "/backstage/visit/goedit";
	@Autowired
	private SbVisitLogService sbVisitLogService;

	/**
	 * 查询日志列表
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView visitIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		// UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		Page<SbVisitLog> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbVisitLog sbVisitLog = new SbVisitLog();
			page = sbVisitLogService.findVisitByPage(sbVisitLog, page, paramDto);// 分页查询
			mav.addObject("pm", page);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "文章日志");
			mav.setViewName("admincps/jsp/visit/visit_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		
		return mav;
	}

	/**
	 * 清空日志
	 * 
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashVisitLog() {
		AjaxRes ar = getAjaxRes();

		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer visitId = paramDto.getAsInteger("visitId");
			SbVisitLog visitLog = new SbVisitLog();
			visitLog.setVisitId(visitId);
			if (sbVisitLogService.trashSbVisitLog(visitLog)) {
				ar.setRes(Const.SUCCEED);
				ar.setSucceedMsg(Const.CLEAR_SUCCEED);
			} else {
				ar.setRes(Const.FAIL);
				ar.setSucceedMsg(Const.CLEAR_FAIL);
			}
		}
		return ar;
	}
}
