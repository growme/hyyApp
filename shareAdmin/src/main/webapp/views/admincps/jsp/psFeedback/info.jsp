<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>反馈回复</title>
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
					<form role="form" id="psFeedbackForm" action="" method="post">
						<input type="hidden" class="form-control" id="id" name="id"
							value="${menuInfo.id}">
						<table class="table table-striped table-bordered"
							id="simpledatatable">
							<tbody>
								<tr>
									<td align="right" width="90px">用户id：</td>
									<td><label>${menuInfo.memberId}</label>
									<%-- <input type="text" class="form-control" id="name"
										name="name" placeholder="用户id"
										value="${menuInfo.memberId}" disabled="disabled"> --%></td>
								</tr>
								<tr>
									<td align="right">联系方式：</td>
									<td><label>${menuInfo.phone}</label>
									<%-- <input type="text" class="form-control" id="realmName"
										name="realmName" placeholder="联系方式" disabled="disabled"
										value="${menuInfo.phone}"> --%></td>
								</tr>
								<tr>
									<td align="right">反馈内容：</td>
									<td><label>${menuInfo.content}</label>
									<%-- <textarea rows="3" cols="60" id="accessToken" disabled="disabled"
											name="accessToken">${menuInfo.content}</textarea> --%></td>
								</tr>

								<tr>
									<td align="right">反馈时间：</td>
									<td><label>${menuInfo.createTime}</label>
									<%-- <input class="input-sm Wdate" style="width: 150px;" disabled="disabled"
										type="text" name="updateTime" id="updateTime"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="${menuInfo.createTime}" /> --%></td>
								</tr>
								<tr>
									<td align="right">回复：</td>
									<td><textarea rows="3" cols="60" id="reply"
											name="reply">${menuInfo.reply}</textarea></td>
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
	<script
		src="${basePath}static/js/admincps/psAppInfo/psFeedback.js"></script>
</body>
</html>
