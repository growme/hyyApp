package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 推广地址
 * 
 * @author jackieWang
 * 
 */
public class SbPromoteLink extends BaseEntity {

	private static final long serialVersionUID = -482031363233645751L;
	
	private Integer linkId;
	private String linkAddr;
	private Integer state;
	@IgnoreTableField
	private String stateName;
	private Date updateTime;
	private Integer orderNo;
	
	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

	public String getLinkAddr() {
		return linkAddr;
	}

	public void setLinkAddr(String linkAddr) {
		this.linkAddr = linkAddr;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getStateName() {
		if (CPSUtil.isNotEmpty(getState())) {
			DomainStateType dsType = DomainStateType.getStateType(getState());
			if (dsType != null) {
				stateName = dsType.getName();
			}
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
