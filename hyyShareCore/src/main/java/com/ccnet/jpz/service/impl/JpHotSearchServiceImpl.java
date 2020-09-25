package com.ccnet.jpz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpHotSearchDao;
import com.ccnet.jpz.entity.JpHotSearch;
import com.ccnet.jpz.service.JpHotSearchService;

@Service("jpHotSearchService")
public class JpHotSearchServiceImpl extends BaseServiceImpl<JpHotSearch> implements JpHotSearchService {

	@Autowired
	private JpHotSearchDao jpHotSearchDao;

	@Override
	protected BaseDao<JpHotSearch> getDao() {
		return jpHotSearchDao;
	}

	@Override
	public JpHotSearch getByID(Integer Id) {
		return jpHotSearchDao.getByID(Id);
	}

	@Override
	public boolean trashList(List<JpHotSearch> list) {
		return jpHotSearchDao.trashList(list);
	}

	public List<JpHotSearch> getHotSearchList() {
		return jpHotSearchDao.getHotSearchList();
	}
	
	@Override
	public int updateOrInsertHotSearch(JpHotSearch jpHotSearch) {
		return jpHotSearchDao.updateOrInsertHotSearch(jpHotSearch);
	}
}
