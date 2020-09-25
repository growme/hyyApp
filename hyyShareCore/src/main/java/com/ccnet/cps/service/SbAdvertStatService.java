package com.ccnet.cps.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbAdvertStat;

public interface SbAdvertStatService extends BaseService<SbAdvertStat> {

	
	/**
	 * 分页查询(多过滤)
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbAdvertStat> findSbAdvertStatByPage(SbAdvertStat visitLog, Page<SbAdvertStat> page,Dto pdDto);
	
	
	
	
	
}
