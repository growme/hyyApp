<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>后台管理系统登录</title>
<meta name="description" content="login page" />
<meta name="renderer" content="webkit|ie-comp|ie-stand" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${siteIco}" type="image/x-icon" />
<%@ include file="../../../common/includes.jsp"%>
<link href="${basePath}static/css/amazeui.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/style.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<header class="sso_header">
	  <a href="${basePath}" class="logo" style="background:url(${siteLogo})left top no-repeat"></a>
	</header>
	<form action="" method="post" name="loginForm">
		<section class="content_box">
			<div class="am-form am-form-horizontal">
				<div class="am-form-group">
				  <div class="am-u-sm-12">
					<input type="text" placeholder="请输入您的登录账户" name="login_account" id="accountNameId" class="user-name" value="">
			      </div>
				</div>

				<div class="am-form-group">
				 <div class="am-u-sm-12">
				  <input type="password" placeholder="请输入您的登录密码" name="login_password" id="passwordId" class="pass-word" value="">
				 </div>
				</div>
				
				<div class="am-form-group">
					<div class="am-u-sm-8 am-padding-right-xs">
					<input type="text" placeholder="请输入验证码" id="verifyCodeId" name="captcha" class="pass-word" value="">
					</div>
					<div class="am-u-sm-4 am-padding-left-0">
					 <img class="authcodeimg" id="check_code" title="点击换一张" alt="验证码获取中..">
					</div>
				</div>

				<div class="am-form-group">
				   <div class="am-u-sm-12">
					<button id="loginBtn" type="button" class="am-btn am-btn-danger am-btn-block">立即登录</button>
				   </div>
				</div>

				<h5 class="reg_now">
				    系统将不再兼容IE8以下浏览器，建议使用谷歌、360浏览器（关闭兼容模式）、火狐浏览器访问!
				</h5>
			</div>
		</section>
		<footer>
			<div class="slogan">— 自媒体广告分销平台 —</div>
			<div style="color: #ccc;" class="am-text-sm am-margin-top-xs am-text-center">
			   ${SYSPARAM.BOTTOM_COPYRIGHT}
			</div>
		</footer>
	</form>
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${ccnetpath}/static/js/fastclick.min.js"></script>
    <script src="${ccnetpath}/static/js/admin/login/login.js"></script>
</body>
<!--Body Ends-->
</html>
