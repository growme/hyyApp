package com.ccnet.admin.bh.entity.video;

public class User_info {

	private String avatar_url;
	private String description;
	private boolean follow;
	private int follower_count;
	private int live_info_type;
	private String name;
	private String schema;
	private String user_auth_info;
	private long user_id;
	private boolean user_verified;
	private String verified_content;

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setFollow(boolean follow) {
		this.follow = follow;
	}

	public boolean getFollow() {
		return follow;
	}

	public void setFollower_count(int follower_count) {
		this.follower_count = follower_count;
	}

	public int getFollower_count() {
		return follower_count;
	}

	public void setLive_info_type(int live_info_type) {
		this.live_info_type = live_info_type;
	}

	public int getLive_info_type() {
		return live_info_type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchema() {
		return schema;
	}

	public void setUser_auth_info(String user_auth_info) {
		this.user_auth_info = user_auth_info;
	}

	public String getUser_auth_info() {
		return user_auth_info;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_verified(boolean user_verified) {
		this.user_verified = user_verified;
	}

	public boolean getUser_verified() {
		return user_verified;
	}

	public void setVerified_content(String verified_content) {
		this.verified_content = verified_content;
	}

	public String getVerified_content() {
		return verified_content;
	}
}
