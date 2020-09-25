<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/includes.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${basePath}/static/images/favicon.ico" type="image/x-icon"/>
<link rel="icon" type="image/gif" href="${basePath}/static/images/favicon.gif" />
<meta charset="utf-8">
<title>404错误提示</title>
<style>
html,#content {
	background: #fff
}
</style>
<script type="text/javascript" src="${basePath}/static/js/ga.js"></script>
<script>
    var _gaq = _gaq || [];
    var href = location.href;
    var ua = navigator.userAgent.toLowerCase();
    var ga_ua = 'UA-28423340-28';
    _gaq.push(['_setAccount', ga_ua]);
    _gaq.push(['_trackPageview', '[404]' + (href.indexOf('?') == -1? href : href.substring(0, href.indexOf('?')))]);

    (function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript'; 
        ga.src = 'http://www.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; 
        s.parentNode.insertBefore(ga, s);
     })();
</script>
</head>

<body>
	<div style="padding: 50px 0;width: 650px;margin: 0 auto;">
		<table border="0" cellspacing="0" cellpadding="0" width="651" height="201">
			<tbody>
				<tr>
					<td>
					<img border="0" alt="" src="${basePath}/static/images/404.jpg" usemap="#map1355107823663" width="658" height="400"> 
					    <map name="map1355107823663" id="map1355107823663">
							<area href="${basePath}/user/index" shape="rect" coords="273,352,387,388" alt="">
						</map>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
