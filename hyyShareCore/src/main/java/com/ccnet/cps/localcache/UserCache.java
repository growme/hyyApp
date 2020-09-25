package com.ccnet.cps.localcache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;

public class UserCache {

	private static UserCache instance = new UserCache();
	private ConcurrentMap<String, UserDailyEntity> userCache = new ConcurrentHashMap<String, UserDailyEntity>();

	private UserCache() {
	}

	public static UserCache getInstance() {
		return instance;
	}

	public UserDailyEntity getUserCache(Integer userId) {
		return getUserCache(String.valueOf(userId));
	}
	
	public UserDailyEntity getUserCache(String userId) {
		userId = StringUtils.trimToEmpty(userId);
		UserDailyEntity userEntity = userCache.get(userId);
		if (userEntity == null) {
			synchronized (userCache) {
				userEntity = userCache.get(userId);
				if (userEntity == null) {
					userEntity = new UserDailyEntity(userId);
					userCache.put(userId, userEntity);
				}
			}
		}
		return userEntity;
	}
}
