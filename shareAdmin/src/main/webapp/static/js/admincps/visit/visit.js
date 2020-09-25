$(function () {
	
	//点击删除
	$("#th_trash_btn").click(function () {
		trashVisitLog();
	});
	
	//点击清空
	$("#th_clear_btn").click(function () {
		clearVisitLog();
	});
	
    //状态
	$("#accountState").change(function () {
		$("#searchForm").submit();
	});
});

//根据IP查询
function queryByReqIP(ph){
	if(ph!=null){
		$("#queryParam").val(ph);
		$("#searchForm").submit();
	}
}

//批量删除
function trashVisitLog(id){
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
     
    var param = "visit_id="+ids+"&now="+new Date().getTime();
	showConFirm('您确定的要删除文章访问日志吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/visit/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/visit/index", 600);
	    			}
	    		}
	     });
	});
}

//清空日志
function clearVisitLog(){
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	showConFirm('您确定的要清空文章访问日志吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/visit/clear",
			   data: {},
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closePFrame("/backstage/visit/index", 600);
	    			}
	    		}
	     });
	});
}

