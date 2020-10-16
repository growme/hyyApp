package com.ccnet.admin.bh.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

import com.ccnet.cps.dao.SbContentPicDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ccnet.admin.bh.dao.ApiSbContentInfoDao;
import com.ccnet.admin.bh.entity.news.Data;
import com.ccnet.admin.bh.entity.news.Dongfang;
import com.ccnet.admin.bh.entity.video.Content;
import com.ccnet.admin.bh.entity.video.Video;
import com.ccnet.admin.bh.entity.videoView.VideoView;
import com.ccnet.admin.bh.service.NewsService;
import com.ccnet.core.common.utils.wxpay.MD5Util;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbContentInfoService;
import org.springframework.transaction.annotation.Transactional;

@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Autowired
	SbContentInfoService contentInfoService;

	@Autowired
	SbColumnInfoService columnInfoService;

	@Autowired
	ApiSbContentInfoDao contentInfoDao;

	@Autowired
	SbContentPicDao sbContentPicDao;

	private static String qid = "qid02561";
	private static String newsurl = "http://newswifiapi.dftoutiao.com/jsonnew/refresh";
	private static String videourl = "http://is.snssdk.com/api/news/feed/v46/";

	@Override
	public void taskList() {
		Map<String, Integer> map = new HashMap<>();
		SbColumnInfo arg0 = new SbColumnInfo();
		arg0.setColumnType(0);
		List<SbColumnInfo> columnList = columnInfoService.findList(arg0);
		for (int i = 0; i < columnList.size(); i++) {
			if (StringUtils.isNotBlank(columnList.get(i).getCode())) {
				map.put(columnList.get(i).getColumnName() + "--" + columnList.get(i).getCode(),
						addSbContentInfoByCode(columnList.get(i).getCode(), columnList.get(i).getColumnId()));
			}
		}
		SbColumnInfo arg1 = new SbColumnInfo();
		arg1.setColumnType(1);
		List<SbColumnInfo> columnList1 = columnInfoService.findList(arg1);
		for (int i = 0; i < columnList1.size(); i++) {
			if (StringUtils.isNotBlank(columnList1.get(i).getCode())) {
				map.put(columnList1.get(i).getColumnName() + "--" + columnList1.get(i).getCode(),
						addSbContentInfoVideoByCode(columnList1.get(i).getCode(), columnList1.get(i).getColumnId()));
			}
		}
	}

	@Override
	@Transactional
	public void deleteContentListBeforeDate(String date) {
		List<SbContentInfo> list = contentInfoDao.findSbContentInfoBeforeDate(date);
		for (SbContentInfo sbContentInfo : list ) {
			SbContentInfo tmp = new SbContentInfo();
			tmp.setContentId(sbContentInfo.getContentId());
			contentInfoDao.delete(tmp);
			sbContentPicDao.trashPicByContentId(sbContentInfo.getContentCode());
		}

	}

	public int addSbContentInfoByCode(String code, Integer colId) {
		List<SbContentInfo> newslist = newList(code, colId);
		int bol = 0;
		List<String> codeList = new ArrayList<>();
		for (int i = 0; i < newslist.size(); i++) {
			codeList.add(newslist.get(i).getContentCode());
		}
		List<SbContentInfo> str = contentInfoDao.getSbContentByCodeList(codeList);
		for (int i = 0; i < str.size(); i++) {
			for (int z = 0; z < newslist.size(); z++) {
				if (str.get(i).getContentCode().equals(newslist.get(z).getContentCode())) {
					newslist.remove(z);
				}
			}
		}
		for (int i = 0; i < newslist.size(); i++) {
			if (contentInfoService.saveSbContentInfo(newslist.get(i))) {
				bol++;
			}
		}
		return bol;
	}

	public int addSbContentInfoVideoByCode(String code, Integer colId) {
		List<SbContentInfo> newslist = videoList(code, colId);
		int bol = 0;
		List<String> codeList = new ArrayList<>();
		for (int i = 0; i < newslist.size(); i++) {
			codeList.add(newslist.get(i).getContentCode());
		}
		List<SbContentInfo> str = contentInfoDao.getSbContentByCodeList(codeList);
		for (int i = 0; i < str.size(); i++) {
			for (int z = 0; z < newslist.size(); z++) {
				if (str.get(i).getContentCode().equals(newslist.get(z).getContentCode())) {
					newslist.remove(z);
				}
			}
		}
		for (int i = 0; i < newslist.size(); i++) {
			if (contentInfoService.saveSbContentInfo(newslist.get(i))) {
				bol++;
			}
		}
		return bol;
	}

	public static List<SbContentInfo> newList(String type, Integer colId) {
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(newsurl + "?type=" + type + "&qid=" + qid);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				String result = buffer.toString();
				Dongfang obj = JSON.parseObject(result, Dongfang.class);
				List<SbContentInfo> list = getnewsList(obj, colId);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<SbContentInfo> getnewsList(Dongfang dongfang, Integer colId) {
		List<SbContentInfo> list = new ArrayList<>();
		List<Data> dataList = dongfang.getData();
		SbContentInfo news = null;
		for (Data data : dataList) {
			String contentId = MD5Util.MD5Encode(data.getUrl(), null);
			Date date = new Date();
			news = new SbContentInfo();
			List<SbContentPic> picList = new LinkedList<>();
			news.setContentCode(contentId);
			news.setContentTitle(data.getTopic());
			news.setContentSbTitle(data.getTopic());
			news.setContentText(regMatch(data.getUrl()));
			news.setContentType(0);
			news.setVisualReadNum("1000");
			news.setCheckState(1);
			news.setOrderNo(100);
			news.setColumnId(colId);
			news.setUserId(1);
			news.setHomeToped(0);
			news.setTopedTime(date);
			news.setGatherPic(1);
			news.setCreateTime(date);
			news.setReadAward(0D);
			news.setFriendShareAward(0D);
			news.setTimelineShareAward(0D);
			news.setUrlGather(0);
			news.setArticleUrl("");
			news.setVideoLink("");
			news.setClickNum("0");
			news.setShareNum(0);
			news.setReadNum(0);
			news.setWeixinLink("");
			news.setWeixinName("");
			news.setCollectNum(0);
			news.setCommentCount(0);
			for (int i = 0; i < data.getMiniimg().size(); i++) {
				if (i == 0) {
					news.setContentPic(data.getMiniimg().get(0).getSrc());
					news.setContentPicLink(data.getMiniimg().get(0).getSrc());
					news.setContentPics(data.getMiniimg().get(0).getSrc());
				} else {
					news.setContentPics(news.getContentPics() + "," + data.getMiniimg().get(i).getSrc());
				}
				SbContentPic e = new SbContentPic();
				e.setContentId(contentId);
				e.setContentPic(data.getMiniimg().get(i).getSrc());
				e.setCreateTime(date);
				e.setSortNum(i);
				picList.add(e);
			}
			news.setPicList(picList);
			list.add(news);
		}
		return list;
	}

	private List<SbContentInfo> videoList(String type, Integer colId) {
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(videourl + "?category=" + type);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				String result = buffer.toString();
				Video obj = JSON.parseObject(result, Video.class);
				List<SbContentInfo> list = getVideoList(obj, colId);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<SbContentInfo> getVideoList(Video dongfang, Integer colId) {
		List<SbContentInfo> list = new ArrayList<>();
		List<com.ccnet.admin.bh.entity.video.Data> dataList = dongfang.getData();
		SbContentInfo news = null;
		for (com.ccnet.admin.bh.entity.video.Data data : dataList) {
			Content obj = JSON.parseObject(data.getContent(), Content.class);
			news = new SbContentInfo();
			String contentId = MD5Util.MD5Encode(obj.getUrl(), null);
			Date date = new Date();
			news = new SbContentInfo();
			List<SbContentPic> picList = new LinkedList<>();
			news.setContentCode(contentId);
			news.setContentTitle(obj.getTitle());
			news.setContentSbTitle(obj.getTitle());
			news.setContentType(1);
			news.setVisualReadNum("1000");
			news.setCheckState(1);
			news.setOrderNo(100);
			news.setColumnId(colId);
			news.setUserId(1);
			news.setHomeToped(0);
			news.setTopedTime(date);
			news.setGatherPic(1);
			news.setContentText("");
			news.setCreateTime(date);
			news.setReadAward(0D);
			news.setFriendShareAward(0D);
			news.setTimelineShareAward(0D);
			news.setUrlGather(0);
			news.setArticleUrl("");
			news.setVideoLink("");
			news.setContentPicLink(obj.getArticle_url());
			news.setClickNum("0");
			news.setShareNum(0);
			news.setReadNum(0);
			news.setWeixinLink("");
			news.setWeixinName("");
			news.setCollectNum(0);
			news.setCommentCount(0);
			news.setVideoLink(obj.getVideo_id());
			if (obj.getLarge_image_list() != null)
				for (int i = 0; i < obj.getLarge_image_list().size(); i++) {
					if (i == 0) {
						news.setContentPic(obj.getLarge_image_list().get(0).getUrl());
						news.setContentPicLink(obj.getLarge_image_list().get(0).getUrl());
						news.setContentPics(obj.getLarge_image_list().get(i).getUrl());
					} else {
						news.setContentPics(news.getContentPics() + "," + obj.getLarge_image_list().get(i).getUrl());
					}
					SbContentPic e = new SbContentPic();
					e.setContentId(contentId);
					e.setContentPic(obj.getLarge_image_list().get(i).getUrl());
					e.setCreateTime(date);
					e.setSortNum(i);
					picList.add(e);
				}
			news.setPicList(picList);
			if (StringUtils.isNoneBlank(news.getVideoLink()) && news.getPicList().size() > 0) {
				list.add(news);
			}
		}
		return list;
	}

	public static String regMatch(String url) {
		HttpURLConnection conn = null;
		// Map<String, Object> map = new HashMap<>();
		// String title = "";
		String content = "";
		// String time = "";
		// String source = "";
		try {
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				String withinText = buffer.toString();
				// Matcher m =
				// Pattern.compile("<h1\\s*class\\s*=\\s*\"?\"title\"[^<]*>(.*?)</h1>").matcher(withinText);
				// while (m.find()) {
				// title = m.group(1);
				// }
				String div = "<div[^>]*id=\"content\"[^>]*>(.*?)</div>";
				Matcher m1 = Pattern.compile(div).matcher(withinText);
				while (m1.find()) {
					content = m1.group(1);
				}
				// String span = "<span[^>]*class=\"src\"[^>]*>(.*?)</span>";
				// Matcher m1x = Pattern.compile(span).matcher(withinText);
				// while (m1x.find()) {
				// String[] str = m1x.group(1).replaceAll("&nbsp;",
				// "").split("来源：");
				// if (str.length > 1) {
				// time = str[0];
				// source = str[1];
				// }
				// }
				// map.put("title", title);
				// map.put("content", content);
				// map.put("time", time);
				// map.put("source", source);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public static String getVideoUrl(String videoId) {
		try {
			String url = "https://ib.365yg.com";
			StringBuffer sb = new StringBuffer();
			Random ra = new Random();
			for (int i = 0; i < 15; i++) {
				sb.append(ra.nextInt(10) + 1 + "");
			}
			String param = "/video/urls/v/1/toutiao/mp4/" + videoId + "?r=" + sb;
			CRC32 crc = new CRC32();
			crc.update(param.getBytes());
			String i = crc.getValue() + "";
			url = url + param + "&s=" + i;
			HttpURLConnection conn = null;
			URL realUrl = new URL(url);
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			int code = conn.getResponseCode();
			if (code == 200) {
				InputStream is = conn.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				String text = buffer.toString();
				VideoView vb = JSON.parseObject(text, VideoView.class);
				if (vb.getMessage().equals("success")) {
					// vb.getData().getVideo_list().getVideo_1().setP2p_verify_url(new
					// String(Base64
					// .decode(vb.getData().getVideo_list().getVideo_1().getP2p_verify_url().toCharArray())));
					// vb.getData().getVideo_list().getVideo_1().setBackup_url_1(new
					// String(
					// Base64.decode(vb.getData().getVideo_list().getVideo_1().getBackup_url_1().toCharArray())));
					// vb.getData().getVideo_list().getVideo_1().setMain_url(new
					// String(
					// Base64.decode(vb.getData().getVideo_list().getVideo_1().getMain_url().toCharArray())));
				}
				String srt = new String(
						Base64.getDecoder().decode(vb.getData().getVideo_list().getVideo_1().getP2p_verify_url()));
				return srt;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
