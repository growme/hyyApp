package com.ccnet.cps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.PsFeedbackDao;
import com.ccnet.cps.entity.PsFeedback;
import com.ccnet.cps.service.PsFeedbackService;

@Service("psFeedbackService")
public class PsFeedbackServiceImpl extends BaseServiceImpl<PsFeedback> implements PsFeedbackService{

	@Autowired
	PsFeedbackDao psFeedbackDao;
	
	@Override
	protected BaseDao<PsFeedback> getDao() {
		return psFeedbackDao;
	}

	@Override
	public Page<PsFeedback> findMemberLoginLogByPage(PsFeedback lg, Page<PsFeedback> page, Dto pdDto) {
		return psFeedbackDao.findPsFeedbackByPage(lg, page, pdDto);
	}

}
