package com.ccnet.api.service;

import java.util.List;

import com.ccnet.api.entity.ApiMoneyCount;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbMoneyCount;

public interface ApiMoneyCountService extends BaseService<SbMoneyCount> {

	public List<ApiMoneyCount> getUserMoneyCountByUserIdAndType(Integer userId, Integer pageNum, Integer pageSize);

	ApiMoneyCount getUserMonetContent(String userId);

	ApiMoneyCount getNewUserContentLog(String userId);

	List<ApiMoneyCount> getUserMoneyByUserId(Integer userId, Integer pageNum, Integer pageSize);
}
