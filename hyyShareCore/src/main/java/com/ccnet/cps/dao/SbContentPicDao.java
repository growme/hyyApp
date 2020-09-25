package com.ccnet.cps.dao;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.FileUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbContentPic;

/**
 * 图片图片操作
 * ClassName: SbContentPicDao 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-9-5
 */
@Repository("sbContentPicDao")
public class SbContentPicDao extends BaseDao<SbContentPic> {
	
	
	/**
	 * 获取图片
	 * @param pic
	 * @return List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbContentPic> findContentPics(SbContentPic pic) {
		return findList(pic);
	}
	
	/**
	 * 获取产品对应的图片
	 * @param contentCode
	 * @return List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbContentPic> findPicsByContentID(String contentCode) {
		List<SbContentPic> pics = null;
		if(CPSUtil.isNotEmpty(contentCode)){
			SbContentPic pic = new SbContentPic();
			pic.setContentId(contentCode);
			pics = findContentPics(pic);
		}
		return pics;
	}
	
	/**
	 * 保存图片
	 * @param pic
	 * @return
	 */
	public boolean saveSbContentPic(SbContentPic pic) {
		if(insert(pic)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改图片
	 * @param pic
	 * @return
	 */
	public boolean editSbContentPic(SbContentPic pic) {
		if(update(pic, "picId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除图片
	 * @param pic
	 * @return
	 */
	public boolean trashSbContentPic(SbContentPic pic) {
		if(delete(pic)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除图片
	 * @param list
	 * @return
	 */
	public boolean trashSbContentPicList(List<SbContentPic> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	} 
	
	
	/**
	 * 根据文章ID获取图片
	 * @param contentCode
	 * @return
	 */
	public List<SbContentPic> findPicsByContentId(String contentCode) {
		SbContentPic spic = new SbContentPic();
		spic.setContentId(contentCode);
		return findList(spic);
	}
	
	
	/**
	 * 删除文章所有图片
	 * @param contentCode
	 * @return
	 */
	public boolean trashPicByContentId(String contentCode) {
		//获取文章所有图片
		List<SbContentPic> plist = findPicsByContentId(contentCode);
		if(CPSUtil.listNotEmpty(plist)){
			for (SbContentPic spic : plist) {
				//文件保存路径
				String savePath = CPSUtil.getContainPath() + spic.getContentPic();
				CPSUtil.xprint("path="+savePath);
				FileUtil.deletePicFile(savePath);
				trashSbContentPic(spic);
			}
		}
		return true;
	}
	
	
	/**
	 * 删除图片
	 * @param pic
	 * @return
	 */
	public boolean trashContentPicInfo(SbContentPic pic) {
		boolean temp = false;
		//先删除物理图片
		String containPath = CPSUtil.getContainPath();//webapps
		String savePath = pic.getContentPic();
		if(CPSUtil.isNotEmpty(savePath)){
			File diskFile = new File(containPath+File.separator+savePath);
			if(diskFile.exists()){
			   FileUtil.delete(diskFile);
			   CPSUtil.xprint("删除图片【"+diskFile.getAbsolutePath()+"】成功了....");
			}
			//删除数据库记录
			if(delete(pic)>0){
				temp = true;
			}
		}
		return temp;
	}
}
