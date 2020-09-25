package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbAdvertInfo;

public interface SbAdvertiseInfoService extends BaseService<SbAdvertInfo> {
	/**
	 * 分页查询广告(多过滤)
	 * 
	 * @param SbAdvertInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findSbAdvertiseInfoByPage(SbAdvertInfo sbAdvertInfo, Page<SbAdvertInfo> page, Dto pdDto);

	/**
	 * 查询所有广告 设置广告图片
	 */
	public List<SbAdvertInfo> findSbAdvertiseInfos();

	/**
	 * 修改广告
	 * 
	 * @param adId
	 * @return
	 */
	public SbAdvertInfo getSbAdvertInfoByID(Integer adId);

	/**
	 * 广告保存
	 * 
	 * @param sbAdvertInfo
	 * @return
	 */
	public boolean saveSbAdvertInfo(SbAdvertInfo sbAdvertInfo);

	/**
	 * 删除广告
	 * 
	 * @param sbAdvertInfo
	 * @return
	 */
	public boolean trashSbAdvertInfo(SbAdvertInfo sbAdvertInfo);

	/**
	 * 批量删除广告
	 * 
	 * @param advertIds
	 * @return
	 */
	public boolean trashSbAdvertInfo(String advertIds);

	/**
	 * 随机获取广告
	 * 
	 * @param i
	 * @return
	 */
	public List<SbAdvertInfo> getRandomAdvert(int i);

	/**
	 * 根据广告类型获取广告列表
	 * 
	 * @return
	 */
	public List<SbAdvertInfo> getSbAdvertInfoByType(int typeId);

	/**
	 * 批量修改状态
	 * 
	 * @param adIds
	 * @param state
	 * @return
	 */
	public boolean updateAdvertiseStateById(String adIds, Integer state);

	/**
	 * 根据用户广告时间查询
	 * 
	 * @return
	 */
	public List<SbAdvertInfo> getAdvertInfosByAdIdAndDate(Integer userId, Integer asId, String date);

}
