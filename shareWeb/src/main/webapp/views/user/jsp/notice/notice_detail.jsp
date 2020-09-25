<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_公告中心</title>
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
<body class="yzk_bg">
	<header class="header">
		<div class="header-menu">
			<a href="${basePath}notice/index.html"><i class="iconfont icon-left"></i></a>
			<span class="title">公告内容</span> 
		    <a href="${basePath}notice/index.html"><span class="menu"><i class="iconfont icon-notice"></i>&nbsp;公告中心</span></a>
		</div>
	</header>
	<div class="yzk_content">
		<div class="yzk_notices">
			<h1>
				${noticeInfo.noticeTitle}<em><fmt:formatDate value="${noticeInfo.createTime}" type="both" pattern="MM-dd HH:mm:ss"/></em>
			</h1>
			<div class="yzk_notices_desc">
				${noticeInfo.noticeContent}
			</div>
		</div>
	</div>
</body>
</html>