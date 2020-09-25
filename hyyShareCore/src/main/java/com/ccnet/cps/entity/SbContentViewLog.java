package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 文章访问日志
 * 
 * @author jackie
 * 
 */
public class SbContentViewLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer viewId;
	private Integer contentId;
	private Integer userId;
	private String hashKey;
	private String requestIp;
	private String requestDetail;
	private Date viewTime;
	private Integer givenMoney;
	private Integer wechatBrowser;// 是否微信浏览器
	@IgnoreTableField
	private MemberInfo memberInfo;
	@IgnoreTableField
	private SbContentInfo contentInfo;
	
	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}
	
	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(String requestDetail) {
		this.requestDetail = requestDetail;
	}

	public Date getViewTime() {
		return viewTime;
	}

	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}

	public Integer getWechatBrowser() {
		return wechatBrowser;
	}

	public void setWechatBrowser(Integer wechatBrowser) {
		this.wechatBrowser = wechatBrowser;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public SbContentInfo getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(SbContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public Integer getGivenMoney() {
		return givenMoney;
	}

	public void setGivenMoney(Integer givenMoney) {
		this.givenMoney = givenMoney;
	}
	
}
