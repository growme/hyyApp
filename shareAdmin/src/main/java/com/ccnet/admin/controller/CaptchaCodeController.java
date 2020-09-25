package com.ccnet.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccnet.core.common.utils.base.Const;
import com.ccnet.core.common.utils.captcha.Captcha;
import com.ccnet.core.common.utils.captcha.SpecCaptcha;
/**
 * 获取验证码
 * @author Jackie Wang
 * @see 2016-09-29
 */
@Controller
@RequestMapping("/captcha/")
public class CaptchaCodeController {
	
	  @RequestMapping("/getcode")
	  public void getCode(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		    HttpSession seesion = req.getSession();
		    resp.setContentType("image/png");// 设置相应类型,告诉浏览器输出的内容为图片
		    resp.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
		    resp.setHeader("Cache-Control", "no-cache");
		    resp.setDateHeader("Expire", 0);
			try {
				Captcha captcha = new SpecCaptcha(150,40,5);//gif格式动画验证码
		        String vcode = captcha.out(resp.getOutputStream());
		        seesion.removeAttribute(Const.SESSION_SECURITY_CODE);
				seesion.setAttribute(Const.SESSION_SECURITY_CODE,vcode);
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 
}
