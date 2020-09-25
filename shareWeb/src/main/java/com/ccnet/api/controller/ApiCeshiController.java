package com.ccnet.api.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.controller.BaseController;
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
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitCounter;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;

/**
 * 文章相关api
 */
@Controller
@RequestMapping("/api/ceshi/")
public class ApiCeshiController extends BaseController<T> {

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

	@RequestMapping(value = "index", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getColumnInfo() {
		try {
			SbContentVisitLog visitLog = new SbContentVisitLog();
			readRecodeMoney(visitLog);

			Integer recomUserId = 7;
			double baseReadMoney = 0.2D;
			int index = 1;
			int maxCount = 2;
			recodeBonusMoney(recomUserId, baseReadMoney, index, maxCount, visitLog);
			return ResultDTO.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
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

		boolean flag = false;
		try {
			// 获取文章信息
			SbContentInfo _contenInfo = CPSUtil.getContentInfoById(contentId);
			if (CPSUtil.isEmpty(_contenInfo)) {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆文章ID[" + contentId + "] 对应文章不存在！◆◆◆◆◆◆◆◆◆◆");
				return;
			}
			// 判断是否存在
			SbContentVisitLog _visitLog = contentVisitLogDao.findContentVisitLogByHashKey(visitLog.getHashKey());
			if (CPSUtil.isNotEmpty(_visitLog) && _visitLog.getContentId() != null) {
				if (CPSUtil.isNotEmpty(visitLog.getAccountTime())) {
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆访问日志记录已存在◆◆◆◆◆◆◆◆◆◆◆◆");
					visitLog.setVisitId(_visitLog.getVisitId());
					// 判断同一篇文章 同一个ip同一个推广人id是否已经成功计费过
					if (!contentVisitLogDao.checkVisitLogExisitMoney(contentId, userId, requestIp.trim())) {
						visitLog.setVisitTime(_visitLog.getVisitTime());
						visitLog.setAccountState(StateType.Valid.getState());// 设置为已经记账
					} else {
						CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆文章ID[" + contentId + "] 推广人ID【" + userId + "】 访问IP【" + requestIp
								+ "】 已经计费过了！◆◆◆◆◆◆◆◆◆◆");
						visitLog.setVisitTime(null);
						visitLog.setAccountState(StateType.InValid.getState());// 设置为未记账
					}
					flag = contentVisitLogDao.editSbContentVisitLog(visitLog);
				}
			} else {
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆◆◆访问日志记录不存在◆◆◆◆◆◆◆◆◆◆");
				visitLog.setAccountState(StateType.InValid.getState());// 设置为未记账
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

			if (CPSUtil.isNotEmpty(visitLog.getAccountTime())) {// 记账时间不为空则记账入库

				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆记账时间不为空记账入库◆◆◆◆◆◆◆◆◆◆◆◆");
				SbContentInfo contentInfo = CPSUtil.getContentInfoById(contentId);
				double readAward = contentInfo.getReadAward();
				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆文章阅读奖励:" + readAward);
				if (readAward <= 0) {
					readAward = getBaseReadMoney();
					CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆默认文章阅读奖励:" + readAward);
				}

				if (readAward <= 0) {
					return;
				}

				CPSUtil.xprint("◆◆◆◆◆◆◆◆◆◆记账金额:" + readAward);

				UserDailyEntity userDailyEntity = UserCache.getInstance().getUserCache(visitLog.getUserId());
				checkUserDailyEntity(userId, userDailyEntity);
				readAward = readAward * userDailyEntity.getReadMoneyRate(); // 根据用户的单价倍率计算用户收益
				flag = userDailyEntity.readProfits(1, readAward); // 阅读允许
				if (!flag) { // 内存判断无效阅读
					return;
				}
				// 有效阅读，记帐入库
				saveMoney(userId, readAward, AwardType.readawd.getAwardId(), contentId, visitLog.getHashKey());

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
				if (CPSUtil.listNotEmpty(effectList) && (effectList.size() == _contentReadNum)) {
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
						saveMoney(userId, shareMoney, AwardType.transmit.getAwardId(), contentId,
								visitLog.getHashKey());
					}

				}

				// 处理上级提成
				recodeBonusMoney(userDailyEntity.getRecomUserId(), readAward, 1, 2, visitLog);
			}

		} catch (Exception e) {
			CPSUtil.xprint("记账金额入库失败......!");
			e.printStackTrace();
		}

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
						visitLog.getHashKey());
			}
		}
		// 上级
		recodeBonusMoney(userDailyEntity.getRecomUserId(), baseReadMoney, ++index, maxCount, visitLog);
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
	private boolean saveMoney(Integer userId, double money, int mtype, Integer contentId, String contentHashKey) {
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
			i = sbUserMoneyDao.insertOrUpdate(userMoney);

			return true;
		}
	}
}
