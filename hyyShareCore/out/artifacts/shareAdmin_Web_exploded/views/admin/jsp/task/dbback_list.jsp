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
									<div class="widget-body">
										<div role="grid" id="simpledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
											<c:if test="${!empty permitFBtn}">
											<div class="DTTT btn-group">
												<div class="buttons-preview">
				                                    <div class="btn-group">
				                                        <c:forEach var="btn" items="${permitFBtn}">
				                                         <button type="button" class="btn btn-default" id="${btn.btnId}"><i class="${btn.icon}"></i> ${btn.resourceName}</button>
				                                        </c:forEach>
			                                        </div>
				                                </div>
											</div>
	                                       </c:if>
											
											<div class="table-scrollable">
											<table class="table table-striped table-bordered table-hover"
												id="simpledatatable" >
												<thead>
													<tr role="row">
														<th style="width:35px;">
															<label>
					                                            <input type="checkbox" name="ckall" onclick="selectAll('ckall','ck');">
					                                            <span class="text"></span>
					                                        </label>
														</th>
														<c:if test="${!empty permitTBtn}">
														<th class="text-center">
														    操 作
														</th>
														</c:if>
														<th>
															文件名称
														</th>
														<th>
															大小
														</th>
														<th>
															时间
														</th>
													</tr>
												</thead>
												<tbody>
												<c:if test="${!empty list}">
												   <c:forEach var="dp" varStatus="status" items="${list}">
													<tr>
														<td>
															<label>
					                                            <input type="checkbox" name="ck" id="ck_${dp.fileName}" value="${dp.fileName}">
					                                            <span class="text"></span>
					                                        </label>
														</td>
														<c:if test="${!empty permitTBtn}">
														<td class="text-center" style="width:80px;">
														<c:forEach var="btn" items="${permitTBtn}">
														    <a href="javascript:${btn.btnFun}('${dp.fileName}');" id="${btn.btnId}" title="${btn.resourceName}"><i class="${btn.icon} sz14"></i></a>&nbsp;
														</c:forEach>
														</td>
														</c:if>
														<td>
															${dp.fileName}
														</td>
														<td>
															${dp.fileSize / 1024} KB
														</td>
														<td> 
															<fmt:formatDate value="${dp.date}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>  
														</td>
													</tr>
													
													</c:forEach>
													</c:if>
													
													
													<c:if test="${empty list}">
													<tr><td colspan="9" align="center" style="font-size:14px;color:red">很抱歉,没有查询到您要的数据</td></tr>
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
		<script src="${basePath}static/js/admin/task/dbback.js"></script>
	</body>
	<!--  /Body -->
</html>
