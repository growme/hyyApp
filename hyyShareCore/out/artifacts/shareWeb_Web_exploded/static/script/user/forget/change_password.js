$(function() {
	
	$("#btnSendCode").click(function (event) {
    	sendMessage();
    });
	
    $("#changeSubmit").click(function (event) {
    	changepwd();
    });
    
});

function changepwd(){
	var n1 = $("#password1").val();
	var n2 = $("#password1").val();
	var acc = $("#account").val();
	var token = $("#token").val();
	if(n1==""){
		toastr.error("密码不能为空！");
	}else if(n1.length <6 || n1.length>20){
		toastr.error("密码最少6位，请重新输入");
	}else if(n2==""){
		toastr.error("再次输入不能为空！");
	}else if(n1 != n2){
		toastr.error("两次输入的密码不相同！");
	}else{
		
		$.ajax({
            url: ccnetpath+"user/setpwd/save",
            type: 'post',
            cache: false,
            data: {
            	"mobile":acc,
            	"token":token,
            	"password":$.md5(n2),
            	"stime":new Date().getTime()
            },
            dataType: 'json',
            success: function (data) {
           	  if ("1000" != data.apicode) {
                M._alert(data.msg);
   			  } else {
   			    M._alert(data.msg);
   				setTimeout(function(){
 				   window.location.href=ccnetpath+"user/login";
 				},500);
   			  }
            }
        });
		
	}
}

