package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbAdvertVisitLog;

import cn.ffcs.memory.BeanListHandler;

/**
 * 广告指纹记录
 * @author jackie wang
 *
 */
@Repository("sbAdvertVisitLogDao")
public class SbAdvertVisitLogDao extends BaseDao<SbAdvertVisitLog> {
	
	/**
	 * 分页查询(多过滤)
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbAdvertVisitLog> findSbAdvertVisitLogByPage(SbAdvertVisitLog visitLog, Page<SbAdvertVisitLog> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbAdvertVisitLog.class, visitLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (content_id like ? or request_ip like ? or hash_key like ? or visit_token like ?) ");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and page_read_time >=? ");
			params.add(start_date);
			//params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and page_read_time <=? ");
        	params.add(end_date);
			//params.add(end_date+" 23:59:59");
		}
        
		//加上排序
		sql.append(" order by page_read_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbAdvertVisitLog>(SbAdvertVisitLog.class), params, page);
		return page;
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
	public int count(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}
	
	/**
	 * 统计会员阅读
	 * @param contentId
	 * @param userId
	 * @param limit
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbAdvertVisitLog> getContentEffectReadList(Integer contentId,Integer userId,Integer limit, Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where account_time is not null ");

		//带上文章
		if(CPSUtil.isNotEmpty(userId)){
			sql.append(" and user_id =? ");
			params.add(userId);
		}
		
		//带上文章
		if(CPSUtil.isNotEmpty(contentId)){
			sql.append(" and content_id =? ");
			params.add(contentId);
		}
		
		// 带上日期查询
		if (startDate != null) {
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time < ? ");
			params.add(endDate);
		}
		
		//加上排序
		sql.append(" order by visit_time desc limit 0,?");
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbAdvertVisitLog>(SbAdvertVisitLog.class), params);
	}
	
	/**
	 * 统计日志表
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
//	public List<SbAdvertVisitLogExtend> getContentVisitCount(Date startDate, Date endDate, Integer userId) {
//		StringBuffer sql = new StringBuffer();
//		List<Object> params = new ArrayList<Object>();
//		sql.append("select visit_time,count(distinct request_ip) as ip_count,count(1) as click_count ,sum(if(account_time is not null, 1, 0)) as read_count,avg(touch_count) as touch_count,avg(coord_num) as coord_count,avg(expand_count) as expand_count from ").append(getCurrentTableName());
//		sql.append(" where 1=1");
//
//		//带上日期查询
//		if (startDate != null) {
//			sql.append(" and visit_time >=? ");
//			params.add(startDate);
//		}
//		if (endDate != null) {
//			sql.append(" and visit_time < ? ");
//			params.add(endDate);
//		}
//		//带上用户
//		if (userId != null) {
//			sql.append(" and user_id = ? ");
//			params.add(userId);
//		}
//		sql.append(" group by visit_time ");
//		return super.memory.query(sql, new BeanListHandler<SbAdvertVisitLogExtend>(SbAdvertVisitLogExtend.class), params);
//	}
	
	/**
	 * 获取广告记录
	 * @param visitLog
	 * @return
	 */
	public SbAdvertVisitLog findSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		return find(visitLog);
	}
	
	/**
	 * 获取广告记录
	 * @param hashKey
	 * @return
	 */
	public SbAdvertVisitLog findAdvertVisitLogByHashKey(String hashKey) {
		SbAdvertVisitLog visitLog = new SbAdvertVisitLog();
		visitLog.setHashKey(hashKey);
		return findSbAdvertVisitLog(visitLog);
	}
	
	/**
	 * 通过visitToken获取文章
	 * @param visitToken
	 * @return
	 */
	public SbAdvertVisitLog findAdvertVisitLogByVisitToken(String visitToken) {
		SbAdvertVisitLog visitLog = new SbAdvertVisitLog();
		visitLog.setVisitToken(visitToken);
		return findSbAdvertVisitLog(visitLog);
	}
	
	
	/**
	 * 保存广告指纹
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		try {
			if (insert(visitLog) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		}
		return false;
	}
	
	
	/**
	 * 保存/更新广告记录
	 * @param contentInfo
	 * @return
	 */
	public boolean insertOrUpdateAdvertVisitLog(SbAdvertVisitLog visitLog) {
		int rowNum = super.memory.createOrUpdate(super.memory.getConnection(), SbAdvertVisitLog.class, visitLog, "hashKey", null);
		if(rowNum>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改广告记录
	 * @param visitLog
	 * @return
	 */
	public boolean editSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		if(update(visitLog, "hashKey")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除广告记录
	 * @param visitLog
	 * @return
	 */
	public boolean trashSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		if(delete(visitLog)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除广告记录
	 * @param list
	 * @return
	 */
	public boolean trashSbAdvertVisitLogList(List<SbAdvertVisitLog> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateSbAdvertVisitLog(){
		StringBuffer sql = new StringBuffer(" truncate table sb_advert_visit_log ");
		List<Object> params = new ArrayList<Object>();
		int rst = memory.update(sql, params);
		if(rst>=0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取没有归属地的
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SbAdvertVisitLog> findNotIpLocationVisitLog(int start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where ip_location is null");
		// 加上排序
		sql.append(" order by visit_time desc limit ?,?");
		params.add(start);
		params.add(limit);
		return super.memory.query(sql,  new BeanListHandler<SbAdvertVisitLog>(SbAdvertVisitLog.class), params);
	}
	
	
	/**
	 * 获取没有计费成功的记录
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SbAdvertVisitLog> findNotAccountVisitLog(int start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where account_time is null");
		// 加上排序
		sql.append(" order by page_read_time asc limit ?,?");
		params.add(start);
		params.add(limit);
		return super.memory.query(sql,  new BeanListHandler<SbAdvertVisitLog>(SbAdvertVisitLog.class), params);
	}
	
	/**
	 * 更新ip归属地
	 * @param ipLocation
	 * @param province
	 * @param id
	 * @return
	 */
	public boolean updateIpLocation(String ipLocation,String province, Integer id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName() + " set ip_location =?,province=? where visit_id=?");
		params.add(ipLocation);
		params.add(province);
		params.add(id);
		return memory.update(sql, params)>=0;
	}
	
	/**
	 * 更新心跳时间
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @return
	 */
	public boolean updateHeartBeat(String visitToken,String lastHeartBeatTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName() + " set last_heart_beat_time =?,heart_beat_count=TIMESTAMPDIFF(SECOND, page_read_time, ?) where visit_token=? ");
		params.add(lastHeartBeatTime);
		params.add(lastHeartBeatTime);
		params.add(visitToken);
		return memory.update(sql, params)>=0;
	}
	
	/**
	 * 更新心跳时间
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @param heartBeatCount
	 * @return
	 */
	public boolean updateHeartBeat(String visitToken,String lastHeartBeatTime,int heartBeatCount) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName() + " t set t.last_heart_beat_time =? ,t.heart_beat_count=?  where t.visit_token=?");
		params.add(DateUtils.getDateTimeFormat(lastHeartBeatTime));
		params.add(heartBeatCount);
		params.add(visitToken);
		return memory.update(sql, params)>=0;
	}
	
	
	/**
	 * 更新阅读参数
	 * @param visitToken
	 * @param touch_count
	 * @param iaws_index
	 * @param iaws_coord_num
	 * @param heartTime
	 * @param heartBeatCount
	 * @return
	 */
	public boolean updateContentReadParam(String visitToken,String touch_count,String iaws_index,String iaws_coord_num,String heartTime,int heartBeatCount) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName() + " t1 set t1.touch_count =?,t1.expand_count=?,t1.coord_num=?,t1.last_heart_beat_time =?,t1.heart_beat_count=? where t1.visit_token=?");
		params.add(touch_count);
		params.add(iaws_index);
		params.add(iaws_coord_num);
		params.add(DateUtils.getDateTimeFormat(heartTime));
		params.add(heartBeatCount);
		params.add(visitToken);
		return memory.update(sql, params)>=0;
	}
	
	
	
	/**
	 * 更新阅读参数
	 * @param visitToken
	 * @param touch_count
	 * @param iaws_index
	 * @param iaws_coord_num
	 * @return
	 */
	public boolean updateContentReadParam(String visitToken,String touch_count,String iaws_index,String iaws_coord_num) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName() + " set touch_count =?,expand_count=?,coord_num=? where visit_token=?");
		params.add(touch_count);
		params.add(iaws_index);
		params.add(iaws_coord_num);
		params.add(visitToken);
		return memory.update(sql, params)>=0;
	}
	
	/**
	 * 统计访问日志情况
	 * @param startDate
	 * @param endDate
	 * @return
	 */
