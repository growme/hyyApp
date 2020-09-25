package com.ccnet.core.common.utils.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.FileUtil;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.UuidUtil;

public class HtmlUtils {
	
	private static final Logger logger = Logger.getLogger(HtmlUtils.class);

	/**
	 * html转议 html转字符串
	 * @descript
	 * @param content
	 */
	public static String htmlToString(String content) {
		if (content == null)
			return "";
		String html = content;
		html = html.replace("'", "&apos;");
		html = html.replaceAll("&", "&amp;");
		html = html.replace("\"", "&quot;"); // "
		html = html.replace("\t", "&nbsp;&nbsp;");// 替换跳格
		html = html.replace(" ", "&nbsp;");// 替换空格
		html = html.replace("<", "&lt;");
		html = html.replaceAll(">", "&gt;");
		return html;
	}

	/**
	 * html转议 字符串转html
	 * @descript
	 * @param content
	 * @return
	 */
	public static String stringToHtml(String content) {
		if (content == null)
			return "";
		String html = content;
		html = html.replace("&apos;", "'");
		html = html.replaceAll("&amp;", "&");
		html = html.replace("&quot;", "\""); // "
		html = html.replace("&nbsp;&nbsp;", "\t");// 替换跳格
		html = html.replace("&nbsp;", " ");// 替换空格
		html = html.replace("&lt;", "<");
		html = html.replaceAll("&gt;", ">");
		return html;
	}
	   
