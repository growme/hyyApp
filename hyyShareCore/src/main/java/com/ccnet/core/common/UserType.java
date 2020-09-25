package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.entity.UserInfo;

public enum UserType {
	SYSTEMADMIN(0, "系统管理员"), //系统管理员
	ADMIN(100, "业务管理员"), //业务管理员
	SHOP(2, "商户用户"), //商户用户
	SHOP_SERVICE(4, "商户客服"), //商户客服
	SHOP_FINALCIAL(5, "商户财务"), //商户财务
	ADVERTISER(6, "广告主"), //广告主
	MEMBER(3, "自媒体用户"); // 会员， 负责推广

	private Integer type;
	private String name;

	private UserType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 判断是否为店铺内人员
	 * @param type
	 * @return
	 */
	public static boolean isShopType(Integer type) {
		return SHOP.getType().equals(type) || SHOP_SERVICE.getType().equals(type) || SHOP_FINALCIAL.getType().equals(type);
	}
	
	/**
	 * 判断是否为店铺主和管理员
	 * @param type
	 * @return
	 */
	public static boolean isShopAndAdminType(Integer type) {
		return SHOP.getType().equals(type) || ADMIN.getType().equals(type) || SYSTEMADMIN.getType().equals(type);
	}
	
	/**
	 * 判断是否为店铺操作
	 * @param userInfo
	 * @return
	 */
	public static boolean isShopOperate(UserInfo userInfo) {
		if(CPSUtil.isNotEmptyForInteger(userInfo.getShopId()) && isShopType(userInfo.getUserType())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取后台用户类型
	 * @return
	 */
	public static List<UserType> all() {
		List<UserType> userTypes = new ArrayList<UserType>(Arrays.asList(UserType.values()));
		userTypes.remove(UserType.MEMBER);
		return userTypes;
	}
	
	/**
	 * 获取商户用户类型
	 * @param filterShop 排除商户用户
	 * @return
	 */
	public static List<UserType> allShopUser(boolean filterShop) {
		List<UserType> userTypes = new ArrayList<UserType>(Arrays.asList(UserType.values()));
		userTypes.remove(UserType.MEMBER);
		userTypes.remove(UserType.ADMIN);
		userTypes.remove(UserType.SYSTEMADMIN);
		if(filterShop){
			userTypes.remove(UserType.SHOP);
		}
		return userTypes;
	}
	
	/**
	 * 根据type获取用户类型
	 * @param type
	 * @return
	 */
	public static UserType parseUserType(Integer type) {
		for (UserType userType : values()) {
			if (userType.getType().equals(type)) {
				return userType;
			}
		}
		return null;
	}

}
