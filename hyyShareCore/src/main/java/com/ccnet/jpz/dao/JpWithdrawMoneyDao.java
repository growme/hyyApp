package com.ccnet.jpz.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.jpz.entity.JpWithdrawMoney;

import cn.ffcs.memory.BeanListHandler;

@Repository("jpWithdrawMoneyDao")
public class JpWithdrawMoneyDao extends BaseDao<JpWithdrawMoney> {

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param columnInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<JpWithdrawMoney> findByPage(JpWithdrawMoney table, Page<JpWithdrawMoney> page, Dto pdDto) {
		// 获取参数
		Integer roleType = pdDto.getAsInteger("roleType");
		Integer release = pdDto.getAsInteger("release");
		Integer checkState = pdDto.getAsInteger("checkState");
		String activityName = pdDto.getAsString("activityName");
		Integer activityTop = pdDto.getAsInteger("activityTop");
		Integer whetherPublic = pdDto.getAsInteger("whetherPublic");
		// 日期过滤
		String end_time = pdDto.getAsString("end_time");
		String start_time = pdDto.getAsString("start_time");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, JpWithdrawMoney.class, table);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_time)) {
			appendWhere(sql);
			sql.append(" and start_time >=? ");
			params.add(start_time + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_time)) {
			appendWhere(sql);
			sql.append(" and end_time <=? ");
			params.add(end_time + " 23:59:59");
		}

		if (CPSUtil.isNotEmpty(roleType)) {
			appendWhere(sql);
			sql.append(" and role_type =? ");
			params.add(roleType);
		}

		if (CPSUtil.isNotEmpty(release)) {
			appendWhere(sql);
			sql.append(" and release =? ");
			params.add(release);
		}

		if (CPSUtil.isNotEmpty(checkState)) {
			appendWhere(sql);
			sql.append(" and check_state =? ");
			params.add(checkState);
		}
		if (CPSUtil.isNotEmpty(activityName)) {
			appendWhere(sql);
			sql.append(" and activity_name =? ");
			params.add(activityName);
		}
		if (CPSUtil.isNotEmpty(activityTop)) {
			appendWhere(sql);
			sql.append(" and activity_top =? ");
			params.add(activityTop);
		}
		if (CPSUtil.isNotEmpty(whetherPublic)) {
			appendWhere(sql);
			sql.append(" and whether_public =? ");
			params.add(whetherPublic);
		}
		
		// 加上排序
		sql.append(" order by create_time desc ");
		super.queryPager(sql.toString(), new BeanListHandler<JpWithdrawMoney>(JpWithdrawMoney.class), params, page);
		return page;
	}

	/**
	 * 获取单个
	 * 
	 * @param columnId
	 * @return
	 */
	public JpWithdrawMoney getByID(Integer id) {
		JpWithdrawMoney JpWithdrawMoney = new JpWithdrawMoney();
		JpWithdrawMoney.setId(id);
		return find(JpWithdrawMoney);
	}


	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpWithdrawMoney> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<JpWithdrawMoney> getListByIds(List<Integer> ids) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "id", ids);
		List<JpWithdrawMoney> list = memory.query(sql, new BeanListHandler<JpWithdrawMoney>(JpWithdrawMoney.class), params);
		return list;
	}
}
