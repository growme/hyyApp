package com.ccnet.jpz.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.jpz.entity.JpNotice;

import cn.ffcs.memory.BeanListHandler;

@Repository("jpNoticeDao")
public class JpNoticeDao extends BaseDao<JpNotice> {

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param columnInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<JpNotice> findByPage(JpNotice table, Page<JpNotice> page, Dto pdDto) {
		// 获取参数
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, JpNotice.class, table);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and create_date >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and create_date <=? ");
			params.add(end_date + " 23:59:59");
		}

		// 加上排序
		sql.append(" order by create_date desc ");
		super.queryPager(sql.toString(), new BeanListHandler<JpNotice>(JpNotice.class), params, page);
		return page;
	}

	/**
	 * 获取单个
	 * 
	 * @param columnId
	 * @return
	 */
	public JpNotice getByID(Integer id) {
		JpNotice JpNotice = new JpNotice();
		JpNotice.setId(id);
		return find(JpNotice);
	}

	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpNotice> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	public List<JpNotice> getListByIds(List<Integer> ids) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "id", ids);
		List<JpNotice> list = memory.query(sql, new BeanListHandler<JpNotice>(JpNotice.class),
				params);
		return list;
	}

}
