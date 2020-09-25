package com.ccnet.api.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.ccnet.api.entity.WechatPublicNumber;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;

import cn.ffcs.memory.BeanListHandler;

@Repository("wechatPublicNumberDao")
public class WechatPublicNumberDao extends BaseDao<WechatPublicNumber> {

	public Page<WechatPublicNumber> findWechatPublicNumberByPage(WechatPublicNumber sc, Page<WechatPublicNumber> page,
			Dto pdDto) {
		// 获取参数
		String name = pdDto.getAsString("name");

		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());

		List<String> whereColumns = memory.parseWhereColumns(params, WechatPublicNumber.class, sc);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}

		// 加上模糊查询
		if (CPSUtil.isNotEmpty(name)) {
			appendWhere(sql);
			sql.append(" and name like ? ");
			params.add("%" + name + "%");
		}

		super.queryPager(sql.toString(), new BeanListHandler<WechatPublicNumber>(WechatPublicNumber.class), params,
				page);
		return page;
	}

}
