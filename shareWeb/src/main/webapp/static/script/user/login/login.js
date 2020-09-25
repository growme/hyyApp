$(function() {
	$(".cimg").click(function (event) {
        refreshCheck();
    });
	
    $(".cfreash").click(function (event) {
        refreshCheck();
    });
    
    $("#loginSubmit").click(function (event) {
    	login();
    });
    
    //设置password字段的值	
    $('.txt-password').bind('input',function(){
    	$('#loginPassword').val($(this).val());
    });
});

//记录密码错误次数
var errorTimes= $.cookie('loginErrorTimes') || 0;
if(errorTimes>3){
	refreshCheck();
    $('#captcha').show();
}

//浏览器记住密码时，jquery取不到输入框中的值，所以直接用js取值
function checkUsername(){		
	var username = $("#loginAccount").val();
	if(username == ""){
		M._alert("请输入手机号");
		return false;
	}else{			
		if (!$("#loginAccount").val().match(/^(((1[3|5|7|8][0-9]{1}))+\d{8})$/)) {
			M._alert("手机号不正确");
			$('#username').val('').focus();
			return false;
		} else {
			return true;
		}			
	}
}

function checkPassword(){
	var password = $("#loginPassword").val();
	if(password == ""){
		M._alert("请输入密码");
		return false;
	} 
	if(password.length <6 || password.length>20){
		M._alert("密码不正确，请重新输入");
		$('#password').val('').focus();
		return false;
	}
	return true;
}

function checkCaptcha(){
    var captcha=$('[name="yzm"]');
    var ccode=captcha.val();
    
    if(captcha.is(':visible') && ccode.length<1){
        M._alert('请输入验证码');
        captcha.focus();
        return false
    }
	return true;
}

//登陆校验
function login(){
	if(!loginCheck()){
		return;
	}		
}

function loginCheck(){	
	var phone = $("#loginAccount").val();
	var pass = $("#loginPassword").val();
	var ccode = $("#yzm").val();
   	if(checkUsername() && checkPassword() && checkCaptcha()){
   		var code = phone+",ccnet,"+$.md5(pass)+",ccnet,"+ccode;
   		$.ajax({
            url: ccnetpath+"user/ulogin",
            type: 'post',
            cache: false,
            data: {KEYDATA:code,tm:new Date().getTime()},
            dataType: 'json',
            success: function (data) {
           	 if ("1000" != data.apicode) {
                 errorTimes++;
                 if(errorTimes>3){
                    refreshCheck();
                    $('#captcha').show();
	             }
                $.cookie('loginErrorTimes',errorTimes);
                M._alert(data.msg);
   			} else {
   				$.cookie('loginErrorTimes',0);
   				M._alert(data.msg);
   				setTimeout(function(){
				   window.location.href=ccnetpath+"home/index";
				},500);
   			   }
            }
        });
   	}
	return false;
}
//获取校验码
function refreshCheck() {
    var url = path+'/captcha/getcode';
    $('.cimg').attr('src', url + '?t=' + new Date().getTime());
    $('#captcha').val('').focus();
}