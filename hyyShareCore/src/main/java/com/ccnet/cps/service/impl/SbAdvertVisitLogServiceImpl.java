package com.ccnet.cps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbAdvertVisitLogDao;
import com.ccnet.cps.entity.SbAdvertVisitLog;
import com.ccnet.cps.service.SbAdvertVisitLogService;

@Service("sbAdvertVisitLogService")
public class SbAdvertVisitLogServiceImpl extends BaseServiceImpl<SbAdvertVisitLog> implements SbAdvertVisitLogService {

	
	@Autowired
	private SbAdvertVisitLogDao advertVisitLogDao;
	
	@Override
	public boolean saveSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		
		return advertVisitLogDao.saveSbAdvertVisitLog(visitLog);
	}



	@Override
	protected BaseDao<SbAdvertVisitLog> getDao() {
		
		return advertVisitLogDao;
	}

	@Override
	public Page<SbAdvertVisitLog> findSbAdvertVisitLogByPage(SbAdvertVisitLog visitLog, Page<SbAdvertVisitLog> page,
			Dto pdDto) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int count(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public SbAdvertVisitLog findSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean editSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean updateHeartBeatTime(String visitToken, String lastHeartBeatTime) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean updateHeartBeat(String visitToken, String lastHeartBeatTime) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean updateContentReadParam(String visitToken) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean updateVisitContentParam(String visitToken, String lastHeartBeatTime) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean trashSbAdvertVisitLog(SbAdvertVisitLog visitLog) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean trashSbAdvertVisitLogList(List<SbAdvertVisitLog> list) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean trashSbAdvertVisitLogList(String visitIds) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean truncateSbAdvertVisitLog() {
		// TODO Auto-generated method stub
		return false;
	}

}