//	public List<SbAdvertVisitLogExtend> countVisitContentLogExtend(Date startDate, Date endDate) {
//		StringBuffer sql = new StringBuffer();
//		List<Object> params = new ArrayList<Object>();
//		sql.append("select count(1) as count,count(request_ip) as ip_count,province,avg(touch_count) as touch_count,avg(coord_num) as coord_num,avg(expand_count) as expand_count from ").append(getCurrentTableName());
//		sql.append(" where 1=1");
//		
//		// 带上日期查询
//		if (startDate != null) {
//			sql.append(" and visit_time >=? ");
//			params.add(startDate);
//		}
//		if (endDate != null) {
//			sql.append(" and visit_time < ? ");
//			params.add(endDate);
//		}
//		
//		sql.append(" group by province ");
//		return super.memory.query(sql, new BeanListHandler<SbAdvertVisitLogExtend>(SbAdvertVisitLogExtend.class), params);
//		
//	}
	
	/**
	 * 批量更新日志
	 * @param logList
	 * @return
	 */
	public boolean updateBatchSbAdvertVisitLogList(List<SbAdvertVisitLog> logList) {
	    return updateBatch(logList, "hashKey");
	}

	public List<SbAdvertVisitLog> getContentVisitCount(Date startDate, Date endDate, Integer userId,Integer adid) {
	StringBuffer sql = new StringBuffer();
	List<Object> params = new ArrayList<Object>();
	sql.append("select visit_time,count(distinct request_ip) as ip_count,count(1) as click_count ,sum(if(account_time is not null, 1, 0)) as read_count,avg(touch_count) as touch_count,avg(coord_num) as coord_count,avg(expand_count) as expand_count from ").append(getCurrentTableName());
	sql.append(" where 1=1");

	//带上日期查询
	if (startDate != null) {
		sql.append(" and visit_time >=? ");
		params.add(startDate);
	}
	if (endDate != null) {
		sql.append(" and visit_time < ? ");
		params.add(endDate);
	}
	//带上用户
	if (userId != null) {
		sql.append(" and user_id = ? ");
		params.add(userId);
	}
	sql.append(" group by visit_time ");
	return super.memory.query(sql, new BeanListHandler<SbAdvertVisitLog>(SbAdvertVisitLog.class), params);
}
}
