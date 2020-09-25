package com.ccnet.admin.bh.utils.wxUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;

public class WeiXinPayUtils {

	private static SortedMap<String, String> sortedMap = new TreeMap<>();
	private final static String WXAppID = GetPropertiesValue.getValue("Config.properties", "WXAppID");
	private final static String WXMchID = GetPropertiesValue.getValue("Config.properties", "WXMchID");
	private final static String WXpaternerKey = GetPropertiesValue.getValue("Config.properties", "WXpaternerKey");

	public static Map<String, Object> withdrawals(String nickName, String openid, String ip, String amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		BigDecimal amountbd = new BigDecimal(amount);
		Integer inte = amountbd.multiply(new BigDecimal("100")).intValue();
		String sginCode = getSgin(openid, inte, nickName, ip);// 获取用户openid 和
		// 用户要提现的金额 拿到签名
		EnterprisesPayment enterprisesPayment = addEnterprisesPayment(openid, inte, sginCode, nickName, ip);// 拿到签名后加密得到要发送到对象数据
		System.out.println(enterprisesPayment.getPartner_trade_no().length());
		String enterprisesPaymentXML = WeixinpaymoneyUtil.createDocumentForEnterprisesPayment(enterprisesPayment); // 封装xml
		// 数据发送
		try {
			String returnXmlData = ClientCustomSSL.doRefund(
					"https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", enterprisesPaymentXML);
			String str = returnXmlData.substring(returnXmlData.indexOf("<result_code>"),
					returnXmlData.indexOf("</result_code>"));
			System.out.println(returnXmlData);
			if (str.indexOf("SUCCESS") <= 0) {
				String mes = returnXmlData.substring(returnXmlData.indexOf("<err_code_des>") + 14,
						returnXmlData.indexOf("</err_code_des>"));
				map.put("code", "0");
				map.put("value", mes);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "0");
			map.put("value", "逻辑错误！");
			return map;
		}
		map.put("partner_trade_no", enterprisesPayment.getPartner_trade_no());
		map.put("code", "1");
		map.put("value", "成功");
		return map;
	}

	public static String getSgin(String openid, Integer amount, String nickName, String ip) {
		sortedMap.put("mch_appid", WXAppID);
		sortedMap.put("mchid", WXMchID);
		sortedMap.put("nonce_str", WeixinpaymoneyUtil.getUUID());
		sortedMap.put("partner_trade_no", WeixinpaymoneyUtil.getOrderId());
		sortedMap.put("openid", openid);
		sortedMap.put("check_name", "NO_CHECK");
		sortedMap.put("amount", amount.toString());
		sortedMap.put("desc", "账户提现");
		sortedMap.put("spbill_create_ip", ip);
		sortedMap.put("re_user_name", nickName);
		return WeixinpaymoneyUtil.getSignCode(sortedMap, WXpaternerKey);
	}

	public static EnterprisesPayment addEnterprisesPayment(String openid, Integer amount, String sginCode,
			String nickName, String ip) {
		EnterprisesPayment enterprisesPayment = new EnterprisesPayment();
		enterprisesPayment.setMch_appid(WXAppID);// 商户号appid
		enterprisesPayment.setMchid(WXMchID);// 商户号
		enterprisesPayment.setNonce_str(sortedMap.get("nonce_str"));// 随机字符串
		enterprisesPayment.setPartner_trade_no(sortedMap.get("partner_trade_no"));// 商户订单号
		enterprisesPayment.setOpenid(openid);
		enterprisesPayment.setCheck_name("NO_CHECK");
		enterprisesPayment.setAmount(amount);// 金额
		enterprisesPayment.setDesc("账户提现");// 描述
		enterprisesPayment.setSpbill_create_ip(ip);// ip地址
		enterprisesPayment.setRe_user_name(nickName); // 根据checkName字段判断是否需要
		enterprisesPayment.setSign(sginCode);// 签名
		return enterprisesPayment;
	}
}
