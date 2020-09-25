//JavaScript Document
$(function () {
	
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加定时任务",800,350,ccnetpath+"/backstage/taskschedule/goadd");
	});
	
	
	//点击修改按钮
	$("#th_edit_btn").click(function () {
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showTErrMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showTErrMsg("对不起！不能进行批量更新！");
			return;
	    }
        openPage("修改定时任务",800,350,ccnetpath + "/backstage/taskschedule/goedit?task_id="+ids);
	});
	
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delTask();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存用户
	$("#saveBtn").click(function () {
		saveTask();
	});
	
});

function saveTask(){
	if(isEmpty($("#jobName").val())){
       showTErrMsg("对不起，请指定任务名称!");
       return false;
    }
	
    if(isEmpty($("#jobGroup").val())){
       showTErrMsg("对不起！请指定任务分组!");
       return false;
    }
    
    if(isEmpty($("#jobClass").val())){
       showTErrMsg("对不起，请指定执行类!");
       return false;
    }
    
    if(isEmpty($("#cronExpression").val())){
        showTErrMsg("对不起，配置定时任务表达式!");
        return false;
    }
    var reqUrl = "/backstage/taskschedule/save";
    if(!isEmpty($("#taskScheduleId").val())){
    	reqUrl = "/backstage/taskschedule/update";
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
    var datas = $('#taskForm').serialize();    
    datas = datas+"&tm="+new Date().getTime();
    showConFirm('您确定的要提交吗？',function(){
       var index = showTLoading();
       $.ajax({
   		type:'POST',
   		url:ccnetpath + reqUrl,
   		data:datas,
   		dataType:'json',
   		success:function(data, textStatus) {
   			if ("1" != data.res) {
   				showTErrMsg(data.resMsg);
   				closeLayer(index);
   			}else{
   				showTSucMsg(data.resMsg);
   				closePFrame("/backstage/taskschedule/index",500);
   			}
   		}
   	  });
    });
    
}

//修改用户
function editTask(id){
   var ids = "";
   if(isEmpty(id)){
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	  }else{
	    ids = id;
	  }
   openPage("修改任务",800,350,ccnetpath+"/backstage/taskschedule/goedit?task_id="+ids);
}

//查看任务
function seeTask(id){
   var ids = "";
   if(isEmpty(id)){
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showWarnMsg("对不起！不能进行批量操作！");
			return;
		}
	  }else{
	    ids = id;
	  }
   openPage("查看任务",800,350,ccnetpath+"/backstage/taskschedule/goedit?task_id="+ids+"&optype=1");
}

//删除任务
function delTask(id){
   var ids = "";
   if(isEmpty(id)){
     var ids = getCKVal("ck");
	 if(ids == null || ids.length==0){
		showWarnMsg("请先选择要操作的数据！");
		return;
	 }
	 if(ids.split(",").length>1){
		showWarnMsg("对不起！不能进行批量操作！");
		return;
	}
  }else{
    ids = id;
  }
   
   //判断演示模式
   if(isDemoMode(demo_mode)){
    showTErrMsg("对不起！演示模式下不能进行此操作!");
    return false;
   }
   
   var param = "task_id="+ids;
   showTConFirm('您确定要删除吗？',function(){
       var index = showTLoading();
       $.ajax({
   		type:'POST',
   		url:ccnetpath+"/backstage/taskschedule/del",
   		data:param,
   		dataType:'json',
   		success:function(data, textStatus) {
   			if ("1" != data.res) {
   				showTErrMsg(data.resMsg);
   				closeLayer(index);
   			}else{
   				showTSucMsg(data.resMsg);
   				closePFrame("/backstage/taskschedule/index",500);
   			}
   		}
   	  });
    });
}

function runOnce(id) {
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
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	var param = "task_id="+ids;
	showTConFirm('您确定要运行一次该任务吗？', function() {
		var index = showTLoading();
		$.ajax({
			type : 'POST',
			url : ccnetpath + "/backstage/taskschedule/runOnce",
			data : param,
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
    				showTErrMsg(data.resMsg);
    				closeLayer(index);
    			}else{
    				showTSucMsg(data.resMsg);
    				closePFrame("/backstage/taskschedule/index",500);
    			}
			}
		});
	});
}

function resumeJob(id) {
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
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	var param = "task_id="+ids;
	showTConFirm('您确定要启动该任务吗？', function() {
		var index = showTLoading();
		$.ajax({
			type : 'POST',
			url : ccnetpath + "/backstage/taskschedule/resumeJob",
			data : param,
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
    				showTErrMsg(data.resMsg);
    				closeLayer(index);
    			}else{
    				showTSucMsg(data.resMsg);
    				closePFrame("/backstage/taskschedule/index",500);
    			}
			}
		});
	});
}

function pauseJob(id) {
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
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	var param = "task_id="+ids;
	showTConFirm('您确定要暂停该任务吗？', function() {
		var index = showTLoading();
		$.ajax({
			type : 'POST',
			url : ccnetpath + "/backstage/taskschedule/pauseJob",
			data : param,
			dataType : 'json',
			success : function(data, textStatus) {
				if ("1" != data.res) {
    				showTErrMsg(data.resMsg);
    				closeLayer(index);
    			}else{
    				showTSucMsg(data.resMsg);
    				closePFrame("/backstage/taskschedule/index",500);
    			}
			}
		});
	});
}
