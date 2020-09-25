<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>${SYSPARAM.SITE_NAME}_我的徒弟</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
</head>
<body class="yzk_bg">
    <header class="header">
        <div class="header-menu">
            <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
            <span class="title">我的徒弟</span>
            <a href="${basePath}invited/detail.html"><span class="menu"><i class="iconfont icon-vip"></i>&nbsp;邀请徒弟</span></a>
        </div>
    </header>
    <div class="yzk_content">
        <div class="yzk_yaoqing_menu">
            <ul>
                <li ><a href="${basePath}invited/list.html">我的徒弟</a></li>
                <li class="on"><a href="${basePath}invited/apply.html">平台申请</a></li>
            </ul>
        </div>
        <div class="yzk_msg">还没有相关数据！</div>         
    </div>
</body>
</html>
