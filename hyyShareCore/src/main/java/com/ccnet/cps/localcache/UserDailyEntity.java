package com.ccnet.cps.localcache;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;

public class UserDailyEntity {

	private String userId;
	private volatile double readMoneyRate = 1; // 用户收益倍率，默认是0(后台会根据会员等级进行调整)
	private volatile Integer recomUserId; // 推荐人用户ID
	private volatile double bonusMoneyRate = 0.2; // 推荐人连带收益率
	private volatile double bonusParentMoneyRate = 0.05; // 1级推荐人连带收益率
	private volatile boolean initCache = false;  //是否需要初始化参数
	private Date date;
	private AtomicDouble dailyUpperMoney = new AtomicDouble(0); // 每日允许的金额上限，默认无限大
	private AtomicDouble dailyMoney = new AtomicDouble();
	private AtomicInteger actualReadCount = new AtomicInteger(0); // 真实的阅读数
	private AtomicInteger saveReadCount = new AtomicInteger(0); // 存档的阅读数
	private AtomicDouble actualReadMoney = new AtomicDouble(0); // 真实的阅读金额
	private AtomicDouble saveReadMoney = new AtomicDouble(0); // 存档的阅读金额
	private AtomicInteger actualBonusCount = new AtomicInteger(0); // 真实的提成次数
	private AtomicInteger saveBonusCount = new AtomicInteger(0); // 存档的提成次数
	private AtomicDouble actualBonusMoney = new AtomicDouble(0); // 真实的提成金额
	private AtomicDouble saveBonusMoney = new AtomicDouble(0); // 存档的提成金额

	private AtomicInteger subtotalCount = new AtomicInteger(0); // 小计

	UserDailyEntity(String userId) {
		this(userId, new Date(), Double.MAX_VALUE);
	}

	UserDailyEntity(String userId, Date date) {
		this(userId, date, Double.MAX_VALUE);
	}

	UserDailyEntity(String userId, Date date, double dailyUpperMoney) {
		this.userId = userId;
		this.setDate(date);
		this.dailyUpperMoney.set(dailyUpperMoney);
	}

	void setDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		this.date = DateUtils.truncate(date, Calendar.DAY_OF_MONTH);
	}

	private void checkDate() {
		// 判断时间
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		if (this.date == null || today.after(this.date)) {
			this.date = today;
			this.initCache = false;
			// 初始化数据
			this.dailyMoney.set(0);
			this.actualReadCount.set(0);
			this.saveReadCount.set(0);
			this.actualReadMoney.set(0);
			this.saveReadMoney.set(0);
			this.actualBonusCount.set(0);
			this.saveBonusCount.set(0);
			this.actualBonusMoney.set(0);
			this.saveBonusMoney.set(0);
			this.subtotalCount.set(0);
		}
	}

	// 检查该记录是否应该保存，否则丢弃
	private boolean checkNeedSave(int needCount, double needMoney) {
		double money = this.dailyMoney.get() + needMoney;
		if (money == 0) {
			return true;
		}
		double dailyUpperMoney = getDailyUpperMoney();
		if (money > dailyUpperMoney) {
			return false;
		}
		double rate = (dailyUpperMoney - money) / dailyUpperMoney;
		// ....
		if (this.subtotalCount.get() < (rate * 10)) {
			this.subtotalCount.addAndGet(needCount);
			return true;
		} else {
			this.subtotalCount.set(0);
			return false;
		}
	}

	public static void main(String[] args) {
		double dailyUpperMoney = 100;
		int i = 0;
		UserDailyEntity entity = new UserDailyEntity("1", null, dailyUpperMoney);
		while (true) {
			double needMoney = 0.05;
			int needCount = 1;
			boolean flag = entity.readProfits(needCount, needMoney);
			i++;
			if (flag) {
				System.out.println(i + " flag: " + flag + "  " + new Random().nextInt(10) + JsonHelper.encodeObject2Json(entity));
			}
			if (i > 10000) {
				break;
			}
		}
		System.out.println(dailyUpperMoney / 0.05);
	}

	// 阅读收益
	public synchronized boolean readProfits(int readCount, double readMoney) {
		// 判断时间
		checkDate();
		// 记录真实的阅读数据
		this.actualReadCount.addAndGet(readCount);
		this.actualReadMoney.addAndGet(readMoney);
		// 判断是否写入
		if (checkNeedSave(readCount, readMoney)) {
			this.saveReadCount.addAndGet(readCount);
			this.saveReadMoney.addAndGet(readMoney);
			this.dailyMoney.addAndGet(readMoney);
			return true;
		}
		return false;
	}

	// 提成收益
	public synchronized boolean bonusProfits(int bonusCount, double bonusMoney) {
		// 判断时间
		checkDate();
		// 记录真实的阅读数据
		this.actualBonusCount.addAndGet(bonusCount);
		this.actualBonusMoney.addAndGet(bonusMoney);
		// 判断是否写入
		if (checkNeedSave(bonusCount, bonusMoney)) {
			this.saveBonusCount.addAndGet(bonusCount);
			this.saveBonusMoney.addAndGet(bonusMoney);
			this.dailyMoney.addAndGet(bonusMoney);
			return true;
		}
		return false;
	}

	public Date getDate() {
		return this.date;
	}

	String getUserId() {
		return userId;
	}

	Integer getUserIdForInteger() {
		return NumberUtils.isDigits(userId) ? Integer.parseInt(userId) : null;
	}

	public double getDailyUpperMoney() {
		return dailyUpperMoney.get();
	}

	public double getDailyMoney() {
		return dailyMoney.get();
	}

	public int getActualReadCount() {
		return actualReadCount.get();
	}

	public int getSaveReadCount() {
		return saveReadCount.get();
	}

	public double getActualReadMoney() {
		return actualReadMoney.get();
	}

	public double getSaveReadMoney() {
		return saveReadMoney.get();
	}

	public int getActualBonusCount() {
		return actualBonusCount.get();
	}

	public int getSaveBonusCount() {
		return saveBonusCount.get();
	}

	public double getActualBonusMoney() {
		return actualBonusMoney.get();
	}

	public double getSaveBonusMoney() {
		return saveBonusMoney.get();
	}

	public double getReadMoneyRate() {
		return readMoneyRate;
	}
 

	public Integer getRecomUserId() {
		return recomUserId;
	}

	public double getBonusMoneyRate() {
		return bonusMoneyRate;
	}

	public void setBonusMoneyRate(double bonusMoneyRate) {
		this.bonusMoneyRate = bonusMoneyRate;
	}
	
	public double getBonusParentMoneyRate() {
		return bonusParentMoneyRate;
	}

	public void setBonusParentMoneyRate(double bonusParentMoneyRate) {
		this.bonusParentMoneyRate = bonusParentMoneyRate;
	}

	public boolean isInitCache() {
		return this.initCache;
	}
	
	public synchronized void initCache(double readMoneyRate, double bonusMoneyRate, double bonusParentMoneyRate, double dailyUpperMoney, Integer recomUserId) {
		if (!initCache) {
			this.readMoneyRate = readMoneyRate;
			this.bonusMoneyRate = bonusMoneyRate;
			this.bonusParentMoneyRate=bonusParentMoneyRate;
			this.dailyUpperMoney.set(dailyUpperMoney);
			if (recomUserId != null) {
				this.recomUserId = recomUserId;
			}
			initCache = true;
		}
	}

}
