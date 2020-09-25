package com.ccnet.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.UserDetailMoney;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;
/**
 * 
 * 每日收益
 * @author zqy
 *
 */
@Controller
@RequestMapping("/earnings/")
public class EarningsController  extends BaseController<SbMoneyCount>{
	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;
	
	/**
	 * 今日收益列表
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String earningsToday(Model model){
		Dto dto = getParamAsDto();
		MemberInfo memberInfo=getCurUser();
		SbMoneyCount sbMoneyCount = new SbMoneyCount();
		SbVisitMoney sbVisitMoney = new SbVisitMoney();
		sbMoneyCount.setUserId(memberInfo.getMemberId());
		sbVisitMoney.setUserId(memberInfo.getMemberId());
		
		dto.put("end_date",CPSUtil.getCurDate());
		dto.put("start_date",CPSUtil.getCurDate());
		//获取当日收益
		List<UserDetailMoney> totalMoneys = sbMoneyCountService.queryUserDetailMoneyList(sbMoneyCount, sbVisitMoney, dto);
		//收益列表
		model.addAttribute("earnLists", totalMoneys);
		model.addAttribute("title", "今日收益");
		model.addAttribute("urlTitle", "收益明细");
		model.addAttribute("url", "earnings/detail.html");
		return "/user/jsp/earnings/earnings_day";
	}
	
	/**
	 * 当前所有收益列表
	 * @param model
	 * @return
	 */
	@RequestMapping("detail")
	public String earningsIndex(Model model){
		Dto dto = getParamAsDto();
		MemberInfo memberInfo=getCurUser();
		SbMoneyCount sbMoneyCount = new SbMoneyCount();
		SbVisitMoney sbVisitMoney = new SbVisitMoney();
		sbMoneyCount.setUserId(memberInfo.getMemberId());
		sbVisitMoney.setUserId(memberInfo.getMemberId());
		//获取当日收益
		List<UserDetailMoney> totalMoneys = sbMoneyCountService.queryUserDetailMoneyList(sbMoneyCount, sbVisitMoney, dto);
		//收益列表
		model.addAttribute("earnLists", totalMoneys);
		model.addAttribute("title", "收益明细");
		model.addAttribute("urlTitle", "今日收益");
		model.addAttribute("url", "earnings/index.html");
		return "/user/jsp/earnings/earnings_day";
	}
	
	
	/**
	 * 每日红包
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="dailyBonus" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void dailyBonus(HttpServletRequest req,HttpServletResponse res){
		try {
			Dto dto = getParamAsDto();
			MemberInfo memberInfo=getCurUser();
			//系统设定红包金额 SYS_DEFAULT_DAILY_BONUS
			double bonus=Double.parseDouble(CPSUtil.getParamValue("SYS_DEFAULT_DAILY_BONUS"));
			//查询当前用户今日是否已领取红包
			SbMoneyCount moneyCount = sbMoneyCountService.getSbMoneyCountByType(memberInfo.getMemberId(), AwardType.redpacke.getAwardId(), CPSUtil.getCurDate(), CPSUtil.getCurDate());
			Map<String, Object> map = new HashMap<String, Object>(0);
			if(CPSUtil.isNotEmpty(moneyCount)){
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(),"您今天已经领过了!");
				responseWriter(map, res);
				return;
			}
			
			//添加红包收益
			SbMoneyCount sbMoneyCount=new SbMoneyCount();
			sbMoneyCount.setCreateTime(new Date());
			sbMoneyCount.setmType(AwardType.redpacke.getAwardId());
			sbMoneyCount.setUmoney(bonus);
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			if(sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount)){
				map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
				map.put(MessageKey.msg.name(),"领取到红包"+bonus+"元");
				responseWriter(map, res);
				return;
			}else{
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(),"红包领取失败,请稍后再试!");
				responseWriter(map, res);
				return;
			}
		} catch (Exception e) {
			CPSUtil.xprint(e.getMessage());
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
			map.put(MessageKey.msg.name(),"服务器异常,请稍后再试!");
			responseWriter(map, res);
			return;
		}
	}
	
}
