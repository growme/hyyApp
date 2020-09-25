package com.ccnet.admin.bh.service;

import com.ccnet.admin.bh.entity.UserInvestMoney;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

public interface UserInvestMoneyService extends BaseService<UserInvestMoney> {

	Page findUserInvestMoneyByPage(UserInvestMoney user, Page<UserInvestMoney> page, Dto pdDto);

}
