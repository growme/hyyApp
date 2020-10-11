package com.ccnet.admin.cps.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ccnet.core.common.*;
import com.ccnet.cps.entity.*;
import com.ccnet.cps.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.bh.utils.wxUtils.WeiXinPayUtils;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;

/**
 * 用户提现记录
 * 
 * @author JackieWang
 *
 */
@Controller("SbCashLogController")
@RequestMapping("/backstage/cash/")
public class SbCashLogController extends BaseController<SbCashLog> {

	// 静态路径
	public static String GO_PAY_URL = "/backstage/cash/gopay";
	public static String GO_CHECK_URL = "/backstage/cash/check";

	@Autowired
	SbCashLogService sbCashLogService;
	@Autowired
	SbPayLogService sbPayLogService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbContentVisitLogService sbVisitLogService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;

	/*
	 * 提现记录
	 */
	@RequestMapping("index")
	public ModelAndView cashIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<SbCashLog> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			SbCashLog cashLog = new SbCashLog();
			page = sbCashLogService.findSbCashLogByPage(cashLog, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("stateList", PayState.values());
			mav.addObject("state", paramDto.getAsString("state"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员提现管理");
			mav.setViewName("admincps/jsp/money/user_cash_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/*
	 * 审核提现
	 */
	@RequestMapping("check")
	public ModelAndView checkCash() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC)) {
			Integer cash_id = paramDto.getAsInteger("cash_id");
			if (CPSUtil.isNotEmpty(cash_id)) {
				// 获取提现信息
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cash_id, paramDto);
				mav.addObject("stateList", PayState.allState());
				mav.addObject("cashLog", cashLog);
				mav.addObject(Const.CT_MENU_NAV, "会员提现审核");
				mav.setViewName("admincps/jsp/money/check_user_cash");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/*
	 * 提现支付
	 */
	@RequestMapping("gopay")
	public ModelAndView payCash() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC)) {
			Integer cash_id = paramDto.getAsInteger("cash_id");
			if (CPSUtil.isNotEmpty(cash_id)) {
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cash_id, paramDto);
				mav.addObject("cashLog", cashLog);
				mav.addObject(Const.CT_MENU_NAV, "提现支付");
				mav.setViewName("admincps/jsp/money/pay_user_cash");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 提交审核
	 * 
	 * @return
	 */
	@RequestMapping(value = "check/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveCheckCash() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_CHECK_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				Integer cashId = paramDto.getAsInteger("ucId");
				Integer state = paramDto.getAsInteger("state");
				String remark = paramDto.getAsString("remark");
				if (StringHelper.checkParameter(cashId, state, remark)) {
					// 处理数据存储
					if (sbCashLogService.updateUserCashState(cashId, state, remark)) {
						if (state.toString().equals(PayState.drawback.getPayStateId().toString())) {
							SbCashLog cashLog = sbCashLogService.findSbCashLogById(cashId, paramDto);
							SbMoneyCount moneyCount = new SbMoneyCount();
							moneyCount.setCreateTime(new Date());
							moneyCount.setUserId(cashLog.getUserId());
							moneyCount.setUmoney(cashLog.getCmoney());
							moneyCount.setmType(AwardType.darwback.getAwardId());
							moneyCount.setVindex(0);
							sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
							ar.setSucceedMsg(Const.SAVE_SUCCEED);
						} else {
							ar.setFailMsg(Const.DATA_UNEXIST);
						}
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					} else {
						ar.setFailMsg(Const.SAVE_FAIL);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 提交支付
	 * 
	 * @return
	 */
	@RequestMapping(value = "pay/save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes savePayCash() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_PAY_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				UserInfo userInfo = getCurrentUser();
				Integer cashId = paramDto.getAsInteger("cashId");
				Dto cashDto = new BaseDto();
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cashId, cashDto);

				// String payAccount = paramDto.getAsString("payAccount");
				// String accountName = paramDto.getAsString("accountName");
				// String serialCode = paramDto.getAsString("serialCode");
				// String payTime = paramDto.getAsString("payTime");
				String remark = paramDto.getAsString("remark");
				String payAccount = cashLog.getPayAccount();
				String accountName = cashLog.getAccountName();
				if (StringHelper.checkParameter(cashId, payAccount, accountName)) {
					// 记录不为空 且状态为审核通过
					if (CPSUtil.isNotEmpty(cashLog)
							&& cashLog.getState().equals(PayState.underReview.getPayStateId())) {
						// 处理数据存储
						SbPayLog payLog = new SbPayLog();
						payLog.setAccountName(accountName);
						payLog.setPayAccount(payAccount);
						payLog.setCreateTime(new Date());
						payLog.setPayTime(new Date());
						payLog.setPayMoney(cashLog.getCmoney());
						payLog.setOperater(userInfo.getLoginAccount());
						if (CPSUtil.isEmpty(remark)) {
							remark = "已给会员【" /*
												 * + cashLog.getMemberInfo().
												 * getLoginAccount() +
												 */ + "】支付【" + cashLog.getCmoney() + "】元佣金";
							payLog.setRemark(remark);
						} else {
							payLog.setRemark(remark);
						}
						payLog.setUcId(cashId);
						//todo 支付宝提现
						System.out.println("提现方式：{}，校验；{}"+cashLog.getPayType()+PayType.ebank.getPayId().equals(cashLog.getPayType()));
						/*if (PayType.ebank.getPayId().equals(cashLog.getPayType())){//微信
							Map<String, Object> map = WeiXinPayUtils.withdrawals(accountName, cashLog.getPayAccount(), "",
									cashLog.getCmoney().toString());
							if (map.get("code").equals("0")) {
								String str = "";
								Object obj = map.get("value");
								if (obj != null) {
									str = obj.toString();
								}
								if (str.length() > 9) {
									ar.setFailMsg(str.substring(str.indexOf("<![CDATA[") + 9, str.indexOf("]]>")));
								} else {
									ar.setFailMsg(Const.SAVE_FAIL);
								}
								return ar;
							}
							payLog.setAlipayCode(map.get("partner_trade_no").toString());
						}else{
							//支付宝

						}*/
						//用户余额减去提现金额
						SbUserMoney sbUserMoney = new SbUserMoney();
						sbUserMoney.setUserId(cashLog.getUserId());
						List<SbUserMoney> sbUserMoneyList = sbUserMoneyService.findSbUserMoneyList(sbUserMoney, new BaseDto());
						if (!CollectionUtils.isEmpty(sbUserMoneyList)){
							SbUserMoney userMoney = sbUserMoneyList.get(0);
							BigDecimal tmoney = new BigDecimal(userMoney.getTmoney()).subtract(new BigDecimal(cashLog.getCmoney()).multiply(new BigDecimal(10000)));
							BigDecimal totalMoney = new BigDecimal(userMoney.getProfitsMoney()).subtract(new BigDecimal(cashLog.getCmoney()).multiply(new BigDecimal(10000)));
							userMoney.setTmoney(tmoney.doubleValue());
							userMoney.setProfitsMoney(totalMoney.doubleValue());
							sbUserMoneyService.update(userMoney,"um_id");
						}
						payLog.setAlipayCode(cashLog.getUcId().toString());
						int flag = sbPayLogService.insert(payLog);
						if (flag > 0) {
							if (sbCashLogService.updateUserCashState(cashId, PayState.prepaid.getPayStateId(),
									"佣金支付成功")) {
								sbCashLogService.updateUserCashMoney(cashLog.getUserId(), cashLog.getCmoney());
								ar.setSucceedMsg(Const.SAVE_SUCCEED);
							} else {
								ar.setFailMsg(Const.SAVE_FAIL);
							}
						} else {
							ar.setFailMsg(Const.SAVE_FAIL);
						}

					} else {
						ar.setFailMsg(Const.DATA_UNEXIST);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	@RequestMapping(value = "drawback", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes drawback() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_PAY_URL))) {
			try {
				Dto paramDto = getParamAsDto();
				Integer cashId = paramDto.getAsInteger("cashId");
				Dto cashDto = new BaseDto();
				SbCashLog cashLog = sbCashLogService.findSbCashLogById(cashId, cashDto);
				String payAccount = cashLog.getPayAccount();
				String accountName = cashLog.getAccountName();
				if (StringHelper.checkParameter(cashId, payAccount, accountName)) {
					// 记录不为空 且状态为审核通过
					if (CPSUtil.isNotEmpty(cashLog) && cashLog.getState().equals(PayState.failured.getPayStateId())) {
						sbCashLogService.updateUserCashState(cashId, PayState.drawback.getPayStateId(), "佣金退还成功");
						SbMoneyCount moneyCount = new SbMoneyCount();
						moneyCount.setCreateTime(new Date());
						moneyCount.setUserId(cashLog.getUserId());
						moneyCount.setUmoney(cashLog.getCmoney());
						moneyCount.setmType(AwardType.darwback.getAwardId());
						moneyCount.setVindex(0);
						sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
						ar.setSucceedMsg(Const.SAVE_SUCCEED);
					} else {
						ar.setFailMsg(Const.DATA_UNEXIST);
					}

				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/*
	 * 收益明细
	 */
	@RequestMapping("goMoney")
	public ModelAndView goMoney() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC)) {
			Integer cash_id = paramDto.getAsInteger("cash_id");
			if (CPSUtil.isNotEmpty(cash_id)) {
				SbCashLog cash = sbCashLogService.findSbCashLogById(cash_id, paramDto);
				Page<SbMoneyCount> page = newPage(paramDto);
				SbMoneyCount sbMoneyCount = new SbMoneyCount();
				sbMoneyCount.setUserId(cash.getUserId());
				page = sbMoneyCountService.getMoneyCountByPage(sbMoneyCount, page, paramDto);
				mav.addObject("pm", page);
				mav.addObject("awardTypes", AwardType.getAwardType());
				mav.addObject("end_date", paramDto.getAsString("end_date"));
				mav.addObject("start_date", paramDto.getAsString("start_date"));
				mav.addObject("cash_id", cash_id);
				mav.addObject(Const.CT_MENU_NAV, "收益明细");
				mav.setViewName("admincps/jsp/money/pay_user_money");
			} else {
				mav.setViewName(Const.NO_AUTHORIZED_URL);
			}
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/*
	 * 收益明细
	 */
	@RequestMapping("goContentLog")
	public ModelAndView goContentLog() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Integer cash_id = paramDto.getAsInteger("cash_id");
		String userIP = paramDto.getAsString("userIP");
		if (CPSUtil.isNotEmpty(cash_id)) {
			SbCashLog cash = sbCashLogService.findSbCashLogById(cash_id, paramDto);
			Page<SbContentVisitLog> page = newPage(paramDto);
			SbContentVisitLog visitLog = new SbContentVisitLog();
			Integer accountState = paramDto.getAsInteger("accountState");
			if (CPSUtil.isNotEmpty(accountState)) {
				visitLog.setAccountState(accountState);
			}
			// if (CPSUtil.isNotEmpty(userIP)) {
			//
			// } else {
			visitLog.setUserId(cash.getUserId());
			visitLog.setAccountState(1);
			visitLog.setRequestIp(userIP);
			// }
			page = sbVisitLogService.findSbContentVisitLogByPage(visitLog, page, paramDto);// 分页查询
			mav.addObject("pm", page);
			mav.addObject("memberId", paramDto.getAsString("memberId"));
			mav.addObject("userIP", userIP);
			mav.addObject("accountState", accountState);
			mav.addObject("channelList", ShareType.values());
			mav.addObject("stlist", AccountState.values());
			mav.addObject("awardTypes", AwardType.getAwardType());
			mav.addObject("cash_id", cash_id);
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject(Const.CT_MENU_NAV, "收益明细");
			mav.setViewName("admincps/jsp/money/visit_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}
}
