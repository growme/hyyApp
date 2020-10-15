package com.ccnet.admin.bh.service;

public interface NewsService {

	public int addSbContentInfoByCode(String code,Integer colId);
	
	public int addSbContentInfoVideoByCode(String code,Integer colId);

	void taskList();

	/**
	 * 删除过时新闻
	 */
    void deleteSbContentInfoByDate();
}
