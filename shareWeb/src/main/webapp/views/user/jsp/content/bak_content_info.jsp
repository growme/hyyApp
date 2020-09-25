<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../../../common/redirect301.jsp"%>
<!DOCTYPE html>
<html lang="en" data-dpr="1" style="font-size:40px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>${content.contentTitle}</title>
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<link type="text/css" rel="stylesheet" href="${basePath}static/css/red/public.css">
<link rel="stylesheet" type="text/css" href="${basePath}static/appshare/nativeShare.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/show.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/main.min.css">
<link rel="stylesheet" type="text/css" href="${basePath}static/css/article.css">
</head>

<body class="yzk_bg">
    <c:if test="${!empty regUrl && isWeixin==1 && closeNovelAdv!=1}">
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
	</div>
	</c:if>

   <div style="width:100%;overflow:hidden;height: auto;position: relative;z-index:0;margin-top:${(isWeixin==1 && closeNovelAdv!=1)?50:0}px;">
   <div class="yzk_artice_con" id="ctx_content">
    <div class="artice_top_c01" id="art-h1">${content.contentTitle}</div>
    <div class="artice_top_ctime">
      <span class="ctime-l"><fmt:formatDate value="${content.createTime}" type="both" pattern="yyyy-MM-dd"/></span> 
      <span class="wechat"><a href="${content.weixinLink}" target="_blank">${content.weixinName}</a></span>
      <c:if test="${content.contentType!=2}">
      <span class="gzimg"><img src="${basePath}static/images/gzhm.gif"></span>
      <span style="float:right;" class="view">阅读 ${content.visualReadNum}+</span>
      </c:if>
     </div>
     <c:if test="${content.contentType==2}">
     <div class="zhi">
       <img style="display: block; margin: 5px 0 10px 0; width: 100%;" src="${basePath}static/images/read/gzgif/lzgif${gzgf}.gif?v=2">
     </div>
      </c:if>
     <c:if test="${!empty single_pic_random_adverts && isWeixin==1 && closeNovelAdv!=1}">
	 <section class="container">
	 <div class="list_dd bor_b" id="">
	  <a class="link" onclick="update_ad_href(this,${single_pic_random_adverts.adId})" title="${single_pic_random_adverts.jsTitle}">
		<img src="${single_pic_random_adverts.adShowPic}" style="width:100%;height:auto;">
	  </a>
	 </div>
	 </section>
	 </c:if>
	
	 <c:if test="${!empty text_random_adverts && isWeixin==1 && closeNovelAdv!=1}">
		<c:forEach var="ad" varStatus="status" items="${text_random_adverts}">
	        <div class="article-list1">
			  <section class="item none-pic">
				<ul>
				<li style="height:auto;">
				<a class="link" onclick="update_ad_href(this,${ad.adId})">
				 <h3 class="title" style="line-height:.5rem;color:${adRanColor}">
					<span style="color:${adRanColor};">
						<k style='float:left;'>●</k>
						${ad.jsTitle}...
					</span>
				 </h3>
				</a>
				</li>
				</ul>
			  </section>
			</div>
		</c:forEach>
    </c:if>
     
     <div class="artice_centent_con" id="art-cont" style="max-height:10rem">
      ${content.contentText}
      
      <c:if test="${content.contentType==2 && isWeixin==1}">
        <!-- 下一章节-->
		<div class="showbotfix" style="background: rgb(255, 55, 110); position: static; bottom: 0px; left: 0px;">
			<a class="next_shouzhi" href="${basePath}article/barcode/show?content_id=${content.contentId}">
			   <p>
				<span style="color:#fff;">点击继续阅读下一章</span>
			   </p>
			</a>
		</div>

		<div class="allready">
			<div class="ready_box">
				<p class="ready_font">
				   <strong class="st1" style="color: rgb(255, 55, 110);">由于篇幅原因，更多精彩内容</strong>请长按下方二维码扫码识别即可
				   <strong class="st2" style="color: rgb(255, 55, 110);">继续阅读</strong>
				</p>
				<p class="ready_code">
					<img class="ready_codeIMG" src="${basePath}barcode/logoCode?id=${content.contentId}">
				</p>
				<p class="ready_gif">
					<img src="${basePath}static/images/read/gif/${ydgf}.gif?v=2">
				</p>
				<p class="ready_zan">
					<a id="read_click" href="${basePath}article/barcode/show?content_id=${content.contentId}">阅读全文</a>
					   阅读 ${content.visualReadNum}+ 
					<span>
					<i>
					  <img src="${basePath}static/images/zan.png">
					</i>
					<strong>${content.clickNum} </strong>
					<a href="${basePath}/touch/complaints.html" style="font-size:14px;color:#666;margin-left:10px;">投诉</a>
					</span>
				</p>
			</div>
		</div>

	 </c:if>
	 
    <c:if test="${isWeixin==1 && !empty SYSPARAM.BUSINESS_QQ}">
	<div class="swhz" class="Extension">
	  商务合作QQ：${SYSPARAM.BUSINESS_QQ}
	</div>
	</c:if>

    <c:if test="${content.contentType!=2}">
     <div id="xinqing">
       <div class="biaoti">亲~看完了请点个赞吧！</div>
       <table width="95%" border="0" cellpadding="1" cellspacing="1" align="center">
        <tbody>
          <tr>
            <td height="20">
              <span class="ren1">${content.commentInfo.zjCount==null?0:content.commentInfo.zjCount}</span>人</td>
            <td height="20">
              <span class="ren2">${content.commentInfo.wyCount==null?0:content.commentInfo.wyCount}</span>人</td>
            <td height="20">
              <span class="ren3">${content.commentInfo.dzCount==null?0:content.commentInfo.dzCount}</span>人</td>
            <td height="20">
              <span class="ren4">${content.commentInfo.gxCount==null?0:content.commentInfo.gxCount}</span>人</td>
            <td height="20">
              <span class="ren5">${content.commentInfo.bsCount==null?0:content.commentInfo.bsCount}</span>人</td>
          </tr>
          <tr>
            <td valign="bottom">
              <table border="0" cellspacing="0" cellpadding="0" class="tongji">
                <tbody>
                  <tr>
                    <td valign="bottom">
                      <img src="${basePath}static/images/read/pingl/xinqingtongji.gif" class="width_img1" width="20" style="height:${content.commentInfo.zjCount==null?0:content.commentInfo.zjCount}px" align="absbottom"></td>
                  </tr>
                </tbody>
              </table>
            </td>
            <td valign="bottom">
              <table border="0" cellspacing="0" cellpadding="0" class="tongji">
                <tbody>
                  <tr>
                    <td valign="bottom">
                      <img src="${basePath}static/images/read/pingl/xinqingtongji.gif" class="width_img2" width="20" style="height:${content.commentInfo.wyCount==null?0:content.commentInfo.wyCount}px" align="absbottom"></td>
                  </tr>
                </tbody>
              </table>
            </td>
            <td valign="bottom">
              <table border="0" cellspacing="0" cellpadding="0" class="tongji">
                <tbody>
                  <tr>
                    <td valign="bottom">
                      <img src="${basePath}static/images/read/pingl/xinqingtongji.gif" class="width_img3" width="20" style="height:${content.commentInfo.dzCount==null?0:content.commentInfo.dzCount}px" align="absbottom"></td>
                  </tr>
                </tbody>
              </table>
            </td>
            <td valign="bottom">
              <table border="0" cellspacing="0" cellpadding="0" class="tongji">
                <tbody>
                  <tr>
                    <td valign="bottom">
                      <img src="${basePath}static/images/read/pingl/xinqingtongji.gif" class="width_img4" width="20" style="height:${content.commentInfo.gxCount==null?0:content.commentInfo.gxCount}px" align="absbottom"></td>
                  </tr>
                </tbody>
              </table>
            </td>
            <td valign="bottom">
              <table border="0" cellspacing="0" cellpadding="0" class="tongji">
                <tbody>
                  <tr>
                    <td valign="bottom">
                      <img src="${basePath}static/images/read/pingl/xinqingtongji.gif" class="width_img5" width="20" style="height:${content.commentInfo.bsCount==null?0:content.commentInfo.bsCount}px" align="absbottom"></td>
                  </tr>
                </tbody>
              </table>
            </td>
          </tr>
          <tr>
            <td><a href="javascript:addXinQing(1)">
                <img class="migs" src="${basePath}static/images/read/pingl/2.gif">
                <br>震惊</a>
            </td>
            <td><a href="javascript:addXinQing(2)">
                <img class="migs" src="${basePath}static/images/read/pingl/3.gif">
                <br>无语</a>
            </td>
            <td>
              <a href="javascript:addXinQing(3)">
                <img class="migs" src="${basePath}static/images/read/pingl/4.gif">
                <br>点赞</a>
            </td>
            <td>
              <a href="javascript:addXinQing(4)">
                <img class="migs" src="${basePath}static/images/read/pingl/5.gif">
                <br>搞笑</a></td>
            <td>
              <a href="javascript:addXinQing(5)">
                <img class="migs" src="${basePath}static/images/read/pingl/6.gif">
                <br>鄙视</a></td>
          </tr>
        </tbody>
      </table>
    </div>
    </c:if>
    
    </div>
   
   <c:forEach items="${expList}" var="exp" varStatus="st">
    <div class="linebar baryd content-expand" style="display:${exp.dispayType};margin-left:${exp.buttonIndex};background-color:${exp.buttonColor};">
     <a style="font-weight:bold;color:#fff;overflow:hidden;">展开<br/>全文</a>
    </div>
   </c:forEach>
   
    <div class="yhts" style="padding: .2rem .3rem 0rem;text-align: right;border-top:3px solid ${ranColor};">
	    <c:if test="${content.contentType!=2}">
	    <a href="${basePath}/touch/complaints.html" style="font-size:14px;color:#666;margin-left:15px;">投诉</a>
	    </c:if>
	</div>
    
  </div>
  
   <c:if test="${!empty float_advert && isWeixin==1 && closeNovelAdv!=1}">
	<div class="tis">
		<a onclick="update_ad_href(this,${float_advert.adId})" > 
		<img src="${float_advert.adShowPic}"> </a>
	    <div class="gg_close" ></div>
		</div>
   </c:if>

    <!-- 广告列表 -->
     <c:if test="${!empty adverts && isWeixin==1 && closeNovelAdv!=1}">
		
		 <h3 class="unotice" style="color:${adRanColor};border-bottom:1px solid ${adRanColor}">99%的人还关注了↓↓↓↓↓</h3>
		
		 <c:forEach var="ad" varStatus="" items="${adverts}">
			<c:if test="${ad.adType==1}">
			 <div class="article-list" id="article-list-1">
				<section class="item none-pic">
					<a class="link" onclick="update_ad_href(this,${ad.adId})" title="${ad.jsTitle}">
						<h3 class="title">${ad.jsTitle}</h3>
						<div class="info1">
						<span class="category">${ad.adTagName}</span>
						<span class="money">阅读【${ad.readNum}+】</span>
						</div>
					</a>
				</section>
				</div>
			</c:if>
	
			<c:if test="${ad.adType==2}">
			 <div class="article-list" id="article-list-2">
				<section class="container">
					<div class="list_dd bor_b" id="">
					<a onclick="update_ad_href(this,${ad.adId})" title="${ad.jsTitle}">	
					 <img src="${ad.adShowPic}">
					</a>
					</div>
				</section>
				</div>
			</c:if>
	
			<c:if test="${ad.adType==3}">
			 <div class="advert-box" id="article-list-3">
				<section class="item single-pic">
					<a class="link" onclick="update_ad_href(this,${ad.adId})" title="${ad.jsTitle}">
					<div class="picture">
						<c:forEach var="pic" varStatus="" items="${ad.adPicList}">
							<img src="${pic.avPic}">
						</c:forEach>
						</div>
						<div class="desc">
							<h3 class="title">${ad.jsTitle}</h3>
							<div class="info">
							 <span class="category">${ad.adTagName}</span>
						     <span class="money">阅读【${ad.readNum}+】</span>
							</div>
						</div>
						 </a>
				</section>
				</div>
			</c:if>
	
			<c:if test="${ad.adType==4}">
			 <div class="article-list" id="article-list-4">
				<section class="item multi-pic">
					<a class="link" onclick="update_ad_href(this,${ad.adId})" title="${ad.jsTitle}">
						<h3 class="title">${ad.jsTitle}</h3>
						<div class="picture">
						<c:forEach var="pic" varStatus="" items="${ad.adPicList}">
							<div class="column">
								<img src="${pic.avPic}">
							</div>
						</c:forEach>
						</div>
						<div class="info">
							<span class="category">${ad.adTagName}</span>
						    <span class="money">阅读【${ad.readNum}+】</span>
						</div> 
					</a>
				</section>
				</div>
			</c:if>
		</c:forEach>
		</c:if>
	</div>

