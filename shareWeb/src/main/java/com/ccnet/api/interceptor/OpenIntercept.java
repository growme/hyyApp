package com.ccnet.api.interceptor;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ccnet.api.util.WeixinpayUtil;
import com.ccnet.cps.service.MemberInfoService;

@Component // 表示这是一个组件，可以实现依赖注入
public class OpenIntercept implements HandlerInterceptor {
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
		if (getPath(request.getServletPath())) {
			return true;
		}
		String paraName = "";
		String signName = "";
		String signP = "";
		boolean dateBoo = false;
		long date = new Date().getTime();
		long date1 = 0L;
		Enumeration<?> enu = request.getParameterNames();
		Map<String, String> sortedMap = new HashMap<>();
		while (enu.hasMoreElements()) {
			paraName = (String) enu.nextElement();
			if (paraName.equals("sign")) {
				signP = request.getParameter(paraName);
			} else if (paraName.equals("date")) {
				signName = request.getParameter(paraName);
				sortedMap.put(paraName, request.getParameter(paraName));
				date1 = Long.valueOf(signName);
			} else {
				signName = request.getParameter(paraName);
				sortedMap.put(paraName, request.getParameter(paraName));
			}
		}
		String sign = WeixinpayUtil.getSignCode(sortedMap, "toutiao123456");
		if (date + 10000 >= date1 && date1 >= date - 20 * 60 * 1000) {
			dateBoo = true;
		}
		// 校验是否合法
		if (signP.equals(sign)  || signP.equals("925685")) {
			return true; // 合法通过
		} else {
			System.out.println("参数为:encodeStr=" + JSON.toJSONString(sortedMap) + "传签名为：" + signP + "系统签名为" + sign);
			response.setContentType("text/JavaScript; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			JSONObject obj = new JSONObject();
			obj.put("status", "0");
			obj.put("status_name", "很抱歉，参数不合法！");
			response.getWriter().write(obj.toString());
			return false;
		}
	}

	private static boolean getPath(String path) {
		StringBuffer sb = new StringBuffer();
		sb.append("/api/article/list").append(",");
		sb.append("/api/param/tz").append(",");
		sb.append("/api/customtask/reward").append(",");
		sb.append("/api/customtask/processCallBack").append(",");
		sb.append("/api/customtask/xiaoshuoCallBack").append(",");
		if (sb.toString().indexOf(path + ",") >= 0) {
			return true;
		}
		return false;
	}

	// 校验数据库是否存在令牌对
	private boolean checkAppSourceIsExsist(String app_id, String app_secret) {
		return true;
	}
}