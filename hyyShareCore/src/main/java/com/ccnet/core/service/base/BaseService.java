package com.ccnet.core.service.base;

import java.util.List;

import com.ccnet.core.dao.base.Page;

public interface BaseService<T> {
	/**
	 * 保存一个对象
	 * @param o 对象
	 * @return 对象的ID
	 */
	public int insert(T o);	
	/**
	 * 删除一个对象
	 * @param o  对象
	 */
	public int delete(T o);
	/**
	 * 批量删除一个对象
	 * @param s (主键)数组
	 */
	public int[] deleteBatch(List<T> os);
	/**
	 * 更新一个对象
	 * @param o 对象       
	 */
	public int update(T o, String camelKey);
	/**
	 * 获得对象列表
	 * @param o 对象       
	 * @return List
	 */
	public List<T> findList(T o);	
	
	public T find(T o);
	/**
	 * 获得对象列表
	 * @param o 对象       
	 * @param page 分页对象
	 * @return List
	 */
	public Page<T> findByPage(T o,Page<T> page);	
	
}
