package com.ccnet.api.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
	
	private static  final  String  ENCODE_KEY="ipoleyhkj/,.}/}[";

	// 加密
	public static String encrypt(String sSrc) {
		try{
			byte[] raw = ENCODE_KEY.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return encoder.encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
		}catch (Exception e){
			System.out.println(e.toString());
			return null;
		}

	}

	// 解密
	public static String decrypt(String sSrc) {
		try {
			byte[] raw = ENCODE_KEY.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			Base64.Decoder decoder = Base64.getDecoder();

			byte[] encrypted1 = decoder.decode(sSrc);

			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original,"utf-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

}
