package com.ccnet.jpz.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.jpz.entity.JpAdState;

import cn.ffcs.memory.BeanListHandler;

@Repository("jpAdStateDao")
public class JpAdStateDao extends BaseDao<JpAdState> {

	public Page<JpAdState> findByPage(JpAdState table, Page<JpAdState> page,
			Dto pdDto) {
		// 获取参数
		String title = pdDto.getAsString("title");
		String content = pdDto.getAsString("content");
		// 日期过滤
		String end_date = pdDto.getAsString("end_date");
		String start_date = pdDto.getAsString("start_date");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.*,b.`content_title` AS toContent,(SELECT c.member_name FROM member_info c WHERE c.member_id = a.`user_id`) AS userName from `jp_user_comment` a,`sb_content_info` b WHERE a.`content_id` = b.`content_id` AND (a.pingbi is null or a.pingbi = 0)");

		// 带上日期查询
		if (CPSUtil.isNotEmpty(start_date)) {
			appendWhere(sql);
			sql.append(" and start_date >=? ");
			params.add(start_date + " 00:00:00");
		}

		if (CPSUtil.isNotEmpty(end_date)) {
			appendWhere(sql);
			sql.append(" and end_date <=? ");
			params.add(end_date + " 23:59:59");
		}

		if (CPSUtil.isNotEmpty(title)) {
			sql.append(" and b.content_title like ? ");
			params.add("%" + title + "%");
		}

		if (CPSUtil.isNotEmpty(content)) {
			sql.append(" and a.ontent =? ");
			params.add(content);
		}

		// 加上排序
		sql.append(" order by a.create_date desc ");
		super.queryPager(sql.toString(), new BeanListHandler<JpAdState>(JpAdState.class), params,
				page);
		return page;
	}

	/**
	 * 获取单个
	 * 
	 * @param columnId
	 * @return
	 */
	public JpAdState getByID(Integer id) {
		JpAdState JpAdState = new JpAdState();
		JpAdState.setId(id);
		return find(JpAdState);
	}

	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpAdState> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}
}
