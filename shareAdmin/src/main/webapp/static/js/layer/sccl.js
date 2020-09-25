/*左侧菜单点击*/
$(".side-menu").on('click', 'li a', function(e) {
	var animationSpeed = 300;
	var $this = $(this);
	var checkElement = $this.next();
	if (checkElement.is('.menu-item-child') && checkElement.is(':visible')) {
	  checkElement.slideUp(animationSpeed, function() {
		checkElement.removeClass('menu-open');
	  });
	  checkElement.parent("li").removeClass("active");
	} else if ((checkElement.is('.menu-item-child')) && (!checkElement.is(':visible'))) {//如果菜单是不可见的
	  //获取上级菜单
	  var parent = $this.parents('ul').first();
	  //从父级开始找所有打开的菜单并关闭
	  var ul = parent.find('ul:visible').slideUp(animationSpeed);
	  //在父级中移出menu-open标记
	  ul.removeClass('menu-open');
	  //获取父级li
	  var parent_li = $this.parent("li");
	  //打开菜单时添加menu-open标记
	  checkElement.slideDown(animationSpeed, function() {
		//添加样式active到父级li
		checkElement.addClass('menu-open');
		parent.find('li.active').removeClass('active');
		parent_li.addClass('active');
	  });
	}
	//防止有链接跳转
	e.preventDefault();
	if($this.data("index")!="0"){
   	  addIframe($this);
	}else{
	  $('.tab-nav-content').children("[data-id]:first").trigger("click");
	}
});

/*添加iframe*/
function addIframe(cur){
	var $this = cur;
	var h = $this.attr("href"),
		m = $this.data("index"),
		label = $this.find("span").html(),
		isHas = false;
	if (h == "" || $.trim(h).length == 0) {
		return false;
	}
	
	var fullWidth = $(window).width();
	if(fullWidth >= 750){
		$(".layout-side").show();
	}else{
		$(".layout-side").hide();
	}
	
	$(".content-tab").each(function() {
		if ($(this).data("id") == h) {
			if (!$(this).hasClass("active")) {
				$(this).addClass("active").siblings(".content-tab").removeClass("active");
				addTab(this);
			}
			isHas = true;
		}
	});
	if(isHas){
		$(".body-iframe").each(function() {
			if ($(this).data("id") == h) {
				$(this).show().siblings(".body-iframe").hide();
			}
		});
	}
	if (!isHas) {
		var tab = "<a href='javascript:;' class='content-tab active' data-id='"+h+"'>"+ label +" <i class='icon-font'>&#xe617;</i></a>";
		$(".content-tab").removeClass("active");
		$(".tab-nav-content").append(tab);
		var iframe = "<iframe class='body-iframe' name='iframe"+ m +"' width='100%' height='99%' src='"+ h +"' frameborder='0' data-id='"+ h +"' seamless></iframe>";
		$(".layout-main-body").find("iframe.body-iframe").hide().parents(".layout-main-body").append(iframe);
		addTab($(".content-tab.active"));
	}
	return false;
}


/*添加tab*/
function addTab(cur) {
	var prev_all = tabWidth($(cur).prevAll()),
		next_all = tabWidth($(cur).nextAll());
	var other_width =tabWidth($(".layout-main-tab").children().not(".tab-nav"));
	var navWidth = $(".layout-main-tab").outerWidth(true)-other_width;//可视宽度
	var hidewidth = 0;
	if ($(".tab-nav-content").width() < navWidth) {
		hidewidth = 0;
	} else {
		if (next_all <= (navWidth - $(cur).outerWidth(true) - $(cur).next().outerWidth(true))) {
			if ((navWidth - $(cur).next().outerWidth(true)) > next_all) {
				hidewidth = prev_all;
				var m = cur;
				while ((hidewidth - $(m).outerWidth()) > ($(".tab-nav-content").outerWidth() - navWidth)) {
					hidewidth -= $(m).prev().outerWidth();
					m = $(m).prev();
				}
			}
		} else {
			if (prev_all > (navWidth - $(cur).outerWidth(true) - $(cur).prev().outerWidth(true))) {
				hidewidth = prev_all - $(cur).prev().outerWidth(true);
			}
		}
	}
	$(".tab-nav-content").animate({
		marginLeft: 0 - hidewidth + "px"
	},"fast");
}

