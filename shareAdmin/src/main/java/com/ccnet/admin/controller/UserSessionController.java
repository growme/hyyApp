package com.ccnet.admin.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.taglib.Functions;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.entity.OnlineSession;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.core.service.UserInfoService;

/**
 * 用户session会话
 * 
 * @author jackie wang
 * 
 */
@Controller
@RequiresPermissions("session:*")
@RequestMapping("/backstage/session/")
public class UserSessionController extends AdminBaseController<Object> {

	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private UserInfoService userInfoService; 

	// 静态地址
	public static String INDEX_URL = "/backstage/session/list";

	@RequestMapping("list")
	public ModelAndView sessionList(Model model) {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.MENU)) {
			Dto paramDto = getParamAsDto();
			String loginAccount = null;
			OnlineSession onlineSession = null;
			Collection<Session> sessions = sessionDAO.getActiveSessions();
			List<OnlineSession> sessionList = new ArrayList<OnlineSession>();
			if(CPSUtil.isNotEmpty(sessions) && sessions.size()>0){
				for (Session session : sessions) {
					onlineSession = new OnlineSession();
					onlineSession.setSessionId(session.getId().toString());
					onlineSession.setLastAccessTime(session.getLastAccessTime());
					onlineSession.setLoginIp((String)session.getAttribute("LOGIN_IP"));
					onlineSession.setTimeout(session.getTimeout());
					onlineSession.setOnline(isForceLogout(session));
					loginAccount = Functions.principal(session);
					if(CPSUtil.isNotEmpty(loginAccount)){
					    UserInfo userInfo = userInfoService.findFormatByLoginName(loginAccount);
					    if(CPSUtil.isNotEmpty(userInfo)){
					    	onlineSession.setUserType(UserType.parseUserType(userInfo.getUserType()).getName());
					    }
					   onlineSession.setLoginAccount(Functions.principal(session));
					   sessionList.add(onlineSession);
					}
				}
			}
			
			mav.addObject("sessionList", sessionList);
			mav.addObject(Const.CT_MENU_NAV, "会话管理");
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.setViewName("admin/jsp/user/online_user_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 判断强制退出标识
	 * 
	 * @param session
	 * @return
	 */
	public static boolean isForceLogout(Session session) {
		return session.getAttribute(Const.SESSION_FORCE_LOGOUT_KEY) != null;
	}

	/**
	 * 强制退出用户
	 * @return
	 */
	@RequestMapping(value = "forceLogout", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes forceLogout() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.MENU, INDEX_URL))) {
			Dto paramDto = getParamAsDto();
			try {
				String sessionId = paramDto.getAsString("session_id");
				Subject currentUser = SecurityUtils.getSubject();
				if(CPSUtil.isNotEmpty(sessionId)){
					if(!sessionId.equals(currentUser.getSession().getId())){
						Session session = sessionDAO.readSession(sessionId);
						if (session != null){
							session.setTimeout(0);
							sessionDAO.delete(session);
							session.setAttribute(Const.SESSION_FORCE_LOGOUT_KEY,Boolean.TRUE);
							ar.setSucceedMsg(Const.FORCE_LOGOUT_SUCCEED);
						} else {
							ar.setFailMsg(Const.FORCE_LOGOUT_FAIL);
						}
				   }else{
					   ar.setFailMsg(Const.ACCOUNT_LOGOUT_ERROR);
				   }
				}else{
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.FORCE_LOGOUT_FAIL);
			}
		}
		return ar;
	}
}
