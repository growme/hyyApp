package com.ccnet.admin.bh.entity.video;

import java.util.List;

public class Large_image_list {

	private int height;
    private String uri;
    private String url;
    private List<Url_list> url_list;
    private int width;
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Url_list> getUrl_list() {
		return url_list;
	}
	public void setUrl_list(List<Url_list> url_list) {
		this.url_list = url_list;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
    
}
