package com.ccnet.core.common.cache;

/**
 * 扣量机制
 * 
 * @author jackieWang
 * 
 */
public class Discount {
	
	// 标准
	private Integer standard;
	// 扣量
	private Integer percent;

	public Discount(Integer standard, Integer percent) {
		this.standard = standard;
		this.percent = percent;
	}

	public Integer getStandard() {
		return standard;
	}

	public void setStandard(Integer standard) {
		this.standard = standard;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}
	
}
