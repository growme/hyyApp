<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<link rel="shortcut icon" href="${siteIco}" type="image/x-icon">
<link href="${basePath}static/css/reset.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/beyond.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/family_font.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/demo.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/typicons.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/animate.min.css" rel="stylesheet" type="text/css"/>
<c:if test="${!empty SESSION_USER.skin}">
<link href="${basePath}static/css/skins/${SESSION_USER.skin}.min.css" rel="stylesheet" type="text/css" id="style_color"/>
</c:if>
<c:if test="${empty SESSION_USER.skin}">
<link href="${basePath}static/css/skins/teal.min.css" rel="stylesheet" type="text/css" id="style_color"/>
</c:if>
<link rel="stylesheet" href="${basePath}static/js/ztree/css/metroStyle/metroStyle.css" type="text/css">