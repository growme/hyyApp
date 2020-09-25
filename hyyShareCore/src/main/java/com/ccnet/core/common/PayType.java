package com.ccnet.core.common;

import java.util.HashMap;
import java.util.Map;

public enum PayType {

	alipay(0,"支付宝"),
	ebank(1,"微信");
	
	private Integer payId;
	private String  payName;
	
	private PayType(Integer payId, String payName) {
		this.payId = payId;
		this.payName = payName;
	}

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public String getPayName() {
		return payName;
	}

	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	public static PayType getPayType(Integer type) {
		for (PayType payType : values()) {
			if (payType.getPayId().equals(type)) {
				return payType;
			}
		}
		return null;
	}
	
	//获取支付类型
	public static Map getPayType() {
		Map<Integer,String> map=new HashMap<Integer,String>();
		for (PayType payType : values()) {
			map.put(payType.getPayId(),payType.getPayName());
		}
		return map;
	}

}
