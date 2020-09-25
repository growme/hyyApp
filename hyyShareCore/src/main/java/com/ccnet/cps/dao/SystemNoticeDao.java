package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SystemNotice;
@Repository("systemNoticeDao")
public class SystemNoticeDao extends BaseDao<SystemNotice> {

	
	/**
	 * 分页查询公告
	 * @param notice
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SystemNotice> findSystemNoticeByPage(SystemNotice notice, Page<SystemNotice> page,Dto pdDto){
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("state");
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		sql.append("select * from ").append(getTableName(SystemNotice.class));
		List<String> whereColumns = memory.parseWhereColumns(params, SystemNotice.class, notice);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (notice_title like ?  or notice_id like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and create_time <=? ");
			params.add(end_date+" 23:59:59");
		}
        
        if(CPSUtil.isNotEmpty(state)){
        	appendWhere(sql);
        	sql.append(" and state =? ");
			params.add(state);
		}
        
        sql.append(" order by create_time desc,order_number asc ");
		super.queryPager(sql.toString(), new BeanListHandler<SystemNotice>(SystemNotice.class), params, page);
		return page;
		
	}
	
	/**
	 * 查询通知集合
	 * @param notice
	 * @return
	 */
	public List<SystemNotice> findSystemNoticeList(SystemNotice notice) {
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getTableName(SystemNotice.class));
		List<String> whereColumns = memory.parseWhereColumns(params, SystemNotice.class, notice);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		sql.append(" order by order_number asc ");
		
		return super.memory.query(sql,  new BeanListHandler<SystemNotice>(SystemNotice.class), params);
		
	}
	
	
	/**
	 * 获取单个公告
	 * @param systemNotice
	 */
	public SystemNotice findSystemNoticeByID(SystemNotice systemNotice){
		return find(systemNotice);
	}
	
	/**
	 * 保存公告
	 * @param systemNotice
	 * @return
	 */
	public boolean saveSystemNotice(SystemNotice systemNotice) {
		if(insert(systemNotice)>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 修改公告
	 * @param systemNotice
	 * @return
	 */
	public boolean editSystemNotice(SystemNotice systemNotice) {
		if(update(systemNotice, "noticeId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除公告
	 * @param systemNotice
	 * @return
	 */
	public boolean trashSystemNotice(SystemNotice systemNotice) {
		if(delete(systemNotice)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量修改状态
	 * @param list
	 * @param state
	 * @return
	 */
	public boolean batchUpdateNoticeSate(List<SystemNotice> list,Integer state) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update system_notice ");
		params.add(new Date());
		if(CPSUtil.isNotEmpty(state)){
			sql.append(" set state=? ");
			params.add(state);
		}
		List<Object> values = memory.getValuesFromField(list, "noticeId");
		memory.in(sql, params, "where ", "notice_id", values);
		return memory.update(sql, params)>=0;
	}
	
}
