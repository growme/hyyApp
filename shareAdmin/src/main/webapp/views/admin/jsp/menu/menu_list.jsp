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
		<link href="${basePath}static/css/jquery.treetable.css" rel="stylesheet" type="text/css"/>
		<link href="${basePath}static/css/jquery.treetable.theme.default.css" rel="stylesheet" type="text/css"/>
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
	                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
	                            <div class="widget flat">
	                            <c:if test="${ADMIN_VERSION!='V5'}">
	                                <div class="widget-header bordered-bottom bordered-azure">
	                                    <i class="widget-icon fa fa-calendar"></i>
	                                    <span class="widget-caption">资源列表</span>
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
	                                <!--Widget Header-->
	                                <div class="widget-body">
	                                    <div class="widget-main">
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
											
											<form action="${basePath}backstage/resources/index" method="post" id="searchForm">
											<div id="simpledatatable_filter" class="dataTables_filter">
												<label class="no-margin-left">
													<input type="text" name="queryParam" id="queryParam"placeholder="请输入查询关键词" class="form-control input-sm" value="${queryParam}"/>
													<input type="text" style="width:180px;" class="form-control input-sm" id="parent_name" name="parent_name" 
													       placeholder="点击选择上级分类" onclick="showMenuTypeTree()" readonly="readonly" value="${parent_name}"> 
													<input type="hidden" id="parent_id" name="parent_id" value="${parent_id}">
													<div id="menuTypeTreeContent" class="menuContent subTreeMenu" style="width:180px;display:none; position: absolute">
														<ul id="menuTypeTree" class="ztree"></ul>
													</div>
													<select name="state" id="state" placeholder="资源状态" class="form-control input-sm">
	                                                <option value="">--所有状态--</option>
													  <c:if test="${!empty slist}">
													    <c:forEach items="${slist}" var="dp">
													       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==state}">selected</c:if>>${dp.valueDesc}</option>
													    </c:forEach>
													  </c:if>
													</select>
													<select name="menu_type" id="menu_type" placeholder="资源类型" class="form-control input-sm">
	                                                <option value="">--所有类型--</option>
													  <c:if test="${!empty tlist}">
													    <c:forEach items="${tlist}" var="dp">
													       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==menu_type}">selected</c:if>>${dp.valueDesc}</option>
													    </c:forEach>
													  </c:if>
													</select>
													
													<button type="submit" id="sb" class="btn btn-default searchBtn"><i class="fa fa-search"></i>查询</button>
												</label>
											</div>
											</form>
											
											<div class="table-scrollable">
											<table class="table table-striped table-bordered table-hover" id="simpledatatable" >
												<thead>
													<tr>
														<th>
														        资源编码
														</th>
														<c:if test="${!empty permitTBtn}">
														<th class="text-center">
															操 作
														</th>
														</c:if>
														<th>
															资源名称
														</th>
														<th>
															类型
														</th>
														<th>
															访问地址
														</th>
														<th>
															上级资源
														</th>
														<th>
															状态
														</th>
														<th>
															是否展开
														</th>
														<th>
															节点类型
														</th>
														<th>
															排序编号
														</th>
														<th>
															创建时间
														</th>
													</tr>
												</thead>
												<tbody>
												<c:if test="${!empty pm}">
												   <c:forEach var="dp" varStatus="status" items="${pm.results}">
													<tr data-tt-id="${dp.resourceCode}"<c:if test="${dp.parentCode!=0}"> data-tt-parent-id="${dp.parentCode}"</c:if> >
														
														<td width="160px;">
															${dp.resourceCode}
														</td>
														<c:if test="${!empty permitTBtn}">
														<td class="text-center" style="width:80px;">
														<c:forEach var="btn" items="${permitTBtn}">
														    <a href="javascript:${btn.btnFun}('${dp.resourceId}');" id="${btn.btnId}" title="${btn.resourceName}"><i class="${btn.icon}"></i></a>
														</c:forEach>
														</td>
														</c:if>
														<td>
															${dp.resourceName}
														</td>
														
														<td>
															${dp.typeName}
														</td>
														
														<td>
														    ${dp.resourceUrl}
														</td>
														
														<td>
														    ${dp.parentName}
														</td>
														
														<td>
															${dp.stateName}
														</td>
														
														<td>
															${dp.expandedName}
														</td>
														
														<td>
															${dp.leafName}
														</td>
														
														<td>
															${dp.orderNumber}
														</td>
														
														<td> 
															<fmt:formatDate value="${dp.createTime}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>  
														</td>
													</tr>
													
													</c:forEach>
													</c:if>
													
													
													<c:if test="${empty pm.results}">
													<tr><td colspan="12" align="center" style="font-size:14px;color:red">很抱歉,没有查询到您要的数据</td></tr>
													</c:if>
													
													<c:if test="${!empty pm.results}">
													  <tr>
													    <td colspan="12">
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
																	<pg:pager url="${basePath}backstage/resources/index"
																		items="${pm.totalRecord}" 
																		export="current=pageNumber"
																		maxIndexPages="5" 
																		maxPageItems="${pm.pageSize}">
															           <pg:param name="totalRecord" value="${pm.totalRecord}" />
															           <pg:param name="queryParam" value="${queryParam}" />
															           <pg:param name="menu_type" value="${menu_type}" />
															           <pg:param name="state" value="${state}" />
															           <pg:param name="parent_id" value="${parent_id}" />
															           <pg:param name="parent_name" value="${parent_name}" />
															           <pg:param name="pc" value="${pc}" />
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
																<c:if test="${current!=pm.totalPage&&pm.totalPage>1}">
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
	                                    </div>
	                                    <!--Widget Main Container-->
	                                </div><!--Widget Body-->
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
		<script src="${basePath}static/js/jquery/jquery.treetable.js"></script>
		<script src="${basePath}static/js/admin/menu/menu.js"></script>
		<script type="text/javascript">
		  $("#simpledatatable").treetable({ expandable: true });
		  $('#simpledatatable').treetable('expandAll');
       </script>
	</body>
	<!--  /Body -->
</html>
