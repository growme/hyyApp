package com.ccnet.cps.entity;

import java.util.Date;

public class DailyMoneyCount {
	private Integer userId; // 用户编号
	private Date moneyDate; // 结算日期
	private Double dailyMaxMoney; // 存允许的每日上限
	private Double dailyMoney; // 每日的实际计算金额
	private Integer settlement; // 用,0 ,1 来标示这天的钱是否已经结算。
	private Double actualMoney; // 实际产生金额

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getMoneyDate() {
		return moneyDate;
	}

	public void setMoneyDate(Date moneyDate) {
		this.moneyDate = moneyDate;
	}

	public Double getDailyMaxMoney() {
		return dailyMaxMoney;
	}

	public void setDailyMaxMoney(Double dailyMaxMoney) {
		this.dailyMaxMoney = dailyMaxMoney;
	}

	public Double getDailyMoney() {
		return dailyMoney;
	}

	public void setDailyMoney(Double dailyMoney) {
		this.dailyMoney = dailyMoney;
	}

	public Integer getSettlement() {
		return settlement;
	}

	public void setSettlement(Integer settlement) {
		this.settlement = settlement;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

}
