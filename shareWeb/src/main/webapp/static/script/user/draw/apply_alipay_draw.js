$(function() {
    $("#alitx").bind('click',function () {
    	cashMoney();
    });
    
    $(".cimg").click(function (event) {
        refreshCheck();
    });
	
    $(".cfreash").click(function (event) {
        refreshCheck();
    });
    
    $("#btnSendCode").click(function (event) {
    	sendMessage();
    });
    
    var click_price ;
    $(".cash_info span").click(function(){
        click_price = parseInt($(this).text());
        $("#cashMoney").val(click_price);
        $(".cash_info span").css("background","#fc30a0");
        $(this).css("background","#f3015c");
    });
    
    checkUser();
});


//获取公告信息
function checkUser(){
  $.ajax({
     url : ccnetpath+'/home/user/check',
     type :'POST',
     dataType:"json",
     success:function(json){
    	 var apicode = json.apicode;
    	 var msg = json.msg;
    	 if(apicode!="1000"){
    		 //如果用户未完善资料
    		 layer.open({
                 content: '用户系统升级需完善个人资料才可提现！', skin: 'msg', time: 3, end: function () {
                     window.location.href = ccnetpath+"/user/setting.html";
                 }
             });
    	 }
     }
 });
}


//验证支付宝账号返回 布尔值
function isAlipay(value) {
    if (!/(^(13[0-9]|15[0-9]|16[0-9]|18[0-9]|14[0-9]|17[0-9]|19[0-9])[0-9]{8}$)|(^([\w-\.]+)@([\w-]+\.)+([a-zA-Z]{2,4})$)/.test(value)) {
        return false;
    }else{
        return true;
    }
}

//验证姓名 返回布尔值
function isName(value) {
    if (!/^[\u4e00-\u9fa5]+$/.test(value) || value.length < 2 || value.length >5) {
        return false;
    }else{
        return true;
    }
}

//判断页面是不是在微信中打开
function is_weixin (){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}


//处理提现
function cashMoney() {
    var checkdata = {
        alipay      :   $("#payAccount").val() ,
        alipayname  :   $("#accountName").val(),
        captcha     :   $("#captcha").val(),
        checkCode   :   $("#checkCode").val(),
        money       :   $("#cashMoney").val()
    };
	
    //验证支付宝账户
    if(!isAlipay(checkdata.alipay)){
        M._alert("请输入正确的支付宝账号");
        return false;
    }

    //验证支付宝姓名
    if(!isName(checkdata.alipayname)){
        M._alert("请输入正确的支付宝姓名");
        return false;
    };
    
    //图形验证码
    if(!checkdata.captcha){
        M._alert("请输入图形验证码");
        return false;
    }
    
    //短信验证码
    if(!checkdata.checkCode){
        M._alert("请输入短信验证码");
        return false;
    }

    //验证金额
    if(!checkdata.money){
        M._alert("请输入提现金额");
        return false;
    }
    
    //开始提交数据
    $.ajax({
        url: ccnetpath+"draw/save",
        type: 'post',
        cache: false,
        data: {
        	alipay:checkdata.alipay,
        	alipayname:checkdata.alipayname,
        	money:checkdata.money,
        	paytype:0,
        	captcha:checkdata.captcha,
        	smscode:checkdata.checkCode,
        	tm:new Date().getTime()
        },
        dataType: 'json',
        success: function (data) {
       	 if ("1000" != data.apicode) {
       		    layer.closeAll();
                M._alert(data.msg);
			} else {
				layer.closeAll();
				M._alert(data.msg);
				setTimeout(function(){
			       window.location.href=ccnetpath+"draw/list.html";
			    },500);
			}
        }
    });
}


/*-------------------------------------------*/
var InterValObj; //timer变量，控制时间  
var count = 60; //间隔函数，1秒执行  
var curCount;//当前剩余秒数  
function sendMessage() {
	curCount = count;
	var phone = $("#mobile").val();//手机号码 
	var checkCode=$("#captcha").val();
	if (phone != "") {
		if (!phone.match(/^(((1[3|5|6|7|8|9][0-9]{1}))+\d{8})$/)) {
			M._alert("手机号不正确");
		} else if(checkCode !=""){
			//设置button效果，开始计时  
			$("#btnSendCode").attr("disabled", "true");
			$("#btnSendCode").val(curCount + "秒后重新获取");
			InterValObj = window.setInterval(setRemainTime, 1000); //启动计时器，1秒执行一次  
			//向后台发送处理数据  
			$.ajax({
	            url: ccnetpath+"mobile/code/send",
	            type: 'post',
	            cache: false,
	            data: {"mobile":phone,"captcha":checkCode,"tp":"1",stime:new Date().getTime()},
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
		$("#btnSendCode").val(curCount + "秒后重新获取");
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
    var phone = $("#mobile").val();
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

