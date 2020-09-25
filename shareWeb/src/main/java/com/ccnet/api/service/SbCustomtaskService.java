package com.ccnet.api.service;

import java.util.List;

import com.ccnet.api.entity.SbCustomtask;
import com.ccnet.core.service.base.BaseService;

public interface SbCustomtaskService extends BaseService<SbCustomtask> {

	public List<SbCustomtask> findSbCustomtask(Long userId);

	Boolean saveUserTask(Integer userid, Integer taskId);
}
