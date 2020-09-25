package com.ccnet.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ccnet.api.dao.ApiSbContentInfoDao;
import com.ccnet.api.dao.ApiSbContentPic;
import com.ccnet.api.service.impl.NewsServiceImpl;
import com.ccnet.api.util.RequestInfoUtils;
import com.ccnet.core.common.AdType;
import com.ccnet.core.common.CommentType;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.PassMethodType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.cache.UserContentReadCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.MD5;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.domain.GetLatAndLngByMapApi;
import com.ccnet.core.common.utils.html.ExtractHtmlUtil;
import com.ccnet.core.common.utils.html.ShortUrlUtil;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.common.utils.sms.IPLocationUtil;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.UserInfoService;
import com.ccnet.cps.entity.ExpandButton;
import com.ccnet.cps.entity.IpLocation;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbAdvertVisitLog;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbCommentLog;
import com.ccnet.cps.entity.SbContentComment;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.service.IpLocationService;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.RecodeMoneyService;
import com.ccnet.cps.service.SbAdvertVisitLogService;
import com.ccnet.cps.service.SbAdvertiseInfoService;
import com.ccnet.cps.service.SbCommentLogService;
import com.ccnet.cps.service.SbContentCommentService;
import com.ccnet.cps.service.SbContentInfoService;

/**
 * 文章请求
 */
@Controller
@RequestMapping("/article/")
public class ArticleController extends BaseController<SbAdvertInfo> {

	@Autowired
	private SbContentInfoService sbContentInfoService;
	@Autowired
	private RecodeMoneyService recodeMoneyService;
	@Autowired
	private SbContentCommentService contentCommentService;
	@Autowired
	private SbCommentLogService commentLogService;
	@Autowired
	private ApiSbContentPic apiSbContentPicDao;
	@Autowired
	private SbAdvertiseInfoService adService;
	@Autowired
	private IpLocationService ipLocationService;
	@Autowired
	private ApiSbContentInfoDao apiSbContentInfoDao;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private SbAdvertVisitLogService sbAdvertVisitLogService;

	/**
	 * 文章列表入口
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String articleList(Model model) {
		Dto dto = getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		if (CPSUtil.isNotEmpty(memberInfo)) {
			List<SbColumnInfo> columns = (List<SbColumnInfo>) CPSUtil.getContextAtrribute(Const.CT_COLUMN_LIST);
			model.addAttribute("columns", columns);
			model.addAttribute("columnId", dto.getAsInteger("columnId"));
			model.addAttribute(Const.MENU_SELECTED_INDEX, "clist");
			model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
			return "user/jsp/content/content_index";
		} else {
			return "redirect:/user/login";
		}
	}

	/**
	 * 动态加载文章列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "page", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public AjaxRes ListPage(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			Dto dto = getParamAsDto();
			MemberInfo memberInfo = getCurUser();
			if (CPSUtil.isEmpty(memberInfo)) {
				return ar;
			}

			String reqPath = req.getRequestURI();
			String nowURl = req.getServletPath();
			String visitIP = IPUtil.getIpAddr(req);// 获取用户IP
			String temp_path = nowURl.substring(nowURl.lastIndexOf("/") + 1, nowURl.length());
			String userAgent = req.getHeader("user-agent");
			String isWeixin = CPSUtil.isWeixin(req);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】isWeixin=" + isWeixin);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】reqPath:" + reqPath);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】temp路径:" + temp_path);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】正在访问URL:" + nowURl);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】访问人IP地址:" + visitIP);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】userAgent=" + userAgent);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【ListPage】userID=" + memberInfo.getLoginAccount() + "["
					+ memberInfo.getAccountName() + "]");

			String basePath = dto.getAsString("basePath");
			Page<SbContentInfo> contents = newPage(dto);
			contents.setPageNum(dto.getAsInteger("page") + 1);
			SbContentInfo sbContentInfo = new SbContentInfo();
			sbContentInfo.setColumnId(dto.getAsInteger("columnId"));
			// 只显示审核通过
			sbContentInfo.setCheckState(StateType.Valid.getState());
			// 获取文章列表
			contents = sbContentInfoService.findSbContentInfoByPage(sbContentInfo, contents, dto);
			StringBuffer contentListBuffer = new StringBuffer("");
			if (null != contents.getResults()) {
				for (SbContentInfo contentInfo : contents.getResults()) {
					String targetUrl = "";
					if (CPSUtil.isNotEmpty(contentInfo.getContentPics())) {
						// 判断地址
						targetUrl = basePath + "article/info?cid=" + contentInfo.getContentId();
						// 存在图片
						String picList[] = contentInfo.getContentPics().split(",");
						// 单张图
						if (picList.length == 1) {
							contentListBuffer.append("<section class=\"item single-pic\">");
							contentListBuffer.append("<a class=\"link\" style=\"margin:0px;\" href=\"" + targetUrl
									+ "\" title=\"" + contentInfo.getFilterTitle() + "\">");
							contentListBuffer.append("<div class=\"desc\">");
							contentListBuffer.append("<h3 style=\"padding-bottom:0px;margin:0px;\" class=\"title "
									+ isHotArticle(contentInfo, "title") + "\">"
									+ dealContentTitle(contentInfo.getFilterTitle(), 20) + "</h3>");
							contentListBuffer.append("<div class=\"info\">");
							contentListBuffer.append(isHotArticle(contentInfo, "label"));
							// contentListBuffer.append("<span
							// class=\"category\">"+contentInfo.getColumnInfo().getColumnName()+"</span>");
							contentListBuffer.append("<span class=\"view\">阅读<font color=red>"
									+ contentInfo.getReadAward() + "</font>元</span>");
							// contentListBuffer.append("<span
							// class=\"share\">好友"+contentInfo.getFriendShareAward()+"元</span>");
							// contentListBuffer.append("<span
							// class=\"share\">朋友圈"+contentInfo.getTimelineShareAward()+"元</span>");
							contentListBuffer
									.append("<span class=\"money\">浏览" + contentInfo.getReadNum() + "次</span>");
							contentListBuffer.append("<span class=\"money\">"
									+ DateUtils.formatDate(contentInfo.getCreateTime(), DateUtils.parsePatterns[1])
									+ "</span>");
							contentListBuffer.append("</div>");
							contentListBuffer.append("</div>");
							contentListBuffer.append("<div class=\"picture\">");
							if (CPSUtil.isNotEmpty(contentInfo.getContentPicLink())) {
								contentListBuffer
										.append("<img src=\"" + contentInfo.getContentPicLink() + "\"> </div>");
							} else {
								contentListBuffer.append("<img src=\"" + picList[0] + "\"> </div>");
							}
							contentListBuffer.append("");
							contentListBuffer.append("</div></a></section>");
						}

						// 多张图
						if (picList.length > 1) {
							contentListBuffer.append("<section class=\"item multi-pic\">");
							contentListBuffer.append("<a class=\"link\" href=\"" + targetUrl + "\" title=\""
									+ dealContentTitle(contentInfo.getFilterTitle(), 30) + "\">");
							contentListBuffer.append("<h3 class=\"title " + isHotArticle(contentInfo, "title") + "\">"
									+ contentInfo.getFilterTitle() + "</h3>");
							contentListBuffer.append("<div class=\"picture\">");
							for (String imgPic : picList) {
								contentListBuffer.append("<div class=\"column\">");
								if (CPSUtil.isNotEmpty(contentInfo.getContentPicLink())) {
									contentListBuffer
											.append("<img src=\"" + contentInfo.getContentPicLink() + "\"> </div>");
								} else {
									contentListBuffer.append("<img src=\"" + imgPic + "\"> </div>");
								}
								// contentListBuffer.append("</div>");
							}
							contentListBuffer.append("</div>");
							contentListBuffer.append("<div class=\"info\">");
							contentListBuffer.append(isHotArticle(contentInfo, "label"));
							// contentListBuffer.append("<span
							// class=\"category\">"+contentInfo.getColumnInfo().getColumnName()+"</span>");
							contentListBuffer.append("<span class=\"view\">阅读<font color=red>"
									+ contentInfo.getReadAward() + "</font>元</span>");
							// contentListBuffer.append("<span
							// class=\"share\">好友"+contentInfo.getFriendShareAward()+"元</span>");
							// contentListBuffer.append("<span
							// class=\"share\">朋友圈"+contentInfo.getTimelineShareAward()+"元</span>");
							contentListBuffer
									.append("<span class=\"money\">浏览" + contentInfo.getReadNum() + "次</span>");
							contentListBuffer.append("<span class=\"money\">"
									+ DateUtils.formatDate(contentInfo.getCreateTime(), DateUtils.parsePatterns[1])
									+ "</span>");
							contentListBuffer.append("</div></a></section>");
						}
					} else {
						// 不存在图片
						contentListBuffer.append("<section class=\"item none-pic\">");
						contentListBuffer.append("<a class=\"link\" href=\"" + targetUrl + "\" title=\""
								+ dealContentTitle(contentInfo.getFilterTitle(), 20) + "\">");
						contentListBuffer.append("<h3 class=\"title " + isHotArticle(contentInfo, "title") + "\">"
								+ contentInfo.getFilterTitle() + "</h3>");
						contentListBuffer.append("<div class=\"info\">");
						contentListBuffer.append(isHotArticle(contentInfo, "label"));
						// contentListBuffer.append("<span
						// class=\"category\">"+contentInfo.getColumnInfo().getColumnName()+"</span>");
						contentListBuffer.append("<span class=\"view\">阅读<font color=red>" + contentInfo.getReadAward()
								+ "</font>元</span>");
						// contentListBuffer.append("<span
						// class=\"share\">好友"+contentInfo.getFriendShareAward()+"元</span>");
						// contentListBuffer.append("<span
						// class=\"share\">朋友圈"+contentInfo.getTimelineShareAward()+"元</span>");
						contentListBuffer.append("<span class=\"money\">浏览" + contentInfo.getReadNum() + "次</span>");
						contentListBuffer.append("<span class=\"money\">"
								+ DateUtils.formatDate(contentInfo.getCreateTime(), DateUtils.parsePatterns[1])
								+ "</span>");
						contentListBuffer.append("</div></a></section>");
					}
				}
			}

			if (null != contents.getResults()) {
				ar.setRes(contents.getResults().size());
				ar.setObj(contentListBuffer.toString());
			} else {
				ar.setRes(Const.FAIL);
				ar.setObj("");
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setRes(Const.FAIL);
			ar.setFailMsg("");
		}
		return ar;
	}

	/**
	 * 验证是不是热门文章
	 * 
	 * @param contentInfo
	 * @param index
	 * @author Jackie Wang
	 * @date 2019-10-27
	 */
	private String isHotArticle(SbContentInfo contentInfo, String index) {
		String indexString = "";
		if (contentInfo.getHomeToped() == 1 && CPSUtil.isNotEmpty(contentInfo.getTopedTime())) {
			if (index.equals("title")) {
				indexString = " font-bold";
			} else {
				indexString = "<span class=\"label label-red\">热</span>";
			}
		}
		return indexString;
	}

