package com.ccnet.cps.entity;

import com.ccnet.core.entity.BaseEntity;

public class SbColumnType extends BaseEntity{
	/**
	 * 栏目类型实体类
	 */
	private static final long serialVersionUID = 1L;
	private String columnTypeId;
	private String columnTypeName;
	public String getColumnTypeId() {
		return columnTypeId;
	}
	public void setColumnTypeId(String columnTypeId) {
		this.columnTypeId = columnTypeId;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	
}
