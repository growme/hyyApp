package com.ccnet.admin.bh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.admin.bh.dao.DistrictDao;
import com.ccnet.admin.bh.entity.District;
import com.ccnet.admin.bh.service.DistrictService;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("districtService")
public class DistrictServiceImpl extends BaseServiceImpl<District> implements DistrictService {

	@Autowired
	DistrictDao districtDao;

	@Override
	public List<District> findDistrictDaoByList(District district){
		return districtDao.findDistrictDaoByList(district);
	}
	
	@Override
	protected BaseDao<District> getDao() {
		return this.districtDao;
	}

}
