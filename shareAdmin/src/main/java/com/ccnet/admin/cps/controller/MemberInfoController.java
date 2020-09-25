package com.ccnet.admin.cps.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ccnet.admin.controller.AdminBaseController;
import com.ccnet.admin.controller.api.HessianClientUtil;
import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.common.UserSateType;
import com.ccnet.core.common.UserType;
import com.ccnet.core.common.ajax.AjaxRes;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.base.ResourceTypes;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.common.utils.security.UserInfoShiroUtil;
import com.ccnet.core.dao.base.Page;
import com.ccnet.core.entity.UserInfo;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.service.MemberInfoService;

/**
 * 会员管理
 * 
 * @author jackie wang
 *
 */
@Controller
@RequestMapping("/backstage/member/")
public class MemberInfoController extends AdminBaseController<MemberInfo> {

	// 静态地址
	public static String INDEX_URL = "/backstage/member/index";
	public static String GO_ADD_URL = "/backstage/member/goadd";
	public static String GO_EDIT_URL = "/backstage/member/goedit";
	public static String RESET_PWD_URL = "/backstage/member/resetpwd";

	@Autowired
	private MemberInfoService memberInfoService;

	/**
	 * 会员管理首页
	 */
	@RequestMapping("index")
	public ModelAndView memberIndex() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		Page<MemberInfo> page = newPage(paramDto);
		if (isAuthedReq(ResourceTypes.MENU)) {
			MemberInfo memberInfo = new MemberInfo();
			page = memberInfoService.findMemberInfoByPage(memberInfo, page, paramDto);
			mav.addObject("pm", page);
			mav.addObject("member_state", paramDto.getAsInteger("member_state"));
			mav.addObject("end_date", paramDto.getAsString("end_date"));
			mav.addObject("start_date", paramDto.getAsString("start_date"));
			mav.addObject("memberId", paramDto.getAsString("memberId"));
			mav.addObject("stlist", UserSateType.all());
			mav.addObject("queryParam", paramDto.getAsString("queryParam"));
			mav.addObject("permitFBtn", getPermitBtn(ResourceTypes.FUNC));
			mav.addObject("permitTBtn", getPermitBtn(ResourceTypes.BUTTON));
			mav.addObject(Const.CT_MENU_NAV, "会员管理");
			mav.setViewName("admincps/jsp/member/member_list");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 添加会员
	 * 
	 * @return
	 */
	@RequestMapping(value = "goadd", method = RequestMethod.GET)
	public ModelAndView goAddMember() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC)) {
			mav.addObject("flag", "0");
			mav.addObject("stlist", UserSateType.all());
			mav.addObject("lvlist", MemeberLevelType.values());
			mav.addObject(Const.CT_MENU_NAV, "添加会员");
			mav.setViewName("admincps/jsp/member/member_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 修改会员
	 * 
	 * @return
	 */
	@RequestMapping(value = "goedit", method = RequestMethod.GET)
	public ModelAndView goEditMember() {
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			Dto paramDto = getParamAsDto();
			Integer flag = paramDto.getAsInteger("flag");
			Integer member_id = paramDto.getAsInteger("member_id");
			MemberInfo memberInfo = new MemberInfo();
			if (CPSUtil.isNotEmpty(member_id)) {
				memberInfo = memberInfoService.getUserByUserID(member_id);
			}
			mav.addObject("flag", flag);
			mav.addObject("memberInfo", memberInfo);
			mav.addObject("stlist", UserSateType.all());
			mav.addObject("lvlist", MemeberLevelType.values());
			mav.setViewName("admincps/jsp/member/member_info");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 增加会员
	 * 
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveUser(MemberInfo memberInfo) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.FUNC, GO_ADD_URL))) {
			try {
				String recomCode = memberInfo.getRecomUser();
				if (CPSUtil.isNotEmpty(memberInfo.getMobile())) {
					memberInfo.setLoginAccount(memberInfo.getMobile());
				}
				if (checkAccExist(memberInfo)) {
					ar.setFailMsg(Const.ACCOUNT_EXIST);
				} else {
					if (CPSUtil.isNotEmpty(recomCode)) {
						if (!recomCode.equals(memberInfo.getLoginAccount())) {
							if (CPSUtil.isNotEmpty(memberInfoService.findFormatByLoginName(recomCode))) {
								dealSaveMember(ar, memberInfo);
							} else {
								ar.setFailMsg(Const.RECOM_DATA_UNEXIST);
							}
						} else {
							ar.setFailMsg(Const.RECOM_DATA_SAME);
						}
					} else {
						dealSaveMember(ar, memberInfo);
					}
				}

			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 检查账号
	 * @param memberInfo
	 * @return
	 */
	public boolean checkAccExist(MemberInfo memberInfo) {
		boolean temp = false;
		try {
			// 验证码账号是否存在
			if (CPSUtil.isNotEmpty(memberInfo.getLoginAccount())) {
				MemberInfo member = memberInfoService.findFormatByLoginName(memberInfo.getLoginAccount());
				if (member != null) {
					if (CPSUtil.isNotEmpty(memberInfo.getMemberId())) {
						if (memberInfo.getMemberId().equals(member.getMemberId())) {
							temp = false;
						} else {
							temp = true;
						}
					} else {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 修改会员
	 * 
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes editUser(MemberInfo memberInfo) {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON, GO_EDIT_URL))) {
			try {
				// 验证账户
				if (checkAccExist(memberInfo)) {
					ar.setFailMsg(Const.ACCOUNT_EXIST);
				} else {
					String recomCode = memberInfo.getRecomUser();
					MemberInfo omember = memberInfoService.getUserByUserID(memberInfo.getMemberId());
					if (CPSUtil.isNotEmpty(omember)) {
						omember.setUpdateTime(new Date());
						omember.setMemberLevel(memberInfo.getMemberLevel());
						omember.setMobile(memberInfo.getMobile());
						omember.setLoginAccount(memberInfo.getMobile());
						omember.setMemberName(memberInfo.getMemberName());
						omember.setUserState(memberInfo.getUserState());
						omember.setQqNum(memberInfo.getQqNum());
						omember.setPayAccount(memberInfo.getPayAccount());
						omember.setAccountName(memberInfo.getAccountName());
						omember.setDisscount(memberInfo.getDisscount());
						// memberInfo.setUpdateTime(new Date());
						// memberInfo.setSalt(omember.getSalt());
						// memberInfo.setRegisterTime(omember.getRegisterTime());
						// memberInfo.setLoginPassword(omember.getLoginPassword());
						// memberInfo.setVisitCode(omember.getVisitCode());
						// memberInfo.setLevelCode(omember.getLevelCode());
						if (CPSUtil.isNotEmpty(memberInfo.getRecomUser())) {
							omember.setRecomUser(memberInfo.getRecomUser());
						}

						if (CPSUtil.isNotEmpty(recomCode)) {
							if (!recomCode.equals(memberInfo.getMemberId())) {
								MemberInfo fInfo1 = memberInfoService.getUserByUserID(Integer.valueOf(recomCode));
								if (CPSUtil.isNotEmpty(fInfo1)) {
									omember.setRecomCode(fInfo1.getVisitCode());
									omember.setLevelCode(fInfo1.getLevelCode() + "|" + omember.getVisitCode());
									dealEditMember(ar, omember);
								} else {
									ar.setFailMsg(Const.RECOM_DATA_UNEXIST);
								}
							} else {
								ar.setFailMsg(Const.RECOM_DATA_SAME);
							}
						} else {
							dealEditMember(ar, omember);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.SAVE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 处理保存
	 * 
	 * @param ar
	 * @param memberInfo
	 */
	private void dealSaveMember(AjaxRes ar, MemberInfo memberInfo) {
		Integer memberID = memberInfoService.saveMemberInfo(memberInfo);
		if (CPSUtil.isNotEmpty(memberID)) {
			ar.setObj(memberID);
			ar.setSucceedMsg(Const.SAVE_SUCCEED);
			// 执行更新操作
			InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
			HessianClientUtil.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
		} else {
			ar.setFailMsg(Const.SAVE_FAIL);
		}
	}

	/**
	 * 处理更新
	 * 
	 * @param ar
	 * @param memberInfo
	 */
	private void dealEditMember(AjaxRes ar, MemberInfo memberInfo) {
		if (memberInfoService.editMemberInfo(memberInfo)) {
			ar.setSucceedMsg(Const.UPDATE_SUCCEED);
			// 执行更新操作
			InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
			HessianClientUtil.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
		} else {
			ar.setFailMsg(Const.UPDATE_FAIL);
		}
	}

	/**
	 * 冻结/激活 用户
	 * 
	 * @param user_id
	 * @param user_state
	 * @return
	 */
	@RequestMapping(value = "freeze", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes freezeMemeber() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC))) {
			try {
				Dto paramDto = getParamAsDto();
				String member_id = paramDto.getAsString("member_id");
				Integer user_state = paramDto.getAsInteger("user_state");
				if (memberInfoService.updateMemberSateByIds(member_id, user_state)) {
					ar.setSucceedMsg(Const.UPDATE_SUCCEED);
					// 执行更新操作
					InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
					HessianClientUtil.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
				} else {
					ar.setFailMsg(Const.UPDATE_FAIL);
				}
			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		}
		return ar;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "resetpwd", method = RequestMethod.GET)
	public ModelAndView resetMemberPwd() {
		Dto paramDto = getParamAsDto();
		ModelAndView mav = new ModelAndView();
		if (isAuthedReq(ResourceTypes.FUNC) || isAuthedReq(ResourceTypes.BUTTON)) {
			String user_id = paramDto.getAsString("member_id");
			mav.addObject("userId", user_id);
			mav.setViewName("admincps/jsp/member/reset_member_pwd");
		} else {
			mav.setViewName(Const.NO_AUTHORIZED_URL);
		}
		return mav;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@RequestMapping(value = "savepwd", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes saveMemberPwd() {
		AjaxRes ar = getAjaxRes();
		Dto paramDto = getParamAsDto();
		if (ar.setNoAuth(
				isAuthedReq(ResourceTypes.BUTTON, RESET_PWD_URL) || isAuthedReq(ResourceTypes.FUNC, RESET_PWD_URL))) {
			try {
				String user_id = paramDto.getAsString("memberId");
				String password = paramDto.getAsString("loginPassword");
				UserInfo currentUser = UserInfoShiroUtil.getCurrentUser();
				Integer userType = currentUser.getUserType();
				// 系统管理员才有权限重置密码
				if (userType.equals(UserType.SYSTEMADMIN.getType()) || userType.equals(UserType.ADMIN.getType())) {
					if (CPSUtil.isNotEmpty(user_id)) {
						if (memberInfoService.resetPassword(user_id, password)) {
							ar.setSucceedMsg(Const.UPDATE_SUCCEED);
							// 执行更新操作
							InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
							HessianClientUtil.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
						} else {
							ar.setFailMsg(Const.UPDATE_FAIL);
						}
					} else {
						ar.setFailMsg(Const.NO_PARAM_ERROR);
					}
				} else {
					ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
				}
			} catch (Exception e) {
				e.printStackTrace();
				ar.setFailMsg(Const.UPDATE_FAIL);
			}
		} else {
			ar.setFailMsg(Const.NO_AUTHORIZED_MSG);
		}
		return ar;
	}

	/**
	 * 删除会员
	 * 
	 * @return
	 */
	@RequestMapping(value = "trash", method = RequestMethod.POST)
	@ResponseBody
	public AjaxRes trashMemeber() {
		AjaxRes ar = getAjaxRes();
		if (ar.setNoAuth(isAuthedReq(ResourceTypes.BUTTON) || isAuthedReq(ResourceTypes.FUNC))) {
			try {
				Dto paramDto = getParamAsDto();
				String member_id = paramDto.getAsString("member_id");
				if (CPSUtil.isNotEmpty(member_id)) {
					if (memberInfoService.trashMemberInfoList(member_id)) {
						ar.setSucceedMsg(Const.DEL_SUCCEED);
						// 执行更新操作
						InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
						HessianClientUtil.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
					} else {
						ar.setFailMsg(Const.DEL_FAIL);
					}
				} else {
					ar.setFailMsg(Const.NO_PARAM_ERROR);
				}

			} catch (Exception e) {
				logger.error(e.toString(), e);
				ar.setFailMsg(Const.DEL_FAIL);
			}
		}
		return ar;
	}
}
