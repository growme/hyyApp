package com.ccnet.admin.bh.utils.alipay;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @Author jingjie
 * @Date 2020/9/24 0024 16:55
 * @Description 支付宝工具类
 **/
public class AlipayUtils {

    /**
     * 初始化客户端
     */
    private AlipayClient alipayClient = new DefaultAlipayClient(AliPayConstants.REQUEST_URL,
            AliPayConstants.OPEN_ID, AliPayConstants.BUS_RAS_PRIVATE_KET, AliPayConstants.FORMAT,
            AliPayConstants.CHARSET, AliPayConstants.RAS_PUBLIC_KEY, AliPayConstants.SIGNTYPE);


    public void withdraw(String payAccount,String realname,double money,String remark){

        HashMap<String, Object> map = new HashMap<>();
        map.put("out_biz_no","");
        map.put("amount",new BigDecimal(money));
        map.put("payee_account",payAccount);
        map.put("payee_real_name",realname);
        map.put("payee_type",AliPayConstants.PAY_TYPE);
        map.put("remark",remark);
        String json = JSON.toJSONString(map);
        //实例化连接对象
        AlipayFundTransToaccountTransferRequest withdrawRequest = new AlipayFundTransToaccountTransferRequest();
        withdrawRequest.setBizContent(json);
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(withdrawRequest);
            if (AliPayConstants.SUCCESS_CODE.equalsIgnoreCase(response.getCode())) {
                // TODO 处理业务逻辑
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
