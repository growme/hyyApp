package com.ccnet.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.UuidUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.common.utils.security.MemberInfoShiroUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.MemberLoginLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.MemberLoginLogService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;

/**
 * 会员登录
 * @author Jackie Wang
 * @company 薄荷咨询
 * @copyright 版权所有 盗版必究
 * @date 2019-6-1
 */
@Controller
@RequestMapping("/user/")
public class MemberLoginController extends BaseController<MemberInfo>{
	
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private MemberLoginLogService memberLoginLogService;
	
	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;
	
	/**
	 * 访问登录页
	 * @param model
	 * @author Jackie Wang
	 * @date 2019-10-19
	 */
	@RequestMapping(value="login")
	public String login(Model model,HttpServletRequest request) {
		
		String nowDomian = request.getServerName();
		CPSUtil.xprint("nowDomian=" + nowDomian);
		if(!CPSUtil.isHomeDomain(nowDomian)){
			CPSUtil.xprint("非白名单域名访问跳转腾讯新闻！！！");
			model.addAttribute("target", "http://www.qq.com");
			return "/common/forward";
		}
		
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "/user/jsp/login/user_login";
	}
	
	
	/**
	 * 访问注册页
	 * @param model
	 * @author Jackie Wang
	 * @date 2019-10-19
	 */
	@RequestMapping(value="register")
	public String register(Model model,HttpServletRequest request) {
		
		String nowDomian = request.getServerName();
		CPSUtil.xprint("nowDomian=" + nowDomian);
		if(!CPSUtil.isHomeDomain(nowDomian)){
			CPSUtil.xprint("非白名单域名访问跳转腾讯新闻！！！");
			model.addAttribute("target", "http://www.qq.com");
			return "/common/forward";
		}
		
		//获取邀请人
		Dto dto = getParamAsDto();
		String visit_code = dto.getAsString("v");
		String visit_time = dto.getAsString("t");
		CPSUtil.xprint("visit_time="+visit_time);
		if(CPSUtil.isNotEmpty(visit_code)){
			model.addAttribute("recom_user", visit_code);
		}
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "/user/jsp/login/user_register";
		
	}
	
	
	/**
	 * 找回密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/forget")
	public String forgetPasword(Model model,HttpServletRequest request){
		
		String nowDomian = request.getServerName();
		CPSUtil.xprint("nowDomian=" + nowDomian);
		if(!CPSUtil.isHomeDomain(nowDomian)){
			CPSUtil.xprint("非白名单域名访问跳转腾讯新闻！！！");
			model.addAttribute("target", "http://www.qq.com");
			return "/common/forward";
		}
		
		Dto dto = getParamAsDto();
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "/user/jsp/forget/user_forget";

	}
	
	/**
	 * 设置密码
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/setpwd")
	public String resetPasword(Model model,HttpServletRequest request){
		
		String nowDomian = request.getServerName();
		CPSUtil.xprint("nowDomian=" + nowDomian);
		if(!CPSUtil.isHomeDomain(nowDomian)){
			CPSUtil.xprint("非白名单域名访问跳转腾讯新闻！！！");
			model.addAttribute("target", "http://www.qq.com");
			return "/common/forward";
		}
		
		Dto dto = getParamAsDto();
		//缓存验证码
	    String stoken = (String)getSessionAttr(Const.MOBILE_CHECK_TOKEN);
	    
	    String token = dto.getAsString("token");
	    String phone = dto.getAsString("mobile");
	    
	    model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
	    if(CPSUtil.isNotEmpty(stoken)){
	    	model.addAttribute("token", token);
	    	model.addAttribute("account", phone);
	    	return "/user/jsp/forget/change_password";
	    }else{
	    	return "/user/jsp/login/user_login";
	    }

	}
	
	
	/**
	 * 用户登录逻辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value="ulogin" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void userLogin(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			//获取参数
			Dto dto = getParamAsDto();
			//获取用户session
			Subject currentUser = SecurityUtils.getSubject(); 
			Session session = currentUser.getSession();
			//获取验证码
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);	
			//解析参数
			String KEYDATA[] = dto.getAsString("KEYDATA").split(",ccnet,");
			if(null == KEYDATA || KEYDATA.length > 3){
				responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
    			return;
			}
			//提取登录参数
			String checkcode = null;
			String username = KEYDATA[0];//登录账号
			String password  = KEYDATA[1];//登录密码
			if(KEYDATA.length==3){
				checkcode = KEYDATA[2];//验证码
			}
			
			if(!StringHelper.checkParameter(username,password)){//判断参数为空
            	responseWriter(ResponseCode.responseMessage(ResponseCode.AccountPwdEmpty), response);
    			return;
			}
			  
			if(CPSUtil.isNotEmpty(checkcode) && !checkcode.equalsIgnoreCase(sessionCode)){//验证码错误
				responseWriter(ResponseCode.responseMessage(ResponseCode.CaptchaError), response);
    			return;
			}
			
			//处理登录逻辑
			UsernamePasswordToken token = new UsernamePasswordToken(username, password.toUpperCase());
			token.setRememberMe(true);
			try {
				
				if(!currentUser.isAuthenticated()){
				   currentUser.login(token);
				}		
				//记录登录日志
				Integer userId = MemberInfoShiroUtil.getCurrentUser().getMemberId();
				String loginIP = IPUtil.getIpAddr(getRequest());//获取用户登录IP
				MemberLoginLog loginLog = new MemberLoginLog(userId,loginIP);
				loginLog.setRequestDetails(getRequest().getHeader("User-Agent"));
				memberLoginLogService.saveMemberLoginLog(loginLog);
				
				//将栏目信息放入session
				setSessionAttr(Const.SN_COLUMN_LIST, CPSUtil.getContextAtrribute(Const.CT_COLUMN_LIST));
				setSessionAttr(Const.SYS_PARAM, CPSUtil.getAllSysParam());
				
			} catch (UnknownAccountException uae) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.AccountPwdError), response);//用户名或密码有误
				return;
			} catch (IncorrectCredentialsException ice) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.LoginPwdError), response);//密码错误
				return;
			} catch (LockedAccountException lae) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.LoginAccInactive), response);//账号未激活
				return;
			} catch (ExcessiveAttemptsException eae) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.MaxLoginNum), response);//错误次数过多
				return;
			} catch (AuthenticationException ae) {
				responseWriter(ResponseCode.responseMessage(ResponseCode.AuthLoginError), response);//验证未通过
				return;
			}
			token.clear();
			//处理session中的参数
			session.setAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
			session.removeAttribute(Const.SESSION_SECURITY_CODE);//移除SESSION的验证
			session.setAttribute("siteLogo", CPSUtil.getSitePic("SITE_LOGO", 0, getRequest()));
			session.setAttribute("siteIco", CPSUtil.getSitePic("SITE_ICO", 1, getRequest()));
			//处理返回提示
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
			map.put(MessageKey.msg.name(),"登录系统成功");
			responseWriter(map, response);
			return;
		}catch (Exception e) {
			e.printStackTrace();
			responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);//验证未通过
			return;
		}
	}
	
	
	/**
	 * 用户注册逻辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value="uregister" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void userRegister(HttpServletRequest request,HttpServletResponse response) {
		try {
			//获取系统邀请奖励金额
			double visitAward = Double.parseDouble(CPSUtil.getParamValue(Const.CT_RECOM_REGISTER_REWARD));
			//注册默认基金
			double umoney = Double.valueOf(CPSUtil.getParamValue(Const.CT_MEMBER_REGISTER_MONEY));
			//获取用户session
			Subject currentUser = SecurityUtils.getSubject(); 
			Session session = currentUser.getSession();
			//获取参数
			Dto paramDto = getParamAsDto();
			String captcha = paramDto.getAsString("captcha");
			String scaptcha = paramDto.getAsString("smscode");
			String recomUser = paramDto.getAsString("recom_user");
			String loginAccount = paramDto.getAsString("loginAccount");
			String loginPassword = paramDto.getAsString("loginPassword");
			//是否邀请码注册
			String USE_VCODE_REG = CPSUtil.getParamValue("USE_VCODE_REG");
			//获取session中的验证码
			String sessionCode = (String)getSessionAttr(Const.SESSION_SECURITY_CODE);	
			//缓存验证码
			String smscode = (String)getSessionAttr(Const.MOBILE_CHECK_CODE);
			
			//判断参数为空
            if(!StringHelper.checkParameter(captcha,scaptcha,loginAccount,loginPassword)){
            	responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
    			return;
			}
            
            //验证码图形验证码
//			if(!captcha.equalsIgnoreCase(sessionCode)){
//				responseWriter(ResponseCode.responseMessage(ResponseCode.CaptchaError), response);
//    			return;
//			}
			
			//验证账号是否存在
			if(CPSUtil.isNotEmpty(memberInfoService.findFormatByLoginName(loginAccount.trim()))){
				responseWriter(ResponseCode.responseMessage(ResponseCode.AccountExistError), response);
    			return;
			}
			
			//手机号码验证
			if(!CPSUtil.isMobileNum(loginAccount)){
				responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
    			return;
			}
			
			//验证码短信验证码
      		String strs[] = smscode.split("\\#");
      		CPSUtil.xprint(strs);
      		if(CPSUtil.isEmpty(strs) || strs.length!=2){
      			responseWriter(ResponseCode.responseMessage(ResponseCode.SMSCodeError), response);
     		    return;
      		}
      		
      		String code = strs[0];
  			String num = strs[1];
      		//手机号码不一致
  			if(!num.equals(loginAccount)){
  				responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
    			return;
  			}
  			
  			//短信验证码错误
  			if(!code.equals(scaptcha)){
  				responseWriter(ResponseCode.responseMessage(ResponseCode.SMSCodeError), response);
    			return;
			}
      		//邀请人
  			MemberInfo recomMember=null;
  			if(CPSUtil.isNotEmpty(recomUser)){
  				recomMember=memberInfoService.findMemberInfoByVisitCode(recomUser);
  			}
  			
      	    //验证码邀请人
			if(CPSUtil.isNotEmpty(recomUser)&&CPSUtil.isEmpty(recomMember)){
				responseWriter(ResponseCode.responseMessage(ResponseCode.RecomCodeUnExistError), response);
    			return;
			}
			
			//处理保存逻辑
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setLoginAccount(loginAccount);
			memberInfo.setLoginPassword(loginPassword);
			memberInfo.setRecomCode(recomUser);//邀请人人邀请码
			memberInfo.setMobile(loginAccount);//手机号
			memberInfo.setMemberLevel(MemeberLevelType.REGULAR.getType());//默认普通会员
			if(memberInfoService.saveMemberInfo(memberInfo)==1){
				
				//处理注册默认基金
				SbMoneyCount moneyCount = new SbMoneyCount();
				//获取系统参数默认奖励金额
				if(CPSUtil.isEmpty(umoney)){
					umoney = 2.00d;//未设置默认2.0
				}
				if(CPSUtil.isEmpty(visitAward)){
					visitAward = 0.50d;//未设置默认0.5
				}
				CPSUtil.xprint("注册默认金额：" + umoney);
				moneyCount.setContentId(null);
				moneyCount.setCreateTime(new Date());
				moneyCount.setUmoney(umoney);
				moneyCount.setmType(AwardType.register.getAwardId());
				moneyCount.setUserId(memberInfo.getMemberId());
				sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
				
				//添加邀请人奖励
				if(CPSUtil.isNotEmpty(recomMember)){
					SbVisitMoney visitMoney=new SbVisitMoney();
					visitMoney.setCreateTime(new Date());
					visitMoney.setUserId(recomMember.getMemberId());
					visitMoney.setVcode(memberInfo.getVisitCode());
					visitMoney.setVmoney(visitAward);
					sbVisitMoneyService.saveVisitMoney(visitMoney);
				}
				
				//执行更新操作
				InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
				
				//清理掉验证码
				session.removeAttribute(Const.SESSION_SECURITY_CODE);
				//清理掉短信验证码
				session.removeAttribute(Const.MOBILE_CHECK_CODE);
				//处理返回提示
				Map<String, Object> map = new HashMap<String, Object>(0);
				map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
				map.put(MessageKey.msg.name(),"账户注册成功,请重新登录！");
				responseWriter(map, response);
				return;
			}else{
				responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);
		}
	}
	
	
	
	/**
	 * 忘记密码账号验证
	 * @param request
	 * @return
	 */
	@RequestMapping(value="forget/check" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void checkForget(HttpServletRequest request,HttpServletResponse response) {
		//获取用户session
		Subject currentUser = SecurityUtils.getSubject(); 
		Session session = currentUser.getSession();
		//获取参数
		Dto paramDto = getParamAsDto();
		String captcha = paramDto.getAsString("captcha");
		String scaptcha = paramDto.getAsString("smscode");
		String mobile = paramDto.getAsString("phone");
		//获取session中的验证码
		String sessionCode = (String)getSessionAttr(Const.SESSION_SECURITY_CODE);	
		//缓存验证码
		String smscode = (String)getSessionAttr(Const.MOBILE_CHECK_CODE);
		
		//判断参数为空
        if(!StringHelper.checkParameter(captcha,scaptcha,mobile)){
        	responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
			return;
		}
        
        //验证码图形验证码
		if(!captcha.equalsIgnoreCase(sessionCode)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.CaptchaError), response);
			return;
		}
		
