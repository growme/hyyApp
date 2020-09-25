package com.ccnet.core.common.utils;

import java.util.Random;

public class EmojiUtils {

	/**
	 * 将Unicode字符转成中文
	 * 
	 * @param src
	 * @return
	 */
	public static String unicode2Emoji(String src) {
		if (CPSUtil.isEmpty(src)) {
			return "";
		}

		StringBuffer retBuf = new StringBuffer();
		int maxLoop = src.length();
		for (int i = 0; i < maxLoop; i++) {
			if (src.charAt(i) == '\\') {
				if ((i < maxLoop - 5)
						&& ((src.charAt(i + 1) == 'u') || (src.charAt(i + 1) == 'U'))) {
					try {
						retBuf.append((char) Integer.parseInt(
								src.substring(i + 2, i + 6), 16));
						i += 5;
					} catch (NumberFormatException localNumberFormatException) {
						retBuf.append(src.charAt(i));
					}
				} else {
					retBuf.append(src.charAt(i));
				}
			} else {
				retBuf.append(src.charAt(i));
			}
		}
		return retBuf.toString();
	}

	/**
	 * 将字符串中的Emoji表情转换成Unicode编码
	 * 
	 * @param src
	 * @return
	 */
	public static String emoji2Unicode(String src) {
		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			int codepoint = src.codePointAt(i);
			if (isEmojiCharacter(codepoint)) {
				unicode.append("\\u").append(Integer.toHexString(c));
			} else {
				unicode.append(c);
			}
		}
		return unicode.toString();
	}

	/**
	 * 判断是否包含Emoji符号
	 * 
	 * @param codePoint
	 * @return
	 */
	public static boolean isEmojiCharacter(int codePoint) {
		return (codePoint >= 0x2600 && codePoint <= 0x27BF) // 杂项符号与符号字体
				|| codePoint == 0x303D
				|| codePoint == 0x2049
				|| codePoint == 0x203C
				|| (codePoint >= 0x2000 && codePoint <= 0x200F)//
				|| (codePoint >= 0x2028 && codePoint <= 0x202F)//
				|| codePoint == 0x205F //
				|| (codePoint >= 0x2065 && codePoint <= 0x206F)//
				/* 标点符号占用区域 */
				|| (codePoint >= 0x2100 && codePoint <= 0x214F)// 字母符号
				|| (codePoint >= 0x2300 && codePoint <= 0x23FF)// 各种技术符号
				|| (codePoint >= 0x2B00 && codePoint <= 0x2BFF)// 箭头A
				|| (codePoint >= 0x2900 && codePoint <= 0x297F)// 箭头B
				|| (codePoint >= 0x3200 && codePoint <= 0x32FF)// 中文符号
				|| (codePoint >= 0xD800 && codePoint <= 0xDFFF)// 高低位替代符保留区域
				|| (codePoint >= 0xE000 && codePoint <= 0xF8FF)// 私有保留区域
				|| (codePoint >= 0xFE00 && codePoint <= 0xFE0F)// 变异选择器
				|| codePoint >= 0x10000; // Plane在第二平面以上的，char都不可以存，全部都转
	}
	
	
	public static String getRandEmoji(){
		String emoji [] ={"☀","☁","⚡","🌂","🌺","⛎","🐳","💖","🔥","🈲","㊙","💥","✨","	💘","🍺","💌","🎸","⛵","🎁","💰","👙","🙏","🐠","☔"};
		Random random = new Random();  
        int index = random.nextInt(emoji.length);  
		return emoji[index];	
	}
	
	public static void main(String[] args) {
		String emoji = emoji2Unicode(getRandEmoji());
		CPSUtil.xprint("emoji==="+emoji);
		CPSUtil.xprint("emoji==="+unicode2Emoji(emoji));
	}
}
