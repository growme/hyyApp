<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_收徒二维码</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
</head>
 <body>
      <header class="header">
      <div class="header-menu">
       <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
       <span class="title">收徒赚钱</span>
       <a href="${basePath}invited/dlist.html"><span class="menu"><i class="iconfont icon-vip"></i>&nbsp;我的徒弟</span></a>
      </div>
     </header>
     <div class="yzk_content">
        <div class="yzk_huabao">
       <p><img src="${basePath}barcode/getcode.html" height="100%" width="100%"></p>
     </div>
    </div>
  </body>
</html>
