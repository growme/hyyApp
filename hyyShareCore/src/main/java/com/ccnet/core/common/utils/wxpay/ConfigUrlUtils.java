package com.ccnet.core.common.utils.wxpay;

public class ConfigUrlUtils {
	private static final String configUrl="ConfigURL.properties";
	
	public static final String COMMON_PAY_INTERFACE=GetPropertiesValue.getValue(configUrl, "common_pay_interface");//统一支付接口
	public static final String DOMAIN=GetPropertiesValue.getValue(configUrl, "domain");//项目域名
}
