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
<link href="${basePath}static/js/bfileinput/css/fileinput.css"
	rel="stylesheet" type="text/css" />
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
					<form id="entityForm" action="" method="post">
						<input type="hidden" id="id" name="id"
							value="${jpWithdrawMoney.id}">
						<div class="info-box">
							<div class="info-bd no-margin no-padding">
								<table class="table" id="zcxxtable">
									<tbody>
										<tr>
											<td align="right" sytle="width:140px;">金额名称：</td>
											<td><input type="text" class="form-control" id="name"
												name="name" placeholder="金额名称"
												value="${jpWithdrawMoney.name}"></td>
										</tr>
										<tr>
											<td align="right" width="130px">金额数：</td>
											<td><input type="number" class="form-control" id="money"
												name="money" placeholder="金额数"
												value="${jpWithdrawMoney.money}"></td>
										</tr>
										<tr>
											<td align="right">是否首次提现：</td>
											<td><select id="type" name="type" class="form-control">
													<option value="0">非首次提现</option>
													<option value="1"
														<c:if test="${jpWithdrawMoney.type=='1' }">selected="selected"</c:if>>首次提现</option>
											</select></td>
										</tr>
										<tr>
											<td align="right">描述：</td>
											<td><input type="text" class="form-control" id="descs"
												name="descs" placeholder="描述"
												value="${jpWithdrawMoney.descs}"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

						<div class="formBtn" style="margin-top: 0px; margin-right: 5px;">
							<button type="button" class="btn btn-primary" id="saveEntity">
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
	<script
		src="${basePath}static/js/adminjp/withdrawMoney/withdrawMoney.js"></script>
</body>
<!--  /Body -->
</html>
