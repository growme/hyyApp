package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DoubleUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbContentInfoDao;
import com.ccnet.cps.dao.SbMoneyCountDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.dao.SbVisitMoneyDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.entity.UserDetailMoney;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;
import com.ccnet.cps.service.SbMoneyCountService;

@Service("sbMoneyCountService")
public class SbMoneyCountServiceImpl extends BaseServiceImpl<SbMoneyCount> implements SbMoneyCountService {

	@Autowired
	SbMoneyCountDao sbMoneyCountDao;
	@Autowired
	SbUserMoneyDao sbUserMoneyDao;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	SbContentInfoDao sbContentInfoDao;
	@Autowired
	SbVisitMoneyDao sbVisitMoneyDao;

	/**
	 * 分页查询
	 */
	public Page<SbMoneyCount> getMoneyCountByPage(SbMoneyCount sbMoneyCount, Page<SbMoneyCount> page, Dto paramDto) {

		Map<Integer, MemberInfo> memberMap = null;
		if (StringUtils.isNotBlank(paramDto.getAsString("memberName"))) {
			sbMoneyCount.setUserId(-1);
			MemberInfo memberInfo = memberInfoDao.findFormatByLoginName(paramDto.getAsString("memberName"));
			if (memberInfo != null) {
				memberMap = new HashMap<Integer, MemberInfo>(0);
				memberMap.put(memberInfo.getMemberId(), memberInfo);
				sbMoneyCount.setUserId(memberInfo.getMemberId());
			}
		}

		Page<SbMoneyCount> MoneyCountPage = sbMoneyCountDao.findSbMoneyCountByPage(sbMoneyCount, page, paramDto);
		List<SbMoneyCount> listMoneyCount = MoneyCountPage.getResults();
		if (!CPSUtil.checkListBlank(listMoneyCount)) {
			// 转换会员ID列表
			if (memberMap == null) { // 没有指定会员查询，则需要再查询一下会员信息补充
				List<Integer> memeberIds = sbVisitMoneyDao.getValuesFromField(listMoneyCount, "userId");
				memberMap = memberInfoDao.getUserMapByIds(memeberIds);
			}

			List<String> visitCodes = sbMoneyCountDao.getValuesFromField(listMoneyCount, "vcode");
			List<Integer> contentIds = sbMoneyCountDao.getValuesFromField(listMoneyCount, "contentId");
			Map<String, MemberInfo> rmembers = memberInfoDao.getUserMapByVisitCodes(visitCodes);
			Map<Integer, SbContentInfo> contents = sbContentInfoDao.getSbContentInfoMapByIds(contentIds);

			for (SbMoneyCount moneyCount : listMoneyCount) {
				// 当前会员
				if (CPSUtil.isNotEmpty(moneyCount.getUserId())) {
					moneyCount.setMemberInfo(memberMap.get(moneyCount.getUserId()));
				}
				// 被邀请人
				if (CPSUtil.isNotEmpty(moneyCount.getVcode())) {
					moneyCount.setInvitedMemberInfo(rmembers.get(moneyCount.getVcode()));
				}
				// 文章信息
				if (CPSUtil.isNotEmpty(moneyCount.getContentId())) {
					moneyCount.setSbContentInfo(contents.get(moneyCount.getContentId()));
				}
			}
		}
		return MoneyCountPage;
	}

	/**
	 * 查询资金集合
	 * 
	 * @param sbMoneyCount
	 * @param dtoParam
	 * @return
	 */
	public List<SbMoneyCount> findSbMoneyCountList(SbMoneyCount sbMoneyCount, Dto dtoParam) {

		List<SbMoneyCount> listMoneyCount = sbMoneyCountDao.findSbMoneyCountList(sbMoneyCount, dtoParam);
		if (!CPSUtil.checkListBlank(listMoneyCount)) {
			// 转换会员ID列表
			List<Integer> memeberIds = sbMoneyCountDao.getValuesFromField(listMoneyCount, "userId");
			List<String> visitCodes = sbMoneyCountDao.getValuesFromField(listMoneyCount, "vcode");
			List<Integer> contentIds = sbMoneyCountDao.getValuesFromField(listMoneyCount, "contentId");
			// 获取会员信息集合
			Map<Integer, MemberInfo> members = memberInfoDao.getUserMapByIds(memeberIds);
			Map<String, MemberInfo> rmembers = memberInfoDao.getUserMapByVisitCodes(visitCodes);
			Map<Integer, SbContentInfo> contents = sbContentInfoDao.getSbContentInfoMapByIds(contentIds);

			for (SbMoneyCount moneyCount : listMoneyCount) {
				// 当前会员
				if (CPSUtil.isNotEmpty(moneyCount.getUserId())) {
					moneyCount.setMemberInfo(members.get(moneyCount.getUserId()));
				}
				// 被邀请人
				if (CPSUtil.isNotEmpty(moneyCount.getVcode())) {
					moneyCount.setInvitedMemberInfo(rmembers.get(moneyCount.getVcode()));
				}
				// 文章信息
				if (CPSUtil.isNotEmpty(moneyCount.getContentId())) {
					moneyCount.setSbContentInfo(contents.get(moneyCount.getContentId()));
				}
			}
		}

		return listMoneyCount;
	}

