<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
   <div style="width:100%;overflow:hidden;height: auto;position: relative;z-index:0;margin-top:${isWeixin==1?50:0}px;margin-bottom:80px;">
   <div class="yzk_artice_con" id="ctx_content">
    <div class="artice_top_c01" id="art-h1">${content.contentTitle}</div>
    <div class="artice_top_ctime" style="margin-bottom:5px;">
      <span class="ctime-l"><fmt:formatDate value="${content.createTime}" type="both" pattern="yyyy-MM-dd"/></span> 
      <c:if test="${!empty content.weixinLink && !empty content.weixinName}">
      <span class="wechat"><a href="${content.weixinLink}" target="_blank">${content.weixinName}</a></span>
      <span class="gzimg"><img src="${basePath}static/images/gzhm.gif"></span>
      </c:if>
      <span style="float:right;" class="view">阅读 ${content.visualReadNum}+</span>
     </div>

     <div class="artice_centent_con" id="art-cont">
      <div id="content">
		<p>
			<iframe class="edui-faked-video" src="https://v.qq.com/iframe/player.html?vid=${content.videoLink}&amp;auto=0"
				width="420" height="280" scrolling="no" frameborder="0"
				align="center" style="width: 100%; height: 221.25px;">
			</iframe>
		</p>
	 </div>
    </div>
   
  </div>
 </div>
</div>

<c:if test="${isWeixin!=1}">
    <div class="clearfix2"></div>
	<div class="info_bottom">
	<div class="share_way" style="background:${ranColor};">通过下方式分享才能正常计费</div>
	<!--  
	<div class="tuiguang js-copy" id="btn" data-clipboard-text="${copyText}">
		群推广链接：<span style="color:${ranColor};">${contentUrl}</span>
		<button id="btncopy" style="background:${ranColor};">复制</button>
	</div>
	-->
	<div id="nativeShare">
	  <div class="list clearfix1">
		<span data-app="weixin" class="nativeShare weixin" data-from="singlemessage"><i></i>微信好友</span>
		<span data-app="weixinFriend" class="nativeShare weixin_timeline" style="border-left:1px solid #ccc;" data-from="timeline"><i></i>微信朋友圈</span>
	  </div>
	</div>
	</div>
</c:if>

<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery-lazyload/1.9.5/jquery.lazyload.js?v=${gzgf}"></script> 
<script type="text/javascript" src="${basePath}static/script/clipboard.min.js?v=${gzgf}"></script> 
<script type="text/javascript" src="${basePath}static/script/mlayer/layer.js?v=${gzgf}"></script>
<script type="text/javascript" src="${basePath}static/script/base_H5.js?v=${gzgf}"></script>
<script type="text/javascript" src="${basePath}static/appshare/nativeShare_v2.js?v=${gzgf}"></script>

<script type="text/javascript">
	var _uid = "${uid}";
	var _cid = "${cid}";
	$(function(){
	   $("img.lazy").lazyload({failurelimit : 20,threshold: 200,effect: "fadeIn"});
	});


   var clipboard = new Clipboard("#btn");
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
    };
    
    if(is_wexin()){
        $(".info_bottom").hide();
        $(".content").css("margin-bottom","0px");
    }
    
    $("title").text("${content.contentTitle}");
    var config = {
        url      : "",
        title    : "${content.contentTitle}",
        img      :  "${basePath}${content.contentPic}",
        describe : "${content.contentSbTitle}",
        q_price  : "${content.friendShareAward}",
        py_price : "${content.timelineShareAward}"
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
    new nativeShare("nativeShare", config);

</script>
</body>
</html>