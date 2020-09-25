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
import com.ccnet.core.entity.SbContentDomain;
/**
 * 域名池管理
 * @author jackie
 *
 */
@Repository("contentDomainDao")
public class ContentDomainDao extends BaseDao<SbContentDomain> {
	
	
	/**
	 * 分页查询域名(多过滤)
	 * @param domain
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentDomain> findContentDomainInfoByPage(SbContentDomain domain, Page<SbContentDomain> page,Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbContentDomain.class, domain);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (domain_name like ?) ");
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
		
		//加上排序
		sql.append(" order by create_time desc,enabled asc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentDomain>(SbContentDomain.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 根据ID获取域名
	 * @param domainId
	 * @return
	 */
	public SbContentDomain findContentDomainInfoByID(String domainId) {
		SbContentDomain domain = new SbContentDomain();
		domain.setDomainId(domainId);
		return find(domain);
	}
	
	
	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	public boolean saveContentDomainInfo(SbContentDomain domain) {
		return insert(domain, "domain_id")>0;
	}
	
	/**
	 * 修改域名
	 * @param domain
	 * @return
	 */
	public boolean editContentDomainInfo(SbContentDomain domain) {
		return update(domain, "domain_id")>0;
	}
	
	/**
	 * 获取所有域名
	 * @param domain
	 * @return
	 */
	public List<SbContentDomain> queryContentDomainList(SbContentDomain domain){
		return findList(domain);
	}
	
	/**
	 * 根据ID获取域名集合
	 * @param domainIds
	 * @return
	 */
	public List<SbContentDomain> getContentDomainListByIds(List<String> domainIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "domain_id", domainIds);
		List<SbContentDomain> list = memory.query(sql, new BeanListHandler<SbContentDomain>(SbContentDomain.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param domainIds
	 * @return
	 */
	public Map<String, SbContentDomain> getContentDomainByIds(List<String> domainIds) {
		List<SbContentDomain> domainInfos = getContentDomainListByIds(domainIds);
		Map<String, SbContentDomain> map = new HashMap<String, SbContentDomain>(0);
		if (CollectionUtils.isNotEmpty(domainInfos)) {
			for (SbContentDomain domain : domainInfos) {
				map.put(domain.getDomainId(), domain);
			}
		}
		return map;
	}
	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getContentDomainInfoList(List<Integer> stateId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "enabled", stateId);
		return memory.query(sql, new BeanListHandler<SbContentDomain>(SbContentDomain.class), params);
	}
	
	
	/**
	 * 获取指定状态和类型域名
	 * @param stateId
	 * @param type
	 * @return
	 */
	public List<SbContentDomain> getContentDomainInfoList(List<Integer> stateId,Integer type) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "enabled", stateId);
		if(CPSUtil.isNotEmpty(type)){
        	appendWhere(sql);
        	sql.append(" and domain_type =? ");
			params.add(type);
		}
		return memory.query(sql, new BeanListHandler<SbContentDomain>(SbContentDomain.class), params);
	}
	
	/**
	 * 批量更新域名状态
	 * @param enabled
	 * @param domainIds
	 * @param type
	 * @return
	 */
	public int updateContentDomainState(Integer enabled,List<Integer> domainIds,Integer type) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName()).append(" set enabled = ? ,domain_type = ? ");
		params.add(enabled);
		params.add(type);
		if(CPSUtil.isNotEmpty(domainIds)){
		   memory.in(sql, params, "where", "domain_id", domainIds);
		}
		
		return memory.update(sql, params);
	}
	
	
	/**
	 * 批量删除域名
	 * @param list
	 * @return
	 */
	public boolean trashContentDomainList(List<SbContentDomain> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除域名
	 * @param role
	 * @return
	 */
	public boolean trashContentDomainInfo(SbContentDomain domain) {
		return delete(domain)>0;
	}
	

}
