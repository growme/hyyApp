package com.ccnet.core.common.utils.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.core.common.cache.ConfigCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.MyHttpClient;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.wxpay.MD5Util;

/**
 * 短信验证码处理类
 * @author jackie
 *
 */
public class SenderSMSUtil {
	
	
	/**
	 * 发送短信内容
	 * @param mobile
	 * @param msgText
	 * @return
	 */
	public static boolean sendSms(String userName,String passWord,String mobile,String msgText) {
		String reqUrl = ConfigCache.getConfig("SMS_API_URL");
		boolean temp = false;
		if(CPSUtil.isNotEmpty(mobile) && CPSUtil.isNotEmpty(msgText)){
			Map<String,String> params=new HashMap<>();
			params.put("account", userName);
			params.put("password", passWord);
			params.put("mobile", mobile);
			params.put("content", msgText);
			try {
				String SubmitResult =MyHttpClient.httpPostReq(reqUrl, params, "UTF-8");
				//String SubmitResult =method.getResponseBodyAsString();
				System.out.println(SubmitResult);
				Document doc = DocumentHelper.parseText(SubmitResult); 
				Element root = doc.getRootElement();
				String code = root.elementText("code");	
				String msg = root.elementText("msg");	
				String smsid = root.elementText("smsid");
				
				System.out.println("code="+code);
				System.out.println("msg="+msg);
				System.out.println("smsid="+smsid);
				
				if(code.equals("2")){
					temp = true;
				}else{
					temp = false;
				}
				
			} catch (DocumentException e) {
				e.printStackTrace();
			}	
		}
		return temp;
		
	}
	
	/**
	 * 发送语音短信内容
	 * @param mobile
	 * @param msgText
	 * @return
	 */
	public static boolean sendVoiceSms(String userName,String passWord,String mobile,String msgText) {
		String reqUrl = ConfigCache.getConfig("SMS_VOICE_API_URL");
		boolean temp = false;
		if(CPSUtil.isNotEmpty(mobile) && CPSUtil.isNotEmpty(msgText)){
			Map<String,String> params=new HashMap<>();
			params.put("account", userName);
			params.put("password", passWord);
			params.put("mobile", mobile);
			params.put("content", msgText);
			try {
				
				String SubmitResult =MyHttpClient.httpPostReq(reqUrl, params, "UTF-8");
				System.out.println(SubmitResult);
				Document doc = DocumentHelper.parseText(SubmitResult); 
				Element root = doc.getRootElement();
				String code = root.elementText("code");	
				String msg = root.elementText("msg");	
				String voiceid = root.elementText("voiceid");
				
				System.out.println("code="+code);
				System.out.println("msg="+msg);
				System.out.println("voiceid="+voiceid);
				
				if(code.equals("2")){
					temp = true;
				}else{
					temp = false;
				}
				
			} catch (DocumentException e) {
				e.printStackTrace();
			}	
		}
		return temp;
		
	}
	
	
	public static String getSign() {
		String sig = "";
		String timestamp=DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		String ACCOUNT_SID = "8a216da86bf97eac016bfa11e3ce00fc";//CPSUtil.getParamValue("CT_SMS_ACCOUNT");
		String ACCOUNT_TOKEN ="8d55da331ef94383a9408a8fd349a564";// CPSUtil.getParamValue("CT_SMS_PASSOWRD");
		sig = ACCOUNT_SID + ACCOUNT_TOKEN + timestamp;
		
		String signature=MD5Util.MD5Encode(sig,null);
		
		String reqUrl="https://app.cloopen.com:8883/2013-12-26/Accounts/{accountSid}/SMS/TemplateSMS?sig={SigParameter}";
		reqUrl=reqUrl.replace("{accountSid}", ACCOUNT_SID);
		reqUrl=reqUrl.replace("{SigParameter}", signature);
		
		Map<String,String> httHeader=new HashMap<>();
		httHeader.put("Accept", "application/json");
		httHeader.put("Content-Type", "application/json;charset=utf-8");
		String Authorization=Base64Utils.encodeToString((ACCOUNT_SID+":"+timestamp).getBytes());
		httHeader.put("Authorization",Authorization);
		
		Map<String,Object> params=new HashMap<>();
		params.put("to", "18510071586");
		params.put("appId", "8a216da86ccd1699016cd135c817006a");
		params.put("templateId", "470496");
		
		List<String> dlist=new ArrayList<>();
		dlist.add("3214");
		params.put("datas",dlist);
		String jsonStr=JSON.toJSONString(params);
		
		String body=MyHttpClient.reqMsgReq(reqUrl, jsonStr, httHeader);
		System.out.println(body);
		
		
		return "";
	}
	
	
    /**
              * 获取验证码
     * @param mobile 手机号码
     * @param flag 0 短信 1 语音
     * @return
     */
	public static Dto getSmsCode(String mobile,int flag) {
		Dto reslutDto = new BaseDto();
		String userName = CPSUtil.getParamValue("CT_SMS_ACCOUNT");
		String passWord = CPSUtil.getParamValue("CT_SMS_PASSOWRD");
		if(flag==1){
			userName = CPSUtil.getParamValue("CT_VSMS_ACCOUNT");
			passWord = CPSUtil.getParamValue("CT_VSMS_PASSOWRD");
		}
		
		CPSUtil.xprint("验证码模式="+(flag==1?"语音验证码":"短信验证码"));
		CPSUtil.xprint("短信账号="+userName);
		CPSUtil.xprint("短信密码="+passWord);
		
		if(StringHelper.checkParameter(userName,passWord)){
			reslutDto = getSmsCode(userName,passWord,mobile,flag);
		}
		return reslutDto;
	}
	
