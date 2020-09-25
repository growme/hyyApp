<%@ page language="java" pageEncoding="UTF-8"%>
<div class="page-header position-relative">
    <div class="header-title">
        <h1>
        <i class="fa fa-bell-o"></i>&nbsp;您好,<span class="red"> ${SESSION_USER.roleName}</span> 
		           欢迎回来! 
		           登录时间：<span class="red">${fn:substring(SESSION_USER.extendInfo.lastLoginTime, 0, 19)}</span>
		           IP：<span class="red">${SESSION_USER.extendInfo.lastLoginIp}</span>
        </h1>
    </div>
    <div class="header-buttons">
        <a class="sidebar-toggler" href="#">
            <i class="fa fa-arrows-h"></i>
        </a>
        <a class="refresh" id="refresh-toggler" href="">
            <i class="glyphicon glyphicon-refresh"></i>
        </a>
        <a class="fullscreen" id="fullscreen-toggler" href="#">
            <i class="glyphicon glyphicon-fullscreen"></i>
        </a>
    </div>
</div>