/*获取宽度*/
function tabWidth(tabarr) {
	var allwidth = 0;
	$(tabarr).each(function() {
		allwidth += $(this).outerWidth(true);
	});
	return allwidth;
}

/*左按钮事件*/
$(".btn-left").on("click", leftBtnFun);
/*右按钮事件*/
$(".btn-right").on("click", rightBtnFun);
/*全屏事件*/
$('.fullscreen').on('click', function () {
    if (!$(this).attr('fullscreen')) {
        $(this).attr('fullscreen', 'true');
        requestFullScreen();
    } else {
        $(this).removeAttr('fullscreen');
        exitFullscreen();
    }
});
/*全屏事件*/
$('.loginOut').on('click', function () {
	 showTConFirm("您确定注销登录？",function(){
       	var index = showTLoading2("注销中,请稍后...");
       	setTimeout(function(){
			closeLayer(index);
			window.location.href=ccnetpath+"/systemLogout";
		},700);
     });
});
//刷新页面
$('.tabReload').on('click', refreshTab);
//关闭当前
$('.tabCloseCurrent').on('click', function () {
	if($('.tab-nav-content').find('.active').attr('id')!="homeTab"){
		var url = $('.tab-nav-content').find('.active').attr('data-id');
	    $(".body-iframe").each(function() {
			if ($(this).data("id") == url) {
				$(this).remove();
				return false;
			}
		});
	    $('.tab-nav-content').find('.active i').trigger("click");
	    $('.tab-nav-content').children("[data-id]:last").trigger("click");
	}
});
//关闭所有
$('.tabCloseAll').on('click', function () {
	$('.tab-nav-content').children("[data-id]").not(":first").each(function () {
        $('.body-iframe[data-id="' + $(this).data('id') + '"]').remove();
        $(this).remove();
    });
    $('.tab-nav-content').children("[data-id]:first").each(function () {
        $('.body-iframe[data-id="' + $(this).data('id') + '"]').show();
        $(this).addClass("active");
    });
    $('.tab-nav-content').css("margin-left", "0");
});
//关闭其他
$('.tabCloseOther').on('click', closeOtherTabs);
/*选项卡切换事件*/
$(".tab-nav-content").on("click", ".content-tab", navChange);
/*选项卡关闭事件*/
$(".tab-nav-content").on("click", ".content-tab i", closePage);
/*选项卡双击关闭事件*/
$(".tab-nav-content").on("dblclick", ".content-tab", function(){
	if($(this).attr("id")!="homeTab"){
		var url = $('.tab-nav-content').find('.active').attr('data-id');
	    $(".body-iframe").each(function() {
			if ($(this).data("id") == url) {
				$(this).remove();
				return false;
			}
		});
	    $('.tab-nav-content').find('.active i').trigger("click");
	    $('.tab-nav-content').children("[data-id]:last").trigger("click");
	}
});


/*左按钮方法*/
function leftBtnFun() {
	var ml = Math.abs(parseInt($(".tab-nav-content").css("margin-left")));
	var other_width = tabWidth($(".layout-main-tab").children().not(".tab-nav"));
	var navWidth = $(".layout-main-tab").outerWidth(true)-other_width;//可视宽度
	var hidewidth = 0;
	if ($(".tab-nav-content").width() < navWidth) {
		return false;
	} else {
		var tabIndex = $(".content-tab:first");
		var n = 0;
		while ((n + $(tabIndex).outerWidth(true)) <= ml) {
			n += $(tabIndex).outerWidth(true);
			tabIndex = $(tabIndex).next();
		}
		n = 0;
		if (tabWidth($(tabIndex).prevAll()) > navWidth) {
			while ((n + $(tabIndex).outerWidth(true)) < (navWidth) && tabIndex.length > 0) {
				n += $(tabIndex).outerWidth(true);
				tabIndex = $(tabIndex).prev();
			}
			hidewidth = tabWidth($(tabIndex).prevAll());
		}
	}
	$(".tab-nav-content").animate({
		marginLeft: 0 - hidewidth + "px"
	},
	"fast");
}