	/**
	 * 获取随机扩展按钮
	 * 
	 * @param len
	 * @param color
	 * @return
	 */
	private List<ExpandButton> getExpandButtons(int len, String color) {
		ExpandButton expandButton = null;
		List<ExpandButton> list = new ArrayList<ExpandButton>();
		int rand = RandomNum.getRandomIntVal(len);
		CPSUtil.xprint("rand=" + rand);
		for (int i = 0; i < len; i++) {
			expandButton = new ExpandButton();
			if (rand == i && rand != len) {
				expandButton.setDispayType("block");
			} else {
				expandButton.setDispayType("none");
			}
			expandButton.setButtonIndex(RandomNum.getRandDoubleVal(80) + "%");
			expandButton.setButtonColor(color);
			list.add(expandButton);
		}

		return list;
	}

	/**
	 * 处理小说二维码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/barcode/show")
	public String showContentBarcode(Model model, HttpServletRequest req) {

		Dto paramDto = getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		if (memberInfo == null) {
			return "redirect:/user/login";
		}
		Integer contentId = paramDto.getAsInteger("content_id");
		String isWeixin = CPSUtil.isWeixin(req);
		SbContentInfo contentInfo = null;
		if (CPSUtil.isNotEmpty(contentId)) {
			contentInfo = sbContentInfoService.getSbContentInfoByID(contentId);
			if (CPSUtil.isNotEmpty(contentInfo)) {
				model.addAttribute("id", contentId);
				model.addAttribute("isWeixin", isWeixin);
				model.addAttribute("content", contentInfo);
			} else {
				return "error/error.jsp";
			}
		} else {
			return "error/error.jsp";
		}

		return "user/jsp/content/show_content_barcode";
	}

	/**
	 * 处理小说二维码
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/barcode/group")
	public String showGroupBarcode(Model model, HttpServletRequest req) {
		Dto paramDto = getParamAsDto();
		String url = paramDto.getAsString("url");
		String isWeixin = CPSUtil.isWeixin(req);
		MemberInfo memberInfo = getCurUser();
		if (memberInfo == null) {
			return "redirect:/user/login";
		}
		if (CPSUtil.isNotEmpty(url)) {
			model.addAttribute("url", url);
			model.addAttribute("isWeixin", isWeixin);
			return "user/jsp/content/show_group_barcode";
		} else {
			return "error/error.jsp";
		}
	}

	/**
	 * 文章查看
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("info")
	public String articleInfo(Model model, HttpServletRequest req) throws UnsupportedEncodingException {

		Dto dto = getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		Integer contentId = dto.getAsInteger("cid");
		SbContentInfo contentInfo = new SbContentInfo();
		if (CPSUtil.isNotEmpty(contentId) && CPSUtil.isNotEmpty(memberInfo)) {
			Integer userId = memberInfo.getMemberId();
			// 改成内存中提取正文
			contentInfo = CPSUtil.getContentInfoById(contentId);
			if (contentInfo == null || contentInfo.getContentId() == null
					|| contentInfo.getContentId().toString().equals("0")) {
				contentInfo = sbContentInfoService.getSbContentInfoByID(contentId);
			}
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

				// 处理好友路径
				String pyXssUrl = "";
				String wxqXssUrl = "";
				String pyqXssUrl = "";

				String pyUrl = "";
				String pyqUrl = "";
				String wxqUrl = "";

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

				// 缩短地址
				// pyXssUrl = ShortUrlUtil.getShortUrl(pyXssUrl);
				// pyqXssUrl = ShortUrlUtil.getShortUrl(pyqXssUrl);
				wxqXssUrl = ShortUrlUtil.getShortUrl(wxqXssUrl);

				String isWeixin = CPSUtil.isWeixin(req);
				String ranColor = CPSUtil.getRandomColor();
				model.addAttribute("uid", memberInfo.getMemberId());
				model.addAttribute("cid", contentId);
				model.addAttribute("ydgf", RandomNum.getRandomIntVal(10));
				model.addAttribute("gzgf", RandomNum.getRandomIntVal(22));
				model.addAttribute("ranColor", ranColor);
				model.addAttribute("adRanColor", CPSUtil.getRandomColor());
				model.addAttribute("isWeixin", isWeixin);
				model.addAttribute("content", contentInfo);
				model.addAttribute("copyText", contentInfo.getContentTitle() + "..." + wxqXssUrl);
				model.addAttribute("contentUrl", wxqXssUrl);
				model.addAttribute("_contentUrl", CPSUtil.encryptBased64(pyXssUrl));
				model.addAttribute("pyqUrl", pyqXssUrl);
				model.addAttribute("pyUrl", pyXssUrl);
				model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());

				if (contentType.equals(ContentType.Video.getState())) {
					return "/user/jsp/content/vcontent_info";
				} else {
					return "/user/jsp/content/acontent_info";
				}
			} else {
				return "/error/error.jsp";
			}
		} else {
			return "redirect:/user/login";
		}
	}

	/**
	 * 验证是否唯一
	 * 
	 * @param hashKey
	 * @param session
	 * @return
	 */
	public boolean isFirstRequest(String hashKey) {
		boolean firstReq = true;
		// 获取hashkey值对应的对象
		String account_money = JedisUtils.getHashKeyCacheValue(hashKey, "account_money");
		if (CPSUtil.isNotEmpty(account_money) && "1".equals(account_money)) {
			// 不是首次访问
			String account_date = JedisUtils.getHashKeyCacheValue(hashKey, "account_date");
			CPSUtil.xprint("成功记账时间为 : " + account_date);
			firstReq = false;
		}
		return firstReq;
	}

	/**
	 * 处理文章指纹
	 * 
	 * @param req
	 * @param contentId
	 * @param userId
	 * @param shareType
	 * @param uuid
	 * @return
	 */
	public String getContentHashKey(HttpServletRequest req, String contentId, String userId, String shareType) {

		String hashKey = "0";
		StringBuilder logger = new StringBuilder();
		String visitIP = IPUtil.getIpAddr(req);// 获取用户IP
		// String clientExp = CPSUtil.getClientExpType(req, "1");
		// String clientOS = CPSUtil.getClientExpType(req, "2");
		String clientExp = RequestInfoUtils.getBrowser(req);
		String clientOS = RequestInfoUtils.getOs(req);
		String userAgent = req.getHeader("user-agent");
		logger.append("当前访问IP：").append(visitIP).append("; \n");
		logger.append("当前访问EXP：").append(clientExp).append("; \n");
		logger.append("当前访问OS：").append(clientOS).append("; \n");
		logger.append("客户端详情：").append(userAgent).append("; \n");
		MemberInfo loginUser = getCurUser();
		boolean isWeixin = CPSUtil.isWeixin(req).equals("1") ? true : false;
		logger.append("微信访问：").append(isWeixin).append("; \n");
		if (isWeixin) {// 必须微信访问
			if (CPSUtil.isNotEmpty(loginUser) && userId.equals("" + loginUser.getMemberId())) {// 本人查看不能计费
				logger.append("登录用户自己看不计费！ \n");
				logger.append("userId：").append(userId).append(";memberId:" + loginUser.getMemberId() + " \n");
				hashKey = "0";
			} else {
				// 计算hashkey
				String md5Str = userId + "<+>" + contentId + "<+>" + shareType + "<+>" + visitIP + "<+>"
						+ dealUserAgent(userAgent);
				logger.append("md5Str=").append(md5Str).append("; \n");
				hashKey = CPSUtil.encryptBased64(CPSUtil.encryptBasedDes(md5Str));
				boolean first_req = isFirstRequest(hashKey);
				logger.append("hashkey=").append(hashKey).append("; ");
				logger.append("当前文章首次访问：").append(first_req).append("; \n");
				if (first_req) {// 是首次访问
					JedisUtils.setHashKeyCacheValue(hashKey, "account_money", "0");
					JedisUtils.setHashKeyCacheValue(hashKey, "account_date", "");
				} else {
					// 不是首次访问则hashKey=0
					hashKey = "0";
				}
			}
		}

		CPSUtil.xprint(logger.toString());
		return hashKey;
	}

	/**
	 * 去掉userAgent中的网络类型 去掉重复
	 * 
	 * @param userAgent
	 * @return
	 */
	private String dealUserAgent(String userAgent) {
		String _userAgent = "";
		if (CPSUtil.isNotEmpty(userAgent)) {
			int index = userAgent.indexOf("NetType");
			_userAgent = userAgent.substring(0, index);
			_userAgent = _userAgent.trim();
		}
		CPSUtil.xprint("_userAgent====>" + _userAgent);
		return _userAgent;
	}

