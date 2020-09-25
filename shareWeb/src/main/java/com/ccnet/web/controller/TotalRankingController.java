package com.ccnet.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.TodayMoneyRank;
import com.ccnet.cps.entity.TotalMoneyRank;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;
import com.ccnet.cps.service.TodayMoneyRankService;
import com.ccnet.cps.service.TotalMoneyRankService;


/**
 * 收益排行榜
 * @author JackieWang
 *
 * @time 2018-04-12 下午4:16:11
 */

@Controller("totalRrankingController")
@RequestMapping("/ranking/")
public class TotalRankingController extends BaseController<SbUserMoney>{

	@Autowired
	SbUserMoneyService sbUserMoneyService;
	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	TodayMoneyRankService todayMoneyRankService;
	@Autowired
	TotalMoneyRankService totalMoneyRankService;
	
	/**
	 * 总收益排行榜 
	 * @param model
	 * @return
	 */
	@RequestMapping("tlist")
	public String totalRankList(Model model) {
		Dto dto=getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		TotalMoneyRank rank = new TotalMoneyRank();
        List<TotalMoneyRank>  rList = totalMoneyRankService.findTotalMoneyRankList(rank);
        model.addAttribute("rList",rList);
		model.addAttribute("tp","total");
		//获取当前用户总收益
		model.addAttribute("totalMoney",getUserTotalMoneyOrBalance(memberInfo, 0));
		//获取今日用户总收益
		model.addAttribute("totalTotayMoney",getTodayTotalMoney(memberInfo));
		return "/user/jsp/earnings/ranking_list";
	}
	
	/**
	 * 日收益排行榜
	 * @param model
	 * @return
	 */
	@RequestMapping("dlist")
	public String todayRankList(Model model) {
		Dto dto=getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		TodayMoneyRank rank = new TodayMoneyRank();
        List<TodayMoneyRank>  rList = todayMoneyRankService.findTodayMoneyRankList(rank);
        model.addAttribute("rList",rList);
		model.addAttribute("tp","today");
		//获取当前用户总收益
		model.addAttribute("totalMoney",getUserTotalMoneyOrBalance(memberInfo, 0));
		//获取今日用户总收益
		model.addAttribute("totalTotayMoney",getTodayTotalMoney(memberInfo));
		return "/user/jsp/earnings/ranking_list";
	}
	
	/**
	 * 获取用户累计收益
	 * @param memberInfo
	 * @param flag 0 总额 1 余额
	 * @return
	 */
	private Double getUserTotalMoneyOrBalance(MemberInfo memberInfo,int flag) {
		Double totalMoney = 0.0d;//累计体现金额
		Double curBalance = 0.0d;//当前余额
		Dto paramDto = new BaseDto();
		if(CPSUtil.isNotEmpty(memberInfo.getMemberId())){
			//设置会员信息
			SbUserMoney userMoney = new SbUserMoney();
			userMoney.setUserId(memberInfo.getMemberId());
			List<SbUserMoney> moneyList= sbUserMoneyService.findSbUserMoneyList(userMoney, paramDto);
			if(CPSUtil.listNotEmpty(moneyList)){
				userMoney = moneyList.get(0); 
				totalMoney = userMoney.getProfitsMoney();
				curBalance = userMoney.getTmoney();
			}
		}
		return flag==0?totalMoney:curBalance;
	}
	
	/**
	 * 获取用户今日总收益
	 * @param memberInfo
	 * @return
	 */
	private Double getTodayTotalMoney(MemberInfo memberInfo) {
		
		Double todayMoney = 0.0d;//今日收益
		Dto paramDto = new BaseDto();
		String visitCode = memberInfo.getVisitCode();
		if(CPSUtil.isNotEmpty(visitCode)){
			//设置用户查询
			SbVisitMoney sbVisitMoney = new SbVisitMoney();
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			sbVisitMoney.setUserId(memberInfo.getMemberId());
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			//设置查询日期
			String startDate = CPSUtil.getCurDate();
			String overDate = CPSUtil.getCurDate();
			paramDto.put("end_date", startDate);
			paramDto.put("start_date", startDate);
			//获取今日邀请奖励
			double totalVisitMoney = sbVisitMoneyService.getCurrentUserVisitMoney(sbVisitMoney, paramDto);
			//获取今日文章/红包/提成收益
			double totalMoneyCount = sbMoneyCountService.getCurrentUserMoneyCount(sbMoneyCount, paramDto);
			//总收益=文章收益+注册奖励
			todayMoney = totalVisitMoney + totalMoneyCount;
		}
		
		CPSUtil.xprint("总金额："+todayMoney);
		return todayMoney;
	}
}
