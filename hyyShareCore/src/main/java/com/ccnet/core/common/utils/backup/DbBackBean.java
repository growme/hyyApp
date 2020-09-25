package com.ccnet.core.common.utils.backup;

import java.util.Date;

public class DbBackBean {

	private String fileName;
	private long fileSize;
	private Date date;

	public DbBackBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DbBackBean(String fileName, long fileSize, Date date) {
		super();
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.date = date;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
