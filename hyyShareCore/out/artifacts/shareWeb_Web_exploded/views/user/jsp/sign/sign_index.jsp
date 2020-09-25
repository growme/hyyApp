<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
 <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<title>天天头条</title>
	<%@ include file="../../../common/header.jsp"%>
	<%@ include file="../../../common/js_param.jsp"%>
	<%@ include file="../../../common/ucom_css.jsp"%>
</head>
  
  <body>
    <header class="header">
        <div class="header-menu">
            <a href="${basePath}home/index.html"><i class="iconfont icon-left"></i></a>
            <span class="title">签到中心</span>
            <a href="${basePath}article/list.html"><span class="menu"><i class="iconfont icon-vip"></i>&nbsp;开始赚钱</span></a>
        </div>
    </header>
    
    <div class="yzk_content">
        <div class="yzk_shoutu">
            <img src="${basePath}static/images/qdbanner2.jpg"/>
            <div class="yzk_shoutu_li">
                <ul>
                    <li style="border-right:#ededed 1px solid;">
                        <p class="num"><b>(${signInfo.seriesCount==null?0:signInfo.seriesCount})</b> 天</p> 
                        <p class="name">连续签到</p>
                    </li>
                    <li style="border-right:#ededed 1px solid;">
                       <p class="num"><b>(${signInfo.totalCount==null?0:signInfo.totalCount})</b> 天</p>
                        <p class="name">累计签到</p>
                    </li>                    
                    <li>
                        <p class="num"><b>(<fmt:formatNumber value="${signInfo.totalMoney==null?0:signInfo.totalMoney}" type="" pattern="0.00"/>)</b> 元</p>
                        <p class="name">累签奖励</p>
                    </li>
                </ul>
            </div>
            
            <div class="click_sign">
                <button type="button" id="signBtn">签到领取奖励</button>
                <input type="hidden" id="jrqd" name="jrqd" value="${jrqd}">
                <p class="qianda">今日签到可以获得 <font color="red">${signMoney}</font> 元</p>
            </div>
            
			<div class="content_foot">
				<div class="foot_title">
					<span>签到活动规则</span>
				</div>
				<div class="font_content">
					<p>1.需要分享文章后并且有收益，才能到本页面进行签到。</p>
			        <p>2.平台用户每天都能签到，初始奖励${qdjj}元，每天签到再累加${djje}</p>
			        <p>3.如果中途签到中断会重新开始计算。（例如昨天没有签到：今天您的签到奖励又变成${qdjj}元）</p>
			        <p>4.每月1号重置签到</p>
				</div>
			</div>
            
        </div>
    </div>
	<%@ include file="../../../common/com_js.jsp"%>
	<script type="text/javascript" src="<%=path %>/static/script/user/sign/sign.js"></script>
  </body>
  
</html>
