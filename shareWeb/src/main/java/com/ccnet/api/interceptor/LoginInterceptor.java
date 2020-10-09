package com.ccnet.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ccnet.api.util.AESUtils;
import com.ccnet.api.util.TokenUtil;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.cps.service.MemberInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component // 表示这是一个组件，可以实现依赖注入
public class LoginInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	// 校验的数据存在数据库中，需要查询数据库
	@Autowired
	MemberInfoService memberInfoService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String token = request.getParameter("sign");
		logger.info("请求之前进行token验证：{}",token);
		response.setContentType("text/JavaScript; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject obj = new JSONObject();

		if (StringUtils.isEmpty(token)){
			obj.put("status", "0");
			obj.put("status_name", "token不能为空");
			response.getWriter().write(obj.toString());
			return false;
		}
		//校验token
		String decrypt = AESUtils.decrypt(token);
		logger.info("token解析结果：{}",decrypt);
		String memId = decrypt.split(":")[0];
		String redisToken = JedisUtils.get(TokenUtil.getKey(memId));
		if (!token.equals(redisToken)){
			obj.put("status", "10000");
			obj.put("status_name", "登录失效");
			response.getWriter().write(obj.toString());
			logger.info("token校验失败，token无效，请重新登录，token:{},rToken:{}",token,redisToken);
			return false;
		}
		logger.info("token校验成功，开始请求。。。。");
		return true;
	}
}