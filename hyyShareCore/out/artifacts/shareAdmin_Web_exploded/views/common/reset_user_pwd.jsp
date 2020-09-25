<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>重置密码</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="no-padding">
<div class="col-lg-12 col-sm-12 col-xs-12 no-padding">
    <div class="swidget">
        <div class="widget-body">
            <div id="registration-form">
             <form role="form" id="userForm" action="" method="post">
            	<table class="table table-striped table-bordered table-hover" id="simpledatatable" >
            	   <tbody>
					<tr>
						<td align="right">
							重置密码：
						</td>
						<td>
							<input type="password" class="form-control" id="loginPassword" name="loginPassword" placeholder="请设置账户登录密码" value="">
						</td>
					</tr>
					<tr>
						<td align="right">
							确认密码：
						</td>
						<td>
							<input type="password" class="form-control" id="loginPassword2" name="loginPassword2" placeholder="请再次输入账户登录密码" value="">
						</td>
					</tr>
					</tbody>
				 </table>
                 <div class="formBtn">
                      <c:if test="${empty optype}">
                    <button type="button" class="btn btn-sky" id="saveBtn">
                      <i class="fa fa-save"></i>提 交
                    </button>
                   </c:if>
                   <button type="button" class="btn btn-default" id="closeBtn">
                   <i class="fa fa-power-off"></i>关 闭 
                   </button>
               </div>
              </form>
          </div>
        </div>
    </div>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admincps/shop/shopuser.js"></script>

</body>
</html>
