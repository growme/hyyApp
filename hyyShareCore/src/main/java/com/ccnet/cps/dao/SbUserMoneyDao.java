package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbUserMoneyExtend;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;

@Repository("sbUserMoneyDao")
public class SbUserMoneyDao extends BaseDao<SbUserMoney> {

	public Page<SbUserMoney> findSbUserMoneyByPage(SbUserMoney sbUserMoney, Page<SbUserMoney> page, Dto dtoParam) {
		// 日期过滤
		String end_date = dtoParam.getAsString("end_date");
		String start_date = dtoParam.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbUserMoney.class, sbUserMoney);
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
		super.queryPager(sql.toString(), new BeanListHandler<SbUserMoney>(SbUserMoney.class), params, page);
		return page;
	}

	/**
	 * 获取用户资金集合
	 * 
	 * @param sbUserMoney
	 * @param paramDto
	 * @return
	 */
	public List<SbUserMoney> findSbUserMoneyList(SbUserMoney sbUserMoney, Dto paramDto) {
		// 日期过滤
		String end_date = paramDto.getAsString("end_date");
		String start_date = paramDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbUserMoney.class, sbUserMoney);
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
		return memory.query(sql, new BeanListHandler<SbUserMoney>(SbUserMoney.class), params);
	}

	// 获取总收益列表根据总收益排序
	public Page<SbUserMoney> findUserMoneyPageByMoney(SbUserMoney sbUserMoney, Page<SbUserMoney> page, Dto dtoParam) {
		// 日期过滤
		String end_date = dtoParam.getAsString("end_date");
		String start_date = dtoParam.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbUserMoney.class, sbUserMoney);
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
		sql.append(" order by profits_money desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbUserMoney>(SbUserMoney.class), params, page);
		return page;
	}

	/**
	 * 有金额的，表示为活跃会员
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countActiveMember(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select COUNT(DISTINCT(user_id)) as count from ").append(getCurrentTableName());
		sql.append(" where tmoney > 0");

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
	 * 统计会员收益情况
	 * 
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbUserMoneyExtend> getCountMemeberTotalMoney(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) as count,user_id,sum(profits_money) as profits_money from ")
				.append(getCurrentTableName());
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

		sql.append(" group by user_id ");
		return super.memory.query(sql, new BeanListHandler<SbUserMoneyExtend>(SbUserMoneyExtend.class), params);

	}

	/**
	 * 
	 * 
	 * /** 保存用户资金
	 * 
	 * @param sbUserMoney
	 * @return
	 */
	public int insertOrUpdate(SbUserMoney sbUserMoney) {
		UserDailyEntity userDailyEntity = UserCache.getInstance().getUserCache(sbUserMoney.getUserId());
		synchronized (userDailyEntity) {
			return super.memory.createOrUpdate(super.memory.getConnection(), SbUserMoney.class, sbUserMoney, "umId",
					"profits_money = profits_money + VALUES(profits_money),tmoney = tmoney + VALUES(tmoney),pmoney = pmoney + VALUES(pmoney)");
		}
	}
}
