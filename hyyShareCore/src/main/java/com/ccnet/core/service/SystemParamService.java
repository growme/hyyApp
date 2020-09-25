package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.service.base.BaseService;

public interface SystemParamService extends BaseService<SystemParams> {
	
	/**
	 * 分页查询参数
	 * @param sp
	 * @param page
	 * @return
	 */
	public Page findSystemParamByPage(SystemParams sp, Page<SystemParams> page,String queryParam);
	
	/**
	 * 获取所有参数
	 * @return
	 */
	public List<SystemParams> queryParamList();
	
	/**
	 * 保存参数
	 * @param sp
	 * @return
	 */
	public boolean saveSystemParam(SystemParams sp);
	
	/**
	 * 批量保存参数
	 * @param spList
	 * @return
	 */
	public boolean updateSystemParamList(List<SystemParams> spList);
	
	/**
	 * 修改参数
	 * @param sp
	 * @return
	 */
	public boolean editSystemParam(SystemParams sp);
	
	/**
	 * 获取参数
	 * @param param_key
	 * @return
	 */
	public SystemParams findSystemParamByKey(String param_key);
	
	/**
	 * 删除参数
	 * @param param_key
	 * @return
	 */
	public boolean trashSystemParam(String param_key);
	
	/**
	 * 批量删除参数
	 * @param param_keys
	 * @return
	 */
	public boolean trashSystemParamList(String param_keys);

}
