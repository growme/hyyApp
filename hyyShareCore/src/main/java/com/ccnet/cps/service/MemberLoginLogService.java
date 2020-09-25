package com.ccnet.cps.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.MemberLoginLog;

public interface MemberLoginLogService {

	/**
	 * 分页查询日志
	 * @param lg
	 * @param page
	 * @return
	 */
	public Page findMemberLoginLogByPage(MemberLoginLog lg, Page<MemberLoginLog> page,String queryParam);
	
	/**
	 * 分页查询日志(多过滤)
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findMemberLoginLogByPage(MemberLoginLog lg, Page<MemberLoginLog> page,Dto pdDto);
	
	/**
	 * 保存日志
	 * @param o
	 */
	public boolean saveMemberLoginLog(MemberLoginLog o);

	/**
	 * 删除日志
	 * @param log_id
	 * @return
	 */
	public boolean trashMemberLoginLog(String log_id);
	
	/**
	 * 批量删除日志
	 * @param log_ids
	 * @return
	 */
	public boolean trashMemberLoginLogList(String log_ids);
	
	/**
	 * 清空日志
	 * @return
	 */
	public boolean truncateMemberLoginLog();
}
