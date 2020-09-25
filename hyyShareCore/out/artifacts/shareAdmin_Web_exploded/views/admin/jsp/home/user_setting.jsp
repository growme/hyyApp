<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>用户设置</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="no-padding">
<div class="col-lg-12 col-sm-12 col-xs-12 no-padding">
    <div class="swidget">
        <div class="widget-body">
            <div class="tabbable">
                <ul class="nav nav-tabs tabs-flat" id="myTab">
                    <li class="active">
                        <a data-toggle="tab" href="#seting">基本信息 </a>
                    </li>
                    <li class="tab-red">
                        <a data-toggle="tab" href="#usericon">修改头像</a>
                    </li>
                </ul>
                
				<form role="form" id="userForm" action="" method="post">
                <div class="tab-content tabs-flat no-padding no-margin">
                    <div id="seting" class="tab-pane active">
                        <div id="registration-form">
				            	<table class="table table-striped table-bordered table-hover margin-top-5" id="simpledatatable" >
				            	   <tbody>
				            	    <tr>
									    <td align="right" width="90px;">
											登录账户：
										</td>
										<td>
				                            ${userInfo.loginAccount} 
										</td>
									</tr>
									<tr>
										<td align="right">
											用户名称：
										</td>
										<td>
										    <input type="hidden" class="form-control" id="userId" name="userId" value="${userInfo.userId}">
											<input type="text" class="form-control" id="userName" name="userName" placeholder="请输入用户名称" value="${userInfo.userName}">
										</td>
									</tr>
									<tr>
										<td align="right">
											用户昵称：
										</td>
										<td>
											<input type="text" class="form-control" id="nickName" name="nickName" placeholder="请输入用户昵称" value="${userInfo.nickName}">
										</td>
									</tr>
									
									<tr>
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
											<input type="text" id="phoneNumber" name="phoneNumber" class="form-control" placeholder="请输入用户联系电话" value="${userInfo.phoneNumber}">
										</td>
									</tr>
									
									<tr>
										<td align="right">
											QQ 号码：
										</td>
										<td>
										     <input type="text" id="qq" name="qq" class="form-control" placeholder="请输入用户QQ号码" value="${userInfo.qq}">
										</td>
									</tr>
									<tr>
										<td align="right">
											电子邮箱：
										</td>
										<td>
											<input type="text" id="email" name="email" class="form-control" placeholder="请输入用户email地址" value="${userInfo.email}">
										</td>
									</tr>
									</tbody>
								 </table>
				          </div>
                    </div>

                    <div id="usericon" class="tab-pane">
                    <div id="iconImg" style="overflow-y:auto;overflow-x:hidden;height:320px;">
                     <c:if test="${!empty avlist}">
				      <c:forEach items="${avlist}" var="dp">
                      <label class="padding-5">
                        <input type="radio" class="picUrlRadio" id="av_${dp.picValue}" <c:if test="${dp.picValue==userInfo.picUrl}">checked</c:if> name="picUrl" value="${dp.picValue}">
                        <span class="text"><img class="header-avatar1" src="${basePath}${dp.picUrl}"></span>
                      </label>
				      </c:forEach>
				     </c:if>
                    </div>
                    </div>
                </div>
               </form>
            </div>
            <div class="formBtn">
                 (<span class="red">修改后需要提交才能生效</span>)
                 <button type="button" class="btn btn-sky" id="saveSetBtn">
                   <i class="fa fa-save"></i>提 交
                 </button>
                <button type="button" class="btn btn-default" id="closeBtn">
                <i class="fa fa-power-off"></i>关 闭 
                </button>
            </div>
        </div>
    </div>
</div>

<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admin/home/home.js"></script>

</body>
</html>
