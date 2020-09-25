
package com.ccnet.core.common.utils.barcode;

/**
 * ClassName: QRParamsException 
 * @Description: 异常处理
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-4-26
 */
public class QRParamsException extends Exception {
	private static final long serialVersionUID = 8837582301762730656L;
	public QRParamsException()  {}                //用来创建无参数对象
    public QRParamsException(String message) {    //用来创建指定参数对象
        super(message);                           //调用超类构造器
    }
}
