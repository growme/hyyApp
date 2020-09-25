package com.ccnet.api.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

public class JpUserAttention extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userId;
	private Integer toUid;
	private Date createDate;

	@IgnoreTableField
	private String memberName;
	@IgnoreTableField
	private String memberIcon;
	@IgnoreTableField
	private String huguan;
	@IgnoreTableField
	private String introduction;

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberIcon() {
		return memberIcon;
	}

	public void setMemberIcon(String memberIcon) {
		this.memberIcon = memberIcon;
	}

	public String getHuguan() {
		return huguan;
	}

	public void setHuguan(String huguan) {
		this.huguan = huguan;
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

	public Integer getToUid() {
		return toUid;
	}

	public void setToUid(Integer toUid) {
		this.toUid = toUid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
