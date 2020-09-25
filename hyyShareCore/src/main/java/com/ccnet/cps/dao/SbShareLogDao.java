package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.entity.SbShareLogExtend;

/**
 * 分享日志
 * @author JackieWang
 *
 */
@Repository("shareLogDao")
public class SbShareLogDao extends BaseDao<SbShareLog> {
	
	/**
	 * 分页查询分享日志
	 * @param log
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbShareLog> findSbShareLogByPage(SbShareLog log, Page<SbShareLog> page,Dto pdDto){
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String queryParam = pdDto.getAsString("queryParam");
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		sql.append("select * from ").append(getTableName(SbShareLog.class));
		List<String> whereColumns = memory.parseWhereColumns(params, SbShareLog.class, log);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (share_ip like ?  or user_id like ? or content_id like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and share_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and share_time <=? ");
			params.add(end_date+" 23:59:59");
		}
        
        sql.append(" order by share_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbShareLog>(SbShareLog.class), params, page);
		return page;
		
	}
	
	/**
	 * 查询分享日志集合
	 * @param log
	 * @return
	 */
	public List<SbShareLog> findSbShareLogList(SbShareLog log) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getTableName(SbShareLog.class));
		List<String> whereColumns = memory.parseWhereColumns(params, SbShareLog.class, log);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		sql.append(" order by share_time desc ");
		
		return super.memory.query(sql,  new BeanListHandler<SbShareLog>(SbShareLog.class), params);
		
	}
	
	/**
	 *  查询分享日志集合
	 * @param contentId
	 * @param userId
	 * @return
	 */
	public List<SbShareLog> findSbShareLogList(Integer contentId,Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName()).append(" where 1=1 ");
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
		sql.append(" order by share_money desc ");
		return super.memory.query(sql,  new BeanListHandler<SbShareLog>(SbShareLog.class), params);
	}
	
	/**
	 * 获取分享价格
	 * @param contentId 文章ID
	 * @param userId 用户ID
	 * @param type 类型
	 * @return
	 */
	public double findUserShareMoney(Integer contentId,Integer userId,String type) {
		double shareMoney = 0;
		SbShareLog shareLog = null;
		List<SbShareLog> slist = findSbShareLogList(contentId, userId); 
		if(CPSUtil.listNotEmpty(slist)){
			if("max".equals(type)){
			   shareLog = slist.get(0);
			}else{
			   shareLog = slist.get(RandomNum.getRandIntVal(slist.size()));
			}
		}
		
		if(CPSUtil.isNotEmpty(shareLog)){
			shareMoney = shareLog.getShareMoney();
		}
		return shareMoney;
	}
	
	/**
	 * 按是否计费统计分享数据
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbShareLogExtend> countMemeberShareByGivenMoney(Date startDate, Date endDate){
		return countMemeberShareData("given_money", startDate, endDate);
	}
	
	/**
	 * 按是分享分类统计分享数据
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbShareLogExtend> countMemeberShareByShareType(Date startDate, Date endDate){
		return countMemeberShareData("share_type", startDate, endDate);
	}
	
	/**
	 * 统计分享数据
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbShareLogExtend> countMemeberShareData(String groupColumn,Date startDate, Date endDate){
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		groupColumn = StringUtils.isNotBlank(groupColumn) ? ("," + groupColumn) : "";
		sql.append("select count(1) as count" + groupColumn + ",sum(share_money) as share_money from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		
		// 带上日期查询
		if (startDate != null) {
			sql.append(" and share_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and share_time < ? ");
			params.add(endDate);
		}
		
		sql.append(" group by ").append(groupColumn.replace(",", ""));
		return super.memory.query(sql, new BeanListHandler<SbShareLogExtend>(SbShareLogExtend.class), params);
		
	}
	
	
	/**
	 * 获取单个分享日志
	 * @param log
	 */
	public SbShareLog findSbShareLogByID(SbShareLog log){
		return find(log);
	}
	
	/**
	 * 保存分享日志
	 * @param log
	 * @return
	 */
	public boolean saveSbShareLog(SbShareLog log) {
		if(insert(log)>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 修改分享日志
	 * @param log
	 * @return
	 */
	public boolean editSbShareLog(SbShareLog log) {
		if(update(log, "shareId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除分享日志
	 * @param log
	 * @return
	 */
	public boolean trashSbShareLog(SbShareLog log) {
		if(delete(log)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除日志
	 * @param list
	 * @return
	 */
	public boolean trashSbShareLogList(List<SbShareLog> list) {
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
	public boolean truncateShareLog(){
		StringBuffer sql = new StringBuffer(" truncate table sb_share_log ");
		List<Object> params = new ArrayList<Object>();
		int rst = memory.update(sql, params);
		if(rst>=0){
			return true;
		}else{
			return false;
		}
	}

}
