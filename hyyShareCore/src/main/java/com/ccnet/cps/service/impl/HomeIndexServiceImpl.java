package com.ccnet.cps.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.PayState;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.SbCashLogDao;
import com.ccnet.cps.dao.SbContentVisitLogDao;
import com.ccnet.cps.dao.SbMoneyCountDao;
import com.ccnet.cps.dao.SbShareLogDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.SbCashLogExtend;
import com.ccnet.cps.entity.SbContentVisitLogExtend;
import com.ccnet.cps.entity.SbMoneyCountExtend;
import com.ccnet.cps.entity.SbShareLogExtend;
import com.ccnet.cps.entity.SbUserMoneyExtend;
import com.ccnet.cps.service.HomeIndexService;


@Service("homeIndexService")
public class HomeIndexServiceImpl extends BaseServiceImpl<Object> implements HomeIndexService {
	
	@Autowired
	private SbShareLogDao shareInfoDao;
	@Autowired
	private SbUserMoneyDao userMoneyDao;
	@Autowired
	private SbMoneyCountDao moneyCountDao;
	@Autowired
	private SbContentVisitLogDao contentVisitLogDao;
	@Autowired
	private SbCashLogDao cashLogDao;
	
	
	/**
	 * 统计日志表
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
	public List<SbContentVisitLogExtend> getContentVisitCount(Date startDate, Date endDate, Integer userId){
		return contentVisitLogDao.getContentVisitCount(startDate, endDate, userId);
	}
	
	
	/**
	 * 按照省份统计
	 * @param startDate
	 * @param endDate
	 * @param visitExtends
	 * @return
	 */
	public Map<String, Integer> countProvinceContentVisitLogExtend(Date startDate, Date endDate, List<SbContentVisitLogExtend> visitExtends) {
		Map<String, Integer> provinceMap = new HashMap<String, Integer>(0);
		if (visitExtends == null) {
			visitExtends = countContentVisitLogExtendsGroupByProvince(startDate, endDate);
		}
		for (SbContentVisitLogExtend visitExtend : visitExtends) {
			String province = visitExtend.getProvince();
			int count = visitExtend.getCount() == null ? 0 : visitExtend.getCount();  //订单量
			if (provinceMap.containsKey(province)) {
				count += provinceMap.get(province);
			} 
			provinceMap.put(province, count);
		}
		return provinceMap;
	}
	
