package com.ccnet.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccnet.api.entity.SbCommonProblem;
import com.ccnet.api.service.SbCommonProblemService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ResultDTO;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/problem/")
public class ApiProblemController extends BaseController<T> {

	@Autowired
	SbCommonProblemService sbCommonProblemService;
	@RequestMapping(value = "problem", method = RequestMethod.GET)
	public ModelAndView problem(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/jsp/problem/problem");
		try {
			SbCommonProblem sbCommonProblem = new SbCommonProblem();
			sbCommonProblem.setType("新手问题");
			List<SbCommonProblem> newHand = sbCommonProblemService.findProblemList(sbCommonProblem);
			sbCommonProblem.setType("金币问题");
			List<SbCommonProblem> coin = sbCommonProblemService.findProblemList(sbCommonProblem);
			sbCommonProblem.setType("提现问题");
			List<SbCommonProblem> getMoney = sbCommonProblemService.findProblemList(sbCommonProblem);
			mav.addObject("newHand",newHand);
			mav.addObject("coin",coin);
			mav.addObject("getMoney",getMoney);
			mav.setViewName("user/jsp/problem/problem_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "consultSheetIndex", method = RequestMethod.GET)
	public String consultSheetIndex(Model model) {
		try {
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/jsp/problem/consultSheetIndex";
	}

	@RequestMapping(value = "consultSheetAdd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public String consultSheetAdd(Model model) {
		try {
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/jsp/problem/consultSheetIndex";
	}

	@RequestMapping(value = "customer", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> customer(Model model, String type) {
		String str = "";
		Map<String, String> map = new HashMap<String, String>();
		if (CPSUtil.isNotEmpty(type) && type.equals("1")) {
			str = CPSUtil.getParamValue("SUPPLY_MQQ_2");
		} else {
			str = CPSUtil.getParamValue("SUPPLY_MQQ_3");
		}
		map.put("qq_key", str);
		return ResultDTO.OK(map);
	}
}
