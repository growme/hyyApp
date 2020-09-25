package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbAdvertStat;

import cn.ffcs.memory.BeanListHandler;

@Repository("sbAdvertStatDao")
public class SbAdvertStatDao extends BaseDao<SbAdvertStat>{

	
	/**
	 * 分页查询(多过滤)
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbAdvertStat> findSbAdvertStatByPage(SbAdvertStat visitLog, Page<SbAdvertStat> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbAdvertStat.class, visitLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(start_date);
			//params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and create_time <=? ");
        	params.add(end_date);
			//params.add(end_date+" 23:59:59");
		}
        
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbAdvertStat>(SbAdvertStat.class), params, page);
		return page;
	}
	
	/**
	 * 日期统计
	 * @param startDate
	 * @param endDate
	 * @return int  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and create_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and create_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}
	
	
}
