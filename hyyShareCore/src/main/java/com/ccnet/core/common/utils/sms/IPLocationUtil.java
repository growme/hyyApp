package com.ccnet.core.common.utils.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.ccnet.core.common.utils.CPSUtil;

public class IPLocationUtil {
	/**
	 * 获取URL返回的字符串
	 * 
	 * @param callurl
	 * @param charset
	 * @return
	 */
	private static String callUrlByGet(String callurl, String charset) {
		String result = "";
		try {
			URL url = new URL(callurl);
			URLConnection connection = url.openConnection();
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String line;
			while ((line = reader.readLine()) != null) {
				result += line;
				result += "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return result;
	}

	/**
	 * IP地址归属地（淘宝）
	 * 
	 * @param ip
	 *            IP地址
	 * @return
	 */
	public static String getTBIPLocation(String ip) {
		String ret = "";
		try {
			Pattern pattern = Pattern.compile(
					"\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher matcher = pattern.matcher(ip);
			if (matcher.matches()) {
				String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip + "&accessKey=alibaba-inc";
				String result = callUrlByGet(url, "UTF-8");
				if (CPSUtil.isNotEmpty(result)) {
					CPSUtil.xprint("jsonData=" + result);
					if (!StringUtils.contains(result, "not found")) {
						JSONObject jsStr = new JSONObject(result);
						JSONObject dataString = jsStr.getJSONObject("data");
						ret = dataString.getString("region") + "," + dataString.getString("city");
						if (CPSUtil.isNotEmpty(dataString.getString("isp"))) {
							ret += "," + dataString.getString("isp");
						}
					} else {
						ret = "";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret = "";
		}
		return ret;

	}

	public static String getSinaIPLocation(String ip) {
		String ret = "";
		try {
			Pattern pattern = Pattern.compile(
					"\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher matcher = pattern.matcher(ip);
			if (matcher.matches()) {
				String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip + "&accessKey=alibaba-inc";
				String result = callUrlByGet(url, "UTF-8");
				if (CPSUtil.isNotEmpty(result)) {
					CPSUtil.xprint("jsonData=" + result);
					if (!StringUtils.contains(result, "not found")) {
						JSONObject jsStr = new JSONObject(result);
						JSONObject dataString = jsStr.getJSONObject("data");
						ret = dataString.getString("region") + "," + dataString.getString("city");
						/*
						 * if(CPSUtil.isNotEmpty(dataString.getString("county"))
						 * ){ ret += ","+ dataString.getString("county"); }
						 */
						if (CPSUtil.isNotEmpty(dataString.getString("isp"))) {
							ret += "," + dataString.getString("isp");
						}
					} else {
						ret = "";
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			ret = "";
		}

		return ret;
	}

	public static String getRegionIdLocation(String ip) {
		String ret = "";
		try {
			Pattern pattern = Pattern.compile(
					"\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher matcher = pattern.matcher(ip);
			if (matcher.matches()) {
				String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip + "&accessKey=alibaba-inc";
				String result = callUrlByGet(url, "UTF-8");
				if (CPSUtil.isNotEmpty(result)) {
					CPSUtil.xprint("jsonData=" + result);
					if (!StringUtils.contains(result, "not found")) {
						JSONObject jsStr = new JSONObject(result);
						JSONObject dataString = jsStr.getJSONObject("data");
						ret = dataString.getString("region_id");
					} else {
						ret = "";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret = "";
		}
		return ret;
	}

	/**
	 * IP地址归属地
	 * 
	 * @param ip
	 *            IP地址
	 * @return
	 */
	public static String getSinaIPLocation3(String ip) {
		String ret = "";
		try {
			Pattern pattern = Pattern.compile(
					"\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
			Matcher matcher = pattern.matcher(ip);
			if (matcher.matches()) {
				String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + ip;
				String result = callUrlByGet(url, "GBK");
				CPSUtil.xprint("jsonData=" + result);
				if (CPSUtil.isNotEmpty(result)) {
					JSONObject jsStr = new JSONObject(result);
					ret = jsStr.getString("province") + "," + jsStr.getString("city");
					if (CPSUtil.isNotEmpty(jsStr.getString("district"))) {
						ret += "," + jsStr.getString("district");
					}
					if (CPSUtil.isNotEmpty(jsStr.getString("isp"))) {
						ret += "," + jsStr.getString("isp");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			ret = "";
		}
		return ret;

	}

	public static String getIpLocation(String ip) {
		ip = StringUtils.trimToEmpty(ip);
		if (StringUtils.isBlank(ip)) {
			return null;
		}
		if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip) || ip.contains("192.168")) {
			return "本地IP";
		}
		String location = null;
		location = getTBIPLocation(ip);
		if (StringUtils.isBlank(location)) {
			location = getSinaIPLocation(ip);
		}
		return StringUtils.trimToNull(location);
	}

	public static void main(String[] args) {
		try {
			System.out.println(IPLocationUtil.getSinaIPLocation("117.136.7.126"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}