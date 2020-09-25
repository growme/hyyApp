//JavaScript Document
$(function() {

	// 点击增加按钮
	$("#th_add_btn").click(function() {
		openPage("增加系统参数", 400, 300, ccnetpath + "/backstage/param/goadd");
	});

	// 点击增加按钮
	$("#th_edit_btn").click(
			function() {
				var ids = getCKVal("ck");
				if (ids == null || ids.length == 0) {
					showErrMsg("请先选择要操作的数据！");
					return;
				}
				if (ids.split(",").length > 1) {
					showErrMsg("对不起！不能进行批量更新！");
					return;
				}
				openPage("编辑系统参数", 400, 300, ccnetpath
						+ "/backstage/param/goedit?param_key=" + ids);
			});

	// 点击删除按钮
	$("#th_del_btn").click(function() {
		delParam();
	});

	$("#closeBtn").click(function() {
		closeFrame(false);
	});

	// 保存字典
	$("#saveBtn").click(function() {
		saveParam();
	});

	// 网站设置
	$("#saveBaseSettingBtn").click(function() {
		saveBaseSetting();
	});

	// 域名设置
	$("#saveDomainSettingBtn").click(function() {
		saveDomianSetting();
	});

	// 下单设置设置
	$("#saveOrderSettingBtn").click(function() {
		saveOrderSetting();
	});

	// 客服设置设置
	$("#saveServerSettingBtn").click(function() {
		saveServerSetting();
	});

	// 其他设置设置
	$("#saveOtherSettingBtn").click(function() {
		saveOtherSetting();
	});

	$("#mySettingTab li").click(function() {
		if (this.id) {
			$("#flag").val(this.id.split('_')[1]);
		}
	});

	$("#CT_SEND_MAIL_CK").change(function() {
		if ($("#CT_SEND_MAIL_CK").is(":checked")) {
			$("#CT_SEND_MAIL").val(1);
		} else {
			$("#CT_SEND_MAIL").val(0);
		}
	});

	$("#PEPEAT_LOGIN_CODE_CK").change(function() {
		if ($("#PEPEAT_LOGIN_CODE_CK").is(":checked")) {
			$("#PEPEAT_LOGIN_CODE").val(1);
		} else {
			$("#PEPEAT_LOGIN_CODE").val(0);
		}
	});

	$("#CT_SEND_SMS_CK").change(function() {
		if ($("#CT_SEND_SMS_CK").is(":checked")) {
			$("#CT_SEND_SMS").val(1);
		} else {
			$("#CT_SEND_SMS").val(0);
		}
	});

	$("#MOBILE_CHECK_TYPE_CK").change(function() {
		if ($("#MOBILE_CHECK_TYPE_CK").is(":checked")) {
			$("#MOBILE_CHECK_TYPE").val(1);
		} else {
			$("#MOBILE_CHECK_TYPE").val(0);
		}
	});

	$("#MOBILE_REG_VOICE_CHECK_CK").change(function() {
		if ($("#MOBILE_REG_VOICE_CHECK_CK").is(":checked")) {
			$("#MOBILE_REG_VOICE_CHECK").val(1);
		} else {
			$("#MOBILE_REG_VOICE_CHECK").val(0);
		}
	});

	$("#MOBILE_PAY_VOICE_CHECK_CK").change(function() {
		if ($("#MOBILE_PAY_VOICE_CHECK_CK").is(":checked")) {
			$("#MOBILE_PAY_VOICE_CHECK").val(1);
		} else {
			$("#MOBILE_PAY_VOICE_CHECK").val(0);
		}
	});

});

// 修改check值
function changeCheckVal(cid) {
	if ($("#" + cid + "_CK").is(":checked")) {
		$("#" + cid).val(1);
	} else {
		$("#" + cid).val(0);
	}
}

/**
 * 处理基础参数提交
 */
function saveBaseSetting() {
	// 如果要开启限制登录，ip白名单不能为空
	if ($("#VALIDATE_LOGINIP_ENABLE_CK").is(":checked")) {
		if (isEmpty($("#VALIDATE_LOGINIP_ADDRESS").val())) {
			showTips("开启IP登录限制请先设置IP白名单!", "VALIDATE_LOGINIP_ADDRESS");
			$("#VALIDATE_LOGINIP_ADDRESS").focus();
			return false;
		}
	}

	// 如果要开启会员邀请功能 默认奖励分成为必填
	if ($("#USER_VISIT_LINK_CK").is(":checked")) {
		if (isEmpty($("#VISIT_REWARD_PERCENT").val())) {
			showTips("开启会员邀请功能请先设置 默认奖励分成值 如 0.05!", "VISIT_REWARD_PERCENT");
			$("#VISIT_REWARD_PERCENT").focus();
			return false;
		}
	}

	if (!isEmpty($("#VISIT_REWARD_PERCENT").val())) {
		if (!isMoney($("#VISIT_REWARD_PERCENT").val())) {
			showTips("默认奖励分成值设置错误，只能为小数！", "VISIT_REWARD_PERCENT");
			$("#VISIT_REWARD_PERCENT").focus();
			return false;
		}
	}

	// 提交表单
	showTConFirm('您确定的要提交吗？', function() {
		var index = showTLoading();
		$("#baseSettingForm").ajaxSubmit({
			type : 'POST',
			dataType : 'json',
			url : ccnetpath + "/backstage/param/setting/save",
			success : function(data) {
				if ("1" != data.res) {
					closeLayer(index);
					showTErrMsg(data.resMsg);
				} else {
					showTSucMsg(data.resMsg);
					closeLayer(index);
					/*
					 * setTimeout(function(){
					 * window.top.location.href=ccnetpath+"/backstage/index";
					 * //window.top.location.href=ccnetpath+"/backstage/param/setting?flag="+$("#flag").val()+"&mod="+$("#mod").val();
					 * },500);
					 */
				}
			}
		});
	});
	return false;
}

