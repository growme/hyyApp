package com.ccnet.admin.bh.entity.video;

public class Action_list {

	private int action;
	private String desc;
	private Extra extra;

	public void setAction(int action) {
		this.action = action;
	}

	public int getAction() {
		return action;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}

	public Extra getExtra() {
		return extra;
	}
}
