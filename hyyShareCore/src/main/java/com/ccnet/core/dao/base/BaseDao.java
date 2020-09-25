package com.ccnet.core.dao.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.ffcs.memory.BeanHandler;
import cn.ffcs.memory.BeanListHandler;
import cn.ffcs.memory.ColumnHandler;
import cn.ffcs.memory.ResultSetHandler;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;

@Repository("baseDao")
public class BaseDao<T> {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 引用外部的jar包. memory-1.0.0.jar
	 * 1. 取消了jar包自带的Memory., 在本项目中使用CCNETMemory.java替代. 
	 *   原因：原先自带的Memory.java包中，解析实体类属性为数据库字段时时无法排除一些非数据库对应字段。
	 *       所以我们做了这方面的支持，在类属性上面加上注解标记@IgnoreTableField时，认为该属性只是一个
	 *       普通的java类属性(非数据库字段)
	 * 2. 实体类与表结构自动转换的规律。将java实体类中的驼峰命名规则(userId)可以自动替换为数据库的下划线分隔规则(user_id)
	 *    如有需要，可以调用memory.camel2underscore(String)和memory.underscore2camel(String)方法.
	 * 3. 数据库操作的底层封装方法都可以在memory类中找到对应的方法，这里需要说明的是ResultSetHandler参数的选用。
	 *    a. BeanListHandler.java 查询多个实体类结果(会自动将字段值设置到实体类中)
	 *    b. BeanHandler.java  查询单个实体类结果(会自动将字段值设置到实体类中)
	 *    c. ColumnHandler.java 查询某一个列(可以指定列名或者索引号) 的单个结果
	 *    d. ColumnListHandler.java 查询某一个列(可以指定列名或者索引号) 的多个结果集
	 *    e. JSONObjectHandler.java 针对一些特定的情况(需要查多列，但又没有对应的实体类时), 这里查出来会是了个JSONObject对象(key为属性名)，查询单个结果
	 *    f. JSONArrayHandler.java #e.的多个结果集, 反回JSONArray(包含JSONObject)对像
	 */
	@Autowired
	protected CCNETMemory memory;

	private Class<T> entityClass;

	private Class<T> getEntityClass() {
		if (this.entityClass == null) {
			synchronized (this) {
				if (this.entityClass == null) {
					 Type genType = getClass().getGenericSuperclass(); 
					 Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
					 this.entityClass = (Class) params[0];
				}
			}
		}		 
		return this.entityClass;
	}
	
	/**
	 * 保存一个对象
	 * 
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public int insert(T o) {
		return insert(o, null);
	}
	
	public int insert(T o, String autoIncrementField) {
		return memory.create(getEntityClass(), o, autoIncrementField);
	}

	/**
	 * 删除一个对象
	 * 
	 * @param  k 字段值
	 */
	public int delete(T o) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		//因为是删除语句，所以where必须有条件
		sql.append("delete from ").append(getCurrentTableName()).append(" where ");
		List<String> whereColumns = memory.parseWhereColumns(params, getEntityClass(), o);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(appendAnd(whereColumns));
		}
		return memory.update(sql, params);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	public int update(T o, String camelKey) {
		return memory.update(getEntityClass(), o, memory.camel2underscore(camelKey));
	}
	
	/**
	 * 更新一组对象
	 * 
	 * @param o
	 *            对象
	 */
	public boolean updateBatch(List<T> list, String camelKey) {
		boolean bool = false;
		int rest [] = memory.update(getEntityClass(), list, memory.camel2underscore(camelKey));
		if(rest!=null && rest.length>0){
			CPSUtil.xprint("共执行更新操作："+rest.length);
			bool = true;
		}
		return bool;
	}

	/**
	 * 批量删除一组对象
	 * 
	 * @param s
	 *   (主键)数组
	 */
	public int[] deleteBatch(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new int[0];
		}
		
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("delete from ").append(getCurrentTableName()).append(" where ");
		List<String> whereColumns = memory.parseWhereColumns(paramList, getEntityClass(), list.get(0));
		if (CollectionUtils.isEmpty(whereColumns)) {
			return new int[0];
		}
		sql.append(appendAnd(whereColumns));
		
		Object[][] params = new Object[list.size()][whereColumns.size()];
		params[0] = paramList.toArray(new Object[paramList.size()]);
		for (int i = 1; i < list.size(); i++) {
			paramList = new ArrayList<Object>();
			memory.parseWhereColumns(paramList, getEntityClass(), list.get(i));
			params[i] = paramList.toArray(new Object[paramList.size()]);
		}
		CPSUtil.xprint("====="+sql.toString());
		return memory.batch(sql.toString(), params);
	}

	/**
	 * 获得对象列表
	 * 
	 * @param o
	 *            对象
	 * @return List
	 */
	public List<T> findList(T o) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, getEntityClass(), o);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		return memory.query(sql, new BeanListHandler<T>(getEntityClass()), params);
	}
	
	public T find(T o) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, getEntityClass(), o);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		return memory.query(sql, new BeanHandler<T>(getEntityClass()), params);
	}

	protected String getCurrentTableName() {
		return getTableName(getEntityClass());
	}
	
	protected String getTableName(Class clazz) {
		return memory.camel2underscore(clazz.getSimpleName());
	}
	
	public void findByPage(T o, Page<T> page) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select * from ").append(getCurrentTableName());
		List<String> whereColumns = memory.parseWhereColumns(params, getEntityClass(), o);
		if (CollectionUtils.isNotEmpty(whereColumns)) {
			sql.append(" where ").append(appendAnd(whereColumns));
		}
		queryPager(sql.toString(), new BeanListHandler<T>(getEntityClass()), params, page);
	}
	
	/**
	 * 根据sql条件和参数，查询分页条件。
	 * 需要注意：sql 中如果存在order by, 则要求order和by之间只能有一个英文空格
	 * @param sql 例: select * from <table> where <id>=?
	 * @param handler 
	 * @param params 例: [1]
	 * @param page 同时也为返回结果对象
	 */
	protected <V> void queryPager(String sql, ResultSetHandler<List<V>> handler, List<Object> params, Page<V> page) {
		//判断当前如果是查询第1页，则还需要count总数据量有多少
		if (page.getPageNum() == Const.pageNum) {
			String s = sql;
			s = StringUtils.substringAfter(s, "from");
			s = StringUtils.substringBefore(s, "order by");
			StringBuffer countSql = new StringBuffer();
			countSql.append("select count(1) from ").append(s);
			page.setTotalRecord(count(countSql, params));
		}
		StringBuffer querySql = new StringBuffer(sql);
		//带上分页条件，直接查询
		memory.pager(querySql, params, page.getPageSize(), page.getPageNum());
		//查询
		page.setResults(memory.query(querySql, handler, params));
		return ;
	}
	
	/**
	 * 统计数目
	 * 
	 * @param o 对象
	 * @return long
	 */
	protected int count(StringBuffer sql, List<Object> params) {
		Integer count = memory.query(sql, new ColumnHandler<Integer>(Integer.class), params);
		return count == null ? 0 : count;
	}
	
	protected String appendAnd(List<String> whereColumns) {
		return StringUtils.join(whereColumns, " =? and ") + " =?";
	}
	
	protected void appendWhere(StringBuffer sql) {
		if(sql.toString().indexOf("where")<0){
			sql.append(" where 1=1 ");
		}
	}
	
	public <K,V> List<V> getValuesFromField(List<K> list, String field) {
		return memory.getValuesFromField(list, field);
	}
	
	
}
