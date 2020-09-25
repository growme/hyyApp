package com.ccnet.core.common;

public enum ContentDomainType {
	TGYM("推广域名", 0),
	TZYM("跳转域名", 1),
	TGBY("推广备用", 2),
	TZBY("跳转备用", 3);
	
	private String name;
	private Integer type;
	private ContentDomainType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public static ContentDomainType getContentDomainType(Integer type) {
		for (ContentDomainType domainType : values()) {
			if (domainType.getType().equals(type)) {
				return domainType;
			}
		}
		return null;
	}
}
