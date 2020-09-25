package com.ccnet.cps.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.IgnoreTableField;
import com.ccnet.core.entity.BaseEntity;

/**
 * 会员帐号表
 */
public class MemberInfo extends BaseEntity {

	private static final long serialVersionUID = -2169967739074136182L;

	private Integer memberId;
	private String loginAccount;
	private String loginPassword;
	private String memberName;
	private Date registerTime;
	private String salt;
	private String visitCode; // 自已的code
	private String recomUser; // 邀请人的code
	private String recomCode; // 邀请人邀请码
	private String levelCode; // 邀请人code串，用|隔开
	private String payAccount; // 支付账户
	private String accountName;
	private Integer userState;
	private Integer memberLevel;
	@IgnoreTableField
	private String levelName;
	@IgnoreTableField
	private String filterPhone;
	@IgnoreTableField
	private String stateName;
	private Date updateTime;
	private String wechat;
	private String mobile;
	private String qqNum;
	private String cnzzCode;// 会员专属cnzz统计代码
	private String baiduCode;// 会员专属百度统计代码
	@IgnoreTableField
	private String showColor;
	private Integer disscount;// 扣量比例值 默认30%

	private String unionid;
	private String memberIcon;

	private String sex;
	private String intro;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public MemberInfo() {
		super();
	}

	public String getMemberIcon() {
		return memberIcon;
	}

	public void setMemberIcon(String memberIcon) {
		this.memberIcon = memberIcon;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getStateName() {
		if (CPSUtil.isNotEmpty(getUserState())) {
			stateName = UserSateType.getUserSateType(getUserState()).getName();
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(String visitCode) {
		this.visitCode = visitCode;
	}

	public String getRecomUser() {
		return recomUser;
	}

	public void setRecomUser(String recomUser) {
		this.recomUser = recomUser;
	}

	public String getLevelCode() {
		return levelCode;
	}

	// 获取邀请人的code
	public List<String> getLevelCodes() {
		String[] codes = StringUtils.split(getLevelCode(), "|");
		List<String> list = new ArrayList<String>(Arrays.asList(codes));
		list.remove(getVisitCode()); // 排除掉自已
		return list;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getRecomCode() {
		return recomCode;
	}

	public void setRecomCode(String recomCode) {
		this.recomCode = recomCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFilterPhone() {
		if (CPSUtil.isNotEmpty(getLoginAccount())) {
			String tel = getLoginAccount();
			filterPhone = tel.substring(0, 3) + "****" + tel.substring(7, 11);
		}
		return filterPhone;
	}

	public void setFilterPhone(String filterPhone) {
		this.filterPhone = filterPhone;
	}

	public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getLevelName() {
		if (CPSUtil.isNotEmpty(getMemberLevel())) {
			levelName = MemeberLevelType.getMemeberLevelType(getMemberLevel()).getName();
		}
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getQqNum() {
		return qqNum;
	}

	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}

	public String getShowColor() {
		return showColor;
	}

	public void setShowColor(String showColor) {
		this.showColor = showColor;
	}

	public String getCnzzCode() {
		return cnzzCode;
	}

	public void setCnzzCode(String cnzzCode) {
		this.cnzzCode = cnzzCode;
	}

	public Integer getDisscount() {
		return disscount;
	}

	public void setDisscount(Integer disscount) {
		this.disscount = disscount;
	}

	public String getBaiduCode() {
		return baiduCode;
	}

	public void setBaiduCode(String baiduCode) {
		this.baiduCode = baiduCode;
	}

}