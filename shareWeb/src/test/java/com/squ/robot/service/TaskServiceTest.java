package com.squ.robot.service;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ccnet.cps.entity.SbAdvertVisitLog;
import com.ccnet.cps.service.SbAdvertVisitLogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value = "dev")
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext-prod.xml" })
public class TaskServiceTest {

	@Autowired
	private SbAdvertVisitLogService sbAdvertVisitLogService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByUserName() {
		SbAdvertVisitLog visitLog = new SbAdvertVisitLog();
		visitLog.setVisitToken("12121212");
		visitLog.setAdId(Integer.valueOf("3"));
		visitLog.setContentId(12);
		visitLog.setHashKey("21212");
		visitLog.setRequestIp("192.168");
		visitLog.setRequestDetail("ua");
		visitLog.setUserId(4);
		visitLog.setVisitTime(new Date());
		visitLog.setWechatBrowser(Integer.valueOf("1"));
		sbAdvertVisitLogService.saveSbAdvertVisitLog(visitLog);
	}

}
