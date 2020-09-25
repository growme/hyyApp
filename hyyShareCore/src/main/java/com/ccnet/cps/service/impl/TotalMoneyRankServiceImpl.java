package com.ccnet.cps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.TotalMoneyRankDao;
import com.ccnet.cps.entity.TotalMoneyRank;
import com.ccnet.cps.service.TotalMoneyRankService;

/**
 * 总排行业务
 * @author JackieWang
 *
 */
@Service("totalMoneyRankService")
public class TotalMoneyRankServiceImpl extends BaseServiceImpl<TotalMoneyRank> implements TotalMoneyRankService {

	@Autowired
	private TotalMoneyRankDao totalMoneyRankDao;
	
	/**
	 * 分页查询排行
	 * @param rank
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<TotalMoneyRank> findTotalMoneyRankByPage(TotalMoneyRank rank, Page<TotalMoneyRank> page,Dto pdDto){
		return totalMoneyRankDao.findTotalMoneyRankByPage(rank, page, pdDto);
	}
	
	/**
	 * 查询排行集合
	 * @param notice
	 * @return
	 */
	public List<TotalMoneyRank> findTotalMoneyRankList(TotalMoneyRank rank){
		return totalMoneyRankDao.findTotalMoneyRankList(rank);
	}
	
	
	/**
	 * 获取单个排行
	 * @param rank
	 */
	public TotalMoneyRank findTotalMoneyRankByID(TotalMoneyRank rank){
		return totalMoneyRankDao.findTotalMoneyRankByID(rank);
	}
	
	/**
	 * 保存排行
	 * @param rank
	 * @return
	 */
	public boolean saveTotalMoneyRank(TotalMoneyRank rank){
		return totalMoneyRankDao.saveTotalMoneyRank(rank);
	}
	
	
	/**
	 * 修改排行
	 * @param rank
	 * @return
	 */
	public boolean editTotalMoneyRank(TotalMoneyRank rank){
		return totalMoneyRankDao.editTotalMoneyRank(rank);
	}
	
	/**
	 * 删除排行
	 * @param rank
	 * @return
	 */
	public boolean trashTotalMoneyRank(TotalMoneyRank rank){
		return totalMoneyRankDao.trashTotalMoneyRank(rank);
	}
	
	protected BaseDao<TotalMoneyRank> getDao() {
		return totalMoneyRankDao;
	}

}
