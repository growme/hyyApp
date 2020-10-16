package com.ccnet.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.admin.LicenseHelper;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.json.JsonHelper;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.LoginLog;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.LoginLogService;
import com.ccnet.cps.service.SbContentVisitLogService;

import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;


@Controller
public class LoginController extends BaseController<Object>{

	@Autowired
	private LoginLogService loginLogService;
	
	@Autowired
	private SbContentVisitLogService contentVisitLogService;
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/gologin")
	public String goLogin(Model model,HttpServletResponse response)throws Exception{
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "admin/jsp/home/gologin";
	}
	
	/**
	 * 账户锁定
	 * @return
	 */
	@RequestMapping(value="/lockacc")
	@ResponseBody
	public AjaxRes lockAccount(HttpSession session)throws Exception{
		AjaxRes ar=getAjaxRes();
		try {
			Dto dto = getParamAsDto();
			UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
			String dlock = dto.getAsString("dlock");
			System.out.println("dlock==>"+dlock);
			if(currentUser!=null){
				if(StringUtils.isNotBlank(dlock) && "1".equals(dlock)) {
					session.getServletContext().setAttribute(Const.SESSION_USER_LOCK+"_"+currentUser.getLoginAccount(), "1");
					ar.setSucceedMsg(Const.LOCK_SUCCEED);
				}else{
					ar.setFailMsg(Const.LOCK_FAILED);
				}
			}else{//会话超时
				ar.setFailMsg(Const.USER_SESSION_EXPIRED);
			}
		}catch (Exception e) {
			ar.setFailMsg(Const.LOCK_FAILED);
			e.printStackTrace();
		}
		return ar;
	}
	
