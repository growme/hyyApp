package com.ccnet.admin.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.service.SbUserMoneyService;

/**
 * 
 *会员收益统计
 * @author sujie
 *
 * @time 2019-10-23 下午2:04:01
 */

@Controller("sbUserMoneyController")
@RequestMapping("/backstage/user/money/")
public class SbUserMoneyController extends BaseController<SbUserMoney>{

	@Autowired
	SbUserMoneyService sbUserMoneyService;
	
	/*
	 * 查询当前会员收入明细列表
	 */
	@RequestMapping("index")
	public ModelAndView userMoneyIndex(){
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbUserMoney> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbUserMoney sbUserMoney=new SbUserMoney();
			page = sbUserMoneyService.getUserMoneyByPage(sbUserMoney, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("memberName",paramDto.getAsString("memberName"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员收益统计");
			mav.setViewName("admincps/jsp/money/user_money_list");
		}else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
}
