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
import com.ccnet.core.dao.impl.SbPromoteLinkDao;
import com.ccnet.core.entity.SbPromoteLink;
import com.ccnet.core.service.SbPromoteLinkService;

/**
 * 入口业务
 * @author jackieWang
 *
 */
@Service("sbPromoteLinkService")
public class SbPromoteLinkServiceImpl extends BaseServiceImpl<SbPromoteLink> implements SbPromoteLinkService {
	
	
	
	@Autowired
	private SbPromoteLinkDao sbPromoteLinkDao; 
	
	
	/**
	 * 分页查询入口(多过滤)
	 * @param link
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbPromoteLink> findSbPromoteLinkInfoByPage(SbPromoteLink link, Page<SbPromoteLink> page,Dto pdDto){
		return sbPromoteLinkDao.findSbPromoteLinkInfoByPage(link, page, pdDto);
	}
	
	
	/**
	 * 根据ID获取入口
	 * @param linkId
	 * @return
	 */
	public SbPromoteLink findSbPromoteLinkInfoByID(Integer linkId){
		return sbPromoteLinkDao.findSbPromoteLinkInfoByID(linkId);
	}
	
	
	/**
	 * 保存入口
	 * @param link
	 * @return
	 */
	public boolean saveSbPromoteLinkInfo(SbPromoteLink link){
		return sbPromoteLinkDao.saveSbPromoteLinkInfo(link);
	}
	
	/**
	 * 修改入口
	 * @param link
	 * @return
	 */
	public boolean editSbPromoteLinkInfo(SbPromoteLink link){
		return sbPromoteLinkDao.editSbPromoteLinkInfo(link);
	}
	
	/**
	 * 获取所有入口
	 * @param link
	 * @return
	 */
	public List<SbPromoteLink> querySbPromoteLinkList(SbPromoteLink link){
		return sbPromoteLinkDao.querySbPromoteLinkList(link);
	}
	
	/**
	 * 根据ID获取入口集合
	 * @param linkIds
	 * @return
	 */
	public List<SbPromoteLink> getSbPromoteLinkListByIds(List<Integer> linkIds){
		return sbPromoteLinkDao.getSbPromoteLinkListByIds(linkIds);
	}
	
	/**
	 * 转换集合数据
	 * @param linkIds
	 * @return
	 */
	public Map<Integer, SbPromoteLink> getSbPromoteLinkByIds(List<Integer> linkIds){
		return sbPromoteLinkDao.getSbPromoteLinkByIds(linkIds);
	}
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getSbPromoteLinkInfoList(List<Integer> stateId){
		return sbPromoteLinkDao.getSbPromoteLinkInfoList(stateId);
	}
	
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getValidPromoteLinkList() {
		return sbPromoteLinkDao.getSbPromoteLinkInfoList(DomainStateType.getValidateSate());//只获取有效的域名
	}
	
	
	/**
	 * 获取指定状态入口
	 * @param stateId
	 * @return
	 */
	public List<SbPromoteLink> getCheckValidPromoteLinkList(){
		return sbPromoteLinkDao.getSbPromoteLinkInfoList(DomainStateType.getValidateSate());//检测非屏蔽域名
	}
	
	
	/**
	 * 批量删除入口
	 * @param list
	 * @return
	 */
	public boolean trashSbPromoteLinkList(List<SbPromoteLink> list){
		return sbPromoteLinkDao.trashSbPromoteLinkList(list);
	}
	
	/**
	 * 删除入口
	 * @param role
	 * @return
	 */
	public boolean trashSbPromoteLinkInfo(SbPromoteLink link) {
		return sbPromoteLinkDao.trashSbPromoteLinkInfo(link);
	}
	
	
	/**
	 * 批量删除入口
	 * @param linkIds
	 * @return
	 */
	public boolean trashSbPromoteLinkList(String linkIds) {
		boolean temp = false;
		List<SbPromoteLink> list = null;
		if(CPSUtil.isNotEmpty(linkIds)){
			list = new ArrayList<SbPromoteLink>();
			SbPromoteLink link = null;
			String link_id [] = linkIds.split(",");
			for (String linkId : link_id) {
				link = new SbPromoteLink();
				link.setLinkId(Integer.valueOf(linkId));
				list.add(link);
			}
			temp = sbPromoteLinkDao.trashSbPromoteLinkList(list);
		}
		return temp;
	}
	
	
	@Override
	protected BaseDao<SbPromoteLink> getDao() {
		return sbPromoteLinkDao;
	}

}
