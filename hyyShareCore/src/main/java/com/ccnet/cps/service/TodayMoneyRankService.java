package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.TodayMoneyRank;

/**
 * 今日排行业务
 * @author JackieWang
 *
 */
public interface TodayMoneyRankService extends BaseService<TodayMoneyRank> {
	
	/**
	 * 分页查询排行
	 * @param rank
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TodayMoneyRank> findTodayMoneyRankByPage(TodayMoneyRank rank, Page<TodayMoneyRank> page,Dto pdDto);
	
	/**
	 * 查询排行集合
	 * @param notice
	 * @return
	 */
	public List<TodayMoneyRank> findTodayMoneyRankList(TodayMoneyRank rank);
	
	
	/**
	 * 获取单个排行
	 * @param rank
	 */
	public TodayMoneyRank findTodayMoneyRankByID(TodayMoneyRank rank);
	
	/**
	 * 保存排行
	 * @param rank
	 * @return
	 */
	public boolean saveTodayMoneyRank(TodayMoneyRank rank);
	
	
	/**
	 * 修改排行
	 * @param rank
	 * @return
	 */
	public boolean editTodayMoneyRank(TodayMoneyRank rank);
	
	/**
	 * 删除排行
	 * @param rank
	 * @return
	 */
	public boolean trashTodayMoneyRank(TodayMoneyRank rank);
	
	

}
