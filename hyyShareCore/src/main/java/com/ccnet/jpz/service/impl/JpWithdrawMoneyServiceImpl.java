package com.ccnet.jpz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpWithdrawMoneyDao;
import com.ccnet.jpz.entity.JpWithdrawMoney;
import com.ccnet.jpz.service.JpWithdrawMoneyService;

@Service("jpWithdrawMoneyService")
public class JpWithdrawMoneyServiceImpl extends BaseServiceImpl<JpWithdrawMoney> implements JpWithdrawMoneyService {

	@Autowired
	private JpWithdrawMoneyDao JpWithdrawMoneyDao;

	@Override
	protected BaseDao<JpWithdrawMoney> getDao() {
		return JpWithdrawMoneyDao;
	}

	@Override
	public Page<JpWithdrawMoney> findByPage(JpWithdrawMoney table, Page<JpWithdrawMoney> page, Dto pdDto) {
		return JpWithdrawMoneyDao.findByPage(table, page, pdDto);
	}

	@Override
	public List<JpWithdrawMoney> getListByIds(List<Integer> Ids) {
		return JpWithdrawMoneyDao.getListByIds(Ids);
	}

	@Override
	public JpWithdrawMoney getByID(Integer Id) {
		return JpWithdrawMoneyDao.getByID(Id);
	}

	@Override
	public boolean trashList(List<JpWithdrawMoney> list) {
		return JpWithdrawMoneyDao.trashList(list);
	}

}
