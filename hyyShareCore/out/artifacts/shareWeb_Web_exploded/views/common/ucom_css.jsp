<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${empty siteIco}">
<link rel="shortcut icon" href="${basePath}static/images/favicon.ico" type="image/x-icon"/>
</c:if>
<c:if test="${!empty siteIco}">
<link rel="shortcut icon" href="${siteIco}" type="image/x-icon"/>
</c:if>
<link type="text/css" rel="stylesheet" href="${basePath}static/css/${SYSPARAM.USER_DEFAULT_THEME}/public.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/${SYSPARAM.USER_DEFAULT_THEME}/index.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/font_aliyun.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/need/layer.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/common.css">

