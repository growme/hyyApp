package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PayState {

	submit(0,"已提交","sky"),
	underReview(1,"已审核","primary"),
	failured(3,"已失败","darkorange"),
	freezed(4,"已冻结","warning"),
	refused(5,"已拒付","danger"),
	drawback(6,"已退款","purple"),
	prepaid(2,"已支付","success");
	
	private Integer payStateId;
	private String payStateName;
	private String showColor;

	private PayState(Integer payStateId, String payStateName, String showColor) {
		this.payStateId = payStateId;
		this.payStateName = payStateName;
		this.showColor = showColor;
	}

	public Integer getPayStateId() {
		return payStateId;
	}

	public void setPayStateId(Integer payStateId) {
		this.payStateId = payStateId;
	}

	public String getPayStateName() {
		return payStateName;
	}

	public void setPayStateName(String payStateName) {
		this.payStateName = payStateName;
	}
	
	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	public static PayState getPayState(Integer state) {
		for (PayState payState : values()) {
			if (payState.getPayStateId().equals(state)) {
				return payState;
			}
		}
		return null;
	}
	
	//获取支付状态类型
	public static Map getPayState() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (PayState payState: values()) {
			map.put(payState.getPayStateId(), payState.getPayStateName());
		}
		return map;
	}
	
	//已完成支付
	public static boolean isCompletedCash(PayState state) {
		return PayState.prepaid.equals(state);
	}
	
	//提取审核状态
	public static List<PayState> allState() {
		List<PayState> states = new ArrayList<PayState>();
		for (PayState state : values()) {
			if (state.equals(PayState.submit) || state.equals(PayState.prepaid)) {
				continue;
			}else{
				states.add(state);
			}
		}
		return states;
	}
	
}
