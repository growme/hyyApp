package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.cps.entity.SbAdvertPic;

public interface SbAdvertPicService {

	/**
	 * 获取图片
	 * @param pic
	 * @return List<SbAPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbAdvertPic> findAdvertPics(SbAdvertPic pic);
	
	/** 
	 * 获取图片
	 * @param advertID
	 * @param picPath
	 * @return   
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-10-17
	 */
	public SbAdvertPic findAdvertPics(String advertID,String picPath);
	
	/**
	 * 获取产品对应的图片
	 * @param contentCode
	 * @return List<SbAdvertPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbAdvertPic> findPicsByAdvertID(String advertCode);
	
	/**
	 * 保存图片
	 * @param pic
	 * @return
	 */
	public boolean saveSbAdvertPic(SbAdvertPic pic);
	
	/**
	 * 修改图片
	 * @param pic
	 * @return
	 */
	public boolean editSbAdvertPic(SbAdvertPic pic);
	
	/**
	 * 删除图片
	 * @param pic
	 * @return
	 */
	public boolean trashSbAdvertPic(SbAdvertPic pic);
	
	/**
	 * 批量删除图片
	 * @param list
	 * @return
	 */
	public boolean trashSbAdvertPicList(List<SbAdvertPic> list);
}
