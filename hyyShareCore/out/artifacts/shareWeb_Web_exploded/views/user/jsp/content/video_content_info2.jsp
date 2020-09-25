<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/redirect301.jsp"%>
<!DOCTYPE html>
<html lang="en" data-dpr="1" style="font-size: 40px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>${content.contentTitle}</title>
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<link type="text/css" rel="stylesheet"
	href="${basePath}static/css/red/public.css">
<link rel="stylesheet" type="text/css"
	href="${basePath}static/appshare/nativeShare.css">
<link type="text/css" rel="stylesheet"
	href="${basePath}static/css/show.css">
<link type="text/css" rel="stylesheet"
	href="${basePath}static/css/main.min.css">
<link rel="stylesheet" type="text/css"
	href="${basePath}static/css/article.css">
<style type="text/css">
.show-more {
	width: 100%;
	text-align: center;
	color: #3faaff;
	margin-left: -5px;
	box-shadow: 10px -30px 30px 10px #ddd;
	padding: 10px;
	background: white;
}

.show-more>p {
	font-size: 14px;
}

.show-more>img {
	width: 220px;
}

.tuijian {
	position: relative;
	background: white;
	border-bottom: 1px dashed gainsboro;
	padding: 10px;
	overflow: hidden;
}

.tuijian img {
	width: 110px;
	height: 85px;
	float: right;
}

.tuijian h3 {
	position: absolute;
	top: 20px;
	left: 10px;
	right: 120px;
	font-size: 16px;
	line-height: 1.5em;
}

.tuijian span {
	left: 10px;
	bottom: 20px;
	position: absolute;
	color: gray;
}

.article-list .item h3 {
	font-size: 16px;
}

.fixtop {
	position: fixed;
	top: 0;
	display: none;
	z-index: 999;
}

.fixtop img {
	width: 100%;
}

.fixbutton {
	position: fixed;
	bottom: 0;
	z-index: 999;
}

.fixbutton img {
	width: 100%;
}
</style>
</head>

