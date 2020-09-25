package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbCashLog;

/**
 * 用户提现
 * @author sujie
 *
 * @time 2017-10-25 下午3:09:10
 */
public interface SbCashLogService extends BaseService<SbCashLog>{

	public int insert(SbCashLog cashLog);
	
	/**
	 * 分页查询(多过滤)
	 * @param cashLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbCashLog> findSbCashLogByPage(SbCashLog cashLog, Page<SbCashLog> page,Dto pdDto);
	
	
	/**
	 * 查询提现记录集合
	 * @param cashLog
	 * @param paramDto
	 * @return
	 */
	public List<SbCashLog> findSbCashLogList(SbCashLog cashLog,Dto paramDto);
	
	/**
	 * 日期统计
	 * @param startDate
	 * @param endDate
	 * @return int  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate);
	
	/**
	 * 获取累计提现总额
	 * @param page
	 * @param dto
	 * @return
	 */
	public Double getCurrentUserCashCount(SbCashLog cashLog, Dto paramDto);
	
	/**
	 * 查询指定提现
	 * @param cashId
	 * @param state
	 * @param userId
	 * @return
	 */
	public SbCashLog findSbCashLogById(Integer cashId,Dto paramDto);
	
	/**
	 * 更新审核信息
	 * @param cashId
	 * @param state
	 * @param remark
	 * @return
	 */
	public boolean updateUserCashState(Integer cashId,Integer state,String remark);
	
	
	public boolean updateUserCashMoney(Integer userID,Double money);
}
