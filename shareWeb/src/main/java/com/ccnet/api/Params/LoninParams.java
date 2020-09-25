package com.ccnet.api.Params;

public class LoninParams {

	public static class LoginParam {
		/*
		 * 密码短信输入手机号，微信输入code
		 */
		public String loginName;
		/*
		 * 短信登陆输入验证码
		 */
		public String password;
		/*
		 * 登录类型 1:密码登录;2:手机短信登录；3微信登陆
		 */
		public String type;
		
		public String invitedCode;
		
		public String getInvitedCode() {
			return invitedCode;
		}
		public void setInvitedCode(String invitedCode) {
			this.invitedCode = invitedCode;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
	}
}
