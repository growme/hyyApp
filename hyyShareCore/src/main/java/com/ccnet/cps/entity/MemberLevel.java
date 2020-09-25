package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

/**
 * 会员等级
 * 
 * @author jackie wang
 * 
 */
public class MemberLevel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer levelId;
	private Integer levelCode;
	private String levelName;
	private Double minMoney;// 成交额最小值
	private Double maxMoney;// 成交额最大值
	private Double rewardMoney;// 额外奖励金额
	private Date createTime;
	
	public MemberLevel() {
		super();
	}
	
	public MemberLevel(Integer levelId) {
		this();
		setLevelId(levelId);
	}


	public Integer getLevelId() {
		return levelId;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Double getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}

	public Double getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Double getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(Double rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
