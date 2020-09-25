//JavaScript Document
$(function () {
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delBackup();
	});
	
	//点击清空按钮
	$("#th_clear_btn").click(function () {
		clearLog();
	});
	
});


function delBackup(id){
    var ids = "";
    if(isEmpty(id)){
     var ids = getCKVal("ck");
	 if(ids == null || ids.length==0){
		showTWarnMsg("请先选择要操作的数据！");
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
    
	var param = "chks="+ids+"&now="+new Date().getTime();
	showTConFirm('您确定要删除吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/dbback/del",
				   data: param,
				   dataType:'json',
				   success:function(data, textStatus) {
		    			if ("1" != data.res) {
		    				showTErrMsg(data.resMsg);
		    				closeLayer(index);
		    			}else{
		    				showTSucMsg(data.resMsg);
		    				closePFrame("/backstage/dbback/index",500);
		    			}
		    	    }
		     });
		});
}

function restore(id){
	if(isEmpty(id)){
      var ids = getCKVal("ck");
		 if(ids == null || ids.length==0){
			showTWarnMsg("请先选择要操作的数据！");
			return;
		 }
		 if(ids.split(",").length>1){
			showTWarnMsg("对不起！不能进行批量操作！");
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
    
   var param = "fname="+ids+"&now="+new Date().getTime();
   showTConFirm('您确定要恢复此文件吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/dbback/restore",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showTErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showTSucMsg(data.resMsg);
	    				closePFrame("/backstage/dbback/index",500);
	    			}
	    	    }
	     });
	});
}