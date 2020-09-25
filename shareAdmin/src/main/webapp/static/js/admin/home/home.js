$(function () {
	
	window.onresize = function(){
		window.location.reload();
	} 
	//保存密码
	$("#changeBtn").click(function () {
		saveUserPwd();
	});
	
	//关闭窗口
	$("#closeBtn").click(function () {
		closeFrame(false);
	});
	
	$(".picUrlRadio").change(function(){
		var picUrl = getAllCheckVal("picUrl");
		if(!isNull(picUrl)){
		 //更新底部头像
		 var _vicon = ccnetpath+"/static/img/avatars/"+picUrl+".jpg";
		 parent.$("#userIconBig").attr("src",_vicon);
		 parent.$("#userIconSBig").attr("src",_vicon);
		 parent.$("#userIconSmall").attr("src",_vicon);
		 //存储cookie
		 $.cookie('userIconBig', _vicon, { expires: 30, path: '/' });
		 $.cookie('userIconSBig', _vicon, { expires: 30, path: '/' });
		 $.cookie('userIconSmall', _vicon, { expires: 30, path: '/' });
		}
	 });
	
	//保存设置
	$("#saveSetBtn").click(function () {
		saveUserSetting();
	});
	
});

function saveUserPwd(){
	if(isEmpty($("#loginPassword").val())){
        showErrMsg("原始密码不能为空!");
        return false;
    }
	
	if(isEmpty($("#newloginPassword").val())){
        showErrMsg("新的密码不能为空!");
        return false;
    }
    
    if(isEmpty($("#newloginPassword2").val())){
        showErrMsg("确认密码不能为空!");
        return false;
    }
    
    /*if(!isEnNum($("#loginPassword").val()) && !isEmpty($("#loginPassword").val())){
        showErrMsg("原始密码只能由字母、数字、下划线组成!");
        return false;
    }*/
    
    /*if(!isEnNum($("#newloginPassword").val()) && !isEmpty($("#newloginPassword").val())){
        showErrMsg("新密码只能由字母、数字、下划线组成!");
        return false;
    }
    
    if(!isEnNum($("#newloginPassword2").val()) && !isEmpty($("#newloginPassword2").val())){
        showErrMsg("确认密码只能由字母、数字、下划线组成!");
        return false;
    }*/
    
    if($("#newloginPassword").val()!=$("#newloginPassword2").val()){
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
  var npassword = $('#newloginPassword').val();
  var npassword2 = $('#newloginPassword2').val();
  var datas = "userId="+userId
              +"&loginPassword="+$.md5(password)
			  +"&newpwd="+$.md5(npassword)
			  +"&newpwd2="+$.md5(npassword2)+"&tm="+new Date().getTime();
  showConFirm('您确定要修改账号密码吗？',function(){
        var index = showLoading();
        $.ajax({
			   type: "POST",
			   cache : false,
			   url: ccnetpath+"/backstage/index/savepwd",
			   data: datas,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showErrMsg(data.resMsg);
	    				closeLayer(index);
	    			}else{
	    				showSucMsg(data.resMsg+",请重新登录");
	    				setTimeout(function(){
	    					window.top.location.href=ccnetpath+"/systemLogout";
	    				},500);
	    			}
	    		}
	     });
	});
}

/**
 * 保存用户设置
 */
function saveUserSetting(){
    
    if(isEmpty($("#sex").val())){
        showErrMsg("对不起，请选择用户性别!");
        return false;
    }
    
    if(!isUName($("#userName").val()) && !isEmpty($("#userName").val())){
        showErrMsg("用户名称只能由字母、中文、数字组成!");
        return false;
    }
    
    if(!isUName($("#nickName").val()) && !isEmpty($("#nickName").val())){
        showErrMsg("用户名称只能由字母、中文、数字组成!");
        return false;
    }
    
    if(!isEmpty($("#phoneNumber").val())){
    	 if(!isPhone($("#phoneNumber").val()) && !isTel($("#phoneNumber").val())){
            showErrMsg("联系电话格式有误!");
            return false;
    	 }
    }
    
    if(!isQQ($("#qq").val()) && !isEmpty($("#qq").val())){
        showErrMsg("QQ号码只能为数字!");
        return false;
    }
    
    if(!isEmail($("#email").val()) && !isEmpty($("#email").val())){
        showErrMsg("电子邮箱格式错误!");
        return false;
    }
    
    if(isEmpty(getAllCheckVal("picUrl"))){
        showErrMsg("对不起，请选择一个头像!");
        return false;
    }
    
    //判断演示模式
    if(isDemoMode(demo_mode)){
     showErrMsg("对不起！演示模式下不能进行此操作!");
     return false;
    }
    
    var datas = $('#userForm').serialize();    
    //alert(datas);
    datas = datas+"&tm="+new Date().getTime();
    showConFirm('您确定的要修改吗？',function(){
       var index = showLoading();
       $.ajax({
   		type:'POST',
   		url:ccnetpath + "/backstage/index/saveSetting",
   		data:datas,
   		dataType:'json',
   		success:function(data, textStatus) {
   			if ("1" != data.res) {
   				showErrMsg(data.resMsg);
   				closeLayer(index);
   			}else{
   				showSucMsg(data.resMsg);
   				closeFrame(false);
   			}
   		}
   	  });
    });
    
}