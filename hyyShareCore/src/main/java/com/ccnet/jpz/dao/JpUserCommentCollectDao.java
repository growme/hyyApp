package com.ccnet.jpz.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.jpz.entity.JpUserCommentCollect;

import cn.ffcs.memory.BeanListHandler;

@Repository("jpUserCommentCollectDao")
public class JpUserCommentCollectDao extends BaseDao<JpUserCommentCollect> {

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param columnInfo
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page<JpUserCommentCollect> findByPage(JpUserCommentCollect table, Page<JpUserCommentCollect> page,
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
		super.queryPager(sql.toString(), new BeanListHandler<JpUserCommentCollect>(JpUserCommentCollect.class), params,
				page);
		return page;
	}

	/**
	 * 获取单个
	 * 
	 * @param columnId
	 * @return
	 */
	public JpUserCommentCollect getByID(Integer userId, Integer commentId) {
		JpUserCommentCollect JpUserCommentCollect = new JpUserCommentCollect();
		JpUserCommentCollect.setUserId(userId);
		JpUserCommentCollect.setCommentId(commentId);
		return find(JpUserCommentCollect);
	}

	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpUserCommentCollect> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	public List<JpUserCommentCollect> getListByIds(List<Integer> ids) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "id", ids);
		List<JpUserCommentCollect> list = memory.query(sql,
				new BeanListHandler<JpUserCommentCollect>(JpUserCommentCollect.class), params);
		return list;
	}
	
	public List<JpUserCommentCollect> findUserCommentCollectList(JpUserCommentCollect table, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.*,b.member_name as userName,b.member_icon as userIcon FROM jp_user_comment_collect a, member_info b WHERE a.user_id=b.member_id AND a.comment_id = "
						+ table.getCommentId());
		sql.append(" order by a.user_id asc limit ?,?");
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserCommentCollect>(JpUserCommentCollect.class), params);
	}
}
