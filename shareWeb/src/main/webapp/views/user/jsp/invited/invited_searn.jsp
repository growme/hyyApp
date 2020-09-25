<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>${SYSPARAM.SITE_NAME}_今日徒孙收益</title>
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
            <span class="title">徒孙收益</span>
            <a href="${basePath}invited/list.html"><span class="menu"><i class="iconfont icon-vip"></i>&nbsp;我的徒弟</span></a>
        </div>
    </header>
    <div class="yzk_content">
        <div class="yzk_yaoqing_menu">
            <ul>
				<li>
				<a href="${basePath}invited/tearn.html">今日徒弟收益</a>
				</li>
				<li class="on"><a href="${basePath}invited/searn">今日徒孙收益</a>
				</li>
				
			</ul>
		</div>
		<div class="yzk_tixian">
			<ul>
			<c:if test="${!empty invitedCounts}">
			<c:forEach var="moneycount" items="${invitedCounts}">
				<li>
					<span class="atitle">${fn:substring(moneycount.sbContentInfo.contentTitle, 0, 18)}</span>
					<p class="mons">
					 +<fmt:formatNumber value="${moneycount.umoney}"  pattern="0.00"/>
					 <span style="font-size:14px;color:#868686">(${moneycount.invitedMemberInfo.filterPhone})</span>
					 <span class="status"><fmt:formatDate value="${moneycount.createTime}" type="both" pattern="MM-dd HH:mm:ss"/></span>
					</p> 
					
				</li>
			</c:forEach>
			</c:if>
			</ul>
		</div>
		<c:if test="${empty invitedCounts}">
		<div class="yzk_msg">还没有记录</div>
		</c:if>
        </div>
</body>
</html>