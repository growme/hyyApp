<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8" />
	<title>佣金结算</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@ include file="../../../common/includes.jsp"%>
	<%@ include file="../../../common/com_css.jsp"%>
</head>
<body class="no-padding">
<form role="form" id="moneyForm" action="" method="post" enctype="multipart/form-data">
<div class="col-lg-12 col-sm-12 col-xs-12 no-padding">
    <div class="swidget">
        <div class="widget-body">
            <div id="registration-form">
           	<table class="table table-striped table-bordered" id="simpledatatable" >
           	   <tbody>
           	    <tr>
				    <td align="right" width="90px;">
						提现用户：
					</td>
					<td>
					 <span class="red">${cashLog.memberInfo.loginAccount}</span>
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
						提现方式：
					</td>
					<td>
					<span class="red">${cashLog.typeName}</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						提现账户：
					</td>
					<td>
					<span class="red">${cashLog.payAccount}【${cashLog.accountName}】</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						打款账户：
					</td>
					<td>
						<input type="text" class="form-control" id="payAccount" name="payAccount" placeholder="请输入给用户打款的 支付宝/微信 账号" value="">
					</td>
				</tr>
				
				<tr>
					<td align="right">
						账户实名：
					</td>
					<td>
					    <input type="text" class="form-control" id="accountName" name="accountName"  placeholder="请输入打款账号对应的实名认证姓名" value="">
					</td>
				</tr>
				<tr>
					<td align="right">
						支付流水号：
					</td>
					<td>
						<input type="text" id="serialCode" name="serialCode" class="form-control" placeholder="请输入支付宝或者微信打款的交易流水号" value="">
					</td>
				</tr>
				
				<tr>
					<td align="right">
						付款时间：
					</td>
					<td>
					     <input class="Wdate form-control" type="text" name="payTime" id="payTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value=""/>
					</td>
				</tr>
				<tr>
					<td align="right">
						备注信息：
					</td>
					<td>
						<input type="text" id="remark" name="remark" class="form-control" placeholder="请输入必要的备注信息" value="">
					</td>
				</tr>
				</tbody>
			  </table>
            </div>
            <div class="formBtn">
                 <button type="button" class="btn btn-sky" id="savePayBtn">
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
