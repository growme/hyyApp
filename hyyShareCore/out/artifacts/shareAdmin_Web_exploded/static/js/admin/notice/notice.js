//JavaScript Document
$(function () {
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加公告",860,550,ccnetpath+"/backstage/notice/add");
	});
    //删除文案
	$("#th_del_btn").click(function () {
		trashNotice();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存文案
	$("#saveBtn").click(function () {
		saveNotice();
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
function saveNotice(){
	
	var noticeId = $("#noticeId").val();
	if(isEmpty($("#noticeType").val())){
		showTips("请选择公告类型!","noticeType");
        return false;
    }
	
	if(isEmpty($("#noticeTitle").val())){
		showTips("公告标题不能为空!","noticeTitle");
        return false;
    }
	
	if(isEmpty($("#orderNumber").val())){
		showTips("排序编号不能为空!","orderNumber");
        return false;
    }else{
    	if(!isNumber($("#orderNumber").val())){
    		showTips("排序编号只能为数字!","orderNumber");
            return false;
        }
    }
	
	var content = UE.getEditor('ntcontent').getContent();
	if(isEmpty(content)){
		showTips("公告内容不能为空!","cpcontent");
        return false;
    }
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
	
	//提交数据
	var reqUrl = "/backstage/notice/save";
    if(!isEmpty(noticeId)){
    	reqUrl = "/backstage/notice/update";
    }
    showTConFirm('您确定的要提交吗？',function(){
	    var index = showTLoading();
	    $("#noticeForm").ajaxSubmit({
	        type: 'POST',
	        dataType: 'json',
	        url: ccnetpath + reqUrl,
	        data: {
	            'noticeContent': content
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


//修改公告
function editNotice(id,tp){
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
   openPage("修改公告",860,550,ccnetpath+"/backstage/notice/edit?noticeId="+ids);
}

//撤销公告
function revokeNotice(id){
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
  var param = "noticeId="+ids+"&now="+new Date().getTime();
  showConFirm('您确定的要撤销吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/notice/revoke",
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

//删除公告
function trashNotice(id){
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
  var param = "noticeId="+ids+"&now="+new Date().getTime();
  showConFirm('您确定的要删除吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/notice/trash",
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

function initUEditor(){
   var ue = UE.getEditor('ntcontent');
}