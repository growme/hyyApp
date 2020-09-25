<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<%@ taglib prefix="fns" uri="/WEB-INF/fns.tld" %>
<%
response.setHeader("Cache-Control", "no-cache"); 
response.setHeader("Cache-Control", "no-store"); 
response.setDateHeader("Expires", 0); 
response.setHeader("Pragma", "no-cache"); 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String serverPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
request.setAttribute("path",path);
request.setAttribute("basePath",basePath);
request.setAttribute("serverPath",basePath);
%>
<c:set var="ccnetpath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
var ccnetpath = "${ccnetpath}";
var menuCode = "${CT_MENU_INDEX}";
var isReload = "${CT_MENU_RELOAD}";
var demo_mode = "${demo_mode}";
var version = "${SYSPARAM.ADMIN_VERSION}";
var loginUserType="${SESSION_USER.userType}";
</script>