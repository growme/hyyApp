package com.ccnet.web.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccnet.api.entity.Oauth2Token;
import com.ccnet.api.entity.SNSUserInfo;
import com.ccnet.api.util.WechatH5Util;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.ShareType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.DeviceUtils;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.StringUtils;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.MemberWxInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.service.MemberWxInfoService;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbShareLogService;

/**
 * api接口控制
 * 
 * @author jackieWang
 *
 */
@Controller
@RequestMapping("/api/")
public class UserApiController extends BaseController<Object> {

	@Autowired
	private SbShareLogService shareLogService;
	@Autowired
	private SbMoneyCountService moneyCountService;
	@Autowired
	private SbContentInfoService contentInfoService;
	@Autowired
	private SbContentInfoService sbContentInfoService;
	@Autowired
	private MemberWxInfoService memberWxInfoService;

	/**
	 * 接收用户分享数据统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "share/receive", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public void receiveShareData(HttpServletRequest request, HttpServletResponse response) {

		// 获取参数
		Dto dto = getParamAsDto();
		Integer userId = dto.getAsInteger("pid");
		Integer contentId = dto.getAsInteger("cid");
		Integer shareType = dto.getAsInteger("stp");
		CPSUtil.xprint("++++++++pid=" + userId + " cid=" + contentId + " stp=" + shareType);
		// 获取请求人IP
		String requestIp = IPUtil.getIpAddr(request);
		// 获取客户端信息
		String deviceDetail = DeviceUtils.getDevDetailInfo(request);
		CPSUtil.xprint("++++++++shareId=" + requestIp);
		CPSUtil.xprint("++++++++shareDevice=" + deviceDetail);
		// 一周分享获取收益上限
		/*
		 * String weekTotalMoney =
		 * CPSUtil.getParamValue(Const.CT_USER_WEEK_TOTAL_MONEY);
		 * if(CPSUtil.isEmpty(weekTotalMoney)){ weekTotalMoney = "2";//默认2元 }
		 * Integer _weekMoney = Integer.valueOf(weekTotalMoney);
		 */
		// 判断参数
		if (!StringHelper.checkParameter(userId, contentId, shareType)) {
			CPSUtil.xprint("++++++++NoParamError++++++++");
			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
			return;
		}

		// 判断会员存在
		SbContentInfo content = CPSUtil.getContentInfoById(Integer.valueOf(contentId));
		if (CPSUtil.isEmpty(content)) {
			content = sbContentInfoService.getSbContentInfoByID(contentId);
		}
		if (CPSUtil.isEmpty(content)) {
			CPSUtil.xprint("++++++++DataUnExistError++++++++");
			responseWriter(ResponseCode.responseMessage(ResponseCode.DataUnExistError), response);
			return;
		}

		// 判断会员存在
		MemberInfo member = CPSUtil.getMemeberById(userId + "");
		if (CPSUtil.isEmpty(member)) {
			CPSUtil.xprint("++++++++AccountUnExistError++++++++");
			responseWriter(ResponseCode.responseMessage(ResponseCode.AccountUnExistError), response);
			return;
		}

