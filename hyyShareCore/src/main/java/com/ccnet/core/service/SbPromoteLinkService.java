package com.ccnet.core.service;

import java.util.List;
import java.util.Map;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbPromoteLink;
import com.ccnet.core.service.base.BaseService;

public interface SbPromoteLinkService extends BaseService<SbPromoteLink> {
	
	
	/**
	 * 分页查询推广地址(多过滤)
	 * @param link
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbPromoteLink> findSbPromoteLinkInfoByPage(SbPromoteLink link, Page<SbPromoteLink> page,Dto pdDto);
	
	
	/**
	 * 根据ID获取推广地址
	 * @param linkId
	 * @return
	 */
	public SbPromoteLink findSbPromoteLinkInfoByID(Integer linkId);
	
	
	/**
	 * 保存推广地址
	 * @param link
	 * @return
	 */
	public boolean saveSbPromoteLinkInfo(SbPromoteLink link);
	
	/**
	 * 修改推广地址
	 * @param link
	 * @return
	 */
	public boolean editSbPromoteLinkInfo(SbPromoteLink link);
	
	/**
	 * 获取所有推广地址
	 * @param link
	 * @return
	 */
	public List<SbPromoteLink> querySbPromoteLinkList(SbPromoteLink link);
	
	/**
	 * 根据ID获取推广地址集合
	 * @param linkIds
	 * @return
	 */
	public List<SbPromoteLink> getSbPromoteLinkListByIds(List<Integer> linkIds);
	
	/**
	 * 转换集合数据
	 * @param linkIds
	 * @return
	 */
	public Map<Integer, SbPromoteLink> getSbPromoteLinkByIds(List<Integer> linkIds);
	
	/**
	 * 获取指定状态推广地址
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getSbPromoteLinkInfoList(List<Integer> stateId);
	
	/**
	 * 获取指定状态推广地址
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getCheckValidPromoteLinkList();
	
	/**
	 * 获取指定状态推广地址
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getValidPromoteLinkList();
	
	/**
	 * 批量删除推广地址
	 * @param list
	 * @return
	 */
	public boolean trashSbPromoteLinkList(List<SbPromoteLink> list);
	
	/**
	 * 删除推广地址
	 * @param role
	 * @return
	 */
	public boolean trashSbPromoteLinkInfo(SbPromoteLink link);
	
	/**
	 * 批量删除推广地址
	 * @param linkIds
	 * @return
	 */
	public boolean trashSbPromoteLinkList(String linkIds);

}
