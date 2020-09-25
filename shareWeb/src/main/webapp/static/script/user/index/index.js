$(function() {
	
	$("#loginOut").click(function (event) {
    	loginOut();
    });
	getCashList();
	getNoticeList();
	checkUser();
});


window.onresize = function (){
   var _width = $(window).width();
   var _height = $(window).height();
   if(_width>500 && _height>800){
	   $("#scomment").height((_height/2-60)+"px");
   }else{
	   $("#scomment").height("200px");
   }
}

//注销登录
function loginOut(){
layer.open({
    content: '您确定要注销登录吗？',
    btn: ['确定', '取消'],
    yes: function(index){
       window.location.href=ccnetpath+"/user/loginout.html";
       layer.close(index);
    }
  });
}

//加载信息
function getuser(){
	
  $.post('/Index/getuser.html',{time:new Date().getTime()},
	 function(data){
        if(data.userid==0){
            window.location.href="/login/login.html";
        }else{
            if(data.flag==1){
                $('.user-flag').html('');
            }else if(data.flag==2){
                $('.user-flag').html('');
            }            
            $('.i-user').text(data.userid);
            $('.income_money').text(data.money);
            $('.i-tomoney').text(data.tomoney);
            $('.i-tointo').text(data.tointo);
            $('.i-tocash').text(data.tocash);
            $('.i-nowcash').text(data.nowcash);
            $('.i-nowmoney').text(data.nowmoney);
            $('.i-tounder').text(data.tounder);
     }
  });
	
} 

//每日红包
function signin(){
    layer.closeAll();
    $.post('/Index/signin.html',{time:new Date().getTime()},function(data){
        if(data.status==200){
            layer.msg(data.msg,{icon:1,time:2000},function(){
               window.location.reload();
            });
        }else{
            layer.msg(data.msg,{icon:4,time:1500});
        }
    },'json');
}


//加载提现数据
function getCashList(){
  var _width = $(window).width();
  var _height = $(window).height();
  $.ajax({
     url : ccnetpath+'/home/cash/list',
     type :'POST',
     dataType:"json",
     success:function(json){
    	 var apicode = json.apicode;
    	 var obj = json.obj;
         var str = '';
         if(obj.length>1){
        	  if(_width>500 && _height>800){
        		  str+='<div id="scomment" class="comment" style="height:'+(_height/2-60)+'px">';
        	  }else{
        		  str+='<div class="comment">';
        	  }
        	  
              str+='<div class="comment-inner">';
              for(var i= 0; i<obj.length; i++){
                 str+=' <div class="comment-content iconfont icon-notice"> 恭喜'+obj[i].phone+'用户在'+obj[i].time+'秒前提现了<span>'+obj[i].price+'</span>元 </div>';
              }
              str+='</div>'
              str+='</div>'
         }
         $(".yzk_cashlist").append(str);
         scrollComment();
     }
 });
}


//获取公告信息
function getNoticeList(){
  $.ajax({
     url : ccnetpath+'/notice/least/list',
     type :'POST',
     dataType:"json",
     success:function(json){
    	 var apicode = json.apicode;
    	 var obj = json.obj.nlist;
         var str = '';
         if(obj.length>1){
        	 str+='<ul>';
             for(var i= 0; i<obj.length; i++){
                 str+='<li class="iconfont icon-notice">';
                 str+='<a href='+ccnetpath+'/notice/detail.html?nid='+obj[i].noticeId+'> ' +obj[i].noticeTitle+'</a>';
                 str+='</li>';
             }
             str+='</ul>'
         }
         $("#noticerecord").append(str);
         indexAutoplay( '#noticerecord', 500 , 4000);
         //处理公告提示弹出
         var least_notice = json.obj.first;
         if(least_notice!=''){
           var noticeId = $.cookie('noticeId');
    	   if (!noticeId) {
    	       $.Layer({
    	           title: '最新公告！',
    	           content: '<span style="text-align: left">'+least_notice.noticeTitle+'</span>',
    	           button: '我知道了',
    	           yes: function () {
    	               $.cookie('noticeId', 'yes', {expires: 1});
    	               $.closeLayer();
    	           }
    	       });
    	   }
         }
         
     }
 });
}


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

