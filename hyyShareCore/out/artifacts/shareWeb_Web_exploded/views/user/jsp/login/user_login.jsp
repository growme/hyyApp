<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_登录</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
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
<%@ include file="../../../common/com_css.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<link type="text/css" href="<%=path %>/static/css/${SYSPARAM.USER_DEFAULT_THEME}/theme.css" rel="stylesheet" >
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
	   <h1>会员登录</h1>
	</header>
	<div class="common-wrapper">
		<div class="linest">
			累积发放 <a style="color: #f00; font-weight: bold;"> ${SYSPARAM.SYS_DEFAULT_BOUNS} </a>元奖金
		</div>
		<div class="main">
			<form action="" method="post" id="frm_login">
				<div class="item">
					<input class="txt-input txtpd" name="loginAccount" id="loginAccount" value="" placeholder="请输入手机号码" maxlength="11" type="tel">
				</div>
				<div class="item">
					<input id="loginPassword" name="loginPassword" class="txt-input txtpd" placeholder="请输入登录密码" type="password">
				</div>
				<div class="item" id="captcha" style="display:none;">
					<input id="yzm" name="yzm" class="txt-input-check txtpd" placeholder="请输入图形验证码" type="text"> 
					<img class="cimg" src="" alt="验证码" /> 
				</div>
				<div class="item item-btns">
					<a id="loginSubmit" class="btn-login">开始赚钱</a>
				</div>
			</form>
			<div class="item item-login-option">
				<span class="register-free"><a rel="nofollow"
					href="<%=path %>/user/register">免费注册</a> </span> <span
					class="retrieve-password"><a href="<%=path %>/user/forget">找回密码</a>
				</span>
			</div>
			<div class="linest1">
				<div style="margin:5px;">
					<span style="margin-top:15px">把你喜欢的文章转发到:<b>
					<font style="color:#e12634;"> 微信好友</font>|<font style="color:#e12634;">朋友圈</font>
					</b>
					</span>
					<span style="margin-top:15px;font-size:14px;"> 
					注册即送<font style="color:#e12634;">1-10</font>元红包，
					邀请朋友将获得<font style="color:#e12634;">0.5</font>元奖励,
					并且获得其收益<font style="color:#e12634;">20%</font>永久分成</span>
					<span style="margin-top:15px">有朋友/朋友的朋友，阅读文章就有奖励</span>
					<span style="margin-top:15px">每次阅读奖励<font style="color:#e12634;">0.01-0.25</font>元,一天一分钟，月入过万</span>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="../../../common/com_js.jsp"%>
	<script type="text/javascript" src="<%=path %>/static/script/user/login/login.js"></script>
</body>
</html>
