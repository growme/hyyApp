package com.ccnet.core.service;

import java.util.List;
import java.util.Map;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.core.service.base.BaseService;

public interface SbForwardLinkService extends BaseService<SbForwardLink> {
	
	
	/**
	 * 分页查询入口(多过滤)
	 * @param link
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbForwardLink> findSbForwardLinkInfoByPage(SbForwardLink link, Page<SbForwardLink> page,Dto pdDto);
	
	
	/**
	 * 根据ID获取入口
	 * @param linkId
	 * @return
	 */
	public SbForwardLink findSbForwardLinkInfoByID(Integer linkId);
	
	
	/**
	 * 保存入口
	 * @param link
	 * @return
	 */
	public boolean saveSbForwardLinkInfo(SbForwardLink link);
	
	/**
	 * 修改入口
	 * @param link
	 * @return
	 */
	public boolean editSbForwardLinkInfo(SbForwardLink link);
	
	/**
	 * 获取所有入口
	 * @param link
	 * @return
	 */
	public List<SbForwardLink> querySbForwardLinkList(SbForwardLink link);
	
	/**
	 * 根据ID获取入口集合
	 * @param linkIds
	 * @return
	 */
	public List<SbForwardLink> getSbForwardLinkListByIds(List<Integer> linkIds);
	
	/**
	 * 转换集合数据
	 * @param linkIds
	 * @return
	 */
	public Map<Integer, SbForwardLink> getSbForwardLinkByIds(List<Integer> linkIds);
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getSbForwardLinkInfoList(List<Integer> stateId);
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getCheckValidForwardLinkList();
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getValidForwardLinkList();
	
	/**
	 * 批量删除入口
	 * @param list
	 * @return
	 */
	public boolean trashSbForwardLinkList(List<SbForwardLink> list);
	
	/**
	 * 删除入口
	 * @param role
	 * @return
	 */
	public boolean trashSbForwardLinkInfo(SbForwardLink link);
	
	/**
	 * 批量删除入口
	 * @param linkIds
	 * @return
	 */
	public boolean trashSbForwardLinkList(String linkIds);

}
