package com.ccnet.cps.entity;

import java.util.Date;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class SbUserMoney extends BaseEntity {

	private static final long serialVersionUID = -2169967739074136181L;

	private Integer umId;

	private Integer userId;

	private Double tmoney;

	private Double profitsMoney;

	private Date lastProDate;

	private Date updateTime;

	private Integer gold;

	@IgnoreTableField
	private MemberInfo memberInfo;

	private Double pmoney;

	public Double getPmoney() {
		return pmoney;
	}

	public void setPmoney(Double pmoney) {
		this.pmoney = pmoney;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getUmId() {
		return umId;
	}

	public void setUmId(Integer umId) {
		this.umId = umId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getTmoney() {
		return tmoney;
	}

	public void setTmoney(Double tmoney) {
		this.tmoney = tmoney;
	}

	public Double getProfitsMoney() {
		return profitsMoney;
	}

	public void setProfitsMoney(Double profitsMoney) {
		this.profitsMoney = profitsMoney;
	}

	public Date getLastProDate() {
		return lastProDate;
	}

	public void setLastProDate(Date lastProDate) {
		this.lastProDate = lastProDate;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

}
