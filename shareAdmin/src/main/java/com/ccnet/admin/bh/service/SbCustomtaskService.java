package com.ccnet.admin.bh.service;

import java.util.List;

import com.ccnet.admin.bh.entity.SbCustomtask;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

public interface SbCustomtaskService extends BaseService<SbCustomtask> {

	public Page<SbCustomtask> findSbCustomtaskByPage(SbCustomtask sc, Page<SbCustomtask> page, Dto paramDto);

	public SbCustomtask findSbCustomtaskByID(Integer id);

	public boolean saveSbCustomtask(SbCustomtask sbCustomtask);

	public boolean editSbCustomtask(SbCustomtask sc);

	public boolean trashSbCustomtask(SbCustomtask sc);

	boolean trashSbCustomtaskList(List<SbCustomtask> list);

}
