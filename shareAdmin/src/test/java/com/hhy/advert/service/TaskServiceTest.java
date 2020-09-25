package com.hhy.advert.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.redis.JedisUtils;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring/applicationContext-prod.xml" })
@ActiveProfiles(value="dev")
public class TaskServiceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByUserName() {

		String timeCount = CPSUtil.getParamValue(Const.CT_HEART_BEAT_UPDATE_TIME);// 更新心跳时间间隔
		if (CPSUtil.isEmpty(timeCount)) {
			timeCount = "5";// 默认超过五分钟没有更新心跳则入库
		}
		CPSUtil.xprint("开始更新，间隔时间==" + timeCount);
		
		int count = 1000;
		// 文章心跳日志
		ScanParams scanParams = new ScanParams().match(Const.CT_CONTENT_HEART_BEAT + ":*").count(count);
		String cursor = "0";
		do{
			ScanResult<String> result = JedisUtils.scan(cursor, scanParams);
			if (result == null) {
				CPSUtil.xprint("更新心跳，同步结果为001null");
				break;
			}
			
			List<String> list = result.getResult();
			if (list == null) {
				CPSUtil.xprint("更新心跳，同步结果为0021null");
				break;
			}
			
			cursor = result.getStringCursor();
			
		} while(!"0".equals(cursor));
		
	}

}
