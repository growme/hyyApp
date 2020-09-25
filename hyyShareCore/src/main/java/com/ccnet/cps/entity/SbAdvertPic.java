package com.ccnet.cps.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;

public class SbAdvertPic {

	private static final long serialVersionUID = 3978703311060723917L;
	private Integer picId;
	private String advertId;
	@IgnoreTableField
	private String picLableId;
	private String advertPic;
	private String advertPicLink;
	@IgnoreTableField
	private List<Map<String, String>> advertisePicList;
	private Integer sortNum;
	private Date createTime;
	
	public Integer getPicId() {
		return picId;
	}
	public void setPicId(Integer picId) {
		this.picId = picId;
	}
	public String getAdvertId() {
		return advertId;
	}
	public void setAdvertId(String advertId) {
		this.advertId = advertId;
	}
	public String getPicLableId() {
		if(CPSUtil.isNotEmpty(getAdvertPic())){
			String picPath = getAdvertPic();
			String subPicStr = picPath.substring(picPath.lastIndexOf("/")+1,picPath.indexOf("."));
			picLableId = "lb_index_"+subPicStr;
		}
		return picLableId;
	}
	public void setPicLableId(String picLableId) {
		this.picLableId = picLableId;
	}
	public String getAdvertPic() {
		return advertPic;
	}
	public void setAdvertPic(String advertPic) {
		this.advertPic = advertPic;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
