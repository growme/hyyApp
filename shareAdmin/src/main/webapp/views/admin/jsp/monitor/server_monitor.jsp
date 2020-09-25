<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>${CT_MENU_NAV}</title>
<meta name="description" content="${CT_MENU_NAV}"/>
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
                <div class="page-body margin-5">
                <div class="row">
                    <div class="col-xs-12 col-md-12">
                        <div class="widget">
                            <div class="widget-header">
                                <span class="widget-caption">
                                <i class="fa fa-tachometer"></i> JVM/CPU/物理内存 实时监控
                                </span>
                            </div>
                            <div class="widget-body">
                               <table style="width: 100%;">
								<tr>
									<td width="33.3%"><div id="main_one" class="chartcontainer" style="height: 240px;"></div></td>
									<td width="33.3%"><div id="main_two" class="chartcontainer" style="height: 240px;"></div></td>
									<td width="33.3%"><div id="main_three" class="chartcontainer" style="height: 240px;"></div></td>
								</tr>
							   </table>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                 <div class="col-xs-12 col-md-6 padding-right-5">
                     <div class="widget no-margin no-padding">
                         <div class="widget-header">
                             <span class="widget-caption">
                             <i class="fa fa-bar-chart-o"></i> JVM/CPU/物理内存 实时监控
                             </span>
                         </div>
                         <div class="widget-body">
							<div class="chartcontainer" id="main" style="width: 100%;height:350px;"></div>
                         </div>
                     </div>
                  </div>
                    
                  <div class="col-xs-12 col-md-6 padding-left-5">
                        <div class="widget no-margin no-padding">
                            <div class="widget-header">
                                <span class="widget-caption"> <i class="fa fa-desktop"></i> 服务器信息</span>
                            </div>
                            <div class="widget-body no-margin">
                            <div class="chartcontainer" style="width: 100%;height:350px;">
	                           <table class="table table-striped table-bordered" style="width: 100%;">
	                                <thead>
	                                  <tr>
										<th width="140;">&nbsp;&nbsp;属性名</th>
										<th>属性值</th>
									  </tr>
									</thead>
									<tr>
										<td >&nbsp;&nbsp;服务器IP地址</td>
										<td>${serverInfo.hostAddress}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;服务器主机名</td>
										<td>${serverInfo.hostName}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;操作系统的名</td>
										<td>${serverInfo.osName}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;操作系统构架</td>
										<td>${serverInfo.osArch}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;操作系统版本</td>
										<td>${serverInfo.osVersion}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;处理器个数</td>
										<td>${serverInfo.availableProcessors}个</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;JAVA运行版本</td>
										<td>${serverInfo.javaVersion}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;JAVA供应商</td>
										<td><a href="${serverInfo.javaVendorUrl}" target="_blank">${serverInfo.javaVendor}</a></td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;JAVA的安装路径</td>
										<td>${serverInfo.javaHome}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;临时文件路径</td>
										<td>${serverInfo.javaIOTmpdir}</td>
									</tr>
									<tr>
										<td >&nbsp;&nbsp;内存使用情况</td>
										<td>
										JVM内存剩余：<span id="td_jvmUsage" class="label label-primary">${serverInfo.jvmFree}</span>MB 可用 &nbsp; 
										物理内存剩余：<span id="td_serverUsage" class="label label-success">${serverInfo.memFree}</span>GB可用  &nbsp;
										CPU已使用：<span id="td_cpuUsage" class="label label-danger">50</span>% 
										</td>
									</tr>
								</table>
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
<!--Page Related Scripts-->
<script src="${basePath}static/js/charts/echarts/echarts-all.js"></script>
<script src="${ccnetpath}/static/js/admin/monitor/monitor.js"></script>
</body>
<!--  /Body -->
</html>
      