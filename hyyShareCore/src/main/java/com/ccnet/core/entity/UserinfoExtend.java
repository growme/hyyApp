package com.ccnet.core.entity;

import java.util.Date;

/**
 * 用戶扩展表
 * 
 * @author Jackie Wang
 */
public class UserinfoExtend extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer userId;
	private Integer loginCount;
	private Date lastLoginTime;
	private String lastLoginIp;

	public UserinfoExtend() {
	}

	public UserinfoExtend(Integer userId, Integer loginCount) {
		this.userId = userId;
		this.loginCount = loginCount;
	}

	public UserinfoExtend(Integer userId, Integer loginCount,
			Date lastLoginTime, String lastLoginIp) {
		this.userId = userId;
		this.loginCount = loginCount;
		this.lastLoginTime = lastLoginTime;
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getLoginCount() {
		return this.loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

}