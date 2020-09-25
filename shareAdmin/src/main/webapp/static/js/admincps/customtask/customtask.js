//JavaScript Document
$(function() {

	// 点击增加按钮
	$("#th_add_btn").click(
			function() {
				openPage("增加字典参数", 600, 510, ccnetpath
						+ "/backstage/customtask/goadd");
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
				openPage("编辑自定义任务", 600, 510, ccnetpath
						+ "/backstage/customtask/goedit?customtask_id=" + ids);
			});

	// 点击删除按钮
	$("#th_del_btn").click(function() {
		delTask();
	});

	// 关闭窗口
	$("#closeBtn").click(function() {
		closeFrame(false);
	});

	// 保持字典
	$("#saveBtn").click(function() {
		var customtaskId = $("#id").val();
		var status = $("#status").val();
		var editmode = $("#editMode").val();

		if (isEmpty($("#title").val())) {
			showErrMsg("对不起！标题不能为空!");
			return false;
		}

		if (isEmpty($("#integral").val())) {
			showErrMsg("对不起，奖励名称不能为空!");
			return false;
		}

		var reqUrl = "/backstage/customtask/save";
		if (!isEmpty(customtaskId)) {
			reqUrl = "/backstage/customtask/edit";
		}

		// 判断演示模式
		if (isDemoMode(demo_mode)) {
			showErrMsg("对不起！演示模式下不能进行此操作!");
			return false;
		}

		// 提交表单
		var datas = $("#customtaskForm").serialize();
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

	});

});

function delTask(id) {
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

	var param = "id=" + ids + "&now=" + new Date().getTime();
	showConFirm('您确定的要删除吗？', function() {
		var index = showLoading();
		$.ajax({
			type : "POST",
			cache : false,
			url : ccnetpath + "/backstage/customtask/trash",
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

function editTask(id) {
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
	openPage("编辑自定义任务", 600, 510, ccnetpath
			+ "/backstage/customtask/goedit?customtask_id=" + ids);
}
$("#state_CK").change(function() {
	if ($("#state_CK").is(":checked")) {
		$("#status").val(0);
	} else {
		$("#status").val(1);
	}
});
