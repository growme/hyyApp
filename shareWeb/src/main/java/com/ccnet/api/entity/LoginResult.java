package com.ccnet.api.entity;

import java.io.Serializable;

/**
 * 登录返回实体
 * @param <T>
 */
@SuppressWarnings("serial")
public class LoginResult<T> implements Serializable{
	
	
	private String token;
	
	/**
	 * 登录时间（豪秒）
	 */
	private Long loginTime;
	
	/**
	 * 过期时长（豪秒）
	 */
	private Long validPeriod;
	
	/**
	 * 失效时间点（豪秒）
	 */
	private Long expireAt;
	
	private T userInfo;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public Long getValidPeriod() {
		return validPeriod;
	}

	public void setValidPeriod(Long validPeriod) {
		this.validPeriod = validPeriod;
	}

	public Long getExpireAt() {
		return expireAt;
	}

	public void setExpireAt(Long expireAt) {
		this.expireAt = expireAt;
	}

	public T getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(T userInfo) {
		this.userInfo = userInfo;
	}

}
