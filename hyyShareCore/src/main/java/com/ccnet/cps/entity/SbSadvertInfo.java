package com.ccnet.cps.entity;

import java.util.Date;

public class SbSadvertInfo {
	/**
	 * 脚本广告
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 广告ID
	 */
	private Integer adId;
	/*
	 * 广告标题
	 */
	private String adName;
	/*
	 * 状态 0 无效 1 有效
	 */
	private Integer state;
	/*
	 * 排序编号
	 */
	private Integer sortNo;
	/*
	 * 备注信息
	 */
	private String remark;
	/*
	 * 发布人
	 */
	private Integer userId;
	/*
	 * 广告脚本
	 */
	private String adScript;
	/*
	 * 广告位置
	 */
	private String adPosition;
	/*
	 * 更新时间
	 */
	private Date createTime;
	
	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAdScript() {
		return adScript;
	}

	public void setAdScript(String adScript) {
		this.adScript = adScript;
	}

	public String getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(String adPosition) {
		this.adPosition = adPosition;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 存库前加密需要加密的参数
	 */
	public void encrypt() 
	{
		
	}
	
	/**
	 * 取库后解密被加密的参数
	 * ALTER TABLE `cpsshop`.`member_info` CHANGE `mobile` `mobile` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '联系电话', CHANGE `email` `email` VARCHAR(450) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '邮箱', CHANGE `qq` `qq` VARCHAR(450) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT 'qq号码', CHANGE `pay_account` `pay_account` VARCHAR(400) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '支付宝账号', CHANGE `account_name` `account_name` VARCHAR(200) CHARSET utf8 COLLATE utf8_general_ci NULL COMMENT '支付宝账号姓名'; 
	 */
	public void decrypt() 
	{
		
	}
	
}
