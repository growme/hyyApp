$(function () {
    var i = 0; //设置当前页数
    var columnId=$("#columnId").val();
    var basePath=$("#basePath").val();
    var url=basePath+"article/page";
    var kz=true;//控制没有数据继续触发下拉加载
    getData();
    function getData(){
    	if(!isqqBrowser()){
            //$(".tc_news2").show();
        }
        $.ajax({
            type: "get",
            url: url,
            data: { columnId: columnId, page: i, basePath: basePath},
            dataType: "json",
            success: function (data) {
                if(data.res > 0){
                    $('#loading').hide();
                    $(".article-list").append(data.obj);
                }else{
                	kz=false;
                	$('#loading').hide();
            		$(".div_null").show();
                    $(".pullUpLabel").html("已经没有更多内容了!");
				}
            }
        });
        i++;
    }
    
    //定义鼠标滚动事件
    var timer = null;
    $(window).scroll(function(){
    	var scrollTop = $(document).scrollTop(); //滚动条滚动高度
		var documentH = $(document).height(); //滚动条高度 
		var windowH = $(window).height(); //窗口高度
        if (scrollTop  >= documentH - windowH-420) {
        	if(kz){
        		 $("#pullUpA").show();
                 $('#loading').show();
                 timer = setTimeout( function (){
                 	 getData();
                     clearTimeout(timer);
                 },800)
        	}
        }
    });
    
    
    var horizontalList = document.querySelector('.top-article-category .horizontal .list');
    var horizontalActive = horizontalList.getElementsByClassName('active').item(0);
    var verticalExpand = horizontalList.nextElementSibling;
    var verticalList = document.querySelector('.top-article-category .vertical');
    var verticalMark = verticalList.getElementsByClassName('masked').item(0);
    if (horizontalActive) {
        var left = horizontalActive.offsetLeft - (horizontalList.clientWidth - horizontalActive.clientWidth) / 2;
        (left > 0) && (horizontalList.scrollLeft = left);
    }
    if (verticalList) {
        verticalExpand.addEventListener('click', function() {
            verticalList.classList.remove('hide');
        }, true);
        verticalMark.addEventListener('click', function() {
            verticalList.classList.add('hide');
        }, true);
    }
    
    
    var clipboard = new Clipboard(".copyButton");
	clipboard.on("success", function(e) {
		layer.open({
			content : "复制成功!",
			btn : "我知道了",
		});
	});

	clipboard.on("error", function(e) {
		layer.open({
			content : "复制失败,请手动复制!",
			btn : "我知道了",
		});
	});
	
	function is_wexin () {
        var a = navigator.appVersion.toLowerCase();
        if (a.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    
    
    function isqqBrowser () {
        var a = navigator.appVersion.toLowerCase();
        if (a.match(/MQQBrowser/i) == "mqqbrowser") {
            return true;
        } else {
            return false;
        }
    }
    
    function getMoreUrl () {
        var layerLoading = layer.open({type: 2, content: "请稍等~"});
        $.ajax({
            type:"POST",
            data:{
            	"c":$("#columnId").val(),
            	"tm":new Date().getTime()
            },
            url:ccnetpath + "/article/url/getMoreUrl",
            success: function (result) {
            	var res = result.res;
            	var obj = result.obj;
            	if(res==200 && obj!=null){
            		var html = "<ul id='cp' style='-webkit-user-select:auto' contenteditable='true'>";
                    var i    = 0;
                    $.each(obj, function (index, vo) {
                        i+=1;
                        html += "<li class='_clearfix'><p>"+vo.title+"</p><p>"+vo.url+"</p></li>";
                    });
                    $(".shortcut_channel h1").text("批量复制(前"+i+"篇)　小技巧：长按指纹区域可全选");
                    html += "</ul>";
                    $(".lianjie").html(html);
                    layer.close(layerLoading);
                    $(".copyButton").show();
            	}
                
            },
            error: function () {
            	M._alert("获取失败，请重试");
            	layer.close(layerLoading);
            }
        });
    };
    
    
    $("#getUrlButton").on("click", function () {
        getMoreUrl();
    });
    
});