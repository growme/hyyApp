package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

public class SbVisitLog extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private Integer visitId;
	private String hashKey;
	private String contentTitle;
	private String loginAccount;
	private Date visitTime;
	private String reqDetail;

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getReqDetail() {
		return reqDetail;
	}

	public void setReqDetail(String reqDetail) {
		this.reqDetail = reqDetail;
	}

}
