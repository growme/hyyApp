package com.ccnet.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum HandleType {

	read("1", "阅读"), share("2", "分享"), comment("3", "评论"), click("4", "点赞"), collect("5", "收藏");

	private String id;
	private String name;

	private HandleType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public static HandleType getHandleType(String type) {
		for (HandleType HandleType : values()) {
			if (HandleType.getid().equals(type)) {
				return HandleType;
			}
		}
		return null;
	}

	// 获取支付类型
	public static Map<String, String> getHandleType() {
		Map<String, String> map = new HashMap<String, String>();
		for (HandleType HandleType : values()) {
			map.put(HandleType.getid(), HandleType.getname());
		}
		return map;
	}

}
