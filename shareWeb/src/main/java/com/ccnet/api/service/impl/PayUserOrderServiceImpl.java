package com.ccnet.api.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.api.dao.PayUserOrderDao;
import com.ccnet.api.entity.PayUserOrder;
import com.ccnet.api.service.PayUserOrderService;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.DailyMoneyCountDao;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.dao.SbMoneyCountDao;
import com.ccnet.cps.dao.SbShareLogDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.dao.SbVisitCounterDao;
import com.ccnet.cps.entity.DailyMoneyCount;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;

@Service("payUserOrderService")
public class PayUserOrderServiceImpl extends BaseServiceImpl<PayUserOrder> implements PayUserOrderService {

	@Autowired
	private PayUserOrderDao payUserOrderDao;
	@Autowired
	SbMoneyCountDao sbMoneyCountDao;
	@Autowired
	SbUserMoneyDao sbUserMoneyDao;
	@Autowired
	DailyMoneyCountDao dailyMoneyCountDao;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	SbContentVisitLogDao contentVisitLogDao;
	@Autowired
	SbVisitCounterDao visitIPCounterDao;
	@Autowired
	SbShareLogDao shareLogDao;

	@Override
	public PayUserOrder getByOrderNo(String orderId) {
		PayUserOrder order = new PayUserOrder();
		return payUserOrderDao.find(order);
	}

	@Override
	protected BaseDao<PayUserOrder> getDao() {
		return this.payUserOrderDao;
	}

	@Override
	public Boolean saveMoney() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 保存收益
	 * 
	 * @param userId
	 * @param money
	 * @param mtype
	 *            register(0,"注册奖励"), transmit(1,"转发奖励"), readawd(5,"阅读奖励"),
	 *            deduct(2,"下线提成"), visitad(4,"邀请奖励"), redpacke(3,"签到奖励");
	 * @param contentId
	 * @param contentHashKey
	 * @return
	 */
	@Override
	public Boolean saveMoney(Integer userId, double money, int mtype, Integer contentId, String contentHashKey) {
		// 保存流水
		SbMoneyCount moneyCount = new SbMoneyCount();
		moneyCount.setUserId(userId);
		moneyCount.setUmoney(money);
		moneyCount.setContentId(contentId);
		moneyCount.setmType(mtype);
		moneyCount.setCreateTime(new Date());
		int i = sbMoneyCountDao.insert(moneyCount, "umId");
		if (i == 0) {
			return false;
		}

		UserDailyEntity userLock = UserCache.getInstance().getUserCache(userId);
		synchronized (userLock) {
			// 保存日累
			DailyMoneyCount dailyMoneyCount = new DailyMoneyCount();
			dailyMoneyCount.setUserId(userId);
			dailyMoneyCount.setMoneyDate(userLock.getDate());
			dailyMoneyCount.setDailyMoney(money);
			String pykg = CPSUtil.getParamValue(Const.CT_MAX_DAILY_READ_MONEY);
			double am = new BigDecimal("" + (userLock.getActualReadMoney() + userLock.getActualBonusMoney()))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			dailyMoneyCount.setActualMoney(am);
			if (org.apache.commons.lang3.StringUtils.isNoneBlank(pykg)) {
				BigDecimal ba = new BigDecimal(pykg);
				dailyMoneyCount.setDailyMaxMoney(ba.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			} else {
				BigDecimal bd = new BigDecimal("" + userLock.getDailyUpperMoney());
				dailyMoneyCount.setDailyMaxMoney(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			}

			dailyMoneyCount.setSettlement(0);
			i = dailyMoneyCountDao.insertOrUpdate(dailyMoneyCount);

			// 保存总数据
			SbUserMoney userMoney = new SbUserMoney();
			userMoney.setUserId(userId);
			userMoney.setProfitsMoney(money);
			userMoney.setTmoney(money);
			userMoney.setUpdateTime(new Date());
			userMoney.setLastProDate(userMoney.getUpdateTime());
			i = sbUserMoneyDao.insertOrUpdate(userMoney);

			return true;
		}
	}

}
