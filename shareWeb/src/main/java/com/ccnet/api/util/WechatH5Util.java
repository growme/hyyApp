package com.ccnet.api.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.ccnet.api.entity.Oauth2Token;
import com.ccnet.api.entity.SNSUserInfo;

public class WechatH5Util {

	// appId
	private static String appid = "wxed0e25a9a3be40b4";
	// appSecret
	private static String appSecret = "40b867acea67cf14d78a066712fe9902";
	// 回调地址,要跟下面的地址能调通(/wxLogin)
	private static String backUrl = "http://kanxiaoshuo.net.cn/wechat/wxLogin";
	// 登录成功回调地址(返回你指定的地址)
	private static String urlLogin = "http://kanxiaoshuo.net.cn/";

	// 微信授权登录
	public void loginUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
				+ URLEncoder.encode(backUrl, "UTF-8") + "&response_type=code" + "&scope=snsapi_userinfo"
				+ "&state=STATE#wechat_redirect";
		response.sendRedirect(url);
	}

	// 微信静默登录
	public void loginBase(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
				+ URLEncoder.encode(backUrl, "UTF-8") + "&response_type=code" + "&scope=snsapi_base"
				+ "&state=STATE#wechat_redirect";
		response.sendRedirect(url);
	}

	/**
	 * 获取网页授权凭证
	 *
	 * @param appId
	 *            公众账号的唯一标识
	 * @param appSecret
	 *            公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static Oauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		Oauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(NetUtil.get(requestUrl));
		if (null != jsonObject) {
			try {
				wat = new Oauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInteger("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
			}
		}
		return wat;
	}

	/**
	 * 通过网页授权获取用户信息
	 *
	 * @param accessToken
	 *            网页授权接口调用凭证
	 * @param openId
	 *            用户标识
	 * @return SNSUserInfo
	 */
	public static SNSUserInfo getWxUserInfo(String accessToken, String openId) {
		SNSUserInfo wxUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(NetUtil.get(requestUrl));

		if (null != jsonObject) {
			try {
				wxUserInfo = new SNSUserInfo();
				// 用户的标识
				wxUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				wxUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				wxUserInfo.setSex(jsonObject.getInteger("sex"));
				// 用户所在国家
				wxUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				wxUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				wxUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				wxUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				List<String> list = JSON.parseArray(jsonObject.getString("privilege"), String.class);
				wxUserInfo.setPrivilegeList(list);
				// 与开放平台共用的唯一标识，只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
				wxUserInfo.setUnionid(jsonObject.getString("unionid"));
			} catch (Exception e) {
				wxUserInfo = null;
				int errorCode = jsonObject.getInteger("errcode");
				String errorMsg = jsonObject.getString("errmsg");
			}
		}
		return wxUserInfo;
	}
}