	/**
	 * 验证锁定
	 * @return
	 */
	@RequestMapping(value="/cklock")
	@ResponseBody
	public AjaxRes checkLockScreen(HttpSession session)throws Exception{
		AjaxRes ar=getAjaxRes();
		try {
			Dto dto = getParamAsDto();
			UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
			String clock = dto.getAsString("clock");
			System.out.println("clock==>"+clock);
			if(currentUser!=null){
				if(StringUtils.isNotBlank(clock) && "1".equals(clock)) {
					String locked = (String)session.getServletContext().getAttribute(Const.SESSION_USER_LOCK+"_"+currentUser.getLoginAccount());
					if(CPSUtil.isNotEmpty(locked) && "1".equals(locked)){
						ar.setSucceedMsg("locked");
					}else{
					    ar.setFailMsg("unlocked");
					}
					
				}else{
					ar.setFailMsg(Const.LOCK_FAILED);
				}
			}else{//会话超时
				ar.setFailMsg(Const.USER_SESSION_EXPIRED);
			}
		}catch (Exception e) {
			ar.setFailMsg(Const.LOCK_FAILED);
			e.printStackTrace();
		}
		return ar;
	}
	
	
	/**
	 * 账户解除锁定
	 * @return
	 */
	@RequestMapping(value="/unlockacc")
	@ResponseBody
	public AjaxRes unLockAccount(String lockpwd,HttpSession session)throws Exception{
		AjaxRes ar=getAjaxRes();
		try {
			UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
			System.out.println("lockpwd==>"+lockpwd);
			if(currentUser!=null){
				if(StringUtils.isNotBlank(lockpwd)) {
					String loginSalt = currentUser.getSalt();
					String loginAccount = currentUser.getLoginAccount();
					String loginPassword = currentUser.getLoginPassword();
					String loginPwd =CipherUtil.createPwdEncrypt(loginAccount,lockpwd,loginSalt); 
					System.out.println("encryptPwd==>"+loginPwd);
					System.out.println("loginPassword==>"+loginPassword);
					//验证密码
					if(loginPwd.equals(loginPassword)){//验证通过
						session.getServletContext().removeAttribute(Const.SESSION_USER_LOCK+"_"+currentUser.getLoginAccount());
						ar.setSucceedMsg(Const.UNLOCK_SUCCEED);
					}else{
						ar.setFailMsg(Const.UNLOCK_FAILED);
					}
				}else{
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			}else{//会话超时
				ar.setFailMsg(Const.USER_SESSION_EXPIRED);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return ar;
	}
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping(value="/loginIndex")
	public String toLogin(Model model,HttpServletRequest req,HttpSession session)throws Exception{
		UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		session.setAttribute("siteLogo", CPSUtil.getSitePic("BACK_SITE_LOGO", 2,req));
		session.setAttribute("siteIco", CPSUtil.getSitePic("BACK_SITE_ICO", 3,req));
		
		/*String nowDomian = req.getServerName();
		CPSUtil.xprint("nowDomian=" + nowDomian);
		if(!CPSUtil.isHomeDomain(nowDomian)){
			CPSUtil.xprint("非白名单域名访问登录跳转腾讯新闻！！！");
			model.addAttribute("target", "http://news.qq.com");
			return "/common/forward";
		}*/
		
		if(CPSUtil.isEmpty(currentUser)){
			return "admin/jsp/home/admin_nlogin";
		}
		
		//先登陆成功，然后再检查认证
		if (!LicenseHelper.license()) {
			return "redirect:/goreglicense";
		}else{
			return "redirect:/backstage/index";
		}
	}
	
	@RequestMapping(value="/goreglicense")
	public String goRegisterLicense(Model model) {
		model.addAttribute("hardware", JsonHelper.encodeObject2Json(LicenseHelper.getLocalHardware()));
		return "admin/jsp/home/license";
	}
	
	@RequestMapping(value="/registerlicense" , method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes registerLicense(String license) {
		AjaxRes ar=getAjaxRes();
		try {
			System.out.println("license==>"+license);
			if (StringUtils.isNotBlank(license)) {
				Boolean auth = LicenseHelper.chkLicenseRemote(license);
				System.out.println("auth==>"+auth);
				if (auth != null && auth) {
					LicenseHelper.writeLicense(license);
					LicenseHelper.initLicense();
					ar.setSucceedMsg(Const.AUTH_SUCCEED);
				}else{
					ar.setFailMsg(Const.AUTH_FAILD);
				}
			}else{
				ar.setFailMsg(Const.AUTH_FAILD);
			}
		} catch (Exception e) {
			ar.setFailMsg(Const.AUTH_FAILD);
			e.printStackTrace();
		}
		
		return ar;
	}
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/adminLogin" ,produces="application/json;charset=UTF-8" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login()throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		Dto dto = getParamAsDto();
		String errInfo = "";
		String KEYDATA[] = dto.getAsString("KEYDATA").split(",ccnet,");
		if(null != KEYDATA && KEYDATA.length == 3){
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码		
			String code = KEYDATA[2];
			String username = KEYDATA[0];
			String password  = KEYDATA[1];	
			//记录登录日志
			String loginIP=IPUtil.getIpAddr(getRequest());//获取用户登录IP
			if(null == code || "".equals(code)){
				errInfo = "nullcode"; //验证码为空
			}else if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
				errInfo = "nullup";	//缺少用户名或密码
			}else if (validateLoginIp(loginIP)) {
				errInfo = "invalidip";
			}else{
				if(StringUtils.isNotEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){										
					// shiro加入身份验证
					UsernamePasswordToken token = new UsernamePasswordToken(username, password.toUpperCase());
					token.setRememberMe(true);
					try {
						if (!currentUser.isAuthenticated()) {
							currentUser.login(token);
						}		
						LoginLog loginLog=new LoginLog(UserInfoShiroUtil.getCurrentUser().getUserId(), loginIP);
						loginLog.setRequestDetails(getRequest().getHeader("User-Agent"));
						loginLogService.saveLoginLog(loginLog);
					} catch (UnknownAccountException uae) {
						errInfo = "usererror";// 用户名或密码有误
					} catch (IncorrectCredentialsException ice) {
						errInfo = "usererror"; // 密码错误
					} catch (LockedAccountException lae) {
						errInfo = "inactive";// 未激活
					} catch (ExcessiveAttemptsException eae) {
						errInfo = "attemptserror";// 错误次数过多
					} catch (AuthenticationException ae) {
						errInfo = "autherror";// 验证未通过
					}
					if(LicenseHelper.license()){
						// 验证是否登录成功
						if (currentUser.isAuthenticated()) {
							//成功
							UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
							Integer userId = userInfo.getUserId();
							session.setAttribute("skinList", CPSUtil.getCodeList("USER_THEME"));
							session.setAttribute(Const.LOGIN_IP, loginIP);
							session.setAttribute("demo_mode", CPSUtil.getContextAtrribute(Const.IS_DEMO_MOD));
							session.setAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
							session.setAttribute("ADMIN_VERSION", CPSUtil.getParamValue("ADMIN_VERSION"));
							loadCurrentUserResources(false); //登陆成功后，初始化加载资源权限
						} else {
							token.clear();
						}
					} else {
						errInfo = "license"; //许可超出使用范围
					}
				}else{
					errInfo = "codeerror";//验证码输入有误
				}
				if(StringUtils.isEmpty(errInfo)){
					errInfo = "success";//验证成功
					session.removeAttribute(Const.SESSION_SECURITY_CODE);//移除SESSION的验证
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}	
		map.put("result", errInfo);
		return map;
	}
	
	/**
	 * 
	 * @param ip
	 * @return true 禁止登陆。 false允许登陆
 	 */
	private boolean validateLoginIp(String ip) {
		boolean filter = false;
		//检查是否需要判断过滤
		String enable = CPSUtil.getParamValue("VALIDATE_LOGINIP_ENABLE");
		if (StringUtils.equals(enable, "1")) {
			filter = true;
			//判断ip
			String ips = CPSUtil.getParamValue("VALIDATE_LOGINIP_ADDRESS");
			if (StringUtils.contains(ips, ip)) {
				filter = false;
			}
		}
		return filter;
	}
	
	  /**
     * 帐号注销
     * @return
     */
    @RequestMapping("/systemLogout")
    public String logout(HttpServletRequest request,HttpSession session) {
    	session = request.getSession(true);
    	UserInfo userInfo = UserInfoShiroUtil.getCurrentUser();
    	session.getServletContext().removeAttribute(Const.SESSION_USER_LOCK+"_"+userInfo.getLoginAccount());
    	session.removeAttribute(Const.SESSION_USER);
    	session.removeAttribute(Const.SESSION_MENULIST);
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser!=null){
           currentUser.logout();
        }
        return "redirect:loginIndex.html";
    }
    
    
    @RequestMapping(value="/admin/license")
	public String license(Model model)throws Exception{
    	String license = getRequest().getParameter("license");
    	Boolean remote = null;
    	//远程认证
    	if (StringUtils.isNotBlank(license)) {
    		remote = LicenseHelper.chkLicenseRemote(license);
    	}
    	//如果远程认证成功，则写入文件
    	if (remote != null && remote) {
    		LicenseHelper.writeLicense(license);
    	}
    	return "redirect:/loginIndex";
	}
   
}