	/**
	 * 获取用户收益明细（包含邀请奖励）
	 * 
	 * @param sbMoneyCount
	 * @param paramDto
	 * @return
	 */
	public List<UserDetailMoney> queryUserDetailMoneyList(SbMoneyCount sbMoneyCount, SbVisitMoney sbVisitMoney,
			Dto paramDto) {

		List<UserDetailMoney> earnLists = new ArrayList<UserDetailMoney>();
		List<UserDetailMoney> dList = new ArrayList<UserDetailMoney>();
		// 获取文章/提成/红包收益
		List<SbMoneyCount> mcList = sbMoneyCountDao.findSbMoneyCountList(sbMoneyCount, paramDto);
		// 获取邀请奖励集合
		List<SbVisitMoney> vmList = sbVisitMoneyDao.queryVisitMoneyList(sbVisitMoney, paramDto);
		// 封装数据
		UserDetailMoney userDetailMoney = null;
		Map<Date, UserDetailMoney> userDetailMoneyMap = new HashMap<Date, UserDetailMoney>();
		List<Integer> contentIds = sbMoneyCountDao.getValuesFromField(mcList, "contentId");
		Map<Integer, SbContentInfo> contents = sbContentInfoDao.getSbContentInfoMapByIds(contentIds);
		if (CPSUtil.listNotEmpty(mcList)) {
			for (SbMoneyCount mc : mcList) {
				userDetailMoney = new UserDetailMoney();
				userDetailMoney.setMoney(mc.getUmoney());
				userDetailMoney.setUserId(mc.getUserId());
				userDetailMoney.setMoneyType(mc.getmType());
				userDetailMoney.setMoneyTypeName(AwardType.getAwardType(mc.getmType()).getAwardName());
				userDetailMoney.setCreateTime(mc.getCreateTime());
				userDetailMoneyMap.put(mc.getCreateTime(), userDetailMoney);
				// 文章信息
				if (CPSUtil.isNotEmpty(mc.getContentId())) {
					SbContentInfo info = contents.get(mc.getContentId());
					if (info != null)
						userDetailMoney.setSbContentInfo(info);
				}
				dList.add(userDetailMoney);
			}
		}
		// 加入邀请奖励
		if (CPSUtil.listNotEmpty(vmList)) {
			for (SbVisitMoney vm : vmList) {
				userDetailMoney = new UserDetailMoney();
				userDetailMoney.setMoney(vm.getVmoney());
				userDetailMoney.setUserId(vm.getUserId());
				userDetailMoney.setMoneyType(AwardType.visitad.getAwardId());
				userDetailMoney.setMoneyTypeName(AwardType.getAwardType(AwardType.visitad.getAwardId()).getAwardName());
				userDetailMoney.setCreateTime(vm.getCreateTime());
				userDetailMoneyMap.put(vm.getCreateTime(), userDetailMoney);
				dList.add(userDetailMoney);
			}
		}
		// 按照指定字段排序
		Object[] keys = userDetailMoneyMap.keySet().toArray();
		Arrays.sort(keys);
		for (int j = keys.length - 1; j >= 0; j--) {
			earnLists.add(userDetailMoneyMap.get(keys[j]));
		}

		return earnLists;
	}

