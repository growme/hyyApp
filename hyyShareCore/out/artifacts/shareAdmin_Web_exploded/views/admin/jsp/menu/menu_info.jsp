<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>资源管理</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="no-padding">
<div class="col-lg-12 col-sm-12 col-xs-12 no-padding">
    <div class="swidget">
        <div class="widget-body">
            <div id="registration-form">
             <form role="form" id="menuForm" action="" method="post">
                 <input type="hidden" id="optype" name="optype" class="form-control" value="${optype}">
	             <input type="hidden" id="resourceId" name="resourceId" class="form-control" value="${menuInfo.resourceId}">
	             <input type="hidden" id="resourceCode" name="resourceCode" class="form-control" value="${menuInfo.resourceCode}">
            	<table class="table table-striped table-bordered table-hover" id="simpledatatable" >
            	   <tbody>
            	   <tr>
						<td align="right" width="90px">
							资源名称：
						</td>
						<td>
							<input type="text" class="form-control" id="resourceName" name="resourceName" placeholder="字母、数字、下划线" value="${menuInfo.resourceName}">
						</td>
						<td align="right">
							上级资源：
						</td>
						<td>
						    <input type="text" class="form-control" id="parentName" name="parentName" placeholder="点击选择" onclick="showParentMenuTree()" readonly="readonly" value="${menuInfo.parentName}">
							<input type="hidden" id="parentCode" name="parentCode" value="${menuInfo.parentCode}">
							<div id="menuTreeContent" class="menuContent subTreeMenu">
							    <ul id="parentMenuTree" class="ztree subTreeMenuUL"></ul>
							</div>  
						</td>
					</tr>
					<tr>
						<td align="right">
							资源类型：
						</td>
						<td>
						    <select name="resourceType" id="resourceType" style="width:100%;">
                             <option value="">选择类型</option>
							  <c:if test="${!empty tlist}">
							    <c:forEach items="${tlist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==menuInfo.resourceType}">selected</c:if>>${dp.valueDesc}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
						<td align="right">
							资源状态：
						</td>
						<td>
							<select name="resourceState" id="resourceState" style="width:100%;">
                              <option value="">选择状态</option>
							  <c:if test="${!empty slist}">
							    <c:forEach items="${slist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==menuInfo.resourceState}">selected</c:if>>${dp.valueDesc}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							是否展开：
						</td>
						<td>
						    <select name="expanded" id="expanded" style="width:100%;">
                              <option value="">请选择..</option>
							  <c:if test="${!empty elist}">
							    <c:forEach items="${elist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==menuInfo.expanded}">selected</c:if>>${dp.valueDesc}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
						<td align="right">
							节点类型：
						</td>
						<td>
						    <select name="isleaf" id="isleaf" style="width:100%;">
                              <option value="">选择类型</option>
							  <c:if test="${!empty flist}">
							    <c:forEach items="${flist}" var="dp">
							       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==menuInfo.isleaf}">selected</c:if>>${dp.valueDesc}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
					</tr>
					
					
					<tr>
						<td align="right">
							资源路径：
						</td>
						<td colspan="3">
							<input type="text" id="resourceUrl" name="resourceUrl" class="form-control" placeholder="设置资源访问的路径" value="${menuInfo.resourceUrl}">
						</td>
					</tr>
					
					<tr>
						<td align="right">
							按钮标识：
						</td>
						<td>
						     <input type="text" id="btnId" name="btnId" class="form-control" placeholder="设置按钮页面元素ID值" value="${menuInfo.btnId}">
						</td>
						<td align="right">
							按钮方法：
						</td>
						<td>
							<input type="text" id="btnFun" name="btnFun" class="form-control" placeholder="设置按钮执行js方法名称" value="${menuInfo.btnFun}">
						</td>
					</tr>
					
					<tr>
						<td align="right">
							按钮图标：
						</td>
						<td>
							<input type="text" id="icon" name="icon" class="form-control" placeholder="点击选择图标和颜色"  readonly="readonly" value="${menuInfo.icon}">
						</td>
						<td align="right">
							排序编号：
						</td>
						<td>
						     <input type="text" id="orderNumber" name="orderNumber" class="form-control" placeholder="只能为数字" value="${menuInfo.orderNumber}">
						</td>
					</tr>
					
					<tr>
						<td align="right">
							资源描述：
						</td>
						<td colspan="3">
							<textarea class="form-control" id="resourceDesc" name="resourceDesc" placeholder="请保证在200字符以内"  style="width:100%;height:150px">${menuInfo.resourceDesc}</textarea>
						</td>
					</tr>
					</tbody>
				 </table>
                 <div class="formBtn">
                      <c:if test="${empty optype || optype!='see'}">
                         <button type="button" class="btn btn-sky" id="saveBtn">
                      <i class="fa fa-save"></i>提 交
                    </button>
                   </c:if>
                   <button type="button" class="btn btn-default" id="closeBtn">
                   <i class="fa fa-power-off"></i>关 闭 
                   </button>
               </div>
              </form>
          </div>
        </div>
    </div>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admin/menu/menu.js"></script>
</body>
</html>
