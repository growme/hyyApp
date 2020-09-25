<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/includes.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>${CT_MENU_NAV}</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/com_css.jsp"%>
	<style type="text/css">
	.info-box {
		font-size: 14px;
		margin-bottom: 0px;
		border: 0px solid #e7e7eb;
	}
	
	.info-box .info-hd {
		line-height: 35px;
		height: 35px;
		font-size: 14px;
		padding: 0 14px;
		background: #f5f5f5;
		border-bottom: 0px solid #e7e7eb;
	}
	
	.info-box .info-hd .pos-top {
		top: -13px !important;
	}
	
	.info-box .info-bd {
		padding: 10px 20px;
		font-size: 12px;
		zoom: 1;
	}
	
	.info-box.mini-padding .info-bd {
		padding: 20px 30px;
	}
	
	.info-box .info-bd .empty {
		padding: 35px 0;
		text-align: center;
		color: #8d8d8d;
	}
	
	.info-box .info-bd .data-box.with-padding {
		margin: 0 -13px;
	}
	
	.info-title {
		font-weight: bold;
	}
	
	.wl-streamUL {
		background: #FBFBFC;
		font-size:12px;
		border: 1px solid #EAEAEA;
	}
	
	ul,ol {
		list-style: none;
	}
	
	li {
		display: list-item;
		margin-bottom:2px;
		text-align: -webkit-match-parent;
	}
	
	.wl-stream-text {
		margin-left: 100px;
	}
	
	.wl-stream-time {
		position: absolute;
		margin-left: -5px;
	}
	
	.wl-stream-last {
		color: #009900;
	}
</style>
</head>
	<!-- /Head -->
	<!-- Body -->
	<body>
	   <!-- Loading Container -->
	    <%@ include file="../../../common/loading.jsp"%>
	    <!--  /Loading Container -->
	    <c:if test="${ADMIN_VERSION!='V5'}">
		<!-- Navbar -->
		<%@ include file="../../../common/navbar.jsp"%>
		<!-- /Navbar -->
		<!-- Main Container -->
		<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
		<!-- menu Container -->
	    <%@ include file="../../../common/menu.jsp"%>
	    <!--  /menu Container -->
		<!-- Page Content -->
		<div class="page-content">
		<!-- Page Breadcrumb -->
		<%@ include file="../../../common/navtip.jsp"%>
		<!-- /Page Breadcrumb -->
		</c:if>		
		
       <div id="gahterContent" class="info-box" style="width:700px; margin:0 auto;padding-top:50px;border-radius:5px">
        <form id="contentForm" name="contentForm" action="" method="post">
         <table class="table table-striped table-bordered">
          <tbody>
          <tr>
				<td align="center" colspan="2"><h4>微信公众号文章采集</h4></td>
		   </tr>
           <tr>
				<td align="right" style="width:140px;">选择栏目:</td>
				<td>
				<select name="columnId" id="columnId" placeholder="选择栏目" style="width:100%;">
			        <option value="">请选择...</option>
				    <c:forEach items="${columnList}" var="dp">
				    <option  value="${dp.columnId}" <c:if test="${dp.columnId==contentInfo.columnId}">selected="selected"</c:if>>${dp.columnName}</option>
				    </c:forEach>
				</select>
				</td>
		   </tr>
		    <tr>
				<td align="right"><label>阅读奖励(元):</label></td>
				<td ><input id="readAward" class="form-control" placeholder="请输入文章阅读奖励" type="number" name="readAward"  value="${readAward}" style="width:100%;"  /></td>
		   </tr>
		   <tr>
				<td align="right"><label>好友分享奖励(元):</label></td>
				<td ><input id="friendShareAward" class="form-control" placeholder="请输入文章分享奖励" type="number" name="friendShareAward"  value="${shareAward}" style="width:100%;" /></td>
		   </tr>
		   <tr>
				<td align="right"><label>朋友圈分享奖励(元):</label></td>
				<td ><input id="timelineShareAward" class="form-control" placeholder="请输入文章分享奖励" type="number" name="timelineShareAward"  value="${shareTimeLineAward}" style="width:100%;" /></td>
		   </tr>
		   <tr>
			<td align="right" width="90px;"><label>文章地址:</label></td>
			<td >
			   <textarea rows="6" style="width:100%" id="weixinLink" name="weixinLink" placeholder="  输入微信公众号文章的URL地址 点击 开始采集"></textarea>
			</td>
	      </tr>
		 <tr>
			<td colspan="2" class="text-center">
               <button type="button" class="btn btn-primary formAddBtn" id="gatherBtn">
                                                     开始采集
               </button>
			</td>
		</tr>
		</tbody>
	 </table>
              
     </form>
  </div>
   </div>
   <!-- /Page Content -->
</div>
<!-- /Page Container -->
<!-- Main Container -->
</div>  
<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admincps/content/content.js"></script>
</body>
</html>
