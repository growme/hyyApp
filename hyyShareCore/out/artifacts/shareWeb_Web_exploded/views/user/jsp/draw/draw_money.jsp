<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_用户提现</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
</head>
<body class="yzk_bg">
    <header class="header">
        <div class="header-menu">
            <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
            <span class="title">我要提现</span>
            <a href="${basePath}draw/list.html"><span class="menu"><i class="iconfont icon-tixianjilu"></i>&nbsp;提现记录</span></a>
        </div>
    </header>
    
    <div class="yzk_content">
        <div class="yzk_tixian_now">
            <h1><em><fmt:formatNumber value="${empty userMoney.tmoney ? 0 : userMoney.tmoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em></h1>
            <p>账户余额</p>
        </div>
        <ul class="yzk_tixian_way">
            <li>
                 <a href="${basePath}draw/wechat.html">
                    <span class="icon wechat"><i class="iconfont icon-wechat"></i></span>
                    <span class="title">微信提现</span>
                    <span class="desc">单次最高200元</span>
                    <i class="iconfont icon-right"></i>
                </a>
            </li>        
            <li>
                <a href="${basePath}draw/alipay.html">
                    <span class="icon alipay"><i class="iconfont icon-alipay"></i></span>
                    <span class="title">支付宝提现</span>
                    <span class="desc">需要实名认证账号</span>
                    <i class="iconfont icon-right"></i>
                </a>
            </li>
        </ul>
    </div>
<%@ include file="../../../common/footer.jsp"%>

</body></html>