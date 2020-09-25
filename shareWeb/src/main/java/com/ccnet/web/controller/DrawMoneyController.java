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

import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.PayState;
import com.ccnet.core.common.PayType;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;
import com.ccnet.cps.service.SbCashLogService;
import com.ccnet.cps.service.SbUserMoneyService;

/**
 * 用户提现
 * @author JackieWang
 * @time 2018-04-03 下午1:38:51
 */
@Controller
@RequestMapping("/draw/")
public class DrawMoneyController extends BaseController<SbCashLog>{

	@Autowired
	SbCashLogService sbCashLogService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;
	
	//申请提现
	@RequestMapping("cash")
	public String drawMoneyIndex(Model model){
		// 当前用户
		MemberInfo memberInfo = getCurUser();
		// 获取当前用户用户总收益
		SbUserMoney userMoney = new SbUserMoney();
		userMoney.setUserId(memberInfo.getMemberId());
		userMoney = sbUserMoneyService.find(userMoney);
		model.addAttribute("userMoney", userMoney);
		model.addAttribute(Const.MENU_SELECTED_INDEX, "cash");
		return "/user/jsp/draw/draw_money";
	}
	
	
	//提现列表
	@RequestMapping("list")
	public String drawMoneyList(Model model){
		Dto dto=getParamAsDto();
		MemberInfo memberInfo=getCurUser();
		Page<SbCashLog> page=newPage(dto);
		SbCashLog cashLog=new SbCashLog();
		cashLog.setUserId(memberInfo.getMemberId());
		page = sbCashLogService.findSbCashLogByPage(cashLog, page, dto);
		//提现记录
		model.addAttribute("cashLogs",page);
		//提现方式
		model.addAttribute("payTypes",PayType.getPayType());
		//提现状态
		model.addAttribute("payStates",PayState.getPayState());
		model.addAttribute(Const.MENU_SELECTED_INDEX, "cash");
		return "/user/jsp/draw/draw_money_list";
	}
	
	//支付宝提现
	@RequestMapping("alipay")
	public String drawByAli(Model model) {
		// 当前用户
		MemberInfo memberInfo = getCurUser();
		// 获取当前用户用户总收益
		SbUserMoney userMoney = new SbUserMoney();
		userMoney.setUserId(memberInfo.getMemberId());
		userMoney = sbUserMoneyService.find(userMoney);
		model.addAttribute("userMoney", userMoney);
		model.addAttribute("mobile", memberInfo.getLoginAccount());
		model.addAttribute(Const.MENU_SELECTED_INDEX, "cash");
		return "/user/jsp/draw/apply_alipay_draw";
	}
	
	//支付宝提现
	@RequestMapping("wechat")
	public String drawByWechat(Model model) {
		// 当前用户
		MemberInfo memberInfo = getCurUser();
		// 获取当前用户用户总收益
		SbUserMoney userMoney = new SbUserMoney();
		userMoney.setUserId(memberInfo.getMemberId());
		userMoney = sbUserMoneyService.find(userMoney);
		model.addAttribute("userMoney", userMoney);
		model.addAttribute("mobile", memberInfo.getLoginAccount());
		model.addAttribute(Const.MENU_SELECTED_INDEX, "cash");
		return "/user/jsp/draw/apply_wechat_draw";
	}
	
	
	
