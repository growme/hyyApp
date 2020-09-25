package com.ccnet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;

/**
 * 客服页面
 * @author jackieWang
 *
 */
@Controller("aboutController")
@RequestMapping("/about/")
public class AboutController extends BaseController<Object> {
	
	/**
	 * 关于我们
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String aboutIndex(Model model){
		MemberInfo userInfo = getCurUser();
		model.addAttribute(Const.SYS_PARAM, CPSUtil.getAllSysParam());
		return "user/jsp/about/about";
	}
	
	@RequestMapping("adv")
	public String adv(){
		return "user/jsp/setting/adv";
	}
}