	//发送验证码
	public static Dto getSmsCode(String userName,String passWord,String mobile,int flag) {
		String reqUrl = ConfigCache.getConfig("SMS_API_URL");
		if(flag==1){
			reqUrl = ConfigCache.getConfig("SMS_VOICE_API_URL");
		}
		String appId=ConfigCache.getConfig("SMS_APP_ID");
		String smsTemplateId=ConfigCache.getConfig("SMS_TEMPLATE_ID");
		Dto reDto = new BaseDto();
		int mobile_code = (int)((Math.random()*9+1)*100000);
//	    String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。"); 
//	    if(flag==1){
//	    	content = mobile_code+"";
//	    }
	    
	    String sig = "";
		String timestamp=DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
		String ACCOUNT_SID =userName;//CPSUtil.getParamValue("CT_SMS_ACCOUNT");
		String ACCOUNT_TOKEN =passWord;// CPSUtil.getParamValue("CT_SMS_PASSOWRD");
		sig = ACCOUNT_SID + ACCOUNT_TOKEN + timestamp;
		String signature=MD5Util.MD5Encode(sig,null);
		//String reqUrl="https://app.cloopen.com:8883/2013-12-26/Accounts/{accountSid}/SMS/TemplateSMS?sig={SigParameter}";
		reqUrl=reqUrl.replace("{accountSid}", ACCOUNT_SID);
		reqUrl=reqUrl.replace("{SigParameter}", signature);
		Map<String,String> httHeader=new HashMap<>();
		httHeader.put("Accept", "application/json");
		httHeader.put("Content-Type", "application/json;charset=utf-8");
		String Authorization=Base64Utils.encodeToString((ACCOUNT_SID+":"+timestamp).getBytes());
		httHeader.put("Authorization",Authorization);
	    
		Map<String,Object> params=new HashMap<>();
		params.put("to", mobile);
		params.put("appId", appId);
		params.put("templateId", smsTemplateId);
		
		List<String> dlist=new ArrayList<>();
		dlist.add(mobile_code+"");
		params.put("datas",dlist);
		String jsonStr=JSON.toJSONString(params);
		String body=MyHttpClient.reqMsgReq(reqUrl, jsonStr, httHeader);//code==2成功
		if(StringUtils.isBlank(body)) {
			reDto.put("msg", "发送失败");
			reDto.put("rcode", "0");
			reDto.put("vcode", mobile_code);
			return reDto;
		}
		
		try {
			JSONObject jobj = JSON.parseObject(body);
			if (null == jobj) {
				reDto.put("msg", "发送失败");
				reDto.put("rcode", "0");
				reDto.put("vcode", mobile_code);
				return reDto;
			}
			
			if(!"000000".equals(jobj.getString("statusCode"))) {
				reDto.put("msg", "发送失败");
				reDto.put("rcode", "0");
				reDto.put("vcode", mobile_code);
				return reDto;
			}
			
			reDto.put("msg", "验证码发送成功");
			reDto.put("rcode", "2");
			reDto.put("vcode", mobile_code);
			return reDto;
			
			
		} catch (Exception e) {
			reDto.put("msg", "发送失败");
			reDto.put("rcode", "0");
			reDto.put("vcode", mobile_code);
			return reDto;
		}
		
	}
	
	
	/**
	 * 获取用户回复内容
	 * @return
	 */
	public static String getUserReplySms(String userName,String passWord) {
    	String reqRPUrl = ConfigCache.getConfig("SMS_REPLY_API_URL");
		String resultString = null;
		Map<String,String> params=new HashMap<>();
		params.put("account", userName);
		params.put("password", passWord);
		
		try {
			
			String SubmitResult =MyHttpClient.httpPostReq(reqRPUrl, params, "UTF-8");
			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();
			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			if(CPSUtil.isNotEmpty(msg)){
				resultString = msg;
			}
			System.out.println("code="+code);
			System.out.println("msg="+msg);
						
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		return resultString;
	}
	
	public static void main(String[] args) {
		//getUSMSReplyText();
		getSign();
	}

}
