package com.ccnet.web.cps.api;

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

}
