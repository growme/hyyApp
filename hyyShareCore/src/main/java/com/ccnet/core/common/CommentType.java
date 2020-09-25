package com.ccnet.core.common;


public enum CommentType {

	zj(1,"震惊"),
	wy(2,"无语"),
	dz(3,"点赞"),
	gx(4,"搞笑"),
	bs(5,"鄙视");
	
	private Integer typeId;
	private String  typeName;

	private CommentType(Integer typeId, String typeName) {
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

	public static CommentType getCommentType(Integer typeId) {
		for (CommentType commentType : values()) {
			if (commentType.getTypeId().equals(typeId)) {
				return commentType;
			}
		}
		return null;
	}
}
