package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.entity.RoleinfoMappingResources;
import com.ccnet.core.entity.UserinfoMappingRole;

@Repository("roleDao")
public class RoleInfoDao extends BaseDao<RoleInfo>{
	
	/**
	 * 分页查询角色(多过滤)
	 * @param role
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<RoleInfo> findAllRoleByPage(RoleInfo role, Page<RoleInfo> page, Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, RoleInfo.class, role);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (role_name like ?  or role_desc like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//加上排序
		sql.append(" order by create_time desc , order_number asc ");
		super.queryPager(sql.toString(), new BeanListHandler<RoleInfo>(RoleInfo.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 保存角色
	 * @param sp
	 * @return
	 */
	public boolean saveRoleInfo(RoleInfo role) {
		return insert(role, "roleId")>0;
	}
	
	/**
	 * 保存角色权限
	 * @param roleID
	 * @param resourceID
	 * @return
	 */
	public boolean saveRoleInfoResource(Integer roleID,Integer resourceID) {
		RoleinfoMappingResources rmResources = new RoleinfoMappingResources();
		rmResources.setRoleId(roleID);
		rmResources.setResourceId(resourceID);
		return saveUserinfoMappingRole(rmResources);
	}
	
	/**
	 * 保存角色授权信息
	 * @param rmlist
	 * @return
	 */
	public boolean saveRoleResources(List<RoleinfoMappingResources> rmlist){
		return memory.create(RoleinfoMappingResources.class, rmlist).length > 0;
	}
	
	/**
	 * 保存角色权限
	 * @param roleMappingResources
	 * @return
	 */
	public boolean saveUserinfoMappingRole(RoleinfoMappingResources roleMappingResources) {
		return memory.create(RoleinfoMappingResources.class, roleMappingResources)>0;
	}
	
	/**
	 * 保存角色关联
	 * @param sp
	 * @return
	 */
	public boolean saveUserinfoMappingRole(UserinfoMappingRole userRole) {
		return memory.create(UserinfoMappingRole.class, userRole)>0;
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public boolean editRoleInfo(RoleInfo role) {
		return update(role, "roleId")>0;
	}
	
	/**
	 * 获取所有角色
	 * @param sp
	 * @return
	 */
	public List<RoleInfo> queryRoleList(RoleInfo role){
		return findList(role);
	}
	
	/**
	 * 获取用户所有角色
	 * @param user_id
	 * @return
	 */
	public List<UserinfoMappingRole> queryUserRoleMappingList(Integer user_id){
		StringBuffer sql = new StringBuffer("select * from userinfo_mapping_role where user_id=?");
		return memory.query(sql.toString(), new BeanListHandler<UserinfoMappingRole>(UserinfoMappingRole.class), user_id);
	}
	
	/**
	 * 检查用户所有角色
	 * @param user_id
	 * @param role_id
	 * @return
	 */
	public boolean checkUserRoleMappingExist(Integer user_id,Integer role_id){
		StringBuffer sql = new StringBuffer("select * from userinfo_mapping_role where user_id=? and role_id=? ");
		List<UserinfoMappingRole> rmList = memory.query(sql.toString(), new BeanListHandler<UserinfoMappingRole>(UserinfoMappingRole.class), user_id,role_id);
		if(CPSUtil.listNotEmpty(rmList)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取用户所有权限
	 * @param user_id
	 * @return
	 */
	public List<RoleinfoMappingResources> queryRoleResourcesMappingList(Integer role_id){
		StringBuffer sql = new StringBuffer("select * from roleinfo_mapping_resources where role_id=?");
		return memory.query(sql.toString(), new BeanListHandler<RoleinfoMappingResources>(RoleinfoMappingResources.class), role_id);
	}
	
	/**
	 * 获取用户所有权限
	 * @param list
	 * @return
	 */
	public List<RoleinfoMappingResources> queryRoleResourcesMappingList(List<RoleInfo> list) {
		List<RoleinfoMappingResources> rmList = null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from roleinfo_mapping_resources");
		List<Object> values = memory.getValuesFromField(list, "roleId");
		memory.in(sql, params, "where", "role_id", values);
		if(CPSUtil.listNotEmpty(values)){
			rmList = memory.query(sql, new BeanListHandler<RoleinfoMappingResources>(RoleinfoMappingResources.class), params);
		}
		return rmList;
	}
	
	/**
	 * 获取指定类型角色
	 * @param typeIds
	 * @return
	 */
	public List<RoleInfo> queryRoleListByTypeIds(List<Integer> typeIds) {
		List<RoleInfo> rmList = null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from role_info");
		memory.in(sql, params, "where", "role_type", typeIds);
		if(CPSUtil.listNotEmpty(typeIds)){
			rmList = memory.query(sql, new BeanListHandler<RoleInfo>(RoleInfo.class), params);
		}
		return rmList;
	}
	
	
	/**
	 * 获取用户所有角色
	 * @param user_id
	 * @return
	 */
	public List<RoleInfo> queryUserRoleList(Integer user_id){
		List<RoleInfo> rolelist= new ArrayList<RoleInfo>();
		List<UserinfoMappingRole> rmList = queryUserRoleMappingList(user_id);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("select * from role_info ");
		List<Object> values = memory.getValuesFromField(rmList, "roleId");
		memory.in(sql, params, "where", "role_id", values);
		if(CPSUtil.listNotEmpty(values)){
		  rolelist = memory.query(sql, new BeanListHandler<RoleInfo>(RoleInfo.class), params);
		}
		return rolelist;
	}
	
	
	/**
	 * 获取单个角色
	 * @param role
	 * @return
	 */
	public RoleInfo findRoleInfo(RoleInfo role) {
		return find(role);
	}
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	public boolean trashRoleInfo(RoleInfo role) {
		return delete(role)>0;
	}
	
	/**
	 * 批量删除角色
	 * @param list
	 * @return
	 */
	public boolean trashRoleInfoList(List<RoleInfo> list) {
		return deleteBatch(list).length>=0 && deleteBatch(list).length==list.size();
	}
	
	/**
	 * 删除用户角色
	 * @param list
	 * @return
	 */
	public boolean trashUserRoleList(List<RoleInfo> list,Integer userId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("delete from userinfo_mapping_role where 1=1 ");
		if(userId!=null){
			sql.append(" and user_id=? ");
			params.add(userId);
		}
		List<Object> values = memory.getValuesFromField(list, "roleId");
		memory.in(sql, params, "and ", "role_id", values);
		return memory.update(sql, params)>=0;
	}
	
	/**
	 * 删除用户角色
	 * @param userId
	 * @return
	 */
	public boolean trashUserRoleList(Integer userId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("delete from userinfo_mapping_role where 1=1 ");
		if(userId!=null){
			sql.append(" and user_id=? ");
			params.add(userId);
		}
		return memory.update(sql, params)>=0;
	}
	
	
	/**
	 * 删除角色权限
	 * @param list
	 * @return
	 */
	public boolean trashRoleFuncList(List<RoleInfo> list) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("delete from roleinfo_mapping_resources");
		List<Object> values = memory.getValuesFromField(list, "roleId");
		memory.in(sql, params, "where", "role_id", values);
		return memory.update(sql, params)>=0;
	}
	
}
