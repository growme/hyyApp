package com.ccnet.admin.cps.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.entity.TodayMoneyRank;
import com.ccnet.cps.service.TodayMoneyRankService;


/**
 * 收益日排行
 * @author JackieWang
 *
 */
@Controller
@RequestMapping("/backstage/drank/")
public class TodayRankingController extends BaseController<Object> {
	
	
	//静态地址
	public static String INDEX_URL = "/backstage/drank/index";
	public static String GO_ADD_URL = "/backstage/drank/goadd";
	public static String GO_EDIT_URL = "/backstage/drank/goedit";
	
	@Autowired
	private TodayMoneyRankService todayMoneyRankService;
	
	
	/**
	 * 用户管理首页
	 */
	@RequestMapping("index")
	public ModelAndView trankIndex() throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){	
			Dto paramDto = getParamAsDto();
			Page<TodayMoneyRank> page = newPage(paramDto);
			TodayMoneyRank rank = new TodayMoneyRank();
			page = todayMoneyRankService.findTodayMoneyRankByPage(rank, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV,"日收益排行");
			mav.setViewName("admincps/jsp/rank/today_rank_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
}
