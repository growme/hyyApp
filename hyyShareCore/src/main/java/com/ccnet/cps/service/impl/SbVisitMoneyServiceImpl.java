package com.ccnet.cps.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.dao.SbVisitMoneyDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.localcache.UserCache;
import com.ccnet.cps.localcache.UserDailyEntity;
import com.ccnet.cps.service.SbVisitMoneyService;

@Service("sbVisitMoneyService")
public class SbVisitMoneyServiceImpl extends BaseServiceImpl<SbVisitMoney> implements SbVisitMoneyService{

	@Autowired
	SbVisitMoneyDao sbVisitMoneyDao;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	SbUserMoneyDao sbUserMoneyDao;
	
	/*
	 *分页查询  会员邀请明细
	 */
	@Override
	public Page<SbVisitMoney> getVisitMoneyByPage(SbVisitMoney sbVisitMoney,Page<SbVisitMoney> page, Dto paramDto) {
		
		Map<Integer, MemberInfo> memberMap = null;
		if (StringUtils.isNotBlank(paramDto.getAsString("memberName"))) {
			sbVisitMoney.setUserId(-1);
			MemberInfo memberInfo = memberInfoDao.findFormatByLoginName(paramDto.getAsString("memberName"));
			if (memberInfo != null) {
				memberMap = new HashMap<Integer, MemberInfo>(0);
				memberMap.put(memberInfo.getMemberId(), memberInfo);
				sbVisitMoney.setUserId(memberInfo.getMemberId());
			}
		}
		
		Page<SbVisitMoney> VisitMoneypage=sbVisitMoneyDao.findSbVisitMoneyByPage(sbVisitMoney, page, paramDto);
		List<SbVisitMoney> listVisitMoneys=VisitMoneypage.getResults();
		if(!CPSUtil.checkListBlank(listVisitMoneys)){
			if (memberMap == null) { //没有指定会员查询，则需要再查询一下会员信息补充
				List<Integer> memeberIds = sbVisitMoneyDao.getValuesFromField(listVisitMoneys, "userId");
				memberMap = memberInfoDao.getUserMapByIds(memeberIds);
			}
			//获取被邀请人
			List<String> visitCodes = sbVisitMoneyDao.getValuesFromField(listVisitMoneys, "vcode");
		    Map<String,MemberInfo> rmembers=memberInfoDao.getUserMapByVisitCodes(visitCodes);
		    for(SbVisitMoney visitMoney:listVisitMoneys){
		    	
		    	if(CPSUtil.isNotEmpty(visitMoney.getUserId())){
		    		visitMoney.setMemberInfo(memberMap.get(visitMoney.getUserId()));
		    	}
		    	
		    	if(CPSUtil.isNotEmpty(visitMoney.getVcode())){
			    	visitMoney.setInvitedMemberInfo(rmembers.get(visitMoney.getVcode()));
		    	}
		    	
		    }
		    
		}
		return VisitMoneypage;
	}
	
	/**
	 * 查询奖励
	 * @param sbVisitMoney
	 * @param dtoParam
	 * @return
	 */
	public List<SbVisitMoney> queryVisitMoneyList(SbVisitMoney sbVisitMoney,Dto dtoParam){
		return sbVisitMoneyDao.queryVisitMoneyList(sbVisitMoney, dtoParam);
	}

	
	/**
	 * 获取当前会员今日奖励
	 */
	public Double getCurrentUserVisitMoney(SbVisitMoney sbVisitMoney,Dto dtoParam) {
		double currentUserTodayVisitEarn=0;
		// 获取当前用户今日总邀请收益列表
		List<SbVisitMoney> vList = sbVisitMoneyDao.queryVisitMoneyList(sbVisitMoney, dtoParam);
		if(CPSUtil.listNotEmpty(vList)){
			for(SbVisitMoney vm:vList){
				currentUserTodayVisitEarn+=vm.getVmoney();
			}
		}
		return currentUserTodayVisitEarn;
	}
	
