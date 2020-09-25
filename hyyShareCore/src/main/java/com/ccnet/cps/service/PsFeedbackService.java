package com.ccnet.cps.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.PsFeedback;

public interface PsFeedbackService extends BaseService<PsFeedback> {
	/**
	 * 分页查询日志(多过滤)
	 * 
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	Page<PsFeedback> findMemberLoginLogByPage(PsFeedback lg, Page<PsFeedback> page, Dto pdDto);
}
