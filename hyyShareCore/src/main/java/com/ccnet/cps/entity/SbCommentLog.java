package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 文章点赞日志 ClassName: SbCommentLog
 *  * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
public class SbCommentLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer commentId;
	private Integer contentId;
	@IgnoreTableField
	private SbContentInfo contentInfo;
	private Integer userId;
	@IgnoreTableField
	private MemberInfo memberInfo;
	private Integer commentType;
	private String commentIp;
	private String ipLocation;
	private Date createTime;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
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

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public String getCommentIp() {
		return commentIp;
	}

	public void setCommentIp(String commentIp) {
		this.commentIp = commentIp;
	}

	public String getIpLocation() {
		return ipLocation;
	}

	public void setIpLocation(String ipLocation) {
		this.ipLocation = ipLocation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SbContentInfo getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(SbContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

}
