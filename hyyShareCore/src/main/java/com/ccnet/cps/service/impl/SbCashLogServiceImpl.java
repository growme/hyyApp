package com.ccnet.cps.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.PayState;
import com.ccnet.core.common.PayType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbCashLogDao;
import com.ccnet.cps.dao.SbMoneyCountDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.service.SbCashLogService;

@Service("sbCashLogService")
public class SbCashLogServiceImpl extends BaseServiceImpl<SbCashLog> implements SbCashLogService {

	@Autowired
	private SbCashLogDao sbCashLogDao;
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private SbUserMoneyDao sbUserMoneyDao;
	@Autowired
	private SbMoneyCountDao sbMoneyCountDao;

	@Override
	public int insert(SbCashLog cashLog) {
		int i = sbCashLogDao.insert(cashLog);
		if (i > 0) {
			// 保存总数据
			SbUserMoney userMoney = new SbUserMoney();
			userMoney.setUserId(cashLog.getUserId());
			userMoney.setProfitsMoney(0d);
			userMoney.setTmoney(-cashLog.getCmoney());
			userMoney.setUpdateTime(new Date());
			userMoney.setLastProDate(userMoney.getUpdateTime());
			if(cashLog.getWithdrawType()==1){
				userMoney.setPmoney(-cashLog.getCmoney());
			}
			i = sbUserMoneyDao.insertOrUpdate(userMoney);
		}
		return i;
	}

	/**
	 * 分页查询(多过滤)
	 * 
	 * @param cashLog
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page<SbCashLog> findSbCashLogByPage(SbCashLog cashLog, Page<SbCashLog> page, Dto pdDto) {
		Page<SbCashLog> cashPage = sbCashLogDao.findSbCashLogByPage(cashLog, page, pdDto);
		if (cashPage != null) {
			List<SbCashLog> logsList = cashPage.getResults();
			if (CPSUtil.listNotEmpty(logsList)) {
				// 获取会员信息
				MemberInfo memeber = null;
				List<Integer> memberIds = sbCashLogDao.getValuesFromField(logsList, "userId");
				Map<Integer, MemberInfo> memberMap = memberInfoDao.getUserMapByIds(memberIds);
				for (SbCashLog sbCashLog : logsList) {
					// 支付类型
					if (CPSUtil.isNotEmpty(sbCashLog.getPayType())) {
						sbCashLog.setTypeName(PayType.getPayType(sbCashLog.getPayType()).getPayName());
					}

					// 支付状态
					if (CPSUtil.isNotEmpty(sbCashLog.getState())) {
						sbCashLog.setStateName(PayState.getPayState(sbCashLog.getState()).getPayStateName());
					}

					if (CPSUtil.isNotEmpty(sbCashLog.getState())) {
						sbCashLog.setShowColor(PayState.getPayState(sbCashLog.getState()).getShowColor());
					}

					if (CPSUtil.isNotEmpty(sbCashLog.getUserId())) {
						memeber = memberMap.get(sbCashLog.getUserId());
						sbCashLog.setMemberInfo(memeber);
						if (memeber != null && CPSUtil.isNotEmpty(memeber.getRecomCode())) {
							sbCashLog.setParentInfo(CPSUtil.getMemeberByVisitCode(memeber.getRecomCode()));
						}
					}
				}
			}
		}
		return cashPage;
	}

	/**
	 * 查询提现记录集合
	 * 
	 * @param cashLog
	 * @param paramDto
	 * @return
	 */
	public List<SbCashLog> findSbCashLogList(SbCashLog cashLog, Dto paramDto) {
		List<SbCashLog> logsList = sbCashLogDao.findSbCashLogList(cashLog, paramDto);
		if (CPSUtil.listNotEmpty(logsList)) {
			if (CPSUtil.listNotEmpty(logsList)) {
				// 获取会员信息
				List<Integer> memberIds = sbCashLogDao.getValuesFromField(logsList, "userId");
				Map<Integer, MemberInfo> memberMap = memberInfoDao.getUserMapByIds(memberIds);
				for (SbCashLog sbCashLog : logsList) {
					// 支付类型
					if (CPSUtil.isNotEmpty(sbCashLog.getPayType())) {
						sbCashLog.setTypeName(PayType.getPayType(sbCashLog.getPayType()).getPayName());
					}
					// 支付状态
					if (CPSUtil.isNotEmpty(sbCashLog.getState())) {
						sbCashLog.setStateName(PayState.getPayState(sbCashLog.getState()).getPayStateName());
					}
					if (CPSUtil.isNotEmpty(sbCashLog.getUserId())) {
						sbCashLog.setMemberInfo(memberMap.get(sbCashLog.getUserId()));
					}
				}
			}
		}
		return logsList;
	}

	/**
	 * 查询指定提现
	 * 
	 * @param cashId
	 * @param state
	 * @param userId
	 * @return
	 */
	public SbCashLog findSbCashLogById(Integer cashId, Dto paramDto) {
		SbCashLog sbCashLog = new SbCashLog();
		if (CPSUtil.isNotEmpty(cashId)) {
			sbCashLog.setUcId(cashId);
		}
		List<SbCashLog> cashLogs = findSbCashLogList(sbCashLog, paramDto);
		if (CPSUtil.listNotEmpty(cashLogs)) {
			sbCashLog = cashLogs.get(0);
		}
		return sbCashLog;
	}

