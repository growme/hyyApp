package com.ccnet.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.NoticeType;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.RandomValueUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.SystemNotice;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.SbCashLogService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;
import com.ccnet.cps.service.SystemNoticeService;

/**
 * 首页控制
 * ClassName: HomeIndexController 
 * @author Jackie Wang
 * @company 薄荷咨询
 * @copyright 版权所有 盗版必究
 * @date 2019-5-24
 */
@Controller
@RequestMapping("/home/")
public class HomeIndexController extends BaseController<Object> {
	
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
	private SystemNoticeService systemNoticeService;
	
	
	
	/**
	 * 访问首页
	 */
	@RequestMapping("index")
	public String Index(Model model){
		MemberInfo memberInfo = getCurUser();
		if(CPSUtil.isNotEmpty(memberInfo)){
			Dto paramDto = getParamAsDto();
			//获取当前用户总收益
			model.addAttribute("totalMoney",getUserTotalMoneyOrBalance(memberInfo, 0));
			//获取当前用户余额
			model.addAttribute("curBalance",getUserTotalMoneyOrBalance(memberInfo, 1));
			//获取今日用户总收益
			model.addAttribute("totalTotayMoney",getTodayTotalMoney(memberInfo));
			//获取累计提现总额
			model.addAttribute("totalCashMoney",getUserTotalCashMoney(memberInfo));
			//获取徒弟提现收益
			model.addAttribute("totalChildMoney",getCurrentUserPercentage(memberInfo));
			//获取用户徒弟+徒孙数量
			model.addAttribute("totalUserNum",getChildMemberNum(memberInfo));
			//获取置顶的新闻
			getHomeNoticeslist(model);
			
			model.addAttribute("memberInfo", memberInfo);
			model.addAttribute(Const.MENU_SELECTED_INDEX, "home");
			return "/user/jsp/home/home_index";
		}else{
			return "redirect:/user/login";
		}
	}
	
	
	/**
	 * 获取置顶新闻
	 * @return
	 */
	private void getHomeNoticeslist(Model model){
		SystemNotice notice = new SystemNotice();
		notice.setNoticeType(NoticeType.SITE_NOTICE.getType());
		notice.setState(StateType.Valid.getState());
		notice.setShowTop(StateType.Valid.getState());
		List<SystemNotice> noticeList = systemNoticeService.findSystemNoticeList(notice);
		if(CPSUtil.isNotEmpty(noticeList)){
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("curNewsInfo",noticeList.get(0));
		}
	}
	
