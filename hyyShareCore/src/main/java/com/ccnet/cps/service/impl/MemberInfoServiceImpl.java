package com.ccnet.cps.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.MD5;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.dataconvert.impl.BaseDto;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.dao.base.BaseDao;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.service.impl.BaseServiceImpl;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.service.MemberInfoService;

@Service("memberInfoService")
public class MemberInfoServiceImpl extends BaseServiceImpl<MemberInfo> implements MemberInfoService {

	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private SbUserMoneyDao userMoneyDao;

	/**
	 * 分页查询用户(多过滤)
	 * 
	 * @param user
	 * @param page
	 * @param pdDto
	 * @return
	 */
	@Override
	public Page findMemberInfoByPage(MemberInfo user, Page<MemberInfo> page, Dto pdDto) {
		Page userPage = memberInfoDao.findUserByPage(user, page, pdDto);
		List<MemberInfo> uList = userPage.getResults();
		if (!CPSUtil.checkListBlank(uList)) {
			for (MemberInfo memberInfo : uList) {
				memberInfo
						.setShowColor(MemeberLevelType.getMemeberLevelType(memberInfo.getMemberLevel()).getShowColor());
			}
		}
		return userPage;
	}

	/**
	 * 通过邀请码获取会员信息
	 * 
	 * @param visitCode
	 * @return
	 */
	public MemberInfo findMemberInfoByVisitCode(String visitCode) {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setVisitCode(visitCode);
		return memberInfoDao.findMemberInfo(memberInfo);
	}

	/**
	 * 根据邀请码查询用户
	 * 
	 * @param visitCodes
	 *            邀请码集合
	 * @return List<MemberInfo> 会员
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCodes(List<String> visitCodes) {
		return memberInfoDao.findRecomMemberInfoByVisitCodes(visitCodes);
	}

	/**
	 * 获取会员徒弟
	 * 
	 * @param visitCode
	 * @return
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCode(String visitCode) {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setRecomCode(visitCode);
		return memberInfoDao.findList(memberInfo);
	}

	/**
	 * 根据手机号查询
	 * 
	 * @param mobile
	 * @return
	 */
	public MemberInfo findMemberByMobile(String mobile) {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMobile(mobile);
		return memberInfoDao.find(memberInfo);
	}

	/**
	 * 获取会员徒孙
	 * 
	 * @param visitCode
	 * @return
	 */
	public List<MemberInfo> findChildMemberInfoByVisitCode(String visitCode) {
		List<MemberInfo> childList = null;
		List<MemberInfo> recomList = findRecomMemberInfoByVisitCode(visitCode);
		if (CPSUtil.listNotEmpty(recomList)) {
			List<String> chList = memberInfoDao.getValuesFromField(recomList, "visitCode");
			if (CPSUtil.listNotEmpty(chList)) {
				childList = memberInfoDao.findRecomMemberInfoByVisitCodes(chList);
			}
		}
		return childList;
	}

	/**
	 * 获取邀请的人 增加时间条件可按时间获取
	 * 
	 * @param visitCode
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            结束时间
	 * @return
	 */
	public List<MemberInfo> findRecomMemberInfoByVisitCode1(String visitCode, String startDate, String endDate) {
		return memberInfoDao.getUserListByVisitCode(visitCode, startDate, endDate);
	}

	/**
	 * 获取单个用户
	 * 
	 * @param userID
	 * @return
	 */
	public MemberInfo getUserByUserID(Integer userID) {
		return memberInfoDao.getUserByUserID(userID);
	}

