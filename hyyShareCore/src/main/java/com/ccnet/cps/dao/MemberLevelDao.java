package com.ccnet.cps.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.cps.entity.MemberLevel;

/**
 * 会员等级
 * @author jackie wang
 *
 */
@Repository("memberLevelDao")
public class MemberLevelDao extends BaseDao<MemberLevel> {
	
	
	/**
	 * 获取单个等级
	 * @param level
	 * @return
	 */
	public MemberLevel findMemberLevel(MemberLevel level) {
		return find(level);
	}
	
	/**
	  * 获取所有等级
	  * @param level
	  * @return
	  */
	public List<MemberLevel> queryMemberLevelList(MemberLevel level){
		return findList(level);
	}
	
	/**
	 * 保存等级
	 * @param level
	 * @return
	 */
	public boolean saveMemberLevel(MemberLevel level) {
		return insert(level)>0;
	}
	
	/**
	 * 修改等级
	 * @param level
	 * @return
	 */
	public boolean editMemberLevel(MemberLevel level) {
		return update(level, "levelId")>0;
	}
	
	
	/**
	 * 删除等级
	 * @param ch
	 * @return
	 */
	public boolean trashMemberLevel(MemberLevel level) {
		return delete(level)>0;
	}
	
	
	/**
	 * 批量删除等级
	 * @param list
	 * @return
	 */
	public boolean trashMemberLevelList(List<MemberLevel> list) {
		return deleteBatch(list).length>=0 && deleteBatch(list).length==list.size();
	}
	
	/**
	 * 清空等级
	 * @return
	 */
	public boolean truncateMemberLevel(){
		StringBuffer sql = new StringBuffer("delete from member_level");
		List<Object> params = new ArrayList<Object>();
		return memory.update(sql, params)>=0;
	}
			

}
