package com.ccnet.core.common.utils.wxpay;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.jdom.JDOMException;

import com.ccnet.core.common.utils.CPSUtil;
public class WXJSPay {

	public static Map jsApiPay(String openid, String total_fee,String out_trade_num,String order_id) throws JDOMException, IOException {	
		String noceStr=Sha1Util.getNonceStr();//随机字符串
		String timeStamp=Sha1Util.getTimeStamp();//时间戳
		// 接口package部分-内容----------------------------------------------------------
		TreeMap<String, String> contentMap = new TreeMap<String, String>();
		contentMap.put("appid", ConfigUtils.APPID); // 公众账号 ID
		contentMap.put("mch_id", ConfigUtils.MCHI_ID); // 商户号
		contentMap.put("nonce_str", noceStr); // 随机字符串		
		contentMap.put("body" ,order_id); // 商品描述
		contentMap.put("out_trade_no",out_trade_num); // 商户订单号
		contentMap.put("total_fee",String.valueOf((Integer.parseInt(total_fee)*100))); // 订单总金额
		//contentMap.put("total_fee","1"); // 订单总金额
		contentMap.put("spbill_create_ip",ConfigUtils.SPBILL_CREATE_IP); // 订单生成的机器IP
		contentMap.put("notify_url",ConfigUtils.NOTIFY_URL); // 通知地址
		contentMap.put("trade_type",ConfigUtils.TRADE_TYPE_JS); // 交易类型
		contentMap.put("openid",openid); // 用户标识
		String wxpackage = WeiXinSignAndPackage.createPackage(contentMap);
		contentMap.put("sign", wxpackage);
		
		String result=WeiXinSignAndPackage.getPrepayId(contentMap);//调用统一接口返回的值
		Map<String,String> map=XMLUtil.doXMLParse(result);//调用统一接口返回的值转换为XML格式
		System.out.println(map);
		TreeMap<String, String> wxPayParamMap = new TreeMap<String, String>();
		wxPayParamMap.put("appId", ConfigUtils.APPID);
		wxPayParamMap.put("timeStamp",timeStamp );		
		wxPayParamMap.put("nonceStr",noceStr);
		wxPayParamMap.put("package", "prepay_id="+map.get("prepay_id"));
		wxPayParamMap.put("signType", ConfigUtils.SIGNTYPE);
		String paySign=WeiXinSignAndPackage.createPaySign(wxPayParamMap);//支付得到的签名
		wxPayParamMap.put("paySign", paySign);	
		wxPayParamMap.put("payMoney", total_fee);//到前段显示使用，支付不需要此参数
		
		CPSUtil.xprint("wxPayParamMap===>>"+wxPayParamMap);
		
		return wxPayParamMap;
	}

}
