package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.PassMethodType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 入口跳转地址
 * 
 * @author jackieWang
 * 
 */
public class SbForwardLink extends BaseEntity {

	private static final long serialVersionUID = 4404340126449381254L;

	private Integer linkId;
	private String linkAddr;
	private Integer state;
	@IgnoreTableField
	private String stateName;
	private Date updateTime;
	private Integer orderNo;
	// 绕过机制
	private Integer passMethod;
	@IgnoreTableField
	private String passMethodName;
	
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

	public Integer getPassMethod() {
		return passMethod;
	}

	public void setPassMethod(Integer passMethod) {
		this.passMethod = passMethod;
	}

	public String getPassMethodName() {
		if (CPSUtil.isNotEmpty(getPassMethod())) {
			PassMethodType psType = PassMethodType.getPassMethodType(getPassMethod());
			if (psType != null) {
				passMethodName = psType.getTypeName();
			}
		}
		return passMethodName;
	}

	public void setPassMethodName(String passMethodName) {
		this.passMethodName = passMethodName;
	}

}
