package com.ccnet.jpz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpUserCollectDao;
import com.ccnet.jpz.entity.JpUserCollect;
import com.ccnet.jpz.service.JpUserCollectService;

@Service("jpUserCollectService")
public class JpUserCollectServiceImpl extends BaseServiceImpl<JpUserCollect> implements JpUserCollectService {

	@Autowired
	private JpUserCollectDao JpUserCollectDao;

	@Override
	protected BaseDao<JpUserCollect> getDao() {
		return JpUserCollectDao;
	}

	@Override
	public Page<JpUserCollect> findByPage(JpUserCollect table, Page<JpUserCollect> page, Dto pdDto) {
		return JpUserCollectDao.findByPage(table, page, pdDto);
	}

	@Override
	public List<JpUserCollect> getListByIds(List<Integer> Ids) {
		return JpUserCollectDao.getListByIds(Ids);
	}

	@Override
	public JpUserCollect getByID(Integer Id) {
		return JpUserCollectDao.getByID(Id);
	}

	@Override
	public boolean trashList(List<JpUserCollect> list) {
		return JpUserCollectDao.trashList(list);
	}

}
