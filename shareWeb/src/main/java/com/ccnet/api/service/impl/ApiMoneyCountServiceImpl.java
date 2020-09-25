package com.ccnet.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.ApiMoneyCountDao;
import com.ccnet.api.entity.ApiMoneyCount;
import com.ccnet.api.service.ApiMoneyCountService;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.entity.SbMoneyCount;

@Service("apiMoneyCountService")
public class ApiMoneyCountServiceImpl extends BaseServiceImpl<SbMoneyCount> implements ApiMoneyCountService {

	@Autowired
	ApiMoneyCountDao apiMoneyCountDao;

	@Override
	public List<ApiMoneyCount> getUserMoneyCountByUserIdAndType(Integer userId,Integer pageNum, Integer pageSize) {
		return apiMoneyCountDao.getUserMoneyCountByUserIdAndType(userId,pageNum, pageSize);
	}

	@Override
	public List<ApiMoneyCount> getUserMoneyByUserId(Integer userId,Integer pageNum, Integer pageSize) {
		return apiMoneyCountDao.getUserMoneyByUserId(userId,pageNum, pageSize);
	}

	@Override
	public ApiMoneyCount getUserMonetContent(String userId) {
		return apiMoneyCountDao.getUserMonetContent(userId);
	}

	@Override
	public ApiMoneyCount getNewUserContentLog(String userId) {
		return apiMoneyCountDao.getNewUserContentLog(userId);
	}

	@Override
	protected BaseDao<SbMoneyCount> getDao() {
		return null;
	}
}
