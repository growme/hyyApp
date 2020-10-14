<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>常见问题</title>
<%@ include file="../../../common/header.jsp"%>
<%@ include file="../../../common/js_param.jsp"%>
<%@ include file="../../../common/ucom_css.jsp"%>
<link rel="stylesheet" href="${basePath}static/css/problem/reset.css">
<script src="${basePath}static/script/problem/jquery-2.1.4.min.js?t=1515399374925"></script>
<link rel="stylesheet" href="${basePath}static/css/problem/problem_style.css">

</head>

<body>
	<div class="main">
		<!-- 切换按钮 -->
		<ul class="main_ul">
			<li class="bg">新手问题</li>
			<li class="">金币问题</li>
			<li class="">提现问题</li>
		</ul>
		<!-- 新手问题 -->
		<div class="main_list">
			<div class="new_hand" style="display: block">
				<p class="red_font">新手问题</p>

				<c:if test="${!empty newHand}">
					<c:forEach var="problem" varStatus="status" items="${newHand}">
						<p class="title">
							${problem.title} <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
						</p>
						<div class="title_content" style="display: none;">
								${problem.content}
						</div>
					</c:forEach>
				</c:if>


			</div>
			<!-- 金币问题 -->
			<div class="coin">
				<p class="red_font">金币问题</p>
				<c:if test="${!empty newHand}">
					<c:forEach var="problem" varStatus="status" items="${coin}">
						<p class="title">
								${problem.title} <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
						</p>
						<div class="title_content" style="display: none;">
								${problem.content}
						</div>
					</c:forEach>
				</c:if>
			</div>
			<!-- 提现问题 -->
			<div class="get_money">
				<p class="red_font in">提现问题</p>

				<c:if test="${!empty newHand}">
					<c:forEach var="problem" varStatus="status" items="${getMoney}">
						<p class="title border_bottom">
								${problem.title} <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
						</p>
						<div class="title_content" style="display: none;">
								${problem.content}
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>

	<script>
		var all = $(".main_list>div");
		var li = $(".main_ul>li");
		//获取到页面总高度
		var HeightAll = $("html,body").height();
		//获取滚动条位置
		var iTop = $(window).scrollTop();
		for (var i = 0; i < all.length; i++) {
			//楼层的联动
			for (var i = 0; i < li.length; i++) {
				li[i].onclick = function() {
					$(this).addClass("bg").siblings().removeClass("bg");
					for (var j = 0; j < li.length; j++) {
						if (this == li[j]) {
							$("html,body").animate({
								scrollTop : all[j].offsetTop
							}, 500);
						}
					}
				}
			}
		}
		// 标题点击隐藏/显示
		$(".title").on("click", function() {
			$(this).next().toggle();
			$(this).children(".logo").toggleClass("rot");
			$(this).toggleClass("border_bottom");
		})
	</script>

</body>
</html>