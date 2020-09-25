package com.ccnet.cps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbVisitLogDao;
import com.ccnet.cps.entity.SbVisitLog;
import com.ccnet.cps.service.SbVisitLogService;

@Service("sbVisitLogService")
public class SbVisitLogServiceImpl extends BaseServiceImpl<SbVisitLog> implements SbVisitLogService {
	
	@Autowired
	private SbVisitLogDao sbVisitLogDao;

	public Page<SbVisitLog> findVisitByPage(SbVisitLog sbVisitLog,Page<SbVisitLog> page, Dto paramDto) {
		return sbVisitLogDao.findSbVisitLogByPage(sbVisitLog, page, paramDto);
	}

	public boolean trashSbVisitLog(SbVisitLog visitLog) {
		if (CPSUtil.isNotEmpty(visitLog.getVisitId())) {
			int i = sbVisitLogDao.delete(visitLog);
			if (i > 0) {
				return true;
			}
		} else {
			int j = sbVisitLogDao.deleteAll();
			if (j > 0) {
				return true;
			}
		}
		return false;
	}
	
	protected BaseDao<SbVisitLog> getDao() {
		return sbVisitLogDao;
	}

}
