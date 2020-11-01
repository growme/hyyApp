package com.ccnet.admin.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserSexSate;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.RoleInfo;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.RoleInfoService;
import com.ccnet.core.service.UserInfoService;

@Controller
@RequestMapping("/backstage/user/")
public class UserInfoController extends AdminBaseController<UserInfo>{

	//静态地址
	public static String INDEX_URL = "/backstage/user/index";
	public static String GO_ADD_URL = "/backstage/user/goadd";
	public static String GO_EDIT_URL = "/backstage/user/goedit";
	public static String RESET_PWD_URL = "/backstage/user/resetpwd";
	
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private CacheManager cacheManager;



	private Cache<String, AtomicInteger> getPasswordRetryCache() {
		if (cacheManager != null) {
			return cacheManager.getCache("passwordRetryCache");
		}
		return null;
	}
	
	/**
	 * 用户管理首页
	 */
	@RequestMapping("index")
	public ModelAndView userIndex() throws UnsupportedEncodingException{	

		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){	
			Dto paramDto = getParamAsDto();
			Page<UserInfo> page = newPage(paramDto);
			Cache<String, AtomicInteger> passwordRetryCache = getPasswordRetryCache();
			page = userInfoService.findUserByPage(new UserInfo(), page, paramDto);
			List<UserInfo> list = page.getResults();
			if (passwordRetryCache != null && CollectionUtils.isNotEmpty(list)) {
				for (UserInfo user : list) {
					AtomicInteger passwordCount = passwordRetryCache.get(user.getLoginAccount());
					if (passwordCount != null && passwordCount.get() > 5) {
						user.setPasswordLocked(true);
					}
				}
			}
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("state",paramDto.getAsString("state"));
			mav.addObject("user_type",paramDto.getAsString("user_type"));
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("tlist", UserType.all());
			mav.addObject("slist", CPSUtil.getCodeList("USER_STATE"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV,"用户管理");
			mav.setViewName("admin/jsp/user/user_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	/**
	 * 添加用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddUser() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC)){		
			mav.addObject("sxlist", UserSexSate.values());
			mav.addObject("tplist", UserType.all());
			mav.addObject("stlist", UserSateType.values());
			mav.addObject("thlist", CPSUtil.getCodeList("USER_THEME"));
			mav.addObject("rlist",roleInfoService.queryRoleList());
			mav.addObject("avlist",CPSUtil.getUserAvatarsList());
			mav.setViewName("admin/jsp/user/user_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	

	/**
	 * 修改用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditUser() {
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){		
			Dto paramDto = getParamAsDto();
			Integer optype = paramDto.getAsInteger("optype");
			Integer user_id = paramDto.getAsInteger("user_id");
			UserInfo userInfo = new UserInfo();
			if(CPSUtil.isNotEmpty(user_id)){
				userInfo = userInfoService.getUserByUserID(user_id);
			}
			mav.addObject("optype", optype);
			mav.addObject("userInfo", userInfo);
			mav.addObject("sxlist", UserSexSate.values());
			mav.addObject("tplist", UserType.all());
			mav.addObject("stlist", UserSateType.values());
			mav.addObject("thlist", CPSUtil.getCodeList("USER_THEME"));
			
			//获取所有角色
			List<RoleInfo> alList = roleInfoService.queryRoleList();
			List<RoleInfo> srList = roleInfoService.queryUserRoleList(user_id);
			mav.addObject("rlist",initSelectedCheckBox(alList, srList));
			mav.addObject("avlist",CPSUtil.getUserAvatarsList());
			mav.setViewName("admin/jsp/user/user_info");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 初始化选中角色
	 * @param alList
	 * @param srList
	 * @return
	 */
	private List<RoleInfo> initSelectedCheckBox(List<RoleInfo> alList,List<RoleInfo> srList){
		if(CPSUtil.listNotEmpty(alList) && CPSUtil.listNotEmpty(srList)){
			for (RoleInfo sr : alList) {
				for (RoleInfo sr2 : srList) {
					if(sr.getRoleId().equals(sr2.getRoleId())){
						sr.setUseState(true);
					}
				}
			}
		}
		return alList;
	}
	
	/**
	 * 切换样式
	 * @return
	 */
	@RequestMapping(value="updateSkin", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes updateUserSkin(){
		AjaxRes ar=getAjaxRes();
		try {
			Dto paramDto = getParamAsDto();
			UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
			if(CPSUtil.isNotEmpty(currentUser)){
				Integer user_id = currentUser.getUserId();
				String skin = paramDto.getAsString("skin");
				UserInfo userInfo = userInfoService.getUserByUserID(user_id);
				if(userInfo!=null){
					userInfo.setSkin(skin);
					if(userInfoService.editUserInfo(userInfo)){
						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
					}else{
						ar.setFailMsg(Const.UPDATE_FAIL);
					}
				}
			}else{
				ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
			}
		}catch (Exception e) {
			ar.setFailMsg(Const.UPDATE_FAIL);
			e.printStackTrace();
		}
		return ar;
	}
	
	
	/**
	 * 增加用户
	 * @param role
	 * @return
	 */
	@RequestMapping(value="saveuser", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveUser(UserInfo userInfo){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL))){
			try {
				boolean temp = false;
				String acc = userInfo.getLoginAccount();
				String pwd = userInfo.getLoginPassword();
				String salt = CipherUtil.createSalt();
				userInfo.setSalt(salt);
				userInfo.setRegisterTime(new Date());
				userInfo.setLoginPassword(CipherUtil.createPwdEncrypt(acc, pwd,salt));
				if(checkAccExist(userInfo)){
					ar.setFailMsg(Const.ACCOUNT_EXIST);
				}else{
					if(userInfoService.saveUserInfo(userInfo)){
						//保存用户角色关系
						UserInfo user = userInfoService.findFormatByLoginName(userInfo.getLoginAccount());
						temp = roleInfoService.saveUserinfoMappingRole(user.getUserId(), userInfo.getRoleId());
						if(temp){
							ar.setSucceedMsg(Const.SAVE_SUCCEED);
						}else{
							ar.setFailMsg(Const.SAVE_FAIL);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}
	
	/**
	 * 更新用户
	 * @param o
	 * @return
	 */
	@RequestMapping(value="edituser", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes editUser(UserInfo userInfo){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL))){
			try {
				boolean temp = false;
				if(checkAccExist(userInfo)){
					ar.setFailMsg(Const.ACCOUNT_EXIST);
				}else{
					UserInfo oldUser = userInfoService.getUserByUserID(userInfo.getUserId());
					if(CPSUtil.isNotEmpty(oldUser)){ 
						userInfo.setUpdateTime(new Date());
						userInfo.setSalt(oldUser.getSalt());
						userInfo.setRegisterTime(oldUser.getRegisterTime());
						userInfo.setLoginPassword(oldUser.getLoginPassword());
						if(userInfoService.editUserInfo(userInfo)){
							//保存用户角色关系
							temp = roleInfoService.saveUserinfoMappingRole(oldUser.getUserId(), userInfo.getRoleId());
							if(temp){
								ar.setSucceedMsg(Const.UPDATE_SUCCEED);
							}else{
								ar.setFailMsg(Const.UPDATE_FAIL);
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 检查账号
	 * @param userInfo
	 * @return
	 */
   public boolean checkAccExist(UserInfo userInfo ) {
		boolean temp = false;
		try {
			//验证码账号是否存在
			if(CPSUtil.isNotEmpty(userInfo.getLoginAccount())){
				UserInfo user = userInfoService.findFormatByLoginName(userInfo.getLoginAccount());
				if(user!=null){
					if(CPSUtil.isNotEmpty(userInfo.getUserId())){
						if(userInfo.getUserId().equals(user.getUserId())){
							temp = false;
					    }else{
					    	temp = true;
					    }
					}else{
						return true;
					}
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	/**
	 * 检查账号是否存在
	 * @param login_account
	 * @param user_id
	 * @return
	 */
	@RequestMapping(value="checkuser", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes checkUser(){
		AjaxRes ar=getAjaxRes();
		boolean goaddPermit = isAuthedReq(ResourceTypes.FUNC,GO_ADD_URL);
		boolean goeditPermit = isAuthedReq(ResourceTypes.BUTTON,GO_EDIT_URL);
		if(ar.setNoAuth(goaddPermit || goeditPermit)){
			try {
				Dto paramDto = getParamAsDto();
				Integer user_id = paramDto.getAsInteger("user_id");
				String login_account = paramDto.getAsString("login_acc");
				if(CPSUtil.isNotEmpty(login_account)){
					UserInfo userInfo = userInfoService.findFormatByLoginName(login_account);
					if(userInfo!=null){
						if(CPSUtil.isNotEmpty(user_id)){//修改账号
							if(userInfo.getUserId().equals(user_id)){
								ar.setSucceedMsg(Const.ACCOUNT_EXIST);
						    }
						}else{
							ar.setFailMsg(Const.ACCOUNT_EXIST);
						}
			        }
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.COMMON_ERROR);
			}
		}
		return ar;
	}

	/**
	 * 冻结/激活 用户
	 * @param user_id
	 * @param user_state
	 * @return
	 */
	@RequestMapping(value="freeze", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes freezeUser(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON)||isAuthedReq(ResourceTypes.FUNC))){
			try {
				Dto paramDto = getParamAsDto();
				Integer user_id = paramDto.getAsInteger("user_id");
				Integer user_state = paramDto.getAsInteger("user_state");
				UserInfo userInfo = userInfoService.getUserByUserID(user_id);
				if(userInfo!=null){
					userInfo.setUserState(user_state);
					if(userInfoService.editUserInfo(userInfo)){
						ar.setSucceedMsg(Const.UPDATE_SUCCEED);
					}else{
						ar.setFailMsg(Const.UPDATE_FAIL);
					}
				}
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}
	
	@RequestMapping(value="unlocked", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes unPasswordLocked(){
		AjaxRes ar=getAjaxRes();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON)||isAuthedReq(ResourceTypes.FUNC))){
			try {
				Dto paramDto = getParamAsDto();
				Integer userId = paramDto.getAsInteger("user_id");
				UserInfo userInfo = userInfoService.getUserByUserID(userId);
				if (userInfo!= null) {
					Cache<String, AtomicInteger> passwordRetryCache = getPasswordRetryCache();
					if (passwordRetryCache != null) {
						AtomicInteger passwordCount = passwordRetryCache.get(userInfo.getLoginAccount());
						if (passwordCount != null) {
							passwordCount.set(0);
						}
					}
				}
				ar.setSucceedMsg(Const.OPERATE_SUCCEED);
			} catch (Exception e) {
				logger.error(e.toString(),e);
				ar.setFailMsg(Const.OPERATE_FAIL);
			}
		}
		return ar;
	}
	
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping(value = "resetpwd", method = RequestMethod.GET)
	public ModelAndView resetUserPwd() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)){
			String user_id = paramDto.getAsString("user_id");
			mav.addObject("userId", user_id);
			mav.setViewName("admin/jsp/user/reset_user_pwd");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping(value="savepwd", method=RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveUserPwd(){
		AjaxRes ar=getAjaxRes();
		Dto paramDto = getParamAsDto();
		if(ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON,RESET_PWD_URL)||isAuthedReq(ResourceTypes.FUNC,RESET_PWD_URL))){
			try {
				String user_id = paramDto.getAsString("userId");
				String password = paramDto.getAsString("loginPassword");
				UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
				Integer userType = currentUser.getUserType();
				if(userType.equals(UserType.SYSTEMADMIN.getType())){//系统管理员才有权限重置密码
					if(CPSUtil.isNotEmpty(user_id)){
						if(userInfoService.resetPassword(user_id,password)){
							ar.setSucceedMsg(Const.UPDATE_SUCCEED);
						}else{
							ar.setFailMsg(Const.UPDATE_FAIL);
						}
					}else{
						ar.setFailMsg(Const.NO_PARAM_ERROR);
					}
				}else{
					ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
				}
			}catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}else{
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG); 
		}
		return ar;
	}
	
}
