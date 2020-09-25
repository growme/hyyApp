package com.ccnet.web.controller;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.controller.BaseController;

@Controller
public class IndexController extends BaseController<T> {

	/**
	 * 关于我们
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "index", "" })
	public String aboutIndex(Model model) {
		return "redirect:/home/index";
	}
}
