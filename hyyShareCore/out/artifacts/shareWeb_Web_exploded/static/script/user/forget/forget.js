$(function() {
	$(".cimg").click(function (event) {
        refreshCheck();
    });
	
    $(".cfreash").click(function (event) {
        refreshCheck();
    });
    
    $("#btnSendCode").click(function (event) {
    	sendMessage();
    });
    
    $("#forgetSubmit").click(function (event) {
    	forgetPaswd();
    });
    
    //设置password字段的值	
    $('.txt-password').bind('input',function(){
    	$('#loginPassword').val($(this).val());
    });
});

//获取校验码
function refreshCheck() {
    var url = path+'/captcha/getcode';
    $('.cimg').attr('src', url + '?t=' + new Date().getTime());
    $('#captcha').val('').focus();
}


/*-------------------------------------------*/
var InterValObj; //timer变量，控制时间  
var count = 60; //间隔函数，1秒执行  
var curCount;//当前剩余秒数  
function sendMessage() {
	curCount = count;
	var phone = $("#phone").val();//手机号码 
	var checkCode=$("#captcha").val();
	if (phone != "") {
		if (!phone.match(/^(((1[3|5|6|7|8|9][0-9]{1}))+\d{8})$/)) {
			M._alert("手机号不正确");
		} else if(checkCode !=""){
			//设置button效果，开始计时  
			$("#btnSendCode").attr("disabled", "true");
			$("#btnSendCode").val(curCount + "秒重新获取");
			InterValObj = window.setInterval(setRemainTime, 1000); //启动计时器，1秒执行一次  
			//向后台发送处理数据  
			$.ajax({
	            url: ccnetpath+"mobile/code/send",
	            type: 'post',
	            cache: false,
	            data: {"mobile":phone,"captcha":checkCode,"tp":2,stime:new Date().getTime()},
	            dataType: 'json',
	            success: function (data) {
	           	  if ("1000" != data.apicode) {
	           		refreshCheck();
	           		clearTimeOut();
	                M._alert(data.msg);
	   			  } else {
	   				M._alert(data.msg);
	   			  }
	            }
	        });
		}else{
			M._alert("请输入图形验证码!");
		}
	} else {
		M._alert("手机号不能为空！");
	}
}

//清楚计时
function clearTimeOut(){
	window.clearInterval(InterValObj);//停止计时器  
	$("#btnSendCode").removeAttr("disabled");//启用按钮  
	$("#btnSendCode").val("获取验证码");
	code = ""; //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效      
}

//timer处理函数  
function setRemainTime() {
	if (curCount == 0) {
		clearTimeOut();
	} else {
		curCount--;
		$("#btnSendCode").val(curCount + "秒重新获取");
	}
}

//验证手机号码
function forgetPaswd(){
	var phone = $("#phone").val();//手机号码 
	var captcha=$("#captcha").val();//验证码
	var phoneCode=$("#checkCode").val();//验证码
	if(phone==''){
		M._alert("手机号不能为空！");
	}else if(captcha==''){
		M._alert("验证码不能为空！");
	}else if(phoneCode==''){
        M._alert('短信验证码不能为空!');
    }else{
    	$.ajax({
            url: ccnetpath+"user/forget/check",
            type: 'post',
            cache: false,
            data: {
            	"phone":phone,
            	"captcha":captcha,
            	"smscode":phoneCode,
            	"stime":new Date().getTime()
            },
            dataType: 'json',
            success: function (data) {
           	  if ("1000" != data.apicode) {
           		refreshCheck();
                M._alert(data.msg);
   			  } else {
   			    M._alert(data.msg);
   				setTimeout(function(){
 				   window.location.href=ccnetpath+"user/setpwd?token="+data.token+"&mobile="+phone;
 				},500);
   			  }
            }
        });
		
	}
}
