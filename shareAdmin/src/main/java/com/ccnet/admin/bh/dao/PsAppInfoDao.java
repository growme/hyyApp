package com.ccnet.admin.bh.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.admin.bh.entity.PsAppInfo;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;

import cn.ffcs.memory.BeanListHandler;

@Repository("psAppInfoDao")
public class PsAppInfoDao extends BaseDao<PsAppInfo>{

	public Page<PsAppInfo> findSbCustomtaskByPage(PsAppInfo content, Page<PsAppInfo> page, Dto pdDto) {
		// 获取参数
		String title = pdDto.getAsString("title");
		String status = pdDto.getAsString("status");
		String type = pdDto.getAsString("type");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, PsAppInfo.class, content);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(title)) {
			appendWhere(sql);
			sql.append(" and title like ? ");
			params.add("%" + title + "%");
		}

		if (CPSUtil.isNotEmpty(status)) {
			appendWhere(sql);
			sql.append(" and status =? ");
			params.add(status);
		}

		if (CPSUtil.isNotEmpty(type)) {
			appendWhere(sql);
			sql.append(" and type =? ");
			params.add(type);
		}

		super.queryPager(sql.toString(), new BeanListHandler<PsAppInfo>(PsAppInfo.class), params, page);
		return page;
	}
}
