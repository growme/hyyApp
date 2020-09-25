package com.ccnet.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ApiMoneyCount;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.service.ApiMoneyCountService;
import com.ccnet.api.service.impl.NewsServiceImpl;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.PassMethodType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.html.ShortUrlUtil;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.UserDetailMoney;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;

/**
 * 收益
 */
@Controller
@RequestMapping("/api/earnings/")
public class ApiEarningsController extends BaseController<SbMoneyCount> {

	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	ApiMoneyCountService apiMoneyCountService;
	@Autowired
	SbContentInfoService sbContentInfoService;
	private final String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server");

	/**
	 * 今日收益列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	@ResponseBody
	public ResultDTO<?> earningsToday(Headers header) {
		try {
			// http.execute(request);
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			Dto dto = getParamAsDto();
			dto.put("end_date", CPSUtil.getCurDate());
			dto.put("start_date", CPSUtil.getCurDate());
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			SbVisitMoney sbVisitMoney = new SbVisitMoney();
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			sbVisitMoney.setUserId(memberInfo.getMemberId());

			// 获取当日收益
			List<UserDetailMoney> totalMoneys = sbMoneyCountService.queryUserDetailMoneyList(sbMoneyCount, sbVisitMoney,
					dto);
			return ResultDTO.OK(totalMoneys);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 当前所有收益列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	public ResultDTO<?> earningsIndex(Headers header) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			Dto dto = getParamAsDto();
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			SbVisitMoney sbVisitMoney = new SbVisitMoney();
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			sbVisitMoney.setUserId(memberInfo.getMemberId());
			// 获取当日收益
			List<UserDetailMoney> totalMoneys = sbMoneyCountService.queryUserDetailMoneyList(sbMoneyCount, sbVisitMoney,
					dto);
			return ResultDTO.OK(totalMoneys);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 当前所有收益列表
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("newsMoneyCount")
	@ResponseBody
	public ResultDTO<?> newsMoneyCount(HttpServletRequest req, Headers header) {
		try {
			Map<String, Object> map = new HashMap<>();
			if (StringUtils.isBlank(header.getUserid())) {
				map.put("count", 0);
				map.put("url", "");
				map.put("contentInfo", null);
				map.put("appWatermark", GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server")
						+ CPSUtil.getParamValue("REPEAT_APP_WATERMARK"));
				return ResultDTO.OK(map);
			}
			ApiMoneyCount table = apiMoneyCountService.getNewUserContentLog(header.getUserid());
			if (table == null) {
				map.put("count", 0);
				map.put("url", "");
				map.put("contentInfo", null);
				map.put("appWatermark", GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server")
						+ CPSUtil.getParamValue("REPEAT_APP_WATERMARK"));
				return ResultDTO.OK(map);
			}
			SbContentInfo contentInfo = sbContentInfoService
					.getSbContentInfoByID(Integer.valueOf(table.getContentId()));
			// 当前用户
			SbMoneyCount arg0 = new SbMoneyCount();
			arg0.setUserId(Integer.valueOf(header.getUserid()));
			arg0.setmType(5);
			arg0.setContentId(Integer.valueOf(table.getContentId()));
			// 获取当日收益
			List<SbMoneyCount> totalMoneys = sbMoneyCountService.findList(arg0);

			map.put("count", totalMoneys.size());
			map.put("url", getPyqurl(req, contentInfo, arg0.getUserId()));
			map.put("contentInfo", contentInfo);
			map.put("appWatermark", GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server")
					+ CPSUtil.getParamValue("REPEAT_APP_WATERMARK"));
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	public String getPyqurl(HttpServletRequest req, SbContentInfo contentInfo, Integer userId)
			throws UnsupportedEncodingException {
		// 处理好友路径
		String pyXssUrl = "";
		String wxqXssUrl = "";
		String pyqXssUrl = "";

		String pyqUrl = "";
		String wxqUrl = "";
		String pyUrl = "";
		if (CPSUtil.isNotEmpty(contentInfo)) {
			// 文章类型
			Integer contentType = contentInfo.getContentType();
			// 生成文章详情页面的推广地址
			String paramString = userId + "_" + contentInfo.getColumnId() + "_" + contentInfo.getContentId();

			String groupParam = paramString + "_groupchat";
			String messageParam = paramString + "_singlemessage";
			String timelineParam = paramString + "_timeline";

			// 处理X站部分
			String pykg = CPSUtil.getParamValue(Const.CT_USER_SHARE_PY_SWITCH);// 好友开关
			String pyqkg = CPSUtil.getParamValue(Const.CT_USER_SHARE_PYQ_SWITCH);// 朋友圈开关
			// 编码
			String pybm = CPSUtil.getParamValue(Const.CT_USER_SHARE_PY_ENCODE);
			String pyqbm = CPSUtil.getParamValue(Const.CT_USER_SHARE_PYQ_ENCODE);
			// 编码次数
			String pybmcs = CPSUtil.getParamValue(Const.CT_USER_SHARE_PY_ENCODE_COUNT);
			String pyqbmcs = CPSUtil.getParamValue(Const.CT_USER_SHARE_PYQ_ENCODE_COUNT);

			// 地址
			String pydz = CPSUtil.getParamValue(Const.CT_USER_SHARE_PY_XSSURL);
			String pyqdz = CPSUtil.getParamValue(Const.CT_USER_SHARE_PYQ_XSSURL);

			// http开关
			String py_http = CPSUtil.getParamValue(Const.CT_USER_SHARE_PY_LINK_HTTP);
			String pyq_http = CPSUtil.getParamValue(Const.CT_USER_SHARE_PYQ_LINK_HTTP);

			// 是否开启301跳转
			String canForward = CPSUtil.getParamValue(Const.PAGE_DOMAIN_FORWARD);
			// 是否开启入口随机获取
			String randomForward = CPSUtil.getParamValue(Const.CT_RANDOM_FORWARD_LINK_SWITCH);

			String target = "/article/detail/";
			if (contentType.equals(ContentType.NK.getState()) || contentType.equals(ContentType.LB.getState())) {
				target = "/article/details/";
			}

			if (CPSUtil.isNotEmpty(canForward) && "1".equals(canForward)) {// 开启了301跳转使用跳转域名
				pyUrl = getTZDomian(userId) + target + CPSUtil.getParamEncrypt(messageParam);
				pyqUrl = getTZDomian(userId) + target + CPSUtil.getParamEncrypt(timelineParam);
				wxqUrl = getTZDomian(userId) + target + CPSUtil.getParamEncrypt(groupParam);
			} else {
				pyUrl = getTGDomian(userId) + target + CPSUtil.getParamEncrypt(messageParam);
				pyqUrl = getTGDomian(userId) + target + CPSUtil.getParamEncrypt(timelineParam);
				wxqUrl = getTGDomian(userId) + target + CPSUtil.getParamEncrypt(groupParam);
			}

			// 处理http头 默认是带http的
			if (CPSUtil.isNotEmpty(py_http) && "1".equals(py_http)) {// 去掉http
				pyUrl = pyUrl.replaceAll("http://", "");
				wxqUrl = wxqUrl.replaceAll("http://", "");
				CPSUtil.xprint("》》》》》》去掉好友地址http头=" + pyqUrl);
			}

			if (CPSUtil.isNotEmpty(pyq_http) && "1".equals(pyq_http)) {// 去掉http
				pyqUrl = pyqUrl.replaceAll("http://", "");
				CPSUtil.xprint("》》》》》》去掉朋友圈地址http头=" + pyqUrl);
			}

			// 处理随机获取入口
			if (CPSUtil.isNotEmpty(randomForward) && "1".equals(randomForward)) {

				PassMethodType _med = null;
				SbForwardLink _sbfy = CPSUtil.getRandomForwardLink();
				if (CPSUtil.isNotEmpty(_sbfy)) {
					pydz = _sbfy.getLinkAddr() + "$PYURL";
					// 处理绕过机制
					if (CPSUtil.isNotEmpty(_sbfy.getPassMethod())) {
						_med = PassMethodType.getPassMethodType(_sbfy.getPassMethod());
						if (CPSUtil.isNotEmpty(_med)) {
							pydz = pydz + _med.getTypeValue() + "www.baidu.com";
						}
						CPSUtil.xprint("【好友】采用绕过机制【" + _sbfy.getPassMethod() + "】地址：" + pydz);
					}

				}
				CPSUtil.xprint("【好友】采用随机入口地址：" + pydz);

				SbForwardLink _sbfq = CPSUtil.getRandomForwardLink();
				if (CPSUtil.isNotEmpty(_sbfq)) {
					pyqdz = _sbfq.getLinkAddr() + "$PYQURL";
					// 处理绕过机制
					if (CPSUtil.isNotEmpty(_sbfq.getPassMethod())) {
						_med = PassMethodType.getPassMethodType(_sbfq.getPassMethod());
						if (CPSUtil.isNotEmpty(_med)) {
							pyqdz = pyqdz + _med.getTypeValue() + "www.baidu.com";
						}
						CPSUtil.xprint("【朋友圈】采用绕过机制【" + _sbfq.getPassMethod() + "】地址：" + pyqdz);
					}

				}
				CPSUtil.xprint("【朋友圈】采用随机入口地址：" + pyqdz);

			}

			// 处理x站
			if (CPSUtil.isNotEmpty(pykg) && "1".equals(pykg) && CPSUtil.isNotEmpty(pydz)) {

				if (CPSUtil.isNotEmpty(pybm) && "1".equals(pybm)) {
					// 判断编码次数
					if (CPSUtil.isNotEmpty(pybmcs) && "2".equals(pybmcs)) {// 编码两次
						pyUrl = URLEncoder.encode(URLEncoder.encode(pyUrl, "UTF-8"), "UTF-8");
						wxqUrl = URLEncoder.encode(URLEncoder.encode(wxqUrl, "UTF-8"), "UTF-8");
					} else {// 编码一次
						pyUrl = URLEncoder.encode(pyUrl, "UTF-8");
						wxqUrl = URLEncoder.encode(wxqUrl, "UTF-8");
					}
				}

				// 处理替换地址
				if (pydz.contains("$PYURL")) {
					pyXssUrl = pydz.replaceAll("\\$PYURL", pyUrl);
					wxqXssUrl = pydz.replaceAll("\\$PYURL", wxqUrl);
				}

			} else {
				pyXssUrl = pyUrl;
				wxqXssUrl = wxqUrl;
			}

			// 处理朋友圈路径
			if (CPSUtil.isNotEmpty(pyqkg) && "1".equals(pyqkg) && CPSUtil.isNotEmpty(pyqdz)) {
				if (CPSUtil.isNotEmpty(pyqbm) && "1".equals(pyqbm)) {
					// 判断编码次数
					if (CPSUtil.isNotEmpty(pyqbmcs) && "2".equals(pyqbmcs)) {// 编码两次
						pyqUrl = URLEncoder.encode(URLEncoder.encode(pyqUrl, "UTF-8"), "UTF-8");
					} else {// 编码一次
						pyqUrl = URLEncoder.encode(pyqUrl, "UTF-8");
					}
				}

				// 处理替换地址
				if (pyqdz.contains("$PYQURL")) {
					pyqXssUrl = StringUtils.replace(pyqdz, "$PYQURL", pyqUrl);
				}
			} else {
				pyqXssUrl = pyqUrl;
			}

			CPSUtil.xprint("pyXssUrl=" + pyXssUrl);
			CPSUtil.xprint("pyqXssUrl=" + pyqXssUrl);
			CPSUtil.xprint("wxqXssUrl=" + wxqXssUrl);

			wxqXssUrl = ShortUrlUtil.getShortUrl(wxqXssUrl);

			if (contentInfo.getPicList() != null && contentInfo.getPicList().size() > 0) {
				for (int z = 0; z < contentInfo.getPicList().size(); z++) {
					if (contentInfo.getPicList().get(z).getContentPic() != null
							&& contentInfo.getPicList().get(z).getContentPic().indexOf("http") < 0) {
						contentInfo.getPicList().get(z)
								.setContentPic(DOMAIN + contentInfo.getPicList().get(z).getContentPic());
					}
				}
			}
			if (StringUtils.isNotBlank(contentInfo.getVideoLink()) && contentInfo.getVideoLink().indexOf("http") < 0) {
				contentInfo.setVideoLink(NewsServiceImpl.getVideoUrl(contentInfo.getVideoLink()));
			}
		}
		return pyXssUrl;
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
			tzym = dealForwardParseDomain(CPSUtil.getRandomOrderDomain(ContentDomainType.TZYM, memberId));
			CPSUtil.xprint("获取到的跳转域名为：" + tzym);
		} else {
			tzym = getDefaultDomain();
			CPSUtil.xprint("使用默认域名为：" + tzym);
		}
		return dealForwardUrl(tzym);
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
	
	
}
