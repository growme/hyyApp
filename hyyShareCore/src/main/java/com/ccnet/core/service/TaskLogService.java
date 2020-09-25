package com.ccnet.core.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.TaskLog;
import com.ccnet.core.service.base.BaseService;
/**
 * 定时任务service
 */
public interface TaskLogService extends BaseService<TaskLog>{
	/**
	 * 分页查询
	 * @param taskLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TaskLog> findTaskLogByPage(TaskLog taskLog, Page<TaskLog> page, Dto pdDto);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateTarskLog();
}
