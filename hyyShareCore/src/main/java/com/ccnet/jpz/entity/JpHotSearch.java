package com.ccnet.jpz.entity;

import java.util.Date;

import com.ccnet.core.entity.BaseEntity;

public class JpHotSearch extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String term;
	private Integer num;
	private Date createDate;
	private String state;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
