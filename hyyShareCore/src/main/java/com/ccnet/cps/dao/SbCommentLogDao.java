package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCommentLog;

/**
 * 文章点赞
 * @author jackieWang
 *
 */
@Repository("sbCommentLogDao")
public class SbCommentLogDao extends BaseDao<SbCommentLog> {
	
	
	/**
	 * 分页查询点赞(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbCommentLog> findSbCommentLogByPage(SbCommentLog comment, Page<SbCommentLog> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbCommentLog.class, comment);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (content_id like ?  or user_id like ? or comment_type like ? or comment_ip like ?  ) ");
			params.add(queryParam+"%");
			params.add(queryParam+"%");
			params.add(queryParam+"%");
			params.add(queryParam+"%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and register_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and register_time <=? ");
			params.add(end_date+" 23:59:59");
		}
        
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbCommentLog>(SbCommentLog.class), params, page);
		return page;
		
	}
	
	/**
	 * 获取文章点赞
	 * @param comment
	 * @return
	 */
	public SbCommentLog findSbCommentLog(SbCommentLog comment) {
		return find(comment);
	}
	
	/**
	 * 获取单个文章点赞
	 * @param commentId
	 * @return
	 */
	public SbCommentLog getSbCommentLogByID(Integer commentId) {
		SbCommentLog comment = new SbCommentLog();
		comment.setCommentId(commentId);
		return find(comment);
	}
	
	/**
	 * 保存文章点赞
	 * @param comment
	 * @return
	 */
	public boolean saveSbCommentLog(SbCommentLog comment) {
		if(insert(comment)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改文章点赞
	 * @param comment
	 * @return
	 */
	public boolean editSbCommentLog(SbCommentLog comment) {
		if(update(comment, "commentId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除文章点赞
	 * @param comment
	 * @return
	 */
	public boolean trashSbCommentLog(SbCommentLog comment) {
		if(delete(comment)>0){
			return true;
		}else{
			return false;
		}
	}
	
}
