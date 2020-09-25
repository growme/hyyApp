package com.ccnet.jpz.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.jpz.entity.JpNotice;

public interface JpNoticeService extends BaseService<JpNotice> {

	public Page<JpNotice> findByPage(JpNotice table, Page<JpNotice> page, Dto pdDto);

	public JpNotice getByID(Integer Id);

	public boolean trashList(List<JpNotice> list);
}
