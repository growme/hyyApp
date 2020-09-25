package com.ccnet.cps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;
import com.ccnet.core.service.SystemParamService;
import com.ccnet.cps.dao.DailyMoneyCountDao;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.dao.SbMoneyCountDao;
import com.ccnet.cps.dao.SbShareLogDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.dao.SbVisitCounterDao;
import com.ccnet.cps.entity.DailyMoneyCount;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbContentVisitLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitCounter;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;
import com.ccnet.cps.service.SbContentInfoService;

@Service("queueReceiver")
@Lazy
public class ReceiverMQServiceImpl implements MessageListener {

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
	@Autowired
	SystemParamService systemParamService;
	@Autowired
	SbContentInfoService sbContentInfoService;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			StringHelper.printDebug(Thread.currentThread().getId() + " 处理消息内容是：" + text);
			SbContentVisitLog visitLog = JsonHelper.parseJson2Model(text, SbContentVisitLog.class,
					"yyyy-MM-dd HH:mm:ss.SSS");
			if (visitLog == null || visitLog.getUserId() == null || visitLog.getContentId() == null
					|| StringUtils.isBlank(visitLog.getHashKey())) {
				StringHelper.printDebug("无效的请求内容： " + text);
				return;
			}
			readRecodeMoney(visitLog);
		} catch (JMSException e) {
			e.printStackTrace();
			StringHelper.printError(e.getMessage(), e);
		}
	}

	private void readRecodeMoney(SbContentVisitLog visitLog) {

		Integer userId = visitLog.getUserId();// 推广人
		Integer contentId = visitLog.getContentId();// 文章
		String requestIp = visitLog.getRequestIp();// 请求IP

		// 判断参数
		if (visitLog == null || CPSUtil.isEmpty(userId) || CPSUtil.isEmpty(contentId)
				|| StringUtils.isBlank(visitLog.getHashKey())) {
			return;
		}
		MemberInfo userDaily = memberInfoDao.getUserByUserID(userId);
		boolean flag = false;
		try {
			// 获取文章信息
			SbContentInfo _contenInfo = CPSUtil.getContentInfoById(contentId);
			if (CPSUtil.isEmpty(_contenInfo)) {
				_contenInfo = sbContentInfoService.getSbContentInfoByID(contentId);
			}
			if (CPSUtil.isEmpty(_contenInfo)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆文章ID[" + contentId + "] 对应文章不存在！◆◆◆◆◆◆◆◆◆◆");
				return;
			}
			double readAward = _contenInfo.getReadAward();
			if (_contenInfo.getHomeToped() != null && _contenInfo.getHomeToped() == 0) {
				String str = CPSUtil.getParamValue("REPEAT_MONEY");
				// String str =
				// systemParamService.findSystemParamByKey("REPEAT_MONEY").getParamValue();
				if (str != null) {
					readAward = Double.valueOf(str);
				}
			} else if (_contenInfo.getHomeToped() != null && _contenInfo.getHomeToped() > 0) {
				readAward = _contenInfo.getReadAward();
				System.out.println();
			}
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆文章阅读奖励:" + readAward);
			if (readAward <= 0) {
				readAward = getBaseReadMoney();
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆默认文章阅读奖励:" + readAward);
			}

			if (readAward <= 0) {
				return;
			}
			int ipNum = 1, openIdNum = 1, ipUserIdNum = 1;
			try {
				String aaa = CPSUtil.getParamValue("REPEAT_IP_BILLING_NUM");
				if (CPSUtil.isNotEmpty(aaa)) {
					ipNum = Integer.valueOf(aaa);
				}
				String bbb = CPSUtil.getParamValue("PEPEAT_OPENID_BILLING_NUM");
				if (CPSUtil.isNotEmpty(bbb)) {
					openIdNum = Integer.valueOf(bbb);
				}
				String ccc = CPSUtil.getParamValue("PEPEAT_IP_USERID_BILLING_NUM");
				if (CPSUtil.isNotEmpty(ccc)) {
					ipUserIdNum = Integer.valueOf(ccc);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			String gzhdl = CPSUtil.getParamValue("PEPEAT_LOGIN_CODE");
			CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆记账金额:" + readAward);
			// 判断是否存在
			SbContentVisitLog _visitLog = contentVisitLogDao.findContentVisitLogByHashKey(visitLog.getHashKey());
			if (CPSUtil.isNotEmpty(_visitLog) && _visitLog.getContentId() != null) {
				if (CPSUtil.isNotEmpty(visitLog.getAccountTime())) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆访问日志记录已存在◆◆◆◆◆◆◆◆◆◆◆◆");
					visitLog.setVisitId(_visitLog.getVisitId());
					SbShareLog shareLog = new SbShareLog();
					shareLog.setContentId(visitLog.getContentId());
					shareLog.setUserId(visitLog.getUserId());
					shareLog.setShareIp(requestIp.trim());
					// 判断同一篇文章 同一个ip同一个推广人id是否已经成功计费过
					if (!contentVisitLogDao.checkVisitLogExisitMoney(contentId, userId, requestIp.trim())) {
						if (contentVisitLogDao.countVisitContentLogIpOrOpenIdDate(requestIp, null) < ipNum) {
							if (shareLogDao.findList(shareLog).size() < ipUserIdNum) {
								visitLog.setVisitTime(new Date());
								if (CPSUtil.isNotEmpty(gzhdl) && gzhdl.equals("1")) {
									if (contentVisitLogDao.countVisitContentLogIpOrOpenIdDate(requestIp,
											visitLog.getOpenId()) < openIdNum) {
										visitLog.setAccountState(StateType.Valid.getState());// 设置为已经记账
									} else {
										visitLog.setCause("3");
										visitLog.setAccountState(StateType.InValid.getState());// 设置为已经记账
									}
								} else {
									visitLog.setAccountState(StateType.Valid.getState());// 设置为已经记账
								}
							} else {
								visitLog.setCause("4");
								visitLog.setAccountState(StateType.InValid.getState());
							}
						} else {
							visitLog.setCause("2");
							visitLog.setAccountState(StateType.InValid.getState());
						}
					} else {
						CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆文章ID[" + contentId + "] 推广人ID【" + userId + "】 访问IP【" + requestIp
								+ "】 已经计费过了！◆◆◆◆◆◆◆◆◆◆");
						visitLog.setCause("1");
						visitLog.setVisitTime(new Date());
						visitLog.setAccountState(StateType.InValid.getState());// 设置为未记账
					}
					visitLog.setVmoney(readAward);
					// 处理扣量逻辑
					if (visitLog.getAccountState().toString().equals(StateType.Valid.getState().toString())) {
						/*
						 * SbContentVisitLog cont =
						 * contentVisitLogDao.countVisitFaistContent(userId); if
						 * (CPSUtil.isNotEmpty(cont) &&
						 * CPSUtil.isNotEmpty(cont.getContentId()) &&
						 * cont.getContentId().toString().equals(visitLog.
						 * getContentId())) { visitLog.setCause("a");//
						 * 第一次分享奖励不记账
						 * visitLog.setAccountState(StateType.InValid.getState()
						 * );// 设置为已经记账 } else {
						 */
						String klbl = CPSUtil.getParamValue(Const.CT_USER_DISS_PERCENT);
						int klz = 0;
						if (CPSUtil.isNotEmpty(klbl)) {
							klz = Integer.valueOf(klbl);
						}
						if (CPSUtil.isNotEmpty(userDaily.getDisscount())) {
							klz = userDaily.getDisscount();
						}
						int max = 0, min = 99;
						int klzz = (int) (Math.random() * (max - min) + min);
						if (klz > klzz) {
							visitLog.setCause("5");
							visitLog.setAccountState(StateType.InValid.getState());
						} else {
							visitLog.setCause("9");
						}
						/* } */
					}
					flag = contentVisitLogDao.editSbContentVisitLog(visitLog);
				}
			} else {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆◆◆访问日志记录不存在◆◆◆◆◆◆◆◆◆◆");
				visitLog.setAccountState(StateType.InValid.getState());// 设置为未记账
				visitLog.setVmoney(readAward);
				visitLog.setCause("0");
				flag = contentVisitLogDao.saveSbContentVisitLog(visitLog);
				// 统计计数器
				SbVisitCounter vc = new SbVisitCounter();
				vc.setVisitIP(visitLog.getRequestIp());
				vc.setTotalCount(1);
				int ret = visitIPCounterDao.updateSbVisitCounter(vc);
				CPSUtil.xprint("更新ip统计数据：" + ret);
			}

			if (!flag) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆◆◆访问日志记录失败◆◆◆◆◆◆◆◆◆◆◆◆◆◆");
				return;
			}

			if (CPSUtil.isNotEmpty(visitLog.getAccountTime())
					&& visitLog.getAccountState().toString().equals(StateType.Valid.getState().toString())) {// 记账时间不为空则记账入库

				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆记账时间不为空记账入库◆◆◆◆◆◆◆◆◆◆◆◆");

				UserDailyEntity userDailyEntity = UserCache.getInstance().getUserCache(visitLog.getUserId());
				checkUserDailyEntity(userId, userDailyEntity);
				readAward = readAward * userDailyEntity.getReadMoneyRate(); // 根据用户的单价倍率计算用户收益
				flag = userDailyEntity.readProfits(1, readAward); // 阅读允许
				if (!flag) { // 内存判断无效阅读
					return;
				}
				// 有效阅读，记帐入库
				saveMoney(userId, readAward, AwardType.readawd.getAwardId(), contentId, visitLog.getHashKey(), 0);

				Integer _contentReadNum = 0;
				String contentReadNum = CPSUtil.getParamValue(Const.CT_USER_SHARE_READ_COUNT);
				if (CPSUtil.isEmpty(contentReadNum)) {
					contentReadNum = "3";
				}

				_contentReadNum = Integer.valueOf(contentReadNum);
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆有效阅读次数：" + contentReadNum);
				// 判断分享奖励 同一个人分享同一篇文章 阅读次数大于3次 开始计费 不同渠道分享只计算一次
				List<SbContentVisitLog> effectList = contentVisitLogDao.getContentEffectReadList(contentId, userId,
						_contentReadNum + 1, null, null);
				if (CPSUtil.listNotEmpty(effectList)
						&& (effectList.size() <= 5 || effectList.size() >= 5 + _contentReadNum)) {
					// 从分享日志表拿分享记录 按照shareMoney 排序获取最高分享价格
					double shareMoney = shareLogDao.findUserShareMoney(contentId, userId, "max");
					// 随机获取分享价格
					// double shareMoney =
					// shareLogDao.findUserShareMoney(contentId,
					// userId,"random");
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆分享计费价格：" + shareMoney);

					if (shareMoney > 0) {
						// 判断同一个账号一个礼拜最多只能获取分享收益值小于设定值继续计费(预留)
						// 给分享人计算分成
						saveMoney(userId, shareMoney, AwardType.transmit.getAwardId(), contentId, visitLog.getHashKey(),
								0);
					}
				}
				if (CPSUtil.isNotEmpty(userDaily) && CPSUtil.isNotEmpty(userDaily.getRecomCode())) {
					MemberInfo fuser = memberInfoDao.getUserByVCode(userDaily.getRecomCode());
					visitLog.setUserId(userId);
					// 处理上级提成
					recodeBonusMoney(fuser.getMemberId(), readAward, 1, 2, visitLog);
					if (CPSUtil.isNotEmpty(fuser) && CPSUtil.isNotEmpty(fuser.getRecomCode())) {
						MemberInfo zuser = memberInfoDao.getUserByVCode(fuser.getRecomCode());
						recodeBonusMoney(zuser.getMemberId(), readAward, 2, 2, visitLog);
					}
				}
			}
		} catch (Exception e) {
			CPSUtil.xprint("记账金额入库失败......!");
			e.printStackTrace();
		}

	}

	/**
	 * 会员近一周的收益统计总额
	 * 
	 * @param contentId
	 * @param userId
	 * @param requestIp
	 * @return
	 */
	private double getCurUserWeekMoney(Integer userId) {
		Double totalMoney = 0.0;
		Dto paramDto = new BaseDto();
		SbMoneyCount moneyCount = new SbMoneyCount();
		moneyCount.setUserId(userId);
		moneyCount.setmType(AwardType.transmit.getAwardId());
		paramDto.put("start_date", CPSUtil.getDateByUDay(-7));// 近一周
		paramDto.put("end_date", CPSUtil.getCurDate());
		List<SbMoneyCount> mlList = sbMoneyCountDao.findSbMoneyCountList(moneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mlList)) {
			for (SbMoneyCount mc : mlList) {
				totalMoney = totalMoney + mc.getUmoney();
			}
		}

		CPSUtil.xprint("totalMoney=" + totalMoney);
		return totalMoney;
	}

	private void recodeBonusMoney(Integer recomUserId, double baseReadMoney, int index, int maxCount,
			SbContentVisitLog visitLog) {
		if (recomUserId == null || index > maxCount) {
			return;
		}

		CPSUtil.xprint("处理上" + index + " 级提成 ");
		UserDailyEntity userDailyEntity = UserCache.getInstance().getUserCache(recomUserId);
		checkUserDailyEntity(recomUserId, userDailyEntity);
		double bonusMoneyRate = userDailyEntity.getBonusMoneyRate();
		if (index > 1) {
			bonusMoneyRate = userDailyEntity.getBonusParentMoneyRate();
		}

		double bonusMoney = baseReadMoney * bonusMoneyRate;
		if (bonusMoney > 0) {
			if (userDailyEntity.bonusProfits(1, bonusMoney)) {
				// 有效提成，入帐
				saveMoney(recomUserId, bonusMoney, AwardType.deduct.getAwardId(), visitLog.getContentId(),
						visitLog.getUserId() + "", index);
			}
		}
		// 上级
		// recodeBonusMoney(userDailyEntity.getRecomUserId(), baseReadMoney,
		// ++index, maxCount, visitLog);
	}

	/**
	 * 处理资金相关参数
	 * 
	 * @param userId
	 * @param userDailyEntity
	 */
	private void checkUserDailyEntity(Integer userId, UserDailyEntity userDailyEntity) {
		if (userDailyEntity != null && !userDailyEntity.isInitCache()) {
			// 获取会员信息
			MemberInfo memberInfo = CPSUtil.getMemeberById(userId + "");
			if (CPSUtil.isNotEmpty(memberInfo)) {
				// 获取推荐人用户ID
				Integer recomUserId = null;
				String recomCode = memberInfo.getRecomCode();// 推荐人visitCode
				MemberInfo recomUser = CPSUtil.getMemeberByVisitCode(recomCode + "");// 推荐人信息
				if (CPSUtil.isNotEmpty(recomUser)) {
					recomUserId = recomUser.getMemberId();// 推荐人ID
					CPSUtil.xprint("【用户账号】" + memberInfo.getLoginAccount() + " 推荐人账号=" + recomUser.getLoginAccount());
				}

				// 从配置中取 用户收益倍率，默认是1(后台会根据会员等级进行调整)
				double readMoneyRate = 1;
				MemeberLevelType levelType = null;
				Integer memberLevel = memberInfo.getMemberLevel();
				if (CPSUtil.isNotEmpty(memberLevel)) {
					levelType = MemeberLevelType.getMemeberLevelType(memberLevel);
					if (CPSUtil.isNotEmpty(levelType)) {
						readMoneyRate = levelType.getPercent();// 获取等级
					}
				}

				// 处理日阅读收益上线
				double dailyUpperMoney = 0d;
				String maxDailyMoney = CPSUtil.getParamValue(Const.CT_MAX_DAILY_READ_MONEY);
				if (CPSUtil.isNotEmpty(maxDailyMoney)) {
					dailyUpperMoney = Double.valueOf(maxDailyMoney);
				} else {
					dailyUpperMoney = Double.MAX_VALUE;
				}

				// 处理上级和上上级提成比例
				double bonusMoneyRate = 0d;
				String parentMoneyPercent = CPSUtil.getParamValue(Const.CT_PARENT_MONEY_PERCENT);
				if (CPSUtil.isNotEmpty(parentMoneyPercent)) {
					bonusMoneyRate = Double.valueOf(parentMoneyPercent);
				} else {
					bonusMoneyRate = 0.2d;
				}

				double bonusParentMoneyRate = 0d;
				String parentMoneyPercent2 = CPSUtil.getParamValue(Const.CT_PARENT_PARENT_MONEY_PERCENT);
				if (CPSUtil.isNotEmpty(parentMoneyPercent2)) {
					bonusParentMoneyRate = Double.valueOf(parentMoneyPercent2);
				} else {
					bonusParentMoneyRate = 0.05d;
				}

				CPSUtil.xprint("【用户收益倍率】" + readMoneyRate);
				CPSUtil.xprint("【用阅读收益上限】" + dailyUpperMoney);
				CPSUtil.xprint("【上级提成比例】" + bonusMoneyRate);
				CPSUtil.xprint("【上上级提成比例】" + bonusParentMoneyRate);

				// 需要初始化参数,读取数据库信息初始化到缓存中
				userDailyEntity.initCache(readMoneyRate, bonusMoneyRate, bonusParentMoneyRate, dailyUpperMoney,
						recomUserId);
			}
		}
	}

	/**
	 * 默认阅读奖励（系统配置）
	 * 
	 * @return
	 */
	private double getBaseReadMoney() {
		// 获取默认阅读文章收益
		double _readmoney = 0d;
		String readMoney = CPSUtil.getParamValue(Const.CT_ARTICLE_READ_MONEY);
		if (CPSUtil.isNotEmpty(readMoney)) {
			_readmoney = Double.valueOf(readMoney);
		} else {
			_readmoney = 0.05;// 如果没有设置就是0.05
		}
		return _readmoney;
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
	private boolean saveMoney(Integer userId, double money, int mtype, Integer contentId, String contentHashKey,
			int vindex) {
		// 保存流水
		SbMoneyCount moneyCount = new SbMoneyCount();
		moneyCount.setUserId(userId);
		moneyCount.setUmoney(money);
		moneyCount.setContentId(contentId);
		moneyCount.setmType(mtype);
		moneyCount.setCreateTime(new Date());
		moneyCount.setVcode(contentHashKey);
		moneyCount.setVindex(vindex);
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
			double am = new BigDecimal("" + (userLock.getActualReadMoney() + userLock.getActualBonusMoney()))
					.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			dailyMoneyCount.setActualMoney(am);
			// BigDecimal bd=new BigDecimal(""+userLock.getDailyUpperMoney());
			//
			// dailyMoneyCount.setDailyMaxMoney(bd.setScale(2,
			// BigDecimal.ROUND_HALF_UP).doubleValue());
			String pykg = CPSUtil.getParamValue(Const.CT_MAX_DAILY_READ_MONEY);
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
			if (mtype == AwardType.readawd.getAwardId()) {
				userMoney.setPmoney(money);
			} else {
				userMoney.setPmoney(0D);
			}
			i = sbUserMoneyDao.insertOrUpdate(userMoney);
			return true;
		}
	}

}
