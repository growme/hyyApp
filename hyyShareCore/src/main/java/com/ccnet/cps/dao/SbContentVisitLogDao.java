package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.entity.SbContentVisitLogExtend;

/**
 * 文章指纹记录
 * 
 * @author jackie wang
 *
 */
@Repository("sbContentVisitLogDao")
public class SbContentVisitLogDao extends BaseDao<SbContentVisitLog> {

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param visitLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbContentVisitLog> findSbContentVisitLogByPage(SbContentVisitLog visitLog, Page<SbContentVisitLog> page,
			Dto pdDto) {
		// 获取参数
		String queryParam = pdDto.getAsString("queryParam");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");
		String memberId = pdDto.getAsString("memberId");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, SbContentVisitLog.class, visitLog);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(queryParam)) {
			appendWhere(sql);
			sql.append(" and (content_id like ? or request_ip like ? or hash_key like ? or visit_token like ?) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(memberId)) {
			appendWhere(sql);
			sql.append(" and user_id = ? ");
			params.add(memberId);
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and page_read_time >=? ");
			params.add(start_date);
			// params.add(start_date+" 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and page_read_time <=? ");
			params.add(end_date);
			// params.add(end_date+" 23:59:59");
		}

		// 加上排序
		sql.append(" order by page_read_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbContentVisitLog>(SbContentVisitLog.class), params, page);
		return page;
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
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time < ? ");
			params.add(endDate);
		}
		return super.count(sql, params);
	}

	/**
	 * 统计会员阅读
	 * 
	 * @param contentId
	 * @param userId
	 * @param limit
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbContentVisitLog> getContentEffectReadList(Integer contentId, Integer userId, Integer limit,
			Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where account_time is not null ");

		// 带上文章
		if (CPSUtil.isNotEmpty(userId)) {
			sql.append(" and user_id =? ");
			params.add(userId);
		}

		// 带上文章
		if (CPSUtil.isNotEmpty(contentId)) {
			sql.append(" and content_id =? ");
			params.add(contentId);
		}

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time < ? ");
			params.add(endDate);
		}

		// 加上排序
		sql.append(" order by visit_time desc limit 0,?");
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentVisitLog>(SbContentVisitLog.class), params);
	}

	/**
	 * 统计日志表
	 * 
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
	public List<SbContentVisitLogExtend> getContentVisitCount(Date startDate, Date endDate, Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"select visit_time,count(distinct request_ip) as ip_count,count(1) as click_count ,sum(if(account_time is not null, 1, 0)) as read_count,avg(touch_count) as touch_count,avg(coord_num) as coord_count,avg(expand_count) as expand_count from ")
				.append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time < ? ");
			params.add(endDate);
		}
		// 带上用户
		if (userId != null) {
			sql.append(" and user_id = ? ");
			params.add(userId);
		}
		sql.append(" group by visit_time ");
		return super.memory.query(sql, new BeanListHandler<SbContentVisitLogExtend>(SbContentVisitLogExtend.class),
				params);
	}

	/**
	 * 获取文章指纹
	 * 
	 * @param visitLog
	 * @return
	 */
	public SbContentVisitLog findSbContentVisitLog(SbContentVisitLog visitLog) {
		return find(visitLog);
	}

	/**
	 * 获取文章指纹
	 * 
	 * @param hashKey
	 * @return
	 */
	public SbContentVisitLog findContentVisitLogByHashKey(String hashKey) {
		SbContentVisitLog visitLog = new SbContentVisitLog();
		visitLog.setHashKey(hashKey);
		return findSbContentVisitLog(visitLog);
	}

	/**
	 * 通过visitToken获取文章
	 * 
	 * @param visitToken
	 * @return
	 */
	public SbContentVisitLog findContentVisitLogByVisitToken(String visitToken) {
		SbContentVisitLog visitLog = new SbContentVisitLog();
		visitLog.setVisitToken(visitToken);
		return findSbContentVisitLog(visitLog);
	}

	/**
	 * 验证是否已经计费
	 * 
	 * @param contentId
	 *            文章ID
	 * @param userId
	 *            推广人ID
	 * @param requestIp
	 *            访问IP
	 * @return true 计费 false 未计费
	 */
	public boolean checkVisitLogExisitMoney(Integer contentId, Integer userId, String requestIp) {
		boolean temp = false;
		SbContentVisitLog visitLog = new SbContentVisitLog();
		if (CPSUtil.isNotEmpty(contentId) && CPSUtil.isNotEmpty(userId) && CPSUtil.isNotEmpty(requestIp)) {
			visitLog.setContentId(contentId);
			visitLog.setUserId(userId);
			visitLog.setRequestIp(requestIp);
			visitLog = findSbContentVisitLog(visitLog);
			if (CPSUtil.isNotEmpty(visitLog) && CPSUtil.isNotEmpty(visitLog.getAccountState())) {
				if (StateType.Valid.getState().equals(visitLog.getAccountState())) {
					temp = true;// 存在相同记账信息则不计费
				}
			}
		}
		return temp;
	}

	/**
	 * 保存文章指纹
	 * 
	 * @param contentInfo
	 * @return
	 */
	public boolean saveSbContentVisitLog(SbContentVisitLog visitLog) {
		try {
			if (visitLog.getGoBack() == null) {
				visitLog.setGoBack(false);
			}
			if (insert(visitLog) > 0) {
				return true;
			} else {
				return false;
			}
		} catch (RuntimeException e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	/**
	 * 保存/更新文章指纹
	 * 
	 * @param contentInfo
	 * @return
	 */
	public boolean insertOrUpdateContentVisitLog(SbContentVisitLog visitLog) {
		int rowNum = super.memory.createOrUpdate(super.memory.getConnection(), SbContentVisitLog.class, visitLog,
				"hashKey", null);
		if (rowNum > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改文章指纹
	 * 
	 * @param visitLog
	 * @return
	 */
	public boolean editSbContentVisitLog(SbContentVisitLog visitLog) {
		if (update(visitLog, "hashKey") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文章指纹
	 * 
	 * @param visitLog
	 * @return
	 */
	public boolean trashSbContentVisitLog(SbContentVisitLog visitLog) {
		if (delete(visitLog) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 批量删除文章指纹
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashSbContentVisitLogList(List<SbContentVisitLog> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 清空日志
	 * 
	 * @return
	 */
	public boolean truncateSbContentVisitLog() {
		StringBuffer sql = new StringBuffer(" truncate table sb_content_visit_log ");
		List<Object> params = new ArrayList<Object>();
		int rst = memory.update(sql, params);
		if (rst >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取没有归属地的
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SbContentVisitLog> findNotIpLocationVisitLog(int start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where ip_location is null");
		// 加上排序
		sql.append(" order by visit_time desc limit ?,?");
		params.add(start);
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentVisitLog>(SbContentVisitLog.class), params);
	}

	/**
	 * 获取没有计费成功的记录
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<SbContentVisitLog> findNotAccountVisitLog(int start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where account_time is null");
		// 加上排序
		sql.append(" order by page_read_time asc limit ?,?");
		params.add(start);
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentVisitLog>(SbContentVisitLog.class), params);
	}

	/**
	 * 更新ip归属地
	 * 
	 * @param ipLocation
	 * @param province
	 * @param id
	 * @return
	 */
	public boolean updateIpLocation(String ipLocation, String province, Integer id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(
				"update " + getCurrentTableName() + " set ip_location =?,province=? where visit_id=?");
		params.add(ipLocation);
		params.add(province);
		params.add(id);
		return memory.update(sql, params) >= 0;
	}

	/**
	 * 更新心跳时间
	 * 
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @return
	 */
	public boolean updateHeartBeat(String visitToken, String lastHeartBeatTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName()
				+ " set last_heart_beat_time =?,heart_beat_count=TIMESTAMPDIFF(SECOND, page_read_time, ?) where visit_token=? ");
		params.add(lastHeartBeatTime);
		params.add(lastHeartBeatTime);
		params.add(visitToken);
		return memory.update(sql, params) >= 0;
	}

	/**
	 * 更新心跳时间
	 * 
	 * @param visitToken
	 * @param lastHeartBeatTime
	 * @param heartBeatCount
	 * @return
	 */
	public boolean updateHeartBeat(String visitToken, String lastHeartBeatTime, int heartBeatCount) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName()
				+ " t set t.last_heart_beat_time =? ,t.heart_beat_count=?  where t.visit_token=?");
		params.add(DateUtils.getDateTimeFormat(lastHeartBeatTime));
		params.add(heartBeatCount);
		params.add(visitToken);
		return memory.update(sql, params) >= 0;
	}

	/**
	 * 更新阅读参数
	 * 
	 * @param visitToken
	 * @param touch_count
	 * @param iaws_index
	 * @param iaws_coord_num
	 * @param heartTime
	 * @param heartBeatCount
	 * @return
	 */
	public boolean updateContentReadParam(String visitToken, String touch_count, String iaws_index,
			String iaws_coord_num, String heartTime, int heartBeatCount) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName()
				+ " t1 set t1.touch_count =?,t1.expand_count=?,t1.coord_num=?,t1.last_heart_beat_time =?,t1.heart_beat_count=? where t1.visit_token=?");
		params.add(touch_count);
		params.add(iaws_index);
		params.add(iaws_coord_num);
		params.add(DateUtils.getDateTimeFormat(heartTime));
		params.add(heartBeatCount);
		params.add(visitToken);
		return memory.update(sql, params) >= 0;
	}

	/**
	 * 更新阅读参数
	 * 
	 * @param visitToken
	 * @param touch_count
	 * @param iaws_index
	 * @param iaws_coord_num
	 * @return
	 */
	public boolean updateContentReadParam(String visitToken, String touch_count, String iaws_index,
			String iaws_coord_num) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("update " + getCurrentTableName()
				+ " set touch_count =?,expand_count=?,coord_num=? where visit_token=?");
		params.add(touch_count);
		params.add(iaws_index);
		params.add(iaws_coord_num);
		params.add(visitToken);
		return memory.update(sql, params) >= 0;
	}

	/**
	 * 统计访问日志情况
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbContentVisitLogExtend> countVisitContentLogExtend(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"select count(1) as count,count(request_ip) as ip_count,province,avg(touch_count) as touch_count,avg(coord_num) as coord_num,avg(expand_count) as expand_count from ")
				.append(getCurrentTableName());
		sql.append(" where 1=1");

		// 带上日期查询
		if (startDate != null) {
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time < ? ");
			params.add(endDate);
		}

		sql.append(" group by province ");
		return super.memory.query(sql, new BeanListHandler<SbContentVisitLogExtend>(SbContentVisitLogExtend.class),
				params);

	}

	/**
	 * 批量更新日志
	 * 
	 * @param logList
	 * @return
	 */
	public boolean updateBatchSbContentVisitLogList(List<SbContentVisitLog> logList) {
		return updateBatch(logList, "hashKey");
	}

	public boolean checkVisitLogExisitMoneyVisitTime(Date visitTime, String requestIp) {
		boolean temp = false;
		SbContentVisitLog visitLog = new SbContentVisitLog();
		if (CPSUtil.isNotEmpty(visitTime) && CPSUtil.isNotEmpty(requestIp)) {
			visitLog.setVisitTime(visitTime);
			visitLog.setRequestIp(requestIp);
			visitLog = findSbContentVisitLog(visitLog);
			if (CPSUtil.isNotEmpty(visitLog) && CPSUtil.isNotEmpty(visitLog.getAccountState())) {
				if (StateType.Valid.getState().equals(visitLog.getAccountState())) {
					temp = true;// 存在相同记账信息则不计费
				}
			}
		}
		return temp;
	}

	public Integer countVisitContentLogIpOrOpenIdDate(String ip, String openid) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select visit_id from ").append(getCurrentTableName());
		sql.append(" where 1=1 and account_state = 1");
		Date startDate = DateUtils.getDateStart(new Date());
		Date endDate = DateUtils.getDateEnd(new Date());
		// 带上日期查询
		if (startDate != null) {
			sql.append(" and visit_time >=? ");
			params.add(startDate);
		}
		if (endDate != null) {
			sql.append(" and visit_time <= ? ");
			params.add(endDate);
		}
		if (CPSUtil.isNotEmpty(openid)) {
			sql.append(" and open_id = ? ");
			params.add(openid);
		} else {
			sql.append(" and request_ip = ? ");
			params.add(ip);
		}
		return super.memory.query(sql, new BeanListHandler<SbContentVisitLog>(SbContentVisitLog.class), params).size();
	}

	public SbContentVisitLog countVisitFaistContent(Integer userId) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT user_id,content_id FROM `sb_share_log` WHERE user_id = ? ORDER BY share_id ASC LIMIT 1 ");
		params.add(userId);
		return super.memory.query(sql, new BeanHandler<SbContentVisitLog>(SbContentVisitLog.class), params);
	}
}
