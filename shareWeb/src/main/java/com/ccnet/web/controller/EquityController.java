package com.ccnet.web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;

/**
 * 会员权益
 *
 * @author sujie
 *
 * @time 2019-11-3 上午11:13:09
 */

@Controller("equityController")
@RequestMapping("/equity/")
public class EquityController extends BaseController<Object>{

	@RequestMapping("index")
	public String equity(Model model){
		//获取当前会员信息
		MemberInfo memberInfo = getCurUser();
		model.addAttribute("member", memberInfo);
		model.addAttribute("memberLevel", MemeberLevelType.values());
		model.addAttribute(Const.MENU_SELECTED_INDEX, "home");
		return "/user/jsp/equity/equity";
	}
	
}
