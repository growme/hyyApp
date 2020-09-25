package com.ccnet.core.common.utils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class StringHelper {
	private static Logger log = Logger.getLogger(StringHelper.class);
	private static boolean needDecrypt = false;
	static {
		needDecrypt = "true".equals(System.getProperty("hnccnet-decrypt"));
		System.out.println("Loade00" + needDecrypt);
	}
	
	public static byte[] decrypt(byte[] source) {
		for (int i = 0; i < source.length; i++) {
			source[i] = (byte) ((int) source[i] - (needDecrypt ? 7 : 0));
		}
		return source;
	}
	
	public static boolean needDecrypt(String str) {
		return str.contains("ccnet");
	}
	
	public static final String templateSplit = "$#####$";  //多个短信模板之间的分隔符
	
	public static boolean chineseSimilar(String template, String str2) {
		if (StringUtils.isBlank(template) || StringUtils.isBlank(str2)) {
			return false;
		}
		if (StringUtils.isNotBlank(template)) {
			template = MessageFormat.format(template, 123456);
		}
		template = template.toLowerCase().replaceAll("\\w+", "");
		str2 = str2.toLowerCase().replaceAll("\\w+", "");
		return template.equals(str2);
	}
	
	public static String extractFirst(String templates, String str) {
		List<String> list = extract(templates, str);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	private static List<String> extract(String templates, String str) {
		if (StringUtils.isBlank(templates) || StringUtils.isBlank(str)) {
			return null;
		}
		if (!StringUtils.contains(templates, "{0}")) {
			templates = templates.replaceFirst("\\w{4,8}", "{0}");
		}
		System.out.println(templates);
		MessageFormat format = new MessageFormat(templates);
		try {
			List<String> strs = new ArrayList<String>();
			Object[] objs = format.parse(str);
			for (Object object : objs) {
				strs.add(object.toString());
			}
			return strs;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			printError("提取失败: 模板:" + templates + " 文本:" + str, e);
		}
		return null;
	}
	
	public static boolean checkParameter(Object ... param) {
		printDebug("input params: " + StringUtils.join(param, ","));
		for (Object object : param) {
			if (object == null) {
				return false;
			}
			if (object instanceof String && StringUtils.isBlank((String) object)) {
				return false;
			}
		}
		return true;
	}
	
	public static void printDebug(Object object) {
		printDebug((object == null ? " null " : object.toString()));
	}
	
	public static void printDebug(String message) {
		log.debug(message);
	}
	
	public static void printInfo(String message) {
		log.info(message);
	}
	
	
	public static void printError(String message) {
		log.error(message);
	}
	
	public static void printError(String message, Throwable e) {
		log.error(message, e);
	}
	
	public static void main(String[] args) {
		String str1 = "您的华为商城注册验证码：1939（10分钟内有效）.【华为】";
		String str2 = "您的华为商城注册验证码：1930（10分钟内有效）.【华为】";
		System.out.println(chineseSimilar(str1, str2));
		System.out.println(extractFirst(str1, str2));
		
		System.setProperty("hnccnet-decrypt", "yes");
		
		System.out.println(System.getProperty("hnccnet-decrypt"));
		
	}
}