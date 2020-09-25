package com.ccnet.cps.service;

import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.IpLocation;

public interface IpLocationService extends BaseService<IpLocation> {

	public IpLocation getIpLocation(String ipCode);
}