<body class="yzk_bg">
	<c:if test="${!empty regUrl && isWeixin==1 && closeNovelAdv!=1}">
		<!--  
	<div class="top-banner" id="top-banner">
		<a href="${regUrl}" class="top-banner-item">
			<span>
			 <img class="image logo" src="${basePath}static/images/logo.png">
		    </span>
			<div class="banner-label">
				<p class="title-main" style="color: #f0ee22">抢红包神器</p>
				<p class="title-sub" style="color: #f0ee22">今天领红包，明天去买房！</p>
			</div>
			<div class="open" style="background-color: #ff7500">点击赚钱</div>
		</a>
	</div>-->
	</c:if>
	<!--  
   <div style="width:100%;overflow:hidden;height: auto;position: relative;z-index:0;margin-top:${(isWeixin==1 && closeNovelAdv!=1)?50:0}px;">
   -->

	<c:if test="${!empty float_advert && closeNovelAdv!=1}">
		<div class="fixtop">
			<a onclick="update_ad_href(this,${float_advert.adId})"> <img
				src="${float_advert.adShowPic}">
			</a>
			<div class="gg_close"></div>
		</div>
	</c:if>

	<c:if test="${!empty float_bottom && closeNovelAdv!=1}">
		<div class="fixbutton">
			<a onclick="update_ad_href(this,${float_bottom.adId})"> <img
				src="${float_bottom.adShowPic}">
			</a>
			<div class="gg_close"></div>
		</div>
	</c:if>

	<div class="yzk_artice_con" id="ctx_content">
		<c:if test="${!empty single_pic_random_adverts && closeNovelAdv!=1}">
			<div class="tuijian"
				onclick="update_ad_href(this,${single_pic_random_adverts.adId})">
				<img src="${single_pic_random_adverts.adShowPic}">
				<h3>${single_pic_random_adverts.jsTitle}</h3>
				<span>${single_pic_random_adverts.adTagName}</span>
			</div>
		</c:if>

		<div class="artice_top_c01" id="art-h1">${content.contentTitle}</div>
		<div class="artice_top_ctime" style="margin-bottom: 5px;">
			<button class=".klbtn" id="chatCode" style="opacity: 0"
				data-clipboard-action="copy" data-clipboard-text="${chatCode}"></button>
			<span class="ctime-l"><fmt:formatDate
					value="${content.createTime}" type="both" pattern="yyyy-MM-dd" /></span>
			<c:if
				test="${!empty content.weixinLink && !empty content.weixinName}">
				<span class="wechat"><a href="${content.weixinLink}"
					target="_blank">${content.weixinName}</a></span>
				<c:if test="${content.contentType!=2}">
					<span class="gzimg"><img
						src="${basePath}static/images/gzhm.gif"></span>
				</c:if>
			</c:if>
		</div>
		<c:if test="${content.contentType==2}">
			<div class="zhi">
				<img style="display: block; margin: 5px 0 10px 0; width: 100%;"
					src="${basePath}static/images/read/gzgif/lzgif${gzgf}.gif?v=2">
			</div>
		</c:if>
		<c:if test="${!empty banner_adverts && closeNovelAdv!=1}">
			<section class="container">
				<div class="list_dd bor_b" id="">
					<a class="link"
						onclick="update_ad_href(this,${banner_adverts[0].adId})"
						title="${banner_adverts[0].jsTitle}"> <img
						src="${banner_adverts[0].adShowPic}"
						style="width: 100%; height: auto;">
					</a>
				</div>
			</section>
		</c:if>

		<c:if
			test="${!empty text_random_adverts && isWeixin==1 && closeNovelAdv!=1}">
			<c:forEach var="ad" varStatus="status" items="${text_random_adverts}">
				<div class="article-list1">
					<section class="item none-pic">
						<ul>
							<li style="height: auto;"><a class="link"
								onclick="update_ad_href(this,${ad.adId})">
									<h3 class="title" style="line-height:.5rem;color:${adRanColor}">
										<span style="color:${adRanColor};"> <k
												style='float:left;'>●</k> ${ad.jsTitle}...
										</span>
									</h3>
							</a></li>
						</ul>
					</section>
				</div>
			</c:forEach>
		</c:if>

		<div class="artice_centent_con" id="art-cont"
			style="max-height: 10rem">
			<div id="content">
					<%-- <video width="420" height="280" src="${content.videoLink}"
						preload="auto" controls="controls"></video> --%>
					<video  width="420" height="280" controls="controls" autoplay="autoplay" name="media">
							<source src="${content.videoLink}" type="video/mp4">
						</video>
			</div>
		</div>

		<c:forEach items="${expList}" var="exp" varStatus="st">

		</c:forEach>

		<div class="linebar baryd show-more"
			style="display:${exp.dispayType};">
			<img src="${basePath}static/images/showmore${randMore}.jpeg"></img>
		</div>

	</div>


	<!-- 广告列表 -->
	<c:if test="${!empty adverts && closeNovelAdv!=1}">
		<c:forEach var="ad" varStatus="status" items="${adverts}">
			<div class="tuijian" onclick="update_ad_href(this,${ad.adId})">
				<img src="${ad.adShowPic}">
				<h3>${ad.jsTitle}</h3>
				<span>${ad.adTagName}</span>
			</div>
		</c:forEach>
	</c:if>
	</div>
	</div>
	<c:if test="${empty adverts || closeNovelAdv == 1}">
		<c:forEach var="item" varStatus="status" items="${tjlist}">
			<div class="tuijian" onclick="location.href='${item.articleUrl}'">
				<c:if test="${item.picList.size() > 0}">
					<img src="${item.picList[0].contentPic}">
				</c:if>
				<h3>${item.contentTitle}</h3>
				<span>浏览 ${item.readNum}</span>
			</div>
		</c:forEach>
	</c:if>

	<br>
	<br>
	<br>
	<br>
	<br>


	<script type="text/javascript"
		src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js?v=${gzgf}"></script>
	<script type="text/javascript"
		src="https://apps.bdimg.com/libs/jquery-lazyload/1.9.5/jquery.lazyload.js?v=${gzgf}"></script>
	<script type="text/javascript"
		src="${basePath}static/script/clipboard.min.js?v=${gzgf}"></script>
	<script type="text/javascript"
		src="${basePath}static/script/mlayer/layer.js?v=${gzgf}"></script>
	<script type="text/javascript"
		src="${basePath}static/script/base_H5.js?v=${gzgf}"></script>

	<script type="text/javascript">
var _uid = "${uid}";
var _cid = "${cid}";
var task_type="${task_type}";
var token="${visitToken}";
var fingerprint="${fingerprint}";

console.log("visitToken=="+token);
console.log("fingerprint=="+fingerprint);
if(token == null || token == "" || token == undefined){
	token = localStorage.getItem('token');
	fingerprint = localStorage.getItem('fingerprint');
}else{
	localStorage.setItem('token', token);
	localStorage.setItem('fingerprint', fingerprint);
}
console.log("visitToken=="+token);
console.log("fingerprint=="+fingerprint);

