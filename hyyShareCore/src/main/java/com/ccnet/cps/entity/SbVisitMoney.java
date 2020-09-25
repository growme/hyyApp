package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class SbVisitMoney extends BaseEntity {

	private static final long serialVersionUID = -2169967739074136182L;

	private Integer vmId;

	private Integer userId;

	private String vcode;

	private Double vmoney;

	private Date createTime;

	@IgnoreTableField
	private MemberInfo memberInfo;// 会员信息

	@IgnoreTableField
	private MemberInfo invitedMemberInfo;// 被邀请会员信息

	public Integer getVmId() {
		return vmId;
	}

	public void setVmId(Integer vmId) {
		this.vmId = vmId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	public Double getVmoney() {
		return vmoney;
	}

	public void setVmoney(Double vmoney) {
		this.vmoney = vmoney;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
}
