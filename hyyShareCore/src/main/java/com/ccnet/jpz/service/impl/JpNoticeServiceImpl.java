package com.ccnet.jpz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.jpz.dao.JpNoticeDao;
import com.ccnet.jpz.entity.JpNotice;
import com.ccnet.jpz.service.JpNoticeService;

@Service("jpNoticeService")
public class JpNoticeServiceImpl extends BaseServiceImpl<JpNotice> implements JpNoticeService {

	@Autowired
	private JpNoticeDao jpNoticeDao;

	@Override
	public Page<JpNotice> findByPage(JpNotice table, Page<JpNotice> page, Dto pdDto) {
		return jpNoticeDao.findByPage(table, page, pdDto);
	}

	@Override
	public JpNotice getByID(Integer Id) {
		JpNotice o = new JpNotice();
		o.setId(Id);
		return jpNoticeDao.find(o);
	}

	@Override
	protected BaseDao<JpNotice> getDao() {
		return jpNoticeDao;
	}

	@Override
	public boolean trashList(List<JpNotice> list) {
		return jpNoticeDao.trashList(list);
	}

}
