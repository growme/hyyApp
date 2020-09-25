package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.api.entity.SbCustomtask;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.dao.base.BaseDao;

import cn.ffcs.memory.BeanListHandler;

@Repository("sbCustomtaskDao")
public class SbCustomtaskDao extends BaseDao<SbCustomtask> {

	public List<SbCustomtask> findSbCustomtask(Long userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT a.*,(SELECT  a.pay_num - COUNT(1) FROM sb_money_count b WHERE a.id = b.m_type ");
		sql.append(" and user_id = ? ");
		params.add(userId);
		sql.append(" and create_time >= ? ");
		params.add(DateUtils.formatDate(DateUtils.getDateStart(new Date()), "yyyy-MM-dd HH:mm:ss"));
		sql.append(" and create_time <= ? ");
		params.add(DateUtils.formatDate(DateUtils.getDateEnd(new Date()), "yyyy-MM-dd HH:mm:ss"));
		sql.append(" ) AS taskNum FROM sb_customtask a where  a.status = 0 ");
		List<SbCustomtask> list = memory.query(sql, new BeanListHandler<SbCustomtask>(SbCustomtask.class), params);
		return list;
	}

}
