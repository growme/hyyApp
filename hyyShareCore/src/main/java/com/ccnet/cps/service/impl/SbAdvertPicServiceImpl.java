package com.ccnet.cps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbAdvertPicDao;
import com.ccnet.cps.entity.SbAdvertPic;
import com.ccnet.cps.service.SbAdvertPicService;
@Service("sbAdvertPicService")
public class SbAdvertPicServiceImpl extends BaseServiceImpl<SbAdvertPic> implements SbAdvertPicService{

	@Autowired
	private SbAdvertPicDao sbAdvertPicDao;
	
	public List<SbAdvertPic> findAdvertPics(SbAdvertPic pic) {
		return sbAdvertPicDao.findAdvertPics(pic);
	}

	@Override
	public SbAdvertPic findAdvertPics(String advertID, String picPath) {
		SbAdvertPic sbAdvertPic=new SbAdvertPic();
		sbAdvertPic.setAdvertId(advertID);
		sbAdvertPic.setAdvertPic(picPath);
		return sbAdvertPicDao.find(sbAdvertPic);
	}

	@Override
	public List<SbAdvertPic> findPicsByAdvertID(String advertCode) {
		return sbAdvertPicDao.findPicsByAdvertCode(advertCode);
	}

	@Override
	public boolean saveSbAdvertPic(SbAdvertPic pic) {
		return sbAdvertPicDao.saveSbAdvertPic(pic);
	}

	@Override
	public boolean editSbAdvertPic(SbAdvertPic pic) {
		return sbAdvertPicDao.editSbAdvertPic(pic);
	}

	@Override
	public boolean trashSbAdvertPic(SbAdvertPic pic) {
		return sbAdvertPicDao.trashAdvertPicInfo(pic);
	}

	@Override
	public boolean trashSbAdvertPicList(List<SbAdvertPic> list) {
		return sbAdvertPicDao.trashSbAdvertPicList(list);
	}

	@Override
	protected BaseDao<SbAdvertPic> getDao() {
		return this.sbAdvertPicDao;
	}

	
}
