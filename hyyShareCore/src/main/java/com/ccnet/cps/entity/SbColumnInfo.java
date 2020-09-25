package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;
import com.ccnet.core.entity.UserInfo;

public class SbColumnInfo extends BaseEntity {

	/**
	 * 栏目实体类
	 */
	private static final long serialVersionUID = 1L;

	private Integer columnId;
	private String columnName;
	private Integer orderNo;
	private Date createTime;
	private Integer userId;
	@IgnoreTableField
	private UserInfo userInfo;
	private Integer enabled;
	@IgnoreTableField
	private String enabledName;

	private Integer columnType;
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getColumnType() {
		return columnType;
	}

	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setEnabledName(String enabledName) {
		this.enabledName = enabledName;
	}

	public String getEnabledName() {
		if (CPSUtil.isNotEmpty(enabled)) {
			enabledName = StateType.getStateType(enabled).getName();
		}
		return enabledName;
	}

	public SbColumnInfo() {
		super();
	}

	public SbColumnInfo(Integer columnId) {
		this();
		setColumnId(columnId);
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
