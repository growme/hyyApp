<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/redirect301.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title></title>
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
</head>
<body class="zh_CN" id="activity-detail">
<c:if test="${!empty content.articleUrl}">
<div id="cnzz_code" style="display:none">
  ${scriptCnzz}
  <script type="text/javascript" src="${bcnzzCode}"></script>
  <script type="text/javascript" src="${acnzzCode}"></script>
  <script type="text/javascript" src="${mcnzzCode}"></script>
</div>
</c:if>
<script type="text/javascript">
    window.onload=function(){
    	goGPage("${articleUrl}");
    }
    
    function goGPage(url){
	    if(url!=null && url!=""){
	      setTimeout(function(){
	   	     window.top.location.href = url;
	   	  },500);
	    }
    }
</script>
</body>
</html>