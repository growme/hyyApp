$(function() {
	user_invited_fun();
	getCashList();
});

function user_invited_fun(){
	//处理点击复制文案按钮事件
	var btn = document.getElementById('fzwa');
    var clipboard = new Clipboard(btn);//实例化
    //复制成功执行的回调，可选
    clipboard.on('success', function(e) {
        //alert('复制成功')
        $.Layer({
            content: '<span style="line-height: 1.8;">复制成功,将复制的内容发送给您的好友吧！</span>',
            button: '朕知道了',
            yes: function(){
                $.closeLayer();
            }
        });
    });
    //复制失败执行的回调，可选
    clipboard.on('error', function(e) {
        $.Layer({
            title: '提示信息' ,
            content: '复制失败,请手动复制!',
            button: '朕知道了',
            yes: function(){
                $.closeLayer();
            }
        });
    });

}

function indexAutoplay (target,speed,interspeed){
    var Otop = $(target+' '+'li').eq(0).height();
    var timer = null;
    timer = setInterval(autoplay,interspeed);
    function autoplay(){
        $(target+' '+'ul').animate({'top':-Otop},speed,function(){
            $(target+' '+'ul').append($(target+' '+'li').first());
            $(target+' '+'ul').css('top','0');
        });
    }
}


//加载提现数据
function getCashList(){
  $.ajax({
     url : ccnetpath+'/home/cash/list',
     type :'POST',
     dataType:"json",
     success:function(json){
    	 var apicode = json.apicode;
    	 var obj = json.obj;
         var str = '';
         if(obj.length>1){
        	 str+='<ul>';
             for(var i= 0; i<obj.length; i++){
                 str+='<li class="iconfont icon-notice">';
                 str+='恭喜：'+obj[i].phone+' &nbsp;用户通过收徒获得<span style="color: #ff0000;">'+obj[i].price+'</span>元';
                 str+='</li>';
             }
             str+='</ul>'
         }
         $("#cashrecord").append(str);
         indexAutoplay( '#cashrecord', 500 , 2000 );
     }
 });
}

var invited = $.cookie('invited');
if(!invited){
    $.Layer({
        title: '温馨提示！' ,
        content: '<span style="text-align: left"><em style="color: #ff0000">严禁刷徒，收自己为徒，一经发现将冻结账户资金不予提现！ 建议在QQ浏览器打开本网站</em></span>',
        button: '朕知道了',
        yes: function(){
            $.cookie('invited', 'yes' ,{ expires: 1 });
            $.closeLayer();
        }
    });
}
