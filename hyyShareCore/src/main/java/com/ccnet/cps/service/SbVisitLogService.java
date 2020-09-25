package com.ccnet.cps.service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbVisitLog;

public interface SbVisitLogService extends BaseService<SbVisitLog>{

	Page<SbVisitLog> findVisitByPage(SbVisitLog sbVisitLog,
			Page<SbVisitLog> page, Dto paramDto);

	boolean trashSbVisitLog(SbVisitLog visitLog);

}
