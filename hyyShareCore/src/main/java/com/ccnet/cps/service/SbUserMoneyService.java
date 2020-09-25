package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbUserMoney;

/**
 * 资金业务
 * @author JackieWang
 *
 */
public interface SbUserMoneyService extends BaseService<SbUserMoney>{
	
	/**
	 * 获取总收益列表
	 * @param sbUserMoney
	 * @param page
	 * @param paramDto
	 * @return
	 */
	public Page<SbUserMoney> getUserMoneyByPage(SbUserMoney sbUserMoney,Page<SbUserMoney> page,Dto paramDto);
	
	/**
	 * 获取用户资金集合
	 * @param sbUserMoney
	 * @param paramDto
	 * @return
	 */
	public List<SbUserMoney> findSbUserMoneyList(SbUserMoney sbUserMoney,Dto paramDto);
	
	/**
	 * 有金额的，表示为活跃会员
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countActiveMember(Date startDate, Date endDate);
	
	/**
	 * 获取资金按金额排序
	 * @param sbUserMoney
	 * @param page
	 * @param paramDto
	 * @return
	 */
	public Page<SbUserMoney> getUserMoneyPageByMoney(SbUserMoney sbUserMoney,Page<SbUserMoney> page,Dto paramDto);
}
