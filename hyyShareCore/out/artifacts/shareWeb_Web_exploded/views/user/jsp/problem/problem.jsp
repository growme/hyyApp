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
				<p class="title">
					聚看点是什么? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					平台致力于打造一款新形式的资讯阅读软件，以移动应用为载体进行内容创造、资讯阅读，提供更多有用、有趣、有益的内容给大家.</div>
				<p class="title">
					聚看点为什么会给您奖励? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					为了引领全民阅读，帮助大家养成良好的阅读习惯，在阅读文章/视频的过程中，平台将给予一定的金币奖励！而奖励的金币也会在次日自动换算成零钱，让您在阅读过程中不仅增长见识，而且还能有所收益！
				</div>
				<p class="title">
					邀请好友有什么好处? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					邀请好友即为收徒，能让您的收益迅速增加，现在每收一位有效徒弟奖励9元零钱，分15次发放，每日好友有效阅读5篇文章，您即可获得当日现金奖励；第一天奖励2元，第2～15天每天奖励0.5元，每位好友每天限奖励师傅一次；而且好友分享文章被有效阅读会贡献给您更多金币！
				</div>
			</div>
			<!-- 金币问题 -->
			<div class="coin">
				<p class="red_font">金币问题</p>
				<p class="title">
					金币是什么? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					金币是聚看点里面的货币单位。当天所赚取的金币会在第二天凌晨0-6点自动转换成零钱，并累计到您的聚看点账户.</div>
				<p class="title">
					金币如何换算成零钱? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					零钱=昨日金币*昨日汇率/10000，这里有一个汇率的概念，汇率值与平台运营收益有关。因平台每天的运营收益都不一样，汇率也会受影响而上下浮动，每日凌晨会通过系统消息公告昨日的汇率及您的收益情况.
				</div>
				<p class="title border_bottom">
					一金币等于多少人民币? <span class="logo rot"> <img
						src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: block;">
					一个金币等于多少钱并不固定，通常情况聚看点的运营收益越高，金币价值越高，具体以次日转换为准！</div>
				<p class="title">
					为什么看了文章没有金币? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					为了鼓励大家认真阅读，阅读文章会有一定几率获得金币奖励，但并不是每次阅读都有奖励哦！这与你平时的阅读习惯有关，例如短时间内快速频繁浏览资讯、文章没看完、随意滚屏等，获得金币的几率就很低，请以平常心去阅读您感兴趣的内容
				</div>
				<p class="title border_bottom">
					签到奖励/开宝箱奖励 <span class="logo rot"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: block;">
					签到奖励——每日签到是给新老用户的福利！第一天40金币、第二天60金币......第七天300金币。
					最高奖励300金币，后续连续不间断签到皆为300 金币。若中断则从第一天开始从新计算。<br>宝箱奖励——开宝箱也是一项福利任务！在首页搜索栏的旁边会显示开宝箱的时间。宝箱每20分钟开一次，在线时间越长，宝箱奖励越高。
				</div>
				<p class="title">
					邀请码是什么?怎么用? <span class="logo"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					邀请码是聚看点平台为每位用户设计的独立ID，只在邀请好友时使用 <br>两种情况: <br> a.
					分享邀请链接给朋友在线注册，链接中已带有您的邀请码，因此不需要再手动填写 <br> b.
					如果朋友是从手机应用商店搜索‘聚看点’先安装后注册的，您需要让朋友登陆后填写您的邀请码进行绑定，这样才能成为您的徒弟哦
				</div>
				<p class="title">
					分享出去的收徒二维码打不开怎么办? <span class="logo"> <img
						src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: none;">
					方法一：告诉朋友到手机应用商店，搜索‘聚看点’下载安装，注册登录后到右下角‘我的’界面找到‘输入邀请码’一栏，填写您的邀请码完成绑定
					<br>方法二：重新分享一次二维码给朋友，最新的可正常打开
				</div>
			</div>
			<!-- 提现问题 -->
			<div class="get_money">
				<p class="red_font in">提现问题</p>
				<p class="title border_bottom">
					如何兑换? <span class="logo rot"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: block;">
					当零钱达到一定金额，您可进入‘我的’页面-&gt;‘兑换提现’一栏，选择兑换商品或提现（订单一旦兑换将无法取消，故请再三确认！）</div>
				<p class="title border_bottom">
					如何微信提现? <span class="logo rot"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: block;">
					1、提交提现申请后，系统会自动将您的提现金额转入您的微信零钱，请自行查收 <br>2、24小时未到账的用户可以进入我们的客服qq群进行问题反馈
				</div>
				<p class="title border_bottom">
					提现多久到账? <span class="logo rot"> <img src="${basePath}static/images/problem/logo.png">
					</span>
				</p>
				<div class="title_content" style="display: block;">
					提现申请提交后，次日开始计算2个工作日内处理完成（双休日、节假日顺延)</div>
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