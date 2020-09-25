package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.entity.SbShareLogExtend;

/**
 * 分享日志业务
 * @author JackieWang
 *
 */
public interface SbShareLogService extends BaseService<SbShareLog> {
	
	
	/**
	 * 分页查询分享日志
	 * @param log
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbShareLog> findSbShareLogByPage(SbShareLog log, Page<SbShareLog> page,Dto pdDto);
	
	/**
	 * 查询分享日志集合
	 * @param notice
	 * @return
	 */
	public List<SbShareLog> findSbShareLogList(SbShareLog log);
	
	/**
	 * 查询用户最近一条分享日志
	 * @param userId
	 * @return
	 */
	public SbShareLog findLastedShareLog(Integer userId);
	
	
	/**
	 * 获取单个分享日志
	 * @param log
	 */
	public SbShareLog findSbShareLogByID(SbShareLog log);
	
	
	/**
	 * 保存分享日志
	 * @param log
	 * @return
	 */
	public boolean saveSbShareLog(SbShareLog log);
	
	
	/**
	 * 修改分享日志
	 * @param log
	 * @return
	 */
	public boolean editSbShareLog(SbShareLog log);
	
	/**
	 * 删除分享日志
	 * @param log
	 * @return
	 */
	public boolean trashSbShareLog(SbShareLog log);
	
	/**
	 * 批量删除日志
	 * @param log_ids
	 * @return
	 */
	public boolean trashSbShareLogList(String log_ids);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateShareLog();

}
