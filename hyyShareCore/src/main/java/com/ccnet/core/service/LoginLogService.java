package com.ccnet.core.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.LoginLog;

public interface LoginLogService {

	/**
	 * 分页查询日志
	 * @param lg
	 * @param page
	 * @return
	 */
	public Page findLoginLogByPage(LoginLog lg, Page<LoginLog> page,String queryParam);
	
	/**
	 * 分页查询日志(多过滤)
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findLoginLogByPage(LoginLog lg, Page<LoginLog> page,Dto pdDto);
	
	/**
	 * 保存日志
	 * @param o
	 */
	public void saveLoginLog(LoginLog o);

	/**
	 * 删除日志
	 * @param log_id
	 * @return
	 */
	public boolean trashLoginLog(String log_id);
	
	/**
	 * 批量删除日志
	 * @param log_ids
	 * @return
	 */
	public boolean trashLoginLogList(String log_ids);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateLoginLog();
}
