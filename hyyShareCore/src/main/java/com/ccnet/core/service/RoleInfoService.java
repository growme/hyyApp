package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.common.RoleType;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.entity.RoleinfoMappingResources;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.entity.UserinfoMappingRole;
import com.ccnet.core.service.base.BaseService;

public interface RoleInfoService extends BaseService<RoleInfo>{

	/**
	 * 分页查询角色(多过滤)
	 * @param role
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findAllRoleByPage(RoleInfo role, Page<RoleInfo> page,Dto pdDto);
	
	/**
	 * 保存角色
	 * @param sp
	 * @return
	 */
	public boolean saveRoleInfo(RoleInfo role);
	
	/**
	 * 保存角色权限
	 * @param roleID
	 * @param resourceID
	 * @return
	 */
	public boolean saveRoleInfoResource(Integer roleID,String resourceID);
	
	/**
	 * 保存角色权限
	 * @param roleMappingResources
	 * @return
	 */
	public boolean saveUserinfoMappingRole(RoleinfoMappingResources roleMappingResources);
	
	/**
	 * 保存角色授权信息
	 * @param rmlist
	 * @return
	 */
	public boolean saveRoleResources(List<RoleinfoMappingResources> rmlist);
	
	/**
	 * 保存角色关联
	 * @param user_id 用户编号
	 * @param role_ids 多个逗号分割
	 * @return
	 */
	public boolean saveUserinfoMappingRole(Integer user_id,String role_ids);
	
	/**
	 * 查询指定类型角色
	 * @param tList
	 * @return
	 */
	public List<RoleInfo> queryRoleListByRoleType(List<RoleType> tList);
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public boolean editRoleInfo(RoleInfo role);
	
	/**
	 * 获取所有角色
	 * @param sp
	 * @return
	 */
	public List<RoleInfo> queryRoleList(RoleInfo role);
	
	/**
	 * 获取角色权限
	 * @param roleID
	 * @return
	 */
	public List<Resources> queryRoleResourcesByRoleID(Integer roleID);
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<RoleInfo> queryRoleList();
	
	/**
	 * 获取用户所有角色
	 * @param user_id
	 * @return
	 */
	public List<RoleInfo> queryUserRoleList(Integer user_id);
	
	/**
	 * 获取用户所有角色关系
	 * @param user_id
	 * @return
	 */
	public List<UserinfoMappingRole> queryUserRoleMappingList(Integer user_id);
	
	/**
	 * 获取用户所有权限
	 * @param list
	 * @return
	 */
	public List<RoleinfoMappingResources> queryRoleResourcesMappingList(List<RoleInfo> list);
	
	/**
	 * 获取单个角色
	 * @param role
	 * @return
	 */
	public RoleInfo findRoleInfo(RoleInfo role);
	
	/**
	 * 获取单个角色
	 * @param roleID
	 * @return
	 */
	public RoleInfo findRoleByID(Integer roleID);
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	public boolean trashRoleInfo(RoleInfo role);
	
	/**
	 * 批量删除角色
	 * @param role_ids
	 * @return
	 */
	public boolean trashRoleInfoList(String role_ids);
	
}
