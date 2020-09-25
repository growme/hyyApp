package com.ccnet.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccnet.api.service.ApiLoginService;
import com.ccnet.api.util.VerifyCodeUtil;
import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.sms.SenderSMSUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.service.MemberInfoService;

/**
 * 处理短信验证码 ClassName: MobileCodeController
 * 
 * @author Jackie Wang
 * @company 薄荷咨询
 * @copyright 版权所有 盗版必究
 * @date 2019-10-19
 */
@Controller
@RequestMapping("/mobile/")
public class MobileCodeController extends BaseController<Object> {

	@Autowired
	private MemberInfoService memberInfoService;

	@Autowired
	private CacheManager cacheManager;
	@Autowired
	ApiLoginService apiLoginService;

	// 获取缓存对象
	private Cache<String, Date> getSendSMSCache() {
		if (cacheManager != null) {
			return cacheManager.getCache("sendSMSCache");
		}
		return null;
	}

	/**
	 * 获取短信验证码
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/code/send", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public void getSMSCode(HttpServletRequest req, HttpSession session, HttpServletResponse resp) {

		// 获取参数
		Dto paramDto = getParamAsDto();
		MemberInfo userInfo = getCurUser();
		String type = paramDto.getAsString("tp");// 标志 0 表示普通模式 1 表示提现模式(必须登陆)
		String mobile = paramDto.getAsString("mobile");// 手机号
		String captcha = paramDto.getAsString("captcha");// 图形验证码
		// session中图片验证码
		String scaptcha = (String) getSessionAttr(Const.SESSION_SECURITY_CODE);
		// 获取配置
		int flag = 0;
		String regType = CPSUtil.getParamValue(Const.MOBILE_REG_VOICE_CHECK);
		String payType = CPSUtil.getParamValue(Const.MOBILE_PAY_VOICE_CHECK);
		if (CPSUtil.isNotEmpty(type) && "1".equals(type)) {// 提现模式下
			if (CPSUtil.isNotEmpty(payType) && "1".equals(payType)) {
				flag = 1;// 开启语音验证码提现
			}
		} else {
			if (CPSUtil.isNotEmpty(regType) && "1".equals(regType)) {
				flag = 1;// 开启语音验证码注册和找回密码
			}
		}

		// 判断参数
		if (!StringHelper.checkParameter(mobile, captcha, scaptcha)) {
			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), resp);
			return;
		}

		// 判断图形验证码
		if (!captcha.equalsIgnoreCase(scaptcha)) {
			responseWriter(ResponseCode.responseMessage(ResponseCode.CaptchaError), resp);
			return;
		}

		// 提取用户信息
		MemberInfo memberInfo = memberInfoService.findFormatByLoginName(mobile.trim());
		// 判断逻辑
		if (CPSUtil.isNotEmpty(type) && "1".equals(type)) {// 提现模式下 用户必须登录状态
															// 且信息一致
			if (CPSUtil.isEmpty(memberInfo) || !(memberInfo.getMobile().equals(userInfo.getMobile()))) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.AccountNotMatchError), resp);
				return;
			}
		} else if (CPSUtil.isNotEmpty(type) && "0".equals(type)) {// 注册模式模式下
																	// 用户必须不能已经存在
			// 判断手机号码是否存在
			if (CPSUtil.isNotEmpty(memberInfo)) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.AccountExistError), resp);
				return;
			}
		} else {
			// 其他模式 2 比如 找回密码 必须账号已经存在
			if (CPSUtil.isEmpty(memberInfo)) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.AccountUnExistError), resp);
				return;
			}
		}

		// 判断的两次发送短信验证码的周期 不少于一分钟
		if (!checkSendSmsTime(mobile)) {
			responseWriter(ResponseCode.responseMessage(ResponseCode.RepeatSubmitError), resp);
			return;
		}

		// 开始发送短信验证码
		Map<String, Object> map = new HashMap<String, Object>(0);
		//Dto reDto = SenderSMSUtil.getSmsCode(mobile, flag);
		String styr = VerifyCodeUtil.getValidVerifyCode(mobile);
//		String msg = reDto.getAsString("msg");
//		String rcode = reDto.getAsString("rcode");
//		String vcode = reDto.getAsString("vcode");
		// String vcode = UniqueID.getUniqueID(6, 0);
		// String msg = "发送验证码成功";
		if (!"0".equals(styr)) {// 发送成功
			CPSUtil.xprint("获取到的手机验证码为：" + styr);
			setSessionAttr(Const.MOBILE_CHECK_CODE, styr + "#" + mobile);
			map.put(MessageKey.apicode.name(), ResponseCode.CommSuccess.getCode());
		} else {
			map.put(MessageKey.apicode.name(), ResponseCode.SendSMSCodeError.getCode());
		}

		map.put(MessageKey.msg.name(), "");
		responseWriter(map, resp);
	}

	// 验证时间
	private boolean checkSendSmsTime(String mobile) {
		boolean temp = false;
		long curTime = System.currentTimeMillis();
		Cache<String, Date> sendSMSCache = getSendSMSCache();
		Date lateTime = sendSMSCache.get(mobile);
		if (lateTime != null) {
			long period = curTime - lateTime.getTime();
			CPSUtil.xprint("period=" + period);
			if (period < 1 * 60 * 1000) {
				temp = false;
			} else {
				temp = true;
			}
		} else {
			temp = true;
		}

		if (temp) {
			sendSMSCache.put(mobile, new Date());
		}
		return temp;
	}

	/**
	 * 验证短信验证码
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/code/check")
	public void checkSMSCode(HttpServletRequest req, HttpServletResponse resp) {

		// 获取参数
		Dto paramDto = getParamAsDto();
		String mobile = paramDto.getAsString("mobile");// 手机号
		String vcode = paramDto.getAsString("vcode");// 验证码
		// 缓存验证码
		String ccode = (String) req.getSession().getAttribute(Const.MOBILE_CHECK_CODE);

		// 判断参数
		if (!StringHelper.checkParameter(mobile, vcode)) {
			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), resp);
			return;
		}

		// 判断验证码
		if (CPSUtil.isNotEmpty(vcode)) {
			// 截取手机号和验证码
			String strs[] = ccode.split("\\#");
			if (strs != null && strs.length == 2) {
				String code = strs[0];
				String num = strs[1];
				if (!num.equals(mobile)) {
					responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), resp);
					return;
				} else if (!code.equals(vcode)) {
					// 设置一个token验证
					responseWriter(ResponseCode.responseMessage(ResponseCode.SMSCodeError), resp);
					return;
				} else {
					responseWriter(ResponseCode.responseMessage(ResponseCode.CommSuccess), resp);
					return;
				}
			} else {
				responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), resp);
				return;
			}
		}

	}

}
