<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>后台管理系统首页</title>
<meta name="description" content="后台管理首页" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<link href="${basePath}static/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/sccl.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/nskins/qingxin.skin.css" id="layout-skin" rel="stylesheet" type="text/css"/>

<style type="text/css">
#menubox {
	position: fixed;
    top: 0;
    display: none;
    background-color: #fff;
    border: solid 1px #ccc;
    border-top: none;
    z-index: 1000;
	display: none;
}

#menubox a {
	display: block;
    clear: both;
    border-bottom: solid 1px #f2f2f2;
    margin: 0;
    padding: 5px 20px;
    text-align: left;
    line-height: 1.5;
    font-weight: normal;
    white-space: nowrap;
    color: #717171;
}

#menubox a:hover {
	background-color: #fafafa;
	color: #717171;
	text-decoration: none;
}
</style>
</head>
<!-- /Head -->
<!-- Body -->
<body class="bodybg">
	<div class="layout-admin">
		<header class="layout-header">
			<span class="header-logo">
			<small><img src="${siteLogo}" alt="" /></small>
			</span>
			 <a class="header-menu-btn" href="javascript:;">
			 <i class="icon-font">&#xe600;</i>
			</a>
			<ul class="header-bar">
				<li class="header-bar-role" style="margin-right:5px">
			       <span id="nowtime"></span>
				</li>
				<li class="header-bar-role">
			      <div class="userLogo" title="头像">
			      <c:if test="${empty SESSION_USER.picUrl && SESSION_USER.sex==0}">
			         <img id="userIconSmall" src="${basePath}/static/img/avatars/man.jpg"> 
			      </c:if>
			      <c:if test="${empty SESSION_USER.picUrl && SESSION_USER.sex==1}">
			         <img id="userIconSmall" src="${basePath}/static/img/avatars/women.jpg"> 
			      </c:if>
			      <c:if test="${!empty SESSION_USER.picUrl}">
                     <img id="userIconSmall" src="${basePath}/static/img/avatars/${SESSION_USER.picUrl}.jpg">
			      </c:if>
                  </div>
				</li>
				<li class="header-bar-nav" id="top1">
				   <a href="javascript:;">欢迎回来 (${SESSION_USER.loginAccount})
				   <i class="icon-font" style="margin-left:5px;">&#xe60c;</i>
				   </a>
					<ul class="header-dropdown-menu" id="top1_sub">
						<li><a href="##" id="userSetting"><i class="fa fa-user"></i> 个人设置</a></li>
						<li><a href="##" id="changePwd"><i class="fa fa-key"></i> 修改密码</a></li>
						<li><a href="##" id="lockScreen"><i class="fa fa fa-coffee"></i> 锁屏离开</a></li>
						<li><a href="##" id="exitLogin"><i class="fa fa-sign-out"></i> 注销退出</a></li>
					</ul>
				</li>
				<li class="header-bar-nav" id="top2">
				    <a href="javascript:;" title="换肤"><i class="icon-font">&#xe608;</i></a>
					<ul class="header-dropdown-menu right dropdown-skin" id="top2_sub">
						<li><a href="javascript:;" data-val="qingxin" title="清新">清新</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="molv" title="墨绿">墨绿</a></li>
						<li><a href="javascript:;" data-val="purpl" title="紫色">紫色</a></li>
						<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
						<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
						<li><a href="javascript:;" data-val="black" title="黑色">黑色</a></li>
					</ul>
				</li>
			</ul>
		</header>
		<aside class="layout-side">
			<ul class="side-menu"></ul>
		</aside>

		<div class="layout-side-arrow">
			<div class="layout-side-arrow-icon">
				<i class="icon-font">&#xe60d;</i>
			</div>
		</div>

		<section class="layout-main">
			<div class="layout-main-tab">
				<button class="tab-btn btn-left">
					<i class="icon-font">&#xe60e;</i>
				</button>
				<nav class="tab-nav">
					<div class="tab-nav-content" title="双击可以关闭当前窗口标签">
						<a href="javascript:;" id="homeTab" data-index="0" class="content-tab active" data-id="${basePath}backstage/home/data?mod=0102" >系统首页</a>
					</div>
				</nav>
				<button class="tab-btn btn-right" style="right:80px;">
					<i class="icon-font">&#xe60f;</i>
				</button>
				<button class="tab-btn btn-right tabClose" id="mdropdown" style="right:40px;">
					<i class="icon-font">&#xe60c;</i>
				</button>
				
				<div id="menubox">
                     <a class="tabReload" href="##"><i class="fa fa-refresh"></i> 刷新当前</a>
                     <a class="tabCloseCurrent" href="##"><i class="fa fa-times-circle"></i> 关闭当前</a>
                     <a class="tabCloseAll" href="##"><i class="fa fa-trash-o"></i> 关闭全部</a>
                     <a class="tabCloseOther" href="##"><i class="fa fa-times-circle-o"></i> 关闭其他</a>
                 </div>
                 
				<button class="tab-btn btn-right fullscreen" title="全屏显示窗口">
					<i class="fa fa-arrows-alt" style="font-size:16px"></i>
				</button>
			</div>
			<div class="layout-main-body">
				<iframe class="body-iframe" name="iframe0" width="100%" height="99%" src="${basePath}backstage/home/data?mod=0102" frameborder="0" data-id="${basePath}backstage/home/data?mod=0102" seamless></iframe>
			</div>
		</section>
		<div class="layout-footer">Copyright©2019  薄荷咨询 版权所有 京ICP备14060614号-1</div>
	</div>
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${basePath}static/js/layer/sccl.js"></script>
	<script src="${basePath}static/js/layer/sccl-util.js"></script>
	</body>
</html>
