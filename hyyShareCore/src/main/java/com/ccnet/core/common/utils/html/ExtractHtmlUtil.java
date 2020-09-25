package com.ccnet.core.common.utils.html;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.encode.tool.ParseEncoding;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomIp;

public class ExtractHtmlUtil {

	private static ParseEncoding encoding = new ParseEncoding();

	public static String getBaseHtmlSource(String uri) {
		StringBuffer sb = new StringBuffer("");
		URL url = null;
		HttpURLConnection urlConnection = null;

		InputStream is = null;
		byte[] bytes = null;
		List<Byte> byteList = new ArrayList<Byte>();
		String str; 
		int len = -1;
		BufferedReader reader = null;
		InputStreamReader isReader = null;
		GZIPInputStream gzin = null;

		String pageEncode = "GBK";

		try {
			url = new URL(uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(30 * 1000);
			urlConnection.setConnectTimeout(30 * 1000);
			urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;");
			urlConnection.setRequestProperty("Accept-Language", "zh-cn");
			urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");

			int stateCode = urlConnection.getResponseCode();
			if (stateCode != 200) {
				return "http code :" + stateCode;
			}

			is = urlConnection.getInputStream();
			String acceptEncoding = "";
			if (urlConnection.getHeaderField("Content-Encoding") != null) {
				acceptEncoding = urlConnection.getHeaderField("Content-Encoding");
			}
			gzin = null;
			if (acceptEncoding.indexOf("gzip") != -1) {
				gzin = new GZIPInputStream(is);
			}
			acceptEncoding = null;
			acceptEncoding = urlConnection.getHeaderField("Content-Type");
			int index = -1;
			if (acceptEncoding != null
					&& (index = acceptEncoding.indexOf("charset=")) != -1) {
				index = index + 8;
				if (index <= acceptEncoding.length()) {
					pageEncode = acceptEncoding.substring(index);
				}
			} else {
				pageEncode = null;
			}
			acceptEncoding = null;

			if (pageEncode == null || "AUTO".equals(pageEncode)) {
				len = -1;
				byteList.clear();
				int count = 0;
				if (gzin != null) {
					while ((len = gzin.read()) != -1) {
						count++;
						byteList.add((byte) len);
						if (count >= 200000) {
							break;
						}
					}
				} else {
					while ((len = is.read()) != -1) {
						count++;
						byteList.add((byte) len);
						if (count >= 200000) {
							break;
						}
					}
				}
				len = byteList.size();
				bytes = new byte[len];
				for (int i = 0; i < len; i++) {
					bytes[i] = byteList.get(i);
				}
				byteList.clear();
				pageEncode = encoding.getEncoding(bytes);
				if ("UNKNOWN".equalsIgnoreCase(pageEncode)) {
					pageEncode = "GBK";
				}
				str = new String(bytes, pageEncode);
				sb.append(str);
				str = null;
				bytes = null;
				len = -1;

			} else {
				if (gzin != null) {
					isReader = new InputStreamReader(gzin, pageEncode);
				} else {
					isReader = new InputStreamReader(is, pageEncode);
				}
				reader = new BufferedReader(isReader);
				String string = null;
				while ((string = reader.readLine()) != null) {
					sb.append(string);
					string = null;
				}
			}

		} catch (Exception e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
				is = null;
			}
			if (gzin != null) {
				try {
					gzin.close();
				} catch (IOException ex) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}

			}
			if (urlConnection != null) {
				urlConnection.disconnect();
				urlConnection = null;
			}
			url = null;
		}
		return sb.toString();
	}
	
	/**
	 * 抓取微信链接
	 * @param uri
	 * @return
	 */
	public static String getWechatHtmlSource(String uri) {
		StringBuffer sb = new StringBuffer("");
		URL url = null;
		HttpURLConnection urlConnection = null;

		InputStream is = null;
		byte[] bytes = null;
		List<Byte> byteList = new ArrayList<Byte>();
		String str; 
		int len = -1;
		BufferedReader reader = null;
		InputStreamReader isReader = null;
		GZIPInputStream gzin = null;

		String pageEncode = "UTF-8";
		String randomIP = RandomIp.getRandomIp();
		CPSUtil.xprint("randomIP====>"+randomIP);
		try {
			url = new URL(uri);
			urlConnection = (HttpURLConnection) url.openConnection();
			HttpURLConnection.setFollowRedirects(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(30 * 1000);
			urlConnection.setConnectTimeout(30 * 1000);
			urlConnection.setRequestProperty("referer", "");
			urlConnection.setRequestProperty("cookie", "");
			urlConnection.setRequestProperty("x-forwarded-for",randomIP);
			urlConnection.setRequestProperty("Proxy-Client-IP",randomIP);
			urlConnection.setRequestProperty("WL-Proxy-Client-IP",randomIP);
			urlConnection.setRequestProperty("HTTP_CLIENT_IP",randomIP);
			urlConnection.setRequestProperty("HTTP_X_FORWARDED_FOR",randomIP);
			urlConnection.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;");
			urlConnection.setRequestProperty("Accept-Language", "zh-cn");
			urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 MicroMessenger/7.0.3(0x17000321) NetType/WIFI Language/zh_CN");

			int stateCode = urlConnection.getResponseCode();
			if (stateCode != 200) {
				return "http code :" + stateCode;
			}

			is = urlConnection.getInputStream();
			String acceptEncoding = "";
			if (urlConnection.getHeaderField("Content-Encoding") != null) {
				acceptEncoding = urlConnection.getHeaderField("Content-Encoding");
			}
			gzin = null;
			if (acceptEncoding.indexOf("gzip") != -1) {
				gzin = new GZIPInputStream(is);
			}
			acceptEncoding = null;
			acceptEncoding = urlConnection.getHeaderField("Content-Type");
			int index = -1;
			if (acceptEncoding != null
					&& (index = acceptEncoding.indexOf("charset=")) != -1) {
				index = index + 8;
				if (index <= acceptEncoding.length()) {
					pageEncode = acceptEncoding.substring(index);
				}
			} else {
				pageEncode = null;
			}
			acceptEncoding = null;

			if (pageEncode == null || "AUTO".equals(pageEncode)) {
				len = -1;
				byteList.clear();
				int count = 0;
				if (gzin != null) {
					while ((len = gzin.read()) != -1) {
						count++;
						byteList.add((byte) len);
						if (count >= 200000) {
							break;
						}
					}
				} else {
					while ((len = is.read()) != -1) {
						count++;
						byteList.add((byte) len);
						if (count >= 200000) {
							break;
						}
					}
				}
				len = byteList.size();
				bytes = new byte[len];
				for (int i = 0; i < len; i++) {
					bytes[i] = byteList.get(i);
				}
				byteList.clear();
				pageEncode = encoding.getEncoding(bytes);
				if ("UNKNOWN".equalsIgnoreCase(pageEncode)) {
					pageEncode = "GBK";
				}
				str = new String(bytes, pageEncode);
				sb.append(str);
				str = null;
				bytes = null;
				len = -1;

			} else {
				if (gzin != null) {
					isReader = new InputStreamReader(gzin, pageEncode);
				} else {
					isReader = new InputStreamReader(is, pageEncode);
				}
				reader = new BufferedReader(isReader);
				String string = null;
				while ((string = reader.readLine()) != null) {
					sb.append(string);
					string = null;
				}
			}

		} catch (Exception e) {
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
				is = null;
			}
			if (gzin != null) {
				try {
					gzin.close();
				} catch (IOException ex) {
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}

			}
			if (urlConnection != null) {
				urlConnection.disconnect();
				urlConnection = null;
			}
			url = null;
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String url1222="http://api.baiyug.cn/vip/index.php?url=http://v.youku.com/v_show/id_XMjg5NzgyNjA5Mg==.html";
		String urlString="https://mp.weixin.qq.com/s?src=11&timestamp=1512465132&ver=556&signature=RKufVPs1xQYPJ52BjkkRLlpFBIqEkRScXRwNbG9rSlkNSivSakUYybSxhwRArNtVfoAGnmVKzkzBdEz3zxOqbWXZgO9avZFRSWfsiaoyTAqThIMDQANI0fMV0jNFWjN6&new=1";
	    String urlString2 = "http://211.149.188.92/spc2/buy?t=0&p=ODkwMDUyNDktMjAxNjgzNTI=";
		String htmlString = getWechatHtmlSource("http://yvhc.egxv59.cn/?arg=12656");
		CPSUtil.xprint(htmlString);
		
		//var linnkHref = "Ls25.kaphp.cn/3/static/2/12658_e735133a3cde8ee533d95d2150e869e5.html";
		/*Pattern pattern = Pattern.compile("var( )+linnkHref( )?=( )?\\\"(.+)\\\"");
		Matcher matcher = pattern.matcher(htmlString);
		CPSUtil.xprint(matcher.group());*/
		
		String subStr[] = htmlString.split("linnkHref = ");
		String surl = subStr[1].split("location")[0].replaceAll("\"|;", "").trim();
		CPSUtil.xprint("surl="+surl);
		
		//String _htmlString = getWechatHtmlSource("http://"+surl);
		//CPSUtil.xprint("_htmlString==="+_htmlString);
		
		String scriptStr = "";
		Pattern pattern = Pattern.compile("<script src=\"([^\"]*?)\" language=\"JavaScript\"></script>");
		Matcher matcher = pattern.matcher(htmlString);
		while(matcher.find()){
			scriptStr += matcher.group() + " ";
		}
		CPSUtil.xprint("scriptStr==="+scriptStr);
		
		
		//String ecsString = EscapeUnescape.escape(htmlString);
		//CPSUtil.xprint(ecsString);
	
	}
	

}
