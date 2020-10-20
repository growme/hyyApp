package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.ccnet.api.entity.JpUserAttention;
import com.ccnet.api.enums.HandleType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.jpz.entity.JpUserCollect;
import com.ccnet.jpz.entity.JpUserComment;

import cn.ffcs.memory.BeanListHandler;

@Repository("apiUserCommentDao")
public class ApiUserCommentDao extends BaseDao<T> {

	public List<JpUserComment> findUserCommentList(JpUserComment jpUserComment, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT uc.*,mi.member_name AS userName,mi.member_icon AS userIcon");
		if (CPSUtil.isNotEmpty(jpUserComment.getUserId())) {
			sql.append(
					",(select count(1) FROM jp_user_comment_collect cc where cc.comment_id = uc.id and cc.user_id = ?) as isLike ");
			params.add(jpUserComment.getUserId());
		}
		sql.append(" FROM jp_user_comment uc LEFT JOIN member_info mi ON uc.user_id = mi.member_id where 1=1 ");
		if (CPSUtil.isNotEmpty(jpUserComment.getContentId())) {
			sql.append(" and uc.content_id = ? ");
			params.add(jpUserComment.getContentId());
		}
		if (CPSUtil.isNotEmpty(jpUserComment.getToUid())) {
			sql.append(" and uc.to_uid = ? ");
			params.add(jpUserComment.getToUid());
		}
		if (CPSUtil.isNotEmpty(jpUserComment.getReplyType())) {
			sql.append(" and uc.reply_type = ? ");
			params.add(jpUserComment.getReplyType());
		}
		if (CPSUtil.isNotEmpty(jpUserComment.getToCommentId())) {
			sql.append(" and uc.to_comment_id = ? ");
			params.add(jpUserComment.getToCommentId());
		}
		if (CPSUtil.isNotEmpty(jpUserComment.getId())) {
			sql.append(" and uc.id = ? ");
			params.add(jpUserComment.getId());
		}
		// sql.append(" order by uc.praise_num desc,uc.create_date desc limit
		// ?,?");
		sql.append(" order by uc.create_date desc limit ?,?");
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserComment>(JpUserComment.class), params);
	}

	/**
	 * 修改点赞、回复数
	 * 
	 * @param type
	 * @return
	 */
	public int updateCommentInfo(Integer id, String type, String code) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		if (code.equals("0")) {
			code = "+";
		} else {
			code = "-";
		}
		// 评论
		if (HandleType.comment.getid().equals(type)) {
			sql.append("update jp_user_comment set reply_num = reply_num " + code + " 1 where id=" + id);
		}
		// 点赞
		if (HandleType.click.getid().equals(type)) {
			sql.append("update jp_user_comment set praise_num = praise_num " + code + " 1 where id=" + id);
		}
		return memory.update(sql, params);
	}

	public List<JpUserComment> findFirstUserCommentList(JpUserComment jpUserComment, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.*,mi.member_name AS userName,mi.member_icon AS userIcon,(SELECT b.content FROM jp_user_comment b WHERE a.to_comment_id = b.id) AS toContent ");
		sql.append(" ,(SELECT c.member_name FROM member_info c WHERE a.to_uid = c.member_id) AS toUserName ");
		if (CPSUtil.isNotEmpty(jpUserComment.getUserId())) {
			sql.append(
					",(select count(1) FROM jp_user_comment_collect cc where cc.comment_id = a.id and cc.user_id = ?) as isLike ");
			params.add(jpUserComment.getUserId());
		}
		sql.append(
				" FROM jp_user_comment a  LEFT JOIN member_info mi ON a.user_id = mi.member_id WHERE a.content_id = ? AND first_comment_id = ?");
		sql.append(" order by a.create_date desc limit ?,?");
		params.add(jpUserComment.getContentId());
		params.add(jpUserComment.getToCommentId());
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserComment>(JpUserComment.class), params);
	}

	public List<SbContentInfo> findJpUserCollectList(JpUserCollect jpUserComment, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT DISTINCT a.*,(SELECT GROUP_CONCAT(pic.content_pic) FROM sb_content_pic pic WHERE a.content_code = pic.content_id) AS contentPics  ");
		sql.append(
				"FROM `jp_user_collect` t1,sb_content_info a WHERE a.content_id = t1.content_id AND t1.type=? AND t1.user_id =? order by t1.create_date desc");
		params.add(jpUserComment.getType());
		params.add(jpUserComment.getUserId());
		sql.append(" limit ?,?");
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<SbContentInfo>(SbContentInfo.class), params);
	}

	public List<JpUserComment> getMyCommentList(JpUserComment jpUserComment, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT zz.* from ( ");
		sql.append(
				"SELECT t1.content_id,t1.create_date,t1.content,a.content_title ,a.content_type,a.comment_count AS commentNum,1 AS checkType,'' AS option_info,(SELECT GROUP_CONCAT(pic.content_pic) FROM sb_content_pic pic WHERE a.content_code = pic.content_id) AS images ");
		sql.append(
				" ,t1.to_uid AS toUid,t1.to_comment_id AS toCommentId,(SELECT comment2.content FROM jp_user_comment comment2 WHERE comment2.id = t1.to_comment_id ) AS toContent ");
		sql.append(
				" FROM jp_user_comment t1  LEFT JOIN sb_content_info a ON t1.content_id = a.content_id WHERE t1.user_id = ? ");
		params.add(jpUserComment.getUserId());
		sql.append("UNION ALL ");
		sql.append(
				"SELECT t2.business_id AS content_id,t2.create_date,t2.content,b.title,b.type AS content_type,b.comment_num AS commentNum,5 AS checkType,b.option_info,b.image as images");
		sql.append(
				" ,t2.to_uid AS toUid,t2.to_comment_id AS toCommentId,(SELECT comment2.content FROM jp_user_comment comment2 WHERE comment2.id = t2.to_comment_id ) AS toContent ");
		sql.append(
				" FROM jp_business_comment t2 LEFT JOIN jp_mutual_help b ON t2.business_id = b.id WHERE t2.business_type = 1 AND t2.member_id = ?  ");
		params.add(jpUserComment.getUserId());
		sql.append(") zz WHERE 1=1 order by zz.create_date desc limit ?,?");
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserComment>(JpUserComment.class), params);
	}

	public List<JpUserComment> findJpUserLikeList(JpUserComment jpUserComment, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT DISTINCT a.*,(SELECT GROUP_CONCAT(pic.content_pic) FROM sb_content_pic pic WHERE a.content_code = pic.content_id) AS images  ");
		sql.append(
				"FROM `jp_user_collect` t1,sb_content_info a WHERE a.content_id = t1.content_id AND t1.type=? AND t1.user_id =? ");
		params.add(jpUserComment.getType());
		params.add(jpUserComment.getUserId());
		sql.append(" limit ?,?");
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserComment>(JpUserComment.class), params);
	}

	public List<JpUserAttention> getMyAttentionList(String memberId, Page<?> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT a.*,b.`member_name`,b.`member_icon`,b.`introduction`,(SELECT COUNT(1) FROM jp_user_attention c WHERE c.user_id = a.`to_uid` AND c.to_uid = a.`user_id`) AS huguan ");
		sql.append(
				" FROM `jp_user_attention` a,member_info b WHERE a.`to_uid` = b.`member_id` AND a.user_id = ?  ORDER BY a.`create_date` DESC LIMIT ?,?");
		params.add(memberId);
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserAttention>(JpUserAttention.class), params);
	}

	public List<JpUserAttention> getAttentionMyList(String memberId, Page<?> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT a.*,b.`member_name`,b.`member_icon`,b.`introduction`,(SELECT COUNT(1) FROM jp_user_attention c WHERE c.user_id = a.`to_uid` AND c.to_uid = a.`user_id`) AS huguan ");
		sql.append(
				" FROM `jp_user_attention` a,member_info b WHERE a.`user_id` = b.`member_id` AND a.to_uid = ?  ORDER BY a.`create_date` DESC LIMIT ?,?");
		params.add(memberId);
		params.add((page.getPageNum() - 1) * page.getPageSize());
		params.add(page.getPageSize());
		return super.memory.query(sql, new BeanListHandler<JpUserAttention>(JpUserAttention.class), params);
	}

}
