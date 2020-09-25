package com.ccnet.core.common.utils.html;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.HttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 生成短连接 ClassName: ShortUrlUtil
 * 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-7-28
 */
public class ShortUrlUtil {

	// 微博 api地址
	public static String WEIBO_API_URL = "https://api.weibo.com/2/short_url/shorten.json";
	// sina api地址
	public static String SINA_API_URL = "https://api.t.sina.com.cn/short_url/shorten.json";

	/**
	 * 微博 api生成短链接
	 * 
	 * @param url
	 * @return void
	 * @throws @author Jackie Wang
	 * @date 2017-7-28
	 */
	public static String weiboShortUrl(String url) {
		String shortUrl = null;
		List<String> shortUrlList = null;
		try {
			// 将路径URLEncoder转码 否则无法带参数
			String reqUrl = WEIBO_API_URL + "?source=1681459862&url_long=" + URLEncoder.encode(url, "UTF-8");
			String returnStr = HttpUtil.get(reqUrl, true);
			CPSUtil.xprint("returnStr=" + returnStr);
			JSONObject jsonObj = JSONObject.fromObject(returnStr.replace("\\", ""));
			JSONArray jsonArray = (JSONArray) jsonObj.get("urls");

			JSONObject jsonObj2 = null;
			shortUrlList = new ArrayList<String>();
			for (Object obj : jsonArray) {
				jsonObj2 = JSONObject.fromObject(obj);
				shortUrl = jsonObj2.getString("url_short");
				shortUrlList.add(shortUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (CPSUtil.listNotEmpty(shortUrlList)) {
			shortUrl = shortUrlList.get(0);
		}
		return shortUrl;
	}

	public static String getTinyUrl(String url) {
		String shortUrl = null;
		try {
			String reqUrl = "http://tinyurl.com/api-create.php?url=" + URLEncoder.encode(url, "UTF-8");
			String returnStr = HttpUtil.get(reqUrl, false);
			CPSUtil.xprint("returnStr=" + returnStr);
			if(StringUtils.isNotBlank(returnStr)) {
				shortUrl=returnStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return shortUrl;

	}

	/**
	 * 新浪 api生成短链接
	 * 
	 * @param url
	 * @return void
	 * @throws @author Jackie Wang
	 * @date 2017-7-28
	 */
	public static String sinaShortUrl(String url) {
		String shortUrl = null;
		List<String> shortUrlList = null;
		String ss[] = { "2849184197", "202088835", "211160679", "783190658", "2702428363", "82966982", "3271760578" };
		Random r = new Random();
		String source = ss[r.nextInt(ss.length)];
		try {
			// 将路径URLEncoder转码 否则无法带参数
			String reqUrl = SINA_API_URL + "?source=" + source + "&url_long=" + URLEncoder.encode(url, "UTF-8");
			String returnStr = HttpUtil.get(reqUrl, true);
			CPSUtil.xprint("returnStr=" + returnStr);
			JSONArray jsonArray = JSONArray.fromObject(returnStr.replaceAll("\\[\\]", ""));
			JSONObject jsonObj2 = null;
			shortUrlList = new ArrayList<String>();
			for (Object obj : jsonArray) {
				jsonObj2 = JSONObject.fromObject(obj);
				shortUrl = jsonObj2.getString("url_short");
				shortUrlList.add(shortUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (CPSUtil.listNotEmpty(shortUrlList)) {
			shortUrl = shortUrlList.get(0);
		}
		return shortUrl;
	}

	/**
	 * 获取短连接url
	 * 
	 * @param urlString
	 * @return
	 */
	public static String getShortUrl(String urlString) {
		String shortUrlString = null;
		if (CPSUtil.isNotEmpty(urlString)) {
			shortUrlString = null;// weiboShortUrl(urlString);
			if (CPSUtil.isEmpty(shortUrlString)) {
				//shortUrlString = getTinyUrl(urlString);
			}
		}

		if (StringUtils.isBlank(shortUrlString)) {
			shortUrlString = urlString;
		}

		CPSUtil.xprint("返回短链接===>" + shortUrlString);
		return shortUrlString;
	}

	public static void main(String[] args) throws InterruptedException {
		String targetString = "http://2581q.cn/df.php?from=groupmessage&isappinstalled=0";
		// weiboShortUrl(targetString);
		// sinaShortUrl(targetString);
		getTinyUrl(targetString);
	}

}
