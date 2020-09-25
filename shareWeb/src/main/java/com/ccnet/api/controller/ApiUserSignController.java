package com.ccnet.api.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccnet.api.dao.ApiSbMoneyCount;
import com.ccnet.api.dao.ApiTaskInfoDao;
import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.MessageKey;
import com.ccnet.core.common.ResponseCode;
import com.ccnet.core.common.ShareType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.DeviceUtils;
import com.ccnet.core.common.utils.IPUtil;
import com.ccnet.core.common.utils.StringHelper;
import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.dataconvert.Dto;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.dao.SbUserMoneyDao;
import com.ccnet.cps.entity.MemberInfo;
import com.ccnet.cps.entity.SbContentInfo;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.entity.SbShareLog;
import com.ccnet.cps.entity.SbSignInfo;
import com.ccnet.cps.service.SbContentInfoService;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbShareLogService;
import com.ccnet.cps.service.SbSignInfoService;

/**
 * 签到
 */
@Controller
@RequestMapping("/api/userSign/")
public class ApiUserSignController extends BaseController<SbSignInfo> {

	@Autowired
	SbSignInfoService sbSignInfoService;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	SbUserMoneyDao sbUserMoneyDao;

	@Autowired
	ApiTaskInfoDao taskInfoDao;

	@Autowired
	ApiSbMoneyCount apiSbMoneyCount;
	@Autowired
	SbContentInfoService sbContentInfoService;
	@Autowired
	SbShareLogService shareLogService;

