package com.ccnet.core.entity;


/**
 * 系统参数表
 * @author Jackie Wang
 */
public class SystemParams extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String paramKey;
	private String paramValue;
	private String paramDesc;

	public SystemParams() {
	}

	public SystemParams(String paramKey) {
		this.paramKey = paramKey;
	}

	public SystemParams(String paramKey, String paramValue, String paramDesc) {
		this.paramKey = paramKey;
		this.paramValue = paramValue;
		this.paramDesc = paramDesc;
	}

	public String getParamKey() {
		return this.paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return this.paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

}