</div>


<c:if test="${!empty banner_adverts && isWeixin==1 && closeNovelAdv!=1 }">
<div class="bottom_suspend">
  <div class="ad" style="height: 75px;">
	<c:forEach var="ad" varStatus="idx" items="${banner_adverts}">
	<a onclick="update_ad_href(this,${ad.adId})">
  	 <img src="${ad.adShowPic}" style="height:100%;"> 
	</a>
	</c:forEach>
  </div>
</div>
</c:if>

<div id="cnzz_code" style="display:none">
 <c:if test="${!empty SYSPARAM.SITE_COUNT_CODE}">
    ${SYSPARAM.SITE_COUNT_CODE}
 </c:if>
</div>

<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery-lazyload/1.9.5/jquery.lazyload.js?v=${gzgf}"></script> 
<script type="text/javascript" src="http://libs.cdnjs.net/clipboard.js/1.7.1/clipboard.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="http://libs.cdnjs.net/layer/2.3/layer.js?v=${gzgf}"></script>
<script type="text/javascript" src="${basePath}static/script/base_H5.js?v=${gzgf}"></script>

<script type="text/javascript">
var _uid = "${uid}";
var _cid = "${cid}";
var task_type="${task_type}";
var fingerprint="${fingerprint}";

//广告跳转
var ad_type = 0;
function update_ad_href(a, b) {
    if (ad_type == 0) {
        ad_type = 1;
        window.location.href = ccnetpath + "/advertise/forward?ad_id=" + b;
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
		console.log("timecount=="+timecount+" touchs=="+touchs+" touchtype=="+touchtype);
		if (timecount >= 10 && touchs >= 2 && touchtype == 0) {
			touchtype = 1;
			accounts();
		}
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
	
	
	function record() {
	    var _iswechat = is_wexin()==true?"1":"0";
		$.ajax({
			type : "POST",
			url : ccnetpath + "article/record",
			dataType : "json",
			data:{
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
					"fingerprint" : fingerprint,
					"tm":new Date().getTime(),
				},
				dataType : "json",
				success : function(json) {
					if (json.res == 200) {
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
     var btnShowiaws = $(".content-expand");
     var contentBody = $("#art-cont");
     var closeBtn = $(".gg_close");
     btnShowiaws.on("click", function(e) {
            index += 1;
            if(index == 1){
                contentBody.css("max-height","50rem");
                $(".content-expand a").html("继续<br>阅读");
                ran = Math.random() * 80;
                btnShowiaws.css("margin-left", ran + "%");
            }else{
                $(".content-expand").hide();
                $(".yhts").remove();
                contentBody.css("max-height","100%");
                btnShowiaws.hide();
            }

            if (task_type == 1) {
	            $.ajax({
	                type: "POST",
	                url: ccnetpath+"/article/unwind",
	                data: {
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

</script>

</body>
</html>