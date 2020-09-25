<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<title>${SYSPARAM.SITE_NAME}_邀请好友</title>
<%@ include file="../../../common/header.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no">
<meta name="HandheldFriendly" content="true">

<!-- <link rel="stylesheet" type="text/css" href="./common/css/base.css?t=1532617155796" /> -->
<script type="text/javascript"
	src="${basePath}static/shareNew/common/js/jquery-2.1.4.min.js?t=1532617155796"></script>
<script type="text/javascript"
	src="${basePath}static/shareNew/common/js/Util.js?t=1532617155796"></script>
<link rel="stylesheet" type="text/css"
	href="${basePath}static/shareNew/common/css/Popup.css?t=1532617155796" />
<script type="text/javascript"
	src="${basePath}static/shareNew/common/js/Popup.js?t=1532617155796"></script>
<script type="text/javascript"
	src="${basePath}static/shareNew/common/js/DataListGetter.js?t=1532617155796"></script>
<script type="text/javascript"
	src="${basePath}static/shareNew/resource-xdz/js/pages.js"></script>
<script type="text/javascript"
	src="${basePath}static/shareNew/resource-xdz/js/pages-min.js"></script>
<script type="text/javascript"
	src="${basePath}static/shareNew/resource-xdz/js/plug-in.js"></script>
<script type="text/javascript"
	src="${basePath}static/shareNew/resource-xdz/js/plug-in.min.js"></script>
<script type="text/javascript">
	var $ctx = "/jkd";

	todayDay = "";

	ossPath = "http://suishouyue.oss-cn-qingdao.aliyuncs.com";

	//跳转指定原生的方法

	function gotoOrign(type) {

		try {

			if ("" == "android") {

				if ("home" == type) {

					window.mobile.button(0);

				} else if ("video" == type) {

					window.mobile.button(1);

				} else if ("task" == type) {

					window.mobile.button(2);

				} else if ("me" == type) {

					window.mobile.button(3);

				}

			} else if ("" == "iOS") {

				loadEventFunc(type, "");

			}

		} catch (e) {

		}

	}

	function fenxiang()

	{

		//alert("分享朋友圈");

		android.showShare("人人头条", "我最近在玩人人头条APP，每天读资讯看视频轻松赚10块，一起来试试吧！",

		"http://game.appshow.cn/public/html/h5/jkd/weixin20/img2/216.png",

		"http://a.app.qq.com/o/simple.jsp?pkgname=com.huanxi.renrentoutiao");

	}

	function shareWeixin()

	{

		android.shareWeixin("人人头条", "我最近在玩人人头条APP，每天读资讯看视频轻松赚10块，一起来试试吧！",

		"http://game.appshow.cn/public/html/h5/jkd/weixin20/img2/216.png",

		"http://a.app.qq.com/o/simple.jsp?pkgname=com.huanxi.renrentoutiao");

	}

	function shareWeixinQuan()

	{

		android.shareWeixinQuan("人人头条", "我最近在玩人人头条APP，每天读资讯看视频轻松赚10块，一起来试试吧！",

		"http://game.appshow.cn/public/html/h5/jkd/weixin20/img2/216.png",

		"http://a.app.qq.com/o/simple.jsp?pkgname=com.huanxi.renrentoutiao");

	}

	function shareQQ() {
		android
				.shareQQ(
						"人人头条",
						"我最近在玩人人头条APP，每天读资讯看视频轻松赚10块，一起来试试吧！",
						"http://game.appshow.cn/public/html/h5/jkd/weixin20/img2/216.png",
						"http://a.app.qq.com/o/simple.jsp?pkgname=com.huanxi.renrentoutiao");
	}

	function qq_qrcode_share() {
		android
				.shareQrcode(
						"人人头条",
						"我最近在玩人人头条APP，每天读资讯看视频轻松赚10块，一起来试试吧！",
						"http://game.appshow.cn/public/html/h5/jkd/weixin20/img2/216.png",
						"http://a.app.qq.com/o/simple.jsp?pkgname=com.huanxi.renrentoutiao");
	}

	function shareIncomeToWeixin()

	{
		android
				.shareWeixinQuan(
						"人人头条",
						"我最近在玩人人头条APP，每天读资讯看视频轻松赚10块，一起来试试吧！",
						"http://game.appshow.cn/public/html/h5/jkd/weixin20/img2/216.png",
						"http://a.app.qq.com/o/simple.jsp?pkgname=com.huanxi.renrentoutiao");
	}
