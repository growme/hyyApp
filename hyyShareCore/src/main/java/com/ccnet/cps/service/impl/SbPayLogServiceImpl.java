package com.ccnet.cps.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.PayType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbCashLogDao;
import com.ccnet.cps.dao.SbPayLogDao;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbPayLog;
import com.ccnet.cps.service.SbPayLogService;
/**
 * 支付日志impl
 * @author zqy
 *
 */
@Service("sbPayLogService")
public  class SbPayLogServiceImpl extends BaseServiceImpl<SbPayLog> implements SbPayLogService {
	@Autowired
	SbPayLogDao sbPayLogDao;
	@Autowired
	SbCashLogDao cashLogDao;
	
	/**
	 * 分页查询支付日志
	 */
	public Page<SbPayLog> getSbPayLogByPage(SbPayLog sbPayLog,Page<SbPayLog> page, Dto paramDto) {
		Page logPage = sbPayLogDao.findSbPayLogByPage(sbPayLog, page, paramDto);
		List<SbPayLog> logList = logPage.getResults();
		if(!CPSUtil.checkListBlank(logList)){
			//处理提现记录
			List<Integer> cashIds = sbPayLogDao.getValuesFromField(logList, "ucId");
			Map<Integer, SbCashLog> cashMap = cashLogDao.getCashMapByIds(cashIds);
			for (SbPayLog pLog : logList) {
				SbCashLog cashLog = null;
				if(CPSUtil.isNotEmpty(pLog.getUcId())){
					cashLog = cashMap.get(pLog.getUcId());
					cashLog.setTypeName(PayType.getPayType(cashLog.getPayType()).getPayName());
					pLog.setCashLog(cashLog);
				}
			}
		}
		
		return logPage;
	}

	protected BaseDao<SbPayLog> getDao() {
		return sbPayLogDao;
	}

	
}
