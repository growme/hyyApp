package com.ccnet.api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbSignInfo;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.SbCashLogService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbSignInfoService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;

@Controller
@RequestMapping("/api/home/")
public class ApiHomeController extends BaseController<Object> {

	@Autowired
	private SbMoneyCountService sbMoneyCountService;
	@Autowired
	private SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	private SbUserMoneyService sbUserMoneyService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private SbCashLogService sbCashLogService;
	@Autowired
	SbSignInfoService sbSignInfoService;

	/**
	 * 访问首页
	 */
	@RequestMapping("index")
	@ResponseBody
	public ResultDTO<?> Index(Headers header) {
		try {
			MemberInfo memberInfo = memberInfoService.getUserByUserID(Integer.valueOf(header.userid));
			Map<String, Object> map = new HashMap<String, Object>();
			if (CPSUtil.isNotEmpty(memberInfo)) {
				// 获取当前用户总收益
				map.put("totalMoney", CPSUtil.formatDoubleVal(getUserTotalMoneyOrBalance(memberInfo, 0), "0.00"));
				// 获取当前用户余额
				map.put("curBalance", CPSUtil.formatDoubleVal(getUserTotalMoneyOrBalance(memberInfo, 1), "0.00"));
				// 获取今日用户总收益
				map.put("totalTotayMoney", CPSUtil.formatDoubleVal(getTodayTotalMoney(memberInfo), "0.00"));
				// 获取累计提现总额
				// map.put("totalCashMoney", getUserTotalCashMoney(memberInfo));
				// 获取徒弟提现收益
				// map.put("totalChildMoney",
				// getCurrentUserPercentage(memberInfo));
				// 获取用户徒弟+徒孙数量
				map.put("totalUserNum", getChildMemberNum(memberInfo));
				map.put("memberInfo", memberInfo);
				double sysBonus = 0d;
				double addBonus = 0d;
				int seriesCount = 0;
				String sys_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_BONUS);// 签到基金
				String add_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_ADD_BONUS);// 连续签到叠加金额
				if (CPSUtil.isEmpty(sys_bonus)) {
					sysBonus = 0.05d;// 系统未设置则默认0.05
				} else {
					sysBonus = Double.parseDouble(sys_bonus);
				}
				if (CPSUtil.isEmpty(add_bonus)) {
					addBonus = 0.01d;// 系统未设置则默认0.01
				} else {
					addBonus = Double.parseDouble(add_bonus);
				}

				SbSignInfo sbSignInfo = sbSignInfoService.getSbSignInfoById(memberInfo.getMemberId());
				double signMoney = 0.0d;
				if (CPSUtil.isNotEmpty(sbSignInfo)) {// 有签到数据
					String lastSingDate = DateUtils.formatDate(sbSignInfo.getLastSignTime(),
							DateUtils.parsePatterns[3]);
					long subDays = DateUtils.getMDaySub(lastSingDate, CPSUtil.getCurrentTime());
					CPSUtil.xprint("subDays=" + subDays);
					if (subDays <= 1) {
						if (subDays == 0) {
							signMoney = sbSignInfo.getSignMoney();
						} else {
							signMoney = sbSignInfo.getSignMoney() + addBonus;
						}
					} else {// 不连续
						signMoney = sysBonus;
					}
					seriesCount = sbSignInfo.getSeriesCount();
				} else {
					signMoney = sysBonus;// 默认值
				}
				if (checkTodaySign(memberInfo.getMemberId())) {
					map.put("jrqd", "1");// 已签到
				} else {
					map.put("jrqd", "0");// 未签到
				}
				map.put("seriesCount", seriesCount);
				map.put("qdjj", sysBonus);
				map.put("djje", addBonus);
				map.put("signMoney", CPSUtil.formatDoubleVal(signMoney, "0.00"));
				map.put("signInfo", sbSignInfo);
				map.put("wxydUrl", CPSUtil.getParamValue("JP_AD_WXYD_URL"));
				map.put("conversionRatio", CPSUtil.getParamValue("PEPEAT_CONVERSION_RATIO"));
				return ResultDTO.OK(map);
			} else {
				return ResultDTO.ERROR(BasicCode.逻辑错误);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	// 累计收入
	@RequestMapping("incomeCount")
	@ResponseBody
	public ResultDTO<?> incomeCount(Headers header) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			MemberInfo memberInfo = memberInfoService.getUserByUserID(Integer.valueOf(header.userid));
			Map<String, Object> map = new HashMap<>();
			// 当前用户
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime then = now.minusDays(7);
			// 获取当日收益
			List<SbMoneyCount> totalMoneys = sbMoneyCountService.getUserMoneyCountByUserIdAndDate(
					Integer.valueOf(header.userid), then.format(format), now.format(format));
			// 总收益
			map.put("totalMoney", getUserTotalMoneyOrBalance(memberInfo, 0));
			// 实时收入
			map.put("curBalance", getUserTotalMoneyOrBalance(memberInfo, 1));
			// 任务奖金
			map.put("totalTotayMoney", CPSUtil.formatDoubleVal(getTodayTotalMoney(memberInfo), "0.00"));
			// 好友贡献
			map.put("totalChildMoney", getCurrentUserPercentage(memberInfo));
			map.put("totalMoneys", totalMoneys);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.参数错误);
		}

	}

	/**
	 * 获取用户累计收益
	 * 
	 * @param memberInfo
	 * @param flag
	 *            0 总额 1 余额
	 * @return
	 */
	private Double getUserTotalMoneyOrBalance(MemberInfo memberInfo, int flag) {
		Double totalMoney = 0.0d;// 累计体现金额
		Double curBalance = 0.0d;// 当前余额
		Dto paramDto = new BaseDto();
		if (CPSUtil.isNotEmpty(memberInfo.getMemberId())) {
			// 设置会员信息
			SbUserMoney userMoney = new SbUserMoney();
			userMoney.setUserId(memberInfo.getMemberId());
			List<SbUserMoney> moneyList = sbUserMoneyService.findSbUserMoneyList(userMoney, paramDto);
			if (CPSUtil.listNotEmpty(moneyList)) {
				userMoney = moneyList.get(0);
				totalMoney = userMoney.getProfitsMoney();
				curBalance = userMoney.getTmoney();
			}
		}
		return flag == 0 ? totalMoney : curBalance;
	}

	/**
	 * 判断今日是否签到
	 * 
	 * @param userId
	 * @return
	 */
	private boolean checkTodaySign(Integer userId) {
		boolean temp = false;
		String endDate = CPSUtil.getCurDate();
		String startDate = CPSUtil.getCurDate();
		if (CPSUtil.isNotEmpty(userId)) {
			SbMoneyCount moneyCount = sbMoneyCountService.getSbMoneyCountByType(userId, AwardType.redpacke.getAwardId(),
					startDate, endDate);
			if (CPSUtil.isNotEmpty(moneyCount)) {
				temp = true;
			}
		}
		return temp;
	}

	/**
	 * 获取下线提成
	 * 
	 * @param memberInfo
	 * @return
	 */
	private Double getCurrentUserPercentage(MemberInfo memberInfo) {
		double totalMoney = 0.0d;// 累计邀请收益
		Dto paramDto = new BaseDto();
		if (CPSUtil.isNotEmpty(memberInfo.getMemberId())) {
			// 设置会员信息
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			double totalTDMoney = sbMoneyCountService.getCurrentUserTDPercentageCount(sbMoneyCount, paramDto);
			if (totalTDMoney > 0) {
				totalMoney += totalTDMoney;
			}
			double totalTSMoney = sbMoneyCountService.getCurrentUserTSPercentageCount(sbMoneyCount, paramDto);
			if (totalTSMoney > 0) {
				totalMoney += totalTSMoney;
			}
		}
		return totalMoney;
	}

	/**
	 * 获取下线人数
	 * 
	 * @param memberInfo
	 * @return
	 */
	private Integer getChildMemberNum(MemberInfo memberInfo) {

		Integer childNum = 0;
		// List<MemberInfo> childInfos1 = null;
		// List<MemberInfo> childInfos2 = null;
		// String visitCode = memberInfo.getVisitCode();
		// if (CPSUtil.isNotEmpty(visitCode)) {
		// 同时提取徒弟和徒孙
		// childInfos1 =
		// CPSUtil.getChildTDMemeberList(memberInfo.getMemberId());
		// childInfos2 =
		// CPSUtil.getChildTSMemeberList(memberInfo.getMemberId());
		// if (CPSUtil.isNotEmpty(childInfos1)) {
		// childNum += childInfos1.size();
		// CPSUtil.xprint("徒弟个数:" + childInfos1.size());
		// }

		// if (CPSUtil.isNotEmpty(childInfos2)) {
		// childNum += childInfos2.size();
		// CPSUtil.xprint("徒孙个数:" + childInfos2.size());
		// }
		// }
		if (StringUtils.isNotBlank(memberInfo.getVisitCode())) {
			// 获取徒弟数据
			List<MemberInfo> memberInfos = memberInfoService.findRecomMemberInfoByVisitCode(memberInfo.getVisitCode());
			childNum = memberInfos.size();
		}
		CPSUtil.xprint("下线总数:" + childNum);
		return childNum;
	}

	/**
	 * 获取用户今日总收益
	 * 
	 * @param memberInfo
	 * @return
	 */
	private Double getTodayTotalMoney(MemberInfo memberInfo) {

		Double todayMoney = 0.0d;// 今日收益
		Dto paramDto = new BaseDto();
		String visitCode = memberInfo.getVisitCode();
		if (CPSUtil.isNotEmpty(visitCode)) {
			// 设置用户查询
			SbVisitMoney sbVisitMoney = new SbVisitMoney();
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			sbVisitMoney.setUserId(memberInfo.getMemberId());
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			// 设置查询日期
			String startDate = CPSUtil.getCurDate();
			paramDto.put("end_date", startDate);
			paramDto.put("start_date", startDate);
			// 获取今日邀请奖励
			double totalVisitMoney = sbVisitMoneyService.getCurrentUserVisitMoney(sbVisitMoney, paramDto);
			// 获取今日文章/红包/提成收益
			double totalMoneyCount = sbMoneyCountService.getCurrentUserMoneyCount(sbMoneyCount, paramDto);
			// 总收益=文章收益+注册奖励
			todayMoney = totalVisitMoney + totalMoneyCount;
		}

		CPSUtil.xprint("总金额：" + todayMoney);
		return todayMoney;
	}

	/**
	 * 获取用户累计提现金额
	 * 
	 * @param memberInfo
	 * @return
	 */
	private Double getUserTotalCashMoney(MemberInfo memberInfo) {
		Double totalCashMoney = 0.0d;// 累计提现金额
		Dto paramDto = new BaseDto();
		if (CPSUtil.isNotEmpty(memberInfo.getMemberId())) {
			// 设置会员信息
			SbCashLog cashLog = new SbCashLog();
			cashLog.setUserId(memberInfo.getMemberId());
			totalCashMoney = sbCashLogService.getCurrentUserCashCount(cashLog, paramDto);
		}
		return totalCashMoney;
	}
}