	@RequestMapping("index")
	@ResponseBody
	public ResultDTO<?> index(Headers header, HttpServletRequest request) {
		try {
			// 获取签到数据
			header = header.update(request);
			if (StringUtils.isBlank(header.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			double sysBonus = 0d;
			double addBonus = 0d;
			String sys_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_BONUS);// 签到基金
			String add_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_ADD_BONUS);// 连续签到叠加金额
			if (CPSUtil.isEmpty(sys_bonus)) {
				sysBonus = 0.05d;// 系统未设置则默认0.05
			} else {
				sysBonus = Double.parseDouble(sys_bonus);
			}
			if (CPSUtil.isEmpty(add_bonus)) {
				addBonus = 0.01d;// 系统未设置则默认0.01
			} else {
				addBonus = Double.parseDouble(add_bonus);
			}

			SbSignInfo sbSignInfo = sbSignInfoService.getSbSignInfoById(memberInfo.getMemberId());
			double signMoney = 0.0d;
			if (CPSUtil.isNotEmpty(sbSignInfo)) {// 有签到数据
				String lastSingDate = DateUtils.formatDate(sbSignInfo.getLastSignTime(), DateUtils.parsePatterns[3]);
				long subDays = DateUtils.getMDaySub(lastSingDate, CPSUtil.getCurrentTime());
				CPSUtil.xprint("subDays=" + subDays);
				if (subDays <= 1) {
					if (subDays == 0) {
						signMoney = sbSignInfo.getSignMoney();
					} else {
						signMoney = sbSignInfo.getSignMoney() + addBonus;
					}
				} else {// 不连续
					signMoney = sysBonus;
				}
			} else {
				signMoney = sysBonus;// 默认值
			}
			Map<String, Object> map = new HashMap<String, Object>();
			if (checkTodaySign(memberInfo.getMemberId())) {
				map.put("jrqd", "1");// 已签到
			} else {
				map.put("jrqd", "0");// 未签到
			}
			map.put("qdjj", sysBonus);
			map.put("djje", addBonus);
			map.put("signMoney", CPSUtil.formatDoubleVal(signMoney, "0.00"));
			map.put("signInfo", sbSignInfo);
			map.put(Const.MENU_SELECTED_INDEX, "home");
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 判断今日是否签到
	 * 
	 * @param userId
	 * @return
	 */
	private boolean checkTodaySign(Integer userId) {
		boolean temp = false;
		String endDate = CPSUtil.getCurDate();
		String startDate = CPSUtil.getCurDate();
		if (CPSUtil.isNotEmpty(userId)) {
			SbMoneyCount moneyCount = sbMoneyCountService.getSbMoneyCountByType(userId, AwardType.redpacke.getAwardId(),
					startDate, endDate);
			if (CPSUtil.isNotEmpty(moneyCount)) {
				temp = true;
			}
		}
		return temp;
	}

	/**
	 * 开宝箱
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "chest", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> chest(Headers header, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			Map<String, Object> map = new HashMap<String, Object>(0);
			if (CPSUtil.isNotEmpty(memberInfo)) {
				Integer ints = apiSbMoneyCount.countShareLog(Integer.valueOf(header.getUserid()));
				if (ints == 0) {
					return ResultDTO.ERROR(AppResultCode.需要完成文章分享开启宝箱);
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				// String start_update = format.format(cal.getTime());
				cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 4);
				String end_update = format.format(cal.getTime());
				SbMoneyCount t = apiSbMoneyCount.getUserLastBx(memberInfo.getMemberId(), end_update);
				if (!CPSUtil.isNotEmpty(t)) {
					SbMoneyCount sbMoneyCount = new SbMoneyCount();
					sbMoneyCount.setCreateTime(new Date());
					sbMoneyCount.setmType(10);
					sbMoneyCount.setUmoney(0.1);
					sbMoneyCount.setUserId(memberInfo.getMemberId());
					if (sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount)) {
						return ResultDTO.OK();
					} else {
						return ResultDTO.INFO(ResultCode.BasicCode.不可操作, "添加失败！");
					}
				} else {
					return ResultDTO.INFO(ResultCode.BasicCode.不可操作, "添加失败！");
				}
			} else {
				map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(), "非法请求,请稍后再试!");
				return ResultDTO.OK(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 开宝箱时间
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "chestTime", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> chestTime(Headers header, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			Map<String, Object> map = new HashMap<String, Object>(0);
			SbMoneyCount t = apiSbMoneyCount.getUserMoneyLastTime(Integer.valueOf(header.getUserid()));
			if (t == null || t.getUserId() == null) {
				map.put("time", new Date());
				return ResultDTO.OK(map);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(t.getCreateTime());
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 4);
			map.put("time", cal.getTime());
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 每日签到
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "bonus", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> dailyBonus(Headers header, HttpServletRequest request) {
		try {
			// 获取签到数据
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			try {
				Dto dto = getParamAsDto();
				SbSignInfo sbSignInfo = null;
				Map<String, Object> map = new HashMap<String, Object>(0);
				if (CPSUtil.isNotEmpty(memberInfo)) {
					double sysBonus = 0d;
					double addBonus = 0d;
					Integer userId = memberInfo.getMemberId();
					String sys_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_BONUS);// 签到基金
					String add_bonus = CPSUtil.getParamValue(Const.CT_DEFAULT_SIGN_ADD_BONUS);// 连续签到叠加金额
					if (CPSUtil.isEmpty(sys_bonus)) {
						sysBonus = 0.05d;// 系统未设置则默认0.05
					} else {
						sysBonus = Double.parseDouble(sys_bonus);
					}
					if (CPSUtil.isEmpty(add_bonus)) {
						addBonus = 0.01d;// 系统未设置则默认0.01
					} else {
						addBonus = Double.parseDouble(add_bonus);
					}

					// 判断只有当日产生了推广收益的用户才能签到
					if (!checkExistMoneyCount(userId, dto)) {
						map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
						map.put(MessageKey.msg.name(), "必须是当日分享了文章并且有收益，才能签到哦！");
						return ResultDTO.OK(map);
					}

					// 查询当前用户今日是否已领取奖励
					if (checkTodaySign(userId)) {
						map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
						map.put(MessageKey.msg.name(), "亲，您今天已经签到过了!");
						return ResultDTO.OK(map);
					}

					// 获取用户签到数据
					boolean success = false;
					sbSignInfo = sbSignInfoService.getSbSignInfoById(userId);
					Integer seriesCount = 0;// 续签次数
					Integer totalCount = 0;// 累签次数
					double totalMoney = 0.00d;// 累计奖励
					double signMoney = 0.00d;// 签到基金
					double _totalMoney = 0.00d;
					double _signMoney = 0.00d;
					if (CPSUtil.isNotEmpty(sbSignInfo)) {
						seriesCount = sbSignInfo.getSeriesCount();// 续签次数
						totalCount = sbSignInfo.getTotalCount();// 累签次数
						totalMoney = sbSignInfo.getTotalMoney();// 累计奖励
						signMoney = sbSignInfo.getSignMoney();// 签到基金
						// 判断当前时间-上次签到时间是否在一天内
						String lastSingDate = DateUtils.formatDate(sbSignInfo.getLastSignTime(),
								DateUtils.parsePatterns[3]);
						if (DateUtils.getMDaySub(lastSingDate, CPSUtil.getCurrentTime()) <= 1) {
							_totalMoney = totalMoney + signMoney + addBonus;
							_signMoney = signMoney + addBonus;
							sbSignInfo.setSeriesCount(seriesCount + 1);
							sbSignInfo.setTotalCount(totalCount + 1);
							sbSignInfo.setSignMoney(_signMoney);
							sbSignInfo.setTotalMoney(_totalMoney);
						} else {
							_totalMoney = totalMoney + sysBonus;
							_signMoney = sysBonus;
							sbSignInfo.setSeriesCount(1);
							sbSignInfo.setTotalCount(totalCount + 1);
							sbSignInfo.setSignMoney(sysBonus);
							sbSignInfo.setTotalMoney(_totalMoney);
						}
						sbSignInfo.setUserId(userId);
						sbSignInfo.setLastSignTime(new Date());
						success = sbSignInfoService.editSbSignInfo(sbSignInfo);

					} else {// 用户首次签到直接插入数据
						_totalMoney = sysBonus;
						_signMoney = sysBonus;
						sbSignInfo = new SbSignInfo();
						sbSignInfo.setUserId(userId);
						sbSignInfo.setSeriesCount(1);
						sbSignInfo.setTotalCount(1);
						sbSignInfo.setSignMoney(sysBonus);
						sbSignInfo.setTotalMoney(sysBonus);
						sbSignInfo.setLastSignTime(new Date());
						success = sbSignInfoService.saveSbSignInfo(sbSignInfo);
					}

					if (success) {
						SbMoneyCount sbMoneyCount = new SbMoneyCount();
						sbMoneyCount.setCreateTime(new Date());
						sbMoneyCount.setmType(AwardType.redpacke.getAwardId());
						sbMoneyCount.setUmoney(_signMoney);
						sbMoneyCount.setUserId(userId);
						if (sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount)) {
							map.put(MessageKey.apicode.name(), ResponseCode.CommSuccess.getCode());
							map.put(MessageKey.msg.name(),
									"签到成功领取" + CPSUtil.formatDoubleVal(_signMoney, "0.00") + "元红包");
							map.put("money", CPSUtil.formatDoubleVal(_signMoney, "0.00"));
							return ResultDTO.OK(map);
						} else {
							map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
							map.put(MessageKey.msg.name(), "签到失败,请稍后再试!");
							return ResultDTO.OK(map);
						}
					}
				} else {
					map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
					map.put(MessageKey.msg.name(), "非法请求,请稍后再试!");
					return ResultDTO.OK(map);
				}

			} catch (Exception e) {
				e.printStackTrace();
				Map<String, Object> map = new HashMap<String, Object>(0);
				map.put(MessageKey.apicode.name(), ResponseCode.CommError.getCode());
				map.put(MessageKey.msg.name(), "服务器异常,请稍后再试!");
				return ResultDTO.OK(map);
			}
			return ResultDTO.OK();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 判断用户是否产生推广收益
	 * 
	 * @param userId
	 * @param paramDto
	 * @return
	 */
	private boolean checkExistMoneyCount(Integer userId, Dto paramDto) {
		boolean temp = false;
		SbMoneyCount moneyCount = new SbMoneyCount();
		if (CPSUtil.isNotEmpty(userId)) {
			moneyCount.setUserId(userId);
			moneyCount.setmType(AwardType.readawd.getAwardId());
			Double earnCount = sbMoneyCountService.getCurrentUserMoneyCount(moneyCount, paramDto);
			if (CPSUtil.isNotEmpty(earnCount) && earnCount > 0) {
				temp = true;
			}
		}
		return temp;
	}

	/**
	 * 接收用户分享数据统计
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "share/receive", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> receiveShareData(Headers header, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 获取参数
			Dto dto = getParamAsDto();
			Integer userId = Integer.valueOf(header.getUserid());
			Integer contentId = dto.getAsInteger("cid");
			Integer shareType = dto.getAsInteger("stp");
			CPSUtil.xprint("++++++++pid=" + userId + " cid=" + contentId + " stp=" + shareType);
			// 获取请求人IP
			String requestIp = IPUtil.getIpAddr(request);
			// 获取客户端信息
			String deviceDetail = DeviceUtils.getDevDetailInfo(request);
			CPSUtil.xprint("++++++++shareId=" + requestIp);
			CPSUtil.xprint("++++++++shareDevice=" + deviceDetail);
			// 判断参数
			if (!StringHelper.checkParameter(userId, contentId, shareType)) {
				CPSUtil.xprint("++++++++NoParamError++++++++");
				return ResultDTO.ERROR(BasicCode.参数错误);
			}

			// 判断会员存在
			SbContentInfo content = CPSUtil.getContentInfoById(Integer.valueOf(contentId));
			if (CPSUtil.isEmpty(content)) {
				content = sbContentInfoService.getSbContentInfoByID(contentId);
			}
			if (CPSUtil.isEmpty(content)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}

			// 判断会员存在
			MemberInfo member = memberInfoDao.getUserByUserID(userId);
			if (CPSUtil.isEmpty(member)) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}

			// 处理文章分享次数
			sbContentInfoService.updateContentShareNum(contentId, content.getShareNum() + 1);// 递增1
			Integer givenMoney = 0;
			Double shareMoney = 0d;
			if (ShareType.WXPYQ.getType().equals(shareType)) {// 朋友圈
				shareMoney = content.getTimelineShareAward();
				CPSUtil.xprint("朋友圈分享价格:" + shareMoney);
			} else {// 好友分享
				shareMoney = content.getFriendShareAward();
				CPSUtil.xprint("好友分享价格:" + shareMoney);
			}

			// 保存日志数据
			SbShareLog shareLog = new SbShareLog();
			shareLog.setShareIp(requestIp);
			shareLog.setShareType(shareType);
			shareLog.setShareTime(new Date());
			shareLog.setUserId(userId);
			shareLog.setGivenMoney(givenMoney);
			shareLog.setShareMoney(shareMoney);
			shareLog.setDeviceDetail(deviceDetail);
			shareLog.setContentId(contentId);
			CPSUtil.xprint("++++++++saveSbShareLog++++++++");
			shareLogService.saveSbShareLog(shareLog);

			// 处理返回提示
			Map<String, Object> map = new HashMap<String, Object>(0);
			map.put(MessageKey.apicode.name(), ResponseCode.CommSuccess.getCode());
			map.put(MessageKey.msg.name(), "分享成功");
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	/**
	 * 开宝箱
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping(value = "sfTask", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> sfTask(Headers header, HttpServletRequest request, Integer type, Double gold) {
		try {
			if (StringUtils.isBlank(header.getUserid())) {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(header.getUserid()));
			if (CPSUtil.isNotEmpty(memberInfo)) {
				SbMoneyCount sbMoneyCount = new SbMoneyCount();
				sbMoneyCount.setCreateTime(new Date());
				sbMoneyCount.setmType(type);
				sbMoneyCount.setUmoney(gold);
				sbMoneyCount.setUserId(memberInfo.getMemberId());
				if (sbMoneyCountService.saveSbMoneyCountInfo(sbMoneyCount)) {
					return ResultDTO.OK();
				} else {
					return ResultDTO.INFO(ResultCode.BasicCode.不可操作, "添加失败！");
				}
			} else {
				return ResultDTO.INFO(ResultCode.BasicCode.不可操作, "添加失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}
}
