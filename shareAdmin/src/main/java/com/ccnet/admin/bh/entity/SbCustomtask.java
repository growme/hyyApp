package com.ccnet.admin.bh.entity;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class SbCustomtask extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String title; // 任务标题
	private String content; // 任务介绍
	private String integral; // 奖励名称
	private String url; // 任务图片
	private String buttontitle; // 按钮标题
	private Integer status; // 状态 0有效 1无效
	private Integer ordernum; //
	private Integer type; // 1日常 2高佣 3新手任务 4积分墙任务 5
	private String musttime; // 必须使用时长，否则不给于奖励
	private Double payIntegral; // 每次支付金币
	private Integer payNum; // 每日限定次数
	private Integer phonetype;
	@IgnoreTableField
	private Integer taskNum; // 剩余次数
	private String target;// 跳转方式 blank新窗口 self本窗口 空不跳转
	private String icon;//图标

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getButtontitle() {
		return buttontitle;
	}

	public void setButtontitle(String buttontitle) {
		this.buttontitle = buttontitle;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMusttime() {
		return musttime;
	}

	public void setMusttime(String musttime) {
		this.musttime = musttime;
	}

	public Double getPayIntegral() {
		return payIntegral;
	}

	public void setPayIntegral(Double payIntegral) {
		this.payIntegral = payIntegral;
	}

	public Integer getPayNum() {
		return payNum;
	}

	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}

	public Integer getPhonetype() {
		return phonetype;
	}

	public void setPhonetype(Integer phonetype) {
		this.phonetype = phonetype;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}

}
