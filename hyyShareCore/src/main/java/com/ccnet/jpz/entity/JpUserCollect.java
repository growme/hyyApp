package com.ccnet.jpz.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class JpUserCollect extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userId;
	private Integer contentId;// 对应文章id
	private String type;
	private Date createDate;

	@IgnoreTableField
	private String title;// 文章标题
	@IgnoreTableField
	private String isTop;// 是否置顶
	@IgnoreTableField
	private String contentMemberId;// 文章发布人id
	@IgnoreTableField
	private String contentMemberName;// 文章发布人名称
	@IgnoreTableField
	private String contentUserId;
	@IgnoreTableField
	private String contentUserName;
	@IgnoreTableField
	private Integer commentNum;// 评论数
	@IgnoreTableField
	private String contentType;// checkType 1时 0文章 1视频 5小视频 checkType
								// 5时互助类型1资讯、2问答、3投票
	@IgnoreTableField
	private String images;// 图片 多个，分割

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getContentMemberId() {
		return contentMemberId;
	}

	public void setContentMemberId(String contentMemberId) {
		this.contentMemberId = contentMemberId;
	}

	public String getContentMemberName() {
		return contentMemberName;
	}

	public void setContentMemberName(String contentMemberName) {
		this.contentMemberName = contentMemberName;
	}

	public String getContentUserId() {
		return contentUserId;
	}

	public void setContentUserId(String contentUserId) {
		this.contentUserId = contentUserId;
	}

	public String getContentUserName() {
		return contentUserName;
	}

	public void setContentUserName(String contentUserName) {
		this.contentUserName = contentUserName;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
