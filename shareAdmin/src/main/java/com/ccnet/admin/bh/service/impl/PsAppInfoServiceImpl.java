package com.ccnet.admin.bh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.admin.bh.dao.PsAppInfoDao;
import com.ccnet.admin.bh.entity.PsAppInfo;
import com.ccnet.admin.bh.service.PsAppInfoService;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("psAppInfoService")
public class PsAppInfoServiceImpl extends BaseServiceImpl<PsAppInfo> implements PsAppInfoService {

	@Autowired
	PsAppInfoDao psAppInfoDao;

	@Override
	protected BaseDao<PsAppInfo> getDao() {
		return psAppInfoDao;
	}

	@Override
	public Page<PsAppInfo> findPsAppInfoByPage(PsAppInfo psAppInfo, Page<PsAppInfo> page, Dto paramDto) {
		return psAppInfoDao.findSbCustomtaskByPage(psAppInfo, page, paramDto);
	}

	@Override
	public PsAppInfo findPsAppInfoByID(Integer psAppInfo_id) {
		PsAppInfo psAppInfo = new PsAppInfo();
		psAppInfo.setId(psAppInfo_id);
		return find(psAppInfo);
	}

}
