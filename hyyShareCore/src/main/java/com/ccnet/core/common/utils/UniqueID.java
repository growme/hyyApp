package com.ccnet.core.common.utils;

import java.util.Random;

public class UniqueID {
	public String uniqueCode = "";

	/**
	 * 获取系统唯一主键ID
	 * 
	 * @param len
	 *            长度
	 * @param type
	 *            类型 0 表示仅获得 数字 随机数 1 表示仅获得 字符 随机数 2 表示获得 数字+字符 混合随机数
	 * @return
	 */
	public static String getUniqueID(int len, int type) {
		String uniqueID = "";
		try {
			uniqueID = RandomNum.CreateRadom(len, type);
		} catch (Exception e) {
			CPSUtil.xprint("对不起！生成系统唯一主键失败！");
			e.printStackTrace();
		}
		return uniqueID;
	}

	/**
	 * 获取时间跟踪ID
	 * 
	 * @param len
	 * @param type
	 * @return
	 */
	public static String getEventID(int len, int type) {
		String uniqueID = "";
		try {
			uniqueID = RandomNum.CreateRadom(len, type);
			uniqueID = "EV" + uniqueID;
		} catch (Exception e) {
			CPSUtil.xprint("对不起！生成系统唯一主键失败！");
			e.printStackTrace();
		}
		return uniqueID;
	}

	public static String getRColor() {
		String color = "";
		int rn = 0;
		Random rnd = new Random();
		String rcolorStr = "dd0000,7dbe26,e72980,f49800";

		String[] colors = rcolorStr.split(",");
		try {
			for (int i = 0; i < colors.length; i++) {
				rn = rnd.nextInt(4);
				color = colors[rn];
				if (CPSUtil.isNotEmpty(color)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return color;

	}

	public static void main(String[] args) {

		  for(int i=0;i<5;i++){
			  CPSUtil.xprint(UniqueID.getUniqueID(32,2)); 
		     //UniqueID.getUniqueID(8,0);
			  //CPSUtil.xprint("byHQ+jOqWSc=");
		    // CPSUtil.xprint("'"+UniqueID.getUniqueID(8,2)+"','"+UniqueID.getUniqueID(5,2)+"',");
		  }

		/*for (int i = 0; i < 10; i++) {
			CPSUtil.xprint(getRColor());
		}*/
	}

}
