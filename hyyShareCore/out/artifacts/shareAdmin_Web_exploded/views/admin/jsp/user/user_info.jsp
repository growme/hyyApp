<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>用户管理</title>
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
						<td align="right" width="90px">
							用户状态：
						</td>
						<td>
							<select name="userState" id="userState" style="width:100%;">
                              <option value="">选择状态</option>
							  <c:if test="${!empty stlist}">
							    <c:forEach items="${stlist}" var="dp">
							       <option value="${dp.type}" <c:if test="${dp.type==userInfo.userState}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
					    <td align="right">
							登录账户：
						</td>
						<td>
							<input type="text" class="form-control" id="loginAccount" name="loginAccount" placeholder="请设置用户登录账户" value="${userInfo.loginAccount}">
                            
						</td>
					</tr>
					<c:if test="${empty userInfo.userId}">
            	    <tr>
						<td align="right" width="90px">
							登录密码：
						</td>
						<td>
							<input type="password" class="form-control" id="loginPassword" name="loginPassword" placeholder="请输入账户登录密码" value="${userInfo.loginPassword}">
						</td>
						<td align="right">
							确认密码：
						</td>
						<td>
							<input type="password" class="form-control" id="loginPassword2" name="loginPassword2" placeholder="请再次输入登录密码" value="">
                            <div class="horizontal-space"></div>
						</td>
					</tr>
					</c:if>
					<tr>
						<td align="right">
							用户名称：
						</td>
						<td>
						    <input type="hidden" class="form-control" id="userId" name="userId" value="${userInfo.userId}">
							<input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名称" value="${userInfo.userName}">
						</td>
						<td align="right">
							用户昵称：
						</td>
						<td>
							<input type="text" class="form-control" id="nickName" name="nickName" placeholder="请输入用户昵称" value="${userInfo.nickName}">
						</td>
					</tr>
					<tr>
						<td align="right">
							用户类型：
						</td>
						<td>
						    <select name="userType" id="userType" style="width:100%;">
                             <option value="">选择类型</option>
							  <c:if test="${!empty tplist}">
							    <c:forEach items="${tplist}" var="dp">
							       <option value="${dp.type}" <c:if test="${dp.type==userInfo.userType}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
						<td align="right">
							用户性别：
						</td>
						<td>
						    <select name="sex" id="sex" style="width:100%;">
                              <option value="">选择性别</option>
							  <c:if test="${!empty sxlist}">
							    <c:forEach items="${sxlist}" var="dp">
							       <option value="${dp.type}" <c:if test="${dp.type==userInfo.sex}">selected</c:if>>${dp.name}</option>
							    </c:forEach>
							  </c:if>
							</select>
						</td>
					</tr>
					
					<tr>
					   <td align="right">
							联系电话：
						</td>
						<td>
							<input type="text" id="phoneNumber" name="phoneNumber" class="form-control" placeholder="请输入联系电话" value="${userInfo.phoneNumber}">
						</td>
						<td align="right">
							电子邮箱：
						</td>
						<td>
							<input type="text" id="email" name="email" class="form-control" placeholder="请输入电子邮箱" value="${userInfo.email}">
						</td>
					</tr>
					
					<tr>
						<td align="right">
						   QQ 号码：
						</td>
						<td>
						   <input type="text" id="qq" name="qq" class="form-control" placeholder="请输入QQ号码" value="${userInfo.qq}">
						</td>
						<td align="right">
							备注信息：
						</td>
						<td >
							<input type="text" id="userDesc" name="userDesc" class="form-control" placeholder="请输入备注信息" value="${userInfo.userDesc}">
						</td>
					</tr>
					
					<tr>
						<td align="right">
							授予角色：
						</td>
						<td colspan="3">
						     <c:if test="${!empty rlist}">
							    <c:forEach items="${rlist}" var="dp">
	                                <label>
	                                    <input type="checkbox" id="roleId_${dp.roleId}" <c:if test="${dp.useState}">checked</c:if> name="roleId" value="${dp.roleId}">
	                                    <span class="text">${dp.roleName}</span>
	                                </label>
							    </c:forEach>
							  </c:if>
						</td>
					</tr>
					
					<tr>
						<td align="right">
							选择主题：
						</td>
						<td colspan="3">
						     <c:if test="${!empty thlist}">
							    <c:forEach items="${thlist}" var="dp">
	                                <label class="padding-5">
	                                    <input type="radio" id="skin_${dp.codeValue}" <c:if test="${dp.codeValue==userInfo.skin}">checked</c:if> name="skin" value="${dp.codeValue}">
	                                    <span class="text">&nbsp;</span>
	                                    <span class="text badge badge-square" style="background-color:${dp.codeDesc};">&nbsp;</span>
	                                </label>
							    </c:forEach>
							  </c:if>
						</td>
					 </tr>
					 
					 <tr>
						<td align="right">
							选择头像：
						</td>
						<td colspan="3">
					     <c:if test="${!empty avlist}">
						    <c:forEach items="${avlist}" var="dp">
                                <label class="padding-5">
                                    <input type="radio" id="av_${dp.picValue}" <c:if test="${dp.picValue==userInfo.picUrl}">checked</c:if> name="picUrl" value="${dp.picValue}">
                                    <span class="text"><img class="header-avatar1" src="${basePath}${dp.picUrl}"></span>
                                </label>
						    </c:forEach>
						  </c:if>
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
<script src="${basePath}static/js/admin/user/user.js"></script>

</body>
</html>
