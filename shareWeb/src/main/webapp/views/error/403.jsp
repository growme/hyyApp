<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/includes.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>403错误提示</title>
    <meta name="description" content="403错误提示" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${basePath}static/css/error.css">
	
  </head>
  
 <body class="body-404">
     <div id="main">
		<header id="header">
			<h1><span class="icon">!</span>403</h1>
		</header>
		<div id="content">
			<h2></h2>
			<p>1.当您看到这个页面,表示您的访问出错,服务器出现内部错误！</p>
			<p>2.请确认您输入的地址是正确的</p>
			<p>3.如果是在本站点击后出现这个页面,请联系站长进行处理</p>
			<p>4.您可以访问我们的系统首页正常访问</p>
			<div class="utilities">
				<a class="button right" href="#" onclick="history.go(-1);return true;">返回..</a>
				<div class="clear"></div>
			</div>
		</div>
	</div>
  </body>
</html>
