package com.ccnet.cps.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbCommentLogDao;
import com.ccnet.cps.dao.SbContentInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCommentLog;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.service.SbCommentLogService;

/**
 * 点赞日志业务
 * @author JackieWang
 *
 */
@Service("sbCommentLogService")
public class SbCommentLogServiceImpl extends BaseServiceImpl<SbCommentLog> implements SbCommentLogService {

	
	@Autowired
	private SbCommentLogDao commentLogDao;
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private SbContentInfoDao contentInfoDao;
	
	/**
	 * 分页查询点赞(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbCommentLog> findSbCommentLogByPage(SbCommentLog comment, Page<SbCommentLog> page,Dto pdDto){
		
		Page<SbCommentLog> commentPage = commentLogDao.findSbCommentLogByPage(comment, page, pdDto);
		List<SbCommentLog> commentLogs = commentPage.getResults();
		if(CPSUtil.listNotEmpty(commentLogs)){
			//处理会员
			List<Integer> userIds = commentLogDao.getValuesFromField(commentLogs, "userId");
			Map<Integer, MemberInfo> memberMap = memberInfoDao.getUserMapByIds(userIds);
			//获取文章
			List<Integer> contentIds = commentLogDao.getValuesFromField(commentLogs, "contentId");
			Map<Integer, SbContentInfo> contentMap = contentInfoDao.getSbContentInfoMapByIds(contentIds);
			for (SbCommentLog commentLog : commentLogs) {
				
				if(CPSUtil.isNotEmpty(commentLog.getContentId())){
					commentLog.setContentInfo(contentMap.get(commentLog.getContentId()));
				}
				
				if(CPSUtil.isNotEmpty(commentLog.getUserId())){
					commentLog.setMemberInfo(memberMap.get(commentLog.getUserId()));
				}
				
				//后期考虑缓存获取文章和会员 加快速度
			}
		}
		
		return commentPage;
	}
	
	/**
	 * 获取文章点赞
	 * @param comment
	 * @return
	 */
	public SbCommentLog findSbCommentLog(SbCommentLog comment){
		return commentLogDao.findSbCommentLog(comment);
	}
	
	/**
	 * 获取单个文章点赞
	 * @param commentId
	 * @return
	 */
	public SbCommentLog getSbCommentLogByID(Integer commentId){
		return commentLogDao.getSbCommentLogByID(commentId);
	}
	
	/**
	 * 保存文章点赞
	 * @param comment
	 * @return
	 */
	public boolean saveSbCommentLog(SbCommentLog comment){
		return commentLogDao.saveSbCommentLog(comment);
	}
	
	/**
	 * 修改文章点赞
	 * @param comment
	 * @return
	 */
	public boolean editSbCommentLog(SbCommentLog comment){
		return commentLogDao.editSbCommentLog(comment);
	}
	
	/**
	 * 删除文章点赞
	 * @param comment
	 * @return
	 */
	public boolean trashSbCommentLog(SbCommentLog comment){
		return commentLogDao.trashSbCommentLog(comment);
	}
	
	protected BaseDao<SbCommentLog> getDao() {
		return commentLogDao;
	}

}
