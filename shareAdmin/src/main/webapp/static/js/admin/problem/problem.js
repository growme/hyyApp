//JavaScript Document
$(function () {
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加常见问题",860,550,ccnetpath+"/backstage/problem/add");
	});
    //删除文案
	$("#th_del_btn").click(function () {
		trashProblem();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存文案
	$("#saveBtn").click(function () {
		saveProblem();
	});
	
	$("#showTop_CK").change(function(){
	     if($("#showTop_CK").is(":checked")){
	    	 $("#showTop").val(1);
	     }else{
	    	 $("#showTop").val(0);
	     }
	 });
	
	$("#state_CK").change(function(){
	     if($("#state_CK").is(":checked")){
	    	 $("#state").val(1);
	     }else{
	    	 $("#state").val(0);
	     }
	 });
	
});



//保存文案
function saveProblem(){
	
	var id = $("#id").val();
	if(isEmpty($("#type").val())){
		showTips("请选择常见问题类型!","type");
        return false;
    }
	
	if(isEmpty($("#title").val())){
		showTips("常见问题标题不能为空!","title");
        return false;
    }
	
	if(isEmpty($("#sort").val())){
		showTips("排序编号不能为空!","sort");
        return false;
    }else{
    	if(!isNumber($("#sort").val())){
    		showTips("排序编号只能为数字!","sort");
            return false;
        }
    }
	
	var content =$("#content").val();
	if(isEmpty(content)){
		showTips("常见问题内容不能为空!","content");
        return false;
    }
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
	
	//提交数据
	var reqUrl = "/backstage/problem/save";
    if(!isEmpty(id)){
    	reqUrl = "/backstage/problem/update";
    }
    showTConFirm('您确定的要提交吗？',function(){
	    var index = showTLoading();
	    $("#problemForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data: {
	            'problemContent': content
	        },
	        success: function(data) {
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
   return false;
}


//修改常见问题
function editProblem(id,tp){
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
   openPage("修改常见问题",860,550,ccnetpath+"/backstage/problem/edit?id="+ids);
}

//撤销常见问题
function revokeProblem(id){
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
  showConFirm('您确定的要撤销吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/problem/revoke",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closeFrame(true);
	    			}
	    		}
	     });
	});
}

//删除常见问题
function trashProblem(id){
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
			   url: ccnetpath+"/backstage/problem/trash",
			   data: param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg);
	    				closeFrame(true);
	    			}
	    		}
	     });
	});
}

// function initUEditor(){
//    var ue = UE.getEditor('ntcontent');
// }