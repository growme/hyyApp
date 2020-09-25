package com.ccnet.core.common.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomNum;

/**
 * 扣量机制实现
 * 
 * @author jackieWang
 * 
 */
public class UserContentReadCache {

	private static UserContentReadCache instance = new UserContentReadCache();
	private ConcurrentMap<Integer, Discount> userCount = new ConcurrentHashMap<Integer, Discount>();
	
	private ConcurrentMap<Integer, Discount> adCount = new ConcurrentHashMap<Integer, Discount>();

	private UserContentReadCache() {

	}

	public static UserContentReadCache getInstance() {
		return instance;
	}

	/**
	 * 控制扣量cnzz
	 * 
	 * @param userId
	 * @param _dis
	 * @return
	 */
	public boolean canShowPage(int userId, int dis) {
		Discount discount = userCount.get(userId);
		if (discount == null) {
			synchronized (this) {
				discount = userCount.get(userId);
				if (discount == null) {
					discount = new Discount(100, dis);
					userCount.put(userId, discount);
				}
			}
		}
		synchronized (discount) {
			if (discount.getStandard() <= 0) {
				//访问100次重新初始化
				discount.setStandard(100);
				discount.setPercent(dis);
			}
			//每访问一次，计数器减1
			discount.setStandard(discount.getStandard() - 1);
			Integer percent = discount.getPercent();
			if (percent > 0) {
				// 随机生成100以内的数字 如果小于30就从30中扣除
				int count = RandomNum.getRandVal(100);
				if (count < dis * 1.5) {
					discount.setPercent(percent - 1);
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean canShowAdPage(int adId, int dis) {
		Discount discount = adCount.get(adId);
		if (discount == null) {
			synchronized (this) {
				discount = adCount.get(adId);
				if (discount == null) {
					discount = new Discount(100, dis);
					adCount.put(adId, discount);
				}
			}
		}
		synchronized (discount) {
			if (discount.getStandard() <= 0) {
				//访问100次重新初始化
				discount.setStandard(100);
				discount.setPercent(dis);
			}
			//每访问一次，计数器减1
			discount.setStandard(discount.getStandard() - 1);
			Integer percent = discount.getPercent();
			if (percent > 0) {
				// 随机生成100以内的数字 如果小于30就从30中扣除
				int count = RandomNum.getRandVal(100);
				if (count < dis * 1.5) {
					discount.setPercent(percent - 1);
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
		boolean bool = false; 
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < 1000; i++) {
			bool = UserContentReadCache.instance.canShowPage(1, 30);
			if(bool){
				count1++;
			}else{
				count2++;
			}
			CPSUtil.xprint("测试第["+i+"]次结果为："+bool);
		}
		
		CPSUtil.xprint("测试结果为 ：扣量【"+count1+"】次===> 不扣量【"+count2+"】次");
	}

}
