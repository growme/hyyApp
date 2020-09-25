package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.service.base.BaseService;

public interface ResourcesService extends BaseService<Resources>{
	
	/**
	 * 分页查询资源(多过滤)
	 * @param res
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findResourcesByPage(Resources res, Page<Resources> page,Dto pdDto);
	
	/**
	 * 根据ID获取资源
	 * @param resId
	 */
	public Resources queryResourceByID(Integer resId);
	
	/**
	 * 根据Code获取资源
	 * @param resCode
	 */
	public Resources queryResourceByCode(String resCode);
	
	/**
	 * 根据上级Code获取最大下级Code
	 * @param parent_id
	 * @return
	 */
	public String getMaxSubMenuCode(String parent_id);
	
	/**
	 * 根据层级唯一码
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getResourcesByLevelCode(String levelCode);
	
	/**
	 * 根据code或所有下级
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getChildResourcesByCode(String resourcesCode);
	
	/**
	 * 判断资源是否存在
	 * @param levelCode
	 * @return
	 */
	public boolean checkMenuExist(String levelCode);
	
	/**
	 * 保存资源
	 * @param resources
	 * @return
	 */
	public boolean saveResources(Resources resources);
	
	/**
	 * 修改资源
	 * @param resources
	 * @return
	 */
	public boolean editResources(Resources resources);
	
	/**
	 * 删除资源
	 * @param resources
	 * @return
	 */
	public boolean trashResources(Resources resources);
	
	/**
	 * 删除资源
	 * @param resID
	 * @return
	 */
	public boolean trashResources(String resID);
	
	/**
	 * 删除资源权限
	 * @param list
	 * @return
	 */
	public boolean trashResourceFuncList(List<Resources> list);
	
	/**
	 * 删除资源权限
	 * @param list
	 * @return
	 */
	public boolean trashResourceFunc(Resources resources);
	
	 /**
     * 菜单树
     * @param userId 用户Id
     * @return
     */
	public List<Resources> findResources(Integer userId);
	

	/**
	 * 获取资源列表
	 * @param rs
	 * @return
	 */
	public List<Resources> queryMenuList(Resources rs);
	
	
	/**
	 * 获取用户制定类型资源
	 * @param userID 用户编号
	 * @param type 资源类型
	 * @return
	 */
	public List<Resources> findUserAuthResourcesList(Integer userID,Integer type);
	
	/**
	 * 获取用户指定类型资源
	 * @param userID 用户编号
	 * @param menuCode 用户编号
	 * @param type 资源类型
	 * @return
	 */
	public List<Resources> findUserAuthResourcesList(Integer userID,String menuCode,Integer type);
	
}
