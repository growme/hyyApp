package com.ccnet.core.common;

/**
 * 记账状态
 * 
 * @author jackieWang
 * 
 */
public enum AccountState {

	unaccount(0, "未记账", "无效阅读，不记录阅读奖励！"), 
	account(1, "已记账", "有效阅读，记录阅读奖励！");

	private Integer stateId;
	private String stateName;
	private String stateDesc;

	private AccountState(Integer stateId, String stateName, String stateDesc) {
		this.stateId = stateId;
		this.stateName = stateName;
		this.stateDesc = stateDesc;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public static AccountState getAccountState(Integer state) {
		for (AccountState _state : values()) {
			if (_state.getStateId().equals(state)) {
				return _state;
			}
		}
		return null;
	}

}
