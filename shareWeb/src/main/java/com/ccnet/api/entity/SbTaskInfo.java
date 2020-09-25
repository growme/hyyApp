package com.ccnet.api.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

/**
 * 开宝箱
 * 
 * @author JackieWang
 *
 */
public class SbTaskInfo extends BaseEntity {

	private static final long serialVersionUID = -816377637368446262L;

	private Integer id;
	private Integer userId;
	private String taskType;
	private Double taskMoney;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public Double getTaskMoney() {
		return taskMoney;
	}

	public void setTaskMoney(Double money) {
		this.taskMoney = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
