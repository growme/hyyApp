package com.ccnet.core.common;

/**
 * 分享类型
 * @author Jackie
 *
 */
public enum ShareType {
	WXHY("微信好友", 0),
	WXPYQ("微信朋友圈", 1),
	WXQ("微信群", 2);
	
	private String name;
	private Integer type;
	private ShareType(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	
	public Integer getType() {
		return type;
	}
	
	public static ShareType getShareType(Integer type) {
		for (ShareType shareType : values()) {
			if (shareType.getType().equals(type)) {
				return shareType;
			}
		}
		return null;
	}
	
}
