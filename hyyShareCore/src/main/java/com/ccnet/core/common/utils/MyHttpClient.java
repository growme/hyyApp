package com.ccnet.core.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHttpClient {

	
	private static final String URL_PARAM_CONNECT_FLAG = "&";
	//private static final int SIZE = 1024 * 1024;
	public static Logger log = LoggerFactory.getLogger(MyHttpClient.class);

	public static CloseableHttpClient getHttpclient() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient;
	}
	
	
  private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
    	
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				
				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws java.security.cert.CertificateException {
					
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx, new String[] { "TLSv1" }, null,new NoopHostnameVerifier());
			return sslsf;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
    	
    	
    }  
	
	
	public static String httpPostReq(String url,Map<String,String> params,String encode){  
    	if(encode == null){  
            encode = "utf-8";  
        }  
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        if(url.startsWith("https:")){
        	SSLConnectionSocketFactory sslsf=createSSLConnSocketFactory();
        	closeableHttpClient = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
        }
        HttpPost httpost = new HttpPost(url);  
        //组织请求参数  
        List<NameValuePair> paramList = new ArrayList <NameValuePair>();  
        if(params != null && params.size() > 0){
        	Set<String> keySet = params.keySet();  
            for(String key : keySet) {  
                paramList.add(new BasicNameValuePair(key, params.get(key)));  
            }  
        }
        try {  
            httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        }  
        String content = null;  
        CloseableHttpResponse  httpResponse = null;  
        try {  
            httpResponse = closeableHttpClient.execute(httpost);  
            HttpEntity entity = httpResponse.getEntity();  
            content = EntityUtils.toString(entity, encode);  
            return content;
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                httpResponse.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        try {  //关闭连接、释放资源  
            closeableHttpClient.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
        return null;  
    } 
	
	
	public static String reqMsgReq(String url,String json,Map<String,String> headerMap){  
		String encode = "utf-8";   
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();   
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        if(url.startsWith("https:")){
        	SSLConnectionSocketFactory sslsf=createSSLConnSocketFactory();
        	closeableHttpClient = HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
        }
        HttpPost httpost = new HttpPost(url);
        headerMap.forEach((key,value)->{
        	httpost.addHeader(key, value);
        });
        
        //组织请求参数  
        try {
        	StringEntity requestEntity = new StringEntity(json,"utf-8");
            httpost.setEntity(requestEntity);  
        } catch (Exception e1) {  
            e1.printStackTrace();  
        }  
        String content = null;  
        CloseableHttpResponse  httpResponse = null;  
        try {  
            httpResponse = closeableHttpClient.execute(httpost);  
            HttpEntity entity = httpResponse.getEntity();  
            content = EntityUtils.toString(entity, encode);  
            return content;
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                httpResponse.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        try {  //关闭连接、释放资源  
            closeableHttpClient.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }    
        return null;  
    } 
	
	
	
	
}
