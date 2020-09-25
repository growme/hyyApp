package com.ccnet.api.enums;

import java.util.HashMap;
import java.util.Map;

public enum CertificateType {
	ID("1", "身份证"), passport("2", "护照"), charter("3", "营业执照");

	private String id;
	private String name;

	private CertificateType(String id, String name) {
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

	public static CertificateType getCertificateType(String type) {
		for (CertificateType CertificateType : values()) {
			if (CertificateType.getid().equals(type)) {
				return CertificateType;
			}
		}
		return null;
	}

	// 获取支付类型
	public static Map<String, String> getCertificateType() {
		Map<String, String> map = new HashMap<String, String>();
		for (CertificateType CertificateType : values()) {
			map.put(CertificateType.getid(), CertificateType.getname());
		}
		return map;
	}
}
