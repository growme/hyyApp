package com.ccnet.cps.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbContentCommentDao;
import com.ccnet.cps.entity.SbContentComment;
import com.ccnet.cps.service.SbContentCommentService;

/**
 * 文章点赞业务
 * @author JackieWang
 *
 */
@Service("sbContentCommentService")
public class SbContentCommentServiceImpl extends BaseServiceImpl<SbContentComment> implements SbContentCommentService {
	
	@Autowired
	private SbContentCommentDao contentCommentDao;

	/**
	 * 获取文章点赞
	 * @param comment
	 * @return
	 */
	public SbContentComment findSbContentComment(SbContentComment comment){
		return contentCommentDao.findSbContentComment(comment);
	}
	
	
	/**
	 * 根据文章ID获取点赞
	 * @param contentIds
	 * @return
	 */
	public List<SbContentComment> getSbContentCommentListByIds(List<Integer> contentIds){
		return contentCommentDao.getSbContentCommentListByIds(contentIds);
	}
	
	/**
	 * 转换集合数据
	 * @param contentIds
	 * @return
	 */
	public Map<Integer, SbContentComment> getSbContentCommentMapByIds(List<Integer> contentIds){
		return contentCommentDao.getSbContentCommentMapByIds(contentIds);
	}
	
	/**
	 * 获取单个文章点赞
	 * @param contentId
	 * @return
	 */
	public SbContentComment getSbContentCommentByID(Integer contentId){
		return contentCommentDao.getSbContentCommentByID(contentId);
	}
	
	/**
	 * 保存文章
	 * @param comment
	 * @return
	 */
	public boolean saveSbContentComment(SbContentComment comment){
		return contentCommentDao.saveSbContentComment(comment);
	}
	
	/**
	 * 修改文章
	 * @param comment
	 * @return
	 */
	public boolean editSbContentComment(SbContentComment comment){
		return contentCommentDao.editSbContentComment(comment);
	}
	
	/**
	 * 删除文章点赞
	 * @param comment
	 * @return
	 */
	public boolean trashSbContentComment(SbContentComment comment){
		return contentCommentDao.trashSbContentComment(comment);
	}
	
	
	/**
	 * 保存用户点赞
	 * @param comment
	 * @return
	 */
	public boolean insertOrUpdate(SbContentComment comment){
		return contentCommentDao.insertOrUpdate(comment)>0?true:false;
	}
	
	protected BaseDao<SbContentComment> getDao() {
		return contentCommentDao;
	}

}
