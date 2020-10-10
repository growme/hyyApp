package com.ccnet.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.core.common.ContentDomainType;
import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.PassMethodType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.WeightType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.html.ShortUrlUtil;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SbForwardLink;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.jpz.entity.JpUserCollect;
import com.ccnet.jpz.service.JpUserCollectService;
import com.alibaba.fastjson.JSON;
import com.ccnet.api.dao.ApiSbContentInfoDao;
import com.ccnet.api.dao.ApiSbContentPic;

/**
 * 文章相关api
 */
@Controller
@RequestMapping("/api/article/")
public class ApiArticleController extends BaseController<MemberInfo> {

	@Autowired
	private SbContentInfoService sbContentInfoService;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	ApiSbContentInfoDao apiSbContentInfoDao;
	@Autowired
	SbColumnInfoService sbColumnInfoService;
	@Autowired
	ApiSbContentPic apiSbContentPicDao;
	@Autowired
	JpUserCollectService jpUserCollectService;

	private final String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server");

	/**
	 * 文章分类数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "column", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getColumnInfo() {
		try {
			SbColumnInfo arg0 = new SbColumnInfo();
			arg0.setEnabled(1);
			arg0.setColumnType(0);
			List<SbColumnInfo> columns = sbColumnInfoService.getColumnInfoList(arg0);
			return ResultDTO.OK(columns);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 文章分页数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getContentInfo() {
		try {
			CPSUtil.xprint("请求开始：---==" + new Date().getTime());
			Dto dto = getParamAsDto();
			Page<SbContentInfo> contents = newPage(dto);
			contents.setPageNum(dto.getAsInteger("page"));
			contents.setPageSize(dto.getAsInteger("pageSize"));
			SbContentInfo sbContentInfo = new SbContentInfo();
			sbContentInfo.setColumnId(dto.getAsInteger("columnId"));
			// 只显示审核通过
			sbContentInfo.setCheckState(StateType.Valid.getState());
			sbContentInfo.setContentType(0);
			List<SbContentInfo> list = new ArrayList<>();
			String InfoStr = null;// JedisUtils.get("ARTICLE_LIST" + ":TYPE" + 0
									// +
									// "_ColumnId_" +
									// sbContentInfo.getColumnId()+"_page_"+contents.getPageNum());
			if (com.ccnet.core.common.utils.StringUtils.isNotEmpty(InfoStr)) {
				list = JSON.parseArray(InfoStr, SbContentInfo.class);
				contents.setResults(list);
				return ResultDTO.OK(contents);
			}
			// 获取文章列表
			if (dto.getAsInteger("page") == 1) {
				sbContentInfo.setHomeToped(WeightType.TOP.getType());
				List<SbContentInfo> con = TopArticle(sbContentInfo);
				for (int i = 0; i < con.size(); i++) {
					list.add(con.get((int) Math.random() * con.size()));
				}
			}
			if (dto.getAsInteger("page") == 1) {
				sbContentInfo.setHomeToped(WeightType.HITHPRICE.getType());
				List<SbContentInfo> con = TopArticle(sbContentInfo);
				for (int i = 0; i < 2 && i < con.size(); i++) {
					int xxx = (int) Math.round(Math.random() * (con.size() - 1));
					list.add(con.get(xxx));
					con.remove(xxx);
				}
			}
			// List<SbContentInfo> list1 =
			// apiSbContentInfoDao.finfSbContentInfoRand(sbContentInfo);
			sbContentInfo.setHomeToped(WeightType.COMMON.getType());
			if (sbContentInfo.getColumnId() == 1) {
				sbContentInfo.setColumnId(null);
			}
			List<SbContentInfo> list1 = pulldownArticle(sbContentInfo,contents);
			int index = list.size();
			for (int i = 0; i < 10 - index && 0 < list1.size(); i++) {
				int xxx = (int) Math.round(Math.random() * (list1.size() - 1));
				list.add(list1.get(xxx));
				list1.remove(xxx);
			}
			// List<SbContentInfo> list = contents.getResults();
			String str = CPSUtil.getParamValue("REPEAT_MONEY");
			Double bou = Double.valueOf(str);
			if (list != null)
				for (int i = 0; i < list.size(); i++) {
					List<SbContentPic> picList = new ArrayList<SbContentPic>();
					if (list.get(i).getContentPics() != null) {
						String[] strArr = list.get(i).getContentPics().split(",");
						for (int z = 0; z < strArr.length; z++) {
							SbContentPic pic = new SbContentPic();
							if (strArr[z].indexOf("http") < 0) {
								pic.setContentPic(DOMAIN + strArr[z]);
							} else {
								pic.setContentPic(strArr[z]);
							}
							picList.add(pic);
						}
					}
					list.get(i).setPicList(picList);
					if (list.get(i).getContentText() != null) {
						list.get(i).setContentText("");
					}
					if (str != null && !str.equals(0) && list.get(i).getHomeToped() == 0) {
						list.get(i).setReadAward(bou);
						list.get(i).setFriendShareAward(bou);
						list.get(i).setTimelineShareAward(bou);
					}
				}
			JedisUtils.set("ARTICLE_LIST" + ":TYPE" + 0 + "_ColumnId_" + sbContentInfo.getColumnId() + "_page_"
					+ contents.getPageNum(), JSON.toJSONString(list), 30 * 60);
			contents.setResults(list);
			CPSUtil.xprint("请求结束：---==" + new Date().getTime());
			return ResultDTO.OK(contents);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/**
	 * 文章推荐数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tjList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getTjContentInfo() {
		Dto dto = getParamAsDto();
		List<SbContentInfo> result = apiSbContentInfoDao.getSbContentTjInfo(dto.getAsInteger("cid"),
				dto.getAsInteger("limit"));
		String str = CPSUtil.getParamValue("REPEAT_MONEY");
		Double bou = Double.valueOf(str);
		for (int i = 0; i < result.size(); i++) {
			List<SbContentPic> pics = apiSbContentPicDao.getSbContentPic(result.get(i).getContentCode());
			result.get(i).setPicList(pics);
			result.get(i).setContentText("");
			if (result.get(i).getPicList() != null)
				for (int z = 0; z < result.get(i).getPicList().size(); z++) {
					if (result.get(i).getPicList().get(z).getContentPic() != null
							&& result.get(i).getPicList().get(z).getContentPic().indexOf("http") < 0) {
						result.get(i).getPicList().get(z)
								.setContentPic(DOMAIN + result.get(i).getPicList().get(z).getContentPic());
					}
				}
			if (result.get(i).getContentText() != null) {
				result.get(i).setContentText(result.get(i).getContentText().replaceAll("\"/upload/download",
						"\"" + DOMAIN + "/upload/download"));
			}
			if (str != null && !str.equals(0) && result.get(i).getHomeToped() == 0) {
				result.get(i).setReadAward(bou);
				result.get(i).setFriendShareAward(bou);
				result.get(i).setTimelineShareAward(bou);
			}
		}
		return ResultDTO.OK(result);
	}

	/**
	 * 文章查看
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "detailusern", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> detailusern(Model model, HttpServletRequest req) throws UnsupportedEncodingException {
		Dto dto = getParamAsDto();
		// 当前用户
		Integer contentId = dto.getAsInteger("cid");
		SbContentInfo contentInfo = new SbContentInfo();
		// 改成内存中提取正文
		contentInfo = CPSUtil.getContentInfoById(contentId);
		if (contentInfo == null || contentInfo.getContentId() == null
				|| contentInfo.getContentId().toString().equals("0")) {
			contentInfo = sbContentInfoService.getSbContentInfoByID(contentId);
		}
		if (CPSUtil.isNotEmpty(contentInfo)) {
			// 文章类型
			if (contentInfo.getPicList() != null && contentInfo.getPicList().size() > 0) {
				for (int z = 0; z < contentInfo.getPicList().size(); z++) {
					if (contentInfo.getPicList().get(z).getContentPic() != null
							&& contentInfo.getPicList().get(z).getContentPic().indexOf("http") < 0) {
						contentInfo.getPicList().get(z)
								.setContentPic(DOMAIN + contentInfo.getPicList().get(z).getContentPic());
					}
				}
			}
			if (contentInfo.getContentText() != null) {
				contentInfo.setContentText(contentInfo.getContentText()
						.replaceAll("\"/upload/download", "\"" + DOMAIN + "/upload/download")
						.replaceAll("<img src=\"/static/", "<img src=\"" + DOMAIN + "/static/"));
			}
			String str = CPSUtil.getParamValue("REPEAT_MONEY");
			Double bou = Double.valueOf(str);
			if (str != null && !str.equals(0) && contentInfo.getHomeToped() == 0) {
				contentInfo.setReadAward(bou);
				contentInfo.setFriendShareAward(bou);
				contentInfo.setTimelineShareAward(bou);
			}

			String isWeixin = CPSUtil.isWeixin(req);
			String ranColor = CPSUtil.getRandomColor();
			String like = "0";
			String shoucang = "0";
			sbContentInfoService.updateContentReadNum(contentId, 1);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isGoldenEggs", false);
			map.put("contentNum", 0);
			map.put("goldenEggs", CPSUtil.getParamValue("PEPEAT_GOLDEN_EGGS_CHANCE"));
			map.put("goldenEggsGold", CPSUtil.getParamValue("PEPEAT_GOLDEN_EGGS_GOLD"));
			map.put("collect", shoucang);
			map.put("like", like);
			map.put("uid", 1);
			map.put("cid", contentId);
			map.put("ydgf", RandomNum.getRandomIntVal(10));
			map.put("gzgf", RandomNum.getRandomIntVal(22));
			map.put("ranColor", ranColor);
			map.put("adRanColor", CPSUtil.getRandomColor());
			map.put("isWeixin", isWeixin);
			map.put("content", contentInfo);
			return ResultDTO.OK(map);
		} else {
			return ResultDTO.OK();
		}
	}

	/**
	 * 文章查看
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "detail", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> articleInfo(Model model, HttpServletRequest req, Headers header)
			throws UnsupportedEncodingException {
		Dto dto = getParamAsDto();
		// 当前用户
		Integer contentId = dto.getAsInteger("cid");
		SbContentInfo contentInfo = new SbContentInfo();
		// 改成内存中提取正文
		contentInfo = CPSUtil.getContentInfoById(contentId);
		if (contentInfo == null || contentInfo.getContentId() == null
				|| contentInfo.getContentId().toString().equals("0")) {
			contentInfo = sbContentInfoService.getSbContentInfoByID(contentId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (CPSUtil.isNotEmpty(contentInfo)) {
			// 文章类型
			Integer contentType = contentInfo.getContentType();
			if (contentInfo.getPicList() != null && contentInfo.getPicList().size() > 0) {
				for (int z = 0; z < contentInfo.getPicList().size(); z++) {
					if (contentInfo.getPicList().get(z).getContentPic() != null
							&& contentInfo.getPicList().get(z).getContentPic().indexOf("http") < 0) {
						contentInfo.getPicList().get(z)
								.setContentPic(DOMAIN + contentInfo.getPicList().get(z).getContentPic());
					}
				}
			}
			if (contentInfo.getContentText() != null) {
				contentInfo.setContentText(contentInfo.getContentText()
						.replaceAll("\"/upload/download", "\"" + DOMAIN + "/upload/download")
						.replaceAll("<img src=\"/static/", "<img src=\"" + DOMAIN + "/static/"));
				contentInfo.setContentText(contentInfo.getContentText().replaceAll("data-original=", "src="));
			}
			String str = CPSUtil.getParamValue("REPEAT_MONEY");
			Double bou = Double.valueOf(str);
			if (str != null && !str.equals(0) && contentInfo.getHomeToped() == 0) {
				contentInfo.setReadAward(bou);
				contentInfo.setFriendShareAward(bou);
				contentInfo.setTimelineShareAward(bou);
			}
			String isWeixin = CPSUtil.isWeixin(req);
			String ranColor = CPSUtil.getRandomColor();
			String like = "0";
			String shoucang = "0";
			if (CPSUtil.isNotEmpty(header.getUserid())) {
				Integer userId = Integer.valueOf(header.getUserid());
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
					System.out.println(getTZDomian(userId));
					System.out.println(target);
					System.out.println(CPSUtil.getParamEncrypt(messageParam));
					pyqUrl = getTGDomian(userId) + target + CPSUtil.getParamEncrypt(timelineParam);
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
				JpUserCollect collect = new JpUserCollect();
				collect.setUserId(Integer.valueOf(header.getUserid()));
				collect.setContentId(contentInfo.getContentId());
				collect.setType("2");
				if (CPSUtil.isNotEmpty(jpUserCollectService.find(collect))) {
					like = "1";
				}
				collect = new JpUserCollect();
				collect.setUserId(Integer.valueOf(header.getUserid()));
				collect.setContentId(contentInfo.getContentId());
				collect.setType("1");
				if (CPSUtil.isNotEmpty(jpUserCollectService.find(collect))) {
					shoucang = "1";
				}
				map.put("copyText", contentInfo.getContentTitle() + "..." + wxqXssUrl);
				map.put("contentUrl", wxqXssUrl);
				map.put("_contentUrl", CPSUtil.encryptBased64(pyXssUrl));
				map.put("pyUrl", pyXssUrl);
				map.put("pyqUrl", pyqXssUrl);
				String eggs = CPSUtil.getParamValue("PEPEAT_GOLDEN_EGGS_CHANCE");
				String contentNum = JedisUtils
						.get("C_saveContentTask:taskId_" + 51 + "_contentId_" + contentId + "_userId_" + userId);
				if (CPSUtil.isEmpty(contentNum)) {
					int klz = 0;
					if (CPSUtil.isNotEmpty(eggs)) {
						klz = Integer.valueOf(eggs);
					}
					int max = 0, min = 99;
					int klzz = (int) (Math.random() * (max - min) + min);
					if (klz > klzz) {
						map.put("isGoldenEggs", true);
					} else {
						map.put("isGoldenEggs", false);
					}
				} else {
					map.put("isGoldenEggs", false);
				}
				map.put("contentNum", contentNum);
			} else {
				map.put("contentNum", 0);
			}
			sbContentInfoService.updateContentReadNum(contentId, 1);
			map.put("goldenEggs", CPSUtil.getParamValue("PEPEAT_GOLDEN_EGGS_CHANCE"));
			map.put("goldenEggsGold", CPSUtil.getParamValue("PEPEAT_GOLDEN_EGGS_GOLD"));
			map.put("collect", shoucang);
			map.put("like", like);
			map.put("uid", 1);
			map.put("cid", contentId);
			map.put("ydgf", RandomNum.getRandomIntVal(10));
			map.put("gzgf", RandomNum.getRandomIntVal(22));
			map.put("ranColor", ranColor);
			map.put("adRanColor", CPSUtil.getRandomColor());
			map.put("isWeixin", isWeixin);
			map.put("content", contentInfo);
			return ResultDTO.OK(map);
		} else {
			return ResultDTO.OK();
		}
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

	/**
	 * 文章查看
	 * 
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("info")
	public String articleInfo1(Model model, HttpServletRequest req, Headers header)
			throws UnsupportedEncodingException {
		Dto dto = getParamAsDto();
		// 当前用户
		Integer contentId = dto.getAsInteger("cid");
		SbContentInfo contentInfo = new SbContentInfo();
		// if (CPSUtil.isNotEmpty(contentId) && CPSUtil.isNotEmpty(memberInfo))
		// {
		// Integer userId = memberInfo.getMemberId();
		Integer userId = Integer.valueOf(header.getUserid());
		MemberInfo memberInfo = memberInfoDao.getUserByUserID(userId);
		if (CPSUtil.isNotEmpty(contentId) && CPSUtil.isNotEmpty(memberInfo)) {
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

	public List<SbContentInfo> TopArticle(SbContentInfo sbContentInfo) {
		String str = JedisUtils.get("ARTICLE_LIST" + ":TYPE" + sbContentInfo.getContentType() + "_ColumnId_"
				+ sbContentInfo.getColumnId() + "_WeightType_" + sbContentInfo.getHomeToped());
		if (str == null || str.contains("[]")) {
			Page<SbContentInfo> contents = new Page<SbContentInfo>();
			sbContentInfo.setCheckState(1);
			contents.setPageSize(5);
			List<SbContentInfo> con = apiSbContentInfoDao.findSbContentInfoByPage(sbContentInfo, contents);
			if (con.size() == 0) {
				sbContentInfo.setContentId(0);
				con.add(sbContentInfo);
			}
			JedisUtils.set(
					"ARTICLE_LIST" + ":TYPE" + sbContentInfo.getContentType() + "_ColumnId_"
							+ sbContentInfo.getColumnId() + "_WeightType_" + sbContentInfo.getHomeToped(),
					JSON.toJSONString(con), 5 * 60);
			if (con.size() == 1 && con.get(0).getContentId() == 0) {
				return new ArrayList<SbContentInfo>();
			}
			return con;
		}
		List<SbContentInfo> list = JSON.parseArray(str, SbContentInfo.class);
		if (list.size() == 1 && list.get(0).getContentId() == 0) {
			return new ArrayList<SbContentInfo>();
		}
		return JSON.parseArray(str, SbContentInfo.class);
	}

	public List<SbContentInfo> pulldownArticle(SbContentInfo sbContentInfo,Page<SbContentInfo> contents) {
		String str = JedisUtils.get("ARTICLE_LIST" + ":TYPE" + sbContentInfo.getContentType() + "_ColumnId_"
				+ sbContentInfo.getColumnId() + "_WeightType_" + sbContentInfo.getHomeToped());
		if (str == null || str.contains("[]")) {
			/*Page<SbContentInfo> contents = new Page<SbContentInfo>();
			contents.setPageSize(20);*/
			sbContentInfo.setCheckState(1);
			List<SbContentInfo> con = apiSbContentInfoDao.getSbContentList(sbContentInfo, contents);
			JedisUtils.set(
					"ARTICLE_LIST" + ":TYPE" + sbContentInfo.getContentType() + "_ColumnId_"
							+ sbContentInfo.getColumnId() + "_WeightType_" + sbContentInfo.getHomeToped(),
					JSON.toJSONString(con), 5 * 60);
			return con;
		}
		return JSON.parseArray(str, SbContentInfo.class);
	}

	/**
	 * 文章推荐数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "tjLists", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getTjContentInfos() {
		SbContentInfo sbContentInfo = new SbContentInfo();
		sbContentInfo.setHomeToped(WeightType.COMMON.getType());
		sbContentInfo.setContentType(0);
		List<SbContentInfo> list = new ArrayList<>();
		Page<SbContentInfo> page = new Page<>();
		page.setPageSize(20);
		page.setPageNum(1);
		List<SbContentInfo> list1 = pulldownArticle(sbContentInfo,page);
		for (int i = 0; i < 3 && 0 < list1.size(); i++) {
			int xxx = (int) Math.round(Math.random() * (list1.size() - 1));
			list.add(list1.get(xxx));
			list1.remove(xxx);
		}
		return ResultDTO.OK(list);
	}
}
