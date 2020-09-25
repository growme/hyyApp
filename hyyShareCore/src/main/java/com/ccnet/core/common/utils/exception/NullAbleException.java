package com.ccnet.core.common.utils.exception;

import com.ccnet.core.common.utils.base.Const;


/**
 * 非空异常校验类<br>
 * 
 * @author wgp
 * @since 2013-01-15
 */
public class NullAbleException extends RuntimeException {

	private String nullField;

	/**
	 * 构造函数1
	 * 
	 * @param 非空校验类
	 */
	public NullAbleException() {
		super(Const.Exception_Head + "对象不能为空,请检查.");
	}

	/**
	 * 构造函数2
	 * 
	 * @param 非空校验类
	 */
	public NullAbleException(Class cs) {
		super(Const.Exception_Head + "对象不能为空,请检查.[" + cs + "]");
	}

	/**
	 * 构造函数3
	 * 
	 * @param pNullField
	 *            异常附加信息
	 */
	public NullAbleException(String pNullField) {
		super(Const.Exception_Head + "对象属性[" + pNullField + "]不能为空,请检查.");
		this.setNullField(pNullField);
	}

	public String getNullField() {
		return nullField;
	}

	public void setNullField(String nullField) {
		this.nullField = nullField;
	}
}
