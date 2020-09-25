package com.ccnet.admin.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.service.SbVisitMoneyService;

/**
 * 
 *会员邀请明细
 * @author sujie
 *
 * @time 2019-10-23 下午2:05:05
 */

@Controller("SbVisitMoneyController")
@RequestMapping("/backstage/money/visit/")
public class SbVisitMoneyController extends AdminBaseController<SbVisitMoney>{

	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	
	/*
	 * 查询当前会员邀请收入列表
	 */
	@RequestMapping("index")
	public ModelAndView visitMoneyIndex(){
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbVisitMoney> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbVisitMoney sbVisitMoney=new SbVisitMoney();
			page = sbVisitMoneyService.getVisitMoneyByPage(sbVisitMoney, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("memberName",paramDto.getAsString("memberName"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员邀请收益");
			mav.setViewName("admincps/jsp/money/visit_money_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
}
