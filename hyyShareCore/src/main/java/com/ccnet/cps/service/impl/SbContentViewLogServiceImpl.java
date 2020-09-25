package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.ccnet.cps.dao.SbContentInfoDao;
import com.ccnet.cps.dao.SbContentViewLogDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentViewLog;
import com.ccnet.cps.service.SbContentViewLogService;

/**
 * 文章日志业务
 * @author jackie wang
 *
 */
@Service("sbContentViewLogService")
public class SbContentViewLogServiceImpl extends BaseServiceImpl<SbContentViewLog> implements SbContentViewLogService {

	
	@Autowired
	private SbContentViewLogDao contentViewLogDao;
	@Autowired
	private SbContentInfoDao contentInfoDao;
	@Autowired
	private MemberInfoDao memberInfoDao;
	
	/**
	 * 分页查询(多过滤)
	 * @param viewLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentViewLog> findSbContentViewLogByPage(SbContentViewLog viewLog, Page<SbContentViewLog> page,Dto pdDto){
		
		Page viewLogPage = contentViewLogDao.findSbContentViewLogByPage(viewLog, page, pdDto);
		List<SbContentViewLog> viewList = viewLogPage.getResults();
		if(!CPSUtil.checkListBlank(viewList)){
			//获取文章信息
			List<Integer> contentIds = contentViewLogDao.getValuesFromField(viewList, "contentId");
			Map<Integer, SbContentInfo> contentMap = contentInfoDao.getSbContentInfoMapByIds(contentIds);
			//获取会员信息
			List<Integer> memberIds = contentViewLogDao.getValuesFromField(viewList, "userId");
			Map<Integer, MemberInfo> memberMap = memberInfoDao.getUserMapByIds(memberIds);
			for (SbContentViewLog _viewLog : viewList) {
				
				if(CPSUtil.isNotEmpty(_viewLog.getContentId())){
					_viewLog.setContentInfo(contentMap.get(_viewLog.getContentId()));
				}
				
				if(CPSUtil.isNotEmpty(_viewLog.getUserId())){
					_viewLog.setMemberInfo(memberMap.get(_viewLog.getUserId()));
				}
				//后期考虑缓存获取文章和会员 加快速度
			}
		}
		
		return viewLogPage;
	}
	
	/**
	 * 日期统计
	 * @param startDate
	 * @param endDate
	 * @return int  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate){
		return contentViewLogDao.count(startDate, endDate);
	}
	
	
	/**
	 * 获取文章日志
	 * @param viewLog
	 * @return
	 */
	public SbContentViewLog findSbContentViewLog(SbContentViewLog viewLog){
		return contentViewLogDao.findSbContentViewLog(viewLog);
	}
	
	/**
	 * 保存文章日志
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentViewLog(SbContentViewLog viewLog){
		return contentViewLogDao.saveSbContentViewLog(viewLog);
	}
	
	/**
	 * 修改文章日志
	 * @param viewLog
	 * @return
	 */
	public boolean editSbContentViewLog(SbContentViewLog viewLog){
		return contentViewLogDao.editSbContentViewLog(viewLog);
	}
	
	/**
	 * 删除文章日志
	 * @param viewLog
	 * @return
	 */
	public boolean trashSbContentViewLog(SbContentViewLog viewLog){
		return contentViewLogDao.trashSbContentViewLog(viewLog);
	}
	
	/**
	 * 批量删除文章日志
	 * @param list
	 * @return
	 */
	public boolean trashSbContentViewLogList(List<SbContentViewLog> list){
		return contentViewLogDao.trashSbContentViewLogList(list);
	}
	
	/**
	 * 批量删除日志
	 * @param viewIds
	 * @return
	 */
	public boolean trashSbContentViewLogList(String viewIds){
		boolean temp = false;
		SbContentViewLog lg = null;
		String lg_id[] = null;
		List<SbContentViewLog> logList = null;
		if(CPSUtil.isNotEmpty(viewIds)){
			logList = new ArrayList<SbContentViewLog>();
			lg_id = viewIds.split(",");
			for (String logid : lg_id) {
				lg = new SbContentViewLog();
				lg.setViewId(Integer.valueOf(logid));
				logList.add(lg);
			}
			temp = contentViewLogDao.trashSbContentViewLogList(logList);
		}
		return temp;
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbContentViewLog(){
		return contentViewLogDao.truncateSbContentViewLog();
	}
	
	protected BaseDao<SbContentViewLog> getDao() {
		return contentViewLogDao;
	}
	

}
