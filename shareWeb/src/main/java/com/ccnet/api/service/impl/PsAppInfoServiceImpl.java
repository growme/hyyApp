package com.ccnet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.PsAppInfoDao;
import com.ccnet.api.entity.PsAppInfo;
import com.ccnet.api.service.PsAppInfoService;
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

	@Override
	public List<PsAppInfo> findPsAppInfoList(PsAppInfo psAppInfo) {
		return psAppInfoDao.findSbCustomtaskList(psAppInfo);
	}

}
