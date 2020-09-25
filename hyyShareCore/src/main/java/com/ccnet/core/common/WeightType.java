package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum WeightType {

	TOP("置顶", 1), HITHPRICE("高价", 2), COMMON("普通", 0);

	private String name;
	private Integer type;

	private WeightType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Integer getType() {
		return type;
	}

	public static WeightType getUserSateType(Integer type) {
		for (WeightType userSateType : values()) {
			if (userSateType.getType().equals(type)) {
				return userSateType;
			}
		}
		return null;
	}

	public static Map getStateMap() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (WeightType stateType : values()) {
			map.put(stateType.getType(), stateType.getName());
		}
		return map;
	}

	public static List<WeightType> all() {
		List<WeightType> stateList = new ArrayList<WeightType>();
		for (WeightType state : values()) {
			if (state.equals(WeightType.COMMON)) {
				continue;
			} else {
				stateList.add(state);
			}
		}
		return stateList;
	}
}
