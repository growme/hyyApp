package com.ccnet.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;

/**
 * 发展下线
 * @author 1234
 *
 */
@Controller
@RequestMapping("/share/")
public class ShareController extends BaseController<Object> {
	
	/**
	 * 发展下线页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index")
	public String logIndex(Model model,HttpServletResponse res) {
		model.addAttribute("user_theme","blue");
		model.addAttribute(Const.MENU_SELECTED_INDEX, "vlink");
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		MemberInfo memberInfo=null;
		if (null != session) {
			Object obj = session.getAttribute(Const.MSESSION_USER);
			memberInfo=(MemberInfo)obj;
		}
		model.addAttribute("vcode",memberInfo.getVisitCode());
		return "/user/jsp/share/apprent";
	}
	
	
}
