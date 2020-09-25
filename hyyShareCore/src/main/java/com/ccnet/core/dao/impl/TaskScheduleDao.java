package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.TaskSchedule;

/**
 * 动态任务数据层
 */
@Repository("taskScheduleDao")
public class TaskScheduleDao extends BaseDao<TaskSchedule> {
	/**
	 * 根据Id获取任务
	 */
	public TaskSchedule getTaskScheduleById(String taskScheduleId) {
		TaskSchedule taskSchedule = new TaskSchedule();
		taskSchedule.setTaskScheduleId(taskScheduleId);
		return super.find(taskSchedule);
	}

	public Page<TaskSchedule> findTaskScheduleByPage(TaskSchedule taskSchedule,
			Page<TaskSchedule> page, Dto pdDto) {
		// 获取参数
		String queryParam = pdDto.getAsString("queryParam");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, TaskSchedule.class, taskSchedule);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		// 加上模糊查询
		if (CPSUtil.isNotEmpty(queryParam)) {
			appendWhere(sql);
			sql.append(" and (job_name like ?  or job_group like ? or alias_name like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}

		// 加上排序
		sql.append(" order by create_time");
		super.queryPager(sql.toString(), new BeanListHandler<TaskSchedule>(TaskSchedule.class), params, page);
		return page;

	}

}
