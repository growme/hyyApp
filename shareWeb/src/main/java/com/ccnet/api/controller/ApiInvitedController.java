package com.ccnet.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ccnet.api.entity.ApiMoneyCount;
import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.util.TokenUtil;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.PayState;
import com.ccnet.core.common.PayType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.core.controller.BaseController;
import com.ccnet.core.dao.base.Page;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbCashLog;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.UserDetailMoney;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.SbCashLogService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.service.ApiMoneyCountService;

/**
 * 徒弟相关
 */
@Controller
@RequestMapping("/api/invited/")
public class ApiInvitedController extends BaseController<SbMoneyCount> {

	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbCashLogService sbCashLogService;
	@Autowired
	SbUserMoneyService sbUserMoneyService;
	@Autowired
	MemberInfoService memberInfoService;
	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	ApiMoneyCountService apiMoneyCountService;
	private final String DOMAIN = GetPropertiesValue.getValue("ConfigURL.properties", "ziyuan.server");

	/**
	 * 我的徒弟
	 */
	@RequestMapping(value = "dlist", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> dlist(Headers header, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			MemberInfo info = new MemberInfo();
			info.setRecomCode(memberInfo.getVisitCode());
			// 获取徒弟数据
			List<MemberInfo> memberInfos = memberInfoDao.findList(info);
			return ResultDTO.OK(memberInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 我的徒孙
	 */
	@RequestMapping(value = "slist", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> slist(Headers header, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			// 获取徒弟数据
			List<MemberInfo> memberInfos = CPSUtil.getChildTSMemeberList(memberInfo.getMemberId());
			return ResultDTO.OK(memberInfos);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 实时收入
	 * 
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	public ResultDTO<?> earningsIndex(Headers header, @RequestParam(value="pageNum",defaultValue = "1") Integer pageNum,
									  @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			List<ApiMoneyCount> list = apiMoneyCountService.getUserMoneyByUserId(memberInfo.getMemberId(), pageNum,
					pageSize);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setTypeName(AwardType.getAwardType(list.get(i).getmType()).getAwardName());
				if (list.get(i).getContentPic() != null && list.get(i).getContentPic().indexOf("http") < 0) {
					list.get(i).setContentPic(DOMAIN + list.get(i).getContentPic());
				}
				if (list.get(i).getVindex() != null && list.get(i).getVindex() == 1) {
					list.get(i).setTypeName(AwardType.getAwardType(list.get(i).getmType()).getAwardName() + ":一级徒弟贡献");
				} else if (list.get(i).getVindex() != null && list.get(i).getVindex() == 2) {
					list.get(i).setTypeName(
							"" + AwardType.getAwardType(list.get(i).getmType()).getAwardName() + ":二级徒弟贡献");
				}
			}
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 其他收入
	 * 
	 * @return
	 */
	@RequestMapping("tearn")
	@ResponseBody
	public ResultDTO<?> invitedTearns(Headers header) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			SbMoneyCount sbMoneyCount = new SbMoneyCount();
			sbMoneyCount.setUserId(memberInfo.getMemberId());
			sbMoneyCount.setmType(2);
			List<SbMoneyCount> list = sbMoneyCountService.findList(sbMoneyCount);
			List<UserDetailMoney> mlist = new ArrayList<UserDetailMoney>();
			for (int i = 0; i < list.size(); i++) {
				UserDetailMoney money = new UserDetailMoney();
				money.setMoney(list.get(i).getUmoney());
				money.setMoneyType(list.get(i).getmType());
				money.setCreateTime(list.get(i).getCreateTime());
				if (list.get(i).getVindex() == 1) {
					money.setMoneyTypeName(AwardType.getAwardType(list.get(i).getmType()).getAwardName() + ":一级徒弟贡献");
				} else if (list.get(i).getVindex() == 2) {
					money.setMoneyTypeName(
							"" + AwardType.getAwardType(list.get(i).getmType()).getAwardName() + ":二级徒弟贡献");
				} else {
					money.setMoneyTypeName(AwardType.getAwardType(list.get(i).getmType()).getAwardName());
				}
				money.setUserId(list.get(i).getUserId());
				money.setMemberInfo(list.get(i).getMemberInfo());
				mlist.add(money);
			}
			return ResultDTO.OK(mlist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	// 支出列表
	@RequestMapping("list")
	@ResponseBody
	public ResultDTO<?> drawMoneyList(Headers header, Integer pageNum, Integer pageSize) {
		try {
			// 获取签到数据
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			Map<String, Object> map = new HashMap<String, Object>();
			Dto dto = getParamAsDto();
			Page<SbCashLog> page = newPage(dto);
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			SbCashLog cashLog = new SbCashLog();
			cashLog.setUserId(memberInfo.getMemberId());
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

	// 新闻列表
	@RequestMapping("newslist")
	@ResponseBody
	public ResultDTO<?> newslist(Headers header, Integer pageSize, Integer pageNum) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			List<ApiMoneyCount> list = apiMoneyCountService.getUserMoneyCountByUserIdAndType(memberInfo.getMemberId(),
					pageSize, pageNum);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setTypeName(AwardType.getAwardType(list.get(i).getmType()).getAwardName());
				if (list.get(i).getContentPic() != null && list.get(i).getContentPic().indexOf("http") < 0) {
					list.get(i).setContentPic(DOMAIN + list.get(i).getContentPic());
				}
			}
			return ResultDTO.OK(list);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 邀请分享页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/{token}")
	@ResponseBody
	public String aboutIndextoken(Model model, @PathVariable("token") String token) {
		String userid = TokenUtil.getValue(token);
		return userid;
	}

	/**
	 * 输入邀请码
	 * 
	 * @param recomUser
	 * @return
	 */
	@RequestMapping("/invitedCode")
	@ResponseBody
	public ResultDTO<?> invitedCode(Headers header, String recomUser) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			// 邀请人
			MemberInfo recomMember = null;
			if (CPSUtil.isNotEmpty(recomUser)) {
				recomMember = memberInfoService.findMemberInfoByVisitCode(recomUser);
			}
			// 验证码邀请人
			if (CPSUtil.isNotEmpty(recomUser) && CPSUtil.isEmpty(recomMember)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			if (CPSUtil.isNotEmpty(memberInfo.getRecomCode())) {
				return ResultDTO.ERROR(AppResultCode.邀请关系已存在);
			}
			if (memberInfo.getVisitCode().equals(recomUser)) {
				return ResultDTO.ERROR(AppResultCode.邀请错误);
			}
			memberInfo.setRecomCode(recomUser);// 邀请人人邀请码
			// 根据推荐人账户获取邀请码
			MemberInfo member = memberInfoDao.getUserByVCode(memberInfo.getRecomCode());
			if (CPSUtil.isNotEmpty(member)) {
				// 验证码邀请人
				if (CPSUtil.isNotEmpty(recomUser) && CPSUtil.isEmpty(recomMember)) {
					return ResultDTO.ERROR(BasicCode.参数错误);
				}
				memberInfo.setRecomUser(member.getLoginAccount());
				memberInfo.setLevelCode(member.getLevelCode() + "|" + memberInfo.getLevelCode());
			}
			// 处理保存逻辑
			if (memberInfoDao.update(memberInfo, "memberId") == 1) {
				// 获取系统邀请奖励金额
				double visitAward = Double.parseDouble(CPSUtil.getParamValue(Const.CT_RECOM_REGISTER_REWARD));
				// 注册默认基金
				double umoney = Double.valueOf(CPSUtil.getParamValue(Const.CT_MEMBER_REGISTER_MONEY));
				// 处理注册默认基金
				SbMoneyCount moneyCount = new SbMoneyCount();
				// 获取系统参数默认奖励金额
				if (CPSUtil.isEmpty(umoney)) {
					umoney = 2.00d;// 未设置默认2.0
				}
				if (CPSUtil.isEmpty(visitAward)) {
					visitAward = 0.50d;// 未设置默认0.5
				}
				CPSUtil.xprint("注册默认金额：" + umoney);
				moneyCount.setContentId(null);
				moneyCount.setCreateTime(new Date());
				moneyCount.setUmoney(umoney);
				moneyCount.setmType(AwardType.register.getAwardId());
				moneyCount.setUserId(memberInfo.getMemberId());
				sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);

				// 添加邀请人奖励
				if (CPSUtil.isNotEmpty(recomMember)) {
					/*SbVisitMoney visitMoney = new SbVisitMoney();
					visitMoney.setCreateTime(new Date());
					visitMoney.setUserId(recomMember.getMemberId());
					visitMoney.setVcode(memberInfo.getVisitCode());
					visitMoney.setVmoney(visitAward);
					sbVisitMoneyService.saveVisitMoney(visitMoney);*/
					//给邀请人发放6000金币
					SbMoneyCount sbMoneyCount = new SbMoneyCount();
					sbMoneyCount.setUserId(recomMember.getMemberId());
					sbMoneyCount.setUmoney(6000d);
					sbMoneyCount.setmType(4);
					sbMoneyCount.setCreateTime(new Date());
					sbMoneyCount.setVcode(memberInfo.getVisitCode());
					sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount);
				}
			}
			return ResultDTO.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

}