	/**
	 * 获取用户累计提现总额
	 * 
	 * @param cashLog
	 * @param paramDto
	 * @return
	 */
	public Double getCurrentUserCashCount(SbCashLog cashLog, Dto paramDto) {
		double getCash = 0;
		List<SbCashLog> clist = sbCashLogDao.findSbCashLogList(cashLog, paramDto);
		if (CPSUtil.listNotEmpty(clist)) {
			for (SbCashLog cash : clist) {
				getCash += cash.getCmoney();
			}
		}
		return getCash;
	}

	/**
	 * 统计总数
	 */
	@Override
	public int count(Date startDate, Date endDate) {
		return sbCashLogDao.count(startDate, endDate);
	}

	/**
	 * 更新审核信息
	 * 
	 * @param cashId
	 * @param state
	 * @param remark
	 * @return
	 */
	public boolean updateUserCashState(Integer cashId, Integer state, String remark) {
		return sbCashLogDao.updateUserCashState(cashId, state, remark) > 0 ? true : false;
	}

	@Override
	protected BaseDao<SbCashLog> getDao() {
		return sbCashLogDao;
	}

	public boolean updateUserCashMoney(Integer userID, Double cMoney) {
		int i = 0;
		MemberInfo memberInfo = memberInfoDao.getUserByUserID(userID);
		if (CPSUtil.isNotEmpty(memberInfo) && CPSUtil.isNotEmpty(memberInfo.getRecomCode())) {
			MemberInfo fuser = memberInfoDao.getUserByVCode(memberInfo.getRecomCode());
			List<SbCashLog> list = sbCashLogDao.getCashListTopThree(userID);
			SbMoneyCount sbMonetCount = new SbMoneyCount();
			sbMonetCount.setCreateTime(new Date());
			sbMonetCount.setUserId(fuser.getMemberId());
			sbMonetCount.setVcode(memberInfo.getVisitCode());
			sbMonetCount.setVindex(1);
			if (list.size() == 3) {// 第三次提现
				System.out.print(false);
				String money = CPSUtil.getParamValue(Const.CT_PARENT_WITHDRAW_THIRD);
				sbMonetCount.setUmoney(Double.valueOf(money));
				sbMonetCount.setmType(AwardType.SECOND.getAwardId());
			} else if (list.size() == 2) {// 第二次提现
				String money = CPSUtil.getParamValue(Const.CT_PARENT_WITHDRAW_SECOND);
				sbMonetCount.setUmoney(Double.valueOf(money));
				sbMonetCount.setmType(AwardType.SECOND.getAwardId());
			} else if (list.size() == 1) {// 第一次提现
				String money = CPSUtil.getParamValue(Const.CT_PARENT_WITHDRAW_FIRST);
				sbMonetCount.setUmoney(Double.valueOf(money));
				sbMonetCount.setmType(AwardType.SECOND.getAwardId());
			}
			if (CPSUtil.isNotEmpty(sbMonetCount.getUmoney())) {
				i = sbMoneyCountDao.insert(sbMonetCount);
				if (i > 0) {
					SbUserMoney userMoney = new SbUserMoney();
					userMoney.setUserId(sbMonetCount.getUserId());
					userMoney.setProfitsMoney(0d);
					userMoney.setTmoney(sbMonetCount.getUmoney());
					userMoney.setUpdateTime(new Date());
					userMoney.setLastProDate(userMoney.getUpdateTime());
					i = sbUserMoneyDao.insertOrUpdate(userMoney);
				}
			}
			SbCashLog sbCashLog = sbCashLogDao.getCashGroupUser(userID);
			BigDecimal d100 = new BigDecimal("100");
			if (CPSUtil.isNotEmpty(sbCashLog)) {
				BigDecimal dz = new BigDecimal(sbCashLog.getCmoney().toString());
				BigDecimal dc = new BigDecimal(cMoney.toString());
				if (dz.subtract(dc).compareTo(d100) < 0 && dz.compareTo(d100) >= 0) {
					String money = CPSUtil.getParamValue(Const.CT_PARENT_WITHDRAW_FULL);
					sbMonetCount = new SbMoneyCount();
					sbMonetCount.setCreateTime(new Date());
					sbMonetCount.setUserId(fuser.getMemberId());
					sbMonetCount.setVcode(memberInfo.getVisitCode());
					sbMonetCount.setVindex(1);
					sbMonetCount.setUmoney(Double.valueOf(money));
					sbMonetCount.setmType(AwardType.FULL.getAwardId());
					i = sbMoneyCountDao.insert(sbMonetCount);
					if (i > 0) {
						SbUserMoney userMoney = new SbUserMoney();
						userMoney.setUserId(sbMonetCount.getUserId());
						userMoney.setProfitsMoney(0d);
						userMoney.setTmoney(sbMonetCount.getUmoney());
						userMoney.setUpdateTime(new Date());
						userMoney.setLastProDate(userMoney.getUpdateTime());
						i = sbUserMoneyDao.insertOrUpdate(userMoney);
					}
				}
			}
		}
		return i == 1;
	}
}
