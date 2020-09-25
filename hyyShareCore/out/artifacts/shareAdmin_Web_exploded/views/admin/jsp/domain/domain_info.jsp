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
        
	</head>
<body class="padding-top-10">
<div class="col-lg-6 col-sm-6 col-xs-12">
    <div class="widget">
        <div class="widget-body">
            <div id="horizontal-form">
                <form class="form-horizontal" role="form" id="domainForm">
                    <input type="hidden" id="domainId" name="domainId" class="form-control" value="${domainInfo.domainId}">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" id="domainName" name="domainName" placeholder="请输入派单备用的域名，确认没有被封" class="form-control" value="${domainInfo.domainName}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" id="orderNumber" name="orderNumber" placeholder="请输入排序编号，排序靠前的先用来派单" class="form-control" value="${domainInfo.orderNumber}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="domainType" id="domainType" style="width:100%;">
                              <option value="">选择类型</option>
							  <c:if test="${!empty tlist}">
							    <c:forEach items="${tlist}" var="dp">
							       <option value="${dp.type}" <c:if test="${dp.type==domainInfo.domainType}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
							  
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="enabled" id="enabled" style="width:100%;">
                              <option value="">选择状态</option>
							  <c:if test="${!empty slist}">
							    <c:forEach items="${slist}" var="dp">
							       <option value="${dp.state}" <c:if test="${dp.state==domainInfo.enabled}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
							  
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div>
                        <button type="button" class="btn btn-primary" id="saveBtn">
                          <i class="fa fa-check"></i>提 交
                        </button>
                        <button type="button" class="btn btn-danger" id="closeBtn">
                          <i class="fa fa-power-off"></i>关 闭 
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admin/domain/domain.js"></script>

</body>
</html>
