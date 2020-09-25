package com.ccnet.api.entity.video;

public class Media_info {

	private String avatar_url;
	private boolean follow;
	private boolean is_star_user;
	private long media_id;
	private String name;
	private String recommend_reason;
	private int recommend_type;
	private long user_id;
	private boolean user_verified;
	private String verified_content;

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public boolean isFollow() {
		return follow;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public boolean isIs_star_user() {
		return is_star_user;
	}

	public void setIs_star_user(boolean is_star_user) {
		this.is_star_user = is_star_user;
	}

	public long getMedia_id() {
		return media_id;
	}

	public void setMedia_id(long media_id) {
		this.media_id = media_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecommend_reason() {
		return recommend_reason;
	}

	public void setRecommend_reason(String recommend_reason) {
		this.recommend_reason = recommend_reason;
	}

	public int getRecommend_type() {
		return recommend_type;
	}

	public void setRecommend_type(int recommend_type) {
		this.recommend_type = recommend_type;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public boolean isUser_verified() {
		return user_verified;
	}

	public void setUser_verified(boolean user_verified) {
		this.user_verified = user_verified;
	}

	public String getVerified_content() {
		return verified_content;
	}

	public void setVerified_content(String verified_content) {
		this.verified_content = verified_content;
	}
}
