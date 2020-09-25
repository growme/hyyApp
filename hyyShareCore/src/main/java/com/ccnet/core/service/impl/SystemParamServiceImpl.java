package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.SystemParamDao;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.service.SystemParamService;

@Service("systemParamService")
public class SystemParamServiceImpl extends BaseServiceImpl<SystemParams> implements SystemParamService {
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	/**
	 * 分页查询参数
	 * @param sp
	 * @param page
	 * @return
	 */
	public Page findSystemParamByPage(SystemParams sp, Page<SystemParams> page,String queryParam){
		return systemParamDao.findSystemParamByPage(sp, page,queryParam);
	}
	
	/**
	 * 保存参数
	 * @param sp
	 * @return
	 */
	public boolean saveSystemParam(SystemParams sp) {
		return systemParamDao.saveSystemParam(sp);
	}
	
	/**
	 * 批量保存参数
	 * @param spList
	 * @return
	 */
	public boolean updateSystemParamList(List<SystemParams> spList){
		return systemParamDao.updateSystemParamList(spList);
	}
	
	/**
	 * 修改参数
	 * @param sp
	 * @return
	 */
	public boolean editSystemParam(SystemParams sp){
		return systemParamDao.editSystemParam(sp);
	}
	
	
	/**
	 * 获取所有参数
	 * @return
	 */
	public List<SystemParams> queryParamList(){
		return systemParamDao.queryParamList(new SystemParams());
	}
	
	/**
	 * 根据字典key获取组
	 * @param param_key
	 * @return
	 */
	public SystemParams findSystemParamByKey(String param_key){
		SystemParams systemParams = new SystemParams();
		systemParams.setParamKey(param_key);
		return systemParamDao.findSystemParam(systemParams);
	}
	
	/**
	 * 删除参数
	 * @param param_key
	 * @return
	 */
	@Transactional
	public boolean trashSystemParam(String param_key){
		SystemParams systemParams = new SystemParams();
		systemParams.setParamKey(param_key);
		return systemParamDao.trashSystemParam(systemParams);
	}
	
	/**
	 * 批量删除参数
	 * @param param_keys
	 * @return
	 */
	@Transactional
	public boolean trashSystemParamList(String param_keys){
		boolean temp = false;
		SystemParams sp = null;
		String pkey[] = null;
		List<SystemParams> paramList = null;
		if(CPSUtil.isNotEmpty(param_keys)){
			paramList = new ArrayList<SystemParams>();
			pkey = param_keys.split(",");
			for (String p_key : pkey) {
				sp = new SystemParams();
				sp.setParamKey(p_key);
				paramList.add(sp);
			}
			temp = systemParamDao.trashSystemParamList(paramList);
		}
		return temp;
	}

	@Override
	protected BaseDao<SystemParams> getDao() {
		return this.systemParamDao;
	}
}
