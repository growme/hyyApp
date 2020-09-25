//JavaScript Document
$(function () {
	//点击增加按钮
	$("#th_add_btn").click(function () {
		openPage("增加会员",700,700,ccnetpath+"/backstage/member/goadd");
	});
	
	$("#member_state").change(function () {
		$("#searchForm").submit();
	});
	
	//点击删除按钮
	$("#th_del_btn").click(function () {
		delMember();
	});
	
	//点击锁定按钮
	$("#th_lock_btn").click(function () {
		freezeAccount('',1);
	});
	
	//点击激活按钮
	$("#th_unlock_btn").click(function () {
		freezeAccount('',0);
	});
	
	//点击重置按钮
	$("#th_reset_btn").click(function () {
		resetMemberPwd();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	//点击删除按钮
	$("#th_trash_btn").click(function () {
		trashMember();
	});
	
	//保存注册信息
	$("#saveZcxxBtn").click(function () {
		saveMember();
	});
	
	//保存密码
	$("#resetBtn").click(function () {
		saveMemberPwd();
	});
	
	if(isNull($('#memberId').val())){
		$('#userState').val("0");
	}
	
});


//重置密码
function resetMemberPwd(id){
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
   openPage("重置密码",360,240,ccnetpath+"/backstage/member/resetpwd?member_id="+ids);
}


function saveMemberPwd(){
	if(!isEnNum($("#loginPassword").val())){
        showErrMsg("登录密码不能为空!");
        return false;
    }
    
    if(!isEnNum($("#loginPassword2").val())){
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
    
	var memberId = $('#userId').val();
	var password = $('#loginPassword').val();
	var datas = "memberId="+memberId+"&loginPassword="+$.md5(password)+"&tm="+new Date().getTime();
	showConFirm('您确定要重置账号密码吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/member/savepwd",
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

function checkMoney(obj){
	if(isNull(obj.value)){
		obj.focus();
	   showErrMsg("请输入整数或者小数!");
	   return false;
	}
	if(!isPriceNumber(obj.value)){
	   obj.value="";
	   obj.focus();
	   showErrMsg("只能输入整数或者小数!");
	   return false;
	}
}

function saveMember(){
	
	if(isEmpty($("#userState").val())){
       showErrMsg("对不起，请选择账户状态!");
       return false;
    }

	if(isEmpty($("#memberLevel").val())){
       showErrMsg("对不起，请选择会员等级!");
       return false;
    }
	
	if(isEmpty($("#loginAccount").val())){
       showErrMsg("对不起，请设输入用户登录账号!");
       return false;
    }
	
    //验证表单
    if(!isEnZHNum($("#loginAccount").val())){
        showErrMsg("登录账号只能由字母、数字、中文、下划线组成!");
        return false;
    }
    
    if(isEmpty($("#memberId").val())){
	    if(isEmpty($("#password").val())){
	        showErrMsg("登录密码不能为空!");
	        return false;
	    }
	    
	    if(isEmpty($("#password2").val())){
	        showErrMsg("确认密码不能为空!");
	        return false;
	    }
	    
	    if($("#password").val()!=$("#password2").val()){
	        showErrMsg("对不起，您两次输入的密码不一致!");
	        return false;
	    }
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    var reqUrl = "/backstage/member/save";
    if(!isEmpty($("#memberId").val())){
    	reqUrl = "/backstage/member/edit";
    }
    var pwd = $("#password").val();
    if(!isEmpty(pwd)){
    	pwd = $.md5(pwd);
    }
    showConFirm('您确定的要提交吗？',function(){
    var index = showLoading();
    $("#baseSettingForm").ajaxSubmit({
       type: 'POST',
       dataType: 'json',
       url: ccnetpath + reqUrl,
       data: {
           'loginPassword': pwd,
           'flag':$("#flag").val()
       },
       success: function(data) {
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

//修改用户
function editMember(id){
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
   openPage("修改会员",700,700,ccnetpath+"/backstage/member/goedit?member_id="+ids);
   //openTab("/backstage/member/goedit?member_id="+ids+"&flag=0");
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
	    
	    var param = "member_id="+ids+"&user_state="+st+"&now="+new Date().getTime();
	    showConFirm(mst,function(){
        var index = showLoading();
        $.ajax({
		   type: "POST",
		   url: ccnetpath + "/backstage/member/freeze",
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



function trashMember(id){
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
	    
	    var param = "member_id="+ids+"&now="+new Date().getTime();
	    showConFirm("您确定要删除会员？",function(){
        var index = showLoading();
        $.ajax({
		   type: "POST",
		   url: ccnetpath + "/backstage/member/trash",
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

