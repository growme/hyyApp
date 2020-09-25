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
import com.ccnet.cps.entity.TotalMoneyRank;
import com.ccnet.cps.service.TotalMoneyRankService;


/**
 * 收益总排行
 * @author JackieWang
 *
 */
@Controller
@RequestMapping("/backstage/trank/")
public class TotalRankingController extends BaseController<Object> {
	
	
	//静态地址
	public static String INDEX_URL = "/backstage/trank/index";
	public static String GO_ADD_URL = "/backstage/trank/goadd";
	public static String GO_EDIT_URL = "/backstage/trank/goedit";
	
	@Autowired
	private TotalMoneyRankService totalMoneyRankService;
	
	
	/**
	 * 用户管理首页
	 */
	@RequestMapping("index")
	public ModelAndView trankIndex() throws UnsupportedEncodingException{	
		ModelAndView mav = new ModelAndView();
		if(isAuthedReq(ResourceTypes.MENU)){	
			Dto paramDto = getParamAsDto();
			Page<TotalMoneyRank> page = newPage(paramDto);
			TotalMoneyRank rank = new TotalMoneyRank();
			page = totalMoneyRankService.findTotalMoneyRankByPage(rank, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("end_date",paramDto.getAsString("end_date"));
			mav.addObject("start_date",paramDto.getAsString("start_date"));
			mav.addObject("queryParam",paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));	
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV,"总收益排行");
			mav.setViewName("admincps/jsp/rank/total_rank_list");
		}else{
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
	
	
	

}
