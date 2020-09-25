<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-dpr="1" style="font-size:40px;">
<head>
<title>提取群推广二维码</title>
<meta name="keywords" content="${SYSPARAM.SITE_KEYWORDS}">
<meta name="description" content="${SYSPARAM.SITE_DESCRIPTION}">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title></title>
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<link type="text/css" rel="stylesheet" href="${basePath}static/css/red/public.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/show.css">
<link type="text/css" rel="stylesheet" href="${basePath}static/css/main.min.css">
<link rel="stylesheet" type="text/css" href="${basePath}static/css/article.css">
<style type="text/css">
.jxread {
	margin-top: 0px;
	margin-bottom: 0px;
	padding: 0px;
	max-width: 100%;
	clear: both;
	min-height: 1em;
	color: rgb(62, 62, 62)!important;
	line-height: 25.6px;
	white-space: normal;
	widows: 1;
	font-family: 微软雅黑;
	text-align: center;
	background-color: rgb(255, 255, 255)!important;
	box-sizing: border-box !important;
	word-wrap: break-word !important;
}

.cnwm {
	margin: 0px;
	padding: 0px;
	max-width: 100%;
	box-sizing: border-box !important;
	word-wrap: break-word !important;
	color: rgb(255, 0, 0)!important;
	line-height: 19px!important;
	white-space: pre-wrap;
	font-size: 18px!important;
}

.web_bg{
  position:fixed;
  top: 0;
  left: 0;
  width:100%;
  height:100%;
  min-width: 1000px;
  z-index:-10;
  zoom: 1;
  background-color: #fff;
  background-repeat: no-repeat;
  background-size: cover;
  -webkit-background-size: cover;
  -o-background-size: cover;
  background-position: center 0;
}
</style>
</head>

<body class="yzk_bg">

   <div style="width:100%;overflow:hidden;height: auto;position: relative;z-index:0;margin-top:0px;">
   <div class="yzk_artice_con" id="ctx_content">
     <div class="artice_centent_con" id="art-cont" style="max-height:100%">
		<div class="allready">
			<div class="ready_box">
				<p class="ready_code">
					<img class="ready_codeIMG" src="${basePath}barcode/groupCode?url=${url}" style="width:100%;height:100%">
				</p>
				<p style="margin-top: 10px;margin-bottom: 0px;padding: 0px;max-width: 100%;clear: both;min-height: 1em;color: rgb(62, 62, 62);line-height: 25.6px;white-space: normal;widows: 1;font-family: 微软雅黑;text-align: center;background-color: rgb(255, 255, 255);box-sizing: border-box !important;word-wrap: break-word !important;">
				<span style="margin: 0px;padding: 0px;max-width: 100%;box-sizing: border-box !important;word-wrap: break-word !important;color: rgb(255, 0, 0);line-height: 19px;white-space: pre-wrap;font-size: 18px;">▲长按上图二维码保存▲</span></p>
			</div>
		</div>
     </div>
    </div>
   </div>

</body>
</html>