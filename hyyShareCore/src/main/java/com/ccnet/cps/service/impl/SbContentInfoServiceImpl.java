package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.CheckStateType;
import com.ccnet.core.common.ContentType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.WeightType;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.EmojiFilter;
import com.ccnet.core.common.utils.RandomNum;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.html.ExtractHtmlUtil;
import com.ccnet.core.common.utils.html.HtmlUtils;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.dao.impl.UserInfoDao;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbColumnInfoDao;
import com.ccnet.cps.dao.SbContentCommentDao;
import com.ccnet.cps.dao.SbContentInfoDao;
import com.ccnet.cps.dao.SbContentPicDao;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.entity.SbContentComment;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.entity.WechatContentInfo;
import com.ccnet.cps.service.SbContentInfoService;

@Service("sbContentInfoService")
public class SbContentInfoServiceImpl extends BaseServiceImpl<SbContentInfo> implements SbContentInfoService{

	@Autowired
	private SbContentInfoDao sbContentInfoDao;
	@Autowired
	private SbContentPicDao sbContentPicDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private SbColumnInfoDao sbColumuInfoDao;
	@Autowired
	private SbContentCommentDao contentCommentDao;
	/**
	 * 分页查询(多过滤)
	 * @param contentInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentInfo> findSbContentInfoByPage(SbContentInfo contentInfo, Page<SbContentInfo> page,Dto pdDto){
		Page contentPage =  sbContentInfoDao.findSbContentInfoByPage(contentInfo, page, pdDto);
		List<SbContentInfo> contentList = contentPage.getResults();
		if(!CPSUtil.checkListBlank(contentList)){
			//处理操作人
			List<Integer> userIds = sbContentInfoDao.getValuesFromField(contentList, "userId");
			Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
			//处理栏目
			List<Integer> columnIds = sbContentInfoDao.getValuesFromField(contentList, "columnId");
			Map<Integer, SbColumnInfo> columnMap = sbColumuInfoDao.getSbColumnInfoMapByIds(columnIds);
			//处理点赞
			List<Integer> contentIds = sbContentInfoDao.getValuesFromField(contentList, "contentId");
			Map<Integer, SbContentComment> commentMap = contentCommentDao.getSbContentCommentMapByIds(contentIds);
			for (SbContentInfo content : contentList) {
				
				if(CPSUtil.isNotEmpty(content.getUserId())){
					content.setUserInfo(userMap.get(content.getUserId()));
				}
				
				if(CPSUtil.isNotEmpty(content.getColumnId())){
					content.setColumnInfo(columnMap.get(content.getColumnId()));
				}
				
				if(CPSUtil.isNotEmpty(content.getCheckState())){
				   content.setCheckStateName(CheckStateType.getStateType(content.getCheckState()).getName());
				}
				
				if(CPSUtil.isNotEmpty(content.getHomeToped())){
				   content.setHomeTopedName(WeightType.getUserSateType(content.getHomeToped()).getName());
				}
				
				if(CPSUtil.isNotEmpty(content.getContentId())){
				   content.setCommentInfo(commentMap.get(content.getContentId()));
				}
				//设置图片
				setContentPics(content);
			}
		}
		return contentPage;
	}
	
	
	/**
	 * 设置产品图片
	 * @param content   
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	private void setContentPics(SbContentInfo content){
		//获取产品对应的图片
		List<SbContentPic> pics = sbContentPicDao.findPicsByContentID(content.getContentCode());
		if(CPSUtil.listNotEmpty(pics)){
			content.setPicList(pics);
			//给文章设置一张列表预览图
			content.setContentPic(pics.get(0).getContentPic());
			//拼接字符串
			int picIndex = 0;
			StringBuffer sbBuffer = new StringBuffer("");
			List<String> picList = sbContentPicDao.getValuesFromField(pics, "contentPic");
			if(CPSUtil.listNotEmpty(picList)){
				for (String pic : picList) {
					sbBuffer.append(pic);
					if(picIndex < picList.size()){
						sbBuffer.append(",");
					}
				}
				content.setContentPics(sbBuffer.toString());
			}
			content.setPicList(pics);
		}
	}
	
	
	/**
	 * 日期统计
	 * @param startDate
	 * @param endDate
	 * @return int  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate){
		return sbContentInfoDao.count(startDate, endDate);
	}
	
	
	/**
	 * 随机查询
	 * @param contentInfo
	 * @param limit 条数
	 * @return
	 */
	public List<SbContentInfo> getRandContentList(SbContentInfo contentInfo,int limit) {
		return sbContentInfoDao.getRandContentList(contentInfo, limit);
	}
	
	
	/**
	 * 获取文章集合
	 * @param contentInfo
	 * @return
	 */
	public List<SbContentInfo> findSbContentInfoList(SbContentInfo contentInfo) {
		List<SbContentInfo> contentList = sbContentInfoDao.findList(contentInfo);
		if(CPSUtil.listNotEmpty(contentList)){
			//处理操作人
			List<Integer> userIds = sbContentInfoDao.getValuesFromField(contentList, "userId");
			Map<Integer, UserInfo> userMap = userInfoDao.getUserMapByIds(userIds);
			//处理栏目
			List<Integer> columnIds = sbContentInfoDao.getValuesFromField(contentList, "columnId");
			Map<Integer, SbColumnInfo> columnMap = sbColumuInfoDao.getSbColumnInfoMapByIds(columnIds);
			//处理点赞
			List<Integer> contentIds = sbContentInfoDao.getValuesFromField(contentList, "contentId");
			Map<Integer, SbContentComment> commentMap = contentCommentDao.getSbContentCommentMapByIds(contentIds);
			for (SbContentInfo content : contentList) {
				
				if(CPSUtil.isNotEmpty(content.getUserId())){
					content.setUserInfo(userMap.get(content.getUserId()));
				}
				
				if(CPSUtil.isNotEmpty(content.getColumnId())){
					content.setColumnInfo(columnMap.get(content.getColumnId()));
				}
				
				if(CPSUtil.isNotEmpty(content.getCheckState())){
				   content.setCheckStateName(CheckStateType.getStateType(content.getCheckState()).getName());
				}
				
				if(CPSUtil.isNotEmpty(content.getHomeToped())){
				   content.setHomeTopedName(StateType.getStateType(content.getHomeToped()).getName());
				}
				
				if(CPSUtil.isNotEmpty(content.getContentId())){
				   content.setCommentInfo(commentMap.get(content.getContentId()));
				}
				//设置图片
				setContentPics(content);
			}
		}
		return contentList;
	}
	
