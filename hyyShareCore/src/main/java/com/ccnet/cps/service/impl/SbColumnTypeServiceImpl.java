package com.ccnet.cps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.dao.SbColumnTypeDao;
import com.ccnet.cps.entity.SbColumnType;
import com.ccnet.cps.service.SbColumnTypeService;

/**
 * 栏目类型管理
 * @author jackieWang
 *
 */
@Service("sbColumnTypeService")
public class SbColumnTypeServiceImpl implements SbColumnTypeService {
	
	@Autowired
	private SbColumnTypeDao sbColumnTypeDao;
	@Override
	public int insert(SbColumnType o) {
		return 0;
	}

	@Override
	public int delete(SbColumnType o) {
		return 0;
	}

	@Override
	public int[] deleteBatch(List<SbColumnType> os) {
		return null;
	}

	@Override
	public int update(SbColumnType o, String camelKey) {
		return 0;
	}

	@Override
	public List<SbColumnType> findList(SbColumnType o) {
		List l=sbColumnTypeDao.findList(o);
		return l;
	}
	
	@Override
	public SbColumnType find(SbColumnType o) {
		return null;
	}

	@Override
	public Page<SbColumnType> findByPage(SbColumnType o, Page<SbColumnType> page) {
		return null;
	}



}
