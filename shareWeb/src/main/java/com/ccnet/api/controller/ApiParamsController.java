package com.ccnet.api.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.service.ApiLoginService;
import com.ccnet.api.service.PayUserOrderService;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringUtils;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.sms.IPLocationUtil;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.service.SystemParamService;
import com.ccnet.cps.entity.PsFeedback;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.service.PsFeedbackService;
import com.ccnet.cps.service.SbMoneyCountService;

/**
 * 参数
 */
@Controller
@RequestMapping("/api/param/")
public class ApiParamsController extends BaseController<SystemParams> {

	@Autowired
	SystemParamService systemParamService;
	@Autowired
	PayUserOrderService payUserOrderService;
	@Autowired
	ApiLoginService apiLoginService;
	@Autowired
	PsFeedbackService psFeedbackService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;

	@RequestMapping(value = "index", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> fetchVerifyCodeFor(String keyx) {
		try {
			SystemParams params = systemParamService.findSystemParamByKey(keyx);
			return ResultDTO.OK(params);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	@RequestMapping(value = "hUserInfoService")
	@ResponseBody
	public ResultDTO<?> updateCache(HttpServletRequest request, String key) {
		try {
			CPSUtil.xprint(CPSUtil.getParamValue("REPEAT_DISTRICT"));
			List<SystemParams> paramList = systemParamService.queryParamList();
			if (!CPSUtil.checkListBlank(paramList)) {
				BaseDto paramDto = new BaseDto();
				for (SystemParams sp : paramList) {
					if (sp.getParamKey().equals("REPEAT_DISTRICT")) {
						sp.getParamValue();
					}
					paramDto.put(sp.getParamKey(), sp);
				}
				CPSUtil.setContextAtrribute(Const.CT_PARAM_LIST, paramDto);
				CPSUtil.xprint("=======共加载【" + paramDto.size() + "】条全局参数！=======");
				CPSUtil.xprint(CPSUtil.getParamValue("REPEAT_DISTRICT"));
				return ResultDTO.OK();
			} else {
				CPSUtil.xprint("=====<<<<<<<<<<<<当前系统还没有任何全局参数！>>>>>>>>>>>=======");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDTO.OK();
	}

	@RequestMapping(value = "saveMoney")
	@ResponseBody
	public ResultDTO<?> saveMoney() {
		try {
			String code = "5kkz85";
			return ResultDTO.OK(code);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	@RequestMapping(value = "versionCode", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> versionCode(String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (org.apache.commons.lang3.StringUtils.isBlank(type)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (type.equals("android")) {
				SystemParams params = systemParamService.findSystemParamByKey("REPEAT_ANDROID_VERSION");
				String[] st = params.getParamValue().split(";");
				map.put("version", st[0]);
				map.put("url", st[1]);
				map.put("type", st[2]);
				map.put("msg", st[3]);
				return ResultDTO.OK(map);
			}
			if (type.equals("ios")) {
				SystemParams params = systemParamService.findSystemParamByKey("REPEAT_IOS_VERSION");
				String[] st = params.getParamValue().split(",");
				map.put("version", st[0]);
				map.put("url", st[1]);
				return ResultDTO.OK(map);
			}
			return ResultDTO.ERROR(BasicCode.参数错误);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	@RequestMapping(value = "dict")
	@ResponseBody
	public ResultDTO<?> dict() {
		try {
			String ip = "118.247.16.126";
			ip = org.apache.commons.lang.StringUtils.trimToEmpty(ip);
			String location = IPLocationUtil.getIpLocation(ip);
			System.out.println(location);
			return ResultDTO.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	@RequestMapping(value = "tz")
	public String tz(String type, String url) {
		try {
			if (type.equals("zf")) {
				if (url.contains("http")) {
					return "redirect:" + url;
				} else {
					return "redirect:http://" + url;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http://www.baidu.com";
	}

	@RequestMapping(value = "addFeedback")
	@ResponseBody
	public ResultDTO<?> psFeedback(Headers header, PsFeedback arg0) {
		try {
			if (StringUtils.isBlank(arg0.getContent())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (StringUtils.isNotBlank(header.getUserid())) {
				arg0.setMemberId(Integer.valueOf(header.getUserid()));
			}
			arg0.setCreateTime(new Date());
			arg0.setStatus("0");
			if (psFeedbackService.insert(arg0) == 1) {
				return ResultDTO.OK();
			} else {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "listFeedback")
	@ResponseBody
	public ResultDTO<?> listFeedback(Headers header, PsFeedback arg0) {
		try {
			Dto dto = getParamAsDto();
			Page<PsFeedback> arg1 = newPage(dto);
			if (dto.getAsInteger("page") != null)
				arg1.setPageNum(dto.getAsInteger("page"));
			if (StringUtils.isNotBlank(header.getUserid())) {
				arg0.setMemberId(Integer.valueOf(header.getUserid()));
			}
			Page<PsFeedback> list = psFeedbackService.findByPage(arg0, arg1);
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "customerService")
	@ResponseBody
	public ResultDTO<?> feedback(Headers header) {
		try {
			Map<String, Object> map = new HashMap<>();
			SystemParams SUPPLY_WX = systemParamService.findSystemParamByKey("SUPPLY_WX");
			map.put("SUPPLY_WX", SUPPLY_WX);
			SystemParams SUPPLY_PHONE = systemParamService.findSystemParamByKey("SUPPLY_PHONE");
			map.put("SUPPLY_PHONE", SUPPLY_PHONE);
			SystemParams SUPPLY_QQ = systemParamService.findSystemParamByKey("SUPPLY_QQ");
			map.put("SUPPLY_QQ", SUPPLY_QQ);
			SystemParams SUPPLY_MQQ = systemParamService.findSystemParamByKey("SUPPLY_MQQ");
			map.put("SUPPLY_MQQ", SUPPLY_MQQ);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "registerBonus")
	@ResponseBody
	public ResultDTO<?> registerBonus(Headers header) {
		try {

			Map<String, Object> map = new HashMap<>();
			if (StringUtils.isNotBlank(header.getUserid())) {
				SbMoneyCount arg0 = new SbMoneyCount();
				arg0.setUserId(Integer.valueOf(header.getUserid()));
				arg0.setmType(AwardType.register.getAwardId());
				int i = sbMoneyCountService.findList(arg0).size();
				map.put("isRegister", i);
			}
			SystemParams SUPPLY_WX = systemParamService.findSystemParamByKey("CT_MEMBER_REGISTER_MONEY");
			map.put("SUPPLY_WX", SUPPLY_WX);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "registerBonusUser")
	@ResponseBody
	public ResultDTO<?> registerBonusUser(Headers header) {
		try {
			Map<String, Object> map = new HashMap<>();
			Integer userId = Integer.valueOf(header.getUserid());
			SbMoneyCount arg0 = new SbMoneyCount();
			arg0.setUserId(userId);
			arg0.setmType(AwardType.register.getAwardId());
			if (sbMoneyCountService.findList(arg0).size() == 0) {
				SystemParams SUPPLY_WX = systemParamService.findSystemParamByKey("CT_MEMBER_REGISTER_MONEY");
				// 注册默认基金
				double umoney = Double.valueOf(SUPPLY_WX.getParamValue());
				// 处理注册默认基金
				SbMoneyCount moneyCount = new SbMoneyCount();
				// 获取系统参数默认奖励金额
				if (CPSUtil.isEmpty(umoney)) {
					umoney = 1.00d;// 未设置默认1.0
				}
				CPSUtil.xprint("注册默认金额：" + umoney);
				moneyCount.setContentId(null);
				moneyCount.setCreateTime(new Date());
				moneyCount.setUmoney(umoney);
				moneyCount.setUserId(userId);
				moneyCount.setmType(AwardType.register.getAwardId());
				sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
				map.put("umoney", umoney);
			} else {
				return ResultDTO.ERROR(AppResultCode.已获得新人奖励);
			}
			return ResultDTO.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

}
