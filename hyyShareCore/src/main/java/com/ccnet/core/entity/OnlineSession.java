package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.sms.IPLocationUtil;

/**
 * 在线会话
 * 
 * @author jackie wang
 * 
 */
public class OnlineSession extends BaseEntity {

	private static final long serialVersionUID = -1055337686131444647L;

	private String sessionId;// 会话ID
	private String loginAccount;// 登录账户
	private String loginIp;// 登录ip
	private String ipLocation; // ip归属地
	private Date lastAccessTime;// 最后操作时间
	private String userType;
	private boolean isOnline;// 是否在线
	private long timeout;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIpLocation() {
//		if(this.ipLocation == null && CPSUtil.isNotEmpty(getLoginIp())){
//			ipLocation = IPLocationUtil.getIpLocation(getLoginIp());
//		}
		return ipLocation;
	}

	public void setIpLocation(String ipLocation) {
		this.ipLocation = ipLocation;
	}

}