	/**
	 * 获取文章信息
	 * @param contentInfo
	 * @return
	 */
	public SbContentInfo findSbContentInfo(SbContentInfo contentInfo){
		SbContentInfo content = sbContentInfoDao.findSbContentInfo(contentInfo);
		if(CPSUtil.isNotEmpty(content)){
			setContentPics(content);
		}
		return content;
	}
	
	/**
	 * 根据ID获取文章
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentListByIds(List<Integer> contentIds){
		return sbContentInfoDao.getSbContentListByIds(contentIds);
	}
	
	/**
	 * 转换集合数据
	 * @param contentIds
	 * @return
	 */
	public Map<Integer, SbContentInfo> getSbContentInfoMapByIds(List<Integer> contentIds){
		return sbContentInfoDao.getSbContentInfoMapByIds(contentIds);
	}
	
	/**
	 * 获取单个文章
	 * @param contentId
	 * @return
	 */
	public SbContentInfo getSbContentInfoByID(Integer contentId){
		SbContentInfo content = sbContentInfoDao.getSbContentByID(contentId);
		if(CPSUtil.isNotEmpty(content)){
			setContentPics(content);
		}
		return content;
	}
	
	
	/**
	 * 处理文章阅读数
	 * @param contentId
	 * @param addNum
	 * @return
	 */
	public boolean addContentReadNum(Integer contentId,Integer addNum) {
		boolean temp = false;
		if(CPSUtil.isNotEmpty(contentId) && CPSUtil.isNotEmpty(addNum)){
			SbContentInfo content = sbContentInfoDao.getSbContentByID(contentId);
			if(CPSUtil.isNotEmpty(content)){
				content.setReadNum(content.getReadNum()+addNum);
				temp = sbContentInfoDao.editSbContentInfo(content);
				/*if(temp){
					InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
				}*/
			}
		}
		return temp;
	}
	
