package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.api.entity.ApiMoneyCount;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbMoneyCount;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;

@Repository("apiMoneyCountDao")
public class ApiMoneyCountDao extends BaseDao<SbMoneyCount> {

	/**
	 * 用户新闻累计收入列表
	 * 
	 * @return
	 */
	public List<ApiMoneyCount> getUserMoneyCountByUserIdAndType(Integer userId, Integer pageNum, Integer pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.*,b.content_title,b.content_code,(SELECT c.content_pic FROM sb_content_pic c WHERE c.content_id=b.content_code AND c.sort_num = 0) AS content_pic FROM (SELECT content_id,user_id,m_type,create_time,SUM(umoney) AS umoney,COUNT(1) conCount FROM `sb_money_count` WHERE m_type = 5");
		sql.append(" and user_id = ? ");
		params.add(userId);
		sql.append(
				" GROUP BY content_id) a,sb_content_info b WHERE a.content_id=b.content_id ORDER BY a.create_time DESC limit ?,?");
		params.add((pageNum - 1) * pageSize);
		params.add(pageSize);
		List<ApiMoneyCount> list = memory.query(sql, new BeanListHandler<ApiMoneyCount>(ApiMoneyCount.class), params);
		return list;
	}

	public List<ApiMoneyCount> getUserMoneyByUserId(Integer userId,Integer pageNum, Integer pageSize) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				"SELECT a.*,b.content_title,b.content_code,(SELECT c.content_pic FROM sb_content_pic c WHERE c.content_id=b.content_code AND c.sort_num = 0) AS content_pic FROM (SELECT content_id,user_id,m_type,create_time,umoney,vindex FROM `sb_money_count` WHERE 1=1 ");
		sql.append(
				" and m_type IN (0,2,3,4,5,10,15,21,22,23,24,41,42,43,44,45,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68) ");
		sql.append(" and user_id = ? ");
		params.add(userId);
		sql.append(" UNION ALL SELECT NULL,user_id,4,create_time,vmoney,'' FROM sb_visit_money WHERE user_id = ? ");
		params.add(userId);
		sql.append(
				" ) a LEFT JOIN sb_content_info b ON a.content_id=b.content_id ORDER BY a.create_time DESC  limit ?,?");
		params.add((pageNum - 1) * pageSize);
		params.add(pageSize);
		List<ApiMoneyCount> list = memory.query(sql, new BeanListHandler<ApiMoneyCount>(ApiMoneyCount.class), params);
		return list;
	}

	public ApiMoneyCount getUserMonetContent(String userId) {
		String sql = "SELECT * FROM sb_money_count WHERE user_id = " + userId
				+ " AND m_type = 5 AND content_id IS NOT NULL ORDER BY create_time ASC LIMIT 1";
		List<ApiMoneyCount> list = memory.query(sql, new BeanListHandler<ApiMoneyCount>(ApiMoneyCount.class));
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	public ApiMoneyCount getNewUserContentLog(String userId) {
		String sql = "SELECT a.content_id,a.user_id,(SELECT COUNT(1) FROM sb_content_visit_log b WHERE b.content_id = a.content_id AND a.user_id = b.user_id) AS conCount "
				+ " FROM sb_share_log a WHERE  user_id = " + userId + "  ORDER BY share_id ASC LIMIT 1";
		return memory.query(sql, new BeanHandler<ApiMoneyCount>(ApiMoneyCount.class));
	}
}
