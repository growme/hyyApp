package com.ccnet.core.common;


/**
 * 绕过机制
 * @author jackieWang
 *
 */
public enum PassMethodType {

	//dxx(1,"单斜线","/"),
	//sxx(2,"双斜线","//"),
	//txx(3,"三斜线","///"),
	//fxx(4,"四斜线","////"),
	yxh(5,"@符号","@"),
	//fxg(6,"反斜线","\\"),
	//sfxg(7,"双反斜线","\\\\"),
	jh(8,"#符号","#"),
	wh(9,"?符号","?");
	//ipv4(10,"IPV4地址","IPV4"),
	//ipv6(11,"IPV6地址","IPV6");
	
	private Integer typeId;
	private String  typeName;
	private String  typeValue;

	private PassMethodType(Integer typeId, String typeName, String typeValue) {
		this.typeId = typeId;
		this.typeName = typeName;
		this.typeValue = typeValue;
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
	
	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public static PassMethodType getPassMethodType(Integer typeId) {
		for (PassMethodType passType : values()) {
			if (passType.getTypeId().equals(typeId)) {
				return passType;
			}
		}
		return null;
	}
}
