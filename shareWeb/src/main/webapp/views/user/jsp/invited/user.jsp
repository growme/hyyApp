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
<script type="text/javascript"
	src="${basePath}static/invited/common/js/jquery-2.1.4.min.js?t=1532617155796"></script>

<script type="text/javascript"
	src="${basePath}static/invited/common/js/Util.js?t=1532617155796"></script>

<link rel="stylesheet" type="text/css"
	href="${basePath}static/invited/common/css/Popup.css?t=1532617155796" />

<script type="text/javascript"
	src="${basePath}static/invited/common/js/Popup.js?t=1532617155796"></script>

<script type="text/javascript"
	src="${basePath}static/invited/common/js/DataListGetter.js?t=1532617155796"></script>

<script type="text/javascript"
	src="${basePath}static/invited/resource-xdz/js/pages.js"></script>

<script type="text/javascript"
	src="${basePath}static/invited/resource-xdz/js/pages-min.js"></script>

<script type="text/javascript"
	src="${basePath}static/invited/resource-xdz/js/plug-in.js"></script>

<script type="text/javascript"
	src="${basePath}static/invited/resource-xdz/js/plug-in.min.js"></script>
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
            <meta name="viewport" content="width=device-width, initial-scale=1.0,viewport-fit=cover">
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <title>邀请邀请</title>
            <link rel="stylesheet" href="${basePath}static/invited/css/style.css">
            <link rel="stylesheet" href="${basePath}static/invited/css/swiper.min.css">
            <script>
                var index = 120;
                var articlenum = 10;
                //设置rem
                function mobilecal() {
                    var size = 100, //规定rem与px之间值的转换
                        maxWidth = 750; //设置基准宽度。
                    ratio = function () {
                        var r = document.documentElement.clientWidth / maxWidth;
                        return r >= 1 ? 1 : r <= 0.234 ? 0.234 : r;
                    };
                    set = function () {
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
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt="">
						Rover 刚邀请1位好友将获得3元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> 丫头
						刚邀请3位好友将获得30元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> ꧁来福꧂
						刚邀请1位好友将获得10元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt="">
						温柔霸道你鑫哥 刚邀请2位好友将获得20元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt="">
						春天的阳光 刚邀请4位好友将获得40元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> 海洋
						刚邀请6位好友将获得60元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> 陌上花开
						刚邀请2位好友将获得20元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> 梅
						刚邀请5位好友将获得50元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> 郭青゛
						刚邀请1位好友将获得10元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> 高风云
						刚邀请2位好友将获得20元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt="">
						愤怒的小鸟 刚邀请3位好友将获得30元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt=""> @癸
						刚邀请5位好友将获得50元
					</p>
				</div>
				<div class="swiper-slide">
					<p class="texts">
						<img src="${basePath}static/invited/img2/l.png" alt="">
						明天会更好 刚邀请4位好友将获得40元
					</p>
				</div>
			</div>
		</div>
		<div class="banner">
			<div class="my" style="display: none">
				<p>我的邀请码</p>
				<p class="center_align">${user.visitCode }</p>
				<img class="toole" src="${basePath}static/invited/img2/san.png"
					alt="">
			</div>
			<img class="share_banner"
				src="${basePath}static/invited/img2/new2_2.png">
			<p class="share_money_p">一级好友分享阅读奖励提成</p>
			<img class="share_money pulse"
				src="${basePath}static/invited/img2/share_money.png"
				onclick="fenxiang();" alt="">
		</div>
		<ul class="tab">
			<li class="red_color my_glod"><img
				class="share_banner icon_right"
				src="${basePath}static/invited/img2/icon_right.png">奖励说明<img
				class="share_banner icon_left"
				src="${basePath}static/invited/img2/icon_left.png"> <b
				class="line"></b></li>
			<li class="my_fun" style="display: none">我的好友</li>
		</ul>
		<div class="main">
			<div class="content top">
				<div class="tit">
					<!--<img src="${basePath}static/invited/img2/p2.png" alt="">-->
					<p class="oneinfro">
						邀请 <span>2-3元</span>奖励怎么领取？
					</p>
					<p class="oneinfro1">好友第一次提现以后，您即可获得随机现金</p>
					<p class="oneinfro1">奖励2-3元</p>
				</div>


				<!-- 邀请小技巧 -->
				<div class="skill">
					<p class="oneinfro3">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_right.png">邀请小技巧<img
							class="share_banner icon_left"
							src="${basePath}static/invited/img2/icon_left.png">
					</p>
					<div class="lines"></div>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_yaoqing.png">邀请成功率提升<span>200%</span>
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_yaoqing.png">邀请您的家人、朋友、同学、同事成功率
						<span>最高</span>哦。
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_yaoqing.png">邀请成功您将获得最低
						<span>2-3元现金</span>红包，邀请越多，奖励越多！
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_yaoqing.png">分享到朋友圈或3个以上微信群，收徒成功率
						<span>提升200%</span>。
					</p>
					<p class="oneinfro2 marginT-15">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_yaoqing.png">可以告诉您的朋友，下载注册可领
						<span>最低41元</span>现金奖励，微信可 <span>立即提现</span>。
					</p>

				</div>
				<div class="lines"></div>
				<div class="how">
					<p class="oneinfro3">
						<img class="share_banner icon_right"
							src="${basePath}static/invited/img2/icon_right.png">如何邀请<img
							class="share_banner icon_left"
							src="${basePath}static/invited/img2/icon_left.png">
					</p>
					<p class="oneinfro4">方法一：晒收入</p>
					<p class="btntop">
						<img src="${basePath}static/invited/img2/h1.png" class="pulse"
							alt=""> <img src="${basePath}static/invited/img2/h2.png"
							class="pulse" alt="">
					</p>
					<p class="oneinfro5" style="padding-top: 0">点击晒收入、晒提现可以将您的收入图片分享到微信好友、朋友圈等位置，好友通过二维码下载登录后立刻成为您的徒弟哦。</p>
					<p class="oneinfro4">方法二：二维码邀请</p>
					<p class="codes">
						<img src="${basePath}static/invited/img2/h3.png" alt="">
					</p>
					<p class="oneinfro5">点击二维码邀请将二维码图片分享到微信好友、朋友圈等位置，好友通过二维码下载登录后立刻成为您的徒弟哦。</p>
					<!-- <p class="oneinfro4">方法三：我的专属好友邀请链接</p>
                        <p class="copys">
                            <span class="links" id="links"></span>
                            <img class="copybtn copybtn1" src="${basePath}static/invited/img2/f.png" alt="">
                        </p>
                        <p class="oneinfro5">点击复制将链接发送到微信好友、朋友圈，好友点击链接下载登录后立刻成为您的徒弟哦。</p> -->
					<p class="oneinfro4">方法三：我的邀请码</p>
					<p class="invite">
						我的邀请码: <span class="int" id="int">${user.visitCode}</span> <img
							class="copybtn copybtn2"
							src="${basePath}static/invited/img2/f.png" alt="">
					</p>
					<p class="oneinfro5">好友通过应用商店搜索“云交汇app”下载并登录，在我的页面输入您的邀请码，即可成为您的徒弟。</p>
					<p class="oneinfro4 short_message">方法四：短信邀请</p>
					<p class="message short_message">
						<img onclick="message()"
							src="${basePath}static/invited/img2/message.png" alt="">
					</p>
					<p class="oneinfro5 short_message">点击短信邀请将邀请链接发送给通讯录好友/好友点击链接下载登陆后立刻成为您的徒弟哦!</p>
					<!--<img class="how_img" src="${basePath}static/invited/img2/how1.png" alt="">-->
					<!-- <img class="how_img" src="${basePath}static/invited/img2/how.png" alt=""> -->
					<p class="oneinfro4 way_six">方法五：微信邀请</p>
					<img src="img2/Group7.png" alt="">
					<p class="oneinfro5">特别提示：新好友需要在新设备上注册+有效阅读5篇文章，方可获得奖励，好友点击下载登录后立刻成为您的徒弟。</p>
					<p class="oneinfro4 careful">注意事项:</p>
					<p class="per_careful">凡是存在以下违规行为的用户，云交汇有权扣除其作弊奖励，行为严重者将做封号处理，请您正常使用平台收徒。</p>
					<p class="per_careful">1、一个手机注册登录多个账号、虚拟机注册、垃圾评论、实施网络攻击、违规收徒、各类形式诱导收徒。</p>
					<p class="per_careful">2、平台内评论带有收徒邀请码字样的信息。</p>
					<p class="per_careful1">* 本次活动最终解释权归“云交汇”所有</p>
				</div>
			</div>
			<div class="content bottom">
				<div class="contents">
					<dl>
						<dt>
							<p class="ps">今日好友人数:0</p>
							<!-- <p class="nub per1"></p> -->
						</dt>
						<dd class="b_line">
							<p class="ps">累计好友人数:1</p>
							<!-- <p class="nub per2"></p> -->
						</dd>
					</dl>
					<dl>
						<dt>
							<p class="ps">累计提成:0</p>
							<!-- <p class="nub num1"></p> -->
						</dt>
						<dd class="b_line">
							<p class="ps">累计奖励:0.0</p>
							<!-- <p class="nub num2"></p> -->
						</dd>
					</dl>
				</div>
				<ul class="myfriend">
					<li>昵称 <b class="line2"></b>
					</li>
					<li>累计奖励 <b class="line2"></b>
					</li>
					<li>累计提成</li>
				</ul>
				<div class="none">
					<p>据统计,50%以上的云交汇</p>
					<p>用户靠徒儿进贡,每天在躺着收零花钱。</p>
				</div>
			</div>
		</div>
	</div>
	<div class="cover_1">
		<div class="c1">
			<img class="close1" src="${basePath}static/invited/img2/close.png"
				alt="">
			<p class="tit_title">奖励计算方式</p>
			<ul class="tit_list">
				<li>1、每邀请一位好友奖励 <b class="red_red">2-3元</b>，奖励分 <b
					class="red_red">15次</b>发放，每次好友当日阅读 <b class="red_red">5篇</b>文章并获得金币奖励后，您即可获得当日现金奖励。
				</li>
				<li>2、第1天奖励 <b class="red_red">2元</b>，第2～15天每天奖励 <b
					class="red_red">0.5元</b>；
				</li>
				<li>3、每个好友每天限奖励师傅一次。</li>
			</ul>
		</div>
	</div>
	<!-- 底部导航 -->
	<div class="nav">
		<ul class="nav_list">
			<li class="first_nav" onclick="shareWeixin()">
				<dl>
					<dt>
						<img src="${basePath}static/invited/img2/1.png" alt="">
					</dt>
					<dd>微信邀请</dd>
				</dl>
			</li>
			<li class="first_nav" onclick="shareWeixinQuan()">
				<dl>
					<dt>
						<img src="${basePath}static/invited/img2/2.png" alt="">
					</dt>
					<dd>朋友圈邀请</dd>
				</dl>
			</li>
			<li class="first_nav" onclick="shareIncomeToWeixin()">
				<dl>
					<dt>
						<img src="${basePath}static/invited/img2/5.png" alt="">
					</dt>
					<dd>晒收入</dd>
				</dl>
			</li>
			<li class="first_nav" onclick="shareQQ()">
				<dl>
					<dt>
						<img src="${basePath}static/invited/img2/3.png" alt="">
					</dt>
					<dd>QQ邀请</dd>
				</dl>
			</li>
			<li class="first_nav" onclick="qq_qrcode_share()">
				<dl>
					<dt>
						<img src="${basePath}static/invited/img2/4.png" alt="">
					</dt>
					<dd>二维码邀请</dd>
				</dl>
			</li>
		</ul>
	</div>
	<div class="sharebox">
		<div class="shareicon">
			<ul class="share_box">
				<li onclick="share('weixintmline')">
					<p class="icon_img">
						<img src="${basePath}static/invited/img2/e1.png" alt="">
					</p>
					<p class="share_tex">朋友圈邀请</p>
				</li>
				<li onclick="share('weixin')">
					<p class="icon_img">
						<img src="${basePath}static/invited/img2/e2.png" alt="">
					</p>
					<p class="share_tex">微信邀请</p>
				</li>
				<li id="qqline" onclick="share('qzone')">
					<p class="icon_img">
						<img src="${basePath}static/invited/img2/e4.png" alt="">
					</p>
					<p class="share_tex">QQ空间邀请</p>
				</li>
				<li onclick="qrcode_share()">
					<p class="icon_img">
						<img src="${basePath}static/invited/img2/e5.png" alt="">
					</p>
					<p class="share_tex">二维码邀请</p>
				</li>
				<li onclick="share('qq')">
					<p class="icon_img">
						<img src="${basePath}static/invited/img2/e3.png" alt="">
					</p>
					<p class="share_tex">QQ邀请</p>
				</li>
				<!-- <li id="copyStLink">
                        <p class="icon_img">
                            <img src="${basePath}static/invited/img2/e6.png" alt="">
                        </p>
                        <p class="share_tex">复制链接邀请</p>
                    </li> -->
			</ul>
			<p class="cancel">取消</p>
		</div>
	</div>
	<div class="contenter">
		<div class="con">
			<p class="top_con">您还没有收入金额，赶紧去做任务，晒一波吧</p>
			<p class="bottom_con">我知道了</p>
		</div>
	</div>
	<div id="shareTipDiv" class="cover1 hide">
		<div class="share-hb">
			<i class="close" onclick="$('#shareTipDiv').addClass('hide');"> <svg
					class="icon" aria-hidden="true">
                        <use xlink:href="#icon-guanbi">
                            <svg id="icon-guanbi"
						viewBox="0 0 1024 1024" width="10%" height="0.45rem">
                                <path
							d="M583.168 523.776L958.464 148.48c18.944-18.944 18.944-50.176 0-69.12l-2.048-2.048c-18.944-18.944-50.176-18.944-69.12 0L512 453.12 136.704 77.312c-18.944-18.944-50.176-18.944-69.12 0l-2.048 2.048C46.08 98.304 46.08 129.536 65.536 148.48l375.296 375.296-375.296 375.296c-18.944 18.944-18.944 50.176 0 69.12l2.048 2.048c18.944 18.944 50.176 18.944 69.12 0l375.296-375.296 375.296 375.296c18.944 18.944 50.176 18.944 69.12 0l2.048-2.048c18.944-18.944 18.944-50.176 0-69.12l-375.296-375.296z"></path>
                            </svg>
                        </use>
                    </svg>
			</i>
			<div class="hongbao-box">
				<img
					src="http://thirdwx.qlogo.cn/mmopen/vi_32/LkZsmVEt7rMnwHYuBz1j9rqwkOicPMrCibrDsWfwEviabfMSoJdZRsOibLGgZRzSx6g1NNTDDPiaHsiaicrYEyrQQJ0Bg/132"
					alt="" class="tx-img" />
				<p id="tipContent" class="col-white"></p>
				<i class="money"></i> <a href="javascript:;" class="s-tx">可提现</a> <img
					src="" alt="" class="hb-ewm" />
				<!-- <img class="hb-ewm" src="https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=fa9140accd95d143ce7bec711299e967/2934349b033b5bb571dc8c5133d3d539b600bc12.jpg" alt=""> -->
				<p id="tipBottom" class="col-white"></p>
			</div>
			<div class="s-hb-btn clearfix">
				<a href="javascript: $('#shareTipDiv').addClass('hide');" class="fl">下次吧</a>
				<input id="shareType" hidden="hidden" /> <a
					href="javascript:shareFunc();" class="fr">分享朋友圈</a>

			</div>
			<p class="col-gray">扫码者自动算为您徒弟</p>
		</div>
		<div class="cover">
			<div class="mind">
				<p>二维码正在生成中...</p>
				<p>请稍后再试!</p>
			</div>
		</div>
	</div>
	<!-- 复制链接弹层 style="display: none;" -->
	<div id="d_cpurl" class="mask" style="display: none;">
		<div class="success">
			<div class="success-info">复制成功</div>
		</div>
	</div>
</body>

<script type="text/javascript"
	src="${basePath}static/invited/common/js/jquery-2.1.4.min.js"></script>
<script src="${basePath}static/invited/js/clipboard.min.js"></script>
<script src="${basePath}static/invited/js/swiper.min.js"></script>
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
			window.location.href = "code.jsp?code=" + code;
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
		//有没有邀请
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
			if (myversion > 38) { //新版分享客户端调后台获取数据..不用传数据
				window.mobile.shareToWXPYQJs("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			}
		} else if (sharewechat == "weixin") { //微信分享
			if (myversion > 38) {
				window.mobile.shareToWXJs("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			}
		} else if (sharewechat == "qq") { //qq分享
			if (myversion > 38) {
				window.mobile.shareToQQJs("", "receive");
			} else {
				getShareData(sharewechat); //旧版
			}
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
		//跳转原生二维码邀请
		if ("" == "android") {
			if (myversion > 34) {
				try {
					window.mobile.jumpMa();
				} catch (e) {
				}
			} else {
				window.location.href = $ctx
						+ "/weixin20/member/receiveMonkeyXd3.action";
			}
		} else if ("" == "iOS") {
			if (myversion1 > 539) {
				qrcodeReceiveFunc("share");
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
