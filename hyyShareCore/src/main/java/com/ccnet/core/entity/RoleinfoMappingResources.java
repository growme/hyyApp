package com.ccnet.core.entity;

/**
 * 角色权限表
 * @author Jackie Wang
 */
public class RoleinfoMappingResources extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer roleId;
	private Integer resourceId;

	public RoleinfoMappingResources() {
		
	}

	public RoleinfoMappingResources(Integer roleId,Integer resourceId) {
		this.roleId = roleId;
		this.resourceId = resourceId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

}