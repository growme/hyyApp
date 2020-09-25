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
				<!-- Page Body -->
				<div class="page-body">
                       <div class="row">
                        <div class="col-xs-12 col-md-12">
                            <div class="widget">
                                <div class="widget-header bordered-bottom bordered-azure">
                                    <i class="widget-icon fa fa-calendar"></i>
                                    <span class="widget-caption">${CT_MENU_NAV}</span>
                                    <div class="widget-buttons">
										<a href="#" data-toggle="maximize">
										   <i class="fa fa-expand" title="全屏展示"></i>
										</a>
										<a href="#" data-toggle="collapse"> 
										   <i class="fa fa-minus"  title="展开缩小"></i> 
										</a>
									</div>
                                </div>
                                <!--Widget Header-->
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
										<form id="searchForm" name="searchForm" action="${basePath}backstage/cash/index" method="post">
										<div id="simpledatatable_filter" class="dataTables_filter1">
											<label class="no-margin-left">
										        <input type="text" name="queryParam" id="queryParam"placeholder="请输入查询关键词" class="form-control input-sm" value="${queryParam}"/>
												<select name="state" id="state" class="form-control input-sm">
                                                 <option  value="">选择状态</option>
												  <c:if test="${!empty stateList}">
												    <c:forEach items="${stateList}" var="dp">
												       <option value="${dp.payStateId}" <c:if test="${dp.payStateId eq state && !empty state }">selected="selected"</c:if> >${dp.payStateName}</option>
												    </c:forEach>
												  </c:if>
												 </select>
												&nbsp;时间: <input class="input-sm Wdate" style="width:100px;" type="text" name="start_date"
												  id="start_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${start_date}"/>
												   至
												 <input class="input-sm Wdate" style="width:100px;" type="text" name="end_date"
												  id="end_date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${end_date}"/>
												<button type="submit" id="sb" class="btn btn-default searchBtn"><i class="fa fa-search"></i>查询</button>
											</label>
										</div>
										</form>
										
										<div class="table-scrollable">
										<table class="table table-striped table-bordered table-hover" id="simpledatatable" >
											<thead>
												<tr role="row">
													<th style="width:30px;">
														<label>
				                                            <input type="checkbox" name="ckall" onclick="selectAll('ckall','ck');">
				                                            <span class="text"></span>
				                                        </label>
													</th>
													<th style="width:50px;" class="text-center">序 号</th>
													<c:if test="${!empty permitTBtn}">
													<th class="text-center">
														操 作
													</th>
													</c:if>
													<th class="text-center">会员ID</th>
													<th class="text-center">会员账号</th>
													<th class="text-center">上级账号</th>
													<th class="text-center">收款账号</th>
													<th class="text-center">账号姓名</th>
													<th class="text-center">提现金额</th>
													<th class="text-center">提现方式</th>
													<th class="text-center">提现状态</th>
													<th class="text-center">备注信息</th>
													<th class="text-center">申请时间</th>
												</tr>
											</thead>
											<tbody>
											<c:if test="${!empty pm}">
											   <c:forEach var="dp" varStatus="status" items="${pm.results}">
												<tr>
													<td>
														<label>
				                                            <input type="checkbox" name="ck" id="ck_${dp.ucId}" value="${dp.ucId}">
				                                            <span class="text"></span>
				                                        </label>
													</td>
													<td class="text-center">${dp.ucId}</td>
													<c:if test="${!empty permitTBtn}">
													<td class="text-center" style="width:60px;">
													<c:forEach var="btn" items="${permitTBtn}" varStatus="st">
													  <a href="javascript:${btn.btnFun}('${dp.ucId}_${dp.state}');" id="${btn.btnId}" title="${btn.resourceName}" class="btn btn-default btn-xs"><i class="${btn.icon}"></i>${btn.resourceName}</a>
													</c:forEach>
													</td>
													</c:if>
													<td class="text-center">${dp.memberInfo.memberId}</td>
													<td class="text-center">${dp.memberInfo.loginAccount}</td>
													<td class="text-center">${dp.parentInfo.loginAccount}</td>
													<td class="text-center">${dp.payAccount}</td>
													<td class="text-center">${dp.accountName}</td>
													<td class="text-center">${dp.cmoney}</td>
													<td class="text-center">${dp.typeName}</td>
													<td class="text-center"><span class="label label-${dp.showColor}">${dp.stateName}</span></td>
													<td class="text-center">${dp.remark}</td>
												    <td class="text-center"><fmt:formatDate value="${dp.updateTime}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/></td>
													
												</tr>
												
												</c:forEach>
												</c:if>
												
												<c:if test="${empty pm.results}">
												<tr><td colspan="15" align="center" style="font-size:14px;color:red">很抱歉,没有查询到您要的数据</td></tr>
												</c:if>
												
												<c:if test="${!empty pm.results}">
												<tr>
												   <td colspan="15">
														<div class="col-sm-6 no-padding">
															<div class="dataTables_info" id="simpledatatable_info">
																<font size="2">共</font>
																<c:out value="${pm.totalPage}"/>
																<font size="2">页 &nbsp;&nbsp;当前第</font>
																<c:out value="${pm.pageNum}"/>
																<font size="2">页&nbsp;&nbsp; 共</font>
																<c:out value="${pm.totalRecord}"/>
																<font size="2">条记录</font>&nbsp;&nbsp;
															</div>
														</div>
														
														<div class="col-sm-6 no-padding">
															<div class="dataTables_paginate paging_bootstrap" id="simpledatatable_paginate">
																<ul class="pagination">
																	<pg:pager url="${basePath}backstage/cash/index"
																		items="${pm.totalRecord}" 
																		export="current=pageNumber"
																		maxIndexPages="5" 
																		maxPageItems="${pm.pageSize}">
															           <pg:param name="totalRecord" value="${pm.totalRecord}" />
															           <pg:param name="queryParam" value="${queryParam}" />
															           <pg:param name="end_date" value="${end_date}" />
														               <pg:param name="start_date" value="${start_date}" />
														               <pg:param name="state" value="${state}" />
															      <c:if test="${current!=1}">
																	 <pg:first>
																	 <li class="prev">
																		<a href="${pageUrl}" class="prev">首页</a>
																	 </li>
																	 </pg:first>
															      </c:if>
																  <pg:prev>
																     <li class="prev">
																        <a href="${pageUrl}" class="prev">上一页</a>
																     </li>
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
											    		            <li class="prev">
																	   <a href="${pageUrl}" class="next">下一页</a>
																	</li>
											    		        </pg:next>
																<c:if test="${current!=pm.totalPage && pm.totalPage>1}">
																    <pg:last>
																      <li class="prev">
																		<a href="${pageUrl}" class="next">尾页</a>
																	  </li>
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
								</div><!--Widget Body-->
                            </div>
                        </div>
                    </div>

				</div>
				<!-- /Page Body -->
			</div>
			<!-- /Page Content -->
		</div>
		<!-- /Page Container -->
		<!-- Main Container -->

	</div>
	<%@ include file="../../../common/com_js.jsp"%>
	<script src="${basePath}static/js/admincps/money/user_cash_list.js"></script>
</body>
<!--  /Body -->
</html>
