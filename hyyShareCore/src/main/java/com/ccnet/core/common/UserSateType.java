package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.List;

public enum UserSateType {
	VALID("启用", 0),
	INVALID("冻结", 1),
	UNCHECK("待审核", 2);
	
	private String name;
	private Integer type;
	private UserSateType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public static UserSateType getUserSateType(Integer type) {
		for (UserSateType userSateType : values()) {
			if (userSateType.getType().equals(type)) {
				return userSateType;
			}
		}
		return null;
	}
	
	public static List<UserSateType> all() {
		List<UserSateType> stateList = new ArrayList<UserSateType>();
		for (UserSateType state : values()) {
			if (state.equals(UserSateType.UNCHECK)) {
				continue;
			}else{
				stateList.add(state);
			}
		}
		return stateList;
	}
}