	/**
	 * 文章内容(nk和裂变处理)
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("details/{params}")
	public String articleDetails(Model model, HttpServletRequest req, @PathVariable String params) {
		CPSUtil.xprint("文章访问开始..");
		CPSUtil.xprint("params=" + params);
		String nowDomian = req.getServerName();
		SbContentInfo contentInfo = new SbContentInfo();

		String reqPath = req.getRequestURI();
		String nowURl = req.getServletPath();
		String visitIP = IPUtil.getIpAddr(req);// 获取用户IP
		String temp_path = nowURl.substring(nowURl.lastIndexOf("/") + 1, nowURl.length());
		String userAgent = req.getHeader("user-agent");
		String isWeixin = CPSUtil.isWeixin(req);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】isWeixin=" + isWeixin);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】reqPath:" + reqPath);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】temp路径:" + temp_path);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】正在访问URL:" + nowURl);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】访问人IP地址:" + visitIP);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】userAgent=" + userAgent);

		String weixinRead = CPSUtil.getParamValue(Const.PAGE_WECHAT_READ);
		if (CPSUtil.isNotEmpty(weixinRead) && "1".equals(weixinRead)) {
			// 获取当前是不是微信访问
			if (!"1".equals(isWeixin)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】非微信访问跳转到腾讯新闻.....");
				model.addAttribute("target", "https://xw.qq.com");
				return "common/forward";
			}
		}

		if (CPSUtil.isEmpty(params)) {
			return "error/error.jsp";
		}

		// 处理掉微信附加参数
		if (params.contains("&")) {
			params = params.split("&")[0];
		}

		// 处理掉@绕过机制参数
		if (params.contains("=@")) {
			params = params.split("=@")[0] + "=";
		}

		// 处理掉？绕过机制参数
		if (params.contains("=?")) {
			params = params.split("=?")[0] + "=";
		}

		// 处理掉#绕过机制参数
		if (params.contains("=#")) {
			params = params.split("=#")[0] + "=";
		}

		CPSUtil.xprint("处理后的params=" + params);

		// 解密参数
		params = CPSUtil.getParamDecrypt(params);
		String _params[] = params.split("_");
		if (_params.length != 4) {
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】参数错误，非法访问!");
			return "error/error.jsp";
		}

		Integer userId = Integer.valueOf(_params[0]);
		Integer columnId = Integer.valueOf(_params[1]);
		Integer contentId = Integer.valueOf(_params[2]);
		String shareType = String.valueOf(_params[3]);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】userId=" + userId);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】columnId=" + columnId);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】contentId=" + contentId);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】shareType=" + shareType);
		// 改成内存中提取正文
		contentInfo = CPSUtil.getContentInfoById(contentId);
		if (CPSUtil.isEmpty(contentInfo)) {
			return "/error/error.jsp";
		}

		// 处理文章下架的情况
		if (!"1".equals(contentInfo.getCheckState() + "")) {
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【details】文章已经下架跳转到腾讯新闻.....");
			model.addAttribute("target", "https://xw.qq.com");
			return "common/forward";
		}

		// 处理301跳转
		String forwardUrl = null;
		String paramString = null;
		String showPageDomain = null;
		String canForward = CPSUtil.getParamValue(Const.PAGE_DOMAIN_FORWARD);
		if (CPSUtil.checkForwardDomain(nowDomian, ContentDomainType.TZYM) && "1".equals(canForward)) {// 如果是301
																										// 切当前域名是跳转域名
																										// 且开启301
			// 获取推广域名
			showPageDomain = getTGDomian(userId);
			if (CPSUtil.isNotEmpty(showPageDomain)) {
				// 处理跳转URL
				paramString = userId + "_" + columnId + "_" + contentId + "_" + shareType;
				forwardUrl = showPageDomain + "/article/details/" + CPSUtil.getParamEncrypt(paramString);
				model.addAttribute("forwardUrl", forwardUrl);
			}
		} else {// 当前域名是推广域名直接显示内容
			MemberInfo member = CPSUtil.getMemeberById(String.valueOf(userId));
			model.addAttribute("uid", userId);
			model.addAttribute("cid", contentId);
			model.addAttribute("isWeixin", isWeixin);
			model.addAttribute("content", contentInfo);
			model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());

			// 处理扣量机制
			Integer disscount = member.getDisscount();
			if (CPSUtil.isEmpty(disscount)) {
				String userDiss = CPSUtil.getParamValue(Const.CT_USER_DISS_PERCENT);
				if (CPSUtil.isNotEmpty(userDiss)) {
					disscount = Integer.valueOf(userDiss);
				} else {
					disscount = 30;
				}
				CPSUtil.xprint("折扣比例值确实采用默认值 30%");
			}

			// 获取统计代码渠道
			String countMethod = CPSUtil.getParamValue(Const.SITE_COUNT_METHOD);
			if (CPSUtil.isEmpty(countMethod)) {
				countMethod = "cnzz";
			}
			CPSUtil.xprint("代码统计方式：" + countMethod);

			// cnzz
			String acnzzCode = CPSUtil.getParamValue(Const.SITE_CNZZ_COUNT_CODE);
			String bcnzzCode = CPSUtil.getParamValue(Const.SITE_CNZZ_COUNT_BCODE);
			// 百度
			String abaiduCode = CPSUtil.getParamValue(Const.SITE_BAIDU_COUNT_CODE);
			String bbaiduCode = CPSUtil.getParamValue(Const.SITE_BAIDU_COUNT_BCODE);

			CPSUtil.xprint("代码统cnzz-1：" + acnzzCode);
			CPSUtil.xprint("代码统cnzz-2：" + bcnzzCode);
			CPSUtil.xprint("代码统baidu-1：" + abaiduCode);
			CPSUtil.xprint("代码统baidu-2：" + bbaiduCode);

			String qjtjz = "";
			String qjtjj = "";
			String hytj = "";
			if (CPSUtil.isNotEmpty(countMethod) && "baidu".equals(countMethod)) {
				hytj = member.getBaiduCode();
				qjtjz = bbaiduCode;// 真统计
				qjtjj = abaiduCode;// 伪统计
			} else {
				hytj = member.getCnzzCode();
				qjtjz = bcnzzCode;// 真统计
				qjtjj = acnzzCode;// 伪统计
			}

			CPSUtil.xprint("qjtjz===>" + qjtjz);
			model.addAttribute("bcnzzCode", qjtjz);

			CPSUtil.xprint("会员[" + member.getLoginAccount() + "]折扣比例值为： " + disscount + "%");
			boolean showCode = UserContentReadCache.getInstance().canShowPage(member.getMemberId(), disscount);
			if (showCode) {
				CPSUtil.xprint("会员[" + member.getMemberName() + "]折扣成功...");
				model.addAttribute("mcnzzCode", "");
				model.addAttribute("acnzzCode", "");
			} else {
				CPSUtil.xprint("hytj===>" + hytj);
				CPSUtil.xprint("qjtjj===>" + qjtjj);
				model.addAttribute("mcnzzCode", hytj);
				model.addAttribute("acnzzCode", qjtjj);
			}
		}

		// 处理广告跳百度问题
		String articleUrl = "";
		String scriptCnzz = "";
		Integer urlGather = contentInfo.getUrlGather();
		String ggUrl = contentInfo.getArticleUrl();
		CPSUtil.xprint("urlGather=====>>>>" + urlGather);
		CPSUtil.xprint("ggUrl=====>>>>" + ggUrl);
		if (CPSUtil.isNotEmpty(ggUrl) && "1".equals(urlGather + "")) {// 开启采集
			String htmlString = ExtractHtmlUtil.getWechatHtmlSource(ggUrl);
			scriptCnzz = getCNZZScript(htmlString);
			articleUrl = getWechatGGUrl(htmlString);
		} else {
			articleUrl = ggUrl;
		}
		CPSUtil.xprint("广告推广地址=====>>>>" + articleUrl);
		CPSUtil.xprint("广告CNZZ=====>>>>" + scriptCnzz);
		if (articleUrl.startsWith("http")) {
			model.addAttribute("articleUrl", articleUrl);
		} else {
			model.addAttribute("articleUrl", "http://" + articleUrl);
		}

		model.addAttribute("scriptCnzz", scriptCnzz);

		// 处理裂变和nk落地页
		if (contentInfo.getContentType().equals(ContentType.NK.getState())) {
			return "user/jsp/content/nk_content_info";
		} else {
			return "user/jsp/content/lb_content_info";
		}
	}

	/**
	 * 抓取地址
	 * 
	 * @param htmlContent
	 * @return
	 */
	private String getWechatGGUrl(String htmlContent) {
		String urlString = "";
		// CPSUtil.xprint("htmlContent====>>>"+htmlContent);
		if (CPSUtil.isNotEmpty(htmlContent)) {
			String subStr[] = htmlContent.split("linnkHref = ");
			urlString = subStr[1].split("location")[0].replaceAll("\"|;", "").trim();
			CPSUtil.xprint("抓取地址====>>>" + urlString);
		}
		return urlString;
	}

	/**
	 * 抓取cnzz
	 * 
	 * @param htmlContent
	 * @return
	 */
	private String getCNZZScript(String htmlContent) {
		String scriptStr = "";
		// CPSUtil.xprint("htmlContent====>>>"+htmlContent);
		if (CPSUtil.isNotEmpty(htmlContent)) {
			Pattern pattern = Pattern.compile("<script src=\"([^\"]*?)\" language=\"JavaScript\"></script>");
			Matcher matcher = pattern.matcher(htmlContent);
			while (matcher.find()) {
				scriptStr += matcher.group() + " ";
			}
			CPSUtil.xprint("scriptStr====>>>" + scriptStr);
		}
		return scriptStr;
	}

