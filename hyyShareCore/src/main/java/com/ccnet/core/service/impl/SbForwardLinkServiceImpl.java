package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.SbForwardLinkDao;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.core.service.SbForwardLinkService;

/**
 * 入口业务
 * @author jackieWang
 *
 */
@Service("sbForwardLinkService")
public class SbForwardLinkServiceImpl extends BaseServiceImpl<SbForwardLink> implements SbForwardLinkService {
	
	
	
	@Autowired
	private SbForwardLinkDao sbForwardLinkDao; 
	
	
	/**
	 * 分页查询入口(多过滤)
	 * @param link
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbForwardLink> findSbForwardLinkInfoByPage(SbForwardLink link, Page<SbForwardLink> page,Dto pdDto){
		return sbForwardLinkDao.findSbForwardLinkInfoByPage(link, page, pdDto);
	}
	
	
	/**
	 * 根据ID获取入口
	 * @param linkId
	 * @return
	 */
	public SbForwardLink findSbForwardLinkInfoByID(Integer linkId){
		return sbForwardLinkDao.findSbForwardLinkInfoByID(linkId);
	}
	
	
	/**
	 * 保存入口
	 * @param link
	 * @return
	 */
	public boolean saveSbForwardLinkInfo(SbForwardLink link){
		return sbForwardLinkDao.saveSbForwardLinkInfo(link);
	}
	
	/**
	 * 修改入口
	 * @param link
	 * @return
	 */
	public boolean editSbForwardLinkInfo(SbForwardLink link){
		return sbForwardLinkDao.editSbForwardLinkInfo(link);
	}
	
	/**
	 * 获取所有入口
	 * @param link
	 * @return
	 */
	public List<SbForwardLink> querySbForwardLinkList(SbForwardLink link){
		return sbForwardLinkDao.querySbForwardLinkList(link);
	}
	
	/**
	 * 根据ID获取入口集合
	 * @param linkIds
	 * @return
	 */
	public List<SbForwardLink> getSbForwardLinkListByIds(List<Integer> linkIds){
		return sbForwardLinkDao.getSbForwardLinkListByIds(linkIds);
	}
	
	/**
	 * 转换集合数据
	 * @param linkIds
	 * @return
	 */
	public Map<Integer, SbForwardLink> getSbForwardLinkByIds(List<Integer> linkIds){
		return sbForwardLinkDao.getSbForwardLinkByIds(linkIds);
	}
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getSbForwardLinkInfoList(List<Integer> stateId){
		return sbForwardLinkDao.getSbForwardLinkInfoList(stateId);
	}
	
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getValidForwardLinkList() {
		return sbForwardLinkDao.getSbForwardLinkInfoList(DomainStateType.getValidateSate());//只获取有效的域名
	}
	
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbForwardLink> getCheckValidForwardLinkList(){
		return sbForwardLinkDao.getSbForwardLinkInfoList(DomainStateType.getValidateSate());//检测非屏蔽域名
	}
	
	
	/**
	 * 批量删除入口
	 * @param list
	 * @return
	 */
	public boolean trashSbForwardLinkList(List<SbForwardLink> list){
		return sbForwardLinkDao.trashSbForwardLinkList(list);
	}
	
	/**
	 * 删除入口
	 * @param role
	 * @return
	 */
	public boolean trashSbForwardLinkInfo(SbForwardLink link) {
		return sbForwardLinkDao.trashSbForwardLinkInfo(link);
	}
	
	
	/**
	 * 批量删除入口
	 * @param linkIds
	 * @return
	 */
	public boolean trashSbForwardLinkList(String linkIds) {
		boolean temp = false;
		List<SbForwardLink> list = null;
		if(CPSUtil.isNotEmpty(linkIds)){
			list = new ArrayList<SbForwardLink>();
			SbForwardLink link = null;
			String link_id [] = linkIds.split(",");
			for (String linkId : link_id) {
				link = new SbForwardLink();
				link.setLinkId(Integer.valueOf(linkId));
				list.add(link);
			}
			temp = sbForwardLinkDao.trashSbForwardLinkList(list);
		}
		return temp;
	}
	
	
	@Override
	protected BaseDao<SbForwardLink> getDao() {
		return sbForwardLinkDao;
	}

}
