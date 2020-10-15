package com.ccnet.api.entity;

import com.ccnet.api.entity.ResultCode;

public enum AppResultCode implements ResultCode {

	用户不存在(--C.STATUS_CODE, true, "用户不存在"),

	文章不存在(--C.STATUS_CODE, true, "文章不存在"),

	无可用域名(--C.STATUS_CODE, true, "无可用域名"),

	手机号已存在(--C.STATUS_CODE, true, "手机号已存在"),

	手机号不能为空(--C.STATUS_CODE, true, "手机号不能为空"),
	用户未绑定手机号(--C.STATUS_CODE, true, "用户未绑定手机号"),
	密码不一致(--C.STATUS_CODE, true, "密码不一致"),

	请不要频繁发短信(--C.STATUS_CODE, true, "请不要频繁发短信"), 验证码无效(--C.STATUS_CODE, true, "验证码无效"),

	验证码不正确(--C.STATUS_CODE, true, "验证码不正确"),

	密码不正确(--C.STATUS_CODE, true, "密码不正确"),

	请用微信注册登陆(--C.STATUS_CODE, true, "请用微信注册登陆"),

	微信授权登录失败(--C.STATUS_CODE, true, "微信授权登录失败"),

	邀请关系已存在(--C.STATUS_CODE, true, "邀请关系已存在"), 邀请错误(--C.STATUS_CODE, true, "邀请人不能是自己"),

	微信提现金额不能超过200元(--C.STATUS_CODE, true, "微信提现金额不能超过200元"),

	首次提现余额需大于5元(--C.STATUS_CODE, true, "首次提现余额需大于5元"),

	账户余额大于10元才能提现(--C.STATUS_CODE, true, "账户余额大于10元才能提现"),

	余额不足(--C.STATUS_CODE, true, "余额不足"),

	未查询到当前账户余额(--C.STATUS_CODE, true, "未查询到当前账户余额"),

	申请提现成功(--C.STATUS_CODE, true, "申请提现成功,请耐心等待!"),

	申请提现失败(--C.STATUS_CODE, true, "申请提现失败,请稍后再试!"),

	微信提现金额不能小于1元(--C.STATUS_CODE, true, "提现金额不能小于1元"),

	需要完成文章分享开启宝箱(--C.STATUS_CODE, true, "需要完成文章分享开启宝箱"),

	已获得新人奖励(--C.STATUS_CODE, true, "已获得新人奖励！"),

	任务次数达到上限(--C.STATUS_CODE, true, "任务次数达到上限！"),

	你已阅读很长时间啦(--C.STATUS_CODE, true, "你已阅读很长时间啦！"),

	翻倍未完成任务(--C.STATUS_CODE, true, "翻倍未完成任务！"),

	登录后获取更多更多奖励(--C.STATUS_CODE, true, "用户尚未登录，登录后获取更多更多奖励！"), 
	支付宝账号信息不能为空(--C.STATUS_CODE, true, "支付宝账号或真实姓名不能为空！"),

	微信圈提现每日只限一次(--C.STATUS_CODE, true, "微信圈提现每日只限一次！");

	public final int code;
	public final boolean show;
	public final String desc;

	AppResultCode(int code, boolean show, String desc) {
		this.code = code;
		this.show = show;
		this.desc = desc;
	}

	public int code() {
		return code;
	}

	public boolean show() {
		return show;
	}

	public String description() {
		return desc;
	}

	private static class C {
		private static int STATUS_CODE = -3000;
	}
}