	/**
	 * 跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("detailTj/{params}")
	public String articleTjDetail(Model model, HttpServletRequest req, @PathVariable String params,
			HttpServletResponse response) {
		model.addAttribute("target", "http://news.baidu.com/");
		return "common/forward";
	}

	/**
	 * 文章内容
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("detail/{params}")
	public String articleDetail(Model model, HttpServletRequest req, @PathVariable String params,
			HttpServletResponse response) {
		// http://192.168.2.198:86/article/detail/TnZLcUp2ZXNEMlp1b0haN0h2d0dWODdHb3h0S2NsSzA=
		CPSUtil.xprint("文章访问开始..");
		CPSUtil.xprint("params=" + params);
		String type = req.getParameter("type");
		String openId = null;
		if (type != null) {
			openId = new String(Base64.getDecoder().decode(type));
		}
		String nowDomian = req.getServerName();
		SbContentInfo contentInfo = new SbContentInfo();

		String reqPath = req.getRequestURI();
		String nowURl = req.getServletPath();
		String visitIP = IPUtil.getIpAddr(req);// 获取用户IP
		String temp_path = nowURl.substring(nowURl.lastIndexOf("/") + 1, nowURl.length());
		String userAgent = req.getHeader("user-agent");
		String isWeixin = CPSUtil.isWeixin(req);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】isWeixin=" + isWeixin);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】reqPath:" + reqPath);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】temp路径:" + temp_path);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】正在访问URL:" + nowURl);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】访问人IP地址:" + visitIP);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】userAgent=" + userAgent);

		String weixinRead = CPSUtil.getParamValue(Const.PAGE_WECHAT_READ);
		if (CPSUtil.isNotEmpty(weixinRead) && "1".equals(weixinRead)) {
			// 获取当前是不是微信访问
			if (!"1".equals(isWeixin)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】非微信访问跳转到腾讯新闻.....");
				model.addAttribute("target", "https://xw.qq.com");
				return "common/forward";
			}
		}
		if (isWeixin.equals("1")) {
			String appid = GetPropertiesValue.getValue("Config.properties", "appId_h5");
			String code = req.getParameter("code");
			if (openId == null && code == null && CPSUtil.getParamValue("PEPEAT_LOGIN_CODE").equals("1")) {
				String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "domain");
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】微信访问静默登录....." + nowDomian);
				try {
					String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri="
							+ URLEncoder.encode(
									DOMAIN + "/wx/wechat/wxLogin?params=" + nowDomian + "/article/detail/" + params,
									"UTF-8")
							+ "&response_type=code" + "&scope=snsapi_base" + "&state=STATE#wechat_redirect";
					model.addAttribute("target", url);
					return "common/forward";
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】微信访问登录完成....." + openId);
			model.addAttribute("openId", openId);
		}
		String dist = CPSUtil.getParamValue("REPEAT_DISTRICT");
		if (CPSUtil.isNotEmpty(dist)) {
			String ipCode = JedisUtils.get("CT_IPLocation_Util:" + visitIP);
			if (com.ccnet.core.common.utils.StringUtils.isBlank(ipCode)) {
				IpLocation ipLocation = ipLocationService.getIpLocation(visitIP);
				// ipCode = IPLocationUtil.getRegionIdLocation(visitIP);
				ipCode = ipLocation.getProvinceId() + "";
				JedisUtils.set("CT_IPLocation_Util:" + visitIP, ipLocation.getProvinceId() + "", 6 * 24 * 60 * 60);
			}
			if (CPSUtil.getParamValue("REPEAT_DISTRICT").contains(ipCode)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】新闻地区限制跳转百度.....");
				model.addAttribute("target", "http://news.baidu.com/");
				return "common/forward";
			}
		}
		if (CPSUtil.isEmpty(params)) {
			return "error/error.jsp";
		}

		// 处理掉微信附加参数
		if (params.contains("&")) {
			params = params.split("&")[0];
		}

		// 解密参数
		params = CPSUtil.getParamDecrypt(params);
		String _params[] = params.split("_");
		if (_params.length != 4) {
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】参数错误，非法访问!");
			return "/error/error.jsp";
		}

		Integer userId = Integer.valueOf(_params[0]);
		Integer columnId = Integer.valueOf(_params[1]);
		Integer contentId = Integer.valueOf(_params[2]);
		String shareType = String.valueOf(_params[3]);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】userId=" + userId);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】columnId=" + columnId);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】contentId=" + contentId);
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】shareType=" + shareType);
		// 改成内存中提取正文
		contentInfo = CPSUtil.getContentInfoById(contentId);
		if (contentInfo == null)
			contentInfo = sbContentInfoService.getSbContentInfoByID(contentId);
		if (CPSUtil.isNotEmpty(contentInfo.getContentText()))
			contentInfo.setContentText(contentInfo.getContentText().replaceAll("data-original", "src"));
		if (CPSUtil.isEmpty(contentInfo)) {
			return "/error/error.jsp";
		}

		// 处理文章下架的情况
		if (!"1".equals(contentInfo.getCheckState() + "")) {
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】文章已经下架跳转到腾讯新闻.....");
			model.addAttribute("target", "https://xw.qq.com");
			return "/common/forward";
		}

		// 处理301跳转
		String forwardUrl = null;
		String paramString = null;
		String showPageDomain = null;
		String canForward = CPSUtil.getParamValue(Const.PAGE_DOMAIN_FORWARD);
		if (CPSUtil.checkForwardDomain(nowDomian, ContentDomainType.TZYM) && "1".equals(canForward)) {// 如果是301
			// 获取推广域名
			showPageDomain = getTGDomian(userId);
			if (CPSUtil.isNotEmpty(showPageDomain)) {
				// 处理跳转URL
				paramString = userId + "_" + columnId + "_" + contentId + "_" + shareType;
				forwardUrl = showPageDomain + "/article/detail/" + CPSUtil.getParamEncrypt(paramString);
				model.addAttribute("forwardUrl", forwardUrl);

				if (contentInfo.getContentType().equals(ContentType.Video.getState())) {
					return "/user/jsp/content/video_content_info1";
				} else {
					return "/user/jsp/content/content_info1";
				}
			}

		} else {// 当前域名是推广域名直接显示内容
			// 处理广告
			dealContentAdvertise(model, CPSUtil.getIpAddr(req));
			// 处理访问指纹
			String hashKey = getContentHashKey(req, contentId + "", userId + "", shareType);
			if (CPSUtil.isNotEmpty(hashKey) && !hashKey.equals("0")) {
				model.addAttribute("task_type", "1");
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】task_type=1");
			} else {
				model.addAttribute("task_type", "0");
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】task_type=0");
			}

			// 指纹信息
			String fingerprint = MD5.md5(hashKey);
			String visitToken = UuidUtil.get32UUID();
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】visitToken===>" + visitToken);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】fingerprint===>" + fingerprint);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】contentType=" + contentInfo.getContentType());

			JedisUtils.setFingerCacheValue(visitToken, "page_read_time", CPSUtil.getCurrentTime());// 访问开始
			JedisUtils.setFingerCacheValue(visitToken, "hashKey", hashKey);
			JedisUtils.setFingerCacheValue(visitToken, "iswechat", isWeixin);
			JedisUtils.setFingerCacheValue(visitToken, "fingerprint", MD5.md5(hashKey));
			// TODO 记录指纹
			System.out.println("记录指纹----------------------------------");
			System.out.println("hashKey---" + hashKey);
			System.out.println("fingerprint---" + fingerprint);
			System.out.println("visitToken---" + visitToken);
			System.out.println("记录指纹----------------------------------");
			// 处理高价文
			if (contentInfo.getHomeToped() != null && contentInfo.getHomeToped().toString().equals("2")) {
				// 判断渠道
				Integer _shareType = null;
				if ("singlemessage".equals(shareType)) {
					_shareType = 0;
				}
				if ("timeline".equals(shareType)) {
					_shareType = 1;
				}
				if ("groupchat".equals(shareType)) {
					_shareType = 2;
				}
				// 处理广告费用
				if (contentInfo.getHighPriceAd() != null && com.ccnet.core.common.utils.StringUtils
						.isNotBlank(contentInfo.getHighPriceAd().toString())) {
					SbAdvertInfo ad = adService.getSbAdvertInfoByID(Integer.valueOf(contentInfo.getHighPriceAd()));
					if (CPSUtil.isNotEmpty(ad) && ad.getState().toString().equals("1")) {
						// 重复点击广告不记录
						SbAdvertVisitLog log = new SbAdvertVisitLog();
						log.setAdId(Integer.valueOf(ad.getAdId()));
						log.setHashKey(fingerprint);
						log.setRequestIp(visitIP);
						log.setType(1);
						log = sbAdvertVisitLogService.find(log);
						if (log != null) {
							model.addAttribute("target", ad.getAdLink());
							return "common/forward";
						}
						if (hashKey.equals("0")) {
							model.addAttribute("target", ad.getAdLink());
							return "common/forward";
						}
						SbContentVisitLog visitLog = new SbContentVisitLog();
						Date date = new Date();
						visitLog.setVisitToken(UuidUtil.get32UUID());
						visitLog.setContentId(contentId);
						visitLog.setHashKey(fingerprint);
						visitLog.setRequestIp(visitIP);
						visitLog.setRequestDetail(req.getHeader("User-Agent"));
						visitLog.setUserId(userId);
						visitLog.setShareType(_shareType);
						visitLog.setShareUuid(UuidUtil.get32UUID());// 随机默认值
						visitLog.setVisitTime(date);
						visitLog.setTouchCount(Integer.valueOf(0));
						visitLog.setExpandCount(Integer.valueOf(0));
						visitLog.setCoordNum(Integer.valueOf(0));
						visitLog.setWechatBrowser(Integer.valueOf(0));
						visitLog.setAccountTime(date);// 记账时间
						visitLog.setPageReadTime(date);
						visitLog.setFirstExpandTime(date);
						visitLog.setSecondExpandTime(date);
						visitLog.setOpenId(openId);
						CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】文章计费◆◆◆◆◆◆◆◆◆◆");
						// 此处插入数据库判断是否之前有过记录，如果没有则认为有初次阅读，则记帐
						recodeMoneyService.readRecodeMoney(visitLog);
						UserInfo user = userInfoService.getUserByUserID(ad.getAdUserId());
						if (user != null && user.getMoney() != null && ad.getUnitPrice() != null) {
							// 减掉用户广告费用
							double balance = user.getMoney() - ad.getUnitPrice();
							user.setMoney(balance);
							userInfoService.editUserInfo(user);
							if (balance <= 0) {
								adService.updateAdvertiseStateById(ad.getAdId().toString(), 0);
							}
						}
						if (CPSUtil.isEmpty(ad.getChargeBalance())) {
							adService.updateAdvertiseStateById(ad.getAdId().toString(), StateType.InValid.getState());
							InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
						} else {
							double balance = ad.getChargeBalance() - ad.getUnitPrice();
							ad.setChargeBalance(balance);
							// 余额小于0下架
							if (balance <= 0D) {
								ad.setState(StateType.InValid.getState());
							}
							adService.update(ad, "ad_id");
							InitSystemCache.updateCache(Const.CT_ADVERTISE_LIST);
						}
						SbAdvertVisitLog visitLogAd = new SbAdvertVisitLog();
						visitLogAd.setVisitToken(visitToken);
						visitLogAd.setAdId(ad.getAdId());
						visitLogAd.setContentId(contentId);
						visitLogAd.setType(1);
						visitLogAd.setHashKey(fingerprint);
						visitLogAd.setRequestIp(visitIP);
						visitLogAd.setRequestDetail(req.getHeader("User-Agent"));
						visitLogAd.setUserId(userId);
						visitLogAd.setVisitTime(new Date());
						visitLogAd.setWechatBrowser(Integer.valueOf(isWeixin));
						visitLogAd.setUnitPrice(ad.getUnitPrice());
						sbAdvertVisitLogService.saveSbAdvertVisitLog(visitLogAd);
						SbContentVisitLog visitLo = new SbContentVisitLog();
						visitLo.setVisitToken(visitToken);
						visitLo.setContentId(contentId);
						visitLo.setHashKey(fingerprint);
						visitLo.setRequestIp(visitIP);
						visitLo.setRequestDetail(req.getHeader("User-Agent"));
						visitLo.setUserId(userId);
						visitLo.setShareType(_shareType);
						visitLo.setShareUuid(UuidUtil.get32UUID());// 随机默认值
						visitLo.setVisitTime(new Date());
						visitLo.setTouchCount(10);
						visitLo.setExpandCount(Integer.valueOf(1));
						visitLo.setCoordNum(Integer.valueOf(5));
						visitLo.setWechatBrowser(Integer.valueOf(1));
						Date date1 = new Date();
						visitLo.setAccountTime(date1);// 记账时间
						visitLo.setOpenId(openId);
						visitLo.setExpandName("1");
						visitLo.setPageReadTime(date1);
						visitLo.setFirstExpandTime(date1);
						visitLo.setSecondExpandTime(date1);
						CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】文章计费◆◆◆◆◆◆◆◆◆◆");
						// 此处插入数据库判断是否之前有过记录，如果没有则认为有初次阅读，则记帐
						recodeMoneyService.readRecodeMoney(visitLo);
						/***************************** 第2次记录结束 *******************************/
						// 记账成功修改缓存中阅读记录标志
						JedisUtils.setHashKeyCacheValue(hashKey, "account_money", "1");
						JedisUtils.setHashKeyCacheValue(hashKey, "account_date", CPSUtil.getCurrentTime());
						model.addAttribute("target", ad.getAdLink());
						return "common/forward";
					} else {
						String shoutuUrl = GetPropertiesValue.getValue("ConfigURL.properties", "shoutuUrl");
						if (CPSUtil.isNotEmpty(shoutuUrl)) {
							model.addAttribute("target", shoutuUrl);
							return "common/forward";
						} else {
							CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【detail】广告已经下架跳转到腾讯新闻没有找到收徒地址.....");
							model.addAttribute("target", "https://xw.qq.com");
							return "/common/forward";
						}
					}
				}
			}
			// 处理顶部注册链接
			String ranColor = CPSUtil.getRandomColor();
			MemberInfo memberInfo = CPSUtil.getMemeberById(userId + "");
			if (memberInfo != null && CPSUtil.isNotEmpty(memberInfo.getVisitCode())) {
				model.addAttribute("regUrl", getRecomUrl(memberInfo.getVisitCode()));
			}

			// 小说广告开关
			String closeNovelAdv = CPSUtil.getParamValue(Const.CLOSE_NOVEL_PAGE_ADVERTISE);
			if (CPSUtil.isNotEmpty(closeNovelAdv) && "1".equals(closeNovelAdv)) {// 关闭小说广告
				if (ContentType.Article.getState().equals(contentInfo.getContentType())) {
					closeNovelAdv = "1";
				} else {
					closeNovelAdv = "0";
				}
			}
			closeNovelAdv = "0";
			isWeixin = "1";

			// 小说跳转二维码开关
			String closeBarcode = CPSUtil.getParamValue(Const.CLOSE_NOVEL_FORWARD_BARCODE);
			if (CPSUtil.isNotEmpty(closeBarcode) && "1".equals(closeBarcode)) {// 关闭小说二维码
				if (ContentType.Article.getState().equals(contentInfo.getContentType())) {
					closeBarcode = "1";
				} else {
					closeBarcode = "0";
					// 处理小说二维码跳转
					String articleUrl = contentInfo.getArticleUrl();
					articleUrl = ShortUrlUtil.getShortUrl(articleUrl);
					model.addAttribute("articleUrl", articleUrl);
				}
			}

			// 获取全局参数设置
			String readTime = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_TIME);// 记账开始时间
			if (contentInfo.getContentType().equals(ContentType.Video.getState())) {
				readTime = CPSUtil.getParamValue("CT_ARTICLE_RECORD_TIME_VIEDO");// 记账开始时间
			}
			if (CPSUtil.isEmpty(readTime)) {
				readTime = "10";// 默认10秒
			}

			String touchCount = CPSUtil.getParamValue(Const.CT_ARTICLE_TOUCH_COUNT);// 屏幕滑动次数
			if (CPSUtil.isEmpty(touchCount)) {
				touchCount = "1";// 默认至少滑动1次
			}
			if (contentInfo.getContentText() != null) {
				contentInfo.setContentText(contentInfo.getContentText().replaceAll("\"/upload/download", "\""
						+ GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server") + "/upload/download"));
			}
			model.addAttribute("readTime", readTime);
			model.addAttribute("touchCount", touchCount);
			model.addAttribute("uid", userId);
			model.addAttribute("cid", contentId);
			model.addAttribute("ydgf", RandomNum.getRandIntVal(9));
			model.addAttribute("gzgf", RandomNum.getRandIntVal(22));
			model.addAttribute("fingerprint", fingerprint);
			model.addAttribute("visitToken", visitToken);
			model.addAttribute("expList", getExpandButtons(20, ranColor));
			model.addAttribute("ranColor", ranColor);
			model.addAttribute("adRanColor", CPSUtil.getRandomColor());
			model.addAttribute("isWeixin", isWeixin);
			model.addAttribute("closeNovelAdv", closeNovelAdv);
			model.addAttribute("closeBarcode", closeBarcode);
			model.addAttribute("content", contentInfo);
			model.addAttribute("chatCode", CPSUtil.getRandChatCode());
			model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());

			if (contentInfo.getContentType().equals(ContentType.Video.getState())) {
				readTime = CPSUtil.getParamValue("CT_ARTICLE_RECORD_TIME_VIEDO");// 记账开始时间
				if (StringUtils.isNotBlank(contentInfo.getVideoLink())
						&& contentInfo.getVideoLink().indexOf("http") < 0) {
					String str = new String(NewsServiceImpl.getVideoUrl(contentInfo.getVideoLink()));
					// str =decodeUnicode(str);
					contentInfo.setVideoLink(str);
				}
				List<SbColumnInfo> sblist = new ArrayList<>();
				String adStr = JSON.toJSONString(sblist);// JedisUtils.get("CT_SbAdvertInfo");
				if (com.ccnet.core.common.utils.StringUtils.isBlank(adStr) || adStr.equals("[]")) {
					List<SbContentInfo> tjlist = apiSbContentInfoDao.getSbContentTjInfo(contentId, 10);
					for (int i = 0; i < tjlist.size(); i++) {
						List<SbContentPic> pics = apiSbContentPicDao.getSbContentPic(tjlist.get(i).getContentCode());
						tjlist.get(i).setPicList(pics);
						// 处理跳转URL
						String paramItem = userId + "_" + columnId + "_" + tjlist.get(i).getContentId() + "_"
								+ shareType;
						String forwardUrlItem = "/article/detail/" + CPSUtil.getParamEncrypt(paramItem);
						tjlist.get(i).setArticleUrl(forwardUrlItem);
					}
					model.addAttribute("tjlist", tjlist);
				}
				return "/user/jsp/content/video_content_info1";
			} else {
				int randNum = (int) (1 + Math.random() * (10 - 1 + 1));
				int randMore = (randNum % 3) + 1;
				// 暂时取消随机
				randNum = 3;
				if (randNum % 2 == 0) {
					return "/user/jsp/content/content_info1";
				} else {
					List<SbColumnInfo> sblist = new ArrayList<>();
					String adStr = JSON.toJSONString(sblist);// JedisUtils.get("CT_SbAdvertInfo");
					if (com.ccnet.core.common.utils.StringUtils.isBlank(adStr) || adStr.equals("[]")) {
						List<SbContentInfo> tjlist = apiSbContentInfoDao.getSbContentTjInfo(contentId, 10);
						for (int i = 0; i < tjlist.size(); i++) {
							List<SbContentPic> pics = apiSbContentPicDao
									.getSbContentPic(tjlist.get(i).getContentCode());
							tjlist.get(i).setPicList(pics);
							// 处理跳转URL
							String paramItem = userId + "_" + columnId + "_" + tjlist.get(i).getContentId() + "_"
									+ shareType;
							String forwardUrlItem = "/article/detail/" + CPSUtil.getParamEncrypt(paramItem);
							tjlist.get(i).setArticleUrl(forwardUrlItem);
						}
						model.addAttribute("tjlist", tjlist);
					}
					model.addAttribute("randMore", randMore);
					return "/user/jsp/content/content_info1";
				}
			}
		}

		return null;

	}

	/**
	 * 1秒记录文章开始访问
	 * 
	 * @param session
	 * @param req
	 * @param fingerprint
	 * @param visitToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "heartbeat", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes heartBeat(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			// CPSUtil.xprint("记录心跳开始..");
			Dto paramDto = getParamAsDto();
			String visitToken = paramDto.getAsString("token");
			String fingerprint = paramDto.getAsString("fingerprint");
			String iswechat = paramDto.getAsString("iswechat");
			String uid = paramDto.getAsString("uid");
			String cid = paramDto.getAsString("cid");
			String visitIP = IPUtil.getIpAddr(req);// 获取用户IP

			// CPSUtil.xprint(">>>>>>>>uid="+ uid +" cid="+ cid +"
			// ip="+visitIP+"<<<<<<");
			if (CPSUtil.isEmpty(visitToken)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【heartbeat】visitToken为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			if (CPSUtil.isEmpty(fingerprint)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【heartbeat】fingerprint为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("iswechat", iswechat);
			map.put("visitToken", visitToken);
			map.put("fingerprint", fingerprint);
			map.put("heartTime", CPSUtil.getCurrentTime());
			// 写入心跳缓存
			JedisUtils.setHeartbeatCacheValue(visitToken, map);
			JedisUtils.setFingerCacheValue(visitToken, "request_ip", IPUtil.getIpAddr(req));
			JedisUtils.setFingerCacheValue(visitToken, "user_agent", req.getHeader("User-Agent"));

			ar.setRes(200); // 正常访问

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;
	}

	/**
	 * 1秒记录文章开始访问
	 * 
	 * @param session
	 * @param req
	 * @param fingerprint
	 * @param visitToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "record", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes articleRecord(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		System.out.println("1秒记录开始..+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("1秒记录开始..");
			Dto paramDto = getParamAsDto();
			String visitToken = paramDto.getAsString("token");
			// TODO 删除
			JedisUtils.setFingerCacheValue(visitToken, "record_time", CPSUtil.getCurrentTime());
			String fingerprint = paramDto.getAsString("fingerprint");
			String iswechat = paramDto.getAsString("iswechat");

			if (CPSUtil.isEmpty(visitToken)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【record】visitToken为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 通过token获取缓存
			String hashKey = JedisUtils.getFingerCacheValue(visitToken, "hashKey");
			String page_read_time = JedisUtils.getFingerCacheValue(visitToken, "page_read_time");
			String _fingerprint = JedisUtils.getFingerCacheValue(visitToken, "fingerprint");

			// 比对指纹
			if (!StringUtils.equals(fingerprint, _fingerprint)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆" + fingerprint + "【record】两次指纹匹配错误！！" + _fingerprint + "◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 比对hashKey
			if (CPSUtil.isEmpty(hashKey) || "0".equals(hashKey)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【record】hashKey为0 再次访问退回!!!◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 处理提交参数
			Integer userId = null;
			Integer contentId = null;
			String shareType = null;
			String requestIP = null;
			String requestDetail = null;
			String md5Str = CPSUtil.decryptBasedDes(CPSUtil.decryptBased64(hashKey));
			if (StringUtils.isBlank(md5Str)) {
				return ar;
			}

			String[] strs = StringUtils.split(md5Str, "<+>");
			if (strs.length < 5) {
				CPSUtil.xprint("<<<<<<非法请求：参数个数错误>>>>>>");
				return ar;
			}

			String str = StringUtils.trim(strs[0]);
			if (NumberUtils.isDigits(str)) {
				userId = Integer.valueOf(str);
			}

			str = StringUtils.trim(strs[1]);
			if (NumberUtils.isDigits(str)) {
				contentId = Integer.valueOf(str);
			}

			str = StringUtils.trim(strs[2]);
			if (CPSUtil.isNotEmpty(str)) {
				shareType = str;
			}

			str = StringUtils.trim(strs[3]);
			if (CPSUtil.isNotEmpty(str)) {
				requestIP = str;
			}

			str = StringUtils.trim(strs[4]);
			if (CPSUtil.isNotEmpty(str)) {
				requestDetail = str;
			}

			if (CPSUtil.isEmpty(userId) || CPSUtil.isEmpty(contentId)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【record】提交参数UserID和ContentID不能为空!◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			if (CPSUtil.isEmpty(shareType)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【record】提交参数shareType不能为空!◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 判断渠道
			Integer _shareType = null;
			if ("singlemessage".equals(shareType)) {
				_shareType = 0;
			}
			if ("timeline".equals(shareType)) {
				_shareType = 1;
			}
			if ("groupchat".equals(shareType)) {
				_shareType = 2;
			}

			/***************************** 第一次记录开始 *******************************/
			// 处理文章阅读数
			SbContentInfo content = CPSUtil.getContentInfoById(Integer.valueOf(contentId));
			if (CPSUtil.isEmpty(content)) {
				content = sbContentInfoService.getSbContentInfoByID(contentId);
			}
			if (CPSUtil.isEmpty(content)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆◆◆【record】文章不存在◆◆◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 判断会员存在
			MemberInfo member = CPSUtil.getMemeberById(userId + "");
			if (CPSUtil.isEmpty(member)) {
				member = memberInfoService.getUserByUserID(userId);
			}

			if (CPSUtil.isEmpty(member)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆◆◆【record】会员不存在◆◆◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 处理物理定位
			String lat = paramDto.getAsString("lat");
			String lng = paramDto.getAsString("lng");
			String geoKey = CPSUtil.getParamValue(Const.CT_GEO_API_KEY);
			if (CPSUtil.isNotEmpty(lat) && CPSUtil.isNotEmpty(lng) && CPSUtil.isNotEmpty(geoKey)) {
				GetLatAndLngByMapApi mapApi = new GetLatAndLngByMapApi();
				String addr = mapApi.getAddrByGEO(geoKey, lat, lng);
				CPSUtil.xprint("geoAddr=" + addr);
			}

			// 记录阅读
			sbContentInfoService.updateContentReadNum(contentId, content.getReadNum() + 1);
			// 记录日志
			SbContentVisitLog visitLog = new SbContentVisitLog();
			visitLog.setVisitToken(visitToken);
			visitLog.setContentId(contentId);
			visitLog.setHashKey(fingerprint);
			visitLog.setRequestIp(requestIP);
			visitLog.setRequestDetail(req.getHeader("User-Agent"));
			visitLog.setUserId(userId);
			visitLog.setShareType(_shareType);
			visitLog.setShareUuid(UuidUtil.get32UUID());// 随机给个默认值32位uuid
			visitLog.setVisitTime(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH));
			visitLog.setTouchCount(0);
			visitLog.setExpandCount(0);
			visitLog.setCoordNum(0);
			visitLog.setWechatBrowser(Integer.valueOf(iswechat));
			if (CPSUtil.isNotEmpty(page_read_time)) {
				visitLog.setPageReadTime(DateUtils.getDateTimeFormat(page_read_time));
			} else {
				visitLog.setPageReadTime(new Date());
			}
			visitLog.setAccountState(StateType.InValid.getState());// 未记账
			visitLog.setAccountTime(null);// 记账时间置空
			visitLog.setFirstExpandTime(null);
			visitLog.setSecondExpandTime(null);
			// 此处插入数据库判断是否之前有过记录，如果没有则认为有初次阅读，则记帐
			recodeMoneyService.readRecodeMoney(visitLog);

			// TODO 寻找代码-记录访问日志、记账
			System.out.println("此处插入数据库判断是否之前有过记录，如果没有则认为有初次阅读，则记帐--------++++++++++++++++++++++++++++");
			System.out.println(visitLog.toString());
			/***************************** 第一次记录结束 *******************************/
			// 存储开始访问时间
			JedisUtils.setFingerCacheValue(visitToken, "iswechat", iswechat);
			JedisUtils.setFingerCacheValue(visitToken, "request_ip", IPUtil.getIpAddr(req));
			JedisUtils.setFingerCacheValue(visitToken, "user_agent", req.getHeader("User-Agent"));
			JedisUtils.setFingerCacheValue(visitToken, "record_time", CPSUtil.getCurrentTime());

			ar.setRes(200); // 正常访问

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * 1秒记录文章开始访问
	 * 
	 * @param session
	 * @param req
	 * @param fingerprint
	 * @param visitToken
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "geo/location", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes getGeoLocation(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("获取物理定位录开始..");
			Dto paramDto = getParamAsDto();
			String visitToken = paramDto.getAsString("token");
			String lat = paramDto.getAsString("lat");
			String lng = paramDto.getAsString("lng");

			if (CPSUtil.isEmpty(visitToken) && CPSUtil.isNotEmpty(lat) && CPSUtil.isNotEmpty(lng)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【geo】参数错误，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 处理物理定位
			String geoKey = CPSUtil.getParamValue(Const.CT_GEO_API_KEY);
			if (CPSUtil.isNotEmpty(geoKey)) {
				GetLatAndLngByMapApi mapApi = new GetLatAndLngByMapApi();
				String addr = mapApi.getAddrByGEO(geoKey, lat, lng);
				CPSUtil.xprint("geoAddr=" + addr);
			}

			ar.setRes(200); // 正常访问

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * 文章点开记录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "unwind", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes contentUnwind(HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		Dto paramDto = getParamAsDto();
		try {
			CPSUtil.xprint("点击了展开..");
			String visitToken = paramDto.getAsString("token");
			String iswechat = paramDto.getAsString("iswechat");
			String randMore = paramDto.getAsString("randMore");
			if (CPSUtil.isEmpty(visitToken)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【unwind】visitToken 为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 获取参数
			String unwind = paramDto.getAsString("unwind");
			String index = paramDto.getAsString("index");
			if (CPSUtil.isNotEmpty(unwind) && "iaws".equals(unwind) && CPSUtil.isNotEmpty(index)) {
				CPSUtil.xprint("第【" + index + "】次展开..");
				// 记录点首次展开时间
				if ("1".equals(index)) {
					JedisUtils.setFingerCacheValue(visitToken, "first_unwind_time", CPSUtil.getCurrentTime());
					JedisUtils.setFingerCacheValue(visitToken, "first_unwind_randMore", randMore);
				}
				// 继续阅读展开时间
				if ("2".equals(index)) {
					JedisUtils.setFingerCacheValue(visitToken, "second_unwind_time", CPSUtil.getCurrentTime());
				}
				// 记录最终index的值
				JedisUtils.setFingerCacheValue(visitToken, "iaws_index", index);
			}

			ar.setRes(200);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * 移动设备移动记录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "gravity", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes deviceGravity(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		Dto paramDto = getParamAsDto();
		try {

			CPSUtil.xprint("设备移动开始..");

			String unwind = paramDto.getAsString("unwind");
			String mun_index = paramDto.getAsString("mun_index");
			String x = paramDto.getAsString("x");
			String y = paramDto.getAsString("y");
			String z = paramDto.getAsString("z");
			String visitToken = paramDto.getAsString("token");

			if (CPSUtil.isEmpty(visitToken)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【gravity】visitToken 为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 判断参数
			if (StringHelper.checkParameter(mun_index, x, y, z)) {
				// 保存每次设备晃动记录
				double x_coord = Double.valueOf(x);
				double y_coord = Double.valueOf(y);
				double z_coord = Double.valueOf(z);
				// 将三个方向的坐标值叠加
				double coord_val = x_coord + y_coord + z_coord;
				CPSUtil.xprint("coord_val=" + coord_val);

				JedisUtils.setFingerCacheValue(visitToken, unwind + "_coord_num", mun_index);
				JedisUtils.setFingerCacheValue(visitToken, unwind + "_coord_val_" + mun_index, coord_val + "");
				JedisUtils.setFingerCacheValue(visitToken, unwind + "_coord_detail_" + mun_index,
						" x:" + x + " y:" + y + " z:" + z);

				ar.setRes(200);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * 记录文章滑动轨迹
	 * 
	 * @param begin_time
	 * @param stop_time
	 * @param begin_points
	 * @param end_points
	 * @param points_diff
	 * @param touchs
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "view_touch", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes articleViewTouch(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("滑动轨迹开始..");
			Dto paramDto = getParamAsDto();
			String visitToken = paramDto.getAsString("token");
			String begin_time = paramDto.getAsString("begin_time");
			String stop_time = paramDto.getAsString("stop_time");
			String begin_points = paramDto.getAsString("begin_points");
			String end_points = paramDto.getAsString("end_points");
			String points_diff = paramDto.getAsString("points_diff");
			String touchs = paramDto.getAsString("touchs");

			if (CPSUtil.isEmpty(visitToken)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【view_touch】visitToken 为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 判断不是在同一个点
			if (StringUtils.equals(begin_points, end_points)) {
				ar.setRes(0); // 不正常访问
			} else {
				// 记录滑动次数
				JedisUtils.setFingerCacheValue(visitToken, "touch_count", touchs);
				JedisUtils.setFingerCacheValue(visitToken, "begin_time", begin_time);
				JedisUtils.setFingerCacheValue(visitToken, "stop_time", stop_time);
				JedisUtils.setFingerCacheValue(visitToken, "begin_points", begin_points);
				JedisUtils.setFingerCacheValue(visitToken, "end_points", end_points);
				JedisUtils.setFingerCacheValue(visitToken, "points_diff", points_diff);
				ar.setRes(200); // 正常访问
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * 文章记帐
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "accounts", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes articleAccounts(HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		System.out.println("文章开始记账====================================================================");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("【accounts】文章记账开始..");
			Dto paramDto = getParamAsDto();
			String visitToken = paramDto.getAsString("token");
			String fingerprint = paramDto.getAsString("fingerprint");
			String openId = paramDto.getAsString("openId");
			String iswechat = CPSUtil.isWeixin(req);// 是否微信访问
			String contentType = paramDto.getAsString("contentType");

			CPSUtil.xprint("【accounts】visitToken=>>>>" + visitToken);
			CPSUtil.xprint("【accounts】fingerprint=>>>>" + fingerprint);

			// 滑动次数
			String touch_count = JedisUtils.getFingerCacheValue(visitToken, "touch_count");
			touch_count = CPSUtil.isEmpty(touch_count) ? "0" : touch_count;
			// 设备晃动次数
			String iaws_coord_num = JedisUtils.getFingerCacheValue(visitToken, "iaws_coord_num");
			iaws_coord_num = CPSUtil.isEmpty(iaws_coord_num) ? "0" : iaws_coord_num;

			String iaws_coord_val = JedisUtils.getFingerCacheValue(visitToken, "iaws_coord_num");// 最后一次设备晃动的x+y+z值
			String iaws_coord_detail = JedisUtils.getFingerCacheValue(visitToken, "iaws_coord_detail");// 最后一次设备晃动的详细记录
			// 展开次数
			String iaws_index = JedisUtils.getFingerCacheValue(visitToken, "iaws_index");
			iaws_index = CPSUtil.isEmpty(iaws_index) ? "0" : iaws_index;

			String first_unwind_time = JedisUtils.getFingerCacheValue(visitToken, "first_unwind_time");// 首次展开时间
			String second_unwind_time = JedisUtils.getFingerCacheValue(visitToken, "second_unwind_time");// 继续阅读时间
			String page_read_time = JedisUtils.getFingerCacheValue(visitToken, "page_read_time");// 页面访问开始时间
			String record_time = JedisUtils.getFingerCacheValue(visitToken, "record_time");// 1秒记录时间
			String first_unwind_randMore = JedisUtils.getFingerCacheValue(visitToken, "first_unwind_randMore");// 展开按钮

			String _fingerprint = JedisUtils.getFingerCacheValue(visitToken, "fingerprint");
			String hashKey = JedisUtils.getFingerCacheValue(visitToken, "hashKey");

			// 记账开始时间
			String readTime = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_TIME);
			readTime = CPSUtil.isEmpty(readTime) ? "0" : readTime;
			Integer _readTime = Integer.valueOf(readTime);
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】contentType 为" + contentType + "！！！◆◆◆◆◆◆◆◆◆◆");
			if (CPSUtil.isNotEmpty(contentType) && contentType.equals("1")) {
				readTime = CPSUtil.getParamValue("CT_ARTICLE_RECORD_TIME_VIEDO");
				readTime = CPSUtil.isEmpty(readTime) ? "0" : readTime;
				_readTime = Integer.valueOf(readTime);
			}
			// 屏幕滑动次数
			String touchCount = CPSUtil.getParamValue(Const.CT_ARTICLE_TOUCH_COUNT);
			touchCount = CPSUtil.isEmpty(touchCount) ? "0" : touchCount;
			Integer _touchCount = Integer.valueOf(touchCount);
			// 屏幕滑动次数
			String touchCountMax = CPSUtil.getParamValue(Const.CT_ARTICLE_TOUCH_COUNT);
			touchCount = CPSUtil.isEmpty(touchCountMax) ? "0" : touchCountMax;
			Integer _touchCountMax = Integer.valueOf(touchCountMax);
			// 点击展开次数
			String expandCount = CPSUtil.getParamValue(Const.CT_ARTICLE_EXPAND_COUNT);
			expandCount = CPSUtil.isEmpty(expandCount) ? "0" : expandCount;
			Integer _expandCount = Integer.valueOf(expandCount);
			// 展开间隔时间
			String expandTime = CPSUtil.getParamValue(Const.CT_ARTICLE_EXPAND_TIME);
			expandTime = CPSUtil.isEmpty(expandTime) ? "0" : expandTime;
			Integer _expandTime = Integer.valueOf(expandTime);
			// 设备晃动次数
			String moveCount = CPSUtil.getParamValue(Const.CT_PHONE_MOVE_COUNT);
			moveCount = CPSUtil.isEmpty(moveCount) ? "0" : moveCount;
			Integer _moveCount = Integer.valueOf(moveCount);

			if (CPSUtil.isEmpty(visitToken)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】visitToken 为空，非法请求！！！◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 比对指纹
			if (!StringUtils.equals(fingerprint, _fingerprint)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】两次指纹匹配错误！！◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 比对hashKey
			if (CPSUtil.isEmpty(hashKey) || "0".equals(hashKey)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】hashKey为0 再次访问退回!!!◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 判断时间
			if (CPSUtil.isEmpty(record_time)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】record_time 为空无效访问!!!◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 处理提交参数
			Integer userId = null;
			Integer contentId = null;
			String shareType = null;
			String requestIP = null;
			Integer _shareType = null;
			String requestDetail = null;
			String md5Str = CPSUtil.decryptBasedDes(CPSUtil.decryptBased64(hashKey));
			if (StringUtils.isBlank(md5Str)) {
				return ar;
			}

			String[] strs = StringUtils.split(md5Str, "<+>");
			if (strs.length < 5) {
				return ar;
			}

			String str = StringUtils.trim(strs[0]);
			if (NumberUtils.isDigits(str)) {
				userId = Integer.valueOf(str);
			}

			str = StringUtils.trim(strs[1]);
			if (NumberUtils.isDigits(str)) {
				contentId = Integer.valueOf(str);
			}

			str = StringUtils.trim(strs[2]);
			if (CPSUtil.isNotEmpty(str)) {
				shareType = str;
			}

			str = StringUtils.trim(strs[3]);
			if (CPSUtil.isNotEmpty(str)) {
				requestIP = str;
			}

			str = StringUtils.trim(strs[4]);
			if (CPSUtil.isNotEmpty(str)) {
				requestDetail = str;
			}

			// 判断渠道
			if ("singlemessage".equals(shareType)) {
				_shareType = 0;
			}
			if ("timeline".equals(shareType)) {
				_shareType = 1;
			}
			if ("groupchat".equals(shareType)) {
				_shareType = 2;
			}

			if (CPSUtil.isEmpty(userId) || CPSUtil.isEmpty(contentId)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】提交参数UserID和ContentID不能为空!◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			if (CPSUtil.isEmpty(shareType)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】提交参数shareType不能为空!◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 处理文章阅读数
			SbContentInfo content = CPSUtil.getContentInfoById(Integer.valueOf(contentId));
			if (CPSUtil.isEmpty(content)) {
				content = sbContentInfoService.getSbContentInfoByID(contentId);
			}
			if (CPSUtil.isEmpty(content)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】文章不存在◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 判断会员存在
			MemberInfo member = memberInfoService.getUserByUserID(userId);
			if (CPSUtil.isEmpty(member)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】会员不存在◆◆◆◆◆◆◆◆◆◆");
				ar.setRes(200);
				return ar;
			}

			// 判断1秒开始记录的时间和页面访问时间间隔>=1秒
			if (CPSUtil.getSubSecond(page_read_time, record_time) < 1) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】开始记录的时间和页面访问时间间隔>=1秒！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 判断页面开始访问到记账的时间间隔
			if (CPSUtil.getSubSecond(page_read_time, CPSUtil.getCurrentTime()) < _readTime) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】页面开始访问到记账的时间间隔要>=" + _readTime + "秒！！◆◆◆◆◆◆◆◆◆◆");
				return ar;
			}

			// 判断滑动次数
			if (_touchCount > 0) {
				if (CPSUtil.isEmpty(touch_count) || Integer.valueOf(touch_count) < _touchCount) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】滑动次数必须>=" + _touchCount + "次！！◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}
				String maxtouchCount = CPSUtil.getParamValue("CT_ARTICLE_TOUCH_COUNT_MAX");
				if (CPSUtil.isEmpty(maxtouchCount) || Integer.valueOf(touch_count) > Integer.valueOf(maxtouchCount)) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】滑动次数必须<=" + _touchCount + "次！！◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}
			}

			// 判断晃动次数
			if (_moveCount > 0) {
				if (CPSUtil.isEmpty(iaws_coord_num) || Integer.valueOf(iaws_coord_num) < _moveCount) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】晃动次数必须>=" + _moveCount + "次 ！！◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}
			}

			// 判断展开次数
			if (_expandCount == 2) {
				if (Integer.valueOf(iaws_index) != _expandCount) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】文章展开阅读点击次数必须=" + _expandCount + "次◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}

				if (CPSUtil.isEmpty(first_unwind_time) || CPSUtil.isEmpty(second_unwind_time)) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】两次点击展开时间不能为空！◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}

				if (CPSUtil.getSubSecond(first_unwind_time, second_unwind_time) < _expandTime) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】两次点击展开时间间隔必须>=" + _expandTime + "秒！！◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}
			}

			if (_expandCount == 1) {
				// 判断次数
				if (CPSUtil.isEmpty(iaws_index) || Integer.valueOf(iaws_index) < _expandCount) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】文章展开阅读点击次数>=" + _expandCount + "次◆◆◆◆◆◆◆◆◆◆");
					return ar;
				}
			}

			/***************************** 第2次记录开始 *******************************/
			SbContentVisitLog visitLog = new SbContentVisitLog();
			visitLog.setVisitToken(visitToken);
			visitLog.setContentId(contentId);
			visitLog.setHashKey(fingerprint);
			visitLog.setRequestIp(requestIP);
			visitLog.setRequestDetail(req.getHeader("User-Agent"));
			visitLog.setUserId(userId);
			visitLog.setShareType(_shareType);
			visitLog.setShareUuid(UuidUtil.get32UUID());// 随机默认值
			visitLog.setVisitTime(new Date());
			visitLog.setTouchCount(Integer.valueOf(touch_count));
			visitLog.setExpandCount(Integer.valueOf(iaws_index));
			visitLog.setCoordNum(Integer.valueOf(iaws_coord_num));
			visitLog.setWechatBrowser(Integer.valueOf(iswechat));
			visitLog.setAccountTime(new Date());// 记账时间
			visitLog.setOpenId(openId);
			visitLog.setExpandName(first_unwind_randMore);
			if (CPSUtil.isNotEmpty(page_read_time)) {
				visitLog.setPageReadTime(DateUtils.getDateTimeFormat(page_read_time));
			}
			if (CPSUtil.isNotEmpty(first_unwind_time)) {
				visitLog.setFirstExpandTime(DateUtils.getDateTimeFormat(first_unwind_time));
			}
			if (CPSUtil.isNotEmpty(second_unwind_time)) {
				visitLog.setSecondExpandTime(DateUtils.getDateTimeFormat(second_unwind_time));
			}
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【accounts】文章计费◆◆◆◆◆◆◆◆◆◆");
			// 此处插入数据库判断是否之前有过记录，如果没有则认为有初次阅读，则记帐
			recodeMoneyService.readRecodeMoney(visitLog);
			/***************************** 第2次记录结束 *******************************/
			// 记账成功修改缓存中阅读记录标志
			JedisUtils.setHashKeyCacheValue(hashKey, "account_money", "1");
			JedisUtils.setHashKeyCacheValue(hashKey, "account_date", CPSUtil.getCurrentTime());

			ar.setRes(200);

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("文章结束始记账====================================================================");
		return ar;
	}

	/**
	 * 用户点赞
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "comment", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes articleComment(HttpSession session, HttpServletRequest req, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("用户点赞..");
			Dto paramDto = getParamAsDto();
			String uid = paramDto.getAsString("uid");
			String cid = paramDto.getAsString("cid");
			Integer type = paramDto.getAsInteger("type");
			String commentIp = IPUtil.getIpAddr(req);// 获取用户IP
			// 判断参数
			if (!StringHelper.checkParameter(uid, cid, type)) {
				return ar;
			}
			// 判断用户是否已经点赞
			SbCommentLog commentLog = new SbCommentLog();
			commentLog.setContentId(Integer.valueOf(cid));
			// commentLog.setUserId(Integer.valueOf(uid));
			commentLog.setCommentIp(commentIp);
			commentLog = commentLogService.findSbCommentLog(commentLog);
			if (CPSUtil.isNotEmpty(commentLog) && CPSUtil.isNotEmpty(commentLog.getCommentType())) {
				return ar;
			}

			// 处理点赞
			SbContentComment comment = new SbContentComment();
			comment.setZjCount(type.equals(CommentType.zj.getTypeId()) ? 1 : 0);
			comment.setDzCount(type.equals(CommentType.dz.getTypeId()) ? 1 : 0);
			comment.setGxCount(type.equals(CommentType.gx.getTypeId()) ? 1 : 0);
			comment.setWyCount(type.equals(CommentType.wy.getTypeId()) ? 1 : 0);
			comment.setBsCount(type.equals(CommentType.bs.getTypeId()) ? 1 : 0);
			comment.setContentId(Integer.valueOf(cid));
			if (contentCommentService.insertOrUpdate(comment)) {
				// 保存日志信息
				SbCommentLog _commentLog = new SbCommentLog();
				_commentLog.setContentId(Integer.valueOf(cid));
				_commentLog.setUserId(Integer.valueOf(uid));
				_commentLog.setCommentIp(commentIp);
				_commentLog.setCommentType(Integer.valueOf(type));
				_commentLog.setCreateTime(new Date());
				if (commentLogService.saveSbCommentLog(_commentLog)) {
					// 更新文章数据
					InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
					ar.setRes(200);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ar;

	}

	/**
	 * 处理广告信息
	 * 
	 * @param model
	 */
	public void dealContentAdvertise(Model model, String ip) {
		String ipCode = JedisUtils.get("CT_IPLocation_Util:" + ip);
		if (com.ccnet.core.common.utils.StringUtils.isBlank(ipCode)) {
			ipCode = IPLocationUtil.getRegionIdLocation(ip);
			JedisUtils.set("CT_IPLocation_Util:" + ip, ipCode, 6 * 24 * 60 * 60);
		}
		SbAdvertInfo tempAd = new SbAdvertInfo();
		tempAd.setState(1);

		// String adStr = JedisUtils.get("CT_SbAdvertInfo");
		// if (com.ccnet.core.common.utils.StringUtils.isBlank(adStr)) {
		// allAd = adService.findList(tempAd);
		// JedisUtils.set("CT_SbAdvertInfo", JSON.toJSONString(allAd), 24 * 60 *
		// 60);
		// } else {
		// allAd = JSON.parseArray(adStr, SbAdvertInfo.class);
		// }
		List<SbAdvertInfo> allAd = adService.findSbAdvertiseInfos();
		CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆【allAd】广告数不存在◆◆◆◆◆◆◆◆◆◆");
		List<SbAdvertInfo> listAd = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> topAd = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> floatAd = new ArrayList<SbAdvertInfo>();
		List<SbAdvertInfo> textAd = new ArrayList<SbAdvertInfo>();
		Integer showState = 0;
		Integer advType = null;
		for (SbAdvertInfo adv : allAd) {
			if (adv.getDistrictIds() != null && adv.getDistrictIds().indexOf(ipCode) >= 0) {
				continue;
			}
			showState = adv.getShowState();
			advType = adv.getAdType();
			adv.setAdTagName(CPSUtil.getCodeName("ADV_TAG", adv.getAdTag()));
			if (advType.equals(AdType.ADTYPE_TEXT.getType()) && showState == 1) {
				textAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_ONLY_SINGLE_PIC.getType()) && showState == 1) {
				listAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_SINGLE_PIC.getType()) && showState == 1) {
				listAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_MORE_PIC.getType()) && showState == 1) {
				listAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_BANNER_PIC.getType()) && showState == 1) {
				topAd.add(adv);
			}

			if (advType.equals(AdType.ADTYPE_FLOAT_PIC.getType()) && showState == 1) {
				floatAd.add(adv);
			}
		}

		// 横幅广告
		List<SbAdvertInfo> banner_adverts = topAd;
		model.addAttribute("banner_adverts", banner_adverts);

		// 顶部随机单图
		List<SbAdvertInfo> tempAdvs = new ArrayList<SbAdvertInfo>();

		if (listAd.size() > 1) {
			model.addAttribute("single_pic_random_adverts", listAd.get(0));
			listAd.remove(0);
		}

		// 顶部随机单图
		List<SbAdvertInfo> tempAdvs2 = new ArrayList<SbAdvertInfo>();
		for (int i = 0; i < topAd.size(); i++) {
			tempAdvs2.add(topAd.get(i));
		}
		tempAdvs2 = CPSUtil.getRandomAdverts(tempAdvs2, 1);
		SbAdvertInfo single_pic_random_Adverts2 = null;
		if (CPSUtil.listNotEmpty(tempAdvs2)) {
			single_pic_random_Adverts2 = tempAdvs2.get(0);
			model.addAttribute("single_pic_random_adverts2", single_pic_random_Adverts2);
		}
		// 浮动广告
		List<SbAdvertInfo> tempAdvsFloat = floatAd;
		SbAdvertInfo floatAdvert = null;
		SbAdvertInfo floatBottom = null;
		tempAdvs = CPSUtil.getRandomAdverts(tempAdvsFloat, 2);
		if (CPSUtil.listNotEmpty(tempAdvs)) {
			floatAdvert = tempAdvs.get(0);
			model.addAttribute("float_advert", floatAdvert);
			if (tempAdvs.size() > 1) {
				floatBottom = tempAdvs.get(1);
				model.addAttribute("float_bottom", floatBottom);
			}
		}
		// 顶部随机纯文本
		List<SbAdvertInfo> tempAdvsText = textAd;
		List<SbAdvertInfo> text_random_Adverts = CPSUtil.getRandomAdverts(tempAdvsText, 3);
		model.addAttribute("text_random_adverts", text_random_Adverts);

		model.addAttribute("adverts", listAd);
	}

	@RequestMapping(value = "getTestJs")
	@ResponseBody
	public String getTestJs() {

		Map<String, Object> model = new HashMap<>();
		model.put("baseUrl", "");
		String html = "";
		html = StringEscapeUtils.escapeJavaScript(html);
		StringBuffer js = new StringBuffer();
		js.append("document.writeln(\'" + html + "\')");

		return js.toString();
	}

	/**
	 * 批量获取推广链接
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "url/getMoreUrl", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public AjaxRes getMoreUrl(HttpSession session, HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		AjaxRes ar = getAjaxRes();
		try {
			CPSUtil.xprint("批量获取链接开始..");
			Dto paramDto = getParamAsDto();
			MemberInfo memberInfo = getCurUser();
			Integer memberId = memberInfo.getMemberId();
			String columnId = paramDto.getAsString("c");
			SbContentInfo contentInfo = new SbContentInfo();
			contentInfo.setCheckState(StateType.Valid.getState());
			if (CPSUtil.isNotEmpty(columnId)) {
				contentInfo.setColumnId(Integer.valueOf(columnId));
			}

			Dto contentDto = null;
			String contentUrl = null;
			String contentDomain = null;
			String paramString = "";
			List<Dto> _contentList = new ArrayList<>();
			List<SbContentInfo> contentList = sbContentInfoService.getRandContentList(contentInfo, 10);
			if (CPSUtil.listNotEmpty(contentList)) {
				for (SbContentInfo content : contentList) {
					contentDto = new BaseDto();
					contentDomain = getTZDomian(memberId);
					paramString = memberId + "_" + content.getColumnId() + "_" + content.getContentId();
					String groupParam = paramString + "_groupchat";

					contentDto.put("title", content.getContentTitle());
					contentUrl = contentDomain + "/article/detail/" + CPSUtil.getParamEncrypt(groupParam);
					contentDto.put("url", ShortUrlUtil.getShortUrl(contentUrl));
					_contentList.add(contentDto);
				}
			}

			ar.setRes(200);
			ar.setObj(_contentList);

		} catch (Exception e) {
			ar.setRes(0);
			e.printStackTrace();
		}
		return ar;

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

	public static String decodeUnicode(final String dataStr) {
		String[] asciis = dataStr.split("\\\\u");
		String nativeValue = asciis[0];
		try {
			for (int i = 1; i < asciis.length; i++) {
				String code = asciis[i];
				nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
				if (code.length() > 4) {
					nativeValue += code.substring(4, code.length());
				}
			}
		} catch (NumberFormatException e) {
			return dataStr;
		}
		return nativeValue;
	}
}
