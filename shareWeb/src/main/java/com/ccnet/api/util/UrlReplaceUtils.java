package com.ccnet.api.util;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;

public class UrlReplaceUtils {

	private static final String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server");
	private static final String qnUrlDOMAIN = GetPropertiesValue.getValue("Config.properties", "qnUrl");

	public static String urlStr(String url) {
		if (CPSUtil.isNotEmpty(url)) {
			if (url.indexOf("/upload/") >= 0) {
				url = url.replaceAll("/upload/",  DOMAIN + "/upload/");
			}
			if (url.indexOf("/../upload/") >= 0) {
				url = url.replaceAll("/../upload/", "" + DOMAIN + "/upload/");
			}
			if (url.indexOf("http://") < 0 && url.indexOf("https://") < 0) {
				url = qnUrlDOMAIN + url;
			}
		}
		return url;
	}

	public static String contentStr(String url) {
		if (CPSUtil.isNotEmpty(url)) {
			if (url.indexOf("/upload/") >= 0) {
				url = url.replaceAll("/upload/",  DOMAIN + "/upload/");
			}
			if (url.indexOf("/../upload/") >= 0) {
				url = url.replaceAll("/../upload/", "" + DOMAIN + "/upload/");
			}
		}
		return url;
	}
	
	public static void main(String[] args) {
		String str = urlStr("/upload/download/20200211/c1f86a5c218646ccb0c2bd259bbf54ca.jpeg");
		System.out.println(str);
	}
}
