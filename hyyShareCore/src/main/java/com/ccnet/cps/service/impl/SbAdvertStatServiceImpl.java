package com.ccnet.cps.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbAdvertInfoDao;
import com.ccnet.cps.dao.SbAdvertStatDao;
import com.ccnet.cps.entity.SbAdvertInfo;
import com.ccnet.cps.entity.SbAdvertStat;
import com.ccnet.cps.service.SbAdvertStatService;

@Service("sbAdvertStatService")
public class SbAdvertStatServiceImpl extends BaseServiceImpl<SbAdvertStat> implements SbAdvertStatService {

	@Autowired
	private SbAdvertStatDao sbAdvertStatDao;
	
	@Autowired
	private SbAdvertInfoDao sbAdvertInfoDao;
	
	@Override
	public Page<SbAdvertStat> findSbAdvertStatByPage(SbAdvertStat advertStat, Page<SbAdvertStat> page, Dto pdDto) {
		
		Page<SbAdvertStat> advertStatPage = sbAdvertStatDao.findSbAdvertStatByPage(advertStat, page, pdDto);
		List<SbAdvertStat> statList = advertStatPage.getResults();
		if(!CPSUtil.checkListBlank(statList)){
			
			List<Integer> adIds =sbAdvertStatDao.getValuesFromField(statList, "adId");
			Map<Integer, SbAdvertInfo> adMap=sbAdvertInfoDao.getAdMapByIds(adIds);
			for (SbAdvertStat _advertStat : statList) {
				if(null!=adMap&&adMap.containsKey(_advertStat.getAdId())) {
					_advertStat.setAdvertInfo(adMap.get(_advertStat.getAdId()));
				}
			}
			
		}
		
		return advertStatPage;
	}

	@Override
	protected BaseDao<SbAdvertStat> getDao() {
		return sbAdvertStatDao;
	}

}
