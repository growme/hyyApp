package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 文章图片 
 * ClassName: SbContentImg
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-9-5
 */
public class SbContentPic extends BaseEntity {

	private static final long serialVersionUID = 3978703311060723917L;
	private Integer picId;
	private String contentId;
	@IgnoreTableField
	private String picLableId;
	private String contentPic;
	private Integer sortNum;
	private Date createTime;

	
	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getPicLableId() {
		if(CPSUtil.isNotEmpty(getContentPic())){
			String picPath = getContentPic();
			String subPicStr = picPath.substring(picPath.lastIndexOf("/")+1,picPath.lastIndexOf("."));
			picLableId = "lb_index_"+subPicStr;
		}
		return picLableId;
	}

	public void setPicLableId(String picLableId) {
		this.picLableId = picLableId;
	}

	public String getContentPic() {
		return contentPic;
	}

	public void setContentPic(String contentPic) {
		this.contentPic = contentPic;
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

}
