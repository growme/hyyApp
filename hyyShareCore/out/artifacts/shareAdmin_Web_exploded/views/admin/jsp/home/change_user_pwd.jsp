<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>修改密码</title>
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
            	<table class="table table-bordered" id="simpledatatable" >
            	   <tbody>
            	   <tr>
						<tr>
						<td align="right">
							原始密码
						</td>
						<td>
						    <input type="hidden" id="userId" name="userId" value="${userId}">
							<input type="password" class="form-control" id="loginPassword" name="loginPassword" placeholder="请输入当前登录密码" value="">
						</td>
					</tr>
					</tr>
					<tr>
						<td align="right">
							新的密码
						</td>
						<td>
							<input type="password" class="form-control" id="newloginPassword" name="newloginPassword" placeholder="请设置新的登录密码" value="">
						</td>
					</tr>
					<tr>
						<td align="right">
							确认密码
						</td>
						<td>
							<input type="password" class="form-control" id="newloginPassword2" name="newloginPassword2" placeholder="请再次输入新的登录密码" value="">
						</td>
					</tr>
					</tbody>
				 </table>
                 <div class="formBtn">
                    <button type="button" class="btn btn-sky" id="changeBtn">
                      <i class="fa fa-save"></i>提 交
                    </button>
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
<script src="${basePath}static/js/admin/home/home.js"></script>
</body>
</html>
