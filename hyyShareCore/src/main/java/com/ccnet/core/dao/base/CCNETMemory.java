package com.ccnet.core.dao.base;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import cn.ffcs.memory.PreparedStatementHandler;
import cn.ffcs.memory.ResultSetHandler;


/**
 * MySQL
 * @author Xiong Wei
 *
 */
public class CCNETMemory {

	private DataSource ds;
	private boolean sequence = false;
	private PreparedStatementHandler psh;

	public CCNETMemory(DataSource ds) {
		this.ds = ds;
		this.psh = PreparedStatementHandler.getInstance();
	}
	
	public <T> T query(StringBuffer sql, ResultSetHandler<T> rsh,
			List<Object> params) {
		return this.query(this.getConnection(), sql, rsh, params);
	}

	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
		return this.query(this.getConnection(), sql, rsh, params);
	}

	public <T> T query(Connection conn, StringBuffer sql,
			ResultSetHandler<T> rsh, List<Object> params) {
		return this.query(
				conn,
				sql.toString(),
				rsh,
				params == null ? new Object[] {} : params
						.toArray(new Object[] {}));
	}

	public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh,Object... params) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			sql = psh.adjust(sequence, sql, params);
			stmt = conn.prepareStatement(sql);
			this.fillStatement(stmt, params);
			rs = stmt.executeQuery();
			result = rsh.handle(rs);
			psh.print(sql, params);
		} catch (SQLException e) {
			psh.print(sql, params);
			throw new RuntimeException(e);
		} finally {
			close(rs, stmt, conn);
		}
		return result;
	}

	public int update(StringBuffer sql, List<Object> params) {
		return this.update(sql.toString(), params.toArray(new Object[] {}));
	}

	public int update(String sql, Object... params) {
		return this.update(this.getConnection(), sql, params);
	}

	public int update(Connection conn, StringBuffer sql, List<Object> params) {
		return this.update(conn, sql.toString(),
				params.toArray(new Object[] {}));
	}

	public int update(Connection conn, String sql, Object... params) {
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			sql = psh.adjust(sequence, sql, params);
			stmt = conn.prepareStatement(sql);
			this.fillStatement(stmt, params);
			rows = stmt.executeUpdate();
			psh.print(sql, params);
		} catch (SQLException e) {
			psh.print(sql, params);
			throw new RuntimeException(e);
		} finally {
			close(stmt, conn);
		}
		return rows;
	}

	public int[] batch(String sql, Object[][] params) {
		return this.batch(this.getConnection(), sql, params);
	}

	public int[] batch(Connection conn, String sql, Object[][] params) {
		PreparedStatement stmt = null;
		int[] rows = null;
		try {
			conn.setAutoCommit(false);
			sql = psh.adjustSQL(sequence, sql, params[0]);
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				psh.adjustParams(params[i]);
				this.fillStatement(stmt, params[i]);
				stmt.addBatch();
			}
			rows = stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {		
			psh.print(sql, params);
			close(stmt, conn);
		}
		return rows;
	}

	public <T> int create(Class<T> cls, T bean) {
		return this.create(this.getConnection(), cls, bean);
	}

	public <T> int create(Class<T> cls, T bean, String autoIncrementField) {
		return this.create(this.getConnection(), cls, bean, autoIncrementField);
	}

	public <T> int create(Connection conn, Class<T> cls, T bean) {
		return this.create(conn, cls, bean, null);
	}

	public <T> int create(Connection conn, Class<T> cls, T bean,
			String autoIncrementField) {
		return createOrUpdate(conn, cls, bean, autoIncrementField, null);
	}
	
	/**
	 * 
	 * @param conn
	 * @param cls
	 * @param bean
	 * @param autoIncrementField  自增长的属性名，如果为空，表示没有自增长
	 * @param afterOnUpdate  ON DUPLICATE KEY UPDATE 关键词之后的语句
	 * @return
	 */
	public <T> int createOrUpdate(Connection conn, Class<T> cls, T bean,
			String autoIncrementField , String afterOnUpdate) {
		int rows = 0;
		PreparedStatement stmt = null;
		try {			
			BeanInfo beanInfo = Introspector.getBeanInfo(cls, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors(); 
			
			String table = camel2underscore(cls.getSimpleName());
			String columns = "", questionMarks = "";

			List<Object> params = new ArrayList<Object>();
//			Object[] params = customKey ? new Object[pds.length]
//					: new Object[pds.length - 1];

			List<String> validColumns = getFieldNames(cls);
			boolean haveAutoIncrement = false;
			int j = 0;
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
					continue;
				}
				//如果为自增长主键，则不需要赋值
				if (StringUtils.isNotBlank(autoIncrementField) && StringUtils.equals(underscore2camel(autoIncrementField), name)) {
					haveAutoIncrement = true;
					continue;
				}
				Method getter = pd.getReadMethod();
				Object value = getter.invoke(bean);
				columns += camel2underscore(name) + ",";
				//CPSUtil.xprint("columns===>"+columns);
				questionMarks += "?,";
				params.add(value);
				//CPSUtil.xprint("params["+j+"]===>"+value);
				j++;
			}
			columns = columns.substring(0, columns.length() - 1);
			questionMarks = questionMarks.substring(0,
					questionMarks.length() - 1);
			String sql = String.format("insert into %s (%s) values (%s)",
					table, columns, questionMarks);
			if (StringUtils.isNotBlank(afterOnUpdate)) {
				sql = sql + " ON DUPLICATE KEY UPDATE " + afterOnUpdate;
			}
			sql = psh.adjust(sequence, sql, params.toArray());
			
			psh.print(sql, params.toArray());

			/**
			 * 如果使用非自定义主键，则返回主键ID的值
			 */
			if (haveAutoIncrement) {
				stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(sql);
			}

			this.fillStatement(stmt, params.toArray());
			try {
				rows = stmt.executeUpdate();
			} catch (SQLException e) {
				psh.print(sql, params.toArray());
				throw new RuntimeException(e);
			}
			/**
			 * 如果使用非自定义主键，则返回主键ID的值
			 */
			if (haveAutoIncrement) {
				ResultSet rs = stmt.getGeneratedKeys();
				Integer id = null;
				if (rs.next()) {
					id = rs.getInt(1);
				}
				
				for (PropertyDescriptor pd : pds) {
					String name = pd.getName();
					if (StringUtils.equals(name, autoIncrementField)) {
						Method setter = pd.getWriteMethod();
						setter.invoke(bean, id);
						break;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} finally {
			close(stmt, conn);
		}
		return rows;
	}

	public <T> int[] create(Class<T> cls, List<T> beans) {
		return create(this.getConnection(), cls, beans, null);
	}

	public <T> int[] create(Class<T> cls, List<T> beans, String autoIncrementField) {
		return create(this.getConnection(), cls, beans, autoIncrementField);
	}

	public <T> int[] create(Connection conn, Class<T> cls, List<T> beans) {
		return create(conn, cls, beans, null);
	}
	
	public <T> int[] create(Connection conn, Class<T> cls, List<T> beans,
			String autoIncrementField) {
		return createOrUpdate(conn, cls, beans, autoIncrementField, null);
	}

	public <T> int[] createOrUpdate(Connection conn, Class<T> cls, List<T> beans,
			String autoIncrementField, String afterOnUpdate) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(cls, Object.class);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors(); 

		// build SQL
		String table = camel2underscore(cls.getSimpleName());
		String columns = "", questionMarks = "";
		
		List<String> validColumns = getFieldNames(cls);
		int fieldCount = 0;
		for (PropertyDescriptor pd : pds) {
			String name = pd.getName();
			if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
				continue;
			}
			//如果为自增长主键，则不需要赋值
			if (StringUtils.isNotBlank(autoIncrementField) && StringUtils.equals(underscore2camel(autoIncrementField), name)) {
				continue;
			}
			columns += camel2underscore(name) + ",";
			questionMarks += "?,";
			fieldCount++;
		}
		columns = columns.substring(0, columns.length() - 1);
		questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
		String sql = String.format("insert into %s (%s) values (%s)", table,
				columns, questionMarks);
		if (StringUtils.isNotBlank(afterOnUpdate)) {
			sql = sql + " ON DUPLICATE KEY UPDATE " + afterOnUpdate;
		}
		// build parameters */
		int rows = beans.size();
		int cols = fieldCount;

		Object[][] params = new Object[rows][cols];
		try {
			for (int i = 0; i < rows; i++) {
				int j = 0;
				for (PropertyDescriptor pd : pds) {
					String name = pd.getName();
					if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
						continue;
					}
					if (StringUtils.isNotBlank(autoIncrementField) && StringUtils.equals(underscore2camel(autoIncrementField), name)) {
						continue;
					}
					Method getter = pd.getReadMethod();
					Object value = getter.invoke(beans.get(i));				
					params[i][j] = value;
					j++;
				}
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		// execute
		return batch(conn,sql, params);
	}

	public <T> int update(Class<T> cls, T bean, String primaryKey) {
		return this.update(this.getConnection(), cls, bean, primaryKey);
	}

	public <T> int update(Connection conn, Class<T> cls, T bean,
			String primaryKey) {		
		try {			
			BeanInfo beanInfo = Introspector.getBeanInfo(cls, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();		
			
			List<Object> params = new ArrayList<Object>(0);			
			primaryKey = underscore2camel(primaryKey);
			Object id = 0;
			String columnAndQuestionMarks = "";
			
			List<String> validColumns = getFieldNames(cls);
			
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
					continue;
				}
				Method getter = pd.getReadMethod();
				Object value = getter.invoke(bean);
				if (name.equals(primaryKey)) {
					id = value;
				} else {
					columnAndQuestionMarks += camel2underscore(name) + "=?,";
					params.add(value);
				}
			}
			params.add(id);			
			String table = camel2underscore(cls.getSimpleName());
			columnAndQuestionMarks = columnAndQuestionMarks.substring(0,
					columnAndQuestionMarks.length() - 1);
			String sql = String.format("update %s set %s where %s = ?", table,
					columnAndQuestionMarks, camel2underscore(primaryKey));
			return update(conn, sql, params.toArray());			
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> int[] update(Class<T> cls, List<T> beans) {
		return this.update(cls, beans, "id");
	}

	public <T> int[] update(Connection conn, Class<T> cls, List<T> beans) {
		return this.update(conn, cls, beans, "id");
	}

	public <T> int[] update(Class<T> cls, List<T> beans, String primaryKey) {
		return this.update(this.getConnection(), cls, beans, primaryKey);
	}

	public <T> int[] update(Connection conn, Class<T> cls, List<T> beans,
			String primaryKey) {
		try {		
			BeanInfo beanInfo = Introspector.getBeanInfo(cls, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();	
			
			primaryKey = underscore2camel(primaryKey);
			String columnAndQuestionMarks = "";
			
			List<String> validColumns = getFieldNames(cls);

			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();	
				if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
					continue;
				}
				if (name.equals(primaryKey)) {
				} else {
					columnAndQuestionMarks += camel2underscore(name) + "=?,";
				}
			}
			String table = camel2underscore(cls.getSimpleName());
			columnAndQuestionMarks = columnAndQuestionMarks.substring(0,
					columnAndQuestionMarks.length() - 1);
			String sql = String.format("update %s set %s where %s = ?", table,
					columnAndQuestionMarks, camel2underscore(primaryKey));

			// build parameters
			int rows = beans.size();
			int cols = validColumns.size() + 1;
			Object id = 0;
			Object[][] params = new Object[rows][cols];
			for (int i = 0; i < rows; i++) {
				int j = 0;
				for (PropertyDescriptor pd : pds) {
					String name = pd.getName();
					if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
						continue;
					}
					Method getter = pd.getReadMethod();
					Object value = getter.invoke(beans.get(i));				
					if (name.equals(primaryKey)) {
						id = value;
					} else {
						params[i][j] = value;
						j++;
					}
				}
				params[i][j] = id;
			}
			return batch(conn, sql, params);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	public void pager(StringBuffer sql, List<Object> params, int pageSize,
			int pageNo) {
		//如果pageNo, pageSize不符合，则不考虑分页查
		if (pageNo > 0 && pageSize > 0 && pageSize < Integer.MAX_VALUE) {
			psh.pager(sequence, sql, params, pageSize, pageNo);
		}
	}

	public <T> void in(StringBuffer sql, List<Object> params, String operator,
			String field, List<T> values) {
		psh.in(sequence, sql, params, operator, field, values);
	}
	
	public <T> List<String> parseWhereColumns(List<Object> params, Class<T> cls, T bean) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(cls, Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors(); 
			List<String> validColumns = getFieldNames(cls);
			List<String> whereColumns = new ArrayList<String>();
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
					continue;
				}
				Method getter = pd.getReadMethod();
				Object value = getter.invoke(bean);
				//有值的作为条件
				if (isNotEmpty(value)) {
					whereColumns.add(camel2underscore(name));
					params.add(value);
				}
			}
			return whereColumns;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isNotEmpty(Object object) {
		if (object == null) {
			return false;
		}
		if (object instanceof String) {
			return StringUtils.isNotBlank((String)object);
		}
		return true;
	}
	
	public Connection getConnection() {
		try {
			return this.ds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void fillStatement(PreparedStatement stmt, Object... params) {
		if (params == null)
			return;
		try {
			for (int i = 0; i < params.length; i++) {
				// hack oracle's bug (version <= 9)
				if (sequence && params[i] == null) {
					stmt.setNull(i + 1, Types.VARCHAR);

				} else {
					stmt.setObject(i + 1, params[i]);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			close(stmt, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void close(Statement stmt, Connection conn) {
		try {
			if (stmt != null) {
				stmt.close();
			}
			close(conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void close(Connection conn) {
		try {
			if (conn != null && conn.getAutoCommit()) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String camel2underscore(String camel) {
		return psh.camel2underscore(camel);
	}

	public String underscore2camel(String underscore) {
		return psh.underscore2camel(underscore);
	}
	
	public <T> List<String> getFieldNames(Class<T> cls) {
		Field[] fields = cls.getDeclaredFields();
		List<String> fieldNames = new ArrayList<String>();
		for (Field field : fields) {
			//System.out.println(field.getName());
			IgnoreTableField t = field.getAnnotation(IgnoreTableField.class);
			if (t == null) {
				fieldNames.add(field.getName());
			}
		}
		if (cls.getSuperclass() != null) {
			fieldNames.addAll(getFieldNames(cls.getSuperclass()));
		}
		return fieldNames;
	}
	

	public <T,V> List<V> getValuesFromField(List<T> list, String field) {
		List<V> params = new ArrayList<V>();
		if (CollectionUtils.isEmpty(list) || StringUtils.isBlank(field)) {
			return params;
		}
		for (T t : list) {
			BeanInfo beanInfo = null;
			try {
				beanInfo = Introspector.getBeanInfo(t.getClass(), Object.class);
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors(); 
			List<String> validColumns = getFieldNames(t.getClass());
			for (PropertyDescriptor pd : pds) {
				String name = pd.getName();
				if (CollectionUtils.isEmpty(validColumns) || !validColumns.contains(name)) {
					continue;
				}
				if (StringUtils.equalsIgnoreCase(name, field)) {
					Method getter = pd.getReadMethod();
					Object value = null;
					try {
						value = getter.invoke(t);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
					//有值的作为条件
					if (isNotEmpty(value)) {
						if (!params.contains((V)value)) {
							params.add((V)value);
						}
					}
				}
			}
		}
		return params;
	}
 
}
