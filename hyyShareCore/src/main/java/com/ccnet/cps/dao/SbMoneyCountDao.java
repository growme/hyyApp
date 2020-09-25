package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;
import cn.ffcs.memory.ColumnHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbMoneyCountExtend;

/**
 * 资金业务逻辑
 * 
 * @author JackieWang
 *
 */
@Repository("sbMoneyCountDao")
public class SbMoneyCountDao extends BaseDao<SbMoneyCount> {

	/**
	 * 分页查询资金
	 * 
	 * @param sbMoneyCount
	 * @param page
	 * @param dtoParam
	 * @return
	 */
	public Page<SbMoneyCount> findSbMoneyCountByPage(SbMoneyCount sbMoneyCount, Page<SbMoneyCount> page, Dto dtoParam) {
		// 获取参数
		String queryParam = dtoParam.getAsString("queryParam");
		// 日期过滤
		String end_date = dtoParam.getAsString("end_date");
		String start_date = dtoParam.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbMoneyCount.class, sbMoneyCount);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
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
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbMoneyCount>(SbMoneyCount.class), params, page);
		return page;
	}

	/**
	 * 查询资金集合
	 * 
	 * @param sbMoneyCount
	 * @param dtoParam
	 * @return
	 */
	public List<SbMoneyCount> findSbMoneyCountList(SbMoneyCount sbMoneyCount, Dto paramDto) {
		// 获取参数
		String queryParam = paramDto.getAsString("queryParam");
		// 日期过滤
		String end_date = paramDto.getAsString("end_date");
		String start_date = paramDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, SbMoneyCount.class, sbMoneyCount);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
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
		sql.append(" order by create_time desc ");

		return memory.query(sql, new BeanListHandler<SbMoneyCount>(SbMoneyCount.class), params);
	}

	/**
	 * 查询用户收益集合
	 * 
	 * @param contentId
	 * @param userId
	 * @param moneyType
	 * @return
	 */
	public List<SbMoneyCount> findSbMoneyCountList(Integer contentId, Integer userId, Integer moneyType) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName()).append("where 1=1 ");
		// 带上用户
		if (CPSUtil.isNotEmpty(userId)) {
			sql.append(" and user_id =? ");
			params.add(userId);
		}
		// 带上文章
		if (CPSUtil.isNotEmpty(contentId)) {
			sql.append(" and cotent_id =? ");
			params.add(contentId);
		}
		// 带上文章
		if (CPSUtil.isNotEmpty(moneyType)) {
			sql.append(" and m_type =? ");
			params.add(moneyType);
		}
		sql.append(" order by create_time desc ");
		return super.memory.query(sql, new BeanListHandler<SbMoneyCount>(SbMoneyCount.class), params);
	}

	/**
	 * 会员收益统计报表
	 * 
	 * @return
	 */
	public List<SbMoneyCountExtend> countMemberMoneyCountList(Dto paramDto, int pageNo, int limit) {

		String queryParam = paramDto.getAsString("queryParam");
		String end_date = paramDto.getAsString("end_date");
		String start_date = paramDto.getAsString("start_date");
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();

		sql.append(" select tmb.user_id,mi.login_account,tmb.* from ( ");
		sql.append(" select tb.user_id,");
		sql.append(" sum(if(tb.m_type=0, 1, 0)) as register_num,");
		sql.append(" sum(if(tb.m_type=0, money, 0)) as register_money,");
		sql.append(" sum(if(tb.m_type=1, 1, 0)) as transmit_num,");
		sql.append(" sum(if(tb.m_type=1, money, 0)) as transmit_money,");
		sql.append(" sum(if(tb.m_type=5, 1, 0)) as readawd_num,");
		sql.append(" sum(if(tb.m_type=5, money, 0)) as readawd_money,");
		sql.append(" sum(if(tb.m_type=2, 1, 0)) as deduct_num,");
		sql.append(" sum(if(tb.m_type=2, money, 0)) as deduct_money,");
		sql.append(" sum(if(tb.m_type=4, 1, 0)) as visitad_num,");
		sql.append(" sum(if(tb.m_type=4, money, 0)) as visitad_money,");
		sql.append(" sum(if(tb.m_type=3, 1, 0)) as redpacke_num,");
		sql.append(" sum(if(tb.m_type=3, money, 0)) as redpacke_money");
		sql.append(" from ( select v.* from (");
		sql.append(" select user_id,umoney as money,m_type,create_time from sb_money_count where 1=1 ");
		if (CPSUtil.isNotEmpty(start_date)) {
			sql.append(" and create_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			sql.append(" and create_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		sql.append(" union all ");

		sql.append(" select user_id,vmoney as money,4 as m_type,create_time from sb_visit_money where 1=1 ");
		if (CPSUtil.isNotEmpty(start_date)) {
			sql.append(" and create_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			sql.append(" and create_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		sql.append(" ) v ");
		if (CPSUtil.isNotEmpty(queryParam)) {
			sql.append(
					" inner join member_info m on v.user_id = m.member_id and (m.login_account like ? or m.member_name like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}

		sql.append(" ) tb group by tb.user_id ");
		sql.append(" ) tmb inner join member_info mi on tmb.user_id = mi.member_id ");
		sql.append(" limit ?,? ");
		params.add(pageNo);
		params.add(limit);

		return null;
	}

	/**
	 * 根据用户Id和被邀请人邀请码查询下线提成
	 * 
	 * @param userId
	 * @param visitCodes
	 * @param startDate
	 * @param endDate
	 * @return List<SbMoneyCount>
	 */
	public List<SbMoneyCount> findSbMoneyCountByVisitCodes(Integer userId, List<String> visitCodes, String startDate,
			String endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where user_id=?");
		params.add(userId);
		memory.in(sql, params, "and", "vcode", visitCodes);
		// 带上日期查询
		if (CPSUtil.isNotEmpty(startDate)) {
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(startDate + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(endDate)) {
			appendWhere(sql);
			sql.append(" and create_time <=? ");
			params.add(endDate + " 23:59:59");
		}
		sql.append(" order by create_time desc");
		List<SbMoneyCount> list = memory.query(sql, new BeanListHandler<SbMoneyCount>(SbMoneyCount.class), params);
		return list;
	}

	/**
	 * 查询当前用户今日是否已领取红包
	 * 
	 * @param userId
	 * @param type
	 *            奖励类型 0 注册金额 1 转发金额 2 下线提成 3 每日红包
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public SbMoneyCount findSbMoneyCountByType(Integer userId, Integer type, String startDate, String endDate) {
		SbMoneyCount moneyCount = null;
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where user_id=?");
		params.add(userId);

		sql.append(" and m_type=?");
		params.add(type);

		// 带上日期查询
		if (CPSUtil.isNotEmpty(startDate)) {
			appendWhere(sql);
			sql.append(" and create_time >=? ");
			params.add(startDate + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(endDate)) {
			appendWhere(sql);
			sql.append(" and create_time <=? ");
			params.add(endDate + " 23:59:59");
		}
		sql.append(" order by create_time desc");
		moneyCount = memory.query(sql, new BeanHandler<SbMoneyCount>(SbMoneyCount.class), params);
		return moneyCount;
	}

	/**
	 * 统计会员收益情况
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbMoneyCountExtend> getUserMoneyCountByMoneyType(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) as count,m_type,sum(umoney) as umoney from ").append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and create_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and create_time < ? ");
			params.add(endDate);
		}

		sql.append(" group by m_type ");
		return super.memory.query(sql, new BeanListHandler<SbMoneyCountExtend>(SbMoneyCountExtend.class), params);

	}

	/**
	 * 按天统计收益
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbMoneyCount> getUserMoneyCountByUserIdAndDate(Integer userId, String startDate, String endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.vcode,SUM(CASE WHEN umoney>0 THEN umoney WHEN umoney=0 THEN 0 END) AS umoney FROM (SELECT CONCAT(YEAR(create_time),'-',MONTH(create_time),'-',DAY(create_time)) AS vcode,umoney FROM sb_money_count WHERE 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and create_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and create_time < ? ");
			params.add(endDate);
		}

		if (userId != null) {
			sql.append(" and user_id = ? ");
			params.add(userId);
		}

		sql.append(" ) a GROUP BY a.vcode DESC");
		List<SbMoneyCount> list = memory.query(sql, new BeanListHandler<SbMoneyCount>(SbMoneyCount.class), params);
		return list;

	}

	public Integer findMaxId() {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select max(um_id) from ").append(getCurrentTableName());
		Integer count = 0;
		count = memory.query(sql, new ColumnHandler<Integer>(Integer.class), params);
		return count == null ? 0 : count;
	}

	public Integer findSbMoneyCountByTypeUserId(Integer userId, Integer type) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());

		sql.append(" where ").append(" user_id =? ");
		params.add(userId);
		sql.append(" And m_type=?");
		params.add(type);

		// 带上日期查询
		sql.append(" and create_time >=? ");
		params.add(DateUtils.formatDate(DateUtils.getDateStart(new Date()), "yyyy-MM-dd HH:mm:ss"));

		sql.append(" and create_time <=? ");
		params.add(DateUtils.formatDate(DateUtils.getDateEnd(new Date()), "yyyy-MM-dd HH:mm:ss"));
		Integer count = 0;
		count = memory.query(sql, new ColumnHandler<Integer>(Integer.class), params);
		return count == null ? 0 : count;
	}
}
