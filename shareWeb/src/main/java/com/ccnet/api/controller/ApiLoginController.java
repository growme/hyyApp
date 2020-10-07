package com.ccnet.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.Params.LoninParams.LoginParam;
import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.LoginResult;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.enums.RedisKey;
import com.ccnet.api.service.ApiLoginService;
import com.ccnet.api.util.TokenUtil;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.MemberLoginLog;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.MemberLoginLogService;

/**
 * 会员登录api
 */
@Controller
@RequestMapping("/api/user/")
public class ApiLoginController extends BaseController<MemberInfo> {

	@Autowired
	ApiLoginService apiLoginService;
	@Autowired
	MemberInfoService infoService;
	@Autowired
	MemberLoginLogService memberLoginLogService;

	Map<String, Long> ipCache = new HashMap<String, Long>();

	@RequestMapping(value = "fetchVerifyCode", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> fetchVerifyCodeFor(@RequestParam(value = "phone", required = true) String phone) {
		if (StringUtils.isBlank(phone)) {
			return ResultDTO.ERROR(AppResultCode.手机号不能为空);
		}
		Long time = ipCache.get(phone);
		if (null == time) {
			ipCache.put(phone, System.currentTimeMillis());
		} else {
			// 小于1分钟禁止发送
			if (System.currentTimeMillis() - time < 60000) {
				return ResultDTO.ERROR(AppResultCode.请不要频繁发短信);
			} else {
				ipCache.put(phone, System.currentTimeMillis());
			}
		}
		apiLoginService.getValidVerifyCode(phone);
		return ResultDTO.OK();
	}

	/**
	 * 用户登录逻辑
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ulogin", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<LoginResult<MemberInfo>> userLogin(LoginParam loginParam, HttpServletRequest request,
			HttpServletResponse response) {
		CPSUtil.xprint("开始登陆用户账号：" + loginParam.getInvitedCode());
		ResultDTO<LoginResult<MemberInfo>> info = null;
		CPSUtil.xprint("开始登陆用户名称：" + loginParam.getLoginName());
		try {
			Map<String, Integer> map = new HashMap<>();
			if (!map.containsKey(loginParam.getInvitedCode())) {
				String identity = request.getHeader("identity");
				info = apiLoginService.userLogin(loginParam, identity);
				CPSUtil.xprint("完成登陆用户名称：" + loginParam.getLoginName() + "---" + loginParam.getInvitedCode());

				if (info.ok()) {
					// 记录登录日志
					Integer userId = info.getBody().getUserInfo().getMemberId();
					String loginIP = IPUtil.getIpAddr(getRequest());// 获取用户登录IP
					MemberLoginLog loginLog = new MemberLoginLog(userId, loginIP);
					loginLog.setRequestDetails(getRequest().getHeader("User-Agent"));
					memberLoginLogService.saveMemberLoginLog(loginLog);
				}
				map.put(loginParam.getInvitedCode(), 0);
			}
			CPSUtil.xprint("全部完成：" + loginParam.getLoginName() + "---" + loginParam.getInvitedCode());
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 用户退出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "loginOut", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> loginOut(HttpServletRequest request, HttpServletResponse response) {
		return ResultDTO.OK();
	}

	/**
	 * 获取用户信息
	 * 
	 * @param header
	 * @return
	 */
	@RequestMapping(value = "getUserInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<MemberInfo> getUserInfo(Headers header) {
		try {
			MemberInfo user = infoService.getUserByUserID(Integer.valueOf(header.getUserid()));
			if (user.getMemberId() == null) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			return ResultDTO.OK(user);
		} catch (Exception e) {
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
	}

	/**
	 * 绑定手机号
	 * 
	 * @param phone,verify_code
	 * @return
	 */
	@RequestMapping(value = "bindingPhone", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> bindingPhone(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "verify_code", required = true) String verify_code, HttpServletRequest request) {
		try {
			String token = request.getHeader("token");
			String userId = TokenUtil.getToken(token);
			MemberInfo user = infoService.getUserByUserID(Integer.valueOf(userId));
			if (user == null) {
				ResultDTO.ERROR(AppResultCode.用户不存在);
			}
			String cacheVerifyCode = JedisUtils.get(RedisKey.APP验证码.getValue() + phone);
			if (StringUtils.isBlank(cacheVerifyCode) || StringUtils.isBlank(verify_code)
					|| !cacheVerifyCode.equals(verify_code)) {
				return ResultDTO.ERROR(AppResultCode.验证码不正确);
			}
			if (infoService.findFormatByLoginName(phone) != null) {
				return ResultDTO.ERROR(AppResultCode.手机号已存在);
			}
			user.setMobile(phone);
			user.setLoginAccount(phone);
			infoService.editMemberInfo(user);
			return ResultDTO.OK();
		} catch (Exception e) {
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param phone,verify_code
	 * @return
	 */
	@RequestMapping(value = "updatePassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> updatePassword(@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "verify_code", required = true) String verify_code,
			@RequestParam(value = "password", required = true) String password, HttpServletRequest request) {
		String token = request.getHeader("token");
		String userId = TokenUtil.getToken(token);
		MemberInfo user = infoService.getUserByUserID(Integer.valueOf(userId));
		if (user == null) {
			ResultDTO.ERROR(AppResultCode.用户不存在);
		}
		String cacheVerifyCode = JedisUtils.get(RedisKey.APP验证码 + phone);
		if (StringUtils.isBlank(verify_code) || !cacheVerifyCode.equals(verify_code)) {
			ResultDTO.ERROR(AppResultCode.验证码不正确);
		}
		String newPassword = CipherUtil.generatePassword(password).toLowerCase();
		user.setLoginPassword(CipherUtil.createPwdEncrypt(phone, newPassword, user.getSalt()));
		user.setMobile(phone);
		user.setLoginAccount(phone);
		infoService.editMemberInfo(user);
		return ResultDTO.OK();
	}
	/**
	 * 重置密码
	 *
	 * @param phone,verify_code
	 * @return
	 */
	@RequestMapping(value = "resetPassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> resetPassword(@RequestParam(value = "phone", required = true) String phone,
									   @RequestParam(value = "verify_code", required = true) String verify_code,
									   @RequestParam(value = "password", required = true) String password, HttpServletRequest request) {
		String token = request.getHeader("token");
		String userId = TokenUtil.getToken(token);
		MemberInfo user = infoService.getUserByUserID(Integer.valueOf(userId));
		if (user == null) {
			ResultDTO.ERROR(AppResultCode.用户不存在);
		}
		String cacheVerifyCode = JedisUtils.get(RedisKey.APP验证码 + phone);
		if (StringUtils.isBlank(verify_code) || !cacheVerifyCode.equals(verify_code)) {
			ResultDTO.ERROR(AppResultCode.验证码不正确);
		}
		String newPassword = CipherUtil.generatePassword(password).toLowerCase();
		user.setLoginPassword(CipherUtil.createPwdEncrypt(phone, newPassword, user.getSalt()));
		user.setMobile(phone);
		user.setLoginAccount(phone);
		infoService.editMemberInfo(user);
		return ResultDTO.OK();
	}
	/**
	 * 设置密码
	 *
	 * @return
	 */
	@RequestMapping(value = "setPassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> updatePassword(
									   @RequestParam(value = "confirPassWord", required = true) String confirPassWord,
									   @RequestParam(value = "password", required = true) String password, HttpServletRequest request) {
		if (!password.equals(confirPassWord)){
			ResultDTO.ERROR(AppResultCode.密码不一致);
		}
		String token = request.getHeader("token");
		String userId = TokenUtil.getToken(token);
		MemberInfo user = infoService.getUserByUserID(Integer.valueOf(userId));
		if (user == null) {
			ResultDTO.ERROR(AppResultCode.用户不存在);
		}

		String newPassword = CipherUtil.generatePassword(password).toLowerCase();
		String mobile = user.getMobile();
		if (StringUtils.isEmpty(mobile)){
			ResultDTO.ERROR(AppResultCode.用户未绑定手机号 );
		}
		user.setLoginPassword(CipherUtil.createPwdEncrypt(mobile, newPassword, user.getSalt()));
		infoService.editMemberInfo(user);
		return ResultDTO.OK();
	}

	@RequestMapping(value = "memberInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> memberInfo(@RequestParam(value = "code", required = true) String code) {
		try {
			if (CPSUtil.isEmpty(code)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			String userId = TokenUtil.getToken(code);
			if (CPSUtil.isEmpty(userId)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			Map<String, Object> map = new HashMap<>();
			MemberInfo userInfo = infoService.getMemberGoldById(userId);
			map.put("memberId", userInfo.getMemberId());
			map.put("memberName", userInfo.getMemberName());
			map.put("memberIcon", userInfo.getMemberIcon());
			map.put("gold", userInfo.getShowColor());
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	@RequestMapping(value = "updateMemberInfoGold", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> updateMemberInfoGold(@RequestParam(value = "code", required = true) String code,
			@RequestParam(value = "gold", required = true) Integer gold) {
		try {
			if (CPSUtil.isEmpty(code)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			String userId = TokenUtil.getToken(code);
			int i = infoService.updateMemberGoldById(userId, gold);
			if (i > 0) {
				return ResultDTO.OK();
			} else {
				return ResultDTO.ERROR(BasicCode.逻辑错误);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}
}
