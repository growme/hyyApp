<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_当日收益</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
</head>
<body class="yzk_bg">
      <header class="header">
	        <div class="header-menu">
	            <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
	            <span class="title">${title}</span>
	            <a href="${basePath}${url}"><span class="menu"><i class="iconfont icon-haoyoutuijian--"></i>&nbsp;${urlTitle}</span></a>
	        </div>
      </header>
      <div class="yzk_content">
          <div class="yzk_shouyi">
               <ul>
               <c:if test="${!empty earnLists}">
               <c:forEach var="earn" items="${earnLists}">
                 <li>
                  <p class="remark">${earn.moneyTypeName}</p>
				  <p class="date"><fmt:formatDate value="${earn.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></p> 
				  <span class="mons">+${earn.money}</span>
				</li>     
               </c:forEach>  
               </c:if>        
               </ul>
          </div>
          <c:if test="${empty earnLists}">
          <div class="yzk_msg">还没有记录！</div>
          </c:if>
          <div style="text-align: center">
          	   <nav>
          	      <ul class="pagination"></ul>
          	   </nav>
          </div>    
      </div>
		<%@ include file="../../../common/footer.jsp"%>
   </body>
</html>
