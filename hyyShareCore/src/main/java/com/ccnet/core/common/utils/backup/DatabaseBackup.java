package com.ccnet.core.common.utils.backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
/**
 * MySQL数据库的备份与恢复 缺陷：可能会被杀毒软件拦截
 * 
 * @author xxx
 * @version xxx
 */
public class DatabaseBackup {
	private static Logger logger = Logger.getLogger(DatabaseBackup.class);
	/** MySQL安装目录的Bin目录的绝对路径 */
	private String mysqlBinPath;
	/** 访问MySQL数据库的用户名 */
	private String username;
	/** 访问MySQL数据库的密码 */
	private String password;
	
	public String getMysqlBinPath() {
		return mysqlBinPath;
	}
	public void setMysqlBinPath(String mysqlBinPath) {
		this.mysqlBinPath = mysqlBinPath;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DatabaseBackup(String mysqlBinPath, String username, String password) {
		if (StringUtils.isNotBlank(mysqlBinPath) && !mysqlBinPath.endsWith(File.separator)) {
			mysqlBinPath = mysqlBinPath + File.separator;
		}
		this.mysqlBinPath = mysqlBinPath;
		this.username = username;
		this.password = password;
	}
	/**
	 * 备份数据库
	 * 
	 * @param output
	 *            输出流
	 * @param dbname
	 *            要备份的数据库名
	 */
	public void backup(String backPath, OutputStream output, String host, int port, String dbname) {
		String command = mysqlBinPath + "mysqldump -h " + host + " -P" + port +" -u" + username + " -p" + password + " --set-charset=utf8 " + dbname;
		PrintWriter p = null;
		BufferedReader reader = null;
		try {
			p = new PrintWriter(new OutputStreamWriter(output, "utf8"));
			File dirFile = new File(StringUtils.isNotBlank(mysqlBinPath) ? mysqlBinPath : backPath);
			Process process = Runtime.getRuntime().exec(command, null, dirFile);
			InputStreamReader inputStreamReader = new InputStreamReader(process
					.getInputStream(), "utf8");
			reader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				p.println(line);
			}
			p.flush();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (p != null) {
					p.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	/**
	 * 备份数据库，如果指定路径的文件不存在会自动生成
	 * 
	 * @param dest
	 *            备份文件的路径
	 * @param dbname
	 *            要备份的数据库
	 */
	public void backup(String backPath, String dest, String host, int port, String dbname) {
		try {
			OutputStream out = new FileOutputStream(backPath + dest);
			backup(backPath, out, host, port, dbname);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 恢复数据库
	 * 
	 * @param input
	 *            输入流
	 * @param dbname
	 *            数据库名
	 */
	public void restore(String backPath, InputStream input, String host, int port, String dbname) {
		String command = mysqlBinPath + "mysql -h" + host + " -P" + port + " -u" + username + " -p" + password + " --default-character-set=utf8 " + dbname + " ";
		try {
			File dirFile = new File(StringUtils.isNotBlank(mysqlBinPath) ? mysqlBinPath : backPath);
			Process process = Runtime.getRuntime().exec(command, null, dirFile);
			OutputStream out = process.getOutputStream();
			String line = null;
			String outStr = null;
			StringBuffer sb = new StringBuffer("");
			BufferedReader br = new BufferedReader(new InputStreamReader(input,"utf8"));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			outStr = sb.toString();
			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			writer.flush();
			out.close();
			br.close();
			writer.close();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	/**
	 * 恢复数据库
	 * 
	 * @param dest
	 *            备份文件的路径
	 * @param dbname
	 *            数据库名
	 */
	public void restore(String backPath, String dest, String host, int port, String dbname) {
		try {
			InputStream input = new FileInputStream(backPath + dest);
			restore(backPath, input, host, port, dbname);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	}
	public static void main(String[] args) {
		String binPath = "";
		String userName = "root";
		String pwd = "root";
		DatabaseBackup bak = new DatabaseBackup(binPath, userName, pwd);
		//bak.backup("c://wxb_site_new_bg.sql", "wxb_site_new_bg");
        bak.restore("E:/apache-tomcat-7.0.21/bin/backdatabase/", "DataBaseBack_20161105154744.sql", "localhost", 3306, "cpsshop");
	}
}
