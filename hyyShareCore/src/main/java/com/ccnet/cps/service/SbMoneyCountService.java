package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.UserDetailMoney;

public interface SbMoneyCountService extends BaseService<SbMoneyCount> {

	public Page<SbMoneyCount> getMoneyCountByPage(SbMoneyCount sbMoneyCount, Page<SbMoneyCount> page, Dto paramDto);

	/**
	 * 获取当前用户总收益
	 * 
	 * @param currentMoneyCount
	 * @param dto
	 * @return
	 */
	public Double getCurrentUserMoneyCount(SbMoneyCount sbMoneyCount, Dto paramDto);

	/**
	 * 查询资金集合
	 * 
	 * @param sbMoneyCount
	 * @param dtoParam
	 * @return
	 */
	public List<SbMoneyCount> findSbMoneyCountList(SbMoneyCount sbMoneyCount, Dto dtoParam);

	/**
	 * 当前用户徒弟提成收益
	 */
	public Double getCurrentUserTDPercentageCount(SbMoneyCount sbMoneyCount, Dto paramDto);

	/**
	 * 当前用户徒孙提成收益
	 */
	public Double getCurrentUserTSPercentageCount(SbMoneyCount sbMoneyCount, Dto paramDto);

	/**
	 * 当前用户徒弟提成收益明细
	 */
	public List<SbMoneyCount> getCurrentUserTDPercentageList(SbMoneyCount sbMoneyCount, Dto paramDto);

	/**
	 * 当前用户徒孙提成收益明细
	 */
	public List<SbMoneyCount> getCurrentUserTSPercentageList(SbMoneyCount sbMoneyCount, Dto paramDto);

	/**
	 * 获取用户收益明细（包含邀请奖励）
	 * 
	 * @param sbMoneyCount
	 * @param paramDto
	 * @return
	 */
	public List<UserDetailMoney> queryUserDetailMoneyList(SbMoneyCount sbMoneyCount, SbVisitMoney sbVisitMoney,
			Dto paramDto);

	/**
	 * 查询当前用户今日是否已领取红包
	 * 
	 * @param userId
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public SbMoneyCount getSbMoneyCountByType(Integer userId, Integer type, String startDate, String endDate);

	/**
	 * 保存收益明细
	 * 
	 * @param moneyCount
	 * @return
	 */
	public boolean saveSbMoneyCountInfo(SbMoneyCount moneyCount);

	/**
	 * 按天统计收益
	 * 
	 * @param sbMoneyCount
	 * @param paramDto
	 * @return
	 */
	public List<SbMoneyCount> getUserMoneyCountByUserIdAndDate(Integer userId, String startDate, String endDate);

	public Integer findMaxId();

	public boolean updateSbMoneyCountInfo(SbMoneyCount moneyCount);

	public Integer findSbMoneyCountByTypeUserId(Integer userId, Integer type);

}
