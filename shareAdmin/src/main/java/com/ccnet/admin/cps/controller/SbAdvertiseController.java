package com.ccnet.admin.cps.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.District;
import com.ccnet.admin.bh.service.DistrictService;
import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.AdType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.FileUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.UserInfoService;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbAdvertPic;
import com.ccnet.cps.entity.SbAdvertStat;
import com.ccnet.cps.service.SbAdvertPicService;
import com.ccnet.cps.service.SbAdvertStatService;
import com.ccnet.cps.service.SbAdvertVisitLogService;
import com.ccnet.cps.service.SbAdvertiseInfoService;

/**
 * 广告管理
 * 
 * @author 1234
 *
 */
@Controller
@RequestMapping("/backstage/advertise/")
public class SbAdvertiseController extends AdminBaseController<SbAdvertInfo> {
	// 静态地址
	public static String INDEX_URL = "/backstage/advertise/index";
	public static String GO_ADD_URL = "/backstage/advertise/goadd";
	public static String GO_EDIT_URL = "/backstage/advertise/goedit";
	public static String BATCH_UP_URL = "/backstage/advertise/batchup";
	public static String BATCH_DOWN_URL = "/backstage/advertise/batchdown";
	public static String GO_EDIT_Url_URL = "/backstage/advertise/goeditUrl";

	@Autowired
	private SbAdvertiseInfoService sbAdvertiseInfoService;
	@Autowired
	private SbAdvertPicService sbAdvertPicService;

	@Autowired
	private SbAdvertStatService sbAdvertStatService;
	@Autowired
	UserInfoService userInfoService;

	@Autowired
	SbAdvertVisitLogService sbAdvertVisitLogService;

	@Autowired
	DistrictService districtService;

	/**
	 * 广告信息列表
	 */
	@RequestMapping("index")
	public ModelAndView advertIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbAdvertInfo> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
			SbAdvertInfo sbAdvertInfo = new SbAdvertInfo();
			if (CPSUtil.isNotEmpty(paramDto.getAsString("adType"))) {
				sbAdvertInfo.setAdType(paramDto.getAsInteger("adType"));
			}
			if (CPSUtil.isNotEmpty(paramDto.getAsString("adTag"))) {
				sbAdvertInfo.setAdTag(paramDto.getAsString("adTag"));
			}
			if (CPSUtil.isNotEmpty(paramDto.getAsString("state"))) {
				sbAdvertInfo.setState(paramDto.getAsInteger("state"));
			}

