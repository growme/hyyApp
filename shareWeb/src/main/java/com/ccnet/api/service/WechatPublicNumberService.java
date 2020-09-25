package com.ccnet.api.service;

import com.ccnet.api.entity.WechatPublicNumber;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

public interface WechatPublicNumberService extends BaseService<WechatPublicNumber> {

	public Page<WechatPublicNumber> findWechatPublicNumberByPage(WechatPublicNumber sc, Page<WechatPublicNumber> page,
			Dto paramDto);

	public WechatPublicNumber getWechatPublicNumberById(Integer id);
}
