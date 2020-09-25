package com.ccnet.admin.jp.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.CheckStateType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.WeightType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.jpz.entity.JpNotice;
import com.ccnet.jpz.service.JpNoticeService;

@Controller
@RequestMapping("/backstage/jpnotice/")
public class JpNoticeController extends BaseController<JpNotice> {

	@Autowired
	private JpNoticeService jpNoticeService;

	/**
	 * 协议信息列表
	 */
	@RequestMapping("index")
	public ModelAndView noticeIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<JpNotice> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			JpNotice arg0 = new JpNotice();
			Page<JpNotice> arg1 = new Page<JpNotice>();
			page = jpNoticeService.findByPage(arg0, arg1);
			mav.addObject("pm", page);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "协议管理");
			mav.setViewName("adminjp/jsp/notice/list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加协议信息
	 */
	@RequestMapping("goadd")
	public ModelAndView goAdd() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("weightMap", WeightType.getStateMap());
		mav.addObject("cstateMap", CheckStateType.values());
		mav.addObject("stateMap", StateType.getStateMap());
		mav.addObject("cstateMap", CheckStateType.values());
		mav.addObject(Const.CT_MENU_NAV, "添加协议");
		mav.setViewName("adminjp/jsp/notice/info");
		return mav;
	}

	/**
	 * 修改协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEdit() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		Integer noticeId = paramDto.getAsInteger("noticeId");
		if (CPSUtil.isNotEmpty(noticeId)) {
			JpNotice notice = new JpNotice();
			notice = jpNoticeService.getByID(noticeId);
			mav.addObject("notice", notice);
			mav.addObject("stateMap", StateType.getStateMap());
			mav.addObject("cstateMap", CheckStateType.values());
			mav.addObject(Const.CT_MENU_NAV, "编辑协议");
			mav.setViewName("adminjp/jsp/notice/info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}

		return mav;
	}

	/**
	 * 保存协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveContent(JpNotice jpNotice) {
		AjaxRes ar = getAjaxRes();
		try {
			int isOk = 0;
			if (CPSUtil.isNotEmpty(jpNotice.getId())) {
				JpNotice jpNotice1 = jpNoticeService.getByID(jpNotice.getId());
				jpNotice1.setContent(jpNotice.getContent());
				jpNotice1.setTitle(jpNotice.getTitle());
				jpNotice1.setCreateDate(new Date());
				jpNoticeService.update(jpNotice1, "id");
				isOk = 1;
			} else {
				jpNotice.setCreateDate(new Date());
				jpNoticeService.insert(jpNotice);
			}
			if (isOk != 0) {
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} else {
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
		return ar;
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	@RequestMapping(value = "goview")
	public ModelAndView goview() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		Integer id = paramDto.getAsInteger("ids");
		if (CPSUtil.isNotEmpty(id)) {
			JpNotice notice = jpNoticeService.getByID(id);
			mav.addObject("notice", notice);
		}
		mav.addObject(Const.CT_MENU_NAV, "查看协议");
		mav.setViewName("adminjp/jsp/notice/view");
		return mav;
	}
}
