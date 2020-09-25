<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_会员权益</title>
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
            <span class="title">会员权益</span>
            <a href="${basePath}article/list.html"><span class="menu"><i class="iconfont icon-zhuanqian"></i>&nbsp;开始赚钱</span></a>
        </div>
    </header>
    <div class="yzk_content">
      <div class="yzk_quanyi">
        <div class="yzk_user">
          <div class="user_info">
            <p class="user_flag">
                          您是：<span class="level_${member.memberLevel}">
              <i class="iconfont icon-huiyuan"></i><em>${member.levelName}</em>
             </span>
             &nbsp; 推广账号:${member.loginAccount}
            </p>
          </div>
        </div>
        
        <div class="yzk_flag">
           <dl class="flag_head">
             <dd class="title"></dd>
             <dd class="row1">赚钱特权</dd>
             <dd class="row2">奖励特权</dd>
             <dd class="row3">升级条件</dd>
           </dl>
           
           <c:if test="${!empty memberLevel}">
             <c:forEach items="${memberLevel}" var="lv">
               <dl class="flag_row">
	             <dd class="title">${lv.name}</dd>
	             <dd class="row1">阅读单价*<em>${lv.percent}</em>倍</dd>
	             <dd class="row2">徒弟分享收入的20%，徒孙分享收入的20%</dd>
	             <c:if test="${lv.type==0}">
	             <dd class="row3">注册</dd>
	             </c:if>
	             <c:if test="${lv.type!=0}">
	             <dd class="row3">前一天收益&gt;${lv.moneyCount}元</dd>
	             </c:if>
              </dl>
             </c:forEach> 
           </c:if>
        </div>
        <div class="flag_notice">提示：所有通过非正常点击获取收益的行为，都是违规行为！升级不易，且行且珍惜
        </div>
        <div class="flag_way">
            <h2><span>升级方法</span></h2>
            <h3>黄金会员</h3>
            <p>每天23点55分达到升级条件的用户自动升级，达不到的用户将进行降级处理</p>
            <h3>白金会员</h3>
            <p>每天23点55分达到升级条件的用户自动升级，达不到的用户将进行降级处理</p>
            <h3>钻石会员</h3>
            <p>每天23点55分达到升级条件的用户自动升级，达不到的用户将进行降级处理</p>         
        </div>
      </div>
    </div>
     <%@ include file="../../../common/footer.jsp"%>
<div class="layui-layer-move"></div></body></html>