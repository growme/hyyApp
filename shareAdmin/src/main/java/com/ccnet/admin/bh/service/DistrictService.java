package com.ccnet.admin.bh.service;

import java.util.List;

import com.ccnet.admin.bh.entity.District;
import com.ccnet.core.service.base.BaseService;

public interface DistrictService extends BaseService<District> {

	public List<District> findDistrictDaoByList(District district);

}