//时间格式化
function formatMsgTime(timespan) {
    var dateTime = new Date(timespan);
    var year = dateTime.getFullYear();
    var month = dateTime.getMonth() + 1;
    var day = dateTime.getDate();
    var hour = dateTime.getHours();
    var minute = dateTime.getMinutes();
    var second = dateTime.getSeconds();
    var now = new Date();
    var now_new = Date.parse(now.toDateString());  //typescript转换写法

    var milliseconds = 0;
    var timeSpanStr;

    var milliseconds = now_new - timespan;
    if (milliseconds <= 1000 * 60 * 1) {
        timeSpanStr = Math.round((milliseconds / Math.round(milliseconds) * second )) + '秒前';
    } else if (1000 * 60 * 1 < milliseconds && milliseconds <= 1000 * 60 * 60) {
        timeSpanStr = Math.round((milliseconds / (1000 * 60))) + '分钟前';
    } else if (1000 * 60 * 60 * 1 < milliseconds && milliseconds <= 1000 * 60 * 60 * 24) {
        timeSpanStr = Math.round(milliseconds / (1000 * 60 * 60)) + '小时前';
    } else if (1000 * 60 * 60 * 24 < milliseconds && milliseconds <= 1000 * 60 * 60 * 24 * 15) {
        timeSpanStr = Math.round(milliseconds / (1000 * 60 * 60 * 24)) + '天前';
    } else if (milliseconds > 1000 * 60 * 60 * 24 * 15 && year == now.getFullYear()) {
        timeSpanStr = rund(1,10)+'天前';
    } else {
        timeSpanStr = rund(1,58)+'分钟前';;
    }
    
    return timeSpanStr;
};

 /*
 * 获取min-max的随机数
 * 如果只传一个参数那么 返回0-max 的随机数
 * 如果穿两个参数那么返回 min-max 的随机数
 */
function rund (){
    var len = arguments.length;
    switch ( len ){
        case 1:
            var length = arguments[0]+1;
            return Math.floor(Math.random()*length);
        case 2:
            var min = arguments[0] ,
                max = arguments[1] ;
            if( arguments[0]-arguments[1]>0 ){
                min = arguments[1];
                max = arguments[0];
            }
       return Math.floor( Math.random()*( max-min+1 ) + min );
    }
}

/*
* 返回随机数组
*   传入一个数组 这个数组打乱后的一个随机数组
*   array
*/
function roundArray( array ){
	var m = array.length, t, i;
	// 如果还剩有元素…
	while (m) {
	    // 随机选取一个元素…
	    i = Math.floor(Math.random() * m--);
	    // 与当前元素进行交换
	    t = array[m];
	    array[m] = array[i];
	    array[i] = t;
    }
    return array;
}


var scrollComment = function(){
	var comment = $(".comment");
	var box = $(".comment-inner");
	var boxClone = box.clone(false);
	comment.append(boxClone);

	setInterval(function(){
		try{
		 if (boxClone[0].offsetHeight - comment[0].scrollTop <= 0) {
            comment[0].scrollTop -= box[0].offsetHeight;
         } else {
            comment[0].scrollTop++;
         }
		}catch(e){}
	},60);
}

var kz=false;
function scrollHandler(){
    var scrollTop = $(document).scrollTop(); //滚动条滚动高度
    var documentH = $(document).height();  //滚动条高度 
    var windowH = $(window).height(); //窗口高度
    if (scrollTop >= documentH - windowH){
        if (i===0){
            $(".yzk_loading").hide();
            getcash();
        }else if(i>=5){
        	$(".yzk_loading").hide();
        	kz=false;
        }else{
            getcash();
            $(".yzk_loading").show();            	
        }
    }
};

//定义鼠标滚动事件
$(window).scroll(function(){
    if(kz){scrollHandler();}
});                