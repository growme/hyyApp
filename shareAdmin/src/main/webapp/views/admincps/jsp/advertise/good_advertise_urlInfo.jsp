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
<link href="${basePath}static/css/product.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
.info-box {
	font-size: 14px;
	margin-bottom: 10px;
	border: 1px solid #fafafa;
}

.info-box .info-hd {
	line-height: 35px;
	height: 35px;
	font-size: 14px;
	padding: 0 14px;
	background: #f5f5f5;
	border-bottom: 0px solid #e7e7eb;
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

.cimg {
	margin: 0;
	display: block;
	width: 100%;
	height: auto;
}

.table>tbody>tr>td {
	border-top: 0px solid #ddd !important;
	padding-right: 0px;
	margin-right: 0px;
}
</style>
</head>
<body class="bodybg">
	<!-- Loading Container -->
	<%@ include file="../../../common/loading.jsp"%>
	<!--  /Loading Container -->
	<c:if test="${ADMIN_VERSION!='V5'}">
		<!-- Navbar -->
		<%@ include file="../../../common/navbar.jsp"%>
		<!-- /Navbar -->
		<!-- Main Container -->
		<div class="main-container container-fluid">
			<!-- Page Container -->
			<div class="page-container">
				<!-- menu Container -->
				<%@ include file="../../../common/menu.jsp"%>
				<!--  /menu Container -->
				<!-- Page Content -->
				<div class="page-content">
					<!-- Page Breadcrumb -->
					<%@ include file="../../../common/navtip.jsp"%>
					<!-- /Page Breadcrumb -->
	</c:if>
	<!-- Page Body -->
	<div class="page-body">
		<div class="swidget">
			<div class="widget-body no-padding">
				<form role="form" id=advertiseForm action="" method="post">

					<div class="info-box">
						<div class="info-hd">
							<span class="no-margin-top"><i class="fa fa-gift"></i>${Const.CT_MENU_NAV}</span>
						</div>
						<div class="info-bd no-margin no-padding">
							<table class="table table-striped" id="simpledatatable">
								<tbody>
									<tr>
										<td align="right" style="width: 120px;"><label>广告标签:</label></td>
										<td><select name="adTag" id="adTag" placeholder="广告标签"
											style="width: 100%;" disabled="disabled">
												<option value="">请选择</option>
												<c:forEach items="${adTagList}" var="dp">
													<option value="${dp.codeValue}"
														<c:if test="${dp.codeValue==advert.adTag}">selected="selected"</c:if>>${dp.valueDesc}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>广告类型:</label></td>
										<td><select name="adType" id="adType" placeholder="广告类型"
											disabled="disabled" style="width: 100%;">
												<option value="">请选择</option>
												<c:forEach items="${adTypeMap}" var="dp">
													<option value="${dp.key}"
														<c:if test="${dp.key==advert.adType}">selected="selected"</c:if>>${dp.value}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>广告地址:</label></td>
										<td><input id="adLink" class="form-control"
											placeholder="请输入广告地址" type="text" name="adLink"
											value="${advert.adLink}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>广告备用地址:</label></td>
										<td><input id="adLink2" class="form-control"
											placeholder="请输入广告备用地址" type="text" name="adLink2"
											value="${advert.adLink2}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right">充值广告主：</td>
										<td><select name="adUserId" id="adUserId"
											disabled="disabled" style="width: 100%;">
												<option value="">请选择...</option>
												<c:if test="${!empty userList}">
													<c:forEach items="${userList}" var="dp">
														<option value="${dp.userId}"
															<c:if test="${dp.userId==advert.adUserId}">selected</c:if>>${dp.userName}</option>
													</c:forEach>
												</c:if>
										</select></td>
									</tr>
									<tr>
										<td align="right"><label>请输入每次点击价格:</label></td>
										<td><input id="unitPrice" disabled="disabled"
											class="form-control" placeholder="请输入每次点击价格" type="text"
											name="unitPrice" value="${advert.unitPrice}"
											style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>排序序号:</label></td>
										<td><input id="sortNo" disabled="disabled"
											class="form-control" placeholder="请输入排序序号" type="text"
											name="sortNo" value="${advert.sortNo}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right"><label>备注信息:</label></td>
										<td><input id="remark" disabled="disabled"
											class="form-control" placeholder="备注信息" type="text"
											name="remark" value="${advert.remark}" style="width: 100%;" /></td>
									</tr>
									<tr>
										<td align="right" colspan="2">
											<div class="formBtn">
												<c:if test="${empty optype}">
													<button type="button" class="btn btn-sky" id=saveAdUrlBtn>
														<i class="fa fa-save"></i>提 交
													</button>
												</c:if>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div>
						<input type="hidden" value="${advert.adId}" name="adId" id="adId" />
						<input type="hidden" value="${advert.adCode}" name="adCode"
							id="adCode" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- /Page Body -->
	<c:if test="${ADMIN_VERSION!='V5'}">
		</div>
		<!-- /Page Content -->
		</div>
		<!-- /Page Container -->
		<!-- Main Container -->
		</div>
	</c:if>

	<%@ include file="../../../common/com_js.jsp"%>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/table.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/admincps/advertise/good_advertise.js"></script>

	<script type="text/javascript">
		$(document).on('ready', function() {
			if (isNull($("#adId").val())) {
				//$("#adTag").val("1");
				$("#readNum").val("1000");
				$("#adType").val("1");
				$("#state").val("1");
			}
		});
	</script>
</body>
<!--  /Body -->
</html>
