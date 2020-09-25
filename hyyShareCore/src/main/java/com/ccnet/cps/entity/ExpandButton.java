package com.ccnet.cps.entity;

import com.ccnet.core.entity.BaseEntity;

public class ExpandButton extends BaseEntity {

	private static final long serialVersionUID = 1L;
	// 展开按钮颜色
	private String buttonColor;
	// 当前显示按钮位置
	private String buttonIndex;
	// 是否显示 none or block
	private String dispayType;

	public String getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public String getButtonIndex() {
		return buttonIndex;
	}

	public void setButtonIndex(String buttonIndex) {
		this.buttonIndex = buttonIndex;
	}

	public String getDispayType() {
		return dispayType;
	}

	public void setDispayType(String dispayType) {
		this.dispayType = dispayType;
	}

}
