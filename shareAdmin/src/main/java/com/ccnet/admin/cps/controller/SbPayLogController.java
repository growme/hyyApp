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
import com.ccnet.cps.entity.SbPayLog;
import com.ccnet.cps.service.SbPayLogService;

@Controller("SbPayLogController")
@RequestMapping("/backstage/pay/")
public class SbPayLogController extends AdminBaseController<SbPayLog> {
	@Autowired
	SbPayLogService sbPayLogService;
	
	/*
	 * 查询佣金支付日志
	 */
	@RequestMapping("index")
	public ModelAndView sbPayLogIndex(){
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbPayLog> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbPayLog sbPayLog=new SbPayLog();
			page = sbPayLogService.getSbPayLogByPage(sbPayLog, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "佣金支付日志");
			mav.setViewName("admincps/jsp/money/user_pay_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
}
