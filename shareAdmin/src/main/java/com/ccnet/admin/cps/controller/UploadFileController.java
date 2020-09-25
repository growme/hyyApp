package com.ccnet.admin.cps.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.html.HtmlUtils;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.entity.UserInfo;
/**
 * 上传文件公用类
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/file/")
public class UploadFileController extends AdminBaseController<Object> {
	
	
	
	@RequestMapping(value = "upload/single")  
	public void uplodSingleFile(HttpServletRequest request,HttpServletResponse response) {
		//获取用户信息
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		if (CPSUtil.isNotEmpty(userInfo)) {//登录才能上传
			Dto paramDto = getParamAsDto();
			String fieldKey = paramDto.getAsString("fk");
			CPSUtil.xprint("fieldKey="+fieldKey);
			//文件保存物理路径
			String savePath = CPSUtil.getContainPath() + File.separator + "upload";
			//文件保存数据库URL
			String saveUrl = File.separator + "upload";
			//定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image","gif,jpg,jpeg,png,bmp,ico,ICO,JPG");
			//最大文件大小
			long maxSize = 1000000;
			try {
				response.setContentType("text/html; charset=UTF-8");
				if(!ServletFileUpload.isMultipartContent(request)){
					outMsg(response,getError("请选择要上传文件。"));
					return;
				}
				//检查目录
				File uploadDir = new File(savePath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				//检查目录写权限
				if(!uploadDir.canWrite()){
					outMsg(response,getError("上传目录没有写权限。"));
					return;
				}

				String dirName = request.getParameter("dir");
				if (dirName == null) {
					dirName = "image";
				}
				if(!extMap.containsKey(dirName)){
					outMsg(response,getError("目录名不正确。"));
					return;
				}
				//创建文件夹
				savePath += File.separator + dirName + File.separator;
				saveUrl +=  File.separator + dirName + File.separator;
				File saveDirFile = new File(savePath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				
				savePath += "siteimg" + File.separator;
				saveUrl += "siteimg" + File.separator;
				File dirFile = new File(savePath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	            List<MultipartFile> fileList = multipartRequest.getFiles(fieldKey);  
	            for (MultipartFile mf : fileList) {
	                if(!mf.isEmpty()){
	                	String fileName = mf.getOriginalFilename();
	    			    CPSUtil.xprint("fileName===="+fileName);
	    				long fileSize = mf.getSize();
						//检查文件大小
						if(fileSize > maxSize){
							outMsg(response,getError("上传文件大小超过限制。"));
							return;
						}
						//检查扩展名
						String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
							outMsg(response,getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
							return;
						}
						//文件重新命名
						String newFileName = "";
						if("siteLogo".equals(fieldKey)){
							newFileName = "site_logo";
						}else if("siteIco".equals(fieldKey)){
							newFileName = "favicon_ico";
						}else if("backstageLogo".equals(fieldKey)){
							newFileName = "admin_logo";
						}else if("backstageIco".equals(fieldKey)){
							newFileName = "admin_favicon_ico";
						}else if("appLogo".equals(fieldKey)){
							newFileName = "app_wate";
						}
						
						newFileName += "." + fileExt;
						CPSUtil.xprint("文件名称："+newFileName);
						
						try{
							File uploadedFile = new File(savePath, newFileName);
							mf.transferTo(uploadedFile);
						}catch(Exception e){
							outMsg(response,getError("上传文件失败。"));
							return;
						}

						JSONObject obj = new JSONObject();
						obj.put("url", HtmlUtils.filePathToUrlPath(saveUrl + newFileName));
						outMsg(response,obj.toJSONString());
	                	
	                }  
	            }  
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			outMsg(response,getError(Const.NO_AUTHORIZED_MSG));
			return;
		}
	}
	
	
	@RequestMapping(value = "upload/multi")  
	public void uplodMultiFile(HttpServletRequest request,HttpServletResponse response) {
		//获取用户信息
		UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
		//if (UserType.isShopOperate(userInfo)) {//只有商户用户才能上传
			//文件保存物理路径
			String savePath = CPSUtil.getContainPath() + File.separator + "upload";
			//文件保存数据库URL
			String saveUrl = File.separator + "upload";
			//定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			//最大文件大小
			long maxSize = 1000000;
			try {
				response.setContentType("text/html; charset=UTF-8");
				if(!ServletFileUpload.isMultipartContent(request)){
					outMsg(response,getError("请选择要上传文件。"));
					return;
				}
				//检查目录
				File uploadDir = new File(savePath);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}
				//检查目录写权限
				if(!uploadDir.canWrite()){
					outMsg(response,getError("上传目录没有写权限。"));
					return;
				}

				String dirName = request.getParameter("dir");
				if (dirName == null) {
					dirName = "image";
				}
				if(!extMap.containsKey(dirName)){
					outMsg(response,getError("目录名不正确。"));
					return;
				}
				//创建文件夹
				savePath += File.separator + dirName + File.separator;
				saveUrl +=  File.separator + dirName + File.separator;
				File saveDirFile = new File(savePath);
				if (!saveDirFile.exists()) {
					saveDirFile.mkdirs();
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String ymd = sdf.format(new Date());
				savePath += ymd + File.separator + userInfo.getUserId() + File.separator;
				saveUrl += ymd + File.separator + userInfo.getUserId() + File.separator;
				File dirFile = new File(savePath);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}
				
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	            List<MultipartFile> fileList = multipartRequest.getFiles("goodExtPic");  
	            for (MultipartFile mf : fileList) {
	                if(!mf.isEmpty()){
	                	String fileName = mf.getOriginalFilename();
	    			    CPSUtil.xprint("fileName===="+fileName);
	    				long fileSize = mf.getSize();
						//检查文件大小
						if(fileSize > maxSize){
							outMsg(response,getError("上传文件大小超过限制。"));
							return;
						}
						//检查扩展名
						String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
							outMsg(response,getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
							return;
						}
						//文件重新命名
						String fileUUID = UuidUtil.get32UUID();
						//SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
						String newFileName = fileUUID + "-"+userInfo.getUserId()+ "-" + UniqueID.getUniqueID(10, 0) + "." + fileExt;
						try{
							File uploadedFile = new File(savePath, newFileName);
							mf.transferTo(uploadedFile);
						}catch(Exception e){
							outMsg(response,getError("上传文件失败。"));
							return;
						}

						JSONObject obj = new JSONObject();
						obj.put("url", HtmlUtils.filePathToUrlPath(saveUrl + newFileName));
						outMsg(response,obj.toJSONString());
	                	
	                }  
	            }  
			}catch (Exception e) {
				e.printStackTrace();
			}
		/*}else{
			outMsg(response,getError(Const.NO_AUTHORIZED_MSG));
			return;
		}*/
	}
	
	
	private String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", "很抱歉，"+message);
		return obj.toJSONString();
	}
	
	private void outMsg(HttpServletResponse response,String msg){
		 try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
