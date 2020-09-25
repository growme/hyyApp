package com.ccnet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.ApiSbContentInfoDao;
import com.ccnet.api.service.ApiContentService;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.entity.JpUserCollect;

@Service("apiContentService")
public class ApiContentServiceImpl implements ApiContentService {

	@Autowired
	ApiSbContentInfoDao apiSbContentInfoDao;

	public int updateContentInfo(Integer id,String type,String code) {
		return apiSbContentInfoDao.updateContentInfo(id,type,code);
	}

	@Override
	public List<SbContentInfo> findTypeByPage(JpUserCollect jpUserCollect, Page<JpUserCollect> page) {
		return apiSbContentInfoDao.findTypeByPage(jpUserCollect,page);
	}
	
	@Override
	public List<SbContentInfo> findSbContentInfoByPage(SbContentInfo jpUserCollect, Page<SbContentInfo> page) {
		return apiSbContentInfoDao.findSbContentInfoByPage(jpUserCollect,page);
	}
}
