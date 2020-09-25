package com.ccnet.core.common.utils.express;

import java.security.MessageDigest;

import org.springframework.util.Base64Utils;


/** 
 *生成摘要工具类 
 */
public class DigestUtil {
	public static final String GBK = "GBK";
	public static final String UTF8 = "UTF-8";

	public static String encryptBASE64(byte[] md5) throws Exception {
		
		return Base64Utils.encodeToString(md5);
		
		//return (new BASE64Encoder()).encodeBuffer(md5);
	}

	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

	public static String digest(String data, String sign, String charset)
			throws Exception {
		return encryptBASE64(encryptMD5((data + sign).getBytes(charset)));
	}

	public static void main(String[] args) throws Exception {
		System.out.println(digest("中通ABC123", "AAA", UTF8));
	}
}