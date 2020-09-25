package com.ccnet.admin.bh.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.UserInvestMoney;
import com.ccnet.admin.bh.service.UserInvestMoneyService;
import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.RoleType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserSexSate;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.UserInfoService;

@Controller
@RequestMapping("/backstage/invest/")
public class UserInvestMoneyController extends AdminBaseController<UserInvestMoney> {

	@Autowired
	UserInvestMoneyService investMoneyService;
	@Autowired
	UserInfoService userInfoService;

	// 静态地址
	public static String GO_ADD_URL = "/backstage/invest/goadd";
	public static String GO_EDIT_URL = "/backstage/invest/goedit";

	/**
	 * 会员管理首页
	 */
	@RequestMapping("index")
	public ModelAndView memberIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		Page<UserInvestMoney> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			UserInvestMoney UserInvestMoney = new UserInvestMoney();
			if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
					|| userInfo.getUserType() == UserType.ADMIN.getType())) {
				UserInvestMoney.setUserId(userInfo.getUserId());
			}
			page = investMoneyService.findUserInvestMoneyByPage(UserInvestMoney, page, paramDto);
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userList", userInfoService.findList(user));
			mav.addObject("pm", page);
			mav.addObject("userId", paramDto.getAsInteger("user_id"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员管理");
			mav.setViewName("admincps/jsp/invest/invest_list");
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
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userInvestMoney", new UserInvestMoney());
			mav.addObject("userList", userInfoService.findList(user));
			mav.addObject("sxlist", UserSexSate.values());
			mav.addObject("tplist", UserType.all());
			mav.addObject("stlist", UserSateType.values());
			mav.addObject("thlist", CPSUtil.getCodeList("USER_THEME"));
			mav.addObject("avlist", CPSUtil.getUserAvatarsList());
			mav.setViewName("admincps/jsp/invest/invest_info");
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
			Integer role_id = paramDto.getAsInteger("ids");
			UserInvestMoney userInvestMoney = new UserInvestMoney();
			userInvestMoney.setId(role_id);
			mav.addObject("userInvestMoney", investMoneyService.find(userInvestMoney));
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userList", userInfoService.findList(user));
			mav.addObject("tplist", RoleType.values());
			mav.addObject("enlist", CPSUtil.getCodeList("ENABLED"));
			mav.setViewName("admincps/jsp/invest/invest_info");
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
	public AjaxRes saveRole(UserInvestMoney role) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				role.setCreateDate(new Date());
				if (investMoneyService.insert(role) > 0) {
					UserInfo user = userInfoService.getUserByUserID(role.getUserId());
					if(user.getMoney()==null){
						user.setMoney(role.getMoney());
					}else {
						user.setMoney(user.getMoney() + role.getMoney());
					}
					userInfoService.editUserInfo(user);
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
	public AjaxRes editRole(UserInvestMoney table) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				UserInvestMoney userInvestMoney = new UserInvestMoney();
				userInvestMoney.setId(table.getId());
				userInvestMoney = investMoneyService.find(userInvestMoney);
				userInvestMoney.setMoney(table.getMoney());
				userInvestMoney.setRemarks(table.getRemarks());
				userInvestMoney.setUserId(table.getUserId());
				userInvestMoney.setCreateDate(new Date());
				if (investMoneyService.update(userInvestMoney, "id") > 0) {
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
