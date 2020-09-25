package com.ccnet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.util.TokenUtil;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;

@Controller
@RequestMapping("/app/invited/")
public class AppInvitedController extends BaseController<Object> {

	@Autowired
	MemberInfoDao memberInfoDao;

	/**
	 * 邀请分享页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/.{token}")
	public String aboutIndex(Model model, @PathVariable("token") String token) {
		String userid = TokenUtil.getToken(token);
		Integer userId = Integer.valueOf(userid);
		MemberInfo user = memberInfoDao.getUserByUserID(userId);
		model.addAttribute("user", user);
		Page<MemberInfo> ylist = memberInfoDao.getyueshouyi();
		Page<MemberInfo> zlist = memberInfoDao.getzongshouyi();
		model.addAttribute("ylist", ylist);
		model.addAttribute("zlist", zlist);
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "user/jsp/invited/user_bohe";
	}

	/**
	 * 邀请分享页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/{token}.html")
	@ResponseBody
	public String aboutIndextoken(Model model, @PathVariable("token") String token) {
		String userid = TokenUtil.getValue(token);
		return userid;
	}
}