			if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
					|| userInfo.getUserType() == UserType.ADMIN.getType())) {
				sbAdvertInfo.setAdUserId(userInfo.getUserId());
			}
			page = sbAdvertiseInfoService.findSbAdvertiseInfoByPage(sbAdvertInfo, page, paramDto);
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userList", userInfoService.findList(user));
			mav.addObject("pm", page);
			mav.addObject("stateMap", StateType.getStateMap());
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("adTag", paramDto.getAsString("adTag"));
			mav.addObject("adType", paramDto.getAsString("adType"));
			mav.addObject("state", paramDto.getAsString("state"));
			List<Resources> permitFBtn = getPermitBtn(ResourceTypes.FUNC);
			mav.addObject("permitFBtn", permitFBtn);
			mav.addObject("adTypeList", AdType.values());
			mav.addObject("adTagList", CPSUtil.getCodeList("ADV_TAG"));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "广告管理");
			mav.setViewName("admincps/jsp/advertise/good_advertise_list");
			if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
					|| userInfo.getUserType() == UserType.ADMIN.getType())) {
				mav.setViewName("admincps/jsp/advertise/good_advertise_user_list");
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 广告信息列表
	 */
	@RequestMapping("stat")
	public ModelAndView advertStat() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbAdvertStat> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
			SbAdvertStat sbAdvertStat = new SbAdvertStat();
			if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
					|| userInfo.getUserType() == UserType.ADMIN.getType())) {
				sbAdvertStat.setUserId(userInfo.getUserId());
			}

			page = sbAdvertStatService.findSbAdvertStatByPage(sbAdvertStat, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			List<Resources> permitFBtn = getPermitBtn(ResourceTypes.FUNC);
			mav.addObject("permitFBtn", permitFBtn);
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "广告统计");
			mav.setViewName("admincps/jsp/advertise/advertise_stat_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加广告信息
	 */
	@RequestMapping("goadd")
	public ModelAndView advertgoadd() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userList", userInfoService.findList(user));
			mav.addObject("districtList", districtService.findDistrictDaoByList(new District()));
			// 广告类型
			mav.addObject("adTypeMap", AdType.getStateMap());
			// 广告状态
			mav.addObject("stateMap", StateType.getStateMap());
			// 广告标签
			mav.addObject("adTagList", CPSUtil.getCodeList("ADV_TAG"));
			mav.addObject(Const.CT_MENU_NAV, "添加广告");
			mav.setViewName("admincps/jsp/advertise/good_advertise_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改广告
	 * 
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView advertgoEdit() {
		// sbAdvertInfo = sbAdvertiseInfoService.getSbAdvertInfoByID(adId);
		ModelAndView mav = new ModelAndView();
		SbAdvertInfo sbAdvertInfo = new SbAdvertInfo();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userList", userInfoService.findList(user));
			Dto paramDto = getParamAsDto();
			Integer optype = paramDto.getAsInteger("optype");
			Integer adId = paramDto.getAsInteger("advertId");
			if (CPSUtil.isNotEmpty(adId)) {
				sbAdvertInfo = sbAdvertiseInfoService.getSbAdvertInfoByID(adId);
			}
			mav.addObject("districtList", districtService.findDistrictDaoByList(new District()));
			mav.addObject("adTypeMap", AdType.getStateMap());
			mav.addObject("adTagList", CPSUtil.getCodeList("ADV_TAG"));
			mav.addObject("optype", optype);
			mav.addObject("advert", sbAdvertInfo);
			mav.addObject("stateMap", StateType.getStateMap());
			mav.addObject(Const.CT_MENU_NAV, "编辑广告");

			mav.setViewName("admincps/jsp/advertise/good_advertise_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改广告地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "goeditUrl", method = RequestMethod.GET)
	public ModelAndView advertgoEditUrl() {
		// sbAdvertInfo = sbAdvertiseInfoService.getSbAdvertInfoByID(adId);
		ModelAndView mav = new ModelAndView();
		SbAdvertInfo sbAdvertInfo = new SbAdvertInfo();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("userList", userInfoService.findList(user));
			Dto paramDto = getParamAsDto();
			Integer optype = paramDto.getAsInteger("optype");
			Integer adId = paramDto.getAsInteger("advertId");
			if (CPSUtil.isNotEmpty(adId)) {
				sbAdvertInfo = sbAdvertiseInfoService.getSbAdvertInfoByID(adId);
			}
			mav.addObject("adTypeMap", AdType.getStateMap());
			mav.addObject("adTagList", CPSUtil.getCodeList("ADV_TAG"));
			mav.addObject("optype", optype);
			mav.addObject("advert", sbAdvertInfo);
			mav.addObject("stateMap", StateType.getStateMap());
			mav.addObject(Const.CT_MENU_NAV, "编辑广告");
			mav.setViewName("admincps/jsp/advertise/good_advertise_urlInfo");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 保存广告地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateUrl", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes advertUpdate(SbAdvertInfo sbAdvertInfo) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_Url_URL))) {
			try {
				boolean isOk = false;
				SbAdvertInfo table = sbAdvertiseInfoService.getSbAdvertInfoByID(sbAdvertInfo.getAdId());
				if (table != null) {
					table.setAdLink(sbAdvertInfo.getAdLink());
					if (sbAdvertiseInfoService.update(table, "adId") > 0) {
						isOk = true;
					}
				}
				if (isOk) {
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
					InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
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
	 * 保存广告
	 * 
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes advertsave(SbAdvertInfo sbAdvertInfo) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				boolean isOk = false;
				UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
				if (CPSUtil.isNotEmpty(sbAdvertInfo.getAdId())) {
					sbAdvertInfo.setUserId(userInfo.getUserId());
					if (CPSUtil.isEmpty(sbAdvertInfo.getChargeBalance()) || sbAdvertInfo.getChargeBalance() <= 0D) {
						sbAdvertInfo.setState(0);
					}
					isOk = sbAdvertiseInfoService.saveSbAdvertInfo(sbAdvertInfo);
				} else {
					sbAdvertInfo.setUserId(userInfo.getUserId());
					sbAdvertInfo.setCreateTime(new Date());
					sbAdvertInfo.setAdCode(UuidUtil.get32UUID());
					sbAdvertInfo.setState(StateType.InValid.getState());
					if (CPSUtil.isEmpty(sbAdvertInfo.getChargeBalance()) || sbAdvertInfo.getChargeBalance() <= 0D) {
						sbAdvertInfo.setState(0);
					}
					isOk = sbAdvertiseInfoService.saveSbAdvertInfo(sbAdvertInfo);
				}
				if (isOk) {
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
					// InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
					JedisUtils.set("CT_SbAdvertInfo", "", 1000);
					HessianClientUtil.updateCache(Const.CT_ADVERTISE_LIST);
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
	 * 批量上架
	 * 
	 * @return
	 */
	@RequestMapping(value = "batchup", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes bacthUpAdvertise() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, BATCH_UP_URL))) {
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				String adId = paramDto.getAsString("advertId");
				if (CPSUtil.isNotEmpty(adId)) {
					if (sbAdvertiseInfoService.updateAdvertiseStateById(adId, StateType.Valid.getState())) {
						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
						InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
						HessianClientUtil.updateCache(Const.CT_ADVERTISE_LIST);
						JedisUtils.set("CT_SbAdvertInfo", "", 1000);
					} else {
						ar.setFailMsg(Const.UPDATE_FAIL);
					}
				}
			}
		}
		return ar;
	}

	/**
	 * 批量下架
	 * 
	 * @return
	 */
	@RequestMapping(value = "batchdown", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes bacthDownAdvertise() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, BATCH_DOWN_URL))) {
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				String adId = paramDto.getAsString("advertId");
				if (CPSUtil.isNotEmpty(adId)) {
					if (sbAdvertiseInfoService.updateAdvertiseStateById(adId, StateType.InValid.getState())) {
						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
						InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
						HessianClientUtil.updateCache(Const.CT_ADVERTISE_LIST);
						JedisUtils.set("CT_SbAdvertInfo", "", 1000);
					} else {
						ar.setFailMsg(Const.UPDATE_FAIL);
					}
				}
			}
		}
		return ar;
	}

	/**
	 * 删除广告
	 * 
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes advertgoDel() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				String adId = paramDto.getAsString("advertId");
				if (CPSUtil.isNotEmpty(adId)) {
					if (sbAdvertiseInfoService.trashSbAdvertInfo(adId)) {
						ar.setRes(Const.SUCCEED);
						ar.setSucceedMsg(Const.DEL_SUCCEED);
						InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
						HessianClientUtil.updateCache(Const.CT_ADVERTISE_LIST);
						JedisUtils.set("CT_SbAdvertInfo", "", 1000);
					} else {
						ar.setRes(Const.FAIL);
						ar.setFailMsg(Const.DEL_FAIL);
					}
				}
			}
		}
		return ar;
	}

	/**
	 * 删除文件 (添加更新时)
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/file/trash")
	@ResponseBody
	public AjaxRes deleteAdvertPicFile() {
		// 获取参数
		boolean temp = false;
		AjaxRes ajaxRes = new AjaxRes();
		Dto paramDto = getParamAsDto();
		String picPath = paramDto.getAsString("picPath");
		String adId = paramDto.getAsString("aId");
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if (ajaxRes.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				if (CPSUtil.isNotEmpty(picPath)) {
					String busStr = picPath.substring(picPath.lastIndexOf("/") + 1, picPath.indexOf("."));
					String params[] = busStr.split("-");
					String userId = params[1];// 用户ID
					if (userId.equals(userInfo.getUserId() + "") && params.length == 3) {
						// 文件保存路径
						String savePath = CPSUtil.getContainPath() + picPath;
						CPSUtil.xprint("path=" + savePath);
						// 未插入数据库的广告
						if ("nf".equals(adId)) {
							if (FileUtil.deletePicFile(savePath)) {
								ajaxRes.setSucceedMsg(busStr);
							} else {
								ajaxRes.setFailMsg(Const.DEL_FAIL);
							}
						} else {
							SbAdvertInfo sbAdvertInfo = sbAdvertiseInfoService
									.getSbAdvertInfoByID(Integer.parseInt(adId));
							if (CPSUtil.isNotEmpty(sbAdvertInfo)) {
								SbAdvertPic rgoodPic = sbAdvertPicService.findAdvertPics(sbAdvertInfo.getAdCode(),
										picPath);
								if (CPSUtil.isNotEmpty(rgoodPic)) {
									temp = sbAdvertPicService.trashSbAdvertPic(rgoodPic);
									if (temp) {
										FileUtil.deletePicFile(savePath);
										ajaxRes.setSucceedMsg(busStr);
									} else {
										ajaxRes.setFailMsg(Const.DEL_FAIL);
									}
								} else {// 还没有提交到数据库 只需要删除物理文件
									if (FileUtil.deletePicFile(savePath)) {
										ajaxRes.setSucceedMsg(busStr);
									} else {
										ajaxRes.setFailMsg(Const.DEL_FAIL);
									}
								}
							} else {
								// 未找到广告 删除物理文件
								if (FileUtil.deletePicFile(savePath)) {
									ajaxRes.setSucceedMsg(busStr);
								} else {
									ajaxRes.setFailMsg(Const.DEL_FAIL);
								}
							}
						}
					} else {
						ajaxRes.setFailMsg(Const.NO_AUTHORIZED_MSG);
					}
				} else {
					ajaxRes.setFailMsg(Const.NO_PARAM_ERROR);
				}
			} catch (Exception e) {
				ajaxRes.setFailMsg(Const.DEL_FAIL);
				e.printStackTrace();
			}
		} else {
			ajaxRes.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}

		return ajaxRes;
	}

	/**
	 * 广告统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAdvertiseStatistics")
	public ModelAndView getAdvertiseStatistics() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		String readDate = paramDto.getAsString("readDate");
		Integer adId = paramDto.getAsInteger("adId");
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if (StringUtils.isBlank(readDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			readDate = sdf.format(new Date());
		}
		mav.addObject("userInfo", userInfo);
		SbAdvertInfo arg0 = new SbAdvertInfo();
		List<SbAdvertInfo> adList = sbAdvertiseInfoService.findList(arg0);
		mav.addObject("adList", adList);
		Integer userId = null;
		if (!(userInfo.getUserType() == UserType.SYSTEMADMIN.getType()
				|| userInfo.getUserType() == UserType.ADMIN.getType())) {
			userId = userInfo.getUserId();
		}
		List<SbAdvertInfo> list = sbAdvertiseInfoService.getAdvertInfosByAdIdAndDate(userId, adId, readDate);
		mav.addObject("readDate", readDate);
		mav.addObject("list", list);
		List<Resources> permitFBtn = getPermitBtn(ResourceTypes.FUNC);
		mav.addObject("permitFBtn", permitFBtn);
		mav.addObject("adId", adId);
		mav.addObject("adTypeMap", AdType.getStateMap());
		mav.addObject("adTagList", CPSUtil.getCodeList("ADV_TAG"));
		mav.addObject("stateMap", StateType.getStateMap());
		mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
		mav.addObject(Const.CT_MENU_NAV, "广告统计");
		mav.setViewName("admincps/jsp/advertise/good_advertise_statistics_list");
		return mav;
	}

}
