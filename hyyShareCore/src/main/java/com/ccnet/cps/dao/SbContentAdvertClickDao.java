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
import com.ccnet.cps.entity.SbContentAdvertClick;

/**
 * 广告日志逻辑
 * @author jackieWang
 *
 */
@Repository("sbContentAdvertClickDao")
public class SbContentAdvertClickDao extends BaseDao<SbContentAdvertClick>{
	/**
	 * 分页查询广告日志(多过滤)
	 * @param adclick
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentAdvertClick> findSbContentAdvertClickByPage(SbContentAdvertClick adclick, Page<SbContentAdvertClick> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbContentAdvertClick.class, adclick);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (token like ? or content_id like ? ) ");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
			params.add(queryParam);
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
        
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentAdvertClick>(SbContentAdvertClick.class), params, page);
		return page;
		
	}
	
	public int count(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and create_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and create_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}
	
	
	/**
	 * 保存广告日志
	 * @param SbContentAdvertClick
	 * @return
	 */
	public boolean saveSbContentAdvertClick(SbContentAdvertClick SbContentAdvertClick) {
		if(insert(SbContentAdvertClick)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改广告日志
	 * @param SbContentAdvertClick
	 * @return
	 */
	public boolean editSbContentAdvertClick(SbContentAdvertClick SbContentAdvertClick) {
		if(update(SbContentAdvertClick, "id")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除广告日志
	 * @param SbContentAdvertClick
	 * @return
	 */
	public boolean trashSbContentAdvertClick(SbContentAdvertClick SbContentAdvertClick) {
		if(delete(SbContentAdvertClick)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除广告日志
	 * @param list
	 * @return
	 */
	public boolean trashSbContentAdvertClickList(List<SbContentAdvertClick> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}

}
