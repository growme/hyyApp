package com.ccnet.jpz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpAdStateDao;
import com.ccnet.jpz.entity.JpAdState;
import com.ccnet.jpz.service.JpAdStateService;

@Service("jpAdStateService")
public class JpAdStateServiceImpl extends BaseServiceImpl<JpAdState> implements JpAdStateService {

	@Autowired
	private JpAdStateDao jpAdStateDao;

	@Override
	public Page<JpAdState> findByPage(JpAdState table, Page<JpAdState> page, Dto pdDto) {
		return jpAdStateDao.findByPage(table, page, pdDto);
	}

	@Override
	public JpAdState getByID(Integer Id) {
		JpAdState o = new JpAdState();
		o.setId(Id);
		return jpAdStateDao.find(o);
	}

	@Override
	protected BaseDao<JpAdState> getDao() {
		return jpAdStateDao;
	}

	@Override
	public boolean trashList(List<JpAdState> list) {
		return jpAdStateDao.trashList(list);
	}

}