/**
 * 处理域名参数提交
 */
function saveDomianSetting() {

	if (isEmpty($("#CT_ARTICLE_RECORD_TIME").val())) {
		showTips("请设置阅读文章计费开始时间！", "CT_ARTICLE_RECORD_TIME");
		$("#CT_ARTICLE_RECORD_TIME").focus();
		return false;
	}

	if (isEmpty($("#CT_ARTICLE_TOUCH_COUNT").val())) {
		showTips("请设置阅读文章滑动屏幕次数！", "CT_ARTICLE_TOUCH_COUNT");
		$("#CT_ARTICLE_TOUCH_COUNT").focus();
		return false;
	}

	if (isEmpty($("#CT_ARTICLE_EXPAND_COUNT").val())) {
		showTips("请设置阅读文章展开次数！", "CT_ARTICLE_EXPAND_COUNT");
		$("#CT_ARTICLE_EXPAND_COUNT").focus();
		return false;
	}

	if (isEmpty($("#CT_ARTICLE_EXPAND_TIME").val())) {
		showTips("请设置阅读文章展开间隔时间", "CT_ARTICLE_EXPAND_TIME");
		$("#CT_ARTICLE_EXPAND_TIME").focus();
		return false;
	}

	if (isEmpty($("#CT_PHONE_MOVE_COUNT").val())) {
		showTips("请设置阅读文章时手机晃动次数", "CT_PHONE_MOVE_COUNT");
		$("#CT_PHONE_MOVE_COUNT").focus();
		return false;
	}
	
	// 提交表单
	showTConFirm('您确定的要提交吗？', function() {
		var index = showTLoading();
		$("#domainSettingForm").ajaxSubmit(
				{
					type : 'POST',
					dataType : 'json',
					url : ccnetpath + "/backstage/param/setting/save",
					success : function(data) {
						if ("1" != data.res) {
							closeLayer(index);
							showTErrMsg(data.resMsg);
						} else {
							showTSucMsg(data.resMsg);
							setTimeout(function() {
								window.location.href = ccnetpath
										+ "/backstage/param/setting?flag="
										+ $("#flag").val() + "&mod="
										+ $("#mod").val();
							}, 500);
						}
					}
				});
	});
	return false;
}

/**
 * 处理下单参数提交
 */
function saveOrderSetting() {
	console.log(document.getElementsByName("REPEAT_DISTRICT"));
	if (isEmpty($("#CT_MEMBER_REGISTER_MONEY").val())) {
		showTips("请设置新人注册奖励红包金额！", "CT_MEMBER_REGISTER_MONEY");
		$("#CT_MEMBER_REGISTER_MONEY").focus();
		return false;
	}

	if (isEmpty($("#CT_RECOM_REGISTER_REWARD").val())) {
		showTips("请设置邀请注册上级奖励金额！", "CT_RECOM_REGISTER_REWARD");
		$("#CT_RECOM_REGISTER_REWARD").focus();
		return false;
	}

	if (isEmpty($("#CT_ARTICLE_READ_MONEY").val())) {
		showTips("请设置阅读文奖励金额！", "CT_ARTICLE_READ_MONEY");
		$("#CT_ARTICLE_READ_MONEY").focus();
		return false;
	}

	if (isEmpty($("#CT_ARTICLE_SHARE_MONEY").val())) {
		showTips("请设置分享文章奖励金额", "CT_ARTICLE_SHARE_MONEY");
		$("#CT_ARTICLE_SHARE_MONEY").focus();
		return false;
	}

	if (isEmpty($("#CT_PARENT_MONEY_PERCENT").val())) {
		showTips("请设置阅读文章上级奖励提成", "CT_PARENT_MONEY_PERCENT");
		$("#CT_PARENT_MONEY_PERCENT").focus();
		return false;
	}

	if (isEmpty($("#CT_MAX_DAILY_READ_MONEY").val())) {
		showTips("请设置日推广收益最大上限值", "CT_MAX_DAILY_READ_MONEY");
		$("#CT_MAX_DAILY_READ_MONEY").focus();
		return false;
	}
	var a = document.getElementsByName('REPEAT_district');
	var dist = '';
	for (var i = 0; i < a.length; i++) {
		if (a[i].checked == true) {
			if (dist == '') {
				dist = a[i].value;
			} else {
				dist = dist + "&&" + a[i].value;
			}
		}
	}
	$("#REPEAT_DISTRICT").val(dist);
	// 提交表单
	showTConFirm('您确定的要提交吗？', function() {
		var index = showTLoading();
		$("#orderSettingForm").ajaxSubmit(
				{
					type : 'POST',
					dataType : 'json',
					url : ccnetpath + "/backstage/param/setting/save",
					success : function(data) {
						if ("1" != data.res) {
							closeLayer(index);
							showTErrMsg(data.resMsg);
						} else {
							showTSucMsg(data.resMsg);
							setTimeout(function() {
								window.location.href = ccnetpath
										+ "/backstage/param/setting?flag="
										+ $("#flag").val() + "&mod="
										+ $("#mod").val();
							}, 500);
						}
					}
				});
	});
	return false;
}

