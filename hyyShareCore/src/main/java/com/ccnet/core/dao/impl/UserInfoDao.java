package com.ccnet.core.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.entity.UserinfoExtend;


/**
 * 用户数据层
 */
@Repository("userInfoDao")
public class UserInfoDao  extends BaseDao<UserInfo>{
	
	/**
	 * 分页查询用户(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<UserInfo> findUserByPage(UserInfo user, Page<UserInfo> page,Dto pdDto){
		
		//获取参数
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("state");
		String user_type = pdDto.getAsString("user_type");
		//日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, UserInfo.class, user);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		
		//加上模糊查询
		if(CPSUtil.isNotEmpty(queryParam)){
			appendWhere(sql);
			sql.append(" and (login_account like ?  or user_name like ? or nick_name like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}
		
		//带上日期查询
		if(CPSUtil.isNotEmpty(start_date)){
			appendWhere(sql);
			sql.append(" and register_time >=? ");
			params.add(start_date+" 00:00:00");
		}
		
        if(CPSUtil.isNotEmpty(end_date)){
        	appendWhere(sql);
        	sql.append(" and register_time <=? ");
			params.add(end_date+" 23:59:59");
		}
        
        if(CPSUtil.isNotEmpty(state)){
        	appendWhere(sql);
        	sql.append(" and user_state =? ");
			params.add(state);
		}
        
        if(CPSUtil.isNotEmpty(user_type)){
        	appendWhere(sql);
        	sql.append(" and user_type =? ");
			params.add(user_type);
		}
		
		//加上排序
		sql.append(" order by register_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<UserInfo>(UserInfo.class), params, page);
		return page;
		
	}
	
	
	/**
	 * 根据ID获取用户集合
	 * @param userIds
	 * @return
	 */
	public List<UserInfo> getUserListByIds(List<Integer> userIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "user_id", userIds);
		return memory.query(sql, new BeanListHandler<UserInfo>(UserInfo.class), params);
	}
	
	/**
	 * 转换集合数据
	 * @param userIds
	 * @return
	 */
	public Map<Integer, UserInfo> getUserMapByIds(List<Integer> userIds) {
		List<UserInfo> userInfos = getUserListByIds(userIds);
		Map<Integer, UserInfo> map = new HashMap<Integer, UserInfo>(0);
		if (CollectionUtils.isNotEmpty(userInfos)) {
			for (UserInfo userInfo : userInfos) {
				map.put(userInfo.getUserId(), userInfo);
			}
		}
		return map;
	}
	
	/**
	 * 获取单个用户
	 * @param userID
	 * @return
	 */
	public UserInfo getUserByUserID(Integer userID) {
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(userID);
		UserInfo userInfo = new UserInfo();
		Map<Integer, UserInfo> userMap = getUserMapByIds(userIds);
		if(userMap!=null && userMap.size()>0){
			userInfo = userMap.get(userID);
		}
		return userInfo;
	}
	
	/**
     * 根据登录帐号查找loginName和UserInfoType，正常只有一条数据
     * and a.isvalid='1' and a.UserInfo_type='1'需要该条件
     * @param loginName
     * @return
     */
    public UserInfo findFormatByLoginName(String loginName) {
    	UserInfo userInfo = memory.query("select * from user_info where login_account = ? ",
				new BeanHandler<UserInfo>(UserInfo.class), loginName);
    	return userInfo;
    }
    
    /**
     * 获取用户
     * @param userId
     * @return
     */
    public UserinfoExtend findUserExtendInfoByID(Integer userId) {
    	UserinfoExtend userExtendInfo = memory.query("select * from userinfo_extend where user_id = ? ",
				new BeanHandler<UserinfoExtend>(UserinfoExtend.class), userId);
    	return userExtendInfo;
    }
    
    
    
    
	
	/**
	 * 保存用户
	 * @param userInfo
	 * @return
	 */
	public boolean saveUserInfo(UserInfo userInfo) {
		if(insert(userInfo, "userId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户
	 * @param userInfo
	 * @return
	 */
	public boolean editUserInfo(UserInfo userInfo) {
		if(update(userInfo, "userId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除用户
	 * @param userInfo
	 * @return
	 */
	public boolean trashUserInfo(UserInfo userInfo) {
		if(delete(userInfo)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除用户
	 * @param list
	 * @return
	 */
	public boolean trashUserInfoList(List<UserInfo> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	}
	
}
