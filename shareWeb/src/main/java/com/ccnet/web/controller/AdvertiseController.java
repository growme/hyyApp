package com.ccnet.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.dao.ApiSbContentInfoDao;
import com.ccnet.api.dao.ApiSbContentPic;
import com.ccnet.core.common.AdType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.cache.UserContentReadCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.HttpUtil;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.StringUtils;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.html.ShortUrlUtil;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.IpLocation;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbAdvertVisitLog;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.service.IpLocationService;
import com.ccnet.cps.service.SbAdvertVisitLogService;
import com.ccnet.cps.service.SbAdvertiseInfoService;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.cps.service.SbContentVisitLogService;

import net.sf.json.JSONObject;

/**
 * 广告信息
 * 
 * @author jackieWang
 *
 */
@Controller("advertiseController")
public class AdvertiseController extends BaseController<SbAdvertInfo> {

	@Autowired
	private SbAdvertiseInfoService advertiseInfoService;

	@Autowired
	private SbAdvertVisitLogService sbAdvertVisitLogService;

	@Autowired
	SbContentInfoService sbContentInfoService;
	@Autowired
	SbAdvertiseInfoService adService;
	@Autowired
	private IpLocationService ipLocationService;
	@Autowired
	private SbContentVisitLogService sbContentVisitLogService;
	@Autowired
	ApiSbContentInfoDao apiSbContentInfoDao;
	@Autowired
	ApiSbContentPic apiSbContentPicDao;

	/**
	 * 广告列表
	 * 
	 * @return
	 */
	@RequestMapping("/nullxh*")
	public String advertisIndex(Model model, HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		String token = req.getParameter("token");
		String JP_AD_WXYD_GB_URL = CPSUtil.getParamValue("JP_AD_WXYD_GB_URL");
		if (CPSUtil.isNotEmpty(JP_AD_WXYD_GB_URL) && !JP_AD_WXYD_GB_URL.equals("0")) {
			return "redirect:" + JP_AD_WXYD_GB_URL;
		}
		if (StringUtils.isNoneBlank(token)) {
			SbContentVisitLog arg0 = new SbContentVisitLog();
			arg0.setVisitToken(token);
			SbContentVisitLog log = sbContentVisitLogService.find(arg0);
			if (log != null && log.getVisitToken() != null) {
				log.setGoBack(true);
				sbContentVisitLogService.update(log, "visit_token");
			}
		}
		// 获取广告列表
		int zz = dealContentAdvertise(model, CPSUtil.getIpAddr(req));
		if (zz == 0) {
			List<SbContentInfo> tjlist = apiSbContentInfoDao.getSbContentTjRandInfo(0, 10);
			for (int i = 0; i < tjlist.size(); i++) {
				List<SbContentPic> pics = apiSbContentPicDao.getSbContentPic(tjlist.get(i).getContentCode());
				tjlist.get(i).setPicList(pics);
				// 处理跳转URL
				String paramItem = 0 + "_" + 1 + "_" + tjlist.get(i).getContentId() + "_" + 2;
				String forwardUrlItem = "/article/detail/" + CPSUtil.getParamEncrypt(paramItem);
				tjlist.get(i).setArticleUrl(forwardUrlItem);
			}
			model.addAttribute("tjlist", tjlist);
		}
		return "user/jsp/content/adcontent_info";
	}

