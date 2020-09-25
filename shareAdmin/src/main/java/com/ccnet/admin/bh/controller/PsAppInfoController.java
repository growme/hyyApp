package com.ccnet.admin.bh.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.PsAppInfo;
import com.ccnet.admin.bh.service.PsAppInfoService;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;

@Controller
@RequestMapping("/backstage/psAppInfo/")
public class PsAppInfoController extends BaseController<PsAppInfo> {

	// 静态地址
	public static String INDEX_URL = "/backstage/psAppInfo/index";
	public static String GO_ADD_URL = "/backstage/psAppInfo/goadd";
	public static String GO_EDIT_URL = "/backstage/psAppInfo/goedit";

	@Autowired
	PsAppInfoService psAppInfoService;

	/**
	 * 自定义任务首页
	 */
	@RequestMapping("index")
	public ModelAndView index() throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.MENU)) {
			Dto paramDto = getParamAsDto();
			String queryParam = paramDto.getAsString("queryParam");
			Page<PsAppInfo> page = newPage(paramDto);
			page = psAppInfoService.findPsAppInfoByPage(new PsAppInfo(), page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("queryParam", queryParam);
			mav.addObject(Const.CT_MENU_NAV, "自定义任务参数");
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admincps/jsp/psAppInfo/psAppInfo_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}

		return mav;
	}

	/**
	 * 添加自定义任务
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddPsAppInfo() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.setViewName("admincps/jsp/psAppInfo/psAppInfo_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改自定义任务
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditPsAppInfo() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer psAppInfo_id = paramDto.getAsInteger("psAppInfo_id");
			PsAppInfo psAppInfo = new PsAppInfo();
			if (CPSUtil.isNotEmpty(psAppInfo_id)) {
				psAppInfo = psAppInfoService.findPsAppInfoByID(psAppInfo_id);
			}
			mav.addObject("psAppInfo", psAppInfo);
			mav.setViewName("admincps/jsp/psAppInfo/psAppInfo_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 增加自定义任务
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes savePsAppInfo(PsAppInfo code) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				if (psAppInfoService.insert(code) == 1) {
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} else {
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 更新角色信息
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes editPsAppInfo(PsAppInfo code) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				if (psAppInfoService.update(code, "id") == 1) {
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				} else {
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}

	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashCode(PsAppInfo psAppInfo) {
		AjaxRes ar = getAjaxRes();
		try {
			if (psAppInfo.getId() != null)
				if (psAppInfoService.delete(psAppInfo) == 1) {
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				} else {
					ar.setFailMsg(Const.DEL_FAIL);
				}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.DEL_FAIL);
		}
		return ar;
	}
}
