package com.ccnet.admin.cps.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.CheckStateType;
import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.WeightType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.FileUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.service.SbAdvertiseInfoService;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.cps.service.impl.SbContentPicServiceImpl;

/**
 * 文章管理
 * 
 * @author JackieWang
 * 
 */
@Controller
@RequestMapping("/backstage/content/")
public class SbContentInfoController extends AdminBaseController<SbContentInfo> {

	// 静态路径
	public static String GO_ADD_URL = "/backstage/content/goadd";
	public static String GO_EDIT_URL = "/backstage/content/goedit";
	public static String GO_GATHER_URL = "/backstage/content/gather";

	@Autowired
	private SbContentInfoService sbContentInfoService;
	@Autowired
	private SbColumnInfoService sbColumnInfoService;
	@Autowired
	private SbContentPicServiceImpl sbContentPicService;
	@Autowired
	private SbAdvertiseInfoService sbAdvertiseInfoService;

	/**
	 * 文章信息列表
	 */
	@RequestMapping("index")
	public ModelAndView contentIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbContentInfo> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbContentInfo contentInfo = new SbContentInfo();
			if (CPSUtil.isNotEmpty(paramDto.getAsString("contentType"))) {
				contentInfo.setContentType(paramDto.getAsInteger("contentType"));
			}
			if (CPSUtil.isNotEmpty(paramDto.getAsString("checkState"))) {
				contentInfo.setCheckState(paramDto.getAsInteger("checkState"));
			}
			if (CPSUtil.isNotEmpty(paramDto.getAsString("weightState"))) {
				contentInfo.setHomeToped(paramDto.getAsInteger("weightState"));
			}
			page = sbContentInfoService.findSbContentInfoByPage(contentInfo, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("stateMap", CheckStateType.values());
			mav.addObject("typeMap", ContentType.values());
			mav.addObject("weightMap", WeightType.getStateMap());
			mav.addObject("weightState", paramDto.getAsInteger("weightState"));
			mav.addObject("checkState", paramDto.getAsInteger("checkState"));
			mav.addObject("contentType", paramDto.getAsInteger("contentType"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "文章管理");
			mav.setViewName("admincps/jsp/content/content_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加文章信息
	 */
	@RequestMapping("goadd")
	public ModelAndView goAdd() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.addObject("weightMap", WeightType.getStateMap());
			mav.addObject("cstateMap", CheckStateType.values());
			mav.addObject("typeMap", ContentType.getStateMap());
			mav.addObject("adList", sbAdvertiseInfoService.findList(new SbAdvertInfo()));
			List<SbColumnInfo> dataList = sbColumnInfoService.findList(new SbColumnInfo());
			mav.addObject("columnList", dataList);
			mav.addObject("stateMap", StateType.getStateMap());
			mav.addObject("cstateMap", CheckStateType.values());
			mav.addObject("typeMap", ContentType.getStateMap());
			mav.addObject(Const.CT_MENU_NAV, "添加文章");
			mav.setViewName("admincps/jsp/content/content_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEdit() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer contentId = paramDto.getAsInteger("contentId");
			if (CPSUtil.isNotEmpty(contentId)) {
				SbContentInfo contentInfo = new SbContentInfo();
				contentInfo.setContentId(contentId);
				if (CPSUtil.isNotEmpty(contentId)) {
					contentInfo = sbContentInfoService.getSbContentInfoByID(contentId);
					mav.addObject("contentInfo", contentInfo);
				}

				List<SbColumnInfo> dataList = sbColumnInfoService.findList(new SbColumnInfo());
				mav.addObject("adList", sbAdvertiseInfoService.findList(new SbAdvertInfo()));
				mav.addObject("columnList", dataList);
				mav.addObject("weightMap", WeightType.getStateMap());
				mav.addObject("stateMap", StateType.getStateMap());
				mav.addObject("cstateMap", CheckStateType.values());
				mav.addObject("typeMap", ContentType.getStateMap());
				mav.addObject(Const.CT_MENU_NAV, "编辑文章");
				mav.setViewName("admincps/jsp/content/content_info");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}

		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 保存文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveContent(SbContentInfo contentInfo) {

		if (CPSUtil.isEmpty(contentInfo.getReadAward())) {
			String readAward = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_MONEY);
			contentInfo.setReadAward(Double.valueOf(readAward));
		}

		if (CPSUtil.isEmpty(contentInfo.getFriendShareAward())) {
			String shareAward = CPSUtil.getParamValue(Const.CT_ARTICLE_SHARE_MONEY);
			contentInfo.setFriendShareAward(Double.valueOf(shareAward));
		}

		if (CPSUtil.isEmpty(contentInfo.getTimelineShareAward())) {
			String shareAward = CPSUtil.getParamValue(Const.CT_ARTICLE_TIME_LINE_SHARE_MONEY);
			contentInfo.setTimelineShareAward(Double.valueOf(shareAward));
		}

		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				boolean isOk = false;
				UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
				if (CPSUtil.isEmpty(contentInfo.getContentId())) {
					contentInfo.setContentCode(UuidUtil.get32UUID());// 生成编码
					contentInfo.setUserId(userInfo.getUserId());
					contentInfo.setCreateTime(new Date());
					contentInfo.setReadNum(0);
					contentInfo.setShareNum(0);
					if (contentInfo.getHomeToped() == 1) {
						contentInfo.setTopedTime(new Date());
					}
					if (CPSUtil.isEmpty(contentInfo.getCheckState())) {
						contentInfo.setCheckState(StateType.InValid.getState());// 待审核
					}
					isOk = sbContentInfoService.saveSbContentInfo(contentInfo);
				} else {
					// 获取老的数据
					SbContentInfo sbContentInfo = new SbContentInfo();
					sbContentInfo = sbContentInfoService.getSbContentInfoByID(contentInfo.getContentId());
					contentInfo.setCreateTime(sbContentInfo.getCreateTime());
					contentInfo.setUserId(userInfo.getUserId());
					contentInfo.setReadNum(sbContentInfo.getReadNum());
					contentInfo.setShareNum(sbContentInfo.getShareNum());
					if (contentInfo.getHomeToped() == 1 && CPSUtil.isEmpty(contentInfo.getTopedTime())) {
						contentInfo.setTopedTime(new Date());
					}
					isOk = sbContentInfoService.editSbContentInfo(contentInfo);
				}
				if (isOk) {
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
					InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
					// HessianClientUtil.updateCache(Const.CT_CONTENT_INFO_LIST);
				} else {
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 发布文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "release", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes releaseContent() {
		AjaxRes ar = getAjaxRes();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			String contentId = paramDto.getAsString("contentId");
			if (CPSUtil.isNotEmpty(contentId)) {
				if (sbContentInfoService.pushSbContentInfo(contentId, StateType.Valid)) {
					InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
					// HessianClientUtil.updateCache(Const.CT_CONTENT_INFO_LIST);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} else {
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 取消发布文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "unrelease", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes unReleaseContent() {
		AjaxRes ar = getAjaxRes();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			String contentId = paramDto.getAsString("contentId");
			if (CPSUtil.isNotEmpty(contentId)) {
				if (sbContentInfoService.pushSbContentInfo(contentId, StateType.InValid)) {
					InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
					// HessianClientUtil.updateCache(Const.CT_CONTENT_INFO_LIST);
					ar.setSucceedMsg(Const.SAVE_SUCCEED);
				} else {
					ar.setFailMsg(Const.SAVE_FAIL);
				}
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 删除文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashContent() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
				Dto paramDto = getParamAsDto();
				String contentId = paramDto.getAsString("contentId");
				if (CPSUtil.isNotEmpty(contentId)) {
					if (sbContentInfoService.trashSbContentInfo(contentId)) {
						InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
						// HessianClientUtil.updateCache(Const.CT_CONTENT_INFO_LIST);
						ar.setSucceedMsg(Const.DEL_SUCCEED);
					} else {
						ar.setFailMsg(Const.DEL_FAIL);
					}
				}
			} else {
				ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 采集文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "gather/wechat", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes gatherWeChatContent() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_GATHER_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				String columnId = paramDto.getAsString("columnId");
				String weixinLink = paramDto.getAsString("weixinLink");
				UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
				if (StringHelper.checkParameter(columnId, weixinLink)) {
					// 处理&amp;标签
					weixinLink = StringUtils.replace(weixinLink, "&amp;", "&");
					paramDto.put("weixinLink", weixinLink);
					if (sbContentInfoService.saveGatherContent(paramDto, userInfo)) {
						InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
						// HessianClientUtil.updateCache(Const.CT_CONTENT_INFO_LIST);
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					} else {
						ar.setFailMsg(Const.SAVE_FAIL);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 微信采集
	 * 
	 * @return
	 */
	@RequestMapping("gather")
	public ModelAndView gatherWechat() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.addObject("columnList", sbColumnInfoService.findList(new SbColumnInfo()));
			// 处理文章阅读和分享奖励金额
			String readAward = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_MONEY);
			if (CPSUtil.isEmpty(readAward)) {
				readAward = "0.2";
			}
			String shareAward = CPSUtil.getParamValue(Const.CT_ARTICLE_SHARE_MONEY);
			if (CPSUtil.isEmpty(shareAward)) {
				shareAward = "0.2";
			}

			String shareTimeLineAward = CPSUtil.getParamValue(Const.CT_ARTICLE_TIME_LINE_SHARE_MONEY);
			if (CPSUtil.isEmpty(shareTimeLineAward)) {
				shareTimeLineAward = "0.2";
			}

			mav.addObject("readAward", readAward);
			mav.addObject("shareTimeLineAward", shareTimeLineAward);
			mav.addObject("shareAward", shareAward);
			mav.addObject(Const.CT_MENU_NAV, "采集微信文章");
			mav.setViewName("admincps/jsp/content/gather_content");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/file/trash")
	@ResponseBody
	public AjaxRes deleteContentPicFile() {
		// 获取参数
		boolean temp = false;
		AjaxRes ajaxRes = new AjaxRes();
		Dto paramDto = getParamAsDto();
		String picPath = paramDto.getAsString("picPath");
		String contentId = paramDto.getAsString("contentId");
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
						if ("nf".equals(contentId)) {
							if (FileUtil.deletePicFile(savePath)) {
								ajaxRes.setSucceedMsg(busStr);
							} else {
								ajaxRes.setFailMsg(Const.DEL_FAIL);
							}
						} else {
							SbContentInfo content = sbContentInfoService
									.getSbContentInfoByID(Integer.parseInt(contentId));
							if (CPSUtil.isNotEmpty(content)) {
								// 查询当前图片是否在图片列表中已存在
								SbContentPic rgoodPic = sbContentPicService.findContentPics(content.getContentCode(),
										picPath);
								if (CPSUtil.isNotEmpty(rgoodPic)) {
									temp = sbContentPicService.trashSbContentPic(rgoodPic);
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

}
