package com.ccnet.api.service;

import java.util.List;

import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;

public interface NewsService {

	public int addSbContentInfoByCode(String code, Integer colId);

	public int addSbContentInfoVideoByCode(String code, Integer colId);

	List<SbContentInfo> findSbContentInfoByPage(SbContentInfo contentInfo, Page<SbContentInfo> page);
}
