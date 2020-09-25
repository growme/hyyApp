package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbContentVisitLog;

/**
 * 文章指纹业务
 * @author jackie wang
 *
 */
public interface SbContentVisitLogService extends BaseService<SbContentVisitLog> {

	/**
	 * 分页查询(多过滤)
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentVisitLog> findSbContentVisitLogByPage(SbContentVisitLog visitLog, Page<SbContentVisitLog> page,Dto pdDto);
	
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
	public SbContentVisitLog findSbContentVisitLog(SbContentVisitLog visitLog);
	
	/**
	 * 保存文章指纹
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentVisitLog(SbContentVisitLog visitLog);
	
	/**
	 * 修改文章指纹
	 * @param visitLog
	 * @return
	 */
	public boolean editSbContentVisitLog(SbContentVisitLog visitLog);
	
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
	public boolean trashSbContentVisitLog(SbContentVisitLog visitLog);
	
	/**
	 * 批量删除文章指纹
	 * @param list
	 * @return
	 */
	public boolean trashSbContentVisitLogList(List<SbContentVisitLog> list);
	
	/**
	 * 批量删除指纹
	 * @param visitIds
	 * @return
	 */
	public boolean trashSbContentVisitLogList(String visitIds);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbContentVisitLog();
	
	
	
}