/*右按钮方法*/
function rightBtnFun() {
	var ml = Math.abs(parseInt($(".tab-nav-content").css("margin-left")));
	var other_width = tabWidth($(".layout-main-tab").children().not(".tab-nav"));
	var navWidth = $(".layout-main-tab").outerWidth(true)-other_width;//可视宽度
	var hidewidth = 0;
	if ($(".tab-nav-content").width() < navWidth) {
		return false;
	} else {
		var tabIndex = $(".content-tab:first");
		var n = 0;
		while ((n + $(tabIndex).outerWidth(true)) <= ml) {
			n += $(tabIndex).outerWidth(true);
			tabIndex = $(tabIndex).next();
		}
		n = 0;
		while ((n + $(tabIndex).outerWidth(true)) < (navWidth) && tabIndex.length > 0) {
			n += $(tabIndex).outerWidth(true);
			tabIndex = $(tabIndex).next();
		}
		hidewidth = tabWidth($(tabIndex).prevAll());
		if (hidewidth > 0) {
			$(".tab-nav-content").animate({
				marginLeft: 0 - hidewidth + "px"
			},"fast");
		}
	}
}

/*选项卡切换方法*/
function navChange() {
	if (!$(this).hasClass("active")) {
		var k = $(this).data("id");
		$(".body-iframe").each(function() {
			if ($(this).data("id") == k) {
				$(this).show().siblings(".body-iframe").hide();
				return false;
			}
		});
		$(this).addClass("active").siblings(".content-tab").removeClass("active");
		addTab(this);
	}
}

//开启全屏
function requestFullScreen () {
    var de = document.documentElement;
    if (de.requestFullscreen) {
        de.requestFullscreen();
    } else if (de.mozRequestFullScreen) {
        de.mozRequestFullScreen();
    } else if (de.webkitRequestFullScreen) {
        de.webkitRequestFullScreen();
    }
}
//关闭全屏
function exitFullscreen () {
    var de = document;
    if (de.exitFullscreen) {
        de.exitFullscreen();
    } else if (de.mozCancelFullScreen) {
        de.mozCancelFullScreen();
    } else if (de.webkitCancelFullScreen) {
        de.webkitCancelFullScreen();
    }
}
//刷新页面
function refreshTab () {
    var currentId = $('.tab-nav-content').find('.active').attr('data-id');
    var target = $('.body-iframe[data-id="' + currentId + '"]');
    var url = target.attr('src');
    //$.loading(true);
    target.attr('src', url).load(function () {
        //$.loading(false);
    });
}

function closeAllTabs () {
    $('.tab-nav-content').children("[data-id]").find('.icon-font').each(function () {
        $('.body-iframe[data-id="' + $(this).data('id') + '"]').remove();
        $(this).parents('a').remove();
    });
    $('.tab-nav-content').children("[data-id]:first").each(function () {
        $('.body-iframe[data-id="' + $(this).data('id') + '"]').show();
        $(this).addClass("active");
    });
    $('.tab-nav-content').css("margin-left", "0");
}


function closeOtherTabs () {
	$('.tab-nav-content').children("[data-id]").not(":first").not(".active").each(function () {
        $('.body-iframe[data-id="' + $(this).data('id') + '"]').remove();
        $(this).remove();
    });
    $('.tab-nav-content').css("margin-left", "0");
}

