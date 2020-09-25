package com.ccnet.cps.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.core.common.AdType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;
import com.ccnet.core.entity.UserInfo;

public class SbAdvertInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private Integer adId;
	private String adTitle;
	@IgnoreTableField
	private String adShowTitle;
	private String adPic;
	@IgnoreTableField
	private String adShowPic;
	private String adLink;
	private String adLink2;
	private Integer state;
	private Date createTime;
	private String sortNo;
	private String remark;
	private Integer userId;
	private Integer readNum;
	private String adTag;
	@IgnoreTableField
	private String adTagName;
	private String adScript;
	private Integer adType;
	@IgnoreTableField
	private String adTypeName;
	private String adCode;// 广告唯一码
	@IgnoreTableField
	private List<SbAdvertPic> picList;// 广告图片列表
	@IgnoreTableField
	private List<Map<String, String>> adTitleList;
	@IgnoreTableField
	private List<Map<String, String>> adPicList;
	@IgnoreTableField
	private String advertPic;// 广告列表预览图
	// 预览图列表
	@IgnoreTableField
	private String advertPics;// 图片路径以，分隔
	// 在线开始时间
	private String startTime;
	// 在线结束时间
	private String endTime;
	@IgnoreTableField
	private Integer showState;// 判断是否展示
	// 截止时间
	private String endDate;
	@IgnoreTableField
	private String jsTitle;// 加密广告标题

	@IgnoreTableField
	private UserInfo userInfo;

	private Integer adUserId;// 广告主id

	private Double unitPrice;// 广告点击单价

	private String districtIds;
	private Double chargeBalance;// 余额
	private Double total;// 总额

	public Double getChargeBalance() {
		return chargeBalance;
	}

	public void setChargeBalance(Double chargeBalance) {
		this.chargeBalance = chargeBalance;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getDistrictIds() {
		return districtIds;
	}

	public void setDistrictIds(String districtIds) {
		this.districtIds = districtIds;
	}

	public String getAdLink2() {
		return adLink2;
	}

	public void setAdLink2(String adLink2) {
		this.adLink2 = adLink2;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getAdUserId() {
		return adUserId;
	}

	public void setAdUserId(Integer adUserId) {
		this.adUserId = adUserId;
	}

	public String getAdScript() {
		return adScript;
	}

	public void setAdScript(String adScript) {
		this.adScript = adScript;
	}

	public Integer getAdType() {
		return adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	public Integer getReadNum() {
		return readNum;
	}

	public void setReadNum(Integer readNum) {
		this.readNum = readNum;
	}

	public String getAdTag() {
		return adTag;
	}

	public void setAdTag(String adTag) {
		this.adTag = adTag;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getAdPic() {
		return adPic;
	}

	public void setAdPic(String adPic) {
		this.adPic = adPic;
	}

	public String getAdLink() {
		return adLink;
	}

	public void setAdLink(String adLink) {
		this.adLink = adLink;
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

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SbAdvertInfo() {
		super();
	}

	public SbAdvertInfo(Integer adId) {
		this();
		setAdId(adId);
	}

	public List<SbAdvertPic> getPicList() {
		return picList;
	}

	public void setPicList(List<SbAdvertPic> picList) {
		this.picList = picList;
	}

	public List<Map<String, String>> getAdTitleList() {
		if (CPSUtil.isNotEmpty(getAdTitle())) {
			adTitleList = CPSUtil.string2MapList(getAdTitle(), "<<<>>>", "avTitle");
		}
		return adTitleList;
	}

	public void setAdTitleList(List<Map<String, String>> adTitleList) {
		this.adTitleList = adTitleList;
	}

	public List<Map<String, String>> getAdPicList() {
		if (CPSUtil.isNotEmpty(getAdPic())) {
			adPicList = CPSUtil.string2MapList(getAdPic(), "<<<>>>", "avPic");
		}
		return adPicList;
	}

	public void setAdPicList(List<Map<String, String>> adPicList) {
		this.adPicList = adPicList;
	}

	public String getAdvertPic() {
		return advertPic;
	}

	public void setAdvertPic(String advertPic) {
		this.advertPic = advertPic;
	}

	public String getAdvertPics() {
		return advertPics;
	}

	public void setAdvertPics(String advertPics) {
		this.advertPics = advertPics;
	}

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getAdTagName() {
		return adTagName;
	}

	public void setAdTagName(String adTagName) {
		this.adTagName = adTagName;
	}

	public String getAdTypeName() {
		if (CPSUtil.isNotEmpty(getAdType())) {
			adTypeName = AdType.getAdType(getAdType()).getName();
		}
		return adTypeName;
	}

	public void setAdTypeName(String adTypeName) {
		this.adTypeName = adTypeName;
	}

	public String getAdShowTitle() {
		// 处理标题随机显示
		if (CPSUtil.isNotEmpty(getAdTitle())) {
			String titleStr[] = getAdTitle().split("<<<>>>");
			int len = titleStr.length;
			adShowTitle = titleStr[RandomNum.getRandomIntVal(len)];
			// 处理长度
			if (adShowTitle.length() > 20) {
				adShowTitle = adShowTitle.substring(0, 20);
			}
		}
		return adShowTitle;
	}

	public void setAdShoWTitle(String adShowTitle) {
		this.adShowTitle = adShowTitle;
	}

	public String getAdShowPic() {
		// 处理图片随机显示
		if (CPSUtil.isNotEmpty(getAdPic())) {
			String picStr[] = getAdPic().split("<<<>>>");
			int len = picStr.length;
			adShowPic = picStr[RandomNum.getRandomIntVal(len)];
		}
		return adShowPic;
	}

	public void setAdShowPic(String adShowPic) {
		this.adShowPic = adShowPic;
	}

	public String getJsTitle() {
		// 处理广告标题加密
		if (CPSUtil.isNotEmpty(getAdShowTitle())) {
			String titleStr = getAdShowTitle();
			int charLen = titleStr.length();

			char chars;
			StringBuffer sbBuffer = new StringBuffer("");
			for (int i = 0; i < charLen; i++) {
				chars = titleStr.charAt(i);
				sbBuffer.append("<k style='float:left;'>" + chars + "</k>");
				sbBuffer.append("<k style='display:none;float:left;'>" + CPSUtil.getRandomZhCode() + "</k>");
			}
			jsTitle = sbBuffer.toString();
		}
		return jsTitle;
	}

	public void setJsTitle(String jsTitle) {
		this.jsTitle = jsTitle;
	}

	public Integer getShowState() {
		// 通过投放时间和截止时间判断是否展示以及状态
		String e_time = getEndTime();
		String s_time = getStartTime();
		String end_date = getEndDate();
		if (StringHelper.checkParameter(s_time, e_time, end_date)) {
			// 比对日期
			if (!CPSUtil.date1AfterDate2(CPSUtil.getCurDate(), end_date) && CPSUtil.isBetweenTime(s_time, e_time)
					&& getState().equals(StateType.Valid.getState())) {
				showState = 1;
			} else {
				showState = 0;
			}
		}

		// CPSUtil.xprint("avdname=["+getAdShowTitle()+"]
		// showState="+showState);
		return showState;
	}

	public void setShowState(Integer showState) {
		this.showState = showState;
	}

}
