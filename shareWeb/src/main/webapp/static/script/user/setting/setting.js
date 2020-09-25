$(function() {
	
	$("#saveBtn").click(function (event) {
		saveSetting();
    });
	
});


//提交修改
function saveSetting(){
	
	var mobile = $("#mobile").val();
	if ($("#mobile").val() == '') {
		M._alert("请输入您的手机号码！");
		return false;
	}
	var qqNum = $("#qqNum").val();
	if ($("#qqNum").val() == '') {
		M._alert("请输入您的QQ号码！");
		return false;
	}
	var payAccount = $("#payAccount").val();
	if ($("#payAccount").val() == '') {
		M._alert("请输入您的支付宝账号！");
		return false;
	}
	var accountName = $("#accountName").val();
	if ($("#accountName").val() == '') {
		M._alert("请输入您的支付宝认证姓名！");
		return false;
	}
	var wechat = $("#wechat").val();
	if ($("#wechat").val() == '') {
		M._alert("请输入您的微信账号！");
		return false;
	}
	
	$.ajax({
        url: ccnetpath+"user/setting/save?tm="+new Date().getTime(),
        type: 'POST',
        cache: false,
        data:{
          'mobile':mobile,
          'qq_num':qqNum,
          'wechat':wechat,
          'pay_account':payAccount,
          'account_name':accountName,
          'tm':new Date().getTime()
        },
        dataType: 'json',
        success: function (data) {
       	    if ("1000" != data.apicode) {
                M._alert(data.msg);
			} else {
				M._alert(data.msg);
				setTimeout(function(){
			       window.location.href = ccnetpath+"user/setting.html";
			    },500);
		    }
        }
    });	
}