		try {
			// 处理文章分享次数
			contentInfoService.updateContentShareNum(contentId, content.getShareNum() + 1);// 递增1
			Integer givenMoney = 0;
			Double shareMoney = 0d;
			if (ShareType.WXPYQ.getType().equals(shareType)) {// 朋友圈
				shareMoney = content.getTimelineShareAward();
				CPSUtil.xprint("朋友圈分享价格:" + shareMoney);
			} else {// 好友分享
				shareMoney = content.getFriendShareAward();
				CPSUtil.xprint("好友分享价格:" + shareMoney);
			}

			// 保存日志数据
			SbShareLog shareLog = new SbShareLog();
			shareLog.setShareIp(requestIp);
			shareLog.setShareType(shareType);
			shareLog.setShareTime(new Date());
			shareLog.setUserId(userId);
			shareLog.setGivenMoney(givenMoney);
			shareLog.setShareMoney(shareMoney);
			shareLog.setDeviceDetail(deviceDetail);
			shareLog.setContentId(contentId);
			CPSUtil.xprint("++++++++saveSbShareLog++++++++");
			shareLogService.saveSbShareLog(shareLog);

		} catch (Exception e) {
			e.printStackTrace();
			CPSUtil.xprint("++++++++CommError++++++++");
			responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);
			return;
		}

		// 处理返回提示
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put(MessageKey.apicode.name(), ResponseCode.CommSuccess.getCode());
		map.put(MessageKey.msg.name(), "分享成功");
		responseWriter(map, response);
		return;

	}

	/**
	 * 会员近一周的收益统计总额
	 * 
	 * @param contentId
	 * @param userId
	 * @param requestIp
	 * @return
	 */
	private double getCurUserWeekMoney(Integer userId) {
		Double totalMoney = 0.0;
		Dto paramDto = new BaseDto();
		SbMoneyCount moneyCount = new SbMoneyCount();
		moneyCount.setUserId(userId);
		moneyCount.setmType(AwardType.transmit.getAwardId());
		paramDto.put("start_date", CPSUtil.getDateByUDay(-7));// 近一周
		paramDto.put("end_date", CPSUtil.getCurDate());
		List<SbMoneyCount> mlList = moneyCountService.findSbMoneyCountList(moneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mlList)) {
			for (SbMoneyCount mc : mlList) {
				totalMoney = totalMoney + mc.getUmoney();
			}
		}
		CPSUtil.xprint("totalMoney=" + totalMoney);
		return totalMoney;
	}

	// 同一个人同一篇文章只能算一次
	private boolean checkShareLogExist(Integer userId, Integer contentId) {
		boolean temp = false;
		SbShareLog shareLog = new SbShareLog();
		shareLog.setUserId(userId);
		shareLog.setContentId(contentId);
		shareLog = shareLogService.findSbShareLogByID(shareLog);
		if (shareLog != null && shareLog.getShareId() != null) {
			temp = true;
		}
		return temp;
	}

	/**
	 * 判断两次分享的间隔
	 * 
	 * @param userId
	 * @param subTime
	 * @return
	 */
	private boolean checkSubTimeByShareLog(Integer userId, Integer subTime) {
		boolean temp = false;
		SbShareLog log = shareLogService.findLastedShareLog(userId);
		if (CPSUtil.isNotEmpty(log)) {
			Date shareTime = log.getShareTime();
			String _shareTime = DateUtils.formatDate(shareTime, DateUtils.parsePatterns[3]);
			int subSecond = CPSUtil.getSubSecond(_shareTime, CPSUtil.getCurrentTime());
			if (subSecond > subTime) {
				temp = true;
			}
		}
		return temp;
	}

	/**
	 * 今日徒孙收益
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("invitedApp")
	public String invitedApp(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			String token = request.getParameter("token");
			model.addAttribute("token", token);
			String isWeixin = CPSUtil.isWeixin(request);
			if (isWeixin.equals("0")) {
				return "/user/jsp/invited/download";
			}
			String code = request.getParameter("code");
			String appId = GetPropertiesValue.getValue("Config.properties", "appId_h5");
			String appSecret = GetPropertiesValue.getValue("Config.properties", "appSecret_h5");
			String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "domain");
			if (StringUtils.isBlank(code)) {
				String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri="
						+ URLEncoder.encode(DOMAIN + "/api/invitedApp?params=" + token, "UTF-8") + "&response_type=code"
						+ "&scope=snsapi_userinfo" + "&state=STATE#wechat_redirect";
				model.addAttribute("target", url);
			}
			Oauth2Token oauth2 = WechatH5Util.getOauth2AccessToken(appId, appSecret, code);
			if (oauth2 == null) {
				return "/user/jsp/invited/download";
			}
			SNSUserInfo userInfo = WechatH5Util.getWxUserInfo(oauth2.getAccessToken(), oauth2.getOpenId());
			if (userInfo != null) {
				MemberWxInfo memberWxInfo = new MemberWxInfo();
				memberWxInfo.setOpenid(userInfo.getOpenId());
				memberWxInfo.setUnionid(userInfo.getUnionid());
				memberWxInfo.setCreateDate(new Date());
				memberWxInfo.setVisitCode(token);
				memberWxInfoService.insert(memberWxInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/user/jsp/invited/download";
	}
}
