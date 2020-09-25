package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbContentInfoDao;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.dao.SbVisitCounterDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.entity.SbVisitCounter;
import com.ccnet.cps.service.SbContentVisitLogService;

/**
 * 文章指纹业务
 * @author jackie wang
 *
 */
@Service("sbContentVisitLogService")
public class SbContentVisitLogServiceImpl extends BaseServiceImpl<SbContentVisitLog> implements SbContentVisitLogService {

	
	@Autowired
	private SbContentVisitLogDao contentVisitLogDao;
	@Autowired
	private SbContentInfoDao contentInfoDao;
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private SbVisitCounterDao visitIPCounterDao;
	
	/**
	 * 分页查询(多过滤)
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentVisitLog> findSbContentVisitLogByPage(SbContentVisitLog visitLog, Page<SbContentVisitLog> page,Dto pdDto){
		
		//查询推广人
		Map<Integer, MemberInfo> memberMap = null;
		if (StringUtils.isNotBlank(pdDto.getAsString("loginAccount"))) {
			visitLog.setUserId(-1);
			MemberInfo memberInfo = memberInfoDao.findFormatByLoginName(pdDto.getAsString("loginAccount"));
			if (memberInfo != null) {
				memberMap = new HashMap<Integer, MemberInfo>(0);
				memberMap.put(memberInfo.getMemberId(), memberInfo);
				visitLog.setUserId(memberInfo.getMemberId());
			}
		}
		
		
		Page visitLogPage = contentVisitLogDao.findSbContentVisitLogByPage(visitLog, page, pdDto);
		List<SbContentVisitLog> visitList = visitLogPage.getResults();
		if(!CPSUtil.checkListBlank(visitList)){
			
			MemberInfo memberInfo = null;
			if (memberMap == null) { //没有指定会员查询，则需要再查询一下会员信息补充
				List<Integer> memberIds = contentInfoDao.getValuesFromField(visitList, "userId");
				memberMap = memberInfoDao.getUserMapByIds(memberIds);
			}
			//获取文章信息
			List<Integer> contentIds = contentVisitLogDao.getValuesFromField(visitList, "contentId");
			Map<Integer, SbContentInfo> contentMap = contentInfoDao.getSbContentInfoMapByIds(contentIds);
			
			//获取统计数据
			List<String> visitIPs = contentVisitLogDao.getValuesFromField(visitList, "request_ip");
			Map<String, SbVisitCounter> counterMap = visitIPCounterDao.getSbVisitCounterMapByIds(visitIPs);
			
			for (SbContentVisitLog _visitLog : visitList) {
				
				//处理会员 后期考虑缓存获取文章和会员 加快速度
				memberInfo = memberMap.get(_visitLog.getUserId());
				if(CPSUtil.isNotEmpty(memberInfo)){
					_visitLog.setMemberInfo(memberInfo);
				}
				
				//设置文章
				if(CPSUtil.isNotEmpty(_visitLog.getContentId())){
					_visitLog.setContentInfo(contentMap.get(_visitLog.getContentId()));
				}
				
				//设置计数
				if(CPSUtil.isNotEmpty(_visitLog.getRequestIp())){
					_visitLog.setVisitIPCounter(counterMap.get(_visitLog.getRequestIp()));
				}
				
			}
		}
		
		return visitLogPage;
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
		return contentVisitLogDao.count(startDate, endDate);
	}
	
	
	/**
	 * 获取文章指纹
	 * @param visitLog
	 * @return
	 */
	public SbContentVisitLog findSbContentVisitLog(SbContentVisitLog visitLog){
		return contentVisitLogDao.findSbContentVisitLog(visitLog);
	}
	
	/**
	 * 保存文章指纹
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentVisitLog(SbContentVisitLog visitLog){
		return contentVisitLogDao.saveSbContentVisitLog(visitLog);
	}
	
	/**
	 * 修改文章指纹
	 * @param visitLog
	 * @return
	 */
	public boolean editSbContentVisitLog(SbContentVisitLog visitLog){
		return contentVisitLogDao.editSbContentVisitLog(visitLog);
	}
	
	/**
	 * 更新心跳时间
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @return
	 */
	public boolean updateHeartBeatTime(String visitToken,String lastHeartBeatTime){
		 boolean temp = contentVisitLogDao.updateHeartBeat(visitToken,lastHeartBeatTime);
		 CPSUtil.xprint("更新数据库心跳【"+visitToken+"】==>>"+(temp==true?"成功":"失败"));
		return temp;
	}
	
	
	/**
	 * 更新心跳时间
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @return
	 */
	public boolean updateHeartBeat(String visitToken,String lastHeartBeatTime){
		 String page_read_time = JedisUtils.getFingerCacheValue(visitToken, "page_read_time");//开始访问时间
		 int heartBeatCount = CPSUtil.getSubSecond(page_read_time, lastHeartBeatTime);
		 boolean temp = contentVisitLogDao.updateHeartBeat(visitToken,lastHeartBeatTime,heartBeatCount);
		 CPSUtil.xprint("批量更新数据库心跳【"+visitToken+"】==>>"+(temp==true?"成功":"失败"));
		return temp;
	}
	
