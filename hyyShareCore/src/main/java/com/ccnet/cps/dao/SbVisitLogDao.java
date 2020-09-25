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
import com.ccnet.cps.entity.SbVisitLog;

@Repository("sbVisitLogDao")
public class SbVisitLogDao extends BaseDao<SbVisitLog> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public int insert(SbVisitLog o) {
		int i = super.insert(o, null);
		return i;
	}

	public int insert(SbVisitLog o, String autoIncrementField) {
		int i = super.insert(o, autoIncrementField);
		return i;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param k
	 *            字段值
	 */
	public int delete(SbVisitLog o) {
		int i = super.delete(o);
		return i;
	}

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public int update(SbVisitLog o, String camelKey) {
		int i = super.update(o, camelKey);
		return i;
	}

	/**
	 * 批量删除一组对象
	 * 
	 * @param s
	 *            (主键)数组
	 */
	public int[] deleteBatch(List<SbVisitLog> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new int[0];
		}
		int[] i = super.deleteBatch(list);
		return i;
	}

	/**
	 * 获得对象列表
	 * 
	 * @param o
	 *            对象
	 * @return List
	 */
	public List<SbVisitLog> findList(SbVisitLog o) {
		List<SbVisitLog> list = super.findList(o);
		return list;
	}

	public SbVisitLog find(SbVisitLog o) {
		SbVisitLog SbVisitLog = super.find(o);
		return SbVisitLog;
	}

	public void findByPage(SbVisitLog o, Page<SbVisitLog> page) {
		super.findByPage(o, page);
	}

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param content
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<SbVisitLog> findSbVisitLogByPage(SbVisitLog content,
			Page<SbVisitLog> page, Dto pdDto) {
		// 获取参数
		String queryParam = pdDto.getAsString("queryParam");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params,
				SbVisitLog.class, content);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(queryParam)) {
			appendWhere(sql);
			sql.append(" and ( req_detail like ? or content_title like ? ) ");
			params.add("%" + queryParam + "%");
			params.add("%" + queryParam + "%");
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and visit_time >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and visit_time <=? ");
			params.add(end_date + " 23:59:59");
		}

		// 加上排序
		sql.append(" order by page_read_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<SbVisitLog>(
				SbVisitLog.class), params, page);
		return page;

	}

	public int count(Date startDate, Date endDate) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select count(1) from ").append(getCurrentTableName());
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
		return super.count(sql, params);
	}

	/**
	 * 获取单条信息
	 * 
	 * @param SbVisitLog
	 * @return
	 */
	public SbVisitLog findSbVisitLog(SbVisitLog SbVisitLog) {
		return find(SbVisitLog);
	}

	/**
	 * 保存
	 * 
	 * @param SbVisitLog
	 * @return
	 */
	public boolean saveSbVisitLog(SbVisitLog SbVisitLog) {
		if (insert(SbVisitLog) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改
	 * 
	 * @param SbVisitLog
	 * @return
	 */
	public boolean editSbVisitLog(SbVisitLog SbVisitLog) {
		if (update(SbVisitLog, "visitId") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除
	 * 
	 * @param SbVisitLog
	 * @return
	 */
	public boolean trashSbVisitLog(SbVisitLog SbVisitLog) {
		if (delete(SbVisitLog) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashSbVisitLogList(List<SbVisitLog> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	public int deleteAll() {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("delete from ").append(getCurrentTableName())
				.append(" where 1=1 ");
		return memory.update(sql, params);

	}

}
