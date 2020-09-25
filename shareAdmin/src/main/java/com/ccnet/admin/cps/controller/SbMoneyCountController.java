package com.ccnet.admin.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.service.SbMoneyCountService;

/**
 * 
 * 会员收益明细列表
 * @author sujie
 *
 * @time 2019-10-23 下午1:57:56
 */

@Controller("sbMoneyCountController")
@RequestMapping("/backstage/money/count/")
public class SbMoneyCountController extends BaseController<SbMoneyCount>{

	@Autowired
	SbMoneyCountService sbMoneyCountService;
	
	/*
	 * 查询当前会员收入明细列表
	 * 
	 */
	@RequestMapping("index")
	public ModelAndView moneyCountIndex(){
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbMoneyCount> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbMoneyCount sbMoneyCount=new SbMoneyCount();
			page = sbMoneyCountService.getMoneyCountByPage(sbMoneyCount, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("awardTypes",AwardType.getAwardType());
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("memberName",paramDto.getAsString("memberName"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员收益明细");
			mav.setViewName("admincps/jsp/money/money_count_list");
		}else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
}
