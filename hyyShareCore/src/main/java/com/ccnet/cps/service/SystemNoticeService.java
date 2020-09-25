package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SystemNotice;

public interface SystemNoticeService extends BaseService<SystemNotice> {

	/**
	 * 分页查询公告
	 * @param notice
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SystemNotice> findSystemNoticeByPage(SystemNotice notice, Page<SystemNotice> page,Dto pdDto);
	
	
	/**
	 * 查询通知集合
	 * @param notice
	 * @return
	 */
	public List<SystemNotice> findSystemNoticeList(SystemNotice notice);
	
	
	/**
	 * 获取单个公告
	 * @param systemNotice
	 */
	public SystemNotice findSystemNoticeByID(SystemNotice systemNotice);
	
	/**
	 * 保存公告
	 * @param systemNotice
	 * @return
	 */
	public boolean saveSystemNotice(SystemNotice systemNotice);
	
	
	/**
	 * 修改公告
	 * @param systemNotice
	 * @return
	 */
	public boolean editSystemNotice(SystemNotice systemNotice);
	
	/**
	 * 删除公告
	 * @param systemNotice
	 * @return
	 */
	public boolean trashSystemNotice(SystemNotice systemNotice);
	
	/**
	 * 批量修改状态
	 * @param list
	 * @param state
	 * @return
	 */
	public boolean batchUpdateNoticeSate(List<SystemNotice> list,Integer state);
	
}
