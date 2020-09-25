package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.ContentDomainDao;
import com.ccnet.core.dao.impl.MemberDomainDao;
import com.ccnet.core.dao.impl.UserInfoDao;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.entity.SbMemberDomain;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.MemberDomainService;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;

/**
 * 专属域名业务
 * @author JackieWang
 *
 */
@Service("memberDomainService")
public class MemberDomainServiceImpl extends BaseServiceImpl<SbMemberDomain> implements MemberDomainService {

	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private MemberDomainDao memberContentDomainDao;
	@Autowired
	private ContentDomainDao contentDomainInfoDao;
	
	/**
	 * 分页查询专属域名(多过滤)
	 * @param domain
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbMemberDomain> findMemberContentDomainByPage(SbMemberDomain domain, Page<SbMemberDomain> page,Dto pdDto){
		//处理会员查询
		Map<Integer, MemberInfo> memberMap = null;
		if (StringUtils.isNotBlank(pdDto.getAsString("memberName"))) {
			domain.setMemberId(-1);
			MemberInfo memberInfo = memberInfoDao.findFormatByLoginName(pdDto.getAsString("memberName").trim());
			if (memberInfo != null) {
				memberMap = new HashMap<Integer, MemberInfo>(0);
				memberMap.put(memberInfo.getMemberId(), memberInfo);
				domain.setMemberId(memberInfo.getMemberId());
			}
		}
		
		Page domainPage = memberContentDomainDao.findMemberContentDomainByPage(domain, page, pdDto);
		List<SbMemberDomain> dList = domainPage.getResults();
		if(!CPSUtil.checkListBlank(dList)){
			//人员
			UserInfo userInfo = null;
			List<Integer> userIds = memberContentDomainDao.getValuesFromField(dList, "userId");
			Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);	
			//会员
			MemberInfo memberInfo = null;
			List<Integer> memberIds = memberContentDomainDao.getValuesFromField(dList, "memberId");
			Map<Integer, MemberInfo> mbMap = memberInfoDao.getUserMapByIds(memberIds);	
			//域名
			SbContentDomain orderDomain = null;
			List<String> domainIds = memberContentDomainDao.getValuesFromField(dList, "domainId");
			Map<String, SbContentDomain> domainMap = contentDomainInfoDao.getContentDomainByIds(domainIds);	
			for (SbMemberDomain memberDomain : dList) {
				//设置人员
				userInfo = userMap.get(memberDomain.getUserId());
				if(CPSUtil.isNotEmpty(userInfo)){
					memberDomain.setUserInfo(userInfo);
				}
				//设置会员
				memberInfo = mbMap.get(memberDomain.getMemberId());
				if(CPSUtil.isNotEmpty(memberInfo)){
					memberDomain.setMemberInfo(memberInfo);
				}
				//设置域名
				orderDomain = domainMap.get(memberDomain.getDomainId()+"");
				if(CPSUtil.isNotEmpty(orderDomain)){
					memberDomain.setContentDomain(orderDomain);
				}
			}
			
		}
		
		return domainPage;
	}
	
	/**
	 * 获取会员专属域名
	 * @param domain
	 * @return List<MemberContentDomain>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public List<SbMemberDomain> getMemberContentDomainList(SbMemberDomain domain) {
		List<SbMemberDomain> dList = memberContentDomainDao.findList(domain);
		if(!CPSUtil.checkListBlank(dList)){
			//人员
			UserInfo userInfo = null;
			List<Integer> userIds = memberContentDomainDao.getValuesFromField(dList, "userId");
			Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);	
			//会员
			MemberInfo memberInfo = null;
			List<Integer> memberIds = memberContentDomainDao.getValuesFromField(dList, "memberId");
			Map<Integer, MemberInfo> mbMap = memberInfoDao.getUserMapByIds(memberIds);	
			//域名
			SbContentDomain orderDomain = null;
			List<String> domainIds = memberContentDomainDao.getValuesFromField(dList, "domainId");
			Map<String, SbContentDomain> domainMap = contentDomainInfoDao.getContentDomainByIds(domainIds);	
			for (SbMemberDomain memberDomain : dList) {
				//设置人员
				userInfo = userMap.get(memberDomain.getUserId());
				if(CPSUtil.isNotEmpty(userInfo)){
					memberDomain.setUserInfo(userInfo);
				}
				//设置会员
				memberInfo = mbMap.get(memberDomain.getMemberId());
				if(CPSUtil.isNotEmpty(memberInfo)){
					memberDomain.setMemberInfo(memberInfo);
				}
				//设置域名
				orderDomain = domainMap.get(memberDomain.getDomainId()+"");
				if(CPSUtil.isNotEmpty(orderDomain)){
					memberDomain.setContentDomain(orderDomain);
				}
			}
		}
		
		return dList;
	}
	
	
	/**
	 * 根据ID获取域名
	 * @param domainId
	 * @return
	 */
	public SbMemberDomain findMemberContentDomainByID(Integer domainId){
		return memberContentDomainDao.findMemberContentDomainByID(domainId);
	}
	
