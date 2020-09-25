package com.ccnet.cps.entity;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;
import com.ccnet.core.entity.UserInfo;

/**
 * 文章信息 ClassName: SbContentInfo
 * 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-8-10
 */
public class SbContentInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer contentId;
	private String contentCode;
	private String contentTitle;
	private String contentSbTitle;
	@IgnoreTableField
	private String filterTitle;//过滤标题
	private String contentText;
	private String weixinName;
	private String weixinLink;
	private Integer readNum;//真实阅读数
	private String visualReadNum;//虚拟阅读数
	private Integer checkState;
	@IgnoreTableField
	private String checkStateName;
	private Integer orderNo;
	private Date createTime;
	private Integer shareNum;//真实分享次数
	private String clickNum;//虚拟点赞数
	private String contentPicLink;//外网图片链接
	
	@IgnoreTableField
	private String contentPic;
	@IgnoreTableField
	private String contentPics;
	@IgnoreTableField
	private List<SbContentPic> picList;
	private Integer columnId;
	@IgnoreTableField
	private SbColumnInfo columnInfo;
	private Integer userId;
	@IgnoreTableField
	private UserInfo userInfo;
	@IgnoreTableField
	private String userName;
	private String videoLink;
	private Integer contentType;
	@IgnoreTableField
	private String contentTypeName;
	private Integer videoNumber;
	private Integer homeToped;
	@IgnoreTableField
	private String homeTopedName;
	private Date topedTime;
	private Integer gatherPic;
	private Double readAward;
	private Double friendShareAward;
	private Double timelineShareAward;
	private String articleUrl;//小说文章链接
	private Integer urlGather;//采集广告 防止拦截
	
	@IgnoreTableField
	private String shortUrl;
	@IgnoreTableField
	private SbContentComment commentInfo;
	
	private String highPriceAd;
	private Integer commentCount;
	private Integer collectNum; // 关注数量
	
	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Integer collectNum) {
		this.collectNum = collectNum;
	}

	public String getHighPriceAd() {
		return highPriceAd;
	}

	public void setHighPriceAd(String highPriceAd) {
		this.highPriceAd = highPriceAd;
	}

	public SbContentInfo() {
		super();
	}

	public SbColumnInfo getColumnInfo() {
		return columnInfo;
	}

	public void setColumnInfo(SbColumnInfo columnInfo) {
		this.columnInfo = columnInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getContentTypeName() {
		if(CPSUtil.isNotEmpty(getContentType())){
			contentTypeName = ContentType.getContentType(getContentType()).getName();
		}
		return contentTypeName;
	}

	public void setContentTypeName(String contentTypeName) {
		this.contentTypeName = contentTypeName;
	}

	public SbContentInfo(Integer contentId) {
		this();
		setContentId(contentId);
	}

	public Integer getContentId() {
		return contentId;
	}
	
	public String getContentCode() {
		return contentCode;
	}

	public void setContentCode(String contentCode) {
		this.contentCode = contentCode;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getGatherPic() {
		return gatherPic;
	}

	public void setGatherPic(Integer gatherPic) {
		this.gatherPic = gatherPic;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentSbTitle() {
		return contentSbTitle;
	}

	public void setContentSbTitle(String contentSbTitle) {
		this.contentSbTitle = contentSbTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getWeixinName() {
		return weixinName;
	}

	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	public String getWeixinLink() {
		return weixinLink;
	}

	public void setWeixinLink(String weixinLink) {
		this.weixinLink = weixinLink;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getShareNum() {
		return shareNum;
	}

	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}

	public String getClickNum() {
		return clickNum;
	}

	public void setClickNum(String clickNum) {
		this.clickNum = clickNum;
	}

	public String getContentPic() {
		return contentPic;
	}

	public void setContentPic(String contentPic) {
		this.contentPic = contentPic;
	}

	public Integer getColumnId() {
		return columnId;
	}

	public void setColumnId(Integer columnId) {
		this.columnId = columnId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getVideoNumber() {
		return videoNumber;
	}

	public void setVideoNumber(Integer videoNumber) {
		this.videoNumber = videoNumber;
	}

	public Integer getHomeToped() {
		return homeToped;
	}

	public void setHomeToped(Integer homeToped) {
		this.homeToped = homeToped;
	}

	public Date getTopedTime() {
		return topedTime;
	}

	public void setTopedTime(Date topedTime) {
		this.topedTime = topedTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<SbContentPic> getPicList() {
		return picList;
	}

	public void setPicList(List<SbContentPic> picList) {
		this.picList = picList;
	}

	public String getContentPics() {
		return contentPics;
	}

	public void setContentPics(String contentPics) {
		this.contentPics = contentPics;
	}
	public Double getReadAward() {
		return readAward;
	}

	public void setReadAward(Double readAward) {
		this.readAward = readAward;
	}

	public Double getFriendShareAward() {
		return friendShareAward;
	}

	public void setFriendShareAward(Double friendShareAward) {
		this.friendShareAward = friendShareAward;
	}

	public Double getTimelineShareAward() {
		return timelineShareAward;
	}

	public void setTimelineShareAward(Double timelineShareAward) {
		this.timelineShareAward = timelineShareAward;
	}

	public String getCheckStateName() {
		return checkStateName;
	}

	public void setCheckStateName(String checkStateName) {
		this.checkStateName = checkStateName;
	}

	public String getHomeTopedName() {
		return homeTopedName;
	}

	public void setHomeTopedName(String homeTopedName) {
		this.homeTopedName = homeTopedName;
	}

	public SbContentComment getCommentInfo() {
		return commentInfo;
	}

	public void setCommentInfo(SbContentComment commentInfo) {
		this.commentInfo = commentInfo;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getVisualReadNum() {
		return visualReadNum;
	}

	public void setVisualReadNum(String visualReadNum) {
		this.visualReadNum = visualReadNum;
	}

	public String getContentPicLink() {
		return contentPicLink;
	}

	public void setContentPicLink(String contentPicLink) {
		this.contentPicLink = contentPicLink;
	}

	public String getFilterTitle() {
		
		if(CPSUtil.isNotEmpty(getContentTitle())){
			//替换标题中的标签
			filterTitle = getContentTitle().replaceAll("(\\{)([\\w]+)(\\})", "");
		}
		
		return filterTitle;
	}

	public void setFilterTitle(String filterTitle) {
		this.filterTitle = filterTitle;
	}

	public Integer getUrlGather() {
		return urlGather;
	}

	public void setUrlGather(Integer urlGather) {
		this.urlGather = urlGather;
	}
	
}
