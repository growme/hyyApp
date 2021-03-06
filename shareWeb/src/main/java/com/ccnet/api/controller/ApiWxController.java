package com.ccnet.api.controller;

import com.alibaba.fastjson.JSON;
import com.ccnet.api.entity.Oauth2Token;
import com.ccnet.api.entity.SNSUserInfo;
import com.ccnet.api.util.NetUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/wx/wechat")
public class ApiWxController {

	private static Logger log = LoggerFactory.getLogger(ApiWxController.class);
	// @Autowired
	// private WxUserService wxuserService;

	// appId
	private static String appid = "wxed0e25a9a3be40b4";
	// appSecret
	private static String appSecret = "40b867acea67cf14d78a066712fe9902";
	// 回调地址,要跟下面的地址能调通(/wxLogin)
	private static String backUrl = "http://kanxiaoshuo.net.cn/wechat/wxLogin";

	/**
	 * 向指定URL发送GET方法的请求
	 * @return URL 所代表远程资源的响应结果
	 *         用户同意授权，获取code
	 */
	@RequestMapping("/wxLoginInit")
	public void loginInit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/**
		 * 这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
		 **/
		// AuthUtil.APPID微信公众号的appId
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
				+ URLEncoder.encode(backUrl, "UTF-8") + "&response_type=code" + "&scope=snsapi_userinfo"
				+ "&state=STATE#wechat_redirect";
		// 重定向到重定向地址
		response.sendRedirect(url);
	}

	@RequestMapping("/wxLogin")
	public String wxLogin(Model model, HttpServletRequest request, HttpServletResponse response) {
		// 微信公众号的APPID和APPSECRET
		String code = request.getParameter("code");
		String params = request.getParameter("params");
		System.out.println("****************code:" + code + "*********params" + params);
		try {
			// 获取网页授权access_token openid 等
			Oauth2Token oauth2Token = getOauth2AccessToken(appid, appSecret, code);
			System.out.println("***********************************oauth2Token信息" + oauth2Token);
			// 用户标识
			String openId = oauth2Token.getOpenId();
			String msg = Base64.getEncoder().encodeToString(openId.getBytes());
			System.out.println("openId+++++++++" + openId + "********" + params + "?type=" + msg);
			if (!params.contains("http")) {
				params = "http://" + params;
			}
			model.addAttribute("openId", msg);
			model.addAttribute("params", params);
			// response.sendRedirect(params + "?type=" + msg);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("网络异常");
		}
		return "user/jsp/content/content_wx_login";
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
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
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
				log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wxUserInfo;
	}

	public String baseloginInit(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 微信公众号的APPID和APPSECRET
		String code = request.getParameter("code");
		/**
		 * 这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
		 **/
		if (code == null) {
			// AuthUtil.APPID微信公众号的appId
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
					+ URLEncoder.encode(backUrl, "UTF-8") + "&response_type=code" + "&scope=snsapi_userinfo"
					+ "&state=STATE#wechat_redirect";
			// 重定向到重定向地址
			response.sendRedirect(url);
		}
		// 获取网页授权access_token openid 等
		Oauth2Token oauth2Token = getOauth2AccessToken(appid, appSecret, code);
		System.out.println("***********************************oauth2Token信息");
		// 用户标识
		return oauth2Token.getOpenId();
	}
}