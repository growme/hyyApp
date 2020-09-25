package com.ccnet.core.common.utils.qiniu.auth;

import com.ccnet.core.common.UZoneType;
import com.ccnet.core.common.utils.CPSUtil;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 7牛用户授权验证
 * @author jackie wang
 *
 */
public class UserAuthManager {
	
	/**
	 * 普通授权
	 * @param accessKey
	 * @param secretKey
	 * @param bucketName
	 * @return token
	 */
	public static String getAuthToken(String accessKey,String secretKey,String bucketName) {
		return getUserAuth(accessKey, secretKey).uploadToken(bucketName);
	}
	
	
	/**
	 * 获取授权对象
	 * @param accessKey
	 * @param secretKey
	 * @return
	 */
	public static Auth getUserAuth(String accessKey,String secretKey) {
		return Auth.create(accessKey, secretKey);
	}
	
	
	/**
	 * 带回调路径授权
	 * @param accessKey
	 * @param secretKey
	 * @param bucketName
	 * @param expires
	 * @param policy
	 * @return
	 */
	public static String callBackAuth(String accessKey,String secretKey,String bucketName,long expires,StringMap policy) {
		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucketName, null, expires, policy);
		return upToken;
	}
	
	/**
	 * 获取回调参数
	 * @param callbackUrl
	 * @param callbackBody
	 * @return
	 */
	public static StringMap getUserPolicy(String callbackUrl,String callbackBody) {
		return new StringMap().put("callbackUrl", callbackUrl).put("callbackBody", callbackBody);
	}
	
	/**
	 * 获取返回内容格式
	 * @return
	 */
	public static String getReturnBody() {
		return "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}";
	}
	
	/**
	 * 获取回调内容格式
	 * @return
	 */
	public static String getCallbackBody() {
		return "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}";
	}
	
	/**
	 * 获取区域
	 * @param zoneType
	 * @return
	 */
	public static Configuration getZConfig(UZoneType zoneType) {
		Zone zone = null;
		switch (zoneType.getType()) {
		case 0:
			zone = Zone.zone0(); 
			break;
		case 1:
			zone = Zone.zone1(); 
			break;
		case 2:
			zone = Zone.zone2(); 
			break;
		default:
			zone = Zone.autoZone(); 
			break;
		}
		CPSUtil.xprint("区域为："+zoneType.getName());
		return new Configuration(zone);
	}

}
