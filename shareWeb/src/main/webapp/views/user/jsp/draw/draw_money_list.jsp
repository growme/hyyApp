<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_提现记录</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
<link rel="stylesheet" href="${basePath}static/css/layer.css" id="layuicss-skinlayercss">
</head>
<body class="yzk_bg">
	<header class="header">
		<div class="header-menu">
			<a href="${basePath}home/index.html">
			<i class="iconfont icon-left"></i>
			</a> 
			<span class="title">提现记录</span> 
			<a href="${basePath}draw/cash.html">
			<span class="menu"><i class="iconfont icon-tixian"></i>&nbsp;我要提现</span>
			</a>
		</div>
	</header>
	<div class="yzk_content">
		<div class="yzk_tixian1">
			<ul>
			<c:if test="${!empty cashLogs.results}">
			<c:forEach var="logs" varStatus="status" items="${cashLogs.results}">
				<li>
				<span class="icon ${logs.typeName}">
				 <i class="iconfont icon-${logs.typeName}"></i> 
			    </span>
				<p class="date"><fmt:formatDate value="${logs.updateTime}" type="both" pattern="yyyy-MM-dd hh:mm:ss"/></p> 
				<p class="mons">+<fmt:formatNumber value="${logs.cmoney}" pattern="0.00" type="currency"/></p>
				<span class="status">${logs.stateName}</span>
				</li>
			</c:forEach>
			</c:if>
			</ul>
		</div>

		<c:if test="${empty cashLogs.results}">
			<div class="yzk_msg">您目前还没有提现记录！</div>
		</c:if>
	</div>
	
<%@ include file="../../../common/footer.jsp"%>
<script type="text/javascript" src="${basePath}static/script/jquery.min.js"></script>
</body>
</html>