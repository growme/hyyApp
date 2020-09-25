package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.SbCashLogExtend;
import com.ccnet.cps.entity.SbContentVisitLogExtend;
import com.ccnet.cps.entity.SbMoneyCountExtend;
import com.ccnet.cps.entity.SbShareLogExtend;
import com.ccnet.cps.entity.SbUserMoneyExtend;

/**
 * 后台首页业务逻辑
 * @author JackieWang
 *
 */
public interface HomeIndexService extends BaseService<Object> {
	
	
	/**
	 * 统计日志表
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
	public List<SbContentVisitLogExtend> getContentVisitCount(Date startDate, Date endDate, Integer userId);
	
	/**
	 * 按照省份统计
	 * @param startDate
	 * @param endDate
	 * @param visitExtends
	 * @return
	 */
	public Map<String, Integer> countProvinceContentVisitLogExtend(Date startDate, Date endDate, List<SbContentVisitLogExtend> visitExtends);
	
	
	/**
	 * 按资金状态统计
	 * @param startDate
	 * @param endDate
	 * @param moneyCountExtends
	 * @return
	 */
	public Map<AwardType, Double> countUserByMoneyAwardType(Date startDate, Date endDate, List<SbMoneyCountExtend> moneyCountExtends);
	
	/**
	 * 统计会员提现情况
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto userCashCountByState(Date startDate, Date endDate);
	
	/**
	 * 统计会员提现情况
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbCashLogExtend> getUserCashCountByState(Date startDate, Date endDate);
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto userMoneyCountByMoneyType(Date startDate, Date endDate);
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbMoneyCountExtend> getUserMoneyCountByMoneyType(Date startDate, Date endDate);
	
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto countMemeberTotalMoney(Date startDate, Date endDate);
	
	/**
	 * 统计会员收益情况
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbUserMoneyExtend> getCountMemeberTotalMoney(Date startDate, Date endDate);
	
	/**
	 * 按照是否计费统计
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto countTotalShareByGivenMoney(Date startDate,Date endDate);
	
	/**
	 * 按是否计费统计分享数据
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbShareLogExtend> countMemeberShareByGivenMoney(Date startDate, Date endDate);
	
	/**
	 * 按是分享分类统计分享数据
	 * @param groupColumn
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SbShareLogExtend> countMemeberShareByShareType(Date startDate, Date endDate);
	
	
	
	

}
