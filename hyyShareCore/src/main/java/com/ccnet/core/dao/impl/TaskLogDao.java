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
import com.ccnet.core.entity.TaskLog;
/**
 * 动态任务数据层
 */
@Repository("taskLogDao")
public class TaskLogDao extends BaseDao<TaskLog>{
	/**
	 * 分页查询日志(多过滤)
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TaskLog> findTaskLogByPage(TaskLog taskLog, Page<TaskLog> page, Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, TaskLog.class, taskLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (log_name like ?  or class_name like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and create_time <=? ");
			params.add(end_date+" 23:59:59");
		}
		
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<TaskLog>(TaskLog.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateTarskLog(){
		StringBuffer sql = new StringBuffer(" truncate table task_log ");
		List<Object> params = new ArrayList<Object>();
		int rst = memory.update(sql, params);
		if(rst>=0){
			return true;
		}else{
			return false;
		}
	}
}
