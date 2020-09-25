package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 提现记录
 * 
 * @author Administrator
 * 
 */
public class SbCashLog extends BaseEntity {

	private static final long serialVersionUID = 4640168198768289960L;

	private Integer ucId;
	private Double cmoney;
	private String payAccount;
	private String accountName;
	private Integer payType;
	@IgnoreTableField
	private String typeName;
	private Date createTime;
	private Date updateTime;
	private Integer state;
	@IgnoreTableField
	private String stateName;
	@IgnoreTableField
	private String showColor;
	private Integer userId;
	@IgnoreTableField
	private MemberInfo memberInfo;
	@IgnoreTableField
	private MemberInfo parentInfo;
	private String remark;
	private Integer withdrawType;

	public Integer getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(Integer withdrawType) {
		this.withdrawType = withdrawType;
	}

	public Integer getUcId() {
		return ucId;
	}

	public void setUcId(Integer ucId) {
		this.ucId = ucId;
	}

	public Double getCmoney() {
		return cmoney;
	}

	public void setCmoney(Double cmoney) {
		this.cmoney = cmoney;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	public MemberInfo getParentInfo() {
		return parentInfo;
	}

	public void setParentInfo(MemberInfo parentInfo) {
		this.parentInfo = parentInfo;
	}

}
