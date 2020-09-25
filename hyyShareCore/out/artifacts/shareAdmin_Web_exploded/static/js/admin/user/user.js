//JavaScript Document
$(function () {
	
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加用户",800,600,ccnetpath+"/backstage/user/goadd");
	});
	
	//点击修改按钮
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
        openPage("修改用户",800,600,ccnetpath + "/backstage/user/goedit?user_id="+ids);
	});
	
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delUser();
	});
	
	//点击锁定按钮
	$("#th_lock_btn").click(function () {
		freezeAccount('',1);
	});
	
	//点击激活按钮
	$("#th_unlock_btn").click(function () {
		freezeAccount('',0);
	});
	
	//点击解除密码锁定
	$("#th_unpwlock_btn").click(function () {
		unPasswordLocked();
	});
	
	//点击重置按钮
	$("#th_reset_btn").click(function () {
		resetUserPwd();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//保存用户
	$("#saveBtn").click(function () {
		saveUser();
	});
	
	//保存密码
	$("#resetBtn").click(function () {
		saveUserPwd();
	});
	
});


//重置密码
function resetUserPwd(id){
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
   openPage("重置密码",360,240,ccnetpath+"/backstage/user/resetpwd?user_id="+ids);
}


function saveUserPwd(){
	if(isEmpty($("#loginPassword").val())){
        showErrMsg("登录密码不能为空!");
        return false;
    }
    
    if(isEmpty($("#loginPassword2").val())){
        showErrMsg("确认密码不能为空!");
        return false;
    }
    
    /*if(!isEnNum($("#loginPassword").val()) && !isEmpty($("#loginPassword").val())){
        showErrMsg("登录密码只能由字母、数字、下划线组成!");
        return false;
    }*/
    
    if($("#loginPassword").val()!=$("#loginPassword2").val()){
        showErrMsg("两次输入的密码不一致!");
        return false;
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
  var userId = $('#userId').val();
  var password = $('#loginPassword').val();
  var datas = "userId="+userId+"&loginPassword="+$.md5(password)+"&tm="+new Date().getTime();
  showConFirm('您确定要重置账号密码吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/user/savepwd",
			   data: datas,
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

function saveUser(){
	if(isEmpty($("#loginAccount").val())){
       showTErrMsg("对不起，请设置登录账号!");
       return false;
    }
	
    if(isEmpty($("#userType").val())){
    	showTErrMsg("对不起！请选择帐号类型!");
       return false;
    }
    
    if(isEmpty($("#userState").val())){
       showTErrMsg("对不起，请选择帐号状态!");
       return false;
    }
    
    if(isEmpty($("#sex").val())){
        showTErrMsg("对不起，请选择用户性别!");
        return false;
    }
    
    //验证表单
    if(!isEnZHNum($("#loginAccount").val())){
        showTErrMsg("登录账号只能由字母、数字、中文、下划线组成!");
        return false;
    }
    
    if(isEmpty($("#userId").val())){
	    if(isEmpty($("#loginPassword").val())){
	        showTErrMsg("登录密码不能为空!");
	        return false;
	    }
	    
	    if(isEmpty($("#loginPassword2").val())){
	        showTErrMsg("确认密码不能为空!");
	        return false;
	    }
	    
	    /*if(!isEnNum($("#loginPassword").val()) && !isEmpty($("#loginPassword").val())){
	        showTErrMsg("登录密码只能由字母、数字、下划线组成!");
	        return false;
	    }*/
	    
	    if($("#loginPassword").val()!=$("#loginPassword2").val()){
	        showTErrMsg("对不起，您两次输入的密码不一致!");
	        return false;
	    }
    }
    
    if(!isUName($("#userName").val()) && !isEmpty($("#userName").val())){
        showTErrMsg("用户名称只能由字母、中文、数字组成!");
        return false;
    }
    
    if(!isUName($("#nickName").val()) && !isEmpty($("#nickName").val())){
        showTErrMsg("用户名称只能由字母、中文、数字组成!");
        return false;
    }
    
    if(!isEmpty($("#phoneNumber").val())){
    	 if(!isPhone($("#phoneNumber").val()) && !isTel($("#phoneNumber").val())){
            showTErrMsg("联系电话格式有误!");
            return false;
    	 }
    }
    
    if(!isQQ($("#qq").val()) && !isEmpty($("#qq").val())){
        showTErrMsg("QQ号码只能为数字!");
        return false;
    }
    
    if(!isEmail($("#email").val()) && !isEmpty($("#email").val())){
        showTErrMsg("电子邮箱格式错误!");
        return false;
    }
    
    if(isEmpty(getAllCheckVal("roleId"))){
        showTErrMsg("对不起，至少要选择一个角色!");
        return false;
    }
    
    if(isEmpty(getAllCheckVal("skin"))){
        showTErrMsg("对不起，请选择一个主题!");
        return false;
    }
    
    if(isEmpty(getAllCheckVal("picUrl"))){
        showTErrMsg("对不起，请选择一个头像!");
        return false;
    }
    
    //处理密码加密
    if(!isEmpty($("#loginPassword").val())){
    	$("#loginPassword").val($.md5($("#loginPassword").val()));
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showTErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
    var reqUrl = "/backstage/user/saveuser";
    if(!isEmpty($("#userId").val())){
    	reqUrl = "/backstage/user/edituser";
    }
    var datas = $('#userForm').serialize();    
    //alert(datas);
    datas = datas+"&tm="+new Date().getTime();
    showTConFirm('您确定的要提交吗？',function(){
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
   				closeFrame(true);
   			}
   		}
   	  });
    });
    
}

