package com.ccnet.core.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.entity.UserinfoExtend;
import com.ccnet.core.service.base.BaseService;


public interface UserInfoService extends BaseService<UserInfo>{

	/**
	 * 分页查询用户(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findUserByPage(UserInfo user, Page<UserInfo> page,Dto pdDto);
	
	/**
	 * 获取单个用户
	 * @param userID
	 * @return
	 */
	public UserInfo getUserByUserID(Integer userID);
	
	/**
     * 获取用户
     * @param userId
     * @return
     */
    public UserinfoExtend findUserExtendInfoByID(Integer userId);
	
    /**
     * 根据登录帐号查找loginName和userInfoType，正常只有一条数据
     * and a.isvalid='1' and a.userInfo_type='1'需要该条件
     * @param loginName
     * @return
     */
    public UserInfo findFormatByLoginName(String loginName);
    
	/**
	 * 保存用户
	 * @param userInfo
	 * @return
	 */
	public boolean saveUserInfo(UserInfo userInfo);
	
	
	/**
	 * 修改用户
	 * @param userInfo
	 * @return
	 */
	public boolean editUserInfo(UserInfo userInfo);
	
	/**
	 * 重置密码
	 * @param user_id
	 * @param password
	 * @return
	 */
	public boolean resetPassword(String user_id,String password);
	
	/**
	 * 删除用户
	 * @param userInfo
	 * @return
	 */
	public boolean trashUserInfo(UserInfo userInfo);
	
	/**
	 * 批量删除用户
	 * @param user_ids
	 * @return
	 */
	public boolean trashUserInfoList(String user_ids);
	
   
}
