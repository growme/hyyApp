package com.ccnet.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum CoceralAuditState {
	dsh("0", "待审核"), ytg("1", "已通过"), btg("2", "不通过");

	private String id;
	private String name;

	private CoceralAuditState(String id, String name) {
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

	public static CoceralAuditState getCoceralAuditState(String type) {
		for (CoceralAuditState CoceralAuditState : values()) {
			if (CoceralAuditState.getid().equals(type)) {
				return CoceralAuditState;
			}
		}
		return null;
	}

	// 获取支付类型
	public static Map<String, String> getCoceralAuditState() {
		Map<String, String> map = new HashMap<String, String>();
		for (CoceralAuditState CoceralAuditState : values()) {
			map.put(CoceralAuditState.getid(), CoceralAuditState.getname());
		}
		return map;
	}
}
