package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.TotalMoneyRank;

/**
 * 总排行业务
 * @author JackieWang
 *
 */
public interface TotalMoneyRankService extends BaseService<TotalMoneyRank> {
	
	
	/**
	 * 分页查询排行
	 * @param rank
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TotalMoneyRank> findTotalMoneyRankByPage(TotalMoneyRank rank, Page<TotalMoneyRank> page,Dto pdDto);
	
	/**
	 * 查询排行集合
	 * @param notice
	 * @return
	 */
	public List<TotalMoneyRank> findTotalMoneyRankList(TotalMoneyRank rank);
	
	
	/**
	 * 获取单个排行
	 * @param rank
	 */
	public TotalMoneyRank findTotalMoneyRankByID(TotalMoneyRank rank);
	
	/**
	 * 保存排行
	 * @param rank
	 * @return
	 */
	public boolean saveTotalMoneyRank(TotalMoneyRank rank);
	
	
	/**
	 * 修改排行
	 * @param rank
	 * @return
	 */
	public boolean editTotalMoneyRank(TotalMoneyRank rank);
	
	/**
	 * 删除排行
	 * @param rank
	 * @return
	 */
	public boolean trashTotalMoneyRank(TotalMoneyRank rank);

}
