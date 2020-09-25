package com.ccnet.api.controller;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ResultDTO;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.TodayMoneyRank;
import com.ccnet.cps.entity.TotalMoneyRank;
import com.ccnet.cps.service.TodayMoneyRankService;
import com.ccnet.cps.service.TotalMoneyRankService;

@Controller
@RequestMapping("/api/rank")
public class RankController extends BaseController<T> {

	@Autowired
	TodayMoneyRankService todayMoneyRankService;
	@Autowired
	TotalMoneyRankService totalMoneyRankService;

	@RequestMapping("/today")
	@ResponseBody
	public ResultDTO<?> today() {
		TodayMoneyRank arg0 = new TodayMoneyRank();
		List<TodayMoneyRank> list = todayMoneyRankService.findTodayMoneyRankList(arg0);
		return ResultDTO.OK(list);
	}

	@RequestMapping("/total")
	@ResponseBody
	public ResultDTO<?> total() {
		TotalMoneyRank arg0 = new TotalMoneyRank();
		List<TotalMoneyRank> list = totalMoneyRankService.findTotalMoneyRankList(arg0);
		return ResultDTO.OK(list);
	}
}
