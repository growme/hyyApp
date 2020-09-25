package com.ccnet.core.common.utils.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.ccnet.core.common.utils.UniqueID;
import com.google.zxing.WriterException;

/**
 * ClassName: MarkLogoIcon 
 * @Description: 生成微商二维码
 * @author Jackie Wang
 * @company 北京简智科技
 * @copyright 版权所有 盗版必究
 * @date 2017-4-26
 */
public class MarkLogoIcon {
	
	public static void markByIcon(String iconPath,String srcImagePath,String targetPath){
		markByIcon(iconPath, srcImagePath, targetPath,null,null,null);
	}
	
	/**
	 * @Description: 输出到文件
	 * @param @param iconPath
	 * @param @param srcImagePath
	 * @param @param tagetPath
	 * @param @param degree
	 * @param @param x
	 * @param @param y   
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-4-26
	 */
	public static void markByIcon(String iconPath,String srcImagePath,String tagetPath,Integer degree,Integer x,Integer y){
		OutputStream os=null;
		try{
			Image srcImage=ImageIO.read(new File(srcImagePath));
			BufferedImage buffImg = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_BGR);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage.getHeight(null), Image.SCALE_SMOOTH),
					0,0,null);
			
			if(degree!=null){
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth(null)/2, (double) buffImg.getHeight(null)/2);
			}
			ImageIcon imageIcon=new ImageIcon(iconPath);
			Image img=imageIcon.getImage();
			float alpha=0.9f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			
			if(x==null){
				x= buffImg.getWidth(null)/2;
			}
			
			if(y==null){
				y = buffImg.getHeight(null)/2;
			}
			
			g.drawImage(img, x, y, null);
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			
			os = new FileOutputStream(tagetPath);
			//生成图片
			ImageIO.write(buffImg, "JPG", os);
			System.out.println("图片水印添加成功。。。");
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

	/**
	 * @Description: 输出到文件
	 * @param @param iconPath
	 * @param @param srcImagePath
	 * @param @param tagetPath
	 * @param @param degree
	 * @param @param x
	 * @param @param y   
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-4-26
	 */
	public static void markByIcon(BufferedImage qrcode,String srcImagePath,String tagetPath,Integer degree,Integer x,Integer y){
		OutputStream os=null;
		try{
			Image srcImage=ImageIO.read(new File(srcImagePath));
			BufferedImage buffImg = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_BGR);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage.getHeight(null), Image.SCALE_SMOOTH),
					0,0,null);
			
			if(degree!=null){
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth(null)/2, (double) buffImg.getHeight(null)/2);
			}
			/*ImageIcon imageIcon=new ImageIcon(iconPath);
			Image img=imageIcon.getImage();*/
			Image img=(Image)qrcode;
			float alpha=0.9f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			
			if(x==null){
				x= buffImg.getWidth(null)/2;
			}
			
			if(y==null){
				y = buffImg.getHeight(null)/2;
			}
			
			g.drawImage(img, x, y, null);
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			
			os = new FileOutputStream(tagetPath);
			//生成图片
			ImageIO.write(buffImg, "JPG", os);
			System.out.println("图片水印添加成功。。。");
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 生成微商二维码图片到文件流
	 * @param @param qrcode 二维码图片
	 * @param @param srcImagePath 背景图片
	 * @param @param degree 倾斜角度
	 * @param @param x x轴距离
	 * @param @param y y轴距离
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-4-26
	 */
	public static void markByIconToStream(BufferedImage qrcode,String srcImagePath,Integer degree,Integer x,Integer y){
		OutputStream os=null;
		try{
			Image srcImage=ImageIO.read(new File(srcImagePath));
			BufferedImage buffImg = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_BGR);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage.getHeight(null), Image.SCALE_SMOOTH),0,0,null);
			if(degree!=null){
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth(null)/2, (double) buffImg.getHeight(null)/2);
			}
			//转换图片
			Image img=(Image)qrcode;
			float alpha=0.9f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			
			if(x==null){
				x= buffImg.getWidth(null)/2;
			}
			
			if(y==null){
				y = buffImg.getHeight(null)/2;
			}
			
			g.drawImage(img, x, y, null);
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			//生成图片
			os = new FileOutputStream(UniqueID.getUniqueID(32, 2)+".jpg");
			if (!ImageIO.write(buffImg, "JPG", os)) {
			      throw new IOException("输出文件出错！");
			 }
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 生成微商二维码图片到文件流
	 * @param @param qrcode 二维码图片
	 * @param @param srcImagePath 背景图片
	 * @param @param degree 倾斜角度
	 * @param @param x x轴距离
	 * @param @param y y轴距离
	 * @return void  
	 * @throws
	 * @author Jackie Wang
	 * @date 2017-4-26
	 */
	public static void markByIconToStream(BufferedImage qrcode,String srcImagePath,Integer degree,Integer x,Integer y,OutputStream os){
		try{
			Image srcImage=ImageIO.read(new File(srcImagePath));
			BufferedImage buffImg = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null), BufferedImage.TYPE_INT_BGR);
			Graphics2D g = buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage.getHeight(null), Image.SCALE_SMOOTH),0,0,null);
			if(degree!=null){
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth(null)/2, (double) buffImg.getHeight(null)/2);
			}
			//转换图片
			Image img=(Image)qrcode;
			float alpha=0.9f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			
			if(x==null){
				x= buffImg.getWidth(null)/2;
			}
			
			if(y==null){
				y = buffImg.getHeight(null)/2;
			}
			
			g.drawImage(img, x, y, null);
	        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			g.dispose();
			//生成图片
			if (!ImageIO.write(buffImg, "JPG", os)) {
			   throw new IOException("输出文件出错！");
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (null != os) {
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * 为图片添加图片文字
	 * 
	 * @param sourceImg
	 *            原图
	 * @param targetImg
	 *            制作完成的图片
	 * @return
	 * @throws IOException
	 */
	public static void markTextMark(String sourceImg, String targetImg,String text,String text1)throws IOException {
		File file = new File(sourceImg);
		Image img = ImageIO.read(file);
		int width = img.getWidth(null);// 水印宽度
		int height = img.getHeight(null);// 水印高
		BufferedImage bi = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH),0, 0, null);
		// 字体
		Font font = new Font("黑体", Font.BOLD, 30);
		g.setBackground(Color.WHITE);
		g.fillRect(0, 10, 100, 10);
		g.setPaint(Color.GRAY);

		FontRenderContext context = g.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(text, context);
		double x = (width - bounds.getWidth()) / 2;
		double y = (height - bounds.getHeight()) / 2;
		double ascent = -bounds.getY();
		double baseY = y + ascent;

		// 绘制字符串
		g.drawString(text, 260, 400);
		g.drawString(text1, 140, 1100);
		File sf = new File(targetImg);
		ImageIO.write(bi, "jpg", sf); // 保存图片
	}
	
	public static void main(String[] args) throws WriterException, IOException{
		String srcImagePath="D://barcode_8.jpg";
		String targetImagePath="D://bj.jpg";
		
		markTextMark(srcImagePath, targetImagePath,"同城交流群","该群7天内(5月1日前)有效，重新进入将更新");
	}
}
