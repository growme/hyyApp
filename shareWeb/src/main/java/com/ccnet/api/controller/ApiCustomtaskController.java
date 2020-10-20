package com.ccnet.api.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ccnet.api.entity.AppResultCode;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.SbCustomtask;
import com.ccnet.api.service.SbCustomtaskService;
import com.ccnet.api.util.DoubleUtil;
import com.ccnet.core.common.AwardType;
import com.ccnet.core.common.utils.CPSUtil;
import com.ccnet.core.common.utils.DateUtils;
import com.ccnet.core.common.utils.base.MD5;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.SbMoneyCount;
import com.ccnet.cps.service.SbMoneyCountService;
import com.ccnet.cps.service.SbUserMoneyService;

/**
 * 任务api
 */
@Controller
@RequestMapping("/api/customtask/")
public class ApiCustomtaskController extends BaseController<SbCustomtask> {

	@Autowired
	SbCustomtaskService sbCustomtaskService;
	@Autowired
	MemberInfoDao memberInfoDao;
	@Autowired
	SbMoneyCountService sbMoneyCountService;
	@Autowired
	SbUserMoneyService SbUserMoneyService;
	private static final String BASE_CALLBACK_SALT = "8_hRl5ZBqIdxTs1PB_Kb6_KeVF2WYrJuc-rVa_jcDEhIIyL_xeppZw7R9m4FrBOlQB5Xht75GUiujKpnJ52pC8eg";

