<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>增加自定义任务</title>
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
					<form role="form" id="psAppInfoForm" action="" method="post">
						<input type="hidden" class="form-control" id="id" name="id"
							value="${psAppInfo.id}">
						<table class="table table-striped table-bordered"
							id="simpledatatable">
							<tbody>
								<tr>
									<td align="right">AppId：</td>
									<td><input type="text" class="form-control" id="appId"
										name="appId" placeholder="AppId" value="${psAppInfo.appId}"></td>
								</tr>
								<tr>
									<td align="right">应用名称：</td>
									<td><input type="text" class="form-control" id="appName"
										name="appName" placeholder="应用名称" value='${psAppInfo.appName}'></td>
								</tr>

								<tr>
									<td align="right">包名：</td>
									<td><input type="text" class="form-control"
										id="packageName" name="packageName" placeholder="包名"
										value='${psAppInfo.packageName}'></td>
								</tr>
								<tr>
									<td align="right">用户名：</td>
									<td><label><input
											type="checkbox" id="bzName_1" name="bzName" placeholder="好友" <c:if test="${fn:contains(psAppInfo.bzName,1)}">checked</c:if>
											value="1" /><span class="text">微信好友</span></label><label>     <input
											type="checkbox" id="bzName_2" name="bzName" placeholder="朋友圈" <c:if test="${fn:contains(psAppInfo.bzName,2)}">checked</c:if>
											value="2" /><span class="text">朋友圈</span></label></td>

								</tr>

								<tr>
									<td align="right" width="90px">状态：</td>
									<td><select id="status" name="status">
											<option value="0"
												<c:if test="${psAppInfo.status==0}">selected</c:if>>启用</option>
											<option value="1"
												<c:if test="${psAppInfo.status==1}">selected</c:if>>停用</option>
									</select></td>
								</tr>
							</tbody>
						</table>
						<div class="formBtn">
							<button type="button" class="btn btn-sky" id="saveBtn">
								<i class="fa fa-save"></i>提 交
							</button>
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
	<script src="${basePath}static/js/admincps/psAppInfo/psAppInfo.js"></script>
</body>
</html>
