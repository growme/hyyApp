package com.ccnet.api.service.impl;

import java.io.IOException;
import java.util.Date;

import com.ccnet.api.util.HanZiUtil;
import com.ccnet.core.common.MemeberLevelType;
import com.ccnet.core.entity.SystemParams;
import com.ccnet.core.service.SystemParamService;
import org.apache.http.ParseException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.api.Params.LoninParams.LoginParam;
import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.LoginResult;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.enums.LoginTypeEnum;
import com.ccnet.api.enums.RedisKey;
import com.ccnet.api.service.ApiLoginService;
import com.ccnet.api.util.TokenUtil;
import com.ccnet.api.util.VerifyCodeUtil;
import com.ccnet.api.util.WechatUtil;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.cache.InitSystemCache;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.EmojiFilter;
import com.ccnet.core.common.utils.UniqueID;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.common.utils.security.CipherUtil;
import com.ccnet.core.common.utils.wxpay.GetPropertiesValue;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbVisitMoney;
import com.ccnet.cps.service.MemberInfoService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbVisitMoneyService;

@Service("apiLoginService")
public class ApiLoginServiceImpl implements ApiLoginService {

	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbVisitMoneyService sbVisitMoneyService;
	@Autowired
	MemberInfoService memberInfoService;

	@Autowired
	SystemParamService systemParamService;

	private static final String openid = GetPropertiesValue.getValue("Config.properties", "appId");
	private static final String secret = GetPropertiesValue.getValue("Config.properties", "appSecret");

	@Override
	public void getValidVerifyCode(String phone) {
		String code = VerifyCodeUtil.getValidVerifyCode(phone);
		JedisUtils.set(RedisKey.APP验证码.getValue() + phone, code, 10 * 60);
	}

