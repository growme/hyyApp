package com.ccnet.api.entity;

import java.util.Date;

public class ApiMoneyCount {

	private String contentId;
	private Integer userId;
	private Integer mType;
	private Date createTime;
	private Double umoney;
	private Integer conCount;
	private String contentTitle;
	private String typeName;
	private String contentPic;
	private Integer vindex;
	private String vcode;

	public Integer getVindex() {
		return vindex;
	}

	public void setVindex(Integer vindex) {
		this.vindex = vindex;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public String getContentPic() {
		return contentPic;
	}

	public void setContentPic(String contentPic) {
		this.contentPic = contentPic;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getmType() {
		return mType;
	}

	public void setmType(Integer mType) {
		this.mType = mType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getUmoney() {
		return umoney;
	}

	public void setUmoney(Double umoney) {
		this.umoney = umoney;
	}

	public Integer getConCount() {
		return conCount;
	}

	public void setConCount(Integer conCount) {
		this.conCount = conCount;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

}
