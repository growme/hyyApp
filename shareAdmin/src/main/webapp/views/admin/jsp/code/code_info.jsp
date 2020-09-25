<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/includes.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>参数管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/com_css.jsp"%>
	</head>
<body class="padding-top-10">
<div class="col-lg-6 col-sm-6 col-xs-12">
    <div class="widget">
        <div class="widget-body">
            <div id="horizontal-form">
                <form class="form-horizontal" role="form" id="codeForm">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="hidden" id="codeId" name="codeId" class="form-control" value="${codeInfo.codeId}">
                            <input type="text" id="codeKey" name="codeKey" placeholder="对照字段" class="form-control" style="width:350px;" value="${codeInfo.codeKey}">
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                            <input type="text" id="codeName" name="codeName" placeholder="字段名称" class="form-control" style="width:350px;" value="${codeInfo.codeName}">
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                            <input type="text" id="codeValue" name="codeValue" placeholder="字段内容" class="form-control" style="width:350px;" value="${codeInfo.codeValue}">
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                            <input type="text" id="valueDesc" name="valueDesc" placeholder="字段描述" class="form-control" style="width:350px;" value="${codeInfo.valueDesc}">
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                             <select name="editMode" id="editMode" placeholder="编辑模式"  style="width:350px;">
							  <c:if test="${!empty edlist}">
							    <c:forEach items="${edlist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==codeInfo.editMode}">selected</c:if>>${dp.codeDesc}</option>
							    </c:forEach>
							  </c:if>
							  </select>
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                             <select name="codeState" id="codeState" placeholder="是否启用"  style="width:350px;">
							  <c:if test="${!empty enlist}">
							    <c:forEach items="${enlist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==codeInfo.codeState}">selected</c:if>>${dp.codeDesc}</option>
							    </c:forEach>
							  </c:if>
							  </select>
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                            <input type="text" id="orderNumber" name="orderNumber" placeholder="排序编号" class="form-control"  style="width:350px;" value="${codeInfo.orderNumber}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <textarea class="form-control" id="codeDesc" name="codeDesc" rows="4" placeholder="备注信息"  style="width:350px;">${codeInfo.codeDesc}</textarea>
                    <div class="horizontal-space"></div>
                    <div class="padding-top-5">
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
<script src="${basePath}static/js/admin/code/code.js"></script>

</body>
</html>
