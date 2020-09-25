package com.ccnet.cps.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbContentPicDao;
import com.ccnet.cps.entity.SbContentPic;
import com.ccnet.cps.service.SbContentPicService;

/**
 * 图片业务
 * ClassName: SbContentPicService 
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-9-5
 */
@Service("sbContentPicService")
public class SbContentPicServiceImpl extends BaseServiceImpl<SbContentPic> implements SbContentPicService {

	
	@Autowired
	private SbContentPicDao contentPicDao;
	
	/**
	 * 获取图片
	 * @param pic
	 * @return List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbContentPic> findContentPics(SbContentPic pic){
		return contentPicDao.findContentPics(pic);
	}
	
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
	public SbContentPic findContentPics(String cotnentID,String picPath) {
		SbContentPic contentPic = new SbContentPic();
		contentPic.setContentId(cotnentID);
		contentPic.setContentPic(picPath);
		return contentPicDao.find(contentPic);
	}
	
	/**
	 * 获取产品对应的图片
	 * @param getContentCode
	 * @return List<SbContentPic>  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-9-5
	 */
	public List<SbContentPic> findPicsByContentID(String getContentCode){
		return contentPicDao.findPicsByContentID(getContentCode);
	}
	
	/**
	 * 保存图片
	 * @param pic
	 * @return
	 */
	public boolean saveSbContentPic(SbContentPic pic){
		return contentPicDao.saveSbContentPic(pic);
	}
	
	/**
	 * 修改图片
	 * @param pic
	 * @return
	 */
	public boolean editSbContentPic(SbContentPic pic){
		return contentPicDao.editSbContentPic(pic);
	}
	
	/**
	 * 删除图片
	 * @param pic
	 * @return
	 */
	public boolean trashSbContentPic(SbContentPic pic){
		return contentPicDao.trashSbContentPic(pic);
	}
	
	/**
	 * 批量删除图片
	 * @param list
	 * @return
	 */
	public boolean trashSbContentPicList(List<SbContentPic> list){
		return contentPicDao.trashSbContentPicList(list);
	}
	
	@Override
	protected BaseDao<SbContentPic> getDao() {
		return this.contentPicDao;
	}

}
