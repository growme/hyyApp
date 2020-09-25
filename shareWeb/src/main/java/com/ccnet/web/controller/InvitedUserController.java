package com.ccnet.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;
import com.ccnet.cps.service.SbVisitMoneyService;
import com.ccnet.cps.service.SystemNoticeService;

/**
 * 邀请控制器
 * 
 * @author JackieWang
 * 
 * @time 2019-10-27 下午2:30:49
 */
@Controller("invitedUserController")
@RequestMapping("/invited/")
public class InvitedUserController extends BaseController<SbMoneyCount> {

	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	MemberInfoService memberInfoService;
	@Autowired
	private SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	private SbUserMoneyService sbUserMoneyService;
	@Autowired
	private SystemNoticeService systemNoticeService;

	/**
	 * 徒弟列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("dlist")
	public String invitedList(Model model) {
		// 当前用户
		MemberInfo memberInfo = getCurUser();
		// 获取徒弟数据
		List<MemberInfo> memberInfos = CPSUtil.getChildTDMemeberList(memberInfo.getMemberId());
		model.addAttribute("tp", "1");
		model.addAttribute("memberInfos", memberInfos);
		model.addAttribute(Const.MENU_SELECTED_INDEX, "visit");
		return "/user/jsp/invited/invited_list";
	}

	/**
	 * 徒孙列表
	 * 
	 * @return
	 */
	@RequestMapping("slist")
	public String invitedSList(Model model) {
		// 当前用户
		MemberInfo memberInfo = getCurUser();
		// 获取徒孙数据
		List<MemberInfo> memberInfos = CPSUtil.getChildTSMemeberList(memberInfo.getMemberId());
		model.addAttribute("tp", "2");
		model.addAttribute("memberInfos", memberInfos);
		model.addAttribute(Const.MENU_SELECTED_INDEX, "visit");
		return "/user/jsp/invited/invited_list";
	}

	/**
	 * 收徒详情页
	 * 
	 * @return
	 */
	@RequestMapping("detail")
	public String invitedDetail(Model model) {
		Dto paramDto = new BaseDto();
		// 当前用户
		MemberInfo memberInfo = getCurUser();
		// 今日收徒
		int jrst = getTodayChildMemberNum(memberInfo);
		// 累计奖励
		double ljjl = getCurrentUserVisitAwardMoney(memberInfo, 1);
		// 今日奖励
		double jrjl = getCurrentUserVisitAwardMoney(memberInfo, 0);
		// 累计收徒
		int ljst = getChildMemberNum(memberInfo, 0);
		model.addAttribute("jrst", jrst);
		model.addAttribute("ljst", ljst);
		model.addAttribute("ljjl", ljjl);
		model.addAttribute("jrjl", jrjl);
		model.addAttribute("visitUrl", getRecomUrl(memberInfo.getVisitCode()));
		model.addAttribute(Const.MENU_SELECTED_INDEX, "visit");
		return "/user/jsp/invited/invited_index";
	}

	/**
	 * 获取当前用户总提成
	 * 
	 * @param memberInfo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private Double getCurUserTotalPercentagecount(MemberInfo memberInfo, Dto paramDto) {
		double totalCountMoney = 0.0d;
		SbMoneyCount sbMoneyCount = new SbMoneyCount();
		sbMoneyCount.setUserId(memberInfo.getMemberId());
		// 获取徒弟提成
		double tdPercentageCount = sbMoneyCountService.getCurrentUserTDPercentageCount(sbMoneyCount, paramDto);
		if (tdPercentageCount > 0) {
			totalCountMoney += tdPercentageCount;
		}
		// 获取徒孙提成
		double tsPercentageCount = sbMoneyCountService.getCurrentUserTSPercentageCount(sbMoneyCount, paramDto);
		if (tdPercentageCount > 0) {
			totalCountMoney += tdPercentageCount;
		}
		return totalCountMoney;
	}

	/**
	 * 获取今日收徒数
	 * 
	 * @param memberInfo
	 * @return
	 */
	private Integer getTodayChildMemberNum(MemberInfo memberInfo) {
		int invitedDateNow = 0;
		String endDate = CPSUtil.getCurDate();
		String startDate = CPSUtil.getCurDate();
		List<MemberInfo> invitedsDateNow = memberInfoService.findRecomMemberInfoByVisitCode1(memberInfo.getVisitCode(),
				startDate, endDate);
		if (CPSUtil.listNotEmpty(invitedsDateNow)) {
			invitedDateNow = invitedsDateNow.size();
		}
		return invitedDateNow;
	}

