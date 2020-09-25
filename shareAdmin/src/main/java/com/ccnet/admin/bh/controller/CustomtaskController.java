package com.ccnet.admin.bh.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.SbCustomtask;
import com.ccnet.admin.bh.service.SbCustomtaskService;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;

/**
 * 自定义任务
 */
@Controller
@RequestMapping("/backstage/customtask/")
public class CustomtaskController extends BaseController<SbCustomtask> {

	// 静态地址
	public static String INDEX_URL = "/backstage/customtask/index";
	public static String GO_ADD_URL = "/backstage/customtask/goadd";
	public static String GO_EDIT_URL = "/backstage/customtask/goedit";

	@Autowired
	SbCustomtaskService sbCustomtaskService;

	/**
	 * 自定义任务首页
	 */
	@RequestMapping("index")
	public ModelAndView index() throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.MENU)) {
			Dto paramDto = getParamAsDto();
			String queryParam = paramDto.getAsString("queryParam");
			Page<SbCustomtask> page = newPage(paramDto);
			page = sbCustomtaskService.findSbCustomtaskByPage(new SbCustomtask(), page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("queryParam", queryParam);
			mav.addObject(Const.CT_MENU_NAV, "自定义任务参数");
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admincps/jsp/customtask/customtask_list");
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
	public ModelAndView goAddCustomtask() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.setViewName("admincps/jsp/customtask/customtask_info");
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
	public ModelAndView goEditCode() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer customtask_id = paramDto.getAsInteger("customtask_id");
			SbCustomtask SbCustomtask = new SbCustomtask();
			if (CPSUtil.isNotEmpty(customtask_id)) {
				SbCustomtask = sbCustomtaskService.findSbCustomtaskByID(customtask_id);
			}
			mav.addObject("customtask", SbCustomtask);
			mav.setViewName("admincps/jsp/customtask/customtask_info");
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
	public AjaxRes saveCode(SbCustomtask code) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				if(code.getStatus()==null){
					code.setStatus(1);
				}
				if (sbCustomtaskService.saveSbCustomtask(code)) {
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
	public AjaxRes editCode(SbCustomtask code) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				if (sbCustomtaskService.editSbCustomtask(code)) {
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
	public AjaxRes trashCode(SbCustomtask customtask) {
		AjaxRes ar = getAjaxRes();
		try {
			if (customtask.getId() != null)
				if (sbCustomtaskService.trashSbCustomtask(customtask)) {
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