		//验证账号是否存在
		if(CPSUtil.isEmpty(memberInfoService.findFormatByLoginName(mobile.trim()))){
			responseWriter(ResponseCode.responseMessage(ResponseCode.AccountUnExistError), response);
			return;
		}
		
		//手机号码验证
		if(!CPSUtil.isMobileNum(mobile)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
			return;
		}
		
		//验证码短信验证码
  		String strs[] = smscode.split("\\#");
  		CPSUtil.xprint(strs);
  		if(CPSUtil.isEmpty(strs) || strs.length!=2){
  			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
 		    return;
  		}
  		
  		String code = strs[0];
		String num = strs[1];
  		//手机号码不一致
		if(!num.equals(mobile)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
		    return;
		}
		
		//短信验证码错误
		if(!code.equals(scaptcha)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.SMSCodeError), response);
		    return;
		}
  		
		//验证码成功生成token
		String token = UuidUtil.get32UUID();
		setSessionAttr(Const.MOBILE_CHECK_TOKEN, mobile+"#"+token);
		
		//处理返回提示
		Map<String, Object> map = new HashMap<String, Object>(0);
		map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
		map.put(MessageKey.msg.name(),"短信验证成功！");
		map.put("token", token);
		responseWriter(map, response);
	}
	
	
	/**
	 * 重置密码保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value="setpwd/save" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void saveReSetPwd(HttpServletRequest request,HttpServletResponse response) {
		//获取用户session
		Subject currentUser = SecurityUtils.getSubject(); 
		Session session = currentUser.getSession();
		try {
			
			//获取参数
			Dto paramDto = getParamAsDto();
			String token = paramDto.getAsString("token");//验证token
			String mobile = paramDto.getAsString("mobile");//手机号码
			String password = paramDto.getAsString("password");//手机号码
			
			//缓存验证码
			String stoken = (String)getSessionAttr(Const.MOBILE_CHECK_TOKEN);
			//判断参数为空
	        if(!StringHelper.checkParameter(password,token,mobile)){
	        	responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
				return;
			}
	        
			//验证账号是否存在
			if(CPSUtil.isEmpty(memberInfoService.findFormatByLoginName(mobile.trim()))){
				responseWriter(ResponseCode.responseMessage(ResponseCode.AccountUnExistError), response);
				return;
			}
			
			//手机号码验证
			if(!CPSUtil.isMobileNum(mobile)){
				responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
				return;
			}
			
			//验证token
	  		String strs[] = stoken.split("\\#");
	  		CPSUtil.xprint(strs);
	  		if(CPSUtil.isEmpty(strs) || strs.length!=2){
	  			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
	 		    return;
	  		}
	  		
	  		String ptoken = strs[1];
			String num = strs[0];
	  		//手机号码不一致
			if(!num.equals(mobile)){
				responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
			    return;
			}
			
			//短信验证码错误
			if(!ptoken.equals(token)){
				responseWriter(ResponseCode.responseMessage(ResponseCode.AuthTokenError), response);
			    return;
			}
	  		
			//验证没有问题即可修改密码
			MemberInfo memberInfo = memberInfoService.findFormatByLoginName(mobile.trim());
			String salt = memberInfo.getSalt();
			memberInfo.setLoginPassword(CipherUtil.createPwdEncrypt(mobile,password,salt));	
			if(memberInfoService.editMemberInfo(memberInfo)){
				//清理掉验证码
				session.removeAttribute(Const.SESSION_SECURITY_CODE);
				//清理掉token
				session.removeAttribute(Const.MOBILE_CHECK_TOKEN);
				//处理返回提示
				Map<String, Object> map = new HashMap<String, Object>(0);
				map.put(MessageKey.apicode.name(),ResponseCode.CommSuccess.getCode());
				map.put(MessageKey.msg.name(),"密码修改成功,请重新登录！");
				responseWriter(map, response);
				return;
			}else{
				responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);
				return;
			}
			
		} catch (Exception e) {
			responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);
			e.printStackTrace();
		}finally{
			//执行更新操作
			InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
		}
		
	}
	
	
	/**
	 * 个人信息设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/setting")
	public String userSetting(Model model){
		Dto dto = getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		// 获取当前用户用户总收益
		SbUserMoney userMoney = new SbUserMoney();
		userMoney.setUserId(memberInfo.getMemberId());
		userMoney = sbUserMoneyService.find(userMoney);
		model.addAttribute("userMoney", userMoney);
		model.addAttribute("member", memberInfo);
		model.addAttribute(Const.MENU_SELECTED_INDEX, "home");
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "/user/jsp/setting/user_setting";

	}
	
	
	/**
	 * 保存个人设置
	 * @param model
	 * @return
	 */
	@RequestMapping(value="setting/save" ,produces="application/json;charset=UTF-8", method=RequestMethod.POST)
	public void saveSetting(HttpServletRequest request,HttpServletResponse response) {
		//获取参数
		Dto paramDto = getParamAsDto();
		MemberInfo memberInfo = getCurUser();
		String mobile = paramDto.getAsString("mobile");
		String qq_num = paramDto.getAsString("qq_num");
		String wechat = paramDto.getAsString("wechat");
		String pay_account = paramDto.getAsString("pay_account");
		String account_name = paramDto.getAsString("account_name");
		
		//验证参数
		if(!StringHelper.checkParameter(mobile,qq_num,wechat,pay_account,account_name)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.NoParamError), response);
			return;
		}
		
		//手机号码验证
		if(!CPSUtil.isMobileNum(mobile)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.InvalidPhoneError), response);
			return;
		}
		
		//验证账号是否存在
		if(CPSUtil.isNotEmpty(memberInfoService.findMemberByMobile(mobile.trim())) && !mobile.equals(memberInfo.getMobile())){
			responseWriter(ResponseCode.responseMessage(ResponseCode.PhoneNumberExistError), response);
			return;
		}
		
		//验证支付宝账号是否存在
		if(CPSUtil.isNotEmpty(memberInfoService.findMemberByPayAccount(pay_account.trim()))){
			responseWriter(ResponseCode.responseMessage(ResponseCode.PayAccountExistError), response);
			return;
		}
		
		memberInfo.setMobile(mobile);
		memberInfo.setWechat(wechat);
		memberInfo.setQqNum(qq_num);
		memberInfo.setPayAccount(pay_account);
		memberInfo.setAccountName(account_name);
		if(memberInfoService.editMemberInfo(memberInfo)){
			responseWriter(ResponseCode.responseMessage(ResponseCode.CommSuccess), response);
			return;
		}else{
			responseWriter(ResponseCode.responseMessage(ResponseCode.CommError), response);
			return;
		}
		
	}
	
	
	/**
     * 帐号注销
     * @return
     */
    @RequestMapping("/loginout")
    public String logout(HttpServletRequest request,HttpSession session) {
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser!=null){
        	currentUser.logout();
        }
        MemberInfo memeber=(MemberInfo)getSessionAttr(Const.MSESSION_USER);
		if (memeber != null) {
			session.removeAttribute(Const.MSESSION_USER);
			session.invalidate();
		}
        return "redirect:/user/login";
    }
}
