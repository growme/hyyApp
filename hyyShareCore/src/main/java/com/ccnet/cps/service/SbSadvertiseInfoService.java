package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbSadvertInfo;;

/*
 * 脚本广告业务接口
 */
public interface SbSadvertiseInfoService extends BaseService<SbSadvertInfo> {
	/**
	 * 分页查询用户(多过滤)
	 * @param SbContentInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findSbSadvertiseInfoByPage(SbSadvertInfo sbSadvertInfo, Page<SbSadvertInfo> page,Dto pdDto);
	
	/**
	 * 修改广告
	 * @param adId
	 * @return
	 */
	public SbSadvertInfo getSbAdvertInfoByID(Integer adId);
	/**
	 * 广告保存
	 * @param sbAdvertInfo
	 * @return
	 */
	public boolean saveSbAdvertInfo(SbSadvertInfo sbSadvertInfo);
	/**删除广告
	 * @param sbAdvertInfo
	 * @return
	 */
	public boolean trashSbAdvertInfo(SbSadvertInfo sSAadvertInfo);

	public List<SbSadvertInfo> getRandomAdvert(int i);
}
