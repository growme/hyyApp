package com.ccnet.core.common.utils.backup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSource;
import com.ccnet.core.common.utils.SpringWebContextUtil;


public class DatabaseBackupHelper {
	private static final Logger LOG = Logger.getLogger(DatabaseBackupHelper.class);
	
	private static String backPath = System.getProperty("user.dir");
	
	static {
		if (StringUtils.isNotBlank(backPath)) {
			File file = new File(backPath);
			if (file.isDirectory()) {
				backPath = backPath + File.separator + "backdatabase" + File.separator;
				file = new File(backPath);
				file.mkdir();
			}
		}
	}
	
	private static String getMysqlBinPath() {
		return "";
	}
	
	public static void backup(String fileName) throws Exception {
		DruidDataSource source = (DruidDataSource) SpringWebContextUtil.getApplicationContext().getBean("dataSource");
		DatabaseBackup databaseBackup = new DatabaseBackup(getMysqlBinPath(), source.getUsername(), source.getPassword());
		String dbname = "";
		String host = "";
		int port = 3306;
		if (StringUtils.equalsIgnoreCase("mysql", source.getDbType())) {
			//jdbc:mysql://localhost:3306/cpsshop?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
			String url = source.getUrl();
			url = StringUtils.substringAfter(url, "//");
			url = StringUtils.substringBefore(url, "?");
			dbname = StringUtils.substringAfterLast(url, "/");
			host = StringUtils.substringBefore(url, ":");
			String p = StringUtils.substringBetween(url, ":", "/");
			if (NumberUtils.isDigits(p)) {
				port = Integer.parseInt(p);
			}
		}
		databaseBackup.backup(backPath, fileName, host, port, dbname);
		LOG.info("Back Database(" + dbname + ") to file name: " + backPath + fileName);
	}
	
	public static void restore(String fileName) {
		DruidDataSource source = (DruidDataSource) SpringWebContextUtil.getApplicationContext().getBean("dataSource");
		DatabaseBackup databaseBackup = new DatabaseBackup(getMysqlBinPath(), source.getUsername(), source.getPassword());
		String dbname = "";
		String host = "";
		int port = 3306;
		if (StringUtils.equalsIgnoreCase("mysql", source.getDbType())) {
			//jdbc:mysql://localhost:3306/cpsshop?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
			String url = source.getUrl();
			url = StringUtils.substringAfter(url, "//");
			url = StringUtils.substringBefore(url, "?");
			dbname = StringUtils.substringAfterLast(url, "/");
			host = StringUtils.substringBefore(url, ":");
			String p = StringUtils.substringBetween(url, ":", "/");
			if (NumberUtils.isDigits(p)) {
				port = Integer.parseInt(p);
			}
		}
		databaseBackup.restore(backPath, fileName, host, port, dbname);
		LOG.info("Restore Database(" + dbname + ") from file name: " + backPath + fileName);
	}
	
	public static List<DbBackBean> findAllBackFiles() {
		File dirFile = new File(backPath);
		File[] files = dirFile.listFiles();
		List<DbBackBean> list = new ArrayList<DbBackBean>();
		for (File file : files) {
			DbBackBean bean = new DbBackBean();
			bean.setFileName(file.getName());
			bean.setFileSize(file.length());
			bean.setDate(new Date(file.lastModified()));
			if (bean.getFileSize() > 0) {
				list.add(bean);
			}
		}
		Collections.sort(list, new Comparator<DbBackBean>() {
			@Override
			public int compare(DbBackBean o1, DbBackBean o2) {
				//倒序排列
				// TODO Auto-generated method stub
				return - o1.getDate().compareTo(o2.getDate());
			}
		});
		return list;
	}
	
	public static void delete(List<String> fileNames) {
		for (String fileName : fileNames) {
			File file = new File(backPath + fileName);
			if (file.isFile()) {
				file.delete();
			}
		}
	}
	
	public static void main(String[] args) {
		String dbname = "";
		String host = "";
		int port = 3306;
		//
		String url = "jdbc:mysql://localhost:3308/cpsshop?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
		url = StringUtils.substringAfter(url, "//");
		url = StringUtils.substringBefore(url, "?");
		dbname = StringUtils.substringAfterLast(url, "/");
		host = StringUtils.substringBefore(url, ":");
		String p = StringUtils.substringBetween(url, ":", "/");
		if (NumberUtils.isDigits(p)) {
			port = Integer.parseInt(p);
		}
		System.out.println(host + "    " + port + "   " + dbname);
	}
	
}
