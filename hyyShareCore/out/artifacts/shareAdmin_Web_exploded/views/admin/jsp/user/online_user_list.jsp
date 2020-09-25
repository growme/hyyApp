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
					<div class="page-body">
						<div class="row">
							<div class="col-xs-12 col-md-12">
								<div class="widget">
									<c:if test="${ADMIN_VERSION!='V5'}">
									<div class="widget-header bordered-bottom bordered-azure">
										<span class="widget-caption"><i class="fa fa-coffee"></i> ${CT_MENU_NAV}</span>
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
									<div class="widget-body">
										<div role="grid" id="simpledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
											
										   <c:if test="${!empty permitFBtn}">
											<div class="DTTT btn-group">
												<div class="buttons-preview">
				                                    <div class="btn-group no-margin-right">
				                                        <c:forEach var="btn" items="${permitFBtn}">
				                                         <button type="button" class="btn btn-default" id="${btn.btnId}"><i class="${btn.icon}"></i> ${btn.resourceName}</button>
				                                        </c:forEach>
			                                        </div>
				                                </div>
											</div>
	                                       </c:if>
											
											<div class="table-scrollable">
											<table class="table table-striped table-bordered table-hover" id="simpledatatable" >
												<thead>
													<tr role="row">
														<th style="width:40px;" class="text-center">
															序号
														</th>
														<c:if test="${!empty permitTBtn}">
														<th class="text-center" style="width:60px;">操 作</th>
														</c:if>
														<th>会话ID</th>
														<th>登录账户</th>
														<th>账户类型</th>
														<th>登录IP</th>
														<!-- <th>IP归属地</th> -->
														<th>会话有效时间</th>
														<th class="text-center">在线状态</th>
														<th>最后访问时间</th>
													</tr>
												</thead>
												<tbody>
												<c:if test="${!empty sessionList}">
												   <c:forEach var="sn" varStatus="status" items="${sessionList}">
													<tr>
														<td class="text-center">${status.index+1}</td>
														<c:if test="${!empty permitTBtn}">
														<td class="text-center" style="width:40px;">
														<c:forEach var="btn" items="${permitTBtn}">
														    <a href="javascript:${btn.btnFun}('${sn.sessionId}');" id="${btn.btnId}" title="${btn.resourceName}"><i class="${btn.icon} sz14"></i></a>
														</c:forEach>
														</td>
														</c:if>
														<td>${sn.sessionId}</td>
														<td>${sn.loginAccount}</td>
														<td>${sn.userType}</td>
														<td>${sn.loginIp}</td>
														<%-- <td>${sn.ipLocation}</td> --%>
														<td>${sn.timeout}</td>
														<td class="text-center">
														    ${sn.online?'<span class="label label-default">离线</span>':'<span class="label label-success">在线</span>'}
														</td>
														<td><fmt:formatDate value="${sn.lastAccessTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
													</tr>
													
													</c:forEach>
													</c:if>
													
													
													<c:if test="${empty sessionList}">
													<tr><td colspan="7" align="center" style="font-size:14px;color:red">很遗憾,当前没有在线用户</td></tr>
													</c:if>
													
												</tbody>
											</table>
											
											</div>
										</div>
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
		<script src="${basePath}static/js/admin/user/user.js"></script>
		
	</body>
	<!--  /Body -->
</html>
