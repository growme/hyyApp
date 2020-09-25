package com.ccnet.api.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 城市分级
 * 
 * @author 朱施健
 *
 */
public enum RedisKey{
	文章内容("wz:article:content:"),
	站外网页SESSION("wz:outside:sessions:"),

	
	APP频道列表("wz:app:article:channels"),
	
	APP验证码("wz:app:login:verifyCode:"),
	
	APP任务列表("wz:app:task:custom:"),
	
	APP设备邀请预存("wz:app:invite:device:"),

	购买会员列表("wz:app:commodity:buy"), 
	
	提现金额列表("wz:app:commodity:withdraw"),;

	private String value;

	RedisKey(String value) {
		this.value = value;
	}

	private static Map<String, RedisKey> valueMap = new HashMap<String, RedisKey>();
	static {
		for (RedisKey _enum : RedisKey.values()) {
			valueMap.put(_enum.value, _enum);
		}
	}

	public String getValue() {
		return this.value;
	}

	public RedisKey getEnum(String value) {
		return valueMap.get(value);
	}

	public Map<String, RedisKey> getAllValueMap() {
		return valueMap;
	}

	public static RedisKey parse(String value) {
		return valueMap.get(value);
	}
}
