package com.ccnet.api.util;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付服务类 Created by Administrator on 2016/10/31 0031.
 */
public class WxNativeService {
	private static Logger MAIN_LOGGER = LoggerFactory.getLogger(WxNativeService.class);

	/**
	 * 提交订单
	 *
	 * @param body
	 * @return
	 */
	public static Map<String, String> post(String ip, String body, String billId, BigDecimal totaAmout, String domain,
			String callbackUrl) throws Exception {
		try {
			Map map = new HashMap();
			String nonceStr = WXUtil.getNonceStr();
			map.put("appid", TenpayConstant.APP_ID);// wx7ae97804c43e7c82
			map.put("mch_id", TenpayConstant.APP_SECRET);
			map.put("body", body);
			map.put("nonce_str", nonceStr);

			map.put("out_trade_no", billId);
			String totalmoney_str = new java.text.DecimalFormat("#.00")
					.format((totaAmout.multiply(new BigDecimal(100))));
			totalmoney_str = moneySlipt(totalmoney_str);

			map.put("total_fee", totalmoney_str);
			map.put("spbill_create_ip", ip);
			map.put("notify_url", String.format("%s/%s", domain, callbackUrl));
			map.put("trade_type", "NATIVE");
			String paySign = WXUtil.getPayCustomSign(map, TenpayConstant.PAY_KEY);
			map.put("sign", paySign);
			// map转XML
			String xml = WXUtil.ArrayToXml(map);

			MAIN_LOGGER.info("提交微信订单:{}", xml);
			//
			String prepayid = WXUtil.getCodeUrl(WXUtil._URL, xml);
			MAIN_LOGGER.info("提交微信预支付订单ID", prepayid);
			if (StringUtils.isEmpty(prepayid)) {
				throw new Exception("生成预支付订单失败");
			}
			return param(prepayid);
		} catch (Exception e) {
			MAIN_LOGGER.error("提交微信订单失败", e);
			throw new Exception();
		}
	}

	public static String moneySlipt(String string) {
		if (StringUtils.isNotEmpty(string) && string.indexOf(".") > -1) {
			String str = string.substring(0, string.indexOf("."));
			return str;
		}
		return string;
	}

	/**
	 * 订单生成预支付ID后返回app下一步参数
	 *
	 * @param prepayid
	 * @return
	 */
	public static Map<String, String> param(String prepayid) throws Exception {

		MAIN_LOGGER.info("prepayid:{}", prepayid);
		try {
			MAIN_LOGGER.info("开始生成微信参数");
			String nonceStr = WXUtil.getNonceStr();
			String timestamp = WXUtil.getTimeStamp();
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", TenpayConstant.APP_ID);
			paramMap.put("partnerid", TenpayConstant.APP_SECRET);
			paramMap.put("prepayid", prepayid);
			paramMap.put("package", "Sign=WXPay");
			paramMap.put("noncestr", nonceStr);
			paramMap.put("timestamp", timestamp);
			String paySign = WXUtil.getPayCustomSign(paramMap, TenpayConstant.PAY_KEY);
			MAIN_LOGGER.info("签名:{}", paySign);
			paramMap.put("sign", paySign);

			return paramMap;
		} catch (Exception e) {
			e.printStackTrace();
			MAIN_LOGGER.error("组装参数失败", e);
			throw new Exception();
		}
	}

	public static boolean validate(Map<String, String> result) {

		if (result.containsKey("return_code") && "SUCCESS".equals(result.get("return_code"))) {

			String sign = result.get("sign");
			result.remove("sign");
			String signK = "";
			try {
				signK = WXUtil.getPayCustomSign(result, TenpayConstant.PAY_KEY);
			} catch (Exception e) {
				e.printStackTrace();
			}
			MAIN_LOGGER.info("计算的sign:{},获取的sign:{}", signK, sign);
			return signK.equals(sign);

		} else {
			return false;
		}
	}

	public static boolean wxPublicValidate(Map<String, String> result) {

		if (result.containsKey("return_code") && "SUCCESS".equals(result.get("return_code"))) {

			String sign = result.get("sign");
			result.remove("sign");
			String signK = "";
			try {
				signK = WXUtil.getPayCustomSign(result, TenpayConstant.PAY_KEY_MARKET);
			} catch (Exception e) {
				e.printStackTrace();
			}
			MAIN_LOGGER.info("计算的sign:{},获取的sign:{}", signK, sign);
			return signK.equals(sign);

		} else {
			return false;
		}
	}

	public static boolean validateByKM(Map<String, String> result) {

		if (result.containsKey("return_code") && "SUCCESS".equals(result.get("return_code"))) {

			String sign = result.get("sign");
			result.remove("sign");
			String signK = "";
			try {
				signK = WXUtil.getPayCustomSign(result, TenpayConstant.PAY_KEY_MARKET);
			} catch (Exception e) {
				e.printStackTrace();
			}
			MAIN_LOGGER.info("计算的sign:{},获取的sign:{}", signK, sign);
			return signK.equals(sign);

		} else {
			return false;
		}
	}