	/**
	 * 支付宝提现
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="save" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void doDrawByAli(HttpServletRequest request,HttpServletResponse response){
		//获取参数
		try {
			Dto dto = getParamAsDto();
			String payAccount=dto.getAsString("alipay");
			String accountName=dto.getAsString("alipayname");
			Integer payType=dto.getAsInteger("paytype");
			String captcha = dto.getAsString("captcha");
			String scaptcha = dto.getAsString("smscode");
			double money = dto.getAsLong("money");
			//图像验证码
			String sessionCode = (String)getSessionAttr(Const.SESSION_SECURITY_CODE);	
			//短信验证码
			String smscode = (String)getSessionAttr(Const.MOBILE_CHECK_CODE);
			
			Map<String, Object> map = new HashMap<String, Object>(0);
			if(CPSUtil.isEmpty(payAccount)){
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				if(payType==0){
			 	    map.put(MessageKey.msg.name(),"支付宝账号不能为空!");
				}else{
					map.put(MessageKey.msg.name(),"微信账号不能为空!");
				}
				responseWriter(map, response);
				return;
			}
			
			if (CPSUtil.isEmpty(accountName)) {
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				if(payType==0){
					map.put(MessageKey.msg.name(),"支付宝账号姓名不能为空!");
				}else{
					map.put(MessageKey.msg.name(),"微信账号姓名不能为空!");
				}
				responseWriter(map, response);
				return;
			}
			
			if (CPSUtil.isEmpty(money)) {
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(),"提现金额不能为空!");
				responseWriter(map, response);
				return;
			}
			
			if(!StringHelper.checkParameter(scaptcha,captcha)){
				map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(),"验证码不能为空!");
				responseWriter(map, response);
				return;
			}
			
			//验证码图形验证码
			if(!captcha.equalsIgnoreCase(sessionCode)){
				responseWriter(ResponseCode.responseMessage(ResponseCode.CaptchaError), response);
    			return;
			}
			
			//验证码短信验证码
      		String strs[] = smscode.split("\\#");
      		CPSUtil.xprint(strs);
      		if(CPSUtil.isEmpty(strs) || strs.length!=2){
      			responseWriter(ResponseCode.responseMessage(ResponseCode.SMSCodeError), response);
     		    return;
      		}
			
			if(CPSUtil.isEmpty(payType)){
				payType = PayType.alipay.getPayId();
				CPSUtil.xprint("默认支付宝支付!");
			}
			
			//当前用户
			MemberInfo memberInfo = getCurUser();
			//获取当前用户用户总收益
			SbUserMoney userMoney=new SbUserMoney();
			userMoney.setUserId(memberInfo.getMemberId());
			userMoney=sbUserMoneyService.find(userMoney);
			//处理扣款
			UserDailyEntity userLock = UserCache.getInstance().getUserCache(memberInfo.getMemberId());
			synchronized (userLock) {
				if(null!=userMoney){
					
					//验证微信单次提现最高200
					if(PayType.ebank.getPayId().equals(payType) && money > 200){
						map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
						map.put(MessageKey.msg.name(),"微信提现金额不能超过200元!");
						responseWriter(map, response);
						return;
					}
					
					//处理首次5元就可以提现
					if(checkIsFirstCash(memberInfo.getMemberId())){//首次已提现
						if(userMoney.getTmoney() < 5){
							map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
							map.put(MessageKey.msg.name(),"首次提现余额需大于5元 您当前余额:"+userMoney.getTmoney()+"元");
							responseWriter(map, response);
							return;
						}
					}else{
						if(userMoney.getTmoney() < 10) {//默认是最低10元才能提现
							map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
							map.put(MessageKey.msg.name(),"账户余额大于10元才能提现!");
							responseWriter(map, response);
							return;
						}
					}
					
					if(userMoney.getTmoney() < money) {
						map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
						map.put(MessageKey.msg.name(),"当前账户余额不足!");
						responseWriter(map, response);
						return;
					}
				}else{
					map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
					map.put(MessageKey.msg.name(),"未查询到当前账户余额!");
					responseWriter(map, response);
					return;
				}
				
				//保存申请记录
				SbCashLog sbCashLog=new SbCashLog();
				sbCashLog.setPayAccount(payAccount);
				sbCashLog.setAccountName(accountName);
				sbCashLog.setCmoney(money);
				sbCashLog.setPayType(payType);
				sbCashLog.setState(PayState.submit.getPayStateId());
				sbCashLog.setUserId(memberInfo.getMemberId());
				sbCashLog.setCreateTime(new Date());
				sbCashLog.setUpdateTime(new Date());
				
				if(sbCashLogService.insert(sbCashLog)>0){
					map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
					map.put(MessageKey.msg.name(),"申请提现成功,请耐心等待!");
					responseWriter(map, response);
					return;
				}else{
					map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
					map.put(MessageKey.msg.name(),"申请提现失败,请稍后再试!");
					responseWriter(map, response);
					return;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommError.getCode());
			map.put(MessageKey.msg.name(),"服务器异常,请稍后再试!");
			responseWriter(map, response);
			return;
		}
	}
	
	
	/**
	 * 判断用户是不是首次提现
	 * @param memberId
	 * @return
	 */
	private boolean checkIsFirstCash(Integer memberId){
		boolean temp = false;
		if(CPSUtil.isNotEmpty(memberId)){
			//获取用户提现记录集合
			SbCashLog cashLog = new SbCashLog();
			cashLog.setUserId(memberId);
			List<SbCashLog> clist = sbCashLogService.findList(cashLog);
			if(CPSUtil.listEmpty(clist)){
				temp = true;
			}
		}
		return temp;
	}
		
}
