package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.api.entity.SbTaskInfo;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbAdvertVisitLog;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbMoneyCount;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;

@Repository("apiSbMoneyCount")
public class ApiSbMoneyCount extends BaseDao<SbMoneyCount> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public int insert(SbMoneyCount o) {
		int i = super.insert(o, null);
		return i;
	}

	public int insert(SbMoneyCount o, String autoIncrementField) {
		int i = super.insert(o, autoIncrementField);
		return i;
	}

	public SbMoneyCount getUserLastBx(Integer userId, String dateStr) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where user_id=?");
		params.add(userId);
		sql.append(" and create_time > ?");
		params.add(dateStr);
		sql.append(" and m_type = 10");
		SbMoneyCount taskInfo = memory.query(sql, new BeanHandler<SbMoneyCount>(SbMoneyCount.class), params);
		return taskInfo;
	}

	public SbMoneyCount getUserMoneyLastTime(Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where user_id=?");
		params.add(userId);
		sql.append(" and m_type = 10");
		sql.append(" order by create_time desc limit 1");
		SbMoneyCount taskInfo = memory.query(sql, new BeanHandler<SbMoneyCount>(SbMoneyCount.class), params);
		return taskInfo;
	}

	public Integer countShareLog(Integer userId) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date zero = calendar.getTime();
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT COUNT(1) FROM `sb_share_log` WHERE user_id = " + userId + " AND share_time > " + zero.getTime());
		return super.count(sql, params);
	}
}
