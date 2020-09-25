package com.ccnet.jpz.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.jpz.entity.JpAdState;

public interface JpAdStateService extends BaseService<JpAdState> {

	public Page<JpAdState> findByPage(JpAdState table, Page<JpAdState> page, Dto pdDto);

	public JpAdState getByID(Integer Id);

	public boolean trashList(List<JpAdState> list);
}