	/**
	 * 保存域名
	 * @param domain
	 * @return
	 */
	public boolean saveMemberContentDomain(SbMemberDomain domain){
		return memberContentDomainDao.saveMemberContentDomain(domain);
	}
	
	/**
	 * 修改域名
	 * @param domain
	 * @return
	 */
	public boolean editMemberContentDomain(SbMemberDomain domain){
		return memberContentDomainDao.editMemberContentDomain(domain);
	}
	
	/**
	 * 获取所有域名
	 * @param domain
	 * @return
	 */
	public List<SbMemberDomain> queryMemberContentDomainList(SbMemberDomain domain){
		return memberContentDomainDao.queryMemberContentDomainList(domain);
	}
	
	/**
	 * 获取指定会员的域名
	 * @param stateId
	 * @return
	 */
	public List<SbMemberDomain> getMemberContentDomainList(List<Integer> memberId){
		return memberContentDomainDao.getMemberContentDomainList(memberId);
	}
	
	/**
	 * 获取指定会员的域名
	 * @param memberId
	 * @param domainId
	 */
	public SbMemberDomain getMemberContentDomain(Integer memberId,Integer domainId){
		SbMemberDomain domain = new SbMemberDomain();
		domain.setMemberId(memberId);
		domain.setDomainId(domainId);
		return memberContentDomainDao.find(domain);
	}
	
	
	/**
	 * 批量删除域名
	 * @param list
	 * @return
	 */
	public boolean trashMemberContentDomainList(List<SbMemberDomain> list){
		return memberContentDomainDao.trashMemberContentDomainList(list);
	}
	
	/**
	 * 批量删除域名
	 * @param domainIds
	 * @return
	 */
	public boolean trashMemberContentDomainList(String domainIds){
		boolean temp = false;
		List<SbMemberDomain> list = null;
		if(CPSUtil.isNotEmpty(domainIds)){
			list = new ArrayList<SbMemberDomain>();
			SbMemberDomain domainInfo = null;
			String domain_id [] = domainIds.split(",");
			for (String domainId : domain_id) {
				domainInfo = new SbMemberDomain();
				domainInfo.setMdId(Integer.valueOf(domainId));
				list.add(domainInfo);
			}
			temp = memberContentDomainDao.trashMemberContentDomainList(list);
		}
		return temp;
	}
	
	/**
	 * 删除域名
	 * @param role
	 * @return
	 */
	public boolean trashMemberContentDomainInfo(SbMemberDomain domain){
		return memberContentDomainDao.trashMemberContentDomainInfo(domain);
	}
	
	@Override
	protected BaseDao<SbMemberDomain> getDao() {
		return this.memberContentDomainDao;
	}

}
