<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_会员中心</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="format-detection" content="telephone=no">
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
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
	font-size: .8rem;
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
	text-align: left;
	margin-left:0.5rem;
	line-height: 1.8rem;
}

.notive-native ul li >a {
	color:#a27d1b;
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
<div class="yzk_content no-margin-top">
  <div class="yzk_income">
    <p class="ui-id"><i class="iconfont icon-qr-code"></i> 
      ID:<span class="i-user">${memberInfo.loginAccount}</span><span class="user-flag"></span>
    </p>
    <p class="ui-money">
       <i class="iconfont icon-renminbi"></i> 总资产:<em class="i-tomoney"><fmt:formatNumber value="${totalMoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em>元
    </p>
    <h1>
     <a href="${basePath}earnings/index.html"> <em class="income_money"><fmt:formatNumber value="${curBalance}" type="currency" pattern="0.00"> </fmt:formatNumber></em> </a> 
    </h1>
    <p>当前余额(元)</p>
  </div>
  
  <div class="yzk_notice" style="position:relative;z-index:1;padding:0;">
	<div class="notive-native" id="noticerecord">
	</div>
  </div>
  
  <div class="yzk_account">
    <ul>
      <li> <a href="${basePath}earnings/index.html">
        <p class="u-num"> <em class="i-nowmoney"><fmt:formatNumber value="${totalTotayMoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em> </p>
        <p class="u-txt">今日收益(元)</p>
        </a>
      </li>
      <li> <a href="${basePath}draw/list.html">
        <p class="u-num"> <em class="i-tocash"><fmt:formatNumber value="${totalCashMoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em> </p>
        <p class="u-txt">累计提现(元)</p>
        </a> 
      </li>
      <li> <a href="${basePath}invited/tearn.html">
        <p class="u-num"> <em class="i-tointo"><fmt:formatNumber value="${totalChildMoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em> </p>
        <p class="u-txt">徒弟收益(元)</p>
        </a> 
      </li>
      <li> <a href="${basePath}invited/dlist.html">
        <p class="u-num"> <em class="i-tounder">${totalUserNum}</em> </p>
        <p class="u-txt">徒弟徒孙(个)</p>
        </a> 
     </li>
    </ul>
  </div>
  
  <div class="yzk_icon">
    <ul>
      <li> <a class="m1" href="${basePath}article/list.html" title="开始赚钱">
        <p class="t-icon"><i class="iconfont icon-zhuanqian"></i></p>
        <p class="t-txt">开始赚钱</p>
        </a> </li>
      <li> <a class="m2" href="${basePath}draw/cash.html" title="我要提现">
        <p class="t-icon"><i class="iconfont icon-tixian"></i></p>
        <p class="t-txt">我要提现</p>
        </a> </li>
      <li> <a class="m3" href="${basePath}user/setting.html" title="账号设置">
        <p class="t-icon"><i class="iconfont icon-huiyuan"></i></p>
        <p class="t-txt">账号设置</p>
        </a> </li>
      <li> <a class="m4" href="${basePath}sign/index.html" title="签到赚钱">
        <p class="t-icon"><i class="iconfont icon-hongbao"></i></p>
        <p class="t-txt">签到赚钱</p>
        </a> 
      </li>
    </ul>
  </div>
  
  <div class="yzk_icon1">
    <ul>
      <li> 
        <a class="m1" href="${basePath}ranking/dlist.html" title="排行榜">
        <p class="t-icon"><i class="iconfont icon-paixingbang"></i></p>
        <p class="t-txt">收益排行</p>
        </a> 
       </li>
      <li> 
        <a class="m4" href="${basePath}equity/index.html" title="会员权益">
        <p class="t-icon"><i class="iconfont icon-huiyuan"></i></p>
        <p class="t-txt">会员权益</p>
        </a> 
      </li>
      <li> 
        <a class="m3" href="${basePath}about/index.html" title="联系我们">
        <p class="t-icon"><i class="iconfont icon-lianxigray"></i></p>
        <p class="t-txt">联系我们</p>
        </a> 
      </li>
      <li>
        <a class="m2" title="退出登录" id="loginOut">
        <p class="t-icon"><i class="iconfont icon-appxiazai"></i></p>
        <p class="t-txt">退出登录</p>
        </a> 
       </li>
    </ul>
  </div>
  
  <div class="yzk_cashlist">
    <div class="title color"><i class="iconfont icon-tixian"></i> 近期提现记录</div>
  </div>
  
</div>
<%@ include file="../../../common/footer.jsp"%>
<%@ include file="../../../common/com_js.jsp"%>
<script type="text/javascript" src="<%=path %>/static/script/jquery/jquery.extend.1.0.js"></script>
<script type="text/javascript" src="<%=path %>/static/script/user/index/index.js"></script>
</body>
</html>