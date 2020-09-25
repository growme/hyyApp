<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<!-- Page Sidebar -->
<div class="page-sidebar" id="sidebar">
    <!-- Page Sidebar Header-->
    <div class="sidebar-header-wrapper">
        <input type="text" class="searchinput"  value="您好！${SESSION_USER.userName}" disabled="disabled"/>
        <i class="searchicon glyphicon glyphicon-user"></i>
        <div class="searchhelper">系统搜索</div>
    </div>
    <!-- /Page Sidebar Header -->
    <!-- Sidebar Menu -->
    <ul class="nav sidebar-menu" id="homeMenu"></ul>
    <!-- /Sidebar Menu -->
</div>
<!-- /Page Sidebar -->