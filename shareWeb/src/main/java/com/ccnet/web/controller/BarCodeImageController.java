package com.ccnet.web.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.barcode.QRCodeUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.image.MarkImageUtils;
import com.ccnet.core.common.utils.image.MarkLogoIcon;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;

/**
 * 获取二维码图片
 * @author Jackie Wang
 * @see 2016-11-09
 */
@Controller
@RequestMapping("/barcode/")
public class BarCodeImageController extends BaseController<Object> {
	
	
	@RequestMapping("/groupCode")
	  public void getWechatBarCode(HttpServletRequest req, HttpServletResponse resp)throws IOException {
		 Dto dto = CPSUtil.getParamAsDto();
		 MemberInfo memberInfo = getCurUser();
		 if(CPSUtil.isNotEmpty(memberInfo)){
			 try {
				 
				 String visitLink = dto.getAsString("url");
				 if(CPSUtil.isNotEmpty(visitLink)){
					 visitLink  = CPSUtil.decryptBased64(visitLink);
					 CPSUtil.xprint("visitLink==>"+visitLink);
					 //目标二维码
					 Random random = new Random();  
				     int rand = random.nextInt(10);
				     String srcImgPath = CPSUtil.getContainPath()+"/upload/image/siteimg/mb/barcode_"+rand+".jpg";
				     String targetPath = CPSUtil.getContainPath()+"/upload/image/siteimg/mb/";//输出路径
					 CPSUtil.xprint("srcImgPath==>"+srcImgPath);
					 String wechat_group =  CPSUtil.getParamValue(Const.CT_WECHAT_GROUP_LIST);
					 String group_title_size = CPSUtil.getParamValue(Const.CT_WECHAT_GROUP_TITLE_SIZE);
					 CPSUtil.xprint("wechat_group==>"+wechat_group);
					 if(CPSUtil.isNotEmpty(wechat_group)){
						 BufferedImage codeImg = QRCodeUtil.generateQRToStream(visitLink+"",520,520,2);
						 //输出文字到图片上
						 String imageType = "jpg";
						 String imageName = "wechatBarcode";
						 
						 String groups[] = wechat_group.split(",");
						 String word1 = groups[random.nextInt(groups.length)];
						 String date = CPSUtil.getDateByDay(-7,"M月d日");
						 String word2 = "该二维码7天内("+date+"前)有效，重新进入将更新";
						 
						 if(CPSUtil.isEmpty(group_title_size)){
							 group_title_size = "30";
						 }
						 CPSUtil.xprint("group_title_size==>"+group_title_size);
						 
						 Color color1 = new Color(39,39,39);
						 Color color2 = new Color(154,154,154);
						 int x1=260;int y1=400;int x2=100;int y2=1100;int size1=Integer.valueOf(group_title_size);int size2=24;
						 MarkImageUtils.markImageByMoreText(srcImgPath, targetPath, imageName, imageType, color1, color2, word1, x1, y1, word2, x2, y2, size1, size2);
						 //输出二维码到图片上
						 MarkLogoIcon.markByIconToStream(codeImg, targetPath + "/" + imageName +"." + imageType,null, 130, 530, resp.getOutputStream());
						 CPSUtil.xprint("输出二维码成功.....");
					 }
				 }
				
			} catch (Exception e) {
				CPSUtil.xprint("输出二维码出错.....");
				e.printStackTrace();
			}
		 }
	 }
	
	
	 @RequestMapping("/getcode")
	  public void getCode(HttpServletRequest req, HttpServletResponse resp)throws IOException {
		 Dto dto = CPSUtil.getParamAsDto();
		 MemberInfo memberInfo = getCurUser();
		 if(CPSUtil.isNotEmpty(memberInfo)){
			 try {
				 //获取当前主题
				 Integer x_index = 0;
				 Integer y_index = 0;
				 Integer height = 0;
				 String themePic = "";
				 String curTheme = CPSUtil.getParamValue("USER_DEFAULT_THEME");
				 if(CPSUtil.isEmpty(curTheme)){
					 themePic = "huabao.png";
					 x_index = 220;
					 y_index = 450;
					 height = 550;
				 }else{
					 x_index = 140;
					 y_index = 300;
					 height = 400;
					 themePic = curTheme+"/huabao.jpg";
				 }
				 
				 CPSUtil.xprint("themePic====>"+themePic);
				 
				 String visitCode = memberInfo.getVisitCode();
         		 String srcImgPath = CPSUtil.getContainPath()+"/upload/image/siteimg/"+themePic;
         		 CPSUtil.xprint("===>srcImgPath==>"+srcImgPath);
				BufferedImage codeImg = QRCodeUtil.generateQRToStream(getRecomUrl(visitCode),height,height,10);
				MarkLogoIcon.markByIconToStream(codeImg, srcImgPath,null, x_index, y_index,resp.getOutputStream());
				CPSUtil.xprint("输出二维码成功.....");
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
	 }
	 
	 @RequestMapping("/logoCode")
	  public void getLogoQRCode(HttpServletRequest req, HttpServletResponse resp)throws IOException {
		 Dto dto = CPSUtil.getParamAsDto();
		 Integer contentId = dto.getAsInteger("id");
		 if(CPSUtil.isNotEmpty(contentId)){
			 try {
				 SbContentInfo content = CPSUtil.getContentInfoById(contentId);
				 if(CPSUtil.isNotEmpty(content) && CPSUtil.isNotEmpty(content.getArticleUrl())){
        		    String logoPic = CPSUtil.getContainPath()+"/upload/image/siteimg/qrlogo.png";
        		    CPSUtil.xprint("===>logoPic==>"+logoPic);
				    QRCodeUtil.generateQRImageToStream(content.getArticleUrl(), logoPic, 600, 600, ".jpg", resp.getOutputStream());
				 }
				 CPSUtil.xprint("输出logo二维码成功.....");
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
	 }
	 
}