	/**
	 * 根据登录帐号查找loginName和memberInfoType，正常只有一条数据 and a.isvalid='1' and
	 * a.memberInfo_type='1'需要该条件
	 * 
	 * @param loginName
	 * @return
	 */
	public MemberInfo findFormatByLoginName(String loginName) {
		MemberInfo a = null;
		try {
			a = memberInfoDao.findFormatByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * 根据支付宝账户查询
	 * 
	 * @param payAccount
	 * @return
	 */
	public MemberInfo findMemberByPayAccount(String payAccount) {
		return memberInfoDao.findMemberByPayAccount(payAccount);
	}

	/**
	 * 根据手机号获取
	 * 
	 * @param mobile
	 * @return
	 */
	public MemberInfo findMemberByPayMobile(String mobile) {
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setLoginAccount(mobile);
		return memberInfoDao.findMemberInfo(memberInfo);
	}

	/**
	 * 后台会员数统计
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Dto countMembers(Date startDate, Date endDate) {
		int allMemberCount = memberInfoDao.count(startDate, endDate);
		Dto dto = new BaseDto();
		dto.put("allMemberCount", allMemberCount);
		// 活跃会员数
		int activeMemberCount = userMoneyDao.countActiveMember(startDate, endDate);
		dto.put("activeMemberCount", activeMemberCount);
		return dto;
	}

	/**
	 * 增加用户
	 * 
	 * @param memberInfo
	 * @return memeberID
	 */
	public Integer saveMemberInfo(MemberInfo memberInfo) {
		Integer memeberID = null;
		String loginAccount = memberInfo.getLoginAccount();
		String loginPassword = memberInfo.getLoginPassword();
		// 是否开启邀请码
		if (CPSUtil.isNotEmpty(loginAccount)) {
			String salt = CipherUtil.createSalt();
			String visitCode = UniqueID.getUniqueID(6, 2);// 注册邀请码
			// String
			// pwdMd5=CipherUtil.generatePassword(loginPassword);//第一次加密md5，
			memberInfo.setSalt(salt);
			memberInfo.setVisitCode(visitCode);
			if (CPSUtil.isNotEmpty(memberInfo.getRecomCode())) {
				// 根据推荐人账户获取邀请码
				MemberInfo member = memberInfoDao.getUserByVCode(memberInfo.getRecomCode());
				if (CPSUtil.isNotEmpty(member)) {
					memberInfo.setRecomUser(member.getLoginAccount());
					memberInfo.setLevelCode(member.getLevelCode() + "|" + visitCode);

				}
			} else {
				memberInfo.setLevelCode(visitCode);
			}

			if (CPSUtil.isEmpty(memberInfo.getMemberName())) {
				memberInfo.setMemberName("zk_" + UniqueID.getUniqueID(8, 0));
			}
			memberInfo.setUserState(UserSateType.VALID.getType());// 默认有效
			memberInfo.setLoginPassword(CipherUtil.createPwdEncrypt(loginAccount, loginPassword, salt));
			memberInfo.setRegisterTime(new Date());
			memberInfo.setUpdateTime(new Date());
			memeberID = memberInfoDao.saveNMemberInfo(memberInfo);
		}
		return memeberID;
	}

	/**
	 * 编辑用户
	 * 
	 * @param memberInfo
	 * @return
	 */
	public boolean editMemberInfo(MemberInfo memberInfo) {
		return memberInfoDao.editMemberInfo(memberInfo);
	}

	/**
	 * 批量更新会员状态
	 * 
	 * @param memberIds
	 * @param state
	 * @return
	 */
	public boolean updateMemberSateByIds(String memberId, Integer state) {
		List<Integer> memberIds = new ArrayList<Integer>();
		if (CPSUtil.isNotEmpty(memberId)) {
			String[] mids = memberId.split(",");
			for (String mid : mids) {
				memberIds.add(Integer.valueOf(mid));
			}
		}
		return memberInfoDao.updateMemberSateByIds(memberIds, state);
	}

	public boolean updateMemberLevel(Integer levelCode, Integer memberId) {
		return memberInfoDao.updateMemberLevel(levelCode, memberId);
	}

	/**
	 * 重置密码
	 * 
	 * @param user_id
	 * @param password
	 * @return
	 */
	public boolean resetPassword(String user_id, String password) {
		boolean temp = false;
		if (CPSUtil.isNotEmpty(user_id)) {
			MemberInfo memberInfo = null;
			String userid[] = user_id.split(",");
			for (String uid : userid) {
				memberInfo = getUserByUserID(Integer.valueOf(uid));
				if (memberInfo != null) {
					memberInfo.setLoginPassword(
							CipherUtil.createPwdEncrypt(memberInfo.getLoginAccount(), password, memberInfo.getSalt()));
					temp = editMemberInfo(memberInfo);
				}
			}
		}
		return temp;
	}

	/**
	 * 删除用户
	 * 
	 * @param memberInfo
	 * @return
	 */
	public boolean trashMemberInfo(MemberInfo memberInfo) {
		return memberInfoDao.trashMemberInfo(memberInfo);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param user_ids
	 * @return
	 */
	public boolean trashMemberInfoList(String user_ids) {
		boolean temp = false;
		MemberInfo memberInfo = null;
		String user_id[] = null;
		List<MemberInfo> userList = null;
		if (CPSUtil.isNotEmpty(user_ids)) {
			userList = new ArrayList<MemberInfo>();
			user_id = user_ids.split(",");
			for (String uid : user_id) {
				memberInfo = new MemberInfo();
				memberInfo.setMemberId(Integer.valueOf(uid));
				userList.add(memberInfo);
			}
			// 删除关联资源

			// 删除用户信息
			temp = memberInfoDao.trashMemberInfoList(userList);
		}
		return temp;
	}

	@Override
	protected BaseDao<MemberInfo> getDao() {
		return this.memberInfoDao;
	}

	@Override
	public boolean checkLogin(String username, String password, String salt) {
		return memberInfoDao.checkLogin(username, CipherUtil.createPwdEncrypt(username, MD5.md5(password), salt));
	}

	@Override
	public MemberInfo getMemberGoldById(String memberId) {
		return memberInfoDao.getMemberGoldById(memberId);
	}

	@Override
	public int updateMemberGoldById(String memberId, Integer gold) {
		return memberInfoDao.updateMemberGoldById(memberId, gold);
	}
}
