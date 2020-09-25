package com.ccnet.core.common;

import java.util.HashMap;
import java.util.Map;

public enum PositionType {
	top(1,"上"),
	down(2,"下"),
	left(3,"左"),
	right(4,"右");
	
	private Integer positionId;
	
	private String positionName;
	
	private PositionType(Integer positionId, String positionName) {
		this.positionId = positionId;
		this.positionName = positionName;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	
	public static Map getStateMap()
	{
		Map<Integer, String> map=new HashMap<Integer, String>();
		for(PositionType instance:values())
		{
			map.put(instance.getPositionId(), instance.getPositionName());
		}
		return map;
	}
	
}
