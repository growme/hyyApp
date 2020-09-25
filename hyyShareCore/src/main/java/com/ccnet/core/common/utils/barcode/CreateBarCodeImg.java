package com.ccnet.core.common.utils.barcode;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class CreateBarCodeImg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
            
		     String content = "http://weixin.qq.com/r/ZzjP1yHE5nyZrWW09211";
		     String path = "C:/testImage";
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     Map hints = new HashMap();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 450, 450);
		     File file1 = new File(path,"二维码.jpg");
		     MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);
		     
		 } catch (Exception e) {
		     e.printStackTrace();
		 }

	}

}
