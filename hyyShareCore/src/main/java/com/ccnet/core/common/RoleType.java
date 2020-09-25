package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum RoleType {
	ADMIN_ROLE(0, "管理角色"), //管理角色
	BUSINESS_ROLE(1, "商户角色"), // 商户角色
	BUSINESS_SERVER_ROLE(2, "客服角色"), // 客服角色
	BUSINESS_FINACIAL_ROLE(3, "财务角色"); // 财务角色

	private Integer type;
	private String name;

	private RoleType(Integer type, String name) {
		this.type = type;
		this.name = name;
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
	
	/**
	 * 判断是否为管理角色
	 * @param type
	 * @return
	 */
	public static boolean isaAdminRoleType(Integer type) {
		return ADMIN_ROLE.getType().equals(type);
	}
	
	/**
	 * 获取业务角色类型
	 * @param filterRole 排除商户角色
	 * @return
	 */
	public static List<RoleType> allShopRole(boolean filterShopRole) {
		List<RoleType> roleTypes = new ArrayList<RoleType>(Arrays.asList(RoleType.values()));
		roleTypes.remove(RoleType.ADMIN_ROLE);
		if(filterShopRole){
			roleTypes.remove(RoleType.BUSINESS_ROLE);
		}
		return roleTypes;
	}
	
	public static RoleType parseRoleType(Integer type) {
		for (RoleType roleType : values()) {
			if (roleType.getType().equals(type)) {
				return roleType;
			}
		}
		return null;
	}

}
