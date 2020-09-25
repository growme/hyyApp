<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>修改密码</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
	</head>
<body class="padding-top-10">
<div class="col-lg-6 col-sm-6 col-xs-12">
    <div class="widget">
        <div class="widget-body">
            <div id="horizontal-form">
                <form class="form-horizontal" role="form" id="roleForm">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="hidden" id="roleId" name="roleId" class="form-control" value="${roleInfo.roleId}">
                            <input type="text" id="roleName" name="roleName" placeholder="角色名称" class="form-control" style="width:350px;" value="${roleInfo.roleName}">
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                             <select name="roleType" id="roleType" placeholder="角色类型"  style="width:350px;">
                              <option value="">角色类型</option>
							  <c:if test="${!empty tplist}">
							    <c:forEach items="${tplist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==roleInfo.roleType}">selected</c:if>>${dp.codeDesc}</option>
							    </c:forEach>
							  </c:if>
							  </select>
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                             <select name="state" id="state" placeholder="角色状态"  style="width:350px;">
                              <option value="">角色状态</option>
							  <c:if test="${!empty enlist}">
							    <c:forEach items="${enlist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==roleInfo.state}">selected</c:if>>${dp.codeDesc}</option>
							    </c:forEach>
							  </c:if>
							  </select>
                            <div class="horizontal-space"></div>
                        </div>
                        <div class="col-md-6">
                            <input type="text" id="orderNumber" name="orderNumber" placeholder="排序编号" class="form-control"  style="width:350px;" value="${roleInfo.orderNumber}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <textarea class="form-control" id="roleDesc" name="roleDesc" rows="4" placeholder="备注信息"  style="width:350px;">${roleInfo.roleDesc}</textarea>
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
<script src="${basePath}static/js/admin/role/role.js"></script>

</body>
</html>
