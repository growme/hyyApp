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
											
											<form action="${basePath}backstage/user/index" method="post">
											<div id="simpledatatable_filter" class="dataTables_filter">
												<label class="no-margin-left">
													<input type="text" name="queryParam" id="queryParam"placeholder="请输入查询关键词" class="form-control input-sm" value="${queryParam}"/>
													<select name="state" id="state" placeholder="账号状态" class="form-control input-sm">
	                                                <option value="">--所有状态--</option>
													  <c:if test="${!empty slist}">
													    <c:forEach items="${slist}" var="dp">
													       <option value="${dp.codeValue}" <c:if test="${dp.codeValue==state}">selected</c:if>>${dp.valueDesc}</option>
													    </c:forEach>
													  </c:if>
													</select>
													<select name="user_type" id="user_type" placeholder="账号类型" class="form-control input-sm">
	                                                <option value="">--所有类型--</option>
													  <c:if test="${!empty tlist}">
													    <c:forEach items="${tlist}" var="dp">
													       <option value="${dp.type}" <c:if test="${dp.type==user_type}">selected</c:if>>${dp.name}</option>
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
														<th class="text-center" >
														   ID
														</th>
														<th class="text-center">
															状 态
														</th>
														<th class="text-center">
															帐号状态
														</th>
														<th>
															登录帐号
														</th>
														<th>
															用户类型
														</th>
														<th>
															用户角色
														</th>
														<th>
															店铺名称
														</th>
														<th>
															姓 名
														</th>
														<th>
															昵 称
														</th>
														<th class="text-center">
															性 别
														</th>
														<th>
															联系电话
														</th>
														<th>
															电子邮箱
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
					                                            <input type="checkbox" name="ck" id="ck_${dp.userId}" value="${dp.userId}">
					                                            <span class="text"></span>
					                                        </label>
														</td>
														
														<c:if test="${!empty permitTBtn}">
														<td class="text-center" style="width:80px;">
														<c:forEach var="btn" items="${permitTBtn}">
														    <a href="javascript:${btn.btnFun}('${dp.userId}');" 
														    id="${btn.btnId}" title="${btn.resourceName}"><i class="${btn.icon} sz14"></i></a>
														</c:forEach>
														</td>
														</c:if>
														
														<td class="text-center" >
															${dp.userId}
														</td>
														
														<td class="text-center">
															<c:if test="${dp.userState==0}">
															 <span class="label label-success">${dp.stateName}</span>
														    </c:if>
														    <c:if test="${dp.userState!=0}">
														    <span class="label label-danger">${dp.stateName}</span>
														    </c:if>
														</td>
														
														<td class="text-center">
														    <c:if test="${dp.passwordLocked}">
															 	<span class="label label-danger" title="密码错误次数过多，被锁定30分钟">登陆锁定</span>
														    </c:if>
														</td>
														
														<td>
															${dp.loginAccount}
														</td>
														
														<td>
															${dp.typeName}
														</td>
														
														<td>
															${dp.roleName}
														</td>
														
														<td>
															${dp.shopName}
														</td>
														
														<td>
															${dp.userName}
														</td>
														
														<td>
															${dp.nickName}
														</td>
														
														<td class="text-center">
														  <c:if test="${dp.sex==0}">
															 <i class="dropdown-icon fa fa-male Info"></i>
														    </c:if>
														    <c:if test="${dp.sex!=0}">
														    <i class="dropdown-icon fa fa-female danger"></i>
														  </c:if>
														</td>
														
														<td>
															${dp.phoneNumber}
														</td>
														
														<td>
														    ${dp.email}
														</td>
														
														<td> 
															<fmt:formatDate value="${dp.registerTime}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>  
														</td>
														
													</tr>
													
													</c:forEach>
													</c:if>
													
													
													<c:if test="${empty pm.results}">
													<tr><td colspan="12" align="center" style="font-size:14px;color:red">很抱歉,没有查询到您要的数据</td></tr>
													</c:if>
													
												</tbody>
											</table>
											
											<c:if test="${!empty pm.results}">
											<div class="row DTTTFooter">
												<div class="col-sm-6">
													<div class="dataTables_info" id="simpledatatable_info"
														role="alert" aria-live="polite" aria-relevant="all">
														<font size="2">共</font>
														<c:out value="${pm.totalPage}"/>
														<font size="2">页 &nbsp;&nbsp;当前第</font>
														<c:out value="${pm.pageNum}"/>
														<font size="2">页&nbsp;&nbsp; 共</font>
														<c:out value="${pm.totalRecord}"/>
														<font size="2">条记录</font>&nbsp;&nbsp;
													</div>
												</div>
												
												<div class="col-sm-6">
													<div class="dataTables_paginate paging_bootstrap" id="simpledatatable_paginate">
														<ul class="pagination">
															<pg:pager url="${basePath}backstage/user/index"
																items="${pm.totalRecord}" 
																export="current=pageNumber"
																maxIndexPages="5" 
																maxPageItems="${pm.pageSize}">
													           <pg:param name="totalRecord" value="${pm.totalRecord}" />
													           <pg:param name="queryParam" value="${queryParam}" />
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
											</div>
											</c:if>
											
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
