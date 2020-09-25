//JavaScript Document
var box_view_btn="loginBtn";//初始化按钮值
$(function () {
	//处理点击
	$("#accountNameId").focus();
	getVerifyCode();
	clearLoginForm();
	//监听docuemnt的onkeydown事件看是不是按了回车键
	$(document).keydown(function(event){
		event = event ? event : window.event;
		if (event.keyCode === 13){
			$("#"+box_view_btn).trigger("click");
		}
	});
	//登录
	$("#loginBtn").click(function () {
		if("" == $("#accountNameId").val()){
			showTips("用户名不能为空！","accountNameId");
			$("#accountNameId").focus();
		}else if("" == $("#passwordId").val()){
			showTips("密码不能为空！","passwordId");
			$("#passwordId").focus();
		}else if("" == $("#verifyCodeId").val()){
			showTips("验证码不能为空！","verifyCodeId");
			$("#verifyCodeId").focus();
		}else{
			//显示进度条
			var index = showLoading2("登录验证中,请稍后...");
			var loginname = $("#accountNameId").val();
			var password = $("#passwordId").val();
			var verifyCode=$("#verifyCodeId").val();
			var code = loginname+",ccnet,"+$.md5(password)+",ccnet,"+verifyCode;
			$.ajax({
				type:'POST',
				url:ccnetpath + '/adminLogin',
				data:{KEYDATA:code,tm:new Date().getTime()},
		        dataType:'json',
		        success:function(data, textStatus) {    
	            	var result=data.result;
	            	if ("success" != result) {//如果登录不成功，则再次刷新验证码
	            		closeLayer(index);
	            		loginAlert(result);
					}else{
						$("#loginBtn").unbind('click');
						setTimeout(function(){
							if(version=="V5"){
								window.location.href=ccnetpath+"/backstage/home";
							}else{
								window.location.href=ccnetpath+"/backstage/index";
							}
						},500);
					}
	            }
		    });
		}
	});
	
	$("#check_code").click(function () {
		getVerifyCode();
	});
	
	
});

function loginAlert(msg) {
	if("codeerror" == msg){
		showTips("验证码不正确！","verifyCodeId");
		$("#verifyCodeId").focus();
	}else if("license" == msg){
		window.location.href=ccnetpath+"/goreglicense";
	}else if("nullup" == msg){
		showTips("用户名或密码不能为空！","accountNameId");
		$("#accountNameId").focus();
	}else if("nullcode" == msg){
		showTips("验证码不能为空！","verifyCodeId");
		$("#verifyCodeId").focus();
	}else if("usererror" == msg){
		showTips("用户名或密码有误！","passwordId");
		clearLoginForm();
		$("#accountNameId").focus();
	}else if("attemptserror" == msg){
		showTips("错误次数过多,账户已经锁定！","passwordId");
		clearLoginForm();
	}else if("error" == msg){
		showTips("账户输入有误！","accountNameId");
		clearLoginForm();
	}else if("inactive" == msg){
		showTips("账号未激活或者已冻结！","accountNameId");
	}else if("autherror" == msg){
		showTips("账号验证出现异常，请稍后再试！","accountNameId");
	}else if("invalidip" == msg){
		showTips("您的IP地址不允许登陆","accountNameId");
	}
	
	return false;
}

//获取校验码
function getVerifyCode() {
    var url = ccnetpath+'/captcha/getcode';
    $('#check_code').attr('src', url + '?random=' + Math.random());
    $('#captcha').val('').focus();
}

//清空表单
function clearLoginForm(){	
	$("#captcha").val("");
	$("#passwordId").val("");
	$("#verifyCodeId").val("");
	getVerifyCode();
}
