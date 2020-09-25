package com.ccnet.cps.dao;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.IpLocation;

import cn.ffcs.memory.BeanHandler;

@Repository("ipLocationDao")
public class IpLocationDao extends BaseDao<IpLocation> {
	/**
	 * 获取IP归属地
	 * @param vcode
	 * @return
	 */
	public IpLocation getIpLocation(String vcode) {
		IpLocation memberInfo = memory.query(
				"SELECT * FROM ip_location WHERE begin <= " + vcode + " AND end >= " + vcode,
				new BeanHandler<IpLocation>(IpLocation.class));
		return memberInfo;
	}
}
