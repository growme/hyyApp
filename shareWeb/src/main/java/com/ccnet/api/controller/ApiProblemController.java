package com.ccnet.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ResultDTO;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.controller.BaseController;

@Controller
@RequestMapping("/app/problem/")
public class ApiProblemController extends BaseController<T> {

	@RequestMapping(value = "problem", method = RequestMethod.GET)
	public String problem(Model model) {
		try {

			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/jsp/problem/problem";
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
