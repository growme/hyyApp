<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>
<% 
  String nowDomian = request.getServerName();
  String redirecUrl = (String)request.getAttribute("forwardUrl");
  System.out.println("当前访问的域名:"+nowDomian);
   if(redirecUrl!=null && !"".equals(redirecUrl)){
     System.out.println("开始301跳转到==>>>>:"+redirecUrl);
	 response.setStatus(303);
	 response.setDateHeader("Expires", 0);
	 response.setHeader("Location",redirecUrl);
	 response.setHeader("Connection","close");
	 System.out.println("303重定向到["+redirecUrl+"]成功...");
   }
%>

