package com.ccnet.cps.entity;

import java.util.Date;

import net.sf.ehcache.pool.sizeof.annotations.IgnoreSizeOf;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 今日排行
 * 
 * @author Administrator
 * 
 */
public class TotalMoneyRank extends BaseEntity {

	private static final long serialVersionUID = 808985660864342488L;
	
	private Integer rankId;
	private String rankName;
	private Double totalMoney;
	private String rankMobile;
	@IgnoreTableField
	private String frankMobile;
	private Date updateTime;

	public Integer getRankId() {
		return rankId;
	}

	public void setRankId(Integer rankId) {
		this.rankId = rankId;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getRankMobile() {
		return rankMobile;
	}

	public void setRankMobile(String rankMobile) {
		this.rankMobile = rankMobile;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFrankMobile() {
		if(CPSUtil.isNotEmpty(getRankMobile())){
			frankMobile = getRankMobile().substring(0, 3) + "*****" + getRankMobile().substring(8, 11);
		}
		return frankMobile;
	}

	public void setFrankMobile(String frankMobile) {
		this.frankMobile = frankMobile;
	}

}
