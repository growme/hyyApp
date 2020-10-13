package com.ccnet.api.controller;

import com.ccnet.admin.bh.utils.wxUtils.WeiXinPayUtils;
import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.PayState;
import com.ccnet.core.common.PayType;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbPayLog;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;
import com.ccnet.cps.service.SbCashLogService;
import com.ccnet.cps.service.SbPayLogService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.jpz.entity.JpWithdrawMoney;
import com.ccnet.jpz.service.JpWithdrawMoneyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户提现
 * 
 * @author JackieWang
 * @time 2018-04-03 下午1:38:51
 */
@Controller
@RequestMapping("/api/draw/")
public class ApiDrawMoneyController extends BaseController<SbCashLog> {

	@Autowired
    SbCashLogService sbCashLogService;
	@Autowired
    SbUserMoneyService sbUserMoneyService;
	@Autowired
    MemberInfoDao memberInfoDao;
	@Autowired
    SbPayLogService sbPayLogService;

	@Autowired
    JpWithdrawMoneyService jpWithdrawMoneyService;

	// 提现列表
	@RequestMapping("list")
	@ResponseBody
	public ResultDTO<?> drawMoneyList(Headers header, Integer type) {
		try {
			// 获取签到数据
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Dto dto = getParamAsDto();
			if (type == null)
				type = 0;
			if (type == 1) {
				dto.put("staelist", "0,1");
			}
			if (type == 2) {
				dto.put("staelist", "2,3,4,5");
			}
			Page<SbCashLog> page = newPage(dto);
			SbCashLog cashLog = new SbCashLog();
			cashLog.setUserId(Integer.valueOf(header.getUserid()));
			page = sbCashLogService.findSbCashLogByPage(cashLog, page, dto);
			// 提现记录
			map.put("cashLogs", page);
			// 提现方式
			map.put("payTypes", PayType.getPayType());
			// 提现状态
			map.put("payStates", PayState.getPayState());
			return ResultDTO.OK(page);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}
	// 提现金额配置列表
	@RequestMapping("withdrawList")
	@ResponseBody
	public ResultDTO<?> withdrawList() {
		try {
			List<JpWithdrawMoney> list = jpWithdrawMoneyService.findList(new JpWithdrawMoney());
			Collections.sort(list, new Comparator<JpWithdrawMoney>() {
				@Override
				public int compare(JpWithdrawMoney o1, JpWithdrawMoney o2) {
					double money = o1.getMoney() - o2.getMoney();
					return new BigDecimal(money).intValue();
				}
			});
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 提现申请
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "save", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> doDrawByAli(Headers header) {
		// 获取参数
		try {
			Dto dto = getParamAsDto();
			// String payAccount = dto.getAsString("alipay");
			String accountName = dto.getAsString("accountName");
			String payAccount = dto.getAsString("payAccount");
			Integer payType = dto.getAsInteger("paytype");
			Integer withdrawType = dto.getAsInteger("withdrawType");
			if(CPSUtil.isEmpty(withdrawType)){
				withdrawType=0;
			}
			// String captcha = dto.getAsString("captcha");
			// String scaptcha = dto.getAsString("smscode");
			Double money = dto.getAsInteger("money").doubleValue();
			// 短信验证码
			// String smscode = (String)
			// getSessionAttr(Const.MOBILE_CHECK_CODE);

			Map<String, Object> map = new HashMap<String, Object>(0);

			if (CPSUtil.isEmpty(money)) {
				map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(), "提现金额不能为空!");
				return ResultDTO.OK(map);
			}

			// 当前用户
			// 获取签到数据
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			if (StringUtils.isEmpty(memberInfo.getPayAccount())){
				memberInfo.setPayAccount(payAccount);
				memberInfo.setAccountName(accountName);
				memberInfoDao.update(memberInfo,"member_id");
			}
			// 获取当前用户用户总收益
			SbUserMoney userMoney = new SbUserMoney();
			userMoney.setUserId(memberInfo.getMemberId());
			userMoney = sbUserMoneyService.find(userMoney);
			// 处理扣款
			UserDailyEntity userLock = UserCache.getInstance().getUserCache(memberInfo.getMemberId());
			synchronized (userLock) {
				if (null != userMoney) {
					// 验证微信单次提现最低1
					if (money < 1) {
						return ResultDTO.ERROR(AppResultCode.微信提现金额不能小于1元);
					}

					// 验证微信单次提现最高200
					if (PayType.ebank.getPayId().equals(payType) && money > 200) {
						return ResultDTO.ERROR(AppResultCode.微信提现金额不能超过200元);
					}

					if (userMoney.getTmoney() < money*10000) {
						return ResultDTO.ERROR(AppResultCode.余额不足);
					}
				} else {
					return ResultDTO.ERROR(AppResultCode.未查询到当前账户余额);
				}

				// 保存申请记录
				SbCashLog sbCashLog = new SbCashLog();
				if (payType.toString().equals("1")) {
					sbCashLog.setPayAccount(memberInfo.getWechat());
				} else {
					sbCashLog.setPayAccount(memberInfo.getPayAccount());
				}
				sbCashLog.setAccountName(accountName);
				sbCashLog.setCmoney(money);
				sbCashLog.setPayType(payType);
				sbCashLog.setState(PayState.submit.getPayStateId());
				sbCashLog.setUserId(memberInfo.getMemberId());
				sbCashLog.setCreateTime(new Date());
				sbCashLog.setUpdateTime(new Date());
				sbCashLog.setWithdrawType(0);
				if (sbCashLogService.insert(sbCashLog) > 0) {
					return ResultDTO.OK();
				} else {
					return ResultDTO.ERROR(AppResultCode.申请提现失败);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}

	/**
	 * 判断用户是不是首次提现
	 * 
	 * @param memberId
	 * @return
	 */
	private boolean checkIsFirstCash(Integer memberId) {
		boolean temp = false;
		if (CPSUtil.isNotEmpty(memberId)) {
			// 获取用户提现记录集合
			SbCashLog cashLog = new SbCashLog();
			cashLog.setUserId(memberId);
			List<SbCashLog> clist = sbCashLogService.findList(cashLog);
			if (CPSUtil.listEmpty(clist)) {
				temp = true;
			}
		}
		return temp;
	}

	/**
	 * 每日分享提现
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "fxsave", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> fxsave(Headers header) {
		// 获取参数
		try {
			Dto dto = getParamAsDto();
			String redisR = dto.getAsString("redisR");
			if (CPSUtil.isNotEmpty("redisR") && redisR.equals("1")) {
				JedisUtils.removeFingerCacheValue("C_fxsave:" + "_userId_" + header.getUserid());
			}
			String accountName = dto.getAsString("accountName");
			Integer payType = dto.getAsInteger("paytype");
			Double money = dto.getAsInteger("money").doubleValue();
			Map<String, Object> map = new HashMap<String, Object>(0);
			if (CPSUtil.isEmpty(money)) {
				map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(), "提现金额不能为空!");
				return ResultDTO.OK(map);
			}
			// 获取签到数据
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			// 获取当前用户用户总收益
			SbUserMoney userMoney = new SbUserMoney();
			userMoney.setUserId(memberInfo.getMemberId());
			userMoney = sbUserMoneyService.find(userMoney);
			String txNum = JedisUtils.get("C_fxsave:" + "_userId_" + header.getUserid());
			if (CPSUtil.isNotEmpty(txNum)) {
				return ResultDTO.ERROR(AppResultCode.微信圈提现每日只限一次);
			}
			// 处理扣款
			UserDailyEntity userLock = UserCache.getInstance().getUserCache(memberInfo.getMemberId());
			synchronized (userLock) {
				if (null != userMoney) {
					// 验证微信单次提现最低1
					if (PayType.ebank.getPayId().equals(payType) && money < 1) {
						return ResultDTO.ERROR(AppResultCode.微信提现金额不能小于1元);
					}
					// 验证微信单次提现最高200
					if (PayType.ebank.getPayId().equals(payType) && money > 200) {
						return ResultDTO.ERROR(AppResultCode.微信提现金额不能超过200元);
					}
					if (userMoney.getTmoney() < money) {
						return ResultDTO.ERROR(AppResultCode.余额不足);
					}
				} else {
					return ResultDTO.ERROR(AppResultCode.未查询到当前账户余额);
				}
				// 保存申请记录
				SbCashLog sbCashLog = new SbCashLog();
				Date date = new Date();
				if (payType.toString().equals("1")) {
					sbCashLog.setPayAccount(memberInfo.getWechat());
				} else {
					sbCashLog.setPayAccount(memberInfo.getPayAccount());
				}
				sbCashLog.setAccountName(accountName);
				sbCashLog.setCmoney(money);
				sbCashLog.setPayType(payType);
				sbCashLog.setState(PayState.submit.getPayStateId());
				sbCashLog.setUserId(memberInfo.getMemberId());
				sbCashLog.setCreateTime(date);
				sbCashLog.setUpdateTime(date);
				sbCashLog.setWithdrawType(1);
				if (sbCashLogService.insert(sbCashLog) > 0) {
					SbCashLog cashLog = sbCashLogService.find(sbCashLog);
					SbPayLog payLog = new SbPayLog();
					payLog.setAccountName(accountName);
					payLog.setPayAccount(cashLog.getPayAccount());
					payLog.setCreateTime(date);
					payLog.setPayTime(date);
					payLog.setPayMoney(cashLog.getCmoney());
					payLog.setOperater(memberInfo.getLoginAccount());
					String remark = "已给会员【"
							/*
							 * + cashLog.getMemberInfo(). getLoginAccount() +
							 */ + "】支付【" + cashLog.getCmoney() + "】元佣金";
					payLog.setRemark(remark);
					payLog.setUcId(sbCashLog.getUcId());
					Map<String, Object> mapx = WeiXinPayUtils.withdrawals(accountName, cashLog.getPayAccount(), "",
							cashLog.getCmoney().toString());
					if (mapx.get("code").equals("0")) {
						String str = "";
						Object obj = mapx.get("value");
						if (obj != null) {
							str = obj.toString();
						}
					}
					payLog.setAlipayCode(mapx.get("partner_trade_no").toString());
					int flag = sbPayLogService.insert(payLog);
					if (flag > 0) {
						if (sbCashLogService.updateUserCashState(cashLog.getUcId(), PayState.prepaid.getPayStateId(),
								"佣金支付成功")) {
							JedisUtils.set("C_fxsave:" + "_userId_" + header.getUserid(), "1",
									DateUtils.getEndDateNum());
							sbCashLogService.updateUserCashMoney(cashLog.getUserId(), cashLog.getCmoney());
						}
					}
					return ResultDTO.OK();
				} else {
					return ResultDTO.ERROR(AppResultCode.申请提现失败);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.系统繁忙);
		}
	}
}
