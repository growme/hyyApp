package com.ccnet.core.common.utils.id.generator;

import com.ccnet.core.common.utils.id.CreateIDException;

public class UUIDGenerator extends AbstractUUIDGenerator {

	private String sep = "";

	public UUIDGenerator() {

	}

	public UUIDGenerator(String pSep) {
		this.sep = pSep;
	}

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public String create() throws CreateIDException {
		StringBuffer sb = new StringBuffer(36);
		sb.append(format(getIP())).append(sep).append(format(getJVM())).append(
				sep).append(format(getHiTime())).append(sep).append(
				format(getLoTime())).append(sep).append(format(getCount()));
		return sb.toString();

	}
	
	public static String get32UUID() {
		UUIDGenerator a = new UUIDGenerator();
		return a.create();
	}

	public static void main(String[] args) throws Exception {
		UUIDGenerator a = new UUIDGenerator();
		System.out.println(a.create());
	}

}
