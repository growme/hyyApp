package com.ccnet.admin.bh.common;

public enum OrderStatus {

	wcl(0, "未处理"), cg(1, "成功"), sb(3, "失败"), jj(4, "拒绝");
	private Integer typeId;
	private String typeName;

	private OrderStatus(Integer typeId, String typeName) {
		this.typeId = typeId;
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public static OrderStatus getCommentType(Integer typeId) {
		for (OrderStatus commentType : values()) {
			if (commentType.getTypeId().equals(typeId)) {
				return commentType;
			}
		}
		return null;
	}
}