	/**
	 * 更新阅读参数
	 * @param visitToken
	 * @return
	 */
	public boolean updateContentReadParam(String visitToken){
		 boolean temp = false;
		 //处理同步更新 滑动 晃动 展开 等参数
		 String hdcs = JedisUtils.getFingerCacheValue(visitToken, "touch_count");//滑动次数
		 String zkcs = JedisUtils.getFingerCacheValue(visitToken, "iaws_index");//展开次数
		 String ydcs = JedisUtils.getFingerCacheValue(visitToken, "iaws_coord_num");//移动次数
		 if(CPSUtil.isEmpty(hdcs)){
			 hdcs = "0";
		 }
		 if(CPSUtil.isEmpty(zkcs)){
			 zkcs = "0";
		 }
		 if(CPSUtil.isEmpty(ydcs)){
			 ydcs = "0";
		 }
		 if(!"0".equals(hdcs) || !"0".equals(zkcs) || !"0".equals(ydcs)){
			 //只要有一个值不为0 则更新数据库
			 temp = contentVisitLogDao.updateContentReadParam(visitToken,hdcs,zkcs,ydcs);
			 CPSUtil.xprint("批量更新数据库阅读参数【"+visitToken+"】==>>"+(temp==true?"成功":"失败"));
		 }
		 
		 return temp;
	}
	
	
	/**
	 * 更新阅读参数
	 * @param visitToken
	 * @return
	 */
	public boolean updateVisitContentParam(String visitToken,String lastHeartBeatTime){
		 boolean temp = false;
		 //处理同步更新 滑动 晃动 展开 等参数
		 String hdcs = JedisUtils.getFingerCacheValue(visitToken, "touch_count");//滑动次数
		 String zkcs = JedisUtils.getFingerCacheValue(visitToken, "iaws_index");//展开次数
		 String ydcs = JedisUtils.getFingerCacheValue(visitToken, "iaws_coord_num");//移动次数
         String page_read_time = JedisUtils.getFingerCacheValue(visitToken, "page_read_time");//开始访问时间
		 
		 int heartBeatCount = 0;
		 if(CPSUtil.isNotEmpty(page_read_time) && CPSUtil.isNotEmpty(lastHeartBeatTime)){
			 heartBeatCount = CPSUtil.getSubSecond(page_read_time, lastHeartBeatTime);
		 }
		 
		 if(CPSUtil.isEmpty(hdcs)){
			 hdcs = "0";
		 }
		 
		 if(CPSUtil.isEmpty(zkcs)){
			 zkcs = "0";
		 }
		 
		 if(CPSUtil.isEmpty(ydcs)){
			 ydcs = "0";
		 }
		 
		 temp = contentVisitLogDao.updateContentReadParam(visitToken,hdcs,zkcs,ydcs,lastHeartBeatTime,heartBeatCount);
		 CPSUtil.xprint("批量更新数据库阅读参数【"+visitToken+"】==>>"+(temp==true?"成功":"失败"));
		 
		 return temp;
	}
	
	
	
	/**
	 * 删除文章指纹
	 * @param visitLog
	 * @return
	 */
	public boolean trashSbContentVisitLog(SbContentVisitLog visitLog){
		return contentVisitLogDao.trashSbContentVisitLog(visitLog);
	}
	
	/**
	 * 批量删除文章指纹
	 * @param list
	 * @return
	 */
	public boolean trashSbContentVisitLogList(List<SbContentVisitLog> list){
		return contentVisitLogDao.trashSbContentVisitLogList(list);
	}
	
	/**
	 * 批量删除指纹
	 * @param visitIds
	 * @return
	 */
	public boolean trashSbContentVisitLogList(String visitIds){
		boolean temp = false;
		SbContentVisitLog lg = null;
		String lg_id[] = null;
		List<SbContentVisitLog> logList = null;
		if(CPSUtil.isNotEmpty(visitIds)){
			logList = new ArrayList<SbContentVisitLog>();
			lg_id = visitIds.split(",");
			for (String logid : lg_id) {
				lg = new SbContentVisitLog();
				lg.setVisitId(Integer.valueOf(logid));
				logList.add(lg);
			}
			temp = contentVisitLogDao.trashSbContentVisitLogList(logList);
		}
		return temp;
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbContentVisitLog(){
		return contentVisitLogDao.truncateSbContentVisitLog();
	}
	
	protected BaseDao<SbContentVisitLog> getDao() {
		return contentVisitLogDao;
	}
	

}
