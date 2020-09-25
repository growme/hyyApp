package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.common.ShareType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 用户分享日志
 * 
 * @author JackieWang
 * 
 */
public class SbShareLog extends BaseEntity {

	private static final long serialVersionUID = -6512334460254681926L;

	private Integer shareId;
	private Integer userId;
	private Integer contentId;
	private String shareIp;
	private Integer shareType;
	@IgnoreTableField
	private String typeName;
	private String deviceDetail;
	private Date shareTime;
	private Integer givenMoney;//计费状态
	private Double shareMoney;//奖励金额
	@IgnoreTableField
	private String givenMoneyName;
	@IgnoreTableField
	private MemberInfo memberInfo;
	@IgnoreTableField
	private SbContentInfo contentInfo;

	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
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

	public String getShareIp() {
		return shareIp;
	}

	public void setShareIp(String shareIp) {
		this.shareIp = shareIp;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	public String getDeviceDetail() {
		return deviceDetail;
	}

	public void setDeviceDetail(String deviceDetail) {
		this.deviceDetail = deviceDetail;
	}

	public Date getShareTime() {
		return shareTime;
	}

	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
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

	public String getTypeName() {
		if(CPSUtil.isNotEmpty(getShareType())){
			typeName = ShareType.getShareType(getShareType()).getName();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getGivenMoney() {
		return givenMoney;
	}

	public void setGivenMoney(Integer givenMoney) {
		this.givenMoney = givenMoney;
	}

	public String getGivenMoneyName() {
		if(CPSUtil.isNotEmpty(getGivenMoney())){
			givenMoneyName = StateType.getStateType(getGivenMoney()).getName();
		}
		return givenMoneyName;
	}

	public void setGivenMoneyName(String givenMoneyName) {
		this.givenMoneyName = givenMoneyName;
	}

	public Double getShareMoney() {
		return shareMoney;
	}

	public void setShareMoney(Double shareMoney) {
		this.shareMoney = shareMoney;
	}
	
}