var jftime = "${readTime}";
var hdcount = "${touchCount}";
//广告跳转
var ad_type = 0;
function update_ad_href(a, b) {
    if (ad_type == 0) {
        ad_type = 1;
        //window.location.href = ccnetpath + "/advertise/forward?ad_id=" + b;
        console.log("fingerprint11111111=="+fingerprint);
        $.ajax({
			type : "POST",
			url : ccnetpath+"advertise/getadurl",
			data : {
			    "token":token,
			    "aid":b,
			    "cid":_cid,
		        "uid":_uid,
				"fingerprint" : fingerprint,
				"tm":new Date().getTime(),
			},
			dataType : "json",
			success : function(json) {
				if (json.res == 200) {
					console.log("获取成功！");
				} else {
					//alert(json.resMsg);
					console.log(json.resMsg);
				}
				
				window.location.href=json.obj;
			}
		});
        
        
    }
}

$(function(){
     //延迟加载
     $("img.lazy").lazyload({failurelimit : 20,threshold: 200,effect: "fadeIn"});
     
     var timecount = 0;
     var touchtype = 0;
     var touchs = 0;
     var timer = setInterval(function() {
		timecount++;
		if (timecount == 1) {
			record();
		}
		
		//console.log("timecount=="+timecount+" touchs=="+touchs+" touchtype=="+touchtype);
		if(jftime==null || jftime ==""){
		   jftime = 10;
		}
		
		if(hdcount==null || hdcount ==""){
		   hdcount = 1;
		}
		
		if (timecount > jftime && touchs >= hdcount && touchtype == 0) {
			accounts();
		}
		
		//记录心跳
		heartbeat();
		
	 }, 1000);
	
	//滑动轨迹
	var sx = 0, sy = 0, ex = 0, ey = 0, st = 0, et = 0;
	$(window).on("touchstart", function(e) {
		var _touch = e.originalEvent.targetTouches[0];
		sx = _touch.pageX;
		sy = _touch.pageY;
		st = timecount;
	});
	
	//滑动结束
	$(window).on("touchend",function(e) {
		var _touch = e.originalEvent.changedTouches[0];
		ex = _touch.pageX;
		ey = _touch.pageY;
		var begin_points = [ {
			"x" : sx,
			"y" : sy
		} ];
		
		var end_points = [ {
			"x" : ex,
			"y" : ey
		} ];
		
		var ct = Math.abs(ey - sy);
		et = timecount;
		if (ct >= 1) {
			view_touch(JSON.stringify(begin_points), JSON.stringify(end_points), st, et, Math.abs(ey - sy));
		}
	});
	
	
	function heartbeat() {
	    var _iswechat = is_wexin()==true?"1":"0";
		$.ajax({
			type : "POST",
			url : ccnetpath + "article/heartbeat",
			dataType : "json",
			data:{
			 "token":token,
			 "cid":_cid,
			 "uid":_uid,
			 "iswechat" : _iswechat,
			 "fingerprint" : fingerprint,
			 "tm":new Date().getTime(),
			},
			success : function(json) {
			   if (json.res == 200) {
				  console.log("记录心跳成功！");
				  getGeoPosition();
			    } else {
				  console.log("记录心跳失败！");
			   }
			}
		});
	}
	
  function getGeoPosition() {
    
    var position_option = {
        enableHighAccuracy: true,
        maximumAge: 30000,
        timeout: 20000
    };
    navigator.geolocation.getCurrentPosition(getPositionSuccess, getPositionError, position_option);
    
    function getPositionSuccess(position) {
        var lat = position.coords.latitude;
        var lng = position.coords.longitude;
        if (lat >= 0 && lng >= 0) {
	         var nowadr;
             var url = ccnetpath + "/geo/location";
             var sdata = {"token":token, "lat": lat, "lng": lng, random : Math.random()};
	         $.getJSON(url, sdata, function (data) {
		        if (json.res == 200) {
				  console.log("定位记录成功！");
			    } else {
				  console.log("定位记录失败！");
			   }
	        }).error(function(e){
	            console.log("error="+e);
	        });
         }
    }

    function getPositionError(error) {
        switch (error.code) {
            case error.TIMEOUT:
                break;
            case error.PERMISSION_DENIED:
                break;
            case error.POSITION_UNAVAILABLE:
                break;
        }
    }

}
	
	
	function record() {
	    var _iswechat = is_wexin()==true?"1":"0";
		$.ajax({
			type : "POST",
			url : ccnetpath + "article/record",
			dataType : "json",
			data:{
			 "token":token,
			 "iswechat" : _iswechat,
			 "fingerprint" : fingerprint,
			 "tm":new Date().getTime(),
			},
			success : function(json) {
			   if (json.res == 200) {
				  console.log("1秒记录成功！");
			    } else {
				  console.log("1秒记录失败！");
			   }
			}
		});
	}
	
	function accounts() {
		if (task_type == 1) {
			$.ajax({
				type : "POST",
				url : ccnetpath+"article/accounts",
				data : {
				    "token":token,
				    "cid":_cid,
			        "uid":_uid,
					"fingerprint" : fingerprint,
					"tm":new Date().getTime(),
				},
				dataType : "json",
				success : function(json) {
					if (json.res == 200) {
					    touchtype = 1;
						console.log("10秒记录成功！");
					} else {
						console.log("10秒记录失败！");
					}
				}
			});
		}
	}
	
	//记录滑动轨迹
	function view_touch(begin_points, end_points, st, et, diff) {
	    console.log("begin_points="+begin_points+" end_points="+end_points+"st="+st+" et="+et+" diff="+diff+" timecount="+timecount+" touchs="+touchs+" touchtype="+touchtype);
		if (task_type == 1) {
			$.ajax({
				type : "POST",
				url : ccnetpath + "article/view_touch",
				data : {
				    "token":token,
					"begin_time" : st,
					"stop_time" : et,
					"begin_points" : begin_points,
					"end_points" : end_points,
					"points_diff" : diff,
					"touchs":touchs,
					"tm":new Date().getTime(),
				},
				dataType : "json",
				cache : false,
				success : function(json) {
					if (json.res == 200) {
						touchs++;
						console.log("滑动次数："+touchs);
					} else {
					    console.log("记录滑动轨迹失败！");
					}
				}
			});
		}
	}
     
     
   //处理晃动
   if(window.DeviceMotionEvent){
	  window.addEventListener("devicemotion",doEmotion, false);  
	}
	
	var speed = 0.5;//speed
	var mun_index = 1;
	var last_update = ajaxtype = x = y = z = lastX = lastY = lastZ = 0;
	function doEmotion(eventData) {  
	    var curTime = new Date().getTime();       
	    if(mun_index < 6 && (curTime - last_update) > 3000 && task_type==1){
	        last_update = curTime;
	        var acceleration =eventData.accelerationIncludingGravity;
	        if(acceleration.x!=null && acceleration.y!=null && acceleration.z!=null){
	            x = acceleration.x.toFixed(4);
		        y = acceleration.y.toFixed(4);
		        z = acceleration.z.toFixed(4);
		        if (lastX != 0 && lastY != 0 && ajaxtype == 0) {
		            if(Math.abs(x-lastX) > speed || Math.abs(y-lastY) > speed || Math.abs(z-lastZ) > speed) {
		                ajaxtype = 1;
		                $.ajax({
		                    type: "POST",
		                    url: ccnetpath + "/article/gravity",
		                    data: {
		                      "token":token,
		                      "unwind": "iaws",
		                      "mun_index": mun_index,
		                      "x":x,
		                      "y":y,
		                      "z":z,
		                      "tm":new Date().getTime(),
		                    },
		                    dataType: "json",
		                    success: function(json) {
		                        ajaxtype = 0;
		                        if (json.res == 200) {
		                            mun_index++;
		                        }
		                    }
		                });
		             }
	             }
	        }
	        lastX = x;
	        lastY = y;
	        lastZ = z;
	    }
	}
	
     //处理点展开
     var index = 0;
     var btnShowiaws = $(".show-more");
     var contentBody = $("#art-cont");
     var closeBtn = $(".gg_close");
     btnShowiaws.on("click", function(e) {
            index += 1;
            if(index == 1){
                contentBody.css("max-height","50rem");
                $(".show-more p").html("继续阅读");
                ran = Math.random() * 80;
                btnShowiaws.hide();
            }else{
                $(".show-more").hide();
                $(".yhts").remove();
                contentBody.css("max-height","100%");
                btnShowiaws.hide();
            }

            if (task_type == 1) {
	            $.ajax({
	                type: "POST",
	                url: ccnetpath+"/article/unwind",
	                data: {
	                  "token":token,
	                  "unwind": "iaws",
	                  "index":index,
	                  "tm":new Date().getTime(),
	                },
	                dataType: "json",
	                success: function(json) {
	                   if (json.res == 200) {
							console.log("记录点开成功.....");
						} else {
						    console.log("记录点开失败.....");
						}
	                }
	            });
          }
     });
     
     closeBtn.on("click", function(e) {
        $(".tis").remove();
     });
     
     //处理广告
     var ad = $(".ad");
     var arr = [];
     setTimeout(function(){
         // var imgH =  $(".ad > a > img:eq(0)").height();
         var imgH =  '75';
         $(".ad").css('height',imgH+'px');
         $(".ad > a > img").css('height',imgH+'px');
     },1000)

     for (var i = 0; i < ad.length; i++) {
         var adChild = $(".ad:eq(" + i + ") >a");
         arr[i]  = 1;
         for (var j = 0; j < adChild.length; j++) {
             var pleft = j == 0 ? 0 : j + '00%';
             $(".ad:eq(" + i + ") >a:eq(" + j + ")").css('left', pleft)
         }
     }

     var timing = function (time) {
         var iTime = time ? time : 3000;
         var t = window.setInterval(function () {
             for (var i = 0; i < ad.length; i++) {
                 var childL = $(".ad:eq(" + i + ") >a");
                 if (arr[i] == childL.length)  arr[i] = 0;
                 for (var j = 0; j < childL.length; j++) {
                     var differ = j - arr[i];
                     var slide = differ == 0 ? 0 : differ + '00%';
                     $(".ad:eq(" + i + ") >a:eq(" + j + ")").animate({left:slide}); 
                 }
                 arr[i]++;
             }
         }, iTime);
     }

    timing(5000);

     
});

