package com.ccnet.admin.jp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.core.common.CheckStateType;
import com.ccnet.core.common.StateType;
import com.ccnet.core.common.WeightType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.jpz.entity.JpWithdrawMoney;
import com.ccnet.jpz.service.JpWithdrawMoneyService;

@Controller
@RequestMapping("/backstage/withdrawMoney/")
public class JpWithdrawMoneyController extends BaseController<JpWithdrawMoney> {

	@Autowired
	private JpWithdrawMoneyService jpWithdrawMoneyService;

	/**
	 * 协议信息列表
	 */
	@RequestMapping("index")
	public ModelAndView WithdrawMoneyIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<JpWithdrawMoney> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			JpWithdrawMoney arg0 = new JpWithdrawMoney();
			Page<JpWithdrawMoney> arg1 = new Page<JpWithdrawMoney>();
			page = jpWithdrawMoneyService.findByPage(arg0, arg1);
			mav.addObject("pm", page);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "协议管理");
			mav.setViewName("adminjp/jsp/withdrawMoney/list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加协议信息
	 */
	@RequestMapping("goadd")
	public ModelAndView goAdd() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("weightMap", WeightType.getStateMap());
		mav.addObject("cstateMap", CheckStateType.values());
		mav.addObject("stateMap", StateType.getStateMap());
		mav.addObject("cstateMap", CheckStateType.values());
		mav.addObject(Const.CT_MENU_NAV, "添加协议");
		mav.setViewName("adminjp/jsp/withdrawMoney/info");
		return mav;
	}

	/**
	 * 修改协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEdit() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		Integer WithdrawMoneyId = paramDto.getAsInteger("ids");
		if (CPSUtil.isNotEmpty(WithdrawMoneyId)) {
			JpWithdrawMoney jpWithdrawMoney = new JpWithdrawMoney();
			jpWithdrawMoney = jpWithdrawMoneyService.getByID(WithdrawMoneyId);
			mav.addObject("jpWithdrawMoney", jpWithdrawMoney);
			mav.addObject("stateMap", StateType.getStateMap());
			mav.addObject("cstateMap", CheckStateType.values());
			mav.addObject(Const.CT_MENU_NAV, "编辑协议");
			mav.setViewName("adminjp/jsp/withdrawMoney/info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}

		return mav;
	}

	/**
	 * 保存协议
	 * 
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveContent(JpWithdrawMoney jpWithdrawMoney) {
		AjaxRes ar = getAjaxRes();
		try {
			int isOk = 0;
			if (CPSUtil.isNotEmpty(jpWithdrawMoney.getId())) {
				JpWithdrawMoney jpWithdrawMoney1 = jpWithdrawMoneyService.getByID(jpWithdrawMoney.getId());
				jpWithdrawMoney1.setState("1");
				jpWithdrawMoney1.setMoney(jpWithdrawMoney.getMoney());
				jpWithdrawMoney1.setType(jpWithdrawMoney.getType());
				jpWithdrawMoney1.setName(jpWithdrawMoney.getName());
				jpWithdrawMoney1.setDescs(jpWithdrawMoney.getDescs());
				isOk = jpWithdrawMoneyService.update(jpWithdrawMoney1, "id");
			} else {
				jpWithdrawMoney.setState("1");
				isOk = jpWithdrawMoneyService.insert(jpWithdrawMoney);
			}
			if (isOk != 0) {
				ar.setSucceedMsg(Const.SAVE_SUCCEED);
			} else {
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			ar.setFailMsg(Const.SAVE_FAIL);
		}
		return ar;
	}

	/**
	 * 查看
	 * 
	 * @return
	 */
	@RequestMapping(value = "goview")
	public ModelAndView goview() {
		ModelAndView mav = new ModelAndView();
		Dto paramDto = getParamAsDto();
		Integer id = paramDto.getAsInteger("ids");
		if (CPSUtil.isNotEmpty(id)) {
			JpWithdrawMoney WithdrawMoney = jpWithdrawMoneyService.getByID(id);
			mav.addObject("WithdrawMoney", WithdrawMoney);
		}
		mav.addObject(Const.CT_MENU_NAV, "查看协议");
		mav.setViewName("adminjp/jsp/withdrawMoney/view");
		return mav;
	}

	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes advertgoDel() {
		AjaxRes ar = getAjaxRes();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			String adId = paramDto.getAsString("id");
			if (CPSUtil.isNotEmpty(adId)) {
				List<JpWithdrawMoney> arg0 = new ArrayList<JpWithdrawMoney>();
				String[] sc = adId.split(",");
				for (int i = 0; i < sc.length; i++) {
					JpWithdrawMoney m = new JpWithdrawMoney();
					m.setId(Integer.valueOf(sc[i]));
					arg0.add(m);
				}
				if (jpWithdrawMoneyService.trashList(arg0)) {
					ar.setRes(Const.SUCCEED);
					ar.setSucceedMsg(Const.DEL_SUCCEED);
				} else {
					ar.setRes(Const.FAIL);
					ar.setFailMsg(Const.DEL_FAIL);
				}
			} else {
				ar.setRes(Const.FAIL);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
}
