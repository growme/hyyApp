<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/includes.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>500页</title>
    <meta name="description" content="404page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../common/com_css.jsp"%>
  </head>
 <body class="body-500">
  	<div class="error-header"></div>
    <div class="container ">
        <section class="error-container text-center">
            <h1>500</h1>
            <div class="error-divider">
                <h2>OOOPS! Server Error!</h2>
                <p class="description"> 服务器内部错误!222 </p>
            </div>
            <%=request.getAttribute("javax.servlet.error.message")%>
            <a href="${basePath}backstage/index" class="return-btn"><i class="fa fa-home"></i>返回首页</a>
        </section>
    </div>
    <%@ include file="../common/com_js.jsp"%>
  </body>
</html>
