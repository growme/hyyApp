<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_找回密码</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta content="telephone=no" name="format-detection">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<link type="text/css" href="<%=path %>/static/css/${empty SYSPARAM.USER_DEFAULT_THEME?'red':SYSPARAM.USER_DEFAULT_THEME}/theme.css" rel="stylesheet" >
<style>
body {
	margin: 0;
}

* {
	box-sizing: border-box;
}

input {
	font-size: 16px;
	line-height: 1.25em;
	outline: 0px none;
	text-decoration: none;
	margin: 0;
}

</style>
</head>

<body>
	<header class="log_header">
       <h1>找回密码</h1>
    </header>
	<div class="common-wrapper">
		<div class="main">
			<div class="linest1">
				转客您好，每天最多只能发送5条验证码哦，点击发送验证码后请耐心等待！！切勿重复点击！！
			</div>
			<div class="item">
				<input class="txt-input txtpd" name="phone" id="phone" value="" placeholder="请输入手机号" type="text">
			</div>
			
			<div class="item">
				<input id="captcha" name="captcha" class="txt-input-check txtpd" placeholder="请输入图像验证码" type="text">
			    <img class="cimg" src="<%=path%>/captcha/getcode" alt="验证码"/>
			</div>
			
			<div class="item">
				<input class="txt-input-check txtpd" type="text" id="checkCode" name="checkCode" placeholder="输入短信验证码">
				<input type="button" id="btnSendCode" name="btnSendCode" class="sqbtn" value="获取验证码" />
			</div>

			<div class="item item-btns">
				<a id="forgetSubmit" class="btn-login">确认提交</a>
			</div>
			<div class="item item-login-option">
				<span class="register-free">
                      <a href="<%=path %>/user/login">返回登录</a>
				</span>
				<span class="retrieve-password">
				   <a rel="nofollow" href="<%=path %>/user/register">注册账号</a>
				</span>
			</div>
		</div>
	</div>
<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript" src="<%=path %>/static/script/user/forget/forget.js" ></script>
</body>
</html>