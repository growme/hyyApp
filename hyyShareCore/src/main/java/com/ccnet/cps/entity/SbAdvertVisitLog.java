package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DeviceUtils;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 广告日志信息（指纹）
 * 
 * @author jackie
 * 
 */
public class SbAdvertVisitLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Integer visitId;
	private Integer contentId;
	private Integer adId;
	private Integer userId;
	private String hashKey;
	private String requestIp;
	private String ipLocation;
	private String province;
	private String requestDetail;
	private Date visitTime;
	private Integer wechatBrowser;// 是否微信浏览器
	
	private String visitToken;//文章访问32位唯一标志
	
	/**
	 * 1：真实
	 * 0：假记录
	 */
	private int type;
	
	private Double unitPrice;
	
	@IgnoreTableField
	private String mobileName;//手机类型
	
	@IgnoreTableField
	private MemberInfo memberInfo;
	@IgnoreTableField
	private SbContentInfo contentInfo;
	@IgnoreTableField
	private SbVisitCounter visitIPCounter;
	
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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
		if(CPSUtil.isNotEmpty(getRequestDetail())){
			mobileName = DeviceUtils.getMobileName(getRequestDetail());
		}
		return mobileName;
	}

	public void setMobileName(String mobileName) {
		this.mobileName = mobileName;
	}

	public Integer getAdId() {
		return adId;
	}

	public void setAdId(Integer adId) {
		this.adId = adId;
	}

	public String getIpLocation() {
		return ipLocation;
	}

	public void setIpLocation(String ipLocation) {
		this.ipLocation = ipLocation;
	}

	public Integer getWechatBrowser() {
		return wechatBrowser;
	}

	public void setWechatBrowser(Integer wechatBrowser) {
		this.wechatBrowser = wechatBrowser;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
