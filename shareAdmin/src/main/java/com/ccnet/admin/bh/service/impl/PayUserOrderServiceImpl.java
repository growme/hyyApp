package com.ccnet.admin.bh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.admin.bh.dao.PayUserOrderDao;
import com.ccnet.admin.bh.entity.PayUserOrder;
import com.ccnet.admin.bh.service.PayUserOrderService;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("payUserOrderService")
public class PayUserOrderServiceImpl extends BaseServiceImpl<PayUserOrder> implements PayUserOrderService {

	@Autowired
	private PayUserOrderDao payUserOrderDao;

	
	@Override
	protected BaseDao<PayUserOrder> getDao() {
		return this.payUserOrderDao;
	}
	
}
