//JavaScript Document
$(function () {
	
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加角色",400,385,ccnetpath+"/backstage/role/goadd");
	});
	
	//点击修改按钮
	$("#th_edit_btn").click(function () {
		editRole();
	});
	
	
	//点击授权按钮
	$("#th_permit_btn").click(function () {
		setPermit();
	});
	
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delRole();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存角色
	$("#saveBtn").click(function () {
		saveRole();
	});
	
});

function saveRole(){
	
	var role_id = $("#roleId").val();
    var role_name = $("#roleName").val();
    var state = $("#state").val();
    var role_desc = $("#roleDesc").val();
    var order_number = $("#orderNumber").val();
    var role_type = $("#roleType").val();
    
    
    if(isEmpty(role_name)){
       showErrMsg("对不起，请输入角色名称!");
       return false;
    }
    
    if(isEmpty(role_type)){
       showErrMsg("对不起，请选择角色类型!");
       return false;
    }
    
    if(isEmpty(state)){
        showErrMsg("对不起，请选择角色状态!");
        return false;
    }
    
    if(isEmpty(order_number)){
        showErrMsg("对不起，请输入排序编号!");
        return false;
    }
    
    var reqUrl = "/backstage/role/saverole";
    if(!isEmpty(role_id)){
    	reqUrl = "/backstage/role/editrole";
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
	//提交表单
    var datas = $("#roleForm").serialize();
    datas = datas+"&tm="+new Date().getTime();
    showConFirm('您确定的要提交吗？',function(){
    	var index = showLoading();
    	$.ajax({
    		type:'POST',
    		url:ccnetpath + reqUrl,
    		data:datas,
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

function setPermit(id){
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
   openPage("角色授权",400,500,ccnetpath + "/backstage/role/setpermit?role_id="+ids);
}

function editRole(id){
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
   openPage("修改角色",400,385,ccnetpath+"/backstage/role/goedit?role_id="+ids);
}

function delRole(id){
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
    
	var param = "role_id="+ids+"&now="+new Date().getTime();
	showConFirm('您确定的要删除吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/role/trashrole",
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