</script>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,viewport-fit=cover">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>邀请代理</title>
<link rel="stylesheet" href="${basePath}static/shareNew/css/style.css">
<link rel="stylesheet"
	href="${basePath}static/shareNew/css/swiper.min.css">
<script>
	var index = 120;
	var articlenum = 10;
	//设置rem
	function mobilecal() {
		var size = 100, //规定rem与px之间值的转换
		maxWidth = 750; //设置基准宽度。
		ratio = function() {
			var r = document.documentElement.clientWidth / maxWidth;
			return r >= 1 ? 1 : r <= 0.234 ? 0.234 : r;
		};
		set = function() {
			document.documentElement.style.fontSize = ratio() * size + 'px';
		}();
		window.onresize = mobilecal;
	}
	mobilecal();
</script>
</head>

<body>
	<div class="wrap">
		<div class="swiper-container swiper-no-swiping list">
			<div class="swiper-wrapper wrap">

				<div class="swiper-slide">
					<p class="texts">白云大道刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">꧁来福꧂刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">温柔霸道你鑫哥 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">春天的阳光 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">海洋 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">陌上花开 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">梅 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">郭青゛ 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">高风云 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">愤怒的小鸟 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">@癸 刚刚微信提现5元</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">明天会更好 刚刚微信提现5元</p>
				</div>
			</div>
		</div>
		<div class="banner">
			<div class="my">
				<p>邀请好友攻略</p>
				<p class="center_align"></p>
			</div>
			<img class="share_banner"
				src="${basePath}static/shareNew/img2/new2_2.png"> <img
				class="share_money pulse"
				src="${basePath}static/shareNew/img2/share_money.png"
				onclick="fenxiang();" alt="">
		</div>
		<ul class="tab">
			<li class="red_color my_glod">如何赚钱</li>
			<li class="my_fun">代理奖励</li>
		</ul>
		<div class="main">
			<div class="content top">
				<ul class="img_list">
					<li><img src="${basePath}static/shareNew/img2/one.png" alt="">
					</li>
					<li><img src="${basePath}static/shareNew/img2/two.png" alt="">
					</li>
					<li><img src="${basePath}static/shareNew/img2/seven.png"
						alt=""></li>
				</ul>
				<div class="rule1">
					<p class="oneinfro4">奖励说明</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right img_left"
							src="${basePath}static/shareNew/img2/icon_1.png">徒弟首次提现奖励<span>1</span>元
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right img_left"
							src="${basePath}static/shareNew/img2/icon_2.png">徒弟二次提现奖励<span>2</span>元
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right img_left"
							src="${basePath}static/shareNew/img2/icon_3.png">徒弟三次提现奖励<span>3</span>元
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right img_left"
							src="${basePath}static/shareNew/img2/icon_4.png">徒弟提现累计满100奖励<span>8</span>元
					</p>
				</div>
				<div class="how"">
					<p class="oneinfro4">如何月入8000</p>
					<p class="oneinfro2 marginT-15" style="color: #969696;">
						例如：您有20个徒弟，200个我的分销商，每人每天赚5元， 您的收益如下：</p>
					<p class="oneinfro2 marginT-15">
						<span style="color: #FF9600;">代理奖励</span>=20人*15元=<span
							style="color: #F83838;">300元</span></br> <span style="color: #FF9600;">徒弟进贡</span>=20人*5元*30%=30元/天<span
							style="color: #F83838;">(月入300元)</span></br> <span
							style="color: #FF9600;">我的分销商进贡</span>=200人*5元*20%=200元/天<span
							style="color: #F83838;">(月入6000元)</span></br>
					</p>
				</div>
				<div class="how">
					<div class="g-1">
						<img class="share_banner icon_right"
							src="${basePath}static/shareNew/img2/g-1.png">
					</div>
				</div>
			</div>

			<div class="content bottom">
				<!-- 代理小技巧 -->
				<div class="skill">
					<p class="oneinfro4">如何代理</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right img_left"
							src="${basePath}static/shareNew/img2/icon_1.png">
						好友通过您分享的链接(二维码)下载登陆APP， 则好友成为你的徒弟。
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right img_left"
							src="${basePath}static/shareNew/img2/icon_1.png">分享给家人、朋友、同学、同事，代理成功率
						更高。
					</p>

				</div>
			</div>
		</div>
	</div>


	<!-- 底部导航 -->
	<div class="nav">
		<ul class="nav_list">
			<li class="first_nav" onclick="share('weixin')">
				<dl>
					<dt>
						<img src="${basePath}static/shareNew/img2/1.png" alt="">
					</dt>
					<dd>微信邀请</dd>
				</dl>
			</li>
			<li class="first_nav" onclick="share('weixintmline')">
				<dl>
					<dt>
						<img src="${basePath}static/shareNew/img2/2.png" alt="">
					</dt>
					<dd>朋友圈邀请</dd>
				</dl>
			</li>

			<li class="first_nav" onclick="share('qq')">
				<dl>
					<dt>
						<img src="${basePath}static/shareNew/img2/3.png" alt="">
					</dt>
					<dd>QQ邀请</dd>
				</dl>
			</li>
			<li class="first_nav" onclick="qrcode_share()">
				<dl>
					<dt>
						<img src="${basePath}static/shareNew/img2/4.png" alt="">
					</dt>
					<dd>晒图邀请</dd>
				</dl>
			</li>
		</ul>


		<%-- <ul class="navbottom_list" style="border:0;padding-top:0.2rem;padding-bottom:0.2rem;">
						<li class="first_nav" onclick="share('weixin')">
							<dl>
								<dt>
									<img src="${basePath}static/shareNew/img2/n-4.png" alt="">
								</dt>
								<dd>分享赚钱</dd>
							</dl>
						</li>
						<li class="first_nav" onclick="share('weixintmline')">
							<dl>
								<dt>
									<img src="${basePath}static/shareNew/img2/n-1.png" alt="">
								</dt>
								<dd>视频</dd>
							</dl>
						</li>
						
						<li class="first_nav" onclick="share('qq')">
							<dl>
								<dt>
									<img src="${basePath}static/shareNew/img2/n-3.png" alt="">
								</dt>
								<dd>代理赚钱</dd>
							</dl>
						</li>
						<li class="first_nav" onclick="qrcode_share()">
							<dl>
								<dt>
									<img src="${basePath}static/shareNew/img2/n-2.png" alt="">
								</dt>
								<dd>提现</dd>
							</dl>
						</li>
					</ul> --%>
	</div>
