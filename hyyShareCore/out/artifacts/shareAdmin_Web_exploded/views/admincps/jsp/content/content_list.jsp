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
									<div class="widget-body">
										<div role="grid" id="simpledatatable_wrapper" class="dataTables_wrapper form-inline no-footer">
										   <c:if test="${!empty permitFBtn}">
											<div class="DTTT btn-group">
												<div class="buttons-preview">
				                                    <div class="btn-group no-margin-right">
				                                        <c:forEach var="btn" items="${permitFBtn}">
				                                         <button type="button" class="btn btn-default" id="${btn.btnId}"><i class="${btn.icon}"></i> ${btn.resourceName}</button>
				                                        </c:forEach>
				                                        <input type="hidden" value="${addtype}" name="addtype" id="addtype" />
				                                        <input type="hidden" value="${mypath}" name="mypath" id="mypath" />
			                                        </div>
				                                </div>
											</div>
	                                       </c:if>
											
											<form id="searchForm" action="${basePath}backstage/content/index" method="post">
											<div id="simpledatatable_filter" class="dataTables_filter">
												<label class="no-margin-left">
													<input type="text" name="queryParam" id="queryParam"placeholder="请输入查询关键词" class="form-control input-sm" value="${queryParam}"/>
													<select name="contentType" id="contentType" class="form-control input-sm">
	                                                <option  value="">文章类型</option>
													  <c:if test="${!empty typeMap}">
													    <c:forEach items="${typeMap}" var="dp">
													       <option value="${dp.state}" <c:if test="${dp.state == contentType}">selected="selected"</c:if> >${dp.name}</option>
													    </c:forEach>
													  </c:if>
													</select>
													<select name="checkState" id="checkState" class="form-control input-sm">
	                                                <option  value="">审核状态</option>
													  <c:if test="${!empty stateMap}">
													    <c:forEach items="${stateMap}" var="dp">
													       <option value="${dp.state}" <c:if test="${dp.state == checkState}">selected="selected"</c:if> >${dp.name}</option>
													    </c:forEach>
													  </c:if>
													</select>
													<select name="weightState" id="weightState" class="form-control input-sm">
	                                                <option  value="">文章属性</option>
													  <c:if test="${!empty weightMap}">
													    <c:forEach items="${weightMap}" var="dp">
													       <option value="${dp.key}" <c:if test="${dp.key == weightState}">selected="selected"</c:if> >${dp.value}</option>
													    </c:forEach>
													  </c:if>
													</select>
													<input class="input-sm Wdate" style="width:100px;" type="text" name="start_date"
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
												       <c:if test="${!empty permitTBtn}">
														<th class="text-center">
															操 作
														</th>
														</c:if>
														<th class="text-center" style="width:60px;">
															编号
														</th>
														<th class="text-center" style="width:60px;">
															类型
														</th>
														<th class="text-center" style="width:120px;">
															缩图
														</th>
														<th>
															标题
														</th>
														<th class="text-center">
															栏目
														</th>
														<th class="text-center">
															发布人
														</th>
														<th class="text-center">
															审核状态
														</th>
														<th class="text-center">
															文章属性
														</th>
														<th class="text-center">
															阅读数(虚)
														</th>
														<th class="text-center">
															点赞数(虚)
														</th>
														<th class="text-center">
															阅读数(实)
														</th>
														<th class="text-center">
															分享数(实)
														</th>
														<th class="text-center">
															阅读奖励(元)
														</th>
														<th class="text-center">
															好友分享(元)
														</th>
														<th class="text-center">
															朋友圈分享(元)
														</th>
														<th class="text-center">
															排序
														</th>
														<th>
															创建时间
														</th>
													</tr>
												</thead>
												<tbody>
												<c:if test="${!empty pm}">
												   <c:forEach var="dp" varStatus="status" items="${pm.results}">
													<tr>
														<td>
															<label>
					                                            <input type="checkbox" name="ck" id="ck_${dp.contentId}" value="${dp.contentId}">
					                                            <span class="text"></span>
					                                        </label>
														</td>
														<c:if test="${!empty permitTBtn}">
														<td class="text-center" style="width:60px;">
														<c:forEach var="btn" items="${permitTBtn}">
														    <a href="javascript:${btn.btnFun}('${dp.contentId}');" id="${btn.btnId}" title="${btn.resourceName}"><i class="${btn.icon} sz14"></i></a>
														</c:forEach>
														</td>
														</c:if>
														<td class="text-center">${dp.contentId}</td>
														<td class="text-center">${dp.contentTypeName}</td>
														<td class="text-center">
														<img width="100px;" height="60px;"  src="${empty dp.contentPicLink ? dp.contentPic : dp.contentPicLink}" />
														</td>
														<td style="width:240px;white-space:normal">${dp.contentTitle}</td>
														<td class="text-center">${dp.columnInfo.columnName}</td>
														<td class="text-center">${dp.userInfo.loginAccount}</td>
														<td class="text-center">
														<c:if test="${dp.checkState==1}">
														<span class="label label-success">${dp.checkStateName}</span>
														</c:if>
														<c:if test="${dp.checkState==0}">
														<span class="label label-danger">${dp.checkStateName}</span>
														</c:if>
														</td>
														<td class="text-center">${dp.homeTopedName}</td>
														<td class="text-center">${dp.visualReadNum}</td>
														<td class="text-center">${dp.clickNum}</td>
														<td class="text-center">${dp.readNum}</td>
														<td class="text-center">${dp.shareNum}</td>
														<td class="text-center">${dp.readAward}</td>
														<td class="text-center">${dp.friendShareAward}</td>
														<td class="text-center">${dp.timelineShareAward}</td>
														<td class="text-center">${dp.orderNo}</td>
														<td>
														<fmt:formatDate value="${dp.createTime}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>  
														</td>
													</tr>
													
													</c:forEach>
													</c:if>
													
													<c:if test="${empty pm.results}">
													<tr><td colspan="25" align="center" style="font-size:14px;color:red">很抱歉,没有查询到您要的数据</td></tr>
													</c:if>
													
													<c:if test="${!empty pm.results}">
													<tr>
													   <td colspan="25">
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
																	<pg:pager url="${basePath}backstage/content/index"
																		items="${pm.totalRecord}" 
																		export="current=pageNumber"
																		maxIndexPages="5" 
																		maxPageItems="${pm.pageSize}">
															         <pg:param name="totalRecord" value="${pm.totalRecord}" />
															         <pg:param name="queryParam" value="${queryParam}" />
															         <pg:param name="checkState" value="${checkState}" />
															         <pg:param name="contentType" value="${contentType}" />
															         <pg:param name="end_date" value="${end_date}" />
															         <pg:param name="start_date" value="${start_date}" />
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
		<script src="${basePath}static/js/admincps/content/content.js"></script>
	</body>
	<!--  /Body -->
</html>
