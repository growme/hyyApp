package com.ccnet.admin.bh.entity.video;

import java.util.List;

public class Video {

	private String message;
    private List<Data> data;
    private int total_number;
    private boolean has_more;
    private int login_status;
    private int show_et_status;
    private String post_content_hint;
    private boolean has_more_to_refresh;
    private int action_to_last_stick;
    private int feed_flag;
    private Tips tips;
    private boolean is_use_bytedance_stream;
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}
	public int getTotal_number() {
		return total_number;
	}
	public void setTotal_number(int total_number) {
		this.total_number = total_number;
	}
	public boolean isHas_more() {
		return has_more;
	}
	public void setHas_more(boolean has_more) {
		this.has_more = has_more;
	}
	public int getLogin_status() {
		return login_status;
	}
	public void setLogin_status(int login_status) {
		this.login_status = login_status;
	}
	public int getShow_et_status() {
		return show_et_status;
	}
	public void setShow_et_status(int show_et_status) {
		this.show_et_status = show_et_status;
	}
	public String getPost_content_hint() {
		return post_content_hint;
	}
	public void setPost_content_hint(String post_content_hint) {
		this.post_content_hint = post_content_hint;
	}
	public boolean isHas_more_to_refresh() {
		return has_more_to_refresh;
	}
	public void setHas_more_to_refresh(boolean has_more_to_refresh) {
		this.has_more_to_refresh = has_more_to_refresh;
	}
	public int getAction_to_last_stick() {
		return action_to_last_stick;
	}
	public void setAction_to_last_stick(int action_to_last_stick) {
		this.action_to_last_stick = action_to_last_stick;
	}
	public int getFeed_flag() {
		return feed_flag;
	}
	public void setFeed_flag(int feed_flag) {
		this.feed_flag = feed_flag;
	}
	public Tips getTips() {
		return tips;
	}
	public void setTips(Tips tips) {
		this.tips = tips;
	}
	public boolean isIs_use_bytedance_stream() {
		return is_use_bytedance_stream;
	}
	public void setIs_use_bytedance_stream(boolean is_use_bytedance_stream) {
		this.is_use_bytedance_stream = is_use_bytedance_stream;
	}
    
}
