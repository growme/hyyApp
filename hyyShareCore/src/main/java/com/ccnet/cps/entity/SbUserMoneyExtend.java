package com.ccnet.cps.entity;

public class SbUserMoneyExtend extends SbUserMoney {
	
    private Integer count;
	
    //会员资金统计使用
    private int registerNum;
    private double registerMoney;
    private int readawdNum;
    private double readawdMoney;
    private int deductNum;
    private double deductMoney;
    private int visitadNum;
    private double visitadMoney;
    private int redpackeNum;
    private double redpackeMoney;
    
	public SbUserMoneyExtend() {

	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	
}
