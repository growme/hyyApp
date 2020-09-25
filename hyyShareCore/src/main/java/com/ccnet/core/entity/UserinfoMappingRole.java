package com.ccnet.core.entity;

/**
 * 用戶角色表
 * @author Jackie Wang
 */
public class UserinfoMappingRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private Integer roleId;

	public UserinfoMappingRole() {
	}

	public UserinfoMappingRole(Integer userId, Integer roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}