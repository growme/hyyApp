$(function() {
	var jrqd = $("#jrqd").val();
	if(jrqd==1){
		 $("#signBtn").css("background","#666666");
	     $("#signBtn").html("今日已成功签到");
	     $(".qianda").hide();
	}
	
	$("#signBtn").click(function (event) {
    	bonus();
    });
	
});


//每日签到
function bonus(){
	$.ajax({
        url: ccnetpath+"sign/bonus?tm="+new Date().getTime(),
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
       	    if ("1000" != data.apicode) {
                M._alert(data.msg);
                $("#signBtn").unbind("click");
			} else {
				M._alert(data.msg);
				setTimeout(function(){
					window.location.reload();
			    },500);
		    }
        }
    });	
}