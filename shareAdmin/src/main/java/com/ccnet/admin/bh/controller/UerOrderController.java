package com.ccnet.admin.bh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.entity.PayUserOrder;
import com.ccnet.admin.bh.entity.UserInvestMoney;
import com.ccnet.admin.bh.service.PayUserOrderService;
import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;

@Controller
@RequestMapping("/backstage/userOrder/")
public class UerOrderController extends AdminBaseController<PayUserOrder> {

	@Autowired
	PayUserOrderService payUserOrderService;

	/**
	 * 提现管理
	 */
	@RequestMapping("index")
	public ModelAndView memberIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<UserInvestMoney> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			UserInfo user = new UserInfo();
			user.setUserType(UserType.ADVERTISER.getType());
			user.setUserState(UserSateType.VALID.getType());
			mav.addObject("pm", page);
			mav.addObject("userId", paramDto.getAsInteger("user_id"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员管理");
			mav.setViewName("admincps/jsp/invest/invest_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
}
