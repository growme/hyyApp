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
					<form role="form" id="wechatPublicNumberForm" action=""
						method="post">
						<input type="hidden" class="form-control" id="id" name="id"
							value="${wechatPublicNumber.id}">
						<table class="table table-striped table-bordered"
							id="simpledatatable">
							<tbody>
								<tr>
									<td align="right" width="90px">公众号：</td>
									<td><input type="text" class="form-control" id="name"
										name="name" placeholder="公众号"
										value="${wechatPublicNumber.name}"></td>
								</tr>
								<tr>
									<td align="right">落地域名：</td>
									<td><input type="text" class="form-control" id="realmName"
										name="realmName" placeholder="落地域名"
										value="${wechatPublicNumber.realmName}"></td>
								</tr>
								<tr>
									<td align="right">APPID：</td>
									<td><input type="text" class="form-control" id="appid"
										name="appid" placeholder="落地域名"
										value="${wechatPublicNumber.appid}"></td>
								</tr>
								<tr>
									<td align="right">APPSECRET：</td>
									<td><input type="text" class="form-control" id="appSecret"
										name="appSecret" placeholder="APPSECRET"
										value='${wechatPublicNumber.appSecret}'></td>
								</tr>

								<tr>
									<td align="right">accessToken：</td>
									<td><textarea rows="3" cols="60" id="accessToken"
											name="accessToken">${wechatPublicNumber.accessToken}</textarea>
									</td>
								</tr>
								<tr>
									<td align="right">jsApiTicket：</td>
									<td><textarea rows="3" cols="60" id="jsApiTicket"
											name="jsApiTicket">${wechatPublicNumber.jsApiTicket}</textarea>
									</td>
								</tr>

								<%-- <tr>
									<td align="right">更新时间：</td>
									<td><input class="input-sm Wdate" style="width: 150px;"
										type="text" name="updateTime" id="updateTime"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="${wechatPublicNumber.updateTime}" /></td>
								</tr>

								<tr>
									<td align="right">过期时间：</td>
									<td><input class="input-sm Wdate" style="width: 150px;"
										type="text" name="validTime" id="validTime"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="${wechatPublicNumber.validTime}" /></td>
								</tr> --%>
								
								<tr>
									<td align="right">状态：</td>
									<td><select id="enabled" name="enabled">
											<option value="1"
												<c:if test="${wechatPublicNumber.enabled==1}">selected</c:if>>正常使用</option>
											<option value="0"
												<c:if test="${wechatPublicNumber.enabled==0}">selected</c:if>>停止使用</option>
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
	<script
		src="${basePath}static/js/admincps/publicNumber/wechatPublicNumber.js"></script>
</body>
</html>
