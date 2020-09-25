package com.ccnet.cps.service;

import java.util.List;
import java.util.Map;

import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbContentComment;

public interface SbContentCommentService extends BaseService<SbContentComment> {
	
	
	/**
	 * 获取文章点赞
	 * @param comment
	 * @return
	 */
	public SbContentComment findSbContentComment(SbContentComment comment);
	
	
	/**
	 * 根据文章ID获取点赞
	 * @param contentIds
	 * @return
	 */
	public List<SbContentComment> getSbContentCommentListByIds(List<Integer> contentIds);
	
	/**
	 * 转换集合数据
	 * @param contentIds
	 * @return
	 */
	public Map<Integer, SbContentComment> getSbContentCommentMapByIds(List<Integer> contentIds);
	
	/**
	 * 获取单个文章点赞
	 * @param contentId
	 * @return
	 */
	public SbContentComment getSbContentCommentByID(Integer contentId);
	
	/**
	 * 保存文章
	 * @param comment
	 * @return
	 */
	public boolean saveSbContentComment(SbContentComment comment);
	
	/**
	 * 修改文章
	 * @param comment
	 * @return
	 */
	public boolean editSbContentComment(SbContentComment comment);
	
	/**
	 * 删除文章点赞
	 * @param comment
	 * @return
	 */
	public boolean trashSbContentComment(SbContentComment comment);
	
	/**
	 * 保存用户点赞
	 * @param comment
	 * @return
	 */
	public boolean insertOrUpdate(SbContentComment comment);
	

}
