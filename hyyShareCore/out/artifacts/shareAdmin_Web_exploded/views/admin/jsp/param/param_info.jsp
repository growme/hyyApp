<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>参数管理</title>
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
               <input type="hidden" id="paramId" name="paramId" class="form-control" value="${paramInfo.paramKey}">
                <form class="form-horizontal" role="form" id="paramForm">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" id="paramKey" name="paramKey" <c:if test="${!empty paramInfo.paramKey}">readonly</c:if> placeholder="参数名" class="form-control" value="${paramInfo.paramKey}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <textarea class="form-control" id="paramValue" name="paramValue" rows="4" placeholder="参数值">${paramInfo.paramValue}</textarea>
                    <div class="horizontal-space"></div>
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" id="paramDesc" name="paramDesc" placeholder="备注信息" class="form-control" value="${paramInfo.paramDesc}">
                            <div class="horizontal-space"></div>
                        </div>
                    </div>
                    <div>
                        <button type="button" class="btn btn-primary" id="saveBtn">
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
<script src="${basePath}static/js/admin/param/param.js"></script>

</body>
</html>
