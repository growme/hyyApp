package com.ccnet.cps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbSignInfoDao;
import com.ccnet.cps.entity.SbSignInfo;
import com.ccnet.cps.service.SbSignInfoService;

@Service("sbSignInfoService")
public class SbSignInfoServiceImpl extends BaseServiceImpl<SbSignInfo> implements SbSignInfoService {
	
	@Autowired
	private SbSignInfoDao sbSignInfoDao;
	
	/**
	 * 查询签到数据
	 * @param sbSignInfo
	 * @param paramDto
	 * @return
	 */
	public List<SbSignInfo> findSbSignInfoList(SbSignInfo sbSignInfo,Dto paramDto) {
		return sbSignInfoDao.findSbSignInfoList(sbSignInfo, paramDto);
	}
	
	/**
	 * 查询签到
	 * @param userId
	 * @return
	 */
	public SbSignInfo getSbSignInfoById(Integer userId){
		SbSignInfo sbSignInfo = new SbSignInfo();
		sbSignInfo.setUserId(userId);
		return sbSignInfoDao.getSbSignInfoById(sbSignInfo);
	}
	
	
	/**
	 * 保存签到
	 * @param SbSignInfo
	 * @return
	 */
	public boolean saveSbSignInfo(SbSignInfo sbSignInfo){
		return sbSignInfoDao.saveSbSignInfo(sbSignInfo);
	}
	
	/**
	 * 修改用户
	 * @param sbSignInfo
	 * @return
	 */
	public boolean editSbSignInfo(SbSignInfo sbSignInfo){
		return sbSignInfoDao.editSbSignInfo(sbSignInfo);
	}
	
	/**
	 * 删除签到
	 * @param SbSignInfo
	 * @return
	 */
	public boolean trashSbSignInfo(SbSignInfo sbSignInfo){
		return sbSignInfoDao.trashSbSignInfo(sbSignInfo);
	}
	
	/**
	 * 批量删除签到
	 * @param list
	 * @return
	 */
	public boolean trashSbSignInfoList(List<SbSignInfo> list){
		return sbSignInfoDao.trashSbSignInfoList(list);
	}
	
	/**
	 * 保存签到数据
	 * @param SbSignInfo
	 * @return
	 */
	public boolean insertOrUpdateSbSignInfo(SbSignInfo signInfo) {
		return sbSignInfoDao.insertOrUpdateSbSignInfo(signInfo) > 0 ? true : false;
	}

	protected BaseDao<SbSignInfo> getDao() {
		return sbSignInfoDao;
	}

}
