package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbAdvertVisitLog;

/**
 * 文章指纹业务
 * @author jackie wang
 *
 */
public interface SbAdvertVisitLogService extends BaseService<SbAdvertVisitLog> {

	/**
	 * 分页查询(多过滤)
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbAdvertVisitLog> findSbAdvertVisitLogByPage(SbAdvertVisitLog visitLog, Page<SbAdvertVisitLog> page,Dto pdDto);
	
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
	 * 获取文章指纹
	 * @param visitLog
	 * @return
	 */
	public SbAdvertVisitLog findSbAdvertVisitLog(SbAdvertVisitLog visitLog);
	
	/**
	 * 保存文章指纹
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbAdvertVisitLog(SbAdvertVisitLog visitLog);
	
	/**
	 * 修改文章指纹
	 * @param visitLog
	 * @return
	 */
	public boolean editSbAdvertVisitLog(SbAdvertVisitLog visitLog);
	
	/**
	 * 更新心跳时间
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @return
	 */
	public boolean updateHeartBeatTime(String visitToken,String lastHeartBeatTime);
	
	/**
	 * 更新心跳时间
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @return
	 */
	public boolean updateHeartBeat(String visitToken,String lastHeartBeatTime);
	
	/**
	 * 更新阅读参数
	 * @param visitToken
	 * @return
	 */
	public boolean updateContentReadParam(String visitToken);
	
	
	/**
	 * 更新阅读参数
	 * @param visitToken
	 * @return
	 */
	public boolean updateVisitContentParam(String visitToken,String lastHeartBeatTime);
	
	
	/**
	 * 删除文章指纹
	 * @param visitLog
	 * @return
	 */
	public boolean trashSbAdvertVisitLog(SbAdvertVisitLog visitLog);
	
	/**
	 * 批量删除文章指纹
	 * @param list
	 * @return
	 */
	public boolean trashSbAdvertVisitLogList(List<SbAdvertVisitLog> list);
	
	/**
	 * 批量删除指纹
	 * @param visitIds
	 * @return
	 */
	public boolean trashSbAdvertVisitLogList(String visitIds);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbAdvertVisitLog();
	
	
	
}