	/**
	 * 从广告服务器上面获取地址
	 * 
	 * @return
	 */
	private String getAdvertiseUrlFromApi(String apiAddress) {

		String luodiUrl = null;
		String resultString = null;
		if (CPSUtil.isNotEmpty(apiAddress)) {
			try {

				CPSUtil.xprint("apiAddress=" + apiAddress);
				if (apiAddress.indexOf("https://") != -1) {
					// 发送https请求获取地址
					resultString = HttpUtil.get(apiAddress, true);
				}

				if (apiAddress.indexOf("http://") != -1) {
					// 发送http请求获取地址
					resultString = HttpUtil.get(apiAddress, false);
				}

				// 解析结果
				if (CPSUtil.isNotEmpty(resultString)) {
					CPSUtil.xprint("resultString=>>>" + resultString);
					resultString = resultString.replaceAll("callback", "");
					resultString = resultString.replaceAll("\\(|\\)", "");
					CPSUtil.xprint("_resultString=>>>" + resultString);

					JSONObject jsonObject = JSONObject.fromObject(resultString);
					if (jsonObject != null) {
						luodiUrl = jsonObject.getString("advertising");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		CPSUtil.xprint("luodiUrl=>>>" + luodiUrl);
		return luodiUrl;
	}

	/**
	 * 从数据库获取地址
	 * 
	 * @param adId
	 * @return
	 */
	private String getAdvertiseFromSetting(String adId) {
		String resultString = null;
		if (CPSUtil.isNotEmpty(adId)) {
			// 获取广告信息
			SbAdvertInfo advertInfo = advertiseInfoService.getSbAdvertInfoByID(Integer.valueOf(adId));
			if (CPSUtil.isNotEmpty(advertInfo)) {
				resultString = advertInfo.getAdLink();
			}
		}
		return resultString;
	}

	/**
	 * 广告跳转
	 * 
	 * @return
	 */
	@RequestMapping("/advertise/forward")
	public void advertisForward(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("广告被点击了。。。。。。。。。。。。。。。");
		Dto paramDto = getParamAsDto();
		String ad_id = paramDto.getAsString("ad_id");
		// 是否开启广告API
		String openApi = CPSUtil.getParamValue(Const.CT_ADVERTISE_SERVER_API_SWITCH);
		// 广告API地址
		String apiAddress = CPSUtil.getParamValue(Const.CT_ADVERTISE_SERVER_API_URL);

		String resultString = null;
		try {
			if (CPSUtil.isNotEmpty(openApi) && StateType.Valid.getState().equals(Integer.valueOf(openApi))) {// 开启了API请求
				if (CPSUtil.isNotEmpty(apiAddress)) {
					resultString = getAdvertiseUrlFromApi(apiAddress);
				} else {
					resultString = getAdvertiseFromSetting(ad_id);
				}
			} else {
				resultString = getAdvertiseFromSetting(ad_id);
			}

			if (CPSUtil.isNotEmpty(resultString)) {
				// 处理地址http
				if (resultString.indexOf("http://") < 0 && resultString.indexOf("https://") < 0) {
					resultString = "http://" + resultString;
				}

				if (resultString.indexOf("t.cn") < 0 && resultString.indexOf("url.cn") < 0) {// 如果不是短连接则转换t.cn
																								// 反之不处理
					resultString = ShortUrlUtil.getShortUrl(resultString);
				}
				CPSUtil.xprint("广告短地址为:" + resultString);

				CPSUtil.renderHtml("<script>window.location.href='" + resultString + "';</script>", response);
			} else {
				CPSUtil.renderHtml("<script>window.location.href='https://news.qq.com?t=11';</script>", response);
			}

		} catch (Exception e) {
			CPSUtil.renderHtml("<script>window.location.href='https://news.qq.com?t=12';</script>", response);
			e.printStackTrace();
		}

	}

	@ResponseBody
	@RequestMapping(value = "advertise/getadurl", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes getAdvertUrl(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("【getAdvertUrl】获取广告链接..");
			Dto paramDto = getParamAsDto();
			String visitToken = paramDto.getAsString("token");
			String fingerprint = paramDto.getAsString("fingerprint");
			String ad_id = paramDto.getAsString("aid");

			Integer contentId = paramDto.getAsInteger("cid");
			Integer userId = paramDto.getAsInteger("uid");

			String iswechat = CPSUtil.isWeixin(req);// 是否微信访问

			CPSUtil.xprint("【accounts】visitToken=>>>>" + visitToken);
			CPSUtil.xprint("【accounts】fingerprint=>>>>" + fingerprint);

			String _fingerprint = JedisUtils.getFingerCacheValue(visitToken, "fingerprint");
			String adUrl = "http://www.qq.com";

			System.out.println("点击广告--------------------------------");
			System.out.println("visitToken---" + visitToken);
			System.out.println("_fingerprint---" + _fingerprint);
			System.out.println("fingerprint---" + fingerprint);
			System.out.println("点击广告--------------------------------");

			if (CPSUtil.isEmpty(ad_id)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【getAdvertUrl】aid 为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				ar.setObj(adUrl);
				return ar;
			}

			// 是否开启广告API
			String openApi = CPSUtil.getParamValue(Const.CT_ADVERTISE_SERVER_API_SWITCH);
			// 广告API地址
			String apiAddress = CPSUtil.getParamValue(Const.CT_ADVERTISE_SERVER_API_URL);

			String resultString = null;
			if (CPSUtil.isNotEmpty(openApi) && StateType.Valid.getState().equals(Integer.valueOf(openApi))) {// 开启了API请求
				if (CPSUtil.isNotEmpty(apiAddress)) {
					resultString = getAdvertiseUrlFromApi(apiAddress);
				} else {
					resultString = getAdvertiseFromSetting(ad_id);
				}
			} else {
				resultString = getAdvertiseFromSetting(ad_id);
			}

			// 点击广告错误处理
			if (visitToken == null || "".equals(visitToken) || _fingerprint == null) {
				if (CPSUtil.isNotEmpty(resultString)) {
					// 处理地址http
					if (resultString.indexOf("http://") < 0 && resultString.indexOf("https://") < 0) {
						resultString = "http://" + resultString;
					}
					if (resultString.indexOf("t.cn") < 0 && resultString.indexOf("url.cn") < 0) {// 如果不是短连接则转换t.cn
																									// 反之不处理
						resultString = ShortUrlUtil.getShortUrl(resultString);
					}
					CPSUtil.xprint("广告短地址为:" + resultString);
					adUrl = resultString;
				}
				ar.setObj(adUrl);
				ar.setRes(200);
				return ar;
			}

			// if (CPSUtil.isEmpty(visitToken)) {
			// CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【getAdvertUrl】visitToken
			// 为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
			// ar.setFailMsg("visitToken 为空");
			// ar.setObj(adUrl);
			// return ar;
			// }

			// 比对指纹
			// if (!StringUtils.equals(fingerprint, _fingerprint)) {
			// CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【getAdvertUrl】两次指纹匹配错误！！◆◆◆◆◆◆◆◆◆◆");
			// ar.setFailMsg("两次指纹匹配错误");
			// ar.setObj(adUrl);
			// return ar;
			// }

			// 处理提交参数
			String requestIP = IPUtil.getIpAddr(req);
			// if (CPSUtil.isEmpty(userId) || CPSUtil.isEmpty(contentId)) {
			// CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【getAdvertUrl】提交参数UserID和ContentID不能为空!◆◆◆◆◆◆◆◆◆◆");
			// ar.setFailMsg("参数UserID和ContentID不能为空");
			// ar.setObj(adUrl);
			// return ar;
			// }
			if (contentId != null) {
				// 处理文章阅读数
				SbContentInfo content = CPSUtil.getContentInfoById(Integer.valueOf(contentId));
				if (CPSUtil.isEmpty(content)) {
					content = sbContentInfoService.getSbContentInfoByID(contentId);
				}
			}
			// if (CPSUtil.isEmpty(content)) {
			// CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【getAdvertUrl】文章不存在◆◆◆◆◆◆◆◆◆◆");
			// ar.setFailMsg("文章不存在");
			// ar.setObj(adUrl);
			// return ar;
			// }

			// 判断会员存在
			// MemberInfo member = CPSUtil.getMemeberById(userId + "");
			// if (CPSUtil.isEmpty(member)) {
			// CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【getAdvertUrl】会员不存在◆◆◆◆◆◆◆◆◆◆");
			// ar.setFailMsg("会员不存在");
			// ar.setObj(adUrl);
			// return ar;
			// }

			if (CPSUtil.isNotEmpty(resultString)) {
				// 处理地址http
				if (resultString.indexOf("http://") < 0 && resultString.indexOf("https://") < 0) {
					resultString = "http://" + resultString;
				}
				if (resultString.indexOf("t.cn") < 0 && resultString.indexOf("url.cn") < 0) {// 如果不是短连接则转换t.cn
																								// 反之不处理
					resultString = ShortUrlUtil.getShortUrl(resultString);
				}
				CPSUtil.xprint("广告短地址为:" + resultString);
				adUrl = resultString;
			}

			// 重复点击广告不记录
			SbAdvertVisitLog log = new SbAdvertVisitLog();
			log.setAdId(Integer.valueOf(ad_id));
			log.setHashKey(fingerprint);
			log.setRequestIp(requestIP);
			log.setType(1);
			log = sbAdvertVisitLogService.find(log);
			if (log != null) {
				ar.setObj(adUrl);
				ar.setRes(200);
				return ar;
			}
			// 处理广告费用
			SbAdvertInfo ad = advertiseInfoService.getSbAdvertInfoByID(Integer.valueOf(ad_id));
			/*
			 * if (ad.getAdUserId() != null && ad.getAdUserId() > 0) { UserInfo
			 * user = userInfoService.getUserByUserID(ad.getAdUserId()); if
			 * (user != null && user.getMoney() != null && ad.getUnitPrice() !=
			 * null) { // 减掉用户广告费用 double balance = user.getMoney() -
			 * ad.getUnitPrice(); user.setMoney(balance);
			 * userInfoService.editUserInfo(user); if (balance <= 0D) {
			 * advertiseInfoService.updateAdvertiseStateById(ad.getAdId().
			 * toString(), 0);
			 * InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST); } } }
			 */
			if (CPSUtil.isNotEmpty(ad) && ad.getState().toString().equals("1")) {
				if (CPSUtil.isEmpty(ad.getChargeBalance())) {
					advertiseInfoService.updateAdvertiseStateById(ad.getAdId().toString(),
							StateType.InValid.getState());
					InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
				} else {
					double balance = ad.getChargeBalance() - ad.getUnitPrice();
					ad.setChargeBalance(balance);
					// 余额小于0下架
					if (balance <= 0D) {
						ad.setState(StateType.InValid.getState());
					}
					advertiseInfoService.update(ad, "ad_id");
					InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
				}
			}
			/***************************** 第2次记录开始 *******************************/
			SbAdvertVisitLog visitLog = new SbAdvertVisitLog();
			visitLog.setVisitToken(visitToken);
			visitLog.setAdId(Integer.valueOf(ad_id));
			visitLog.setContentId(contentId);
			visitLog.setType(1);
			visitLog.setHashKey(fingerprint);
			visitLog.setRequestIp(requestIP);
			visitLog.setRequestDetail(req.getHeader("User-Agent"));
			visitLog.setUserId(userId);
			visitLog.setVisitTime(new Date());
			visitLog.setWechatBrowser(Integer.valueOf(iswechat));
			visitLog.setUnitPrice(ad.getUnitPrice());
			sbAdvertVisitLogService.saveSbAdvertVisitLog(visitLog);

			Integer adDiss = 0;
			String adSham = CPSUtil.getParamValue(Const.CT_AD_SHAM_PERCENT);
			if (CPSUtil.isNotEmpty(adSham)) {
				adDiss = Integer.valueOf(adSham);
			}

			boolean showCode = UserContentReadCache.getInstance().canShowAdPage(visitLog.getAdId(), adDiss);
			if (showCode) {
				visitLog.setRequestIp(IPUtil.getRandomIp());
				visitLog.setType(0);
				sbAdvertVisitLogService.saveSbAdvertVisitLog(visitLog);
			}

			// 此处插入数据库判断是否之前有过记录，如果没有则认为有初次阅读，则记帐
			// 记账成功修改缓存中阅读记录标志
			ar.setObj(adUrl);
			ar.setRes(200);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ar;

	}

	/**
	 * 处理广告信息
	 * 
	 * @param model
	 */
	public int dealContentAdvertise(Model model, String ip) {
		String ipCode = JedisUtils.get("CT_IPLocation_Util:" + ip);
		if (com.ccnet.core.common.utils.StringUtils.isBlank(ipCode)) {
			IpLocation ipLocation = ipLocationService.getIpLocation(ip);
			ipCode = ipLocation.getProvinceId() + "";
			// ipCode = IPLocationUtil.getRegionIdLocation(ip);
			JedisUtils.set("CT_IPLocation_Util:" + ip, ipCode, 6 * 24 * 60 * 60);
		}
		SbAdvertInfo tempAd = new SbAdvertInfo();
		tempAd.setState(1);
		List<SbAdvertInfo> allAd = new ArrayList<SbAdvertInfo>();
		// String adStr = JedisUtils.get("CT_SbAdvertInfo");
		// if (com.ccnet.core.common.utils.StringUtils.isBlank(adStr)) {
		// allAd = adService.findList(tempAd);
		// JedisUtils.set("CT_SbAdvertInfo", JSON.toJSONString(allAd), 24 * 60 *
		// 60);
		// } else {
		// allAd = JSON.parseArray(adStr, SbAdvertInfo.class);
		// allAd = adService.findList(tempAd);
		// }
		allAd = adService.findSbAdvertiseInfos();
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【allAd】广告数不存在◆◆◆◆◆◆◆◆◆◆");
		List<SbAdvertInfo> listAd = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> topAd = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> floatAd = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> textAd = new ArrayList<SbAdvertInfo>();
		Integer showState = 0;
		Integer advType = null;
		for (SbAdvertInfo adv : allAd) {
			if (adv.getDistrictIds() != null && adv.getDistrictIds().indexOf(ipCode) >= 0) {
				continue;
			}
			showState = adv.getShowState();
			advType = adv.getAdType();
			adv.setAdTagName(CPSUtil.getCodeName("ADV_TAG", adv.getAdTag()));
			if (advType.equals(AdType.ADTYPE_TEXT.getType()) && showState == 1) {
				textAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_ONLY_SINGLE_PIC.getType()) && showState == 1) {
				listAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_SINGLE_PIC.getType()) && showState == 1) {
				listAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_MORE_PIC.getType()) && showState == 1) {
				listAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_BANNER_PIC.getType()) && showState == 1) {
				topAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_FLOAT_PIC.getType()) && showState == 1) {
				floatAd.add(adv);
			}
		}

		// 横幅广告
		List<SbAdvertInfo> banner_adverts = topAd;
		model.addAttribute("banner_adverts", banner_adverts);

		// 顶部随机单图
		List<SbAdvertInfo> tempAdvs = new ArrayList<SbAdvertInfo>();

		if (listAd.size() > 1) {
			model.addAttribute("single_pic_random_adverts", listAd.get(0));
			listAd.remove(0);
		}

		// 顶部随机单图
		List<SbAdvertInfo> tempAdvs2 = new ArrayList<SbAdvertInfo>();
		for (int i = 0; i < topAd.size(); i++) {
			tempAdvs2.add(topAd.get(i));
		}
		tempAdvs2 = CPSUtil.getRandomAdverts(tempAdvs2, 1);
		SbAdvertInfo single_pic_random_Adverts2 = null;
		if (CPSUtil.listNotEmpty(tempAdvs2)) {
			single_pic_random_Adverts2 = tempAdvs2.get(0);
			model.addAttribute("single_pic_random_adverts2", single_pic_random_Adverts2);
		}
		// 浮动广告
		List<SbAdvertInfo> tempAdvsFloat = floatAd;
		SbAdvertInfo floatAdvert = null;
		SbAdvertInfo floatBottom = null;
		tempAdvs = CPSUtil.getRandomAdverts(tempAdvsFloat, 2);
		if (CPSUtil.listNotEmpty(tempAdvs)) {
			floatAdvert = tempAdvs.get(0);
			model.addAttribute("float_advert", floatAdvert);
			if (tempAdvs.size() > 1) {
				floatBottom = tempAdvs.get(1);
				model.addAttribute("float_bottom", floatBottom);
			}
		}
		// 顶部随机纯文本
		List<SbAdvertInfo> tempAdvsText = textAd;
		List<SbAdvertInfo> text_random_Adverts = CPSUtil.getRandomAdverts(tempAdvsText, 3);
		model.addAttribute("text_random_adverts", text_random_Adverts);
		model.addAttribute("adverts", allAd);
		return listAd.size();
	}

}
