package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

public class SbContentAdvertClick extends BaseEntity {

	private static final long serialVersionUID = -8048173460256115702L;

	private Integer id;
	private Integer adId;
	private Integer userId;
	private Integer contentId;
	private String token;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
