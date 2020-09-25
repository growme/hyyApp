<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_个人设置</title>
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
            <span class="title">用户设置</span>
            <a href="${basePath}article/list.html">
            <span class="menu"><i class="iconfont icon-tixianjilu"></i>&nbsp;开始赚钱</span>
            </a>
        </div>
    </header>
    <div class="yzk_content">
         <div class="yzk_tixian_now">
            <h1><em>
           <fmt:formatNumber value="${empty userMoney.tmoney ? 0 : userMoney.tmoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em></h1>
            <p>账户余额</p>
            <input type="hidden" id="tomoney" value="${empty userMoney.tmoney ? 0 : userMoney.tmoney}">
        </div>
        <form id="cashForm" name="cashForm">
        <div class="wx_tixian_money">
            <p class="wx_tixian_txt01" <c:if test="${!empty member.mobile}"> style="background:#fafafa" </c:if>>
                <em>手机号码</em>
                <input type="text" id="mobile" name="mobile" placeholder="请输入您的 手机号码" ${!empty member.mobile ? "readonly style='background:#fafafa'":''} value="${member.mobile}">
            </p>
        	<p class="wx_tixian_txt01">
                <em>QQ 号码</em>
                <input type="text" id="qqNum" name="qqNum" placeholder="请输入您的  QQ 号码"  value="${member.qqNum}">
            </p>
            <p class="wx_tixian_txt01" <c:if test="${!empty member.payAccount}"> style="background:#fafafa" </c:if>>
                <em>支付宝号</em>
                <input type="text" id="payAccount" name="payAccount" ${!empty member.payAccount ? "readonly style='background:#fafafa'":''} placeholder="请输入您的支付宝账号" value="${member.payAccount}">
            </p>  
            <p class="wx_tixian_txt01" <c:if test="${!empty member.accountName}"> style="background:#fafafa" </c:if>>
                <em>认证姓名</em>
                <input type="text" id="accountName" name="accountName" ${!empty member.accountName ? "readonly style='background:#fafafa'":''} placeholder="请输入支付宝认证姓名" value="${member.accountName}">
            </p> 
            <p class="wx_tixian_txt01" <c:if test="${!empty member.wechat}"> style="background:#fafafa" </c:if>>
                <em>微信账号</em>
                <input type="text" id="wechat" name="wechat" ${!empty member.wechat ? "readonly style='background:#fafafa'" : ''} placeholder="请输入您常用微信账号" value="${member.wechat}">
            </p>      
            
            <div class="wx_tixian_btn02"><a href="javascript:void(0);" id="saveBtn">立即提交</a></div>

			<div class="content_foot">
				<div class="foot_title">
					<span>温馨提示！</span>
				</div>
				<div class="font_content">
					<p>为了保证您的资金安全，请填写真实个人信息</p>
					<p>实名认证的支付宝和微信号才可以收到提现</p>
					<p>一经绑定不能修改。如有问题请联系客服</p>
				</div>
				<div class="font_content">
					<p class="kufu">
					账号出现冻结无法登录情况的用户请及时联系平台客服，为方便联系客服处理问题，
					<br/>请速加官方群QQ <a href="${basePath}about/index.html" class="add_qq_qun">一键添加</a>
					</p>
				</div>
			</div>
			
        </div>
        </form>
    </div>
    <%@ include file="../../../common/footer.jsp"%>
    <%@ include file="../../../common/com_js.jsp"%>
    <script type="text/javascript" src="<%=path %>/static/script/user/setting/setting.js"></script>
</body></html>