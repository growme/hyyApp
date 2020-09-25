package com.ccnet.core.common.utils.barcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * ClassName: QRCodeUtil 
 * @Description: 二维码处理工具
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-4-26
 */
public class QRCodeUtil {
	
	private static int width = 117;              //二维码宽度
	private static int height = 117;             //二维码高度
	private static int onColor = 0xFF000000;     //前景色
	private static int offColor = 0xFFFFFFFF;    //背景色
	private static int margin = 1;               //白边大小，取值范围0~4
	private static ErrorCorrectionLevel level = ErrorCorrectionLevel.L;  //二维码容错率
    
    /**
     * 生成二维码
     * @param params
     * QRCodeParams属性：txt、fileName、filePath不得为空；
     * @throws QRParamsException
     */
    public static void generateQRImage(QRCodeParams params)throws QRParamsException{
    	if(params == null
			|| params.getFileName() == null
			|| params.getFilePath() == null
			|| params.getTxt() == null){

			throw new QRParamsException("参数错误");
		}
    	try{
    		initData(params);
    		
            String imgPath = params.getFilePath();  
            String imgName = params.getFileName(); 
            String txt = params.getTxt();
            
            if(params.getLogoPath() != null && !"".equals(params.getLogoPath().trim())){
            	generateQRImage(txt, params.getLogoPath(), imgPath, imgName, params.getSuffixName());
            }else{
            	generateQRImage(txt, imgPath, imgName, params.getSuffixName());
            }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
            
    }
    
	/**
	 * 生成二维码
	 * @param txt          //二维码内容
	 * @param imgPath      //二维码保存物理路径
	 * @param imgName      //二维码文件名称
	 * @param suffix       //图片后缀名
	 */
	public static void generateQRImage(String txt, String imgPath, String imgName, String suffix){  
         
        File filePath = new File(imgPath);
        if(!filePath.exists()){
        	filePath.mkdirs();
        }
        
        File imageFile = new File(imgPath,imgName);
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
        // 指定纠错等级  
        hints.put(EncodeHintType.ERROR_CORRECTION, level);  
        // 指定编码格式  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); 
        hints.put(EncodeHintType.MARGIN, margin);
        try {  
        	MatrixToImageConfig config = new MatrixToImageConfig(onColor, offColor);
        	BitMatrix bitMatrix = new MultiFormatWriter().encode(txt,BarcodeFormat.QR_CODE, width, height, hints);  
        	bitMatrix = deleteWhite(bitMatrix,4);
        	MatrixToImageWriter.writeToFile(bitMatrix, suffix, imageFile, config);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
	
    /**
     * 生成带logo的二维码图片 
     * @param txt          //二维码内容
     * @param logoPath     //logo绝对物理路径
	 * @param imgPath      //二维码保存绝对物理路径
	 * @param imgName      //二维码文件名称
	 * @param suffix       //图片后缀名
     * @throws Exception
     */
    public static void generateQRImage(String txt, String logoPath, String imgPath, String imgName, String suffix) throws Exception{
   
        File filePath = new File(imgPath);
        if(!filePath.exists()){
        	filePath.mkdirs();
        }
        
        if(imgPath.endsWith("/")){
        	imgPath += imgName;
        }else{
        	imgPath += "/"+imgName;
        }
        
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    
        hints.put(EncodeHintType.ERROR_CORRECTION, level);  
        hints.put(EncodeHintType.MARGIN, margin);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, width, height, hints);  
        File qrcodeFile = new File(imgPath);    
        writeToFile(bitMatrix, suffix, qrcodeFile, logoPath);    
    }  
    
    /**
     * 生成带logo的二维码图片到流
     * @param txt 二维码内容
     * @param logoPath logo图片
     * @param width 二维码宽度
     * @param height 二维码高度
     * @param suffix 后缀
     * @param os 流
     * @throws Exception
     */
    public static void generateQRImageToStream(String txt, String logoPath,int width,int height, String suffix,OutputStream os) throws Exception{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    
        hints.put(EncodeHintType.ERROR_CORRECTION, level);  
        hints.put(EncodeHintType.MARGIN, margin);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(txt, BarcodeFormat.QR_CODE, width, height, hints);  
        writeToStream(bitMatrix, suffix, logoPath, os);    
    }
    
    /**
     * 生成二维码到流
     * @Description: TODO
     * @param @param codeText
     * @param @param width
     * @param @param height
     * @param @return
     * @param @throws Exception   
     * @return BufferedImage  
     * @throws
     * @author Jackie Wang
     * @date 2017-4-27
     */
    public static BufferedImage generateQRToStream(String codeText,int width,int height,int margin) throws Exception{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();   
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    
        hints.put(EncodeHintType.ERROR_CORRECTION, level);  
        hints.put(EncodeHintType.MARGIN, margin);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(codeText, BarcodeFormat.QR_CODE, width, height, hints);  
        bitMatrix = deleteWhite(bitMatrix,margin);
        return toBufferedImage(bitMatrix);
    } 
    
      
    /** 
     *  
     * @param matrix 二维码矩阵相关 
     * @param format 二维码图片格式 
     * @param file 二维码图片文件 
     * @param logoPath logo路径 
     * @throws IOException 
     */  
    public static void writeToFile(BitMatrix matrix,String format,File file,String logoPath) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        Graphics2D gs = image.createGraphics();  
         
        int ratioWidth = image.getWidth()/6;
        int ratioHeight = image.getHeight()/6;
        //载入logo  
        Image img = ImageIO.read(new File(logoPath)); 
        int logoWidth = img.getWidth(null) > ratioWidth?ratioWidth:img.getWidth(null);
        int logoHeight = img.getHeight(null) > ratioHeight?ratioHeight:img.getHeight(null);
        
        int x = (image.getWidth() - logoWidth) / 2; 
        int y = (image.getHeight() - logoHeight) / 2;
        
        gs.drawImage(img, x, y, logoWidth, logoHeight, null); 
        gs.setColor(Color.black);
        gs.setBackground(Color.WHITE);
        gs.dispose();  
        img.flush();  
        if(!ImageIO.write(image, format, file)){  
            throw new IOException("Could not write an image of format " + format + " to " + file);    
        }  
    }  
    
    
    /** 
     *  
     * @param matrix 二维码矩阵相关 
     * @param format 二维码图片格式 
     * @param file 二维码图片文件 
     * @param logoPath logo路径 
     * @throws IOException 
     */  
    public static void writeToStream(BitMatrix matrix,String format,String logoPath,OutputStream os) throws IOException {  
        BufferedImage image = toBufferedImage(matrix);  
        Graphics2D gs = image.createGraphics();  
         
        int ratioWidth = image.getWidth()/6;
        int ratioHeight = image.getHeight()/6;
        //载入logo  
        Image img = ImageIO.read(new File(logoPath)); 
        int logoWidth = img.getWidth(null) > ratioWidth?ratioWidth:img.getWidth(null);
        int logoHeight = img.getHeight(null) > ratioHeight?ratioHeight:img.getHeight(null);
        
        int x = (image.getWidth() - logoWidth) / 2; 
        int y = (image.getHeight() - logoHeight) / 2;
        
        gs.drawImage(img, x, y, logoWidth, logoHeight, null); 
        gs.setColor(Color.black);
        gs.setBackground(Color.WHITE);
        gs.dispose();  
        img.flush();  
        
        if (!ImageIO.write(image, "JPG", os)) {
		   throw new IOException("输出文件出错！");
		}
       
    } 
    
    /**
     * 生成文件流
     * @param @param matrix
     * @param @return   
     * @return BufferedImage  
     * @throws
     * @author Jackie Wang
     * @date 2017-4-26
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix){  
        int width = matrix.getWidth();  
        int height = matrix.getHeight();  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        for(int x=0;x<width;x++){  
            for(int y=0;y<height;y++){  
                image.setRGB(x, y, matrix.get(x, y) ? onColor : offColor);  
            }  
        }  
        return image;     
    } 
    
    
    /**
     * @Description: 完全删除白边
     * @param @param matrix
     * @param @return   
     * @return BitMatrix  
     * @throws
     * @author Jackie Wang
     * @date 2017-4-26
     */
    public static BitMatrix deleteWhite(BitMatrix matrix){  
        int[] rec = matrix.getEnclosingRectangle();  
        int resWidth = rec[2] + 1;  
        int resHeight = rec[3] + 1;  
      
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
        resMatrix.clear();  
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {  
                if (matrix.get(i + rec[0], j + rec[1]))  
                    resMatrix.set(i, j);  
            }  
        }  
        return resMatrix;  
    } 
    
    
    /**
     * @Description: 设置任意白边宽度 必须大于0
     * @param @param matrix
     * @param @param white
     * @param @return   
     * @return BitMatrix  
     * @throws
     * @author Jackie Wang
     * @date 2017-4-26
     */
 	private static BitMatrix deleteWhite(BitMatrix matrix, int white) {
 		int[] rec = matrix.getEnclosingRectangle();
 		int resWidth = rec[2] + 1 + white * 2;
 		int resHeight = rec[3] + 1 + white * 2;

 		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
 		// resMatrix.setRegion(-2,-2,resWidth, resHeight);
 		resMatrix.clear();

 		for (int i = 0; i < resWidth - white * 2; i++) {
 			for (int j = 0; j < resHeight - white * 2; j++) {
 				if (matrix.get(i + rec[0], j + rec[1]))
 					resMatrix.set(i + white, j + white);
 			}
 		}
 		return resMatrix;
 	}
    
 	/**
 	 * 数据初始化
 	 * @Description: TODO
 	 * @param @param params   
 	 * @return void  
 	 * @throws
 	 * @author Jackie Wang
 	 * @date 2017-4-26
 	 */
    private static void initData(QRCodeParams params){
    	if(params.getWidth() != null){
    		width = params.getWidth();
    	}
    	if(params.getHeight() != null){
    		height = params.getHeight();
    	}
    	if(params.getOnColor() != null){
    		onColor = params.getOnColor();
    	}
    	if(params.getOffColor() != null){
    		offColor = params.getOffColor();
    	}
    	if(params.getLevel() != null){
    		level = params.getLevel();
    	}
    }
    
    public static void main(String[] args) {
    	 String content = "http://weixin.qq.com/r/ZzjP1yHE5nyZrWW09211";
	     String path = "D://logo.png";
	     //generateQRImage(content, path, "tttt.jpg", "jpg");
	     try {
	    	 initData(new QRCodeParams());
			 generateQRImage(content, path, "D://", "logoQR.jpg", "jpg");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
