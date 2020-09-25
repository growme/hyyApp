package com.ccnet.admin.bh.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.admin.bh.entity.District;
import com.ccnet.core.dao.base.BaseDao;

import cn.ffcs.memory.BeanListHandler;

@Repository("districtDao")
public class DistrictDao extends BaseDao<District> {

	public List<District> findDistrictDaoByList(District district) {
		String sql = "SELECT * FROM district WHERE parent_id =0 ORDER BY id ASC";
		List<District> list = memory.query(sql, new BeanListHandler<District>(District.class));
		return list;
	}
}
