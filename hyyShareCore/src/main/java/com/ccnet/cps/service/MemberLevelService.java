package com.ccnet.cps.service;

import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.MemberLevel;

/**
 * 会员等级
 * @author jackie wang
 *
 */
public interface MemberLevelService extends BaseService<MemberLevel> {
	
	/**
	 * 获取单个等级
	 * @param level
	 * @return
	 */
	public MemberLevel findMemberLevel(MemberLevel level);
	
	/**
	  * 获取所有等级
	  * @param level
	  * @return
	  */
	public List<MemberLevel> queryMemberLevelList(MemberLevel level);
	
	/**
	 * 保存等级
	 * @param level
	 * @return
	 */
	public boolean saveMemberLevel(MemberLevel level);
	
	/**
	 * 保存等级
	 * @param paramDto
	 * @return
	 */
	public boolean saveMemberLevel(Dto paramDto);
	
	/**
	 * 修改等级
	 * @param level
	 * @return
	 */
	public boolean editMemberLevel(MemberLevel level);
	
	
	/**
	 * 删除等级
	 * @param level
	 * @return
	 */
	public boolean trashMemberLevel(MemberLevel level);
	
	/**
	 * 删除所有等级
	 * @return
	 */
	public boolean trashAllMemberLevel();
	
	
	/**
	 * 批量删除等级
	 * @param list
	 * @return
	 */
	public boolean trashMemberLevelList(List<MemberLevel> list);
	
	

}
