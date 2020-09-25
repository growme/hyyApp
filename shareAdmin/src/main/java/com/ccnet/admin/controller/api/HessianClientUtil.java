package com.ccnet.admin.controller.api;

import com.ccnet.core.common.utils.SpringWebContextUtil;

public class HessianClientUtil {
	
	/**
	 * 调用更新缓存接口
	 * @param key
	 * @return
	 */
	public static boolean updateCache(String key){
		UserInfoRemoteApiService userApi = (UserInfoRemoteApiService)SpringWebContextUtil.getBean("hessianClient");
		return userApi.reloadSystemCache(key);
	}

	/**
	 * 更新文章心跳
	 * @return
	 */
	public static boolean updateHeartBeatCache(){
		UserInfoRemoteApiService userApi = (UserInfoRemoteApiService)SpringWebContextUtil.getBean("hessianClient");
		return userApi.updateHeartBeatCache();
	}
	
	
}
