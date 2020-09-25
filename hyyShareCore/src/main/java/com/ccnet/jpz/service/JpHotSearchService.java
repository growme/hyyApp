package com.ccnet.jpz.service;

import java.util.List;

import com.ccnet.core.service.base.BaseService;
import com.ccnet.jpz.entity.JpHotSearch;

public interface JpHotSearchService extends BaseService<JpHotSearch>{

	public JpHotSearch getByID(Integer Id);

	public boolean trashList(List<JpHotSearch> list);
	
	public List<JpHotSearch> getHotSearchList();
	
	int updateOrInsertHotSearch(JpHotSearch jpHotSearch);
}