    /** 
     *  
     * 根据图片的外网地址下载图片到本地硬盘的filePath 
     * @param filePath 本地保存图片的文件路径 
     * @param imgUrl 图片的外网地址 
     * @throws UnsupportedEncodingException  
     * @return 相对的本地url地址
     * @throws Exception 
     */  
    public static String downImages(String rootPath, String absPath, String fileName, String suffix, String imgUrl) throws Exception {  
    	if (!rootPath.endsWith(File.separator)) {
    		rootPath = rootPath + File.separator;
    	}
    	if (!absPath.endsWith(File.separator)) {
    		absPath = absPath + File.separator;
    	}
    	//创建文件目录  
    	File files = new File(rootPath + absPath);  
    	if (!files.exists()) {  
    		files.mkdirs();  
    	}  
    	HttpURLConnection connection = null;
    	FileOutputStream out = null;
    	InputStream is = null;
    	GZIPInputStream gzin = null;
        try {
            //获取下载地址  
            URL url = new URL(imgUrl);  
            //链接网络地址  
            connection = (HttpURLConnection)url.openConnection();  
            connection.setReadTimeout(30 * 1000);
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
            //获取链接的输出流  
            is = connection.getInputStream();  
            String acceptEncoding = "";
    		if (connection.getHeaderField("Content-Encoding") != null) {
    			acceptEncoding = connection.getHeaderField("Content-Encoding");
    		}
    		gzin = null;
    		if (acceptEncoding.indexOf("gzip") != -1) {
    			gzin = new GZIPInputStream(is);
    		}
    		
            //创建文件，fileName为编码之前的文件名  
            File file = new File(rootPath + absPath + fileName + suffix);  
            //根据输入流写入文件  
            out = new FileOutputStream(file);  
            if (gzin != null) {
            	int i = 0;  
	            while((i = gzin.read()) != -1){  
	                out.write(i);  
	            }
            } else {
            	int i = 0;  
	            while((i = is.read()) != -1){  
	                out.write(i);  
	            }  
            }
            out.flush();
        } catch (Exception e) {  
        	logger.error(e.getMessage(), e);
        	throw e;
        } finally {
        	if (out != null) {
        		try {
					out.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}  
        	}
        	if (gzin != null) {
        		try {
					gzin.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}  
        	}
        	if (is != null) {
        		try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}  
        	}
        	if (connection != null) {
        		connection.disconnect();
        	}
        }
        absPath = filePathToUrlPath(absPath);
        absPath = absPath.startsWith("/") ? absPath : ("/" + absPath);
        return absPath  + fileName + suffix;
    } 
    
    public static String filePathToUrlPath(String filePath) {
    	return StringUtils.replace(filePath, File.separator, "/");
    }
    
    
    /**
     * 下载文案里面的图片
     * @param element dom节点
     * @param folder 文件夹
     * @throws Exception   
     * void  
     * @throws
     * @author Jackie Wang
     * @date 2017-10-16
     */
    public static void downloadImg(Element element, String folder) throws Exception {
		String suffix = ".jpeg";
		String tmp = element.attr("data-type");
		if (StringUtils.isNotBlank(tmp)) {
			suffix = "." + tmp;
		}
		//处理class="lazy"
		element.attr("class","lazy");
		String src = element.attr("src");
		String newUrl = null;
		if (StringUtils.isNotBlank(src) && (StringUtils.startsWith(src, "http") || StringUtils.startsWith(src, "https"))) {
			if (StringUtils.isBlank(newUrl)) {
				src = formatWxSrc(src);
				newUrl = HtmlUtils.downImages(CPSUtil.getContainPath(), getAbsPath(folder), UuidUtil.get32UUID(), suffix, src);
			}
			element.attr("src", newUrl);
			element.attr("data-original", newUrl);
		}else{
			src = element.attr("data-src");
			if (StringUtils.isNotBlank(src) && (StringUtils.startsWith(src, "http") || StringUtils.startsWith(src, "https"))) {
				if (StringUtils.isBlank(newUrl)) {
					src = formatWxSrc(src);
					newUrl = HtmlUtils.downImages(CPSUtil.getContainPath(), getAbsPath(folder), UuidUtil.get32UUID(), suffix, src);
				}
				element.attr("data-src", newUrl);
				element.attr("data-original", newUrl);
			}
		}
	}
    
    
    /**
     * 下载文案里面的图片
     * @param element dom节点
     * @param folder 文件夹
     * @throws Exception   
     * void  
     * @throws
     * @author Jackie Wang
     * @date 2017-10-16
     */
    public static void downloadImg2(Element element, String folder,Integer userId,List<String> imgList) throws Exception {
		String suffix = ".jpeg";
		String tmp = element.attr("data-type");
		if (StringUtils.isNotBlank(tmp)) {
			suffix = "." + tmp;
		}
		//处理class="lazy"
		element.attr("class","lazy");
		String src = element.attr("src");
		String newUrl = null;
		String copyUrl = null;
		if (StringUtils.isNotBlank(src) && (StringUtils.startsWith(src, "http") || StringUtils.startsWith(src, "https"))) {
			if (StringUtils.isBlank(newUrl)) {
				src = formatWxSrc(src);
				newUrl = HtmlUtils.downImages(CPSUtil.getContainPath(), getAbsPath(folder), UuidUtil.get32UUID(), suffix, src);
			}
			
			if(tmp.equalsIgnoreCase("png") || tmp.equalsIgnoreCase("jpeg") || tmp.equalsIgnoreCase("jpg")){
				if(imgList.size()<4){
					copyUrl = getCopyFileUrl(newUrl,userId,suffix);
					FileUtil.copyFile(CPSUtil.getContainPath() + newUrl,CPSUtil.getContainPath() + copyUrl);
					imgList.add(copyUrl);
				}
			}
			element.attr("src", newUrl);
			element.attr("data-src", newUrl);
		} else {
			src = element.attr("data-src");
			if (StringUtils.isNotBlank(src) && (StringUtils.startsWith(src, "http") || StringUtils.startsWith(src, "https"))) {
				if (StringUtils.isBlank(newUrl)) {
					src = formatWxSrc(src);
					newUrl = HtmlUtils.downImages(CPSUtil.getContainPath(), getAbsPath(folder), UuidUtil.get32UUID(), suffix, src);
				}
				if(tmp.equalsIgnoreCase("png") || tmp.equalsIgnoreCase("jpeg") || tmp.equalsIgnoreCase("jpg")){
					if(imgList.size()<4){
						copyUrl = getCopyFileUrl(newUrl,userId,suffix);
						FileUtil.copyFile(CPSUtil.getContainPath() + newUrl,CPSUtil.getContainPath() + copyUrl);
						imgList.add(copyUrl);
					}
				}
				element.attr("src", newUrl);
				element.attr("data-src", newUrl);
			}
		}
	}
    
    
    /**
     * 获取复制文件路径
     * @param targetUrl
     * @param userId
     * @param suffix
     * @author Jackie Wang
     * @date 2017-10-24
     */
    private static String getCopyFileUrl(String targetUrl,Integer userId,String suffix){
    	String copyUrl = StringUtils.replace(targetUrl, "download", "image");
    	String strs[] = copyUrl.split("\\.");
    	String savePath = strs[0]+"-"+userId+"-"+UniqueID.getUniqueID(10, 0)+suffix;
    	CPSUtil.xprint("savePath="+savePath);
    	return savePath;
    }

	private static String formatWxSrc(String src) {
    	if (StringUtils.startsWith(src, "http") || StringUtils.startsWith(src, "https")) {
    		try {
    			URI uri = new URI(src);
				String query = uri.getQuery();
				if (StringUtils.isNotBlank(query)) {
					String[] querys = StringUtils.split(query, "&");
					if (ArrayUtils.isNotEmpty(querys)) {
						String str = null;
						for (int i = 0; i < querys.length; i++) {
							str = querys[i];
							if (StringUtils.startsWith(str, "tp") || StringUtils.startsWith(str, "wx_lazy")) {
								if (i > 0) {
									str = "&" + str;
								}
								src = StringUtils.replace(src, str, "");
							}
						}
					}
				}
			} catch (URISyntaxException e) {
				logger.error(e);
			}
    	}
    	return src;
    }
	
	
	/**
	 * 下载文件位置
	 * @param folder
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-10-24
	 */
	private static String getAbsPath(String folder) {
		return "upload" + File.separator + "download" + File.separator + folder;
	}
	
	
    public static void main(String[] args) throws Exception {
    	String rootPath = "E:\\apache-tomcat-7.0.21\\webapps";
    	String absPath = "upload\\download";
    	String fileName = "test";
    	String suffix = ".png";
    	String imgUrl = "http://mmbiz.qpic.cn/mmbiz_jpg/PaUM0vBcadprxp4UJMHTOiclg21D21ppdM6nmPgTAxPNpTPphVot67h7OpK0XWyibvOYIIzrdh8KSAPTlEaVYy7g/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1";
    	imgUrl = "http://mmbiz.qpic.cn/mmbiz/e3lz6MLovptcAVxJ4TgthdtAxNVWwpu95Nou0w1yaib7ptfnF7ZDgd7pmpdObg44aYIicKZOSl1YLz0FAYvPdAGQ/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1";
    	imgUrl = "http://mmbiz.qpic.cn/mmbiz/gB7yLNibDqW4eU2HZnjvmNh8icadHKqHj69C38iaLzFzw5gSRO14AGvo6eDFvuZuPLKRurib2qPadtR8GTDNIpZnrA/640?wxfrom=5&wx_lazy=1";
    	String url = downImages(rootPath, absPath, fileName, suffix, imgUrl);
		System.out.println("Hello word: " + url);
	}
}


