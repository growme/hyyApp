//package com.ccnet.test;
//
//import com.ccnet.core.common.utils.CPSUtil;
//
//public class Test {
//	public static void main(String[] args) {
//		/*for (int i = 0; i < 100; i++) {
//			System.out.println((int)((Math.random()*9+1)*100000)); 
//			
//		}*/
//		
//		String ageString = "Mozilla/5.0 (Linux; Android 5.1; GN5001S Build/LMY47D; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044203 Mobile Safari/537.36 MicroMessenger/6.6.6.1300(0x26060638) NetType/WIFI Language/zh_CN";
//		dealUserAgent(ageString);
//	}
//	
//	
//	private static String dealUserAgent(String userAgent) {
//		String _userAgent = "";
//		if(CPSUtil.isNotEmpty(userAgent)){
//			//去掉userAgent中的网络类型 去掉重复
//			int index = userAgent.indexOf("NetType");
//			_userAgent = userAgent.substring(0, index).trim();
//		}
//		CPSUtil.xprint("_userAgent====>"+_userAgent);
//		return _userAgent;
//	}
//
//}
