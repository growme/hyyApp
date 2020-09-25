<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/includes.jsp"%>

<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>500 Error！</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<style type="text/css">
	body{
		font-size: 12px;
		font-family: '微软雅黑';
	}
	p{
		padding: 0;
		margin: 0;
	}
	.fiveBox{
		width: 700px;
		height: 250px;
		margin: 150px auto 0;
		background:#f5f5f5;
		border:1px solid #ccc;
	}
	.errorTitle{
		width: 690px;
		height: 29px;
		background: url(/img/ebill/bg.jpg);
		color: #000;
		padding:5px 0 0 10px;
		margin: 0;
	}
	.fiveCon{
		padding-left: 50px;
	}
	
	.fiveCon h4{
		height: 35px;
		overflow: hidden;
		line-height: 35px;
		font-size: 16px;
		margin-left:15px;
		margin-bottom: 5px;
	}
	.fiveCon img{
	    width:32px;
		margin-bottom: -13px;
	}
	.descText{
		margin-left: 50px;
	}
	.errorList{
		margin-left: 10px;
	}
	.errorList li{
		list-style: none;
		line-height: 24px;
	}
	.link{
		color: #428bca;
	}
</style>
	<div class="fiveBox">
		<h3 class="errorTitle">错误提示</h3>
		<div class="fiveCon">
			<h4>
				<img src="${basePath}static/images/info-icon-1.png">
				<span class="nofindTitle">您浏览的网页暂时无法显示!</span>
			</h4>
			 <%=request.getAttribute("javax.servlet.error.message")%>
			<p class="descText">页面已经被删除或者访问地址有误，试试下面几种方法吧：</p>
			<ul class="errorList">
				<li>【1】检查网址是否正确。</li>
				<li>【2】访问系统<a href="${basePath}user/index" class="link">首页</a>。</li>
				<li>【3】去其他地方逛逛。</li>
			</ul>
		</div>
	</div>
</body></html>
