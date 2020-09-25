package com.ccnet.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.entity.UserinfoExtend;

@Repository("userInfoExtendDao")
public class UserInfoExtendDao extends BaseDao<UserinfoExtend> {
	
	public int insert(UserinfoExtend o) {
		return super.memory.createOrUpdate(super.memory.getConnection(), UserinfoExtend.class, o, null, "login_count=login_count + VALUES(login_count),last_login_time=VALUES(last_login_time),last_login_ip=VALUES(last_login_ip)");
	}
	
}
