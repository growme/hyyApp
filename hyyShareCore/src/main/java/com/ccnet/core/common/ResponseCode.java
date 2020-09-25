package com.ccnet.core.common;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
	CommSuccess(1000,"数据处理成功"),
	CommError(1001,"数据处理失败"),
	XSSParamError(1007,"提交非法参数"),
	CaptchaEmptyError(1002,"验证码为空"),
	CaptchaError(1003,"验证码错误"),
	InvalidPhoneError(1004, "手机号不正确"),
	BuyNumError(1005,"客官您至少买一件吧"),
	NoParamError(1006,"提交参数错误"),
	InvalidAddressError(1007,"购买地址受限"),
	InvalidIPError(1008,"IP地址受限"),
	RepeatSubmitError(1009,"1分钟内不能重复获取短信验证码"),
	SaveOrderSuccess(1010,"恭喜您，订单提交成功"),
	SaveOrderError(1011,"订单提交失败，请稍后再试"),
	AccountExistError(1012,"账号已经存在"),
	AccountUnExistError(1013,"账号不存在"),
	PayAccountExistError(2001,"支付宝账号已经存在"),
	PayAccountUnExistError(2002,"支付宝账号不存在"),
	PhoneNumberExistError(1014, "手机号已存在"),
	RecomCodeUnExistError(1015, "邀请码不存在"),
	DataExistError(1016, "数据已经存在"),
	
	AccountPwdEmpty(1017, "账户和密码不能为空"),
	CheckCodeError(1018, "登录验证码错误"),
	AccountPwdError(1019, "登录账户或密码不正确"),
	LoginPwdError(1020, "登录密码不正确"),
	LoginAccInactive(1021, "登录账号已被冻结，请联系管理员"),
	MaxLoginNum(1022, "登录密码错误次数过多"),
	AuthLoginError(1022, "登录验证失败，请重新登录!"),
	LoginPwdUnSame(1023, "两次输入的密码不一致"),
	AuthTokenError(1024, "token验证失败"),
	
	DataUnExistError(1006, "数据不存在"),
	AccountNotMatchError(1025, "提现账号和当前登录账号不一致"),
	
	SMSCodeError(3001, "短信验证码错误"),
	SendSMSCodeError(3002, "发送短信验证码失败");
	
	private int code;
	private String desc;

	private ResponseCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
 
	public static ResponseCode parseResponseCode(int code) {
		for (ResponseCode responseCode : values()) {
			if (responseCode.getCode() == code) {
				return responseCode;
			}
		}
		return null;
	}
	
	public static Map<String, String> responseMessage(ResponseCode responseCode) {
		Map<String, String> map = new HashMap<String, String>(0);
		map.put(MessageKey.apicode.name(), String.valueOf(responseCode.getCode()));
		map.put(MessageKey.msg.name(), responseCode.getDesc());
		return map;
	}

}
