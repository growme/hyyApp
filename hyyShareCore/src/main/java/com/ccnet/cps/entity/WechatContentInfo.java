package com.ccnet.cps.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ccnet.core.entity.BaseEntity;

/**
 * 采集微信正文信息 ClassName: WechatContentInfo
 * 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-10-18
 */
public class WechatContentInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String contentTitle;
	private String contentText;
	private Date publicTime;
	private String wechatName;
	private List<String> picList = new ArrayList<String>();

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public String getWechatName() {
		return wechatName;
	}

	public void setWechatName(String wechatName) {
		this.wechatName = wechatName;
	}

	public List<String> getPicList() {
		return picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

}
