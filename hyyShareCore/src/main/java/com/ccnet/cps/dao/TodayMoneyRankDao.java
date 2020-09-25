package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.TodayMoneyRank;

/**
 * 今日排行查询
 * @author Jackie
 *
 */
@Repository("todayMoneyRankDao")
public class TodayMoneyRankDao extends BaseDao<TodayMoneyRank> {
	
	/**
	 * 分页查询排行
	 * @param rank
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TodayMoneyRank> findTodayMoneyRankByPage(TodayMoneyRank rank, Page<TodayMoneyRank> page,Dto pdDto){
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String queryParam = pdDto.getAsString("queryParam");
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		sql.append("select * from ").append(getTableName(TodayMoneyRank.class));
		List<String> whereColumns = memory.parseWhereColumns(params, TodayMoneyRank.class, rank);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (rank_name like ?  or rank_mobile like ?) ");
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
        
        sql.append(" order by today_money desc ");
		super.queryPager(sql.toString(), new BeanListHandler<TodayMoneyRank>(TodayMoneyRank.class), params, page);
		return page;
		
	}
	
	/**
	 * 查询排行集合
	 * @param notice
	 * @return
	 */
	public List<TodayMoneyRank> findTodayMoneyRankList(TodayMoneyRank rank) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getTableName(TodayMoneyRank.class));
		List<String> whereColumns = memory.parseWhereColumns(params, TodayMoneyRank.class, rank);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		sql.append(" order by today_money desc ");
		
		return super.memory.query(sql,  new BeanListHandler<TodayMoneyRank>(TodayMoneyRank.class), params);
		
	}
	
	
	/**
	 * 获取单个排行
	 * @param rank
	 */
	public TodayMoneyRank findTodayMoneyRankByID(TodayMoneyRank rank){
		return find(rank);
	}
	
	/**
	 * 保存排行
	 * @param rank
	 * @return
	 */
	public boolean saveTodayMoneyRank(TodayMoneyRank rank) {
		if(insert(rank)>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 修改排行
	 * @param rank
	 * @return
	 */
	public boolean editTodayMoneyRank(TodayMoneyRank rank) {
		if(update(rank, "rankId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除排行
	 * @param rank
	 * @return
	 */
	public boolean trashTodayMoneyRank(TodayMoneyRank rank) {
		if(delete(rank)>0){
			return true;
		}else{
			return false;
		}
	}
	
	

}
