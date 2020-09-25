package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.PsFeedback;

import cn.ffcs.memory.BeanListHandler;

@Repository("psFeedbackDao")
public class PsFeedbackDao extends BaseDao<PsFeedback> {
	/**
	 * 分页查询日志(多过滤)
	 * 
	 * @param lg
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<PsFeedback> findPsFeedbackByPage(PsFeedback lg, Page<PsFeedback> page, Dto pdDto) {

		// 获取参数
		String memberId = pdDto.getAsString("memberId");
		String phone = pdDto.getAsString("phone");
		String status = pdDto.getAsString("status");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, PsFeedback.class, lg);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(phone)) {
			appendWhere(sql);
			sql.append(" and  phone like ? ");
			params.add("%" + phone + "%");
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(memberId)) {
			appendWhere(sql);
			sql.append(" and  member_id like ? ");
			params.add("%" + memberId + "%");
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(status)) {
			appendWhere(sql);
			sql.append(" and  status = ? ");
			params.add(status);
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
		super.queryPager(sql.toString(), new BeanListHandler<PsFeedback>(PsFeedback.class), params, page);
		return page;

	}
}
