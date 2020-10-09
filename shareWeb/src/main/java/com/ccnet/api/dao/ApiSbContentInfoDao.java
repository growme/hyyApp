package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.api.enums.HandleType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.StringUtils;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.entity.JpUserCollect;

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
		if (contentInfo.getColumnId() != null && StringUtils.isNoneBlank(contentInfo.getColumnId().toString())) {
			sql.append(" where column_id = ? ");
			params.add(contentInfo.getColumnId());
		}
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
		SbContentInfo contentInfo = getSbContentByID(contentId);
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where column_id = ? ");
		sql.append(" and content_type = ? ");
		params.add(contentInfo.getColumnId());
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
		}
		if (CPSUtil.isNotEmpty(contentInfo.getContentTitle())) {
			sql.append(" and a.content_title like '%%" + contentInfo.getContentTitle() + "%%' ");
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
	 * 修改阅读、收藏、评论数
	 * 
	 * @param type
	 * @return
	 */
	public int updateContentInfo(Integer id, String type, String code) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		if (code.equals("0")) {
			code = "+";
		} else {
			code = "-";
		}
		// 阅读
		if (HandleType.read.getid().equals(type)) {
			sql.append("update sb_content_info set read_num = read_num " + code + " 1 where content_id=" + id);
		}
		// 分享
		if (HandleType.share.getid().equals(type)) {
			sql.append("update sb_content_info set share_num = share_num " + code + " 1 where content_id=" + id);
		}
		// 评论
		if (HandleType.comment.getid().equals(type)) {
			sql.append(
					"update sb_content_info set comment_count = comment_count " + code + " 1 where content_id=" + id);
		}
		// 点赞
		if (HandleType.click.getid().equals(type)) {
			sql.append("update sb_content_info set click_num = click_num " + code + " 1 where content_id=" + id);
		}
		// 收藏
		if (HandleType.collect.getid().equals(type)) {
			sql.append("update sb_content_info set collect_num = collect_num " + code + " 1 where content_id=" + id);
		}
		return memory.update(sql, params);
	}

	public List<SbContentInfo> findTypeByPage(JpUserCollect jpUserCollect, Page<JpUserCollect> page) {
		// 获取参数
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.* FROM sb_content_info a,jp_user_collect b WHERE a.content_id = b.content_id ");
		sql.append(" and b.type = " + jpUserCollect.getType());
		sql.append(" and b.user_id = " + jpUserCollect.getUserId());
		sql.append(" and a.content_type in (0,1)");
		// 加上排序
		sql.append(" order by b.create_date desc ");
		sql.append(" limit " + (page.getPageNum() - 1) * page.getPageSize() + " , " + page.getPageSize());
		List<SbContentInfo> list = memory.query(sql.toString(),
				new BeanListHandler<SbContentInfo>(SbContentInfo.class));
		return list;
	}

	// 获取商会频道文章
	public List<SbContentInfo> findCoceralContentByPage(SbContentInfo info, Page<SbContentInfo> page) {
		// 获取参数
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT a.*,(SELECT GROUP_CONCAT(c.content_pic) FROM sb_content_pic c WHERE a.content_code = c.content_id) AS contentPics from sb_content_info a,(SELECT DISTINCT member_id FROM jp_user_coceral_member)  AS b WHERE a.member_id = b.member_id ");
		sql.append(" AND a.is_release = 1 AND a.check_state = 1 AND a.coceral_state = 1 AND a.content_type IN(0,1) ");
		sql.append(" AND (a.is_public = 1 ");
		sql.append(") ");
		// 加上排序
		sql.append(" order by a.create_time desc ");
		sql.append(" limit " + (page.getPageNum() - 1) * page.getPageSize() + " , " + page.getPageSize());
		return memory.query(sql.toString(), new BeanListHandler<SbContentInfo>(SbContentInfo.class));
	}

	// 获取商会频道文章
	public List<SbContentInfo> findCoceralIdContentByPage(SbContentInfo info, Page<SbContentInfo> page) {
		// 获取参数
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.* from sb_content_info a  ");
		sql.append(
				"  WHERE a.is_release =1 AND a.coceral_state = 1 AND a.check_state = 1 AND a.content_type IN (0,1) ");
		sql.append(" AND (a.is_public = 1 ");
		sql.append(") ");
		// 加上排序
		sql.append(" order by a.create_time desc ");
		sql.append(" limit " + (page.getPageNum() - 1) * page.getPageSize() + " , " + page.getPageSize());
		return memory.query(sql.toString(), new BeanListHandler<SbContentInfo>(SbContentInfo.class));
	}

	public List<SbContentInfo> getSbContentTjRandInfo(Integer type, Integer limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(
				" where content_id >= (SELECT FLOOR(RAND() * (SELECT MAX(content_id) FROM `sb_content_info`)))  AND content_type=? AND create_time >= ? ");
		params.add(type);
		params.add(DateUtils.formatDate(DateUtils.getDateStart(new Date()), "yyyy-MM-dd HH:mm:ss"));
		// 加上排序
		sql.append(" ORDER BY content_id LIMIT ?");
		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
	}
}
