package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentViewLog;

/**
 * 文章日志记录
 * @author jackie wang
 *
 */
@Repository("sbContentViewLogDao")
public class SbContentViewLogDao extends BaseDao<SbContentViewLog> {
	
	/**
	 * 分页查询(多过滤)
	 * @param viewLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentViewLog> findSbContentViewLogByPage(SbContentViewLog viewLog, Page<SbContentViewLog> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbContentViewLog.class, viewLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (content_id like ? or hash_key like ? ) ");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and view_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and view_time <=? ");
			params.add(end_date+" 23:59:59");
		}
        
		//加上排序
		sql.append(" order by view_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentViewLog>(SbContentViewLog.class), params, page);
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
			sql.append(" and view_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and view_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}
	
	/**
	 * 获取文章日志
	 * @param viewLog
	 * @return
	 */
	public SbContentViewLog findSbContentViewLog(SbContentViewLog viewLog) {
		return find(viewLog);
	}
	
	/**
	 * 保存文章日志
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentViewLog(SbContentViewLog viewLog) {
		try {
			if (insert(viewLog) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 修改文章日志
	 * @param viewLog
	 * @return
	 */
	public boolean editSbContentViewLog(SbContentViewLog viewLog) {
		if(update(viewLog, "contentId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除文章日志
	 * @param viewLog
	 * @return
	 */
	public boolean trashSbContentViewLog(SbContentViewLog viewLog) {
		if(delete(viewLog)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除文章日志
	 * @param list
	 * @return
	 */
	public boolean trashSbContentViewLogList(List<SbContentViewLog> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbContentViewLog(){
		StringBuffer sql = new StringBuffer(" truncate table sb_content_view_log ");
		List<Object> params = new ArrayList<Object>();
		int rst = memory.update(sql, params);
		if(rst>=0){
			return true;
		}else{
			return false;
		}
	}

}
