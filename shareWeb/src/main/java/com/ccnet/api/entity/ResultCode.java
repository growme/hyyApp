/**
 * created by 朱施健 2019年3月23日 下午2:15:06
 */
package com.ccnet.api.entity;

/**
 * @author 朱施健
 * 返回状态码
 */
public interface ResultCode {
	
	int code();
	String name();
	String description();
	boolean show();
	
	default boolean isOK() {
		return code()==1;
	}
	
	public static enum BasicCode implements ResultCode{
		正常(1, false, null),
		登陆过期(0, true, "登陆过期，请重新登录"),
		
		NOTHING(Integer.MIN_VALUE+7, false, "nothing"),
		METHOD不支持(Integer.MIN_VALUE+6, true, "METHOD不支持"),
		不可操作(Integer.MIN_VALUE+5, true, "不可操作"),
		权限不够(Integer.MIN_VALUE+4, true, "权限不够"),
		数据关联(Integer.MIN_VALUE+3, true, "数据关联"),
		逻辑错误(Integer.MIN_VALUE+2, true, "逻辑错误"),
		参数错误(Integer.MIN_VALUE+1, true, "参数错误"),
		系统繁忙(Integer.MIN_VALUE, true, "服务器开小差了~"),
		;
		
		public final int code;
		public final boolean show;
		public final String desc;

		BasicCode(int code,boolean show, String desc) {
	        this.code = code;
	        this.show = show;
	        this.desc = desc;
	    }

		public int code() { return code; }
		public boolean show() { return show; }
		public String description() { return desc; }
	}
}
