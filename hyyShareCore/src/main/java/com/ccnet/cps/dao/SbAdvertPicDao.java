package com.ccnet.cps.dao;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.FileUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbAdvertPic;

@Repository("sbAdvertPicDao")
public class SbAdvertPicDao extends BaseDao<SbAdvertPic> {

	/**
	 * 获取广告图片
	 * @param pic
	 * @return List<SbAdvertPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbAdvertPic> findAdvertPics(SbAdvertPic pic) {
		return findList(pic);
	}
	
	/**
	 * 获取广告对应的图片
	 * @param advertCode
	 * @return List<SbAdvertPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbAdvertPic> findPicsByAdvertCode(String advertCode) {
		List<SbAdvertPic> pics = null;
		if(CPSUtil.isNotEmpty(advertCode)){
			SbAdvertPic pic = new SbAdvertPic();
			pic.setAdvertId(advertCode);
			pics = findAdvertPics(pic);
		}
		return pics;
	}
	
	/**
	 * 保存广告图片
	 * @param pic
	 * @return
	 */
	public boolean saveSbAdvertPic(SbAdvertPic pic) {
		if(insert(pic)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改广告图片
	 * @param pic
	 * @return
	 */
	public boolean editSbAdvertPic(SbAdvertPic pic) {
		if(update(pic, "picId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除广告图片
	 * @param pic
	 * @return
	 */
	public boolean trashSbAdvertPic(SbAdvertPic pic) {
		if(delete(pic)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量删除广告图片
	 * @param list
	 * @return
	 */
	public boolean trashSbAdvertPicList(List<SbAdvertPic> list) {
		int rst [] = deleteBatch(list);
		if(rst.length>0 && rst.length==list.size()){
			return true;
		}else{
			return false;
		}
	} 
	
	
	/**
	 * 根据广告唯一编码获取图片
	 * @param advertCode
	 * @return
	 */
	public List<SbAdvertPic> findPicsByAdvertId(String advertCode) {
		SbAdvertPic spic = new SbAdvertPic();
		spic.setAdvertId(advertCode);
		return findList(spic);
	}
	
	
	/**
	 * 删除广告所有图片
	 * @param contentCode
	 * @return
	 */
	public boolean trashPicByAdvertId(String advertCode) {
		//获取文章所有图片
		List<SbAdvertPic> plist = findPicsByAdvertId(advertCode);
		if(CPSUtil.listNotEmpty(plist)){
			for (SbAdvertPic spic : plist) {
				//文件保存路径
				String savePath = CPSUtil.getContainPath() + spic.getAdvertPic();
				CPSUtil.xprint("path="+savePath);
				FileUtil.deletePicFile(savePath);
				trashSbAdvertPic(spic);
			}
		}
		return true;
	}
	
	
	/**
	 * 删除广告图片
	 * @param pic
	 * @return
	 */
	public boolean trashAdvertPicInfo(SbAdvertPic pic) {
		boolean temp = false;
		//先删除物理图片
		String containPath = CPSUtil.getContainPath();//webapps
		String savePath = pic.getAdvertPic();
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
