package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbContentViewLog;

/**
 * 文章日志业务
 * @author jackie wang
 *
 */
public interface SbContentViewLogService extends BaseService<SbContentViewLog> {

	/**
	 * 分页查询(多过滤)
	 * @param viewLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentViewLog> findSbContentViewLogByPage(SbContentViewLog viewLog, Page<SbContentViewLog> page,Dto pdDto);
	
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
	 * 获取文章日志
	 * @param viewLog
	 * @return
	 */
	public SbContentViewLog findSbContentViewLog(SbContentViewLog viewLog);
	
	/**
	 * 保存文章日志
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentViewLog(SbContentViewLog viewLog);
	
	/**
	 * 修改文章日志
	 * @param viewLog
	 * @return
	 */
	public boolean editSbContentViewLog(SbContentViewLog viewLog);
	
	/**
	 * 删除文章日志
	 * @param viewLog
	 * @return
	 */
	public boolean trashSbContentViewLog(SbContentViewLog viewLog);
	
	/**
	 * 批量删除文章日志
	 * @param list
	 * @return
	 */
	public boolean trashSbContentViewLogList(List<SbContentViewLog> list);
	
	/**
	 * 批量删除日志
	 * @param viewIds
	 * @return
	 */
	public boolean trashSbContentViewLogList(String viewIds);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbContentViewLog();
	
	
	
}
