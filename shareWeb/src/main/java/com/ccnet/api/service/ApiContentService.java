package com.ccnet.api.service;

import java.util.List;

import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.entity.JpUserCollect;

public interface ApiContentService {
	public int updateContentInfo(Integer id,String type,String code);

	public List<SbContentInfo> findTypeByPage(JpUserCollect jpUserCollect, Page<JpUserCollect> arg1);

	List<SbContentInfo> findSbContentInfoByPage(SbContentInfo jpUserCollect, Page<SbContentInfo> page);
}
