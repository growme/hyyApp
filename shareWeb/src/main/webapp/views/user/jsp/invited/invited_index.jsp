<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_邀请徒弟</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
<style type="text/css">
.notive-native {
	width: 100%;
	line-height: 1.8rem;
	text-align: center;
	background: #fff;
	color: #a27d1b;
	font-size: .6rem;
	position: relative;
	overflow: hidden;
	height: 1.8rem;
}

.notive-native ul {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
}

.notive-native ul li {
	text-align: center;
	margin-left:0.5rem;
	line-height: 1.8rem;
	font-size:14px;
}

.yzk_notice i {
    position: absolute;
    left: 15px;
    top: 2px;
    color: #f3015c;
}

</style>
</head>
  
  <body class="yzk_bg">
    <header class="header">
        <div class="header-menu">
            <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
            <span class="title">收徒赚钱</span>
            <a href="${basePath}invited/dlist.html"><span class="menu"><i class="iconfont icon-vip"></i>&nbsp;我的徒弟</span></a>
        </div>
    </header>
    
    <div class="yzk_notice" style="position:relative;z-index:1;padding:0;">
		<div class="notive-native" id="cashrecord">
		</div>
	  </div>
    
    <div class="yzk_content">
        <div class="yzk_tech" style="display:none;">
            <span>我的师傅：<i class="bossinfo"></i></span>
            <span class="go"><a onclick="">马上联系<i class="iconfont icon-right"></i></a></span>
        </div>     
        <div class="yzk_shoutu">
            <img src="${basePath}static/images/${SYSPARAM.USER_DEFAULT_THEME}/shoutu.jpg"/>
            <div class="yzk_shoutu_li">
                <ul>
                    <li style="border-right:#ededed 1px solid;">
                        <p class="num"><b>${jrst}</b>人</p>
                        <p class="name">今日收徒</p>
                    </li>
                    <li style="border-right:#ededed 1px solid;">
                        <p class="num"><b><fmt:formatNumber value="${jrjl}" type="" pattern="0.00"/></b>元</p>
                        <p class="name">今日奖励</p>
                    </li>                    
                    <li style="border-right:#ededed 1px solid;">
                        <p class="num"><b>${ljst}</b>人</p>
                        <p class="name">累计收徒</p>
                    </li>
                    <li>
                        <p class="num"><b><fmt:formatNumber value="${ljjl}" type="" pattern="0.00"/></b>元</p>
                        <p class="name">累计奖励</p>
                    </li>
                </ul>
            </div>
            <div class="myhuabao">
                <a href="${basePath}invited/barcode.html">查看专属收徒二维码</a>
            </div>
            <p class="line-title">
                <span>收徒链接</span>
            </p>
            <div class="url">
                           【${SYSPARAM.SITE_NAME}】${SYSPARAM.CT_USER_VISIT_CONTENT_COPY}!<span class="surl">${visitUrl}</span>
            </div>
            
            <div class="myhuabao" id="fzwa" data-clipboard-text="【${SYSPARAM.SITE_NAME}】${SYSPARAM.CT_USER_VISIT_CONTENT_COPY} ${visitUrl}">一键复制推广文案</div>
            
            <p class="line-title">
                <span>收徒技巧</span>
            </p>
            <div class="shoutus">
            <c:if test="${!empty noticeList}">
			<c:forEach var="nt" varStatus="status" end="2"  items="${noticeList}">
			  <a href="${basePath}notice/detail.html?nid=${nt.noticeId}" title="${nt.noticeTitle}">
               	<i class="nums">${status.index+1}</i>${nt.noticeTitle}
              </a> 
			</c:forEach>
			</c:if>      
            </div>
            <div class="notices">
                <p class="label">1、如何邀请徒弟？</p>
                <p>把你的“收徒链接”或收徒二维码发给你的朋友，你朋友注册完成后，即成为你的徒弟。</p>
                <p class="label">2、怎么招收更多徒弟？</p>
                <p> a.作为您的QQ、微信签名 /发到QQ、微信好友或者群 /放到您的网站网页。</p>
                <p> b.在网赚/兼职/学生等QQ群及社区论坛贴吧中发送您的收徒链接。二三线的初高中、技校QQ群及百度贴吧收徒效果非常好。</p>
                <p> c.还可以选择在各大网站的兼职频道发帖，如豆瓣的兼职小组、百度兼职吧、19楼兼职等，这样你的下线发展会快些。</p>
                <p class="label">3、收徒奖励什么时候给？</p>
                <p>收徒奖励在徒弟注册完成后给予。</p>                    
            </div>
        </div>
    </div>
	<%@ include file="../../../common/footer.jsp"%>
	<script type="text/javascript" src="<%=path %>/static/script/jquery/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="<%=path %>/static/script/jquery/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="<%=path %>/static/script/clipboard.min.js"></script>
	<script type="text/javascript" src="<%=path %>/static/script/jquery/jquery.extend.1.0.js"></script>
	<script type="text/javascript" src="<%=path %>/static/script/user/invited/invited.js"></script>
  </body>
  
</html>
