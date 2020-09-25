package com.ccnet.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ccnet.admin.LicenseHelper;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.RoleType;
import com.ccnet.core.common.UserSexSate;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.common.utils.tree.MenuTreeUtil;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.UserInfoService;
import com.ccnet.cps.entity.SbContentVisitLogExtend;
import com.ccnet.cps.service.HomeIndexService;
import com.ccnet.cps.service.MemberInfoService;

import net.sf.json.JSONString;
import net.sf.json.util.JSONStringer;

@Controller
@RequestMapping("/backstage/")
public class AdminIndexController extends AdminBaseController<Object> {

	public static String RESET_PWD_URL = "/backstage/index/changepwd";
	public static String GO_SETTING_URL = "/backstage/index/setting";

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private HomeIndexService homeIndexService;

	/**
	 * 访问系统首页
	 */
	@RequestMapping("index")
	public String index(Model model) {

		Date startDate = null;
		Date endDate = null;
		Dto paramDto = getParamAsDto();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		// 统计总量数据
		Dto totalDto = memberInfoService.countMembers(startDate, endDate); // 会员数

		logger.info(JSON.toJSONString(totalDto) + "***********************");

		// 统计会员收益情况
		Dto userMoneyDto = homeIndexService.countMemeberTotalMoney(startDate, endDate);
		// 总的推广收益=阅读收益+分享收益+下线提成
		Dto promoteMoneyDto = homeIndexService.userMoneyCountByMoneyType(startDate, endDate);
		// 会员提现情况
		Dto cashMoneyDto = homeIndexService.userCashCountByState(startDate, endDate);
		// 处理资金统计饼图
		List<AwardType> awardTypes = AwardType.all();
		Map<AwardType, Double> moneyTypeCountMap = homeIndexService.countUserByMoneyAwardType(startDate, endDate, null);
		List<String> allStateTitles = new ArrayList<String>(0);
		List<Map<String, String>> allStateValues = new ArrayList<Map<String, String>>(0);
		if (CPSUtil.isNotEmpty(moneyTypeCountMap)) {
			for (AwardType awardType : awardTypes) {
				allStateTitles.add(awardType.getAwardName());
				Map<String, String> map = new HashMap<String, String>(0);
				Double money = moneyTypeCountMap.get(awardType);
				map.put("name", awardType.getAwardName());
				map.put("value", (money == null ? 0 : money) + "");
				allStateValues.add(map);
			}
		}

		// 统计会员分享总次数和分享总收益
		Dto shareDto = homeIndexService.countTotalShareByGivenMoney(startDate, endDate);
		shareDto.put("shareMoney", moneyTypeCountMap.get(AwardType.transmit));

		/**
		 * 统计省数据
		 */
		Map<String, Integer> provinceCountMap = homeIndexService.countProvinceContentVisitLogExtend(startDate, endDate,
				null);
		List<String> countTitles = new ArrayList<String>(0);
		countTitles.add("访问总量");
		List<Map<String, String>> provStepDatas = new ArrayList<Map<String, String>>(0);
		List<Map<String, String>> provDatas = new ArrayList<Map<String, String>>(0);
		if (provinceCountMap != null) {
			for (String prov : provinceCountMap.keySet()) {
				Map<String, String> dataMap = new HashMap<String, String>(0);
				dataMap.put("value",
						String.valueOf(provinceCountMap.get(prov) == null ? 0 : provinceCountMap.get(prov)));
				if (CPSUtil.isNotEmpty(prov)) {
					prov = prov.replaceAll("省|市", "");
				}
				dataMap.put("name", prov);
				provDatas.add(dataMap);
			}
			Map<String, String> map = new HashMap<String, String>(0);
			map.put("name", "访问量");
			map.put("data", JsonHelper.encodeObject2Json(provDatas));
			provStepDatas.add(map);
		}
		model.addAttribute("provStepTitles", JsonHelper.encodeObject2Json(countTitles));
		model.addAttribute("provStepDatas", provStepDatas);

		// 统计日访问量
		endDate = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH);
		startDate = DateUtils.addDays(endDate, -14);
		List<SbContentVisitLogExtend> contentVisitCountExtends = homeIndexService.getContentVisitCount(startDate,
				endDate, null);
		Map<Date, SbContentVisitLogExtend> dateContentVisitCountMap = new HashMap<Date, SbContentVisitLogExtend>(0);
		for (SbContentVisitLogExtend visitCountExtend : contentVisitCountExtends) {
			dateContentVisitCountMap.put(visitCountExtend.getVisitTime(), visitCountExtend);
		}

