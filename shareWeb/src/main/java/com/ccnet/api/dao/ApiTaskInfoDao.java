package com.ccnet.api.dao;

import java.util.ArrayList;
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

@Repository("apiTaskInfoDao")
public class ApiTaskInfoDao extends BaseDao<SbTaskInfo> {

	/**
	 * 保存一个对象
	 * 
	 * @param o 对象
	 * @return 对象的ID
	 */
	public int insert(SbTaskInfo o) {
		int i = super.insert(o, null);
		return i;
	}

	public int insert(SbTaskInfo o, String autoIncrementField) {
		int i = super.insert(o, autoIncrementField);
		return i;
	}

	public SbTaskInfo getUserLastData(Integer userId, String dateStr) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where user_id=?");
		params.add(userId);
		sql.append(" and create_time > ?");
		params.add(dateStr);
		SbTaskInfo taskInfo = memory.query(sql, new BeanHandler<SbTaskInfo>(SbTaskInfo.class), params);
		return taskInfo;
	}

}
