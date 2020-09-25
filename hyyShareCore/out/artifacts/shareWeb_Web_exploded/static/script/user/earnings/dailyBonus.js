$(function() {
    $(".m4").click(function (event) {
    	bonus();
    });
});

//提现
function bonus(){
	$.ajax({
        url: ccnetpath+"earnings/dailyBonus",
        type: 'post',
        cache: false,
        dataType: 'json',
        success: function (data) {
       	 if ("1000" != data.apicode) {
            M._alert(data.msg);
			} else {
				M._alert(data.msg);
				setTimeout(function(){
			   window.location.href=ccnetpath+"home/index.html";
			},500);
			   }
        }
    });	
}

