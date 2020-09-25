<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String basePath = null;
if(request.getServerPort()==80){
	basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
}else{
	basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
}
request.setAttribute("path",path);
request.setAttribute("basePath",basePath);
request.setAttribute("serverPath",serverPath);
response.setHeader("pragam","no-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("expires",-1);
response.setHeader("Set-Cookie","uid=tmd;Path=/;Max-Age=30;HTTPOnly");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
