package com.ccnet.core.common;

public enum UZoneType {
	
	Z_HD("华东区", 0),
	Z_HN("华北区", 1),
	Z_HB("华南区", 2),
	Z_AUTO("自动选择", -1);
	
	private String name;
	private Integer type;
	private UZoneType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public static UZoneType getUZoneType(Integer type) {
		for (UZoneType zoneType : values()) {
			if (zoneType.getType().equals(type)) {
				return zoneType;
			}
		}
		return null;
	}
}
