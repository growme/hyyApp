package com.ccnet.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.util.Base64Utils;

import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;
import com.ccnet.core.common.utils.sigar.SigarInfoEntity;
import com.ccnet.core.common.utils.sigar.SigarUtils;

public final class LicenseHelper {
	private static Logger logger = Logger.getLogger(LicenseHelper.class);
	private static final byte[] DES_KEY = { 104, 8, 28, 93, 76, -80, -113, 72 };
	
	private static List<String> remoteDomains = new ArrayList<String>(0);
	static {
		remoteDomains.add("localhost:8080/erp");
		remoteDomains.add("license.hnccnet.com");
		remoteDomains.add("license1.hnccnet.com");
		remoteDomains.add("license2.hnccnet.com");
	}
	
	/**
	 * 许可是否有效
	 */
	private static boolean islicense = false;
	
	public final static void initLicense() {
		//本地检查
		islicense = chkLicenseLocal();
		logger.info("license is " + islicense);
		//远程认证
		new Thread(new Runnable() {
			public void run() {
				//远程认证一次(远程认证失败时覆盖本地认证， 否则不改变本地值)
				Boolean remoteBoolean = chkLicenseRemote(readLicense());
				if (remoteBoolean != null) {
					islicense = islicense && remoteBoolean;
				}
			}
		}).start();
	}
	
	/**
	 * 加密key的一部分
	 */
	private static int key = 0;
	public static final int getKey() {
		return key;
	}
	
	private  final static boolean chkLicenseLocal() {
		//读取本地的license信息
		String str  = "2029-04-01";
		String license = readLicense();
		if (StringUtils.isNotBlank(license)) {
			//获取本机信息
			Map<String, String> localHardware = getLocalHardware();
			license = decryptBasedDes(license);
			//解析本地license的内容 (authcode|localip|os|cpu|expiry|key)
			System.out.println(license);
			String[] strs = StringUtils.split(license, "|");
			if (ArrayUtils.isNotEmpty(strs) && strs.length == 6) {
				if (!StringUtils.equals(strs[1], localHardware.get("localip"))) {
					return false;
				}
				if (!StringUtils.equals(strs[2], getMD5(localHardware.get("os")))) {
					return false;
				}
				if (!StringUtils.equals(strs[3], getMD5(localHardware.get("cpu")))) {
					return false;
				}
				str = strs[4];
				if (NumberUtils.isDigits(strs[5])) {
					key = Integer.valueOf(strs[5]);
				}
			}
		}
		logger.info("有效期:" + str);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return new Date().before(sdf.parse(str));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public final static boolean license() {
		return islicense;
	}
	
	/**
	 * @param license
	 * @return null未知， true成功，false失败。
 	 */
	public  final static Boolean chkLicenseRemote(String license) {
		if (StringUtils.isNotBlank(license)) {
			Map<String, String> localHardware = getLocalHardware();
			Map<String, String> params = new HashMap<String, String>();
			params.put("auth_code", encryptBasedDes(license)); //加密后传输
			params.put("hardware", JsonHelper.encodeObject2Json(localHardware));
			Response response = null;
			for (String domain : remoteDomains) {
				response = connect(domain, params);
				if (response != null && response.statusCode() == 200) {
					//返回 status:auth_success|auth_error,code:MD5(license),time:new date();
					String body = response.body();
					return StringUtils.contains(body, "auth_success") && StringUtils.contains(body, getMD5(license));
				}
			}
		}
		return null;
	}
	
	private final static Response connect(String domain, Map<String, String> params){
		try {
			return Jsoup.connect("http://" + domain + "/authLog/remote")
					.userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
					.timeout(30 * 1000).ignoreContentType(true)
					.data(params).method(Method.POST).execute();
		} catch (Exception e) {
			logger.error("远程调用服务器失败");
		}
		return null;
	}
	
	public final static Map<String, String> getLocalHardware() {
		Map<String, String> infoMap = new HashMap<String, String>(0);
 		//获取cpu
		Map<String, SigarInfoEntity> localMap = SigarUtils.convertToMap(SigarUtils.getCpuInfos());
		SigarInfoEntity entity = null;
		entity = localMap.get("cpu.vendor");
		String cpuVendor = entity != null ? entity.getValue() : null;
		entity = null;
		entity = localMap.get("cpu.model");
		String cpuModel = entity != null ? entity.getValue() : null;
		entity = null;
		infoMap.put("cpu", StringUtils.trimToEmpty(cpuVendor) + " " + StringUtils.trimToEmpty(cpuModel));
		
		localMap = SigarUtils.convertToMap(SigarUtils.getJvmInfos());
		entity = localMap.get("hostAddress");
		infoMap.put("localip", StringUtils.trimToEmpty(entity != null ? entity.getValue() : null));
		entity = null;
		
		entity = localMap.get("os.name");
		infoMap.put("os", StringUtils.trimToEmpty(entity != null ? entity.getValue() : null));
		entity = null;
		
		localMap = SigarUtils.convertToMap(SigarUtils.getMemoryInfos());
		entity = localMap.get("mem.total");
		infoMap.put("memtotal", StringUtils.trimToEmpty(entity != null ? entity.getValue() : null));
		entity = null;
		/*localMap = SigarUtils.convertToMap(SigarUtils.getFileInfos());
		entity = localMap.get("all.file.total");
		infoMap.put("disktotal", StringUtils.trimToEmpty(entity != null ? entity.getValue() : null));
		entity = null;
		entity = localMap.get("all.file.used");
		infoMap.put("diskused", StringUtils.trimToEmpty(entity != null ? entity.getValue() : null));
		entity = null;*/
		return infoMap;
	}

	private final static String licenseFile() {
		return System.getProperty("java.home") + File.separator + "license.txt";
	}
	
	/**
	 * 读取认证码
	 * @return
	 */
	private final static String readLicense() {
		FileReader fr = null;
		StringBuilder license = new StringBuilder("");
		try {
			fr = new FileReader(new File(licenseFile()));
			char[] buf = new char[1024];  
	        int len = 0;  
	        while((len=fr.read(buf))!=-1){  
	        	license.append(new String(buf,0,len));
	        } 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("未找到license文件");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("读取license文件失败");
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("关闭读文件流失败");
			}
		}
		return license.toString();
	}
	
	public final static void writeLicense(String license) {
		FileWriter fos = null;
		try {
			fos = new FileWriter(licenseFile());
			fos.write(license);
			fos.flush();
			logger.info("Update license to file ");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("未找到license文件");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("写入license文件失败");
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("关闭写文件流失败");
			}
		}
	}
	
	private static String getMD5(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes("UTF-8"));
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	        logger.error("MD5加密出现错误");
	    }
	    return str;
	}
	
	private static String encryptBasedDes(String data) {
		if (data == null) {
			return data;
		}
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			encryptedData =Base64Utils.encodeToString(cipher.doFinal(data
					.getBytes("UTF-8")));
//			encryptedData = new BASE64Encoder().encode(cipher.doFinal(data
//					.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}
	
	private static String decryptBasedDes(String cryptData) {
		if (cryptData == null) {
			return cryptData;
		}
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(DES_KEY);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher
					.doFinal(Base64Utils.decodeFromString(cryptData)), "UTF-8");
			
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	
	public final static void main(String[] args) {
		chkLicenseLocal();
		getLocalHardware();
		System.out.println(">" + getMD5("hehe"));
		String string = "中文";
		string = encryptBasedDes(string);
				System.out.println(string);
				string  = decryptBasedDes(string);
				System.out.println(string);
	}
	
	
}
