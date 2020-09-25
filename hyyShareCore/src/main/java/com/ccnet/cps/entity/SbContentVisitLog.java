package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.common.AccountState;
import com.ccnet.core.common.ShareType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.DeviceUtils;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 文章日志信息（指纹）
 * 
 * @author jackie
 * 
 */
public class SbContentVisitLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer visitId;
	private Integer contentId;
	private Integer userId;
	private String hashKey;
	private String requestIp;
	private String ipLocation;
	private String province;
	private String requestDetail;
	private Date visitTime;
	private Integer touchCount;// 滑动次数
	private Integer coordNum;// 晃动次数
	private Integer expandCount;// 展开次数
	private Integer wechatBrowser;// 是否微信浏览器
	private Date pageReadTime;// 开始访问时间
	private Date firstExpandTime;// 首次展开时间
	private Date secondExpandTime;// 继续阅读时间
	private Date accountTime;// 记账时间
	private Integer accountState;// 是否记账
	private String visitToken;// 文章访问32位唯一标志
	@IgnoreTableField
	private String mobileName;// 手机类型
	@IgnoreTableField
	private String stateName;
	@IgnoreTableField
	private String stateDesc;
	private Integer shareType;
	@IgnoreTableField
	private String typeName;// 渠道
	private String shareUuid;
	private Date lastHeartBeatTime;// 最后心跳时间
	private Integer heartBeatCount;// 停留时间
	@IgnoreTableField
	private Integer aliveTime;// 停留时间
	@IgnoreTableField
	private MemberInfo memberInfo;
	@IgnoreTableField
	private SbContentInfo contentInfo;
	@IgnoreTableField
	private SbVisitCounter visitIPCounter;
	private String openId;
	private Boolean goBack;
	private String expandName;
	private Double vmoney;
	private String cause;

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Double getVmoney() {
		return vmoney;
	}

	public void setVmoney(Double vmoney) {
		this.vmoney = vmoney;
	}

	public String getExpandName() {
		return expandName;
	}

	public void setExpandName(String expandName) {
		this.expandName = expandName;
	}

	public Boolean getGoBack() {
		return goBack;
	}

	public void setGoBack(Boolean goBack) {
		this.goBack = goBack;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getVisitId() {
		return visitId;
	}

	public void setVisitId(Integer visitId) {
		this.visitId = visitId;
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

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getRequestDetail() {
		return requestDetail;
	}

	public void setRequestDetail(String requestDetail) {
		this.requestDetail = requestDetail;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Integer getTouchCount() {
		return touchCount;
	}

	public void setTouchCount(Integer touchCount) {
		this.touchCount = touchCount;
	}

	public Integer getCoordNum() {
		return coordNum;
	}

	public void setCoordNum(Integer coordNum) {
		this.coordNum = coordNum;
	}

	public Integer getExpandCount() {
		return expandCount;
	}

	public void setExpandCount(Integer expandCount) {
		this.expandCount = expandCount;
	}

	public Integer getWechatBrowser() {
		return wechatBrowser;
	}

	public void setWechatBrowser(Integer wechatBrowser) {
		this.wechatBrowser = wechatBrowser;
	}

	public Date getPageReadTime() {
		return pageReadTime;
	}

	public void setPageReadTime(Date pageReadTime) {
		this.pageReadTime = pageReadTime;
	}

	public Date getFirstExpandTime() {
		return firstExpandTime;
	}

	public void setFirstExpandTime(Date firstExpandTime) {
		this.firstExpandTime = firstExpandTime;
	}

	public Date getSecondExpandTime() {
		return secondExpandTime;
	}

	public void setSecondExpandTime(Date secondExpandTime) {
		this.secondExpandTime = secondExpandTime;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public SbContentInfo getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(SbContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getIpLocation() {
		return ipLocation;
	}

	public void setIpLocation(String ipLocation) {
		this.ipLocation = ipLocation;
	}

	public Date getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(Date accountTime) {
		this.accountTime = accountTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	public String getShareUuid() {
		return shareUuid;
	}

	public void setShareUuid(String shareUuid) {
		this.shareUuid = shareUuid;
	}

	public String getTypeName() {
		if (CPSUtil.isNotEmpty(getShareType())) {
			typeName = ShareType.getShareType(getShareType()).getName();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getAccountState() {
		return accountState;
	}

	public void setAccountState(Integer accountState) {
		this.accountState = accountState;
	}

	public String getStateName() {
		if (CPSUtil.isNotEmpty(getAccountState())) {
			stateName = AccountState.getAccountState(getAccountState()).getStateName();
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateDesc() {
		if (CPSUtil.isNotEmpty(getAccountState())) {
			stateDesc = AccountState.getAccountState(getAccountState()).getStateDesc();
		}
		return stateDesc;
	}

	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public Date getLastHeartBeatTime() {
		return lastHeartBeatTime;
	}

	public void setLastHeartBeatTime(Date lastHeartBeatTime) {
		this.lastHeartBeatTime = lastHeartBeatTime;
	}

	public Integer getAliveTime() {
		if (CPSUtil.isNotEmpty(getPageReadTime()) && CPSUtil.isNotEmpty(getLastHeartBeatTime())) {
			String startTime = DateUtils.formatDate(getPageReadTime(), DateUtils.parsePatterns[3]);
			String overTime = DateUtils.formatDate(getLastHeartBeatTime(), DateUtils.parsePatterns[3]);
			aliveTime = CPSUtil.getSubSecond(startTime, overTime);
			// CPSUtil.xprint("aliveTime==="+aliveTime);
		}
		return aliveTime;
	}

	public void setAliveTime(Integer aliveTime) {
		this.aliveTime = aliveTime;
	}

	public SbVisitCounter getVisitIPCounter() {
		return visitIPCounter;
	}

	public void setVisitIPCounter(SbVisitCounter visitIPCounter) {
		this.visitIPCounter = visitIPCounter;
	}

	public String getVisitToken() {
		return visitToken;
	}

	public void setVisitToken(String visitToken) {
		this.visitToken = visitToken;
	}

	public String getMobileName() {
		if (CPSUtil.isNotEmpty(getRequestDetail())) {
			mobileName = DeviceUtils.getMobileName(getRequestDetail());
		}
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}

	public Integer getHeartBeatCount() {
		return heartBeatCount;
	}

	public void setHeartBeatCount(Integer heartBeatCount) {
		this.heartBeatCount = heartBeatCount;
	}

}
