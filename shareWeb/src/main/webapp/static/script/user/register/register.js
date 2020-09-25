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
	    
		$("#registerSubmit").click(function (event) {
	    	register();
	    });
		 
		$('.txt-password').bind('input',function(){
			$('#password').val($(this).val());
		});
		
		$("#username").bind("blur",function() {
			if ($("#username").val() == '') {
				M._alert("手机号不能为空！");
			} else {
				if (!$("#username").val().match(/^(((1[3|5|6|7|8|9][0-9]{1}))+\d{8})$/)) {
					M._alert("手机号不正确");
					$("#username").focus();
					return false;
				} else {
					return true;
				}
			}

		});
		
		$("#password").bind("blur", function() {
			if ($("#password").val() == '') {
				M._alert("密码不能为空！");
			}
		});
		
		$("#rpassword").bind("blur", function() {
			if ($("#rpassword").val() == '') {
				M._alert("请输入确认密码！");
			}
		});
	});
	
	
	/*-------------------------------------------*/
	var InterValObj; //timer变量，控制时间  
	var count = 60; //间隔函数，1秒执行  
	var curCount;//当前剩余秒数  
	function sendMessage() {
		curCount = count;
		var phone = $("#username").val();//手机号码 
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
		            data: {"mobile":phone,"captcha":checkCode,"tp":"0",stime:new Date().getTime()},
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
	
	function register() {
		var phone = $("#username").val();
		var pass = $("#password").val();
		var pass2 = $("#rpassword").val();
		var ccode = $("#captcha").val();
		var recom_user = $("#recom_user").val();
		var checkCode=$("#checkCode").val();
		if(recom_user==undefined){
		   recom_user = '';
		}
		if(phone==''){
			M._alert("手机号不能为空！");
		}else if(!phone.match(/^(((1[3|5|7|8][0-9]{1}))+\d{8})$/)){
			M._alert("手机号不正确！");
		}else if(pass==''){
			M._alert("密码不能为空！");
		}else if(ccode==''){
			M._alert("验证码不能为空！");
	    }else if(pass!=pass2){
	    	M._alert("两次输入密码不一致！");
	    }else if(checkCode==''){
			M._alert("短信验证码不能为空！");
		}else{
        	$.ajax({
	            url: ccnetpath+"user/uregister",
	            type: 'post',
	            cache: false,
	            data: {
	            	"loginAccount":phone,
	            	"loginPassword":$.md5(pass),
	            	"captcha":ccode,
	            	"smscode":checkCode,
	            	"recom_user":recom_user,
	            	"stime":new Date().getTime()
	            },
	            dataType: 'json',
	            success: function (data) {
	           	  if ("1000" != data.apicode) {
	           		refreshCheck();
	                M._alert(data.msg);
	   			  } else {
	   				M._alert(data.msg);
	   				//返回登录
	   				setTimeout(function(){
	 				   window.location.href=ccnetpath+"user/login";
	 				},500);
	   			  }
	            }
	        });
		}
	}
	
	//获取校验码
    function refreshCheck() {
        var url = path+'/captcha/getcode';
        $('.cimg').attr('src', url + '?t=' + new Date().getTime());
        $('#captcha').val('').focus();
    }
    
    //验证手机验证码
    function checkMobilCode() {
        var vcode = $("#checkCode").val();
        var phone = $("#username").val();
        if(vcode==''||vcode==null){
            M._alert("请填写手机手机验证码!");
            return false;
        }
        var action = path+'/mobile/code/check';
        var param = "vcode="+vcode+"&mobile="+phone;
        return commonLoad(action,param,"post");
    }
    
	function commonLoad(url,param,method){
		var obj;var value;
		if (window.ActiveXObject) {
			obj = new ActiveXObject('Microsoft.XMLHTTP');
		} else if (window.XMLHttpRequest) {
			obj = new XMLHttpRequest();
		}
		obj.open(method, url, false);
		obj.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		obj.send(param);
		var result = obj.responseText
		return result;
	}
