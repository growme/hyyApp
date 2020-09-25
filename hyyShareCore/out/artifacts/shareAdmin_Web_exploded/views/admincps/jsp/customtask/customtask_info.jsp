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
					<form role="form" id="customtaskForm" action="" method="post">
						<input type="hidden" class="form-control" id="id" name="id"
							value="${customtask.id}">
						<table class="table table-striped table-bordered"
							id="simpledatatable">
							<tbody>
								<tr>
									<td align="right" width="90px">任务类型：</td>
									<td><select id="type" name="type">
											<option value="">请选择...</option>
											<option value="1"
												<c:if test="${customtask.type==1}">selected</c:if>>日常任务</option>
											<option value="2"
												<c:if test="${customtask.type==2}">selected</c:if>>高佣任务</option>
											<option value="3"
												<c:if test="${customtask.type==3}">selected</c:if>>新手任务</option>
											<option value="4"
												<c:if test="${customtask.type==4}">selected</c:if>>积分墙任务</option>
											<option value="5"
												<c:if test="${customtask.type==5}">selected</c:if>>限量任务</option>
											<option value="6"
												<c:if test="${customtask.type==6}">selected</c:if>>其它任务</option>
									</select></td>
								</tr>
								<tr>
									<td align="right">任务标题：</td>
									<td><input type="text" class="form-control" id="title"
										name="title" placeholder="任务标题" value="${customtask.title}"></td>
								</tr>
								<tr>
									<td align="right"><label>任务图片:</label></td>
									<td><input id="icon" class="form-control"
										placeholder="任务图片" type="text" name="icon"
										value="${customtask.icon}" /></td>
								</tr>
								<tr>
									<td align="right">是否发布：&nbsp;&nbsp;</td>
									<td><label class="no-margin"> <input
											class="checkbox-slider toggle colored-success" id="state_CK"
											name="state_CK" type="checkbox"
											<c:if test="${customtask.status==0}">checked</c:if>>
											<span class="text no-margin"></span> <input type="hidden"
											id="status" name="status" class="form-control"
											value="${customtask.status}">
									</label></td>
								</tr>
								<tr>
									<td align="right">奖励名称：</td>
									<td><input type="text" class="form-control" id="integral"
										name="integral" placeholder="奖励名称"
										value='${customtask.integral}'></td>
								</tr>
								<tr>
									<td align="right">支付金币：</td>
									<td><input type="text" class="form-control"
										id="payIntegral" name="payIntegral" placeholder="支付金币"
										value='${customtask.payIntegral}'></td>
								</tr>
								<tr>
									<td align="right">限定次数：</td>
									<td><input type="text" class="form-control" id="payNum"
										name="payNum" placeholder="奖励名称" value='${customtask.payNum}'></td>
								</tr>
								<tr>
									<td align="right">完成需要时长（秒）：</td>
									<td><input type="text" class="form-control" id="musttime"
										name="musttime" placeholder="完成需要时长（秒）"
										value='${customtask.musttime}'></td>
								</tr>
								
								<tr>
									<td align="right">任务介绍：</td>
									<td><textarea rows="3" cols="60" id="content"
											name="content">${customtask.content}</textarea></td>
								</tr>
								<tr>
									<td align="right" width="90px">跳转方式：</td>
									<td><select id="target" name="target">
											<option value="">请选择...</option>
											<option value="blank"
												<c:if test="${customtask.target=='blank'}">selected</c:if>>新窗口打开</option>
											<option value="self"
												<c:if test="${customtask.target=='self'}">selected</c:if>>本窗口打开</option>
									</select></td>
								</tr>
								<tr>
									<td align="right">任务跳转地址：</td>
									<td><input type="text" class="form-control" id="url"
										name="url" placeholder="任务跳转地址" value="${customtask.url}"></td>
								</tr>
								<tr>
									<td align="right">按钮名称：</td>
									<td><input type="text" class="form-control" id="url"
										name="url" placeholder="任务跳转地址" value="${customtask.url}"></td>
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
	<script src="${basePath}static/js/admincps/customtask/customtask.js"></script>
	<script type="text/javascript">
		$(document)
				.on(
						'ready',
						function() {
							$("#appLogo")
									.fileinput(
											{
												language : "zh",
												uploadUrl : ccnetpath
														+ "/file/upload/single?fk=appLogo",
												deleteURl : ccnetpath
														+ "/file/trash",
												browseClass : "btn btn-success",
												browseLabel : "选择",
												browseIcon : "<i class=\"glyphicon glyphicon-picture\"></i> ",
												removeClass : "btn btn-danger",
												removeLabel : "清除",
												removeIcon : "<i class=\"glyphicon glyphicon-trash\"></i> ",
												uploadClass : "btn btn-info",
												uploadLabel : "上传",
												dropZoneTitle : "请选择或者拖拽图片到这里&hellip;",
												uploadIcon : "<i class=\"glyphicon glyphicon-upload\"></i> ",
												allowedFileExtensions : [
														"jpg", "JPG", "gif",
														"png", "jpeg", "tmp" ],
												slugCallback : function(
														filename) {
													return filename;
												}
											});
							$("#appLogo").on(
									"fileuploaded",
									function(event, data, previewId, index) {
										var sdata = data.response.url;
										console.log(sdata);
										if (!isNull(sdata)) {
											$("#REPEAT_APP_WATERMARK").val(
													sdata);
											$("#appLogo_pre")
													.attr("src", sdata);
										}
									});
						})
	</script>
</body>
</html>
