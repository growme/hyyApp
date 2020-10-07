package com.ccnet.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ccnet.api.util.AESUtils;
import com.ccnet.api.util.TokenUtil;
import com.ccnet.core.common.utils.redis.JedisUtils;
import com.ccnet.cps.service.MemberInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component // 表示这是一个组件，可以实现依赖注入
public class LoginInterceptor implements HandlerInterceptor {

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
		String memId = decrypt.split(":")[0];
		String redisToken = JedisUtils.get(TokenUtil.getKey(memId));
		if (!token.equals(redisToken)){
			obj.put("status", "10000");
			obj.put("status_name", "登录失效");
			response.getWriter().write(obj.toString());
			return false;
		}
		return true;
	}
}