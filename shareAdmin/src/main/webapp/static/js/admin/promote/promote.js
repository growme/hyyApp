//JavaScript Document
$(function () {
	
	//点击增加
	$("#th_add_btn").click(function () {
		openPage("增加推广地址",500,320,ccnetpath+"/backstage/promote/goadd");
	});
	
	//点击修改
	$("#th_edit_btn").click(function () {
        var ids = getCKVal("ck");
		if(ids == null || ids.length==0){
			showErrMsg("请先选择要操作的数据！");
			return;
		}
		if(ids.split(",").length>1){
			showErrMsg("对不起！不能进行批量更新！");
			return;
	    }
        openPage("修改推广地址",400,320,ccnetpath + "/backstage/promote/goedit?link_id="+ids);
	});
	
	//点击删除
	$("#th_trash_btn").click(function () {
		trashPromote();
	});
	
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存域名
	$("#saveBtn").click(function () {
		savePromote();
	});
	
});


function savePromote(){
	
	var linkId = $("#linkId").val();
    var linkAddr = $("#linkAddr").val();
    var orderNo = $("#orderNo").val();
   
    if(isEmpty(linkAddr)){
       showErrMsg("对不起，请输入入口地址!");
       return false;
    }
    
    if(!isValidUrl(linkAddr) || !isUrl(linkAddr)){
       showErrMsg("输入的入口地址错误，请重新输入!");
       return false;
    }
    
    if(isEmpty(orderNo)){
        showErrMsg("对不起，请输入排序编号!");
        return false;
     }else{
     	if(!isNumber(orderNo)){
     	    showErrMsg("排序编号只能为数字，请再次输入!");
     	    return false;
     	}
     }
    
    if(isEmpty($("#state").val())){
        showErrMsg("对不起，请选择状态!");
        return false;
     }
    
    var reqUrl = "/backstage/promote/save";
    if(!isEmpty(linkId)){
    	reqUrl = "/backstage/promote/edit";
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
       showErrMsg("对不起！演示模式下不能进行此操作!");
       return false;
    }
    
    var datas = $("#linkForm").serialize();
    datas = datas+"&tm="+new Date().getTime();
	showConFirm('您确定要提交吗？',function(){
	    var index = showLoading();
	    $("#linkForm").ajaxSubmit({
		        type: 'POST',
		        dataType: 'json',
		        url: ccnetpath + reqUrl,
		        success: function(data) {
		        	if ("1" != data.res) {
		        		closeLayer(index);
		   				showErrMsg(data.resMsg);
		   			}else{
		   				showSucMsg(data.resMsg);
		   				closeFrame(true);
		   			}
		        }
		     });
	    });
	    return false;
}

function trashPromote(id){
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
      
	  var param = "link_id="+ids+"&now="+new Date().getTime();
	  showTConFirm('您确定要删除吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/promote/trash",
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

function editPromote(id){
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
   openPage("修改推广地址",400,320,ccnetpath + "/backstage/promote/goedit?link_id="+ids);
}


