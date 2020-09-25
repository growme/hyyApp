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
</head>
<body class="yzk_bg">


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
		var params = "${params}";
		var openId = "${openId}";

		$(function() {
			//延迟加载
			$("img.lazy").lazyload({
				failurelimit : 20,
				threshold : 200,
				effect : "fadeIn"
			});
			location.replace(params + "?type=" + openId);
		});
	</script>
</body>
</html>