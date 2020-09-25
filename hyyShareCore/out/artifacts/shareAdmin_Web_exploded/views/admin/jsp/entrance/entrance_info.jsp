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
                <form class="form-horizontal" role="form" id="linkForm">
                    <input type="hidden" id="linkId" name="linkId" class="form-control" value="${linkInfo.linkId}">
                    <div class="row">
                        <div class="col-md-6">
                            <textarea rows="4" style="width:100%;" id="linkAddr" name="linkAddr" class="form-control" placeholder="请输入跳转入口地址，请确认没有被封">${linkInfo.linkAddr}</textarea>
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="state" id="state" style="width:100%;">
                              <option value="">选择状态</option>
							  <c:if test="${!empty slist}">
							    <c:forEach items="${slist}" var="dp">
							       <option value="${dp.state}" <c:if test="${dp.state==linkInfo.state}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
							  
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="passMethod" id="passMethod" style="width:100%;">
                              <option value="">选择机制</option>
							  <c:if test="${!empty rlist}">
							    <c:forEach items="${rlist}" var="dp">
							       <option value="${dp.typeId}" <c:if test="${dp.typeId==linkInfo.passMethod}">selected</c:if>>${dp.typeName}</option>
							    </c:forEach>
							  </c:if>
							</select>
							  
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" id="orderNo" name="orderNo" placeholder="请输入排序编号" class="form-control" value="${linkInfo.orderNo}">
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
<script src="${basePath}static/js/admin/entrance/entrance.js"></script>

</body>
</html>