	/**
	 * 获取下线人数
	 * @param memberInfo
	 * @return
	 */
	private Integer getChildMemberNum(MemberInfo memberInfo){
		
		Integer childNum = 0;
		List<MemberInfo> childInfos1 = null;
		List<MemberInfo> childInfos2 = null;
		String visitCode = memberInfo.getVisitCode();
		if(CPSUtil.isNotEmpty(visitCode)){
			//同时提取徒弟和徒孙
			childInfos1 = CPSUtil.getChildTDMemeberList(memberInfo.getMemberId());
			childInfos2 = CPSUtil.getChildTSMemeberList(memberInfo.getMemberId());
			if(CPSUtil.isNotEmpty(childInfos1)){
				childNum+=childInfos1.size();
				CPSUtil.xprint("徒弟个数:"+childInfos1.size());
			}
			
			if(CPSUtil.isNotEmpty(childInfos2)){
				childNum+=childInfos2.size();
				CPSUtil.xprint("徒孙个数:"+childInfos2.size());
			}
		}
		
		CPSUtil.xprint("下线总数:"+ childNum);
		return childNum;
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
	
	
	/**
	 * 获取用户累计提现金额
	 * @param memberInfo
	 * @return
	 */
	private Double getUserTotalCashMoney(MemberInfo memberInfo) {
		Double totalCashMoney = 0.0d;//累计提现金额
		Dto paramDto = new BaseDto();
		if(CPSUtil.isNotEmpty(memberInfo.getMemberId())){
			//设置会员信息
			SbCashLog cashLog = new SbCashLog();
			cashLog.setUserId(memberInfo.getMemberId());
			totalCashMoney = sbCashLogService.getCurrentUserCashCount(cashLog, paramDto);
		}
		return totalCashMoney;
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
	 * 获取邀请收益
	 * @param memberInfo
	 * @return
	 */
	private Double getCurrentUserVisitMoney(MemberInfo memberInfo) {
		Double totalMoney = 0.0d;//累计邀请收益
		Dto paramDto = new BaseDto();
		if(CPSUtil.isNotEmpty(memberInfo.getMemberId())){
			//设置会员信息
			SbVisitMoney visitMoney = new SbVisitMoney();
			visitMoney.setUserId(memberInfo.getMemberId());
			totalMoney = sbVisitMoneyService.getCurrentUserVisitMoney(visitMoney, paramDto);
		}
		return totalMoney;
	}
	
	
	/**
	 * 获取下线提成
	 * @param memberInfo
	 * @return
	 */
	private Double getCurrentUserPercentage (MemberInfo memberInfo) {
		double totalMoney = 0.0d;//累计邀请收益
		Dto paramDto = new BaseDto();
		if(CPSUtil.isNotEmpty(memberInfo.getMemberId())){
			//设置会员信息
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			double totalTDMoney = sbMoneyCountService.getCurrentUserTDPercentageCount(sbMoneyCount, paramDto);
			if(totalTDMoney>0){
				totalMoney +=totalTDMoney;
			}
			double totalTSMoney = sbMoneyCountService.getCurrentUserTSPercentageCount(sbMoneyCount, paramDto);
			if(totalTSMoney>0){
				totalMoney +=totalTSMoney;
			}
		}
		return totalMoney;
	}
	
	
	/**
	 * 验证用户是否完善资料
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="user/check" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void checkUser(HttpServletRequest req, HttpServletResponse res){
		
		Dto paramDto = getParamAsDto();
		MemberInfo userInfo = getCurUser();
		if(CPSUtil.isEmpty(userInfo)){//未登录
			responseWriter(ResponseCode.responseMessage(ResponseCode.AuthLoginError), res);
			return;
		}
		//判断用户资料有没有完善
		String payAccount = userInfo.getPayAccount();//支付宝账号
		String accountName = userInfo.getAccountName();//账号名称
		String wechat = userInfo.getWechat();
		String qqNum = userInfo.getQqNum();
		String mobile = userInfo.getMobile();
		if(!StringHelper.checkParameter(payAccount,accountName,wechat,qqNum)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), res);
			return;
		}
		
		//处理返回提示
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
		map.put(MessageKey.msg.name(),"已完善资料");
		responseWriter(map, res);
	}
	
	/**
	 * 提现滚动记录
	 * @param req
	 * @param res
	 */
	@RequestMapping(value="cash/list" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void getCashList(HttpServletRequest req, HttpServletResponse res){
		
		try {
			//获取参数
			Dto dto = getParamAsDto();
			//获取用户列表
			Random rand = new Random();
			Map<String, String> cMap = null;
			int rtime = 0;
			int rprice = 0;
			List<Map<String, String>> cList = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				cMap = new HashMap<>();
				rtime = rand.nextInt(60);
				rprice = rand.nextInt(10);
				//处理时间和金额
				if(rtime==0){
					rtime = rtime+1;
				}
				
				if(rtime<10){
					rtime = rtime+10;
				}
				
				if(rtime==60){
					rtime = rtime-1;
				}
				
				if(rprice==0){
					rprice = rprice+1;
				}
				if(rprice==10){
					rprice = rprice-1;
				}
				
				cMap.put("phone", RandomValueUtil.getTelPhone());
				cMap.put("price", String.valueOf(rprice)+"0");
				cMap.put("time", String.valueOf(rtime));
				cList.add(cMap);
			}
			
			//处理返回提示
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
			map.put(MessageKey.msg.name(),"获取数据成功");
			map.put("obj", cList);
			responseWriter(map, res);
			
		}catch (Exception e) {
			e.printStackTrace();
			//处理返回提示
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
			map.put(MessageKey.msg.name(),"服务器异常,请稍后再试!");
			responseWriter(map, res);
		}
		
	}
	
	
}
