package com.ccnet.core.entity;

import java.util.Date;
/**
 * 工具：邮箱发送日志实体类
 */
public class Email extends BaseEntity{
	
	
	private static final long serialVersionUID = 1L;
	
	private String id;  //主键
	private String subject; //标题
	private String body;  //内容
	private String toList;  //接收人
	private String ccList;  //抄送
	private String keyWord;
	private Date createTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
