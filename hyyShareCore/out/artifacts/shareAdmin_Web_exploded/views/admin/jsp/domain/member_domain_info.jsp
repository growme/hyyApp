<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>${CT_MENU_NAV}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../../../common/includes.jsp"%>
<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="padding-top-10">
<div class="col-lg-6 col-sm-6 col-xs-12">
    <div class="widget">
        <div class="widget-body">
            <div id="horizontal-form">
                <form class="form-horizontal" id="personalForm">
                    <input type="hidden" id="domainId" name="domainId" class="form-control" value="${domainId}">
                    <div class="row">
                        <div class="col-md-6">
                            <select name="memeberId" id="memeberId" style="width:100%;">
                              <option value="">域名类型</option>
							  <c:if test="${!empty memberList}">
							    <c:forEach items="${memberList}" var="dp">
							       <option value="${dp.memeberId}">${dp.loginAccount}----(${dp.userName})</option>
							    </c:forEach>
							  </c:if>
							</select>
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                        <textarea style="width:100%;height:80px;" id="remark" name="remark" placeholder="请输入备注信息"></textarea>
                        <div class="horizontal-space"></div>
                        </div>
                    </div>
                    
                    <div>
                        <button type="button" class="btn btn-primary" id="savePersonalBtn">
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
