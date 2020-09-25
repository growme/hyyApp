package com.ccnet.cps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.IpLocationDao;
import com.ccnet.cps.entity.IpLocation;
import com.ccnet.cps.service.IpLocationService;

@Service("ipLocationService")
public class IpLocationServiceImpl extends BaseServiceImpl<IpLocation> implements IpLocationService {

	@Autowired
	IpLocationDao ipLocationDao;

	@Override
	protected BaseDao<IpLocation> getDao() {
		return ipLocationDao;
	}

	@Override
	public IpLocation getIpLocation(String ip) {
		String[] strs = ip.split("\\.");
		Long ipNum = 0L;
		for (String str : strs) {
			ipNum = Long.parseLong(str) | ipNum << 8L;
		}
		return ipLocationDao.getIpLocation(ipNum + "");
	}

}
