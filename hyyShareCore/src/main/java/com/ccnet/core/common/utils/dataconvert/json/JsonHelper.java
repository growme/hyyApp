package com.ccnet.core.common.utils.dataconvert.json;

import com.ccnet.core.common.utils.StringHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json处理器<br>
 * 
 * @author wgp
 * @since 2013-01-15
 */
public class JsonHelper {

	private static final String DATE_FORMAT= "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject) {
		return encodeObject2Json(pObject, DATE_FORMAT);
	}

	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		Gson gson = new GsonBuilder().setDateFormat(pFormatString).create();  
		String jsonString = gson.toJson(pObject);
		StringHelper.printDebug("序列化后的JSON资料输出:\n" + jsonString);
		return jsonString;
	}

	 
	
	public static <T> T parseJson2Model(String json, Class<T> clazz) {
		return parseJson2Model(json, clazz, DATE_FORMAT);
	}
	
	public static <T> T parseJson2Model(String json, Class<T> clazz, String pFormatString) {
		Gson gson = new GsonBuilder().setDateFormat(pFormatString).create();  
		return gson.fromJson(json, clazz);
	}

}