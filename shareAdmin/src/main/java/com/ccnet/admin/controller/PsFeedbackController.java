package com.ccnet.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
import com.ccnet.cps.entity.PsFeedback;
import com.ccnet.cps.service.PsFeedbackService;

@Controller
@RequestMapping("/backstage/psFeedback/")
public class PsFeedbackController extends BaseController<PsFeedback> {

	@Autowired
	private PsFeedbackService psFeedbackService;
	// 静态地址
	public static String INDEX_URL = "/backstage/psFeedback/index";
	public static String GO_EDIT_URL = "/backstage/psFeedback/goedit";

	/**
	 * 日志首页
	 */
	@RequestMapping("index")
	public ModelAndView logIndex() throws UnsupportedEncodingException {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.MENU)) {
			Dto paramDto = getParamAsDto();
			Page<PsFeedback> page = newPage(paramDto);
			page = psFeedbackService.findMemberLoginLogByPage(new PsFeedback(), page, paramDto);

			mav.addObject("pm", page);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("title", paramDto.getAsString("title"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员反馈信息");
			mav.setViewName("admincps/jsp/psFeedback/list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改资源
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditMenu(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer id = paramDto.getAsInteger("id");
			PsFeedback resource = new PsFeedback();
			resource.setId(id);
			resource = psFeedbackService.find(resource);
			mav.addObject("menuInfo", resource);
			mav.setViewName("admincps/jsp/psFeedback/info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改资源
	 * 
	 * @param resources
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes editResources(PsFeedback psFeedback) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				PsFeedback resource = new PsFeedback();
				resource.setId(psFeedback.getId());
				resource = psFeedbackService.find(resource);
				resource.setUpdateTime(new Date());
				resource.setReply(psFeedback.getReply());
				resource.setStatus("1");
				if (psFeedbackService.update(resource, "id") == 1) {
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

	/**
	 * 删除资源
	 * 
	 * @param param_id
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashParam(Integer resource_id) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON))) {
			try {
				PsFeedback resource = new PsFeedback();
				resource.setId(resource_id);
				if (psFeedbackService.delete(resource) == 1) {
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				} else {
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
}