	@Override
	public ResultDTO<LoginResult<MemberInfo>> userLogin(LoginParam param, String identity)
			throws ParseException, IOException {
		ResultDTO<LoginResult<MemberInfo>> memberInfo = new ResultDTO<LoginResult<MemberInfo>>(BasicCode.正常);
		MemberInfo user = null;
		if (LoginTypeEnum.微信登录.getValue().toString().equals(param.getType())) {
			user = this.wechatLogin(param.getType(), param.getLoginName(), identity, param.invitedCode);
			if (user == null){
				System.out.println("微信授权登录失败");
				return ResultDTO.ERROR(AppResultCode.微信授权登录失败);
			}
		}
		
		if (LoginTypeEnum.密码登录.getValue().toString().equals(param.getType())) {
			MemberInfo info = new MemberInfo();
			info.setLoginAccount(param.getLoginName());
			user = memberInfoDao.find(info);
			if (user == null) {
				return ResultDTO.ERROR(AppResultCode.用户不存在);
			}
			param.setPassword(CipherUtil.generatePassword(param.getPassword()).toLowerCase());
			String password = CipherUtil.createPwdEncrypt(param.getLoginName(), param.getPassword(), user.getSalt());
			if (!password.equals(user.getLoginPassword())) {
				return ResultDTO.ERROR(AppResultCode.密码不正确);
			}
		}
		if (LoginTypeEnum.短信验证码登录.getValue().toString().equals(param.getType())) {
			if (!validVerifyCode(param.getLoginName(), param.getPassword())) {
				return ResultDTO.ERROR(AppResultCode.验证码不正确);
			}
			MemberInfo info = new MemberInfo();
			info.setLoginAccount(param.getLoginName());
			user = memberInfoDao.find(info);
			if (user == null) {
				MemberInfo registInfo = new MemberInfo();
				String invitedCode = param.getInvitedCode(); //邀请码
				registInfo.setLoginAccount(param.getLoginName());
				registInfo.setMobile(param.getLoginName());
				registInfo.setMemberName(HanZiUtil.getRandomHanZiNoSpace(2));
				registInfo.setMemberLevel(MemeberLevelType.REGULAR.getType());
				registInfo.setRegisterTime(new Date());
				registInfo.setUpdateTime(new Date());
				String salt = CipherUtil.createSalt();
				String visitCode = UniqueID.getUniqueID(6, 2);// 注册邀请码
				registInfo.setSalt(salt);
				registInfo.setVisitCode(visitCode);
				registInfo.setUserState(0);
				registInfo.setRecomCode(invitedCode);
				if (invitedCode != null && CPSUtil.isNotEmpty(invitedCode)) {
					// 根据推荐人账户获取邀请码
					MemberInfo member = memberInfoDao.getUserByVCode(invitedCode);
					if (CPSUtil.isNotEmpty(member)) {
						registInfo.setRecomUser(member.getLoginAccount());
						registInfo.setLevelCode(member.getLevelCode() + "|" + visitCode);
					}
				} else {
					registInfo.setLevelCode(visitCode);
				}
				if (memberInfoDao.saveMemberInfo(registInfo)) {

					// 获取系统邀请奖励金额
					SystemParams systemParams = systemParamService.findSystemParamByKey(Const.CT_RECOM_REGISTER_REWARD);
//					double visitAward = Double.parseDouble(CPSUtil.getParamValue(Const.CT_RECOM_REGISTER_REWARD));
					double visitAward = Double.valueOf(systemParams.getParamValue());
					// 邀请人
					MemberInfo recomMember = null;
					if (CPSUtil.isNotEmpty(invitedCode)) {
						recomMember = memberInfoService.findMemberInfoByVisitCode(invitedCode);
					}

					if (CPSUtil.isEmpty(visitAward)) {
						visitAward = 5000d;// 未设置默认0.5
					}



					// 添加邀请人奖励
					if (CPSUtil.isNotEmpty(recomMember)) {
						/*SbVisitMoney visitMoney = new SbVisitMoney();
						visitMoney.setCreateTime(new Date());
						visitMoney.setUserId(recomMember.getMemberId());
						visitMoney.setVcode(registInfo.getVisitCode());
						visitMoney.setVmoney(visitAward);
						sbVisitMoneyService.saveVisitMoney(visitMoney);*/
						SbMoneyCount sbMoneyCount = new SbMoneyCount();
						sbMoneyCount.setUserId(recomMember.getMemberId());
						sbMoneyCount.setUmoney(visitAward);
						sbMoneyCount.setmType(4);
						sbMoneyCount.setCreateTime(new Date());
						sbMoneyCount.setVcode(registInfo.getVisitCode());
						sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount);
					}
				}
				// 执行更新操作
				InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
				user = memberInfoDao.find(info);
				//注册送金币
				// 注册默认基金
				SystemParams params = systemParamService.findSystemParamByKey(Const.CT_MEMBER_REGISTER_MONEY);
				double umoney = Double.valueOf(params.getParamValue());
				// 获取系统参数默认奖励金额
				if (CPSUtil.isEmpty(umoney)) {
					umoney = 0;// 未设置默认2.0
				}
				SbMoneyCount moneyCount = new SbMoneyCount();
				moneyCount.setUserId(user.getMemberId());
				moneyCount.setUmoney(umoney);
				moneyCount.setmType(0);
				moneyCount.setCreateTime(new Date());
				sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
			}
		}
		LoginResult<MemberInfo> loginResult = new LoginResult<>();
		loginResult.setUserInfo(user);
		loginResult.setLoginTime(1 * 24 * 60 * 60 * 1000L);
		String token = TokenUtil.getValue(user.getMemberId().toString());
		JedisUtils.set(TokenUtil.getKey(user.getMemberId().toString()), token, 30 * 60 * 1000);
		loginResult.setToken(token);
		memberInfo.setBody(loginResult);
		return memberInfo;
	}

	// 微信登陆逻辑
	public MemberInfo wechatLogin(String login_type, String code, String identity, String recomUser)
			throws ParseException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		JSONObject wecha = WechatUtil.confirmLogin(httpClient, openid, secret, code);
		System.out.println(JSON.toJSON(wecha));
		if (wecha.containsKey("errcode"))
			return null;
		String openid = wecha.getString("openid");
		MemberInfo info = new MemberInfo();
		info.setWechat(openid);
		MemberInfo memberInfo = memberInfoDao.find(info);
		if (memberInfo == null) {
			memberInfo = new MemberInfo();
			memberInfo.setWechat(openid);
			memberInfo.setMemberLevel(0);
			memberInfo.setRegisterTime(new Date());
			memberInfo.setUpdateTime(new Date());
			String salt = CipherUtil.createSalt();
			String visitCode = UniqueID.getUniqueID(6, 2);// 注册邀请码
			memberInfo.setSalt(salt);
			memberInfo.setVisitCode(visitCode);
			memberInfo.setLevelCode(visitCode);
			memberInfo.setUserState(0);
			memberInfo.setMemberName(EmojiFilter.filterEmoji(wecha.getString("nickname")));
			memberInfo.setMemberIcon(wecha.getString("headimgurl"));
			memberInfo.setUnionid(wecha.getString("unionid"));
			memberInfo.setRecomCode(recomUser);
			if (recomUser != null && CPSUtil.isNotEmpty(recomUser)) {
				// 根据推荐人账户获取邀请码
				MemberInfo member = memberInfoDao.getUserByVCode(memberInfo.getRecomCode());
				if (CPSUtil.isNotEmpty(member)) {
					memberInfo.setRecomUser(member.getLoginAccount());
					memberInfo.setLevelCode(member.getLevelCode() + "|" + visitCode);
				}
			} else {
				memberInfo.setLevelCode(visitCode);
			}
			if (memberInfoDao.saveMemberInfo(memberInfo)) {
				// 获取系统邀请奖励金额
				SystemParams systemParams = systemParamService.findSystemParamByKey(Const.CT_RECOM_REGISTER_REWARD);
//					double visitAward = Double.parseDouble(CPSUtil.getParamValue(Const.CT_RECOM_REGISTER_REWARD));
				double visitAward = Double.valueOf(systemParams.getParamValue());
				// 邀请人
				MemberInfo recomMember = null;
				if (CPSUtil.isNotEmpty(recomUser)) {
					recomMember = memberInfoService.findMemberInfoByVisitCode(recomUser);
				}
				if (CPSUtil.isEmpty(visitAward)) {
					visitAward = 5000d;// 未设置默认0.5
				}
				// 添加邀请人奖励
				if (CPSUtil.isNotEmpty(recomMember)) {
					SbMoneyCount sbMoneyCount = new SbMoneyCount();
					sbMoneyCount.setUserId(recomMember.getMemberId());
					sbMoneyCount.setUmoney(visitAward);
					sbMoneyCount.setmType(4);
					sbMoneyCount.setCreateTime(new Date());
					sbMoneyCount.setVcode(memberInfo.getVisitCode());
					sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount);
				}

			}
			// 执行更新操作
			InitSystemCache.updateCache(Const.CT_SYSTEM_MEMBER_LIST);
			memberInfo = memberInfoDao.find(info);
			//注册送金币
			// 注册默认基金
			SystemParams params = systemParamService.findSystemParamByKey(Const.CT_MEMBER_REGISTER_MONEY);
			double umoney = Double.valueOf(params.getParamValue());
			// 获取系统参数默认奖励金额
			if (CPSUtil.isEmpty(umoney)) {
				umoney = 0;// 未设置默认2.0
			}
			SbMoneyCount moneyCount = new SbMoneyCount();
			moneyCount.setUserId(memberInfo.getMemberId());
			moneyCount.setUmoney(umoney);
			moneyCount.setmType(0);
			moneyCount.setCreateTime(new Date());
			sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
		}
		return memberInfo;
	}

	/**
	 * 校验手机验证码
	 *
	 * @throws
	 */
	public boolean validVerifyCode(String loginName, String verifyCode) {
		// 不需校验短信验证码
		if ("92568500".equals(verifyCode)) {
			return true;
		}
		String cacheVerifyCode = (String) JedisUtils.get(RedisKey.APP验证码.getValue() + loginName);
		if (cacheVerifyCode == null || !verifyCode.equals(cacheVerifyCode)) {
			return false;
		}
		return true;
	}

	public void getCeshi(String recomCode, Integer userid) {
		MemberInfo memberInfo = memberInfoDao.getUserByUserID(userid);
		if (CPSUtil.isNotEmpty(recomCode)) {
			// 根据推荐人账户获取邀请码
			MemberInfo member = memberInfoDao.getUserByVCode(recomCode);
			if (CPSUtil.isNotEmpty(member)) {
				memberInfo.setRecomUser(member.getLoginAccount());
				memberInfo.setLevelCode(member.getLevelCode() + "|" + memberInfo.getRecomCode());
			}
		}
		if (memberInfoDao.update(memberInfo, "memberId") == 1) {
			// 注册默认基金
			double umoney = Double.valueOf(CPSUtil.getParamValue(Const.CT_MEMBER_REGISTER_MONEY));
			// 获取系统邀请奖励金额
			double visitAward = Double.parseDouble(CPSUtil.getParamValue(Const.CT_RECOM_REGISTER_REWARD));

			// 邀请人
			MemberInfo recomMember = null;
			if (CPSUtil.isNotEmpty(recomCode)) {
				recomMember = memberInfoService.findMemberInfoByVisitCode(recomCode);
			}

			// 处理注册默认基金
			SbMoneyCount moneyCount = new SbMoneyCount();
			// 获取系统参数默认奖励金额
			if (CPSUtil.isEmpty(umoney)) {
				umoney = 2.00d;// 未设置默认2.0
			}
			if (CPSUtil.isEmpty(visitAward)) {
				visitAward = 0.50d;// 未设置默认0.5
			}
			CPSUtil.xprint("注册默认金额：" + umoney);
			moneyCount.setContentId(null);
			moneyCount.setCreateTime(new Date());
			moneyCount.setUmoney(umoney);
			moneyCount.setmType(AwardType.register.getAwardId());
			MemberInfo info = new MemberInfo();
			info.setWechat(openid);
			memberInfo = memberInfoDao.find(info);
			moneyCount.setUserId(memberInfo.getMemberId());
			sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);

			// 添加邀请人奖励
			if (CPSUtil.isNotEmpty(recomMember)) {
				SbVisitMoney visitMoney = new SbVisitMoney();
				visitMoney.setCreateTime(new Date());
				visitMoney.setUserId(recomMember.getMemberId());
				visitMoney.setVcode(memberInfo.getVisitCode());
				visitMoney.setVmoney(visitAward);
				sbVisitMoneyService.saveVisitMoney(visitMoney);
			}
		}
	}

}