	/**
	 * 当前用户今日收益
	 */
	public Double getCurrentUserMoneyCount(SbMoneyCount sbMoneyCount, Dto paramDto) {
		double currentUserTodayEarn = 0;
		// 处理时间
		paramDto.put("end_date", CPSUtil.getCurDate());
		paramDto.put("start_date", CPSUtil.getCurDate());
		List<SbMoneyCount> mList = sbMoneyCountDao.findSbMoneyCountList(sbMoneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mList)) {
			for (SbMoneyCount sbm : mList) {
				currentUserTodayEarn += sbm.getUmoney();
			}
		}
		return currentUserTodayEarn;
	}

	/**
	 * 当前用户徒弟提成收益
	 */
	public Double getCurrentUserTDPercentageCount(SbMoneyCount sbMoneyCount, Dto paramDto) {
		double currentUserTodayEarn = 0;
		List<SbMoneyCount> mList = getCurrentUserTDPercentageList(sbMoneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mList)) {
			for (SbMoneyCount sbm : mList) {
				currentUserTodayEarn += sbm.getUmoney();
			}
		}
		return currentUserTodayEarn;
	}

	/**
	 * 当前用户徒孙提成收益
	 */
	public Double getCurrentUserTSPercentageCount(SbMoneyCount sbMoneyCount, Dto paramDto) {
		double currentUserTodayEarn = 0;
		List<SbMoneyCount> mList = getCurrentUserTSPercentageList(sbMoneyCount, paramDto);
		if (CPSUtil.listNotEmpty(mList)) {
			for (SbMoneyCount sbm : mList) {
				currentUserTodayEarn += sbm.getUmoney();
			}
		}
		return currentUserTodayEarn;
	}

	/**
	 * 当前用户徒弟提成收益明细
	 */
	public List<SbMoneyCount> getCurrentUserTDPercentageList(SbMoneyCount sbMoneyCount, Dto paramDto) {
		List<SbMoneyCount> mList = null;
		List<MemberInfo> mbList = null;
		List<String> visitCodes = null;
		Integer userId = sbMoneyCount.getUserId();
		if (CPSUtil.isNotEmpty(userId)) {
			String endDate = paramDto.getAsString("end_date");
			String startDate = paramDto.getAsString("start_date");
			// 获取当前会员所有徒弟
			mbList = CPSUtil.getChildTDMemeberList(userId);
			if (CPSUtil.listNotEmpty(mbList)) {
				visitCodes = sbMoneyCountDao.getValuesFromField(mbList, "visitCode");
				List<Integer> userIds = sbMoneyCountDao.getValuesFromField(mbList, "userId");
				List<Integer> contentIds = sbMoneyCountDao.getValuesFromField(mbList, "contentId");
				// 获取会员
				Map<Integer, MemberInfo> userMap = memberInfoDao.getUserMapByIds(userIds);
				// 获取文章
				Map<Integer, SbContentInfo> contentMap = sbContentInfoDao.getSbContentInfoMapByIds(contentIds);
				mList = sbMoneyCountDao.findSbMoneyCountByVisitCodes(userId, visitCodes, startDate, endDate);
				if (CPSUtil.listNotEmpty(mList)) {
					for (SbMoneyCount sbm : mList) {
						// 设置会员
						if (CPSUtil.isNotEmpty(sbm.getUserId())) {
							sbm.setMemberInfo(userMap.get(sbm.getUserId()));
						}
						// 设置文章
						if (CPSUtil.isNotEmpty(sbm.getContentId())) {
							sbm.setSbContentInfo(contentMap.get(sbm.getContentId()));
						}
						// 设置邀请人
						if (CPSUtil.isNotEmpty(sbm.getVcode())) {
							sbm.setInvitedMemberInfo(CPSUtil.getMemeberByVisitCode(sbm.getVcode()));
						}
					}
				}
			}
		}
		return mList;
	}

	/**
	 * 当前用户徒孙提成收益明细
	 */
	public List<SbMoneyCount> getCurrentUserTSPercentageList(SbMoneyCount sbMoneyCount, Dto paramDto) {
		List<SbMoneyCount> mList = null;
		List<MemberInfo> mbList = null;
		List<String> visitCodes = null;
		Integer userId = sbMoneyCount.getUserId();
		if (CPSUtil.isNotEmpty(userId)) {
			String endDate = paramDto.getAsString("end_date");
			String startDate = paramDto.getAsString("start_date");
			// 获取当前会员所有徒弟
			mbList = CPSUtil.getChildTSMemeberList(userId);
			if (CPSUtil.listNotEmpty(mbList)) {
				visitCodes = sbMoneyCountDao.getValuesFromField(mbList, "visitCode");
				List<Integer> userIds = sbMoneyCountDao.getValuesFromField(mbList, "userId");
				List<Integer> contentIds = sbMoneyCountDao.getValuesFromField(mbList, "contentId");
				// 获取会员
				Map<Integer, MemberInfo> userMap = memberInfoDao.getUserMapByIds(userIds);
				// 获取文章
				Map<Integer, SbContentInfo> contentMap = sbContentInfoDao.getSbContentInfoMapByIds(contentIds);
				mList = sbMoneyCountDao.findSbMoneyCountByVisitCodes(userId, visitCodes, startDate, endDate);
				if (CPSUtil.listNotEmpty(mList)) {
					for (SbMoneyCount sbm : mList) {
						// 设置会员
						if (CPSUtil.isNotEmpty(sbm.getUserId())) {
							sbm.setMemberInfo(userMap.get(sbm.getUserId()));
						}
						// 设置文章
						if (CPSUtil.isNotEmpty(sbm.getContentId())) {
							sbm.setSbContentInfo(contentMap.get(sbm.getContentId()));
						}
						// 设置邀请人
						if (CPSUtil.isNotEmpty(sbm.getVcode())) {
							sbm.setInvitedMemberInfo(CPSUtil.getMemeberByVisitCode(sbm.getVcode()));
						}
					}
				}
			}
		}
		return mList;
	}

	/**
	 * 查询当前用户今日是否已领取红包
	 * 
	 * @param userId
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public SbMoneyCount getSbMoneyCountByType(Integer userId, Integer type, String startDate, String endDate) {
		return sbMoneyCountDao.findSbMoneyCountByType(userId, type, startDate, endDate);
	}

	/**
	 * 保存收益明细
	 * 
	 * @param moneyCount
	 * @return
	 */
	public boolean saveSbMoneyCountInfo(SbMoneyCount moneyCount) {
		boolean temp = false;
		if (sbMoneyCountDao.insert(moneyCount) > 0) {
			UserDailyEntity userLock = UserCache.getInstance().getUserCache(moneyCount.getUserId());
			synchronized (userLock) {
				// 保存总数据
				SbUserMoney userMoney = new SbUserMoney();
				userMoney.setUserId(moneyCount.getUserId());
				userMoney.setProfitsMoney(moneyCount.getUmoney());
				userMoney.setTmoney(moneyCount.getUmoney());
				userMoney.setUpdateTime(new Date());
				userMoney.setLastProDate(userMoney.getUpdateTime());
				if (sbUserMoneyDao.insertOrUpdate(userMoney) > 0) {
					temp = true;
				}
			}
		}
		return temp;
	}

	@Override
	protected BaseDao<SbMoneyCount> getDao() {
		return sbMoneyCountDao;
	}

	@Override
	public List<SbMoneyCount> getUserMoneyCountByUserIdAndDate(Integer userId, String startDate, String endDate) {
		return sbMoneyCountDao.getUserMoneyCountByUserIdAndDate(userId, startDate, endDate);
	}

	@Override
	public Integer findMaxId() {
		return sbMoneyCountDao.findMaxId();
	}

	public boolean updateSbMoneyCountInfo(SbMoneyCount moneyCount) {
		boolean temp = false;
		if (sbMoneyCountDao.update(moneyCount, "um_id") > 0) {
			UserDailyEntity userLock = UserCache.getInstance().getUserCache(moneyCount.getUserId());
			synchronized (userLock) {
				// 保存总数据
				SbUserMoney userMoney = new SbUserMoney();
				userMoney.setUserId(moneyCount.getUserId());
				userMoney.setProfitsMoney(DoubleUtil.div(moneyCount.getUmoney(), 2D, 3));
				userMoney.setTmoney(DoubleUtil.div(moneyCount.getUmoney(), 2D, 3));
				userMoney.setUpdateTime(new Date());
				userMoney.setLastProDate(userMoney.getUpdateTime());
				if (sbUserMoneyDao.insertOrUpdate(userMoney) > 0) {
					temp = true;
				}
			}
		}
		return temp;
	}

	public Integer findSbMoneyCountByTypeUserId(Integer userId, Integer type) {
		return sbMoneyCountDao.findSbMoneyCountByTypeUserId(userId, type);
	}
}
