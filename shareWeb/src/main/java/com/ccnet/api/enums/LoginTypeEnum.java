package com.ccnet.api.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 评论状态
 */
public enum LoginTypeEnum {
	密码登录(1), 短信验证码登录(2), 微信登录(3),;
	private int value;

	LoginTypeEnum(int value) {
		this.value = value;
	}

	private static Map<Integer, LoginTypeEnum> valueMap = new HashMap<Integer, LoginTypeEnum>();
	static {
		for (LoginTypeEnum _enum : LoginTypeEnum.values()) {
			valueMap.put(_enum.value, _enum);
		}
	}

	public Integer getValue() {
		return this.value;
	}

	public LoginTypeEnum getEnum(Integer value) {
		return valueMap.get(value);
	}

	public Map<Integer, LoginTypeEnum> getAllValueMap() {
		return valueMap;
	}

	public static LoginTypeEnum parse(Integer value) {
		return valueMap.get(value);
	}
}
