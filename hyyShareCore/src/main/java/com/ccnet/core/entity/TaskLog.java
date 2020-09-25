package com.ccnet.core.entity;

import java.util.Date;

public class TaskLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String logId;
	/** 日志名称 */
	private String logName;
	/** 类名 */
	private String className;
	/** 日志类型 1：正常，2:异常 */
	private Integer logType;
	/** 日志描述 */
	private String description;
	/** 创建时间 */
	private Date createTime;

	public TaskLog() {
	}

	public TaskLog(String logId, String logName, String className,
			Integer logType, String description, Date createTime) {
		super();
		this.logId = logId;
		this.logName = logName;
		this.className = className;
		this.logType = logType;
		this.description = description;
		this.createTime = createTime;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getLogType() {
		return logType;
	}

	public void setLogType(Integer logType) {
		this.logType = logType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
