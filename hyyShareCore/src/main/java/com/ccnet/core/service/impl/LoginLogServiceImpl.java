package com.ccnet.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.LoginLogDao;
import com.ccnet.core.dao.impl.UserInfoDao;
import com.ccnet.core.dao.impl.UserInfoExtendDao;
import com.ccnet.core.entity.LoginLog;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.entity.UserinfoExtend;
import com.ccnet.core.service.LoginLogService;
@Service("loginLogService")
public class LoginLogServiceImpl implements LoginLogService{

	@Autowired
	private LoginLogDao loginLogDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserInfoExtendDao userInfoExtendDao;
	
	/**
	 * 分页查询日志
	 * @param lg
	 * @param page
	 * @return
	 */
	@Override
	public Page findLoginLogByPage(LoginLog lg, Page<LoginLog> page,String queryParam){
		return loginLogDao.findLoginLogByPage(lg, page, queryParam);
	}
	
	/**
	 * 分页查询日志(多过滤)
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page findLoginLogByPage(LoginLog lg, Page<LoginLog> page,Dto pdDto){
		Page logPage = loginLogDao.findLoginLogByPage(lg, page, pdDto);
		List<LoginLog> logList = logPage.getResults();
		if(CPSUtil.listNotEmpty(logList)){
			UserInfo userInfo = null;
			List<Integer> userIds = loginLogDao.getValuesFromField(logList, "userId");
			Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
			for (LoginLog loginLog : logList) {
				userInfo = userMap.get(loginLog.getUserId());
				loginLog.setUserName(userInfo.getUserName());
				loginLog.setLoginAccount(userInfo.getLoginAccount());
				loginLog.setLoginToken(userInfo.getSalt());
			}
		}
		return logPage;
	}
	
	/**
	 * 保存日志
	 */
	@Override
	public void saveLoginLog(LoginLog o) {
		o.setLoginTime(new Date());
		loginLogDao.insert(o);
		userInfoExtendDao.insert(new UserinfoExtend(o.getUserId(), 1, o.getLoginTime(), o.getLoginIp()));
	}

	/**
	 * 删除日志
	 * @param log_id
	 * @return
	 */
	@Override
	public boolean trashLoginLog(String log_id){
		LoginLog loginLog = new LoginLog();
		loginLog.setId(Integer.valueOf(log_id));
		return loginLogDao.trashLoginLog(loginLog); 
	}
	
	/**
	 * 批量删除日志
	 * @param log_ids
	 * @return
	 */
	@Override
	public boolean trashLoginLogList(String log_ids){
		
		boolean temp = false;
		LoginLog lg = null;
		String lg_id[] = null;
		List<LoginLog> logList = null;
		if(CPSUtil.isNotEmpty(log_ids)){
			logList = new ArrayList<LoginLog>();
			lg_id = log_ids.split(",");
			for (String logid : lg_id) {
				lg = new LoginLog();
				lg.setId(Integer.valueOf(logid));
				logList.add(lg);
			}
			temp = loginLogDao.trashLoginLogList(logList);
		}
		return temp;
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateLoginLog(){
		return loginLogDao.truncateLoginLog();
	}

}
