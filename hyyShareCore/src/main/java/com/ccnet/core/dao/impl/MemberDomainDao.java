package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbMemberDomain;

/**
 * 专属域名数据操作
 * @author JackieWang
 *
 */
@Repository("memberDomainDao")
public class MemberDomainDao extends BaseDao<SbMemberDomain> {
	
	/**
	 * 分页查询专属域名(多过滤)
	 * @param domain
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbMemberDomain> findMemberContentDomainByPage(SbMemberDomain domain, Page<SbMemberDomain> page,Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbMemberDomain.class, domain);
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
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbMemberDomain>(SbMemberDomain.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 根据ID获取域名
	 * @param domainId
	 * @return
	 */
	public SbMemberDomain findMemberContentDomainByID(Integer domainId) {
		SbMemberDomain domain = new SbMemberDomain();
		domain.setMdId(domainId);
		return find(domain);
	}
	
	
	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	public boolean saveMemberContentDomain(SbMemberDomain domain) {
		return insert(domain, "md_id")>0;
	}
	
	/**
	 * 修改域名
	 * @param domain
	 * @return
	 */
	public boolean editMemberContentDomain(SbMemberDomain domain) {
		return update(domain, "md_id")>0;
	}
	
	/**
	 * 获取所有域名
	 * @param domain
	 * @return
	 */
	public List<SbMemberDomain> queryMemberContentDomainList(SbMemberDomain domain){
		return findList(domain);
	}
	
	/**
	 * 获取指定会员的域名
	 * @param stateId
	 * @return
	 */
	public List<SbMemberDomain> getMemberContentDomainList(List<Integer> memberId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "member_id", memberId);
		return memory.query(sql, new BeanListHandler<SbMemberDomain>(SbMemberDomain.class), params);
	}
	
	
	/**
	 * 批量删除域名
	 * @param list
	 * @return
	 */
	public boolean trashMemberContentDomainList(List<SbMemberDomain> list) {
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
	public boolean trashMemberContentDomainInfo(SbMemberDomain domain) {
		return delete(domain)>0;
	}
	
	

}
