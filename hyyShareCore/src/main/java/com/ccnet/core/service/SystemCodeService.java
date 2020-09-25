package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.service.base.BaseService;

public interface SystemCodeService extends BaseService<SystemCode> {
	
	/**
	 * 分页查询字典参数
	 * @param sc
	 * @param page
	 * @return
	 */
	public Page findSystemCodeByPage(SystemCode sc, Page<SystemCode> page,String queryParam);
	
	/**
	 * 获取所有字典数据
	 * @return
	 */
	public List<SystemCode> queryCodeList();
	
	/**
	 * 根据字典key获取组
	 * @param code_key
	 * @return
	 */
	public List<SystemCode> queryCodeListByKey(String code_key);
	
	/**
	 * 获取字典
	 * @param code_id
	 * @return
	 */
	public SystemCode findSystemCodeByID(String code_id);
	
	/**
	 * 保存字典
	 * @param sc
	 * @return
	 */
	public boolean saveSystemCode(SystemCode sc);
	
	/**
	 * 修改字典
	 * @param sc
	 * @return
	 */
	public boolean editSystemCode(SystemCode sc);
	
	/**
	 * 删除字典
	 * @param code_id
	 * @return
	 */
	public boolean trashSystemCode(String code_id);
	
	/**
	 * 批量删除字典
	 * @param code_ids
	 * @return
	 */
	public boolean trashSystemCodeList(String code_ids);
	
}
