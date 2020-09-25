package com.ccnet.cps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SystemNoticeDao;
import com.ccnet.cps.entity.SystemNotice;
import com.ccnet.cps.service.SystemNoticeService;
@Service("systemNoticeService")
public class SystemNoticeServiceImpl extends BaseServiceImpl<SystemNotice> implements SystemNoticeService {
	
	@Autowired
	private SystemNoticeDao systemNoticeDao;
	
	/**
	 * 分页查询公告
	 * @param notice
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SystemNotice> findSystemNoticeByPage(SystemNotice notice, Page<SystemNotice> page,Dto pdDto){
		return systemNoticeDao.findSystemNoticeByPage(notice, page, pdDto);
	}
	
	/**
	 * 查询通知集合
	 * @param notice
	 * @return
	 */
	public List<SystemNotice> findSystemNoticeList(SystemNotice notice){
		return systemNoticeDao.findSystemNoticeList(notice);
	}
	
	
	/**
	 * 获取单个公告
	 * @param systemNotice
	 */
	public SystemNotice findSystemNoticeByID(SystemNotice systemNotice){
		return systemNoticeDao.findSystemNoticeByID(systemNotice);
	}
	
	/**
	 * 保存公告
	 * @param systemNotice
	 * @return
	 */
	public boolean saveSystemNotice(SystemNotice systemNotice){
		return systemNoticeDao.saveSystemNotice(systemNotice);
	}
	
	
	/**
	 * 修改公告
	 * @param systemNotice
	 * @return
	 */
	public boolean editSystemNotice(SystemNotice systemNotice){
		return systemNoticeDao.editSystemNotice(systemNotice);
	}
	
	/**
	 * 删除公告
	 * @param systemNotice
	 * @return
	 */
	public boolean trashSystemNotice(SystemNotice systemNotice){
		return systemNoticeDao.trashSystemNotice(systemNotice);
	}
	
	/**
	 * 批量修改状态
	 * @param list
	 * @param state
	 * @return
	 */
	public boolean batchUpdateNoticeSate(List<SystemNotice> list,Integer state){
		return systemNoticeDao.batchUpdateNoticeSate(list, state);
	}


	@Override
	protected BaseDao<SystemNotice> getDao() {
		return systemNoticeDao;
	}

}
