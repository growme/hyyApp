package com.ccnet.core.common;

/**
 * 审核状态
 * ClassName: CheckStateType 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-10-26
 */
public enum CheckStateType {
	Checked(1,"已发布"),
	UnCheck(0,"待审核");
	
	private Integer state;
	private String name;
	
	private CheckStateType(Integer state,String name) {
		this.state = state;
		this.name = name;
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
	
	public static CheckStateType getStateType(Integer type) {
		for (CheckStateType stateType : values()) {
			if (stateType.getState().equals(type)) {
				return stateType;
			}
		}
		return null;
	}
	
}
