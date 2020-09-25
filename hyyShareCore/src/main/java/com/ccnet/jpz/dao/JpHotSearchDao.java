package com.ccnet.jpz.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.jpz.entity.JpHotSearch;

import cn.ffcs.memory.BeanListHandler;

@Repository("jpHotSearchDao")
public class JpHotSearchDao extends BaseDao<JpHotSearch> {

	/**
	 * 获取单个
	 * 
	 * @param columnId
	 * @return
	 */
	public JpHotSearch getByID(Integer id) {
		JpHotSearch JpHotSearch = new JpHotSearch();
		JpHotSearch.setId(id);
		return find(JpHotSearch);
	}

	/**
	 * 批量删除里面
	 * 
	 * @param list
	 * @return
	 */
	public boolean trashList(List<JpHotSearch> list) {
		int rst[] = deleteBatch(list);
		if (rst.length > 0 && rst.length == list.size()) {
			return true;
		} else {
			return false;
		}
	}

	public List<JpHotSearch> getHotSearchList() {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("SELECT * FROM jp_hot_search WHERE state=0 ");
		sql.append(" order by num,create_date desc limit 0,10");
		return super.memory.query(sql, new BeanListHandler<JpHotSearch>(JpHotSearch.class), params);
	}

	public int updateOrInsertHotSearch(JpHotSearch jpHotSearch) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<>();
		sql.append(
				"INSERT INTO jp_hot_search(term,num,create_date,state) VALUES (?,1,?,0) ON DUPLICATE KEY UPDATE num=num+1");
		params.add(jpHotSearch.getTerm());
		params.add(new Date());
		return memory.update(sql, params);
	}
}
