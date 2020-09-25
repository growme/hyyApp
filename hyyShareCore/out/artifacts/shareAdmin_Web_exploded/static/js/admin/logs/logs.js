//JavaScript Document
$(function () {
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delLog();
	});
	
	//点击清空按钮
	$("#th_clear_btn").click(function () {
		clearLog();
	});
	
});


function delLog(id){
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
	  showTConFirm('您确定要删除吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/log/trash",
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

function clearLog(){
	 //判断演示模式
     if(isDemoMode(demo_mode)){
      showErrMsg("对不起！演示模式下不能进行此操作!");
      return false;
     }
	  var param = "now="+new Date().getTime();
	  showTConFirm('您确定清空日志吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/log/clear",
				   data: param,
				   dataType:'json',
				   success:function(data, textStatus) {
		    			if ("1" != data.res) {
		    				showTErrMsg(data.resMsg);
		    				closeLayer(index);
		    			}else{
		    				showTSucMsg(data.resMsg);
		    				closePFrame("/backstage/log/index",500);
		    			}
		    	    }
		     });
		});
}