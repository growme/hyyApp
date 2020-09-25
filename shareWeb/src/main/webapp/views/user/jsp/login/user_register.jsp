<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_会员注册</title>
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
       <h1>会员注册</h1>
    </header>
	<div class="common-wrapper">
		<div class="linest">
			已经发放 <a style="color: #f00; font-weight: bold;">${SYSPARAM.SYS_DEFAULT_BOUNS}</a>元奖金
		</div>
		<div class="main">
			
			<form action="" method="post" id="frm_login">
				<div class="item">
					<input class="txt-input txtpd" name="username" id="username" value="" placeholder="请输入手机号" type="text">
				</div>
				<div class="item">
					<input id="password" name="password" class="txt-input txtpd" placeholder="请输入密码" type="password">
				</div>
				<div class="item">
					<input id="rpassword" name="rpassword" class="txt-input txtpd" placeholder="请确认密码" type="password">
				</div>
				<div class="item">
					<input id="captcha" name="captcha" class="txt-input-check txtpd" placeholder="请输入图像验证码" type="text">
				    <img class="cimg" src="<%=path%>/captcha/getcode" alt="验证码"/>
				</div>
				
				<div class="item">
					<input class="txt-input-check txtpd" type="text" id="checkCode" name="checkCode" placeholder="输入短信验证码">
					<input type="button" id="btnSendCode" name="btnSendCode" class="sqbtn" value="获取验证码" />
				</div>
				
				<div class="item">
			        <c:if test="${!empty recom_user}">
					   <label for="number" style="width:400px;font-weight:bold;font-size:24px;">邀请人：${recom_user}</label>
			           <input type="hidden" id="recom_user" name="recom_user" value="${recom_user}">
			        </c:if>
				</div>
				
				<div class="linest1">
					每天只能发送5条验证码，发送后请耐心等待,切勿重复点击!
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
				<div class="item item-btns">
				  <a id="registerSubmit" class="btn-login">确认注册</a>
				</div>
			</form>
			<div class="item item-login-option">
				<span class="register-free">
				  <a rel="nofollow" href="<%=path%>/user/login">返回登录</a>
				</span>
			</div>
		</div>
	</div>
<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript" src="<%=path %>/static/script/user/register/register.js" ></script>