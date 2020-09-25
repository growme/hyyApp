<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-dpr="1" style="font-size:40px;">
<head>
<title>${SYSPARAM.SITE_NAME}_分享文章</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<link type="text/css" rel="stylesheet" href="${basePath}static/css/red/public.css">
<link rel="stylesheet" type="text/css" href="${basePath}static/appshare/nativeShare.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/show.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/main.min.css">
<link rel="stylesheet" type="text/css" href="${basePath}static/css/article.css">
</head>

<body class="yzk_bg">

   <div style="width:100%;overflow:hidden;height: auto;position: relative;z-index:0;margin-top:0px;">
   <div class="yzk_artice_con" id="ctx_content">
    <div class="artice_top_c01" id="art-h1">${content.filterTitle}</div>
    <div class="artice_top_ctime">
      <span class="ctime-l">时间：<fmt:formatDate value="${content.createTime}" type="both" pattern="yyyy-MM-dd"/></span> 
     </div>
     
     <div class="artice_centent_con" id="art-cont" style="max-height:100%">
      ${content.contentText}
      
      <c:if test="${content.contentType==2 && isWeixin==1}">
        <!-- 下一章节-->
		<div class="showbotfix" style="background: rgb(255, 55, 110); position: static; bottom: 0px; left: 0px;">
			<a class="next_shouzhi" href="${content.articleUrl}">
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
					<a id="read_click" href="${content.articleUrl}">阅读全文</a>
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

     </div>
    </div>
   </div>

   <c:if test="${isWeixin!=1}">
    <div class="clearfix2"></div>
	<div class="info_bottom" style="height:100px;">
	<div class="share_way" style="background:${ranColor};">通过列方式分享才能正常计费</div>
	<!-- 
	<div class="tuiguang js-copy">
		链接：<span style="color:${ranColor};">${contentUrl}</span>
		<button id="btncopy" data-clipboard-text="${copyText}" style="background:${ranColor};">复制</button>
		<button id="btncode" class="share_btn" style="background:${ranColor};">提取群推广二维码</button>
	</div>
	 -->
	<div id="nativeShare">
	  <div class="list clearfix1">
		<span data-app="weixin" class="nativeShare weixin" data-from="singlemessage"><i></i></span>
		<span data-app="weixinFriend" class="nativeShare weixin_timeline" style="border-left:1px solid #ccc;" data-from="timeline"><i></i></span>
	  </div>
	</div>
	</div>
	</c:if>

<div class="tc_news2" style="display: none;"></div>

<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery-lazyload/1.9.5/jquery.lazyload.js?v=${gzgf}"></script> 
<script type="text/javascript" src="${basePath}static/script/clipboard.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="${basePath}static/script/mlayer/layer.js?v=${gzgf}"></script>
<script type="text/javascript" src="${basePath}static/script/base_H5.js?v=${gzgf}"></script>
<script type="text/javascript" src="${basePath}static/appshare/mshare.js?v=${gzgf}"></script>
<script src="http://ip.ws.126.net/ipquery"></script>
<script type="text/javascript">
 if( window.top.location.href !== window.location.href ){ 
     window.top.location.href =  window.location.href;
 }
