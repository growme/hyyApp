package com.ccnet.cps.entity;

import java.util.Date;

import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 收益明细封装
 * @author JackieWang
 *
 */
public class UserDetailMoney extends BaseEntity {

	private static final long serialVersionUID = 8937528466957658114L;
	
	//用户ID
	private Integer userId;
	//明细金额
    private Double money;
    //资金类型
    private Integer moneyType;
    @IgnoreTableField
    private String moneyTypeName;
    //记录时间
	private Date createTime;
	
	@IgnoreTableField
	private MemberInfo memberInfo;//会员信息
	@IgnoreTableField
	private SbContentInfo sbContentInfo;//新闻信息
	
	public SbContentInfo getSbContentInfo() {
		return sbContentInfo;
	}

	public void setSbContentInfo(SbContentInfo sbContentInfo) {
		this.sbContentInfo = sbContentInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	public Integer getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(Integer moneyType) {
		this.moneyType = moneyType;
	}

	public String getMoneyTypeName() {
		return moneyTypeName;
	}

	public void setMoneyTypeName(String moneyTypeName) {
		this.moneyTypeName = moneyTypeName;
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
	
}
