package com.ccnet.core.common.utils.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author LingLee
 *
 */
public class MarkLogo {
	
	public static void markByText(String logoText,String srcImagePath,String targetImagePath){
		markByText(logoText, srcImagePath, targetImagePath,null);
	}
	
	public static void markByText(String logoText,String srcImagePath,String targetImagePath,Integer degree){
		InputStream is=null;
		OutputStream os=null;
		
		try{
			Image srcImage=ImageIO.read(new File(srcImagePath));
			BufferedImage buffImg=new BufferedImage(srcImage.getWidth(null),srcImage.getHeight(null),
					BufferedImage.TYPE_INT_BGR);
			Graphics2D g=buffImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImage.getScaledInstance(srcImage.getWidth(null), srcImage.getHeight(null), Image.SCALE_SMOOTH),
					0,0,null);
			
			if(degree!=null){
				g.rotate(Math.toRadians(degree), (double) buffImg.getWidth(null)/2, (double) buffImg.getHeight(null)/2);
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("宋体",Font.BOLD,30));
			float alpha=0.5f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			g.drawString(logoText, srcImage.getWidth(null)/4, srcImage.getHeight(null)/2);
			g.dispose();//消除Windows图形资源，避免多人使用时出现内存泄漏，及时使用
			os=new FileOutputStream(targetImagePath);
			ImageIO.write(buffImg, "JPG", os);
			System.out.println("图片完成文字印章");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(is!=null) is.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			try{
				if(os!=null) os.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		String srcImagePath="D://43627966_1.jpg";
		String targetImagePath="D://4444.jpg";
		String logoText="hello world";
		String targetImagePath2="f:/Music/photo/3.jpg";
		//markByText(logoText, srcImagePath, targetImagePath2);
		markByText(logoText, srcImagePath, targetImagePath, -45);
	}
}