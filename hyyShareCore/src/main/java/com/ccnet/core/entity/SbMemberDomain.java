package com.ccnet.core.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.cps.entity.MemberInfo;

/**
 * 专属域名
 * 
 * @author JackieWang
 * 
 */
public class SbMemberDomain extends BaseEntity {

	private static final long serialVersionUID = 6992878178872668888L;

	private Integer mdId; // 主键
	private Integer domainId; // 域名
	@IgnoreTableField
	private SbContentDomain contentDomain;
	private Integer memberId; // 会员ID
	@IgnoreTableField
	private MemberInfo memberInfo;
	private Integer userId; // 操作人
	@IgnoreTableField
	private UserInfo userInfo;
	private Date createTime;// 分配时间
	private String remark;// 备注信息
	
	public Integer getMdId() {
		return mdId;
	}

	public void setMdId(Integer mdId) {
		this.mdId = mdId;
	}

	public Integer getDomainId() {
		return domainId;
	}

	public void setDomainId(Integer domainId) {
		this.domainId = domainId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SbContentDomain getContentDomain() {
		return contentDomain;
	}

	public void setContentDomain(SbContentDomain orderDomain) {
		this.contentDomain = orderDomain;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
