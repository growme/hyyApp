<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Head -->
<head>
<meta charset="utf-8" />
<title>${CT_MENU_NAV}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
<link href="${basePath}static/js/bfileinput/css/fileinput.css" rel="stylesheet" type="text/css" />
<style type="text/css">

.info-box {
	font-size: 14px;
	margin-bottom: 10px;
	border: 0px solid #eee;
}

.info-box .info-hd {
	line-height: 35px;
	height: 35px;
	font-size: 14px;
	padding: 0 14px;
	background: #f5f5f5;
	border-bottom: 0px solid #eee;
}

.info-box .info-hd .pos-top {
	top: -13px !important;
}

.info-box .info-bd {
	padding: 10px 20px;
	font-size: 12px;
	zoom: 1;
}

.info-box.mini-padding .info-bd {
	padding: 20px 30px;
}

.info-box .info-bd .empty {
	padding: 35px 0;
	text-align: center;
	color: #8d8d8d;
}

.info-box .info-bd .data-box.with-padding {
	margin: 0 -13px;
}

.info-title {
	font-weight: bold;
}

.table>tbody>tr>td {
	border-top: 0px dashed #eee !important;
}
</style>
</head>
<!-- /Head -->
<!-- Body -->
<body class="bodybg">
	<!-- Loading Container -->
	<%@ include file="../../../common/loading.jsp"%>
	<!--  /Loading Container -->
	<div class="page-body">
		<div class="swidget">
			<div class="widget-body no-padding">
				<div class="widget-main">
					<input type="hidden" id="flag" name="flag" value="${flag}">
					<input type="hidden" id="mod" name="mod" value="${menuId}">
					<form id="baseSettingForm" action="" method="post">
						<input type="hidden" id="memberId" name="memberId"
							value="${memberInfo.memberId}">
						<div class="info-box">

							<div class="info-bd no-margin no-padding">
								<table class="table" id="zcxxtable">
									<tbody>
										<tr>
											<td align="right" sytle="width:140px;">账户状态：</td>
											<td>
											<select name="userState" id="userState" style="width:100%;">
											<option value="">请选择...</option>
											<c:if test="${!empty stlist}">
											<c:forEach items="${stlist}" var="dp">
											  <option value="${dp.type}" <c:if test="${dp.type==memberInfo.userState}">selected</c:if>>${dp.name}</option>
											</c:forEach>
											</c:if>
											</select>
										  </td>
										</tr>
										<tr>
											<td align="right" width="130px">会员等级：</td>
											<td>
											<select name="memberLevel" id="memberLevel" style="width:100%;">
											<option value="">请选择...</option>
											<c:if test="${!empty lvlist}">
											<c:forEach items="${lvlist}" var="dp">
											  <option value="${dp.type}" <c:if test="${dp.type==memberInfo.memberLevel}">selected</c:if>>${dp.name}</option>
											</c:forEach>
											</c:if>
											</select>
										  </td>
										</tr>
										<tr>
											<td align="right">手机账户：</td>
											<td><input type="text" class="form-control"
												id="loginAccount" name="loginAccount"
												placeholder="请设置用户登录手机号码" value="${memberInfo.loginAccount}">

											</td>
										</tr>
										<c:if test="${empty memberInfo.memberId}">
											<tr>
												<td align="right" width="130px">登录密码：</td>
												<td>
												<input type="password" class="form-control"  id="password" name="password" placeholder="请输入账户登录密码" value="">
												</td>
											</tr>
											<tr>
												<td align="right">确认密码：</td>
												<td>
												<input type="password" class="form-control" id="password2" name="password2" placeholder="请再次输入登录密码" value="">
												<div class="horizontal-space"></div>
												</td>
											</tr>
										</c:if>
										<tr>
											<td align="right">会员昵称：</td>
											<td>
											<input type="text" class="form-control" id="memberName" name="memberName" placeholder="请输入用户名称，留空则系统自动生成" value="${memberInfo.memberName}">
											</td>
										</tr>
										<tr>
											<td align="right">手机号码：</td>
											<td>
											<input type="text" class="form-control" id="mobile" name="mobile" placeholder="请输入会员手机号码" value="${memberInfo.mobile}">
											</td>
										</tr>
										<tr>
											<td align="right">QQ号码：</td>
											<td>
											<input type="text" class="form-control" id="qqNum" name="qqNum" placeholder="请输入会员的QQ号码" value="${memberInfo.qqNum}">
										    </td>
										</tr>
										<tr>
											<td align="right">微信账号：</td>
											<td>
											<input type="text" class="form-control"	id="wechat" name="wechat" placeholder="请输入会员的微信号" value="${memberInfo.wechat}">
											</td>
										</tr>
										<tr>
											<td align="right">支付宝账号：</td>
											<td>
											<input type="text" class="form-control" id="payAccount" name="payAccount" placeholder="请输入支付宝账号，方便转账" value="${memberInfo.payAccount}">
											</td>
										</tr>
										<tr>
											<td align="right">支付宝姓名：</td>
											<td>
											<input type="text" class="form-control" id="accountName" name="accountName" placeholder="请输入支付宝账号对应的名称，方便核实" value="${memberInfo.accountName}">
											</td>
										</tr>
										<tr>
											<td align="right">推&nbsp;&nbsp;荐&nbsp;&nbsp;人：</td>
											<td>
											<input type="text" class="form-control" id="recomUser" name="recomUser" placeholder="请输入推荐人的登录手机号码" value="${memberInfo.recomUser}">
											</td>
										</tr>
										<tr>
											<td align="right">折扣值：</td>
											<td>
											<input type="text" class="form-control" id="disscount" name="disscount" placeholder="请输入流量折扣值 例如扣除30%就直接输入30  只支持整数" value="${memberInfo.disscount}">
											</td>
										</tr>
										<tr>
											<td align="right">CNZZ统计码：</td>
											<td>
											 <textarea style="width:100%;height:75px;" name="cnzzCode" id="cnzzCode" placeholder="请输入cnzz统计js代码">${memberInfo.cnzzCode}</textarea>
											</td>
										</tr>
										<tr>
											<td align="right">百度统计码：</td>
											<td>
											 <textarea style="width:100%;height:75px;" name="baiduCode" id="baiduCode" placeholder="请输入者百度统计js代码">${memberInfo.baiduCode}</textarea>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<div class="formBtn" style="margin-top:0px;margin-right:5px;">
							<button type="button" class="btn btn-primary" id="saveZcxxBtn">
								<i class="fa fa-check"></i>提 交
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
	<!-- /Page Body -->
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${basePath}static/js/admincps/member/member.js"></script>
</body>
<!--  /Body -->
</html>