	/**
	 * 按资金状态统计
	 * @param startDate
	 * @param endDate
	 * @param moneyCountExtends
	 * @return
	 */
	public Map<AwardType, Double> countUserByMoneyAwardType(Date startDate, Date endDate, List<SbMoneyCountExtend> moneyCountExtends) {
		Map<AwardType, Double> moneyCountMap = new HashMap<AwardType, Double>();
		if (moneyCountExtends == null) {
			moneyCountExtends = getUserMoneyCountByMoneyType(startDate, endDate);
		}
		for (SbMoneyCountExtend moneyExtend : moneyCountExtends) {
			AwardType moneyState = AwardType.getAwardType(moneyExtend.getmType()); //当前状态
			int count = moneyExtend.getCount() == null ? 0 : moneyExtend.getCount();  //订单量
			double countMoney = moneyExtend.getUmoney()==null?0:moneyExtend.getUmoney();//资金
			if (moneyCountMap.containsKey(moneyState)) {
				countMoney += moneyCountMap.get(moneyState);
			} 
			moneyCountMap.put(moneyState, countMoney);
		}
		return moneyCountMap;
	}
	
	
	/**
	 * 统计会员提现情况
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto userCashCountByState(Date startDate, Date endDate) {
		Dto dto = new BaseDto();
		List<SbCashLogExtend> extendList = cashLogDao.getUserCashCountByState(startDate, endDate);
		// 总数
		int totalCount = 0;
		double traddingMoney = 0;
		double tradedMoney = 0;
		PayState payState = null;
		if(CPSUtil.listNotEmpty(extendList)){
			for (SbCashLogExtend extend : extendList) {
				payState = PayState.getPayState(extend.getState());
				long count = extend.getCount() == null ? 0 : extend.getCount();  //数量
				double cmoney = extend.getCmoney() == null ? 0 : extend.getCmoney(); //金额
				if(PayState.isCompletedCash(payState)){
					tradedMoney += cmoney;
				}else{
					traddingMoney += cmoney;
				}
				totalCount +=count;
			}
		}
		
		dto.put("totalCount", totalCount);
		dto.put("tradedMoney", tradedMoney);
		dto.put("traddingMoney", traddingMoney);
		return dto;
	}
	
	/**
	 * 统计会员提现情况
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbCashLogExtend> getUserCashCountByState(Date startDate, Date endDate){
		return cashLogDao.getUserCashCountByState(startDate, endDate);
	}
	
	
	/**
	 * 统计会员推广收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto userMoneyCountByMoneyType(Date startDate, Date endDate) {
		Dto dto = new BaseDto();
		List<SbMoneyCountExtend> extendList = moneyCountDao.getUserMoneyCountByMoneyType(startDate, endDate);
		// 总数
		int totalCount = 0;
		// 推广收益=阅读+分享+提成
		double totalMoney = 0;
		AwardType awardType = null;
		if(CPSUtil.listNotEmpty(extendList)){
			for (SbMoneyCountExtend extend : extendList) {
				awardType = AwardType.getAwardType(extend.getmType());
				long count = extend.getCount() == null ? 0 : extend.getCount();  //数量
				double umoney = extend.getUmoney() == null ? 0 : extend.getUmoney(); //金额
				if(AwardType.isValidatePromte(awardType)){
					totalMoney+=umoney;
				}
				totalCount +=count;
			}
		}
		
		dto.put("totalCount", totalCount);
		dto.put("totalMoney", totalMoney);
		return dto;
		
	}
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbMoneyCountExtend> getUserMoneyCountByMoneyType(Date startDate, Date endDate){
		return moneyCountDao.getUserMoneyCountByMoneyType(startDate, endDate);
	}
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto countMemeberTotalMoney(Date startDate, Date endDate){
		Dto dto = new BaseDto();
		List<SbUserMoneyExtend> extendList = userMoneyDao.getCountMemeberTotalMoney(startDate, endDate);
        //有效会员数
		int totalCount = 0;
		//计费总数
		int totalMoneyCount = 0;
		// 总金额
		double totalMoney = 0;
		if(CPSUtil.listNotEmpty(extendList)){
			for (SbUserMoneyExtend extend : extendList) {
				long count = extend.getCount() == null ? 0 : extend.getCount();  //总数
				double shareMoney = extend.getProfitsMoney() == null ? 0 : extend.getProfitsMoney(); //总收益
				totalCount += count;
				totalMoney += shareMoney;
			}
			
			dto.put("totalCount", totalCount);
			dto.put("totalMoney", totalMoney);
		}
		
		return dto;
		
	}
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbUserMoneyExtend> getCountMemeberTotalMoney(Date startDate, Date endDate){
		return userMoneyDao.getCountMemeberTotalMoney(startDate, endDate);
	}
	

	/**
	 * 按照是否计费统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto countTotalShareByGivenMoney(Date startDate,Date endDate) {
		Dto dto = new BaseDto();
		List<SbShareLogExtend> extendList = shareInfoDao.countMemeberShareByGivenMoney(startDate, endDate);
		// 总分享数
		int totalCount = 0;
		//计费总数
		int totalMoneyCount = 0;
		// 总金额
		double totalMoney = 0;
		if(CPSUtil.listNotEmpty(extendList)){
			for (SbShareLogExtend extend : extendList) {
				long count = extend.getCount() == null ? 0 : extend.getCount();  //分享数
				int givenMoney = extend.getGivenMoney();  //类型
				double shareMoney = extend.getShareMoney() == null ? 0 : extend.getShareMoney(); //订单金额
				if(givenMoney==StateType.Valid.getState()){
					totalMoneyCount+=givenMoney;
				}
				totalCount += count;
				totalMoney += shareMoney;
			}
			
			dto.put("totalCount", totalCount);
			dto.put("totalMoney", totalMoney);
			dto.put("totalMoneyCount", totalMoneyCount);
		}
		
		return dto;
	}
	
	public List<SbShareLogExtend> countMemeberShareByGivenMoney(Date startDate,Date endDate) {
		return shareInfoDao.countMemeberShareByGivenMoney(startDate, endDate);
	}


	public List<SbShareLogExtend> countMemeberShareByShareType(Date startDate,Date endDate) {
		return shareInfoDao.countMemeberShareByShareType(startDate, endDate);
	}
	
	public List<SbContentVisitLogExtend> countContentVisitLogExtendsGroupByProvince(Date startDate, Date endDate) {
		return contentVisitLogDao.countVisitContentLogExtend(startDate, endDate);
	}

	protected BaseDao<Object> getDao() {
		return null;
	}

}
