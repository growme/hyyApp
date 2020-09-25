package com.ccnet.admin.bh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.WechatPublicNumber;
import com.ccnet.admin.bh.service.WechatPublicNumberService;
import com.ccnet.core.common.RoleType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserSexSate;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;

@Controller
@RequestMapping("/backstage/wechatPublicNumber/")
public class WechatPublicNumberController extends BaseController<WechatPublicNumber> {

	// 静态地址
	public static String GO_ADD_URL = "/backstage/wechatPublicNumber/goadd";
	public static String GO_EDIT_URL = "/backstage/wechatPublicNumber/goedit";
	@Autowired
	WechatPublicNumberService wechatPublicNumberService;

	@RequestMapping("/index")
	public ModelAndView list() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		WechatPublicNumber wechatPublicNumber = new WechatPublicNumber();
		Page<WechatPublicNumber> page = newPage(paramDto);

		if (isAuthedReq(ResourceTypes.MENU)) {
			page = wechatPublicNumberService.findWechatPublicNumberByPage(wechatPublicNumber, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("userId", paramDto.getAsInteger("user_id"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "公众号管理");
			mav.setViewName("admincps/jsp/publicNumber/list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddUser() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.addObject("sxlist", UserSexSate.values());
			mav.addObject("tplist", UserType.all());
			mav.addObject("stlist", UserSateType.values());
			mav.addObject("thlist", CPSUtil.getCodeList("USER_THEME"));
			mav.addObject("avlist", CPSUtil.getUserAvatarsList());
			mav.setViewName("admincps/jsp/publicNumber/info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditRole() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer id = paramDto.getAsInteger("id");
			WechatPublicNumber wechatPublicNumber = wechatPublicNumberService.getWechatPublicNumberById(id);
			mav.addObject("wechatPublicNumber", wechatPublicNumber);
			mav.addObject("tplist", RoleType.values());
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admincps/jsp/publicNumber/info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 增加角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveRole(WechatPublicNumber table) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				// table.setUpdateTime(new Date());
				if (wechatPublicNumberService.insert(table) > 0) {
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
				} else {
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
				System.out.println();
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 更新角色
	 * 
	 * @param o
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes editRole(WechatPublicNumber table) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				wechatPublicNumberService.getWechatPublicNumberById(table.getId());
				if (wechatPublicNumberService.update(table, "id") > 0) {
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
}
