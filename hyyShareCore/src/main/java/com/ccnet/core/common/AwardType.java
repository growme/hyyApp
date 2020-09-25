package com.ccnet.core.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum AwardType {

	register(0, "注册奖励"), transmit(1, "分享奖励"), readawd(5, "阅读奖励"), deduct(2, "下线提成"), visitad(4, "邀请奖励"),

	redpacke(3, "签到奖励"), chest(10, "宝箱奖励"), darwback(15, "提现退款"), FIRST(21, "徒弟第一次提现奖励"),

	SECOND(22, "徒弟第二次提现奖励"), THIRD(23, "徒弟第三次提现奖励"), FULL(24, "徒弟提现满额度奖励"), HAIXING(41, "海星小游戏奖励"),

	XINGYUN(42, "幸运大转盘奖励"), GUAKA(43, "刮卡领现金奖励"), WEIXIN(44, "微信领现金奖励"), yuedu(51, "阅读文章奖励"),

	zhuanpan(52, "转盘宝箱奖励"), xiaochengxi(53, "小程序试玩奖励"), kankanzhuan(54, "看看赚"),

	xiaoshuozhuan(55, "小说赚奖励"), yingyongshiwan(56, "应用试玩奖励"), shipinhongbao(57, "视频红包"),

	shipinzhuan(58, "视频赚"), xiaoshuozhuan1(59, "小说赚"), yuduwenzhuang(60, "阅读文章奖励"),

	yueduwenzhang(61, "阅读文章奖励"), lianxufenxiang(62, "连续分享奖励"), xinwenliulan(63, "新闻浏览"),

	shipinliulan(64, "视频浏览"), yuedutuisong(65, "阅读推送"), kaiqitongzhi(66, "开启通知奖励"),

	yaoqinghaoyou(67, "邀请好友赚大钱"), CPAanzhuang(100, "CPA安装"), CPAqiandao(101, "CPA签到"), huangxingguanggao(102,
			"唤醒广告"), xiaochengxuguanggao(104, "小程序试玩"), gongzhonghao(104,
					"公众号"), CPLguanggao(105, "CPL广告"), ASOguanggao(106, "ASO广告"), jietuguanggao(107, "截图广告");

	private Integer awardId;
	private String awardName;

	private AwardType(Integer awardId, String awardName) {
		this.awardId = awardId;
		this.awardName = awardName;
	}

	public Integer getAwardId() {
		return awardId;
	}

	public void setAwardId(Integer awardId) {
		this.awardId = awardId;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public static List<AwardType> all() {
		List<AwardType> types = new ArrayList<AwardType>();
		for (AwardType awardType : values()) {
			types.add(awardType);
		}
		return types;
	}

	// 获取收益类型
	public static Map getAwardType() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		for (AwardType awardType : values()) {
			map.put(awardType.getAwardId(), awardType.getAwardName());
		}
		return map;
	}

	public static AwardType getAwardType(Integer typeId) {
		for (AwardType awardType : values()) {
			if (awardType.getAwardId().equals(typeId)) {
				return awardType;
			}
		}
		return null;
	}

	// 推广收益
	public static boolean isValidatePromte(AwardType type) {
		return AwardType.transmit.equals(type) || AwardType.readawd.equals(type) || AwardType.deduct.equals(type);
	}
}
