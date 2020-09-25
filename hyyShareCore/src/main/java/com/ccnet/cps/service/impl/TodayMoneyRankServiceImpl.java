package com.ccnet.cps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.TodayMoneyRankDao;
import com.ccnet.cps.entity.TodayMoneyRank;
import com.ccnet.cps.service.TodayMoneyRankService;

/**
 * 日排行业务
 * @author JackieWang
 *
 */
@Service("todayMoneyRankService")
public class TodayMoneyRankServiceImpl extends BaseServiceImpl<TodayMoneyRank> implements TodayMoneyRankService {

	@Autowired
	private TodayMoneyRankDao todayMoneyRankDao;
	
	/**
	 * 分页查询排行
	 * @param rank
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TodayMoneyRank> findTodayMoneyRankByPage(TodayMoneyRank rank, Page<TodayMoneyRank> page,Dto pdDto){
		return todayMoneyRankDao.findTodayMoneyRankByPage(rank, page, pdDto);
	}
	
	/**
	 * 查询排行集合
	 * @param notice
	 * @return
	 */
	public List<TodayMoneyRank> findTodayMoneyRankList(TodayMoneyRank rank){
		return todayMoneyRankDao.findTodayMoneyRankList(rank);
	}
	
	
	/**
	 * 获取单个排行
	 * @param rank
	 */
	public TodayMoneyRank findTodayMoneyRankByID(TodayMoneyRank rank){
		return todayMoneyRankDao.findTodayMoneyRankByID(rank);
	}
	
	/**
	 * 保存排行
	 * @param rank
	 * @return
	 */
	public boolean saveTodayMoneyRank(TodayMoneyRank rank){
		return todayMoneyRankDao.saveTodayMoneyRank(rank);
	}
	
	
	/**
	 * 修改排行
	 * @param rank
	 * @return
	 */
	public boolean editTodayMoneyRank(TodayMoneyRank rank){
		return todayMoneyRankDao.editTodayMoneyRank(rank);
	}
	
	/**
	 * 删除排行
	 * @param rank
	 * @return
	 */
	public boolean trashTodayMoneyRank(TodayMoneyRank rank){
		return todayMoneyRankDao.trashTodayMoneyRank(rank);
	}
	
	protected BaseDao<TodayMoneyRank> getDao() {
		return todayMoneyRankDao;
	}

}
