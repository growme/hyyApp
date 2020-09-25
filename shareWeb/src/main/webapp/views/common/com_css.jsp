<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${empty siteIco}">
<link rel="shortcut icon" href="${basePath}static/images/favicon.ico" type="image/x-icon"/>
</c:if>
<c:if test="${!empty siteIco}">
<link rel="shortcut icon" href="${siteIco}" type="image/x-icon"/>
</c:if>
<link type="text/css" href="${basePath}static/css/common.css" rel="stylesheet">

