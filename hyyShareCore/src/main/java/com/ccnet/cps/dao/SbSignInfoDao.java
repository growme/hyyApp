package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbSignInfo;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;

/**
 * 签到数据
 * @author JackieWang
 *
 */
@Repository("sbSignInfoDao")
public class SbSignInfoDao extends BaseDao<SbSignInfo> {

	/**
	 * 查询签到数据
	 * @param sbSignInfo
	 * @param paramDto
	 * @return
	 */
	public List<SbSignInfo> findSbSignInfoList(SbSignInfo sbSignInfo,Dto paramDto){
		
		//获取参数
		String queryParam = paramDto.getAsString("queryParam");
		//日期过滤
		String end_date = paramDto.getAsString("end_date");
		String start_date = paramDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
	
		List<String> whereColumns = memory.parseWhereColumns(params, SbSignInfo.class, sbSignInfo);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and update_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
	    if(CPSUtil.isNotEmpty(end_date)){
	    	appendWhere(sql);
	    	sql.append(" and update_time <=? ");
			params.add(end_date+" 23:59:59");
		}
		//加上排序
		sql.append(" order by update_time desc ");
		
		return memory.query(sql, new BeanListHandler<SbSignInfo>(SbSignInfo.class), params);
	}
	
	/**
	 * 查询签到
	 * @param sbSignInfo
	 * @return
	 */
	public SbSignInfo getSbSignInfoById(SbSignInfo sbSignInfo) {
		return find(sbSignInfo);
	}
	
	
	/**
	 * 保存签到
	 * @param SbSignInfo
	 * @return
	 */
	public boolean saveSbSignInfo(SbSignInfo sbSignInfo) {
		if(insert(sbSignInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户
	 * @param sbSignInfo
	 * @return
	 */
	public boolean editSbSignInfo(SbSignInfo sbSignInfo) {
		if(update(sbSignInfo, "signId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除签到
	 * @param SbSignInfo
	 * @return
	 */
	public boolean trashSbSignInfo(SbSignInfo sbSignInfo) {
		if(delete(sbSignInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除签到
	 * @param list
	 * @return
	 */
	public boolean trashSbSignInfoList(List<SbSignInfo> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 保存签到数据
	 * @param SbSignInfo
	 * @return
	 */
	public int insertOrUpdateSbSignInfo(SbSignInfo signInfo) {
		UserDailyEntity userDailyEntity = UserCache.getInstance().getUserCache(signInfo.getUserId());
		synchronized (userDailyEntity) {
			return super.memory.createOrUpdate(super.memory.getConnection(), SbSignInfo.class, signInfo, "signId",null);
		}
	}
	
}
