package com.ccnet.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MakeOrderNum
 * @CreateTime 2016年11月12日 下午4:51:02
 * @author : jackie wang
 * @Description: 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 * 
 */
public class MakeOrderNum {
	
	public static final String PREFIX_ORDERFLOW= "LS";
	public static final String PREFIX_PAY= "PY";
	
	//微信订单
	public static final String PREFIX_WX= "WX";
	
	//代客下单
	public static final String PREFIX_DL= "DL";
	
	//spc下单
	public static final String PREFIX_PC= "PC";
	
	//异常订单
	public static final String PREFIX_YC= "YC";
	 
	/**
	 * 订单号生成计数器
	 */
	private static long orderNumCount = 0L;
	/**
	 * 每毫秒生成订单号数量最大值
	 */
	private static final int maxPerMSECSize = 1000;

	/**
	 * 生成带前缀的流水号
	 * @param prefix
	 */
	public static String getOrderFlowNum(String prefix) {
		// 最终生成的订单号
		StringBuilder id = new StringBuilder(prefix);
		synchronized (MakeOrderNum.class) {
			// 取系统当前时间作为订单号变量前半部分，精确到毫秒
			id.append(new SimpleDateFormat("yyyyMMddHHmmssSSS")
					.format(new Date()));
			// 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
			if (orderNumCount > maxPerMSECSize) {
				orderNumCount = 0L;
			}
			// 组装订单号
			id.append(String.valueOf(maxPerMSECSize + orderNumCount).substring(
					1));
			orderNumCount++;
		}
		CPSUtil.xprint("orderNum=" + id.toString());
	   return id.toString();
	}

	public static void main(String[] args) {
		// 测试多线程调用订单号生成工具
		try {
			for (int i = 0; i < 200; i++) {
				Thread t1 = new Thread(new Runnable() {
					public void run() {
						//MakeOrderNum makeOrder = new MakeOrderNum();
						//makeOrder.makeOrderNum("a");
						getOrderFlowNum("WXD");
					}
				}, "at" + i);
				t1.start();

				Thread t2 = new Thread(new Runnable() {
					public void run() {
						//MakeOrderNum makeOrder = new MakeOrderNum();
						//makeOrder.makeOrderNum("b");
						getOrderFlowNum("SPC");
					}
				}, "bt" + i);
				t2.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