/**
 * 处理图片参数提交
 */
function saveOtherSetting() {

	var site_logo = $("#SITE_LOGO").val();
	var site_ico = $("#SITE_ICO").val();
	var bsite_logo = $("#BACK_SITE_LOGO").val();
	var bsite_ico = $("#BACK_SITE_ICO").val();
	if (isEmpty(site_logo) && isEmpty(site_ico) && isEmpty(bsite_logo)
			&& isEmpty(bsite_ico)) {
		showTErrMsg("请至少选择一张需要上传的图片!");
		return false;
	}

	// 提交表单
	showTConFirm('您确定的要提交吗？', function() {
		var index = showTLoading();
		$("#otherSettingForm").ajaxSubmit(
				{
					type : 'POST',
					dataType : 'json',
					url : ccnetpath + "/backstage/param/setting/save",
					success : function(data) {
						if ("1" != data.res) {
							closeLayer(index);
							showTErrMsg(data.resMsg);
						} else {
							showTSucMsg(data.resMsg);
							setTimeout(function() {
								window.location.href = ccnetpath
										+ "/backstage/param/setting?flag="
										+ $("#flag").val() + "&mod="
										+ $("#mod").val();
							}, 500);
						}
					}
				});
	});
	return false;
}

/**
 * 处理客服参数提交
 */
function saveServerSetting() {

	// 提交表单
	showTConFirm('您确定的要提交吗？', function() {
		var index = showTLoading();
		$("#serverSettingForm").ajaxSubmit(
				{
					type : 'POST',
					dataType : 'json',
					url : ccnetpath + "/backstage/param/setting/save",
					success : function(data) {
						if ("1" != data.res) {
							closeLayer(index);
							showTErrMsg(data.resMsg);
						} else {
							showTSucMsg(data.resMsg);
							setTimeout(function() {
								window.location.href = ccnetpath
										+ "/backstage/param/setting?flag="
										+ $("#flag").val() + "&mod="
										+ $("#mod").val();
							}, 500);
						}
					}
				});
	});
	return false;
}

function saveParam() {

	var paramId = $("#paramId").val();
	var param_key = $("#paramKey").val();
	var param_val = $("#paramValue").val();
	var param_desc = $("#paramDesc").val();
	if (isEmpty(param_key)) {
		showErrMsg("对不起，请输入参数名!");
		return false;
	}

	if (isEmpty(param_val)) {
		showErrMsg("对不起，请输入参数值!");
		return false;
	}

	var reqUrl = "/backstage/param/save";
	if (!isEmpty(paramId) && paramId == param_key) {
		reqUrl = "/backstage/param/edit";
	}
	// 判断演示模式
	if (isDemoMode(demo_mode)) {
		showErrMsg("对不起！演示模式下不能进行此操作!");
		return false;
	}
	// 提交表单
	var datas = $("#paramForm").serialize();
	datas = datas + "&tm=" + new Date().getTime();
	showConFirm('您确定的要提交吗？', function() {
		var index = showLoading();
		$.ajax({
			type : 'POST',
			url : ccnetpath + reqUrl,
			data : datas,
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
					showErrMsg(data.resMsg);
					closeLayer(index);
				} else {
					showSucMsg(data.resMsg);
					closeFrame(true);
				}
			}
		});
	});

}

function delParam(id) {
	var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
	} else {
		ids = id;
	}
	// 判断演示模式
	if (isDemoMode(demo_mode)) {
		showErrMsg("对不起！演示模式下不能进行此操作!");
		return false;
	}

	var param = "param_id=" + ids + "&now=" + new Date().getTime();
	showConFirm('您确定的要删除吗？', function() {
		var index = showLoading();
		$.ajax({
			type : "POST",
			cache : false,
			url : ccnetpath + "/backstage/param/trash",
			data : param,
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
					showErrMsg(data.resMsg);
					closeLayer(index);
				} else {
					showSucMsg(data.resMsg);
					closeFrame(true);
				}
			}
		});
	});
}

function editParam(id) {
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
	openPage("编辑系统参数", 400, 300, ccnetpath
			+ "/backstage/param/goedit?param_key=" + ids);
}