</body>
<script type="text/javascript"
	src="${basePath}static/shareNew/common/js/jquery-2.1.4.min.js"></script>
<script src="${basePath}static/shareNew/js/clipboard.min.js"></script>
<script src="${basePath}static/shareNew/js/swiper.min.js"></script>
<script>
	$(function() {
		//图片加速
		//   var arr = ["200648","200835","200698","200547"];
		//  console.log($("img").length);
		//  var indexs = Math.floor((Math.random()*arr.length)); 
		//  for(var i=0;i<$("img").length;i++){
		//     var url=$("img")[i].src;
		//     console.log(url)
		//     $("img").eq(i).attr("src","https://img03.sogoucdn.com/v2/thumb/?appid="+arr[indexs]+"&url="+url);
		//     //console.log($("img").eq(i).src())
		//  }

		//点击复制邀请码
		var clipboard = new Clipboard('.invite', {
			// 通过target指定要复印的节点
			target : function() {
				return document.getElementById('int');
				$(".copy_box").show();
			}
		});
		//点击复制链接
		var clipboard1 = new Clipboard('.copys', {
			// 通过target指定要复印的节点
			target : function() {
				return document.getElementById('links');
				$(".copy_box").show();
			}
		});
		var t2;
		function copyhide() {
			$("#d_cpurl").hide();
			window.clearInterval(t2);
		}
		clipboard1.on('success', function(e) {
			$("#d_cpurl").show();
			t2 = window.setInterval(copyhide, 700);
		});

		clipboard1.on('error', function(e) {
			console.log(e);
		});
		clipboard.on('success', function(e) {
			$("#d_cpurl").show();
			t2 = window.setInterval(copyhide, 700);
		});

		clipboard.on('error', function(e) {
			console.log(e);
		});
		//轮播
		var mySwiper = new Swiper('.swiper-container', {
			direction : 'vertical',
			loop : true,
			speed : 2000,
			slidesPerView : 1,
			autoplay : {
				delay : 2000
			},
		})
		//有钱可去提现
		var int1 = "1.83";
		if (parseFloat(int1) > 0) {
			$(".sp2").css("background", "#FE7739");
			$(".sp2").click(function() {
				window.location.href = "./userWithdraw/userWithdraw.action";
			})
		}
		//点击我知道了
		$(".bottom_con").click(function() {
			$(".contenter").hide();
		})
		$(".tab").on("click", "li", function() {
			var ind = $(this).index();
			console.log(ind);
			$(this).addClass("red_color").siblings().removeClass("red_color");
			$(".content").eq(ind).show().siblings().hide();
		})
		//奖励发放条件
		$(".way").click(function() {
			$(".cover_1").show();
		})
		$(".close1").click(function() {
			$(".cover_1").hide();
		})
		//跳转邀请好友
		var code = 101694;
		$(".my").click(function() {
			$("html,body").animate({scrollTop:$(".rule1").offset().top-200},1);
			//window.location.href = "code.jsp?code=" + code;
		})
		$(".cancel").click(function() {
			$(".sharebox").hide();
		})
		var page = 1;
		var bool = true;
		$(".my_glod").click(function() {
			bool = true;
		})
		$(".my_fun").click(function() {
			bool = false;
			bools();
		})
		//有没有代理
		var person = "1";
		if (parseInt(person) == 0) {
			$(".myfriend").hide();
			$(".none").show();
		} else {
			$(".myfriend").show();
			$(".none").hide();
		}
		function ajasfriend(page, pagesize, orderByColumn) {
			$.ajax({
				async : false,
				url : $ctx + "/weixin20/member/getUserLink.action",
				data : {
					page : page,
					orderByColumn : orderByColumn,
					pagesize : pagesize
				},
				success : function(data) {
					var datas = data.datas;
					console.log(datas);
					if ("ok" != data.ret) {
						datas = [];
						return;
					} else {
						for (var i = 0; i < datas.length; i++) {
							if (datas[i].username == datas[i].usercode) {
								datas[i].username = "游客" + datas[i].username;
							}
							lists(datas[i].username, datas[i].upmoney / 1000,
									datas[i].upgold);
						}
					}
				}
			})
		}
		window.onload = function() {
			ajasfriend(page, 10);
		}
		function bools() {

			// 点击监测页面滚动
			$(window).scroll(function() {
				// console.log("w=" + bool)
				var scrollTop = $(this).scrollTop();
				var scrollHeight = $(document).height();
				var windowHeight = $(this).height();
				if (scrollTop + windowHeight == scrollHeight) {
					if (bool == false) {
						// console.log("执行滚动到底部")
						page++;
						ajasfriend(page, 10);
					} else {
						// console.log("走的else")
						return;
					}

				}
			});
		}

	})
	//好友
	function lists(name, yuan, coin) {
		listhtml = '<ul class="allfriend">' + '<li class="name">' + name
				+ '</li>' + '<li><span class="obtain">' + yuan
				+ '</span>元</li>' + '<li><span class="coin">' + coin
				+ '</span>金币</li>' + '</ul>';
		$(".bottom").append(listhtml);
	}
	function loadShareIOS(sharewechat, title, text, image, url, wxurl) {
		var json;
		if (myversion1 > 540) {
			json = {
				"sharewechat" : sharewechat,
				"sharetitle" : title,
				"text" : text,
				"sharepic" : image,
				"shareurl" : url,
				"shareurl" : wxurl
			}
		} else {
			json = {
				"sharewechat" : sharewechat,
				"title" : title,
				"text" : text,
				"image" : image,
				"url" : url,
				"wxurl" : wxurl
			}
		}
		loadURLFunc(JSON.stringify(json));
	}
	var openid = "62fb33bcd15743dda4275fe8c1246632";
	var version = "";

	function share(sharewechat) {

		if (sharewechat == "weixintmline") { //朋友圈分享
			android.openweixinquan("", "receive");
			/* if (myversion > 38) { //新版分享客户端调后台获取数据..不用传数据
				window.mobile.openweixinquan("", "receive");
				android.openweixinquan("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			} */
		} else if (sharewechat == "weixin") { //微信分享
			/* if (myversion > 38) {
				window.mobile.openweixin("", "receive");
				android.openweixin("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			} */
			android.openweixin("", "receive");
		} else if (sharewechat == "qq") { //qq分享
		/* 	if (myversion > 38) {
				window.mobile.openqq("", "receive");
				android.openqq("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			} */
			android.openqq("", "receive");
		} else { //qq空间
			if (myversion > 38) {
				window.mobile.shareToQzoneJs("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			}
		}
	}
	var myversion = parseInt(version); //判断版本号
	var myversion1 = parseInt(version.replace(/\./g, ''));//ios
	function qrcode_share() {
		android.openurl();
		return ;
		//跳转原生二维码邀请
		if ("" == "android") {
			if (myversion > 34) {
				try {
					window.mobile.openurl();
					android.openurl();
				} catch (e) {
				}
			} else {
				window.location.href = $ctx
						+ "/weixin20/member/receiveMonkeyXd3.action";
			}
		} else if ("" == "iOS") {
			if (myversion1 > 539) {
				openurl("share");
			} else {
				window.location.href = $ctx
						+ "/weixin20/member/receiveMonkeyXd3.action";
			}
		}

	}

	function getShareData(sharewechat) {
		var sharetype = "qq";
		if (sharewechat == "weixintmline") {
			sharetype = "timeline";
		} else if (sharewechat == "weixin") {
			sharetype = "timegroup";
		} else if (sharewechat == "qzone") {
			sharetype = "qqspace";
		}

		$.ajax({
			type : "POST",
			url : "getShareDate.action",
			data : {
				openid : openid,
				sharetype : sharetype,
				shareclass : "receive"
			},
			dataType : "json",
			success : function(data) {
				console.log(data);
				var sharemodel = data.sharemodel;
				var sharetitle = data.sharetitle;
				var sharecontext = data.sharecontext;
				var sharepic = data.sharepic;
				var shareurl = data.shareurl;
				var flag = "0";
				if ("" == "iOS") {
					if (sharewechat == "message") { //短信邀请
						messageinvite(sharetitle);
					} else {
						loadShareIOS(sharewechat, sharetitle, sharecontext,
								sharepic, shareurl, shareurl);
					}
				} else {
					if (sharewechat == "weixintmline") { //微信朋友圈
						if (data.sharemodel == "2") {
							window.mobile.share(sharetitle, sharecontext,
									sharepic, shareurl, shareurl, sharewechat);
						} else {
							window.mobile.shouTuShareToWXAPP("3", sharetitle,
									sharepic);
							flag = "1";
						}
					} else if (sharewechat == "message") { //短信邀请
						window.mobile.shareToSms(sharetitle);
					} else {
						window.mobile.share(sharetitle, sharecontext, sharepic,
								shareurl, shareurl, sharewechat);
					}
				}
				//记录分享
				if (flag == "0") {
					$.ajax({
						type : "POST",
						url : "shareReceive.action",
						data : {
							openid : openid,
							url : shareurl,
							sharewechat : sharewechat
						},
						dataType : "json",
						success : function(data) {
							console.log("成功");
						}
					});
				}

			}
		});
	}
	//判断版本来决定是否显示短信邀请
	if ("" == "android") {
		if (myversion <= 37) {
			$(".short_message").hide();
			$(".way_six").html("方法四: 微信邀请");
		}
	} else if ("" == "iOS") {
		if (myversion1 < 540 || myversion1 == 540) {
			$(".short_message").hide();
			$(".way_six").html("方法四: 微信邀请");
		}
	}
	function message() {
		//跳转原生短信邀请
		if (myversion1 > 540 || myversion > 37) {
			getShareData('message');
		}
	}
	function shareIncomeFunc(shareType) {
		var tipTitle = "", tipContent = "", money = "", tipBottom = "";
		if ("1" == shareType) {
			tipTitle = "提现已受理<br>请到微信零钱中查看";
			tipContent = "浪迹天涯累计提现";
			money = parseInt("0");//"-19.0";
			tipBottom = "长按识别二维码，现金红包拿到手软";

			if (money == 0) {
				$(".contenter").show()
				return;
			} else {
				//跳转原生晒提现
				if ("" == "android") {
					if (myversion > 34) {
						try {
							window.mobile.jumpWithDraw();
						} catch (e) {
						}
					} else {
						$("#shareTipDiv").removeClass("hide");
					}
				} else if ("" == "iOS") {
					if (myversion1 > 538) {
						exposureCashFunc("share");
					} else {
						$("#shareTipDiv").removeClass("hide");
					}

				}
			}
		} else if ("2" == shareType) {
			tipTitle = "";
			tipContent = "浪迹天涯累计收入";
			money = "1835";
			tipBottom = "长按识别二维码，金币拿到手软";
			if (money == 0) {
				$(".contenter").show()
				return;
			} else {
				//跳转原生晒收入
				if ("" == "android") {
					if (myversion > 31) {
						try {
							window.mobile.jumpInicomeAcitivity();
						} catch (e) {
						}
					} else {
						$("#shareTipDiv").removeClass("hide");
					}
				} else if ("" == "iOS") {
					if (myversion1 > 529) {
						shareIncomeFun("share");
					} else {
						$("#shareTipDiv").removeClass("hide");
					}

				}

				return;
			}
		}
		$("#shareType").val(shareType);
		$("#tipContent").text(tipContent);
		$(".money").text("¥" + changeTwoDecimal_f(money / 1000));
		$("#tipBottom").text(tipBottom);
	}
	//判断哪个版本以前可以调原生
	$(".share_banner,.share_money").click(function() {
		if ("" == "android") {
			if (myversion > 34) {
				try {
					window.mobile.shareNow();
				} catch (e) {
				}
			} else {
				//alert(222);
				$(".sharebox").show();
			}
		} else if ("" == "iOS") {
			if (myversion1 > 539) {
				alertshare("share");
			} else {
				$(".sharebox").show();
			}
		}

	})
	//复制链接邀请
	var t1;
	var copyStLink = new Clipboard('#copyStLink', {
		text : function() {
			var sturl = "";
			return sturl;
		}
	});
	function copyhide() {
		$("#d_cpurl").hide();
		window.clearInterval(t1);
	}
	copyStLink.on('success', function(e) {
		$("#d_cpurl").show();
		t1 = window.setInterval(copyhide, 700);
	});

	copyStLink.on('error', function(e) {
		console.log(e);
	});
</script>

</html>