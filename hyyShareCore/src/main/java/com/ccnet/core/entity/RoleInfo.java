package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 角色信息表
 * 
 * @author Jackie Wang
 */
public class RoleInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer roleId;
	private String roleName;
	private String roleDesc;
	private Integer orderNumber;
	private Integer roleType;
	private Date createTime;
	private Date updateTime;
	private Integer state;
	@IgnoreTableField
	private boolean useState;//是否选中
	@IgnoreTableField
	private String stateName;
	@IgnoreTableField
	private String typeName;
	
	public boolean isUseState() {
		return useState;
	}

	public void setUseState(boolean useState) {
		this.useState = useState;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public RoleInfo() {
	}

	public RoleInfo(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleInfo(Integer roleId, String roleName, String roleDesc,
			Integer orderNumber, Integer roleType) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.orderNumber = orderNumber;
		this.roleType = roleType;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getRoleType() {
		return this.roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

}