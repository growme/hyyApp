package com.ccnet.jpz.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.jpz.entity.JpUserCollect;

public interface JpUserCollectService extends BaseService<JpUserCollect> {
	/**
	 * 分页查询(多过滤)
	 * 
	 * @param Info
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<JpUserCollect> findByPage(JpUserCollect table, Page<JpUserCollect> page, Dto pdDto);


	/**
	 * 根据ID获取
	 * 
	 * @param Ids
	 * @return
	 */
	public List<JpUserCollect> getListByIds(List<Integer> Ids);

	/**
	 * 获取单个
	 * @param Id
	 * @return
	 */
	public JpUserCollect getByID(Integer Id);

	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpUserCollect> list);

}
