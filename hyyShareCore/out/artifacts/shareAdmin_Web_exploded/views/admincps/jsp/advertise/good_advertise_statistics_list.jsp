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
					<div class="widget-header bordered-bottom bordered-azure">
						<span class="widget-caption"><i class="fa fa-coffee"></i>
							${CT_MENU_NAV}</span>
						<div class="widget-buttons">
							<a href="#" data-toggle="maximize"> <i class="fa fa-expand"
								title="全屏展示"></i>
							</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"
								title="展开缩小"></i>
							</a>
						</div>
					</div>
					<div class="widget-body">
						<div role="grid" id="simpledatatable_wrapper"
							class="dataTables_wrapper form-inline no-footer">

							<c:if test="${!empty permitFBtn}">
								<div class="DTTT btn-group">
									<div class="buttons-preview">
										<div class="btn-group no-margin-right">
											<c:forEach var="btn" items="${permitFBtn}">
												<button type="button" class="btn btn-default"
													id="${btn.btnId}">
													<i class="${btn.icon}"></i> ${btn.resourceName}
												</button>
											</c:forEach>
										</div>
									</div>
								</div>
							</c:if>

							<form id="searchForm"
								action="${basePath}backstage/advertise/getAdvertiseStatistics" method="post">
								<div id="simpledatatable_filter" class="dataTables_filter">
									<input class="input-sm Wdate" style="width: 100px;" type="text"
										name="readDate" id="readDate"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
										value="${readDate}" /> <select name="adId" id="adId"
										class="form-control input-sm">
										<option value="">选择类型</option>
										<c:forEach items="${adList}" var="dp">
											<option value="${dp.adId}"
												<c:if test="${dp.adId eq adId}">selected="selected"</c:if>>${dp.adId}：${dp.adTitle}</option>
										</c:forEach>
									</select>
									<button type="submit" id="sb" class="btn btn-default searchBtn">
										<i class="fa fa-search"></i>查询
									</button>
								</div>
							</form>

							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover"
									id="simpledatatable">
									<thead>
										<tr role="row">
											<th class="text-center">操 作</th>
											<th class="text-center">日期</th>
											<th class="text-center">广告id</th>
											<th class="text-center">广告名称</th>
											<th>今日独立ip数</th>
											<th>单价</th>
											<th>今日消耗</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty list}">
											<c:forEach var="dp" varStatus="status" items="${list}">
												<tr>
													<td class="text-center" style="width: 60px;"><c:if
															test="${!empty permitTBtn}">
															<c:forEach var="btn" items="${permitTBtn}">
																<a href="javascript:${btn.btnFun}('${dp.adId}');"
																	id="${btn.btnId}" title="${btn.resourceName}"><i
																	class="${btn.icon} sz14"></i></a>
															</c:forEach>
														</c:if></td>
													<td>${readDate}</td>
													<td>${dp.adId}</td>
													<td><c:if test="${!empty dp.adTitleList}">
															<ul>
																<c:forEach var="tt" items="${dp.adTitleList}"
																	varStatus="st" end="0">
																	<li style="margin-bottom: 2px;">${tt.avTitle}</li>
																</c:forEach>
															</ul>
														</c:if></td>
													<td>${dp.readNum}</td>
													<td>${dp.unitPrice}</td>
													<td>${dp.readNum*dp.unitPrice}</td>
												</tr>

											</c:forEach>
										</c:if>



										<c:if test="${!empty pm.results}">
											<tr>
												<td colspan="15">
													<div class="col-sm-6 no-padding">
														<div class="dataTables_info" id="simpledatatable_info">
															<font size="2">共</font>
															<c:out value="${pm.totalPage}" />
															<font size="2">页 &nbsp;&nbsp;当前第</font>
															<c:out value="${pm.pageNum}" />
															<font size="2">页&nbsp;&nbsp; 共</font>
															<c:out value="${pm.totalRecord}" />
															<font size="2">条记录</font>&nbsp;&nbsp;
														</div>
													</div>

													<div class="col-sm-6 no-padding">
														<div class="dataTables_paginate paging_bootstrap"
															id="simpledatatable_paginate">
															<ul class="pagination">
																<pg:pager url="${basePath}backstage/advertise/index"
																	items="${pm.totalRecord}" export="current=pageNumber"
																	maxIndexPages="5" maxPageItems="${pm.pageSize}">
																	<pg:param name="totalRecord" value="${pm.totalRecord}" />
																	<pg:param name="queryParam" value="${queryParam}" />
																	<pg:param name="state" value="${state}" />
																	<pg:param name="adTag" value="${adTag}" />
																	<pg:param name="adType" value="${adType}" />
																	<pg:param name="end_date" value="${end_date}" />
																	<pg:param name="start_date" value="${start_date}" />
																	<c:if test="${current!=1}">
																		<pg:first>
																			<li class="prev"><a href="${pageUrl}"
																				class="prev">首页</a></li>
																		</pg:first>
																	</c:if>
																	<pg:prev>
																		<li class="prev"><a href="${pageUrl}"
																			class="prev">上一页</a></li>
																	</pg:prev>
																	<pg:pages>
																		<c:choose>
																			<c:when test="${pageNumber == current}">
																				<li class="active"><a href="#">${pageNumber}</a></li>
																			</c:when>
																			<c:otherwise>
																				<li><a href="${pageUrl}">${pageNumber}</a></li>
																			</c:otherwise>
																		</c:choose>
																	</pg:pages>
																	<pg:next>
																		<li class="prev"><a href="${pageUrl}"
																			class="next">下一页</a></li>
																	</pg:next>
																	<c:if test="${current!=pm.totalPage&&pm.totalPage>1}">
																		<pg:last>
																			<li class="prev"><a href="${pageUrl}"
																				class="next">尾页</a></li>
																		</pg:last>
																	</c:if>

																</pg:pager>
															</ul>
														</div>
													</div>
												</td>
											</tr>
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
	<script src="${basePath}static/js/admincps/advertise/good_advertise.js"></script>
</body>
<!--  /Body -->
</html>
