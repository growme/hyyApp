package com.ccnet.core.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;
import com.ccnet.core.common.utils.html.ShortUrlUtil;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.Resources;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.ResourcesService;
import com.ccnet.cps.entity.MemberInfo;



public class BaseController<T> {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	public ResourcesService resourcesService;
	
	
	/**
	 * 获取前台当前用户
	 */
	public MemberInfo getCurUser() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		MemberInfo memberInfo = null;
		if (null != session) {
			Object obj = session.getAttribute(Const.MSESSION_USER);
			memberInfo = (MemberInfo) obj;
		}
		return memberInfo;
	}
	
	/**
	 * 获取后台当前用户
	 */
	public UserInfo getCurrentUser() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		UserInfo userInfo = null;
		if (null != session) {
			Object obj = session.getAttribute(Const.SESSION_USER);
			userInfo = (UserInfo) obj;
		}
		return userInfo;
	}
	
	/**
	 * 获取推广短连接
	 * @param visitCode
	 * @return
	 */
	public String getRecomUrl(String visitCode){
		String shortUrl = null;
		if(CPSUtil.isNotEmpty(visitCode)){
			// 生成推广链接
			String recomUrl = getDefaultDomain() + "/user/register/?v="+ visitCode + "&t="+ System.currentTimeMillis();
			shortUrl = ShortUrlUtil.getShortUrl(recomUrl);
		}
		return shortUrl;
	}
	
	/**
	 * 处理文章标题长度显示
	 * @param title 标题
	 * @param len 最大多长
	 * @return
	 */
	public String dealContentTitle(String title,int len) {
		String ntitle = "";
		if(CPSUtil.isNotEmpty(title)){
			if(title.length()>len){
			   ntitle = title.substring(0, len)+"...";
			}else{
				ntitle = title;
			}
		}
		return ntitle;
	}
	
    public void setSessionObj(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
    
    public String getDefaultDomain() {
    	String domain = "";
		if(getRequest().getServerPort()==80){
			domain = getRequest().getScheme()+"://"+getRequest().getServerName()+getRequest().getContextPath();
		}else{
			domain = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+getRequest().getContextPath();
		}
		CPSUtil.xprint("domain===>"+domain);
		return domain;
	}
    
    public String dealForwardUrl(String forwardUrl){
		String domainUrl = null;
		if(CPSUtil.isNotEmpty(forwardUrl)){
			if(forwardUrl.startsWith("http")){
				domainUrl = forwardUrl;
			} else {
				domainUrl = getRequest().getScheme()+"://"+ forwardUrl;
			}
			if(getRequest().getServerPort() != 80){
				//domainUrl = domainUrl + ":" + getRequest().getServerPort();
			}
			domainUrl = domainUrl + getRequest().getContextPath();
		}
		return domainUrl;
	}
	
	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public Dto getParamAsDto() {
		Dto dto = new BaseDto();
		Map properties = getRequest().getParameterMap();
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			dto.put(name, StringEscapeUtils.escapeHtml4(value.trim())); 
		}
		//CPSUtil.xprint("打印参数："+dto);
		String selected = dto.getAsString("mod");
		if (StringUtils.isNotBlank(selected)) {
			setSessionAttr(Const.CT_MENU_INDEX, selected);
		}
		return dto;
	}
	
	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public Dto getParamsAsDto() {
		Dto dto = new BaseDto();
		Map properties = getRequest().getParameterMap();
		Iterator entries = properties.entrySet().iterator(); 
		Map.Entry entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			dto.put(name, value.trim()); 
		}
		//CPSUtil.xprint("打印参数："+dto);
		String selected = dto.getAsString("mod");
		if (StringUtils.isNotBlank(selected)) {
			setSessionAttr(Const.CT_MENU_INDEX, selected);
		}
		return dto;
	}
	
	protected <K> Page<K> newPage(Dto dto) {
		Page<K> page = new Page<K>();
		Integer offset = dto.getAsInteger("pager.offset");
		if (offset != null && offset != 0) {
			page.setPageNum((offset / Const.PAGER_SIZE) + 1);
		}
		offset = dto.getAsInteger("pageSize");
		if (offset != null && offset != 0) {
			page.setPageSize(offset);
		}
		offset = dto.getAsInteger("totalRecord");
		if (offset != null) {
			page.setTotalRecord(offset);
		}
		return page;
	}
	
	/**
	 * 得到PageData
	 *//*
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}*/
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	public AjaxRes getAjaxRes(){
		return new AjaxRes();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();	
		return request;
	}
	
	/**
	 * 获取session值
	 * @param attrName
	 * @return
	 */
	public Object getSessionAttr(String attrName){
		return getRequest().getSession().getAttribute(attrName);
	}
	
	/**
	 * 设置session值
	 * @param attrName
	 * @param attrValue
	 * @return
	 */
	public void setSessionAttr(String attrName,Object attrValue){
		 getRequest().getSession().setAttribute(attrName,attrValue);
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){	
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 得到分页列表的信息 
	 * @param <T>
	 */
	@SuppressWarnings("hiding")
	public <T> Page<T> getPage(){	
		return new Page<T>();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	/**
	 * 资源的权限（显示级别）
	 * @param type 资源类别
	 * @return
	 */
	public List<Resources> getPermitBtn(ResourceTypes type){
		List<Resources> perBtns=new ArrayList<Resources>();	
		try {
			String menuCode=(String)getSessionAttr(Const.CT_MENU_INDEX);
			if(StringUtils.isNotBlank(menuCode)){
				List<Resources> resList = loadCurrentUserResources(true);
				for (Resources resources : resList) {
					if (resources != null 
							&& StringUtils.equals(resources.getParentCode(), menuCode) 
							&& resources.getResourceType().equals(type.getType())) {
						perBtns.add(resources);
					}
				}
			}	
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return perBtns;
	}
	
	/**
	 * 资源的权限（URl级别）
	 * @param type 资源类别(优化速度)
	 * @return
	 */
	protected boolean doSecurityIntercept(ResourceTypes type){
		String servletPath = getRequest().getServletPath();
		servletPath = StringUtils.substringBeforeLast(servletPath,".");// 去掉后面的后缀
		return doSecurityIntercept(type, servletPath);
	}
	
	/**
	 * 资源的权限（URl级别）
	 * @param type 资源类别(优化速度)
	 * @return
	 */
	protected boolean isAuthedReq(ResourceTypes type){
		return doSecurityIntercept(type);
	}
	
	/**
	 * 资源的权限（URl级别,拥有第一级资源权限，这资源才能访问）
	 * @param type 资源类别(优化速度)
	 * @param url 第一级资源
	 * @return
	 */
	protected boolean isAuthedReq(ResourceTypes type,String url){
		CPSUtil.xprint("url="+url);
		return doSecurityIntercept(type,url);
	}
	
	/**
	 * 资源的权限（URl级别,拥有第一级资源权限，这资源才能访问）
	 * @param type 资源类别(优化速度)
	 * @param url 第一级资源
	 * @return
	 */
	protected boolean doSecurityIntercept(ResourceTypes type,String url){
		try {
			List<Resources> authorized= loadCurrentUserResources(true);
			for (Resources r : authorized) {
				if (r != null && StringUtils.isNotBlank(r.getResourceUrl()) && r.getResourceType().equals(type.getType())) {
					if (StringUtils.equals(r.getResourceUrl(), url)) {
						return true;
					}
				}	
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
		return false;
	}
	
	protected List<Resources> loadCurrentUserResources(boolean useCache) {
		List<Resources> menus = null;
		// 获得用户Id
		if (useCache) {
			menus = (List<Resources>) getSessionAttr(Const.SESSION_MENULIST);
		}
		if (menus == null) {
			UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
			Integer userId = userInfo.getUserId();
			menus = resourcesService.findResources(userId);
			setSessionAttr(Const.SESSION_MENULIST, menus);
		}
		if (menus == null) {
			menus = new ArrayList<Resources>(0);
		}
		return menus;
	}
	
	/**
	 * 获取菜单树结构数据
	 * @param menuList
	 * @param target 目标frame
	 * @return
	 */
	protected String getMenuTreeJsonData(List<Resources> menuList,String target) {
		String jsonString = "";
		try {
			int count = 0;
			StringBuffer sbBuffer = new StringBuffer("");
			if(CPSUtil.listNotEmpty(menuList)){
				String parentId = null;
				//sbBuffer.append("{id:\"0\",name:\"系统资源\",open:true,pId:-1,icon:\""+CPSUtil.getCXTPath()+"/static/img/base.gif\"},");
				for (Resources menu : menuList) {
					count++;
					parentId = menu.getParentCode();
					sbBuffer.append("{");
					sbBuffer.append("id:\"").append(menu.getResourceCode()).append("\",");
					sbBuffer.append("rid:\"").append(menu.getResourceId()).append("\",");
					sbBuffer.append("name:\"").append(menu.getResourceName()).append("\",");
					if("1".equals(menu.getExpanded()+"")){
					   sbBuffer.append("open:").append("true").append(",");
			    	}else{
			    	   sbBuffer.append("open:").append("false").append(",");
			    	}
					
					if(CPSUtil.isEmpty(parentId)){
						parentId = "0";
					}
					sbBuffer.append("checked:").append(menu.getChecked()).append(",");
					sbBuffer.append("pId:\"").append(parentId).append("\",");
					if(CPSUtil.isNotEmpty(target)){
					  sbBuffer.append("url:\"").append(CPSUtil.getCXTPath()+"/"+menu.getResourceUrl()).append("\",");
					  sbBuffer.append("target:\"").append(target).append("\"");
					}
					sbBuffer.append("}");
					if(count < menuList.size()){
						sbBuffer.append(",");
					}
				}
			}
			jsonString = sbBuffer.toString();
			CPSUtil.xprint("treejsonData="+jsonString);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
	
	/**
	 * 获取菜单树结构数据
	 * @param menuList
	 * @param target
	 * @param url
	 * @param containParent 是否带上上级
	 * @return
	 */
	protected String getMenuTreeJsonData(List<Resources> menuList,String target,String url,Boolean containParent) {
		String jsonString = "";
		try {
			int count = 0;
			StringBuffer sbBuffer = new StringBuffer("");
			if(CPSUtil.listNotEmpty(menuList)){
				String parentId = null;
				for (Resources menu : menuList) {
					count++;
					parentId = menu.getParentCode();
					sbBuffer.append("{");
					sbBuffer.append("id:\"").append(menu.getResourceCode()).append("\",");
					sbBuffer.append("rid:\"").append(menu.getResourceId()).append("\",");
					sbBuffer.append("name:\"").append(menu.getResourceName()).append("\",");
					if("1".equals(menu.getExpanded()+"")){
					   sbBuffer.append("open:").append("true").append(",");
			    	}else{
			    	   sbBuffer.append("open:").append("false").append(",");
			    	}
					
					if(CPSUtil.isEmpty(parentId)){
						parentId = "0";
					}
					sbBuffer.append("pId:\"").append(parentId).append("\",");
					if(!StringUtils.startsWith(url, "/")){
						sbBuffer.append("url:\"").append(CPSUtil.getCXTPath()+"/"+url);
					}else{
						sbBuffer.append("url:\"").append(CPSUtil.getCXTPath()+url);
					}
					if(containParent){
						sbBuffer.append("?pc=").append(menu.getResourceCode());
						sbBuffer.append("&pt=").append(menu.getParentCode()).append("\",");
					}else{
						sbBuffer.append("\",");
					}
					sbBuffer.append("\"checked\":\"").append(menu.getChecked()).append("\",");
					sbBuffer.append("target:\"").append(target).append("\"");
					sbBuffer.append("}");
					if(count < menuList.size()){
						sbBuffer.append(",");
					}
				}
			}
			jsonString = sbBuffer.toString();
			CPSUtil.xprint("treejsonData="+jsonString);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonString;
	}
	
	/**
	 * 日历控件上的时间转换为Date对象
	 * @param dateString
	 * @return
	 */
	
	protected Date parseToDate(String dateString) {
		return parseToDate(dateString, "yyyy-MM-dd");
	}
	
	protected Date parseToDate(String dateString, String format) {
		if (StringUtils.isBlank(dateString)) {
			return null;
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage() + "日期转换失败:" + dateString, e);
		}
		return date;
	}
	
	protected String getBasePath(){
		String basePath = null;
		String path = getRequest().getContextPath();
		if(getRequest().getServerPort()==80){
			basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+path+"/";
		}else{
			basePath = getRequest().getScheme()+"://"+getRequest().getServerName()+":"+getRequest().getServerPort()+path+"/";
		}
		return basePath;
	}
	
	/**
	 * 验证字符串中是否包含html标签
	 * @param str
	 * @return true包含， false 不包含 
	 */
	protected boolean includeHtmlTag(String ... str) {
		String text = StringUtils.trimToEmpty(StringUtils.join(str, ","));
		text = text.replaceAll("\t| ", "");
		Document doc = Jsoup.parse(text);
		return !StringUtils.equalsIgnoreCase(text, doc.text());
	}

	protected String cleanHtmlTag(String text) {
		if (StringUtils.isBlank(text)) {
			return text;
		}
		return Jsoup.clean(text, Whitelist.none());
	}
	
	@Deprecated
	protected boolean includeHtmlTag(Dto dto) {
		if (dto != null) {
			for (Object key : dto.keySet()) {
				if (includeHtmlTag(String.valueOf(key), String.valueOf(dto.get(key)))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 下载附件方法
	 * @throws Exception 
	 */
   public void downloadFile(String path, HttpServletResponse response) throws Exception{
		logger.info("开始下载附件信息....");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		String filename= path.substring(path.lastIndexOf("/")+1, path.length());//获取文件名称
		try{
			if(path==null||"".equals(path)||filename==null||"".equals(filename)){
				logger.info("对不起! 没有指定附件名称,不能下载!");
			}
			File file = new File(path);
			if (!file.exists()) {// 文件不存在
				file = null;
				logger.info("对不起!此附件已经不存在，不能下载!!");
			}
			if (file.isDirectory()) {// 是目录
				file = null;
				logger.info("对不起!此附件是目录，不能下载!!");
			}
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			//设置文件类型以及文件头
			response.setContentType(this.getContentType(filename));
			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));

			fos = response.getOutputStream();
			bos = new BufferedOutputStream(fos);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
				bos.flush();
			}
			logger.debug("《《《《《《文件下载完成》》》》》");
		}catch (Exception e) {
			logger.info("下载出现异常!用户中中断了下载操作！");
			response.reset();
		}finally{
			try{
				if (fos != null) {
					fos.close();
					fos = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
				if (fis != null) {
					fis.close();
					fis = null;
				}
				if (bis != null) {
					bis.close();
					bis = null;
				}
		}catch(Exception e){
			logger.info("下载出现异常!用户中断了下载操作！");
		  }
		}
   }	
   /**
	 * 获得文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	private String getContentType(String fileName) {
		String fileNameTmp = fileName.toLowerCase();
		String ret = "";
		if (fileNameTmp.endsWith("txt")) {
			ret = "text/plain";
		}
		if (fileNameTmp.endsWith("gif")) {
			ret = "image/gif";
		}
		if (fileNameTmp.endsWith("jpg")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("jpeg")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("jpe")) {
			ret = "image/jpeg";
		}
		if (fileNameTmp.endsWith("zip")) {
			ret = "application/zip";
		}
		if (fileNameTmp.endsWith("rar")) {
			ret = "application/rar";
		}
		if (fileNameTmp.endsWith("doc")) {
			ret = "application/msword";
		}
		if (fileNameTmp.endsWith("ppt")) {
			ret = "application/vnd.ms-powerpoint";
		}
		if (fileNameTmp.endsWith("xls")) {
			ret = "application/vnd.ms-excel";
		}
		if (fileNameTmp.endsWith("html")) {
			ret = "text/html";
		}
		if (fileNameTmp.endsWith("htm")) {
			ret = "text/html";
		}
		if (fileNameTmp.endsWith("tif")) {
			ret = "image/tiff";
		}
		if (fileNameTmp.endsWith("tiff")) {
			ret = "image/tiff";
		}
		if (fileNameTmp.endsWith("pdf")) {
			ret = "application/pdf";
		}
		System.out.println("文件类型==" + ret);
		return ret;
	}
	
	
	
	
	/**
	 * 直接输出.
	 * 
	 * @param contentType
	 *  内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	protected void render(String text, String contentType, HttpServletResponse response) {
		try {
			response.setContentType(contentType + ";charset="+ Const.SYS_PAGE_ENCODE);
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接输出纯字符串.
	 */
	public void renderText(String text, HttpServletResponse response) {
		render(text, "text/plain", response);
	}

	/**
	 * 直接输出纯HTML.
	 */
	public void renderHtml(String text, HttpServletResponse response) {
		render(text, "text/html", response);
	}

	/**
	 * 直接输出纯XML.
	 */
	public void renderXML(String text, HttpServletResponse response) {
		render(text, "text/xml", response);
	}
	
	/**
	 * 回调输出内容
	 * @param object
	 * @param response   
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-5-26
	 */
	public static void responseWriter(Object object, HttpServletResponse response) {
		CPSUtil.renderHtml(JsonHelper.encodeObject2Json(object), response);
	}
	
}
