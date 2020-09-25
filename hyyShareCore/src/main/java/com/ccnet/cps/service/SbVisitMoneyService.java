package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbVisitMoney;

public interface SbVisitMoneyService extends BaseService<SbVisitMoney>{
	

	/**
	 * 分页查询奖励
	 * @param sbVisitMoney
	 * @param page
	 * @param paramDto
	 * @return
	 */
	public Page<SbVisitMoney> getVisitMoneyByPage(SbVisitMoney sbVisitMoney,Page<SbVisitMoney> page,Dto paramDto);
	
	/**
	 * 查询奖励
	 * @param sbVisitMoney
	 * @param dtoParam
	 * @return
	 */
	public List<SbVisitMoney> queryVisitMoneyList(SbVisitMoney sbVisitMoney,Dto dtoParam);
	
	/**
	 * 获取当日奖励
	 * @param currentVisitMoney
	 * @param dto
	 * @return
	 */
	public Double getCurrentUserVisitMoney(SbVisitMoney sbVisitMoney,Dto dtoParam);
	
	/**
	 * 保存奖励
	 * @param sbVisitMoney
	 * @return
	 */
	public int saveVisitMoney(SbVisitMoney sbVisitMoney);
}
