package com.ccnet.core.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

@SuppressWarnings("unchecked")
public class ZHToEN {
	/**
	 * 中文转换为拼音实现类
	 * @author 王桂平
	 * @see 2016-10-19
	 */
	public static String getPingyin(String zhongwen) {
		String[] pinYin = null;
		String zhongWenPinYin = "";
		try {
			char[] chars = zhongwen.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i],getDefaultOutputFormat());
				// 当转换不是中文字符时,返回null
				if (pinYin != null) {
					zhongWenPinYin += capitalize(pinYin[0]);
				} else {
					zhongWenPinYin += chars[i];
				}
			}
			CPSUtil.xprint("zhongWenPinYin="+zhongWenPinYin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return zhongWenPinYin;
	}

	/**
	 * 设置输出格式
	 * @return
	 */
	private static HanyuPinyinOutputFormat getDefaultOutputFormat() {
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 没有音调数字
		format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// u显示
		return format;
	}

	/**
	 * 设置单词首字母大写
	 * @return
	 */
	private static String capitalize(String s) {
		char ch[];
		ch = s.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		String newString = new String(ch);
		return newString;
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		CPSUtil.xprint("pinyinName="+pinyinName);
		return pinyinName;
	}
	
	/**
	 * 获取首字母拼音
	 * @param chars
	 * @return
	 */
	public static String getFSpell(String chars) {
		return converterToFirstSpell(chars.trim()).toLowerCase();
	}
	
	public static void main(String[] args) {
		getFSpell("新增菜单");
		getPingyin("新增菜单");
	}
}
