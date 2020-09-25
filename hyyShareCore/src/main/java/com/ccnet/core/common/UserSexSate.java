package com.ccnet.core.common;

public enum UserSexSate {
	MALE("男", 0),
	FEMALE("女", 1),
	UNKNOWN("未知", 2);
	
	private String name;
	private Integer type;
	private UserSexSate(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public static UserSexSate getUserSexSate(Integer type) {
		for (UserSexSate userSexSate : values()) {
			if (userSexSate.getType().equals(type)) {
				return userSexSate;
			}
		}
		return null;
	}
}
