<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/includes.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- Head -->
	<head>
		<meta charset="utf-8" />
		<title>${CT_MENU_NAV}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="../../../common/com_css.jsp"%>
	</head>
	<!-- /Head -->
	<!-- Body -->
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
					<div class="page-body no-padding">
					<div class="row no-margin">
						<div class="col-xs-12 col-md-12 padding-5 no-margin">
							<div class="widget no-padding">
								<c:if test="${ADMIN_VERSION!='V5'}">
								<div class="widget-header bordered-bottom bordered-azure ">
									<span class="widget-caption"><i class="fa  fa-coffee"></i> ${CT_MENU_NAV}</span>
									<div class="widget-buttons">
										<a href="#" data-toggle="maximize">
										   <i class="fa fa-expand" title="全屏展示"></i>
										</a>
										<a href="#" data-toggle="collapse"> 
										   <i class="fa fa-minus"  title="展开缩小"></i> 
										</a>
									</div>
								</div>
								</c:if>
								<div class="widget-body no-padding">
						           <iframe src="${basePath}/${monitorUrl}" id="iframepage" frameborder="0" scrolling="no" width="100%" height="100%" marginheight="0" marginwidth="0" ></iframe>
								</div>
							</div>
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
		<script src="${basePath}static/js/admin/task/taskSchedule.js"></script>

	</body>
	<!--  /Body -->
</html>
