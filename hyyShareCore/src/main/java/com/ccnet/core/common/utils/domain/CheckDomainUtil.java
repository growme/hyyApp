package com.ccnet.core.common.utils.domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSONObject;

import com.ccnet.core.common.DomainStateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.HttpUtil;

public class CheckDomainUtil {
    //检测地址
    public static String VIP_CHEKC_SERVER_PATH2="http://vip.weixin139.com/weixin/wx_domain.php";
    public static String VIP_CHEKC_SERVER_PATH="http://api.monkeyapi.com";
	
	/**
     * 获取URL返回的字符串
     * @param callurl
     * @param charset
     * @return
     */
    private static String callUrlByGet(String callurl,String charset){   
        String result = "";   
        try {   
        	CPSUtil.xprint("url="+callurl);
            URL url = new URL(callurl);   
            URLConnection connection = url.openConnection();   
            connection.connect();   
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));   
            String line;   
            while((line = reader.readLine())!= null){    
                result += line;   
                result += "\n";
            }
        } catch (Exception e) {   
            e.printStackTrace();   
            return "";
        }
        return result;
    }
    
    
    
    
    /**
     * 域名检测
     * @param key
     * @param domainUrl
     * @return
     */
    public static String domainIsEnabledVip(String key,String domainUrl){
    	String code = null;
    	String msg = null;
    	String resultString = "";
    	try {
    		if(CPSUtil.isNotEmpty(key) && CPSUtil.isNotEmpty(domainUrl)){
    			if(StringUtils.contains(domainUrl, "&")){
    				resultString = HttpUtil.post(VIP_CHEKC_SERVER_PATH, "type=1&appkey="+key+"&url="+URLEncoder.encode(domainUrl,"UTF-8"), false);
    			}else{
    				resultString = HttpUtil.post(VIP_CHEKC_SERVER_PATH, "appkey="+key+"&url="+URLEncoder.encode(domainUrl,"UTF-8"), false); 
    			}
    			JSONObject jsonObject = JSONObject.fromObject(resultString);
    			String status = jsonObject.optString("status");
    			if(CPSUtil.isNotEmpty(status) && "ok".equals(status)){
    				msg =  jsonObject.optString("msg");
    				code =  jsonObject.optString("code");
    				if("0".equals(code)){//域名正常
    					resultString = String.valueOf(DomainStateType.Valid.getState());
    				}
    				
    				if("1".equals(code)){//检测错误
    					resultString = String.valueOf(DomainStateType.ApiError.getState());
    				}
    				
    				if("2".equals(code)){//已被封杀
    					resultString = String.valueOf(DomainStateType.InValid.getState());
    				}
    				
    				if("3".equals(code)){//频率过高
    					resultString = String.valueOf(DomainStateType.CheckFast.getState());
    				}
    			}else{
    				if("bad".equals(status)){//接口到期
    					msg = DomainStateType.ApiExpire.getDesc();
    					resultString = String.valueOf(DomainStateType.ApiExpire.getState());
    				}else{
    					msg = DomainStateType.ApiError.getDesc();
    					resultString = String.valueOf(DomainStateType.ApiError.getState());//接口异常
    				}
    			}
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	CPSUtil.xprint("域名["+domainUrl+"]===》"+msg+"["+resultString+"]");
    	return resultString;
    }
    
    public static void main(String[] args) {
		String urlString [] = {"lsy0.cn","a.lsy4.cn","lsy5.cn","hy7t.cn","http://22l6i4.cn/df.php?from=groupmessage&isappinstalled=0"};
		for (int i = 0; i < 1; i++) {
			for (String dm : urlString) {
				domainIsEnabledVip("7D27B3D52E02E9E806E26DE755B59998", dm);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		double randomName = Math.floor(Math.random() * 308 + 1);
		CPSUtil.xprint("randomName==="+randomName);
	}

}
