package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

public class MemberWxInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3018938731612231433L;
	private String openid;
	private String unionid;
	private String visitCode;
	private Date createDate;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(String visitCode) {
		this.visitCode = visitCode;
	}

}
