<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>系统授权认证</title>
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
        <div class="lock-username">【★★授权认证★★】</div>
        <img src="${basePath}static/img/licuser.jpg" alt="license user" />
        <div class="lock-password">
             <form id="licenseForm" class="form-inline" action="" method="post">
                <div class="form-group">
                    <span class="input-icon">
                      <textarea class="form-control" rows="4" style="width:340px;" placeholder="请输入授权认证序列号,系统认证成功后终身使用，如果需要更换服务器需要重新认证，请联系开发商再次授权！"  id="license" name="license"></textarea>
                    </span>
                    <span class="input-icon">
                        <input type="hidden" readonly="readonly" class="form-control"  value='${hardware}' >
                    </span>
                    <span class="input-icon">
	                 <button type="button" class="btn btn-danger" style="width:340px;height:40px;margin-top:15px;border-radius:4px" id="licenseBtn">
	                  <i class="fa fa-key"></i>提交认证
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
