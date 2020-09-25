//JavaScript Document
$(function () {
	//点击删除按钮
	$("#th_trash_btn").click(function () {
		trashShareLog();
	});
	//点击清空
	$("#th_clear_btn").click(function () {
		clearShareLog();
	});
});


function trashShareLog(id){
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
       showErrMsg("对不起！演示模式下不能进行此操作!");
       return false;
     }
	  var param = "log_id="+ids+"&now="+new Date().getTime();
	  showTConFirm('您确定要删除分享记录吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/sharelog/trash",
				   data: param,
				   dataType:'json',
				   success:function(data, textStatus) {
		    			if ("1" != data.res) {
		    				showTErrMsg(data.resMsg);
		    				closeLayer(index);
		    			}else{
		    				showTSucMsg(data.resMsg);
		    				closeFrame(true);
		    			}
		    	    }
		     });
		});
}


function clearShareLog(){
     //判断演示模式
     if(isDemoMode(demo_mode)){
       showErrMsg("对不起！演示模式下不能进行此操作!");
       return false;
     }
	  var param = "now="+new Date().getTime();
	  showTConFirm('您确定要清空分享记录吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/sharelog/clear",
				   data: param,
				   dataType:'json',
				   success:function(data, textStatus) {
		    			if ("1" != data.res) {
		    				showTErrMsg(data.resMsg);
		    				closeLayer(index);
		    			}else{
		    				showTSucMsg(data.resMsg);
		    				closeFrame(true);
		    			}
		    	    }
		     });
		});
}
