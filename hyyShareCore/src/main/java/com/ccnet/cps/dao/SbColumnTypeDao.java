package com.ccnet.cps.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.SbColumnType;
@Repository("sbColumnTypeDao")
public class SbColumnTypeDao extends BaseDao<SbColumnType> {
@Override
public List<SbColumnType> findList(SbColumnType o) {
	// TODO Auto-generated method stub
	return super.findList(o);
}
}
