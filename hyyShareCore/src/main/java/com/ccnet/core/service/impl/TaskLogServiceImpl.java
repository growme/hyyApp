package com.ccnet.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.TaskLogDao;
import com.ccnet.core.entity.TaskLog;
import com.ccnet.core.service.TaskLogService;

@Service("taskLogService")
public class TaskLogServiceImpl extends BaseServiceImpl<TaskLog> implements TaskLogService {
	
	@Autowired
	private TaskLogDao taskLogDao;
	
	@Override
	protected BaseDao<TaskLog> getDao() {
		return this.taskLogDao;
	}
	
	public Page<TaskLog> findTaskLogByPage(TaskLog taskLog, Page<TaskLog> page, Dto pdDto) {
		return this.taskLogDao.findTaskLogByPage(taskLog, page, pdDto);
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateTarskLog(){
		return taskLogDao.truncateTarskLog();
	}

}
