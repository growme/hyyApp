package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.MemberLoginLogDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.MemberLoginLog;
import com.ccnet.cps.service.MemberLoginLogService;
@Service("memberLoginLogService")
public class MemberLoginLogServiceImpl implements MemberLoginLogService{

	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private MemberLoginLogDao memberLoginLogDao;
	
	/**
	 * 分页查询日志
	 * @param lg
	 * @param page
	 * @return
	 */
	@Override
	public Page findMemberLoginLogByPage(MemberLoginLog lg, Page<MemberLoginLog> page,String queryParam){
		return memberLoginLogDao.findMemberLoginLogByPage(lg, page, queryParam);
	}
	
	/**
	 * 分页查询日志(多过滤)
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page findMemberLoginLogByPage(MemberLoginLog lg, Page<MemberLoginLog> page,Dto pdDto){
		Page logPage = memberLoginLogDao.findMemberLoginLogByPage(lg, page, pdDto);
		List<MemberLoginLog> logList = logPage.getResults();
		if(CPSUtil.listNotEmpty(logList)){
			MemberInfo memberInfo = null;
			List<Integer> userIds = memberLoginLogDao.getValuesFromField(logList, "userId");
			Map<Integer, MemberInfo> userMap = memberInfoDao.getUserMapByIds(userIds);
			for (MemberLoginLog loginLog : logList) {
				memberInfo = userMap.get(loginLog.getUserId());
				if(CPSUtil.isNotEmpty(memberInfo)){
					loginLog.setUserName(memberInfo.getMemberName());
					loginLog.setLoginAccount(memberInfo.getLoginAccount());
					loginLog.setLoginToken(memberInfo.getSalt());
				}
			}
		}
		return logPage;
	}
	
	/**
	 * 保存日志
	 */
	@Override
	public boolean saveMemberLoginLog(MemberLoginLog o) {
		o.setLoginTime(new Date());
		return memberLoginLogDao.saveLoginInfo(o);
		
	}

	/**
	 * 删除日志
	 * @param log_id
	 * @return
	 */
	@Override
	public boolean trashMemberLoginLog(String log_id){
		MemberLoginLog loginLog = new MemberLoginLog();
		loginLog.setId(Integer.valueOf(log_id));
		return memberLoginLogDao.trashMemberLoginLog(loginLog); 
	}
	
	/**
	 * 批量删除日志
	 * @param log_ids
	 * @return
	 */
	@Override
	public boolean trashMemberLoginLogList(String log_ids){
		
		boolean temp = false;
		MemberLoginLog lg = null;
		String lg_id[] = null;
		List<MemberLoginLog> logList = null;
		if(CPSUtil.isNotEmpty(log_ids)){
			logList = new ArrayList<MemberLoginLog>();
			lg_id = log_ids.split(",");
			for (String logid : lg_id) {
				lg = new MemberLoginLog();
				lg.setId(Integer.valueOf(logid));
				logList.add(lg);
			}
			temp = memberLoginLogDao.trashMemberLoginLogList(logList);
		}
		return temp;
	}
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateMemberLoginLog(){
		return memberLoginLogDao.truncateMemberLoginLog();
	}

}
