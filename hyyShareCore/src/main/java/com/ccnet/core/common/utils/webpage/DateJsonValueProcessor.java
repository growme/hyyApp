package com.ccnet.core.common.utils.webpage;

import java.sql.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * JSON-LIB 补丁，修复Date转换错误，应用其开放接口进行修复
 * @author jackie wang
 * @version 1.0
 */
public class DateJsonValueProcessor implements JsonValueProcessor {

	/**
	 * 处理数组的值
	 * @param value 对象值
	 * @param jsonConfig JSON配置对象
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return this.process(value);
	}

	/**
	 * 处理对象的值
	 * @param key 对象key值
	 * @param value 对象value值
	 */
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return this.process(value);
	}

	/**
	 * 处理Date类型返回的Json数值
	 * @param value 需要处理的日期对象
	 * @return 处理后的日期对象
	 */
	private Object process(Object value) {
		if (value == null) {
			return "";
		} else if (value instanceof Date) {
			return JSONObject.fromObject(new java.util.Date(((Date) value).getTime()));
		} else {
			return value.toString();
		}
	}
}