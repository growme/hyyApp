package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

/**
 * 签到信息
 * @author JackieWang
 *
 */
public class SbSignInfo extends BaseEntity {

	private static final long serialVersionUID = -816377637368446262L;

	private Integer signId;
	private Integer userId;
	private Integer seriesCount;
	private Integer totalCount;
	private Double signMoney;//签到基金
	private Double totalMoney;//累签奖励
	private Date lastSignTime;

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSeriesCount() {
		return seriesCount;
	}

	public void setSeriesCount(Integer seriesCount) {
		this.seriesCount = seriesCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Double getSignMoney() {
		return signMoney;
	}

	public void setSignMoney(Double signMoney) {
		this.signMoney = signMoney;
	}

	public Date getLastSignTime() {
		return lastSignTime;
	}

	public void setLastSignTime(Date lastSignTime) {
		this.lastSignTime = lastSignTime;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

}
