package com.ccnet.admin.bh.common;

public enum OrderComform {

	android("android", "安卓"), ios("ios", "ios");
	private String typeId;
	private String typeName;

	private OrderComform(String typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static OrderComform getCommentType(String typeId) {
		for (OrderComform commentType : values()) {
			if (commentType.getTypeId().equals(typeId)) {
				return commentType;
			}
		}
		return null;
	}
}
