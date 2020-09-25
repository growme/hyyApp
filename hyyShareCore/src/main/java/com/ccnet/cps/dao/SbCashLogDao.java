package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbCashLogExtend;

/**
 * 财务日志dao
 * 
 * @author zqy
 *
 */
@Repository("sbCashLogDao")
public class SbCashLogDao extends BaseDao<SbCashLog> {

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param SbCashLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbCashLog> findSbCashLogByPage(SbCashLog cashLog, Page<SbCashLog> page, Dto pdDto) {
		// 获取参数
		String queryParam = pdDto.getAsString("queryParam");
		String state = pdDto.getAsString("state");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");

		String statelist = pdDto.getAsString("staelist");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbCashLog.class, cashLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		if (CPSUtil.isNotEmpty(queryParam)) {
			appendWhere(sql);
			sql.append(" and pay_account like ? or account_name like ? ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}

		if (CPSUtil.isNotEmpty(statelist)) {
			appendWhere(sql);
			sql.append(" and state in (" + statelist + ") ");
		}

		if (CPSUtil.isNotEmpty(state)) {
			appendWhere(sql);
			sql.append(" and state =? ");
			params.add(state);
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and create_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		// 加上排序
		sql.append(" order by update_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbCashLog>(SbCashLog.class), params, page);
		return page;
	}

	/**
	 * 查询提现记录集合
	 * 
	 * @param cashLog
	 * @param paramDto
	 * @return
	 */
	public List<SbCashLog> findSbCashLogList(SbCashLog cashLog, Dto paramDto) {
		// 获取参数
		String queryParam = paramDto.getAsString("queryParam");
		String state = paramDto.getAsString("enabled");
		// 日期过滤
		String end_date = paramDto.getAsString("end_date");
		String start_date = paramDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbCashLog.class, cashLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and update_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and update_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		// 加上排序
		sql.append(" order by update_time desc ");
		return memory.query(sql, new BeanListHandler<SbCashLog>(SbCashLog.class), params);
	}

	/**
	 * 日期统计
	 * 
	 * @param startDate
	 * @param endDate
	 * @return int
	 * @throws @author
	 *             Jackie Wang
	 * @date 2017-8-10
	 */
	public int count(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and update_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and update_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}

	/**
	 * 根据ID获取提现集合
	 * 
	 * @param cashIds
	 * @return
	 */
	public List<SbCashLog> getCashListByIds(List<Integer> cashIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "uc_id", cashIds);
		List<SbCashLog> list = memory.query(sql, new BeanListHandler<SbCashLog>(SbCashLog.class), params);
		return list;
	}

	/**
	 * 转换集合数据
	 * 
	 * @param cashIds
	 * @return
	 */
	public Map<Integer, SbCashLog> getCashMapByIds(List<Integer> cashIds) {
		List<SbCashLog> cashLogs = getCashListByIds(cashIds);
		Map<Integer, SbCashLog> map = new HashMap<Integer, SbCashLog>(0);
		if (CollectionUtils.isNotEmpty(cashLogs)) {
			for (SbCashLog log : cashLogs) {
				map.put(log.getUcId(), log);
			}
		}
		return map;
	}

	/**
	 * 统计会员提现情况
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbCashLogExtend> getUserCashCountByState(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) as count,state,sum(cmoney) as cmoney from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and update_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and update_time < ? ");
			params.add(endDate);
		}

		sql.append(" group by state ");
		return super.memory.query(sql, new BeanListHandler<SbCashLogExtend>(SbCashLogExtend.class), params);

	}

	/**
	 * 更新审核信息
	 * 
	 * @param cashId
	 * @param state
	 * @param remark
	 * @return
	 */
	public int updateUserCashState(Integer cashId, Integer state, String remark) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("update ").append(getCurrentTableName())
				.append(" set state = ?,remark=?,update_time=? where uc_id =? ");
		params.add(state);
		params.add(remark);
		params.add(CPSUtil.getCurrentTime());
		params.add(cashId);
		return memory.update(sql, params);
	}

	/**
	 * 获取用户前三次成功提现信息
	 * 
	 * @return
	 */
	public List<SbCashLog> getCashListTopThree(Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT * FROM `sb_cash_log` WHERE user_id = ? AND state =2 ORDER BY uc_id ASC LIMIT 0,5");
		params.add(userId);
		return memory.query(sql, new BeanListHandler<SbCashLog>(SbCashLog.class), params);
	}

	/**
	 * 获取用户累计提现金额
	 * 
	 * @return
	 */
	public SbCashLog getCashGroupUser(Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT user_id,SUM(cmoney) AS cmoney FROM `sb_cash_log` WHERE user_id = ? AND state =2 GROUP BY user_id");
		params.add(userId);
		return memory.query(sql, new BeanHandler<SbCashLog>(SbCashLog.class), params);
	}
}