/*选项卡关闭方法*/
function closePage() {
	var url = $(this).parents(".content-tab").data("id");
	var cur_width = $(this).parents(".content-tab").width();
	if ($(this).hasClass("active") || $(this).parents(".content-tab").hasClass("active")) {
		
		//$(this).parents(".content-tab").remove();
		//$(this).remove();
		
		if ($(this).parents(".content-tab").next(".content-tab").size()) {
			var next_url = $(this).parents(".content-tab").next(".content-tab:eq(0)").data("id");
			$(this).parents(".content-tab").next(".content-tab:eq(0)").addClass("active");
			$(".body-iframe").each(function() {
				if ($(this).data("id") == next_url) {
					$(this).show().siblings(".body-iframe").hide();
					return false;
				}
			});
			var n = parseInt($(".tab-nav-content").css("margin-left"));
			if (n < 0) {
				$(".tab-nav-content").animate({
					marginLeft: (n + cur_width) + "px"
				},
				"fast");
			}
			$(this).parents(".content-tab").remove();
			$(".body-iframe").each(function() {
				if ($(this).data("id") == url) {
					$(this).remove();
					return false;
				}
			});
		}
		if ($(this).parents(".content-tab").prev(".content-tab").size()) {
			var prev_url = $(this).parents(".content-tab").prev(".content-tab:last").data("id");
			$(this).parents(".content-tab").prev(".content-tab:last").addClass("active");
			$(".body-iframe").each(function() {
				if ($(this).data("id") == prev_url) {
					$(this).show().siblings(".body-iframe").hide();
					return false;
				}
			});
			$(this).parents(".content-tab").remove();
			$(".body-iframe").each(function() {
				if ($(this).data("id") == url) {
					$(this).remove();
					return false;
				}
			});
		}
	} else {
		$(this).parents(".content-tab").remove();
		$(".body-iframe").each(function() {
			if ($(this).data("id") == url) {
				$(this).remove();
				return false;
			}
		});
		addTab($(".content-tab.active"));
	}
	return false;
}


/*循环菜单*/
function initMenu(menu,parent){
	var menu = JSON.parse(menu);
	for(var i=0; i<menu.length; i++){   
		var item = menu[i];
		var str = "";
		try{
			if(item.childs == ""){
				str = "<li><a href='"+item.url+"' data-index='"+item.index+"'><i class='"+item.icon+"'></i><span>"+item.name+"</span>";
				if(item.orderCount!=null && item.orderCount!=undefined && item.orderCount>=0){
					str += "<span class='count_label' id='menu_cdata_"+item.stepId+"'>"+item.orderCount+"</span>";
				}
				str += "</a></li>";
				$(parent).append(str);
			}else{
				if(item.url!="#"){
					str = "<li><a href='"+item.url+"' data-index='"+item.index+"'><i class='"+item.icon+"'></i><span>"+item.name+"</span></a>";
				}else{
					str = "<li><a href><i class='"+item.icon+"'></i><span>"+item.name+"</span><i class='icon-font icon-right'>&#xe60b;</i></a>";
				}
				str +="<ul class='menu-item-child' id='menu-child-"+item.id+"'></ul></li>";
				$(parent).append(str);
				var childParent = $("#menu-child-"+item.id);
				initMenu(item.childs,childParent);
			}
		}catch(e){
			
		}
	}
}



/*头部下拉框移入移出*/
$(document).on("mouseenter",".header-bar-nav",function(){
	$(this).addClass("open");
});
$(document).on("mouseleave",".header-bar-nav",function(){
	$(this).removeClass("open");
});

/*左侧菜单展开和关闭按钮事件*/
$(document).on("click",".layout-side-arrow",function(){
	if($(".layout-side").hasClass("close")){
		$(".layout-side").removeClass("close");
		$(".layout-main").removeClass("full-page");
		$(".layout-footer").removeClass("full-page");
		$(this).removeClass("close");
		$(".layout-side-arrow-icon").removeClass("close");
	}else{
		$(".layout-side").addClass("close");
		$(".layout-main").addClass("full-page");
		$(".layout-footer").addClass("full-page");
		$(this).addClass("close");
		$(".layout-side-arrow-icon").addClass("close");
	}
});

/*头部菜单按钮点击事件*/
$(".header-menu-btn").click(function(){
	$(".layout-side").removeClass("close");
	$(".layout-main").removeClass("full-page");
	$(".layout-footer").removeClass("full-page");
	$(".layout-side-arrow").removeClass("close");
	$(".layout-side-arrow-icon").removeClass("close");
	
	$(".layout-side").slideToggle();
});

/*左侧菜单响应式*/
$(window).resize(function() {  
	var width = $(this).width();  
	if(width >= 750){
		$(".layout-side").show();
	}else{
		$(".layout-side").hide();
	}
	initDropMenuWidth();
});

