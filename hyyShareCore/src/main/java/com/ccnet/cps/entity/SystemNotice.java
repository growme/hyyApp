package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.common.NoticeType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;
/**
 * 公告信息
 * @author jackie wang
 *
 */
public class SystemNotice extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer noticeId;
	private String noticeTitle;
	private String noticeContent;
	private Integer showTop;
	private Integer state;
	@IgnoreTableField
	private String stateName;
	private Integer noticeType;
	@IgnoreTableField
	private String typeName;
	private Date createTime;
	private Integer orderNumber;

	public Integer getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}

	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public Integer getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}

	public Integer getShowTop() {
		return showTop;
	}

	public void setShowTop(Integer showTop) {
		this.showTop = showTop;
	}
	
	public String getTypeName() {
		if(CPSUtil.isNotEmpty(getNoticeType())){
			typeName = NoticeType.getNoticeType(getNoticeType()).getName();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	

}
