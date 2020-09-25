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
<script type="text/javascript"
	src="${basePath}static/shareNew/common/js/jquery-2.1.4.min.js?t=1532617155796"></script>
<link rel="stylesheet" href="${basePath}static/boheyaoqing/css/css.css">
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

	function fenxiang() {
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
</script>
</head>

<body>
	<div class="top">
		<div id="broadcast" class="bar" name="giftactive">
			<div id="demo"
				style="overflow: hidden; height: 22px; line-height: 22px;">
				<ul class="mingdan" id="holder">
					<li><a href="#" target="_blank">百合刚邀请了45人获得514元</a></li>
					<li><a href="#" target="_blank">于大锤刚邀请了25人获得695元</a></li>
					<li><a href="#" target="_blank">猪公子 (-^〇^-)刚邀请了41人获得245元</a></li>
					<li><a href="#" target="_blank">杨春胜คิดถึง刚邀请了30人获得134元</a></li>
					<li><a href="#" target="_blank">寻梦刚邀请了15人获得145元</a></li>
					<li><a href="#" target="_blank">执星 绘梦刚邀请了6人获得45元</a></li>
					<li><a href="#" target="_blank">峰花雪月刚邀请了8人获得45元</a></li>
					<li><a href="#" target="_blank">意中人刚邀请了5人获得15元</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="banner">
		<div class="bannerimg">
			<!--攻略-->
			<a class="gonglue" href="javascript:void();"> </a>
			<!--规则-->
			<a class="guize" href="javascript:void();"> </a> <img
				src="${basePath}static/boheyaoqing/images/yq1_02.jpg"> <a
				class="yqbtn"> <img
				src="${basePath}static/boheyaoqing/images/yq1_02_03.png"
				onclick="fenxiang();">
			</a>
			<div class="yqcode">
				我的邀请码<span id="int">${user.visitCode }</span> <a class="invite">点击复制</a>
			</div>
		</div>
		<div class="najiangli">
			<div class="najianglibox">
				<p class="">
					<img src="${basePath}static/boheyaoqing/images/t2.png"
						style="width: 100%">
				</p>
			</div>
			<div class="jianglicon">
				<div class="jianglicon1">
					<div class="jiangliintro">
						<p>徒弟首次提现</p>
						<p style="font-size: 28px">+1元</p>
					</div>
					<div class="jiangliintro">
						<p>徒弟二次提现</p>
						<p style="font-size: 28px">+3元</p>
					</div>
					<div class="jiangliintro">
						<p>徒弟三次提现</p>
						<p style="font-size: 28px">+4元</p>
					</div>

				</div>
				<div class="jianglicon2">
					<div class="jiangliintro jiangliintro3">
						<p>徒弟收益满100</p>
						<p style="font-size: 28px">奖励8元</p>
					</div>
					<div class="jiangliintro jiangliintro4">
						<p>好友分享文章，你躺赚贡献</p>
						<p>徒弟收益永久分成</p>
					</div>


				</div>
				<p class="hytip">好友完成任务，你拿现金奖励</p>
			</div>
		</div>
	</div>

	<div class="yqjiangli">
		<p class="title">
			<img src="${basePath}static/boheyaoqing/images/index_08.png">
		</p>
		<div class="yqjlcon">
			<p class="whyqy">
				为什么有的人可以<span>每月提现过万</span>， 而你却只能拿到几块钱？原因就差在邀请好友这里！
			</p>
			<p class="whyqy">
				成功邀请一个好友即可<span>获得16元现金红包</span>， 邀请的好友越多，奖励越多，还有其他额外红包惊喜哦！
			</p>
			<p class="whyqy">
				<span> 分享3个50人以上的微信群，成功邀请的概率提升300%！ </span>
			</p>
			<p class="whyqy">根据大数据统计发现，邀请亲戚，朋友，同学家人的成功率最高</p>
			<p class="ljyq2">
				<img src="${basePath}static/boheyaoqing/images/index_11.png"
					onclick="fenxiang();">
			</p>
		</div>
	</div>
	<div class="liwu">
		<p class="title">
			<img src="${basePath}static/boheyaoqing/images/yaoqing3_03.png">
		</p>
		<Div class="kede">
			<p>
				<img src="${basePath}static/boheyaoqing/images/yaoqing3_032.png">
			</p>
			<p class="kedetxt">
				说明：分享被阅读每日贡献好友数不设上限；代理越多，赚的越多！。另外好友打开app，分享文章，分享视频你你都可以获得徒弟收益提成，永久享受！
			</p>
		</Div>
		<div class="liwu3">
			<img src="${basePath}static/boheyaoqing/images/yq6_02.png">
		</div>
	</div>
	<div class="paihang">
		<p class="title">
			<img src="${basePath}static/boheyaoqing/images/yq7_03.png">
		</p>
		<div class="phall">
			<p class="phrenshu">
				共<span>4545</span>人参加
			</p>
			<div class="mouthtab">
				<div class="mouthtabitem active" id="zph">
					<p>总榜单</p>
					<p>截至今日总排名</p>
				</div>
				<div class="mouthtabitem " id="yph">
					<p>月榜单</p>
					<p>本月截至今日排名</p>
				</div>
			</div>
			<div class="nobang">还未进入榜单，继续努力呀！</div>
			<div class="three zphlist" style="display:;">
				<c:forEach items="${zlist.results}" var="item" varStatus="status"
					begin="1" end="1">
					<div class="three1">
						<div class="thimg">
							<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
								<img src="${item.memberIcon }">
							</c:if>
							<c:if test="${item.showColor =='' || item.memberIcon == null}">
								<img src="${basePath}static/boheyaoqing/images/touxiang.png">
							</c:if>

							<p class="thimgbanh">
								<img src="${basePath}static/boheyaoqing/images/yq7_10.png">
							</p>
						</div>
						<p class="name">${item.memberName}</p>
						<p class="tong">
							奖励
							<fmt:formatNumber type="number" value="${item.showColor } "
								maxFractionDigits="0" />
							元
						</p>
					</div>
				</c:forEach>
				<c:forEach items="${zlist.results}" var="item" varStatus="status"
					begin="0" end="0">
					<div class="three2">
						<div class="thimg2">
							<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
								<img src="${item.memberIcon }">
							</c:if>
							<c:if test="${item.showColor =='' || item.memberIcon == null}">
								<img src="${basePath}static/boheyaoqing/images/touxiang.png">
							</c:if>
							<p class="thimg2guan">
								<img src="${basePath}static/boheyaoqing/images/yq7_07.png">
							</p>
						</div>
						<p class="name">${item.memberName}</p>
						<p class="jin">
							奖励
							<fmt:formatNumber type="number" value="${item.showColor } "
								maxFractionDigits="0" />
							元
						</p>

					</div>
				</c:forEach>
				<c:forEach items="${zlist.results}" var="item" varStatus="status"
					begin="2" end="2">
					<div class="three3">
						<div class="thimg3">
							<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
								<img src="${item.memberIcon }">
							</c:if>
							<c:if test="${item.showColor =='' || item.memberIcon == null}">
								<img src="${basePath}static/boheyaoqing/images/touxiang.png">
							</c:if>

							<p class="thimg3guan">
								<img src="${basePath}static/boheyaoqing/images/yq7_13.png">
							</p>
						</div>
						<p class="name">${item.memberName}</p>
						<p class="tong">
							奖励
							<fmt:formatNumber type="number" value="${item.showColor } "
								maxFractionDigits="0" />
							元
						</p>
					</div>
				</c:forEach>
			</div>
			<c:forEach items="${zlist.results}" var="item" varStatus="status"
				begin="3">
				<div class="pmlist zphlist" style="display:;">
					<div class="pmlist1">${status.index+1}</div>
					<div class="pmlist2">
						<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
							<img src="${item.memberIcon }">
						</c:if>
						<c:if test="${item.showColor =='' || item.memberIcon == null}">
							<img src="${basePath}static/boheyaoqing/images/touxiang.png">
						</c:if>
					</div>
					<div class="pmlist3">${item.memberName}</div>
					<div class="pmlist4">
						<span><fmt:formatNumber type="number"
								value="${item.showColor } " maxFractionDigits="1" /></span>元
					</div>
				</div>
			</c:forEach>

			<div class="three yphlist" style="display: none;">
				<c:forEach items="${ylist.results}" var="item" varStatus="status"
					begin="1" end="1">
					<div class="three1">
						<div class="thimg">
							<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
								<img src="${item.memberIcon }">
							</c:if>
							<c:if test="${item.showColor =='' || item.memberIcon == null}">
								<img src="${basePath}static/boheyaoqing/images/touxiang.png">
							</c:if>

							<p class="thimgbanh">
								<img src="${basePath}static/boheyaoqing/images/yq7_10.png">
							</p>
						</div>
						<p class="name">${item.memberName}</p>
						<p class="tong">
							奖励
							<fmt:formatNumber type="number" value="${item.showColor } "
								maxFractionDigits="0" />
							元
						</p>
					</div>
				</c:forEach>
				<c:forEach items="${ylist.results}" var="item" varStatus="status"
					begin="0" end="0">
					<div class="three2">
						<div class="thimg2">
							<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
								<img src="${item.memberIcon }">
							</c:if>
							<c:if test="${item.showColor =='' || item.memberIcon == null}">
								<img src="${basePath}static/boheyaoqing/images/touxiang.png">
							</c:if>
							<p class="thimg2guan">
								<img src="${basePath}static/boheyaoqing/images/yq7_07.png">
							</p>
						</div>
						<p class="name">${item.memberName}</p>
						<p class="jin">
							奖励
							<fmt:formatNumber type="number" value="${item.showColor } "
								maxFractionDigits="0" />
							元
						</p>

					</div>
				</c:forEach>
				<c:forEach items="${ylist.results}" var="item" varStatus="status"
					begin="2" end="2">
					<div class="three3">
						<div class="thimg3">
							<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
								<img src="${item.memberIcon }">
							</c:if>
							<c:if test="${item.showColor =='' || item.memberIcon == null}">
								<img src="${basePath}static/boheyaoqing/images/touxiang.png">
							</c:if>

							<p class="thimg3guan">
								<img src="${basePath}static/boheyaoqing/images/yq7_13.png">
							</p>
						</div>
						<p class="name">${item.memberName}</p>
						<p class="tong">
							奖励
							<fmt:formatNumber type="number" value="${item.showColor } "
								maxFractionDigits="0" />
							元
						</p>
					</div>
				</c:forEach>
			</div>
			<c:forEach items="${ylist.results}" var="item" varStatus="status"
				begin="3">
				<div class="pmlist yphlist" style="display: none;">
					<div class="pmlist1">${status.index+1}</div>
					<div class="pmlist2">
						<c:if test="${item.memberIcon != '' && item.memberIcon != null}">
							<img src="${item.memberIcon }">
						</c:if>
						<c:if test="${item.showColor =='' || item.memberIcon == null}">
							<img src="${basePath}static/boheyaoqing/images/touxiang.png">
						</c:if>
					</div>
					<div class="pmlist3">${item.memberName}</div>
					<div class="pmlist4">
						<span><fmt:formatNumber type="number"
								value="${item.showColor } " maxFractionDigits="1" /></span>元
					</div>
				</div>
			</c:forEach>
		</div>

	</div>
	<div class="space"></div>
	<div class="footer">
		<p class="footertxt">每位好友点开分享链接，你都可得0.1元</p>
		<div class="footerbtn">
			<a href="javascript:void();" onclick="share('weixintmline')"> <img
				src="${basePath}static/boheyaoqing/images/yaoqing3_09.png">
				<p>微信邀请</p>
			</a> <a href="javascript:void();" onclick="qrcode_share()"> <img
				src="${basePath}static/boheyaoqing/images/yaoqing3_11.png">
				<p>晒图邀请</p>
			</a> <a href="javascript:void();" onclick="share('weixin')"> <img
				src="${basePath}static/boheyaoqing/images/yaoqing3_13.png">
				<p>朋友群邀请</p>
			</a> <a href="javascript:void();" onclick="share('qq')"> <img
				src="${basePath}static/boheyaoqing/images/yaoqing3_15.png">
				<p>QQ好友</p>
			</a>
		</div>
	</div>
	<script type="text/javascript">
		function AutoScroll(obj) {
			$(obj).find("ul:first").animate({
				marginTop : "-22px"
			}, 500, function() {
				$(this).css({
					marginTop : "0px"
				}).find("li:first").appendTo(this);
			});
		}
		$(document).ready(function() {
			setInterval('AutoScroll("#demo")', 1000)
		});
		$("#zph").click(function(event) {
			zph();
		});
		$("#yph").click(function(event) {
			yph();
		});
		function yph() {
			$("#zph").attr("class", "mouthtabitem");
			$("#yph").attr("class", "mouthtabitem active");
			$(".zphlist").hide();
			$(".yphlist").show();
		}
		function zph() {
			$("#yph").attr("class", "mouthtabitem");
			$("#zph").attr("class", "mouthtabitem active");
			$(".yphlist").hide();
			$(".zphlist").show();
		}
		$(".gonglue").click(function() {
			$("html,body").animate({
				scrollTop : $(".najiangli").offset().top - 10
			}, 1);
		})
		$(".guize").click(function() {
			$("html,body").animate({
				scrollTop : $(".yqjiangli").offset().top - 10
			}, 1);
		})
	</script>
</body>
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
			$("html,body").animate({
				scrollTop : $(".rule1").offset().top - 200
			}, 1);
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
		return;
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