		List<String> dateList = new ArrayList<String>(0);
		List<Integer> ipCountList = new ArrayList<Integer>(0);
		List<Integer> readCountList = new ArrayList<Integer>(0);
		List<Integer> clickCountList = new ArrayList<Integer>(0);
		List<Integer> touchCountList = new ArrayList<Integer>(0);
		List<Integer> coordCountList = new ArrayList<Integer>(0);
		List<Integer> expandCountList = new ArrayList<Integer>(0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		do {

			SbContentVisitLogExtend extend = dateContentVisitCountMap.get(startDate);
			// CPSUtil.xprint("extend="+extend);
			if (extend == null) {
				extend = new SbContentVisitLogExtend();
			}
			dateList.add(sdf.format(startDate));
			ipCountList.add(extend.getIpCount() == null ? 0 : extend.getIpCount());
			readCountList.add(extend.getReadCount() == null ? 0 : extend.getReadCount());
			clickCountList.add(extend.getClickCount() == null ? 0 : extend.getClickCount());
			touchCountList.add(extend.getTouchCount() == null ? 0 : extend.getTouchCount());
			coordCountList.add(extend.getCoordCount() == null ? 0 : extend.getCoordCount());
			expandCountList.add(extend.getExpandCount() == null ? 0 : extend.getExpandCount());
			startDate = DateUtils.addDays(startDate, 1);

		} while (startDate.before(endDate));

		model.addAttribute("visitDate", JsonHelper.encodeObject2Json(dateList));
		model.addAttribute("visitIpCount", JsonHelper.encodeObject2Json(ipCountList));
		model.addAttribute("visitReadCount", JsonHelper.encodeObject2Json(readCountList));
		model.addAttribute("visitClickCount", JsonHelper.encodeObject2Json(clickCountList));
		model.addAttribute("visitTouchCount", JsonHelper.encodeObject2Json(touchCountList));
		model.addAttribute("visitCoordCount", JsonHelper.encodeObject2Json(coordCountList));
		model.addAttribute("visitExpandCount", JsonHelper.encodeObject2Json(expandCountList));

		logger.info(JSON.toJSONString(shareDto) + "share***********************");
		logger.info(JSON.toJSONString(cashMoneyDto) + "cashMoney***********************");
		logger.info(JSON.toJSONString(promoteMoneyDto) + "promoteMoney***********************");

		model.addAttribute("total", totalDto);
		model.addAttribute("share", shareDto);
		model.addAttribute("userMoney", userMoneyDto);
		model.addAttribute("cashMoney", cashMoneyDto);
		model.addAttribute("promoteMoney", promoteMoneyDto);

		model.addAttribute("allStateTitles", JsonHelper.encodeObject2Json(allStateTitles));
		model.addAttribute("allStateValues", JsonHelper.encodeObject2Json(allStateValues));

		model.addAttribute("currentAccount", userInfo);
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		model.addAttribute(Const.CT_MENU_NAV, "欢迎页面");

		if (isV5Admin()) {
			return "admin/jsp/home/admin_index_new";
		} else {
			if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
					|| userInfo.getUserType() == UserType.ADMIN.getType())) {
				return "admin/jsp/home/admin_index_ad";
			}
			return "admin/jsp/home/admin_index";
		}
	}

	/**
	 * 访问系统首页
	 */
	@RequestMapping("home")
	public String indexAdmin(Model model) {

		if (isV5Admin()) {
			return "admin/jsp/home/admin_index_new";
		} else {
			return "redirect:/backstage/index";
		}

	}

	/**
	 * 访问系统首页
	 */
	@RequestMapping("home/data")
	public String homeData(Model model) {
		Date startDate = null;
		Date endDate = null;
		Dto paramDto = getParamAsDto();
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();

		if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
				|| userInfo.getUserType() == UserType.ADMIN.getType())) {
			return "admin/jsp/home/home_index_new";
		}

		// 统计总量数据
		Dto totalDto = memberInfoService.countMembers(startDate, endDate); // 会员数
		// 统计会员收益情况
		Dto userMoneyDto = homeIndexService.countMemeberTotalMoney(startDate, endDate);
		// 总的推广收益=阅读收益+分享收益+下线提成
		Dto promoteMoneyDto = homeIndexService.userMoneyCountByMoneyType(startDate, endDate);
		// 会员提现情况
		Dto cashMoneyDto = homeIndexService.userCashCountByState(startDate, endDate);
		// 处理资金统计饼图
		List<AwardType> awardTypes = AwardType.all();
		Map<AwardType, Double> moneyTypeCountMap = homeIndexService.countUserByMoneyAwardType(startDate, endDate, null);
		List<String> allStateTitles = new ArrayList<String>(0);
		List<Map<String, String>> allStateValues = new ArrayList<Map<String, String>>(0);
		if (CPSUtil.isNotEmpty(moneyTypeCountMap)) {
			for (AwardType awardType : awardTypes) {
				allStateTitles.add(awardType.getAwardName());
				Map<String, String> map = new HashMap<String, String>(0);
				Double money = moneyTypeCountMap.get(awardType);
				map.put("name", awardType.getAwardName());
				map.put("value", (money == null ? 0 : money) + "");
				allStateValues.add(map);
			}
		}

		// 统计会员分享总次数和分享总收益
		Dto shareDto = homeIndexService.countTotalShareByGivenMoney(startDate, endDate);
		shareDto.put("shareMoney", moneyTypeCountMap.get(AwardType.transmit));

		/**
		 * 统计省数据
		 */
		Map<String, Integer> provinceCountMap = homeIndexService.countProvinceContentVisitLogExtend(startDate, endDate,
				null);
		List<String> countTitles = new ArrayList<String>(0);
		countTitles.add("访问总量");
		List<Map<String, String>> provStepDatas = new ArrayList<Map<String, String>>(0);
		List<Map<String, String>> provDatas = new ArrayList<Map<String, String>>(0);
		if (provinceCountMap != null) {
			for (String prov : provinceCountMap.keySet()) {
				Map<String, String> dataMap = new HashMap<String, String>(0);
				dataMap.put("value",
						String.valueOf(provinceCountMap.get(prov) == null ? 0 : provinceCountMap.get(prov)));
				if (CPSUtil.isNotEmpty(prov)) {
					prov = prov.replaceAll("省|市", "");
				}
				dataMap.put("name", prov);
				provDatas.add(dataMap);
			}
			Map<String, String> map = new HashMap<String, String>(0);
			map.put("name", "访问量");
			map.put("data", JsonHelper.encodeObject2Json(provDatas));
			provStepDatas.add(map);
		}
		model.addAttribute("provStepTitles", JsonHelper.encodeObject2Json(countTitles));
		model.addAttribute("provStepDatas", provStepDatas);

		// 统计日访问量
		endDate = DateUtils.truncate(DateUtils.addDays(new Date(), 1), Calendar.DAY_OF_MONTH);
		startDate = DateUtils.addDays(endDate, -14);
		List<SbContentVisitLogExtend> contentVisitCountExtends = homeIndexService.getContentVisitCount(startDate,
				endDate, null);
		Map<Date, SbContentVisitLogExtend> dateContentVisitCountMap = new HashMap<Date, SbContentVisitLogExtend>(0);
		for (SbContentVisitLogExtend visitCountExtend : contentVisitCountExtends) {
			dateContentVisitCountMap.put(visitCountExtend.getVisitTime(), visitCountExtend);
		}

		List<String> dateList = new ArrayList<String>(0);
		List<Integer> ipCountList = new ArrayList<Integer>(0);
		List<Integer> readCountList = new ArrayList<Integer>(0);
		List<Integer> clickCountList = new ArrayList<Integer>(0);
		List<Integer> touchCountList = new ArrayList<Integer>(0);
		List<Integer> coordCountList = new ArrayList<Integer>(0);
		List<Integer> expandCountList = new ArrayList<Integer>(0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		do {

			SbContentVisitLogExtend extend = dateContentVisitCountMap.get(startDate);
			if (extend == null) {
				extend = new SbContentVisitLogExtend();
			}
			dateList.add(sdf.format(startDate));
			ipCountList.add(extend.getIpCount() == null ? 0 : extend.getIpCount());
			readCountList.add(extend.getReadCount() == null ? 0 : extend.getReadCount());
			clickCountList.add(extend.getClickCount() == null ? 0 : extend.getClickCount());
			touchCountList.add(extend.getTouchCount() == null ? 0 : extend.getTouchCount());
			coordCountList.add(extend.getCoordCount() == null ? 0 : extend.getCoordCount());
			expandCountList.add(extend.getExpandCount() == null ? 0 : extend.getExpandCount());
			startDate = DateUtils.addDays(startDate, 1);

		} while (startDate.before(endDate));

		model.addAttribute("visitDate", JsonHelper.encodeObject2Json(dateList));
		model.addAttribute("visitIpCount", JsonHelper.encodeObject2Json(ipCountList));
		model.addAttribute("visitReadCount", JsonHelper.encodeObject2Json(readCountList));
		model.addAttribute("visitClickCount", JsonHelper.encodeObject2Json(clickCountList));
		model.addAttribute("visitTouchCount", JsonHelper.encodeObject2Json(touchCountList));
		model.addAttribute("visitCoordCount", JsonHelper.encodeObject2Json(coordCountList));
		model.addAttribute("visitExpandCount", JsonHelper.encodeObject2Json(expandCountList));

		model.addAttribute("total", totalDto);
		model.addAttribute("share", shareDto);
		model.addAttribute("userMoney", userMoneyDto);
		model.addAttribute("cashMoney", cashMoneyDto);
		model.addAttribute("promoteMoney", promoteMoneyDto);

		model.addAttribute("allStateTitles", JsonHelper.encodeObject2Json(allStateTitles));
		model.addAttribute("allStateValues", JsonHelper.encodeObject2Json(allStateValues));

		model.addAttribute("currentAccount", userInfo);
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		model.addAttribute(Const.CT_MENU_NAV, "欢迎页面");
		return "admin/jsp/home/home_index";
	}

	/**
	 * 获取服务器时间
	 * 
	 * @return
	 */
	@RequestMapping(value = "getServerTime", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getServerTime(HttpServletRequest request) {
		AjaxRes ar = getAjaxRes();
		try {
			ar.setSucceedMsg(CPSUtil.getCurrentTime("yyyy,M,d,H,m,s"));
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return ar;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "index/changepwd", method = RequestMethod.GET)
	public ModelAndView changeUserPwd() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
			mav.addObject("userId", currentUser.getUserId());
			mav.setViewName("admin/jsp/home/change_user_pwd");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 个人设置
	 * 
	 * @return
	 */
	@RequestMapping(value = "index/setting", method = RequestMethod.GET)
	public ModelAndView userSetting() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			UserInfo userInfo = new UserInfo();
			UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
			if (CPSUtil.isNotEmptyForInteger(currentUser.getUserId())) {
				userInfo = userInfoService.getUserByUserID(currentUser.getUserId());
			}
			mav.addObject("userInfo", userInfo);
			mav.addObject("sxlist", UserSexSate.values());
			mav.addObject("avlist", CPSUtil.getUserAvatarsList());
			mav.setViewName("admin/jsp/home/user_setting");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 更新用户
	 * 
	 * @param o
	 * @return
	 */
	@RequestMapping(value = "index/saveSetting", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveSetting(UserInfo userInfo) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_SETTING_URL))) {
			try {
				UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
				if (CPSUtil.isNotEmpty(userInfo) && userInfo.getUserId().equals(currentUser.getUserId())) {
					UserInfo oldUser = userInfoService.getUserByUserID(userInfo.getUserId());
					if (CPSUtil.isNotEmpty(oldUser)) {
						oldUser.setUpdateTime(new Date());
						oldUser.setQq(userInfo.getQq());
						oldUser.setEmail(userInfo.getEmail());
						oldUser.setSex(userInfo.getSex());
						oldUser.setNickName(userInfo.getNickName());
						oldUser.setUserName(userInfo.getUserName());
						oldUser.setPicUrl(userInfo.getPicUrl());
						oldUser.setPhoneNumber(userInfo.getPhoneNumber());
						if (userInfoService.editUserInfo(oldUser)) {
							ar.setSucceedMsg(Const.UPDATE_SUCCEED);
						} else {
							ar.setFailMsg(Const.UPDATE_FAIL);
						}
					}
				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "index/savepwd", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveUserPwd() {
		AjaxRes ar = getAjaxRes();
		Dto paramDto = getParamAsDto();
		if (ar.setNoAuth(
				isAuthedReq(ResourceTypes.BUTTON, RESET_PWD_URL) || isAuthedReq(ResourceTypes.FUNC, RESET_PWD_URL))) {
			try {
				Integer user_id = paramDto.getAsInteger("userId");
				String password = paramDto.getAsString("loginPassword");
				String newpwd = paramDto.getAsString("newpwd");
				String newpwd2 = paramDto.getAsString("newpwd2");

				UserInfo userInfo = userInfoService.getUserByUserID(user_id);
				if (CPSUtil.isNotEmpty(user_id)) {
					String md5Pwd = CipherUtil.createPwdEncrypt(userInfo.getLoginAccount(), password,
							userInfo.getSalt());
					if (userInfo.getLoginPassword().equals(md5Pwd)) {
						if (newpwd.equals(newpwd2)) {
							if (userInfoService.resetPassword(user_id + "", newpwd)) {
								ar.setSucceedMsg(Const.UPDATE_SUCCEED);
							} else {
								ar.setFailMsg(Const.UPDATE_FAIL);
							}
						} else {
							ar.setFailMsg(Const.PASSWORD_NOT_SAME);
						}
					} else {
						ar.setFailMsg(Const.OPASSSORD_ERROR);
					}
				} else {
					ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 获取用户菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "menu/getMenu", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getMenu(HttpServletRequest request) {
		AjaxRes ar = getAjaxRes();
		Dto dto = getParamAsDto();
		String isReload = dto.getAsString("isReload");
		String menuCode = dto.getAsString("menuCode");
		UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
		if (currentUser != null) {
			if (LicenseHelper.license()) {
				try {
					List<Resources> menus = loadCurrentUserResources(!"y".equals(isReload));
					if (menus != null) {
						menus = MenuTreeUtil.buildResourcesTree(menus);
						String html = MenuTreeUtil.buildTreeHtml(menus, menuCode, currentUser);
						ar.setSucceed(html);
					}
				} catch (Exception e) {
					logger.error(e.toString(), e);
					ar.setFailMsg(Const.DATA_FAIL);
				}
			} else {
				ar.setFailMsg(Const.AUTH_FAILD);
			}
		} else {
			ar.setFailMsg(Const.NO_LOGIN_USER);
		}
		return ar;
	}

	/**
	 * 获取用户菜单(JSON)
	 * 
	 * @return
	 */
	@RequestMapping(value = "menu/getJsonMenu", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes getJsonMenu(HttpServletRequest request) {
		AjaxRes ar = getAjaxRes();
		Dto dto = getParamAsDto();
		String isReload = dto.getAsString("isReload");
		String menuCode = dto.getAsString("menuCode");
		UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
		if (currentUser != null) {
			if (LicenseHelper.license()) {

				try {
					List<Resources> menus = loadCurrentUserResources(!"y".equals(isReload));
					if (menus != null) {
						menus = MenuTreeUtil.buildResourcesTree(menus);
						String html = MenuTreeUtil.buildTreeJson(menus, menuCode, currentUser);
						ar.setSucceed(html);
					}

				} catch (Exception e) {
					logger.error(e.toString(), e);
					ar.setFailMsg(Const.DATA_FAIL);
				}
			} else {
				ar.setFailMsg(Const.AUTH_FAILD);
			}
		} else {
			ar.setFailMsg(Const.NO_LOGIN_USER);
		}
		return ar;
	}

	@RequestMapping("adv")
	public String advUI(Model model) {
		return "system/adv/adv";
	}

	@RequestMapping("404")
	public String errorlistUI(Model model) {
		return "system/error/404";
	}

	/**
	 * 没权限页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("noAuthorized")
	public String noAuthorizedUI(Model model) {
		return Const.NO_AUTHORIZED_URL;
	}
}
