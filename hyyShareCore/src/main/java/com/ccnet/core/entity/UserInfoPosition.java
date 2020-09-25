package com.ccnet.core.entity;

public class UserInfoPosition extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String userInfoId;
	
	private String posId;

	public UserInfoPosition() {
		super();
	}

	public UserInfoPosition(String userInfoId,String posId) {
		super();
		this.userInfoId = userInfoId;
		this.posId = posId;
	}

	public String getuserInfoId() {
		return userInfoId;
	}

	public void setuserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}
	
}
