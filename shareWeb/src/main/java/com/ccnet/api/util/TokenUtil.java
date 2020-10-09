package com.ccnet.api.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ccnet.api.util.AESUtils;
import com.ccnet.core.common.utils.redis.JedisUtils;

public class TokenUtil {

	private final static int CACHE_TOKEN_PHONE_TIME_OUT = 30 * 24 * 60 * 60 * 1000;

	public static final String BASEUSER_TOKEN_PREFIX = "horiz:user:token:";

	public static Map<String, String> createToken(String id) {

		Map<String, String> map = new HashMap<String, String>();

		int nowTime = (int) System.currentTimeMillis();

		// 有效期，毫秒
		int validPeriod = 0;
		validPeriod = CACHE_TOKEN_PHONE_TIME_OUT;

		int expireAt = nowTime + validPeriod;

		String key = BASEUSER_TOKEN_PREFIX + id;
		String token = getValue(id);
		JedisUtils.set(key, token, validPeriod);
		map.put("token", token);
		/* 系统当前时间 */
		map.put("nowTime", String.valueOf(nowTime));
		/* 过期时长(毫秒) */
		map.put("validPeriod", String.valueOf(validPeriod));
		/* 失效时间点(毫秒) */
		map.put("expireAt", String.valueOf(expireAt));
		return map;
	}

	public boolean checkToken(String id, String authentication) {
		try {
			if (id == null) {
				return false;
			}
			String key = getKey(id);
			String redisValue = JedisUtils.get(key);
			if (redisValue == null || !redisValue.equals(authentication)) {
				return false;
			}
			// 有效期，毫秒
			int validPeriod = CACHE_TOKEN_PHONE_TIME_OUT;
			JedisUtils.set(key, redisValue, validPeriod);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String getToken(String authentication) {
		// f717ccfbaa0c441584f4f0260a2f83ab:1:25:18632004830:1
		authentication = AESUtils.decrypt(authentication);
		if (StringUtils.isBlank(authentication)) {
			return null;
		}
		return authentication.split(":")[0];
	}

	public static boolean deleteToken(String key) {
		try {
			JedisUtils.del("token:" + key);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 生成 加密后的token
	 * 
	 * @param baseUser
	 * @return
	 */
	public static String getValue(String key) {
		key=key+":"+System.currentTimeMillis();
		return AESUtils.encrypt(key);
	}

	public static String getKey(String userid) {

		StringBuffer key = new StringBuffer();
		key.append(BASEUSER_TOKEN_PREFIX);
		key.append(":");
		key.append(userid);
		return key.toString();
	}

	public static void main(String[] args) {
		String str = getValue("120");
		String userid = getToken(str);
		System.out.println(str+"======"+userid);
	}
}
