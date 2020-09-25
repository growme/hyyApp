package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.ContentDomainDao;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.service.ContentDomainInfoService;
/**
 * 域名池管理
 * @author jackie wang
 *
 */
@Service("contentDomainInfoService")
public class ContentDomainInfoServiceImpl extends BaseServiceImpl<SbContentDomain> implements
		ContentDomainInfoService {
	
	@Autowired
	private ContentDomainDao contentDomainInfoDao; 

	/**
	 * 分页查询域名(多过滤)
	 * @param domain
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentDomain> findContentDomainInfoByPage(SbContentDomain domain, Page<SbContentDomain> page,Dto pdDto){
		return contentDomainInfoDao.findContentDomainInfoByPage(domain, page, pdDto);
	}
	
	
	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	public boolean saveContentDomainInfo(SbContentDomain domain){
		return contentDomainInfoDao.saveContentDomainInfo(domain);
	}
	
	/**
	 * 修改域名
	 * @param domain
	 * @return
	 */
	public boolean editContentDomainInfo(SbContentDomain domain){
		return contentDomainInfoDao.editContentDomainInfo(domain);
	}
	
	/**
	 * 修改域名专属
	 * @param domainId
	 * @param personal
	 * @return boolean  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public boolean updateContentDomainPersonal(String domainId,Integer personal){
		boolean temp = false;
		SbContentDomain domain = contentDomainInfoDao.findContentDomainInfoByID(domainId);
		if(CPSUtil.isNotEmpty(domain)){
		  domain.setDomainId(domainId);
		  domain.setPersonal(personal);
		  domain.setLastUpdateTime(new Date());
		  temp = contentDomainInfoDao.editContentDomainInfo(domain);
		}
		return temp;
	}
	
	/**
	 * 获取所有域名
	 * @param domain
	 * @return
	 */
	public List<SbContentDomain> queryContentDomainList(SbContentDomain domain){
		return contentDomainInfoDao.queryContentDomainList(domain);
	}
	
	/**
	 * 检查域名是否存在
	 * @param domainName
	 * @return
	 */
	public boolean checkDomainExist(String domainName){
		SbContentDomain domainInfo = new SbContentDomain();
		domainInfo.setDomainName(domainName);
		domainInfo = contentDomainInfoDao.find(domainInfo);
		if(CPSUtil.isNotEmpty(domainInfo)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getValidContentDomainList() {
		return contentDomainInfoDao.getContentDomainInfoList(DomainStateType.getValidateSate());
	}
	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getValidContentDomainList(Integer type) {
		return contentDomainInfoDao.getContentDomainInfoList(DomainStateType.getValidateSate(),type);
	}

	/**
	 * 批量更新域名状态
	 * @param enabled
	 * @param domainIds
	 * @param type
	 * @return
	 */
	public boolean updateContentDomainState(Integer enabled,List<Integer> domainIds,Integer type) {
		return contentDomainInfoDao.updateContentDomainState(enabled,domainIds,type) > 0 ? true : false;
	}

	
	/**
	 * 获取指定状态域名
	 * @param stateId
	 * @return
	 */
	public List<SbContentDomain> getCheckValidContentDomainList(){
		//检测非屏蔽域名
		return contentDomainInfoDao.getContentDomainInfoList(DomainStateType.getValidateSate());
	}
	
	/**
	 * 删除域名
	 * @param role
	 * @return
	 */
	public boolean trashContentDomainInfo(SbContentDomain domain){
		return contentDomainInfoDao.trashContentDomainInfo(domain);
	}
	
	/**
	 * 批量删除域名
	 * @param domainIds
	 * @return
	 */
	public boolean trashContentDomainList(String domainIds){
		boolean temp = false;
		List<SbContentDomain> list = null;
		if(CPSUtil.isNotEmpty(domainIds)){
			list = new ArrayList<SbContentDomain>();
			SbContentDomain domainInfo = null;
			String domain_id [] = domainIds.split(",");
			for (String domainId : domain_id) {
				domainInfo = new SbContentDomain();
				domainInfo.setDomainId(domainId);
				list.add(domainInfo);
			}
			temp = contentDomainInfoDao.trashContentDomainList(list);
		}
		return temp;
	}

	@Override
	protected BaseDao<SbContentDomain> getDao() {
		return contentDomainInfoDao;
	}

}
