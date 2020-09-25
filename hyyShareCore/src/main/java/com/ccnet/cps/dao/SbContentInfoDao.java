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
import com.ccnet.cps.entity.SbContentInfo;

/**
 * 文章数据操作
 * ClassName: SbContentInfoDao 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */

@Repository("sbContentInfoDao")
public class SbContentInfoDao extends BaseDao<SbContentInfo> {
	
	/**
	 * 分页查询(多过滤)
	 * @param contentInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentInfo> findSbContentInfoByPage(SbContentInfo contentInfo, Page<SbContentInfo> page,Dto pdDto){
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbContentInfo.class, contentInfo);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (content_title like ? or content_id like ? ) ");
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
        
		//加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentInfo>(SbContentInfo.class), params, page);
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
	 * 获取文章信息
	 * @param contentInfo
	 * @return
	 */
	public SbContentInfo findSbContentInfo(SbContentInfo contentInfo) {
		return find(contentInfo);
	}
	
	/**
	 * 根据ID获取文章
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentListByIds(List<Integer> contentIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "content_id", contentIds);
		
		List<SbContentInfo> list = memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param contentIds
	 * @return
	 */
	public Map<Integer, SbContentInfo> getSbContentInfoMapByIds(List<Integer> contentIds) {
		List<SbContentInfo> contentInfos = getSbContentListByIds(contentIds);
		Map<Integer, SbContentInfo> map = new HashMap<Integer, SbContentInfo>(0);
		if (CollectionUtils.isNotEmpty(contentInfos)) {
			for (SbContentInfo contentInfo : contentInfos) {
				map.put(contentInfo.getContentId(), contentInfo);
			}
		}
		return map;
	}
	
	/**
	 * 获取单个文章
	 * @param contentId
	 * @return
	 */
	public SbContentInfo getSbContentByID(Integer contentId) {
		SbContentInfo contentInfo = new SbContentInfo();
		contentInfo.setContentId(contentId);
		return find(contentInfo);
	}
	
	
	/**
	 * 保存文章
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentInfo(SbContentInfo contentInfo) {
		if(insert(contentInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改文章
	 * @param contentInfo
	 * @return
	 */
	public boolean editSbContentInfo(SbContentInfo contentInfo) {
		if(update(contentInfo, "contentId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 处理阅读增加
	 * @param contentId
	 * @param addNum
	 */
	public int updateContentReadNum(Integer contentId,Integer addNum) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName()).append(" set read_num = ? where content_id =? ");
		params.add(addNum+1);
		params.add(contentId);
		return memory.update(sql, params);
	}
	
	/**
	 * 处理分享增加
	 * @param contentId
	 * @param addNum
	 */
	public int updateContentShareNum(Integer contentId,Integer addNum) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName()).append(" set share_num = ? where content_id =? ");
		params.add(addNum);
		params.add(contentId);
		return memory.update(sql, params);
	}
	
	
	/**
	 * 删除文章
	 * @param contentInfo
	 * @return
	 */
	public boolean trashSbContentInfo(SbContentInfo contentInfo) {
		if(delete(contentInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除文章
	 * @param list
	 * @return
	 */
	public boolean trashSbContentInfoList(List<SbContentInfo> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *查询当前ID上一篇文章
	 *
	 */
	public Page<SbContentInfo> getUpContentInfo(SbContentInfo contentInfo,Page<SbContentInfo> page,Dto pdDto){
        //获取参数
        String contentId=pdDto.getAsString("contentId");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		sql.append(" and content_id<?");
		params.add(contentId);
		//加上排序
		sql.append(" order by content_id desc");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentInfo>(SbContentInfo.class), params, page);
		return page;
	}
	
	/**
	 *查询当前ID下一篇文章
	 *
	 */
	public Page<SbContentInfo> getNextContentInfo(SbContentInfo contentInfo,Page<SbContentInfo> page,Dto pdDto){
        //获取参数
		String contentId = pdDto.getAsString("contentId");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		sql.append(" and content_id>?");
		params.add(contentId);
		//加上排序
		sql.append(" order by content_id asc");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentInfo>(SbContentInfo.class), params, page);
		return page;
	}
	
	
	/**
	 * 随机查询
	 * @param contentInfo
	 * @param limit 条数
	 * @return
	 */
	public List<SbContentInfo> getRandContentList(SbContentInfo contentInfo,int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where 1=1");
		if(CPSUtil.isNotEmpty(contentInfo.getCheckState())){
			sql.append(" and check_state=?");
			params.add(contentInfo.getCheckState());
		}
		sql.append(" order by rand() limit "+limit);
		return memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
	}
	
}
