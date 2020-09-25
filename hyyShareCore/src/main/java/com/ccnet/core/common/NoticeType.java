package com.ccnet.core.common;

public enum NoticeType {
	
	SYSTEM_NOTICE("后台公告", 0),
	SITE_NOTICE("网站公告", 1);
	
	private String name;
	private Integer type;
	private NoticeType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public static NoticeType getNoticeType(Integer type) {
		for (NoticeType noticeType : values()) {
			if (noticeType.getType().equals(type)) {
				return noticeType;
			}
		}
		return null;
	}
}
