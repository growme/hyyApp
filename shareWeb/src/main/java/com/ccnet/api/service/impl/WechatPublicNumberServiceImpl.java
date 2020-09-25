package com.ccnet.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.WechatPublicNumberDao;
import com.ccnet.api.entity.WechatPublicNumber;
import com.ccnet.api.service.WechatPublicNumberService;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;

@Service("wechatPublicNumberService")
public class WechatPublicNumberServiceImpl extends BaseServiceImpl<WechatPublicNumber>
		implements WechatPublicNumberService {

	@Autowired
	WechatPublicNumberDao wechatPublicNumberDao;

	/**
	 * 分页查询字典
	 * 
	 * @param sc
	 * @param page
	 * @return
	 */
	public Page<WechatPublicNumber> findWechatPublicNumberByPage(WechatPublicNumber sc, Page<WechatPublicNumber> page,
			Dto paramDto) {
		return wechatPublicNumberDao.findWechatPublicNumberByPage(sc, page, paramDto);
	}

	@Override
	protected BaseDao<WechatPublicNumber> getDao() {
		return wechatPublicNumberDao;
	}

	@Override
	public WechatPublicNumber getWechatPublicNumberById(Integer id) {
		WechatPublicNumber number = new WechatPublicNumber();
		number.setId(id);
		return wechatPublicNumberDao.find(number);
	}

}
