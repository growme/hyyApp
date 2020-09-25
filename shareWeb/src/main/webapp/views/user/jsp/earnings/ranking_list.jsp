<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>${SYSPARAM.SITE_NAME}_收益排行榜</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
<style type="text/css">
td {
	border-bottom: 1px dashed #ddd;
	height: 40px;
	line-height: 40px;
	font-size: 14px;
	color: #333;
}

</style>
</head>
<body class="yzk_bg">
	<header class="header">
		<div class="header-menu">
			<a href="${basePath}home/index.html">
			<i class="iconfont icon-left"></i>
			</a> <span class="title">排行榜</span>
			 <a href="${basePath}article/list.html">
				<span class="menu"><i class="iconfont icon-zhuanqian"></i>&nbsp;开始赚钱</span>
			 </a>
		</div>
	</header>
	<div class="yzk_content">
		<div class="yzk_rank_now">
			<ul>
				 <li>
					<h1>
						<em><fmt:formatNumber value="${totalTotayMoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em>
					</h1>
					<p>今日收益(元)</p>
				 </li>
				 <li>
					<h1>
						<em><fmt:formatNumber value="${totalMoney}" type="currency" pattern="0.00"> </fmt:formatNumber></em>
					</h1>
					<p>累计收益(元)</p>
				 </li>
			</ul>
		</div>
		
		<div class="yzk_rank_tab">
		<ul>
		  <a href="${basePath}ranking/dlist.html"><li class="${tp=='today'?'on':''}" data-id="shouru">今日收益排名</li></a>
	      <a href="${basePath}ranking/tlist.html"><li class="${tp=='total'?'on':''}"  data-id="shoutu">累计收入排名</li></a>
		</ul>
		</div>
		<div class="shouru" style="display: block;">
		   <table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tbody>
				<tr>
					<td align="center">
						排&nbsp;名
					</td>
					<td align="center">
						用&nbsp;户
					</td>
					<td align="center">
						收&nbsp;益
					</td>
				</tr>
				
                <c:if test="${!empty rList}">
                 <c:forEach items="${rList}" var="rk" varStatus="tt">
				<tr>
					<td align="center">
					    <c:if test="${tt.index<3}">
						<span class="sl${tt.index+1}">${tt.index+1}</span>
						</c:if>
						<c:if test="${tt.index>=3}">
						<div class="sl">${tt.index+1}</div>
						</c:if>
					</td>
					<td align="center">
						${rk.rankName}(${rk.frankMobile})
					</td>
					<td align="center">
					    <c:if test="${tp=='total'}">
						<fmt:formatNumber value="${rk.totalMoney}" type="currency" pattern="0.00"> </fmt:formatNumber>元
						</c:if>
						<c:if test="${tp=='today'}">
						<fmt:formatNumber value="${rk.todayMoney}" type="currency" pattern="0.00"> </fmt:formatNumber>元
						</c:if>
					</td>
				</tr>
				</c:forEach>
				</c:if>
				
			</tbody>
		</table>
		</div>
		
	</div>
	<%@ include file="../../../common/footer.jsp"%>
	<script type="text/javascript" src="${basePath}static/script/jquery.min.js"></script>
	<script type="text/javascript">
    $(function(){
       $('.yzk_rank_tab li').click(function(){
           $('.yzk_rank_tab li').removeClass('on');
           $('.shouru').hide();
           $('.shoutu').hide();
           $(this).addClass('on');
           var dataId=$(this).attr('data-id');
           $('.'+dataId).show();
       });
    });
   </script>
</body>
</html>