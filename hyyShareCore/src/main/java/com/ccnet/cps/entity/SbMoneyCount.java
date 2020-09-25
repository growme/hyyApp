package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class SbMoneyCount extends BaseEntity{

	private static final long serialVersionUID = -2169967739074136182L;
	
	private Integer umId;
	
	private Integer userId;
	
	private Integer contentId;
	
	private Double umoney;
	
	private Integer mType;
	
	private Date createTime;
	
	private String vcode;
	
	private Integer vindex;

	@IgnoreTableField
	private MemberInfo memberInfo;//会员信息
	
	@IgnoreTableField
	private MemberInfo  invitedMemberInfo;//被邀请会员信息
	
	@IgnoreTableField
	private SbContentInfo sbContentInfo;//文章信息

	
	public Integer getVindex() {
		return vindex;
	}

	public void setVindex(Integer vindex) {
		this.vindex = vindex;
	}

	public Integer getUmId() {
		return umId;
	}

	public void setUmId(Integer umId) {
		this.umId = umId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Double getUmoney() {
		return umoney;
	}

	public void setUmoney(Double umoney) {
		this.umoney = umoney;
	}

	public Integer getmType() {
		return mType;
	}

	public void setmType(Integer mType) {
		this.mType = mType;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public MemberInfo getInvitedMemberInfo() {
		return invitedMemberInfo;
	}

	public void setInvitedMemberInfo(MemberInfo invitedMemberInfo) {
		this.invitedMemberInfo = invitedMemberInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SbContentInfo getSbContentInfo() {
		return sbContentInfo;
	}

	public void setSbContentInfo(SbContentInfo sbContentInfo) {
		this.sbContentInfo = sbContentInfo;
	}

}
