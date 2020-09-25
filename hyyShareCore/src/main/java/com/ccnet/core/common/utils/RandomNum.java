package com.ccnet.core.common.utils;

import java.util.Random;

public class RandomNum {

	/** 
	 * 方法说明： 
	 * 该函数获得随机数字符串 
	 * @param iLen  int:需要获得随机数的长度 
	 * @param iType int:随机数的类型： 
	 * '0 ':表示仅获得数字随机数； 
	 * '1 '：表示仅获得字符随机数； 
	 * '2 '：表示获得数字字符混合随机数 
	 * @since   1.0.0 
	 */
	public static final String CreateRadom(int iLen, int iType) {
		String strRandom = "";//随机字符串 
		Random rnd = new Random();
		if (iLen < 0) {
			iLen = 5;
		}
		if ((iType > 2) || (iType < 0)) {
			iType = 2;
		}
		switch (iType) {
		case 0:
			for (int iLoop = 0; iLoop < iLen; iLoop++) {
				strRandom += Integer.toString(rnd.nextInt(10));
			}
			break;
		case 1:
			for (int iLoop = 0; iLoop < iLen; iLoop++) {
				strRandom += Integer.toString((35 - rnd.nextInt(10)), 36);
			}
			break;
		case 2:
			for (int iLoop = 0; iLoop < iLen; iLoop++) {
				strRandom += Integer.toString(rnd.nextInt(36), 36);
			}
			break;
		}
		//System.out.println(strRandom);
		return strRandom.trim();
	}
	
	public static Integer createRandom(int length) {
		String random = CreateRadom(length, 0);
		return Integer.parseInt(random);
	}
	
	/**
	 * 生成指定数值以内随机数double类型
	 * @param length
	 * @return
	 */
	public static String getRandDoubleVal(int length) {
        Random random = new Random();  
        double v = random.nextDouble() * length;
		return CPSUtil.formatDoubleVal(v,"0.00");
	}
	
	/**
	 * 生成指定数值以内随机数
	 * @param length
	 * @return
	 */
	public static int getRandomIntVal(int length) {
        Random random = new Random();  
        int rand = random.nextInt(length);
        if(rand==0){
        	rand++;
        }
		return random.nextInt(length);
	}
	
	/**
	 * 生成指定数值以内随机数
	 * @param length
	 * @return
	 */
	public static int getRandIntVal(int length) {
        Random random = new Random();  
        int i1 = random.nextInt(length)+1;  
        if(i1==10){
        	i1--;
        }
		return i1;
	}
	
	/**
	 * 生成指定数值以内随机数
	 * @param length
	 * @return
	 */
	public static int getRandVal(int length) {
        Random random = new Random();  
        int i1 = random.nextInt(length)+1;  
		return i1;
	}
	
	
	public static final String NewCreateRadom(int iLen, int iType) {
		String returNum = null;
		while(true){
			returNum = CreateRadom(iLen,iType);
			if(iType==0 && returNum!=null && !"".equals(returNum)){
				if(returNum.charAt(0)!='0'){
					break;
				}
			}
		}
		System.out.println("最后返回随机"+iLen+"位数："+returNum);
		return returNum;
	}
	public static void main(String []args){
		for(int i=0;i<=100;i++){
			CPSUtil.xprint("val="+getRandomIntVal(10));
		}
		
		
		//CreateRadom(8,0);
		//CreateRadom(8,1);
		//CreateRadom(8,2);
	}
}
