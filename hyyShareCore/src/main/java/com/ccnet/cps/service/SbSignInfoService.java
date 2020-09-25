package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbSignInfo;

/**
 * 签到业务
 * @author JackieWang
 *
 */
public interface SbSignInfoService extends BaseService<SbSignInfo> {
	
	/**
	 * 查询签到数据
	 * @param sbSignInfo
	 * @param paramDto
	 * @return
	 */
	List<SbSignInfo> findSbSignInfoList(SbSignInfo sbSignInfo,Dto paramDto);
	
	/**
	 * 查询签到
	 * @param userId
	 * @return
	 */
	public SbSignInfo getSbSignInfoById(Integer userId);
	
	
	/**
	 * 保存签到
	 * @param SbSignInfo
	 * @return
	 */
	public boolean saveSbSignInfo(SbSignInfo sbSignInfo);
	
	/**
	 * 保存签到数据
	 * @param SbSignInfo
	 * @return
	 */
	public boolean insertOrUpdateSbSignInfo(SbSignInfo signInfo);
	
	/**
	 * 修改用户
	 * @param sbSignInfo
	 * @return
	 */
	public boolean editSbSignInfo(SbSignInfo sbSignInfo);
	
	/**
	 * 删除签到
	 * @param SbSignInfo
	 * @return
	 */
	public boolean trashSbSignInfo(SbSignInfo sbSignInfo);
	
	/**
	 * 批量删除签到
	 * @param list
	 * @return
	 */
	public boolean trashSbSignInfoList(List<SbSignInfo> list);

}
