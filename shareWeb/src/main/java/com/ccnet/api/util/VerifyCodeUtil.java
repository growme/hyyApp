package com.ccnet.api.util;

import java.util.Random;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;

public class VerifyCodeUtil {

	public static String getValidVerifyCode(String phone) {
		// 设置超时时间-可自行调整
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		final String accessKeyId = GetPropertiesValue.getValue("Config.properties", "accessKeyId");// 你的accessKeyId
		final String accessKeySecret = GetPropertiesValue.getValue("Config.properties", "accessKeySecret");// 你的accessKeySecret
		// 初始化ascClient,暂时不支持多region（请勿修改）
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			e1.printStackTrace();
		}
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest requests = new SendSmsRequest();
		// 使用post提交
		requests.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
		requests.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		requests.setSignName(GetPropertiesValue.getValue("Config.properties", "signName"));
		String msgCode = getMsgCode();
		// 必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
		requests.setTemplateCode(GetPropertiesValue.getValue("Config.properties", "templateCode"));
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		// request.setTemplateParam("{\"code\":\"988756\"}");

		requests.setTemplateParam("{\"code\":\"" + msgCode + "\"}");
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = new SendSmsResponse();
		try {
			sendSmsResponse = acsClient.getAcsResponse(requests);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			return msgCode;
		} else {
			return "0";
		}
	}

	/**
	 * 生成随机的6位数，短信验证码
	 * 
	 * @return
	 */
	private static String getMsgCode() {
		int n = 6;
		StringBuilder code = new StringBuilder();
		Random ran = new Random();
		for (int i = 0; i < n; i++) {
			code.append(Integer.valueOf(ran.nextInt(10)).toString());
		}
		return code.toString();
	}

}