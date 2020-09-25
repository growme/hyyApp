/**
 * created by 朱施健 2019年6月3日 下午6:02:12
 */
package com.ccnet.api.util;

import java.io.IOException;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * @author 朱施健
 */
public class WechatUtil {
	private static final String WX_CODE2SESSION = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=<appid>&secret=<secret>&code=<code>&grant_type=authorization_code";
	// private static final String WX_CODE2SESSION =
	// "https://api.weixin.qq.com/sns/oauth2/access_token?appid=<appid>&secret=<secret>&js_code=<code>&grant_type=authorization_code";

	private static final String WX_OAUTH2_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=<webtoken>&openid=<openid>&lang=zh_CN";

	private static CloseableHttpClient client;

	public static JSONObject confirmLogin(CloseableHttpClient httpClient, String appid, String secret, String code)
			throws ParseException, IOException {
		// 拼接请求的url
		HttpGet httpGet = new HttpGet(
				WX_CODE2SESSION.replace("<appid>", appid).replace("<secret>", secret).replace("<code>", code));
		HttpResponse httpResponse = httpClient.execute(httpGet);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = httpResponse.getEntity();
			String response = EntityUtils.toString(entity, "UTF-8");
			JSONObject resp1 = JSON.parseObject(response);

			if(resp1.containsKey("errcode")) {
				return resp1;
			}
			
			HttpGet httpGet2 = new HttpGet(WX_OAUTH2_USERINFO_URL.replace("<webtoken>", resp1.getString("access_token"))
					.replace("<openid>", resp1.getString("openid")));
			HttpResponse httpResponse2 = httpClient.execute(httpGet2);
			if (httpResponse2.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity2 = httpResponse2.getEntity();
				String response2 = EntityUtils.toString(entity2, "UTF-8");
				JSONObject resp2 = JSON.parseObject(response2);

				for (Entry<String, Object> e : resp1.entrySet()) {
					if (!resp2.containsKey(e.getKey()))
						resp2.put(e.getKey(), e.getValue());
				}
				return resp2;
			} else {
				return resp1;
			}
		} else {
			return null;
		}
	}
}
