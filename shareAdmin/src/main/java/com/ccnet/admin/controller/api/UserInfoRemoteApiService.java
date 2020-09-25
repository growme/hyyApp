package com.ccnet.admin.controller.api;

import java.io.Serializable;

/**
 * 远程接口调用类
 * @author jackie wang
 *
 */
public interface UserInfoRemoteApiService extends Serializable {
	
	/**
	 * 更新缓存
	 * @param paramKey
	 * @return
	 */
	public boolean reloadSystemCache(String paramKey);
	
	/**
	 * 更新文章心跳
	 * @return
	 */
	public boolean updateHeartBeatCache();
	

}
