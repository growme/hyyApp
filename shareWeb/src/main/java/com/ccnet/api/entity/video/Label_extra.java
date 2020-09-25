package com.ccnet.api.entity.video;

public class Label_extra {

	private Icon_url icon_url;
	private boolean is_redirect;
	private String redirect_url;
	private int style_type;

	public Icon_url getIcon_url() {
		return icon_url;
	}

	public void setIcon_url(Icon_url icon_url) {
		this.icon_url = icon_url;
	}

	public boolean isIs_redirect() {
		return is_redirect;
	}

	public void setIs_redirect(boolean is_redirect) {
		this.is_redirect = is_redirect;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public int getStyle_type() {
		return style_type;
	}

	public void setStyle_type(int style_type) {
		this.style_type = style_type;
	}

}
