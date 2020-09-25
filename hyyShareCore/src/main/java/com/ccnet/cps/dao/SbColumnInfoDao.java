package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbColumnInfo;

/**
 * 栏目数据操作
 * ClassName: SbColumnInfoDao 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
@Repository("sbColumnInfoDao")
public class SbColumnInfoDao extends BaseDao<SbColumnInfo> {
	
	/**
	 * 分页查询(多过滤)
	 * @param columnInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbColumnInfo> findSbColumnInfoByPage(SbColumnInfo columnInfo, Page<SbColumnInfo> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("enabled");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		String columnType = pdDto.getAsString("columnType");
		String code = pdDto.getAsString("code");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbColumnInfo.class, columnInfo);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (column_name like ? or column_id like ? ) ");
			params.add("%"+queryParam+"%");
			params.add("%"+queryParam+"%");
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
        	sql.append(" and enabled =? ");
			params.add(state);
		}
        
        if(CPSUtil.isNotEmpty(columnType)){
        	appendWhere(sql);
        	sql.append(" and column_type =? ");
			params.add(columnType);
		}
        
        if(CPSUtil.isNotEmpty(code)){
        	appendWhere(sql);
        	sql.append(" and code =? ");
			params.add(state);
		}
        
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbColumnInfo>(SbColumnInfo.class), params, page);
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
	 * 获取column信息
	 * @param SbColumnInfo
	 * @return
	 */
	public SbColumnInfo findSbColumnInfo(SbColumnInfo SbColumnInfo) {
		return find(SbColumnInfo);
	}
	
	/**
	 * 查询栏目
	 * @param columnInfo
	 * @return
	 */
	public List<SbColumnInfo> getColumnInfoList(SbColumnInfo columnInfo) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		if(CPSUtil.isNotEmpty(columnInfo.getColumnType())){
			sql.append(" and column_type = ?");
			params.add(columnInfo.getColumnType());
		}
		sql.append(" order by order_no asc ");
		return memory.query(sql, new BeanListHandler<SbColumnInfo>(SbColumnInfo.class), params);
	}
	
	/**
	 * 根据ID获取栏目
	 * @param columnIds
	 * @return
	 */
	public List<SbColumnInfo> getColumnListByIds(List<Integer> columnIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "column_id", columnIds);
		List<SbColumnInfo> list = memory.query(sql, new BeanListHandler<SbColumnInfo>(SbColumnInfo.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param columnIds
	 * @return
	 */
	public Map<Integer, SbColumnInfo> getSbColumnInfoMapByIds(List<Integer> columnIds) {
		List<SbColumnInfo> columnInfos = getColumnListByIds(columnIds);
		Map<Integer, SbColumnInfo> map = new HashMap<Integer, SbColumnInfo>(0);
		if (CollectionUtils.isNotEmpty(columnInfos)) {
			for (SbColumnInfo columnInfo : columnInfos) {
				map.put(columnInfo.getColumnId(), columnInfo);
			}
		}
		return map;
	}
	
	/**
	 * 获取单个栏目
	 * @param columnId
	 * @return
	 */
	public SbColumnInfo getColumnByID(Integer columnId) {
		SbColumnInfo columnInfo = new SbColumnInfo();
		columnInfo.setColumnId(columnId);
		return find(columnInfo);
	}
	
	
	/**
	 * 保存栏目
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean saveSbColumnInfo(SbColumnInfo SbColumnInfo) {
		if(insert(SbColumnInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改栏目
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean editSbColumnInfo(SbColumnInfo SbColumnInfo) {
		if(update(SbColumnInfo, "columnId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除用户
	 * @param SbColumnInfo
	 * @return
	 */
	public boolean trashSbColumnInfo(SbColumnInfo SbColumnInfo) {
		if(delete(SbColumnInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除里面
	 * @param list
	 * @return
	 */
	public boolean trashSbColumnInfoList(List<SbColumnInfo> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}

}
