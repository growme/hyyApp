package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.RoleType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.ResourcesDao;
import com.ccnet.core.dao.impl.RoleInfoDao;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.entity.RoleinfoMappingResources;
import com.ccnet.core.entity.UserinfoMappingRole;
import com.ccnet.core.service.RoleInfoService;

@Service("RoleService")
public class RoleInfoServiceImpl extends BaseServiceImpl<RoleInfo> implements RoleInfoService{
	
	@Autowired
	private RoleInfoDao roleInfoDao; 
	@Autowired
	private ResourcesDao resourcesDao; 
	
	/**
	 * 分页查询角色(多过滤)
	 * @param role
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page findAllRoleByPage(RoleInfo role, Page<RoleInfo> page,Dto pdDto){
		Page rolePage = roleInfoDao.findAllRoleByPage(role, page, pdDto);
		List<RoleInfo> roleList = rolePage.getResults();
		if(!CPSUtil.checkListBlank(roleList)){
			for (RoleInfo roleInfo : roleList) {
				roleInfo.setStateName(CPSUtil.getCodeName("USER_STATE", roleInfo.getState()+""));
				if(CPSUtil.isNotEmpty(RoleType.parseRoleType(roleInfo.getRoleType()))){
					roleInfo.setTypeName(RoleType.parseRoleType(roleInfo.getRoleType()).getName());
				}
				//roleInfo.setTypeName(CPSUtil.getCodeName("ROLE_TYPE", roleInfo.getRoleType()+""));
			}
		}
		rolePage.setResults(roleList);
		return rolePage;
	}
	
	/**
	 * 保存角色
	 * @param sp
	 * @return
	 */
	public boolean saveRoleInfo(RoleInfo role){
		return roleInfoDao.saveRoleInfo(role);
	}
	