//修改用户
function editUser(id){
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
   openPage("修改用户",800,600,ccnetpath+"/backstage/user/goedit?user_id="+ids);
}

//查看用户
function seeUser(id){
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
   openPage("查看用户",800,460,ccnetpath+"/backstage/user/goedit?user_id="+ids+"&optype=1");
}

//检查账号
function checkAccount(login_acc,user_id){
   if(login_acc){
	   var param = "login_acc="+login_acc+"&user_id="+user_id;
	   var result = commonLoad(ccnetpath+"/backstage/user/checkuser",param,"POST");
	   var datas = JSON.parse(result);
	   if (datas.res!=1) {
			showErrMsg(datas.resMsg);
			return false;
		}
	   return true;
   }
}

function freezeAccount(id,st){
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
    
	   var mst= "锁定账号将无法登录，确定继续?";
	   if(st==0){
	      mst= "您确定要激活用户账号?";
	   }
	   
	  //判断演示模式
      if(isDemoMode(demo_mode)){
       showErrMsg("对不起！演示模式下不能进行此操作!");
       return false;
      }
	   
	 var param = "user_id="+ids+"&user_state="+st+"&now="+new Date().getTime();
	 showConFirm(mst,function(){
        var index = showLoading();
        $.ajax({
		   type: "POST",
		   url: ccnetpath + "/backstage/user/freeze",
		   data: param,
		   dataType:'json',
		   success:function(data, textStatus) {
			    //data = JSON.parse(data);
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

function unPasswordLocked(id){
    var ids = "";
	if (isEmpty(id)) {
		var ids = getCKVal("ck");
		if (ids == null || ids.length == 0) {
			showWarnMsg("请先选择要操作的数据！");
			return;
		}
	} else {
		ids = id;
	}
	
	//判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
    var param = "user_id="+ids+"&now="+new Date().getTime();
    showConFirm("您确定要解锁登陆锁定吗？",function(){
	    var index = showLoading();
	    $.ajax({
		   type: "POST",
		   url: ccnetpath + "/backstage/user/unlocked",
		   data: param,
		   dataType:'json',
		   success:function(data, textStatus) {
			    //data = JSON.parse(data);
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


function reSetPwd(id){
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
   
   var param = "user_id="+ids+"&now="+new Date().getTime();
   showConFirm('您确定要重置账号密码吗？',function(){
	        var index = showLoading();
	        $.ajax({
				   type: "POST",
				   cache : false,
				   url: ccnetpath+"/backstage/user/savepwd",
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

function forceLogout(id){
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
    
	 var param = "session_id="+ids+"&now="+new Date().getTime();
	 showConFirm('您确定要强制退出用户吗？',function(){
          var index = showLoading();
          $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/session/forceLogout",
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