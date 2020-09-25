package com.ccnet.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.service.NewsService;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbColumnInfo;
import com.ccnet.cps.service.SbColumnInfoService;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.jpz.entity.JpAdState;
import com.ccnet.jpz.service.JpAdStateService;
import com.ccnet.jpz.service.impl.JpAdStateServiceImpl;

/**
 * 视频相关api
 */
@Controller
@RequestMapping("/api/news/")
public class ApiNewsController extends BaseController<MemberInfo> {

	@Autowired
	SbColumnInfoService columnInfoService;

	@Autowired
	SbContentInfoService contentInfoService;

	@Autowired
	NewsService newsService;
	@Autowired
	JpAdStateService jpAdStateService;

	@RequestMapping(value = "newsList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> newsList() {
		try {
			Map<String, Integer> map = new HashMap<>();
			SbColumnInfo arg0 = new SbColumnInfo();
			arg0.setColumnType(0);
			List<SbColumnInfo> columnList = columnInfoService.findList(arg0);
			for (int i = 0; i < columnList.size(); i++) {
				if (StringUtils.isNotBlank(columnList.get(i).getCode())) {
					map.put(columnList.get(i).getColumnName() + "--" + columnList.get(i).getCode(), newsService
							.addSbContentInfoByCode(columnList.get(i).getCode(), columnList.get(i).getColumnId()));
				}
			}
			SbColumnInfo arg1 = new SbColumnInfo();
			arg1.setColumnType(1);
			List<SbColumnInfo> columnList1 = columnInfoService.findList(arg1);
			for (int i = 0; i < columnList1.size(); i++) {
				if (StringUtils.isNotBlank(columnList1.get(i).getCode())) {
					map.put(columnList1.get(i).getColumnName() + "--" + columnList1.get(i).getCode(),
							newsService.addSbContentInfoVideoByCode(columnList1.get(i).getCode(),
									columnList1.get(i).getColumnId()));
				}
			}
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "videoList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> videoList() {
		try {
			Map<String, Integer> map = new HashMap<>();
			SbColumnInfo arg0 = new SbColumnInfo();
			arg0.setColumnType(1);
			List<SbColumnInfo> columnList = columnInfoService.findList(arg0);
			for (int i = 0; i < columnList.size(); i++) {
				if (StringUtils.isNotBlank(columnList.get(i).getCode())) {
					map.put(columnList.get(i).getColumnName() + "--" + columnList.get(i).getCode(), newsService
							.addSbContentInfoVideoByCode(columnList.get(i).getCode(), columnList.get(i).getColumnId()));
				}
			}
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "adStateList", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> adStateList() {
		try {
			Map<String, Object> map = new HashMap<>();
			JpAdState arg0 = new JpAdState();
			List<JpAdState> columnList = jpAdStateService.findList(arg0);
			map.put("adStateType", CPSUtil.getParamValue("JP_AD_STATE"));
			map.put("list", columnList);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}
}
