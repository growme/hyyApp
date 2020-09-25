package com.ccnet.web.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbSignInfo;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbSignInfoService;

/**
 * 用户签到
 * @author JackieWang
 *
 */
@RequestMapping("/sign/")
@Controller("userSignController")
public class UserSignController extends BaseController<SbSignInfo> {
	
	@Autowired
	SbSignInfoService sbSignInfoService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	
	@RequestMapping("index")
	public String index(Model model){
		//获取签到数据
		MemberInfo memberInfo = getCurUser();
		double sysBonus = 0d;
		double addBonus = 0d;
		String sys_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_BONUS);//签到基金
		String add_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_ADD_BONUS);//连续签到叠加金额
		if(CPSUtil.isEmpty(sys_bonus)){
			sysBonus=0.05d;//系统未设置则默认0.05
		}else{
			sysBonus = Double.parseDouble(sys_bonus);
		}
		if(CPSUtil.isEmpty(add_bonus)){
			addBonus=0.01d;//系统未设置则默认0.01
		}else{
			addBonus = Double.parseDouble(add_bonus);
		}
		
		SbSignInfo sbSignInfo = sbSignInfoService.getSbSignInfoById(memberInfo.getMemberId());
		double signMoney = 0.0d;
		if(CPSUtil.isNotEmpty(sbSignInfo)){//有签到数据
			String lastSingDate = DateUtils.formatDate(sbSignInfo.getLastSignTime(), DateUtils.parsePatterns[3]);
			long subDays = DateUtils.getMDaySub(lastSingDate, CPSUtil.getCurrentTime());
			CPSUtil.xprint("subDays="+subDays);
			 if(subDays<=1){
				 if(subDays==0){
				    signMoney = sbSignInfo.getSignMoney();
				 }else{
					signMoney = sbSignInfo.getSignMoney() + addBonus;
				 }
			 }else{//不连续
				 signMoney = sysBonus;
			 }
		}else{
			 signMoney = sysBonus;//默认值
		}
		
		if(checkTodaySign(memberInfo.getMemberId())){
			model.addAttribute("jrqd", "1");//已签到
		}else{
			model.addAttribute("jrqd", "0");//未签到
		}
		
		model.addAttribute("qdjj", sysBonus);
		model.addAttribute("djje", addBonus);
		model.addAttribute("signMoney", CPSUtil.formatDoubleVal(signMoney, "0.00"));
		model.addAttribute("signInfo", sbSignInfo);
		model.addAttribute(Const.MENU_SELECTED_INDEX, "home");
		return "/user/jsp/sign/sign_index";
	}
	
	
	/**
	 * 每日签到
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="bonus" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void dailyBonus(HttpServletRequest req,HttpServletResponse res){
		
		try {
			Dto dto = getParamAsDto();
			SbSignInfo sbSignInfo = null;
			MemberInfo memberInfo = getCurUser();
			Map<String, Object> map = new HashMap<String, Object>(0);
			if(CPSUtil.isNotEmpty(memberInfo)){
				double sysBonus = 0d;
				double addBonus = 0d;
				Integer userId = memberInfo.getMemberId();
				String sys_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_BONUS);//签到基金
				String add_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_ADD_BONUS);//连续签到叠加金额
				if(CPSUtil.isEmpty(sys_bonus)){
					sysBonus=0.05d;//系统未设置则默认0.05
				}else{
					sysBonus = Double.parseDouble(sys_bonus);
				}
				if(CPSUtil.isEmpty(add_bonus)){
					addBonus=0.01d;//系统未设置则默认0.01
				}else{
					addBonus = Double.parseDouble(add_bonus);
				}
				
				//判断只有当日产生了推广收益的用户才能签到
				if(!checkExistMoneyCount(userId,dto)){
					map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
					map.put(MessageKey.msg.name(),"必须是当日分享了文章并且有收益，才能签到哦！");
					responseWriter(map, res);
					return;
				}
				
				//查询当前用户今日是否已领取奖励
				if(checkTodaySign(userId)){
					map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
					map.put(MessageKey.msg.name(),"亲，您今天已经签到过了!");
					responseWriter(map, res);
					return;
				}
				
				//获取用户签到数据
				boolean success = false;
				sbSignInfo = sbSignInfoService.getSbSignInfoById(userId);
				Integer seriesCount = 0;//续签次数
				Integer totalCount = 0;//累签次数
				double totalMoney = 0.00d;//累计奖励
				double signMoney = 0.00d;//签到基金
				double _totalMoney = 0.00d;
				double _signMoney = 0.00d;
				if(CPSUtil.isNotEmpty(sbSignInfo)){
					 seriesCount = sbSignInfo.getSeriesCount();//续签次数
				     totalCount = sbSignInfo.getTotalCount();//累签次数
					 totalMoney = sbSignInfo.getTotalMoney();//累计奖励
					 signMoney = sbSignInfo.getSignMoney();//签到基金
					 //判断当前时间-上次签到时间是否在一天内
					 String lastSingDate = DateUtils.formatDate(sbSignInfo.getLastSignTime(), DateUtils.parsePatterns[3]);
					 if(DateUtils.getMDaySub(lastSingDate, CPSUtil.getCurrentTime())<=1){
						_totalMoney = totalMoney + signMoney + addBonus;
						_signMoney = signMoney + addBonus;
						sbSignInfo.setSeriesCount(seriesCount + 1);
						sbSignInfo.setTotalCount(totalCount + 1);
						sbSignInfo.setSignMoney(_signMoney);
						sbSignInfo.setTotalMoney(_totalMoney);
					 }else{
						_totalMoney = totalMoney + sysBonus;
						_signMoney = sysBonus;
						sbSignInfo.setSeriesCount(1);
						sbSignInfo.setTotalCount(totalCount + 1);
						sbSignInfo.setSignMoney(sysBonus);
						sbSignInfo.setTotalMoney(_totalMoney);
					 }
					 sbSignInfo.setUserId(userId);
					 sbSignInfo.setLastSignTime(new Date());
					 success = sbSignInfoService.editSbSignInfo(sbSignInfo);
					 
				}else{//用户首次签到直接插入数据
					_totalMoney = sysBonus;
					_signMoney = sysBonus;
					sbSignInfo = new SbSignInfo();
					sbSignInfo.setUserId(userId);
					sbSignInfo.setSeriesCount(1);
					sbSignInfo.setTotalCount(1);
					sbSignInfo.setSignMoney(sysBonus);
					sbSignInfo.setTotalMoney(sysBonus);
					sbSignInfo.setLastSignTime(new Date());
					success = sbSignInfoService.saveSbSignInfo(sbSignInfo);
				}
				
				if(success){
					SbMoneyCount sbMoneyCount=new SbMoneyCount();
					sbMoneyCount.setCreateTime(new Date());
					sbMoneyCount.setmType(AwardType.redpacke.getAwardId());
					sbMoneyCount.setUmoney(_signMoney);
					sbMoneyCount.setUserId(userId);
					if(sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount)){
						map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
						map.put(MessageKey.msg.name(),"签到成功领取"+CPSUtil.formatDoubleVal(_signMoney, "0.00")+"元红包");
						responseWriter(map, res);
						return;
					}else{
						map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
						map.put(MessageKey.msg.name(),"签到失败,请稍后再试!");
						responseWriter(map, res);
						return;
					}
				}
			}else{
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(),"非法请求,请稍后再试!");
				responseWriter(map, res);
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
			map.put(MessageKey.msg.name(),"服务器异常,请稍后再试!");
			responseWriter(map, res);
			return;
		}
	}
	
	
	
	/**
	 * 判断用户是否产生推广收益
	 * @param userId
	 * @param paramDto
	 * @return
	 */
	private boolean checkExistMoneyCount(Integer userId,Dto paramDto) {
		boolean temp = false;
		SbMoneyCount moneyCount = new SbMoneyCount();
		if(CPSUtil.isNotEmpty(userId)){
			moneyCount.setUserId(userId);
			moneyCount.setmType(AwardType.readawd.getAwardId());
		    Double earnCount = sbMoneyCountService.getCurrentUserMoneyCount(moneyCount, paramDto);
		   if(CPSUtil.isNotEmpty(earnCount) && earnCount>0){
			   temp = true;
		   }
		}
		return temp;
	}
	
	/**
	 * 判断今日是否签到
	 * @param userId
	 * @return
	 */
	private boolean checkTodaySign(Integer userId) {
		boolean temp = false;
		String endDate = CPSUtil.getCurDate();
		String startDate = CPSUtil.getCurDate();
		if(CPSUtil.isNotEmpty(userId)){
			SbMoneyCount moneyCount = sbMoneyCountService.getSbMoneyCountByType(userId, AwardType.redpacke.getAwardId(), startDate, endDate);
		   if(CPSUtil.isNotEmpty(moneyCount)){
			   temp = true;
		   }
		}
		return temp;
	}
	
	
	/**
	 * 判断前一天是否签到
	 * @param userId
	 * @return
	 */
	private boolean checkYesterdaySign(Integer userId) {
		boolean temp = false;
		String endDate = CPSUtil.getDateByUDay(-1);
		String startDate = CPSUtil.getDateByUDay(-1);
		if(CPSUtil.isNotEmpty(userId)){
			SbMoneyCount moneyCount = sbMoneyCountService.getSbMoneyCountByType(userId, AwardType.redpacke.getAwardId(), startDate, endDate);
		   if(CPSUtil.isNotEmpty(moneyCount)){
			   temp = true;
		   }
		}
		return temp;
	}
	
}
