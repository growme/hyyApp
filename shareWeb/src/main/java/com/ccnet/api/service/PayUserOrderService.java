package com.ccnet.api.service;

import com.ccnet.api.entity.PayUserOrder;
import com.ccnet.core.service.base.BaseService;

public interface PayUserOrderService extends BaseService<PayUserOrder> {

	public PayUserOrder getByOrderNo(String orderId);
	public Boolean saveMoney();
	public Boolean saveMoney(Integer userId, double money, int mtype, Integer contentId, String contentHashKey);
}
