package com.ccnet.api.entity.video;

public class Share_info {

	private String cover_image;
	private String description;
	private int on_suppress;
	private Share_type share_type;
	private String share_url;
	private String title;
	private int token_type;
	private Weixin_cover_image weixin_cover_image;

	public void setCover_image(String cover_image) {
		this.cover_image = cover_image;
	}

	public String getCover_image() {
		return cover_image;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setOn_suppress(int on_suppress) {
		this.on_suppress = on_suppress;
	}

	public int getOn_suppress() {
		return on_suppress;
	}

	public void setShare_type(Share_type share_type) {
		this.share_type = share_type;
	}

	public Share_type getShare_type() {
		return share_type;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setToken_type(int token_type) {
		this.token_type = token_type;
	}

	public int getToken_type() {
		return token_type;
	}

	public void setWeixin_cover_image(Weixin_cover_image weixin_cover_image) {
		this.weixin_cover_image = weixin_cover_image;
	}

	public Weixin_cover_image getWeixin_cover_image() {
		return weixin_cover_image;
	}
}