	/**
	 * 保存角色关联
	 * @param user_id 用户编号
	 * @param role_ids 多个逗号分割
	 * @return
	 */
	public boolean saveUserinfoMappingRole(Integer user_id,String role_ids){
		boolean temp = true;
		try {
			if(CPSUtil.isNotEmpty(user_id) && CPSUtil.isNotEmpty(role_ids)){
				//先删除用户角色关联
				temp = roleInfoDao.trashUserRoleList(user_id);
				if(temp){
					String role_id[] = role_ids.split(",");
					UserinfoMappingRole userRole = null;
					for (String rid : role_id) {
						userRole = new UserinfoMappingRole();
						userRole.setRoleId(Integer.valueOf(rid));
						userRole.setUserId(user_id);
						//if(!roleInfoDao.checkUserRoleMappingExist(user_id,Integer.valueOf(rid))){
					    roleInfoDao.saveUserinfoMappingRole(userRole);
						//}
					}
			    }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	
	/**
	 * 保存角色权限
	 * @param roleID
	 * @param resourceID
	 * @return
	 */
	public boolean saveRoleInfoResource(Integer roleID,String resourceID){
		boolean temp = false;
		try {
			if(CPSUtil.isNotEmpty(roleID) && CPSUtil.isNotEmpty(resourceID)){
				//先获取角色已经拥有的资源
				trashRoleFunInfoList(String.valueOf(roleID));
				//新增角色资源
				RoleinfoMappingResources resource = null;
				String resourceIds [] = resourceID.split(","); 
				List<RoleinfoMappingResources> rmlist = new ArrayList<RoleinfoMappingResources>();
				for (String res_id : resourceIds) {
					resource = new RoleinfoMappingResources();
					resource.setResourceId(Integer.valueOf(res_id));
					resource.setRoleId(roleID);
					rmlist.add(resource);
				}
				temp = saveRoleResources(rmlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	/**
	 * 保存角色权限
	 * @param roleMappingResources
	 * @return
	 */
	public boolean saveUserinfoMappingRole(RoleinfoMappingResources roleMappingResources){
		return roleInfoDao.saveUserinfoMappingRole(roleMappingResources);
	}
	
	/**
	 * 保存角色授权信息
	 * @param rmlist
	 * @return
	 */
	public boolean saveRoleResources(List<RoleinfoMappingResources> rmlist){
		return roleInfoDao.saveRoleResources(rmlist);
	}
	
	/**
	 * 解析角色ID到集合
	 * @param role_ids
	 * @return
	 */
	public List<RoleInfo> getRoleListFromRoleID(String role_ids) {
		RoleInfo roleInfo = null;
		List<RoleInfo> rolelist = new ArrayList<RoleInfo>();
		String role_id[] = role_ids.split(",");
		for (String rid : role_id) {
			roleInfo = new RoleInfo();
			roleInfo.setRoleId(Integer.valueOf(rid));
			rolelist.add(roleInfo);
		}
		return rolelist;
	}
	
	/**
	 * 获取角色权限
	 * @param roleID
	 * @return
	 */
	public List<Resources> queryRoleResourcesByRoleID(Integer roleID){
		List<Integer> resIds = null;
		List<Resources> retList = null;
		List<RoleinfoMappingResources> rmList = roleInfoDao.queryRoleResourcesMappingList(roleID);
		if(CPSUtil.listNotEmpty(rmList)){
			resIds = roleInfoDao.getValuesFromField(rmList, "resourceId");
			retList = resourcesDao.getResourceListByIds(resIds);
		}
		return retList;
	}
	
	/**
	 * 获取用户所有权限
	 * @param list
	 * @return
	 */
	public List<RoleinfoMappingResources> queryRoleResourcesMappingList(List<RoleInfo> list){
		return roleInfoDao.queryRoleResourcesMappingList(list);
	}
	
	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	public boolean editRoleInfo(RoleInfo role){
		return roleInfoDao.editRoleInfo(role);
	}
	
	/**
	 * 获取所有角色
	 * @param sp
	 * @return
	 */
	public List<RoleInfo> queryRoleList(RoleInfo role){
		return roleInfoDao.queryRoleList(role);
	}
	
	/**
	 * 获取所有角色
	 * @param sp
	 * @return
	 */
	public List<RoleInfo> queryRoleList(){
		return queryRoleList(new RoleInfo());
	}
	
	/**
	 * 查询指定类型角色
	 * @param tList
	 * @return
	 */
	public List<RoleInfo> queryRoleListByRoleType(List<RoleType> tList){
		List<Integer> typeIds = new ArrayList<Integer>();
		if(CPSUtil.listNotEmpty(tList)){
			for (RoleType roleType : tList) {
				typeIds.add(roleType.getType());
			}
		}
		return roleInfoDao.queryRoleListByTypeIds(typeIds);
	}
	
	/**
	 * 获取用户所有角色
	 * @param user_id
	 * @return
	 */
	public List<RoleInfo> queryUserRoleList(Integer user_id){
		return roleInfoDao.queryUserRoleList(user_id);
	}
	
	/**
	 * 获取用户所有角色关系
	 * @param user_id
	 * @return
	 */
	public List<UserinfoMappingRole> queryUserRoleMappingList(Integer user_id){
		return roleInfoDao.queryUserRoleMappingList(user_id);
	}
	/**
	 * 获取单个角色
	 * @param role
	 * @return
	 */
	public RoleInfo findRoleInfo(RoleInfo role){
		return roleInfoDao.findRoleInfo(role);
	}
	
	/**
	 * 获取单个角色
	 * @param roleID
	 * @return
	 */
	public RoleInfo findRoleByID(Integer roleID){
		RoleInfo role = new RoleInfo();
		role.setRoleId(roleID);
		return roleInfoDao.findRoleInfo(role);
	}
	
	/**
	 * 删除角色
	 * @param role
	 * @return
	 */
	public boolean trashRoleInfo(RoleInfo role){
		return roleInfoDao.trashRoleInfo(role);
	}
	
	/**
	 * 批量删除角色
	 * @param role_ids
	 * @return
	 */
	public boolean trashRoleInfoList(String role_ids){
		boolean temp = false;
		RoleInfo role = null;
		String r_id[] = null;
		List<RoleInfo> roleList = null;
		if(CPSUtil.isNotEmpty(role_ids)){
			roleList = new ArrayList<RoleInfo>();
			r_id = role_ids.split(",");
			for (String roleid : r_id) {
				role = new RoleInfo();
				role.setRoleId(Integer.valueOf(roleid));
				roleList.add(role);
			}
			//删除角色权限资源
			roleInfoDao.trashRoleFuncList(roleList);
			roleInfoDao.trashUserRoleList(roleList,null);
			//删除角色信息
			temp = roleInfoDao.trashRoleInfoList(roleList);
		}
		return temp;
	}

	@Override
	protected BaseDao<RoleInfo> getDao() {
		// TODO Auto-generated method stub
		return this.roleInfoDao;
	}
	
	/**
	 * 批量删除角色权限
	 * @param role_ids
	 * @return
	 */
	public boolean trashRoleFunInfoList(String role_ids){
		boolean temp = false;
		RoleInfo role = null;
		String r_id[] = null;
		List<RoleInfo> roleList = null;
		if(CPSUtil.isNotEmpty(role_ids)){
			roleList = new ArrayList<RoleInfo>();
			r_id = role_ids.split(",");
			for (String roleid : r_id) {
				role = new RoleInfo();
				role.setRoleId(Integer.valueOf(roleid));
				roleList.add(role);
			}
			//删除角色权限资源
			roleInfoDao.trashRoleFuncList(roleList);
		}
		return temp;
	}
	
}
