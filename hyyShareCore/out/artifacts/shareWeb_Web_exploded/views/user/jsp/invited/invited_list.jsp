<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_我的徒弟</title>
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
            <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
            <span class="title">我的徒弟</span>
            <a href="${basePath}invited/detail.html"><span class="menu"><i class="iconfont icon-vip"></i>&nbsp;邀请徒弟</span></a>
        </div>
    </header>
    <div class="yzk_content">
        <div class="yzk_yaoqing_menu">
            <ul>
                <li class="${tp=='1'?'on':''}"><a href="${basePath}invited/dlist.html">我的徒弟</a></li>
                <li class="${tp=='2'?'on':''}"><a href="${basePath}invited/slist.html">我的徒孙</a></li>
            </ul>
        </div>
        <div class="yzk_tixian">
			<ul>
			<c:if test="${!empty memberInfos}">
			<c:forEach var="memberInfo" items="${memberInfos}">
				<li>
			    <p class="img"><i class="iconfont icon-huiyuan"></i></p>
				<span class="membername">${memberInfo.memberName}(${memberInfo.filterPhone})</span>
				<span class="regtime"><fmt:formatDate value="${memberInfo.registerTime}" type="both" pattern="yyyy-MM-dd"/></span>
				</li>
			</c:forEach>
			</c:if>
			</ul>
		</div>
		<c:if test="${empty memberInfos}">
        <div class="yzk_msg">还没有相关数据！</div>     
        </c:if>       
    </div>
</body>

</html>
