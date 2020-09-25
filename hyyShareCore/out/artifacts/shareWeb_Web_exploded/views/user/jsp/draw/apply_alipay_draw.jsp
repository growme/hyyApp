<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_支付宝提现</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta content="telephone=no" name="format-detection">
<meta name="apple-touch-fullscreen" content="yes">
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
            <span class="title">支付宝提现</span>
            <a href="${basePath}draw/list.html"><span class="menu"><i class="iconfont icon-tixianjilu"></i>&nbsp;提现记录</span></a>
        </div>
    </header>
    <div class="yzk_content">
        <div class="yzk_tixian_now">
            <h1><em>
           <fmt:formatNumber value="${empty userMoney.tmoney ? 0 : userMoney.tmoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em></h1>
            <p>账户余额</p>
            <input type="hidden" id="tomoney" value="${empty userMoney.tmoney ? 0 : userMoney.tmoney}">
        </div>
        
         <div class="content_foot" style="padding: 10px 15px;">
            <div class="foot_title"><span>友情提示！</span></div>
         </div>
         <div class="infomation">
           <p style="width: 100%;">首次提现，<span stle="color:red">可以提取5元！</span> </p>
           <p style="width: 100%;">工作时间：周一至周六  早上：<span>10:00-19:00</span></p>
           <p style="width: 100%;">打款时间：周一至周六  中午<span>15:00</span>之前提现的当天到账，<span>15:00</span>之后提现的次日到账。</p>
           <p style="width: 100%;">注：周日不打款，周六<span>15:00<span></span>之后提现的和<span>周日</span>提现的会在<span>周一</span>打款</span></p>
        </div>
        
        <form id="cashForm" name="cashForm">
        <div class="wx_tixian_money">
        	<p class="wx_tixian_txt01" <c:if test="${!empty MSESSION_USER.payAccount}"> style="background:#fafafa" </c:if>>
        	    <input type="hidden" id="mobile" name="mobile" value="${mobile}">
                <input type="text" id="payAccount" name="payAccount" <c:if test="${!empty MSESSION_USER.payAccount}"> style="background:#fafafa" readonly</c:if> placeholder="请输入您的支付宝账户" value="${MSESSION_USER.payAccount}">
            </p>
            <p class="wx_tixian_txt01" <c:if test="${!empty MSESSION_USER.accountName}"> style="background:#fafafa" </c:if>>
                <input type="text" id="accountName" name="accountName" <c:if test="${!empty MSESSION_USER.accountName}"> style="background:#fafafa" readonly</c:if> placeholder="请输入支付宝账户姓名" value="${MSESSION_USER.accountName}">
            </p>      
            <p class="wx_tixian_txt01">
               <input type="text" id="cashMoney" name="cashMoney" type="number" placeholder="请输入需要提现的金额" value="">
               <%--
               <span class="cash_info">
	              <span class="cashspan" >10</span>
	              <span class="cashspan" >30</span>
	              <span class="cashspan" >50</span>
	              <span class="cashspan" >100</span>
	              <span class="cashspan" >150</span>
               </span>
            --%>
            </p>
            <p class="wx_tixian_txt01">
                <input type="text" id="captcha" name="captcha" placeholder="请输入图形验证码" value="">
                 <img class="cimg" src="<%=path%>/captcha/getcode" alt="验证码"/>
            </p> 
            <p class="wx_tixian_txt01">
                <input type="text" id="checkCode" name="checkCode" placeholder="请输入语音验证码" value="">
                <a id="btnSendCode" name="btnSendCode" class="sqbtn">获取验证码</a>
            </p>        
            
            <div class="wx_tixian_btn02"><a href="javascript:void(0);" id="alitx">立即提现</a></div>

			<div class="content_foot">
				<div class="foot_title">
					<span>温馨提示！</span>
				</div>
				<div class="font_content">
					<p>三个工作日内到账</p>
					<p>实名验证的支付宝才可以收到提现</p>
					<p>支付宝账号和姓名一经绑定则不能修改,如有问题需要修改请联系客服</p>
				</div>
				<div class="font_content">
					<p class="kufu">
					为了保证用户的收益以及提现顺利到账，提现出现以下情况的用户例如：失败、冻结、拒付
					出现这些情况的用户请及时联系平台客服处理问题，
					<br/>请速加官方群QQ <a href="${basePath}about/index.html" class="add_qq_qun">一键添加</a>
					</p>
				</div>
			</div>
			
        </div>
        </form>
    </div>
    <%@ include file="../../../common/footer.jsp"%>
    <%@ include file="../../../common/com_js.jsp"%>
    <script type="text/javascript" src="<%=path %>/static/script/user/draw/apply_alipay_draw.js"></script>
</body></html>