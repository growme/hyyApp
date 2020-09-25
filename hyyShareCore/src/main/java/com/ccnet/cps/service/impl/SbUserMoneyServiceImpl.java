package com.ccnet.cps.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbUserMoney;
import com.ccnet.cps.service.SbUserMoneyService;


/**
 *用户收益 
 *
 * @author Jackie
 *
 * @time 2017-10-26 下午4:54:15
 */
@Service("sbUserMoneyService")
public class SbUserMoneyServiceImpl extends BaseServiceImpl<SbUserMoney> implements SbUserMoneyService{

	@Autowired
	SbUserMoneyDao sbUserMoneyDao;
	@Autowired
	MemberInfoDao memberInfoDao;
	
	/**
	 * 获取总收益列表
	 * @param sbUserMoney
	 * @param page
	 * @param paramDto
	 * @return
	 */
	public Page<SbUserMoney> getUserMoneyByPage(SbUserMoney sbUserMoney,Page<SbUserMoney> page, Dto paramDto) {
		
		Map<Integer, MemberInfo> memberMap = null;
		if (StringUtils.isNotBlank(paramDto.getAsString("memberName"))) {
			sbUserMoney.setUserId(-1);
			MemberInfo memberInfo = memberInfoDao.findFormatByLoginName(paramDto.getAsString("memberName"));
			if (memberInfo != null) {
				memberMap = new HashMap<Integer, MemberInfo>(0);
				memberMap.put(memberInfo.getMemberId(), memberInfo);
				sbUserMoney.setUserId(memberInfo.getMemberId());
			}
		}
		
		Page<SbUserMoney> UserMoneyPage=sbUserMoneyDao.findSbUserMoneyByPage(sbUserMoney, page, paramDto);
		List<SbUserMoney> moneyList = UserMoneyPage.getResults();
		if(!CPSUtil.checkListBlank(moneyList)){
		    
			if (memberMap == null) { //没有指定会员查询，则需要再查询一下会员信息补充
				List<Integer> memberIds = sbUserMoneyDao.getValuesFromField(moneyList, "userId");
				memberMap = memberInfoDao.getUserMapByIds(memberIds);
			}
			
		    for(SbUserMoney userMoney : moneyList){
		    	if(CPSUtil.isNotEmpty(userMoney.getUserId())){
		    		MemberInfo _memberInfo = memberMap.get(userMoney.getUserId());
		    		if(_memberInfo!=null){
		    		   _memberInfo.setShowColor(MemeberLevelType.getMemeberLevelType(_memberInfo.getMemberLevel()).getShowColor());
		    		   userMoney.setMemberInfo(_memberInfo);
		    		}
		    	}
		    }
		    
		}
		return UserMoneyPage;
	}
	
	
	/**
	 * 获取用户资金集合
	 * @param sbUserMoney
	 * @param paramDto
	 * @return
	 */
	public List<SbUserMoney> findSbUserMoneyList(SbUserMoney sbUserMoney,Dto paramDto){
		List<SbUserMoney> listMoneyCount = sbUserMoneyDao.findSbUserMoneyList(sbUserMoney,paramDto);
		if(!CPSUtil.checkListBlank(listMoneyCount)){
			//转换会员ID列表
			List<Integer> memeberIds = sbUserMoneyDao.getValuesFromField(listMoneyCount, "userId");
			//获取会员信息集合
		    Map<Integer,MemberInfo> members = memberInfoDao.getUserMapByIds(memeberIds);
		    for(SbUserMoney moneyCount:listMoneyCount){
		    	if(CPSUtil.isNotEmpty(moneyCount.getUserId())){
		    		moneyCount.setMemberInfo(members.get(moneyCount.getUserId()));
		    	}
		    }
		}
		return listMoneyCount;
	}

	/**
	 * 有金额的，表示为活跃会员
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int countActiveMember(Date startDate, Date endDate){
		return sbUserMoneyDao.countActiveMember(startDate, endDate);
	}
	
	/**
	 * 获取资金按金额排序
	 * @param sbUserMoney
	 * @param page
	 * @param paramDto
	 * @return
	 */
	public Page<SbUserMoney> getUserMoneyPageByMoney(SbUserMoney sbUserMoney,Page<SbUserMoney> page, Dto paramDto) {
		Page<SbUserMoney> UserMoneyPage=sbUserMoneyDao.findUserMoneyPageByMoney(sbUserMoney, page, paramDto);
		List<SbUserMoney> listMoneyCount=UserMoneyPage.getResults();
		if(!CPSUtil.checkListBlank(listMoneyCount)){
			//转换会员ID列表
			List<Integer> memeberIds=sbUserMoneyDao.getValuesFromField(listMoneyCount, "userId");
			//获取会员信息集合
		    Map<Integer,MemberInfo> members=memberInfoDao.getUserMapByIds(memeberIds);
		    for(SbUserMoney moneyCount:listMoneyCount){
		    	if(CPSUtil.isNotEmpty(moneyCount.getUserId())){
		    		moneyCount.setMemberInfo(members.get(moneyCount.getUserId()));
		    	}
		    }
		}
		return UserMoneyPage;
	}
	
	@Override
	protected BaseDao<SbUserMoney> getDao() {
		return this.sbUserMoneyDao;
	}
}
