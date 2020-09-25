package com.ccnet.admin.bh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.admin.bh.dao.SbCustomtaskDao;
import com.ccnet.admin.bh.entity.SbCustomtask;
import com.ccnet.admin.bh.service.SbCustomtaskService;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemCode;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("sbCustomtaskService")
public class SbCustomtaskServiceImpl extends BaseServiceImpl<SbCustomtask> implements SbCustomtaskService {

	@Autowired
	SbCustomtaskDao sbCustomtaskDao;

	/**
	 * 分页查询字典
	 * 
	 * @param sc
	 * @param page
	 * @return
	 */
	public Page<SbCustomtask> findSbCustomtaskByPage(SbCustomtask sc, Page<SbCustomtask> page, Dto paramDto) {
		return sbCustomtaskDao.findSbCustomtaskByPage(sc, page, paramDto);
	}

	@Override
	public SbCustomtask findSbCustomtaskByID(Integer id) {
		SbCustomtask customtask = new SbCustomtask();
		customtask.setId(id);
		return sbCustomtaskDao.find(customtask);
	}

	@Override
	protected BaseDao<SbCustomtask> getDao() {
		return this.sbCustomtaskDao;
	}

	@Override
	public boolean saveSbCustomtask(SbCustomtask customtask) {
		if (insert(customtask) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean editSbCustomtask(SbCustomtask sc) {
		if (update(sc, "id") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除字典
	 * 
	 * @param sc
	 * @return
	 */
	public boolean trashSbCustomtask(SbCustomtask sc) {
		int rst = delete(sc);
		if (rst > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean trashSbCustomtaskList(List<SbCustomtask> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

}
