package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanListHandler;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbContentComment;

/**
 * 文章点赞
 * @author jackieWang
 *
 */
@Repository("sbContentCommentDao")
public class SbContentCommentDao extends BaseDao<SbContentComment> {
	
	
	/**
	 * 获取文章点赞
	 * @param comment
	 * @return
	 */
	public SbContentComment findSbContentComment(SbContentComment comment) {
		return find(comment);
	}
	
	
	/**
	 * 根据文章ID获取点赞
	 * @param contentIds
	 * @return
	 */
	public List<SbContentComment> getSbContentCommentListByIds(List<Integer> contentIds) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		memory.in(sql, params, "where", "content_id", contentIds);
		
		List<SbContentComment> list = memory.query(sql, new BeanListHandler<SbContentComment>(SbContentComment.class), params);
		return list;
	}
	
	/**
	 * 转换集合数据
	 * @param contentIds
	 * @return
	 */
	public Map<Integer, SbContentComment> getSbContentCommentMapByIds(List<Integer> contentIds) {
		List<SbContentComment> comments = getSbContentCommentListByIds(contentIds);
		Map<Integer, SbContentComment> map = new HashMap<Integer, SbContentComment>(0);
		if (CollectionUtils.isNotEmpty(comments)) {
			for (SbContentComment comment : comments) {
				map.put(comment.getContentId(), comment);
			}
		}
		return map;
	}
	
	/**
	 * 获取单个文章点赞
	 * @param contentId
	 * @return
	 */
	public SbContentComment getSbContentCommentByID(Integer contentId) {
		SbContentComment comment = new SbContentComment();
		comment.setContentId(contentId);
		return find(comment);
	}
	
	/**
	 * 保存文章
	 * @param comment
	 * @return
	 */
	public boolean saveSbContentComment(SbContentComment comment) {
		if(insert(comment)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改文章
	 * @param comment
	 * @return
	 */
	public boolean editSbContentComment(SbContentComment comment) {
		if(update(comment, "commentId")>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 删除文章点赞
	 * @param comment
	 * @return
	 */
	public boolean trashSbContentComment(SbContentComment comment) {
		if(delete(comment)>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 保存用户点赞
	 * @param comment
	 * @return
	 */
	public int insertOrUpdate(SbContentComment comment) {
		return super.memory.createOrUpdate(super.memory.getConnection(), SbContentComment.class, comment, "commentId",
				"zj_count = zj_count + VALUES(zj_count)," +
				"wy_count = wy_count + VALUES(wy_count)," +
				"dz_count = dz_count + VALUES(dz_count)," +
				"gx_count = gx_count + VALUES(gx_count)," +
				"bs_count = bs_count + VALUES(bs_count)");
	}
	
	
}
