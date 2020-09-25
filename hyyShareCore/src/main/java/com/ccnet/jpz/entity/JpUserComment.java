package com.ccnet.jpz.entity;

import java.util.Date;
import java.util.List;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class JpUserComment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer contentId;//文章id
	private Integer userId;//用户id
	private String content;//评论内容
	private Integer praiseNum;//点赞数
	private Integer replyNum;//回复数
	private Date createDate;
	private Integer replyType;//1回复文章 2回复评论
	private Integer toUid;//目标用户 id
	private Integer toCommentId;//回复评论id
	private Integer firstCommentId;
	private String pingbi;
	@IgnoreTableField
	private List<JpUserComment> commentList;
	@IgnoreTableField
	private String userName;//用户名称
	@IgnoreTableField
	private Boolean louzhu = false;
	@IgnoreTableField
	private Boolean zuozhe = false;
	@IgnoreTableField
	private Boolean guanzhu = false;
	@IgnoreTableField
	private String userIcon;//
	@IgnoreTableField
	private String toUserName;//被回复人名
	@IgnoreTableField
	private String toContent;//被回复的评论内容
	@IgnoreTableField
	private String isLike;// 是否点赞 1点赞
	@IgnoreTableField
	private String contentTitle;//文章内容
	@IgnoreTableField
	private String checkType;//1文章,2活动,3招聘,4供求,5互助圈
	@IgnoreTableField
	private String images;//文章图片
	@IgnoreTableField
	private String optionInfo;// x选项（json串）
	@IgnoreTableField
	private String contentType;// checkType 1时 0文章 1视频 5小视频 checkType
								// 5时互助类型1资讯、2问答、3投票
	@IgnoreTableField
	private Integer voteNum;// 投票数
	@IgnoreTableField
	private String type;//2点赞
	@IgnoreTableField
	private String contentMemberId;// 文章发布人id
	@IgnoreTableField
	private String contentMemberName;// 文章发布人名称

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getVoteNum() {
		return voteNum;
	}

	public void setVoteNum(Integer voteNum) {
		this.voteNum = voteNum;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getOptionInfo() {
		return optionInfo;
	}

	public void setOptionInfo(String optionInfo) {
		this.optionInfo = optionInfo;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getIsLike() {
		return isLike;
	}

	public void setIsLike(String isLike) {
		this.isLike = isLike;
	}

	public String getPingbi() {
		return pingbi;
	}

	public void setPingbi(String pingbi) {
		this.pingbi = pingbi;
	}

	public Integer getFirstCommentId() {
		return firstCommentId;
	}

	public void setFirstCommentId(Integer firstCommentId) {
		this.firstCommentId = firstCommentId;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToContent() {
		return toContent;
	}

	public void setToContent(String toContent) {
		this.toContent = toContent;
	}

	public Boolean getLouzhu() {
		return louzhu;
	}

	public void setLouzhu(Boolean louzhu) {
		this.louzhu = louzhu;
	}

	public Boolean getZuozhe() {
		return zuozhe;
	}

	public void setZuozhe(Boolean zuozhe) {
		this.zuozhe = zuozhe;
	}

	public Boolean getGuanzhu() {
		return guanzhu;
	}

	public void setGuanzhu(Boolean guanzhu) {
		this.guanzhu = guanzhu;
	}

	public Integer getToCommentId() {
		return toCommentId;
	}

	public void setToCommentId(Integer toCommentId) {
		this.toCommentId = toCommentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public List<JpUserComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<JpUserComment> commentList) {
		this.commentList = commentList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public Integer getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(Integer replyNum) {
		this.replyNum = replyNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}

	public Integer getToUid() {
		return toUid;
	}

	public void setToUid(Integer toUid) {
		this.toUid = toUid;
	}

}
