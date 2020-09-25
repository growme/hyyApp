package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 广告日志信息（指纹）
 * 
 * @author jackie
 * 
 */
public class SbAdvertStat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer adId;
	
	private Integer userId;
	
	/**
	 * 真实ip数
	 */
	private Integer realIpNum;
	
	/**
	 * ip数
	 */
	private Integer ipNum;

	private Date createTime;

	private Date updateTime;

	@IgnoreTableField
	private SbAdvertInfo advertInfo;

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

	public Integer getIpNum() {
		return ipNum;
	}

	public void setIpNum(Integer ipNum) {
		this.ipNum = ipNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public SbAdvertInfo getAdvertInfo() {
		return advertInfo;
	}

	public void setAdvertInfo(SbAdvertInfo advertInfo) {
		this.advertInfo = advertInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRealIpNum() {
		return realIpNum;
	}

	public void setRealIpNum(Integer realIpNum) {
		this.realIpNum = realIpNum;
	}
	
	
	

}
