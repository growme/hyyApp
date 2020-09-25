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
import com.ccnet.core.entity.SbPromoteLink;


/**
 * 推广链接业务
 * @author JackieWang
 *
 */
@Repository("sbPromoteLinkDao")
public class SbPromoteLinkDao extends BaseDao<SbPromoteLink> {
	
	
	/**
	 * 分页查询推广地址(多过滤)
	 * @param link
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbPromoteLink> findSbPromoteLinkInfoByPage(SbPromoteLink link, Page<SbPromoteLink> page,Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbPromoteLink.class, link);
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
		super.queryPager(sql.toString(), new BeanListHandler<SbPromoteLink>(SbPromoteLink.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 根据ID获取推广地址
	 * @param linkId
	 * @return
	 */
	public SbPromoteLink findSbPromoteLinkInfoByID(Integer linkId) {
		SbPromoteLink link = new SbPromoteLink();
		link.setLinkId(linkId);
		return find(link);
	}
	
	
	/**
	 * 保存推广地址
	 * @param link
	 * @return
	 */
	public boolean saveSbPromoteLinkInfo(SbPromoteLink link) {
		return insert(link, "link_id")>0;
	}
	
	/**
	 * 修改推广地址
	 * @param link
	 * @return
	 */
	public boolean editSbPromoteLinkInfo(SbPromoteLink link) {
		return update(link, "link_id")>0;
	}
	
	/**
	 * 获取所有推广地址
	 * @param link
	 * @return
	 */
	public List<SbPromoteLink> querySbPromoteLinkList(SbPromoteLink link){
		return findList(link);
	}
	
	/**
	 * 根据ID获取推广地址集合
	 * @param linkIds
	 * @return
	 */
	public List<SbPromoteLink> getSbPromoteLinkListByIds(List<Integer> linkIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "link_id", linkIds);
		List<SbPromoteLink> list = memory.query(sql, new BeanListHandler<SbPromoteLink>(SbPromoteLink.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param linkIds
	 * @return
	 */
	public Map<Integer, SbPromoteLink> getSbPromoteLinkByIds(List<Integer> linkIds) {
		List<SbPromoteLink> linkInfos = getSbPromoteLinkListByIds(linkIds);
		Map<Integer, SbPromoteLink> map = new HashMap<Integer, SbPromoteLink>(0);
		if (CollectionUtils.isNotEmpty(linkInfos)) {
			for (SbPromoteLink link : linkInfos) {
				map.put(link.getLinkId(), link);
			}
		}
		return map;
	}
	
	/**
	 * 获取指定状态推广地址
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getSbPromoteLinkInfoList(List<Integer> stateId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "state", stateId);
		return memory.query(sql, new BeanListHandler<SbPromoteLink>(SbPromoteLink.class), params);
	}
	
	
	/**
	 * 批量删除推广地址
	 * @param list
	 * @return
	 */
	public boolean trashSbPromoteLinkList(List<SbPromoteLink> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除推广地址
	 * @param role
	 * @return
	 */
	public boolean trashSbPromoteLinkInfo(SbPromoteLink link) {
		return delete(link)>0;
	}
	
}
