package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 登录日志表
 * 
 * @author Jackie Wang
 */
public class LoginLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userId;
	private Date loginTime;
	private String loginIp;
	private String ipLocation;
	private String requestDetails; // 登陆请求的参数
	@IgnoreTableField
	private String userName;
	@IgnoreTableField
	private String loginAccount;
	@IgnoreTableField
	private String loginToken;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public LoginLog() {
	}

	public LoginLog(Integer userId, String loginIp) {
		this.userId = userId;
		this.loginIp = loginIp;
	}

	public LoginLog(Integer id, Integer userId, Date loginTime, String loginIp) {
		this.id = id;
		this.userId = userId;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRequestDetails() {
		return requestDetails;
	}

	public void setRequestDetails(String requestDetails) {
		this.requestDetails = requestDetails;
	}

	public String getIpLocation() {
		/*
		 * if(this.ipLocation == null && CPSUtil.isNotEmpty(getLoginIp())){
		 * ipLocation = IPLocationUtil.getIpLocation(getLoginIp()); }
		 */
		return ipLocation;
	}

	public void setIpLocation(String ipLocation) {
		this.ipLocation = ipLocation;
	}

}