//显示右键菜单
function showCTXMenu(showbtnid,showboxid) {
	var showmenubtn = $(showbtnid);
	var showmenubox = $(showboxid);
	var t;
	showmenubtn.mouseenter(function(e){
		var thish = $(this).height();
		var offset = $(this).offset();
		var tipx = offset.left;
		var tipy = offset.top+thish-1;
		showmenubox.show().css("left",tipx-73).css("top",tipy+2);
		t= setTimeout(function(){showmenubox.hide();},2000);
	  });
	showmenubox.mouseenter(function(){
		clearTimeout(t);
	});
	showmenubox.mouseleave(function(){
		$(this).hide();
	});
	showmenubox.click(function(){
		$(this).hide();
	});
 }

//初始化宽度
function initDropMenuWidth(){
	
	$(".header-bar-nav").each(function() {
	    var $this = $(this);
	    var this_id = $this.prop('id');
	    if(this_id=='top1'){
		    var nav_width = $this.width();
		    if(nav_width){
		  	  $("#"+this_id+"_sub").width(nav_width);
		  	  $("#"+this_id+"_sub li").width(nav_width);
		    }
	    }
	});
}

/*皮肤选择*/
$(".dropdown-skin li a").click(function(){
	var v = $(this).attr("data-val");
	var hrefStr=$("#layout-skin").attr("href");
	var hrefRes=hrefStr.substring(0,hrefStr.lastIndexOf('nskins/'))+'nskins/'+v+'.skin.css';
	$(window.frames.document).contents().find("#layout-skin").attr("href",hrefRes);
	setCookie("scclui-skin", v);
});

/*获取cookie中的皮肤*/
function getSkinByCookie(){
	var v = getCookie("scclui-skin");
	var hrefStr=$("#layout-skin").attr("href");
	if(v == null || v == ""){
		v="qingxin";
	}
	if(hrefStr != undefined){
		var hrefRes=hrefStr.substring(0,hrefStr.lastIndexOf('nskins/'))+'nskins/'+v+'.skin.css';
		//$("#skin").attr("href",hrefRes);
		$(window.frames.document).contents().find("#layout-skin").attr("href",hrefRes);
	}
}

/*随机颜色*/
function getMathColor(){
	var arr = new Array();
	arr[0] = "#ffac13";
	arr[1] = "#83c44e";
	arr[2] = "#2196f3";
	arr[3] = "#e53935";
	arr[4] = "#00c0a5";
	arr[5] = "#16A085";
	arr[6] = "#ee3768";

	var le = $(".menu-item > a").length;
	for(var i=0;i<le;i++){
		var num = Math.round(Math.random()*5+1);
		var color = arr[num-1];
		$(".menu-item > a").eq(i).find("i:first").css("color",color);
	}
}

/*
  初始化加载
*/
$(function(){
	/*获取皮肤*/
	getSkinByCookie();
	if(version=='V5'){
	   getJsonMenuTree();
	}
	function getJsonMenuTree(){
	    var param = "menuCode="+menuCode+"&isReload="+isReload+"&now="+new Date().getTime();
	    $.ajax({
			   type:"POST",
			   cache:false,
			   async:false,
			   url:ccnetpath+"/backstage/menu/getJsonMenu",
			   data:param,
			   dataType:'json',
			   success:function(data, textStatus) {
	    			if ("1" != data.res) {
	    				showTErrMsg(data.resMsg);
	    				if((data.resMsg).indexOf("未登录")>0){
	    				   setTimeout(function(){
								window.location.href=ccnetpath+"/loginIndex";
							},1500);
	    				}
	    				if((data.resMsg).indexOf("认证失败")>0){
	    				   setTimeout(function(){
								window.location.href=ccnetpath+"/goreglicense";
							},1500);
	    				}
	    			}else{
	                     //alert(data.obj);
	                     initMenu(data.obj,$(".side-menu"));
	                 	$(".side-menu > li").addClass("menu-item");
	    			}
	    	    }
	     });
	}
	
	/*获取菜单icon随机色*/
	//getMathColor();
	initDropMenuWidth();
	showCTXMenu("#mdropdown","#menubox");
}); 