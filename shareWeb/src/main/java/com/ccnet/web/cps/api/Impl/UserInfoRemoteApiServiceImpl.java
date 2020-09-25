package com.ccnet.web.cps.api.Impl;

import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.web.cps.api.UserInfoRemoteApiService;

public class UserInfoRemoteApiServiceImpl implements UserInfoRemoteApiService {

	private static final long serialVersionUID = 546636342021763138L;

	/**
	 * 更新系统指定缓存
	 */
	public boolean reloadSystemCache(String paramKey) {
		
		CPSUtil.xprint("hession调用成功...");
		
		if(CPSUtil.isEmpty(paramKey)){
		   return false;
		}else{
		   return InitSystemCache.updateCache(paramKey);
		}
	}
	

}
