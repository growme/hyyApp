package com.ccnet.admin.bh.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;

import cn.ffcs.memory.BeanListHandler;

@Repository("apiSbContentInfoDao")
public class ApiSbContentInfoDao extends BaseDao<SbContentInfo> {

	/**
	 * 获取单个文章
	 * 
	 * @param contentId
	 * @return
	 */
	public SbContentInfo getSbContentByID(Integer contentId) {
		SbContentInfo contentInfo = new SbContentInfo();
		contentInfo.setContentId(contentId);
		return find(contentInfo);
	}

	/**
	 * 根据ID获取文章
	 * 
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentTjInfo(Integer contentId, Integer limit) {
		SbContentInfo contentInfo = getSbContentByID(contentId);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where column_id = ? ");
		params.add(contentInfo.getColumnId());
		// 带上文章
		// if(CPSUtil.isNotEmpty(userId)){
		// sql.append(" and user_id =? ");
		// params.add(userId);
		// }
		// 加上排序
		sql.append(" order by rand()  limit 0,?");
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
	}

	/**
	 * 根据ID获取文章
	 * 
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentTjInfo(Integer contentId, Integer type, Integer limit) {
		// StringBuffer sql = new StringBuffer();
		// List<Object> params = new ArrayList<Object>();
		// sql.append("select * from ").append(getCurrentTableName());
		// memory.query(sql, params, "where", "content_id", contentId);
		//
		// List<SbContentInfo> list = memory.query(sql, new
		// BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
		// return list;
		// SbContentInfo contentInfo = getSbContentByID(contentId);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where column_id = ? ");
		sql.append(" and content_type = ? ");
		params.add(contentId);
		params.add(type);
		// 带上文章
		// if(CPSUtil.isNotEmpty(userId)){
		// sql.append(" and user_id =? ");
		// params.add(userId);
		// }

		// 加上排序
		sql.append(" order by rand()  limit 0,?");
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
	}

	/**
	 * 根据code获取文章
	 * 
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentByCodeList(List<String> contentCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "content_code", contentCode);

		List<SbContentInfo> list = memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
		return list;
	}

	public List<SbContentInfo> findSbContentInfoByPage(SbContentInfo contentInfo, Page<SbContentInfo> page) {
		// 获取参数
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.*,(SELECT GROUP_CONCAT(b.content_pic) FROM sb_content_pic b WHERE a.content_code = b.content_id) AS contentPics from sb_content_info a ");
		sql.append(" where a.content_type=" + contentInfo.getContentType());
		if (contentInfo.getColumnId() != null) {
			sql.append(" and a.column_id=" + contentInfo.getColumnId());
		}
		if (contentInfo.getHomeToped() != null) {
			sql.append(" and a.home_toped=" + contentInfo.getHomeToped());
			if (contentInfo.getHomeToped().toString().equals("2")) {
				sql.append(" and a.high_price_ad IN (SELECT ad_id FROM `sb_advert_info` WHERE state=1)");
			}
		}
		// 加上排序
		sql.append(" order by home_toped DESC,create_time desc ");
		sql.append(" limit " + (page.getPageNum() - 1) * page.getPageSize() + " , " + page.getPageSize());
		List<SbContentInfo> list = memory.query(sql.toString(),
				new BeanListHandler<SbContentInfo>(SbContentInfo.class));
		return list;
	}

	public List<SbContentInfo> finfSbContentInfoRand(SbContentInfo contentInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT t1.*,(SELECT GROUP_CONCAT(b.content_pic) FROM sb_content_pic b WHERE t1.content_code = b.content_id) AS contentPics FROM `sb_content_info` AS t1 ");
		sql.append(
				" JOIN ( SELECT ROUND( RAND( ) * (( SELECT MIN( content_id ) FROM (SELECT content_id FROM `sb_content_info` WHERE content_id > 1000  ORDER BY content_id DESC LIMIT 5) tt ) - ( SELECT MIN( content_id ) FROM `sb_content_info` WHERE content_id > 1000 ) ");
		sql.append(
				" ) + ( SELECT MIN( content_id ) FROM `sb_content_info` WHERE content_id > 1000 ) ) AS content_id ) AS t2 ");
		sql.append(" WHERE  t1.content_id >= t2.content_id ");
		if (contentInfo.getColumnId() != null && contentInfo.getColumnId() > 1) {
			sql.append(" AND t1.`column_id`= " + contentInfo.getColumnId());
		}
		sql.append(" AND t1.content_type=" + contentInfo.getContentType() + " ORDER BY t1.content_id LIMIT 20");
		List<SbContentInfo> list = memory.query(sql.toString(),
				new BeanListHandler<SbContentInfo>(SbContentInfo.class));
		return list;
	}

	/**
	 * 根据ID获取文章
	 * 
	 * @param contentIds
	 * @return
	 */
	public List<SbContentInfo> getSbContentList(SbContentInfo contentInfo, Page<SbContentInfo> page) {
		// 获取参数
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.content_id,a.content_code,a.content_title,a.weixin_name,a.read_num,a.visual_read_num,a.show_top,a.check_state,a.order_no,a.share_num,a.click_num,a.content_pic_link,a.column_id,a.user_id,a.video_link,a.content_type,a.video_number,a.home_toped,a.toped_time,a.gather_pic,a.create_time,a.read_award,a.friend_share_award,(SELECT GROUP_CONCAT(b.content_pic) FROM sb_content_pic b WHERE a.content_code = b.content_id) AS contentPics from sb_content_info a ");
		sql.append(" where a.content_type=" + contentInfo.getContentType());
		if (contentInfo.getColumnId() != null) {
			sql.append(" and a.column_id=" + contentInfo.getColumnId());
		}
		if (contentInfo.getHomeToped() != null) {
			sql.append(" and a.home_toped=" + contentInfo.getHomeToped());
		}
		// 加上排序
		sql.append(" order by create_time desc ");
		sql.append(" limit " + (page.getPageNum() - 1) * page.getPageSize() + " , " + page.getPageSize());
		List<SbContentInfo> list = memory.query(sql.toString(),
				new BeanListHandler<SbContentInfo>(SbContentInfo.class));
		return list;
	}

	/**
	 * 按时间删除文章
	 * @param
	 * @return
	 */
	public List<SbContentInfo> findSbContentInfoBeforeDate(String date) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName()).append(" where create_time < ? ");
		params.add(date);
		List<SbContentInfo> list = memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
		return  list;
	}
}
