package com.ccnet.core.common.utils.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ccnet.core.common.utils.CPSUtil;
 
public class MobileLocationUtil {
    /**
     * 归属地查询
     * @param mobile
     * @return mobileAddress
     */
    @SuppressWarnings("unused")
    private static String getLocationByMobile(final String mobile) throws ParserConfigurationException, SAXException, IOException{ 
        String MOBILEURL = " http://www.youdao.com/smartresult-xml/search.s?type=mobile&q="; 
        String result = callUrlByGet(MOBILEURL + mobile, "GBK");
        StringReader stringReader = new StringReader(result); 
        InputSource inputSource = new InputSource(stringReader); 
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
        Document document = documentBuilder.parse(inputSource);
 
        if (!(document.getElementsByTagName("location").item(0) == null)) {
            return document.getElementsByTagName("location").item(0).getFirstChild().getNodeValue();
        }else{
            return "无此号记录！";
        }
    }
    /**
     * 获取URL返回的字符串
     * @param callurl
     * @param charset
     * @return
     */
    private static String callUrlByGet(String callurl,String charset){   
        String result = "";   
        try {   
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
     * 手机号码归属地
     * @param tel 手机号码
     * @return
     */
    public static String getMobileLocation(String tel) {
    	String ret = "";
    	try {
    		 Pattern pattern = Pattern.compile("1\\d{10}");
    	        Matcher matcher = pattern.matcher(tel);
    	        if(matcher.matches()){
    	        	 String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + tel;
    	             String result = callUrlByGet(url,"GBK");
    	             if(CPSUtil.isNotEmpty(result)){
    	            	 result = result.substring(result.indexOf("=")+1,result.length());
    	            	 //CPSUtil.xprint("归属地："+result);
    	            	 if(CPSUtil.isNotEmpty(result) && result.contains("carrier")){
    	            	    JSONObject jsStr = new JSONObject(result);
    	            	    ret = jsStr.getString("carrier");
    	            	 }else{
    	            		ret = "归属地异常";
    	            	 }
    	             }
    	        }
    	        
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return ret;
    	
	}
    
    
    /**
     * 手机号码归属地
     * @param tel  手机号码
     * @return 135XXXXXXXX,联通/移动/电信,湖北武汉
     * @throws Exception
     * @author 
     */
    public static String getMobileLocation1(String tel) throws Exception{
    	String ret = null;
    	try {
        Pattern pattern = Pattern.compile("1\\d{10}");
        Matcher matcher = pattern.matcher(tel);
        if(matcher.matches()){
            String url = "http://life.tenpay.com/cgi-bin/mobile/MobileQueryAttribution.cgi?chgmobile=" + tel;
            String result = callUrlByGet(url,"GBK");
            StringReader stringReader = new StringReader(result); 
            InputSource inputSource = new InputSource(stringReader); 
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance(); 
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder(); 
            Document document = documentBuilder.parse(inputSource);
            String retmsg = document.getElementsByTagName("retmsg").item(0).getFirstChild().getNodeValue();
            if(retmsg.equals("OK")){
                String supplier = document.getElementsByTagName("supplier").item(0).getFirstChild().getNodeValue().trim();
                String province = document.getElementsByTagName("province").item(0).getFirstChild().getNodeValue().trim();
                String city = document.getElementsByTagName("city").item(0).getFirstChild().getNodeValue().trim();
                if (province.equals("-") || city.equals("-")) {
                	ret = (getLocationByMobile(tel) + "," + supplier);
                }else {
                	ret = (province + city + "," + supplier );
                }

            }else {
            	ret = "无此号记录！";
            }

        }else{
        	ret = tel+ "：手机号码格式错误！";
        }
        
    	} catch (Exception e) {
    		ret = "服务器异常";
			e.printStackTrace();
		}
    	return ret;
    }
    
    
    public static void main(String[] args) {
        try {
            System.out.println(MobileLocationUtil.getMobileLocation("13787047370"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}