	/**
	 * 获取当前用户的奖励（邀请奖励+提成）
	 * 
	 * @param memberInfo
	 * @param flag
	 *            0 今日奖励 1 总奖励
	 * @return
	 */
	private Double getCurrentUserVisitAwardMoney(MemberInfo memberInfo, int flag) {

		Double totalMoney = 0.0d;// 今日收益
		Dto paramDto = new BaseDto();
		String visitCode = memberInfo.getVisitCode();
		if (CPSUtil.isNotEmpty(visitCode)) {
			// 设置用户查询
			SbVisitMoney sbVisitMoney = new SbVisitMoney();
			sbVisitMoney.setUserId(memberInfo.getMemberId());

			if (flag == 0) {
				// 设置查询日期
				String startDate = CPSUtil.getCurDate();
				String overDate = CPSUtil.getCurDate();
				paramDto.put("end_date", startDate);
				paramDto.put("start_date", startDate);
			}

			// 获取邀请奖励
			double visitMoney = sbVisitMoneyService.getCurrentUserVisitMoney(sbVisitMoney, paramDto);
			// 提成金额
			double percentMoney = getCurUserTotalPercentagecount(memberInfo, paramDto);

			totalMoney = visitMoney + percentMoney;
		}

		return totalMoney;
	}

	/**
	 * 获取下线人数
	 * 
	 * @param memberInfo
	 * @param flag
	 *            0 不包含徒孙 1 包含徒孙
	 * @return
	 */
	private Integer getChildMemberNum(MemberInfo memberInfo, int flag) {

		Integer childNum = 0;
		List<MemberInfo> childInfos1 = null;
		List<MemberInfo> childInfos2 = null;
		String visitCode = memberInfo.getVisitCode();
		if (CPSUtil.isNotEmpty(visitCode)) {
			// 同时提取徒弟和徒孙
			childInfos1 = CPSUtil.getChildTDMemeberList(memberInfo.getMemberId());
			childInfos2 = CPSUtil.getChildTSMemeberList(memberInfo.getMemberId());
			if (CPSUtil.isNotEmpty(childInfos1)) {
				childNum += childInfos1.size();
				CPSUtil.xprint("徒弟个数:" + childInfos1.size());
			}

			if (flag == 1) {
				if (CPSUtil.isNotEmpty(childInfos2)) {
					childNum += childInfos2.size();
					CPSUtil.xprint("徒孙个数:" + childInfos2.size());
				}
			}
		}

		CPSUtil.xprint("下线总数:" + childNum);
		return childNum;
	}

	/**
	 * 收徒二维码
	 * 
	 * @return
	 */
	@RequestMapping("barcode")
	public String invitedBarCode() {
		return "/user/jsp/invited/invited_qrcode";
	}

	/**
	 * 今日徒弟收益
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("tearn")
	public String invitedTearns(Model model) {
		MemberInfo memberInfo = getCurUser();
		Dto paramDto = new BaseDto();
		paramDto.put("end_date", CPSUtil.getCurDate());
		paramDto.put("start_date", CPSUtil.getCurDate());
		SbMoneyCount sbMoneyCount = new SbMoneyCount();
		sbMoneyCount.setUserId(memberInfo.getMemberId());
		List<SbMoneyCount> mlist = sbMoneyCountService.getCurrentUserTDPercentageList(sbMoneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mlist)) {
			model.addAttribute("invitedCounts", mlist);
		}
		model.addAttribute(Const.MENU_SELECTED_INDEX, "visit");
		return "/user/jsp/invited/invited_tearn";
	}

	/**
	 * 今日徒孙收益
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("searn")
	public String invitedSearns(Model model) {
		MemberInfo memberInfo = getCurUser();
		Dto paramDto = new BaseDto();
		paramDto.put("end_date", CPSUtil.getCurDate());
		paramDto.put("start_date", CPSUtil.getCurDate());
		SbMoneyCount sbMoneyCount = new SbMoneyCount();
		sbMoneyCount.setUserId(memberInfo.getMemberId());
		List<SbMoneyCount> mlist = sbMoneyCountService.getCurrentUserTSPercentageList(sbMoneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mlist)) {
			model.addAttribute("invitedCounts", mlist);
		}
		model.addAttribute(Const.MENU_SELECTED_INDEX, "visit");
		return "/user/jsp/invited/invited_searn";
	}

}
