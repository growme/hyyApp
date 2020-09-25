package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;
import cn.ffcs.memory.ColumnHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
/**
 * 资源数据层
 */
@Repository("resourcesDao")
public class ResourcesDao extends BaseDao<Resources>{
	
	/**
	 * 分页查询资源
	 * @param rs
	 * @param page
	 * @return
	 */
	public Page<Resources> findResourcesByPage(Resources rs, Page<Resources> page,Dto pdDto) {
		
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("state");
		String parent_code = pdDto.getAsString("parent_id");
		String menu_type = pdDto.getAsString("menu_type");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, Resources.class, rs);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			if(sql.toString().indexOf("where")<0){
			   sql.append(" where 1=1 ");
			}
			sql.append(" and (resource_name like ?  or resource_code like ? or alisa_code like ?) ");
			params.add("%" + queryParam.trim() + "%");
			params.add( queryParam.trim() + "%");
			params.add("%" + queryParam.trim() + "%");
		}
		
		if(CPSUtil.isNotEmpty(parent_code)){
        	appendWhere(sql);
        	sql.append(" and resource_code like ? ");
        	params.add( parent_code + "%");
		}
		
		if(CPSUtil.isNotEmpty(state)){
        	appendWhere(sql);
        	sql.append(" and resource_state =? ");
			params.add(state);
		}
        
        if(CPSUtil.isNotEmpty(menu_type)){
        	appendWhere(sql);
        	sql.append(" and resource_type =? ");
			params.add(menu_type);
		}
		
		//加上排序
		sql.append(" order by resource_code asc ");
		super.queryPager(sql.toString(), new BeanListHandler<Resources>(Resources.class), params, page);
		return page;
	}
	
	/**
	 * 获取资源列表
	 * @param rs
	 * @return
	 */
	public List<Resources> queryMenuList(Resources rs){
		return findList(rs);
	}
	
	
	/**
	 * 根据ID获取资源
	 * @param resId
	 */
	public Resources queryResourceByID(Integer resId){
		Resources resources = new Resources();
		resources.setResourceId(resId);
		return find(resources);
	}
	
	/**
	 * 根据Code获取资源
	 * @param resCode
	 */
	public Resources queryResourceByCode(String resCode){
		Resources resources = new Resources();
		resources.setResourceCode(resCode);
		return find(resources);
	}
	
	/**
	 * 根据ID获取菜单集合
	 * @param resourceIds
	 * @return
	 */
	public List<Resources> getResourceListByIds(List<Integer> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return new ArrayList<Resources>(0);
		}
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "resource_id", resourceIds);
		return memory.query(sql, new BeanListHandler<Resources>(Resources.class), params);
	}
	
	/**
	 * 转换集合数据
	 * @param resourceIds
	 * @return
	 */
	public Map<Integer, Resources> getResourcesMapByIds(List<Integer> resourceIds) {
		List<Resources> resources = getResourceListByIds(resourceIds);
		Map<Integer, Resources> map = new HashMap<Integer, Resources>(0);
		if (CollectionUtils.isNotEmpty(resources)) {
			for (Resources resource : resources) {
				map.put(resource.getResourceId(), resource);
			}
		}
		return map;
	}
	
	
	/**
	 * 根据Code获取菜单集合
	 * @param resourceIds
	 * @return
	 */
	public List<Resources> getResourceListByCodes(List<String> resourceCodes) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "resource_code", resourceCodes);
		return memory.query(sql, new BeanListHandler<Resources>(Resources.class), params);
	}
	
	/**
	 * 转换集合数据
	 * @param resourceCodes
	 * @return
	 */
	public Map<String, Resources> getResourcesMapByCodes(List<String> resourceCodes) {
		List<Resources> resources = getResourceListByCodes(resourceCodes);
		Map<String, Resources> map = new HashMap<String, Resources>(0);
		if (CollectionUtils.isNotEmpty(resources)) {
			for (Resources resource : resources) {
				map.put(resource.getResourceCode(), resource);
			}
		}
		return map;
	}
	
	/**
	 * 根据上级Code获取最大下级Code
	 * @param parent_id
	 * @return
	 */
	public String getMaxSubMenuCode(String parent_id) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select max(resource_code) from ").append(getCurrentTableName());
		if(CPSUtil.isNotEmpty(parent_id)){
			sql.append(" where parent_code =? ");
			params.add(parent_id);
		}
		return memory.query(sql, new ColumnHandler<String>(String.class), params);
		
	}
	
	
	/**
	 * 根据层级唯一码
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getResourcesByLevelCode(String levelCode){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		if(CPSUtil.isNotEmpty(levelCode)){
			sql.append(" where level_code = ?");
			params.add(levelCode);
		}
		return memory.query(sql, new BeanListHandler<Resources>(Resources.class), params);
	}
	
	/**
	 * 根据code或所有下级
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getChildResourcesByCode(String resourcesCode){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		if(CPSUtil.isNotEmpty(resourcesCode)){
			sql.append(" where resource_code like ? and resource_code <> ? ");
			params.add(resourcesCode+"%");
			params.add(resourcesCode);
		}
		return memory.query(sql, new BeanListHandler<Resources>(Resources.class), params);
	}
	
	/**
	 * 根据code或所有下级
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getChildsResourcesByCode(String resourcesCode){
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		if(CPSUtil.isNotEmpty(resourcesCode)){
			sql.append(" where resource_code like ? ");
			params.add(resourcesCode+"%");
		}
		return memory.query(sql, new BeanListHandler<Resources>(Resources.class), params);
	}
	
	
	/**
	 * 保存资源
	 * @param resources
	 * @return
	 */
	public boolean saveResources(Resources resources) {
		if(insert(resources, "resourceId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改资源
	 * @param resources
	 * @return
	 */
	public boolean editResources(Resources resources) {
		if(update(resources, "resourceId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除资源
	 * @param resources
	 * @return
	 */
	public boolean trashResources(Resources resources) {
		if(delete(resources)>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 批量删除资源
	 * @param list
	 * @return
	 */
	public boolean trashResourcesList(List<Resources> list){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("delete from resources");
		List<Object> values = memory.getValuesFromField(list, "resourceId");
		memory.in(sql, params, "where", "resource_id", values);
		return memory.update(sql, params)>=0;
	}
	
	
	/**
	 * 删除资源权限
	 * @param list
	 * @return
	 */
	public boolean trashResourceFuncList(List<Resources> list){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("delete from roleinfo_mapping_resources");
		List<Object> values = memory.getValuesFromField(list, "resourceId");
		memory.in(sql, params, "where", "resource_id", values);
		return memory.update(sql, params)>=0;
	}
	
	/**
	 * 删除资源权限
	 * @param list
	 * @return
	 */
	public boolean trashResourceFunc(Resources resources) {
		List<Resources> list = new ArrayList<Resources>();
		list.add(resources);
		return trashResourceFuncList(list);
	}
	
}
