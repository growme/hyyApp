<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_联系我们</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
</head>
 <body class="yzk_bg">
	<header class="header">
		<div class="header-menu">
			<a href="${basePath}home/index.html"><i
				class="iconfont icon-left"></i>
			</a> <span class="title">联系客服</span> <a
				href="${basePath}article/list.html"><span
				class="menu"><i class="iconfont icon-zhuanqian"></i>&nbsp;开始赚钱</span>
			</a>
		</div>
	</header>
	<div class="yzk_content">
	    <c:if test="${!empty SYSPARAM.SUPPLY_QQ}">
	    
	    </c:if>
	    <div class="content_foot" style="padding: 10px 15px;margin-top:2px;">
            <div class="foot_title"><span>在线客服</span></div>
         </div>
         <div class="infomation">
            <c:if test="${!empty SYSPARAM.SUPPLY_QQ}">
            <p style="width: 100%;">客服QQ-1：${SYSPARAM.SUPPLY_QQ}
             <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.SUPPLY_QQ}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.SUPPLY_QQ}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	         </a>
            </p>
	        </c:if>
	        <c:if test="${!empty SYSPARAM.SUPPLY_QQ_2}">
            <p style="width: 100%;">客服QQ-2：${SYSPARAM.SUPPLY_QQ_2}
             <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.SUPPLY_QQ_2}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.SUPPLY_QQ_2}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	         </a>
            </p>
	        </c:if>
	        <c:if test="${!empty SYSPARAM.SUPPLY_QQ_3}">
            <p style="width: 100%;">客服QQ-1：${SYSPARAM.SUPPLY_QQ_3}
            <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.SUPPLY_QQ_3}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.SUPPLY_QQ_3}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	         </a>
             </p>
	        </c:if>
        </div>
        
        <div class="content_foot" style="padding: 10px 15px;margin-top:2px;">
            <div class="foot_title"><span>QQ交流群</span></div>
         </div>
         <div class="infomation">
            <c:if test="${!empty SYSPARAM.SUPPLY_MQQ}">
            <p style="width: 100%;">官方QQ群-1：${SYSPARAM.SUPPLY_MQQ}
            <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.SUPPLY_MQQ}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.SUPPLY_MQQ}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	         </a>
            </p>
	        </c:if>
	        <c:if test="${!empty SYSPARAM.SUPPLY_MQQ_2}">
            <p style="width: 100%;">官方QQ群-2：${SYSPARAM.SUPPLY_MQQ_2}
            <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.SUPPLY_MQQ_2}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.SUPPLY_MQQ_2}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	         </a>
            </p>
	        </c:if>
	        <c:if test="${!empty SYSPARAM.SUPPLY_MQQ_3}">
            <p style="width: 100%;">官方QQ群-1：${SYSPARAM.SUPPLY_MQQ_3}
             <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.SUPPLY_MQQ_3}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.SUPPLY_MQQ_3}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	         </a>
            </p>
	        </c:if>
        </div>
        
        <div class="content_foot" style="padding: 10px 15px;margin-top:2px;">
            <div class="foot_title"><span>商务合作</span></div>
         </div>
         <div class="infomation">
            <c:if test="${!empty SYSPARAM.BUSINESS_QQ}">
            <p style="width: 100%;">商务合作：${SYSPARAM.BUSINESS_QQ}
            <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${SYSPARAM.BUSINESS_QQ}&site=qq&menu=yes">
	          <img border="0" src="http://wpa.qq.com/pa?p=2:${SYSPARAM.BUSINESS_QQ}:51" alt="点击这里给我发消息" title="点击这里给我发消息"/>
	        </a>	
	        </p>
	        </c:if>
        </div>
	</div>
</body>
</html>