	/**
	 * 处理文章分享数
	 * @param contentId
	 * @param addNum
	 * @return
	 */
	public boolean addContentShareNum(Integer contentId,Integer addNum) {
		boolean temp = false;
		if(CPSUtil.isNotEmpty(contentId) && CPSUtil.isNotEmpty(addNum)){
			SbContentInfo content = sbContentInfoDao.getSbContentByID(contentId);
			if(CPSUtil.isNotEmpty(content)){
				content.setShareNum(content.getShareNum()+addNum);
				temp = sbContentInfoDao.editSbContentInfo(content);
				if(temp){
					InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
				}
			}
		}
		return temp;
	}
	
	
	/**
	 * 保存文章
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentInfo(SbContentInfo contentInfo){
		//处理文章图片下载
		downloadPic(contentInfo);
		//处理文章预览图片保存
		dealContentPic(contentInfo);
		contentInfo.setContentText(EmojiFilter.filterEmoji(contentInfo.getContentText()));
		CPSUtil.xprint("contentInfo="+contentInfo);
		return sbContentInfoDao.saveSbContentInfo(contentInfo);
	}
	
	/**
	 * 修改文章
	 * @param contentInfo
	 * @return
	 */
	public boolean editSbContentInfo(SbContentInfo contentInfo){
		//处理文章图片下载
		downloadPic(contentInfo);
		//处理文章预览图片保存
		dealContentPic(contentInfo);
		contentInfo.setContentText(EmojiFilter.filterEmoji(contentInfo.getContentText()));
		CPSUtil.xprint("contentInfo="+contentInfo);
		return sbContentInfoDao.editSbContentInfo(contentInfo);
	}
	
	
	/**
	 * 采集文章保存
	 * @param cDto
	 * @param userInfo
	 * @author Jackie Wang
	 * @date 2017-10-26
	 */
	public boolean saveGatherContent(Dto cDto ,UserInfo userInfo){
		boolean temp = false;
		SbContentInfo contentInfo = null;
		Integer userId = userInfo.getUserId();
		Integer columnId = cDto.getAsInteger("columnId");
		String weixinLink = cDto.getAsString("weixinLink");
		String readAward = cDto.getAsString("readAward");
		String shareAward = cDto.getAsString("shareAward");
		String shareTimeLineAward = cDto.getAsString("shareTimeLineAward");
		
		WechatContentInfo wcontent =null;
		if(weixinLink.contains("mp.weixin.qq.com")){
			wcontent = gatherWechatContent(weixinLink,userId);
		}else if(weixinLink.contains("plmm.com.cn")){
			wcontent = gatherMeinvContent(weixinLink,userId);
		}
		
		if(CPSUtil.isNotEmpty(wcontent)){
			//开始组合数据
			contentInfo = new SbContentInfo();  
			String contentCode = UuidUtil.get32UUID();
			contentInfo.setOrderNo(99);
			contentInfo.setReadNum(0);
			contentInfo.setClickNum(RandomNum.getRandIntVal(1000)+"");
			contentInfo.setShareNum(0);
			contentInfo.setVisualReadNum("100000");
			contentInfo.setTopedTime(new Date());
			contentInfo.setCreateTime(new Date());
			contentInfo.setUserId(userId);
			contentInfo.setContentCode(contentCode);
			contentInfo.setGatherPic(StateType.Valid.getState());
			contentInfo.setHomeToped(StateType.Valid.getState());
			contentInfo.setCheckState(StateType.InValid.getState());//审核状态
			contentInfo.setContentType(ContentType.Common.getState());
			contentInfo.setContentTitle(wcontent.getContentTitle());
			contentInfo.setContentSbTitle(wcontent.getContentTitle());
			
			String filterContent = EmojiFilter.filterEmoji(wcontent.getContentText());
			CPSUtil.xprint("filterContent="+filterContent);
			contentInfo.setContentText(filterContent);
			contentInfo.setColumnId(columnId);
			
			//处理文章阅读和分享奖励金额
			String _readAward = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_MONEY);
			readAward = CPSUtil.isEmpty(readAward) ? _readAward : readAward;
			if(CPSUtil.isEmpty(readAward)){
				readAward="0.2";
			}
			
			String _shareAward = CPSUtil.getParamValue(Const.CT_ARTICLE_SHARE_MONEY);
			shareAward = CPSUtil.isEmpty(shareAward) ? _shareAward : shareAward;
			if(CPSUtil.isEmpty(shareAward)){
				shareAward="0.2";
			}
			
			String _shareTimeLineAward = CPSUtil.getParamValue(Const.CT_ARTICLE_TIME_LINE_SHARE_MONEY);
			shareTimeLineAward = CPSUtil.isEmpty(shareTimeLineAward) ? _shareTimeLineAward : shareTimeLineAward;
			if(CPSUtil.isEmpty(shareTimeLineAward)){
				shareTimeLineAward="0.2";
			}
			
			contentInfo.setReadAward(Double.valueOf(readAward));
			//如果没有设置则微信好友和朋友圈分享一个价
			contentInfo.setFriendShareAward(Double.valueOf(shareAward));
			contentInfo.setTimelineShareAward(Double.valueOf(shareTimeLineAward));
			//处理图片
			List<String> picList = wcontent.getPicList();
			dealContentPic(picList, contentCode);
			
			CPSUtil.xprint("contentInfo="+contentInfo);
			sbContentInfoDao.saveSbContentInfo(contentInfo);
			temp = true;
		}
		
