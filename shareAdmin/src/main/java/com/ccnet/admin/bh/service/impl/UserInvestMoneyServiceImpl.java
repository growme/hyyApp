package com.ccnet.admin.bh.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.admin.bh.dao.UserInvestMoneyDao;
import com.ccnet.admin.bh.entity.UserInvestMoney;
import com.ccnet.admin.bh.service.UserInvestMoneyService;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("userInvestMoneyService")
public class UserInvestMoneyServiceImpl extends BaseServiceImpl<UserInvestMoney> implements UserInvestMoneyService {

	@Autowired
	private UserInvestMoneyDao userInvestMoneyDao;

	/**
	 * 添加
	 */
	public int add(UserInvestMoney table) {
		table.setCreateDate(new Date());
		return userInvestMoneyDao.insert(table);
	}

	/**
	 * 修改
	 */
	public int edit(UserInvestMoney table) {
		table.setCreateDate(new Date());
		return userInvestMoneyDao.update(table, table.getId().toString());
	}

	/**
	 * 删除
	 */
	public int delete(UserInvestMoney table) {
		return userInvestMoneyDao.delete(table);
	}

	/**
	 * 分页查询
	 * 
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page<?> findUserInvestMoneyByPage(UserInvestMoney user, Page<UserInvestMoney> page, Dto pdDto) {
		Page<?> userPage = userInvestMoneyDao.findUserByPage(user, page, pdDto);
		return userPage;
	}

	@Override
	protected BaseDao<UserInvestMoney> getDao() {
		return this.userInvestMoneyDao;
	}

}
