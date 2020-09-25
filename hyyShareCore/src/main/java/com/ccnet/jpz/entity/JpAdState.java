package com.ccnet.jpz.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

public class JpAdState extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer adType;
	private Integer adWidth;
	private Integer adHeight;
	private Integer adId;
	private Date createDate;
	private String adSite;

	public String getAdSite() {
		return adSite;
	}

	public void setAdSite(String adSite) {
		this.adSite = adSite;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public Integer getAdWidth() {
		return adWidth;
	}

	public void setAdWidth(Integer adWidth) {
		this.adWidth = adWidth;
	}

	public Integer getAdHeight() {
		return adHeight;
	}

	public void setAdHeight(Integer adHeight) {
		this.adHeight = adHeight;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

}
