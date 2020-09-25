<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_修改密码</title>
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
			<div class="item">
			    <input id="token" name="token" type="hidden" value="${token}">
			    <input id="account" name="account" type="hidden" value="${account}">
				<input id="password1" name="password" class="txt-input txtpd" placeholder="输入新密码" type="password">
			</div>
			<div class="item">
				<input id="password2" name="password" class="txt-input txtpd" placeholder="再次输入新密码" type="password">
			</div>
			<div class="item item-btns">
				<a id="changeSubmit" class="btn-login">确认提交</a>
			</div>
		</div>
	</div>

<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript" src="<%=path %>/static/script/user/forget/change_password.js" ></script>
</body>
</html>