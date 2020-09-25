package com.ccnet.cps.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbCommentLog;

/**
 * 点赞日志业务
 * @author JackieWang
 *
 */
public interface SbCommentLogService extends BaseService<SbCommentLog> {
	
	
	/**
	 * 分页查询点赞(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbCommentLog> findSbCommentLogByPage(SbCommentLog comment, Page<SbCommentLog> page,Dto pdDto);
	
	/**
	 * 获取文章点赞
	 * @param comment
	 * @return
	 */
	public SbCommentLog findSbCommentLog(SbCommentLog comment);
	
	/**
	 * 获取单个文章点赞
	 * @param commentId
	 * @return
	 */
	public SbCommentLog getSbCommentLogByID(Integer commentId);
	
	/**
	 * 保存文章点赞
	 * @param comment
	 * @return
	 */
	public boolean saveSbCommentLog(SbCommentLog comment);
	
	/**
	 * 修改文章点赞
	 * @param comment
	 * @return
	 */
	public boolean editSbCommentLog(SbCommentLog comment);
	
	/**
	 * 删除文章点赞
	 * @param comment
	 * @return
	 */
	public boolean trashSbCommentLog(SbCommentLog comment);

}
