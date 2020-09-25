package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbMemberDomain;
import com.ccnet.core.service.base.BaseService;

/**
 * 专属域名业务
 * @author JackieWang
 *
 */
public interface MemberDomainService extends BaseService<SbMemberDomain> {
	
	/**
	 * 分页查询专属域名(多过滤)
	 * @param domain
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbMemberDomain> findMemberContentDomainByPage(SbMemberDomain domain, Page<SbMemberDomain> page,Dto pdDto);
	
	
	/**
	 * 获取会员专属域名
	 * @param domain
	 * @return List<MemberContentDomain>  
	 */
	public List<SbMemberDomain> getMemberContentDomainList(SbMemberDomain domain);
	
	/**
	 * 根据ID获取域名
	 * @param domainId
	 * @return
	 */
	public SbMemberDomain findMemberContentDomainByID(Integer domainId);
	
	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	public boolean saveMemberContentDomain(SbMemberDomain domain);
	
	/**
	 * 修改域名
	 * @param domain
	 * @return
	 */
	public boolean editMemberContentDomain(SbMemberDomain domain);
	
	/**
	 * 获取所有域名
	 * @param domain
	 * @return
	 */
	public List<SbMemberDomain> queryMemberContentDomainList(SbMemberDomain domain);
	
	/**
	 * 获取指定会员的域名
	 * @param stateId
	 * @return
	 */
	public List<SbMemberDomain> getMemberContentDomainList(List<Integer> memberId);
	
	/**
	 * 获取指定会员的域名
	 * @param memberId
	 * @param domainId
	 */
	public SbMemberDomain getMemberContentDomain(Integer memberId,Integer domainId);
	
	
	/**
	 * 批量删除域名
	 * @param domainIds
	 * @return
	 */
	public boolean trashMemberContentDomainList(String domainIds);
	
	/**
	 * 批量删除域名
	 * @param list
	 * @return
	 */
	public boolean trashMemberContentDomainList(List<SbMemberDomain> list);
	
	/**
	 * 删除域名
	 * @param role
	 * @return
	 */
	public boolean trashMemberContentDomainInfo(SbMemberDomain domain);
	

}