	@RequestMapping(value = "taskList", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> name(Headers headers) {
		try {
			List<SbCustomtask> list = sbCustomtaskService.findSbCustomtask(Long.valueOf(headers.getUserid()));
			List<SbCustomtask> list1 = new ArrayList<>();
			List<SbCustomtask> list2 = new ArrayList<>();
			List<SbCustomtask> list3 = new ArrayList<>();
			List<SbCustomtask> list4 = new ArrayList<>();
			List<SbCustomtask> list5 = new ArrayList<>();
			List<SbCustomtask> list6 = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				switch (list.get(i).getType()) {
				case 1:
					list1.add(list.get(i));
					break;
				case 2:
					list2.add(list.get(i));
					break;
				case 3:
					list3.add(list.get(i));
					break;
				case 4:
					list4.add(list.get(i));
					break;
				case 5:
					list5.add(list.get(i));
					break;
				case 6:
					list6.add(list.get(i));
					break;
				default:
					break;
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rcrw", list1);
			map.put("gyrw", list2);
			map.put("xsrw", list3);
			map.put("xlrw", list5);
			map.put("qtrw", list6);
			return ResultDTO.OK(map);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.逻辑错误);
		}
	}

	@RequestMapping(value = "saveTask", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> saveTask(Headers headers, Integer taskId) {
		try {
			if (CPSUtil.isEmpty(headers.getUserid())) {
				return ResultDTO.ERROR(AppResultCode.登录后获取更多更多奖励);
			}
			SbCustomtask arg0 = new SbCustomtask();
			arg0.setId(taskId);
			arg0 = sbCustomtaskService.find(arg0);
			if (CPSUtil.isNotEmpty(arg0)) {
				if (sbCustomtaskService.saveUserTask(Integer.valueOf(headers.getUserid()), taskId)) {
					Integer num = 0;
					String contentNum = JedisUtils
							.get("C_saveContentTask:taskId_" + taskId + "_userId_" + headers.getUserid());
					if (CPSUtil.isNotEmpty(contentNum)) {
						num = Integer.valueOf(contentNum);
					}
					num = num + 1;
					JedisUtils.set("C_saveContentTask:taskId_" + taskId + "_userId_" + headers.getUserid(),
							num.toString(), DateUtils.getEndDateNum());
					arg0.setTaskNum(arg0.getPayNum() - num);
					return ResultDTO.OK(arg0);
				}
				return ResultDTO.ERROR(BasicCode.逻辑错误);
			} else {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDTO.ERROR(BasicCode.参数错误);
	}

	@RequestMapping(value = "getTask", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getTask(Headers headers, Integer taskId) {
		try {
			SbCustomtask arg0 = new SbCustomtask();
			arg0.setId(taskId);
			arg0 = sbCustomtaskService.find(arg0);
			if (CPSUtil.isNotEmpty(arg0)) {
				return ResultDTO.OK(arg0);
			} else {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDTO.ERROR(BasicCode.参数错误);
	}

	@RequestMapping(value = "getContentTaskTime", method = RequestMethod.GET)
	@ResponseBody
	public ResultDTO<?> getContentTaskTime(Headers headers, Integer taskId, Integer contentId) {
		try {
			Map<String, Object> map = new HashMap<>();
			SbCustomtask arg0 = new SbCustomtask();
			arg0.setId(taskId);
			arg0 = sbCustomtaskService.find(arg0);
			if (CPSUtil.isNotEmpty(arg0)) {
				map.put("mustTime",arg0.getMusttime());
				return ResultDTO.OK(map);
			}else {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDTO.ERROR(BasicCode.参数错误);
	}

	@RequestMapping(value = "saveContentTask", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> saveContentTask(Headers headers, Integer taskId, Integer contentId) {
		try {
			if (CPSUtil.isEmpty(headers.getUserid())) {
				return ResultDTO.ERROR(AppResultCode.登录后获取更多更多奖励);
			}
			Map<String, Object> map = new HashMap<>();
			SbCustomtask arg0 = new SbCustomtask();
			arg0.setId(taskId);
			SbMoneyCount moneyCount = new SbMoneyCount();
			arg0 = sbCustomtaskService.find(arg0);
			if (CPSUtil.isNotEmpty(arg0)) {
				String contentNum = JedisUtils.get("C_saveContentTask:taskId_" + taskId + "_contentId_" + contentId
						+ "_userId_" + headers.getUserid());
				moneyCount.setmType(taskId);
				moneyCount.setUserId(Integer.valueOf(headers.getUserid()));
				if (sbMoneyCountService.findSbMoneyCountByTypeUserId(Integer.valueOf(headers.getUserid()),
						taskId) >= arg0.getPayNum()) {
					return ResultDTO.ERROR(AppResultCode.任务次数达到上限);
				}
				if (CPSUtil.isNotEmpty(contentNum) && contentNum.equals("3")) {
					return ResultDTO.ERROR(AppResultCode.你已阅读很长时间啦);
				}
				Random random = new Random();
				int randomIntegral = arg0.getRandomIntegral()==0?0:(random.nextInt(arg0.getRandomIntegral()*2+1));
				Double jiangli = (arg0.getPayIntegral()-arg0.getRandomIntegral())+randomIntegral;
				if (CPSUtil.isNotEmpty(contentNum)) {
					if (contentNum.equals("1")) {
						contentNum = "2";
//						jiangli = DoubleUtil.mul(jiangli, 0.5D);
					} else if (contentNum.equals("2")) {
						contentNum = "3";
//						jiangli = DoubleUtil.mul(jiangli, 0.3D);
					}
				} else {
					contentNum = "1";
				}
				JedisUtils.set("C_saveContentTask:taskId_" + taskId + "_contentId_" + contentId + "_userId_"
						+ headers.getUserid(), contentNum, 24 * 60 * 60);
				moneyCount.setCreateTime(new Date());
				moneyCount.setUserId(Integer.valueOf(headers.getUserid()));
				moneyCount.setUmoney(jiangli);
				moneyCount.setContentId(contentId);
				if (sbMoneyCountService.saveSbMoneyCountInfo(moneyCount)) {
					Integer mnid = sbMoneyCountService.findMaxId();
					map.put("id", mnid);
				}
				map.put("contentNum", contentNum);
				map.put("money", jiangli);
				return ResultDTO.OK(map);
			} else {
				return ResultDTO.ERROR(BasicCode.参数错误);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResultDTO.ERROR(BasicCode.参数错误);
	}

	@RequestMapping(value = "reward", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Boolean> reward(String user_id, String trans_id, String sign, String reward_amount,
			String reward_name, String extra) {
		Map<String, Boolean> map = new HashMap<>();
		try {
			SbMoneyCount arg0 = new SbMoneyCount();
			arg0.setUmId(Integer.valueOf(trans_id));
			JedisUtils.set("C_saveContentTask:trans_id_" + trans_id, "1", 1000);
			map.put("isValid", true);
		} catch (Exception e) {
			map.put("isValid", false);
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "fbreward", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> fbreward(String user_id, String trans_id, String sign, String reward_amount, String reward_name,
			String extra) {
		Map<String, Object> map = new HashMap<>();
		try {
			SbMoneyCount arg0 = new SbMoneyCount();
			arg0.setUmId(Integer.valueOf(trans_id));
			String transKey = JedisUtils.get("C_saveContentTask:trans_id_" + trans_id);
			arg0 = sbMoneyCountService.find(arg0);
			if (CPSUtil.isNotEmpty(arg0) && transKey.equals("1")) {
				sbMoneyCountService.update(arg0, "um_id");
				map.put("money", DoubleUtil.mul(arg0.getUmoney(), 2D));
				map.put("id", arg0.getUmId());
			} else {
				return ResultDTO.ERROR(AppResultCode.翻倍未完成任务);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDTO.ERROR(BasicCode.参数错误);
		}
		return ResultDTO.OK(map);
	}

	@RequestMapping(value = "processCallBack")
	@ResponseBody
	public String processCallBack(String ocode, Integer cid, String cuid, String devid, String adid, String adname,
			String pkg, Integer adtype, String time, String points, String sign) {
		Map<String, Object> map = new HashMap<>();
		// 1. 校验MD5数据内容
		String AD_KEY = "338678fd19228de";
		String parameter = ocode + "" + cid + "" + cuid + devid + adid + adname + pkg + "" + adtype + "" + time + ""
				+ points + "" + AD_KEY;
		CPSUtil.xprint("闵动回调接口参数===" + parameter);
		// 小写比较
		String md5Result = MD5.md5(parameter).toLowerCase().substring(10, 20);
		// 参数错误
		if (ocode == null || points == null || sign == null || adtype == null) {
			map.put("status", 0);
			map.put("msg", "参数错误");
		}
		// md5校对结果
		boolean md5flag = md5Result.equals(sign.toLowerCase());
		// 数据校验成功
		if (md5flag) {
			JedisUtils.set("C_fbreward:ocode_" + ocode, "1", 1000);
			SbMoneyCount moneyCount = new SbMoneyCount();
			moneyCount.setmType(midongtype(adtype));
			moneyCount.setUserId(Integer.valueOf(cuid));
			moneyCount.setCreateTime(new Date());
			moneyCount.setUmoney(Double.valueOf(points));
			sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
			map.put("status", 1);
			map.put("msg", "ok");
		} else {
			map.put("status", 0);
			map.put("msg", "数据校验失败");
			CPSUtil.xprint("xiaoshuoCallBack接口回调失败，参数校验失败"+parameter+"&&"+sign+"&&MD5&&"+md5Result);
		}
		return JSON.toJSONString(map);
	}

	@RequestMapping(value = "xiaoshuoCallBack")
	@ResponseBody
	public String xiaoshuoCallBack(String userId, Integer coinCount, Long ts, String coinCallbackId, String sign) {
		Map<String, Object> map = new HashMap<>();
		// 1. 校验MD5数据内容
		// md5校对结果
		LocalDate now = LocalDate.now(ZoneId.of("Asia/Shanghai"));
		String salt = MD5.md5(BASE_CALLBACK_SALT + now.format(DateTimeFormatter.BASIC_ISO_DATE));
		String md5 = MD5.md5(userId + coinCount + salt + ts);
		// 数据校验成功
		if (sign.equals(md5)) {
			SbMoneyCount moneyCount = new SbMoneyCount();
			moneyCount.setmType(AwardType.xiaoshuozhuan.getAwardId());
			moneyCount.setUserId(Integer.valueOf(userId));
			moneyCount.setCreateTime(new Date());
			moneyCount.setUmoney(Double.valueOf(coinCount));
			sbMoneyCountService.saveSbMoneyCountInfo(moneyCount);
			map.put("code", 0);
			map.put("message", "ok");
		} else {
			map.put("code", 1);
			map.put("message", "数据校验失败");
			CPSUtil.xprint("xiaoshuoCallBack接口回调失败，参数校验失败"+userId+"&&" + coinCount+"&&" + salt+"&&" + ts+"&&"+sign+"&&MD5&&"+md5);
		}
		return JSON.toJSONString(map);
	}

	private static Integer midongtype(Integer adType) {
		Integer i = 0;
		switch (adType) {
		case 0:
			i = 56;
			break;
		case 1:
			i = 53;
			break;
		case 2:
			i = 102;
			break;
		case 3:
			i = 103;
			break;
		case 4:
			i = 104;
			break;
		case 5:
			i = 105;
			break;
		case 6:
			i = 106;
			break;
		case 7:
			i = 107;
			break;
		case 8:
			i = 108;
			break;
		case 9:
			i = 54;
			break;
		case 10:
			i = 59;
			break;
		default:
			break;
		}
		return i;
	}
}
