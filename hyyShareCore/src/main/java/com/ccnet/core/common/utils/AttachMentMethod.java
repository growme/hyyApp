package com.ccnet.core.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class AttachMentMethod {
	HttpServletResponse response = CPSUtil.getResponse();// 获得response对象
	HttpServletRequest request = CPSUtil.getRequest();// 获得Request对象
	HttpSession session = request.getSession();// 获得Session对象
	
	Logger logger = Logger.getLogger(AttachMentMethod.class);
	String errorMsg="";//错误信息
	/**
	 * 下载附件方法
	 * @throws Exception 
	 */
   public void downloadFile(String path) throws Exception{
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
			logger.info("下载出现异常!用户中中断了下载操作！");
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
}
