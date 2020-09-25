package com.ccnet.core.entity;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserSexSate;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.FileUtil;
import com.ccnet.core.dao.base.IgnoreTableField;

/**
 * 用户帐号表
 */
public class UserInfo extends BaseEntity {

	private static final long serialVersionUID = 1108022485026914669L;

	private Integer userId;
	private String userName;
	private String loginAccount;
	private String loginPassword;
	private String userDesc;
	private Integer userType;
	@IgnoreTableField
	private String typeName;
	private Integer userState;
	@IgnoreTableField
	private String stateName;
	@IgnoreTableField
	private String roleId;
	@IgnoreTableField
	private String roleName;
	private String phoneNumber;
	private Integer sex;
	@IgnoreTableField
	private String sexName;
	private String nickName;
	private String email;
	private String qq;
	private Date registerTime;
	private String salt;
	private String skin;
	private String picUrl;
	private Date updateTime;
	@IgnoreTableField
	private List<RoleInfo> roleList;
	@IgnoreTableField
	private Integer shopId; // 店铺id
	@IgnoreTableField
	private String shopName;
	@IgnoreTableField
	private UserinfoExtend extendInfo;
	@IgnoreTableField
	private boolean passwordLocked = false;
	//处理logo和ico
	@IgnoreTableField
	private String siteLogo;
	@IgnoreTableField
	private String siteIco;
	@IgnoreTableField
	private String backStageLogo;
	@IgnoreTableField
	private String backStageIco;

	private Double money;
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSexName() {
		if (CPSUtil.isNotEmpty(getSex())) {
			sexName = UserSexSate.getUserSexSate(getSex()).getName();
		}
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName;
	}

	public String getTypeName() {
		if (CPSUtil.isNotEmpty(getUserType())) {
			typeName = UserType.parseUserType(getUserType()).getName();
		}
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public List<RoleInfo> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public UserinfoExtend getExtendInfo() {
		return extendInfo;
	}

	public void setExtendInfo(UserinfoExtend extendInfo) {
		this.extendInfo = extendInfo;
	}

	public boolean isPasswordLocked() {
		return passwordLocked;
	}

	public void setPasswordLocked(boolean passwordLocked) {
		this.passwordLocked = passwordLocked;
	}

	public String getSiteLogo() {
		//判断文件是否存在
		String filePath = null;
		String fileUrl = CPSUtil.getParamValue("SITE_LOGO");
		if(CPSUtil.isNotEmpty(fileUrl)){
			filePath = CPSUtil.getContainPath() + fileUrl;
			if(FileUtil.exist(filePath)){
				siteLogo = fileUrl;
			}else{
				siteLogo = "static/images/logo.png";
			}
		}
		CPSUtil.xprint("siteLogo="+siteLogo);
		return siteLogo;
	}

	public void setSiteLogo(String siteLogo) {
		this.siteLogo = siteLogo;
	}

	public String getSiteIco() {
		//判断文件是否存在
		String filePath = null;
		String fileUrl = CPSUtil.getParamValue("SITE_ICO");
		if(CPSUtil.isNotEmpty(fileUrl)){
			filePath = CPSUtil.getContainPath() + fileUrl;
			if(FileUtil.exist(filePath)){
				siteIco = fileUrl;
			}else{
				siteIco = "static/images/favicon.ico";
			}
		}
		CPSUtil.xprint("siteIco="+siteIco);
		return siteIco;
	}

	public void setSiteIco(String siteIco) {
		this.siteIco = siteIco;
	}

	public String getBackStageLogo() {
		//判断文件是否存在
		String filePath = null;
		String fileUrl = CPSUtil.getParamValue("BACK_SITE_LOGO");
		if(CPSUtil.isNotEmpty(fileUrl)){
			filePath = CPSUtil.getContainPath() + fileUrl;
			if(FileUtil.exist(filePath)){
				backStageLogo = fileUrl;
			}else{
				backStageLogo = "static/img/logo.png";
			}
		}
		CPSUtil.xprint("backStageLogo="+backStageLogo);
		return backStageLogo;
	}

	public void setBackStageLogo(String backStageLogo) {
		this.backStageLogo = backStageLogo;
	}

	public String getBackStageIco() {
		//判断文件是否存在
		String filePath = null;
		String fileUrl = CPSUtil.getParamValue("BACK_SITE_ICO");
		if(CPSUtil.isNotEmpty(fileUrl)){
			filePath = CPSUtil.getContainPath() + fileUrl;
			if(FileUtil.exist(filePath)){
				backStageIco = fileUrl;
			}else{
				backStageIco = "static/img/favicon.png";
			}
		}
		CPSUtil.xprint("backStageIco="+backStageIco);
		return backStageIco;
	}

	public void setBackStageIco(String backStageIco) {
		this.backStageIco = backStageIco;
	}

}