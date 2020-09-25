package com.ccnet.api.entity.video;

import java.util.List;

public class Weixin_cover_image {
	private int height;
	private String uri;
	private String url;
	private List<Url_list> url_list;
	private int width;

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl_list(List<Url_list> url_list) {
		this.url_list = url_list;
	}

	public List<Url_list> getUrl_list() {
		return url_list;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}
}
