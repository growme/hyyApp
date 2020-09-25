package com.ccnet.cps.service;

import java.util.Date;
import java.util.List;

import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.base.BaseService;
import com.ccnet.cps.entity.MemberInfo;


public interface MemberInfoService extends BaseService<MemberInfo>{

	/**
	 * 分页查询用户(多过滤)
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	public Page findMemberInfoByPage(MemberInfo user, Page<MemberInfo> page,Dto pdDto);
	
	/**
	 * 通过邀请码获取会员信息
	 * @param visitCode
	 * @return
	 */
	public MemberInfo findMemberInfoByVisitCode(String visitCode);
	
	/**
	 * 根据邀请码查询用户
	 * @param visitCodes 邀请码集合
	 * @return List<MemberInfo> 会员
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCodes(List<String> visitCodes);
	
	/**
	 * 获取会员徒孙
	 * @param visitCode
	 * @return
	 */
	public List<MemberInfo> findChildMemberInfoByVisitCode(String visitCode);
	
	/**
     * 根据支付宝账户查询
     * @param payAccount
     * @return
     */
    public MemberInfo findMemberByPayAccount(String payAccount);
    
    /**
     * 后台会员数统计
     * @param startDate
     * @param endDate
     * @return
     */
	public Dto countMembers(Date startDate, Date endDate);
    
    
    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    public MemberInfo findMemberByMobile(String mobile);
    
    /**
     * 根据手机号获取
     * @param mobile
     * @return
     */
    public MemberInfo findMemberByPayMobile(String mobile);
	
	/**
	 * 获取邀请的人
	 * @param visitCode
	 * @return
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCode(String visitCode);
	
	/**
	 * 获取邀请的人 增加时间条件可按时间获取
	 * @param visitCode
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCode1(String visitCode,String startDate,String endDate);
	
	/**
	 * 获取单个用户
	 * @param userID
	 * @return
	 */
	public MemberInfo getUserByUserID(Integer userID);
	
    /**
     * 根据登录帐号查找loginName和userInfoType，正常只有一条数据
     * and a.isvalid='1' and a.userInfo_type='1'需要该条件
     * @param loginName
     * @return
     */
    public MemberInfo findFormatByLoginName(String loginName);
	
	/**
	 * 保存用户
	 * @param memberInfo
	 * @return
	 */
	public Integer saveMemberInfo(MemberInfo memberInfo);
	
	/**
	 * 修改用户
	 * @param memberInfo
	 * @return
	 */
	public boolean editMemberInfo(MemberInfo memberInfo);
	
	/**
     * 批量更新会员状态
     * @param memberIds
     * @param state
     * @return
     */
	public boolean updateMemberSateByIds(String memberId,Integer state);
	
	/**
	 * 更新会员等级
	 * @param levelCode
	 * @param memberId
	 * @return
	 */
	public boolean updateMemberLevel(Integer levelCode, Integer memberId);
	
	/**
	 * 重置密码
	 * @param user_id
	 * @param password
	 * @return
	 */
	public boolean resetPassword(String user_id,String password);
	
	/**
	 * 删除用户
	 * @param memberInfo
	 * @return
	 */
	public boolean trashMemberInfo(MemberInfo memberInfo);
	
	/**
	 * 批量删除用户
	 * @param user_ids
	 * @return
	 */
	public boolean trashMemberInfoList(String user_ids);

	/**
	 * 验证登录状态
	 * @param username
	 * @param password
	 * @param salt
	 * @return
	 */
	public boolean checkLogin(String username, String password,String salt);

	public MemberInfo getMemberGoldById(String memberId);

	public int updateMemberGoldById(String memberId, Integer gold);
	
}
