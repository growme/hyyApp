<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${CT_MENU_NAV}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
<link href="${basePath}static/js/bfileinput/css/fileinput.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/multiselect.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}static/css/product.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
.info-box {
	font-size: 14px;
	margin-bottom: 10px;
	border: 1px solid #ddd;
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

.table>tbody>tr>td {
	border-top: 0px solid #ddd!important;
	padding-right:0px;
	margin-right:0px;
}
</style>
</head>
<body class="bodybg">
	<!-- Loading Container -->
    <%@ include file="../../../common/loading.jsp"%>
    <!--  Loading Container -->
	<!-- Page Body -->
	<div class="page-body">
      <div class="swidget">
        <div class="widget-body  no-padding">
          <form role="form" id="columnForm" action="" method="post">
            <div id="registration-form">
		       <table class="table table-striped table-bordered" id="simpledatatable"  >
		         <tbody>
                      <tr>
						<td align="right" width="80px;">选择栏目:</td>
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
						<td align="right" width="80px;"><label>文章地址:</label></td>
						<td >
						<textarea rows="4" style="width:100%" id="weixinLink" name="weixinLink" placeholder="  输入微信公众号文章的URL地址 点击 开始采集"></textarea>
						</td>
				   </tr>
				 </tbody>
			   </table>
             </div>
             <div>
            	</div>
           		<div class="formBtn">
                    <c:if test="${empty optype}">
                  	<button type="button" class="btn btn-sky" id="saveBtn"><i class="fa fa-save"></i>开始采集</button>
                 	  </c:if>
              </div>
           </form>
          </div>
        </div>
	</div>
	 <!-- /Page Body -->
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${basePath}static/js/admincps/content/content.js"></script>
</body>
<!--  /Body -->
</html>
