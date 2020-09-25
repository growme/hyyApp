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
				<form role="form" id="noticeForm" action="" method="post">

					<div class="info-box">
						<div class="info-hd">
							<span class="no-margin-top"><i class="fa fa-gift"></i>${Const.CT_MENU_NAV}</span>
						</div>
						<div class="info-bd no-margin no-padding">
							<table class="table table-striped" id="simpledatatable">
								<tbody>
									<tr>
										<td align="right" style="width: 120px;"><label>标题:</label></td>
										<td style="max-width: 500px;"><input id="title"
											class="form-control" type="text" name="title"
											value="${notice.title}" style="width: 100%;" /><input
											type="hidden" value="${notice.id}" name="id" id="id" /></td>
									</tr>
									<tr>
										<td width="100px;" align="right"><label>内容:</label></td>
										<td><script id="content" name="content" type="text/plain"
												style="width: 100%; height: 400px;">${notice.content}</script>
										</td>
									</tr>
									<tr>
										<td align="right" colspan="2">
											<div class="formBtn">
												<c:if test="${empty optype}">
													<button type="button" class="btn btn-sky" id="saveBtn">
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
	<script src="${basePath}static/js/bfileinput/js/fileinput.js"></script>
	<script src="${basePath}static/js/bfileinput/js/locales/zh.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/ueditor/ueditor.all.min.js">
		<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/table.js">
	</script>
	<script type="text/javascript" charset="utf-8"
		src="${basePath}static/js/adminjp/activity/good_notice.js"></script>
	<script type="text/javascript">
		$(document).on(
				'ready',
				function() {
					//初始化编辑器
					var ue = UE.getEditor('content', {
						toolbars : [ [ 'fullscreen', 'source', 'undo', 'redo',
								'bold', 'fontsize', 'forecolor', 'insertimage',
								'backcolor' ] ],
						autoHeightEnabled : true,
						autoFloatEnabled : true
					});
				});
	</script>
</body>
<!--  /Body -->
</html>
