package com.ccnet.core.common.utils.sigar;
/**
 * 数据显示实体
 * 
 * @author jackie wang
 * 
 */
public class SigarInfoEntity {
	private String value;
	private String name;
	private String desc;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SigarInfoEntity(String value, String desc, String name) {
		super();
		this.value = value;
		this.name = name;
		this.desc = desc;
	}

	public SigarInfoEntity() {

	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}