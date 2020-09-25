<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>系统锁屏中</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="bodybg">
<div class="lock-container animated fadeInDown">
    <div class="lock-box text-align-center">
        <div class="lock-username">【admin】</div>
        <img src="${basePath}static/img/licuser.jpg" alt="license user" />
        <div class="lock-password">
             <form id="licenseForm" class="form-inline" action="" method="post">
                <div class="form-group">
                    <span class="input-icon">
                      <input class="form-control" id="lockpwd" name="lockpwd" placeholder="请输入登录密码解锁" type="password" value="">
                      <i class="glyphicon glyphicon-log-in themeprimary"></i>
                    </span>
                    <span class="input-icon">
                        <input type="hidden" readonly="readonly" class="form-control"  value='${hardware}' >
                    </span>
                    <span class="input-icon">
	                 <button type="button" class="btn btn-danger" style="width:340px;height:40px;margin-top:15px;border-radius:4px" id="licenseBtn">
	                  <i class="fa fa-key"></i>解除锁定
	                 </button>
                    </span>
                </div>
            </form>
        </div>
    </div>
    <div class="signinbox">
        <span>无法完成认证?</span>
        <a href="http://www.hnccnet.com" target="_blank">联系开发商</a>
    </div>
</div>
<%@ include file="../../../common/lcom_js.jsp"%>
<script src="${ccnetpath}/static/js/admin/login/license.js"></script>
<script>
    clearRollBar(true);
</script>
</body>
</html>