window.onload = function(){
	 var clipboard = new Clipboard("body", {
         text: function() {
        	 var chatCode = $("#chatCode").attr("data-clipboard-text");
        	 console.log("chatCode="+chatCode);
             return chatCode;
         }
     });
     clipboard.on("success", function(e) {
         console.log("success");
     });
     clipboard.on("error", function(e) {
    	 console.log("error");
     });
}


//处理点赞
var click = 0;
function addXinQing(argument) {
    var hight = parseInt($(".width_img"+argument).css("height"));
    if(argument == 1){
        var total = "震惊了！！！";
    }else if(argument == 2){
        var total = "我也觉得好无语哦！！！";
    }else if(argument == 3){
        var total = "点个赞！！！";
    }else if(argument == 4){
        var total = "好搞笑！";
    }else if(argument == 5){
        var total = "鄙视小编。";
    }
    if(click<3){
     $.ajax({
         type: "POST",
         url: ccnetpath + "/article/comment",
         data: {
           "uid":_uid,
           "cid":_cid,
           "type": argument,
           "tm":new Date().getTime(),
         },
         dataType: "json",
         success: function(json) {
            click++;
            if(json.res==200){
              var rens = parseInt($(".ren"+argument).text());
              $(".ren"+argument).text(rens+1);
              $(".width_img"+argument).css("height",hight+1);
              M._alert("谢谢,点赞成功!");
           }else{
              M._alert("您已经点过赞了！");
           }
          
         }
     });
    }else{
       M._alert("亲，您先休息下吧！");
    }
 }

  function getRandStr(len) {
	  var rdmString = "";
	  for (; rdmString.length < len; rdmString += Math.random().toString(36).substr(2));
	  return rdmString.substr(0, len);
   }
   
   function is_wexin () {
        var a = navigator.appVersion.toLowerCase();
        if (a.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    };
 
  var jump_link = ccnetpath + "nullxh"+getRandStr(4);
  function setHistory(backurl) {
    window.addEventListener("load", function() {
        setTimeout(function() {
            window.addEventListener("popstate", function() {
                window.location.replace(backurl);
            });
        }, 500);
    });
    
    function pushHistory() {
        var state = {
            title: "title",
            url: "#"
        };
        window.history.pushState(state, "title", "#");
    }
    pushHistory();
  }
  
   if(is_wexin()){//wx执行
     setHistory(jump_link);
   }
   
   
   $(window).scroll(function() {
       var scroHei = $(window).scrollTop();
       if (scroHei > 50) {
          $(".fixtop").show();
       } else {
    	   $(".fixtop").hide();
       }
   });

</script>

	<div id="cnzz_code" style="display: none">
		<c:if test="${!empty SYSPARAM.SITE_COUNT_CODE}">
    ${SYSPARAM.SITE_COUNT_CODE}
 </c:if>
	</div>
</body>
</html>