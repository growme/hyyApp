package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 支付日志表
 * 
 * @author zqy
 * 
 */
public class SbPayLog extends BaseEntity {

	private static final long serialVersionUID = 7646175951530900869L;

	private Integer payId; // 日志id
	private String payAccount; // 支付宝账号
	private String accountName; // 账号姓名
	private String alipayCode; // 支付流水号
	private Date payTime; // 付款时间
	private String operater; // 付款人
	private Date createTime; // 日志创建时间
	private String remark; // 备注
	private double payMoney; // 支付金额
	private Integer ucId; // 提现编号
	@IgnoreTableField
	private SbCashLog cashLog;

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
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

	public String getAlipayCode() {
		return alipayCode;
	}

	public void setAlipayCode(String alipayCode) {
		this.alipayCode = alipayCode;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
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

	public double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getUcId() {
		return ucId;
	}

	public void setUcId(Integer ucId) {
		this.ucId = ucId;
	}

	public SbCashLog getCashLog() {
		return cashLog;
	}

	public void setCashLog(SbCashLog cashLog) {
		this.cashLog = cashLog;
	}

}
