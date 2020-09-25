package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.List;

public enum DomainStateType {
	Valid(0,"正常使用","域名可以正常访问"),
	ApiError(1,"接口异常","域名验证接口异常"),
	InValid(2,"已被封杀","域名在微信中无法访问"),
	ApiExpire(-1,"接口到期","API到期，请续费"),
	CheckFast(3,"频率过高","请求太频繁");
	
	private Integer state;
	private String name;
	private String desc;
	
	private DomainStateType(Integer state,String name,String desc) {
		this.state = state;
		this.name = name;
		this.desc = desc;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static DomainStateType getStateType(Integer type) {
		for (DomainStateType stateType : values()) {
			if (stateType.getState().equals(type)) {
				return stateType;
			}
		}
		return null;
	}
	
	//获取有效域名
	public static List<Integer> getValidateSate() {
		List<Integer> domainInfos = new ArrayList<Integer>();
		for (DomainStateType stateType : values()) {
			if (stateType.equals(DomainStateType.Valid)) {
				domainInfos.add(stateType.getState());
			}
		}
		return domainInfos;
	}
	
	//获取检测域名 除屏蔽域名外其他继续检测
	public static List<Integer> getCheckValidateSate() {
		List<Integer> domainInfos = new ArrayList<Integer>();
		for (DomainStateType stateType : values()) {
			if (!stateType.equals(DomainStateType.InValid)) {//排除已屏蔽域名
				domainInfos.add(stateType.getState());
			}
		}
		return domainInfos;
	}
	
}
