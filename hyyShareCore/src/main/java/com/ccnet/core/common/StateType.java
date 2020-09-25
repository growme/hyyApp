package com.ccnet.core.common;

import java.util.HashMap;
import java.util.Map;

public enum StateType {
	Valid(1,"是"),
	InValid(0,"否");
	
	private Integer state;
	private String name;
	
	private StateType(Integer state,String name) {
		this.state = state;
		this.name = name;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static StateType getStateType(Integer type) {
		for (StateType stateType : values()) {
			if (stateType.getState().equals(type)) {
				return stateType;
			}
		}
		return null;
	}
	
	public static Map  getStateMap()
	{
		Map<Integer,String> map=new HashMap<Integer,String>();
		for (StateType stateType : values())
		{
			map.put(stateType.getState(), stateType.getName());
		}
		return map;
	}
}
