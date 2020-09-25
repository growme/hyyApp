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


	<!-- Main Container -->
	<div class="main-container container-fluid">
		<!-- Page Container -->
		<div class="page-container">
			<!-- Page Content -->
			<!-- Page Body -->
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<div class="widget">
						<div class="widget-header bordered-bottom bordered-azure">
							<i class="widget-icon fa fa-calendar"></i> <span
								class="widget-caption">${CT_MENU_NAV}</span>
							<div class="widget-buttons">
								<a href="#" data-toggle="maximize"> <i class="fa fa-expand"
									title="全屏展示"></i>
								</a> <a href="#" data-toggle="collapse"> <i class="fa fa-minus"
									title="展开缩小"></i>
								</a>
							</div>
						</div>
						<!--Widget Header-->
						<%-- <form action="${basePath}backstage/money/count/index" method="post">
										<div id="simpledatatable_filter" class="dataTables_filter1">
											<label class="no-margin-left">
										      <input type="text" name="memberName" id="memberName"placeholder="请输入会员登录账号" class="form-control input-sm" value="${memberName}"/> 
												&nbsp;时间: <input class="input-sm Wdate" style="width:100px;" type="text" name="start_date"
												  id="start_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${start_date}"/>
												   至
												 <input class="input-sm Wdate" style="width:100px;" type="text" name="end_date"
												  id="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${end_date}"/>
												<button type="submit" id="sb" class="btn btn-default searchBtn"><i class="fa fa-search"></i>查询</button>
											</label>
										</div>
										</form> --%>

						<div class="table-scrollable">
							<table class="table table-striped table-bordered table-hover"
								id="simpledatatable">
								<thead>
									<tr role="row">
										<th style="width: 50px;" class="text-center">序 号</th>
										<th>用户账号</th>
										<th>收益金额</th>
										<th>收益类型</th>
										<th style="width: 200px;">文章标题</th>
										<th>下线账号</th>
										<th>收益时间</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty pm}">
										<c:forEach var="dp" varStatus="status" items="${pm.results}">
											<tr>
												<td>${dp.umId}</td>
												<td>${dp.memberInfo.loginAccount}</td>
												<td><fmt:formatNumber value="${dp.umoney}"
														type="currency" pattern="0.000" /></td>
												<c:forEach var="tp" items="${awardTypes}">
													<c:if test="${tp.key==dp.mType}">
														<td><font color="">${tp.value}</font></td>
													</c:if>
												</c:forEach>
												<td>${fn:trim(dp.sbContentInfo.contentTitle)}</td>
												<td>${dp.invitedMemberInfo.loginAccount}</td>
												<td><fmt:formatDate value="${dp.createTime}"
														type="both" pattern="yyyy年MM月dd日 HH:mm:ss" /></td>
											</tr>
										</c:forEach>
									</c:if>

									<c:if test="${empty pm.results}">
										<tr>
											<td colspan="15" align="center"
												style="font-size: 14px; color: red">很抱歉,没有查询到您要的数据</td>
										</tr>
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
															<pg:pager url="${basePath}backstage/money/count/index"
																items="${pm.totalRecord}" export="current=pageNumber"
																maxIndexPages="5" maxPageItems="${pm.pageSize}">
																<pg:param name="totalRecord" value="${pm.totalRecord}" />
																<pg:param name="memberName" value="${memberName}" />
																<pg:param name="end_date" value="${end_date}" />
																<pg:param name="start_date" value="${start_date}" />
																<c:if test="${current!=1}">
																	<pg:first>
																		<li class="prev"><a href="${pageUrl}"
																			class="prev">首页</a></li>
																	</pg:first>
																</c:if>
																<pg:prev>
																	<li class="prev"><a href="${pageUrl}" class="prev">上一页</a></li>
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
																	<li class="prev"><a href="${pageUrl}" class="next">下一页</a></li>
																</pg:next>
																<c:if test="${current!=pm.totalPage && pm.totalPage>1}">
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
		<!-- /Page Body -->
	</div>
	<!-- /Page Content -->
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${basePath}static/js/admincps/money/money.js"></script>
</body>
<!--  /Body -->
</html>
