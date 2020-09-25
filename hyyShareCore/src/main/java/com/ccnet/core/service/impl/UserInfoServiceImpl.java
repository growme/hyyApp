package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.RoleInfoDao;
import com.ccnet.core.dao.impl.UserInfoDao;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.entity.UserinfoExtend;
import com.ccnet.core.service.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private RoleInfoDao roleInfoDao;
	
	/**
	 * 分页查询用户(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page findUserByPage(UserInfo user, Page<UserInfo> page,Dto pdDto){
		Page userPage = userInfoDao.findUserByPage(user, page, pdDto);
		List<UserInfo> uList = userPage.getResults();
		if(!CPSUtil.checkListBlank(uList)){
			for (UserInfo userInfo : uList) {
				if(CPSUtil.isNotEmpty(UserSateType.getUserSateType(userInfo.getUserState()))){
				   userInfo.setStateName(UserSateType.getUserSateType(userInfo.getUserState()).getName());
				}
				if(CPSUtil.isNotEmpty(UserType.parseUserType(userInfo.getUserType()))){
				   userInfo.setTypeName(UserType.parseUserType(userInfo.getUserType()).getName());
				}
				//设置角色
				setRoleInfoList(userInfo);
			}
		}
		userPage.setResults(uList);
		return userPage;
	}
	
	
	/**
	 * 设置角色
	 * @param userInfo
	 */
	private void setRoleInfoList(UserInfo userInfo){
		List<RoleInfo> roleInfos = roleInfoDao.queryUserRoleList(userInfo.getUserId());
		if(CPSUtil.listNotEmpty(roleInfos)){
			int roleCount = 0;
			userInfo.setRoleList(roleInfos);
			StringBuffer sbBuffer = new StringBuffer("");
			for (RoleInfo roleInfo : roleInfos) {
				roleCount++;
				sbBuffer.append(roleInfo.getRoleName());
				if(roleCount < roleInfos.size()){
					sbBuffer.append(" + ");
				}
				userInfo.setRoleName(sbBuffer.toString());
			}
		}
	}
	
	
	/**
	 * 设置扩展信息
	 * @param userInfo
	 */
	private void setExtendInfo(UserInfo userInfo){
		UserinfoExtend extend = new UserinfoExtend();
		if(userInfo!=null){
			extend = userInfoDao.findUserExtendInfoByID(userInfo.getUserId());
			if(extend!=null){
				userInfo.setExtendInfo(extend);
			}
		}
	}
	
	/**
	 * 获取单个用户
	 * @param userID
	 * @return
	 */
	public UserInfo getUserByUserID(Integer userID){
		UserInfo userInfo = userInfoDao.getUserByUserID(userID);
		if(CPSUtil.isNotEmpty(userInfo)){
			setRoleInfoList(userInfo);
		}
		return userInfo;
	}

	 /**
     * 根据登录帐号查找loginName和userInfoType，正常只有一条数据
     * and a.isvalid='1' and a.userInfo_type='1'需要该条件
     * @param loginName
     * @return
     */
    public UserInfo findFormatByLoginName(String loginName){
		UserInfo userInfo= userInfoDao.findFormatByLoginName(loginName);
		if(CPSUtil.isNotEmpty(userInfo)){
		   setRoleInfoList(userInfo);
		   setExtendInfo(userInfo);
		}
		return userInfo;
	}
    
    /**
     * 获取用户
     * @param userId
     * @return
     */
    public UserinfoExtend findUserExtendInfoByID(Integer userId){
    	return userInfoDao.findUserExtendInfoByID(userId);
    }
	
	/**
	 * 增加用户
	 * @param userID
	 * @return
	 */
	public boolean saveUserInfo(UserInfo userInfo) {
		return userInfoDao.saveUserInfo(userInfo);
	}
	
	/**
	 * 编辑用户
	 * @param userInfo
	 * @return
	 */
	public boolean editUserInfo(UserInfo userInfo) {
		return userInfoDao.editUserInfo(userInfo);
	}
	
	/**
	 * 重置密码
	 * @param user_id
	 * @param password
	 * @return
	 */
	public boolean resetPassword(String user_id,String password) {
		boolean temp = false;
		if(CPSUtil.isNotEmpty(user_id)){
			UserInfo userInfo = null;
			String userid[] = user_id.split(",");
			for (String uid : userid) {
				userInfo = getUserByUserID(Integer.valueOf(uid));
				if(userInfo!=null){
					userInfo.setLoginPassword(CipherUtil.createPwdEncrypt(userInfo.getLoginAccount(),password,userInfo.getSalt()));
					temp = editUserInfo(userInfo);
				}
			}
		}
		return temp;
	}

	/**
	 * 删除用户
	 * @param userInfo
	 * @return
	 */
	public boolean trashUserInfo(UserInfo userInfo) {
		return userInfoDao.trashUserInfo(userInfo);
	}
	
	/**
	 * 批量删除用户
	 * @param user_ids
	 * @return
	 */
	public boolean trashUserInfoList(String user_ids){
		boolean temp = false;
		UserInfo userInfo = null;
		String user_id[] = null;
		List<UserInfo> userList = null;
		if(CPSUtil.isNotEmpty(user_ids)){
			userList = new ArrayList<UserInfo>();
			user_id = user_ids.split(",");
			for (String uid : user_id) {
				userInfo = new UserInfo();
				userInfo.setUserId(Integer.valueOf(uid));
				userList.add(userInfo);
			}
			//删除关联资源
			
			//删除用户信息
			temp = userInfoDao.trashUserInfoList(userList);
		}
		return temp;
	}


	@Override
	protected BaseDao<UserInfo> getDao() {
		// TODO Auto-generated method stub
		return this.userInfoDao;
	}
	
}
