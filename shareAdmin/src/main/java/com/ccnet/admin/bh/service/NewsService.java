package com.ccnet.admin.bh.service;

public interface NewsService {

	public int addSbContentInfoByCode(String code,Integer colId);
	
	public int addSbContentInfoVideoByCode(String code,Integer colId);

	void taskList();

    void deleteContentListBeforeDate(String date);
}
