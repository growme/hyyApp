package com.ccnet.core.common;

/**
 * 会员等级（每天23:55自动处理升级）
 * @author JackieWang
 *
 */
public enum MemeberLevelType {
	
	REGULAR("普通会员", 0, 0.00, 1.0,"success"), 
	GOLDEN("黄金会员", 1, 50.00, 1.2,"darkorange"), 
	PLATINUM("白金会员",2, 100.00, 1.4,"danger"), 
	DIAMOND("钻石会员", 3, 300.00, 1.6,"maroon");

	private String name;//等级名称
	private Integer type;//等级变量
	private Double moneyCount;//前一天的收益总额
	private Double percent;//阅读收益倍数
	private String showColor;//阅读收益倍数
	
	private MemeberLevelType(String name, Integer type, Double moneyCount,Double percent,String showColor) {
		this.name = name;
		this.type = type;
		this.moneyCount = moneyCount;
		this.percent = percent;
		this.showColor = showColor;
	}

	public String getName() {
		return name;
	}

	public Integer getType() {
		return type;
	}

	public static MemeberLevelType getMemeberLevelType(Integer type) {
		for (MemeberLevelType levelType : values()) {
			if (levelType.getType().equals(type)) {
				return levelType;
			}
		}
		return null;
	}

	public Double getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(Double moneyCount) {
		this.moneyCount = moneyCount;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

}
