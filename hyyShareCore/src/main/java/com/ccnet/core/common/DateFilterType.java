package com.ccnet.core.common;


public enum DateFilterType {
	YESTERDAY(-1, "昨天"), //昨天
	TODAY(1, "今天"), //今天
	WEEK(7, "本周"), //本周
	MONTH(30, "本月");//本月

	private Integer type;
	private String name;

	private DateFilterType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 转换数据
	 * @param type
	 * @return
	 */
	public static DateFilterType parseUserType(Integer type) {
		for (DateFilterType dateType : values()) {
			if (dateType.getType().equals(type)) {
				return dateType;
			}
		}
		return null;
	}

}
