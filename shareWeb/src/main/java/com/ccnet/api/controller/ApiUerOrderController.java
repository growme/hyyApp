package com.ccnet.api.controller;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ccnet.api.entity.Headers;
import com.ccnet.api.entity.PayUserOrder;
import com.ccnet.api.entity.ResultDTO;
import com.ccnet.api.entity.ResultCode.BasicCode;
import com.ccnet.api.service.PayUserOrderService;
import com.ccnet.api.util.WXUtil;
import com.ccnet.api.util.WxNativeService;
import com.ccnet.core.controller.BaseController;
import com.ccnet.cps.dao.MemberInfoDao;
import com.ccnet.cps.entity.MemberInfo;

/**
 * 提现相关
 */
@Controller
@RequestMapping("/api/userOrder/")
public class ApiUerOrderController extends BaseController<PayUserOrder> {

	@Autowired
	PayUserOrderService payUserOrderService;
	@Autowired
	MemberInfoDao memberInfoDao;

	@RequestMapping(value = "wxWithdraw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO<?> fetchVerifyCodeFor(Headers headers, PayUserOrder userOrder) {
		try {
			if (StringUtils.isBlank(headers.getUserid())) {
				ResultDTO.ERROR(BasicCode.参数错误);
			}
			// 当前用户
			MemberInfo memberInfo = memberInfoDao.getUserByUserID(Integer.valueOf(headers.getUserid()));
			long lo = new Date().getTime();
			int i = (int) (Math.random() * 900 + 100);
			userOrder.setOrderNo(lo + "" + i);
			userOrder.setUserId(memberInfo.getMemberId().longValue());
			userOrder.setPayType("weixin");
			userOrder.setCreateTime(new Date());
			userOrder.setUpdateTime(new Date());
			userOrder.setStatus(0);
			payUserOrderService.insert(userOrder);
		} catch (Exception e) {
			e.printStackTrace();
			ResultDTO.ERROR(BasicCode.系统繁忙);
		}
		return ResultDTO.OK();
	}

	@RequestMapping(value = "/success")
	public String wxpaySucc(HttpServletRequest request) {
		// 商户处理后同步返回给微信参数：
		String xmlResultSuccess = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
				+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		String xmlResultFailure = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
				+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		JSONObject element = null;
		try {
			InputStream inStream = request.getInputStream();
			JSONObject jsonobj = WXUtil.xml2JSON(inStream);
			element = jsonobj.getJSONObject("xml");
			inStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 验证签名
		boolean signVerified = WxNativeService.validateByKM(WXUtil.wxResultToMap(element));
		if (!signVerified) {

			// 将订单状态设置为待支付
			return xmlResultFailure;
		} else {
			String return_code = (String) element.get("return_code");
			String out_trade_no = (String) element.get("out_trade_no");
			// String return_code = ((JSONArray)
			// element.get("return_code")).get(0).toString();// 状态
			// String out_trade_no = ((JSONArray)
			// element.get("out_trade_no")).get(0).toString();// 订单号
			System.out.println("订单号out_trade_no:" + out_trade_no);
			PayUserOrder userOrder = payUserOrderService.getByOrderNo(out_trade_no);
			if (userOrder != null && userOrder.getStatus() == 0) {
				if (return_code.equals("SUCCESS")) {
					PayUserOrder record = new PayUserOrder();
					record.setOrderNo(out_trade_no);
					record.setStatus(1);
					record.setUpdateTime(new Date());
					payUserOrderService.update(userOrder, "orderId");
					return xmlResultSuccess;
				} else {
					return xmlResultSuccess;
				}
			}
			return xmlResultFailure;
		}
	}
}
