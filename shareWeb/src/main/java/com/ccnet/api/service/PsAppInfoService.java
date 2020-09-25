package com.ccnet.api.service;

import java.util.List;

import com.ccnet.api.entity.PsAppInfo;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

public interface PsAppInfoService extends BaseService<PsAppInfo> {

	Page<PsAppInfo> findPsAppInfoByPage(PsAppInfo psAppInfo, Page<PsAppInfo> page, Dto paramDto);

	PsAppInfo findPsAppInfoByID(Integer psAppInfo_id);
	
	List<PsAppInfo> findPsAppInfoList(PsAppInfo psAppInfo);

}
