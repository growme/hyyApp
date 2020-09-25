package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbContentPic;

/**
 * 业务层接口
 * ClassName: SbContentPicService 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-9-5
 */
public interface SbContentPicService extends BaseService<SbContentPic> {
	
	/**
	 * 获取图片
	 * @param pic
	 * @return List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbContentPic> findContentPics(SbContentPic pic);
	
	/**
	 * 获取图片
	 * @param cotnentID
	 * @param picPath
	 * @return   
	 * List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-10-17
	 */
	public SbContentPic findContentPics(String cotnentID,String picPath);
	
	/**
	 * 获取产品对应的图片
	 * @param contentCode
	 * @return List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbContentPic> findPicsByContentID(String contentCode);
	
	/**
	 * 保存图片
	 * @param pic
	 * @return
	 */
	public boolean saveSbContentPic(SbContentPic pic);
	
	/**
	 * 修改图片
	 * @param pic
	 * @return
	 */
	public boolean editSbContentPic(SbContentPic pic);
	
	/**
	 * 删除图片
	 * @param pic
	 * @return
	 */
	public boolean trashSbContentPic(SbContentPic pic);
	
	/**
	 * 批量删除图片
	 * @param list
	 * @return
	 */
	public boolean trashSbContentPicList(List<SbContentPic> list);

}
