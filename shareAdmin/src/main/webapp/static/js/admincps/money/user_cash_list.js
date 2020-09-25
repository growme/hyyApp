//JavaScript Document
$(function() {

	// 关闭窗口
	$("#closeBtn").click(function() {
		closeFrame(false);
	});

	// 提交审核
	$("#saveCheckBtn").click(function() {
		saveCashCheck();
	});

	// 提交支付
	$("#savePayBtn").click(function() {
		savePayCash();
	});

	$("#state").change(function() {
		$("#searchForm").submit();
	});

	// 审核提现
	$("#th_check_btn").click(function() {
		checkUserCash();
	});

	// 提现支付
	$("#th_pay_btn").click(function() {
		payUserCash();
	});

	// 审核提现
	$("#th_money_btn").click(function() {
		checkUserMoney();
	});

});

function checkUserMoney(id) {
	var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if (ids.split(",").length > 1) {
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	} else {
		ids = id;
	}
	openPage("", 900, 600, ccnetpath + "/backstage/cash/goContentLog?cash_id=" + ids);
}

function checkUserCash(id) {
	var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if (ids.split(",").length > 1) {
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	} else {
		ids = id;
	}
	// 判断参数
	var cashId = id.split("_")[0];
	var state = id.split("_")[1];
	if (state == 2) {
		showWarnMsg("对不起，已支付的提现记录不能修改！");
		return;
	}
	openPage("提现审核", 400, 350, ccnetpath + "/backstage/cash/check?cash_id="
			+ ids);
}

function payUserCash(id) {
	var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if (ids.split(",").length > 1) {
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	} else {
		ids = id;
	}

	// 判断参数
	var cashId = id.split("_")[0];
	var state = id.split("_")[1];
	if (state != 1) {
		if (state == 2) {
			showWarnMsg("对不起，佣金已经支付过了！");
			return;
		} else {
			showWarnMsg("对不起，只有通过审核的提现才能支付！");
			return;
		}
	}

	openPage("提现支付", 380, 470, ccnetpath + "/backstage/cash/gopay?cash_id="
			+ ids);
}

function saveCashCheck() {

	if (isNull($("#state").val())) {
		showErrMsg("请选择提现审核状态!");
		return false;
	}

	if (isNull($("#remark").val())) {
		showErrMsg("请输入备注信息!");
		return false;
	}

	var param = "ucId=" + $("#ucId").val() + "&state=" + $("#state").val()
			+ "&remark=" + $("#remark").val() + "&now=" + new Date().getTime();
	showConFirm('您确定的要提交吗？', function() {
		var index = showLoading();
		$.ajax({
			type : "POST",
			cache : false,
			url : ccnetpath + "/backstage/cash/check/save",
			data : param,
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
					closeLayer(index);
					showTErrMsg(data.resMsg);
				} else {
					showTSucMsg(data.resMsg);
					closeFrame(true);
				}
			}
		});
	});
}

// 支付佣金
function savePayCash() {

	var cashId = $("#ucId").val()
	var payAccount = $("#payAccount").val();
	var accountName = $("#accountName").val();
	var serialCode = $("#serialCode").val();
	var payTime = $("#payTime").val();
	var remark = $("#remark").val();

	// if(isNull(payAccount)){
	// showErrMsg("支付账号不能为空!");
	// return false;
	// }
	//	
	// if(isNull(accountName)){
	// showErrMsg("支付账号姓名不能为空!");
	// return false;
	// }
	//	
	// if(isNull(serialCode)){
	// showErrMsg("支付流水号不能为空!");
	// return false;
	// }
	//	
	// if(isNull(payTime)){
	// showErrMsg("支付时间不能为空!");
	// return false;
	// }

	showTConFirm('您确定的要提交吗？', function() {
		var index = showLoading();
		$.ajax({
			type : "POST",
			cache : false,
			url : ccnetpath + "/backstage/cash/pay/save",
			data : {
				cashId : cashId,
				payAccount : payAccount,
				accountName : accountName,
				serialCode : serialCode,
				payTime : payTime,
				remark : remark,
				tm : new Date().getTime()
			},
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
					closeLayer(index);
					showTErrMsg(data.resMsg);
				} else {
					showTSucMsg(data.resMsg);
					closeFrame(true);
				}
			}
		});

	});
}