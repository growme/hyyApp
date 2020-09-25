package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.ResourcesDao;
import com.ccnet.core.dao.impl.RoleInfoDao;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.entity.RoleinfoMappingResources;
import com.ccnet.core.service.ResourcesService;


@Service("resourcesService")
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService {

	@Autowired
	protected ResourcesDao resourcesDao;
	@Autowired
	protected RoleInfoDao roleInfoDao;
	/**
	 * 分页查询资源(多过滤)
	 * @param res
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findResourcesByPage(Resources res, Page<Resources> page,Dto pdDto){
		Page menuPage = resourcesDao.findResourcesByPage(res, page, pdDto);
		List<Resources> rsList = menuPage.getResults();
		if(CPSUtil.listNotEmpty(rsList)){
			Resources resc = null;
			List<String> resourceCodes = resourcesDao.getValuesFromField(rsList, "resourceCode");
			String prcode = pdDto.getAsString("pt");
			resourceCodes.add(prcode);
			Map<String, Resources> userMap = resourcesDao.getResourcesMapByCodes(resourceCodes);
			for (Resources resu : rsList) {
				//获取上级名称
				resc = userMap.get(resu.getParentCode());
				if(CPSUtil.isNotEmpty(resc)){
				   resu.setParentName(resc.getResourceName());
				}
				//处理状态和类型
				resu.setStateName(CPSUtil.getCodeName("MENU_STATE", resu.getResourceState()+""));
				resu.setTypeName(CPSUtil.getCodeName("MENU_TYPE", resu.getResourceType()+""));
				//处理展开和节点类型
				resu.setExpandedName(CPSUtil.getCodeName("EXPANDED", resu.getExpanded()+""));
				resu.setLeafName(CPSUtil.getCodeName("LEAF_TYPE", resu.getIsleaf()+""));
			}
		}
		return menuPage;
	}
	
	/**
	 * 获取资源列表
	 * @param rs
	 * @return
	 */
	public List<Resources> queryMenuList(Resources rs){
		return resourcesDao.queryMenuList(rs);
	}
	
	/**
	 * 根据ID获取资源
	 * @param resId
	 */
	public Resources queryResourceByID(Integer resId){
		return resourcesDao.queryResourceByID(resId);
	}
	
	/**
	 * 根据Code获取资源
	 * @param resCode
	 */
	public Resources queryResourceByCode(String resCode){
		return resourcesDao.queryResourceByCode(resCode);
	}
	
	/**
	 * 根据上级Code获取最大下级Code
	 * @param parent_id
	 * @return
	 */
	public String getMaxSubMenuCode(String parent_id){
		return resourcesDao.getMaxSubMenuCode(parent_id);
	}
	
	
	/**
	 * 根据层级唯一码
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getResourcesByLevelCode(String levelCode){
		return resourcesDao.getResourcesByLevelCode(levelCode);
	}
	
	/**
	 * 判断资源是否存在
	 * @param levelCode
	 * @return
	 */
	public boolean checkMenuExist(String levelCode){
		boolean temp = false;
		List<Resources> resList = resourcesDao.getResourcesByLevelCode(levelCode);
		if(!CPSUtil.listEmpty(resList)){
			temp = true;
		}
		return temp;
	}
	
	/**
	 * 根据code或所有下级
	 * @param levelCode
	 * @return
	 */
	public List<Resources> getChildResourcesByCode(String resourcesCode){
		return resourcesDao.getChildResourcesByCode(resourcesCode);
	}
	
	
	/**
	 * 保存资源
	 * @param resources
	 * @return
	 */
	public boolean saveResources(Resources resources){
		return resourcesDao.saveResources(resources);
	}
	
	/**
	 * 修改资源
	 * @param resources
	 * @return
	 */
	public boolean editResources(Resources resources){
		return resourcesDao.editResources(resources);
	}
	
	/**
	 * 删除资源
	 * @param resources
	 * @return
	 */
	public boolean trashResources(Resources resources){
		return resourcesDao.trashResources(resources);
	}
	
	/**
	 * 删除资源
	 * @param resID
	 * @return
	 */
	public boolean trashResources(String resID){
		String res_id [] = null;
		Resources resources = null;
		List<Resources> rlist = null;
		List<Resources> clist = null;
		if(CPSUtil.isNotEmpty(resID)){
			res_id = resID.split(",");
			rlist = new ArrayList<Resources>();
			clist = new ArrayList<Resources>();
			for (String rid : res_id) {
				resources = new Resources();
				resources.setResourceId(Integer.valueOf(rid));
				resources = resourcesDao.find(resources);
				if(CPSUtil.isNotEmpty(resources.getResourceCode())){
				  clist = resourcesDao.getChildsResourcesByCode(resources.getResourceCode());
				  rlist.addAll(clist);
				}
			}
           resourcesDao.trashResourceFuncList(rlist);
           resourcesDao.trashResourcesList(rlist); 
		}
       return true;
	}
	
	/**
	 * 删除资源权限
	 * @param list
	 * @return
	 */
	public boolean trashResourceFuncList(List<Resources> list) {
		return resourcesDao.trashResourceFuncList(list);
	}
	
	/**
	 * 删除资源权限
	 * @param list
	 * @return
	 */
	public boolean trashResourceFunc(Resources resources){
		return resourcesDao.trashResourceFunc(resources);
	}
	
	
	/**
	 * 批量删除资源
	 * @param list
	 * @return
	 */
	public boolean trashResourcesList(List<Resources> list){
		return resourcesDao.trashResourcesList(list);
	}
	
	/**
	 * 获取用户的菜单权限
	 * @param userID
	 * @return
	 */
	@Override
	public List<Resources> findResources(Integer userID) {
		List<Integer> resIds = null;
		List<Resources> resList = null;
		List<RoleinfoMappingResources> rmList = null;
		List<RoleInfo> roleInfos = roleInfoDao.queryUserRoleList(userID);
		if(CPSUtil.listNotEmpty(roleInfos)){
			rmList = roleInfoDao.queryRoleResourcesMappingList(roleInfos);
			resIds = resourcesDao.getValuesFromField(rmList, "resourceId");
			resList = resourcesDao.getResourceListByIds(resIds);
		}
		return resList;
	}
	
	/**
	 * 获取用户指定类型资源
	 * @param userID 用户编号
	 * @param type 资源类型
	 * @return
	 */
	public List<Resources> findUserAuthResourcesList(Integer userID, Integer type) {
		 List<Resources> resList = new ArrayList<Resources>();
		 List<Resources> resources = findResources(userID);
		 if (CPSUtil.listNotEmpty(resources)) {
			for (Resources resources2 : resources) {
				if (resources2.getResourceType() == type) {
					resList.add(resources2);
				}
			}
		 }
		 return resList;
	}
	

	/**
	 * 获取用户指定类型资源
	 * @param userID 用户编号
	 * @param menuCode 用户编号
	 * @param type 资源类型
	 * @return
	 */
	public List<Resources> findUserAuthResourcesList(Integer userID, String menuCode, Integer type) {
		 List<Resources> resList = new ArrayList<Resources>();
		 List<Resources> resources = findResources(userID);
		 if(CPSUtil.listNotEmpty(resources)){
			 for (Resources res2 : resources) {
				if (res2.getResourceType() == type
						&& res2.getParentCode().equals(menuCode)) {
					resList.add(res2);
				}
			}
		 }
		 return resList;
	}
 
	@Override
	protected BaseDao<Resources> getDao() {
		// TODO Auto-generated method stub
		return resourcesDao;
	}

}
