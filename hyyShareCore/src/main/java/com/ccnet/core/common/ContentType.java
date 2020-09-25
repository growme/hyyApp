package com.ccnet.core.common;

import java.util.HashMap;
import java.util.Map;

public enum ContentType {
	Common(0,"图文"),
	Video (1,"视频"),
	Article (2,"小说"),
	NK (3,"男科"),
	LB (4,"裂变");
	
	private Integer state;
	private String name;
	
	private ContentType(Integer state,String name) {
		this.state=state;
		this.name=name;
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
	
	public static ContentType getContentType(Integer type) {
		for (ContentType contentType : values()) {
			if (contentType.getState().equals(type)) {
				return contentType;
			}
		}
		return null;
	}
	
	public static Map getStateMap()
	{
		Map<Integer, String> map=new HashMap<Integer, String>();
		for(ContentType instance:values())
		{
			map.put(instance.getState(), instance.getName());
		}
		return map;
	}
}
