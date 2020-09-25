package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.service.base.BaseService;
/**
 * 域名池 业务
 * @author jackie wang
 *
 */
public interface ContentDomainInfoService extends BaseService<SbContentDomain> {

	/**
	 * 分页查询域名(多过滤)
	 * @param domain
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentDomain> findContentDomainInfoByPage(SbContentDomain domain, Page<SbContentDomain> page,Dto pdDto);
	
	
	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	public boolean saveContentDomainInfo(SbContentDomain domain);
	
	/**
	 * 修改域名
	 * @param domain
	 * @return
	 */
	public boolean editContentDomainInfo(SbContentDomain domain);
	
	/**
	 * 修改域名专属
	 * @param domainId
	 * @param personal
	 * @return boolean  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public boolean updateContentDomainPersonal(String domainId,Integer personal);
	
	/**
	 * 获取所有域名
	 * @param domain
	 * @return
	 */
	public List<SbContentDomain> queryContentDomainList(SbContentDomain domain);
	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getValidContentDomainList();
	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getValidContentDomainList(Integer type);
	
	/**
	 * 批量更新域名状态
	 * @param enabled
	 * @param domainIds
	 * @param type
	 * @return
	 */
	public boolean updateContentDomainState(Integer enabled,List<Integer> domainIds,Integer type);
	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getCheckValidContentDomainList();
	
	/**
	 * 检查域名是否存在
	 * @param domainName
	 * @return
	 */
	public boolean checkDomainExist(String domainName);
	
	/**
	 * 删除域名
	 * @param role
	 * @return
	 */
	public boolean trashContentDomainInfo(SbContentDomain domain);
	
	/**
	 * 批量删除域名
	 * @param domainIds
	 * @return
	 */
	public boolean trashContentDomainList(String domainIds);
	
	
}
