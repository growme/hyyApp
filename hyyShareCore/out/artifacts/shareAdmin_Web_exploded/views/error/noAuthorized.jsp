<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/includes.jsp"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8" />
    <title>未授权访问</title>
    <meta name="description" content="404page" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../common/com_css.jsp"%>
  </head>
 <body class="bodybg">
    <%@ include file="../common/com_js.jsp"%>
    <script type="text/javascript">
         showErrorMsg("您没有权限访问该资源，请联系管理员！！",2000);
         var index = parent.layer.getFrameIndex(window.name);
         if(index!=undefined){
           closeSFrame(2100);
         }else{
           setTimeout(function(){
               window.history.back(-1);
	       },1000);
         }
     </script>
  </body>
</html>
