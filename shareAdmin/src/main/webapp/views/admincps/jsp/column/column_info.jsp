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
</head>
<body class="bodybg">
	<!-- Loading Container -->
	<%@ include file="../../../common/loading.jsp"%>
	<!--  /Loading Container -->

	<!-- Page Body -->
	<div class="page-body">
		<div class="swidget">
			<div class="widget-body  no-padding">
				<form role="form" id="columnForm" action="" method="post">
					<div id="registration-form">
						<table class="table table-striped table-bordered"
							id="simpledatatable">
							<tbody>
								<tr>
									<td align="right">栏目类型:</td>
									<td><select name="columnType" id="columnType"
										style="width: 100%">
											<c:forEach items="${typeMap}" var="dp">
												<option value="${dp.state}"
													<c:if test="${dp.state == columnInfo.columnType}">selected="selected"</c:if>>${dp.name}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td align="right" width="90px">栏目名称:</td>
									<td><input id="columnName" class="form-control"
										placeholder="请输入栏目名称" type="text" name="columnName"
										value="${columnInfo.columnName}" /></td>
								</tr>
								<tr>
									<td align="right">排序编号:</td>
									<td><input id="orderNo" class="form-control"
										placeholder="请输入栏目编号" type="number" name="orderNo"
										value="${columnInfo.orderNo}" /></td>
								</tr>
								<tr>
									<td align="right" width="90px">栏目code:</td>
									<td><input id="code" class="code"
										placeholder="请输入编码" type="text" name="code"
										value="${columnInfo.code}" /></td>
								</tr>
								<tr>
									<td align="right">是否有效:</td>
									<td><select name="enabled" id="enabled"
										style="width: 100%">
											<c:forEach items="${enabledMap}" var="dp">
												<option value="${dp.key}"
													<c:if test="${dp.key==columnInfo.enabled}">selected="selected"</c:if>>${dp.value}</option>
											</c:forEach>
									</select></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div>
						<input type="hidden" class="form-control" id="columnId"
							name="columnId" value="${columnInfo.columnId}" />
					</div>
					<div class="formBtn">
						<c:if test="${empty optype}">
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
	<!-- /Page Body -->

	<%@ include file="../../../common/com_js.jsp"%>

	<script src="${basePath}static/js/admincps/column/column.js"></script>


</body>
<!--  /Body -->
</html>
