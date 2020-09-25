<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>提现审核</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="no-padding">
<form role="form" id="checkForm" name="checkForm" action="" method="post" enctype="multipart/form-data">
<div class="col-lg-12 col-sm-12 col-xs-12 no-padding">
    <div class="swidget">
        <div class="widget-body">
            <div id="registration-form">
           	<table class="table table-striped table-bordered" id="simpledatatable" >
           	   <tbody>
           	    <tr>
				    <td align="right" width="90px;">
						提现账户：
					</td>
					<td>
					 ${cashLog.memberInfo.loginAccount}
					</td>
				</tr>
				<tr>
					<td align="right">
						提现金额：
					</td>
					<td>
					   <span class="red"> <fmt:formatNumber value="${cashLog.cmoney}" type="currency" pattern="￥0.00"/></span>
					   <input type="hidden" id="ucId" name="ucId" value="${cashLog.ucId}">
					</td>
				</tr>
				<tr>
					<td align="right">
						申请时间：
					</td>
					<td>
					   <fmt:formatDate value="${cashLog.createTime}" type="both" pattern="yyyy年MM月dd日 HH:mm:ss"/>
					</td>
				</tr>
				<tr>
				<td align="right" width="90px">
					<span class="red">*</span>审核状态&nbsp;&nbsp;
				</td>
				<td>
					<select id="state" name="state" style="width:150px;">
				    <c:if test="${!empty stateList}">
				      <option value="">请选择...</option>
				      <c:forEach items="${stateList}" var="st">
				      <option value="${st.payStateId}" <c:if test="${st.payStateId eq cashLog.state }">selected="selected"</c:if>>${st.payStateName}</option>
				      </c:forEach>
				    </c:if>
				  </select>
				</td>
		     </tr>
			  <tr>
				<td align="right" width="90px;">
					 订单备注&nbsp;&nbsp;
				</td>
				<td>
				   <textarea id="remark" name="remark" placeholder="请输入订单备注信息"
			        rows="3" style="width:260px;"></textarea>
				</td>
			  </tr>
				</tbody>
			  </table>
            </div>
            <div class="formBtn">
                 <button type="button" class="btn btn-sky" id="saveCheckBtn">
                   <i class="fa fa-save"></i>提 交
                 </button>
                <button type="button" class="btn btn-default" id="closeBtn">
                <i class="fa fa-power-off"></i>关 闭 
                </button>
            </div>
        </div>
    </div>
</div>
</form>
<%@ include file="../../../common/com_js.jsp"%>
<script src="${basePath}static/js/admincps/money/user_cash_list.js"></script>
</body>
</html>
