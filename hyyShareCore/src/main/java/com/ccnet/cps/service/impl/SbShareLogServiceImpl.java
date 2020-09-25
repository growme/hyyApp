package com.ccnet.cps.service.impl;

import java.util.ArrayList;
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
import com.ccnet.cps.dao.SbShareLogDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.service.SbShareLogService;

/**
 * 总分享日志业务
 * @author JackieWang
 *
 */
@Service("shareLogService")
public class SbShareLogServiceImpl extends BaseServiceImpl<SbShareLog> implements SbShareLogService {

	@Autowired
	private SbShareLogDao shareLogDao;
	@Autowired
	private SbContentInfoDao contentInfoDao;
	@Autowired
	private MemberInfoDao memberInfoDao;
	
	/**
	 * 分页查询分享日志
	 * @param log
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbShareLog> findSbShareLogByPage(SbShareLog log, Page<SbShareLog> page,Dto pdDto){
		
		Page shareLogPage = shareLogDao.findSbShareLogByPage(log, page, pdDto);
		List<SbShareLog> shareList = shareLogPage.getResults();
		if(!CPSUtil.checkListBlank(shareList)){
			//获取文章信息
			List<Integer> contentIds = shareLogDao.getValuesFromField(shareList, "contentId");
			Map<Integer, SbContentInfo> contentMap = contentInfoDao.getSbContentInfoMapByIds(contentIds);
			//获取会员信息
			List<Integer> memberIds = shareLogDao.getValuesFromField(shareList, "userId");
			Map<Integer, MemberInfo> memberMap = memberInfoDao.getUserMapByIds(memberIds);
			for (SbShareLog _shareLog : shareList) {
				
				if(CPSUtil.isNotEmpty(_shareLog.getContentId())){
					_shareLog.setContentInfo(contentMap.get(_shareLog.getContentId()));
				}
				
				if(CPSUtil.isNotEmpty(_shareLog.getUserId())){
					_shareLog.setMemberInfo(memberMap.get(_shareLog.getUserId()));
				}
				//后期考虑缓存获取文章和会员 加快速度
			}
		}
		
		return shareLogPage;
	}
	
	/**
	 * 查询分享日志集合
	 * @param notice
	 * @return
	 */
	public List<SbShareLog> findSbShareLogList(SbShareLog log){
		return shareLogDao.findSbShareLogList(log);
	}
	
	/**
	 * 查询用户最近一条分享日志
	 * @param userId
	 * @return
	 */
	public SbShareLog findLastedShareLog(Integer userId) {
		SbShareLog lg = new SbShareLog();
		lg.setUserId(userId);
		List<SbShareLog> slist = findSbShareLogList(lg);
		if(CPSUtil.listNotEmpty(slist)){
			lg = slist.get(0);
		}else{
			lg = new SbShareLog();
		}
		return lg;
	}
	
	
	/**
	 * 获取单个分享日志
	 * @param log
	 */
	public SbShareLog findSbShareLogByID(SbShareLog log){
		return shareLogDao.findSbShareLogByID(log);
	}
	
	/**
	 * 保存分享日志
	 * @param log
	 * @return
	 */
	public boolean saveSbShareLog(SbShareLog log){
		return shareLogDao.saveSbShareLog(log);
	}
	
	
	/**
	 * 修改分享日志
	 * @param log
	 * @return
	 */
	public boolean editSbShareLog(SbShareLog log){
		return shareLogDao.editSbShareLog(log);
	}
	
	/**
	 * 删除分享日志
	 * @param log
	 * @return
	 */
	public boolean trashSbShareLog(SbShareLog log){
		return shareLogDao.trashSbShareLog(log);
	}
	
	/**
	 * 批量删除日志
	 * @param log_ids
	 * @return
	 */
	public boolean trashSbShareLogList(String log_ids) {
		boolean temp = false;
		SbShareLog lg = null;
		String lg_id[] = null;
		List<SbShareLog> logList = null;
		if(CPSUtil.isNotEmpty(log_ids)){
			logList = new ArrayList<SbShareLog>();
			lg_id = log_ids.split(",");
			for (String logid : lg_id) {
				lg = new SbShareLog();
				lg.setShareId(Integer.valueOf(logid));
				logList.add(lg);
			}
			temp = shareLogDao.trashSbShareLogList(logList);
		}
		return temp;
	}
	
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateShareLog(){
		return shareLogDao.truncateShareLog();
	}
	
	protected BaseDao<SbShareLog> getDao() {
		return shareLogDao;
	}

}
