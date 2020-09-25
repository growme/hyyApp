package com.ccnet.core.common.utils.base;

public enum ResourceTypes {
	MENU("菜单", 0),
	BUTTON("按钮", 1),
	FUNC("功能", 2);
	
	private String name;
	private Integer type;
	private ResourceTypes(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public String getStrType() {
		return String.valueOf(type);
	}

	public static ResourceTypes getResourceTypes(Integer type) {
		for (ResourceTypes resourceTypes : values()) {
			if (resourceTypes.getType().equals(type)) {
				return resourceTypes;
			}
		}
		return null;
	}
	
}