		return temp;
		
	}
	
	
	
	/**
	 * 处理图片
	 * @param contentInfo
	 * @return
	 */
	public boolean dealGoodPics(SbContentInfo contentInfo) {
		sbContentPicDao.trashPicByContentId(contentInfo.getContentCode());
		return dealContentPic(contentInfo);
	}
	
	
	
	/**
	 * 处理图片
	 * @param contentInfo
	 * @return
	 */
	public boolean dealContentPic(SbContentInfo contentInfo){
		boolean temp = false;
		String contentPic = contentInfo.getContentPics();
		if(CPSUtil.isNotEmpty(contentPic)){
		   int picIndex = 0;		   
		   SbContentPic scpic = null;
		   String cpics[] = contentPic.split(",");
		   for (String pic : cpics) {
			   if(CPSUtil.isNotEmpty(pic)){
				   scpic = new SbContentPic();
				   scpic.setContentId(contentInfo.getContentCode());
				   scpic.setCreateTime(new Date());
				   scpic.setContentPic(pic);
				   scpic.setSortNum(picIndex);
				   if(!checkContentPicExist(contentInfo.getContentCode(), pic)){
					  temp = sbContentPicDao.saveSbContentPic(scpic);
				   }
				   picIndex++;
			   }
		   }
		}
		return temp;
	}
	
	
	/**
	 * 处理图片
	 * @param picList
	 * @return
	 */
	public boolean dealContentPic(List<String> picList,String contentCode){
		boolean temp = false;
		if(CPSUtil.listNotEmpty(picList)){
		   int picIndex = 0;		   
		   SbContentPic scpic = null;
		   for (String pic : picList) {
			   if(CPSUtil.isNotEmpty(pic)){
				   scpic = new SbContentPic();
				   scpic.setContentId(contentCode);
				   scpic.setCreateTime(new Date());
				   scpic.setContentPic(pic);
				   scpic.setSortNum(picIndex);
				   if(!checkContentPicExist(contentCode, pic)){
					  temp = sbContentPicDao.saveSbContentPic(scpic);
				   }
				   picIndex++;
			   }
		   }
		}
		return temp;
	}
	
	
	/**
	 * 检查图片是否已经存在
	 * @param contentId
	 * @param picPath
	 * @return
	 */
	public boolean checkContentPicExist(String contentId,String picPath){
		boolean temp = false;
		 List<SbContentPic> picList = sbContentPicDao.findPicsByContentID(contentId);
		 if(CPSUtil.listNotEmpty(picList)){
			 for (SbContentPic spic : picList) {
				if(spic.getContentPic().equals(picPath)){
					temp = true;
					break;
				}
			}
		 }
		 return temp;
	}
	
	
	/**
	 * 批量发布/取消发布文章
	 * @param contentIds
	 * @param state
	 * @return
	 */
	public boolean pushSbContentInfo(String contentIds,StateType state){
		boolean temp = false;
		try {
			if(CPSUtil.isNotEmpty(contentIds)){
				SbContentInfo contentInfo = null;
				String cids[] = contentIds.split(",");
				for (String contentId : cids) {
					contentInfo = getSbContentInfoByID(Integer.valueOf(contentId));
				    contentInfo.setContentId(Integer.valueOf(contentId));
				    contentInfo.setCheckState(state.getState());
					sbContentInfoDao.editSbContentInfo(contentInfo);
				}
				temp = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	
	/**
	 * 批量删除文章
	 * @param contentIds
	 * @return
	 */
	public boolean trashSbContentInfo(String contentIds){
		boolean temp = false;
		try {
			if(CPSUtil.isNotEmpty(contentIds)){
				SbContentInfo contentInfo = null;
				String cids[] = contentIds.split(",");
				for (String cid : cids) {
				    contentInfo = getSbContentInfoByID(Integer.valueOf(cid));
				    //删除文章图片
					sbContentPicDao.trashPicByContentId(contentInfo.getContentCode());
					sbContentInfoDao.trashSbContentInfo(contentInfo);
				}
				temp = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return temp;
	}
	
	
	/**
	 * 删除文章
	 * @param contentInfo
	 * @return
	 */
	public boolean trashSbContentInfo(SbContentInfo contentInfo){
		//删除文章图片
		sbContentPicDao.trashPicByContentId(contentInfo.getContentCode());
		return sbContentInfoDao.trashSbContentInfo(contentInfo);
	}
	
	/**
	 * 批量删除文章
	 * @param list
	 * @return
	 */
	public boolean trashSbContentInfoList(List<SbContentInfo> list){
		boolean temp = sbContentInfoDao.trashSbContentInfoList(list);
		if(temp){
			//删除图片
			for (SbContentInfo sbContentInfo : list) {
				sbContentPicDao.trashPicByContentId(sbContentInfo.getContentCode());
			}
		}
		return temp;
	}

	
	@Override
	protected BaseDao<SbContentInfo> getDao(){
		return this.sbContentInfoDao;
	}


	@Override
	public Page<SbContentInfo> getUpContent(SbContentInfo sbContentInfo,Page<SbContentInfo> page,Dto pdDto) {
		sbContentInfoDao.getUpContentInfo(sbContentInfo, page, pdDto);
		return page;
	}

	@Override
	public Page<SbContentInfo> getNextContent(SbContentInfo sbContentInfo,Page<SbContentInfo> page,Dto pdDto) {
		sbContentInfoDao.getNextContentInfo(sbContentInfo, page, pdDto);
		return page;
	}
	
	
	
	
	/**
	 * 采集微信文章
	 * @param contentLink
	 * @return   
	 * WechatContentInfo  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-10-18
	 */
	public WechatContentInfo gatherWechatContent(String contentLink,Integer userId) {
		
		WechatContentInfo contentInfo = null;
		try {
			
			ExtractHtmlUtil extractHtmlUtil = new ExtractHtmlUtil();
			String htmlString = extractHtmlUtil.getBaseHtmlSource(contentLink);
			//CPSUtil.xprint("htmlString="+htmlString);
			if(CPSUtil.isNotEmpty(htmlString)){
				String contentTitle = "";
				String contentText = "";
				contentInfo = new WechatContentInfo();
				//解析标题
				Pattern pattern = Pattern.compile("<h2 class=\"rich_media_title\" id=\"activity-name\">(.*?)</h2>");
				Matcher matcher = pattern.matcher(htmlString);
				if (matcher.find()) {
					contentTitle = matcher.group();
					contentTitle = contentTitle.replace("<h2 class=\"rich_media_title\" id=\"activity-name\">", "");
					contentTitle = contentTitle.replace("</h2>", "");
					contentTitle = contentTitle.trim();
					
					if(contentTitle.contains("else") || contentTitle.contains("document")){
						//处理掉新的js代码
						contentTitle = contentTitle.substring(contentTitle.indexOf("else")+5,contentTitle.length()-11);
						contentTitle = contentTitle.replace(" document.write", "");
						contentTitle = contentTitle.replaceAll("\\(|\\)", "");
						contentTitle = contentTitle.replaceAll("\\{|\\}", "");
						contentTitle = contentTitle.replaceAll("“|”|\"|\'|;", "");
						contentTitle = contentTitle.trim();
					}
				}
				
				contentInfo.setContentTitle(contentTitle);
				CPSUtil.xprint("contentTitle="+contentTitle);
				
				//解析正文信息
				Pattern pattern1 = Pattern.compile("<div class=\"rich_media_content \" id=\"js_content\">(.*?)</div>");
				Matcher matcher1 = pattern1.matcher(htmlString);
				if (matcher1.find()) {
					CPSUtil.xprint("正文采集方式1采集到内容>>>>>>");
					contentText = matcher1.group();
					contentText = contentText.replace("<div class=\"rich_media_content \" id=\"js_content\">", "");
					contentText = contentText.replace("</div>", "");
					contentText = contentText.trim();
				}
				
				//解析正文信息
				Pattern pattern2 = Pattern.compile("<div class=\"rich_media_content \" lang==\"en\" id=\"js_content\">(.*?)</div>");
				Matcher matcher2 = pattern2.matcher(htmlString);
				if (matcher2.find()) {
					CPSUtil.xprint("正文采集方式2采集到内容>>>>>>");
					contentText = matcher2.group();
					contentText = contentText.replace("<div class=\"rich_media_content \" lang==\"en\" id=\"js_content\">", "");
					contentText = contentText.replace("</div>", "");
					contentText = contentText.trim();
				}
				
				contentInfo.setContentText(contentText);
				CPSUtil.xprint("contentText="+contentText);
				
				//采集到正文的同时处理图片下载
				if(CPSUtil.isNotEmpty(contentText)){
					//处理iframe
					contentText = processIframe(contentText);
					CPSUtil.xprint("采集文章内的图片.....");
					HashMap<String, Object> cMap = processImg2(contentText,userId);
					contentInfo.setContentText((String)cMap.get("text"));
					contentInfo.setPicList((ArrayList<String>)cMap.get("picList"));
				}
			}
			
		} catch (Exception e) {
			CPSUtil.xprint("采集文章失败....");
			e.printStackTrace();
		}
		
		return contentInfo;
	}
	
	public static void main(String[] args) {
//		String contentLink="https://www.plmm.com.cn/xinggan/4360.html";
//		ExtractHtmlUtil extractHtmlUtil = new ExtractHtmlUtil();
//		String htmlString = extractHtmlUtil.getBaseHtmlSource(contentLink);
//		Document doc=Jsoup.parse(htmlString);
//		Elements elemts=doc.getElementsByClass("main");
//		if(null==elemts||elemts.isEmpty()){
//			return;
//		}
//		
//		Element mainContent =elemts.get(0);
//		String title="";
//		Elements titleElem = mainContent.getElementsByTag("h1");
//		if(null!=titleElem){
//			title=titleElem.text();
//		}
//		System.out.println(title);
		
		String linkHref="http://www.baiud.com/1.jpg";
		String contentText="<img src=\""+linkHref+"\">";
		System.out.println(contentText);
		
		
	}
	
	/**
	 * https://www.plmm.com.cn/
	 * 漂亮妹妹网
	 * @param contentLink
	 * @param userId
	 * @return
	 */
	public WechatContentInfo gatherMeinvContent(String contentLink,Integer userId) {

		WechatContentInfo contentInfo = null;
		try {

			ExtractHtmlUtil extractHtmlUtil = new ExtractHtmlUtil();
			String htmlString = extractHtmlUtil.getBaseHtmlSource(contentLink);
			// CPSUtil.xprint("htmlString="+htmlString);
			if (CPSUtil.isNotEmpty(htmlString)) {
				Document doc=Jsoup.parse(htmlString);
				Elements elemts=doc.getElementsByClass("main");
				if(null==elemts||elemts.isEmpty()){
					return null;
				}
				
				String contentTitle = "";
				String contentText = "<div>";
				
				// 解析标题
				Element mainContent =elemts.get(0);
				Elements titleElem = mainContent.getElementsByTag("h1");
				if(null!=titleElem){
					contentTitle=titleElem.text();
				}
				
				contentInfo = new WechatContentInfo();
				
				contentInfo.setContentTitle(contentTitle);
				CPSUtil.xprint("contentTitle=" + contentTitle);

				// 解析正文信息 w1200高清
				Element eleDemo=mainContent.getElementById("demo-test-gallery");
				if(null!=eleDemo){
					Elements links =eleDemo.getElementsByTag("a");
					for (Element link : links){
						  String linkHref = link.attr("data-med");
						  if(null!=linkHref&&linkHref.endsWith("@!w960")){
							  linkHref=linkHref.replace("@!w960", "@!w640");
							  if(!(linkHref.startsWith("http:"))||(linkHref.startsWith("https:"))){
								  linkHref="https:"+linkHref;
							  }
							  contentText+="<p><img src=\""+linkHref+"\" data-type=\"jpg\" data-src=\""+linkHref+"\" data-ratio=\"1.5\"></p>";
						  }

					}
				}

				contentText+="<div>";
				contentInfo.setContentText(contentText);
				CPSUtil.xprint("contentText=" + contentText);

				// 采集到正文的同时处理图片下载
				if (CPSUtil.isNotEmpty(contentText)) {
					// 处理iframe
					contentText = processIframe(contentText);
					CPSUtil.xprint("采集文章内的图片.....");
					HashMap<String, Object> cMap = processImg2(contentText,userId);
					contentInfo.setContentText((String) cMap.get("text"));
					contentInfo.setPicList((ArrayList<String>) cMap.get("picList"));
				}
			}

		} catch (Exception e) {
			CPSUtil.xprint("采集文章失败....");
			e.printStackTrace();
		}

		return contentInfo;
	}
	
	
	private void downloadPic(SbContentInfo contentInfo){
		if(contentInfo.getGatherPic() != null && contentInfo.getGatherPic()==1){
		   processImg(contentInfo);
		   CPSUtil.xprint("采集文章内的图片.....");
		}else{
			contentInfo.setGatherPic(0);
			CPSUtil.xprint("不采集文章内的图片.....");
		}
	}
	
	private void processImg(SbContentInfo contentInfo) {
		String content = contentInfo.getContentText();
		if (StringUtils.isNotBlank(content)) {
			Document dom = Jsoup.parse(content);
			Elements imageNodes = dom.getElementsByTag("img");
			if (imageNodes != null && imageNodes.size() > 0) {
				for (Element element : imageNodes) {
					//下载并替换
					try {
						HtmlUtils.downloadImg(element,CPSUtil.getCurDateNoSplit());
					} catch (Exception e) {
						logger.error(e.getMessage() + "Download file error", e);
					}
				}
			}
			contentInfo.setContentText(dom.body().html());
		}
	}
	
	private HashMap<String, Object> processImg2(String contentText,Integer userId) {
		List<String> imgList = new ArrayList<String>();
		HashMap<String, Object> cMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(contentText)) {
			Document dom = Jsoup.parse(contentText);
			Elements imageNodes = dom.getElementsByTag("img");
			if (imageNodes != null && imageNodes.size() > 0) {
				for (Element element : imageNodes) {
					//下载并替换
					try {
						HtmlUtils.downloadImg2(element,CPSUtil.getCurDateNoSplit(),userId,imgList);
					} catch (Exception e) {
						logger.error(e.getMessage() + "Download file error", e);
					}
				}
				if(CPSUtil.listNotEmpty(imgList)){
					cMap.put("picList", imgList);
				}
			}
			
			CPSUtil.xprint("采集到文章内的图片数量="+imgList.size());
			contentText = dom.body().html();
			cMap.put("text", contentText);
		}
		return cMap;
	}
	
	/**
	 * 处理iframe显示
	 * @param contentText
	 * @return
	 */
	private String processIframe(String contentText) {
		if (StringUtils.isNotBlank(contentText)) {
			Document dom = Jsoup.parse(contentText);
			Elements imageNodes = dom.getElementsByTag("iframe");
			if (imageNodes != null && imageNodes.size() > 0) {
				for (Element element : imageNodes) {
					try {
						String data_src = element.attr("data-src");
						if(CPSUtil.isNotEmpty(data_src)){
							element.attr("src", data_src);
							element.attr("height", "380px");
							element.attr("width", "585px");
						}
					} catch (Exception e) {
						logger.error(e.getMessage() + "deal iframe error", e);
					}
				}
			}
			contentText = dom.body().html();
		}
		return contentText;
	}
	
	/**
	 * 处理阅读增加
	 * @param contentId
	 * @param addNum
	 */
	public int updateContentReadNum(Integer contentId,Integer addNum){
		int updateNum = sbContentInfoDao.updateContentReadNum(contentId, addNum);
		/*if(updateNum > 0){
			//更新缓存
			InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
		}*/
		return updateNum;
	}
	
	/**
	 * 处理分享增加
	 * @param contentId
	 * @param addNum
	 */
	public int updateContentShareNum(Integer contentId,Integer addNum){
		int updateNum = sbContentInfoDao.updateContentShareNum(contentId, addNum);
		if(updateNum > 0){
			//更新缓存
			InitSystemCache.updateCache(Const.CT_CONTENT_INFO_LIST);
		}
		return updateNum;
	}
	
}