	/**
	 * 获取当前会员徒弟今日奖励
	 */
	public Double getCurrentUserTDVisitMoney(SbVisitMoney sbVisitMoney,MemberInfo member,Dto dtoParam) {
		//下级奖励
		List<SbVisitMoney> vList = null;
		double userTodayVisitEarn=0;
		//获取当前用户的所有下级
		List<MemberInfo> mList = memberInfoDao.getUserListByVisitCode(member.getVisitCode(), null, null);
		//获取被邀请人
		List<Integer> userIds = sbVisitMoneyDao.getValuesFromField(mList, "userId");
		if(CPSUtil.listNotEmpty(userIds)){
			// 获取当前用户下级今日总邀请奖励列表
			vList = sbVisitMoneyDao.queryVisitMoneyByVisitCodes(userIds,dtoParam);
			if(CPSUtil.listNotEmpty(vList)){
				for(SbVisitMoney vm:vList){
					userTodayVisitEarn += vm.getVmoney();
				}
			}
		}
		
		return userTodayVisitEarn;
	}
	
	
	/**
	 * 获取当前会员徒孙今日奖励
	 */
	public Double getCurrentUserTSVisitMoney(SbVisitMoney sbVisitMoney,MemberInfo member,Dto dtoParam) {
		//下级奖励
		List<SbVisitMoney> vList = null;
		double userTodayVisitEarn=0;
		//获取当前用户的所有下级
		List<MemberInfo> mList = memberInfoDao.getUserListByVisitCode(member.getVisitCode(), null, null);
		//获取被邀请人
		List<Integer> userIds = sbVisitMoneyDao.getValuesFromField(mList, "userId");
		if(CPSUtil.listNotEmpty(userIds)){
			// 获取当前用户下级今日总邀请奖励列表
			vList = sbVisitMoneyDao.queryVisitMoneyByVisitCodes(userIds,dtoParam);
			if(CPSUtil.listNotEmpty(vList)){
				for(SbVisitMoney vm:vList){
					userTodayVisitEarn += vm.getVmoney();
				}
			}
		}
		
		return userTodayVisitEarn;
	}
	
	
	/**
	 * 获取会员徒孙
	 * @param visitCode
	 * @return
	 */
	public List<MemberInfo> findChildMemberInfoByVisitCode(String visitCode){
		 List<MemberInfo> childList = null;
		 List<MemberInfo> recomList = memberInfoDao.getUserListByVisitCode(visitCode, null, null);
		 if(CPSUtil.listNotEmpty(recomList)){
			 List<String> chList = memberInfoDao.getValuesFromField(recomList, "visitCode");
			 if(CPSUtil.listNotEmpty(chList)){
			    childList = memberInfoDao.findRecomMemberInfoByVisitCodes(chList);
			 }
		 }
		return childList;
	}
	
	
	/**
	 * 新增邀请记录
	 */
	@Override
	public int saveVisitMoney(SbVisitMoney sbVisitMoney) {
		int i = 0;
		if (sbVisitMoney.getUserId() == null || sbVisitMoney.getVmoney() == null || sbVisitMoney.getVmoney() <= 0) {
			return i;
		}
		if (sbVisitMoneyDao.insert(sbVisitMoney, "vm_id") > 0) {
			UserDailyEntity userLock = UserCache.getInstance().getUserCache(sbVisitMoney.getUserId());
			synchronized (userLock) {
				//保存总数据
				SbUserMoney userMoney = new SbUserMoney();
				userMoney.setUserId(sbVisitMoney.getUserId());
				userMoney.setProfitsMoney(sbVisitMoney.getVmoney());
				userMoney.setTmoney(sbVisitMoney.getVmoney());
				userMoney.setUpdateTime(new Date());
				userMoney.setLastProDate(userMoney.getUpdateTime());
				i =  sbUserMoneyDao.insertOrUpdate(userMoney);
			}
		}
		return i;
	}

	
	@Override
	protected BaseDao<SbVisitMoney> getDao() {
		return sbVisitMoneyDao;
	}
}