	/**
	 * 微信退款申请
	 *
	 * @param outTradeNo
	 *            支付ID
	 * @param refundNo
	 *            退款id
	 * @param totalAmount
	 *            支付总金额
	 * @param refundFee
	 *            退款金额
	 * @return
	 * @throws Exception
	 */
	public static void refund(String outTradeNo, String refundNo, BigDecimal totalAmount, BigDecimal refundFee) {
		try {
			Map map = new HashMap();
			String nonceStr = WXUtil.getNonceStr();
			map.put("appid", TenpayConstant.APP_ID);
			map.put("mch_id", TenpayConstant.APP_SECRET);
			map.put("nonce_str", nonceStr);
			// 退款先用自己订单退款号
			// map.put("transaction_id","");
			map.put("out_trade_no", outTradeNo);
			map.put("out_refund_no", refundNo);

			String totalmoney_str = new java.text.DecimalFormat("#.00")
					.format((totalAmount.multiply(new BigDecimal(100))));
			totalmoney_str = moneySlipt(totalmoney_str);

			String refundFee_str = new java.text.DecimalFormat("#.00")
					.format((refundFee.multiply(new BigDecimal(100))));
			refundFee_str = moneySlipt(refundFee_str);

			map.put("total_fee", totalmoney_str);
			map.put("refund_fee", refundFee_str);
			map.put("op_user_id", TenpayConstant.APP_SECRET);

			String paySign = WXUtil.getPayCustomSign(map, TenpayConstant.PAY_KEY);
			map.put("sign", paySign);
			// map转XML
			String xml = WXUtil.ArrayToXml(map);
			MAIN_LOGGER.info("申请退款请求xml:{}", xml);
			JSONObject resultJson = WXUtil.httpsRequest(WXUtil.REFUNDURL, xml);
			MAIN_LOGGER.info("申请退款响应:{}", resultJson);
			JSONObject xmlJSON = resultJson.getJSONObject("xml");
			if (xmlJSON.containsKey("return_code") && xmlJSON.getJSONArray("return_code").get(0).equals("SUCCESS")) {
				if (xmlJSON.containsKey("result_code") && xmlJSON.getJSONArray("result_code").get(0).equals("FAIL")) {
					StringBuilder strb = new StringBuilder();
					if (xmlJSON.containsKey("err_code")) {
						Object code = xmlJSON.getJSONArray("err_code").get(0);
						strb.append("code:").append(code).append(" ");
					}
					if (xmlJSON.containsKey("err_code_des")) {
						Object msg = xmlJSON.getJSONArray("err_code_des").get(0);
						strb.append("msg:").append(msg);
					}
					if (strb.length() == 0) {
						strb.append("提交微信退款失败");
					}
				}
			} else {
				StringBuilder strb = new StringBuilder("提交微信退款失败");
				if (xmlJSON.containsKey("return_code") && xmlJSON.getJSONArray("return_code").get(0).equals("FAIL")) {
					strb.setLength(0);
					strb.append(xmlJSON.getJSONArray("return_msg").get(0));
				}
			}
		} catch (Exception e) {
			MAIN_LOGGER.error("提交申请微信退款失败", e);
		}
	}

	public static void main(String[] a) {
		try {
			// String domain = "http://dev.hnylbsc.com/api";// 测试环境
			// Map<String, String> param = post("175.9.138.92", "优良宝充值支付",
			// "31173202946293726", new BigDecimal(0.01), domain,
			// "");
			// System.out.println(JSONObject.toJSONString(param));

			String d = "appid=wx83146b0d5cebaa1d&attach=支付测试&bank_type=CFT&fee_type=CNY&is_subscribe=Y&mch_id=1498649462&nonce_str=5d2b6c2a8db53831f7eda20af46e531c&openid=oUpF8uMEb4qRXf22hE3X68TekukE&out_trade_no=1409823116531&result_code=SUCCESS&return_code=SUCCESS&sub_mch_id=10000100&time_end=20140903131540&total_fee=1&trade_type=JSAPI&transaction_id=1004400740201409030005092168&key=09c4d4950db0db14f90f6b0dc86977c5";
			String z = WXUtil.MD5(d, TenpayConstant.INPUT_CHARSET);
			System.out.println(z);

			// Sys

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 订单生成预支付ID后返回app下一步参数
	 *
	 * @param prepayid
	 * @return
	 */
	public static Map<String, String> paramByKM(String prepayid) throws Exception {

		MAIN_LOGGER.info("prepayid:{}", prepayid);
		try {
			MAIN_LOGGER.info("开始生成微信参数");
			String nonceStr = WXUtil.getNonceStr();
			String timestamp = WXUtil.getTimeStamp();
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("appid", TenpayConstant.APP_ID_MARKET);
			paramMap.put("partnerid", TenpayConstant.APP_SECRET_MARKET);
			paramMap.put("prepayid", prepayid);
			paramMap.put("package", "Sign=WXPay");
			paramMap.put("noncestr", nonceStr);
			paramMap.put("timestamp", timestamp);
			String paySign = WXUtil.getPayCustomSign(paramMap, TenpayConstant.PAY_KEY_MARKET);
			MAIN_LOGGER.info("签名:{}", paySign);
			paramMap.put("sign", paySign);
			return paramMap;
		} catch (Exception e) {
			e.printStackTrace();
			MAIN_LOGGER.error("组装参数失败", e);
			throw new Exception();
		}
	}

}
