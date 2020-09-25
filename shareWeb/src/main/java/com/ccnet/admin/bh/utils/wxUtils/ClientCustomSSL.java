package com.ccnet.admin.bh.utils.wxUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;


/**
 * @Auther: psk
 * @Description: 加载证书 发送请求
 */
public class ClientCustomSSL {

	public static String doRefund(String url, String xmlData) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(GetPropertiesValue.getValue("Config.properties", "WXwebStatic")+"/cert/apiclient_cert.p12"));// P12文件目录
		try {
			
			keyStore.load(instream, GetPropertiesValue.getValue("Config.properties", "WXMchID").toCharArray());// 这里写密码..默认是你的MCHID
		} finally {
			instream.close();
		}
		javax.net.ssl.SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, GetPropertiesValue.getValue("Config.properties", "WXMchID").toCharArray())
				.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
//			httpost.addHeader("Connection", "keep-alive");
//			httpost.addHeader("Accept", "*/*");
//			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//			httpost.addHeader("Host", "api.mch.weixin.qq.com");
//			httpost.addHeader("X-Requested-With", xmlData);
//			httpost.addHeader("Cache-Control", "max-age=0");
//			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
//			httpost.setEntity(new StringEntity("UTF-8"));
			httpost.addHeader("Content-Type", "text/xml");
			StringEntity se = new StringEntity(xmlData,"UTF-8");
			httpost.setEntity(se);
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				String returnMessage = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return returnMessage; // 返回后自己解析结果
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

}
