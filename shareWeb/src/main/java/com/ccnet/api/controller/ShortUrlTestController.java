package com.ccnet.api.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.WechatPublicNumber;
import com.ccnet.api.service.WechatPublicNumberService;
import com.ccnet.api.util.ShortUrlTest;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.HttpUtil;
import com.ccnet.core.common.utils.StringUtils;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.SystemParams;

@Controller
@RequestMapping("/wx")
public class ShortUrlTestController extends BaseController<SystemParams> {

	@Autowired
	WechatPublicNumberService wechatPublicNumberService;
	
	@RequestMapping("shortUrl")
	@ResponseBody
	public ResultDTO<?> shortUrl(String url, Headers header) {
		if(StringUtils.isBlank(url)){
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		if(!url.contains("http://")){
			url = "http://"+url;
		}
		String[] shortUrl = ShortUrlTest.shortUrl(url);
		List<String> list = new ArrayList<>();
		String domen = getTZDomian(Integer.valueOf(header.getUserid()));
		for (int i = 0; i < shortUrl.length; i++) {
			JedisUtils.set("CT_ShortUrlTest:"+shortUrl[i], url, 60*60*24*3);
			list.add(domen+"/wx/"+shortUrl[i]);
		}
		return ResultDTO.OK(list);
	}

	@RequestMapping("wxShortUrl")
	@ResponseBody
	public ResultDTO<?> wxshortUrl(String url, Headers header) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		if(StringUtils.isBlank(url)){
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		if(!url.contains("http://")){
			url = "http://"+url;
		}
		WechatPublicNumber arg0 = new WechatPublicNumber();
		arg0.setEnabled("1");
		List<WechatPublicNumber> list = wechatPublicNumberService.findList(arg0);
		String wxUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+list.get(0).getAccessToken();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action", "long2short");
		map.put("long_url", url);
		String str = HttpUtil.post(wxUrl, JSON.toJSONString(map), false);
		JSONObject json = (JSONObject) JSON.parse(str);
		List<String> lisy = new ArrayList<>();
		if(json.getString("errcode").equals("0")){
			lisy.add(json.getString("short_url"));
		}
		return ResultDTO.OK(lisy);
	}
	
	@RequestMapping("/{url}")
	public String putUrl(@PathVariable("url") String url) {
		try {
			String maxUrl = JedisUtils.get("CT_ShortUrlTest:"+url);
			if(StringUtils.isNoneBlank(maxUrl)){
				return "redirect:" + maxUrl;
			}
			return "redirect:http://www.baidu.com";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http://www.baidu.com";
	}
	
	/**
	 * 获取跳转域名
	 * 
	 * @param memberId
	 * @return
	 */
	public String getTZDomian(Integer memberId) {
		String tzym = null;
		if (CPSUtil.isNotEmpty(memberId)) {
			tzym = CPSUtil.getRandomOrderDomain(ContentDomainType.TZYM, memberId);
			CPSUtil.xprint("获取到的跳转域名为：" + tzym);
		} else {
			tzym = getDefaultDomain();
			CPSUtil.xprint("使用默认域名为：" + tzym);
		}
		return dealForwardUrl(tzym);
	}

	/**
	 * 获取推广域名
	 * 
	 * @param memberId
	 * @return
	 */
	public String getTGDomian(Integer memberId) {
		String tgym = null;
		if (CPSUtil.isNotEmpty(memberId)) {
			tgym = dealForwardParseDomain(CPSUtil.getRandomOrderDomain(ContentDomainType.TGYM, memberId));
			CPSUtil.xprint("获取到的推广域名为：" + tgym);
		} else {// 获取默认域名
			tgym = getDefaultDomain();
			CPSUtil.xprint("使用默认域名为：" + tgym);
		}
		return dealForwardUrl(tgym);
	}

	/**
	 * 处理泛解析域名
	 * 
	 * @param forwardDomain
	 * @return
	 */
	public String dealForwardParseDomain(String forwardDomain) {
		// 获取解析参数
		String useGeDomain = CPSUtil.getParamValue(Const.GENERIC_PARSE_DOMAIN);// 开启域名泛解析
		String geDomainType = CPSUtil.getParamValue(Const.DOMAIN_FOR_GENERIC_PARSE_TYPE);// 泛解析域名类型
																							// 0
																							// 为纯数字
																							// 1
																							// 纯字母
																							// 2
																							// 字母数字混合
		String geDomainLen = CPSUtil.getParamValue(Const.DOMAIN_FOR_GENERIC_PARSE_LENGTH);// 解析前缀长度
																							// 建议不要超过32位
		if (CPSUtil.isNotEmpty(useGeDomain) && "1".equals(useGeDomain)) {
			if (CPSUtil.isNotEmpty(geDomainLen) && CPSUtil.isNotEmpty(geDomainType)) {
				// 处理容错www.
				String redomain = "";
				if (CPSUtil.isNotEmpty(forwardDomain)) {// 此处修改成多个主域名随机泛解析
					if (forwardDomain.contains("www.")) {
						forwardDomain = forwardDomain.replaceAll("www.", "");
					}
					redomain = forwardDomain;
				}
				// 处理类型和长度
				Integer geType = Integer.valueOf(geDomainType);
				Integer geLength = Integer.valueOf(geDomainLen);
				if (!CPSUtil.isNotEmpty(geType)) {
					geType = 2;// 默认数字+字母
				}
				if (!CPSUtil.isNotEmpty(geLength)) {
					geLength = 15;// 默认15位
				}
				forwardDomain = UniqueID.getUniqueID(geLength, geType) + "." + redomain;// 采用随机字符串
				CPSUtil.xprint("301随机跳转泛解析域名******>" + forwardDomain);
			}
		}

		CPSUtil.xprint("域名处理完毕返回结果******>" + forwardDomain);

		return forwardDomain;
	}
}
