//package com.ccnet.test;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.ccnet.core.common.utils.CPSUtil;
//import com.ccnet.core.common.utils.redis.JedisUtils;
//
//@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
//@ContextConfiguration(locations={"classpath*:spring-context-jedis.xml"}) //加载配置文件 
//public class JedisUnitTest {
//	
//	@Test
//	public void testJedis(){
//		
//		//测试redis
//		JedisUtils.set("Jackie", "Wang", 60);
//		
//		CPSUtil.xprint("Jackie="+JedisUtils.get("Jackie"));
//		
//	}
//
//}
