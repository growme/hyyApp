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


							<form id="searchForm" action="${basePath}backstage/visit/index"
								method="post">
								<div id="simpledatatable_filter" class="dataTables_filter">
									<label class="no-margin-left"> <input type="text"
										name="queryParam" id="queryParam" placeholder="文章 ID/访问 IP"
										class="form-control input-sm" value="${queryParam}" /> <input
										type="text" name="memberId" id="memberId"
										style="width: 100px;" placeholder="推广人ID"
										class="input-sm padding-left-5" value="${memberId}" /> <input
										type="text" name="loginAccount" id="loginAccount"
										style="width: 100px;" placeholder="推广账号"
										class="input-sm padding-left-5" value="${loginAccount}" /> <select
										name="accountState" id="accountState"
										class="form-control input-sm">
											<option value="">--计费状态--</option>
											<c:if test="${!empty stlist}">
												<c:forEach items="${stlist}" var="st">
													<option value="${st.stateId}"
														<c:if test="${st.stateId eq accountState && !empty accountState}">selected</c:if>>${st.stateName}</option>
												</c:forEach>
											</c:if>
									</select> <input class="input-sm Wdate" style="width: 150px;"
										type="text" name="start_date" id="start_date"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="${start_date}" /> 至 <input class="input-sm Wdate"
										style="width: 150px;" type="text" name="end_date"
										id="end_date"
										onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
										value="${end_date}" />
										<button type="submit" id="sb"
											class="btn btn-default searchBtn">
											<i class="fa fa-search"></i>查询
										</button>
									</label>
								</div>
							</form>

							<div class="table-scrollable">
								<table class="table table-striped table-bordered table-hover"
									id="simpledatatable">
									<thead>
										<tr role="row">
											<th>日志编号</th>
											<th>文章编号</th>
											<th>文章标题</th>
											<th>记账状态</th>
											<th>推广账号ID</th>
											<th>推广账号</th>
											<th>访问渠道</th>
											<th>阅读奖励</th>

											<th>滑动(次)</th>
											<th>晃动(次)</th>
											<th>展开(次)</th>
											<th>展开按钮</th>
											<th>是否返回广告页面</th>
											<th>微信访问</th>
											<th>停留时间(秒)</th>
											<th>心跳次数</th>
											<th>访问IP</th>
											<th>IP归属地</th>
											<th>OpenId</th>
											<th>时间记录</th>
											<!-- <th>
														   访问指纹
														</th> -->
											<th>访问标记</th>
											<th>终端型号</th>
											<th>终端详情</th>
											<th>计费原因</th>
										</tr>
									</thead>
									<tbody>
										<c:if test="${!empty pm}">
											<c:forEach var="dp" varStatus="status" items="${pm.results}">
												<tr>
													<td class="text-center">${dp.visitId}</td>
													<td class="text-center">${dp.contentInfo.contentId}</td>
													<td>${fn:substring(dp.contentInfo.contentTitle, 0, 20)}...</td>
													<td><c:if test="${dp.accountState==0}">
															<span class="label label-danger" title="${dp.stateDesc}">${dp.stateName}</span>
														</c:if> <c:if test="${dp.accountState==1}">
															<span class="label label-success" title="${dp.stateDesc}">${dp.stateName}</span>
														</c:if></td>
													<td class="text-center">${dp.memberInfo.memberId}</td>
													<td class="text-center">${dp.memberInfo.loginAccount}</td>
													<td class="text-center">${dp.typeName}</td>
													<td class="text-center">${dp.contentInfo.readAward}</td>
													<td class="text-center">${dp.touchCount}</td>
													<td class="text-center">${dp.coordNum}</td>
													<td class="text-center">${dp.expandCount}</td>
													<td class="text-center"><c:if
															test="${dp.expandName==1}">点击阅读全文</c:if> <c:if
															test="${dp.expandName==2}">点击展开全文</c:if> <c:if
															test="${dp.expandName==3}">点击查看全文</c:if></td>
													<td class="text-center">${dp.goBack?'否':'是'}</td>
													<td class="text-center">${dp.wechatBrowser==0?'否':'是'}</td>
													<td class="text-center">${empty dp.aliveTime ? 0 : dp.aliveTime}</td>
													<td class="text-center">${empty dp.heartBeatCount ? 0 : dp.heartBeatCount}</td>
													<td>${dp.requestIp}<c:if
															test="${!empty dp.visitIPCounter.totalCount}">
													     (<a
																style="font-size: 14px; color: red; font-weight: 700"
																title="点击查看该IP所有记录"
																href="javascript:queryByReqIP('${dp.requestIp}');">${dp.visitIPCounter.totalCount}</a>)
														</c:if>
													</td>
													<td>${dp.ipLocation}</td>
													<td>${dp.openId}</td>
													<td>
														<ul>
															<c:if test="${!empty dp.pageReadTime}">
																<li>访问开始：<fmt:formatDate value="${dp.pageReadTime}"
																		type="both" pattern="yyyy-MM-dd HH:mm:ss" />
																</li>
															</c:if>
															<c:if test="${!empty dp.firstExpandTime}">
																<li>点击展开：<fmt:formatDate
																		value="${dp.firstExpandTime}" type="both"
																		pattern="yyyy-MM-dd HH:mm:ss" />
																</li>
															</c:if>
															<c:if test="${!empty dp.secondExpandTime}">
																<li>继续阅读：<fmt:formatDate
																		value="${dp.secondExpandTime}" type="both"
																		pattern="yyyy-MM-dd HH:mm:ss" />
																</li>
															</c:if>
															<c:if test="${!empty dp.accountTime}">
																<li style="margin-bottom: 2px; color: red">记账时间：<fmt:formatDate
																		value="${dp.accountTime}" type="both"
																		pattern="yyyy-MM-dd HH:mm:ss" />
																</li>
															</c:if>
															<c:if test="${!empty dp.lastHeartBeatTime}">
																<li style="margin-bottom: 2px; color: red">最后心跳：<fmt:formatDate
																		value="${dp.lastHeartBeatTime}" type="both"
																		pattern="yyyy-MM-dd HH:mm:ss" />
																</li>
															</c:if>
														</ul>
													</td>
													<%-- <td class="text-center">${dp.hashKey}</td> --%>
													<td class="text-center">${dp.visitToken}</td>
													<td class="text-center">${empty dp.mobileName?'iPhone':dp.mobileName}</td>
													<td style="width: 400px; white-space: normal">${dp.requestDetail}</td>
													<th><c:choose>
															<c:when test="${dp.cause=='0'}">待结算（未完成外部条件）</c:when>
															<c:when test="${dp.cause=='1'}">重复阅读</c:when>
															<c:when test="${dp.cause=='2'}">IP重复访问</c:when>
															<c:when test="${dp.cause=='3'}">OPENID重复访问</c:when>
															<c:when test="${dp.cause=='4'}">自己访问</c:when>
															<c:when test="${dp.cause=='5'}">扣量</c:when>
															<c:when test="${dp.cause=='9'}">完成</c:when>
															<c:otherwise>其他</c:otherwise>
														</c:choose></th>
												</tr>
											</c:forEach>
										</c:if>
										<c:if test="${empty pm.results}">
											<tr>
												<td colspan="20" align="center"
													style="font-size: 14px; color: red">很抱歉,没有查询到您要的数据</td>
											</tr>
										</c:if>


										<c:if test="${!empty pm.results}">
											<tr>
												<td colspan="20">
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
																<pg:pager url="${basePath}backstage/visit/index"
																	items="${pm.totalRecord}" export="current=pageNumber"
																	maxIndexPages="5" maxPageItems="${pm.pageSize}">
																	<pg:param name="totalRecord" value="${pm.totalRecord}" />
																	<pg:param name="queryParam" value="${queryParam}" />
																	<pg:param name="loginAccount" value="${loginAccount}" />
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
	<script src="${basePath}static/js/admincps/visit/visit.js"></script>

</body>
<!--  /Body -->
</html>
