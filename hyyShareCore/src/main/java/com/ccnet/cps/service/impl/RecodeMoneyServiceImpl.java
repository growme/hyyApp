package com.ccnet.cps.service.impl;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.service.RecodeMoneyService;

/**
 * 璁板笎
 * @author WeiXiong
 *
 */
@Service("recodeMoneyService")
@Lazy
public class RecodeMoneyServiceImpl implements RecodeMoneyService{
	
	@Autowired
	private Destination forwardQueueDestination;
	
	@Autowired
	private SenderMQServiceImpl senderMQService;
	
	public void readRecodeMoney(SbContentVisitLog visitLog) {
		senderMQService.sendMessage(forwardQueueDestination, JsonHelper.encodeObject2Json(visitLog, "yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
