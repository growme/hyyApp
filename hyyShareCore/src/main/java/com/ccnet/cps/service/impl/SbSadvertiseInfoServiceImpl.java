package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbSadvertInfoDao;
import com.ccnet.cps.entity.SbSadvertInfo;
import com.ccnet.cps.service.SbSadvertiseInfoService;

@Service("sbSadvertiseInfoService")
public class SbSadvertiseInfoServiceImpl extends BaseServiceImpl<SbSadvertInfo> implements SbSadvertiseInfoService{

	@Autowired
	private SbSadvertInfoDao sbSadvertInfoDao;

	public Page findSbSadvertiseInfoByPage(SbSadvertInfo sbSadvertInfo,Page<SbSadvertInfo> page, Dto pdDto) {
		Page SbSadvertiseInfoPage = sbSadvertInfoDao.findSbContentInfoByPage(sbSadvertInfo, page, pdDto);
		List<SbSadvertInfo> sadvertList = SbSadvertiseInfoPage.getResults();
		if(!CPSUtil.checkListBlank(sadvertList)){
			
			for (SbSadvertInfo sbSadverertInfo : sadvertList) 
			{
				/*userInfo = userMap.get(sbContentInfo.getUserId());
				sbContentInfo.setUserName(userInfo.getUserName());	
				columnInfo=sbColumnInfoMap.get(sbContentInfo.getColumnId());
				sbContentInfo.setColumnName(columnInfo.getColumnName());
				sbContentInfo.setContentTypeName(contentTypeMap.get(sbContentInfo.getContentType()));*/
			}
		}
		return SbSadvertiseInfoPage;
	}

	public SbSadvertInfo getSbAdvertInfoByID(Integer adId) {
		SbSadvertInfo sbSadvertInfo=sbSadvertInfoDao.getSbAdvertInfoByID(adId);
		return sbSadvertInfo;
	}

	public boolean saveSbAdvertInfo(SbSadvertInfo sbSadvertInfo) {
		boolean flag=false;
		if(CPSUtil.isNotEmpty(sbSadvertInfo.getAdId())){
			SbSadvertInfo old =sbSadvertInfoDao.getSbAdvertInfoByID(sbSadvertInfo.getAdId());
			sbSadvertInfo.setCreateTime(old.getCreateTime());
			flag=sbSadvertInfoDao.editSbAdvertInfo(sbSadvertInfo);
		}else{
			flag=sbSadvertInfoDao.saveSbAdvertInfo(sbSadvertInfo);
		}
		return flag;
	}

	public boolean trashSbAdvertInfo(SbSadvertInfo sbSadvertInfo) {
		int count=sbSadvertInfoDao.delete(sbSadvertInfo);
		if(count>0){
			return true;
		}
		return false;
	}

	public List<SbSadvertInfo> getRandomAdvert(int i) {
		List<SbSadvertInfo> randomlist=new ArrayList<SbSadvertInfo>();
		List<SbSadvertInfo> list=findList(new SbSadvertInfo());
		if(list.size()>i){
			Random random = new Random();
			for(int x=0;x<i;x++){
				int j= random.nextInt(list.size());
				randomlist.add(list.get(j));
			}
		}else{
		return list;
		}
		return randomlist;
	}
	
	protected BaseDao<SbSadvertInfo> getDao() {
		return sbSadvertInfoDao;
	}
}
