package com.ccnet.api.entity.news;

import java.util.List;

public class Dongfang {

	private String stat;
    private List<Data> data;
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
    
}
