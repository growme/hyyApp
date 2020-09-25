package com.ccnet.core.common.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.xml.sax.InputSource;

import com.ccnet.core.common.AdType;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.MD5;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.entity.SbContentDomain;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.core.entity.SbMemberDomain;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbContentInfo;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

public class CPSUtil {
	public static final String CHARSET = "UTF-8";
	/**
	 * DES算法密钥
	 */
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128,-65 };
	
	/**
	 * DES算法密钥
	 */
	private static final byte[] ORDER_DES_KEY = { 104, 8, 28, 93, 76, -80, -113, 79 };
	
	
	private static final String[] LEVEL_COLOR = {
		"badge-default",
		"badge-info",
		"badge-success",
		"badge-yellow",
		"badge-warning",
		"badge-darkpink",
		"badge-blueberry",
		"badge-orange",
		"badge-maroon",
		"badge-darkorange"
	};
	
	private static final String[] RANDOM_COLOR = {
		"#14aae4",
		"#03b3b2",
		"#428bca",
		"#e35b5a",
		"#44b6ae",
		"#8cc474",
		"#e31436",
		"#ff5000",
		"#f10215",
		"#f74140",
		"#f3015c"
	};
	
	
	/**
	 * 得到request对象
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();	
		return request;
	}
	
	/**
	 * 得到request对象
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		return response;
	}
	
	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public static Dto getParamAsDto() {
		Dto dto = new BaseDto();
		Map properties = getRequest().getParameterMap();
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			dto.put(name, value); 
		}
		//CPSUtil.xprint("打印参数："+dto);
		return dto;
	}
	

	/**
	 * 将JavaBean对象中的属性值拷贝到Dto对象
	 * 
	 * @param pFromObj
	 *            JavaBean对象源
	 * @param pToDto
	 *            Dto目标对象
	 */
	public static void copyPropFromBean2Dto(Object pFromObj, Dto pToDto) {
		if (pToDto != null) {
			try {
				pToDto.putAll(BeanUtils.describe(pFromObj));
				// BeanUtils自动加入了一个Key为class的键值,故将其移除
				pToDto.remove("class");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 检查list是否为空
	 * @param list
	 * @return
	 */
	public static boolean checkListBlank(List list) {
		if(list!=null && list.size()>0){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 检查list是否为空
	 * @param list
	 * @return
	 */
	public static boolean listEmpty(List list) {
		return checkListBlank(list);
	}
	
	/**
	 * 检查list是否不为空
	 * @param list
	 * @return
	 */
	public static boolean listNotEmpty(List list) {
		return !listEmpty(list);
	}
	
	public static String getLevelColor(int level) {
		return LEVEL_COLOR[level-1];
	}
	
	/**
	 * 随机获取颜色值
	 * @return
	 */
	public static String getRandomColor() {
		int rand = RandomNum.getRandIntVal(RANDOM_COLOR.length-1);
		return RANDOM_COLOR[rand];
	}
	
	/**
	 * 将ISO8859_1编码的字符串转化为UTF-8编码的字符串 主要用来处理中文显示乱码的问题
	 * 
	 * @param ISO8859_1str
	 *            通过ISO8859_1编码的字符串
	 * @return 通过UTF-8编码的字符串
	 * @throws UnsupportedEncodingException
	 */
	public static String CharsetFilter(String keys) {
		// 将参数UTF-8编码
		String value1 = keys;
		try {
			value1 = new String(value1.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			xprint("转换字符编码出错!");
		}
		System.out.println("utf-8==" + value1);
		return value1;
	}

	
	/**
	 * 基于MD5算法的单向加密
	 * 
	 * @param strSrc
	 *            明文
	 * @return 返回密文
	 */
	public static String encryptBasedMd5(String strSrc) {
		String outString = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] outByte = md5.digest(strSrc.getBytes(CHARSET));
			outString = outByte.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outString;
	}
	
	/**
	 * 加密派单路径
	 * @param param
	 * @return
	 */
	public static String getParamEncrypt(String param){
		return encryptBased64(encryptBasedDes(param));
	}
	
	
	/**
	 * 解密派单路径
	 * @param param
	 * @return
	 */
	public static String getParamDecrypt(String param){
		return decryptBasedDes(decryptBased64(param));
	}
	
	/**
	 * 基于base64编码加密
	 * @param enStr
	 * @return
	 */
	public static String encryptBased64(String enStr) {
		String outString = null;
		try {
			byte[] outByte = _Base64.encode(enStr.getBytes(CHARSET));
			outString = new String(outByte, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outString;
	}
	
	/**
	 * 基于base64编码解密
	 * @param deStr
	 * @return
	 */
	public static String decryptBased64(String deStr) {
		String outString = null;
		try {
			byte [] outByte = _Base64.decode(deStr);
			outString = new String(outByte, CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outString;
	}
	
	/**
	 * 随机生成一个新的des key
	 * @return
	 */
	private static byte[] generateDesKey() {
		// 生成个DES密钥
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56); //选择DES算法,密钥长度必须为56位
			Key key = keyGenerator.generateKey(); //生成密钥
			return key.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String encryptBasedDes(String data) {
		return encryptBasedDes(data, DES_KEY);
	}
	
	/**
	 * 专门加密订单参数
	 * @param data
	 * @return
	 */
	public static String encryptOrderDes(String data) {
		if (StringUtils.isBlank(data)) {
			return data;
		}
		try {
			/**
			 * 为了避免重复加密，所以先解一次密，如果解密异常，则认为是未加密数据
			 * 但后来发现当一个中文输入来解密的时候，也不会报异常, 但解密的结果为空字符串，所以就加了一个这样的判断 
			 */
			String string = decryptBasedDes(data, ORDER_DES_KEY);
			if (StringUtils.isBlank(string)) {
				return encryptBasedDes(data, ORDER_DES_KEY);
			}
			return data;
		} catch (Exception e) {
			// TODO: handle exception
			return encryptBasedDes(data, ORDER_DES_KEY);
		}
	}
	
	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static String encryptBasedDes(String data, byte[] desKey) {
		if (data == null || desKey == null) {
			return data;
		}
		String encryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(desKey);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 加密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 加密，并把字节数组编码成字符串
			
			encryptedData =Base64Utils.encodeToString(cipher.doFinal(data
					.getBytes(CHARSET)));
			
			
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	public static String decryptBasedDes(String cryptData) {
		return decryptBasedDes(cryptData, DES_KEY);
	}
	
	/**
	 * 专门解密订单相关参数
	 * @param cryptData
	 * @return
	 */
	public static String decryptOrderDes(String cryptData) {
		if (StringUtils.isBlank(cryptData)) {
			return cryptData;
		}
		try {
			String string = decryptBasedDes(cryptData, ORDER_DES_KEY);
			return StringUtils.isBlank(string) ? cryptData : string;
		} catch (Exception e) {
			// TODO: handle exception
			return cryptData;
		}
	}
	
	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static String decryptBasedDes(String cryptData, byte[] desKey) {
		if (cryptData == null || desKey == null) {
			return cryptData;
		}
		String decryptedData = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			DESKeySpec deskey = new DESKeySpec(desKey);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(deskey);
			// 解密对象
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(cipher
					.doFinal(Base64Utils.decodeFromString(cryptData)), CHARSET);
			
			
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	
	/**
	 * JavaBean之间对象属性值拷贝
	 * 
	 * @param pFromObj
	 *            Bean源对象
	 * @param pToObj
	 *            Bean目标对象
	 */
	public static void copyPropBetweenBeans(Object pFromObj, Object pToObj) {
		if (pToObj != null) {
			try {
				BeanUtils.copyProperties(pToObj, pFromObj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断对象是否为NotEmpty(!null或元素>0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isNotEmpty(Object pObj) {
		if (pObj == null)
			return false;
		if (pObj == "")
			return false;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return false;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return false;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNotEmptyForInteger(Integer integer) {
		return integer != null && integer > 0;
	}
	
	/**
	 * 正则表达式判断数字
	 * @param str
	 * @return
	 */
	public static boolean isNumerByPrex(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	public static void render(String text, String contentType,HttpServletResponse response) {
		try {
			response.setContentType(contentType + ";charset=utf-8");
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用打印方法 必须指定类型
	 */
	public static void printObj(String type, Object obj) {
		if (type.equals("String")) {
			System.out.println(obj.toString());
		}
		if (type.equals("List")) {
			List list = (ArrayList) obj;
			Iterator it = list.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}
		if (type.equals("Set")) {
			Set kset = (HashSet) obj;
			Iterator it = kset.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}
		if (type.equals("Array")) {
			String[] arr = (String[]) obj;
			for (int i = 0; i < arr.length; i++) {
				System.out.println(arr[i]);
			}
		}
		if (type.equals("Boolean")) {
			System.out.println(obj);
		}
		if (type.equals("Map")) {
			Map kmap = (HashMap) obj;
			Set key = kmap.keySet();
			Set entry = kmap.entrySet();

			/*
			 * Iterator itk = key.iterator(); while(itk.hasNext()){
			 * System.out.println("打印Map key："+itk.next()); }
			 */
			Iterator itv = entry.iterator();
			while (itv.hasNext()) {
				System.out.println(String.valueOf(itv.next()));
			}

		}
	}

	/**
	 * 打印共用方法
	 */
	public static void xprint(Object obj) {
		if (obj != null && obj instanceof String) {// 打印String
			printObj("String", obj);
		}
		if (obj != null && obj instanceof List) {// 打印List
			printObj("List", obj);
		}
		if (obj != null && obj instanceof Map) {// 打印Map
			printObj("Map", obj);
		}
		if (obj != null && obj instanceof Set) {// 打印Set
			printObj("Set", obj);
		}
		if (obj != null && obj instanceof Array) {// 打印数组
			printObj("Array", obj);
		}
		if (obj != null && obj instanceof Boolean) {// 打印数组
			printObj("Boolean", obj);
		}
	}
	
	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public static Map getParamAsDto(HttpServletRequest request) {
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String value = null;
			String key = (String) keyIterator.next();
			if((map.get(key)).getClass().isArray()) {
			    value = ((String[]) (map.get(key)))[0];
			}else{
				value = (String)map.get(key);
			}
			map.put(key, value.trim());
		}
		//CPSUtil.xprint("打印参数："+map);
		return map;
	}
	
	public static String getSitePic(String key,int flag,HttpServletRequest request){
		//判断文件是否存在
		String retPath = null;
		String filePath = null;
		String fileUrl = CPSUtil.getParamValue(key);
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
		
		if(CPSUtil.isNotEmpty(fileUrl)){
			filePath = CPSUtil.getContainPath() + fileUrl;
			if(FileUtil.exist(filePath)){
				retPath = serverPath + fileUrl;
			}else{
				switch (flag) {
				case 0:
					retPath = basePath + "static/images/logo.png";
					break;
				case 1:
					retPath = basePath + "static/images/favicon.ico";
					break;
				case 2:
					retPath = basePath + "static/img/login-logo.png";
					break;
				case 3:
					retPath = basePath + "static/img/favicon.ico";
					break;
				default:
					break;
				}
			}
		}else{
			switch (flag) {
			case 0:
				retPath = basePath + "static/images/logo.png";
				break;
			case 1:
				retPath = basePath + "static/images/favicon.ico";
				break;
			case 2:
				retPath = basePath + "static/img/login-logo.png";
				break;
			case 3:
				retPath = basePath + "static/img/favicon.ico";
				break;
			default:
				break;
			}
		}
		
		CPSUtil.xprint("retPath="+retPath);
		return retPath;
	}
	
	public static String getContainterPath() {
		String TOMCAT_ROOT = "";
		String CONFIGPATH = CPSUtil.class.getResource("/").getPath();
		//获取TOMCAT的根路径
		int index = CONFIGPATH.indexOf("/webapps");
		if (index > 0 && index < CONFIGPATH.length()) {
		    TOMCAT_ROOT = CONFIGPATH.substring(0, index);
		}
		return TOMCAT_ROOT;
	}
	
	public static String getContainPath() {
		String TOMCAT_ROOT = "";
		String CONFIGPATH = CPSUtil.class.getResource("/").getPath();
		//获取TOMCAT的根路径
		int index = CONFIGPATH.indexOf("/webapps");
		if (index > 0 && index < CONFIGPATH.length()) {
		    TOMCAT_ROOT = CONFIGPATH.substring(0, index+8);
		}
		return TOMCAT_ROOT;
	}
	
	/**
	 * 处理保留小数点后两位
	 * @param val
	 * @return
	 */
	public static double getDoubleValScale(double val) {
		BigDecimal bigDecimal=new BigDecimal(val);
		return bigDecimal.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 获取容器路径
	 * @return
	 */
	public static String getCXTPath() {
		return SpringWebContextUtil.getWebApplicationContext().getServletContext().getContextPath();
	}
	
	/**
	 * 根据提供的天数获取开始日期和结束日期 例如：获取近三天 的开始和结束日期
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateByDay(int day,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -day);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 根据提供的分钟数获取开始日期和结束日期 例如：获取近五分钟 的开始和结束日期
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateByMinute(int minute,String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -minute);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 比较日期1是否大于日期2
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean date1AfterDate2(String beginTime,String endTime) {
	   boolean temp = false;
	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			Date bt = sdf.parse(beginTime); 
			Date et = sdf.parse(endTime);
			if(bt.after(et)){ 
			    temp = true;
		     } 
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return temp;
	}
	
	/**
     * 判断时间是否在时间段内
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean isBetweenTime(String beginTime, String endTime) {
    	boolean temp = false;
    	try {
    		SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
			Date nowTime = df.parse(df.format(new Date()));
			Calendar date = Calendar.getInstance();
	        date.setTime(nowTime);
	        Calendar begin = Calendar.getInstance();
	        begin.setTime(df.parse(beginTime));
	        Calendar end = Calendar.getInstance();
	        end.setTime(df.parse(endTime));
	        if (date.after(begin) && date.before(end)) {
	        	temp = true;
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return temp;
    }
	
	
	/**
	 * 根据给出的日期计算日期
	 * @param day
	 * @return
	 */
	public static String getDateByUDay (int day) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//返回给出的天指定的日期
		calendar.add(Calendar.DAY_OF_MONTH,day);
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 返回自定义格式的当前日期时间字符串
	 * 
	 * @param format
	 *            格式规则
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime(String format) {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat(format);
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}
	
	/**
	 * 计算日期差 精确到小时
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static int getSubHour(String date1,String date2)  {
		int hours= 0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal1=Calendar.getInstance();
		try {
			cal1.setTime(sdf.parse(date1));
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(sdf.parse(date2));
			long l=cal2.getTimeInMillis()-cal1.getTimeInMillis();
		    hours=new Long(l/(1000*60*60*1)).intValue();
			System.out.println("2个日期之间相差："+hours+"小时。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hours;
	}
	
	/**
	 * 计算日期差 精确到秒
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException 
	 */
	public static int getSubSecond(String date1,String date2)  {
		int second= 0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal1=Calendar.getInstance();
		try {
			cal1.setTime(sdf.parse(date1));
			Calendar cal2=Calendar.getInstance();
			cal2.setTime(sdf.parse(date2));
			long l=cal2.getTimeInMillis()-cal1.getTimeInMillis();
			second=new Long(l/(1000)).intValue();
			System.out.println("2个日期之间相差："+second+"秒。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return second;
	}
	
	
	/**
	 * 验证QQ号码
	 * 
	 * @param QQ
	 * @return
	 */
	public static boolean isQQ(String QQ) {
		boolean flag = false;
		try {
			String check = "^[1-9][0-9]{4,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(QQ);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
    
	
	/**
	 * 验证邮箱
	 *
	 * @param email
	 * @return
	 */

	public static boolean isEmail(String email) {
	    boolean flag = false;
	    try {
	        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	        Pattern regex = Pattern.compile(check);
	        Matcher matcher = regex.matcher(email);
	        flag = matcher.matches();
	    } catch (Exception e) {
	        flag = false;
	    }
	    return flag;
	}

	/**
	 * 验证手机号码，11位数字，1开通，第二位数必须是3456789这些数字之一 *
	 * @param mobileNumber
	 * @return
	 */
	public static boolean isMobile(String mobile) {
	    boolean flag = false;
	    try {
	        Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
	        Matcher matcher = regex.matcher(mobile);
	        flag = matcher.matches();
	    } catch (Exception e) {
	        e.printStackTrace();
	        flag = false;

	    }
	    return flag;
	}
	
	/**
	 * 验证固话号码
	 * @param mobileNumber
	 * @return
	 */
	public static boolean isPhone(String phone) {
	    boolean flag = false;
	    try {
	        Pattern regex = Pattern.compile("^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$");
	        Matcher matcher = regex.matcher(phone);
	        flag = matcher.matches();
	    } catch (Exception e) {
	        e.printStackTrace();
	        flag = false;

	    }
	    return flag;
	}
	
	/**
	 * 判断微信
	 * @param request
	 * @return
	 */
	public static String isWeixin(HttpServletRequest request) {
	    String userAgent = request.getHeader("User-Agent").toLowerCase();
		if(userAgent.indexOf("micromessenger")>-1 && !isWeixinDevTools(request) && !isWechatWindows(request)){//微信客户端
			return "1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 判断微信开发者工具
	 * @param request
	 * @return
	 */
	public static boolean isWeixinDevTools(HttpServletRequest request) {
	    String userAgent = request.getHeader("User-Agent").toLowerCase();
		if(userAgent.indexOf("wechatdevtools")>-1){//微信客户端
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断微信电脑版
	 * @param request
	 * @return
	 */
	public static boolean isWechatWindows(HttpServletRequest request) {
	    String userAgent = request.getHeader("User-Agent").toLowerCase();
		if(userAgent.indexOf("windowswechat")>-1){//微信客户端
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 验证手机号码的有效性
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNum(String mobiles){
		Pattern p = Pattern.compile("^1[3|4|5|6|7|8|9][0-9]{9}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches()+"---");
		return m.matches();
	}

	/**
	 * 返回当前字符串型日期
	 * 
	 * @return String 返回的字符串型日期
	 */
	public static String getCurDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}
	
	/**
	 * 返回当前日期时间字符串<br>
	 * 默认格式:yyyy-mm-dd hh:mm:ss
	 * 
	 * @return String 返回当前字符串型日期时间
	 */
	public static String getCurrentTime() {
		String returnStr = null;
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		returnStr = f.format(date);
		return returnStr;
	}
	
	/**
	 * 返回当前字符串型日期
	 * 
	 * @return String 返回的字符串型日期
	 */
	public static String getCurDateNoSplit() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}
	
	/**
	 * 返回当前字符串型日期
	 * 
	 * @return String 返回的字符串型日期
	 */
	public static String getCurDateNoSplitTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}
	
	
	/**
	 * 返回当前字符串型日期带中文
	 * 
	 * @return String 返回的字符串型日期
	 */
	public static String getCurDateZN() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy年MM月dd日");
		String strDate = simpledateformat.format(calendar.getTime());
		return strDate;
	}
	
	/**
	 * 直接输出纯字符串.
	 */
	public static void renderText(String text,HttpServletResponse response) {
		render(text, "text/plain",response);
	}

	/**
	 * 直接输出纯HTML.
	 */
	public static void renderHtml(String text,HttpServletResponse response) {
		render(text, "text/html",response);
	}

	/**
	 * 直接输出纯XML.
	 */
	public static void renderXML(String text,HttpServletResponse response) {
		render(text, "text/xml",response);
	}
	
	/**
	 * 获取 ServletContext对象
	 * @return
	 */
	public static ServletContext getSContext() {
		return SpringWebContextUtil.getWebApplicationContext().getServletContext();
	}
	
	/**
	 * 获取全局参数
	 * @param attr_name
	 * @return
	 */
	public static Object getContextAtrribute(String attr_name) {
		return getSContext().getAttribute(attr_name);
	}
	
	/**
	 * 设置全局参数
	 * @param attr_name
	 * @param object
	 */
	public static void setContextAtrribute(String attr_name,Object object) {
		getSContext().setAttribute(attr_name, object);
	}
	
	/**
	 * 获取参数
	 * @param param_key
	 * @return
	 */
	
	public static String getParamValue(String paramKey) {
		return getParamValue(paramKey, null);
	}
	
	public static String getParamValue(String paramKey, String defaultValue) {
		String paramValue=null;
		Dto paramDto = (BaseDto)getSContext().getAttribute(Const.CT_PARAM_LIST);
		if(isNotEmpty(paramDto)){
			SystemParams sysparam = (SystemParams)paramDto.get(paramKey);
			if(isNotEmpty(sysparam)){
				paramValue = sysparam.getParamValue();
			}
		}
		if (paramValue == null) {
			paramValue = defaultValue;
		}
		return paramValue;
	}
	
	/**
	 * 格式化数值
	 * @param val
	 * @param pattern
	 * @return
	 */
	public static String formatDoubleVal(Double val,String pattern){
		 DecimalFormat df=(DecimalFormat) DecimalFormat.getInstance(); 
		 df.applyPattern(pattern); 
		 return df.format(val); 
	}
	
	/**
	 * 将字符转换为集合
	 * @param target
	 * @param regex
	 * @return
	 */
	public static List<String> string2List(String target,String regex) {
		List<String> mList = new ArrayList<String>();
		if(isNotEmpty(target)){
			String  str [] = target.split(regex);
			for (String mstr : str) {
				mList.add(mstr);
			}
		}
		return mList;
	}
	
	
	/**
	 * 将字符转换为集合
	 * @param target
	 * @param regex
	 * @return
	 */
	public static List<Map<String, String>> string2MapList(String target,String regex,String key) {
		List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
		if(isNotEmpty(target)){
			Map<String, String> cmMap = null;
			String  str [] = target.split(regex);
			int count =0;
			for (String mstr : str) {
				if(isNotEmpty(mstr)){
					count++;
					cmMap = new HashMap<String, String>();
					cmMap.put(key, mstr);
					cmMap.put("num", count+"");
					mList.add(cmMap);
				}
			}
		}
		return mList;
	}
	
	/**
	 * 获取所有系统参数
	 * @return
	 */
	public static Dto getAllSysParam(){
		Dto paramMap = new BaseDto();
		String param_value=null;
		Dto paramDto = (BaseDto)getSContext().getAttribute(Const.CT_PARAM_LIST);
		if(isNotEmpty(paramDto)){
			String param_key = "";
			Iterator<String> iterator = paramDto.keySet().iterator();
			while (iterator.hasNext()) {
				param_key = iterator.next();
				SystemParams sysparam = (SystemParams)paramDto.get(param_key);
				if(isNotEmpty(sysparam)){
					param_value = sysparam.getParamValue();
					paramMap.put(param_key, param_value);
				}
			}
		}
		return paramMap;
	}
	
	
	/**
	 * 获取指定字典参数集合
	 * @param code_name
	 * @return
	 */
	public static List<SystemCode> getCodeList(String code_name) {
		Dto codeDto = (BaseDto)getContextAtrribute(Const.CT_CODE_LIST);
		List<SystemCode> coList = codeDto.getAsList(code_name); 
		if(CPSUtil.isNotEmpty(coList)){
			coList = sortCodeList(coList, "asc");
		}
		return coList;
	}
	
	
	/**
	 * 对list集合进行冒泡排序
	 * @param dList 排序集合
	 * @param sort_code 排序字段
	 * @param sort_type asc or desc
	 * @return
	 */
	public static List<SystemCode> sortCodeList(List<SystemCode> dList,String sort_type){
		SystemCode temp = null;
		try {
            
			SystemCode sc1 = null;
			SystemCode sc2 = null;
			
			Integer sort_id1 = null;
			Integer sort_id2 = null;
			
			for(int i=0;i<dList.size()-1;i++) {
			  for(int j=1;j<dList.size()-i;j++) {	
				    sc1 = dList.get(j-1);
				    sc2 = dList.get(j);
					sort_id1 = sc1.getOrderNumber();
					sort_id2 = sc2.getOrderNumber();
				    
					if("asc".equals(sort_type)){
						if(sort_id1.compareTo(sort_id2)>0){
							temp = dList.get(j-1);
							dList.set((j-1), dList.get(j)); 
							dList.set(j, temp);
						}
				  }else{
					    if(sort_id1.compareTo(sort_id2)<0){
						    temp = dList.get(j-1);
						    dList.set((j-1), dList.get(j)); 
						    dList.set(j, temp);
					    }
				  }
			   }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dList;
		
	}
	
	
	/**
	 * 根据值获取字典描述
	 * @param coList
	 * @param code_value
	 * @return
	 */
	public static String getCodeName(String field_code,String code_value) {
		String retStr = "";
		List<SystemCode> coList = getCodeList(field_code);
		if(isNotEmpty(coList)){
			for (SystemCode sc : coList) {
				if(code_value.trim().equalsIgnoreCase(sc.getCodeValue())){
					retStr = sc.getValueDesc();
					//CPSUtil.xprint("code_desc="+retStr);
					break;
				}
			}
		}
		return retStr;
	}
	
	/**
	 * 根据值获取字典值
	 * @param coList
	 * @param code_desc
	 * @return
	 */
	public static String getCodeValue(String field_code,String code_desc) {
		String retStr = "";
		List<SystemCode> coList = getCodeList(field_code);
		if(isNotEmpty(coList)){
			for (SystemCode sc : coList) {
				if(code_desc.trim().equalsIgnoreCase(sc.getValueDesc())){
					retStr = sc.getCodeValue();
					//CPSUtil.xprint("code_value="+retStr);
					break;
				}
			}
		}
		return retStr;
	}
	
	/**
	 * 根据值获取字典值
	 * @param coList
	 * @param code_desc
	 * @return
	 */
	public static String getCodeDescValue(String field_code,String code_value) {
		String retStr = "";
		List<SystemCode> codeList = getCodeList(field_code);
		if(isNotEmpty(codeList)){
			for (SystemCode sc : codeList) {
				if(code_value.trim().equals(sc.getCodeValue())){
					retStr = sc.getCodeDesc();
					break;
				}
			}
		}
		return retStr;
	}
	
	/**
	 * 倒序字符串
	 * @param s
	 * @return
	 */
	public static String reverse(String s) {
	  return new StringBuffer(s).reverse().toString();
	}
	
	/**
	 * 倒序字符串
	 * @param s
	 * @return
	 */
	public static String reverse1(String s) {
	  int length = s.length();
	  if (length <= 1)
	   return s;
	  String left = s.substring(0, length / 2);
	  String right = s.substring(length / 2, length);
	  return reverse1(right) + reverse1(left);
	 }
	
	/**
	 * 随机生成简体汉字
	 * @return
	 */
	 public static String getRandomJTChar() {
        String str = "";
        int highCode;
        int lowCode;
        Random random = new Random();
        highCode = (176 + Math.abs(random.nextInt(39))); //B0 + 0~39(16~55) 一级汉字所占区
        lowCode = (161 + Math.abs(random.nextInt(93))); //A1 + 0~93 每区有94个汉字

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(highCode)).byteValue();
        b[1] = (Integer.valueOf(lowCode)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
	 
	/**
	 * 生成随机繁体汉字
	 * 
	 * @return
	 */
	 public static String getRandomFTChar() {
		String str = "";
		int hightPos;
		int lowPos;
		Random random = new Random();
		hightPos = (176 + Math.abs(random.nextInt(20)));
		lowPos = (161 + Math.abs(random.nextInt(93)));
		// 一个汉字由两个字节组成
		byte[] b = new byte[2];
		b[0] = (Integer.valueOf(hightPos)).byteValue();
		b[1] = (Integer.valueOf(lowPos)).byteValue();
		try {
			str = new String(b, "BIG5");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	 
	 /**
	  * 随机获取词
	  */
	 public static String getRandomZhCode(){
		 StringBuffer sbBuffer = new StringBuffer("");
		 for (int i = 0; i < 2; i++) {
			 sbBuffer.append(getRandomFTChar());
		 }
		 return sbBuffer.toString();
	 }
	  
	
	/**
	 * 获取头像集合
	 * @return
	 */
	public static List<Dto> getUserAvatarsList(){
		Dto avDto =null;
		String avName = null;
		List<Dto> avlist = new ArrayList<Dto>();
		for (int i = 1; i < 19; i++) {
			avDto = new BaseDto();
			avName = "hpic"+i+".jpg";
			avDto.put("picName", avName);
			avDto.put("picValue", "hpic"+i);
			avDto.put("picUrl","/static/img/avatars/"+avName);
			avlist.add(avDto);
		}
		return avlist;
	}
	
    //随机获取配置的域名池的域名
	public static String getRandomDomain(String key) {
		//获取随机域名
		String cdomain = "";
		String domains = getParamValue(key);
		if(isNotEmpty(domains)){
			String domain[] = domains.split(";");
			if(domain.length==1){
				cdomain = domain[0];
			}else{//多个域名
				Random r = new Random();
				cdomain = domain[r.nextInt(domain.length)];
			}
		}
		xprint("domain=>>>>>>"+cdomain);
		return cdomain;
	}
	
	/**
	 * 随机获取支付宝口令
	 * @return
	 */
	public static String getRandChatCode() {
		String _code = "";
		String chatCode = CPSUtil.getParamValue(Const.CT_ALIPAY_CHAT_CODE);
		if(CPSUtil.isNotEmpty(chatCode)){
			String _codes[] = chatCode.split(";");
			if(_codes.length==1){
				_code = _codes[0];
			}else{
				Random r = new Random();
				_code = _codes[r.nextInt(_codes.length)];
			}
		}
		xprint("_code=>>>>>>"+_code);
		return _code;
	}
	
	/**
	 * 获取公用域名
	 * @param domainType
	 * @return List<SbMemberDomain>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public static List<SbContentDomain> getOrderDomainList(ContentDomainType domainType) {
		List<SbContentDomain> domainInfos = null;
		Dto domainDto = (BaseDto)getSContext().getAttribute(Const.CT_DOMAIN_LIST);
		if(CPSUtil.isNotEmpty(domainDto) && domainDto.size()>0){
			domainInfos = new ArrayList<SbContentDomain>();
			if(domainType.equals(ContentDomainType.TZYM)){
				domainInfos = domainDto.getAsList(Const.CT_TZYM_DOMAIN_LIST);
			} 
			
			if(domainType.equals(ContentDomainType.TGYM)){
				domainInfos = domainDto.getAsList(Const.CT_TGYM_DOMAIN_LIST);
			}
			
			if(domainType.equals(ContentDomainType.TZBY)){
				domainInfos = domainDto.getAsList(Const.CT_TZBY_DOMAIN_LIST);
			}
			
			if(domainType.equals(ContentDomainType.TGBY)){
				domainInfos = domainDto.getAsList(Const.CT_TGBY_DOMAIN_LIST);
			}
		}
		return domainInfos;
	}
	
	/**
	 * 获取指定会员的专属域名
	 * @param domainType
	 * @return List<SbMemberDomain>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public static List<SbMemberDomain> getZOrderDomainList(ContentDomainType domainType) {
		List<SbMemberDomain> domainInfos = null;
		Dto domainDto = (BaseDto)getSContext().getAttribute(Const.CT_ZS_DOMAIN_LIST);
		if(CPSUtil.isNotEmpty(domainDto) && domainDto.size()>0){
			domainInfos = new ArrayList<SbMemberDomain>();
			if(domainType.equals(ContentDomainType.TZYM)){
				domainInfos = domainDto.getAsList(Const.CT_ZS_TZYM_DOMAIN_LIST);
			} 
			
			if(domainType.equals(ContentDomainType.TGYM)){
				domainInfos = domainDto.getAsList(Const.CT_ZS_TGYM_DOMAIN_LIST);
			}
			
			if(domainType.equals(ContentDomainType.TZBY)){
				domainInfos = domainDto.getAsList(Const.CT_ZS_TZBY_DOMAIN_LIST);
			}
			
			if(domainType.equals(ContentDomainType.TGBY)){
				domainInfos = domainDto.getAsList(Const.CT_ZS_TGBY_DOMAIN_LIST);
			}
			
		}
		return domainInfos;
	}
	
	/**
	 * 获取会员专属域名
	 * @param memberId
	 * @param domainType
	 * @return List<SbMemberDomain>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public static List<SbMemberDomain> getSbMemberDomains(ContentDomainType domainType,Integer memberId) {
		List<SbMemberDomain> _domains = null;
		if(CPSUtil.isNotEmptyForInteger(memberId)){
			_domains = new ArrayList<SbMemberDomain>();
			List<SbMemberDomain> domains = getZOrderDomainList(domainType);
			if(listNotEmpty(domains)){
				for (SbMemberDomain memberOrderDomain : domains) {
					if(memberId.equals(memberOrderDomain.getMemberId())){
						_domains.add(memberOrderDomain);
					}
				}
			}
		}
		return _domains;
	}
	
	//随机获取配置的域名池的域名 先获取专属 后获取公共
	public static String getRandomOrderDomain(ContentDomainType domainType,Integer memberId) {
		String domainString = "";
		List<SbMemberDomain> mdomainInfos = getSbMemberDomains(domainType, memberId);
		if(CPSUtil.listNotEmpty(mdomainInfos)){
			SbMemberDomain domain = null;
			if(mdomainInfos.size()==1){
				domain = mdomainInfos.get(0);
			}else{//多个域名
				Random r = new Random();
				domain = mdomainInfos.get(r.nextInt(mdomainInfos.size()));
			}
			domainString = domain.getContentDomain().getDomainName();
		}
		xprint("获取到随机专属域名为："+domainString);
		
		if(CPSUtil.isEmpty(domainString)){//没有专属域名获取公共池的域名
			List<SbContentDomain> domainInfos = getOrderDomainList(domainType);
			if(CPSUtil.listNotEmpty(domainInfos)){
				SbContentDomain domain = null;
				if(domainInfos.size()==1){
					domain = domainInfos.get(0);
				}else{//多个域名
					Random r = new Random();
					domain = domainInfos.get(r.nextInt(domainInfos.size()));
				}
				domainString = domain.getDomainName();
			}
			xprint("获取到随机公共域名为："+domainString);
		}
		
		return domainString;
	}
	
	/**
	 * 通过二级域名提取顶级域名
	 * @param url
	 * @return
	 */
	public static String getTopDomain(String url) {
        String result = url;
        try {
        	Pattern pattern = Pattern.compile("[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co|tw|ltb)\\b()*");
            Matcher matcher = pattern.matcher(url);
            matcher.find();
            result = matcher.group();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
	
	
	
	/**
	 * 检查跳转
	 * @param domian
	 * @param odtype
	 * @return boolean  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-7-27
	 */
	public static boolean checkForwardDomain(String domain,ContentDomainType odtype) {
		//如果开启了域名泛解析 则需要将子域名提取一级域名判断逻辑
		String _domain = null;
		String useGeDomain = CPSUtil.getParamValue(Const.GENERIC_PARSE_DOMAIN);//开启域名泛解析
		if(CPSUtil.isNotEmpty(useGeDomain) && "1".equals(useGeDomain)){
			_domain = CPSUtil.getTopDomain(domain);
			CPSUtil.xprint("获取域名【"+domain+"】对应主域名为====>【"+_domain+"】");
		}else{
			_domain = domain;
		}
		return (checkZForwardDomain(_domain, odtype)||checkOForwardDomain(_domain, odtype));
	}
	
	
	
	
	/**
	 * 检测是否为专属跳转域名
	 * @param domian
	 * @param odtype
	 * @return
	 */
	public static boolean checkZForwardDomain(String domian,ContentDomainType odtype) {
		boolean temp = false;
		String domainName = "";
		if(CPSUtil.isNotEmpty(domian)){
			List<SbMemberDomain> forwarList = getZOrderDomainList(odtype);
			if(listNotEmpty(forwarList)){
				for (SbMemberDomain mdomain : forwarList) {
					domainName = mdomain.getContentDomain().getDomainName();
					if(domainName.equals(domian) || StringUtils.contains(domian,domainName) ){
						temp = true;
						break;
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * 检测是否为跳转域名
	 * @param domian
	 * @param odtype
	 * @return
	 */
	public static boolean checkOForwardDomain(String domian,ContentDomainType odtype) {
		boolean temp = false;
		String domainName = "";
		if(CPSUtil.isNotEmpty(domian)){
			List<SbContentDomain> forwarList = getOrderDomainList(odtype);
			if(listNotEmpty(forwarList)){
				for (SbContentDomain domain : forwarList) {
					domainName = domain.getDomainName();
					if(domainName.equals(domian) || StringUtils.contains(domian,domainName) ){
						temp = true;
						break;
					}
				}
			}
		}
		return temp;
	}
	
	
	/**
	 * 通过visitCode获取会员
	 * @param visitCode
	 * @return
	 */
	public static MemberInfo getMemeberByVisitCode(String visitCode) {
		//获取全局会员信息
		MemberInfo memberInfo = null;
		Dto memberDto = (BaseDto)getContextAtrribute(Const.CT_SYSTEM_MEMBER_LIST);
		if(CPSUtil.isNotEmpty(memberDto)){
			memberInfo = (MemberInfo)memberDto.get(visitCode);
		}
		return memberInfo;
	}
	
	/**
	 * 通过memberId获取会员
	 * @param memberId
	 * @return
	 */
	public static MemberInfo getMemeberById(String memberId) {
		//获取全局会员信息
		MemberInfo memberInfo = null;
		Dto memberDto = (BaseDto)getContextAtrribute(Const.CT_SYSTEM_MEMBER_LIST);
		if(CPSUtil.isNotEmpty(memberDto)){
			memberInfo = (MemberInfo)memberDto.get(memberId+"_mid");
		}
		return memberInfo;
	}
	
	/**
	 * 获取指定会员的所有徒弟
	 * @param memberId
	 * @return
	 */
	public static List<MemberInfo> getChildTDMemeberList(Integer memberId) {
		//获取全局会员信息
		List<MemberInfo> mbList = null;
		Dto memberDto = (BaseDto)getContextAtrribute(Const.CT_SYSTEM_MEMBER_LIST);
		if(CPSUtil.isNotEmpty(memberDto)){
			mbList = memberDto.getAsList(memberId+"_"+Const.CT_TD_MEMBER_LIST);
		}
		return mbList;
	}
	
	
	/**
	 * 获取指定会员的所有徒孙
	 * @param memberId
	 * @return
	 */
	public static List<MemberInfo> getChildTSMemeberList(Integer memberId) {
		//获取全局会员信息
		List<MemberInfo> mbList = null;
		Dto memberDto = (BaseDto)getContextAtrribute(Const.CT_SYSTEM_MEMBER_LIST);
		if(CPSUtil.isNotEmpty(memberDto)){
			mbList = memberDto.getAsList(memberId+"_"+Const.CT_TS_MEMBER_LIST);
		}
		return mbList;
	}
	
	
	//随机获取字符串中的一个
	public static String getRandomAttr(String str) {
		String attr = "";
		if(isNotEmpty(str)){
			String attrs[] = str.split(";");
			if(attrs.length==1){
				attr = attrs[0];
			}else{//多个域名
				Random r = new Random();
				attr = attrs[r.nextInt(attrs.length)];
			}
		}
		xprint("随机获取字符串=>>>>>>"+attr);
		return attr;
	}
	
	
	public String dealForwardUrl(String forwardUrl,HttpServletRequest request){
		String domainUrl = null;
		if(CPSUtil.isNotEmpty(forwardUrl)){
			if(!forwardUrl.contains("http")){
				if(request.getServerPort()==80){
					domainUrl = request.getScheme()+"://"+forwardUrl+request.getContextPath();
				}else{
					domainUrl = request.getScheme()+"://"+forwardUrl+":"+request.getServerPort()+request.getContextPath();
				}
			}
		}
		return domainUrl;
	}
	
	/**
	 * 处理随机域名
	 * @param domainKey
	 */
	public static String getBuyPageUrl(String domainKey,HttpServletRequest request) {
		int sport = request.getServerPort();
		String path = request.getContextPath();
		String buy_url = getRandomDomain(domainKey);
		if(CPSUtil.isEmpty(buy_url)){
		   if(sport==80){
		     buy_url = request.getScheme()+"://"+request.getServerName()+path;
		   }else{
		     buy_url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		   }
		}else{
		   if(sport!=80){
		       //buy_url = buy_url+":"+request.getServerPort();
		       buy_url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		   }
		   if(!buy_url.contains("http://")){
		       buy_url = "http://"+buy_url;
		   }
		}
		return buy_url;
	}
	
	/**
	 * 订单信息存入文件
	 * @param logs
	 */
	public static void saveLogToFile(String logs) {
         try {
		    String savePath = getContainterPath()+"/bin/bakdata/";
		    //处理二级文件夹
			FileUtil.createDir(savePath);
			//处理日期分类文件夹
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			FileUtil.createDir(savePath);
			//处理写入字符串
			savePath += ymd + "_cpsorder_data.dat";;
			xprint("保存文件路径为："+savePath);
			FileUtil.appendMethodB(savePath, logs);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取百分数
	 * @param number
	 * @param digit 保留位数
	 * @return
	 */
	public static String getPercentNum(String number,Integer digit){
		String percentNum = null;
		if(isNotEmpty(number)){
			double vpercent = Double.valueOf(number);
			NumberFormat fmt = NumberFormat.getPercentInstance();  
			if(CPSUtil.isEmpty(digit)){
				digit=2;
			}
	        fmt.setMaximumFractionDigits(digit); 
	        percentNum = fmt.format(vpercent);
		}
		return percentNum;
	}
	
	/**
	 * 判断是否是IE浏览器
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isIE(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isIe = true;
		int index = userAgent.indexOf("msie");
		if (index == -1) {
			isIe = false;
		}
		return isIe;
	}
	
	//处理空格
	public static String rhtml(String content){
		String html = content;
		html = html.replace("\r", "");
		html = html.replace("\n", "");
		html = html.replace("\t", "");
		return html;
	}

	/**
	 * 判断是否是Chrome浏览器
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isChrome(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isChrome = true;
		int index = userAgent.indexOf("chrome");
		if (index == -1) {
			isChrome = false;
		}
		return isChrome;
	}

	/**
	 * 判断是否是Firefox浏览器
	 * 
	 * @param userAgent
	 * @return
	 */
	public static boolean isFirefox(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		boolean isFirefox = true;
		int index = userAgent.indexOf("firefox");
		if (index == -1) {
			isFirefox = false;
		}
		return isFirefox;
	}
	
	/**
	 * 根据请求获取浏览器版本
	 * @return
	 */
	public static String getExplorerVersion(HttpServletRequest request){
		String version = "";
		if(isIE(request)){
			version = "IE";
		}else if(isFirefox(request)){
			version = "Firefox";
		}else if(isChrome(request)){
			version = "Chrome";
		}else{
			version = "IE";//默认IE
		}
		return version;
	}
	
	/**
	 * 判断是否移动设备访问
	 * @param request
	 * @return
	 */
	public static boolean clientIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile",
				"wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",
				"windows ce", "symbian", "series", "webos", "sony",
				"blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",
				"pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin",
				"huawei", "novarra", "coolpad", "webos", "techfaith",
				"palmsource", "alcatel", "amoi", "ktouch", "nexian",
				"ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",
				"smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop",
				"benq", "haier", "^lct", "320x320", "240x320", "176x220",
				"w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",
				"bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
				"doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",
				"keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",
				"midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",
				"newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",
				"port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",
				"send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",
				"sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-",
				"upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp",
				"wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
	
	/**
	 * 获取真实IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	   }
	

	/**
	 * 获取客户端类型
	 * 
	 * @param userAgent
	 * @return
	 */
	public static String getClientExplorerType(HttpServletRequest request) {
		String userAgent = request.getHeader("USER-AGENT").toLowerCase();
		String explorer = "非主流浏览器";
		if (isIE(request)) {
			int index = userAgent.indexOf("msie");
			explorer = userAgent.substring(index, index + 8);
		} else if (isChrome(request)) {
			int index = userAgent.indexOf("chrome");
			explorer = userAgent.substring(index, index + 12);
		} else if (isFirefox(request)) {
			int index = userAgent.indexOf("firefox");
			explorer = userAgent.substring(index, index + 11);
		}
		return explorer.toUpperCase();
	}
	
	/**
	 * 获取浏览器类型
	 * @param request
	 * @return
	 */
	public static String getClientExpType(HttpServletRequest request,String tp) {
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));  
	    Browser browser = userAgent.getBrowser();  
	    OperatingSystem os = userAgent.getOperatingSystem();  
	    if("1".equals(tp)){
	        return browser.getName().trim();//浏览器名
	    }else{
	        return os.getName().trim();//操作系统
	    }
	}
	
	
	/**
	 * 验证是否唯一
	 * @param hashKey
	 * @param session
	 * @return
	 */
	public static boolean isFirstRequest(String hashKey, HttpSession session) {
		synchronized (session) {
			Set<String> reqUniqueInSession = (Set<String>) session.getAttribute(Const.CT_REQUEST_UNIQUE);
			if (reqUniqueInSession == null) {
				reqUniqueInSession = new HashSet<String>();
				session.setAttribute(Const.CT_REQUEST_UNIQUE, reqUniqueInSession);
			}
			return reqUniqueInSession.add(hashKey);
		}
	}
	
	
	/**
	 * 解析xml
	 * @param xml
	 * @return
	 */
	public static Map parseXmlToList(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = (Element) doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 获取内存中的广告
	 * @return 当前类型广告集合
	 */
	public static Dto getAdvertsDto(){
		Dto reDto = new BaseDto();
		List<SbAdvertInfo> advList = (List<SbAdvertInfo>)CPSUtil.getContextAtrribute(Const.CT_ADVERTISE_LIST);
		// 纯文本广告
		List<SbAdvertInfo> text_Adverts = new ArrayList<SbAdvertInfo>();
		// 单图广告列表
		List<SbAdvertInfo> single_pic_Adverts = new ArrayList<SbAdvertInfo>();
		// 单图图文广告列表
		List<SbAdvertInfo> single_pic_text_Adverts = new ArrayList<SbAdvertInfo>();
		// 多图图文广告列表
		List<SbAdvertInfo> more_pic_text_Adverts = new ArrayList<SbAdvertInfo>();
		// 横幅广告列表
		List<SbAdvertInfo> banner_Adverts = new ArrayList<SbAdvertInfo>();
		// 漂浮广告列表
		List<SbAdvertInfo> float_Adverts = new ArrayList<SbAdvertInfo>();
		if(listNotEmpty(advList)){
			Integer showState = 0;
			Integer advType = null;
		   for (SbAdvertInfo adv : advList) {
			   
			    showState = adv.getShowState();
			    advType = adv.getAdType();
			    adv.setAdTagName(CPSUtil.getCodeName("ADV_TAG", adv.getAdTag()));
				if(advType.equals(AdType.ADTYPE_TEXT.getType()) && showState==1){
					text_Adverts.add(adv);
				}
				
				if(advType.equals(AdType.ADTYPE_ONLY_SINGLE_PIC.getType()) && showState==1){
					single_pic_Adverts.add(adv);
				}
				
				if(advType.equals(AdType.ADTYPE_SINGLE_PIC.getType()) && showState==1){
					single_pic_text_Adverts.add(adv);
				}
				
				if(advType.equals(AdType.ADTYPE_MORE_PIC.getType()) && showState==1){
					more_pic_text_Adverts.add(adv);
				}
				
				if(advType.equals(AdType.ADTYPE_BANNER_PIC.getType()) && showState==1){
					banner_Adverts.add(adv);
				}
				
				if(advType.equals(AdType.ADTYPE_FLOAT_PIC.getType()) && showState==1){
					float_Adverts.add(adv);
				}
			
		   }
		}
		
		reDto.put(AdType.ADTYPE_TEXT.getFlag(), text_Adverts);
		reDto.put(AdType.ADTYPE_ONLY_SINGLE_PIC.getFlag(), single_pic_Adverts);
		reDto.put(AdType.ADTYPE_SINGLE_PIC.getFlag(), single_pic_text_Adverts);
		reDto.put(AdType.ADTYPE_MORE_PIC.getFlag(), more_pic_text_Adverts);
		reDto.put(AdType.ADTYPE_BANNER_PIC.getFlag(), banner_Adverts);
		reDto.put(AdType.ADTYPE_FLOAT_PIC.getFlag(), float_Adverts);
		
		return reDto;
	}
	
	/**
	 * 随机获取集合中的num条广告 并从集合中移除
	 * @param adverts
	 * @return
	 */
	public static List<SbAdvertInfo> getRandomAdverts(List<SbAdvertInfo> adverts,int num){
		List<SbAdvertInfo> advs=new ArrayList<SbAdvertInfo>();
		for (int i = 0; i < num; i++) {
		if(CPSUtil.listNotEmpty(adverts)){
			int randomSinglePic = new Random().nextInt(adverts.size());
			SbAdvertInfo advertInfo = adverts.get(randomSinglePic);
			advs.add(advertInfo);
			adverts.remove(randomSinglePic);
		}
		}
		return advs;
	}
	
	
	/**
	 * 随机获取入口
	 * @param link
	 * @param num
	 * @return
	 */
	public static SbForwardLink getRandomForwardLink(){
		SbForwardLink _link = null;
		List<SbForwardLink> _linkList = (List<SbForwardLink>)CPSUtil.getContextAtrribute(Const.CT_FORWARD_LINK_LIST);
		if(CPSUtil.listNotEmpty(_linkList) && _linkList.size()>0){
			int randomLink = new Random().nextInt(_linkList.size());
			_link = _linkList.get(randomLink);
		}
		if(CPSUtil.isNotEmpty(_link)){
			CPSUtil.xprint("获取到的入口地址为："+_link.getLinkAddr());
		}
		return _link;
	}
	
	
	/**
	 * 将dto中的 纯文本、单图、单图文、多图文广告提取出来
	 * @param dto
	 * @return
	 */
	public static List<SbAdvertInfo> getAdverts(Dto dto){
		List<SbAdvertInfo> advertInfos=new ArrayList<SbAdvertInfo>();
		advertInfos.addAll(dto.getAsList(AdType.ADTYPE_TEXT.getFlag()));
		advertInfos.addAll(dto.getAsList(AdType.ADTYPE_SINGLE_PIC.getFlag()));
		advertInfos.addAll(dto.getAsList(AdType.ADTYPE_ONLY_SINGLE_PIC.getFlag()));
		advertInfos.addAll(dto.getAsList(AdType.ADTYPE_MORE_PIC.getFlag()));
		return advertInfos;
	}
	
	
	/**
	 * 提取文章信息
	 * @param contentId
	 * @return
	 */
	public static SbContentInfo getContentInfoById(Integer contentId) {
		SbContentInfo contentInfo = null;
		Dto contentDto = (BaseDto)CPSUtil.getContextAtrribute(Const.CT_CONTENT_INFO_LIST);
		if(CPSUtil.isNotEmpty(contentDto)){
			contentInfo = (SbContentInfo)contentDto.get(contentId);
		}
		return contentInfo;
	}
	
	
	/**
	 * 是否为主域名
	 * @param domain
	 * @return boolean  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-4
	 */
	public static boolean isHomeDomain(String domain) {
		boolean temp = false;
		String homeDomain = CPSUtil.getParamValue("HOME_INDEX_DOMAIN");
		if(CPSUtil.isNotEmpty(homeDomain)){
			CPSUtil.xprint("域名白名单=" + homeDomain);
			String domains [] = homeDomain.split(",");
			for (String dm : domains) {
				CPSUtil.xprint("domain=" + domain +"==="+dm);
				if(domain.contains(dm)){
					temp = true;
					break;
				}
			}
		}else{
			temp = true;
		}
		return temp;
	}
	public static void main(String[] args) {
		boolean f=isNumerByPrex("0.2");
		System.out.println(f);
	}
	
	public static void main2(String[] args) throws Exception {
		for (int i = 0; i < 2; i++) {
			String string = i+"-"+(i+2)+"-"+(i+4);
			String enString = getParamEncrypt(string);
			String deString = getParamDecrypt(enString);
			xprint("enString="+enString);
			xprint("deString="+deString);
		}
		
		xprint("\\upload\\image\\22323.jpg".replace("\\", "/"));
		
		xprint("md5="+MD5.md5("ad123654789"));
		
		xprint(CPSUtil.encryptBased64(CPSUtil.encryptBasedDes("DL205420215845111111221")));
		
		xprint(CPSUtil.decryptBasedDes(CPSUtil.decryptBased64("Mld0KzMyeVE0eDVyNWVOTFJteGN0RFNBK1hldUtkUG8=")));
		
		String msg = "你好吗同学们是不是有什么好消息";
		System.out.println(msg);
		msg = encryptOrderDes(msg);
		System.out.println(msg);
		System.out.println(msg.length());
		System.out.println(decryptOrderDes(msg));
		
		for (int i = 0; i < 50; i++) {
//			CPSUtil.xprint("'"+CipherUtil.createSalt()+"'");
		}
		System.out.println("bb:" + encryptOrderDes(""));
		System.out.println("aa:" + decryptOrderDes("xyUGlcc3GfA="));
		xprint("getContainPath="+decryptBasedDes("mdB7g2HWRiqhYHNhf3I53w=="));
		
		
		xprint("yesterday="+getDateByUDay(-1));
		xprint("code="+getRandomZhCode());
		xprint("date1afterDate2="+date1AfterDate2(getCurDate(), "2018-04-08"));
		xprint(isBetweenTime("15:30", "19:30"));
		xprint(getSubSecond("2018-04-08 15:30:35", "2018-04-08 15:30:36"));
		
		String msg2 = "(a+)你好吗同学们是不是有什么好消息!";
		for (int i = 0; i < msg2.length(); i++) {
			xprint("char["+i+"]="+ msg2.charAt(i));
		}
		
		
		CPSUtil.xprint(CPSUtil.getDateByDay(-7,"M月d日"));
		//CPSUtil.xprint("desc=="+decryptBasedDes(decryptBased64("aVZuaGkzLzlkOG1nSGMyUmozWW9iQU1lV0VLa3lQU0Q=")));
		
		CPSUtil.xprint("asdasdasd{tag}asdasd{emoji}sdfsdf".replaceAll("(\\{)([\\w]+)(\\})", ""));
		
	}
	
	
}
