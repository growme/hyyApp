package com.ccnet.cps.dao;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.DailyMoneyCount;
import com.ccnet.cps.localcache.UserDailyEntity;
import com.ccnet.cps.localcache.UserCache;

@Repository("dailyMoneyCountDao")
public class DailyMoneyCountDao extends BaseDao<DailyMoneyCount>{

	public int insertOrUpdate(DailyMoneyCount dailyMoneyCount) {
		UserDailyEntity userDailyEntity = UserCache.getInstance().getUserCache(dailyMoneyCount.getUserId());
		synchronized (userDailyEntity) {
			return super.memory.createOrUpdate(super.memory.getConnection(), DailyMoneyCount.class, dailyMoneyCount, null, "daily_max_money = VALUES(daily_max_money),daily_money = daily_money + VALUES(daily_money),actual_money = VALUES(actual_money)");
		}
	}
}
