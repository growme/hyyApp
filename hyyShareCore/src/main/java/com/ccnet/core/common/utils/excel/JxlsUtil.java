package com.ccnet.core.common.utils.excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.ccnet.core.common.utils.CPSUtil;

public class JxlsUtil {

	/** 
	 * 导出excel 
	 * @param templateFile - excel模版名称 
	 * @param beans - 模版中填充的数据 
	 * @param os - 生成模版输出流 
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws ParsePropertyException 
	 */
	public static boolean export2Excel(Map beans, String srcPath,OutputStream os) throws Exception {
		boolean temp = false;
		try {
			XLSTransformer transformer = new XLSTransformer();
			//获得模板的输入流
			FileInputStream in = new FileInputStream(srcPath);
			//将beans通过模板输入流写到workbook中
			Workbook workbook = transformer.transformXLS(in, beans);
			//将workbook中的内容用输出流写出去
			workbook.write(os);
			temp = true;
			CPSUtil.xprint("导出数据成功...");
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return temp;
	}

	public static void main(String[] args) throws ParsePropertyException,
			InvalidFormatException, IOException {
		    
	}
}
