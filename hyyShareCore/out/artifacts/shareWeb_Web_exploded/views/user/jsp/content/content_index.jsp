<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html style="font-size:50px;">
<head>
<title>${SYSPARAM.SITE_NAME}_热点文章</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
<link type="text/css" rel="stylesheet" href="${basePath}static/css/main.min.css">
<style type="text/css">
 .yzk_foot{
   padding-top:10px;
 }
</style>
</head>
<body class="yzk_bg">
<%@ include file="../../../common/topper.jsp"%>
<div class="top-article-category">
<div class="horizontal">
<div class="list">
<a class="item ${empty columnId ? 'active' : ''}" href="${basePath}article/list">全部</a>
<c:forEach var="cl" varStatus="status" items="${columns}">
<a href="${basePath}article/list?columnId=${cl.columnId}" class="item ${cl.columnId eq columnId ?'active':''}">${cl.columnName}</a>
</c:forEach>
</div>
<div class="expand">
<i class="iconfont icon-down ff"></i>
</div>
</div>
<div class="vertical hide">
<div class="list">
<a class="item " href="${basePath}article/list">全部</a>
<c:forEach var="cl" varStatus="status" items="${columns}">
<a href="${basePath}article/list?columnId=${cl.columnId}" class="item ${cl.columnId eq columnId ?'active':''}">${cl.columnName}</a>
</c:forEach>
</div>
<div class="masked"></div>
</div>
</div>

<div class="shortcut_channel">
<h1>
	批量复制(前0篇)小技巧:获取链接后长按指纹区域可<span>全选</span>
</h1>
<div class="lianjie">
	<a href="javascript:;" class="top-link-button" id="getUrlButton">获取群推广链接</a>
</div>
<button class="copyButton apply_nav_header" style="display: none;" data-clipboard-action="copy" data-clipboard-target="#cp">一键复制</button>
</div>

<div class="yzk_content1">
<div class="zyk_con_switch">
	<div class="news_con">
		<ul class="news_con_col">
		<div class="article-list" id="article-list"></div>
		</ul>
		
		<p id="loading" style="padding:45px 0px;width:100%;text-align:center;background:#ffffff">
		  <img src="${basePath}static/images/loading2.gif" />
		</p>
		<input type="hidden" id="columnId" value="${columnId}">
		<input type="hidden" id="basePath" value="${basePath}">
		<div class="div_null">
		  <span class="pullUpLabel">&nbsp;</span>
		</div>
		</div>
	</div>
</div>
<div class="tc_news2" style="display: none;"></div>
<div id="xgyindao" style="display:none;"></div>
<%@ include file="../../../common/footer.jsp"%>
<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript">
 if( window.top.location.href !== window.location.href ){ 
     window.top.location.href =  window.location.href;
 }
</script>
<script type="text/javascript" src="${basePath}static/script/clipboard.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="${basePath}static/script/user/content/article.js"></script>
</body>
</html>