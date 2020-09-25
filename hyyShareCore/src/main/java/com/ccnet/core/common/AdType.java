package com.ccnet.core.common;
import java.util.HashMap;
import java.util.Map;
public enum AdType {
	ADTYPE_TEXT(1,"纯文本广告","TEXT"),
	ADTYPE_ONLY_SINGLE_PIC(2,"单图广告","ONLY_SINGLE_PIC"),
    ADTYPE_SINGLE_PIC(3,"单图文广告","SINGLE_PIC"),
    ADTYPE_MORE_PIC(4,"多图文广告","MORE_PIC"),
	ADTYPE_BANNER_PIC(5,"横幅广告","BANNER_PIC"),
	ADTYPE_FLOAT_PIC(6,"漂浮广告","FLOAT_PIC");
 
    private Integer type;
	private String name;
	private String flag;

	private AdType(Integer type, String name,String flag) {
		this.type = type;
		this.name = name;
		this.flag = flag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static Map getStateMap() {
		Map<Integer, String> map=new HashMap<Integer, String>();
		for(AdType instance:values())
		{
			map.put(instance.getType(), instance.getName());
		}
		return map;
	}
	
	public static AdType getAdType(Integer type) {
		for (AdType advType : values()) {
			if (advType.getType().equals(type)) {
				return advType;
			}
		}
		return null;
	}
	
	public static AdType getAdTypeByFlag(String flag) {
		for (AdType advType : values()) {
			if (advType.getFlag().equals(flag)) {
				return advType;
			}
		}
		return null;
	}

}