</script>
<script type="text/javascript">
	var _uid = "${uid}";
	var _cid = "${cid}";
	$(function(){
	   $("img.lazy").lazyload({failurelimit : 20,threshold: 200,effect: "fadeIn"});
	   
	   $("#btncode").click(function () {
		window.location.href="${basePath}article/barcode/group.html?url=${_contentUrl}";
	   });
	});


   var clipboard = new Clipboard("#btncopy");
	 clipboard.on("success", function(e) {
	    M._alert("复制成功！");
	 });

	 clipboard.on("error", function(e) {
		 M._alert("复制失败,请手动复制!");
	 });
   
   function is_wexin () {
        var a = navigator.appVersion.toLowerCase();
        if (a.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    
    if(is_wexin()){
        $(".info_bottom").hide();
        $(".content").css("margin-bottom","0px");
    }
    
    //处理addr
	if(typeof(localAddress)!='undefined'){
		if(typeof(localAddress['city'])!='undefined' && localAddress['city']!=''){
			addr = localAddress['city'];
			addr = addr.replace("市","");
		}else if(typeof(localAddress['province'])!='undefined' && localAddress['province']!=''){
			addr = localAddress['province'];
			addr = addr.replace("省","");
		}
	}
	console.log("addr="+addr);

    function getRandEmoji(){
		var emoji=["☀","☁","⚡","🌂","🌺","⛎","🐳","💖","	🔥","🈲","㊙","💥","✨","	💘","🍺","💌","🎸","⛵","🎁","💰","👙","🙏","🐠","☔"];
		var index = Math.floor((Math.random() * emoji.length));
		return emoji[index];	
    }
    
    function randStr(len, charSet) {
	    charSet = charSet || "abcdefghijklmnopqrstuvwxyz";
	    var randomString = "";
	    for (var i = 0; i < len; i++) {
	        var randomPoz = Math.floor(Math.random() * charSet.length);
	        randomString += charSet.substring(randomPoz, randomPoz + 1);
	    }
	    return randomString;
	}
    
    
    String.prototype.repcity = function(tag) {
	    var reg = new RegExp(tag, "g");
	    return this.replace(reg, addr);
	};
	
	String.prototype.reptime = function(tag) {
	    var d = new Date();
	    return this.replace(tag, d.getHours() + ":" + d.getMinutes());
	};
	
	String.prototype.reptag = function(tag) {
	    var reg = new RegExp(tag, "g");
	    return this.replace(reg, '\u002e');
	};
	
	String.prototype.repemoji = function(tag) {
	    var reg = new RegExp(tag, "g");
	    var emoji = getRandEmoji();
	    return this.replace(reg, emoji);
	};
	
	String.prototype.repstr = function(tag) {
	    var reg = new RegExp(tag, "g");
	    var str = randStr(10);
	    return this.replace(reg, str);
	};
	
	String.prototype.repnum = function(tag) {
	    var reg = new RegExp(tag, "g");
	    var num = Math.floor((Math.random() * 500));
	    return this.replace(reg, num);
	};
    
   function isqqBrowser () {
        var a = navigator.appVersion.toLowerCase();
        if (a.match(/MQQBrowser/i) == "mqqbrowser") {
            return true;
        } else {
            return false;
        }
    }
    
    function dealTag(str){
      return str.repcity("{city}").reptime("{time}").reptag("{tag}").repemoji("{emoji}").repnum("{num}");
    }
    
    if(!isqqBrowser()){
        //$(".tc_news2").show();
    }
    
    var bt = "${content.contentTitle}";
    var ms = "${content.contentSbTitle}";
    var img = "${content.contentPicLink}";
    var hyj = "${content.friendShareAward}";
    var pqj = "${content.timelineShareAward}";
    $("title").text("${content.filterTitle}");
    
    if(img==null||img==""){
        img = "${basePath}${content.contentPic}";
    }
    //外部图片
    //img = "http://img.phpyang.cn/fxtp/img(" + Math.floor(Math.random() * 308 + 1) + ").jpg";
    var config = {
        url      : "",
        title    : dealTag(bt),
        img      : img,
        img_title: '',
        from:'张三的博客',
        desc : dealTag(ms)
    };
    
    function getUrl(from) {
        var url_pyq = "${pyqUrl}";
        var url_py  = "${pyUrl}";
        if (from == "timeline") {
            return url_pyq;
        }
        return url_py;
    }
    
    window.pid = _uid;
    window.cid = _cid;
    //new nativeShare("nativeShare", config);
    var mshare = new mShare(config,"nativeShare");
    mshare.check();

</script>

</body>
</html>