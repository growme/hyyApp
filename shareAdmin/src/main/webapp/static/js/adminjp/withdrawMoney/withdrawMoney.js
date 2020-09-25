//JavaScript Document
$(function() {
	// 点击增加按钮
	$("#th_add_btn").click(function() {
		openPage("查看", 760, 540, ccnetpath + "/backstage/withdrawMoney/goadd");
	});

	$("#th_del_btn").click(function() {
		trashContent();
	});

	// 保存广告
	$("#saveEntity").click(function() {
		saveEntity();
	});

	// 生成脚本
	$("#add").click(
			function() {
				// 获取当前网址，如： http://localhost:9527/zdss-web/login/login.do
				var curWwwPath = window.document.location.href;
				// console.log("当前网址：" + curWwwPath);

				// 获取主机地址之后的目录，如：zdss-web/login/login.do
				var pathName = window.document.location.pathname;
				// console.log("当前路径：" + pathName);

				var pos = curWwwPath.indexOf(pathName);
				// console.log("路径位置：" + pos);

				// 获取主机地址，如： http://localhost:9527
				var localhostPath = curWwwPath.substring(0, pos);
				console.log("当前主机地址：" + localhostPath);
				var adId = $("#adId").val();
				var urlStr = localhostPath + ccnetpath
						+ "/backstage/notice/goadScript.js?adId=" + adId;
				var scriptStr = "<script type=\"text/javascript\" src=\""
						+ urlStr + "\"><\/script>";
				$("#adScript").val(scriptStr);
				$("#demojs").val(urlStr);
			});

});

// 查看
function chakan(id, type) {
	var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showTWarnMsg("请先选择要操作的数据！");
			return;
		}
		if (ids.split(",").length > 1) {
			showTWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	} else {
		ids = id;
	}
	openPage("查看", 760, 540, ccnetpath + "/backstage/withdrawMoney/goview?ids=" + ids);
}

// 修改广告
function editContent(id) {
	var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showTWarnMsg("请先选择要操作的数据！");
			return;
		}
		if (ids.split(",").length > 1) {
			showTWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	} else {
		ids = id;
	}
	openPage("修改", 760, 540, ccnetpath + "/backstage/withdrawMoney/goedit?ids=" + ids);
}

// 保存广告
function saveEntity() {
	if(isEmpty($("#name").val())){
	       showErrMsg("对不起，金额名称不能为空!");
	       return false;
	    }
	if(isEmpty($("#money").val())){
	       showErrMsg("对不起，金额数不能为空!");
	       return false;
	    }
	// 提交表单
	var reqUrl = "/backstage/withdrawMoney/save";
	showTConFirm('您确定的要提交吗？', function() {
		// alert($("#noticeForm").serialize());
		var index = showTLoading();
		$("#entityForm").ajaxSubmit({
			type : 'POST',
			dataType : 'json',
			url : ccnetpath + reqUrl,
			success : function(data) {
				if ("1" != data.res) {
					showTErrMsg(data.resMsg);
					closeLayer(index);
				} else {
					showTSucMsg(data.resMsg);
					closePFrame("/backstage/withdrawMoney/index", 500);
				}
			}
		});
	});
	return false;
}

function trashContent(id){
    var ids = "";
    if(isEmpty(id)){
     var ids = getCKVal("ck");
	 if(ids == null || ids.length==0){
		showWarnMsg("请先选择要操作的数据！");
		return;
	 }
    }else{
     ids = id;
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	var param = "id="+ids+"&now="+new Date().getTime();
	showConFirm('您确定的要删除吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/withdrawMoney/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/withdrawMoney/index",500);
	    			}
	    		}
	     });
	});
}
