<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/includes.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta charset="utf-8" />
		<title>${CT_MENU_NAV}</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		
		<%@ include file="../../../common/com_css.jsp"%>
        
	</head>
<body class="padding-top-10">
<div class="col-lg-6 col-sm-6 col-xs-12">
    <div class="widget">
        <div class="widget-body">
            <div id="horizontal-form">
                <form class="form-horizontal" role="form" id="parseDomainForm">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" id="domainName" name="domainName" placeholder="请输入派单备用的主域名 且已经泛解析(*) 不要www." class="form-control" value="${domainInfo.domainName}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="domainType" id="domainType" style="width:100%;">
                              <option value="">域名类型</option>
							  <c:if test="${!empty tlist}">
							    <c:forEach items="${tlist}" var="dp">
							       <option value="${dp.type}" <c:if test="${dp.type==domainInfo.domainType}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
							  
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="domainPrefix" id="domainPrefix" style="width:100%;">
                              <option value="">域名前缀</option>
                              <option value="0">纯数字</option>
                              <option value="1">纯字母</option>
                              <option value="2">二者混合</option>
							</select>
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="domainLength" id="domainLength" style="width:100%;">
                              <option value="">前缀长度</option>
                              <option value="8">8位字符</option>
                              <option value="10">10位字符</option>
                              <option value="20">20位字符</option>
                              <option value="32">32位字符</option>
							</select>
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <select name="domainNumber" id="domainNumber" style="width:100%;">
                              <option value="">生成个数</option>
                              <option value="2">2</option>
                              <option value="4">4</option>
                              <option value="6">6</option>
                              <option value="8">8</option>
                              <option value="10">10</option>
                              <option value="20">20</option>
                              <option value="30">30</option>
                              <option value="50">50</option>
							</select>
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div>
                        <button type="button" class="btn btn-primary" id="saveParseBtn">
                          <i class="fa fa-check"></i>提 交
                        </button>
                        <button type="button" class="btn btn-danger" id="closeBtn">
                          <i class="fa fa-power-off"></i>关 闭 
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admin/domain/domain.js"></script>

</body>
</html>
