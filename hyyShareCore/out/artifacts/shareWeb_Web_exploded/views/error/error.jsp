<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>错误提示</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="${basePath}static/css/estyle.css">
<style type="text/css">
* {
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
}
</style>
</head>
<body>
	<div>
		<div style="text-align: center">
			<img src="${basePath}static/images/error.png" style="display: block; max-width: 100%; height: auto; margin: 0 auto;">
		</div>
		<p style="text-align: center; font-size: 2em; color: #333">很抱歉，出错了！</p>
		<p style="text-align: center; font-size: 1em; color: #666; padding: 1em 1.5em">
			系统出现内部错误，请<a href="javascript:window.history.back(-1)">返回</a>重试！
		</p>
	</div>
</body>
</html>
