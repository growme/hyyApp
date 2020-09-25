package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbAdvertVisitLog;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentPic;

import cn.ffcs.memory.BeanListHandler;

@Repository("apiSbContentPicDao")
public class ApiSbContentPic extends BaseDao<SbContentPic> {

	/**
	 * 根据ID获取文章
	 * 
	 * @param contentIds
	 * @return
	 */
	public List<SbContentPic> getSbContentPic(String contentCode) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		sql.append(" where content_id = ? ");
		params.add(contentCode);
		// 带上文章
//		if(CPSUtil.isNotEmpty(userId)){
//			sql.append(" and user_id =? ");
//			params.add(userId);
//		}
		// 加上排序
//		sql.append(" order by rand()  limit 0,?");
//		params.add(limit);
		return super.memory.query(sql, new BeanListHandler<SbContentPic>(SbContentPic.class), params);
	}

}
