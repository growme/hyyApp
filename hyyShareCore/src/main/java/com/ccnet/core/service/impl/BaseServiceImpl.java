package com.ccnet.core.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;

public abstract class BaseServiceImpl<T> implements BaseService<T>{
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public int insert(T o) {
		return getDao().insert(o);
	}

	@Override
	public int delete(T o) {
		return getDao().delete(o);
	}
	
	@Override
	public int [] deleteBatch(List<T> os){
		return getDao().deleteBatch(os);
	}

	@Override
	public int update(T o, String camelKey) {
		return getDao().update(o, camelKey);
	}

	@Override
	public List<T> findList(T o) {
		return getDao().findList(o);
	}
	
	@Override
	public T find(T o) {
		return getDao().find(o);
	}

	@Override
	public Page<T> findByPage(T o, Page<T> page) {
		getDao().findByPage(o, page);
		return page;
	}

	protected abstract BaseDao<T> getDao();
	
}
