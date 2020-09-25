package com.ccnet.admin.bh.utils.wxUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

/**
 * @Auther: liuyubo
 * @Date: 2018/8/10 20:46
 * @Description: 微信提现 xml数据 签名等
 */
public class WeixinpaymoneyUtil {

	private static final Logger LOG = Logger.getLogger(WeixinpaymoneyUtil.class);

	public static String createDocumentForEnterprisesPayment(EnterprisesPayment enterprisesPayment) {
		final StringBuffer result = new StringBuffer();
		result.append("<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><xml>");
		result.append("<mch_appid>" + enterprisesPayment.getMch_appid() + "</mch_appid>");
		result.append("<mchid>" + enterprisesPayment.getMchid() + "</mchid>");
		result.append("<nonce_str>" + enterprisesPayment.getNonce_str() + "</nonce_str>");
		result.append("<partner_trade_no>" + enterprisesPayment.getPartner_trade_no() + "</partner_trade_no>");
		result.append("<openid>" + enterprisesPayment.getOpenid() + "</openid>");
		result.append("<check_name>" + enterprisesPayment.getCheck_name() + "</check_name>");
		result.append("<re_user_name>" + enterprisesPayment.getRe_user_name() + "</re_user_name>");
		result.append("<amount>" + enterprisesPayment.getAmount() + "</amount>");
		result.append("<desc>" + enterprisesPayment.getDesc() + "</desc>");
		result.append("<spbill_create_ip>" + enterprisesPayment.getSpbill_create_ip() + "</spbill_create_ip>");
		result.append("<sign>" + enterprisesPayment.getSign() + "</sign>");
		result.append("</xml>");
		return result.toString();
	}

	public static String getSignCode(Map<String, String> map, String keyValue) {
		String result = "";
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				@Override
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});

			// 构造签名键值对的格式
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds) {
				if (item.getKey() != null || item.getKey() != "") {
					String key = item.getKey();
					String val = item.getValue();
					if (!(val == "" || val == null)) {
						sb.append(key + "=" + val + "&");
					}
				}

			}
			sb.append("key=" + keyValue);
			result = sb.toString();

			// 进行MD5加密
			result = WeixinpaymoneyUtil.md5(result).toUpperCase();
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * 生成32位编码
	 * 
	 * @return string
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 生成提现订单号
	 * 
	 * @return string
	 */
	public static String getOrderId() {
		int machineId = 1;// 最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		return machineId + String.format("%015d", hashCodeV);
	}

	/**
	 * md5加密
	 * 
	 * @param text
	 * @return
	 */
	public static String md5(String str) {
		String encodeStr=DigestUtils.md5Hex(str).toUpperCase();;
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
	}
}
