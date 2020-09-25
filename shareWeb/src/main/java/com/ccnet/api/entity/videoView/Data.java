package com.ccnet.api.entity.videoView;

public class Data {

	private int status;
	private String user_id;
	private String video_id;
	private String validate;
	private boolean enable_ssl;
	private String poster_url;
	private double video_duration;
	private String media_type;
	private String auto_definition;
	private Video_list video_list;
	private String dynamic_video;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public boolean isEnable_ssl() {
		return enable_ssl;
	}
	public void setEnable_ssl(boolean enable_ssl) {
		this.enable_ssl = enable_ssl;
	}
	public String getPoster_url() {
		return poster_url;
	}
	public void setPoster_url(String poster_url) {
		this.poster_url = poster_url;
	}
	public double getVideo_duration() {
		return video_duration;
	}
	public void setVideo_duration(double video_duration) {
		this.video_duration = video_duration;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public String getAuto_definition() {
		return auto_definition;
	}
	public void setAuto_definition(String auto_definition) {
		this.auto_definition = auto_definition;
	}
	public Video_list getVideo_list() {
		return video_list;
	}
	public void setVideo_list(Video_list video_list) {
		this.video_list = video_list;
	}
	public String getDynamic_video() {
		return dynamic_video;
	}
	public void setDynamic_video(String dynamic_video) {
		this.dynamic_video = dynamic_video;
	}
	
}
