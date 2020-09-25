<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<a href="${basePath}home/index.html">
			<i class="iconfont icon-left"></i>
			</a> 
			<span class="title">公告中心</span> 
			<a href="${basePath}article/list.html">
			<span class="menu"><i class="iconfont icon-tixian"></i>&nbsp;开始赚钱</span>
			</a>
		</div>
	</header>
	<div class="yzk_content">
		<div class="yzk_tixian">
			<ul>
			<c:if test="${!empty noticeList}">
			<c:forEach var="nt" varStatus="status"  items="${noticeList}">
			<li>
			<span class="atitle">
			  <a style="color:#09c" href="${basePath}notice/detail.html?nid=${nt.noticeId}" title="${nt.noticeTitle}">
				${status.index+1}. ${nt.noticeTitle}
			  </a>
			</span>
			<p class="mons" style="color:#868686">
			 <fmt:formatDate value="${nt.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
			 <span class="status">已发布</span>
			</p> 
			</li>
			</c:forEach>
			</c:if>
			</ul>
		</div>
		<c:if test="${empty noticeList}">
		 <div class="yzk_msg">没有查询到公告信息！</div>
		</c:if>
	</div>
	
<%@ include file="../../../common/footer.jsp"%>
</body>
</html>