package com.ccnet.cps.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberLevelDao;
import com.ccnet.cps.entity.MemberLevel;
import com.ccnet.cps.service.MemberLevelService;
/**
 * 会员等级
 * @author jackie wang
 *
 */
@Service("memberLevelService")
public class MemberLevelServiceImpl extends BaseServiceImpl<MemberLevel> implements MemberLevelService {
	
	@Autowired
	private MemberLevelDao memberLevelDao;
	
	/**
	 * 获取单个等级
	 * @param level
	 * @return
	 */
	public MemberLevel findMemberLevel(MemberLevel level){
		return memberLevelDao.findMemberLevel(level);
	}
	
	/**
	  * 获取所有等级
	  * @param level
	  * @return
	  */
	public List<MemberLevel> queryMemberLevelList(MemberLevel level){
		return memberLevelDao.queryMemberLevelList(level);
	}
	
	/**
	 * 保存等级
	 * @param level
	 * @return
	 */
	public boolean saveMemberLevel(MemberLevel level){
		return memberLevelDao.saveMemberLevel(level);
	}
	
	/**
	 * 保存等级
	 * @param paramDto
	 * @return
	 */
	public boolean saveMemberLevel(Dto paramDto){
		boolean temp = false;
		if(CPSUtil.isNotEmpty(paramDto)){
			String levelCode = paramDto.getAsString("levelCode");
			String minMoney = paramDto.getAsString("minMoney");
			String maxMoney = paramDto.getAsString("maxMoney");
			String rewardMoney = paramDto.getAsString("rewardMoney");
			if(CPSUtil.isNotEmpty(levelCode) && CPSUtil.isNotEmpty(minMoney) && CPSUtil.isNotEmpty(maxMoney)){		
				MemberLevel level = null;
				String levelName = null;
				String leve_code[] = levelCode.split("\\|");
				String min_money[] = minMoney.split("\\|");
				String max_money[] = maxMoney.split("\\|");
				String reward_money[] = rewardMoney.split("\\|");
				//先清空表
				memberLevelDao.truncateMemberLevel();
				for (int i = 0; i < leve_code.length; i++) {
					if(CPSUtil.isNotEmpty(leve_code[i])){
						level = new MemberLevel();
						level.setCreateTime(new Date());
						level.setLevelCode(Integer.valueOf(leve_code[i]));
						levelName = MemeberLevelType.getMemeberLevelType(Integer.valueOf(leve_code[i])).getName();
						level.setLevelName(levelName);
						level.setMinMoney(Double.valueOf(min_money[i]));
						level.setMaxMoney(Double.valueOf(max_money[i]));
						level.setRewardMoney(Double.valueOf(reward_money[i]));
						temp = memberLevelDao.saveMemberLevel(level);
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * 修改等级
	 * @param level
	 * @return
	 */
	public boolean editMemberLevel(MemberLevel level){
		return memberLevelDao.editMemberLevel(level);
	}
	
	/**
	 * 删除等级
	 * @param level
	 * @return
	 */
	public boolean trashMemberLevel(MemberLevel level){
		return memberLevelDao.trashMemberLevel(level);
	}
	
	/**
	 * 批量删除等级
	 * @param list
	 * @return
	 */
	public boolean trashMemberLevelList(List<MemberLevel> list){
		return memberLevelDao.trashMemberLevelList(list);
	}
	
	/**
	 * 删除所有等级
	 * @return
	 */
	public boolean trashAllMemberLevel(){
		return memberLevelDao.truncateMemberLevel();
	}
	
	protected BaseDao<MemberLevel> getDao() {
		return memberLevelDao;
	}
	
	

}
