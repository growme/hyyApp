package com.ccnet.core.dao.impl;

import java.util.ArrayList;
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
import com.ccnet.core.entity.SbForwardLink;


/**
 * 入口链接业务
 * @author JackieWang
 *
 */
@Repository("sbForwardLinkDao")
public class SbForwardLinkDao extends BaseDao<SbForwardLink> {
	
	
	/**
	 * 分页查询入口(多过滤)
	 * @param link
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbForwardLink> findSbForwardLinkInfoByPage(SbForwardLink link, Page<SbForwardLink> page,Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbForwardLink.class, link);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (link_addr like ?) ");
			params.add("%" + queryParam + "%");
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
		sql.append(" order by update_time desc,order_no asc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbForwardLink>(SbForwardLink.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 根据ID获取入口
	 * @param linkId
	 * @return
	 */
	public SbForwardLink findSbForwardLinkInfoByID(Integer linkId) {
		SbForwardLink link = new SbForwardLink();
		link.setLinkId(linkId);
		return find(link);
	}
	
	
	/**
	 * 保存入口
	 * @param link
	 * @return
	 */
	public boolean saveSbForwardLinkInfo(SbForwardLink link) {
		return insert(link, "link_id")>0;
	}
	
	/**
	 * 修改入口
	 * @param link
	 * @return
	 */
	public boolean editSbForwardLinkInfo(SbForwardLink link) {
		return update(link, "link_id")>0;
	}
	
	/**
	 * 获取所有入口
	 * @param link
	 * @return
	 */
	public List<SbForwardLink> querySbForwardLinkList(SbForwardLink link){
		return findList(link);
	}
	
	/**
	 * 根据ID获取入口集合
	 * @param linkIds
	 * @return
	 */
	public List<SbForwardLink> getSbForwardLinkListByIds(List<Integer> linkIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "link_id", linkIds);
		List<SbForwardLink> list = memory.query(sql, new BeanListHandler<SbForwardLink>(SbForwardLink.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param linkIds
	 * @return
	 */
	public Map<Integer, SbForwardLink> getSbForwardLinkByIds(List<Integer> linkIds) {
		List<SbForwardLink> linkInfos = getSbForwardLinkListByIds(linkIds);
		Map<Integer, SbForwardLink> map = new HashMap<Integer, SbForwardLink>(0);
		if (CollectionUtils.isNotEmpty(linkInfos)) {
			for (SbForwardLink link : linkInfos) {
				map.put(link.getLinkId(), link);
			}
		}
		return map;
	}
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getSbForwardLinkInfoList(List<Integer> stateId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "state", stateId);
		return memory.query(sql, new BeanListHandler<SbForwardLink>(SbForwardLink.class), params);
	}
	
	
	/**
	 * 批量删除入口
	 * @param list
	 * @return
	 */
	public boolean trashSbForwardLinkList(List<SbForwardLink> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除入口
	 * @param role
	 * @return
	 */
	public boolean trashSbForwardLinkInfo(SbForwardLink link) {
		return delete(link)>0;
	}
	
}
