package com.ccnet.cps.service;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbPayLog;

/**
 * 支付日志service
 * @author zqy
 *
 */
public interface SbPayLogService extends BaseService<SbPayLog>{

	public Page<SbPayLog> getSbPayLogByPage(SbPayLog sbPayLog,Page<SbPayLog> page,Dto paramDto);
}

