package com.ccnet.cps.entity;

import com.ccnet.core.entity.BaseEntity;

/**
 * ip计数器
 * 
 * @author JackieWang
 * 
 */
public class SbVisitCounter extends BaseEntity {

	private static final long serialVersionUID = 2325096821463234024L;
	
	private String visitIP; // 访问ip
	private Integer totalCount; // 记录数

	public String getVisitIP() {
		return visitIP;
	}

	public void setVisitIP(String visitIP) {
		this.visitIP = visitIP;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
