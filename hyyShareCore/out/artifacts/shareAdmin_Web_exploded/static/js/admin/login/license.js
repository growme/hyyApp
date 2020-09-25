$(function () {
	
	$("#licenseBtn").click(function () {
		if("" == $("#license").val()){
			showTips("授权序列号不能为空！","license");
			$("#license").focus();
		}else if($("#license").val().length<32){
			showTips("授权序列号长度不正确，一般为大于32位的加密字符串！","license");
			$("#license").focus();
		}else{
			var license = $("#license").val();
			showConFirm('确定的要提交认证吗？',function(){
				var index = showLoading("授权认证中,请稍后...");
			    $("#licenseForm").ajaxSubmit({
			        type: 'POST',
			        dataType: 'json',
			        url: ccnetpath + "/registerlicense",
			        success: function(data) {
			        	if ("1" != data.res) {
			   				showErrMsg(data.resMsg);
			   				closeLayer(index);
			   			}else{
			   				showSucMsg(data.resMsg);
			   				$("#licenseBtn").unbind('click');
							setTimeout(function(){
								window.location.href=ccnetpath+"/backstage/index";
							},500);
			   			}
			        }
			     });
	        });
		}
	});
	
	
});